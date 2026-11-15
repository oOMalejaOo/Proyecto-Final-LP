/**
 * Se lanza cuando se dispara a una casilla que ya había sido atacada.
 */
public class DisparoRepetidoException extends Exception {

    public DisparoRepetidoException(String mensaje) {
        super(mensaje);
    }
}
