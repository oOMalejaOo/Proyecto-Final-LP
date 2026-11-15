/**
 * Interfaz que implementan los mundos que tienen botones.
 * Así el botón puede avisarle al mundo que lo pulsaron,
 * sin importar si es el menú o el juego.
 */
public interface ReceptorBoton {

    void botonPulsado(int accion);
}
