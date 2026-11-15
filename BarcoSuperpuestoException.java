/**
 * Se lanza cuando se intenta poner un barco encima de otro.
 */
public class BarcoSuperpuestoException extends Exception {

    public BarcoSuperpuestoException(String mensaje) {
        super(mensaje);
    }
}
