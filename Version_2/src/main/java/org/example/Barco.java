package org.example;

import java.util.ArrayList;

public abstract class Barco {

    private String nombre;
    private int tamaño;
    private ArrayList<Coordenada> posiciones;
    private ArrayList<Coordenada> impactos;

    public Barco(String nombre, int tamano) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.posiciones = new ArrayList<>();
        this.impactos = new ArrayList<>();
    }

    public void addPosicion(Coordenada c) {
        posiciones.add(c);
    }

    public boolean estaHundido() {
        return impactos.size() == tamaño;
    }

    public void registrarImpacto(Coordenada c) {
        if(!impactos.contains(c)){
            impactos.add(c);
        }
    }

    public int getTamano() {
        return tamaño;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Coordenada> getPosiciones() {
        return posiciones;
    }

}
