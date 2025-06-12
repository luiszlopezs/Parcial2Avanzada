/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.modelo;

/**
 *
 * @author sangr
 */
public class Tablero {
    private Casilla [][] casilla;

    public Tablero() {
    }
    
    //Método que retorna la casilla seleccionada por el servidor, de la cual se obtendrá su nombre/id
    public Casilla obtenerCasilla(int fila, int columna){
        return casilla[fila][columna]; 
    }
    
    //getters y setters

    public Casilla[][] getCasilla() {
        return casilla;
    }

    public void setCasilla(Casilla[][] casilla) {
        this.casilla = casilla;
    }
    
    
}
