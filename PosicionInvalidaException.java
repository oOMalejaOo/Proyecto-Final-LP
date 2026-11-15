/**
 * Se lanza cuando una coordenada está fuera del tablero
 * o cuando un barco no cabe en la posición elegida.
 */
public class PosicionInvalidaException extends Exception {

    public PosicionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
