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
    private int idJugador; // Jugador 1, 2, 3 o 4

    String nameUser;
    String clave;
    int intentos;

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

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

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
                this.idJugador = ControlServidor.clientesActivos.size(); //Añadir id al jugador
                cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Ingresó un nuevo Jugador: " + this.nameUser);
                estaAgregado = true;
                cServidor.getcPrinc().getcVentana().activarPartidaBasica();
                
                
                //Actualizar el número de intentos del jugador
                if (cServidor.getcPrinc().sumaIntento(true)) {
                    intentos++;
                }
//                switch (this.idJugador) {
//                    case 1:
//                        cServidor.getcPrinc().getcVentana().getvServidor().getLblNumAciertos1().setText("" + intentos);
//                        break;
//                    case 2:
//                        cServidor.getcPrinc().getcVentana().getvServidor().getLblNumAciertos2().setText("" + intentos);
//                        break;
//                    case 3:
//                        cServidor.getcPrinc().getcVentana().getvServidor().getLblNumAciertos3().setText("" + intentos);
//                        break;
//                    case 4:
//                        cServidor.getcPrinc().getcVentana().getvServidor().getLblNumAciertos4().setText("" + intentos);
//                        break;
//                }

                // Inicialmente todos los jugadores deben esperar hasta que se inicie el juego
                enviarControlTurno(false);
            }

            try {
                opcion = entrada.readInt();

                switch (opcion) {
                    case 1: // Mensaje/Coordenadas del jugador
                        // Verificar si es el turno del jugador ANTES de procesar
                        if (!esMiTurno()) {
                            enviarMensajeError("No es tu turno. Espera al otro jugador.");
                            continue; // ignorar el mensaje
                        }

                        mencli = entrada.readUTF();
                        enviaMsg(mencli);
                        cServidor.getcPrinc().getcVentana().getvServidor().mostrar("mensaje recibido " + mencli);

                        
                        // cServidor.cambiarTurnoAutomatico();
                        break;
                }
            } catch (IOException e) {
                break;
            }
        }

        enviaMsg(this.getNameUser() + " se ha desconectado del chat.");
        cServidor.getcPrinc().getcVentana().getvServidor().mostrar("Se removio un usuario");
        ControlServidor.clientesActivos.removeElement(this);
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

    private void enviaMsg(String jugador, String mencli) {
        for (ServidorThread user : ControlServidor.clientesActivos) {
            try {
                if (user.nameUser.equals(jugador)) {
                    user.salida2.writeInt(3); // opción mensaje privado
                    user.salida2.writeUTF(this.getNameUser());
                    user.salida2.writeUTF(this.getNameUser() + " > " + mencli);
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
                salida2.writeUTF("tu turno");
            } else {
                salida2.writeUTF("espera");
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
        return cServidor.getTurno() == this.idJugador;
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
