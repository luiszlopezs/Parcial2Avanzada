package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.Jugador;
import edu.progavud.parcial2pa.modelo.JugadorDAO;
import edu.progavud.parcial2pa.modelo.JugadorVO;
import edu.progavud.parcial2pa.modelo.ServidorVO;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

public class ControlServidor {

    private ControlPrincipalServidor cPrinc;
    private ServidorVO servidorVO;
    private JugadorDAO jugadorDAO;
    private ServidorThread servidorThread;
    private Random random;

    private int turnoActual = 0; // 0 = juego no iniciado, 1-4 = jugador activo
    public static Vector<ServidorThread> clientesActivos = new Vector();
    public Vector<ServidorThread> jugadoresEnPartida = new Vector();

    public ControlServidor(ControlPrincipalServidor cPrinc) {
        this.cPrinc = cPrinc;
        random = new Random();
        servidorVO = new ServidorVO();
        jugadorDAO = new JugadorDAO();
    }

    public void runServer() {
        try {
            servidorVO.setServ(new ServerSocket(servidorVO.getPort1()));
            servidorVO.setServ2(new ServerSocket(servidorVO.getPort2()));
            cPrinc.getcVentana().getvServidor().mostrar("::Servidor activo::");

            while (servidorVO.isListening()) {
                Socket sock = null, sock2 = null;
                try {
                    cPrinc.getcVentana().getvServidor().mostrar("Esperando Jugadores");
                    sock = servidorVO.getServ().accept();
                    sock2 = servidorVO.getServ2().accept();
                } catch (IOException e) {
                    cPrinc.getcVentana().getvServidor()
                            .mostrar("Accept failed: " + servidorVO.getServ() + ", " + e.getMessage());
                    continue;
                }
                ServidorThread user = new ServidorThread(sock, sock2, this);
                user.start();
            }
        } catch (IOException e) {
            cPrinc.getcVentana().getvServidor().mostrar("error :" + e);
        }
    }

    public void inicializarDesdeProperties(int serv1, int serv2) {
        servidorVO.setPort1(serv1);
        servidorVO.setPort2(serv2);
        System.out.println(serv1);
        System.out.println(serv2);
    }

    public JugadorVO verificarUsuario(String usuario, String clave) throws SQLException {
        jugadorDAO = new JugadorDAO();
        return jugadorDAO.consultarJugador(usuario, clave);
    }

    public void iniciarTurnos() {
        if (clientesActivos.size() >= 2 && clientesActivos.size() <= 4) {
            turnoActual = 1; // Comienza el jugador 1
            actualizarTurnosEnClientes();
            cPrinc.getcVentana().getvServidor().mostrar("La partida ha iniciado");
        } else {
            cPrinc.getcVentana().getvServidor().mostrarMensaje("Deben haber entre 2 y 4 jugadores para jugar");
        }
    }

    // Establecer el turno y actualizar todos los clientes
    public void setTurno(int turno) {
        if (turno >= 1 && turno <= clientesActivos.size()) {
            this.turnoActual = turno;
            actualizarTurnosEnClientes();
            cPrinc.getcVentana().getvServidor().mostrar("Ahora es el turno del jugador " + turno);
        } else {
            cPrinc.getcVentana().getvServidor().mostrar("Error: Jugador " + turno + " no existe");
        }
    }

    // Actualizar el estado de turnos en todos los clientes
    public void actualizarTurnosEnClientes() {
        for (ServidorThread thread : clientesActivos) {
            boolean esSuTurno = (thread.getJugadorVO().getIdJugador() == turnoActual);
            thread.enviarControlTurno(esSuTurno);

            if (esSuTurno) {
                thread.enviaMsgServer("Turno de " + thread.getJugadorVO().getNombre());
                thread.enviarDesdeServidor("Es tu turno. Ingresa las coordenadas.");
                System.out.println("Turno asignado a: " + thread.getNameUser());
            } else {
                thread.enviarDesdeServidor("Espera tu turno.");
            }
        }
    }

    public void asignarJugadoresAPartida() {
        jugadoresEnPartida.clear();
        for (ServidorThread jugserv : clientesActivos) {
            jugadoresEnPartida.add(jugserv);
        }
    }

    public void interaccionTurnos() {
        int turno = 1;
        boolean equivocado = false;

        while (!equivocado) {
            
        }

    }

    // Cambiar automáticamente al siguiente jugador
//    public void cambiarTurnoAutomatico() {
//        int siguienteTurno = turnoActual + 1;
//        if (siguienteTurno > clientesActivos.size()) {
//            siguienteTurno = 1; // Volver al primer jugador
//        }
//        setTurno(siguienteTurno);
//    }
    // Obtener el turno actual
    public int getTurno() {
        return turnoActual;
    }

    // Reemplazado por actualizarTurnosEnClientes()
//    public void notificarTurno(int jugador) {
//        // Este método ya no es necesario, pero lo mantengo por compatibilidad
//        actualizarTurnosEnClientes();
//    }
    // NUEVO MÉTODO: Verificar si hay suficientes jugadores para iniciar
    public boolean puedeIniciarPartida() {
        return clientesActivos.size() >= 2 && clientesActivos.size() <= 4;
    }

    // Reiniciar el juego en caso de tener que hacr mas partidas
    public void reiniciarJuego() {
        turnoActual = 0;
        for (ServidorThread thread : clientesActivos) {
            thread.enviarControlTurno(false); // Deshabilitar todos los botones
            thread.enviarDesdeServidor("Partida reiniciada. Esperando inicio...");
        }
        cPrinc.getcVentana().getvServidor().mostrar("Partida reiniciada.");
    }

    // Getters existentes
    public ControlPrincipalServidor getcPrinc() {
        return cPrinc;
    }

    public ServidorVO getServidorVO() {
        return servidorVO;
    }

    public ServidorThread getServidorThread() {
        return servidorThread;
    }

    public static Vector<ServidorThread> getClientesActivos() {
        return clientesActivos;
    }

    public static void setClientesActivos(Vector<ServidorThread> clientesActivos) {
        ControlServidor.clientesActivos = clientesActivos;
    }
}
