import greenfoot.*;

/**
 * Botón con texto. Cambia de color cuando el mouse pasa por encima
 * y le avisa a su mundo cuando se le hace clic.
 */
public class Boton extends Actor {

    private String texto;
    private int ancho;
    private int alto;
    private int accion;
    private boolean encima;

    public Boton(String texto, int ancho, int alto, int accion) {
        this.texto = texto;
        this.ancho = ancho;
        this.alto = alto;
        this.accion = accion;
        this.encima = false;
        dibujar();
    }

    public void setTexto(String texto) {
        this.texto = texto;
        dibujar();
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public int getAccion() {
        return accion;
    }

    private void dibujar() {
        GreenfootImage imagen = new GreenfootImage(ancho, alto);
        Color borde = encima ? Estilo.DORADO : new Color(90, 150, 210);
        Color relleno = encima ? new Color(40, 105, 175) : new Color(20, 65, 125);

        Estilo.rectRedondeado(imagen, 0, 0, ancho, alto, 14, borde);
        Estilo.rectRedondeado(imagen, 2, 2, ancho - 4, alto - 4, 12, relleno);
        Estilo.escribirCentrado(imagen, texto, 20, Estilo.BLANCO, ancho / 2, alto / 2);
        setImage(imagen);
    }

    public void act() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            boolean ahoraEncima = (mouse.getActor() == this);
            if (ahoraEncima != encima) {
                encima = ahoraEncima;
                dibujar();
            }
        }

        if (Greenfoot.mouseClicked(this)) {
            ReceptorBoton mundo = (ReceptorBoton) getWorld();
            mundo.botonPulsado(accion);
        }
    }
}
