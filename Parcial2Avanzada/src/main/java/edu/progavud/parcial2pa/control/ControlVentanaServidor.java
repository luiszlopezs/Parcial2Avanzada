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
            case "INICIAR_JUEGO":
                
                break;
            case "AUMENTAR_INTENTO":
                
                break;
            case "ENVIAR_RESULTADOS":
                
                break;
                
                
                
            case "BOTON_40":
                manejarClick(vServidor.getBtnCarta40());
                break;
            case "BOTON_1":
                manejarClick(vServidor.getBtnCarta1());
                break;
            case "BOTON_2":
                manejarClick(vServidor.getBtnCarta2());
                break;
            case "BOTON_3":
                manejarClick(vServidor.getBtnCarta3());
                break;
            case "BOTON_4":
                manejarClick(vServidor.getBtnCarta4());
                break;
            case "BOTON_5":
                manejarClick(vServidor.getBtnCarta5());
                break;
            case "BOTON_6":
                manejarClick(vServidor.getBtnCarta6());
                break;
            case "BOTON_7":
                manejarClick(vServidor.getBtnCarta7());
                break;
            case "BOTON_8":
                manejarClick(vServidor.getBtnCarta8());
                break;
            case "BOTON_9":
                manejarClick(vServidor.getBtnCarta9());
                break;
            case "BOTON_10":
                manejarClick(vServidor.getBtnCarta10());
                break;
            case "BOTON_11":
                manejarClick(vServidor.getBtnCarta11());
                break;
            case "BOTON_12":
                manejarClick(vServidor.getBtnCarta12());
                break;
            case "BOTON_13":
                manejarClick(vServidor.getBtnCarta13());
                break;
            case "BOTON_14":
                manejarClick(vServidor.getBtnCarta14());
                break;
            case "BOTON_15":
                manejarClick(vServidor.getBtnCarta15());
                break;
            case "BOTON_16":
                manejarClick(vServidor.getBtnCarta16());
                break;
            case "BOTON_17":
                manejarClick(vServidor.getBtnCarta17());
                break;
            case "BOTON_18":
                manejarClick(vServidor.getBtnCarta18());
                break;
            case "BOTON_19":
                manejarClick(vServidor.getBtnCarta19());
                break;
            case "BOTON_20":
                manejarClick(vServidor.getBtnCarta20());
                break;
            case "BOTON_21":
                manejarClick(vServidor.getBtnCarta21());
                break;
            case "BOTON_22":
                manejarClick(vServidor.getBtnCarta22());
                break;
            case "BOTON_23":
                manejarClick(vServidor.getBtnCarta23());
                break;
            case "BOTON_24":
                manejarClick(vServidor.getBtnCarta24());
                break;
            case "BOTON_25":
                manejarClick(vServidor.getBtnCarta25());
                break;
            case "BOTON_26":
                manejarClick(vServidor.getBtnCarta26());
                break;
            case "BOTON_27":
                manejarClick(vServidor.getBtnCarta27());
                break;
            case "BOTON_28":
                manejarClick(vServidor.getBtnCarta28());
                break;
            case "BOTON_29":
                manejarClick(vServidor.getBtnCarta29());
                break;
            case "BOTON_30":
                manejarClick(vServidor.getBtnCarta30());
                break;
            case "BOTON_31":
                manejarClick(vServidor.getBtnCarta31());
                break;
            case "BOTON_32":
                manejarClick(vServidor.getBtnCarta32());
                break;
            case "BOTON_33":
                manejarClick(vServidor.getBtnCarta33());
                break;
            case "BOTON_34":
                manejarClick(vServidor.getBtnCarta34());
                break;
            case "BOTON_35":
                manejarClick(vServidor.getBtnCarta35());
                break;
            case "BOTON_36":
                manejarClick(vServidor.getBtnCarta36());
                break;
            case "BOTON_37":
                manejarClick(vServidor.getBtnCarta37());
                break;
            case "BOTON_38":
                manejarClick(vServidor.getBtnCarta38());
                break;
            case "BOTON_39":
                manejarClick(vServidor.getBtnCarta39());
                break;
            default:
                System.out.println("Comando desconocido: " + comando);
        }

    }

    public void cargarVistaServidor() {
        vServidor = new VistaServidor();
        asignarActionListeners();
        vServidor.setVisible(true);
    }

    public void asignarActionListeners() {
        for (int i = 1; i <= 40; i++) {
            JButton boton = obtenerBotonCarta(i);
            if (boton != null) {
                boton.setActionCommand("BOTON_" + i);
                boton.addActionListener(this);
            }
        }

        vServidor.getBtnEnviarResultados().setActionCommand("ENVIAR_RESULTADOS");
        vServidor.getBtnEnviarResultados().addActionListener(this);

        vServidor.getBtnAumentarIntento().setActionCommand("AUMENTAR_INTENTO");
        vServidor.getBtnJug1().addActionListener(this);

        vServidor.getBtnIniciarJuego().setActionCommand("INICIAR_JUEGO");
        vServidor.getBtnJug2().addActionListener(this);

    }

    private JButton obtenerBotonCarta(int numero) {
        switch (numero) {
            case 1:
                return vServidor.getBtnCarta1();
            case 2:
                return vServidor.getBtnCarta2();
            case 3:
                return vServidor.getBtnCarta3();
            case 4:
                return vServidor.getBtnCarta4();
            case 5:
                return vServidor.getBtnCarta5();
            case 6:
                return vServidor.getBtnCarta6();
            case 7:
                return vServidor.getBtnCarta7();
            case 8:
                return vServidor.getBtnCarta8();
            case 9:
                return vServidor.getBtnCarta9();
            case 10:
                return vServidor.getBtnCarta10();
            case 11:
                return vServidor.getBtnCarta11();
            case 12:
                return vServidor.getBtnCarta12();
            case 13:
                return vServidor.getBtnCarta13();
            case 14:
                return vServidor.getBtnCarta14();
            case 15:
                return vServidor.getBtnCarta15();
            case 16:
                return vServidor.getBtnCarta16();
            case 17:
                return vServidor.getBtnCarta17();
            case 18:
                return vServidor.getBtnCarta18();
            case 19:
                return vServidor.getBtnCarta19();
            case 20:
                return vServidor.getBtnCarta20();
            case 21:
                return vServidor.getBtnCarta21();
            case 22:
                return vServidor.getBtnCarta22();
            case 23:
                return vServidor.getBtnCarta23();
            case 24:
                return vServidor.getBtnCarta24();
            case 25:
                return vServidor.getBtnCarta25();
            case 26:
                return vServidor.getBtnCarta26();
            case 27:
                return vServidor.getBtnCarta27();
            case 28:
                return vServidor.getBtnCarta28();
            case 29:
                return vServidor.getBtnCarta29();
            case 30:
                return vServidor.getBtnCarta30();
            case 31:
                return vServidor.getBtnCarta31();
            case 32:
                return vServidor.getBtnCarta32();
            case 33:
                return vServidor.getBtnCarta33();
            case 34:
                return vServidor.getBtnCarta34();
            case 35:
                return vServidor.getBtnCarta35();
            case 36:
                return vServidor.getBtnCarta36();
            case 37:
                return vServidor.getBtnCarta37();
            case 38:
                return vServidor.getBtnCarta38();
            case 39:
                return vServidor.getBtnCarta39();
            case 40:
                return vServidor.getBtnCarta40();
            default:
                return null;
        }
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
        boton.setIcon(new ImageIcon(cPrinc.getMapaBotonCarta().get(boton).getRutaImg()));
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

    public void mostrarJDialogParejaEncontrada() {
        vServidor.mostrarJDialogParejaEncontrada();
    }

    public void resetearParejaBotones(JButton b1, JButton b2) {
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
