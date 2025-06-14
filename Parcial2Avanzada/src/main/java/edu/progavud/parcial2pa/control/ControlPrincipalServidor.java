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

        //JButton[][] botones = cVentana.getvServidor().getBotones();
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
//                boton.setText("");
//                boton.setEnabled(true);
            }
        }
    }

    public void verificarPareja(int btn1, int btn2) {
        Carta c1 = mapaBotonCarta.get(btn1);
        Carta c2 = mapaBotonCarta.get(btn2);
        System.out.println(c1);
        System.out.println(c2);
        if (c1.getId() == c2.getId()) {
            // Pareja correcta
            System.out.println("roorecootoot");
            cVentana.mostrarJDialogParejaEncontrada();
            System.out.println("corecorotooo despueeeeeeeeees");
        } else {
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
