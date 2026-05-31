package org.example;

public class Coordenada {

    private int fila;
    private int columna;

    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenada c = (Coordenada) o;
        return fila == c.fila && columna == c.columna;
    }
//recurrimos a la ia para solucionar el problema
    @Override
    public int hashCode() {
        return 31 * fila + columna;
    }
}