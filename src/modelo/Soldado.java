package modelo;

public class Soldado extends Pieza {
    
    public Soldado(int fila, int columna, ColorPieza color) {
        super(fila, columna, color, TipoPieza.SOLDADO);
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        int deltaFila = filaDestino - this.fila;
        int deltaCol = Math.abs(colDestino - this.columna);
        
        int direccionAvance = (this.color == ColorPieza.ROJO) ? -1 : 1;
        
        if ((this.color == ColorPieza.ROJO && deltaFila > 0) || 
            (this.color == ColorPieza.NEGRO && deltaFila < 0)) {
            return false;
        }
        
        if (!haCruzadoRio()) {
            if (deltaFila == direccionAvance && deltaCol == 0) {
                Pieza piezaDestino = tablero[filaDestino][colDestino];
                if (piezaDestino != null && piezaDestino.getColor() == this.color) {
                    return false;
                }
                return true;
            }
            return false;
        }
        
        boolean avanzaAdelante = deltaFila == direccionAvance && deltaCol == 0;
        boolean mueveLateral = deltaFila == 0 && deltaCol == 1;
        
        if (avanzaAdelante || mueveLateral) {
            Pieza piezaDestino = tablero[filaDestino][colDestino];
            if (piezaDestino != null && piezaDestino.getColor() == this.color) {
                return false;
            }
            return true;
        }
        
        return false;
    }
    
    private boolean haCruzadoRio() {
        if (this.color == ColorPieza.ROJO) {
            return this.fila < Constantes.RIO_FILA_2;
        } else {
            return this.fila > Constantes.RIO_FILA_1;
        }
    }
}