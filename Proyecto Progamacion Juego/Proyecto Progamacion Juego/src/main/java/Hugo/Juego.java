package Hugo;

import java.util.Scanner;

public class Juego {

    private Jugador jugador;
    private JugadorIA cpu;
    private Scanner sc;

    public Juego() {
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        UI.mostrarTitulo();

        System.out.print("  Introduce tu nombre: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) nombre = "Jugador";

        jugador = new Jugador(nombre);
        cpu = new JugadorIA("CPU");

        System.out.println();
        System.out.println("  Colocando flotas...");
        UI.pausar(500);
        jugador.prepararFlota();
        cpu.prepararFlota();
        System.out.println("  Flota de la CPU colocada.");
        UI.pausar(800);

        jugar();
    }

    private void jugar() {
        boolean turnoJugador = true;

        while (true) {

            if (turnoJugador) {
                UI.cabeceraTurnoJugador(jugador.getNombre());
                UI.mostrarTablerosDobles(jugador.getMiTablero(), jugador.getTableroAtaque(), jugador.getNombre());

                String resultado = jugador.atacar(cpu.getMiTablero(), sc);

                if (resultado.equals("REPETIDO")) continue;

                UI.mostrarResultadoJugador(resultado);
                UI.pausar(600);

                if (cpu.getMiTablero().todosHundidos()) {
                    terminarPartida(true);
                    return;
                }

                if (resultado.equals("AGUA")) {
                    turnoJugador = false;
                }

            } else {
                UI.cabeceraTurnoCPU();
                UI.pausar(900);

                String resultado = cpu.atacar(jugador.getMiTablero());

                String nombreBarco = resultado.startsWith("HUNDIDO") ? resultado.split(":")[1] : "";
                UI.mostrarResultadoCPU(resultado, nombreBarco);

                UI.mostrarTableroAtaqueCPU(cpu.getTableroAtaque());
                UI.pausar(700);

                if (jugador.getMiTablero().todosHundidos()) {
                    terminarPartida(false);
                    return;
                }

                if (resultado.equals("AGUA")) {
                    turnoJugador = true;
                }
            }
        }
    }

    private void terminarPartida(boolean ganaJugador) {
        UI.pausar(500);
        UI.mostrarFinPartida(ganaJugador, jugador.getNombre(), cpu.getMiTablero(), jugador.getMiTablero());

        System.out.print("  ¿Jugar otra partida? (s/n): ");
        String respuesta = sc.nextLine().trim().toLowerCase();
        if (respuesta.equals("s")) {
            iniciar();
        } else {
            System.out.println();
            System.out.println("  Hasta la proxima!");
            System.out.println();
        }
    }
}
