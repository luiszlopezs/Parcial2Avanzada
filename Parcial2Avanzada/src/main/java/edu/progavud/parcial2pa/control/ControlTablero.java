/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import edu.progavud.parcial2pa.modelo.Carta;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author hailen
 */
public class ControlTablero {
    
    private Carta[][] matrizCartas;
    
    private ControlPrincipalServidor cPrinc;

    public ControlTablero(ControlPrincipalServidor cPrinc) {
        this.cPrinc = cPrinc;
    }
    
    
    

    public void generarCartas() {
        matrizCartas = null;
        ArrayList<Carta> cartaLista = new ArrayList<>();
        int filas = 5;
        int columnas = 8;
        int total = filas * columnas;

        for (int i = 1; i <= total / 2; i++) {
            // Se crean dos objetos distintos con el mismo valor
            Carta carta1 = new Carta(i, "/cartas/carta" + i + ".jpg");
            Carta carta2 = new Carta(i, "/cartas/carta" + i + ".jpg");
            cartaLista.add(carta1);
            cartaLista.add(carta2);
        }

        Collections.shuffle(cartaLista);

        matrizCartas = new Carta[filas][columnas];
        int idx = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizCartas[i][j] = cartaLista.get(idx);
                idx++;
            }
        }
        
        System.out.println(matrizCartas);
    }

    public Carta[][] getMatrizCartas() {
        return matrizCartas;
    }

    public void setMatrizCartas(Carta[][] matrizCartas) {
        this.matrizCartas = matrizCartas;
    }
    
    
}
