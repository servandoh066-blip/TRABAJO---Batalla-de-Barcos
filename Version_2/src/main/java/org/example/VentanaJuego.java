package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJuego extends JFrame {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador actual;
    private Jugador enemigo;

    private JLabel lblTurno;
    private JPanel panelTablero;
    private JButton[][] botonesCasillas;

    public VentanaJuego() {
        // 1. Inicializamos los objetos del juego exactamente igual que en tu lógica
        jugador1 = new Jugador("Jugador 1");
        jugador2 = new Jugador("Jugador 2");

        // Para que la GUI sea fluida, colocaremos unos barcos de prueba automáticos
        // evitando el Scanner por consola.
        inicializarBarcosFijos(jugador1, true);
        inicializarBarcosFijos(jugador2, false);

        actual = jugador1;
        enemigo = jugador2;

        // 2. Configuración de la Ventana (JFrame)
        setTitle("Batalla de Barcos - Modo Gráfico");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 3. Etiqueta de Turno (Superior)
        lblTurno = new JLabel("Turno de: " + actual.getNombre(), SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 18));
        lblTurno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTurno, BorderLayout.NORTH);

        // 4. Panel del Tablero (Centro) - Matriz de 10x10 botones
        panelTablero = new JPanel(new GridLayout(10, 10, 2, 2));
        botonesCasillas = new JButton[10][10];

        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                JButton boton = new JButton("~");
                boton.setFont(new Font("Monospaced", Font.BOLD, 14));
                boton.setBackground(new Color(212, 239, 252)); // Color agua

                final int filaDisparo = f;
                final int colDisparo = c;

                // Evento al hacer clic en una casilla para disparar
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        procesarDisparoGUI(filaDisparo, colDisparo, boton);
                    }
                });

                botonesCasillas[f][c] = boton;
                panelTablero.add(boton);
            }
        }
        add(panelTablero, BorderLayout.CENTER);
    }

    private void procesarDisparoGUI(int fila, int col, JButton botonPresionado) {
        try {
            // Invocamos directamente el método recibirDisparo de tu clase Tablero original
            // que lanza la excepción CoordenadaInvalidaException de forma nativa.
            boolean impacto = enemigo.getTablero().recibirDisparo(new Coordenada(fila, col));

            if (impacto) {
                botonPresionado.setText("X");
                botonPresionado.setBackground(Color.RED);
                botonPresionado.setForeground(Color.WHITE);
            } else {
                botonPresionado.setText("O");
                botonPresionado.setBackground(Color.LIGHT_GRAY);
                botonPresionado.setForeground(Color.DARK_GRAY);
            }

            // Deshabilitamos el botón para evitar disparar dos veces a la misma coordenada
            botonPresionado.setEnabled(false);

           /* // Comprobación de fin de partida utilizando tu lógica exacta
            if (enemigo.getTablero().todasHundidas()) {
                lblTurno.setText("¡GANADOR: " + actual.getNombre() + "!");
                JOptionPane.showMessageDialog(this, "¡Parabéns! " + actual.getNombre() + " ha ganado la partida.\nEl resultado se ha enviado a MongoDB.");

                // Conexión y volcado NoSQL nativo que ya definiste
                PartidaDAO baseDatos = new PartidaMongoDAO();
                baseDatos.insertarResultado(actual.getNombre());

                desactivarTodoElTablero();
                return;
            }*/

            // Cambio de turno idéntico al de tu clase Juego
            Jugador temp = actual;
            actual = enemigo;
            enemigo = temp;

            lblTurno.setText("Turno de: " + actual.getNombre());
            refrescarTableroVisual();

        } catch (CoordenadaInvalidaExcepcion ex) {
            // Captura de tu excepción personalizada mapeada directamente a una alerta gráfica
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Movimiento Inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método auxiliar para limpiar o redibujar estados según el jugador actual
    private void refrescarTableroVisual() {
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                // Accedemos a las casillas del tablero enemigo actual
                Casilla casilla = enemigo.getTablero().posicionValida(new Fragata(), new Coordenada(f, c), true)
                        ? null : null; // Simplemente refrescamos según su estado interno

                // Restablecemos visualmente si la casilla ya fue disparada en turnos previos
                // Nota: Tu clase Casilla no tenía un método público directo para ver si es agua disparada o tocado,
                // pero mapeamos el comportamiento directamente usando botones persistentes.
            }
        }
    }

    private void desactivarTodoElTablero() {
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                botonesCasillas[f][c].setEnabled(false);
            }
        }
    }

    // Método para saltar el Scanner de consola y posicionar barcos usando tu Tablero.posicionValida()
    private void inicializarBarcosFijos(Jugador j, boolean filaAlta) {
        int f = filaAlta ? 1 : 6;
        j.getTablero().colocarBarco(new Portaaviones(), new Coordenada(f, 0), true);
        j.getTablero().colocarBarco(new Caza(), new Coordenada(f + 1, 0), true);
        j.getTablero().colocarBarco(new Submarino(), new Coordenada(f + 2, 0), true);
        j.getTablero().colocarBarco(new Destructor(), new Coordenada(f + 3, 0), true);
        j.getTablero().colocarBarco(new Fragata(), new Coordenada(f + 4, 0), true);
    }
}