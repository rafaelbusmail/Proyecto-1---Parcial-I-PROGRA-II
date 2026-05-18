package modelo;

public class Elefante extends Pieza {
    
    public Elefante(int fila, int columna, boolean esRoja) {
        super(fila, columna, esRoja, "Elefante");
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        // Validar que esté dentro del tablero
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        // No puede cruzar el río
        if (!puedeEstarEnFila(filaDestino)) {
            return false;
        }
        
        // Movimiento: 2 casillas en diagonal
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        boolean movimientoDiagonal2 = deltaFila == 2 && deltaCol == 2;
        
        if (!movimientoDiagonal2) {
            return false;
        }
        
        // Verificar que el "ojo del elefante" no esté bloqueado
        int filaOjo = (this.fila + filaDestino) / 2;
        int colOjo = (this.columna + colDestino) / 2;
        
        if (tablero[filaOjo][colOjo] != null) {
            return false; // Ojo bloqueado
        }
        
        // No puede capturar sus propias piezas
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.isEsRoja() == this.esRoja) {
            return false;
        }
        
        return true;
    }
    
    private boolean puedeEstarEnFila(int fila) {
        // Elefantes rojos: solo filas 5-9 (no cruzan el río)
        if (this.esRoja) {
            return fila >= Constantes.RIO_FILA_2 && fila <= Constantes.PALACIO_FILA_MAX_ROJO;
        }
        // Elefantes negros: solo filas 0-4 (no cruzan el río)
        else {
            return fila >= Constantes.PALACIO_FILA_MIN_NEGRO && fila <= Constantes.RIO_FILA_1;
        }
    }
}