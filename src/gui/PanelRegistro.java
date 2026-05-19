/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import datos.GestorDatos;
import modelo.Constantes;
import javax.swing.*;
import java.awt.*;

public class PanelRegistro extends JPanel {

    private CardLayout cardLayout;
    private JPanel panelContenido;
    private GestorDatos gestor;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmar;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private JLabel lblError;

    public PanelRegistro(CardLayout cardLayout, JPanel panelContenido, GestorDatos gestor) {
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

        JLabel lblTitulo = new JLabel("CREAR PLAYER");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(218, 165, 32));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbcForm.gridx = 0;
        gbcForm.gridy = 0;
        gbcForm.gridwidth = 2;
        panelFormulario.add(lblTitulo, gbcForm);

        gbcForm.gridwidth = 1;

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsername.setForeground(Color.WHITE);
        gbcForm.gridx = 0;
        gbcForm.gridy = 1;
        panelFormulario.add(lblUsername, gbcForm);

        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcForm.gridx = 1;
        panelFormulario.add(txtUsername, gbcForm);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setForeground(Color.WHITE);
        gbcForm.gridx = 0;
        gbcForm.gridy = 2;
        panelFormulario.add(lblPassword, gbcForm);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcForm.gridx = 1;
        panelFormulario.add(txtPassword, gbcForm);

        JLabel lblConfirmar = new JLabel("Confirmar:");
        lblConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        lblConfirmar.setForeground(Color.WHITE);
        gbcForm.gridx = 0;
        gbcForm.gridy = 3;
        panelFormulario.add(lblConfirmar, gbcForm);

        txtConfirmar = new JPasswordField(15);
        txtConfirmar.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcForm.gridx = 1;
        panelFormulario.add(txtConfirmar, gbcForm);

        JLabel lblInfo = new JLabel("(5 caracteres, al menos una letra)");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(255, 215, 0));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        gbcForm.gridx = 0;
        gbcForm.gridy = 4;
        gbcForm.gridwidth = 2;
        panelFormulario.add(lblInfo, gbcForm);

        lblError = new JLabel(" ");
        lblError.setFont(new Font("Arial", Font.ITALIC, 12));
        lblError.setForeground(new Color(255, 100, 100));
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        gbcForm.gridy = 5;
        panelFormulario.add(lblError, gbcForm);

        btnRegistrar = crearBoton("CREAR CUENTA", new Color(34, 139, 34));
        btnRegistrar.addActionListener(e -> manejarRegistro());
        gbcForm.gridy = 6;
        panelFormulario.add(btnRegistrar, gbcForm);

        btnVolver = crearBoton("VOLVER", new Color(105, 105, 105));
        btnVolver.addActionListener(e -> {
            limpiarCampos();
            cardLayout.show(panelContenido, "LOGIN");
        });
        gbcForm.gridy = 7;
        panelFormulario.add(btnVolver, gbcForm);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelFormulario, gbc);
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

    private void manejarRegistro() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }

        if (username.length() < 3) {
            mostrarError("Username debe tener mínimo 3 caracteres");
            return;
        }

        if (!Constantes.esPasswordValido(password)) {
            mostrarError("Password debe tener 5 caracteres y al menos una letra");
            return;
        }

        if (!password.equals(confirmar)) {
            mostrarError("Las contraseñas no coinciden");
            return;
        }

        if (gestor.crearJugador(username, password)) {
            JOptionPane.showMessageDialog(this,
                    "¡Cuenta creada exitosamente!\nBienvenido " + username,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();

            for (Component comp : panelContenido.getComponents()) {
                if (comp instanceof PanelMenuPrincipal) {
                    ((PanelMenuPrincipal) comp).setJugadorActual(gestor.buscarJugador(username));
                }
            }

            cardLayout.show(panelContenido, "MENU");
        } else {
            mostrarError("El username ya existe");
        }
    }

    private void mostrarError(String mensaje) {
        lblError.setText("⚠ " + mensaje);
    }

    private void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmar.setText("");
        lblError.setText(" ");
    }
}
