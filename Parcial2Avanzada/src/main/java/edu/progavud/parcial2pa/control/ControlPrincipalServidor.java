/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.Carta;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * Controlador principal del servidor. Se encarga de inicializar y coordinar los
 * subcontroladores del servidor y la interfaz gráfica asociada.
 *
 * @author hailen
 */
public class ControlPrincipalServidor {

    // Controlador encargado de gestionar la lógica del servidor
    private ControlServidor cServidor;

    private ControlTablero cTablero;

    private int pasarPort1;
    private int pasarPort2;

    // Controlador de la ventana de interfaz gráfica del servidor
    private ControlVentanaServidor cVentana;

    private Map<Integer, Carta> mapaBotonCarta = new HashMap<>();

    /**
     * Constructor de ControlPrincipalServidor.
     *
     * Inicializa el controlador de la ventana del servidor y el controlador de
     * la lógica del servidor. Luego, inicia la ejecución del servidor.
     */
    public ControlPrincipalServidor() {

        cVentana = new ControlVentanaServidor(this);
        cTablero = new ControlTablero(this);
        cServidor = new ControlServidor(this);

        cServidor.inicializarDesdeProperties(pasarPort1, pasarPort2);
        Thread hilo = new Thread(() -> {
            cServidor.runServer();
        });
        hilo.start();

    }

    public void iniciarPartida() {
        cTablero.generarCartas();
        asignarCartasABotones();
        cServidor.iniciarTurnos();
        habilitarBotones();
        // asignar turnos a jugadores
    }

    public void habilitarBotones() {
        if (ControlServidor.clientesActivos.size() >= 3) {
            cVentana.getvServidor().getBtnJug3().setVisible(true);
            cVentana.getvServidor().getLblAciertosJug3().setVisible(true);
            cVentana.getvServidor().getLblIntentosJug3().setVisible(true);
        }
        if (ControlServidor.clientesActivos.size() >= 4) {
            cVentana.getvServidor().getBtnJug4().setVisible(true);
            cVentana.getvServidor().getLblAciertosJug4().setVisible(true);
            cVentana.getvServidor().getLblIntentosJug4().setVisible(true);
        }
    }

//    public void incrementarIntentosJugador(int idJugador) {
//        for (ServidorThread cliente : ControlServidor.clientesActivos) {
//            if (cliente.getIdJugador() == idJugador) {
//                cliente.incrementarIntentos();
//                cVentana.getvServidor().mostrar("Intento incrementado para jugador " + cliente.getNameUser()
//                        + ". Total: " + cliente.getIntentos());
//                break;
//            }
//        }
//    }

    public void manejarBotonAumentarIntentos() {
        int turnoActual = cServidor.getTurno();

        if (turnoActual == 0) {
            cVentana.getvServidor().mostrarMensaje("No hay partida iniciada. Inicia la partida primero.");
            return;
        }

        // Buscar el jugador que está en turno
        ServidorThread jugadorEnTurno = null;
        for (ServidorThread cliente : ControlServidor.clientesActivos) {
            if (cliente.getIdJugador() == turnoActual) {
                jugadorEnTurno = cliente;
                break;
            }
        }

        if (jugadorEnTurno != null) {
            jugadorEnTurno.incrementarIntentos();
            cVentana.getvServidor().mostrar("Intento incrementado para " + jugadorEnTurno.getNameUser() + " (Jugador "
                    + turnoActual + "). Total: " + jugadorEnTurno.getIntentos());

            // Notificar al cliente que su intento fue registrado
            jugadorEnTurno.enviarDesdeServidor(
                    "Tu intento ha sido registrado. Total intentos: " + jugadorEnTurno.getIntentos());
        } else {
            cVentana.getvServidor().mostrarMensaje("Error: No se encontró el jugador en turno.");
        }
    }

    public String obtenerEstadisticasJugador(int idJugador) { //Metodo para mostrar los resultados al final
        for (ServidorThread cliente : ControlServidor.clientesActivos) {
            if (cliente.getIdJugador() == idJugador) {
                return "Jugador: " + cliente.getNameUser() + " - Intentos: " + cliente.getIntentos();
            }
        }
        return "Jugador no encontrado";
    }

    /**
     * Devuelve el controlador del servidor.
     *
     * @return El controlador de la lógica del servidor.
     */
    public ControlServidor getcServidor() {
        return cServidor;
    }

    /**
     * Devuelve el controlador de la ventana del servidor.
     *
     * @return El controlador de la interfaz gráfica del servidor.
     */
    public ControlVentanaServidor getcVentana() {
        return cVentana;
    }

    public void asignarCartasABotones() {
        mapaBotonCarta.clear(); // importante si se reinicia

        // JButton[][] botones = cVentana.getvServidor().getBotones();
        int[][] botonesIndice = cVentana.getvServidor().getIndiceBotones();
        Carta[][] matrizCartas = cTablero.getMatrizCartas();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                int indiceBoton = botonesIndice[i][j];
                System.out.println(indiceBoton);
                Carta carta = matrizCartas[i][j];
                mapaBotonCarta.put(indiceBoton, carta);
                System.out.println("aaaaaaaaaaaaaaaaaa" + mapaBotonCarta.get(indiceBoton));
                System.out.println(mapaBotonCarta.get(indiceBoton).toString());
                // Aquí puedes asignar el nuevo ActionListener o resetear el texto
                // boton.setText("");
                // boton.setEnabled(true);
            }
        }
    }

    public void verificarPareja(int btn1, int btn2) {
        Carta c1 = mapaBotonCarta.get(btn1);
        Carta c2 = mapaBotonCarta.get(btn2);
        System.out.println(c1);
        System.out.println(c2);

        if (c1.getId() == c2.getId()) {
            int turnoActual = cServidor.getTurno();
         
            //Buscar jugador en turno (reciclado del método de incrementar intentos, pero se llama cada vez que encuente una pareja)
            ServidorThread jugadorEnTurno = null;
            for (ServidorThread cliente : ControlServidor.clientesActivos) {
                if (cliente.getIdJugador() == turnoActual) {
                    jugadorEnTurno = cliente;
                    break;
                }
            }

            if (jugadorEnTurno != null) {
//                jugadorEnTurno.incrementarIntentos();
                cVentana.getvServidor().mostrar("Acierto incrementado para " + jugadorEnTurno.getNameUser() + " (Jugador "
                        + turnoActual + "). Total: " + jugadorEnTurno.getAciertos());

                // Notificar al cliente que tuvo un acierto
                jugadorEnTurno.enviarDesdeServidor("Tuvo un acierto, total: " + jugadorEnTurno.getAciertos());
                jugadorEnTurno.incrementarAciertos();
            } else {
                cVentana.getvServidor().mostrarMensaje("Error: No se encontró el jugador en turno.");
            }

        } else { //Si no son pareja 
            System.out.println("no son pareja jajajajajajaj");
            // No es pareja → volver a ocultar después de un momento
            Timer timer = new Timer(1000, e -> {
                cVentana.resetearParejaBotones(btn1, btn2);
            });
            timer.setRepeats(false);
            timer.start();

        }

        c1 = null;
        c2 = null;
        cVentana.setPrimerBoton(10000);
    }

    public Map<Integer, Carta> getMapaBotonCarta() {
        return mapaBotonCarta;
    }

    public void setMapaBotonCarta(Map<Integer, Carta> mapaBotonCarta) {
        this.mapaBotonCarta = mapaBotonCarta;
    }

    public void inicializarPuertosDesdeProps(File archivo) {
        if (archivo != null) {
            try (FileInputStream fis = new FileInputStream(archivo)) {
                Properties props = new Properties();
                props.load(fis);

                pasarPort1 = Integer.parseInt(props.getProperty("props1"));
                pasarPort2 = Integer.parseInt(props.getProperty("props2"));

            } catch (IOException e) {

            }
        } else {

        }
    }

    public ControlTablero getcTablero() {
        return cTablero;
    }

    public void setcTablero(ControlTablero cTablero) {
        this.cTablero = cTablero;
    }

    public int getPasarPort1() {
        return pasarPort1;
    }

    public void setPasarPort1(int pasarPort1) {
        this.pasarPort1 = pasarPort1;
    }

    public int getPasarPort2() {
        return pasarPort2;
    }

    public void setPasarPort2(int pasarPort2) {
        this.pasarPort2 = pasarPort2;
    }

}
