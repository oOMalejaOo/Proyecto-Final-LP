/**
 * Controla la lógica de la partida: disparos, turnos y ganador.
 * No dibuja nada; de eso se encarga MundoBatalla.
 */
public class Juego {

    private Jugador jugador1;
    private Jugador jugador2;

    public Juego(String nombreJugador) {
        jugador1 = new JugadorHumano(nombreJugador);
        jugador2 = new JugadorComputador();
        jugador2.colocarFlota();
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    /** El jugador humano dispara a la casilla donde hizo clic. */
    public String disparoHumano(int fila, int col)
            throws PosicionInvalidaException, DisparoRepetidoException {
        ((JugadorHumano) jugador1).setDisparo(fila, col);
        return ejecutarDisparo(jugador1, jugador2);
    }

    /** La computadora elige y ejecuta su disparo. */
    public String turnoComputador() {
        try {
            return ejecutarDisparo(jugador2, jugador1);
        } catch (PosicionInvalidaException e) {
            return "Error: " + e.getMessage();
        } catch (DisparoRepetidoException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Misma lógica para los dos jugadores (polimorfismo con elegirDisparo)
    private String ejecutarDisparo(Jugador atacante, Jugador rival)
            throws PosicionInvalidaException, DisparoRepetidoException {

        int[] disparo = atacante.elegirDisparo();
        String resultado = rival.getTablero().recibirDisparo(disparo[0], disparo[1]);

        String coordenada = "" + (char) ('A' + disparo[1]) + (disparo[0] + 1);
        String mensaje = atacante.getNombre() + " disparó a " + coordenada + ": " + resultado;

        if (resultado.equals("HUNDIDO")) {
            Barco barco = rival.getTablero().getBarcoEn(disparo[0], disparo[1]);
            mensaje = mensaje + " (se hundió el " + barco.getNombre() + ")";
        }
        return mensaje;
    }

    public boolean hayGanador() {
        return jugador1.perdio() || jugador2.perdio();
    }

    public Jugador getGanador() {
        if (jugador2.perdio()) {
            return jugador1;
        }
        if (jugador1.perdio()) {
            return jugador2;
        }
        return null;
    }
}
