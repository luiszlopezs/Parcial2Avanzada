/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.Vista.VistaJugador;
import edu.progavud.parcial2pa.modelo.Jugador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de la interfaz gráfica del cliente.Se encarga de gestionar los
 eventos de las vistas {@link VistaJugador}, {@link VistaPrivada} y
 {@link VistaAyuda}, así como de coordinar acciones entre la vista y el
 controlador principal del cliente.
 *
 * @author hailen
 */
public class ControlVentanaJugador implements ActionListener {

    // Controlador principal del cliente
    private ControlPrincipalJugador cPrinc;

// Ventana principal del cliente (interfaz de chat general)
    private VistaJugador vJugador;

// Ventana de chat privado con un usuario
//    private VistaPrivada vPrivada;

// Nombre del cliente
    private String nombre;
    
// Contraseña del cliente
    private String clave;
   
// Dirección IP del servidor
    private ArrayList<String> datosPasar;

// Mapa para ventanas privadas por cada amigo
//    private Map<String, VistaPrivada> chatsPrivados = new HashMap<>();

    /**
     * Constructor de ControlVentanaCliente.
     *
     * Inicializa las vistas del cliente (general y privada), obtiene el nombre
     * de usuario e IP ingresados por el usuario, y los almacena para uso
     * posterior.
     *
     * @param cPrinc Controlador principal del cliente.
     */
    public ControlVentanaJugador (ControlPrincipalJugador cPrinc) throws SQLException {
        this.cPrinc = cPrinc;
        cargarVistaJugador();
//        cargarVistaPrivada();
        datosPasar = cPrinc.inicializarPuertosDesdeProps(vJugador.rutaJfileChooserPorts());
        nombre = vJugador.nombreJugador(); //Reciclado de vCliente
        vJugador.setNombreJugador(nombre); //Reciclado de vCliente, recibe del JOptionPane el nombre y contraseña
        clave = vJugador.claveJugador();
        
//        cPrinc.enviarInformacionJugador(nombre, contraseña); //Se envía la informacion del jugador
//        ip = vJugador.numeroIP(); debe venir del properties

    }

    /**
     * Maneja los eventos de la interfaz gráfica del cliente.
     *
     * <p>
     * Dependiendo del comando recibido, realiza una de las siguientes
     * acciones:</p>
     * <ul>
     * <li><b>"CLIENTE_ENVIAR"</b>: Envía un mensaje público a todos los
     * usuarios.</li>
     * <li><b>"CLIENTE_PRIVADO"</b>: Abre la ventana de chat privado con el
     * usuario seleccionado.</li>
     * <li><b>"PRIVADO_ENVIAR"</b>: Envía un mensaje privado al destinatario
     * desde la ventana privada.</li>
     * </ul>
     *
     * @param e Evento de acción generado por un componente de la vista.
     */
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
//        String destinatario = "";
        switch (comando) {
            case "CLIENTE_ENVIAR":
                String mensaje = vJugador.getMensaje();
                cPrinc.enviarMensajePublico(mensaje);
                vJugador.getTxtMensage().setText("");
                System.out.println(mensaje);
                break;
        }
    }

    /**
     * Inicializa y configura la ventana principal del cliente.
     *
     * <p>
     * Asocia los botones de enviar mensaje y chat privado con sus respectivos
     * comandos de acción y los vincula a este controlador como listener.</p>
     */
    public void cargarVistaJugador() {
        vJugador = new VistaJugador(this);

        vJugador.getBtnEnviar().setActionCommand("CLIENTE_ENVIAR");
        vJugador.getBtnEnviar().addActionListener(this);

//        vJugador.getBtnPrivado().setActionCommand("CLIENTE_PRIVADO");
//        vJugador.getBtnPrivado().addActionListener(this);


    }
    

    /**
     * Inicializa y configura la ventana de chat privado.
     *
     * <p>
     * Asocia el botón de enviar mensaje con el comando "PRIVADO_ENVIAR" y lo
     * vincula a este controlador como listener.</p>
     */
//    public void cargarVistaPrivada() {
//        vPrivada = new VistaPrivada(this);
//
//        vPrivada.getBtnEnviar().setActionCommand("PRIVADO_ENVIAR");
//        vPrivada.getBtnEnviar().addActionListener(this);
//    }
//
//    /**
//     * Actualiza la lista de usuarios conectados mostrada en la ventana
//     * principal del cliente.
//     *
//     * @param usuarios Arreglo con los nombres de los usuarios actualmente
//     * conectados.
//     */
//    public void actualizarListaUsuarios(String[] usuarios) {
//        vCliente.actualizarListaUsuarios(usuarios);
//    }
//
//    /**
//     * Devuelve la ventana de ayuda del cliente.
//     *
//     * @return Objeto {@link VistaAyuda} asociado al cliente.
//     */
//    public VistaAyuda getvAyuda() {
//        return vAyuda;
//    }
//
//    /**
//     * Asigna la ventana de ayuda del cliente.
//     *
//     * @param vAyuda Objeto {@link VistaAyuda} a asignar.
//     */
//    public void setvAyuda(VistaAyuda vAyuda) {
//        this.vAyuda = vAyuda;
//    }
//
//    /**
//     * Devuelve la ventana principal del cliente.
//     *
//     * @return Objeto {@link VistaJugador} actual.
//     */
//    public VistaJugador getvCliente() {
//        return vCliente;
//    }
//
//    /**
//     * Asigna la ventana principal del cliente.
//     *
//     * @param vCliente Objeto {@link VistaJugador} a asignar.
//     */
//    public void setvCliente(VistaJugador vCliente) {
//        this.vCliente = vCliente;
//    }
//
//    /**
//     * Devuelve la ventana de chat privado.
//     *
//     * @return Objeto {@link VistaPrivada} asociado.
//     */
//    public VistaPrivada getvPrivada() {
//        return vPrivada;
//    }
//
//    /**
//     * Asigna la ventana de chat privado.
//     *
//     * @param vPrivada Objeto {@link VistaPrivada} a asignar.
//     */
//    public void setvPrivada(VistaPrivada vPrivada) {
//        this.vPrivada = vPrivada;
//    }

    /**
     * Muestra un mensaje recibido de un usuario amigo en la ventana privada.
     *
     * @param amigo Nombre del amigo que envió el mensaje.
     * @param msg Contenido del mensaje recibido.
     */
//    public void mensageAmigo(String amigo, String msg) {
//        VistaPrivada ventanaPrivada = chatsPrivados.get(amigo);
//
//        if (ventanaPrivada == null) {
//            ventanaPrivada = new VistaPrivada(this);
//            ventanaPrivada.setAmigo(amigo);
//            ventanaPrivada.getBtnEnviar().setActionCommand("PRIVADO_ENVIAR:" + amigo);
//            ventanaPrivada.getBtnEnviar().addActionListener(this);
//            chatsPrivados.put(amigo, ventanaPrivada);
//        }
//
//        ventanaPrivada.mostrarMsg(msg);
//        ventanaPrivada.mostrar();
//    }

    /**
     * Devuelve el nombre del usuario cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del cliente.
     *
     * @param nombre Nombre que se desea asignar al cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la dirección IP del servidor ingresada por el cliente.
     *
     * @return Dirección IP como cadena de texto.
     */
    

    public VistaJugador getvJugador() {
        return vJugador;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public ArrayList<String> getDatosPasar() {
        return datosPasar;
    }

    public void setDatosPasar(ArrayList<String> datosPasar) {
        this.datosPasar = datosPasar;
    }


    
    
    
    

}
