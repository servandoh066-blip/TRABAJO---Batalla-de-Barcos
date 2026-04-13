package org.example;

public class Casilla {

    private boolean disparada;
    private Barco barco;

    public Casilla() {
        disparada = false;
        barco = null;
    }

    public void setBarco(Barco b) {
        barco = b;
    }

    public boolean tieneBarco() {
        return barco != null;
    }

    public void marcarDisparo() {
        disparada = true;
    }

    public boolean estaDisparada() {
        return disparada;
    }

    public Barco getBarco() {
        return barco;
    }
}
