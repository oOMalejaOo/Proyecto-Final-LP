import greenfoot.*;

/**
 * Un texto en pantalla. Se cambia con setTexto().
 */
public class Etiqueta extends Actor {

    private String textoActual = "";
    private int tamanioActual = 0;
    private Color colorActual = null;

    public Etiqueta() {
        setImage(new GreenfootImage(1, 1));
    }

    public Etiqueta(String texto, int tamanio, Color color) {
        setTexto(texto, tamanio, color);
    }

    public void setTexto(String texto, int tamanio, Color color) {
        // Solo se vuelve a dibujar si algo cambió
        if (texto.equals(textoActual) && tamanio == tamanioActual && color == colorActual) {
            return;
        }
        textoActual = texto;
        tamanioActual = tamanio;
        colorActual = color;
        setImage(Estilo.texto(texto, tamanio, color));
    }
}
