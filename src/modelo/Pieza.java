package modelo;

import javax.swing.ImageIcon;

public abstract class Pieza {
    
    protected int fila;
    protected int columna;
    protected boolean esRoja;
    protected ImageIcon imagen;
    protected String nombre;
    
    public Pieza(int fila, int columna, boolean esRoja, String nombre) {
        this.fila = fila;
        this.columna = columna;
        this.esRoja = esRoja;
        this.nombre = nombre;
    }
    
    // Método abstracto que cada pieza debe implementar
    public abstract boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero);
    
    // Métodos final
    public final int getFila() {
        return fila;
    }
    
    public final void setFila(int fila) {
        this.fila = fila;
    }
    
    public final int getColumna() {
        return columna;
    }
    
    public final void setColumna(int columna) {
        this.columna = columna;
    }
    
    public final boolean isEsRoja() {
        return esRoja;
    }
    
    public final ImageIcon getImagen() {
        return imagen;
    }
    
    public final void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
    
    public final String getNombre() {
        return nombre;
    }
    
    @Override
    public final String toString() {
        return (esRoja ? "Roja-" : "Negra-") + nombre;
    }
    
    // Método final para validar que la casilla esté dentro del tablero
    protected final boolean estaDentroTablero(int fila, int col) {
        return fila >= 0 && fila < Constantes.FILAS && 
               col >= 0 && col < Constantes.COLUMNAS;
    }
}