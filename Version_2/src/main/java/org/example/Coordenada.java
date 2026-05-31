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
    @Override
    public boolean recibirDisparo(Coordenada c) throws CoordenadaInvalidaException {
        if (c.getFila() < 0 || c.getFila() >= 10 || c.getColumna() < 0 || c.getColumna() >= 10) {
            throw new CoordenadaInvalidaException("La coordenada tiene que estar entre 0-9");
        }
        return this.equals(c);
    }
}
