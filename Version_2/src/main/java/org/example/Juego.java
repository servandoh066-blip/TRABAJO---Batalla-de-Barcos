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
        jugador1.colocarBarcos();
        jugador2.colocarBarcos();

        Scanner sc = new Scanner(System.in);

        boolean terminado = false;
        Jugador actual = jugador1;
        Jugador enemigo = jugador2;

        while (!terminado) {
            System.out.println("\nTurno de " + actual.getNombre());

            System.out.print("Fila del disparo: ");
            int fila = sc.nextInt();

            System.out.print("Columna del disparo: ");
            int col = sc.nextInt();

            enemigo.getTablero().recibirDisparo(new Coordenada(fila, col));

            if (enemigo.getTablero().todasHundidas()) {
                System.out.println(actual.getNombre() + " ha ganado la partida");
                terminado = true;
            }

            Jugador temp = actual;
            actual = enemigo;
            enemigo = temp;
        }
    }
}
