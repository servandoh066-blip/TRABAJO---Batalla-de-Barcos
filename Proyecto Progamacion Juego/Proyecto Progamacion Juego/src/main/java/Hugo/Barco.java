package Hugo;

public class Barco {

    private String nombre;
    private int tamanio;
    private int vecesGolpeado;

    public Barco(String nombre, int tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.vecesGolpeado = 0;
    }

    public void golpear() {
        vecesGolpeado++;
    }

    public boolean estaHundido() {
        return vecesGolpeado >= tamanio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTamanio() {
        return tamanio;
    }
}
