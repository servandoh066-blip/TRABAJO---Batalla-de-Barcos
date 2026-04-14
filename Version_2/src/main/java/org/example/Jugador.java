package org.example;

import java.util.Scanner;

public class Jugador {


    private String nombre;
    private Tablero tablero;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
    }

    public Tablero getTablero()
    {
        return tablero;
    }

    public String getNombre() {
        return nombre;
    }

    public void colocarBarcos() {
        Scanner sc = new Scanner(System.in);

        int[] tamanos = {5, 4, 3, 3, 2};
        String[] nombres = {"Portaaviones", "Acorazado", "Submarino", "Destructor", "Fragata"};

        System.out.println("Colocando barcos para " + nombre);

        for (int i = 0; i < tamanos.length; i++) {
            boolean colocado = false;
            while (!colocado) {
                System.out.println("Coloca el " + nombres[i] + " (tamaño " + tamanos[i] + ")");

                System.out.print("Fila inicial: ");
                int fila = sc.nextInt();

                System.out.print("Columna inicial: ");
                int col = sc.nextInt();

                System.out.print("Horizontal (true/false): ");
                boolean horizontal = sc.nextBoolean();

                Barco b = new Barco(nombres[i], tamanos[i]);

                if (tablero.posicionValida(b, new Coordenada(fila, col), horizontal)) {
                    tablero.colocarBarco(b, new Coordenada(fila, col), horizontal);
                    colocado = true;
                } else {
                    System.out.println("Posición no válida, intentalo de nuevo.");
                }
            }


        }
    }
}
