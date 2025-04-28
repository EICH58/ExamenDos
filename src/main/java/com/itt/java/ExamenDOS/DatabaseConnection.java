package com.itt.java.ExamenDOS;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    public static Connection ConnectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/eich3/eclipse-workspace/ExamenDOS/resources/empleados");
            JOptionPane.showMessageDialog(null, "Coneccion Establecida");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de Coneccion");
            return null;
        }
    }
}
