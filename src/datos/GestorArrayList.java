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
        if (!Constantes.esPasswordValido(password)) {
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
        return buscarJugadorRec(username, 0);
    }

    private Player buscarJugadorRec(String username, int indice) {
        if (indice >= jugadores.size()) {
            return null;
        }
        if (jugadores.get(indice).getUsername().equalsIgnoreCase(username)) {
            return jugadores.get(indice);
        }
        return buscarJugadorRec(username, indice + 1);
    }

    @Override
    public boolean validarLogin(String username, String password) {
        Player jugador = buscarJugador(username);
        return jugador != null && jugador.getPassword().equals(password) && jugador.isActivo();
    }

    @Override
    public boolean eliminarJugador(String username, String password) {
        Player jugador = buscarJugador(username);
        if (jugador != null && jugador.getPassword().equals(password)) {
            jugador.setActivo(false);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Player> obtenerJugadoresActivos() {
        ArrayList<Player> activos = new ArrayList<>();
        for (Player jugador : jugadores) {
            if (jugador.isActivo()) {
                activos.add(jugador);
            }
        }
        return activos;
    }

    @Override
    public ArrayList<Player> obtenerRanking() {
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
    }

    @Override
    public boolean cambiarPassword(String username, String passwordActual, String passwordNuevo) {
        if (!Constantes.esPasswordValido(passwordNuevo)) {
            return false;
        }

        Player jugador = buscarJugador(username);
        if (jugador != null && jugador.getPassword().equals(passwordActual)) {
            jugador.setPassword(passwordNuevo);
            return true;
        }
        return false;
    }
}
