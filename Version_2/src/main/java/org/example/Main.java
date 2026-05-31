package org.example;
import javax.swing.SwingUtilities;
public class Main {
    static void main(String[] args) {
        Juego juego = new Juego();
        juego.iniciar();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                        VentanaJuego ventana = new VentanaJuego();
                        ventana.setVisible(true);
                    }
                });
            }
        }

