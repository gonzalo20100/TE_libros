package com.emergentes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    static String url = "jdbc:mysql://localhost:3306/bd_biblio";
    static String usuario = "root";
    static String password = "";
    
    protected Connection conn = null;

    public ConexionDB() throws SQLException {
        try {
            //especificacion del driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Falta driver>>> : " + ex.getMessage());
        } catch (SQLException ex){
            System.out.println("Error de SQL >>>>: " + ex.getMessage());
        }
    }
    public Connection conectar ()
    {
        return conn;
    }
    public void desconectar()
    {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("ERROR AL CERRAR >>>> : " + ex.getMessage());
        }
    }
}