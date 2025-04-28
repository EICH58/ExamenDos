package com.itt.java.ExamenDOS;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class App {
	
	public static void main(String[] args) {
	        // Call the static method from DatabaseConnection
		Connection conn = DatabaseConnection.ConnectDB();

	    	if (conn != null) {
	    		System.out.println("La conexión fue exitosa.");
	            // Do something with the connection...
	        } 
	    	else {
	            System.out.println("Fallo la conexión.");
	        }
	    	
	    	EventQueue.invokeLater(new Runnable() {
	    	    public void run() {
	    	        try {
	    	            Empleados window = new Empleados(conn); // Pasar la conexión
	    	            window.frame.setVisible(true);
	    	        } catch (Exception e) {
	    	            e.printStackTrace();
	    	        }
	    	    }
	    	});
	}
	
	public static class DatabaseConnection {
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
	
}
