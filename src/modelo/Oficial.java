package modelo;

public class Oficial extends Pieza {
    
    public Oficial(int fila, int columna, ColorPieza color) {
        super(fila, columna, color, TipoPieza.OFICIAL);
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        if (!estaDentroPalacio(filaDestino, colDestino)) {
            return false;
        }
        
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        boolean movimientoDiagonal = deltaFila == 1 && deltaCol == 1;
        
        if (!movimientoDiagonal) {
            return false;
        }
        
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.getColor() == this.color) {
            return false;
        }
        
        return true;
    }
    
    private boolean estaDentroPalacio(int fila, int col) {
        if (this.color == ColorPieza.ROJO) {
            return fila >= Constantes.PALACIO_FILA_MIN_ROJO && 
                   fila <= Constantes.PALACIO_FILA_MAX_ROJO && 
                   col >= Constantes.PALACIO_COL_MIN && 
                   col <= Constantes.PALACIO_COL_MAX;
        } else {
            return fila >= Constantes.PALACIO_FILA_MIN_NEGRO && 
                   fila <= Constantes.PALACIO_FILA_MAX_NEGRO && 
                   col >= Constantes.PALACIO_COL_MIN && 
                   col <= Constantes.PALACIO_COL_MAX;
        }
    }
}