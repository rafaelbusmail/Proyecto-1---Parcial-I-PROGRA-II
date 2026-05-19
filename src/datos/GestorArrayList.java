/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Constantes;
import modelo.Player;
import java.util.ArrayList;

public class GestorArrayList implements GestorDatos {

    private ArrayList<Player> jugadores;

    public GestorArrayList() {
        this.jugadores = new ArrayList<>();
    }

    @Override
    public boolean crearJugador(String username, String password) {
        if (password.length() != Constantes.LONGITUD_PASSWORD) {
            return false;
        }

        if (buscarJugador(username) != null) {
            return false;
        }

        Player nuevoJugador = new Player(username, password);
        jugadores.add(nuevoJugador);
        return true;
    }

    @Override
    public Player buscarJugador(String username) {
        return buscarJugadorRecursivo(username, 0);
    }

    
    private Player buscarJugadorRecursivo(String username, int indice) {
        if (indice >= jugadores.size()) {
            return null;
        }

        Player jugadorActual = jugadores.get(indice);
        if (jugadorActual.getUsername().equalsIgnoreCase(username)) {
            return jugadorActual;
        }

        return buscarJugadorRecursivo(username, indice + 1);
    }

    @Override
    public boolean validarLogin(String username, String password) {
        try {
            Player jugador = buscarJugador(username);
            return jugador != null && jugador.getPassword().equals(password) && jugador.isActivo();
        } catch (Exception e) {
            System.err.println("Error en validarLogin: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarJugador(String username, String password) {
        try {
            Player jugador = buscarJugador(username);
            if (jugador != null && jugador.getPassword().equals(password)) {
                jugador.setActivo(false);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error en eliminarJugador: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Player> obtenerJugadoresActivos() {
        try {
            ArrayList<Player> activos = new ArrayList<>();
            for (Player jugador : jugadores) {
                if (jugador.isActivo()) {
                    activos.add(jugador);
                }
            }
            return activos;
        } catch (Exception e) {
            System.err.println("Error en obtenerJugadoresActivos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Player> obtenerRanking() {
        try {
            ArrayList<Player> ranking = new ArrayList<>(obtenerJugadoresActivos());

            for (int i = 0; i < ranking.size() - 1; i++) {
                for (int j = 0; j < ranking.size() - i - 1; j++) {
                    if (ranking.get(j).getPuntos() < ranking.get(j + 1).getPuntos()) {
                        Player temp = ranking.get(j);
                        ranking.set(j, ranking.get(j + 1));
                        ranking.set(j + 1, temp);
                    }
                }
            }

            return ranking;
        } catch (Exception e) {
            System.err.println("Error en obtenerRanking: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean cambiarPassword(String username, String passwordActual, String passwordNuevo) {
        try {
            if (passwordNuevo.length() != Constantes.LONGITUD_PASSWORD) {
                return false;
            }

            Player jugador = buscarJugador(username);
            if (jugador != null && jugador.getPassword().equals(passwordActual)) {
                jugador.setPassword(passwordNuevo);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error en cambiarPassword: " + e.getMessage());
            return false;
        }
    }
}
