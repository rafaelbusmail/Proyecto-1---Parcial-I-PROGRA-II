/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Player;
import java.util.ArrayList;

public interface GestorDatos {
    
    boolean crearJugador(String username, String password);
    
    Player buscarJugador(String username);
    
    boolean validarLogin(String username, String password);
    
    boolean eliminarJugador(String username, String password);
    
    ArrayList<Player> obtenerJugadoresActivos();
    
    ArrayList<Player> obtenerRanking();
    
    boolean cambiarPassword(String username, String passwordActual, String passwordNuevo);
}
