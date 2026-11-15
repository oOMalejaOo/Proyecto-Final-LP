import java.util.Random;

/**
 * Jugador controlado por la computadora: coloca y dispara al azar,
 * pero recuerda dónde ya disparó para no repetir.
 */
public class JugadorComputador extends Jugador {

    private Random random;
    private boolean[][] disparados;

    public JugadorComputador() {
        super("Computador");
        this.random = new Random();
        this.disparados = new boolean[Tablero.TAMANIO][Tablero.TAMANIO];
    }

    @Override
    public boolean esHumano() {
        return false;
    }

    @Override
    public void colocarFlota() {
        colocarAleatorio();
    }

    @Override
    public int[] elegirDisparo() throws PosicionInvalidaException {
        int fila;
        int col;
        do {
            fila = random.nextInt(Tablero.TAMANIO);
            col = random.nextInt(Tablero.TAMANIO);
        } while (disparados[fila][col]);

        disparados[fila][col] = true;
        return new int[]{fila, col};
    }
}
