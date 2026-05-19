package gui;

import datos.GestorDatos;
import modelo.*;
import util.CargadorImagenes;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelTablero extends JPanel {

    private CardLayout cardLayout;
    private JPanel panelContenido;
    private GestorDatos gestor;

    private Player jugador1;
    private Player jugador2;
    private boolean turnoRojo;

    private JButton[][] casillas;
    private Pieza[][] tablero;
    private Pieza piezaSeleccionada;
    private int filaSeleccionada;
    private int columnaSeleccionada;

    private ArrayList<Pieza> piezasCapturadas;

    private JLabel lblTurno;
    private JLabel lblJugador1;
    private JLabel lblJugador2;
    private JPanel panelCapturasRojo;
    private JPanel panelCapturasNegro;
    private JButton btnRetirar;
    private JButton btnVolverMenu;

    private static final int FILAS = Constantes.FILAS;
    private static final int COLUMNAS = Constantes.COLUMNAS;

    private static final Color COLOR_BLANCO = new Color(238, 238, 210);
    private static final Color COLOR_VERDE = new Color(118, 150, 86);
    private static final Color COLOR_BORDE = new Color(101, 67, 33);
    private static final Color COLOR_RIO = new Color(70, 130, 180);
    private static final Color COLOR_SELECCION = new Color(255, 255, 0, 150);
    private static final Color COLOR_MOVIMIENTO_VALIDO = new Color(144, 238, 144, 150);
    private static final Color COLOR_PALACIO = new Color(218, 165, 32);

    public PanelTablero(CardLayout cardLayout, JPanel panelContenido, GestorDatos gestor) {
        this.cardLayout = cardLayout;
        this.panelContenido = panelContenido;
        this.gestor = gestor;
        this.tablero = new Pieza[FILAS][COLUMNAS];
        this.piezasCapturadas = new ArrayList<>();

        configurarPanel();
        crearComponentes();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 235, 216));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void crearComponentes() {
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setOpaque(false);

        lblTurno = new JLabel("", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 24));
        lblTurno.setOpaque(true);
        lblTurno.setBackground(new Color(240, 235, 216));
        lblTurno.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.add(lblTurno);

        JPanel panelJugadores = new JPanel(new GridLayout(1, 2, 10, 0));
        panelJugadores.setOpaque(false);

        JPanel panelJ1 = new JPanel(new BorderLayout(5, 5));
        panelJ1.setOpaque(false);

        lblJugador1 = new JLabel("", SwingConstants.CENTER);
        lblJugador1.setFont(new Font("Arial", Font.BOLD, 16));
        lblJugador1.setForeground(new Color(178, 34, 34));
        lblJugador1.setOpaque(true);
        lblJugador1.setBackground(new Color(255, 255, 255));
        lblJugador1.setBorder(BorderFactory.createLineBorder(new Color(178, 34, 34), 2));
        panelJ1.add(lblJugador1, BorderLayout.NORTH);

        panelCapturasRojo = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        panelCapturasRojo.setOpaque(false);
        panelCapturasRojo.setBorder(BorderFactory.createTitledBorder("Capturas"));
        panelCapturasRojo.setPreferredSize(new Dimension(0, 60));
        panelJ1.add(panelCapturasRojo, BorderLayout.CENTER);

        panelJugadores.add(panelJ1);

        JPanel panelJ2 = new JPanel(new BorderLayout(5, 5));
        panelJ2.setOpaque(false);

        lblJugador2 = new JLabel("", SwingConstants.CENTER);
        lblJugador2.setFont(new Font("Arial", Font.BOLD, 16));
        lblJugador2.setForeground(Color.BLACK);
        lblJugador2.setOpaque(true);
        lblJugador2.setBackground(new Color(255, 255, 255));
        lblJugador2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panelJ2.add(lblJugador2, BorderLayout.NORTH);

        panelCapturasNegro = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        panelCapturasNegro.setOpaque(false);
        panelCapturasNegro.setBorder(BorderFactory.createTitledBorder("Capturas"));
        panelCapturasNegro.setPreferredSize(new Dimension(0, 60));
        panelJ2.add(panelCapturasNegro, BorderLayout.CENTER);

        panelJugadores.add(panelJ2);

        panel.add(panelJugadores);

        return panel;
    }


    private JPanel crearPanelCentral() {
        JPanel panelContenedor = new JPanel(new BorderLayout(0, 0));
        panelContenedor.setOpaque(false);

        JPanel panelLetras = new JPanel(new BorderLayout(0, 0));
        panelLetras.setOpaque(false);

        JLabel espacioIzq = new JLabel("");
        espacioIzq.setPreferredSize(new Dimension(28, 24));
        panelLetras.add(espacioIzq, BorderLayout.WEST);

        JPanel filaCols = new JPanel(new GridLayout(1, COLUMNAS, 0, 0));
        filaCols.setOpaque(false);
        String[] columnas = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        for (String col : columnas) {
            JLabel lblCol = new JLabel(col, SwingConstants.CENTER);
            lblCol.setFont(new Font("Arial", Font.BOLD, 15));
            lblCol.setForeground(COLOR_BORDE);
            filaCols.add(lblCol);
        }
        panelLetras.add(filaCols, BorderLayout.CENTER);
        panelContenedor.add(panelLetras, BorderLayout.NORTH);

        JPanel panelFilas = new JPanel(new GridLayout(FILAS, 1, 0, 0));
        panelFilas.setOpaque(false);
        panelFilas.setPreferredSize(new Dimension(28, 0));
        for (int i = FILAS; i >= 1; i--) {
            JLabel lblFila = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            lblFila.setFont(new Font("Arial", Font.BOLD, 15));
            lblFila.setForeground(COLOR_BORDE);
            panelFilas.add(lblFila);
        }
        panelContenedor.add(panelFilas, BorderLayout.WEST);

        JPanel panelTableroGrafico = crearTableroGrafico();
        panelContenedor.add(panelTableroGrafico, BorderLayout.CENTER);

        return panelContenedor;
    }

    private JPanel crearTableroGrafico() {
        JPanel panel = new JPanel(new GridLayout(FILAS, COLUMNAS, 0, 0));
        panel.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 4));
        panel.setBackground(COLOR_BORDE);

        casillas = new JButton[FILAS][COLUMNAS];

        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                JButton casilla = new JButton();
                casilla.setFocusPainted(false);
                casilla.setBorderPainted(true);
                casilla.setContentAreaFilled(true);
                casilla.setPreferredSize(new Dimension(70, 70));
                casilla.setFont(new Font("Arial", Font.BOLD, 10));

                Color colorCasilla = obtenerColorCasilla(fila, col);
                casilla.setBackground(colorCasilla);

                casilla.setBorder(obtenerBordeCasilla(fila, col));

                final int f = fila;
                final int c = col;
                casilla.addActionListener(e -> manejarClicCasilla(f, c));

                casillas[fila][col] = casilla;
                panel.add(casilla);
            }
        }

        return panel;
    }

    private javax.swing.border.Border obtenerBordeCasilla(int fila, int col) {
        boolean esPalacioSuperior = (fila >= 0 && fila <= 2) && (col >= 3 && col <= 5);
        boolean esPalacioInferior = (fila >= 7 && fila <= 9) && (col >= 3 && col <= 5);

        if (esPalacioSuperior || esPalacioInferior) {
            int top = (fila == 0 || fila == 7) ? 3 : 1;
            int left = (col == 3) ? 3 : 1;
            int bottom = (fila == 2 || fila == 9) ? 3 : 1;
            int right = (col == 5) ? 3 : 1;

            return BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(top, left, bottom, right, COLOR_PALACIO),
                    BorderFactory.createLineBorder(COLOR_BORDE, 1)
            );
        }

        if (fila == 4) {
            return BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, COLOR_RIO),
                    BorderFactory.createLineBorder(COLOR_BORDE, 1)
            );
        }

        if (fila == 5) {
            return BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(3, 0, 0, 0, COLOR_RIO),
                    BorderFactory.createLineBorder(COLOR_BORDE, 1)
            );
        }

        return BorderFactory.createLineBorder(COLOR_BORDE, 1);
    }

    private Color obtenerColorCasilla(int fila, int col) {
        boolean esCasillaBlanca = (fila + col) % 2 == 0;
        return esCasillaBlanca ? COLOR_BLANCO : COLOR_VERDE;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setOpaque(false);

        btnRetirar = new JButton("RETIRAR");
        btnRetirar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRetirar.setBackground(new Color(178, 34, 34));
        btnRetirar.setForeground(Color.WHITE);
        btnRetirar.setFocusPainted(false);
        btnRetirar.setPreferredSize(new Dimension(150, 40));
        btnRetirar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRetirar.addActionListener(e -> manejarRetiro());
        panel.add(btnRetirar);

        btnVolverMenu = new JButton("VOLVER AL MENU");
        btnVolverMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolverMenu.setBackground(new Color(105, 105, 105));
        btnVolverMenu.setForeground(Color.WHITE);
        btnVolverMenu.setFocusPainted(false);
        btnVolverMenu.setPreferredSize(new Dimension(200, 40));
        btnVolverMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolverMenu.setEnabled(false);
        btnVolverMenu.addActionListener(e -> volverAlMenu());
        panel.add(btnVolverMenu);

        return panel;
    }

    private void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = null;
            }
        }
        piezasCapturadas.clear();

        tablero[0][0] = crearPieza(0, 0, ColorPieza.NEGRO, TipoPieza.CARRO);
        tablero[0][1] = crearPieza(0, 1, ColorPieza.NEGRO, TipoPieza.CABALLO);
        tablero[0][2] = crearPieza(0, 2, ColorPieza.NEGRO, TipoPieza.ELEFANTE);
        tablero[0][3] = crearPieza(0, 3, ColorPieza.NEGRO, TipoPieza.OFICIAL);
        tablero[0][4] = crearPieza(0, 4, ColorPieza.NEGRO, TipoPieza.GENERAL);
        tablero[0][5] = crearPieza(0, 5, ColorPieza.NEGRO, TipoPieza.OFICIAL);
        tablero[0][6] = crearPieza(0, 6, ColorPieza.NEGRO, TipoPieza.ELEFANTE);
        tablero[0][7] = crearPieza(0, 7, ColorPieza.NEGRO, TipoPieza.CABALLO);
        tablero[0][8] = crearPieza(0, 8, ColorPieza.NEGRO, TipoPieza.CARRO);

        tablero[2][1] = crearPieza(2, 1, ColorPieza.NEGRO, TipoPieza.CANON);
        tablero[2][7] = crearPieza(2, 7, ColorPieza.NEGRO, TipoPieza.CANON);

        tablero[3][0] = crearPieza(3, 0, ColorPieza.NEGRO, TipoPieza.SOLDADO);
        tablero[3][2] = crearPieza(3, 2, ColorPieza.NEGRO, TipoPieza.SOLDADO);
        tablero[3][4] = crearPieza(3, 4, ColorPieza.NEGRO, TipoPieza.SOLDADO);
        tablero[3][6] = crearPieza(3, 6, ColorPieza.NEGRO, TipoPieza.SOLDADO);
        tablero[3][8] = crearPieza(3, 8, ColorPieza.NEGRO, TipoPieza.SOLDADO);

        tablero[9][0] = crearPieza(9, 0, ColorPieza.ROJO, TipoPieza.CARRO);
        tablero[9][1] = crearPieza(9, 1, ColorPieza.ROJO, TipoPieza.CABALLO);
        tablero[9][2] = crearPieza(9, 2, ColorPieza.ROJO, TipoPieza.ELEFANTE);
        tablero[9][3] = crearPieza(9, 3, ColorPieza.ROJO, TipoPieza.OFICIAL);
        tablero[9][4] = crearPieza(9, 4, ColorPieza.ROJO, TipoPieza.GENERAL);
        tablero[9][5] = crearPieza(9, 5, ColorPieza.ROJO, TipoPieza.OFICIAL);
        tablero[9][6] = crearPieza(9, 6, ColorPieza.ROJO, TipoPieza.ELEFANTE);
        tablero[9][7] = crearPieza(9, 7, ColorPieza.ROJO, TipoPieza.CABALLO);
        tablero[9][8] = crearPieza(9, 8, ColorPieza.ROJO, TipoPieza.CARRO);

        tablero[7][1] = crearPieza(7, 1, ColorPieza.ROJO, TipoPieza.CANON);
        tablero[7][7] = crearPieza(7, 7, ColorPieza.ROJO, TipoPieza.CANON);

        tablero[6][0] = crearPieza(6, 0, ColorPieza.ROJO, TipoPieza.SOLDADO);
        tablero[6][2] = crearPieza(6, 2, ColorPieza.ROJO, TipoPieza.SOLDADO);
        tablero[6][4] = crearPieza(6, 4, ColorPieza.ROJO, TipoPieza.SOLDADO);
        tablero[6][6] = crearPieza(6, 6, ColorPieza.ROJO, TipoPieza.SOLDADO);
        tablero[6][8] = crearPieza(6, 8, ColorPieza.ROJO, TipoPieza.SOLDADO);

        actualizarTableroGrafico();
        actualizarPanelCapturas();
    }

    private Pieza crearPieza(int fila, int col, ColorPieza color, TipoPieza tipo) {
        Pieza pieza;

        switch (tipo) {
            case GENERAL:
                pieza = new General(fila, col, color);
                break;
            case OFICIAL:
                pieza = new Oficial(fila, col, color);
                break;
            case ELEFANTE:
                pieza = new Elefante(fila, col, color);
                break;
            case CABALLO:
                pieza = new Caballo(fila, col, color);
                break;
            case CARRO:
                pieza = new Carro(fila, col, color);
                break;
            case CANON:
                pieza = new Canon(fila, col, color);
                break;
            case SOLDADO:
                pieza = new Soldado(fila, col, color);
                break;
            default:
                return null;
        }

        ImageIcon imagen = CargadorImagenes.cargarImagenPieza(tipo, color);
        if (imagen != null) {
            pieza.setImagen(imagen);
        }

        return pieza;
    }

    private void actualizarTableroGrafico() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                Pieza pieza = tablero[fila][col];

                if (pieza != null) {
                    if (pieza.getImagen() != null) {
                        casillas[fila][col].setIcon(pieza.getImagen());
                        casillas[fila][col].setText("");
                    } else {
                        casillas[fila][col].setIcon(null);
                        casillas[fila][col].setText(pieza.getNombreCorto());
                        casillas[fila][col].setForeground(pieza.isEsRoja()
                                ? new Color(178, 34, 34) : Color.BLACK);
                    }
                } else {
                    casillas[fila][col].setIcon(null);
                    casillas[fila][col].setText("");
                }

                Color colorOriginal = obtenerColorCasilla(fila, col);
                casillas[fila][col].setBackground(colorOriginal);
            }
        }
    }

    private void actualizarPanelCapturas() {
        panelCapturasRojo.removeAll();
        panelCapturasNegro.removeAll();

        for (Pieza pieza : piezasCapturadas) {
            JLabel lblPieza;

            if (pieza.getImagen() != null) {
                lblPieza = new JLabel(pieza.getImagen());
            } else {
                lblPieza = new JLabel(pieza.getNombreCorto());
                lblPieza.setFont(new Font("Arial", Font.BOLD, 12));
                lblPieza.setForeground(pieza.isEsRoja() ? new Color(178, 34, 34) : Color.BLACK);
            }

            lblPieza.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            lblPieza.setPreferredSize(new Dimension(35, 35));
            lblPieza.setHorizontalAlignment(SwingConstants.CENTER);

            if (pieza.isEsRoja()) {
                panelCapturasNegro.add(lblPieza);
            } else {
                panelCapturasRojo.add(lblPieza);
            }
        }

        panelCapturasRojo.revalidate();
        panelCapturasRojo.repaint();
        panelCapturasNegro.revalidate();
        panelCapturasNegro.repaint();
    }


    private boolean dejaPalaciosEnfrentados(int filaOrigen, int colOrigen,
            int filaDestino, int colDestino) {
        Pieza[][] copia = new Pieza[FILAS][COLUMNAS];
        for (int f = 0; f < FILAS; f++) {
            System.arraycopy(tablero[f], 0, copia[f], 0, COLUMNAS);
        }
        copia[filaDestino][colDestino] = copia[filaOrigen][colOrigen];
        copia[filaOrigen][colOrigen] = null;

        int[] posGeneralRojo = null;
        int[] posGeneralNegro = null;
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (copia[f][c] instanceof General) {
                    if (copia[f][c].isEsRoja()) {
                        posGeneralRojo = new int[]{f, c};
                    } else {
                        posGeneralNegro = new int[]{f, c};
                    }
                }
            }
        }

        if (posGeneralRojo == null || posGeneralNegro == null) {
            return false;
        }

        if (posGeneralRojo[1] != posGeneralNegro[1]) {
            return false;
        }

        int col = posGeneralRojo[1];
        int filaMin = Math.min(posGeneralRojo[0], posGeneralNegro[0]);
        int filaMax = Math.max(posGeneralRojo[0], posGeneralNegro[0]);

        for (int f = filaMin + 1; f < filaMax; f++) {
            if (copia[f][col] != null) {
                return false;
            }
        }
        return true; 
    }

    private void manejarClicCasilla(int fila, int col) {
        if (piezaSeleccionada == null) {
            Pieza pieza = tablero[fila][col];

            if (pieza != null && pieza.isEsRoja() == turnoRojo) {
                piezaSeleccionada = pieza;
                filaSeleccionada = fila;
                columnaSeleccionada = col;

                casillas[fila][col].setBackground(COLOR_SELECCION);
                mostrarMovimientosValidos(pieza);
            }
        } else {
            if (fila == filaSeleccionada && col == columnaSeleccionada) {
                deseleccionarPieza();
                return;
            }

            Pieza piezaDestino = tablero[fila][col];
            if (piezaDestino != null && piezaDestino.isEsRoja() == turnoRojo) {
                deseleccionarPieza();
                manejarClicCasilla(fila, col);
                return;
            }

            if (piezaSeleccionada.esMovimientoValido(fila, col, tablero)) {
                if (dejaPalaciosEnfrentados(filaSeleccionada, columnaSeleccionada, fila, col)) {
                    JOptionPane.showMessageDialog(this,
                            "Movimiento inválido: dejaría a los Generales enfrentados",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    deseleccionarPieza();
                } else {
                    moverPieza(filaSeleccionada, columnaSeleccionada, fila, col);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Movimiento inválido",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                deseleccionarPieza();
            }
        }
    }

    private void mostrarMovimientosValidos(Pieza pieza) {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (pieza.esMovimientoValido(f, c, tablero)
                        && !dejaPalaciosEnfrentados(filaSeleccionada, columnaSeleccionada, f, c)) {
                    Color colorOriginal = obtenerColorCasilla(f, c);
                    Color colorResaltado = mezclarColores(colorOriginal, COLOR_MOVIMIENTO_VALIDO);
                    casillas[f][c].setBackground(colorResaltado);
                }
            }
        }
    }

    private Color mezclarColores(Color base, Color overlay) {
        int alpha = overlay.getAlpha();
        float factor = alpha / 255.0f;

        int r = (int) (overlay.getRed() * factor + base.getRed() * (1 - factor));
        int g = (int) (overlay.getGreen() * factor + base.getGreen() * (1 - factor));
        int b = (int) (overlay.getBlue() * factor + base.getBlue() * (1 - factor));

        return new Color(r, g, b);
    }

    private void deseleccionarPieza() {
        piezaSeleccionada = null;
        actualizarTableroGrafico();
    }

    private void moverPieza(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        Pieza piezaCapturada = tablero[filaDestino][colDestino];
        Pieza piezaMovida = tablero[filaOrigen][colOrigen];

        tablero[filaDestino][colDestino] = piezaMovida;
        tablero[filaOrigen][colOrigen] = null;
        piezaMovida.setFila(filaDestino);
        piezaMovida.setColumna(colDestino);

        if (piezaCapturada != null) {
            piezasCapturadas.add(piezaCapturada);

            if (piezaCapturada instanceof General) {
                piezaSeleccionada = null;
                actualizarTableroGrafico();
                actualizarPanelCapturas();
                finalizarPartidaPorCaptura(piezaCapturada);
                return;
            }
        }

        piezaSeleccionada = null;

        turnoRojo = !turnoRojo;
        actualizarTurno();

        actualizarTableroGrafico();
        actualizarPanelCapturas();
    }

    private void finalizarPartidaPorCaptura(Pieza generalCapturado) {
        Player ganador = generalCapturado.isEsRoja() ? jugador2 : jugador1;
        Player perdedor = generalCapturado.isEsRoja() ? jugador1 : jugador2;

        ganador.agregarPuntos(Constantes.PUNTOS_VICTORIA);

        String log = ganador.getUsername() + " VENCIO A " + perdedor.getUsername()
                + ", FELICIDADES HAS GANADO " + Constantes.PUNTOS_VICTORIA + " PUNTOS";

        ganador.agregarLog(log);
        perdedor.agregarLog(log);

        JOptionPane.showMessageDialog(
                this,
                log,
                "Victoria",
                JOptionPane.INFORMATION_MESSAGE
        );

        btnRetirar.setEnabled(false);
        btnVolverMenu.setEnabled(true);

        lblJugador1.setText("ROJO: " + jugador1.getUsername() + " (" + jugador1.getPuntos() + " pts)");
        lblJugador2.setText("NEGRO: " + jugador2.getUsername() + " (" + jugador2.getPuntos() + " pts)");
    }

    private void manejarRetiro() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea retirarse?\nEl oponente ganará automáticamente.",
                "Confirmar Retiro",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            Player ganador = turnoRojo ? jugador2 : jugador1;
            Player perdedor = turnoRojo ? jugador1 : jugador2;

            ganador.agregarPuntos(Constantes.PUNTOS_VICTORIA);

            String log = perdedor.getUsername() + " SE HA RETIRADO, FELICIDADES "
                    + ganador.getUsername() + ", HAS GANADO " + Constantes.PUNTOS_VICTORIA + " PUNTOS";

            ganador.agregarLog(log);
            perdedor.agregarLog(log);

            JOptionPane.showMessageDialog(
                    this,
                    log,
                    "Victoria por Retiro",
                    JOptionPane.INFORMATION_MESSAGE
            );

            btnRetirar.setEnabled(false);
            btnVolverMenu.setEnabled(true);

            lblJugador1.setText("ROJO: " + jugador1.getUsername() + " (" + jugador1.getPuntos() + " pts)");
            lblJugador2.setText("NEGRO: " + jugador2.getUsername() + " (" + jugador2.getPuntos() + " pts)");
        }
    }

    private void volverAlMenu() {
        for (Component comp : panelContenido.getComponents()) {
            if (comp instanceof PanelMenuPrincipal) {
                ((PanelMenuPrincipal) comp).setJugadorActual(jugador1);
            }
        }
        cardLayout.show(panelContenido, "MENU");
    }

    public void iniciarPartida(Player j1, Player j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;
        this.turnoRojo = true;
        this.piezaSeleccionada = null;

        lblJugador1.setText("ROJO: " + j1.getUsername() + " (" + j1.getPuntos() + " pts)");
        lblJugador2.setText("NEGRO: " + j2.getUsername() + " (" + j2.getPuntos() + " pts)");

        actualizarTurno();
        inicializarTablero();

        btnRetirar.setEnabled(true);
        btnVolverMenu.setEnabled(false);
    }

    private void actualizarTurno() {
        if (turnoRojo) {
            lblTurno.setText("TURNO: ROJOS (" + jugador1.getUsername() + ")");
            lblTurno.setForeground(new Color(178, 34, 34));
        } else {
            lblTurno.setText("TURNO: NEGROS (" + jugador2.getUsername() + ")");
            lblTurno.setForeground(Color.BLACK);
        }
    }
}
