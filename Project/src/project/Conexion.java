package project;
//creates the conexion between the mysql data base and eclipse

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private static final String controller = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/compounds";
	private static final String user = "root";
	private static final String password = "1234";
			
	static {//para que no cargue el controlador cada vez, lo ponemos fuera
		try {
			Class.forName(controller); //para el gestor de bases de datos mysql, se usa esta cadena
		
		} catch (Exception e) {
			System.out.println("Error loading the driver");
			e.printStackTrace();
		}
	}
	public Connection conect () {
		Connection link = null; //ya que getConnection devuelve un elemento de tipo connection
		try {
			link = DriverManager.getConnection(url,user,password); //clase ya existente en java, para establecer la conexion, necesita 3 strings
			//primer parametro: una cadena de conexion: jdbc: nombre del gestor de la base de datos: ip del servidor : puerto (3306 por defecto) : nombre base de datos
			//segundo y tercer parametro: usuario y contraseña
			
			System.out.println("Connected"); 
			
		
		}catch(SQLException e) { //excepción necesaria la clase DriverManager..
			System.out.println("Error in the connection");
			e.printStackTrace();
		}
		
		return link;
	}
	
	//public void
	

}
