/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.control;

import java.io.IOException;

/**
 *
 * @author sangr
 */
public class LauncherServidor {
        /**
     * Método principal que inicia la ejecución de la aplicación cliente.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     * @throws IOException Si ocurre un error al establecer la conexión inicial.
     */
    public static void main(String[] args) throws IOException {
        new ControlPrincipalServidor();
    }

}
