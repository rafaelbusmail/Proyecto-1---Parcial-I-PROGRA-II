package modelo;

public class Elefante extends Pieza {
    
    public Elefante(int fila, int columna, ColorPieza color) {
        super(fila, columna, color, TipoPieza.ELEFANTE);
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        if (!puedeEstarEnFila(filaDestino)) {
            return false;
        }
        
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        boolean movimientoDiagonal2 = deltaFila == 2 && deltaCol == 2;
        
        if (!movimientoDiagonal2) {
            return false;
        }
        
        int filaOjo = (this.fila + filaDestino) / 2;
        int colOjo = (this.columna + colDestino) / 2;
        
        if (tablero[filaOjo][colOjo] != null) {
            return false;
        }
        
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.getColor() == this.color) {
            return false;
        }
        
        return true;
    }
    
    private boolean puedeEstarEnFila(int fila) {
        if (this.color == ColorPieza.ROJO) {
            return fila >= Constantes.RIO_FILA_2 && fila <= Constantes.PALACIO_FILA_MAX_ROJO;
        } else {
            return fila >= Constantes.PALACIO_FILA_MIN_NEGRO && fila <= Constantes.RIO_FILA_1;
        }
    }
}