package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

public class InsertTable {
	public static void insertElement (Integer compound_id, int numC, int numN, int numCl, int numO, int numH, int numP, int numS, String form){
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		
		try {
				cn = conexion.conect();
				String stmt = "INSERT INTO compound_elements (compound_id,C,N,Cl,O,H,P,S, formula) " + "VALUES (?,?,?,?,?,?,?,?,?)";
				PreparedStatement prep = cn.prepareStatement(stmt);
				prep.setInt(1, compound_id);
				prep.setInt(2, numC);
				prep.setInt(3, numN);
				prep.setInt(4, numCl);
				prep.setInt(5, numO);
				prep.setInt(6, numH);
				prep.setInt(7, numP);
				prep.setInt(8, numS);
				prep.setString(9, form);
		
				prep.executeUpdate();
				prep.close();
				
				System.out.println("Inserted: "+compound_id);

		} catch (Exception e) {
			e.printStackTrace();
		}

			finally { //close
			try {
				
				if(cn!=null) {
					cn.close();
				}
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
