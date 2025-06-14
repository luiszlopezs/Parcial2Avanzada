/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

/**
 * Hilo que maneja la comunicación con un cliente conectado al servidor.
 *
 * <p>
 * Este hilo recibe mensajes del cliente, los procesa (por ejemplo, filtrando
 * groserías), y los reenvía a otros clientes conectados si es necesario.</p>
 *
 * También mantiene una lista estática de clientes activos en el servidor.
 *
 * @author hailen
 */
import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Administrador
 */
public class ServidorThread extends Thread {

    // Socket principal para la comunicación con el cliente
    Socket scli = null;

// Segundo socket para comunicación adicional (por ejemplo, mensajes privados)
    Socket scli2 = null;

// Flujo de entrada de datos desde el cliente
    DataInputStream entrada = null;

// Flujo de salida de datos hacia el cliente
    DataOutputStream salida = null;

// Segundo flujo de salida (por ejemplo, para mensajes privados)
    DataOutputStream salida2 = null;

    private volatile boolean partidaIniciada = false;

// Lista estática de hilos de clientes activos
// Nombre de usuario del cliente conectado
    String nameUser;
    String clave;

// Referencia al controlador del servidor
    private ControlServidor cServidor;

    /**
     * Constructor de ServidorThread.
     *
     * Inicializa los sockets de comunicación con el cliente, registra este hilo
     * en la lista de clientes activos y notifica en la vista del servidor que
     * un cliente se ha conectado.
     *
     * @param scliente Socket principal de conexión con el cliente.
     * @param scliente2 Segundo socket (por ejemplo, para mensajes privados).
     * @param cServidor Controlador del servidor que gestiona la lógica del
     * sistema.
     */
    public ServidorThread(Socket scliente, Socket scliente2, ControlServidor cServidor) {
        scli = scliente;
        scli2 = scliente2;
        this.cServidor = cServidor;
        nameUser = "";

    }

    /**
     * Devuelve el nombre de usuario asociado a este cliente.
     *
     * @return Nombre del usuario.
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * Asigna el nombre de usuario a este cliente.
     *
     * @param name Nombre que se desea asignar al cliente.
     */
    public void setNameUser(String name) {
        nameUser = name;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Ejecuta el hilo que gestiona la comunicación con un cliente.
     *
     * <p>
     * Este método realiza lo siguiente:</p>
     * <ul>
     * <li>Inicializa los flujos de entrada y salida.</li>
     * <li>Lee el nombre del usuario conectado.</li>
     * <li>Escucha continuamente los comandos del cliente:</li>
     * <ul>
     * <li><b>1:</b> Recibe un mensaje público y lo reenvía a todos los clientes
     * conectados.</li>
     * <li><b>2:</b> Envía al cliente la lista actual de usuarios
     * conectados.</li>
     * <li><b>3:</b> Recibe un mensaje privado y lo envía al destinatario
     * correspondiente.</li>
     * </ul>
     * <li>Cuando el cliente se desconecta, lo elimina de la lista de clientes
     * activos y actualiza la lista para los demás clientes.</li>
     * </ul>
     */
    public void setPartidaIniciada(boolean estado) {
        this.partidaIniciada = estado;
    }

    public void run() {
        cServidor.getcPrinc().getcVentana().getvServidor().mostrar(".::Esperando Mensajes :");

        try {
            entrada = new DataInputStream(scli.getInputStream());
            salida = new DataOutputStream(scli.getOutputStream());
            salida2 = new DataOutputStream(scli2.getOutputStream());
            this.setNameUser(entrada.readUTF());
            this.setClave(entrada.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int opcion = 0, numUsers = 0;
        String jugador = "", mencli = "";
        boolean estaAgregado = false;
        System.out.println("holaaaa, paso por qquiii");
        while (true) {

//            try {
//                if (!cServidor.verificarUsuario(this.nameUser, this.clave)) {
//                    break;
//                }
                if (!estaAgregado) {
                    ControlServidor.clientesActivos.add(this);
                    cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Ingresó un nuevo Jugador: " + this.nameUser);
                    estaAgregado = true;
                    cServidor.getcPrinc().getcVentana().activarPartidaBasica();
                }
                
//            } catch (SQLException ex) {
//                System.getLogger(ServidorThread.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//            }
            String filtrado = "";
            try {
                opcion = entrada.readInt();
                switch (opcion) {

                    case 1: // Mensaje a todos
                        mencli = entrada.readUTF();

                        enviaMsg(mencli);
                        cServidor.getcPrinc().getcVentana().getvServidor().mostrar("mensaje recibido " + mencli);

                        break;

                }
            } catch (IOException e) {
                break;
            }
        }

        enviaMsg(this.getNameUser() + " se ha desconectado del chat.");
        cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Se removio un usuario");
        ControlServidor.clientesActivos.removeElement(this);

        try {
            cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Se desconecto un usuario");
            scli.close();
        } catch (Exception et) {
            cServidor.getcPrinc().getcVentana().getvServidor().mostrar("no se puede cerrar el socket");
        }
    }

    /**
     * Envía un mensaje público a todos los clientes conectados.
     *
     * <p>
     * El mensaje se envía a través del segundo canal de salida de cada cliente
     * (utilizando {@code salida2}), precedido por la opción 1 para indicar que
     * se trata de un mensaje general.</p>
     *
     * @param mencli2 El contenido del mensaje a enviar.
     */
    public void enviaMsg(String mencli2) {
        ServidorThread user = null;
        for (int i = 0; i < ControlServidor.clientesActivos.size(); i++) {
            cServidor.getcPrinc().getcVentana().getvServidor().mostrar("MENSAJE DEVUELTO:" + mencli2);
            try {
                user = ControlServidor.clientesActivos.get(i);
                user.salida2.writeInt(1);//opcion de mensage 
                user.salida2.writeUTF("" + this.getNameUser() + " ->" + mencli2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Envía un mensaje privado a un cliente específico.
     *
     * <p>
     * Busca al cliente con el nombre indicado y le envía el mensaje a través de
     * {@code salida2}, usando el código <b>3</b> para indicar que es un mensaje
     * privado.</p>
     *
     * @param amigo Nombre del destinatario del mensaje.
     * @param mencli Contenido del mensaje privado.
     */
    private void enviaMsg(String jugador, String mencli) {
        ServidorThread user = null;
        for (int i = 0; i < ControlServidor.clientesActivos.size(); i++) {
            try {
                user = ControlServidor.clientesActivos.get(i);
                if (user.nameUser.equals(jugador)) {
                    user.salida2.writeInt(3);//opcion de mensaje amigo   
                    user.salida2.writeUTF(this.getNameUser());
                    user.salida2.writeUTF("" + this.getNameUser() + ">" + mencli);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Devuelve la lista de hilos de clientes actualmente conectados al
     * servidor.
     *
     * @return Un vector con las instancias de {@link ServidorThread} activas.
     */
}
