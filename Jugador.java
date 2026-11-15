import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase abstracta que representa a quien juega.
 * Cada tipo de jugador decide cómo colocar su flota y cómo elegir su disparo.
 */
public abstract class Jugador {

    protected String nombre;
    protected Tablero tablero;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero(Tablero.TAMANIO);
    }

    public abstract void colocarFlota();

    public abstract int[] elegirDisparo() throws PosicionInvalidaException;

    public abstract boolean esHumano();

    public boolean perdio() {
        return tablero.todosHundidos();
    }

    public String getNombre() {
        return nombre;
    }

    public Tablero getTablero() {
        return tablero;
    }

    /** Crea la flota estándar que usa cada jugador. */
    public List<Barco> crearFlota() {
        List<Barco> flota = new ArrayList<Barco>();
        flota.add(new Portaaviones());
        flota.add(new Acorazado());
        flota.add(new Destructor());
        flota.add(new Destructor());
        return flota;
    }

    /** Coloca todos los barcos en posiciones al azar. */
    protected void colocarAleatorio() {
        Random random = new Random();
        for (Barco barco : crearFlota()) {
            boolean colocado = false;
            while (!colocado) {
                int fila = random.nextInt(Tablero.TAMANIO);
                int col = random.nextInt(Tablero.TAMANIO);
                boolean horizontal = random.nextBoolean();
                try {
                    tablero.colocarBarco(barco, fila, col, horizontal);
                    colocado = true;
                } catch (PosicionInvalidaException e) {
                    // No cabía, se intenta con otra posición
                } catch (BarcoSuperpuestoException e) {
                    // Estaba ocupado, se intenta con otra posición
                }
            }
        }
    }
}
