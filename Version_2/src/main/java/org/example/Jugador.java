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


        }
    }
}
