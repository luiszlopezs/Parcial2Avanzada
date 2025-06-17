/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.Carta;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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


//    private int pasarPort1;
//    private int pasarPort2;
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

        try {

            cVentana = new ControlVentanaServidor(this);
            cTablero = new ControlTablero(this);
            cServidor = new ControlServidor(this);
            
            cServidor.inicializarDesdeProperties(cVentana.getDatosPasar());
            cServidor.runServer();
        } catch (SQLException ex) {

            System.getLogger(ControlPrincipalServidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public void iniciarPartida() {
//        cServidor.setCartasEncontradas(0);
//        cServidor.vaciarAciertosEIntentos();
        cServidor.cerrarEntradasDeJugadores();
        cTablero.generarCartas();
        asignarCartasABotones();

        cVentana.activarBotonesCartas();

        asignarNombresABotones();
        cVentana.siguienteTurnoEnVista(cServidor.getTurnoActual());
        // asignar turnos a jugadores

        // asignar turnos a jugadores
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

            }
        }
    }

    public void verificarPareja(int btn1, int btn2) {
        Carta c1 = mapaBotonCarta.get(btn1);
        Carta c2 = mapaBotonCarta.get(btn2);
        System.out.println(c1);
        System.out.println(c2);
        ServidorThread jugadorEnTurno = null;
        if (c1.getId() == c2.getId()) {

            // Pareja correcta
            System.out.println("roorecootoot");
            cServidor.avisarAcierto(btn1, btn2);
            cVentana.mostrarJDialogParejaEncontrada();
            System.out.println("corecorotooo despueeeeeeeeees");

            cServidor.incrementarAcierto();
        } else {
            cServidor.avisarError(btn1, btn2);
            cServidor.incrementarIntento();

            System.out.println("no son pareja jajajajajajaj");

            // No es pareja → volver a ocultar después de un momento
            Timer timer = new Timer(1000, e -> {
                //resetear a la imagen volteada, no lo he hecho
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

    public ArrayList<String> inicializarPuertosDesdeProps(File archivo) throws SQLException {
        ArrayList<String> datos = new ArrayList<>();
        if (archivo != null) {
            try (FileInputStream fis = new FileInputStream(archivo)) {
                Properties props = new Properties();
                props.load(fis);

                

// Guardar los puertos
                datos.add(props.getProperty("props1"));
                datos.add(props.getProperty("props2"));

// Guardar usuarios y claves
                for (int i = 1; i <= 6; i++) {
                    datos.add(props.getProperty("usuario" + i));
                    datos.add(props.getProperty("clave" + i));

                    
                }
            } catch (IOException e) {

            }
        } else {

        }
        return datos;
    }

    public void asignarNombresABotones() {
        int i = 1;
        for (ServidorThread jugador : ControlServidor.clientesActivos) {
            cVentana.habilitarBotonesAlIniciarSwitch(jugador.getJugadorVO().getNombre(), i);
            i++;
        }
    }

    public ControlTablero getcTablero() {
        return cTablero;
    }

    public void setcTablero(ControlTablero cTablero) {
        this.cTablero = cTablero;
    }

//    public int getPasarPort1() {
//        return pasarPort1;
//    }
//
//    public void setPasarPort1(int pasarPort1) {
//        this.pasarPort1 = pasarPort1;
//    }
//
//    public int getPasarPort2() {
//        return pasarPort2;
//    }
//
//    public void setPasarPort2(int pasarPort2) {
//        this.pasarPort2 = pasarPort2;
//    }
}
