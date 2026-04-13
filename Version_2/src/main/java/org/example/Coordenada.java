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
        if (!(o instanceof Coordenada)) return false;
        Coordenada c = (Coordenada) o;
        return fila == c.fila && columna == c.columna;
    }
}
