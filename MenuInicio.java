import greenfoot.*;

/**
 * Menú inicial: permite elegir si se juega contra la computadora
 * o entre dos personas.
 */
public class MenuInicio extends World implements ReceptorBoton {

    public static final int JUGAR_CONTRA_COMPUTADOR = 1;
    public static final int JUGAR_DOS_JUGADORES = 2;

    public MenuInicio() {
        super(860, 590, 1);

        GreenfootImage fondo = getBackground();
        Estilo.fondoMar(fondo);

        // Barcos decorativos al fondo
        Color silueta = new Color(8, 24, 50);
        Estilo.dibujarBarco(fondo, 30, 575, 1.0, silueta);
        Estilo.dibujarBarco(fondo, 560, 585, 1.3, silueta);
        Estilo.dibujarBarco(fondo, 360, 560, 0.6, silueta);

        addObject(new Etiqueta("BATALLA NAVAL", 72, Estilo.DORADO), 430, 110);
        addObject(new Etiqueta("Hunde la flota enemiga antes de que hunda la tuya", 22,
                Estilo.AZUL_CLARO), 430, 175);

        addObject(new Etiqueta("¿Cómo quieres jugar?", 26, Estilo.BLANCO), 430, 260);
        addObject(new Boton("Jugar contra la computadora", 380, 60, JUGAR_CONTRA_COMPUTADOR), 430, 330);
        addObject(new Boton("Jugar de a dos (2 jugadores)", 380, 60, JUGAR_DOS_JUGADORES), 430, 410);

        addObject(new Etiqueta("En el modo de 2 jugadores la pantalla se tapa entre turnos,", 16,
                Estilo.AZUL_CLARO), 430, 475);
        addObject(new Etiqueta("así nadie ve los barcos del otro.", 16, Estilo.AZUL_CLARO), 430, 497);
    }

    public void botonPulsado(int accion) {
        boolean dosJugadores = (accion == JUGAR_DOS_JUGADORES);
        Greenfoot.setWorld(new MundoBatalla(dosJugadores));
    }
}
