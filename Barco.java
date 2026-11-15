/**
 * Clase abstracta que representa un barco cualquiera.
 * Cada tipo de barco (hija) define su nombre y su tamaño.
 */
public abstract class Barco {

    protected String nombre;
    protected int tamanio;
    protected int impactos;

    public Barco(String nombre, int tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.impactos = 0;
    }

    public void recibirImpacto() {
        if (impactos < tamanio) {
            impactos++;
        }
    }

    public boolean estaHundido() {
        return impactos >= tamanio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTamanio() {
        return tamanio;
    }
}
