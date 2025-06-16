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

    private int turnoActual = 1; // 0 = juego no iniciado, 1-4 = jugador activo
    private int cartasEncontradas = 0;
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

    public int getTurno() {
        return turnoActual;
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


    public void incrementarIntento() {
        int intentos = clientesActivos.get(turnoActual-1).getJugadorVO().getIntentos();
        clientesActivos.get(turnoActual-1).getJugadorVO().setIntentos(intentos + 1);
        cPrinc.getcVentana().aumentarIntentosEnVista(intentos+1, turnoActual);
        if (turnoActual == clientesActivos.size()){
            turnoActual = 1;
        }
        else{
            turnoActual++;
        }
        terminarPartida();
        cPrinc.getcVentana().siguienteTurnoEnVista(getTurnoActual());
        
    }

    public void incrementarAcierto() {
        cartasEncontradas++;
        int acierto = clientesActivos.get(turnoActual-1).getJugadorVO().getAciertos();
        int intentos = clientesActivos.get(turnoActual-1).getJugadorVO().getIntentos();
        clientesActivos.get(turnoActual-1).getJugadorVO().setAciertos(acierto + 1);
        clientesActivos.get(turnoActual-1).getJugadorVO().setIntentos(intentos + 1);
        cPrinc.getcVentana().aumentarAciertoEnVista(acierto+1,intentos+1, turnoActual);
        terminarPartida();
    }
    
    public void terminarPartida(){
        if (cartasEncontradas >= 20){
            //enviar mensajes a los jugadores de que se acabo el juego e inhabilitar sus entradas de texto
            cPrinc.getcVentana().inhabilitarBotonesPartida();
        }
        
        
        
    }
    
    public void vaciarAciertosEIntentos(){
        for (ServidorThread jugador: clientesActivos){
            jugador.getJugadorVO().setIntentos(0);
            jugador.getJugadorVO().setAciertos(0);
        }
    }
    
    public void asignarTurnos(){
        
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
    
    
    
    

}
