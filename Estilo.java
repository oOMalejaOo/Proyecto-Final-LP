import greenfoot.*;

/**
 * Colores y métodos de dibujo que se usan en todo el juego.
 * Así el aspecto visual queda en un solo lugar.
 */
public class Estilo {

    public static final Color BLANCO = new Color(255, 255, 255);
    public static final Color AZUL_CLARO = new Color(160, 205, 255);
    public static final Color DORADO = new Color(255, 210, 100);
    public static final Color VERDE = new Color(150, 240, 170);
    public static final Color NARANJA = new Color(255, 175, 110);
    public static final Color ROJO_SUAVE = new Color(255, 125, 115);

    private static final Color PANEL = new Color(8, 28, 58);
    private static final Color BORDE = new Color(90, 150, 210);
    private static final Color TRANSPARENTE = new Color(0, 0, 0, 0);

    /** Crea una imagen con un texto (fondo transparente). */
    public static GreenfootImage texto(String texto, int tamanio, Color color) {
        if (texto == null || texto.length() == 0) {
            return new GreenfootImage(1, 1);
        }
        return new GreenfootImage(texto, tamanio, color, TRANSPARENTE);
    }

    /** Escribe un texto centrado en el punto (xc, yc) de una imagen. */
    public static void escribirCentrado(GreenfootImage destino, String texto, int tamanio,
                                        Color color, int xc, int yc) {
        GreenfootImage imagen = texto(texto, tamanio, color);
        destino.drawImage(imagen, xc - imagen.getWidth() / 2, yc - imagen.getHeight() / 2);
    }

    /** Rectángulo con las esquinas redondeadas. */
    public static void rectRedondeado(GreenfootImage img, int x, int y, int ancho, int alto,
                                      int radio, Color color) {
        img.setColor(color);
        img.fillRect(x + radio, y, ancho - 2 * radio, alto);
        img.fillRect(x, y + radio, ancho, alto - 2 * radio);
        img.fillOval(x, y, 2 * radio, 2 * radio);
        img.fillOval(x + ancho - 2 * radio, y, 2 * radio, 2 * radio);
        img.fillOval(x, y + alto - 2 * radio, 2 * radio, 2 * radio);
        img.fillOval(x + ancho - 2 * radio, y + alto - 2 * radio, 2 * radio, 2 * radio);
    }

    /** Panel oscuro con borde claro. */
    public static void panel(GreenfootImage img, int x, int y, int ancho, int alto) {
        rectRedondeado(img, x, y, ancho, alto, 12, BORDE);
        rectRedondeado(img, x + 2, y + 2, ancho - 4, alto - 4, 11, PANEL);
    }

    /** Fondo de mar: degradado azul con olas suaves. */
    public static void fondoMar(GreenfootImage img) {
        int ancho = img.getWidth();
        int alto = img.getHeight();

        for (int y = 0; y < alto; y += 2) {
            double t = (double) y / alto;
            int r = (int) (8 + 14 * t);
            int g = (int) (24 + 70 * t);
            int b = (int) (52 + 80 * t);
            img.setColor(new Color(r, g, b));
            img.fillRect(0, y, ancho, 2);
        }

        img.setColor(new Color(255, 255, 255, 16));
        for (int y = 40; y < alto; y += 34) {
            for (int x = 0; x < ancho; x += 4) {
                int y1 = y + (int) (4 * Math.sin(x / 22.0 + y));
                int y2 = y + (int) (4 * Math.sin((x + 4) / 22.0 + y));
                img.drawLine(x, y1, x + 4, y2);
            }
        }
    }

    /** Silueta sencilla de un barco (para decorar el menú). */
    public static void dibujarBarco(GreenfootImage img, int x, int y, double escala, Color color) {
        img.setColor(color);
        int[] xs = {x, (int) (x + 200 * escala), (int) (x + 170 * escala), (int) (x + 25 * escala)};
        int[] ys = {(int) (y - 30 * escala), (int) (y - 30 * escala), y, y};
        img.fillPolygon(xs, ys, 4);
        img.fillRect((int) (x + 50 * escala), (int) (y - 52 * escala),
                (int) (70 * escala), (int) (22 * escala));
        img.fillRect((int) (x + 70 * escala), (int) (y - 72 * escala),
                (int) (30 * escala), (int) (20 * escala));
        img.fillRect((int) (x + 130 * escala), (int) (y - 45 * escala),
                (int) (40 * escala), (int) (15 * escala));
        img.fillRect((int) (x + 85 * escala), (int) (y - 100 * escala),
                (int) (3 * escala) + 2, (int) (30 * escala));
    }
}
