/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import modelo.ColorPieza;
import modelo.TipoPieza;
import javax.swing.ImageIcon;
import java.net.URL;

public final class CargadorImagenes {
    
    private CargadorImagenes() {
        throw new AssertionError("No se puede instanciar CargadorImagenes");
    }
    
    public static final ImageIcon cargarImagenPieza(TipoPieza tipo, ColorPieza color) {
        String nombreArchivo = tipo.getNombreArchivo() + "_" + 
                               (color == ColorPieza.ROJO ? "rojo" : "negro") + ".png";
        
        String ruta = "/recursos/" + nombreArchivo;
        
        try {
            URL urlImagen = CargadorImagenes.class.getResource(ruta);
            
            if (urlImagen != null) {
                ImageIcon icono = new ImageIcon(urlImagen);
                
                java.awt.Image img = icono.getImage();
                java.awt.Image imgEscalada = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                
                return new ImageIcon(imgEscalada);
            } else {
                System.err.println("No se encontró la imagen: " + ruta);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen " + ruta + ": " + e.getMessage());
            return null;
        }
    }
}
