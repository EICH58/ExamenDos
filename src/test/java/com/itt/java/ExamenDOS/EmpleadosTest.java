package com.itt.java.ExamenDOS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.GraphicsEnvironment;


class EmpleadosTest {

    private Empleados empleados;
    private Connection testConn;
    
    @BeforeEach
    void setUp() {
        System.setProperty("java.awt.headless", "true");
    	assert GraphicsEnvironment.isHeadless(); // Optional check
        empleados = new Empleados(testConn);
        testConn = App.DatabaseConnection.ConnectDB();
        try {
            testConn.setAutoCommit(false); // Desactivar autocommit
        } catch (SQLException e) {
            fail("Error al configurar conexión: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            if (testConn != null) {
                testConn.rollback(); // Deshacer cambios
                testConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Prueba 1: Conexión exitosa a la base de datos
    @Test
    void testConexionBaseDatosExitosa() {
        assertNotNull(testConn, "La conexión a la base de datos no debería ser nula");
    }

    // Prueba 2: Inserción de un nuevo empleado
    @Test
    void testInsertarEmpleado() {
        try {
            // Preparar datos de prueba
            String sql = "INSERT INTO empleados(IDEmpleado,NSS,Nombre,Apellido,Genero,FechaNacimiento,Edad,Salario) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = testConn.prepareStatement(sql);
            
            pst.setString(1, "TEST001");
            pst.setString(2, "TESTNSS001");
            pst.setString(3, "TestNombre");
            pst.setString(4, "TestApellido");
            pst.setString(5, "M");
            pst.setString(6, "2000-01-01");
            pst.setString(7, "23");
            pst.setString(8, "10000.00");
            
            int rowsAffected = pst.executeUpdate();
            assertEquals(1, rowsAffected, "Debería insertarse exactamente 1 registro");
            
            // Limpieza: eliminar el registro de prueba
            sql = "DELETE FROM empleados WHERE IDEmpleado = ?";
            pst = testConn.prepareStatement(sql);
            pst.setString(1, "TEST001");
            pst.executeUpdate();
            
        } catch (SQLException e) {
            fail("Excepción al insertar empleado: " + e.getMessage());
        }
    }

    // Prueba 3: Consulta de empleados
    @Test
    void testConsultaEmpleados() {
        try {
            String sql = "SELECT COUNT(*) AS total FROM empleados";
            PreparedStatement pst = testConn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            assertTrue(rs.next(), "Debería haber al menos un resultado");
            int count = rs.getInt("total");
            assertTrue(count >= 0, "El conteo de empleados no puede ser negativo");
            
        } catch (SQLException e) {
            fail("Excepción al consultar empleados: " + e.getMessage());
        }
    }

 // Prueba 4: Validación de campos obligatorios (caso negativo)
    @Test
    void testCamposObligatoriosFaltantes() {
        try {
            // Primero verificar la estructura de la tabla
            DatabaseMetaData meta = testConn.getMetaData();
            ResultSet columns = meta.getColumns(null, null, "empleados", null);
            
            // Contar columnas NOT NULL (excluyendo la clave primaria)
            int notNullColumns = 0;
            while (columns.next()) {
                if ("NO".equals(columns.getString("IS_NULLABLE")) && 
                    !columns.getString("COLUMN_NAME").equalsIgnoreCase("IDEmpleado")) {
                    notNullColumns++;
                }
            }
            
            // Si no hay columnas NOT NULL, la prueba no tiene sentido
            assumeTrue(notNullColumns > 0, "La tabla no tiene columnas NOT NULL definidas");
            
            // Intentar inserción con solo el ID
            String testId = "TESTNULL_" + System.currentTimeMillis();
            String sql = "INSERT INTO empleados(IDEmpleado) VALUES(?)";
            PreparedStatement pst = testConn.prepareStatement(sql);
            pst.setString(1, testId);
            
            try {
                pst.executeUpdate();
                fail("Debería lanzar una excepción por campos obligatorios faltantes");
            } catch (SQLException e) {
                // Aceptar cualquier violación de constraint (puede ser NOT NULL o CHECK)
                assertTrue(e.getMessage().contains("constraint"),
                    "Debería fallar por alguna restricción. Mensaje: " + e.getMessage());
            }
            
        } catch (SQLException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }
    
    // Prueba 5: Actualización de la tabla en la interfaz
    @Test
    void testActualizacionTabla() {
        // Simular la actualización de la tabla
        empleados.updateTable();
        
        // Verificar que el modelo de la tabla no es nulo
        assertNotNull(empleados.model, "El modelo de la tabla no debería ser nulo");
        
        // Nota: En una prueba más completa, podríamos verificar filas específicas
    }

    // Prueba de regresión: Verificar que la conexión sigue funcionando después de operaciones
    @Test
    void testRegresionConexion() {
        try {
            // Realizar una operación simple
            String sql = "SELECT 1";
            PreparedStatement pst = testConn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            assertTrue(rs.next(), "Debería haber un resultado");
            assertEquals(1, rs.getInt(1), "El resultado debería ser 1");
            
            // Verificar que la conexión sigue activa
            assertFalse(testConn.isClosed(), "La conexión no debería estar cerrada");
            
        } catch (SQLException e) {
            fail("Excepción en prueba de regresión: " + e.getMessage());
        }
    }
}
