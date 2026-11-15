import greenfoot.*;

/**
 * Representa una casilla dibujada en pantalla. Cambia de imagen según
 * lo que haya en el tablero (agua, barco, tocado o fallo) y avisa al
 * mundo cuando se le hace clic.
 */
public class CasillaVisual extends Actor {

    public static final int TAM = 36;

    private int fila;
    private int col;
    private boolean esRival;

    public CasillaVisual(int fila, int col, boolean esRival) {
        this.fila = fila;
        this.col = col;
        this.esRival = esRival;
        actualizar(Tablero.AGUA);
    }

    /** Cambia la imagen según el símbolo del tablero. */
    public void actualizar(char simbolo) {
        GreenfootImage imagen = new GreenfootImage(TAM, TAM);

        if (simbolo == Tablero.BARCO) {
            imagen.setColor(new Color(110, 115, 125));
            imagen.fillRect(0, 0, TAM, TAM);
        } else if (simbolo == Tablero.TOCADO) {
            imagen.setColor(new Color(200, 50, 40));
            imagen.fillRect(0, 0, TAM, TAM);
            imagen.setColor(Color.WHITE);
            imagen.drawLine(8, 8, TAM - 9, TAM - 9);
            imagen.drawLine(TAM - 9, 8, 8, TAM - 9);
        } else if (simbolo == Tablero.FALLO) {
            imagen.setColor(new Color(90, 150, 215));
            imagen.fillRect(0, 0, TAM, TAM);
            imagen.setColor(Color.WHITE);
            imagen.fillOval(13, 13, 10, 10);
        } else {
            imagen.setColor(new Color(60, 120, 190));
            imagen.fillRect(0, 0, TAM, TAM);
        }

        // Borde de la casilla
        imagen.setColor(new Color(20, 50, 90));
        imagen.drawRect(0, 0, TAM - 1, TAM - 1);
        setImage(imagen);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            MundoBatalla mundo = (MundoBatalla) getWorld();
            mundo.clicCasilla(fila, col, esRival);
        }
    }
}
