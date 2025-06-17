/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author hailen
 */
public class Jugador {
    private String nombre;
    private String clave;

    // Flujo de salida hacia el servidor (canal principal)
    private DataOutputStream salida;

    // Flujo de entrada desde el servidor (canal principal)
    private DataInputStream entrada;

    // Flujo adicional de entrada para mensajes
    private DataInputStream entrada2;

    // Dirección IP del servidor
    private static String IPserver;

    // Socket principal para la comunicación
    Socket comunication = null;

    // Segundo socket para recepción de mensajes
    Socket comunication2 = null;


    
    public Jugador(){
    }

    //Reciclado de enviarMensaje() en cliente

    // Método para enviar dos coordenadas de un intento en una instrucción (primera y segunda casilla)
    public void enviarIntento(String instruccion) throws IOException {
        salida.writeInt(1); 
        salida.writeUTF(instruccion);
    }
    public void enviarInformacionJugador(String nombre, String clave) throws IOException{
        salida.writeInt(0);
        salida.writeUTF(nombre);
        salida.writeUTF(clave);
    }

    public void conexion(ArrayList<String> datosPasar) throws IOException {

        comunication = new Socket(datosPasar.get(2), Integer.parseInt(datosPasar.get(0)));

        comunication2 = new Socket(datosPasar.get(2), Integer.parseInt(datosPasar.get(1)));

        // Primero salida, luego entrada
        salida = new DataOutputStream(comunication.getOutputStream());

        entrada = new DataInputStream(comunication.getInputStream());

        // Segundo socket (igual)
        // Si vas a usar otro flujo en comunication2, igual hazlo bien ordenado.
        entrada2 = new DataInputStream(comunication2.getInputStream());

        salida.writeUTF(nombre);
        salida.writeUTF(clave);

    }

    // Getters y setters

 

    public DataOutputStream getSalida() {
        return salida;
    }

    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    public DataInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }

    public DataInputStream getEntrada2() {
        return entrada2;
    }

    public void setEntrada2(DataInputStream entrada2) {
        this.entrada2 = entrada2;
    }

    public static String getIPserver() {
        return IPserver;
    }

    public static void setIPserver(String IPserver) {
        Jugador.IPserver = IPserver;
    }

    public Socket getComunication() {
        return comunication;
    }

    public void setComunication(Socket comunication) {
        this.comunication = comunication;
    }

    public Socket getComunication2() {
        return comunication2;
    }

    public void setComunication2(Socket comunication2) {
        this.comunication2 = comunication2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    

}
