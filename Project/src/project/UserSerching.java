package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

public class UserSerching {
	/*
	 * It receives the min and max occurrences of each element to filter the compounds
	 * 
	 * @param Cmin 
	 * @param Cmax
	 * 
	 * @return the set of compounds that fulfill the requirements
	 */
	// you can add the mass as a filter too. It is up to you. You need to find some testing element to show in presentation
	
	public static Collection<Compound_pojo> searchingElement(int Cmin, int Cmax, int Nmin, int Nmax, 
			int Clmin, int Clmax, int Omin, int Omax, int Hmin, int Hmax, int Pmin, int Pmax, int Smin, int Smax){
		Set<Compound_pojo> compoundSet = new TreeSet<Compound_pojo>();
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		ResultSet infoFormulas = null;

		try {
			cn = conexion.conect();
			stm = cn.createStatement();
			infoFormulas= stm.executeQuery("SELECT ce.compound_id, compound_name, formula, mass FROM compound_elements ce inner join compounds c "
					+ "on c.compound_id=ce.compound_id"
					+ " WHERE (C BETWEEN " + Cmin+ " AND " + Cmax+ ") AND (N BETWEEN " +Nmin+ " AND " + Nmax+ ") "
							+ "AND (Cl BETWEEN " +Clmin+ " AND " +Clmax+ ") AND (O BETWEEN " +Omin+ " AND " +Omax+ ") "
									+ "AND (H BETWEEN " +Hmin+ " AND " +Hmax+ ") "
											+ "AND (P BETWEEN " +Pmin+ " AND " +Pmax+ ") "
													+ "AND (S BETWEEN " +Smin+ " AND " +Smax+ ")");
			
			while(infoFormulas.next())
			{
				String formula = infoFormulas.getString("formula"); //como lo hago si enseña la formula, pero en mi tabla no la hay
				int compound_id = infoFormulas.getInt("compound_id");
				double mass = infoFormulas.getDouble("mass");
				String name = infoFormulas.getString("name");
				
				// get mass and name
				// create a new compound
				Compound_pojo c = new Compound_pojo(compound_id, formula, mass, name);
				// compoundSet.add(c); // mi entidad compound es un POJO con 4 atributos (ver tipo) con getters (no hacen 
				// falta setters. SI HACE FALTA toString y Equals (identidad es el compound_id). 
				// y la interfaz Comparable<Compound> y el método compareTo().
				// 
				compoundSet.add(c);
				//System.out.println(compound_id);
			}
			return compoundSet;
				
			
			
		} catch (SQLException e) {
			e.printStackTrace();

		}finally { //close connections
			try {
				if (infoFormulas!=null) {
					infoFormulas.close();
				} 
				if(stm!=null) {
					stm.close();
				}
				if(cn!=null) {
					cn.close();
				}

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return formulas;
	}
	
	/*public static void main(String[] args) { //main used to check this class while implementing it
		Map<String, Set<Integer>> myMap = UserSerching.searchingElement(2, 9, 1, 1, 0, 0, 0, 10, 0, 10, 0, 10, 0, 1);
		for (String formula: myMap.keySet())
		{
			System.out.println(formula);
			System.out.println(myMap.get(formula));	
		}
	}*/
}
