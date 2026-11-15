import greenfoot.*;

/**
 * Pantalla que tapa los tableros por completo. Se usa en el modo de dos
 * jugadores para que nadie vea los barcos del otro cuando se pasan el equipo.
 */
public class Pantalla extends Actor {

    public Pantalla(int ancho, int alto, String titulo, String linea1, String linea2) {
        GreenfootImage imagen = new GreenfootImage(ancho, alto);
        Estilo.fondoMar(imagen);

        // Capa oscura para que el texto se lea bien
        imagen.setColor(new Color(5, 15, 35, 170));
        imagen.fillRect(0, 0, ancho, alto);

        Estilo.panel(imagen, ancho / 2 - 340, alto / 2 - 140, 680, 280);
        Estilo.escribirCentrado(imagen, titulo, 40, Estilo.DORADO, ancho / 2, alto / 2 - 80);
        Estilo.escribirCentrado(imagen, linea1, 22, Estilo.BLANCO, ancho / 2, alto / 2 - 20);
        Estilo.escribirCentrado(imagen, linea2, 18, Estilo.AZUL_CLARO, ancho / 2, alto / 2 + 15);
        Estilo.escribirCentrado(imagen, "Haz clic (o presiona Enter) para continuar", 18,
                Estilo.VERDE, ancho / 2, alto / 2 + 90);
        setImage(imagen);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            MundoBatalla mundo = (MundoBatalla) getWorld();
            mundo.continuar();
        }
    }
}
