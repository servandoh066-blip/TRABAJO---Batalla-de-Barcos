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

    public void colocarBarcos() {
        Scanner sc = new Scanner(System.in);

        int[] tamaños = {5, 4, 3, 3, 2};
        String[] nombres = {"Portaaviones", "Caza", "Submarino", "Destructor", "Fragata"};
        System.out.println("Colocando barcos para " + nombre);

        for (int i = 0; i < tamaños.length; i++) {
            boolean colocado = false;
            while (!colocado) {
                System.out.println("Coloca el " + nombres[i] + " (tamaaño " + tamaños[i] + ")");
                System.out.print("Fila inicial: ");
                int fila = sc.nextInt();
                System.out.print("Columnna inicial: ");
                int col = sc.nextInt();
                System.out.print("En horizontal? (true/false): ");
                boolean horizontal = sc.nextBoolean();

                Barco b = null;
                switch (nombres[i]) {
                    case "Portaaviones": b = new Portaaviones(); break;



                if (tablero.posicionValida(b, new Coordenada(fila, col), horizontal)) {
                    tablero.colocarBarco(b, new Coordenada(fila, col), horizontal);
                    colocado = true;
                } else {
                    System.out.println("Posición inválida, intentalo de nuevo.");
                }
            }


        }
    }
}
