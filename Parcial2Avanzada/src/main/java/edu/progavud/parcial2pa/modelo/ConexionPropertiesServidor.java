/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.modelo;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase encargada de manejar la configuración de conexión utilizando archivos
 * de propiedades.
 *
 * Esta clase permite cargar y almacenar propiedades desde o hacia un archivo
 * .properties, que se utiliza comúnmente para definir los parámetros de
 * conexión a bases de datos u otros recursos externos.
 *
 * Atributos: - {@code propiedades}: Objeto Properties que almacena las claves y
 * valores de configuración. - {@code archivoIn}: Flujo de entrada para leer el
 * archivo de propiedades. - {@code archivoOut}: Flujo de salida para guardar el
 * archivo de propiedades.
 */
public class ConexionPropertiesServidor {

    private Properties propiedades;
    private FileInputStream archivoIn;
    private FileOutputStream archivoOut;

    /**
     * Constructor que recibe el objeto Properties y el flujo de entrada.
     *
     * @param propiedades Objeto Properties que contiene la configuración.
     * @param archivoIn Flujo de entrada para leer el archivo de propiedades.
     */
    public ConexionPropertiesServidor(Properties propiedades, FileInputStream archivoIn) {
        this.propiedades = propiedades;
        this.archivoIn = archivoIn;
    }

    /**
     * Carga las propiedades desde el archivo de entrada.
     *
     * Este método lee las claves y valores definidos en el archivo .properties
     * asociado al flujo {@code archivoIn} y los carga en el objeto
     * {@code propiedades}.
     *
     * @return Las propiedades cargadas desde el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Properties cargar() throws IOException {
        propiedades.load(archivoIn);
        archivoIn.close();
        return propiedades;
    }

    /**
     * Guarda las propiedades actuales en el archivo de salida.
     *
     * Este método escribe las claves y valores contenidos en el objeto
     * {@code propiedades} en el archivo .properties asociado al flujo
     * {@code archivoOut}.
     *
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void guardar() throws IOException {
        propiedades.store(archivoOut, "Datos guardados");
        archivoOut.close();
    }

    // Getters y Setters
    /**
     * Retorna el objeto de propiedades.
     *
     * @return Objeto {@code Properties} que contiene las configuraciones
     * cargadas.
     */
    public Properties getPropiedades() {
        return propiedades;
    }

    /**
     * Establece el objeto de propiedades.
     *
     * @param propiedades Objeto {@code Properties} con las configuraciones a
     * guardar.
     */
    public void setPropiedades(Properties propiedades) {
        this.propiedades = propiedades;
    }

    /**
     * Retorna el archivo de entrada utilizado para cargar las propiedades.
     *
     * @return Objeto {@code FileInputStream} asociado al archivo .properties de
     * entrada.
     */
    public FileInputStream getArchivoIn() {
        return archivoIn;
    }

    /**
     * Establece el archivo de entrada para cargar las propiedades.
     *
     * @param archivoIn Flujo de entrada de tipo {@code FileInputStream}.
     */
    public void setArchivoIn(FileInputStream archivoIn) {
        this.archivoIn = archivoIn;
    }

    /**
     * Retorna el archivo de salida utilizado para guardar las propiedades.
     *
     * @return Objeto {@code FileOutputStream} asociado al archivo .properties
     * de salida.
     */
    public FileOutputStream getArchivoOut() {
        return archivoOut;
    }

    /**
     * Establece el archivo de salida para guardar las propiedades.
     *
     * @param archivoOut Flujo de salida de tipo {@code FileOutputStream}.
     */
    public void setArchivoOut(FileOutputStream archivoOut) {
        this.archivoOut = archivoOut;
    }
}
