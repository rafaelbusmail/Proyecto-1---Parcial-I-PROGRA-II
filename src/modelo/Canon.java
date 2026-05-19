package modelo;

public class Canon extends Pieza {
    
    public Canon(int fila, int columna, ColorPieza color) {
        super(fila, columna, color, TipoPieza.CANON);
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
        
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        
        int piezasEnMedio = contarPiezasEnMedio(filaDestino, colDestino, tablero);
        
        if (piezaDestino == null) {
            return piezasEnMedio == 0;
        }
        
        if (piezaDestino.getColor() == this.color) {
            return false;
        }
        
        return piezasEnMedio == 1;
    }
    
    private int contarPiezasEnMedio(int filaDestino, int colDestino, Pieza[][] tablero) {
        int contador = 0;
        
        int dirFila = Integer.compare(filaDestino, this.fila);
        int dirCol = Integer.compare(colDestino, this.columna);
        
        int filaActual = this.fila + dirFila;
        int colActual = this.columna + dirCol;
        
        while (filaActual != filaDestino || colActual != colDestino) {
            if (tablero[filaActual][colActual] != null) {
                contador++;
            }
            filaActual += dirFila;
            colActual += dirCol;
        }
        
        return contador;
    }
}