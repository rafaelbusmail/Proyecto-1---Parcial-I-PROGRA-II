package modelo;

public class General extends Pieza {
    
    public General(int fila, int columna, ColorPieza color) {
        super(fila, columna, color, TipoPieza.GENERAL);
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
        
        boolean movimientoValido = (deltaFila == 1 && deltaCol == 0) || 
                                   (deltaFila == 0 && deltaCol == 1);
        
        if (!movimientoValido) {
            return false;
        }
        
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.getColor() == this.color) {
            return false;
        }
        
        if (!validarGeneralesEnfrentados(filaDestino, colDestino, tablero)) {
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
    
    private boolean validarGeneralesEnfrentados(int filaDestino, int colDestino, Pieza[][] tablero) {
        General otroGeneral = null;
        for (int f = 0; f < Constantes.FILAS; f++) {
            for (int c = 0; c < Constantes.COLUMNAS; c++) {
                if (tablero[f][c] instanceof General && 
                    tablero[f][c].getColor() != this.color) {
                    otroGeneral = (General) tablero[f][c];
                    break;
                }
            }
            if (otroGeneral != null) break;
        }
        
        if (otroGeneral == null) return true;
        
        if (colDestino == otroGeneral.getColumna()) {
            int filaMin = Math.min(filaDestino, otroGeneral.getFila());
            int filaMax = Math.max(filaDestino, otroGeneral.getFila());
            
            for (int f = filaMin + 1; f < filaMax; f++) {
                if (tablero[f][colDestino] != null) {
                    return true;
                }
            }
            return false;
        }
        
        return true;
    }
}