/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public enum ColorPieza {
    ROJO("Rojo", true),
    NEGRO("Negro", false);
    
    private final String nombre;
    private final boolean esRojo;
    
    private ColorPieza(String nombre, boolean esRojo) {
        this.nombre = nombre;
        this.esRojo = esRojo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public boolean isRojo() {
        return esRojo;
    }
    
    public static ColorPieza fromBoolean(boolean esRojo) {
        return esRojo ? ROJO : NEGRO;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
