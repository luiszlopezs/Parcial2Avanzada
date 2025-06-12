/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.modelo;

/**
 *
 * @author sangr
 */
public class Carta {
    private int fila;
    private int columna;
    private boolean esEmparejada = false;
    private boolean esDestapada = false;
    private String nombre;
    private String rutaImg;
    private int id;

    public Carta(int fila, int columna, boolean esEmparejada, boolean esDestapada, String nombre, String rutaImg, int id) {
        this.fila = fila;
        this.columna = columna;
        this.esEmparejada = esEmparejada;
        this.esDestapada = esDestapada;
        this.nombre = nombre;
        this.rutaImg = rutaImg;
        this.id = id;
    }

    public Carta(int id, String rutaImg) {
        this.id = id;
        this.rutaImg = rutaImg;
    }
    
    
    
    //Getters y setters

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean isEsEmparejada() {
        return esEmparejada;
    }

    public void setEsEmparejada(boolean esEmparejada) {
        this.esEmparejada = esEmparejada;
    }

    public boolean isEsDestapada() {
        return esDestapada;
    }

    public void setEsDestapada(boolean esDestapada) {
        this.esDestapada = esDestapada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaImg() {
        return rutaImg;
    }

    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
