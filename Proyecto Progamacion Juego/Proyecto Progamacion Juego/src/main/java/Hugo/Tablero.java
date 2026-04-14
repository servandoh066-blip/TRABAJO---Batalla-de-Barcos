package Hugo;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {

    static final int TAMANIO = 8;

    static final char AGUA    = '~';
    static final char BARCO   = 'B';
    static final char FALLO   = 'O';
    static final char TOCADO  = 'X';
    static final char HUNDIDO = '#';

    private char[][] celdas;
    private Barco[][] barcoEnPosicion;
    private ArrayList<Barco> listaBarcos;

    public Tablero() {
        celdas = new char[TAMANIO][TAMANIO];
        barcoEnPosicion = new Barco[TAMANIO][TAMANIO];
        listaBarcos = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                celdas[i][j] = AGUA;
            }
        }
    }

    public void colocarBarcos() {
        Random random = new Random();

        int[] tamanios = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        String[] nombres = {"Portaaviones", "Crucero", "Crucero", "Destructor",
                            "Destructor", "Destructor", "Submarino", "Submarino",
                            "Submarino", "Submarino"};

        for (int i = 0; i < tamanios.length; i++) {
            Barco barco = new Barco(nombres[i], tamanios[i]);
            boolean colocado = false;
            int intentos = 0;

            while (!colocado && intentos < 500) {
                intentos++;
                boolean horizontal = random.nextBoolean();
                int fila = random.nextInt(TAMANIO);
                int col  = random.nextInt(TAMANIO);

                if (cabe(fila, col, tamanios[i], horizontal)) {
                    poner(fila, col, tamanios[i], horizontal, barco);
                    listaBarcos.add(barco);
                    colocado = true;
                }
            }

            // Si no encontro hueco, limpiar y empezar desde cero
            if (!colocado) {
                for (int f = 0; f < TAMANIO; f++)
                    for (int c = 0; c < TAMANIO; c++)
                        celdas[f][c] = AGUA;
                listaBarcos.clear();
                i = -1;
            }
        }
    }

    private boolean cabe(int fila, int col, int tamanio, boolean horizontal) {
        if (horizontal && col + tamanio > TAMANIO) return false;
        if (!horizontal && fila + tamanio > TAMANIO) return false;

        for (int i = 0; i < tamanio; i++) {
            int f = horizontal ? fila    : fila + i;
            int c = horizontal ? col + i : col;

            for (int df = -1; df <= 1; df++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nf = f + df;
                    int nc = c + dc;
                    if (nf >= 0 && nf < TAMANIO && nc >= 0 && nc < TAMANIO) {
                        if (celdas[nf][nc] == BARCO) return false;
                    }
                }
            }
        }
        return true;
    }

    private void poner(int fila, int col, int tamanio, boolean horizontal, Barco barco) {
        for (int i = 0; i < tamanio; i++) {
            int f = horizontal ? fila    : fila + i;
            int c = horizontal ? col + i : col;
            celdas[f][c] = BARCO;
            barcoEnPosicion[f][c] = barco;
        }
    }

    public String disparo(int fila, int col) {
        char celda = celdas[fila][col];

        if (celda == FALLO || celda == TOCADO || celda == HUNDIDO) {
            return "REPETIDO";
        }

        if (celda == AGUA) {
            celdas[fila][col] = FALLO;
            return "AGUA";
        }

        Barco barco = barcoEnPosicion[fila][col];
        barco.golpear();
        celdas[fila][col] = TOCADO;

        if (barco.estaHundido()) {
            marcarHundido(barco);
            rodearDeAgua(barco);
            return "HUNDIDO:" + barco.getNombre();
        }

        return "TOCADO";
    }

    private void marcarHundido(Barco barco) {
        for (int f = 0; f < TAMANIO; f++) {
            for (int c = 0; c < TAMANIO; c++) {
                if (barcoEnPosicion[f][c] == barco) {
                    celdas[f][c] = HUNDIDO;
                }
            }
        }
    }

    private void rodearDeAgua(Barco barco) {
        for (int f = 0; f < TAMANIO; f++) {
            for (int c = 0; c < TAMANIO; c++) {
                if (barcoEnPosicion[f][c] == barco) {
                    for (int df = -1; df <= 1; df++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            int nf = f + df;
                            int nc = c + dc;
                            if (nf >= 0 && nf < TAMANIO && nc >= 0 && nc < TAMANIO) {
                                if (celdas[nf][nc] == AGUA) {
                                    celdas[nf][nc] = FALLO;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean todosHundidos() {
        for (Barco b : listaBarcos) {
            if (!b.estaHundido()) return false;
        }
        return true;
    }

    public char getCelda(int fila, int col) {
        return celdas[fila][col];
    }

    public void setCelda(int fila, int col, char valor) {
        celdas[fila][col] = valor;
    }

    public void imprimirConBarcos() {
        imprimirCabecera();
        for (int f = 0; f < TAMANIO; f++) {
            System.out.print((char) ('A' + f) + " | ");
            for (int c = 0; c < TAMANIO; c++) {
                System.out.print(celdas[f][c] + " ");
            }
            System.out.println();
        }
    }

    public void imprimirSinBarcos() {
        imprimirCabecera();
        for (int f = 0; f < TAMANIO; f++) {
            System.out.print((char) ('A' + f) + " | ");
            for (int c = 0; c < TAMANIO; c++) {
                char mostrar = (celdas[f][c] == BARCO) ? AGUA : celdas[f][c];
                System.out.print(mostrar + " ");
            }
            System.out.println();
        }
    }

    private void imprimirCabecera() {
        System.out.print("  | ");
        for (int c = 1; c <= TAMANIO; c++) {
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.println("  +-----------------");
    }
}
