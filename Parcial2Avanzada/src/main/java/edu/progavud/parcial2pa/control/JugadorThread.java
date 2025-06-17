/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 *
 * Hilo que representa la conexión con un cliente. Se encarga de manejar la
 * comunicación entrante desde el cliente en una aplicación cliente-servidor.
 *
 * @author hailen
 */
public class JugadorThread extends Thread {

    DataInputStream entrada;
    private ControlJugador cJugador;
//    public static Vector<ClienteThread> clientesActivos = new Vector(); //c

    /**
     * Crea un nuevo hilo para manejar la conexión con un cliente.
     *
     * @param entrada Flujo de datos de entrada desde el cliente.
     * @param cCliente Referencia al controlador que gestiona la lógica del
     * cliente.
     * @throws IOException Si ocurre un error al inicializar el flujo de
     * entrada.
     */
    public JugadorThread(DataInputStream entrada, ControlJugador cJugador) throws IOException {
        this.entrada = entrada;
        this.cJugador = cJugador;
    }

    /**
     * Ejecuta el hilo que escucha continuamente los mensajes enviados por el
     * servidor. Según el valor recibido, se realizan diferentes acciones:
     * <ul>
     * <li><b>1:</b> Muestra un mensaje enviado desde el servidor.</li>
     * <li><b>2:</b> Agrega un nuevo usuario a la interfaz.</li>
     * <li><b>3:</b> Muestra un mensaje privado de un usuario amigo.</li>
     * <li><b>4:</b> Actualiza la lista de usuarios conectados.</li>
     * </ul>
     *
     * Si ocurre una excepción de entrada/salida, se asume que la conexión se ha
     * perdido y se termina el hilo.
     */
    public void run() {
        String menser = "", amigo = "";
        int opcion = 0;
        while (true) {
            try {
                opcion = entrada.readInt();
                switch (opcion) {
                    case 0: //usuario y contraseña

                    case 1://mensage enviado
                        menser = entrada.readUTF();
                        cJugador.getcPrinc().getcVentana().getvJugador().mostrarMsg(menser);
                        break;

                    case 3://mensage de amigo
                        amigo = entrada.readUTF();
                        menser = entrada.readUTF();
                        if (menser.equalsIgnoreCase("inhabilitar")) {
                            cJugador.getcPrinc().getcVentana().getvJugador().getTxtMensage().setEnabled(false);
                            cJugador.getcPrinc().getcVentana().getvJugador().getBtnEnviar().setEnabled(false);
                        } else if (menser.equalsIgnoreCase("habilitar")) {
                            cJugador.getcPrinc().getcVentana().getvJugador().getTxtMensage().setEnabled(true);
                            cJugador.getcPrinc().getcVentana().getvJugador().getBtnEnviar().setEnabled(true);
                        } else {
                            cJugador.getcPrinc().getcVentana().getvJugador().mostrarMsg(menser);
                        }

                        break;
//                    

                }
            } catch (IOException e) {
                break;
            }
        }

    }

}
