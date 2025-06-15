package edu.progavud.parcial2pa.Vista;

import edu.progavud.parcial2pa.control.ControlVentanaJugador;
import java.awt.*;
import javax.swing.*;

/**
 * Ventana principal de la interfaz gráfica del cliente. Permite al usuario
 * enviar mensajes públicos y acceder a la opción de ayuda.
 */
public class VistaJugador extends JFrame {

    private ControlVentanaJugador cVentana;

    private JTextArea panMostrar;
    private JTextField txtMensage;
    private JButton butEnviar;
    private JButton butSalir;


    private JMenuItem itemAyuda;
    private JLabel lblAdvertencia;
    private JLabel lblNombreUsuario;

    public VistaJugador(ControlVentanaJugador cVentana) {
        this.cVentana = cVentana;
        super("Jugador - Vista");

        // Eliminado: setIconImage con imagen
        initializeComponents();
        setupLayout();
        this.setVisible(true);
    }

    private void initializeComponents() {
        txtMensage = new JTextField(30);
        butEnviar = new JButton("Enviar");
        panMostrar = new JTextArea();

        panMostrar.setEditable(false);
        panMostrar.setOpaque(true);
        panMostrar.setBackground(new Color(230, 240, 255)); // Azul muy claro
        panMostrar.setForeground(Color.BLACK);
        panMostrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panMostrar.setLineWrap(true);
        panMostrar.setWrapStyleWord(true);

        butEnviar.setBackground(new Color(0, 123, 255)); // Azul brillante
        butEnviar.setForeground(Color.WHITE);
        butEnviar.setFocusPainted(false);
        butEnviar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        butSalir = new JButton("Salir");
        butSalir.setBackground(new Color(220, 53, 69)); // Rojo suave
        butSalir.setForeground(Color.WHITE);
        butSalir.setFocusPainted(false);
        butSalir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        butSalir.setActionCommand("CLIENTE_SALIR");

    }

    private void setupLayout() {
        JPanel panAbajo = new JPanel(new BorderLayout());
        panAbajo.setBackground(new Color(210, 230, 255));
        panAbajo.add(new JLabel("  Ingrese 2 coordenadas para elegir la pareja de cartas:"), BorderLayout.NORTH);
        panAbajo.add(txtMensage, BorderLayout.CENTER);
        panAbajo.add(butEnviar, BorderLayout.EAST);

        // Panel con botones Enviar y Salir
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(210, 230, 255));
        panelBotones.add(butEnviar);
        panelBotones.add(butSalir);

        panAbajo.add(panelBotones, BorderLayout.EAST);



        lblAdvertencia = new JLabel("");
        lblAdvertencia.setForeground(Color.RED);
        lblAdvertencia.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panAbajo.add(lblAdvertencia, BorderLayout.SOUTH);

        lblNombreUsuario = new JLabel("Jugador: ", SwingConstants.LEFT);
        lblNombreUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombreUsuario.setForeground(new Color(0, 102, 204)); // Azul oscuro

        // Eliminado el fondo con imagen
        JPanel fondoChat = new JPanel(new BorderLayout());
        fondoChat.setBackground(new Color(230, 240, 255)); // Fondo azul claro
        fondoChat.add(panMostrar);

        JScrollPane scrollChat = new JScrollPane(fondoChat);
        scrollChat.getViewport().setBackground(new Color(230, 240, 255));
        scrollChat.setBorder(BorderFactory.createEmptyBorder());

        JPanel panMain = new JPanel(new BorderLayout());
        panMain.add(lblNombreUsuario, BorderLayout.NORTH);
        panMain.add(scrollChat, BorderLayout.CENTER);
        panMain.add(panAbajo, BorderLayout.SOUTH);

        JMenuBar barraMenu = new JMenuBar();
        JMenu menuAyuda = new JMenu("Ayuda");
        this.itemAyuda = new JMenuItem("Ayuda");
        itemAyuda.setActionCommand("AYUDA");
        menuAyuda.add(itemAyuda);
        barraMenu.add(menuAyuda);

        setLayout(new BorderLayout());
        add(barraMenu, BorderLayout.NORTH);
        add(panMain, BorderLayout.CENTER);
        setSize(600, 400);
        setMinimumSize(new Dimension(500, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(200, 220, 245)); // Fondo general azul claro
    }
    
    public boolean confirmarSalida() {
    int opcion = JOptionPane.showConfirmDialog(
        this,
        "¿Estás seguro que deseas salir del juego?",
        "Confirmar salida",
        JOptionPane.YES_NO_OPTION
    );
    return opcion == JOptionPane.YES_OPTION;
    }


    public String nombreJugador() { //Reciclado de vCliente
        return JOptionPane.showInputDialog("Introducir Nombre :");
    }

    public String claveJugador() { //Reciclado de vClientes
        return JOptionPane.showInputDialog("Introducir Clave :");
    }

    public String numeroIP() { //Reciclado de vCliente
        return JOptionPane.showInputDialog("Introducir IP_SERVER :", "localhost");
    }

    public void mostrarMensajeDesconectado() {
        JOptionPane.showMessageDialog(null, "El usuario y/o la clave son incorrectas por lo que ha sido desconectado");
    }

    public JTextArea getPanMostrar() {
        return panMostrar;
    }

    public void setPanMostrar(JTextArea panMostrar) {
        this.panMostrar = panMostrar;
    }

    public JTextField getTxtMensage() {
        return txtMensage;
    }

    public void setTxtMensage(JTextField txtMensage) {
        this.txtMensage = txtMensage;
    }

    public JButton getBtnEnviar() {
        return butEnviar;
    }
    
    public JButton getBtnSalir() {
    return butSalir;
    }


    public void setBtnEnviar(JButton butEnviar) {
        this.butEnviar = butEnviar;
    }

    public void setNombreJugador(String nombre) {
        lblNombreUsuario.setText("Jugador: " + nombre);
    }

    public void mostrarMsg(String msg) {
        this.panMostrar.append(msg + "\n");
    }

    public String getMensaje() {
        return txtMensage.getText().trim();
    }

    public JMenuItem getItemAyuda() {
        return itemAyuda;
    }

    public JLabel getLblAdvertencia() {
        return lblAdvertencia;
    }
}
