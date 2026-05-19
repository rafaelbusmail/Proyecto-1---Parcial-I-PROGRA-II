package modelo;

import javax.swing.ImageIcon;

public abstract class Pieza {
    
    protected int fila;
    protected int columna;
    protected ColorPieza color;
    protected TipoPieza tipo;
    protected ImageIcon imagen;
    
    public Pieza(int fila, int columna, ColorPieza color, TipoPieza tipo) {
        this.fila = fila;
        this.columna = columna;
        this.color = color;
        this.tipo = tipo;
        this.imagen = null;
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
    
    public final ColorPieza getColor() {
        return color;
    }
    
    public final boolean isEsRoja() {
        return color.isRojo();
    }
    
    public final TipoPieza getTipo() {
        return tipo;
    }
    
    public final ImageIcon getImagen() {
        return imagen;
    }
    
    public final void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
    
    public final String getNombre() {
        return tipo.getNombreCompleto();
    }
    
    public final String getNombreCorto() {
        return tipo.getNombreCorto();
    }
    
    @Override
    public final String toString() {
        return color.getNombre() + "-" + tipo.getNombreCompleto();
    }
    
    // Método final para validar que la casilla esté dentro del tablero
    protected final boolean estaDentroTablero(int fila, int col) {
        return fila >= 0 && fila < Constantes.FILAS && 
               col >= 0 && col < Constantes.COLUMNAS;
    }
}