package modelo;

public class Oficial extends Pieza {
    
    public Oficial(int fila, int columna, boolean esRoja) {
        super(fila, columna, esRoja, "Oficial");
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        // Validar que esté dentro del tablero
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        // Validar que esté dentro del palacio
        if (!estaDentroPalacio(filaDestino, colDestino)) {
            return false;
        }
        
        // Solo puede moverse 1 casilla en diagonal
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        boolean movimientoDiagonal = deltaFila == 1 && deltaCol == 1;
        
        if (!movimientoDiagonal) {
            return false;
        }
        
        // No puede capturar sus propias piezas
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.isEsRoja() == this.esRoja) {
            return false;
        }
        
        return true;
    }
    
    private boolean estaDentroPalacio(int fila, int col) {
        // Palacio rojo (filas 7-9, columnas 3-5)
        if (this.esRoja) {
            return fila >= Constantes.PALACIO_FILA_MIN_ROJO && 
                   fila <= Constantes.PALACIO_FILA_MAX_ROJO && 
                   col >= Constantes.PALACIO_COL_MIN && 
                   col <= Constantes.PALACIO_COL_MAX;
        }
        // Palacio negro (filas 0-2, columnas 3-5)
        else {
            return fila >= Constantes.PALACIO_FILA_MIN_NEGRO && 
                   fila <= Constantes.PALACIO_FILA_MAX_NEGRO && 
                   col >= Constantes.PALACIO_COL_MIN && 
                   col <= Constantes.PALACIO_COL_MAX;
        }
    }
}