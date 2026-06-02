package org.example;

import java.util.Scanner;

public class Juego {

    private Jugador jugador1;
    private Jugador jugador2;

    public Juego() {
        jugador1 = new Jugador("Jugador 1");
        jugador2 = new Jugador("Jugador 2");
    }

    public void iniciar() throws CoordenadaInvalidaExcepcion {
        jugador1.colocarBarcos();
        System.out.println("\n==========================================\n");
        jugador2.colocarBarcos();

        Scanner sc = new Scanner(System.in);

        sc.nextLine();

        boolean terminado = false;

        Jugador actual = jugador1;
        Jugador enemigo = jugador2;

        while (!terminado) {

            mostrarInterfazGraficaTexto(actual, enemigo);

            System.out.println("TURNO DE: " + actual.getNombre().toUpperCase());

            System.out.print("Introduce fila del disparo (0-9): ");
            int fila = sc.nextInt();

            System.out.print("Introduce col del disparo (0-9): ");
            int col = sc.nextInt();

            boolean Acertado = false;

            if (fila < 0 || fila >= 10 || col < 0 || col >= 10) {
                System.out.println("\nCoordenada fuera de rango");
            } else {
                Coordenada disparo = new Coordenada(fila, col);
                System.out.println("\n--- RESULTADO DEL DISPARO ---");
                enemigo.getTablero().recibirDisparo(disparo);

                try {
                    Acertado = enemigo.getTablero().recibirDisparo(disparo);
                }
                catch (CoordenadaInvalidaExcepcion e) {
                    System.out.println("Error: " + e.getMessage());
                    Acertado = false;
                }
                System.out.println("-----------------------------\n");
            }
            if (enemigo.getTablero().todasHundidas()) {
                mostrarInterfazGraficaTexto(actual, enemigo);
                System.out.println("\n¡VICTORIA! " + actual.getNombre() + " ha hundido todos los barcos.");
                terminado = true;
                break;
            }

            System.out.println("Presiona ENTER para cambiar de turno...");
            sc.nextLine();
            sc.nextLine();

            if (!Acertado) {
                Jugador temp = actual;
                actual = enemigo;
                enemigo = temp;
            } else {
                System.out.println("Le diste, dale otra");
            }
        }
    }


    private void mostrarInterfazGraficaTexto(Jugador jugadorActual, Jugador jugadorEnemigo) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("=========================================================================");
        System.out.println("         MONITOR DE BATALLA - TURNO DE: " + jugadorActual.getNombre().toUpperCase());
        System.out.println("=========================================================================");

        System.out.println("      [ TU MAPA DEFENSIVO ]                        [ TU RADAR DE ATAQUE ]");
        System.out.println("     0  1  2  3  4  5  6  7  8  9                 0  1  2  3  4  5  6  7  8  9");
        System.out.println("   +-----------------------------+              +-----------------------------+");

        Tablero miTablero = jugadorActual.getTablero();
        Tablero tableroRival = jugadorEnemigo.getTablero();

        for (int i = 0; i < 10; i++) {
            System.out.print(i + "  |");
            for (int j = 0; j < 10; j++) {
                Casilla c = miTablero.getCasilla(i, j);
                if (c.estaDisparada() && c.tieneBarco()) {
                    System.out.print(" X ");
                } else if (c.estaDisparada()) {
                    System.out.print(" O ");
                } else if (c.tieneBarco()) {
                    System.out.print(" B ");
                } else {
                    System.out.print(" ~ ");
                }
            }

            System.out.print("|           " + i + "  |");

            for (int j = 0; j < 10; j++) {
                Casilla c = tableroRival.getCasilla(i, j);
                if (c.estaDisparada() && c.tieneBarco()) {
                    System.out.print(" X ");
                } else if (c.estaDisparada()) {
                    System.out.print(" O ");
                    System.out.print(" ~ ");
                }
            }
            System.out.println("|");
        }
        System.out.println("   +-----------------------------+              +-----------------------------+");
        System.out.println("   [~] Mar  [B] Tu Barco  [O] Agua disparada  [X] Impacto\n");
    }
}   