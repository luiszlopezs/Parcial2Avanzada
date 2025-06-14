/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.modelo;

import java.net.ServerSocket;



public class ServidorVO {

// Socket de conexión principal
    private ServerSocket serv;

// Segundo socket de conexión (mensajes privados u otros propósitos)
    private ServerSocket serv2;
    
    private int port1 ;
    private int port2 ;

// Indica si el servidor está escuchando conexiones
    boolean listening = true;

    /**
     * Constructor por defecto de la clase Servidor.
     *
     * Inicializa las listas de groserías y sus reemplazos, y establece los
     * sockets como {@code null} hasta que se configuren.
     */
    public ServidorVO() {

        serv = null;//para comunicacion
        serv2 = null;//para enviar mensajes    
    }

    /**
     * Devuelve el socket principal del servidor.
     *
     * @return El {@link ServerSocket} principal.
     */
    public ServerSocket getServ() {
        return serv;
    }

    /**
     * Asigna el socket principal del servidor.
     *
     * @param serv El {@link ServerSocket} que se desea asignar.
     */
    public void setServ(ServerSocket serv) {
        this.serv = serv;
    }

    /**
     * Devuelve el segundo socket del servidor (por ejemplo, para mensajes
     * privados).
     *
     * @return El segundo {@link ServerSocket}.
     */
    public ServerSocket getServ2() {
        return serv2;
    }
    /**
     * Asigna el segundo socket del servidor.
     *
     * @param serv2 El {@link ServerSocket} secundario a asignar.
     */
    public void setServ2(ServerSocket serv2) {
        this.serv2 = serv2;
    }

    /**
     * Indica si el servidor está actualmente escuchando conexiones.
     *
     * @return {@code true} si el servidor está escuchando; {@code false} en
     * caso contrario.
     */
    public boolean isListening() {
        return listening;
    }

    /**
     * Establece si el servidor debe seguir escuchando conexiones.
     *
     * @param listening {@code true} para mantener activo el servidor,
     * {@code false} para detenerlo.
     */
    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public int getPort1() {
        return port1;
    }

    public void setPort1(int port1) {
        this.port1 = port1;
    }

    public int getPort2() {
        return port2;
    }

    public void setPort2(int port2) {
        this.port2 = port2;
    }
    
    
    
}