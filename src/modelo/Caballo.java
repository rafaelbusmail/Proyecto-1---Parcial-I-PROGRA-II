package modelo;

public class Caballo extends Pieza {
    
    public Caballo(int fila, int columna, boolean esRoja) {
        super(fila, columna, esRoja, "Caballo");
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        // Validar que esté dentro del tablero
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        // Movimiento en L: 1 ortogonal + 1 diagonal
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        // Verificar patrón de movimiento en L
        boolean esMovimientoL = (deltaFila == 2 && deltaCol == 1) || 
                                (deltaFila == 1 && deltaCol == 2);
        
        if (!esMovimientoL) {
            return false;
        }
        
        // Verificar bloqueo usando recursividad
        if (estaBloqueado(filaDestino, colDestino, tablero)) {
            return false;
        }
        
        // No puede capturar sus propias piezas
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.isEsRoja() == this.esRoja) {
            return false;
        }
        
        return true;
    }
    
    // FUNCIÓN RECURSIVA 1: Verificar si el caballo está bloqueado
    private boolean estaBloqueado(int filaDestino, int colDestino, Pieza[][] tablero) {
        int deltaFila = filaDestino - this.fila;
        int deltaCol = colDestino - this.columna;
        
        // Determinar la casilla de bloqueo (primer paso ortogonal)
        int filaBloqueo, colBloqueo;
        
        if (Math.abs(deltaFila) == 2) {
            // Movimiento vertical primero
            filaBloqueo = this.fila + (deltaFila > 0 ? 1 : -1);
            colBloqueo = this.columna;
        } else {
            // Movimiento horizontal primero
            filaBloqueo = this.fila;
            colBloqueo = this.columna + (deltaCol > 0 ? 1 : -1);
        }
        
        // Verificar si hay una pieza bloqueando
        return tablero[filaBloqueo][colBloqueo] != null;
    }
}