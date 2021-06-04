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
			
	static { //so that it doesn't load the controller every time, we put it outside
		try {
			Class.forName(controller); //for mysql database manager, use this string
		
		} catch (Exception e) {
			System.out.println("Error loading the driver");
			e.printStackTrace();
		}
	}
	public Connection conect () {
		Connection link = null;  //since getConnection returns an element of type connection
		try {
			link = DriverManager.getConnection(url,user,password); //class already existing in java, to establish the connection, you need 3 strings
			//first parameter: a connection string: jdbc: name of the database manager: server ip: port (3306 by default): database name
			//second and third parameters: username and password
			
			System.out.println("\n*Connected to the data base*"); 
			
		
		}catch(SQLException e) { //required exception the DriverManager class ...
			System.out.println("Error in the connection");
			e.printStackTrace();
		}
		
		return link;
	}

}
