package modelo;

public class Soldado extends Pieza {
    
    public Soldado(int fila, int columna, boolean esRoja) {
        super(fila, columna, esRoja, "Soldado");
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        // Validar que esté dentro del tablero
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        int deltaFila = filaDestino - this.fila;
        int deltaCol = Math.abs(colDestino - this.columna);
        
        // Soldados rojos: avanzan hacia arriba (fila disminuye)
        // Soldados negros: avanzan hacia abajo (fila aumenta)
        int direccionAvance = this.esRoja ? -1 : 1;
        
        // Nunca puede retroceder
        if ((this.esRoja && deltaFila > 0) || (!this.esRoja && deltaFila < 0)) {
            return false;
        }
        
        // Antes de cruzar el río
        if (!haCruzadoRio()) {
            // Solo puede avanzar 1 hacia adelante
            if (deltaFila == direccionAvance && deltaCol == 0) {
                Pieza piezaDestino = tablero[filaDestino][colDestino];
                if (piezaDestino != null && piezaDestino.isEsRoja() == this.esRoja) {
                    return false;
                }
                return true;
            }
            return false;
        }
        
        // Después de cruzar el río: puede avanzar o moverse lateralmente
        boolean avanzaAdelante = deltaFila == direccionAvance && deltaCol == 0;
        boolean mueveLateral = deltaFila == 0 && deltaCol == 1;
        
        if (avanzaAdelante || mueveLateral) {
            Pieza piezaDestino = tablero[filaDestino][colDestino];
            if (piezaDestino != null && piezaDestino.isEsRoja() == this.esRoja) {
                return false;
            }
            return true;
        }
        
        return false;
    }
    
    private boolean haCruzadoRio() {
        // Soldados rojos cruzan cuando fila < 5 (entran al territorio negro)
        if (this.esRoja) {
            return this.fila < Constantes.RIO_FILA_2;
        }
        // Soldados negros cruzan cuando fila > 4 (entran al territorio rojo)
        else {
            return this.fila > Constantes.RIO_FILA_1;
        }
    }
}