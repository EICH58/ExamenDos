package com.itt.java.ExamenDOS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;

import com.itt.java.ExamenDOS.App.DatabaseConnection;

import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.awt.print.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

 class Empleados {

	JFrame frame;
	private JTextField textField;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	DefaultTableModel model = new DefaultTableModel();

	/**
	 * Launch the application.
	 */
	
	public void updateTable() 
	{
		conn = DatabaseConnection.ConnectDB();
		
		if(conn!=null) 
		{
			String sql = "Select IDEmpleado, NSS, Nombre, Apellido, Genero, FechaNacimiento, Edad, Salario";
		
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[8];
			
				while(rs.next()) 
				{
					columnData[0]=rs.getString("IDEmpleado");
					columnData[1]=rs.getString("NSS");
					columnData[2]=rs.getString("Nombre");
					columnData[3]=rs.getString("Apellido");
					columnData[4]=rs.getString("Genero");
					columnData[5]=rs.getString("FechaNacimiento");
					columnData[6]=rs.getString("Edad");
					columnData[7]=rs.getString("Salario");
					
					model.addRow(columnData);
				}
			}
			catch(Exception e) 
			{
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Empleados window = new Empleados();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public Empleados() {
		initialize();
		
		Connection conn = DatabaseConnection.ConnectDB();
		Object col[] = {"IDEmp","NSS","Nombre","Apellido","Genero","DiaNacimiento","Edad","Salario"};
		model.setColumnIdentifiers(col);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID Empleado");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNewLabel.setBounds(32, 137, 122, 32);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.BOLD, 18));
		textField.setBounds(207, 137, 170, 32);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO empleados(IDEmpleado,NSS,Nombre,Apellido,Genero,FechaNacimiento,Edad,Salario)VALUES(?,?,?,?,?,?,?,?)";
				
				try 
				{
					pst = conn.prepareStatement(sql);
					pst.setString(1, textField.getText());
					pst.setString(2, textField_1.getText());
					pst.setString(3, textField_2.getText());
					pst.setString(4, textField_3.getText());
					pst.setString(5, textField_4.getText());
					pst.setString(6, textField_5.getText());
					pst.setString(7, textField_6.getText());
					pst.setString(8, textField_7.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
				}
				
				catch(Exception ev)
				{			
					JOptionPane.showMessageDialog(null, "Actualizacion del Sistema Completo");
				}
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model .addRow(new Object[] {
						textField.getText(),
						textField_1.getText(),
						textField_2.getText(),
						textField_3.getText(),
						textField_4.getText(),
						textField_5.getText(),
						textField_6.getText(),
						textField_7.getText()
				});
				if(table.getSelectedRow() == -1) 
				{
					if(table.getRowCount() == 0) 
					{
						JOptionPane.showMessageDialog(null, "Actualizacion de Datos Confirmada", "Sistema de Base de Datos de Empleado",
								JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnNewButton.setBounds(200, 648, 177, 55);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(389, 137, 783, 469);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Dialog", Font.BOLD, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"IDEmpleado", "NSS", "Nombre", "Apellido", "Genero", "Fecha Nacimiento", "Edad", "Salario"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNss = new JLabel("NSS");
		lblNss.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNss.setBounds(32, 198, 89, 32);
		frame.getContentPane().add(lblNss);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNombre.setBounds(32, 259, 89, 32);
		frame.getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Dialog", Font.BOLD, 18));
		lblApellido.setBounds(35, 320, 89, 32);
		frame.getContentPane().add(lblApellido);
		
		JLabel lblGenero = new JLabel("Genero");
		lblGenero.setFont(new Font("Dialog", Font.BOLD, 18));
		lblGenero.setBounds(32, 380, 89, 32);
		frame.getContentPane().add(lblGenero);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento");
		lblFechaNacimiento.setFont(new Font("Dialog", Font.BOLD, 18));
		lblFechaNacimiento.setBounds(33, 443, 177, 32);
		frame.getContentPane().add(lblFechaNacimiento);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setFont(new Font("Dialog", Font.BOLD, 18));
		lblEdad.setBounds(35, 505, 89, 32);
		frame.getContentPane().add(lblEdad);
		
		JLabel lblSalario = new JLabel("Salario");
		lblSalario.setFont(new Font("Dialog", Font.BOLD, 18));
		lblSalario.setBounds(32, 574, 89, 32);
		frame.getContentPane().add(lblSalario);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.BOLD, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(207, 198, 170, 32);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.BOLD, 18));
		textField_2.setColumns(10);
		textField_2.setBounds(207, 259, 170, 32);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Dialog", Font.BOLD, 18));
		textField_3.setColumns(10);
		textField_3.setBounds(207, 320, 170, 32);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.BOLD, 18));
		textField_4.setColumns(10);
		textField_4.setBounds(207, 380, 170, 32);
		frame.getContentPane().add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Dialog", Font.BOLD, 18));
		textField_5.setColumns(10);
		textField_5.setBounds(207, 443, 170, 32);
		frame.getContentPane().add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Dialog", Font.BOLD, 18));
		textField_6.setColumns(10);
		textField_6.setBounds(207, 505, 170, 32);
		frame.getContentPane().add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Dialog", Font.BOLD, 18));
		textField_7.setColumns(10);
		textField_7.setBounds(207, 574, 170, 32);
		frame.getContentPane().add(textField_7);
		
		JButton btnM = new JButton("Imprimir");
		btnM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MessageFormat header = new MessageFormat("Impresion en progreso");
				MessageFormat footer = new MessageFormat("Page {0, number, integer}");
				
				try {
					table.print();
				}
				catch(java.awt.print.PrinterException ev) {
					System.err.format("No se encontro Impresora", ev.getMessage());
				}
			}
		});
		btnM.setBounds(523, 648, 177, 55);
		frame.getContentPane().add(btnM);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				textField_1.setText(null);
				textField_2.setText(null);
				textField_3.setText(null);
				textField_4.setText(null);
				textField_5.setText(null);
				textField_6.setText(null);
				textField_7.setText(null);
			}
		});
		btnReset.setBounds(766, 648, 177, 55);
		frame.getContentPane().add(btnReset);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirma si Deseas Salir", "Sistema de Base de Datos de Empleados",
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
						System.exit(0);
					}
			}
		});
		btnSalir.setBounds(993, 648, 177, 55);
		frame.getContentPane().add(btnSalir);
		
		JLabel lblSistemaDeManejo = new JLabel("Sistema de Manejo de Base de Datos de Empleados");
		lblSistemaDeManejo.setFont(new Font("Dialog", Font.BOLD, 32));
		lblSistemaDeManejo.setBounds(207, 45, 822, 32);
		frame.getContentPane().add(lblSistemaDeManejo);
	}
}