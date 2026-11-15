import greenfoot.*;
import java.util.List;

/**
 * Mundo de Greenfoot: dibuja los dos tableros y maneja los clics y teclas.
 *
 * Controles:
 *   - Clic en TU tablero (izquierda): coloca el barco actual.
 *   - R: gira el barco (horizontal / vertical).
 *   - A: coloca toda la flota automáticamente.
 *   - Clic en el tablero RIVAL (derecha): disparar.
 *   - Enter: reiniciar cuando termina la partida.
 */
public class MundoBatalla extends World {

    private static final int N = Tablero.TAMANIO;
    private static final int X_PROPIO = 40;
    private static final int X_RIVAL = 460;
    private static final int Y_TABLERO = 130;

    private Juego juego;
    private CasillaVisual[][] casillasPropias;
    private CasillaVisual[][] casillasRival;

    private List<Barco> barcosPorColocar;
    private int indiceBarco;
    private boolean horizontal;

    private boolean colocando;
    private boolean terminado;
    private boolean esperandoComputador;
    private int espera;

    private String textoHumano;
    private String textoComputador;

    public MundoBatalla() {
        super(860, 520, 1);

        juego = new Juego("Jugador");
        barcosPorColocar = juego.getJugador1().crearFlota();
        indiceBarco = 0;
        horizontal = true;
        colocando = true;
        terminado = false;
        esperandoComputador = false;
        textoHumano = "";
        textoComputador = "";

        dibujarFondo();
        crearCasillas();
        refrescar();
        mostrarMensajes();
    }

    // ---------- Dibujo ----------

    private void dibujarFondo() {
        GreenfootImage fondo = getBackground();
        fondo.setColor(new Color(15, 35, 70));
        fondo.fillRect(0, 0, getWidth(), getHeight());

        fondo.setColor(Color.WHITE);
        for (int i = 0; i < N; i++) {
            String letra = "" + (char) ('A' + i);
            String numero = "" + (i + 1);
            int xc = i * CasillaVisual.TAM + CasillaVisual.TAM / 2;
            int yc = i * CasillaVisual.TAM + CasillaVisual.TAM / 2;

            fondo.drawString(letra, X_PROPIO + xc - 3, Y_TABLERO - 8);
            fondo.drawString(letra, X_RIVAL + xc - 3, Y_TABLERO - 8);
            fondo.drawString(numero, X_PROPIO - 22, Y_TABLERO + yc + 4);
            fondo.drawString(numero, X_RIVAL - 22, Y_TABLERO + yc + 4);
        }

        showText("TU FLOTA", X_PROPIO + 180, 110);
        showText("TABLERO RIVAL", X_RIVAL + 180, 110);
    }

    private void crearCasillas() {
        casillasPropias = new CasillaVisual[N][N];
        casillasRival = new CasillaVisual[N][N];
        int mitad = CasillaVisual.TAM / 2;

        for (int f = 0; f < N; f++) {
            for (int c = 0; c < N; c++) {
                casillasPropias[f][c] = new CasillaVisual(f, c, false);
                casillasRival[f][c] = new CasillaVisual(f, c, true);
                addObject(casillasPropias[f][c],
                        X_PROPIO + c * CasillaVisual.TAM + mitad,
                        Y_TABLERO + f * CasillaVisual.TAM + mitad);
                addObject(casillasRival[f][c],
                        X_RIVAL + c * CasillaVisual.TAM + mitad,
                        Y_TABLERO + f * CasillaVisual.TAM + mitad);
            }
        }
    }

    /** Actualiza la imagen de todas las casillas según los tableros. */
    private void refrescar() {
        Tablero propio = juego.getJugador1().getTablero();
        Tablero rival = juego.getJugador2().getTablero();

        for (int f = 0; f < N; f++) {
            for (int c = 0; c < N; c++) {
                casillasPropias[f][c].actualizar(propio.getSimbolo(f, c));

                char simbolo = rival.getSimbolo(f, c);
                if (simbolo == Tablero.BARCO) {
                    simbolo = Tablero.AGUA; // los barcos del rival no se ven
                }
                casillasRival[f][c].actualizar(simbolo);
            }
        }
    }

    private void mostrarMensajes() {
        String instruccion;
        if (terminado) {
            Jugador ganador = juego.getGanador();
            if (ganador.esHumano()) {
                instruccion = "¡GANASTE! Todos los barcos enemigos se hundieron. Enter para jugar de nuevo.";
            } else {
                instruccion = "Perdiste: la computadora hundió tu flota. Enter para jugar de nuevo.";
            }
        } else if (colocando) {
            Barco barco = barcosPorColocar.get(indiceBarco);
            String orientacion = horizontal ? "HORIZONTAL" : "VERTICAL";
            instruccion = "Coloca el " + barco.getNombre() + " (tamaño " + barco.getTamanio()
                    + ") - " + orientacion + " | R = girar | A = automático";
        } else if (esperandoComputador) {
            instruccion = "Turno de la computadora...";
        } else {
            instruccion = "Tu turno: haz clic en una casilla del tablero rival";
        }

        showText(instruccion, getWidth() / 2, 25);
        showText(textoHumano, getWidth() / 2, 55);
        showText(textoComputador, getWidth() / 2, 78);
    }

    // ---------- Eventos ----------

    /** Lo llama CasillaVisual cuando se le hace clic. */
    public void clicCasilla(int fila, int col, boolean esRival) {
        if (terminado) {
            return;
        }

        if (colocando) {
            if (esRival) {
                textoHumano = "Primero coloca tus barcos en TU tablero (izquierda).";
            } else {
                colocarBarcoHumano(fila, col);
            }
        } else {
            if (!esRival) {
                textoHumano = "Dispara en el tablero rival (derecha).";
            } else if (!esperandoComputador) {
                dispararHumano(fila, col);
            }
        }
        mostrarMensajes();
    }

    private void colocarBarcoHumano(int fila, int col) {
        Barco barco = barcosPorColocar.get(indiceBarco);
        try {
            juego.getJugador1().getTablero().colocarBarco(barco, fila, col, horizontal);
            indiceBarco++;
            textoHumano = "";
            if (indiceBarco >= barcosPorColocar.size()) {
                colocando = false;
                textoHumano = "¡Flota lista! Empieza la batalla.";
            }
            refrescar();
        } catch (PosicionInvalidaException e) {
            textoHumano = "Error: " + e.getMessage();
        } catch (BarcoSuperpuestoException e) {
            textoHumano = "Error: " + e.getMessage();
        }
    }

    private void dispararHumano(int fila, int col) {
        try {
            textoHumano = juego.disparoHumano(fila, col);
            textoComputador = "";
            refrescar();
            if (juego.hayGanador()) {
                terminado = true;
            } else {
                esperandoComputador = true;
                espera = 35;
            }
        } catch (PosicionInvalidaException e) {
            textoHumano = "Error: " + e.getMessage();
        } catch (DisparoRepetidoException e) {
            textoHumano = "Error: " + e.getMessage();
        }
    }

    public void act() {
        String tecla = Greenfoot.getKey();

        if (tecla != null) {
            if (terminado && tecla.equals("enter")) {
                Greenfoot.setWorld(new MundoBatalla());
                return;
            }
            if (colocando) {
                if (tecla.equals("r")) {
                    horizontal = !horizontal;
                    mostrarMensajes();
                } else if (tecla.equals("a")) {
                    colocarAutomatico();
                }
            }
        }

        if (esperandoComputador) {
            espera--;
            if (espera <= 0) {
                textoComputador = juego.turnoComputador();
                refrescar();
                esperandoComputador = false;
                if (juego.hayGanador()) {
                    terminado = true;
                }
                mostrarMensajes();
            }
        }
    }

    private void colocarAutomatico() {
        if (indiceBarco > 0) {
            textoHumano = "La opción A solo sirve antes de colocar el primer barco.";
        } else {
            juego.getJugador1().colocarFlota();
            indiceBarco = barcosPorColocar.size();
            colocando = false;
            textoHumano = "¡Flota colocada! Empieza la batalla.";
            refrescar();
        }
        mostrarMensajes();
    }
}
