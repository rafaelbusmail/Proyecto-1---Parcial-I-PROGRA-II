/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public enum TipoPieza {
    GENERAL("General", "GEN", "general"),
    OFICIAL("Oficial", "OFI", "oficial"),
    ELEFANTE("Elefante", "ELE", "elefante"),
    CABALLO("Caballo", "CAB", "caballo"),
    CARRO("Carro", "CAR", "carro"),
    CANON("Cañón", "CAN", "canon"),
    SOLDADO("Soldado", "SOL", "soldado");
    
    private final String nombreCompleto;
    private final String nombreCorto;
    private final String nombreArchivo;
    
    private TipoPieza(String nombreCompleto, String nombreCorto, String nombreArchivo) {
        this.nombreCompleto = nombreCompleto;
        this.nombreCorto = nombreCorto;
        this.nombreArchivo = nombreArchivo;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public String getNombreCorto() {
        return nombreCorto;
    }
    
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    
    @Override
    public String toString() {
        return nombreCompleto;
    }
}
