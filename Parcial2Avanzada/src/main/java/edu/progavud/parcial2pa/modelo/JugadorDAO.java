/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progavud.parcial2pa.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author sangr
 */
public class JugadorDAO {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    /**
     * Constructor de la clase JugadorDAO.
     *
     * Inicializa los atributos {@code con}, {@code st} y {@code rs} en
     * {@code null}. Estos se usarán para la conexión a la base de datos,
     * ejecución de sentencias y manejo de resultados respectivamente.
     */
    public JugadorDAO() {
        con = null;
        st = null;
        rs = null;
    }

    /**
     * Inicializa los parámetros de conexión a la base de datos utilizando las
     * propiedades recibidas.
     *
     * @param props Propiedades con la URL, usuario y contraseña de la BD.
     */
    public void inicializarBD(Properties props) {
        ConexionBD.inicializarBD(props);
    }

    public Jugador consultarGatoIndividual(String nombre,String contraseña) throws SQLException {
        Jugador JugadorVO = null;
        String consulta = "SELECT * FROM JugadoresTabla WHERE nombre= '" + nombre + "' AND contraseña= '"+ contraseña +"'";

        con = (Connection) ConexionBD.getConexion();
        st = con.createStatement();
        rs = st.executeQuery(consulta);
        if (rs.next()) {
            JugadorVO = new Jugador(nombre,contraseña);
           
        }
        st.close();
        ConexionBD.desconectar();

        return JugadorVO;
    }
}
