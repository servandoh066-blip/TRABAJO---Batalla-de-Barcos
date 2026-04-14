package Hugo;

import java.util.Scanner;

public class Jugador {

    private String nombre;
    private Tablero miTablero;
    private Tablero tableroAtaque;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.miTablero = new Tablero();
        this.tableroAtaque = new Tablero();
    }

    public void prepararFlota() {
        miTablero.colocarBarcos();
        UI.mostrarFlotaInicial(miTablero);
    }

    public String atacar(Tablero tableroEnemigo, Scanner sc) {
        int[] coordenada = pedirCoordenada(sc);
        int fila = coordenada[0];
        int col  = coordenada[1];

        String resultado = tableroEnemigo.disparo(fila, col);

        // Sincronizar tableroAtaque con el estado real del enemigo
        for (int f = 0; f < Tablero.TAMANIO; f++) {
            for (int c = 0; c < Tablero.TAMANIO; c++) {
                char celdaReal = tableroEnemigo.getCelda(f, c);
                if (celdaReal == Tablero.FALLO || celdaReal == Tablero.TOCADO || celdaReal == Tablero.HUNDIDO) {
                    tableroAtaque.setCelda(f, c, celdaReal);
                }
            }
        }

        return resultado;
    }

    private int[] pedirCoordenada(Scanner sc) {
        while (true) {
            System.out.print("  " + nombre + " -> Introduce coordenada (ej: B4): ");
            String entrada = sc.nextLine().trim().toUpperCase();

            if (entrada.length() < 2) {
                System.out.println("  Formato incorrecto. Ejemplo: A3");
                continue;
            }

            char letra = entrada.charAt(0);
            String numero = entrada.substring(1);

            if (letra < 'A' || letra > 'H') {
                System.out.println("  Fila incorrecta. Usa letras de A hasta H.");
                continue;
            }

            int fila = letra - 'A';
            int col;

            try {
                col = Integer.parseInt(numero) - 1;
            } catch (NumberFormatException e) {
                System.out.println("  Columna incorrecta. Usa numeros del 1 al 8.");
                continue;
            }

            if (col < 0 || col >= Tablero.TAMANIO) {
                System.out.println("  Columna fuera de rango. Usa del 1 al 8.");
                continue;
            }

            char estado = tableroAtaque.getCelda(fila, col);
            if (estado == Tablero.FALLO || estado == Tablero.TOCADO || estado == Tablero.HUNDIDO) {
                System.out.println("  Ya disparaste ahi. Elige otra casilla.");
                continue;
            }

            return new int[]{fila, col};
        }
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
