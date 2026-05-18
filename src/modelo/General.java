package modelo;

public class General extends Pieza {

    public General(int fila, int columna, boolean esRoja) {
        super(fila, columna, esRoja, "General");
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

        // Solo puede moverse 1 casilla en vertical u horizontal
        int deltaFila = Math.abs(filaDestino - this.fila);
        int deltaCol = Math.abs(colDestino - this.columna);

        // Movimiento de 1 casilla ortogonal
        boolean movimientoValido = (deltaFila == 1 && deltaCol == 0)
                || (deltaFila == 0 && deltaCol == 1);

        if (!movimientoValido) {
            return false;
        }

        // No puede capturar sus propias piezas
        Pieza piezaDestino = tablero[filaDestino][colDestino];
        if (piezaDestino != null && piezaDestino.isEsRoja() == this.esRoja) {
            return false;
        }

        // Validar regla "Generales enfrentados"
        if (!validarGeneralesEnfrentados(filaDestino, colDestino, tablero)) {
            return false;
        }

        return true;
    }

    private boolean estaDentroPalacio(int fila, int col) {
        // Palacio rojo (filas 7-9, columnas 3-5)
        if (this.esRoja) {
            return fila >= Constantes.PALACIO_FILA_MIN_ROJO
                    && fila <= Constantes.PALACIO_FILA_MAX_ROJO
                    && col >= Constantes.PALACIO_COL_MIN
                    && col <= Constantes.PALACIO_COL_MAX;
        } // Palacio negro (filas 0-2, columnas 3-5)
        else {
            return fila >= Constantes.PALACIO_FILA_MIN_NEGRO
                    && fila <= Constantes.PALACIO_FILA_MAX_NEGRO
                    && col >= Constantes.PALACIO_COL_MIN
                    && col <= Constantes.PALACIO_COL_MAX;
        }
    }

    private boolean validarGeneralesEnfrentados(int filaDestino, int colDestino, Pieza[][] tablero) {
        // Encontrar el otro General
        General otroGeneral = null;
        for (int f = 0; f < Constantes.FILAS; f++) {
            for (int c = 0; c < Constantes.COLUMNAS; c++) {
                if (tablero[f][c] instanceof General
                        && tablero[f][c].isEsRoja() != this.esRoja) {
                    otroGeneral = (General) tablero[f][c];
                    break;
                }
            }
            if (otroGeneral != null) {
                break;
            }
        }

        if (otroGeneral == null) {
            return true;
        }

        // Si están en la misma columna, verificar que haya piezas entre ellos
        if (colDestino == otroGeneral.getColumna()) {
            int filaMin = Math.min(filaDestino, otroGeneral.getFila());
            int filaMax = Math.max(filaDestino, otroGeneral.getFila());

            for (int f = filaMin + 1; f < filaMax; f++) {
                if (tablero[f][colDestino] != null) {
                    return true; // Hay una pieza entre ellos, es válido
                }
            }
            return false; // No hay piezas entre ellos, movimiento inválido
        }

        return true;
    }
}
