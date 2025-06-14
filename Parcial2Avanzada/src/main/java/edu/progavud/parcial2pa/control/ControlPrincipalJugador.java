/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

/**
 * Controlador principal del cliente. Se encarga de inicializar y coordinar los
 * subcontroladores como la ventana del cliente y la lógica de conexión del
 * cliente.
 *
 * @author hailen
 */
public class ControlPrincipalJugador {

    // Controlador de la ventana del cliente (interfaz gráfica)
    private ControlVentanaJugador cVentana;

// Controlador de la lógica de conexión del cliente
    private ControlJugador cJugador;

    /**
     * Constructor de ControlPrincipalCliente.
     *
     * Inicializa el controlador de la ventana y el controlador del cliente,
     * solicita la lista de usuarios activos y muestra la interfaz gráfica.
     *
     * @throws IOException Si ocurre un error al establecer la conexión con el
     * servidor.
     */
    public ControlPrincipalJugador() throws IOException {

        cVentana = new ControlVentanaJugador(this);
        cJugador = new ControlJugador(this);
//        cVentana.getvJugador().ponerActivos(cJugador.pedirUsuarios());
        cVentana.getvJugador().setVisible(true);
    }

    /**
     * Devuelve el controlador de la ventana del cliente.
     *
     * @return El controlador de la ventana del cliente.
     */
    public ControlVentanaJugador getcVentana() {
        return cVentana;
    }

    /**
     * Asigna el controlador de la ventana del cliente.
     *
     * @param cVentana El nuevo controlador de ventana a asignar.
     */
    public void setcVentana(ControlVentanaJugador cVentana) {
        this.cVentana = cVentana;
    }

    /**
     * Devuelve el controlador del cliente.
     *
     * @return El controlador del cliente.
     */
    public ControlJugador getcCliente() {
        return cJugador;
    }

    /**
     * Asigna el controlador del cliente.
     *
     * @param cCliente El nuevo controlador del cliente a asignar.
     */
    public void setcCliente(ControlJugador cCliente) {
        this.cJugador = cCliente;
    }

    /**
     * Envía un mensaje privado a un usuario específico utilizando el
     * controlador del cliente.
     *
     * @param amigo Nombre del usuario destinatario.
     * @param contenido Contenido del mensaje a enviar.
     */
//    public void enviarMensajePrivado(String amigo, String contenido) {
//        try {
//            cJugador.enviarMensajePrivado(amigo, contenido);
//        } catch (IOException ex) {
//            cVentana.getvJugador().mostrarMensajeDesconectado();
//
//        }
//    }
    /**
     * Envía un mensaje público a todos los usuarios conectados utilizando el
     * controlador del cliente.
     *
     * @param contenido Contenido del mensaje a enviar.
     */
    public void enviarMensajePublico(String contenido) {

        try {
            cJugador.enviarIntento(contenido);
        } catch (IOException ex) {
            cVentana.getvJugador().mostrarMensajeDesconectado();

        }

    }

    public void enviarInformacionJugador(String nombre, String contraseña) {
        cJugador.enviarInformacionJugador(nombre, contraseña);
    }

}
