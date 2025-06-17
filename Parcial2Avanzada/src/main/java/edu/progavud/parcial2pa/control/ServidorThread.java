package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.Jugador;
import edu.progavud.parcial2pa.modelo.JugadorVO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class ServidorThread extends Thread {

    Socket scli = null;
    Socket scli2 = null;
    DataInputStream entrada = null;
    DataOutputStream salida = null;
    DataOutputStream salida2 = null;

    private volatile boolean partidaIniciada = false;

    String nameUser;
    String clave;


    private JugadorVO jugadorVO;


    private JugadorVO jugadorVO;
    private ControlServidor cServidor;

    public ServidorThread(Socket scliente, Socket scliente2, ControlServidor cServidor) {
        scli = scliente;
        scli2 = scliente2;
        this.cServidor = cServidor;
        nameUser = "";
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String name) {
        nameUser = name;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    public JugadorVO getJugadorVO() {
        return jugadorVO;
    }

    public void setJugadorVO(JugadorVO jugadorVO) {
        this.jugadorVO = jugadorVO;
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

        int opcion = 0;
        String jugador = "", mencli = "";
        boolean estaAgregado = false;
        System.out.println("holaaaa, paso por qquiii");

        while (true) {
            try {
                if (cServidor.verificarUsuario(this.nameUser, this.clave) == null) {
                    break;
                }

                if (!estaAgregado) {
                    jugadorVO = cServidor.verificarUsuario(this.nameUser, this.clave);
                    System.out.println(jugadorVO.getNombre() + jugadorVO.getClave() + "ed-------------------------");
                    ControlServidor.clientesActivos.add(this);
                    cServidor.getcPrinc().getcVentana().habilitarBotonesAlIniciar(nameUser);
                    cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Ingresó un nuevo Jugador: " + this.nameUser);
                    
                    this.enviaMsg(ControlServidor.clientesActivos.get(ControlServidor.clientesActivos.size()-1).getNameUser(), "Turno Asignado ->" + ControlServidor.clientesActivos.size());
                    estaAgregado = true;
                    cServidor.getcPrinc().getcVentana().activarPartidaBasica();
                }



            } catch (SQLException ex) {
                Logger.getLogger(ServidorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!estaAgregado) {
                try {
                    jugadorVO = cServidor.verificarUsuario(this.nameUser, this.clave);
                } catch (SQLException ex) {
                    Logger.getLogger(ServidorThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(jugadorVO.getNombre() + jugadorVO.getClave() + "ed-------------------------");

                ControlServidor.clientesActivos.add(this);

                jugadorVO.setIdJugador(ControlServidor.clientesActivos.size()); //Añadir id al jugador

                cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Ingresó un nuevo Jugador: " + this.nameUser);
                estaAgregado = true;
                cServidor.getcPrinc().getcVentana().activarPartidaBasica();

                // Inicializar la vista de intentos para este jugador
                // Inicialmente todos los jugadores deben esperar hasta que se inicie el juego
                enviarControlTurno(false);
            }

            try {
                opcion = entrada.readInt();

                switch (opcion) {
                    case 1: // Mensaje/Coordenadas del jugador
                        // Verificar si es el turno del jugador ANTES de procesar

                        mencli = entrada.readUTF();
                        enviaMsg(mencli);
                        cServidor.getcPrinc().getcVentana().getvServidor().mostrar("------------------------- \n Coordenadas de:" + ControlServidor.clientesActivos.get(cServidor.getTurnoActual()-1).getJugadorVO().getNombre()+ " -> Turno " + cServidor.getTurnoActual() + " \n" + mencli);

                        break;
                    case 3: // Mensaje privado
                        jugador = entrada.readUTF();
                        mencli = entrada.readUTF();
                        enviaMsg(jugador, mencli);
                        cServidor.getcPrinc().getcVentana().getvServidor().mostrar(mencli);

                        break;
                }
            } catch (IOException e) {
                break;
            }
        }

        enviaMsg(this.getNameUser() + " se ha desconectado del juego.");
        cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Se removio un usuario");

//        
        ControlServidor.clientesActivos.removeElement(this);
//        if (ControlServidor.clientesActivos.size() < 2) {
//            cServidor.getcPrinc().getcVentana().getvServidor().getBtnIniciarJuego().setEnabled(false);
//        } else {
//            cServidor.getcPrinc().getcVentana().getvServidor().getBtnIniciarJuego().setEnabled(false);
//        }
        System.out.println(ControlServidor.clientesActivos);

        try {
            cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Se desconecto un usuario");
            scli.close();
        } catch (Exception et) {
            cServidor.getcPrinc().getcVentana().getvServidor().mostrar("no se puede cerrar el socket");
        }
    }

    public void enviaMsg(String mencli2) {
        ServidorThread user = null;
        for (int i = 0; i < ControlServidor.clientesActivos.size(); i++) {
            //cServidor.getcPrinc().getcVentana().getvServidor().mostrar("MENSAJE DEVUELTO:" + mencli2);
            try {
                user = ControlServidor.clientesActivos.get(i);
                user.salida2.writeInt(1);//opcion de mensage 
                user.salida2.writeUTF(mencli2);
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
    public void enviaMsg(String jugador, String mencli) {

        ServidorThread user = null;
        for (int i = 0; i < ControlServidor.clientesActivos.size(); i++) {
            cServidor.getcPrinc().getcVentana().getvServidor().mostrar("MENSAJE DEVUELTO:" + mencli2);
            try {
                user = ControlServidor.clientesActivos.get(i);
                user.salida2.writeInt(1);//opcion de mensage 
                user.salida2.writeUTF( mencli2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void enviaMsg(String jugador, String mencli) {
        for (ServidorThread user : ControlServidor.clientesActivos) {
            try {
                if (user.nameUser.equals(jugador)) {
                    user.salida2.writeInt(3); // opción mensaje privado
                    user.salida2.writeUTF(this.getNameUser());

                    user.salida2.writeUTF(mencli);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Enviar control de turno (habilitar/deshabilitar botones)
    public void enviarControlTurno(boolean esMiTurno) {
        try {
            salida2.writeInt(5); // código 5 = control de turno
            if (esMiTurno) {
                salida2.writeUTF("Es tu turno");
            } else {
                salida2.writeUTF("Espera tu turno");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Enviar mensaje de error
    public void enviarMensajeError(String mensaje) {
        try {
            salida2.writeInt(4); // código 4 = mensaje de error
            salida2.writeUTF(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Enviar mensaje normal del servidor
    public void enviarDesdeServidor(String mensaje) {
        try {
            salida2.writeInt(1); // código 1 = mensaje normal
            salida2.writeUTF("SERVIDOR -> " + mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Verificar si es el turno de este jugador
    private boolean esMiTurno() {
        return cServidor.getTurno() == jugadorVO.getIdJugador();
    }

    // Getters y Setters
    public DataOutputStream getSalida() {
        return salida;
    }

    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    public DataOutputStream getSalida2() {
        return salida2;
    }

    public void setSalida2(DataOutputStream salida2) {
        this.salida2 = salida2;
    }



}
