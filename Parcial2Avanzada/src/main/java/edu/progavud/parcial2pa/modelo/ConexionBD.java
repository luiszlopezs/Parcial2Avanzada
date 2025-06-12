package edu.progavud.parcial2pa.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hailen
 * Clase que gestiona la conexión a la base de datos MySQL utilizada por la aplicación.
 *
 * Esta clase implementa una conexión estática reutilizable a la base de datos `gatosbd`
 * alojada en `localhost`, utilizando el usuario `root` sin contraseña.
 *
 * Componentes principales:
 *
 * - {@code cn}: Objeto de tipo {@code Connection} que mantiene la conexión activa.
 * - {@code URLBD}: URL de conexión a la base de datos MySQL.
 * - {@code usuario}, {@code contrasena}: Credenciales para acceder a la base de datos.
 *
 * Métodos:
 * - {@code conectar()}: Establece y retorna una conexión a la base de datos.
 * - {@code cerrarConexion()}: Cierra la conexión si está abierta.
 */
public class ConexionBD {

    private static Connection cn = null;
    private static String URLBD = "jdbc:mysql://localhost/juegobd";
    private static String usuario = "root";
    private static String contrasena = "";
   /**
     * Establece y devuelve la conexión a la base de datos MySQL.
     * 
     * Intenta conectarse a la base de datos "gatosbd" utilizando los parámetros
     * definidos en las variables URLBD, usuario y contraseña. Si ocurre un error,
     * se imprime un mensaje indicando que no se pudo cargar el controlador.
     * 
     * @return La conexión establecida con la base de datos, o {@code null} si falla.
     */
    public static Connection getConexion() {
        try {
            cn = DriverManager.getConnection(URLBD, usuario, contrasena);
        } catch (SQLException ex) {
        }
        return cn;
    }

        /**
     * Desconecta la conexión a la base de datos estableciendo el objeto {@code cn} en {@code null}.
     * 
     * Este método no cierra explícitamente la conexión con el motor de base de datos, 
     * solo elimina la referencia local.
     */
    public static void desconectar() {
        try {
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        cn = null;
    }
    /**
     * Inicializa los parámetros de conexión a la base de datos usando un objeto {@code Properties}.
     * 
     * Este método permite configurar dinámicamente la URL, el usuario y la contraseña 
     * de conexión a la base de datos a partir de un archivo de propiedades u otra fuente.
     *
     * @param props Objeto {@code Properties} que debe contener las claves:
     *              {@code urlBD}, {@code usuarioBD} y {@code contrasenaBD}.
     */
    public static void inicializarBD(Properties props) {
        URLBD = props.getProperty("urlBD");
        usuario = props.getProperty("usuarioBD");
        contrasena = props.getProperty("contrasenaBD");

    }
}
