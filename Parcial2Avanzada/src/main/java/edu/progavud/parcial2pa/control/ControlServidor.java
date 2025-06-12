/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.ServidorVO;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

/**
 * Controlador encargado de manejar la lógica principal del servidor. Se encarga
 * de gestionar las conexiones entrantes, coordinar la lógica del modelo
 * {@link Servidor}, y comunicarse con el controlador principal del servidor.
 *
 * @author hailen
 */
public class ControlServidor {

    // Referencia al controlador principal del servidor
    private ControlPrincipalServidor cPrinc;

// Objeto que representa la lógica del servidor (modelo)
    private ServidorVO servidorVO;

// Hilo encargado de atender a los clientes que se conectan
    private ServidorThread servidorThread;

// Generador de números aleatorios para funcionalidades del servidor
    private Random random;

    /**
     * Constructor de ControlServidor.
     *
     * Inicializa el controlador del servidor, el modelo {@link Servidor}, y el
     * generador de números aleatorios.
     *
     * @param cPrinc Referencia al controlador principal del servidor.
     */
    public ControlServidor(ControlPrincipalServidor cPrinc) {
        this.cPrinc = cPrinc;
        random = new Random();
        servidorVO = new ServidorVO();

    }

    /**
     * Inicia el servidor y queda a la espera de conexiones de usuarios.
     *
     * <p>
     * Realiza las siguientes acciones:
     * <ul>
     * <li>Inicializa las listas de palabras restringidas desde un archivo de
     * configuración.</li>
     * <li>Establece los sockets del servidor en los puertos 8081 y 8082.</li>
     * <li>Muestra mensajes en la interfaz del servidor.</li>
     * <li>Espera conexiones de clientes y crea un hilo {@link ServidorThread}
     * por cada cliente que se conecta.</li>
     * </ul>
     *
     * Si ocurre un error de entrada/salida, se muestra el mensaje
     * correspondiente en la interfaz del servidor.
     */
    public void runServer() {
        try {
                       
            servidorVO.setServ(new ServerSocket(8081));
            servidorVO.setServ2(new ServerSocket(8082));
            cPrinc.getcVentana().getvServidor().mostrar("::Servidor activo::");
            while (servidorVO.isListening()) {
                Socket sock = null, sock2 = null;
                try {
                    //muestra un mensaje en la vista del server
                    cPrinc.getcVentana().getvServidor().mostrar("Esperando Jugadores");
                    sock = servidorVO.getServ().accept();
                    sock2 = servidorVO.getServ2().accept();
                } catch (IOException e) {
                    //muestra un mensaje en la vista del server
                    cPrinc.getcVentana().getvServidor().mostrar("Accept failed: " + servidorVO.getServ() + ", " + e.getMessage());
                    continue;
                }
                ServidorThread user = new ServidorThread(sock, sock2, this);
                user.start();

            }

        } catch (IOException e) {
            //muestra un mensaje en la vista del server
            cPrinc.getcVentana().getvServidor().mostrar("error :" + e);
        }

    }



    /**
     * Revisa un mensaje y reemplaza automáticamente cualquier palabra
     * considerada grosería por una palabra alternativa tomada aleatoriamente de
     * una lista de reemplazos.
     *
     * @param mensaje El mensaje original enviado por el usuario.
     * @return El mensaje filtrado, con las groserías reemplazadas.
     */


    /**
     * Inicializa las listas de palabras ofensivas y sus reemplazos desde un
     * archivo de propiedades.
     *
     * <p>
     * Busca claves con formato <b>"groseria1", "groseria2", ...</b> y
     * <b>"reemplazo1", "reemplazo2", ...</b> hasta que no encuentre más. Los
     * valores encontrados se agregan a las listas correspondientes del modelo
     * {@link Servidor}.
     *
     * @param props Archivo de propiedades cargado previamente.
     */
    public void inicializarDesdeProperties(Properties props) {
        
    }

    /**
     * Devuelve la referencia al controlador principal del servidor.
     *
     * @return El controlador principal del servidor.
     */
    public ControlPrincipalServidor getcPrinc() {
        return cPrinc;
    }

    /**
     * Devuelve el objeto del modelo {@link Servidor} que contiene la lógica del
     * servidor.
     *
     * @return El modelo del servidor.
     */
    public ServidorVO getServidorVO() {
        return servidorVO;
    }

    /**
     * Devuelve el hilo actual del servidor encargado de manejar la conexión con
     * un cliente.
     *
     * @return El hilo del servidor.
     */
    public ServidorThread getServidorThread() {
        return servidorThread;
    }

}
