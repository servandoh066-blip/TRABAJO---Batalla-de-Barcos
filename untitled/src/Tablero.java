import java.util.Random;
public class Tablero {
    private Casilla[][] casillas;
    private Barco[] barcos;

    public Tablero(int tamaño, int cantidadBarcos){
        casillas=new Casilla[tamaño][tamaño];
        barcos = new Barco[cantidadBarcos];

        for (int i=0; i<tamaño; i++){
            for (int j=0; j<tamaño; j++){
                casillas[i][j]=new Casilla();
            }
        }
        colocarBarcosAleatorio();
    }

    public void colocarBarcosAleatorio(){
        Random random=new Random();
        int colocados=0;

        while (colocados<barcos.length){
            int fila=random.nextInt(casillas.length);
            int columna=random.nextInt(casillas.length);

            if(!casillas[fila][columna].tieneBarco()){
                casillas[fila][columna].colacarBarco();
                barcos[colocados]=new Barco(fila, columna);
                colocados++;
            }
        }
    }

    public boolean disparar(int fila, int columna){
        casillas[fila][columna].disparar();
        return  casillas[fila][columna].tieneBarco();
    }

    public boolean quedanBarcos(){
        for (Barco b: barcos){
            if(!casillas[b.getFila()][b.getColumna()].estaDisparada()){ //----------------------------
                return true;
            }
        }
        return  false;

    }

    public void mostrarTableroOculto(){
        System.out.println(" 0 1 2 3 4");
        for(int i=0; i<casillas.length; i++){
            System.out.println(i + " ");
            for(int j=0; j< casillas.length; j++){
                if(!casillas[i][j].estaDisparada()){
                    System.out.println("~ ");
                } else if (casillas[i][j].tieneBarco()){
                    System.out.println("X ");
                }
                else {
                    System.out.println("O ");
                }
            }
            System.out.println();
        }


    }

    /*public void mostrarTableroCompleto(){

    }*/

}
