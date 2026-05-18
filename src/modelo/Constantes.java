/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public final class Constantes {
    
    // Constructor privado para evitar instanciación
    private Constantes() {
        throw new AssertionError("No se puede instanciar la clase Constantes");
    }
    
    // Dimensiones del tablero
    public static final int FILAS = 10;
    public static final int COLUMNAS = 9;
    
    // Límites del palacio
    public static final int PALACIO_FILA_MIN_ROJO = 7;
    public static final int PALACIO_FILA_MAX_ROJO = 9;
    public static final int PALACIO_FILA_MIN_NEGRO = 0;
    public static final int PALACIO_FILA_MAX_NEGRO = 2;
    public static final int PALACIO_COL_MIN = 3;
    public static final int PALACIO_COL_MAX = 5;
    
    // Límite del río
    public static final int RIO_FILA_1 = 4;
    public static final int RIO_FILA_2 = 5;
    
    // Puntos por victoria
    public static final int PUNTOS_VICTORIA = 3;
    
    // Validación de password
    public static final int LONGITUD_PASSWORD = 5;
    
    // Límite de logs
    public static final int MAX_LOGS = 100;
}
