package org.example;

import java.util.ArrayList;

public class Tablero {


    private Casilla[][] casillas;
    private ArrayList<Barco> barcos;

    public Tablero() {
        casillas = new Casilla[10][10];
        barcos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public void colocarBarco(Barco barco, Coordenada inicio, boolean horizontal) {
        int fila = inicio.getFila();
        int col = inicio.getColumna();


    }

    public boolean recibirDisparo(Coordenada c) {
        Casilla casilla = casillas[c.getFila()][c.getColumna()];
        casilla.marcarDisparo();

    }

    public boolean todasHundidas() {
        for (Barco b : barcos) {
            if (!b.estaHundido()) return false;
        }
        return true;
    }

    public boolean posicionValida(Barco barco, Coordenada inicio, boolean horizontal) {
        int fila = inicio.getFila();
        int col = inicio.getColumna();


    }
}
