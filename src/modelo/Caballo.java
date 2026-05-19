package modelo;

public class Caballo extends Pieza {
    
    public Caballo(int fila, int columna, ColorPieza color) {
        super(fila, columna, color, TipoPieza.CABALLO);
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        boolean esMovimientoL = (deltaFila == 2 && deltaCol == 1) || 
                                (deltaFila == 1 && deltaCol == 2);
        
        if (!esMovimientoL) {
            return false;
        }
        
        if (estaBloqueado(filaDestino, colDestino, tablero)) {
            return false;
        }
        
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.getColor() == this.color) {
            return false;
        }
        
        return true;
    }
    
    private boolean estaBloqueado(int filaDestino, int colDestino, Pieza[][] tablero) {
        int deltaFila = filaDestino - this.fila;
        int deltaCol = colDestino - this.columna;
        
        int filaBloqueo, colBloqueo;
        
        if (Math.abs(deltaFila) == 2) {
            filaBloqueo = this.fila + (deltaFila > 0 ? 1 : -1);
            colBloqueo = this.columna;
        } else {
            filaBloqueo = this.fila;
            colBloqueo = this.columna + (deltaCol > 0 ? 1 : -1);
        }
        
        return tablero[filaBloqueo][colBloqueo] != null;
    }
}