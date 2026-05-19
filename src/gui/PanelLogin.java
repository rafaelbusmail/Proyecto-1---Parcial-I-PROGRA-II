/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import datos.GestorDatos;
import javax.swing.*;
import java.awt.*;

public class PanelLogin extends JPanel {

    private CardLayout cardLayout;
    private JPanel panelContenido;
    private GestorDatos gestor;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCrear;
    private JButton btnSalir;
    private JLabel lblError;

    public PanelLogin(CardLayout cardLayout, JPanel panelContenido, GestorDatos gestor) {
        this.cardLayout = cardLayout;
        this.panelContenido = panelContenido;
        this.gestor = gestor;

        configurarPanel();
        crearComponentes();
    }

    private void configurarPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 235, 216));
    }

    private void crearComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(139, 69, 19, 200));
        panelFormulario.setBorder(BorderFactory.createLineBorder(new Color(101, 67, 33), 3));

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(10, 20, 10, 20);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("象棋 XIANGQI");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(218, 165, 32));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbcForm.gridx = 0;
        gbcForm.gridy = 0;
        gbcForm.gridwidth = 2;
        panelFormulario.add(lblTitulo, gbcForm);

        JLabel lblSubtitulo = new JLabel("Ajedrez Chino");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbcForm.gridy = 1;
        panelFormulario.add(lblSubtitulo, gbcForm);

        gbcForm.gridwidth = 1;

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsername.setForeground(Color.WHITE);
        gbcForm.gridx = 0;
        gbcForm.gridy = 2;
        panelFormulario.add(lblUsername, gbcForm);

        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcForm.gridx = 1;
        panelFormulario.add(txtUsername, gbcForm);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setForeground(Color.WHITE);
        gbcForm.gridx = 0;
        gbcForm.gridy = 3;
        panelFormulario.add(lblPassword, gbcForm);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcForm.gridx = 1;
        panelFormulario.add(txtPassword, gbcForm);

        lblError = new JLabel(" ");
        lblError.setFont(new Font("Arial", Font.ITALIC, 12));
        lblError.setForeground(new Color(255, 100, 100));
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        gbcForm.gridx = 0;
        gbcForm.gridy = 4;
        gbcForm.gridwidth = 2;
        panelFormulario.add(lblError, gbcForm);

        btnLogin = crearBoton("LOG IN", new Color(184, 134, 11));
        btnLogin.addActionListener(e -> manejarLogin());
        gbcForm.gridy = 5;
        panelFormulario.add(btnLogin, gbcForm);

        btnCrear = crearBoton("CREAR PLAYER", new Color(139, 69, 19));
        btnCrear.addActionListener(e -> {
            limpiarCampos();
            cardLayout.show(panelContenido, "REGISTRO");
        });
        gbcForm.gridy = 6;
        panelFormulario.add(btnCrear, gbcForm);

        btnSalir = crearBoton("SALIR", new Color(178, 34, 34));
        btnSalir.addActionListener(e -> System.exit(0));
        gbcForm.gridy = 7;
        panelFormulario.add(btnSalir, gbcForm);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelFormulario, gbc);

        txtPassword.addActionListener(e -> manejarLogin());
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(250, 40));
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

    private void manejarLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }

        if (gestor.validarLogin(username, password)) {
            lblError.setText(" ");
            limpiarCampos();

            for (Component comp : panelContenido.getComponents()) {
                if (comp instanceof PanelMenuPrincipal) {
                    ((PanelMenuPrincipal) comp).setJugadorActual(gestor.buscarJugador(username));
                }
            }

            cardLayout.show(panelContenido, "MENU");
        } else {
            mostrarError("Usuario o contraseña incorrectos");
        }
    }

    private void mostrarError(String mensaje) {
        lblError.setText("[!] " + mensaje);
    }

    private void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        lblError.setText(" ");
    }
}
