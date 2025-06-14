/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.modelo;

/**
 *
 * @author hailen
 */
public class JugadorVO {

    private String nombre;
    private String clave;
    private int intentos;
    private int aciertos;
    private double eficiencia;

    public JugadorVO(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

    public JugadorVO() {
    }
    
    

    //Método que incrementa el número de intentos, se llama cada vez que el jugador envía una orden al servidor, sea un acierto o no
    public void incrementarIntento() {
        intentos++;
    }

    //Método que incrementa el número de aciertos, se llama cada vez que el usuario destapa dos casillas con la misma imagen
    public void incrementarAciertos() {
        aciertos++;
    }

    //Método que calcula la eficiencia, se calcula dividiendo el número de aciertos entre el número de intentos (esto determinará el ganador)
    public double calcularEficiencia() {
//        if (intentos == 0) return 0.0;
        return (double) aciertos / intentos;
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

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    public double getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(double eficiencia) {
        this.eficiencia = eficiencia;
    }
    
    
}
