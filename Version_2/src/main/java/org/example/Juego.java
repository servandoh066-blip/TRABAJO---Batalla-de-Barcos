package org.example;

import java.util.Scanner;

public class Juego {

    private Jugador jugador1;
    private Jugador jugador2;

    public Juego() {
        jugador1 = new Jugador("Jugador 1");
        jugador2 = new Jugador("Jugador 2");
    }

    public void iniciar() {
        // FASE 1: Colocación de barcos (Consola limpia para cada uno)
        jugador1.colocarBarcos();
        System.out.println("\n==========================================\n");
        jugador2.colocarBarcos();

        Scanner sc = new Scanner(System.in);

        // ¡MUY IMPORTANTE!: Limpiamos el salto de línea que dejó el último 'nextBoolean()'
        // de la colocación de barcos para que no se ralle el bucle.
        sc.nextLine();

        boolean terminado = false;

        // PRIMERO: Inicializamos las variables de los turnos
        Jugador actual = jugador1;
        Jugador enemigo = jugador2;

        // FASE 2: Bucle principal de juego
        while (!terminado) {

            // SEGUNDO: Ahora sí, llamamos al tablero gráfico usando las variables ya creadas
            mostrarInterfazGraficaTexto(actual, enemigo);

            System.out.println("👉 TURNO DE: " + actual.getNombre().toUpperCase());

            System.out.print("Introduce fila del disparo (0-9): ");
            int fila = sc.nextInt();

            System.out.print("Introduce col del disparo (0-9): ");
            int col = sc.nextInt();

            // Procesamos el disparo en el tablero del rival
            if (fila < 0 || fila >= 10 || col < 0 || col >= 10) {
                System.out.println("\n⚠️ ¡Coordenada fuera de rango! Pierdes el turno.");
            } else {
                Coordenada disparo = new Coordenada(fila, col);
                System.out.println("\n--- RESULTADO DEL DISPARO ---");
                enemigo.getTablero().recibirDisparo(disparo);
                System.out.println("-----------------------------\n");
            }

            // Comprobamos si el enemigo ha perdido todos sus barcos
            if (enemigo.getTablero().todasHundidas()) {
                mostrarInterfazGraficaTexto(actual, enemigo); // Render final del mapa
                System.out.println("\n🏆 ¡VICTORIA! " + actual.getNombre() + " ha hundido todos los barcos.");
                terminado = true;
                break;
            }

            // Pausa táctica para que el jugador vea si tocó barco ('X') o agua ('O')
            System.out.println("Presiona ENTER para cambiar de turno...");
            sc.nextLine(); // Limpia el buffer del número entero anterior
            sc.nextLine(); // Espera la pulsación real de ENTER

            // Intercambio de roles para el siguiente turno
            Jugador temp = actual;
            actual = enemigo;
            enemigo = temp;
        }
        sc.close();
    }

    /**
     * Dibuja los mapas en la terminal de forma organizada en paralelo
     */
    private void mostrarInterfazGraficaTexto(Jugador jugadorActual, Jugador jugadorEnemigo) {
        // Limpieza de pantalla ANSI antes de pintar
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
            // Panel izquierdo: Tu Flota
            System.out.print(i + "  |");
            for (int j = 0; j < 10; j++) {
                Casilla c = miTablero.getCasilla(i, j);
                if (c.estaDisparada() && c.tieneBarco()) {
                    System.out.print(" X "); // Te han dado
                } else if (c.estaDisparada()) {
                    System.out.print(" O "); // Fallo del rival
                } else if (c.tieneBarco()) {
                    System.out.print(" B "); // Tus barcos colocados
                } else {
                    System.out.print(" ~ "); // Agua oculta
                }
            }

            System.out.print("|           " + i + "  |");

            // Panel derecho: Tu radar sobre el enemigo (No ves sus "B" ocultas)
            for (int j = 0; j < 10; j++) {
                Casilla c = tableroRival.getCasilla(i, j);
                if (c.estaDisparada() && c.tieneBarco()) {
                    System.out.print(" X "); // Le diste a un barco suyo
                } else if (c.estaDisparada()) {
                    System.out.print(" O "); // Agua
                } else {
                    System.out.print(" ~ "); // Mar sin explorar
                }
            }
            System.out.println("|");
        }
        System.out.println("   +-----------------------------+              +-----------------------------+");
        System.out.println("   Leyenda: [~] Mar  [B] Tu Barco  [O] Agua disparada  [X] Impacto\n");
    }
}   