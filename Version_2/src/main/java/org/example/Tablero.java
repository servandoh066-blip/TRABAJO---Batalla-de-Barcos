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

        for (int i = 0; i < barco.getTamano(); i++) {
            if (horizontal) {
                casillas[fila][col + i].setBarco(barco);
                barco.addPosicion(new Coordenada(fila, col + i));
            } else {
                casillas[fila + i][col].setBarco(barco);
                barco.addPosicion(new Coordenada(fila + i, col));
            }
        }

        barcos.add(barco);
    }


    public boolean recibirDisparo(Coordenada coord) {
        int fila = coord.getFila();
        int columna = coord.getColumna();

        Casilla casilla = casillas[fila][columna];

        // Si la casilla tiene un barco...
        if (casilla.tieneBarco()) {
            casilla.setDisparada(true);
            System.out.println("Tocado");
            return true;
        }
        // Si la casilla está vacía (Agua)...
        else {
            casilla.setDisparada(true);
            System.out.println("Agua");
            return false;
        }
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
        int tam = barco.getTamano();

        if (horizontal) {
            if (col + barco.getTamano() > 10) return false;
            for (int i = 0; i < tam; i++) {
                if (casillas[fila][col + i].tieneBarco()) return false;
            }
        } else {
            if (fila + tam > 10 || col < 0 || col >= 10 || fila < 0) return false;
            for (int i = 0; i <tam; i++) {
                if (casillas[fila + i][col].tieneBarco()) return false;
            }
        }

        return true;
    }
    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }

}
