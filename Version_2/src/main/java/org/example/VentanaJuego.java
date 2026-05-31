package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJuego extends JFrame {

    private Juego backendJuego;
    private Jugador jugador1;
    private Jugador jugador2;

    // Componentes visuales organizados por pestañas para cada jugador
    private JButton[][] casillasJ1;
    private JButton[][] casillasJ2;
    private JLabel lblEstado;

    public VentanaJuego() {
        // 1. Inicializamos las entidades de tu modelo original
        jugador1 = new Jugador("Jugador 1");
        jugador2 = new Jugador("Jugador 2");

        // Colocamos barcos de prueba directamente para que puedas verlos al abrir la app
        inicializarBarcosFijos(jugador1, true);
        inicializarBarcosFijos(jugador2, false);

        // 2. Configuración básica de la ventana Swing
        setTitle("Batalla Naval - Vista de Tableros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Etiqueta superior de estado
        lblEstado = new JLabel("¡Preparaos! Los barcos están desplegados en los tableros.", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblEstado, BorderLayout.NORTH);

        // 3. Crear el contenedor de pestañas (Una para cada jugador)
        JTabbedPane pestanas = new JTabbedPane();

        // Creamos los paneles de cada jugador pasando su propio tablero
        JPanel panelJ1 = crearPanelTablero(jugador1, casillasJ1 = new JButton[10][10], jugador2);
        JPanel panelJ2 = crearPanelTablero(jugador2, casillasJ2 = new JButton[10][10], jugador1);

        pestanas.addTab("Tablero de: " + jugador1.getNombre(), panelJ1);
        pestanas.addTab("Tablero de: " + jugador2.getNombre(), panelJ2);

        add(pestanas, BorderLayout.CENTER);
    }

    /**
     * Construye dinámicamente el panel del tablero de un jugador.
     * Muestra sus barcos de forma primordial.
     */
    private JPanel crearPanelTablero(Jugador dueñoDelTablero, JButton[][] matrizBotones, Jugador enemigo) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));

        // Mensaje de ayuda dentro de la pestaña
        JLabel lblInfo = new JLabel("Visualización del mapa de " + dueñoDelTablero.getNombre() + " (Los barcos están en GRIS)", SwingConstants.CENTER);
        lblInfo.setForeground(Color.DARK_GRAY);
        panelPrincipal.add(lblInfo, BorderLayout.NORTH);

        // Cuadrícula de 10x10 botones
        JPanel cuadricula = new JPanel(new GridLayout(10, 10, 2, 2));
        Tablero tablero = dueñoDelTablero.getTablero();

        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                JButton boton = new JButton("~");
                boton.setFont(new Font("Monospaced", Font.BOLD, 12));

                // Creamos la coordenada correspondiente (Índices 0-9 para tu matriz)
                Coordenada coordActual = new Coordenada(f, c);

                // LO PRIMORDIAL: Verificar si la casilla tiene un barco asignado usando tu backend
                // Si la casilla tiene barco, la pintamos de un color identificativo (Gris Oscuro)
                if (tablero.posicionValida(new Barco("Detectar", 1), coordActual, true) == false) {
                    // Si posicionValida da false es porque o hay un barco encima o se sale,
                    // como estamos dentro de 0-9, significa que la celda está ocupada por un barco.
                    boton.setBackground(new Color(108, 117, 125)); // Color Gris Barco
                    boton.setForeground(Color.WHITE);
                    boton.setText("B"); // Inicial de Barco
                } else {
                    boton.setBackground(new Color(212, 239, 252)); // Color Agua azul claro
                    boton.setForeground(Color.BLUE);
                }

                // Evento de disparo al hacer clic sobre la celda
                final int filaDisparo = f;
                final int colDisparo = c;
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Al disparar sobre el tablero del jugador dueño, el atacante es el 'enemigo'
                        boolean tocado = tablero.recibirDisparo(new Coordenada(filaDisparo, colDisparo));

                        if (tocado) {
                            boton.setText("X");
                            boton.setBackground(Color.RED);
                            boton.setForeground(Color.WHITE);
                        } else {
                            boton.setText("O");
                            boton.setBackground(Color.WHITE);
                            boton.setForeground(Color.LIGHT_GRAY);
                        }

                        // Deshabilitamos el botón para evitar dobles clics erróneos
                        boton.setEnabled(false);

                        // Comprobar si todos los barcos de este tablero han caído
                        if (tablero.todasHundidas()) {
                            lblEstado.setText("¡PARTIDA TERMINADA! " + enemigo.getNombre() + " ha ganado.");
                            JOptionPane.showMessageDialog(null, "¡Victoria magistral de " + enemigo.getNombre() + "!");
                        }
                    }
                });

                matrizBotones[f][c] = boton;
                cuadricula.add(boton);
            }
        }

        panelPrincipal.add(cuadricula, BorderLayout.CENTER);
        return panelPrincipal;
    }

    /**
     * Coloca barcos automáticamente usando el rango correcto (0-9) adaptado
     * para verificar visualmente que la interfaz los pinta en la cuadrícula.
     */
    private void inicializarBarcosFijos(Jugador j, boolean posicionAlterna) {
        int desplazamiento = posicionAlterna ? 0 : 4;

        // Respetamos escrupulosamente tu flujo: crear Barco -> comprobar posición -> colocar en tablero
        Barco p = new Barco("Portaaviones", 5);
        Coordenada c1 = new Coordenada(1 + desplazamiento, 1);
        if (j.getTablero().posicionValida(p, c1, true)) {
            j.getTablero().colocarBarco(p, c1, true);
        }

        Barco s = new Barco("Submarino", 3);
        Coordenada c2 = new Coordenada(3 + desplazamiento, 2);
        if (j.getTablero().posicionValida(s, c2, false)) {
            j.getTablero().colocarBarco(s, c2, false);
        }
    }
}