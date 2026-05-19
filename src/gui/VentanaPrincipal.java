/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import datos.GestorArrayList;
import datos.GestorDatos;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel panelContenido;
    private GestorDatos gestor;
    
    public VentanaPrincipal() {
        gestor = new GestorArrayList();
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("象棋 Xiangqi - Ajedrez Chino");
        setSize(1200, 900); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1100, 850)); 
    }
    
    private void inicializarComponentes() {
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        
        PanelLogin panelLogin = new PanelLogin(cardLayout, panelContenido, gestor);
        PanelRegistro panelRegistro = new PanelRegistro(cardLayout, panelContenido, gestor);
        PanelMenuPrincipal panelMenu = new PanelMenuPrincipal(cardLayout, panelContenido, gestor);
        PanelTablero panelTablero = new PanelTablero(cardLayout, panelContenido, gestor);
        
        panelContenido.add(panelLogin, "LOGIN");
        panelContenido.add(panelRegistro, "REGISTRO");
        panelContenido.add(panelMenu, "MENU");
        panelContenido.add(panelTablero, "TABLERO");
        
        add(panelContenido);
        
        cardLayout.show(panelContenido, "LOGIN");
    }
}