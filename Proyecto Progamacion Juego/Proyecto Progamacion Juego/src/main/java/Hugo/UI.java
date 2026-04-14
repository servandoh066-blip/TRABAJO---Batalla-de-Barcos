package Hugo;

public class UI {

    // Colores ANSI
    private static final String RESET   = "\u001B[0m";
    private static final String NEGRITA = "\u001B[1m";

    private static final String AZUL    = "\u001B[34m";
    private static final String CYAN    = "\u001B[36m";
    private static final String VERDE   = "\u001B[32m";
    private static final String ROJO    = "\u001B[31m";
    private static final String AMARILLO= "\u001B[33m";
    private static final String BLANCO  = "\u001B[37m";
    private static final String GRIS    = "\u001B[90m";

    private static final String FONDO_AZUL   = "\u001B[44m";
    private static final String FONDO_ROJO   = "\u001B[41m";
    private static final String FONDO_VERDE  = "\u001B[42m";
    private static final String FONDO_NEGRO  = "\u001B[40m";

    // ─────────────────────────────────────────
    //  PANTALLA DE TITULO
    // ─────────────────────────────────────────
    public static void mostrarTitulo() {
        limpiarPantalla();
        System.out.println();
        System.out.println(CYAN + NEGRITA +
            "  ██╗  ██╗██╗   ██╗███╗   ██╗██████╗ ██╗██████╗     " + RESET);
        System.out.println(CYAN + NEGRITA +
            "  ██║  ██║██║   ██║████╗  ██║██╔══██╗██║██╔══██╗    " + RESET);
        System.out.println(CYAN + NEGRITA +
            "  ███████║██║   ██║██╔██╗ ██║██║  ██║██║██████╔╝    " + RESET);
        System.out.println(CYAN + NEGRITA +
            "  ██╔══██║██║   ██║██║╚██╗██║██║  ██║██║██╔══██╗    " + RESET);
        System.out.println(CYAN + NEGRITA +
            "  ██║  ██║╚██████╔╝██║ ╚████║██████╔╝██║██║  ██║    " + RESET);
        System.out.println(CYAN + NEGRITA +
            "  ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝╚═╝  ╚═╝    " + RESET);
        System.out.println();
        System.out.println(AZUL + NEGRITA +
            "        ██╗      █████╗     ███████╗██╗      ██████╗ ████████╗ █████╗ " + RESET);
        System.out.println(AZUL + NEGRITA +
            "        ██║     ██╔══██╗    ██╔════╝██║     ██╔═══██╗╚══██╔══╝██╔══██╗" + RESET);
        System.out.println(AZUL + NEGRITA +
            "        ██║     ███████║    █████╗  ██║     ██║   ██║   ██║   ███████║" + RESET);
        System.out.println(AZUL + NEGRITA +
            "        ██║     ██╔══██║    ██╔══╝  ██║     ██║   ██║   ██║   ██╔══██║" + RESET);
        System.out.println(AZUL + NEGRITA +
            "        ███████╗██║  ██║    ██║     ███████╗╚██████╔╝   ██║   ██║  ██║" + RESET);
        System.out.println(AZUL + NEGRITA +
            "        ╚══════╝╚═╝  ╚═╝    ╚═╝     ╚══════╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝" + RESET);
        System.out.println();
        lineaSeparadora();
        System.out.println(GRIS + "        Cuadricula 8x8   |   Filas A-H   |   Columnas 1-8" + RESET);
        System.out.println(GRIS + "        Flota: 1x Portaaviones  2x Crucero  3x Destructor  4x Submarino" + RESET);
        lineaSeparadora();
        System.out.println();
    }

    // ─────────────────────────────────────────
    //  TABLEROS DOBLES (jugador + CPU lado a lado)
    // ─────────────────────────────────────────
    public static void mostrarTablerosDobles(Tablero miTablero, Tablero tableroAtaque, String nombreJugador) {
        System.out.println();

        String tituloIzq = centrar("⚓  TU FLOTA", 24);
        String tituloDer = centrar("🎯  TUS DISPAROS", 24);
        System.out.println(NEGRITA + VERDE  + "  " + tituloIzq + RESET +
                           "        " +
                           NEGRITA + AMARILLO + tituloDer + RESET);
        System.out.println();

        // Cabecera columnas
        String cabecera = cabeceraTablero();
        System.out.println("  " + GRIS + cabecera + RESET + "        " + GRIS + cabecera + RESET);

        for (int f = 0; f < Tablero.TAMANIO; f++) {
            String filaIzq = filaTableroPropio(miTablero, f);
            String filaDer = filaTableroAtaque(tableroAtaque, f);
            System.out.println("  " + filaIzq + "        " + filaDer);
        }
        System.out.println();
    }

    // ─────────────────────────────────────────
    //  TABLERO SIMPLE (para el turno de la CPU)
    // ─────────────────────────────────────────
    public static void mostrarTableroAtaqueCPU(Tablero tableroAtaque) {
        System.out.println();
        System.out.println(NEGRITA + ROJO + "  [ RADAR DE LA CPU ]" + RESET);
        System.out.println();
        System.out.println("  " + GRIS + cabeceraTablero() + RESET);

        for (int f = 0; f < Tablero.TAMANIO; f++) {
            System.out.println("  " + filaTableroAtaque(tableroAtaque, f));
        }
        System.out.println();
    }

    // ─────────────────────────────────────────
    //  CONSTRUIR FILAS
    // ─────────────────────────────────────────
    private static String cabeceraTablero() {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int c = 1; c <= Tablero.TAMANIO; c++) {
            sb.append(" ").append(c);
        }
        return sb.toString();
    }

    private static String filaTableroPropio(Tablero tablero, int f) {
        StringBuilder sb = new StringBuilder();
        sb.append(NEGRITA).append(BLANCO).append((char) ('A' + f)).append(RESET);
        sb.append(GRIS).append(" │").append(RESET);
        for (int c = 0; c < Tablero.TAMANIO; c++) {
            sb.append(" ").append(colorCelda(tablero.getCelda(f, c), false));
        }
        return sb.toString();
    }

    private static String filaTableroAtaque(Tablero tablero, int f) {
        StringBuilder sb = new StringBuilder();
        sb.append(NEGRITA).append(BLANCO).append((char) ('A' + f)).append(RESET);
        sb.append(GRIS).append(" │").append(RESET);
        for (int c = 0; c < Tablero.TAMANIO; c++) {
            char celda = tablero.getCelda(f, c);
            // En el tablero de ataque los barcos no se muestran
            char mostrar = (celda == Tablero.BARCO) ? Tablero.AGUA : celda;
            sb.append(" ").append(colorCelda(mostrar, true));
        }
        return sb.toString();
    }

    private static String colorCelda(char celda, boolean esAtaque) {
        return switch (celda) {
            case Tablero.AGUA    -> AZUL + "~" + RESET;
            case Tablero.BARCO   -> VERDE + NEGRITA + "■" + RESET;
            case Tablero.FALLO   -> GRIS + "·" + RESET;
            case Tablero.TOCADO  -> AMARILLO + NEGRITA + "✕" + RESET;
            case Tablero.HUNDIDO -> ROJO + NEGRITA + "▓" + RESET;
            default              -> String.valueOf(celda);
        };
    }

    // ─────────────────────────────────────────
    //  MENSAJES DE RESULTADO
    // ─────────────────────────────────────────
    public static void mostrarResultadoJugador(String resultado) {
        System.out.println();
        if (resultado.equals("AGUA")) {
            System.out.println(AZUL + "  💧  Agua... turno de la CPU." + RESET);
        } else if (resultado.equals("TOCADO")) {
            System.out.println(AMARILLO + NEGRITA + "  💥  ¡TOCADO! Vuelves a disparar." + RESET);
        } else {
            String barco = resultado.split(":")[1];
            System.out.println(ROJO + NEGRITA + "  🚢  ¡¡HUNDIDO el " + barco + "!! Vuelves a disparar." + RESET);
        }
        System.out.println();
    }

    public static void mostrarResultadoCPU(String resultado, String nombreBarco) {
        System.out.println();
        if (resultado.equals("AGUA")) {
            System.out.println(VERDE + "  💧  La CPU falla. ¡Agua!" + RESET);
        } else if (resultado.equals("TOCADO")) {
            System.out.println(ROJO + NEGRITA + "  💥  ¡La CPU ha tocado uno de tus barcos!" + RESET);
        } else {
            System.out.println(ROJO + NEGRITA + "  🚢  ¡¡La CPU ha hundido tu " + nombreBarco + "!!" + RESET);
        }
        System.out.println();
    }

    // ─────────────────────────────────────────
    //  CABECERAS DE TURNO
    // ─────────────────────────────────────────
    public static void cabeceraTurnoJugador(String nombre) {
        System.out.println();
        lineaSeparadora();
        System.out.println(VERDE + NEGRITA + "  ▶  TURNO DE " + nombre.toUpperCase() + RESET);
        lineaSeparadora();
    }

    public static void cabeceraTurnoCPU() {
        System.out.println();
        lineaSeparadora();
        System.out.println(ROJO + NEGRITA + "  ▶  TURNO DE LA CPU" + RESET);
        lineaSeparadora();
    }

    // ─────────────────────────────────────────
    //  PANTALLA FINAL
    // ─────────────────────────────────────────
    public static void mostrarFinPartida(boolean ganaJugador, String nombre, Tablero tableroFinalCPU, Tablero miTablero) {
        limpiarPantalla();
        System.out.println();

        if (ganaJugador) {
            System.out.println(VERDE + NEGRITA +
                "  ╔══════════════════════════════════════╗" + RESET);
            System.out.println(VERDE + NEGRITA +
                "  ║   🏆  ¡¡VICTORIA!! 🏆               ║" + RESET);
            System.out.println(VERDE + NEGRITA +
                "  ║   Has hundido toda la flota enemiga  ║" + RESET);
            System.out.println(VERDE + NEGRITA +
                "  ╚══════════════════════════════════════╝" + RESET);
        } else {
            System.out.println(ROJO + NEGRITA +
                "  ╔══════════════════════════════════════╗" + RESET);
            System.out.println(ROJO + NEGRITA +
                "  ║   💀  DERROTA                        ║" + RESET);
            System.out.println(ROJO + NEGRITA +
                "  ║   La CPU ha destruido tu flota       ║" + RESET);
            System.out.println(ROJO + NEGRITA +
                "  ╚══════════════════════════════════════╝" + RESET);
        }

        System.out.println();
        System.out.println(AMARILLO + NEGRITA + "  Flota final de la CPU:" + RESET);
        mostrarTableroFinal(tableroFinalCPU, true);

        System.out.println(AMARILLO + NEGRITA + "  Tu flota:" + RESET);
        mostrarTableroFinal(miTablero, false);
    }

    private static void mostrarTableroFinal(Tablero tablero, boolean esCPU) {
        System.out.println();
        System.out.println("  " + GRIS + cabeceraTablero() + RESET);
        for (int f = 0; f < Tablero.TAMANIO; f++) {
            System.out.println("  " + filaTableroPropio(tablero, f));
        }
        System.out.println();
    }

    // ─────────────────────────────────────────
    //  PREPARACION
    // ─────────────────────────────────────────
    public static void mostrarFlotaInicial(Tablero tablero) {
        System.out.println();
        System.out.println(VERDE + NEGRITA + "  Tu flota:" + RESET);
        System.out.println();
        System.out.println("  " + GRIS + cabeceraTablero() + RESET);
        for (int f = 0; f < Tablero.TAMANIO; f++) {
            System.out.println("  " + filaTableroPropio(tablero, f));
        }
        System.out.println();
    }

    // ─────────────────────────────────────────
    //  UTILIDADES
    // ─────────────────────────────────────────
    public static void lineaSeparadora() {
        System.out.println(GRIS + "  " + "─".repeat(50) + RESET);
    }

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static String centrar(String texto, int ancho) {
        int espacios = Math.max(0, (ancho - texto.length()) / 2);
        return " ".repeat(espacios) + texto;
    }

    public static void pausar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // no hacer nada
        }
    }
}
