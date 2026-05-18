package modelo;

public class Canon extends Pieza {
    
    public Canon(int fila, int columna, boolean esRoja) {
        super(fila, columna, esRoja, "Canon");
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        // Validar que esté dentro del tablero
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        // Movimiento: horizontal o vertical
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        boolean movimientoRecto = (deltaFila == 0 && deltaCol > 0) || 
                                  (deltaFila > 0 && deltaCol == 0);
        
        if (!movimientoRecto) {
            return false;
        }
        
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        
        // Contar piezas intermedias
        int piezasEnMedio = contarPiezasEnMedio(filaDestino, colDestino, tablero);
        
        // Sin captura: camino debe estar libre (0 piezas en medio)
        if (piezaDestino == null) {
            return piezasEnMedio == 0;
        }
        
        // Con captura: debe haber exactamente 1 pieza intermedia (pantalla)
        if (piezaDestino.isEsRoja() == this.esRoja) {
            return false; // No puede capturar sus propias piezas
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