/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

public class Player {
    
    private String username;
    private String password;
    private int puntos;
    private Date fechaIngreso;
    private boolean activo;
    private String[] logsPartidas;
    private int contadorLogs;
    
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntos = 0;
        this.fechaIngreso = new Date();
        this.activo = true;
        this.logsPartidas = new String[Constantes.MAX_LOGS];
        this.contadorLogs = 0;
    }
    
    public final String getUsername() {
        return username;
    }
    
    public final String getPassword() {
        return password;
    }
    
    public final void setPassword(String password) {
        this.password = password;
    }
    
    public final int getPuntos() {
        return puntos;
    }
    
    public final void agregarPuntos(int cantidad) {
        this.puntos += cantidad;
    }
    
    public final Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    public final boolean isActivo() {
        return activo;
    }
    
    public final void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public final void agregarLog(String log) {
        for (int i = contadorLogs - 1; i >= 0; i--) {
            if (i + 1 < logsPartidas.length) {
                logsPartidas[i + 1] = logsPartidas[i];
            }
        }
        logsPartidas[0] = log;
        if (contadorLogs < logsPartidas.length) {
            contadorLogs++;
        }
    }
    
    public final String[] getLogsPartidas() {
        String[] logs = new String[contadorLogs];
        for (int i = 0; i < contadorLogs; i++) {
            logs[i] = logsPartidas[i];
        }
        return logs;
    }
    
    @Override
    public final String toString() {
        return "Username: " + username + " | Puntos: " + puntos;
    }
}