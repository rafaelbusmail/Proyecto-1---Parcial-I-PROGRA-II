/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package modelo;


public class PlayerInvitado extends Player {

    private ColorPieza turnoAsignado;

    public PlayerInvitado(Player jugadorBase, ColorPieza turno) {
        super(jugadorBase.getUsername(), jugadorBase.getPassword());

        if (jugadorBase.getPuntos() > 0) {
            this.agregarPuntos(jugadorBase.getPuntos());
        }
        this.setActivo(jugadorBase.isActivo());

        String[] logsAnteriores = jugadorBase.getLogsPartidas();
        for (String log : logsAnteriores) {
            if (log != null) {
                this.agregarLog(log);
            }
        }

        this.turnoAsignado = turno;
    }


    public boolean esSesionActual() {
        return false;
    }

    public ColorPieza getTurnoAsignado() {
        return turnoAsignado;
    }

    public void setTurnoAsignado(ColorPieza turno) {
        this.turnoAsignado = turno;
    }
}
