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

// Controlador de la ventana de interfaz gráfica del servidor
    private ControlVentanaServidor cVentana;

    private Map<JButton, Carta> mapaBotonCarta = new HashMap<>();

    /**
     * Constructor de ControlPrincipalServidor.
     *
     * Inicializa el controlador de la ventana del servidor y el controlador de
     * la lógica del servidor. Luego, inicia la ejecución del servidor.
     */
    public ControlPrincipalServidor() {

        cVentana = new ControlVentanaServidor(this);
        cServidor = new ControlServidor(this);
        cServidor.runServer();

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

    private void asignarCartasABotones() {
        mapaBotonCarta.clear(); // importante si se reinicia

        JButton[][] botones = cVentana.getvServidor().getBotones();
        Carta[][] matrizCartas = cTablero.getMatrizCartas();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                JButton boton = botones[i][j];
                Carta carta = matrizCartas[i][j];
                mapaBotonCarta.put(boton, carta);

                // Aquí puedes asignar el nuevo ActionListener o resetear el texto
                boton.setText("");
                boton.setEnabled(true);
            }
        }
    }

    public void verificarPareja(JButton b1, JButton b2) {
        Carta c1 = mapaBotonCarta.get(b1);
        Carta c2 = mapaBotonCarta.get(b2);

        if (c1.getId() == c2.getId()) {
            // Pareja correcta
            cVentana.mostrarJDialogParejaEncontrada();
        } else {
            // No es pareja → volver a ocultar después de un momento
            Timer timer = new Timer(1000, e -> {
                cVentana.resetearParejaBotones(b1, b2);
            });
            timer.setRepeats(false);
            timer.start();
        }
        
        c1 = null;
        c2 = null;
        cVentana.setPrimerBoton(null);
    }

    public Map<JButton, Carta> getMapaBotonCarta() {
        return mapaBotonCarta;
    }

    public void setMapaBotonCarta(Map<JButton, Carta> mapaBotonCarta) {
        this.mapaBotonCarta = mapaBotonCarta;
    }
    
    

}
