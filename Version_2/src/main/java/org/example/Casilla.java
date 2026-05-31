package org.example;

public class Casilla {

    private TipoCasilla estado;
    private Barco barco;

    public Casilla() {
        this.estado = TipoCasilla.AGUA;
        this.barco = null;
    }
    public void setBarco(Barco b) {
        this.barco = b;
        this.estado=TipoCasilla.BARCO;
    }

    public boolean tieneBarco() {
        return barco != null;
    }

    public void marcarDisparo() {
        if(tieneBarco()){
        estado=TipoCasilla.TOCADO;
        }
        else {
            estado=TipoCasilla.AGUA_DISPARADA;
        }
    }

    public boolean estaDisparada() {
        return estado == TipoCasilla.TOCADO || estado == TipoCasilla.AGUA_DISPARADA;
    }

    public Barco getBarco() {
        return barco;
    }

    public TipoCasilla getEstado() {
        return estado;
    }
}


