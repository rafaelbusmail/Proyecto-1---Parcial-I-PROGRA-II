package modelo;

public class Carro extends Pieza {
    
    public Carro(int fila, int columna, ColorPieza color) {
        super(fila, columna, color, TipoPieza.CARRO);
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        boolean movimientoRecto = (deltaFila == 0 && deltaCol > 0) || 
                                  (deltaFila > 0 && deltaCol == 0);
        
        if (!movimientoRecto) {
            return false;
        }
        
        if (!esCaminoLibre(this.fila, this.columna, filaDestino, colDestino, tablero)) {
            return false;
        }
        
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.getColor() == this.color) {
            return false;
        }
        
        return true;
    }
    
    private boolean esCaminoLibre(int filaActual, int colActual, int filaDestino, int colDestino, Pieza[][] tablero) {
        if (filaActual == filaDestino && colActual == colDestino) {
            return true;
        }
        
        int dirFila = Integer.compare(filaDestino, filaActual);
        int dirCol = Integer.compare(colDestino, colActual);
        
        int siguienteFila = filaActual + dirFila;
        int siguienteCol = colActual + dirCol;
        
        if (!(siguienteFila == filaDestino && siguienteCol == colDestino)) {
            if (tablero[siguienteFila][siguienteCol] != null) {
                return false;
            }
        }
        
        return esCaminoLibre(siguienteFila, siguienteCol, filaDestino, colDestino, tablero);
    }
}