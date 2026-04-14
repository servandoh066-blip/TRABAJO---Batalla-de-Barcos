package Hugo;

import java.util.ArrayList;
import java.util.Random;

public class JugadorIA {

    private String nombre;
    private Tablero miTablero;
    private Tablero tableroAtaque;
    private boolean[][] yaDisparado;
    private Random random;

    // Variables para el modo caza
    private boolean modoCaza;
    private ArrayList<int[]> colaDisparos;
    private ArrayList<int[]> casillasGolpe;

    public JugadorIA(String nombre) {
        this.nombre = nombre;
        this.miTablero = new Tablero();
        this.tableroAtaque = new Tablero();
        this.yaDisparado = new boolean[Tablero.TAMANIO][Tablero.TAMANIO];
        this.random = new Random();
        this.modoCaza = false;
        this.colaDisparos = new ArrayList<>();
        this.casillasGolpe = new ArrayList<>();
    }

    public void prepararFlota() {
        miTablero.colocarBarcos();
    }

    public String atacar(Tablero tableroEnemigo) {
        int[] objetivo = elegirCasilla();
        int fila = objetivo[0];
        int col  = objetivo[1];

        yaDisparado[fila][col] = true;

        String resultado = tableroEnemigo.disparo(fila, col);

        // Sincronizar tablero visual de la CPU
        for (int f = 0; f < Tablero.TAMANIO; f++) {
            for (int c = 0; c < Tablero.TAMANIO; c++) {
                char celdaReal = tableroEnemigo.getCelda(f, c);
                if (celdaReal == Tablero.FALLO || celdaReal == Tablero.TOCADO || celdaReal == Tablero.HUNDIDO) {
                    tableroAtaque.setCelda(f, c, celdaReal);
                    yaDisparado[f][c] = true;
                }
            }
        }

        System.out.println(nombre + " dispara en " + (char) ('A' + fila) + (col + 1)
                + "  ->  " + traducirResultado(resultado));

        procesarResultado(fila, col, resultado);

        return resultado;
    }

    private int[] elegirCasilla() {
        if (modoCaza && !colaDisparos.isEmpty()) {
            if (casillasGolpe.size() >= 2) {
                int[] siguiente = siguienteEnLinea();
                if (siguiente != null) return siguiente;
            }

            while (!colaDisparos.isEmpty()) {
                int[] candidata = colaDisparos.remove(0);
                if (esValida(candidata[0], candidata[1])) {
                    return candidata;
                }
            }
        }

        return buscarEnPatron();
    }

    private int[] siguienteEnLinea() {
        int[] primero = casillasGolpe.get(0);
        int[] ultimo  = casillasGolpe.get(casillasGolpe.size() - 1);

        int df = Integer.compare(ultimo[0], primero[0]);
        int dc = Integer.compare(ultimo[1], primero[1]);

        int nf = ultimo[0] + df;
        int nc = ultimo[1] + dc;
        if (esValida(nf, nc)) return new int[]{nf, nc};

        nf = primero[0] - df;
        nc = primero[1] - dc;
        if (esValida(nf, nc)) return new int[]{nf, nc};

        modoCaza = false;
        return null;
    }

    private int[] buscarEnPatron() {
        ArrayList<int[]> opciones = new ArrayList<>();

        for (int f = 0; f < Tablero.TAMANIO; f++) {
            for (int c = 0; c < Tablero.TAMANIO; c++) {
                if (!yaDisparado[f][c] && (f + c) % 2 == 0) {
                    opciones.add(new int[]{f, c});
                }
            }
        }

        if (opciones.isEmpty()) {
            for (int f = 0; f < Tablero.TAMANIO; f++) {
                for (int c = 0; c < Tablero.TAMANIO; c++) {
                    if (!yaDisparado[f][c]) {
                        opciones.add(new int[]{f, c});
                    }
                }
            }
        }

        return opciones.get(random.nextInt(opciones.size()));
    }

    private void procesarResultado(int fila, int col, String resultado) {
        if (resultado.equals("TOCADO")) {
            if (!modoCaza) {
                modoCaza = true;
                colaDisparos.clear();
                casillasGolpe.clear();
                agregarAdyacentes(fila, col);
            }
            casillasGolpe.add(new int[]{fila, col});

        } else if (resultado.startsWith("HUNDIDO")) {
            modoCaza = false;
            colaDisparos.clear();
            casillasGolpe.clear();
        }
    }

    private void agregarAdyacentes(int fila, int col) {
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : direcciones) {
            int nf = fila + d[0];
            int nc = col  + d[1];
            if (esValida(nf, nc)) {
                colaDisparos.add(new int[]{nf, nc});
            }
        }
    }

    private boolean esValida(int fila, int col) {
        return fila >= 0 && fila < Tablero.TAMANIO
            && col  >= 0 && col  < Tablero.TAMANIO
            && !yaDisparado[fila][col];
    }

    private String traducirResultado(String resultado) {
        if (resultado.equals("AGUA"))   return "Agua";
        if (resultado.equals("TOCADO")) return "Tocado!";
        if (resultado.startsWith("HUNDIDO")) {
            String nombreBarco = resultado.split(":")[1];
            return "HUNDIDO el " + nombreBarco + "!!";
        }
        return resultado;
    }

    public Tablero getMiTablero() {
        return miTablero;
    }

    public Tablero getTableroAtaque() {
        return tableroAtaque;
    }

    public String getNombre() {
        return nombre;
    }
}
