/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.Jugador;
import edu.progavud.parcial2pa.modelo.JugadorDAO;
import edu.progavud.parcial2pa.modelo.JugadorVO;
import edu.progavud.parcial2pa.modelo.ServidorVO;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private JugadorDAO jugadorDAO;

// Hilo encargado de atender a los clientes que se conectan
    private ServidorThread servidorThread;

    private int turnoActual = 1;
    private int cartasEncontradas = 0;

// Generador de números aleatorios para funcionalidades del servidor
    private Random random;

    public static Vector<ServidorThread> clientesActivos = new Vector();

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
        jugadorDAO = new JugadorDAO();

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

            servidorVO.setServ(new ServerSocket(servidorVO.getPort1()));  //traer de properties
            servidorVO.setServ2(new ServerSocket(servidorVO.getPort2()));  //traer de properties
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
    public void inicializarDesdeProperties(ArrayList<String> datosPasar) {
        servidorVO.setPort1(Integer.parseInt(datosPasar.get(0)));
        servidorVO.setPort2(Integer.parseInt(datosPasar.get(1)));
        for (int i = 2; i <= 12; i += 2) {

            try {
                getJugadorDAO().insertarJugador(
                        datosPasar.get(i),
                        datosPasar.get(i + 1)
                );
                System.out.println(datosPasar.get(i) + " " + datosPasar.get(i + 1));
            } catch (SQLException ex) {
                System.getLogger(ControlServidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        System.out.println(datosPasar.get(0));
        System.out.println(datosPasar.get(1));
    }

    public JugadorVO verificarUsuario(String usuario, String clave) throws SQLException {
        //conexion con JUGADOR DAO o SERVIDOR DAO para verificar si el usuario existe
        jugadorDAO = new JugadorDAO();
        return jugadorDAO.consultarJugador(usuario, clave);

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

    public static Vector<ServidorThread> getClientesActivos() {
        return clientesActivos;
    }

    public static void setClientesActivos(Vector<ServidorThread> clientesActivos) {
        ControlServidor.clientesActivos = clientesActivos;
    }

    public void incrementarIntento() {
        int intentos = clientesActivos.get(turnoActual - 1).getJugadorVO().getIntentos();
        clientesActivos.get(turnoActual - 1).getJugadorVO().setIntentos(intentos + 1);
        cPrinc.getcVentana().aumentarIntentosEnVista(intentos + 1, turnoActual);
        if (turnoActual == clientesActivos.size()) {
            turnoActual = 1;
        } else {
            turnoActual++;
        }
        terminarPartida();
        cPrinc.getcVentana().siguienteTurnoEnVista(getTurnoActual());

    }

    public void incrementarAcierto() {
        cartasEncontradas++;
        int acierto = clientesActivos.get(turnoActual - 1).getJugadorVO().getAciertos();
        int intentos = clientesActivos.get(turnoActual - 1).getJugadorVO().getIntentos();
        clientesActivos.get(turnoActual - 1).getJugadorVO().setAciertos(acierto + 1);
        clientesActivos.get(turnoActual - 1).getJugadorVO().setIntentos(intentos + 1);
        cPrinc.getcVentana().aumentarAciertoEnVista(acierto + 1, intentos + 1, turnoActual);
        terminarPartida();
    }

    public void terminarPartida() {
        if (cartasEncontradas >= 20) {
            //enviar mensajes a los jugadores de que se acabo el juego e inhabilitar sus entradas de texto
            cPrinc.getcVentana().inhabilitarBotonesPartida();
        }

    }

    public void enviarResultados() {
        // Determinar ganador
        ServidorThread ganador = null;
        int maxAciertos = -1;

        for (ServidorThread jugador : clientesActivos) {
            if (jugador.getJugadorVO().getAciertos() > maxAciertos) {
                maxAciertos = jugador.getJugadorVO().getAciertos();
                ganador = jugador;
            }
        }

        // MENSAJES MEJORADOS DE FIN DE PARTIDA:
        if (ganador != null) {
            // 1. Mensaje público de fin de partida con estadísticas
            String mensajeFinal = " ¡PARTIDA TERMINADA! \n"
                    + "Ganador: " + ganador.getJugadorVO().getNombre()
                    + " con " + maxAciertos + " aciertos\n"
                    + "Estadísticas finales:";

            for (ServidorThread jugador : clientesActivos) {
                mensajeFinal += "\n- " + jugador.getJugadorVO().getNombre()
                        + ": " + jugador.getJugadorVO().getAciertos() + " aciertos, "
                        + jugador.getJugadorVO().getIntentos() + " intentos";
            }
            
            cPrinc.getcVentana().getvServidor().mostrar(mensajeFinal);

            // Enviar estadísticas a todos
            for (ServidorThread jugador : clientesActivos) {
                jugador.enviaMsg(mensajeFinal);
            }

            // 2. Mensaje privado personalizado al ganador
            ganador.enviaMsg(ganador.getJugadorVO().getNombre()," ¡FELICIDADES! Has ganado la partida con " + maxAciertos + " aciertos. \n¡Eres el maestro de la memoria!");

            // 3. Mensaje privado personalizado a los demás jugadores
            for (ServidorThread jugador : clientesActivos) {
                if (!jugador.equals(ganador)) {
                    jugador.enviaMsg(jugador.getJugadorVO().getNombre(),"Partida terminada. " + ganador.getJugadorVO().getNombre()
                            + " ha ganado. \n¡Mejor suerte la próxima vez!");
                }
            }
        }

    }

    public void vaciarAciertosEIntentos() {
        for (ServidorThread jugador : clientesActivos) {
            jugador.getJugadorVO().setIntentos(0);
            jugador.getJugadorVO().setAciertos(0);
        }
    }

    public void avisarTurnos() {
        clientesActivos.get(turnoActual - 1).enviaMsg(" ");
        clientesActivos.get(turnoActual - 1).enviaMsg("Turno N°: " + getTurnoActual() + ", para jugador -> " + clientesActivos.get(cPrinc.getcServidor().getTurnoActual() - 1).getJugadorVO().getNombre());
        for (ServidorThread jugador : clientesActivos) {
            if (jugador.getJugadorVO().getNombre().equalsIgnoreCase(clientesActivos.get(getTurnoActual() - 1).getJugadorVO().getNombre())) {
                clientesActivos.get(turnoActual - 1).enviaMsg(jugador.getJugadorVO().getNombre(), "Ingresa las coordenadas de 2 cartas.");
                clientesActivos.get(turnoActual - 1).enviaMsg(jugador.getJugadorVO().getNombre(), "habilitar");
            } else {

                clientesActivos.get(turnoActual - 1).enviaMsg(jugador.getJugadorVO().getNombre(), "Aun no es tu turno, Espera");
                clientesActivos.get(turnoActual - 1).enviaMsg(jugador.getJugadorVO().getNombre(), "inhabilitar");
            }
        }
    }

    public void avisarError(int carta1, int carta2) {
        clientesActivos.get(turnoActual - 1).enviaMsg("   " + clientesActivos.get(turnoActual - 1).getJugadorVO().getNombre() + " se ha equivocado \n" + "   Las cartas " + String.valueOf(carta1) + " " + String.valueOf(carta2) + " no coinciden");
        clientesActivos.get(turnoActual - 1).enviaMsg(clientesActivos.get(turnoActual - 1).getJugadorVO().getNombre(), "inhabilitar");
    }

    public void avisarErrorDeEscritura() {
        clientesActivos.get(turnoActual - 1).enviaMsg("   " + clientesActivos.get(turnoActual - 1).getJugadorVO().getNombre() + " se ha equivocado \n" + "   Ha introducido coordenadas invalidas");
        clientesActivos.get(turnoActual - 1).enviaMsg(clientesActivos.get(turnoActual - 1).getJugadorVO().getNombre(), "inhabilitar");
    }

    public void avisarAcierto(int carta1, int carta2) {
        clientesActivos.get(turnoActual - 1).enviaMsg("   " + clientesActivos.get(turnoActual - 1).getJugadorVO().getNombre() + " ha acertado \n" + "   Las cartas " + String.valueOf(carta1) + " " + String.valueOf(carta2) + " son pareja");
    }

    public void asignarTurnos() {

    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

    public int getCartasEncontradas() {
        return cartasEncontradas;
    }

    public void setCartasEncontradas(int cartasEncontradas) {
        this.cartasEncontradas = cartasEncontradas;
    }

    public void cerrarEntradasDeJugadores() {
        this.servidorVO.setListening(false);
    }

    public JugadorDAO getJugadorDAO() {
        return jugadorDAO;
    }

    public void setJugadorDAO(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

}
