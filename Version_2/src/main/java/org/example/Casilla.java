package org.example;

public class Casilla {

    private TipoCasilla estado;
    private Barco barco;

    public Casilla() {
        this.estado = TipoCasilla.AGUA;
        this.barco = null;
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


