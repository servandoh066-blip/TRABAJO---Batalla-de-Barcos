package org.example;

public class Casilla {
    private boolean disparada;
    private Barco barco;
    public Casilla() {
        this.disparada = false;
        this.barco = null;
    }
    public boolean tieneBarco() {
        return this.barco != null;
    }

    public boolean estaDisparada() {
        return this.disparada;
    }
    public void setDisparada(boolean disparada) {
        this.disparada = disparada;
    }

    public Barco getBarco() {
        return this.barco;
    }
    public void setBarco(Barco barco) {
        this.barco = barco;
    }
}