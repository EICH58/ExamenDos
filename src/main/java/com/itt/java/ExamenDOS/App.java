package com.itt.java.ExamenDOS;

import java.awt.EventQueue;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.ConnectDB();

        if (conn != null) {
            System.out.println("La conexión fue exitosa.");
        } else {
            System.out.println("Fallo la conexión.");
        }
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Empleados window = new Empleados(conn);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
