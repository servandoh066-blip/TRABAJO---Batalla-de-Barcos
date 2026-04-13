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

    public boolean recibirDisparo(Coordenada c) {
        Casilla casilla = casillas[c.getFila()][c.getColumna()];
        casilla.marcarDisparo();

        if (casilla.tieneBarco()) {
            Barco b = casilla.getBarco();
            b.registrarImpacto(c);
            System.out.println("Impacto en " + b.getNombre());
            return true;
        }

        System.out.println("Agua.");
        return false;
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

        if (horizontal) {
            if (col + barco.getTamano() > 10) return false;
            for (int i = 0; i < barco.getTamano(); i++) {
                if (casillas[fila][col + i].tieneBarco()) return false;
            }
        } else {
            if (fila + barco.getTamano() > 10) return false;
            for (int i = 0; i < barco.getTamano(); i++) {
                if (casillas[fila + i][col].tieneBarco()) return false;
            }
        }

        return true;
    }
}
