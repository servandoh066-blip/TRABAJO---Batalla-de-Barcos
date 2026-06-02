package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private Tablero tablero;
    private HashMap<String, Integer> barcosRestantes;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
        this.barcosRestantes = new HashMap<>();
        this.barcosRestantes.put("Portaaviones", 1);
        this.barcosRestantes.put("Caza", 1);
        this.barcosRestantes.put("Submarino", 1);
        this.barcosRestantes.put("Destructor", 1);
        this.barcosRestantes.put("Fragata", 1);
    }
    public HashMap<String, Integer> getBarcosRestantes(){
        return barcosRestantes;
    }

    public Tablero getTablero()
    {
        return tablero;
    }

    public String getNombre() {
        return nombre;
    }
    //creado con la ia
    public void mostrarTableroColocacion() {
        System.out.println("\n=== MAPA ACTUAL DE " + this.nombre.toUpperCase() + " ===");
        System.out.println("     0  1  2  3  4  5  6  7  8  9");
        System.out.println("   +-----------------------------+");

        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i + " |");
            for (int j = 0; j < 10; j++) {
                Casilla c = this.tablero.getCasilla(i, j);
                if (c.tieneBarco()) {
                    System.out.print(" B ");
                } else {
                    System.out.print(" ~ ");
                }
            }
            System.out.println("|");
        }
        System.out.println("   +-----------------------------+");
    }

    public void colocarBarcos() {
        Scanner sc = new Scanner(System.in);

        int[] tamaños = {5, 4, 3, 3, 2};
        String[] nombres = {"Portaaviones", "Caza", "Submarino", "Destructor", "Fragata"};
        System.out.println("Colocando barcos para " + nombre);

        mostrarTableroColocacion();

        for (int i = 0; i < tamaños.length; i++) {
            boolean colocado = false;
            while (!colocado) {
                System.out.println("Coloca el " + nombres[i] + " (tamaño " + tamaños[i] + ")");
                System.out.print("Fila inicial: ");
                int fila = sc.nextInt();
                System.out.print("Columna inicial: ");
                int col = sc.nextInt();
                System.out.print("En horizontal? (true/false): ");
                boolean horizontal = sc.nextBoolean();

                Barco b = null;
                switch (nombres[i]) {
                    case "Portaaviones":
                        b = new Portaaviones();
                        break;
                    case "Caza":
                        b = new Caza();
                        break;
                    case "Submarino":
                        b = new Submarino();
                        break;
                    case "Destructor":
                        b = new Destructor();
                        break;
                    case "Fragata":
                        b = new Fragata();
                        break;
                }

                if (tablero.posicionValida(b, new Coordenada(fila, col), horizontal)) {
                    tablero.colocarBarco(b, new Coordenada(fila, col), horizontal);
                    colocado = true;

                    mostrarTableroColocacion();
                } else {
                    System.out.println("Posición inválida, inténtalo de nuevo.");
                }
            }
        }
    }

}
