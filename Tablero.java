import java.util.ArrayList;
import java.util.List;

/**
 * Tablero de juego. Guarda una matriz de caracteres para saber qué hay
 * en cada casilla y una matriz de barcos para saber qué barco es.
 */
public class Tablero {

    public static final int TAMANIO = 10;

    public static final char AGUA = '~';
    public static final char BARCO = 'B';
    public static final char TOCADO = 'X';
    public static final char FALLO = 'O';

    private int tamanio;
    private char[][] matriz;
    private Barco[][] celdas;
    private List<Barco> barcos;

    public Tablero(int tamanio) {
        this.tamanio = tamanio;
        this.matriz = new char[tamanio][tamanio];
        this.celdas = new Barco[tamanio][tamanio];
        this.barcos = new ArrayList<Barco>();
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                matriz[i][j] = AGUA;
            }
        }
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean estaDentro(int fila, int col) {
        return fila >= 0 && fila < tamanio && col >= 0 && col < tamanio;
    }

    /** Devuelve el símbolo de una casilla (~, B, X u O). */
    public char getSimbolo(int fila, int col) {
        return matriz[fila][col];
    }

    /** Devuelve el barco que está en una casilla (o null si es agua). */
    public Barco getBarcoEn(int fila, int col) {
        return celdas[fila][col];
    }

    public void colocarBarco(Barco barco, int fila, int col, boolean horizontal)
            throws PosicionInvalidaException, BarcoSuperpuestoException {

        int filaFinal = horizontal ? fila : fila + barco.getTamanio() - 1;
        int colFinal = horizontal ? col + barco.getTamanio() - 1 : col;

        if (!estaDentro(fila, col) || !estaDentro(filaFinal, colFinal)) {
            throw new PosicionInvalidaException("El " + barco.getNombre() + " no cabe en esa posición.");
        }

        // Primero reviso que todas las casillas estén libres
        for (int i = 0; i < barco.getTamanio(); i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? col + i : col;
            if (celdas[f][c] != null) {
                throw new BarcoSuperpuestoException("Ya hay un barco en esa zona.");
            }
        }

        // Si todo está bien, lo coloco
        for (int i = 0; i < barco.getTamanio(); i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? col + i : col;
            celdas[f][c] = barco;
            matriz[f][c] = BARCO;
        }
        barcos.add(barco);
    }

    /**
     * Procesa un disparo y devuelve "AGUA", "TOCADO" o "HUNDIDO".
     */
    public String recibirDisparo(int fila, int col)
            throws PosicionInvalidaException, DisparoRepetidoException {

        if (!estaDentro(fila, col)) {
            throw new PosicionInvalidaException("Esa coordenada está fuera del tablero.");
        }
        if (matriz[fila][col] == TOCADO || matriz[fila][col] == FALLO) {
            throw new DisparoRepetidoException("Ya habías disparado a esa casilla.");
        }

        Barco barco = celdas[fila][col];
        if (barco == null) {
            matriz[fila][col] = FALLO;
            return "AGUA";
        }

        barco.recibirImpacto();
        matriz[fila][col] = TOCADO;
        if (barco.estaHundido()) {
            return "HUNDIDO";
        }
        return "TOCADO";
    }

    public boolean todosHundidos() {
        if (barcos.isEmpty()) {
            return false;
        }
        for (Barco b : barcos) {
            if (!b.estaHundido()) {
                return false;
            }
        }
        return true;
    }
}
