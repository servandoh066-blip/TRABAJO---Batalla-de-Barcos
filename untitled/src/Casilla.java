public class Casilla {
    private boolean tieneBarco;
    private boolean disparado;

    public Casilla(){
    this.tieneBarco=false;
    this.disparado=false;
    }

    public boolean tieneBarco() {
        return tieneBarco;
    }

    public void colacarBarco(){
        this.tieneBarco=true;
    }

    public void disparar(){
        this.disparado=true;
    }

    public boolean estaDisparada(){
        return disparado;
    }
}
