package org.example;

import java.util.ArrayList;

public class Barco {

    private String nombre;
    private int tamano;
    private ArrayList<Coordenada> posiciones;
    private ArrayList<Coordenada> impactos;

    public Barco(String nombre, int tamano) {
        this.nombre = nombre;
        this.tamano = tamano;
        posiciones = new ArrayList<>();
        impactos = new ArrayList<>();
    }

    public void addPosicion(Coordenada c) {
        posiciones.add(c);
    }

    public boolean estaHundido() {
        return impactos.size() == tamano;
    }

    public void registrarImpacto(Coordenada c) {
        impactos.add(c);
    }

    public int getTamano() {
        return tamano;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Coordenada> getPosiciones() {
        return posiciones;
    }

}
