/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.Carta;
import edu.progavud.parcial2pa.vista.VistaServidor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Controlador de la interfaz gráfica del servidor. Se encarga de manejar los
 * eventos de la vista {@link VistaServidor} y coordinar acciones con el
 * controlador principal del servidor.
 *
 * @author hailen
 */
public class ControlVentanaServidor implements ActionListener {

    // Controlador principal del servidor
    private ControlPrincipalServidor cPrinc;

// Vista gráfica del servidor
    private VistaServidor vServidor;

    private JButton primerBoton = null;
    private boolean esperandoSegundo = false;

    /**
     * Constructor de ControlVentanaServidor.
     *
     * Inicializa la vista del servidor y la vincula a este controlador.
     *
     * @param cPrinc Controlador principal del servidor.
     */
    public ControlVentanaServidor(ControlPrincipalServidor cPrinc) {
        this.cPrinc = cPrinc;

    }

    /**
     * Maneja los eventos generados por la interfaz gráfica del servidor.
     *
     * <p>
     * Actualmente no tiene acciones implementadas, pero está listo para
     * procesar eventos según el comando recibido.</p>
     *
     * @param e Evento de acción generado por un componente.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            
        }
    }

    public void cargarVistaServidor() {
        
    }

    /**
     * Devuelve la vista gráfica del servidor.
     *
     * @return Objeto {@link VistaServidor} asociado.
     */
    public VistaServidor getvServidor() {
        return vServidor;
    }

    /**
     * Asigna la vista gráfica del servidor.
     *
     * @param vServidor Objeto {@link VistaServidor} a asignar.
     */
    public void setvServidor(VistaServidor vServidor) {
        this.vServidor = vServidor;
    }

    public void manejarClick(JButton boton) {
        Carta carta = cPrinc.getMapaBotonCarta().get(boton);
        boton.setIcon(new ImageIcon(carta.getRutaImg()));
        boton.setEnabled(false);

        if (!esperandoSegundo) {
            primerBoton = boton;
            esperandoSegundo = true;
        } else {
            JButton segundoBoton = boton;
            cPrinc.verificarPareja(primerBoton, segundoBoton);
            esperandoSegundo = false;
        }
        
        
    }
    
    public void mostrarJDialogParejaEncontrada(){
        vServidor.mostrarJDialogParejaEncontrada();
    }
    
    public void resetearParejaBotones(JButton b1, JButton b2){
        b1.setIcon(new ImageIcon());
        b1.setEnabled(true);
        b2.setIcon(new ImageIcon());
        b2.setEnabled(true);
    }

    public JButton getPrimerBoton() {
        return primerBoton;
    }

    public void setPrimerBoton(JButton primerBoton) {
        this.primerBoton = primerBoton;
    }

    public boolean isEsperandoSegundo() {
        return esperandoSegundo;
    }

    public void setEsperandoSegundo(boolean esperandoSegundo) {
        this.esperandoSegundo = esperandoSegundo;
    }
    
    

}
