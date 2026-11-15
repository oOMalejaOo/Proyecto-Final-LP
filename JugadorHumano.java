/**
 * Jugador controlado por una persona. En Greenfoot el disparo no se lee
 * por teclado: el mundo guarda la casilla donde se hizo clic y
 * elegirDisparo() la entrega.
 */
public class JugadorHumano extends Jugador {

    private int[] disparoPendiente;

    public JugadorHumano(String nombre) {
        super(nombre);
    }

    @Override
    public boolean esHumano() {
        return true;
    }

    /** Coloca la flota automáticamente (cuando se presiona la tecla A). */
    @Override
    public void colocarFlota() {
        colocarAleatorio();
    }

    /** Guarda la casilla donde la persona hizo clic. */
    public void setDisparo(int fila, int col) {
        disparoPendiente = new int[]{fila, col};
    }

    @Override
    public int[] elegirDisparo() throws PosicionInvalidaException {
        if (disparoPendiente == null) {
            throw new PosicionInvalidaException("Elige una casilla del tablero rival.");
        }
        int[] disparo = disparoPendiente;
        disparoPendiente = null;
        return disparo;
    }
}
