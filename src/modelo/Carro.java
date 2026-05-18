package modelo;

public class Carro extends Pieza {
    
    public Carro(int fila, int columna, boolean esRoja) {
        super(fila, columna, esRoja, "Carro");
    }
    
    @Override
    public boolean esMovimientoValido(int filaDestino, int colDestino, Pieza[][] tablero) {
        // Validar que esté dentro del tablero
        if (!estaDentroTablero(filaDestino, colDestino)) {
            return false;
        }
        
        // Movimiento: horizontal o vertical, sin límite de distancia
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);
        
        // Debe moverse en línea recta
        boolean movimientoRecto = (deltaFila == 0 && deltaCol > 0) || 
                                  (deltaFila > 0 && deltaCol == 0);
        
        if (!movimientoRecto) {
            return false;
        }
        
        // Verificar camino libre usando recursividad
        if (!esCaminoLibre(this.fila, this.columna, filaDestino, colDestino, tablero)) {
            return false;
        }
        
        // No puede capturar sus propias piezas
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.isEsRoja() == this.esRoja) {
            return false;
        }
        
        return true;
    }
    
    // FUNCIÓN RECURSIVA 2: Verificar camino libre
    private boolean esCaminoLibre(int filaActual, int colActual, int filaDestino, int colDestino, Pieza[][] tablero) {
        // Caso base: llegamos al destino
        if (filaActual == filaDestino && colActual == colDestino) {
            return true;
        }
        
        // Determinar dirección
        int dirFila = Integer.compare(filaDestino, filaActual);
        int dirCol = Integer.compare(colDestino, colActual);
        
        // Avanzar una posición
        int siguienteFila = filaActual + dirFila;
        int siguienteCol = colActual + dirCol;
        
        // Si no es el destino y hay una pieza, el camino está bloqueado
        if (!(siguienteFila == filaDestino && siguienteCol == colDestino)) {
            if (tablero[siguienteFila][siguienteCol] != null) {
                return false;
            }
        }
        
        // Llamada recursiva
        return esCaminoLibre(siguienteFila, siguienteCol, filaDestino, colDestino, tablero);
    }
}