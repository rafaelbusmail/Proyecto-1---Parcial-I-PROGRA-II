/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import datos.GestorDatos;
import modelo.Player;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class PanelMenuPrincipal extends JPanel {
    
    private CardLayout cardLayout;
    private JPanel panelContenido;
    private GestorDatos gestor;
    private Player jugadorActual;
    
    private JLabel lblBienvenida;
    private JButton btnJugar;
    private JButton btnMiCuenta;
    private JButton btnReportes;
    private JButton btnLogout;
    
    public PanelMenuPrincipal(CardLayout cardLayout, JPanel panelContenido, GestorDatos gestor) {
        this.cardLayout = cardLayout;
        this.panelContenido = panelContenido;
        this.gestor = gestor;
        
        configurarPanel();
        crearComponentes();
    }
    
    private void configurarPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 235, 216));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
    
    private void crearComponentes() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        
        JLabel lblTitulo = new JLabel("象棋 XIANGQI", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 48));
        lblTitulo.setForeground(new Color(139, 69, 19));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        
        lblBienvenida = new JLabel("", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
        lblBienvenida.setForeground(new Color(184, 134, 11));
        panelSuperior.add(lblBienvenida, BorderLayout.CENTER);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new GridLayout(4, 1, 15, 15));
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        btnJugar = crearBoton("JUGAR XIANGQI", new Color(178, 34, 34));
        btnJugar.addActionListener(e -> mostrarMenuJugar());
        panelCentral.add(btnJugar);
        
        btnMiCuenta = crearBoton("MI CUENTA", new Color(184, 134, 11));
        btnMiCuenta.addActionListener(e -> mostrarMiCuenta());
        panelCentral.add(btnMiCuenta);
        
        btnReportes = crearBoton("REPORTES", new Color(139, 69, 19));
        btnReportes.addActionListener(e -> mostrarReportes());
        panelCentral.add(btnReportes);
        
        btnLogout = crearBoton("LOG OUT", new Color(105, 105, 105));
        btnLogout.addActionListener(e -> {
            jugadorActual = null;
            cardLayout.show(panelContenido, "LOGIN");
        });
        panelCentral.add(btnLogout);
        
        add(panelCentral, BorderLayout.CENTER);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    private void mostrarMenuJugar() {
        String[] opciones = {"Nueva Partida", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(
            this,
            "Seleccione una opción:",
            "Jugar Xiangqi",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (seleccion == 0) {
            iniciarNuevaPartida();
        }
    }
    
    private void iniciarNuevaPartida() {
        java.util.ArrayList<Player> jugadoresDisponibles = gestor.obtenerJugadoresActivos();
        
        if (jugadoresDisponibles.size() < 2) {
            JOptionPane.showMessageDialog(
                this,
                "No hay suficientes jugadores registrados.\nNecesitas al menos 2 jugadores para iniciar una partida.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        java.util.ArrayList<String> opcionesJugadores = new java.util.ArrayList<>();
        for (Player p : jugadoresDisponibles) {
            if (!p.getUsername().equals(jugadorActual.getUsername())) {
                opcionesJugadores.add(p.getUsername() + " (" + p.getPuntos() + " pts)");
            }
        }
        
        if (opcionesJugadores.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No hay otros jugadores disponibles.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        String[] nombresArray = opcionesJugadores.toArray(new String[0]);
        
        String seleccion = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione su oponente (Jugador 2):",
            "Seleccionar Oponente",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nombresArray,
            nombresArray[0]
        );
        
        if (seleccion != null) {
            String usernameOponente = seleccion.split(" \\(")[0];
            Player jugador2 = gestor.buscarJugador(usernameOponente);
            
            if (jugador2 != null) {
                for (Component comp : panelContenido.getComponents()) {
                    if (comp instanceof PanelTablero) {
                        ((PanelTablero) comp).iniciarPartida(jugadorActual, jugador2);
                    }
                }
                cardLayout.show(panelContenido, "TABLERO");
            }
        }
    }
    
    private void mostrarMiCuenta() {
        if (jugadorActual == null) return;
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("MI CUENTA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        
        panel.add(Box.createVerticalStrut(15));
        
        JLabel lblUsername = new JLabel("Username: " + jugadorActual.getUsername());
        lblUsername.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(lblUsername);
        
        JLabel lblPuntos = new JLabel("Puntos: " + jugadorActual.getPuntos());
        lblPuntos.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(lblPuntos);
        
        JLabel lblFecha = new JLabel("Fecha de Ingreso: " + sdf.format(jugadorActual.getFechaIngreso()));
        lblFecha.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(lblFecha);
        
        JLabel lblEstado = new JLabel("Estado: " + (jugadorActual.isActivo() ? "Activo" : "Inactivo"));
        lblEstado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(lblEstado);
        
        String[] opciones = {"Cambiar Password", "Eliminar Cuenta", "Cerrar"};
        int seleccion = JOptionPane.showOptionDialog(
            this,
            panel,
            "Mi Cuenta",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opciones,
            opciones[2]
        );
        
        if (seleccion == 0) {
            cambiarPassword();
        } else if (seleccion == 1) {
            eliminarCuenta();
        }
    }
    
    private void cambiarPassword() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JPasswordField txtActual = new JPasswordField();
        JPasswordField txtNueva = new JPasswordField();
        JPasswordField txtConfirmar = new JPasswordField();
        
        panel.add(new JLabel("Password Actual:"));
        panel.add(txtActual);
        panel.add(new JLabel("Nueva Password (5 caracteres):"));
        panel.add(txtNueva);
        panel.add(new JLabel("Confirmar:"));
        panel.add(txtConfirmar);
        
        int resultado = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Cambiar Password",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (resultado == JOptionPane.OK_OPTION) {
            String actual = new String(txtActual.getPassword());
            String nueva = new String(txtNueva.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());
            
            if (nueva.length() != 5) {
                JOptionPane.showMessageDialog(this, "La password debe ser de 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!nueva.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "Las passwords no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (gestor.cambiarPassword(jugadorActual.getUsername(), actual, nueva)) {
                JOptionPane.showMessageDialog(this, "Password cambiada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Password actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarCuenta() {
        String password = JOptionPane.showInputDialog(this, "Ingrese su password para confirmar:");
        
        if (password != null && !password.isEmpty()) {
            if (gestor.eliminarJugador(jugadorActual.getUsername(), password)) {
                JOptionPane.showMessageDialog(this, "Cuenta eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                jugadorActual = null;
                cardLayout.show(panelContenido, "LOGIN");
            } else {
                JOptionPane.showMessageDialog(this, "Password incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void mostrarReportes() {
        String[] opciones = {"Ranking de Jugadores", "Mis Ultimos Partidos", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(
            this,
            "Seleccione un reporte:",
            "Reportes",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (seleccion == 0) {
            mostrarRanking();
        } else if (seleccion == 1) {
            mostrarLogs();
        }
    }
    
    private void mostrarRanking() {
        java.util.ArrayList<Player> ranking = gestor.obtenerRanking();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("RANKING DE JUGADORES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        
        panel.add(Box.createVerticalStrut(15));
        
        if (ranking.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay jugadores activos.");
            lblVacio.setFont(new Font("Monospaced", Font.PLAIN, 14));
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblVacio);
        } else {
            for (int i = 0; i < ranking.size(); i++) {
                Player p = ranking.get(i);
                JLabel lblJugador = new JLabel(String.format("%d. %s - %d puntos", (i + 1), p.getUsername(), p.getPuntos()));
                lblJugador.setFont(new Font("Monospaced", Font.PLAIN, 14));
                panel.add(lblJugador);
                panel.add(Box.createVerticalStrut(5));
            }
        }
        
        JOptionPane.showMessageDialog(this, panel, "Ranking", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarLogs() {
        if (jugadorActual == null) return;
        
        String[] logs = jugadorActual.getLogsPartidas();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("MIS ULTIMOS PARTIDOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        
        panel.add(Box.createVerticalStrut(15));
        
        if (logs.length == 0) {
            JLabel lblVacio = new JLabel("No hay partidos registrados.");
            lblVacio.setFont(new Font("Monospaced", Font.PLAIN, 12));
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblVacio);
        } else {
            for (int i = 0; i < logs.length; i++) {
                JLabel lblLog = new JLabel((i + 1) + ". " + logs[i]);
                lblLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
                panel.add(lblLog);
                panel.add(Box.createVerticalStrut(5));
            }
        }
        
        JOptionPane.showMessageDialog(this, panel, "Mis Ultimos Partidos", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void setJugadorActual(Player jugador) {
        this.jugadorActual = jugador;
        if (jugador != null) {
            lblBienvenida.setText("Bienvenido, " + jugador.getUsername() + " | Puntos: " + jugador.getPuntos());
        }
    }
}