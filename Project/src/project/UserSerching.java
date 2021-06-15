package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UserSerching {
	public static Map<String, Set<Integer>> searchingElement(int Cmin, int Cmax, int Nmin, int Nmax, int Clmin, int Clmax, int Omin, int Omax, int Hmin, int Hmax, int Pmin, int Pmax, int Smin, int Smax){
		Map<String, Set<Integer>> formulas = new HashMap();
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		ResultSet infoFormulas = null;

		try {
			cn = conexion.conect();
			stm = cn.createStatement();//habrá que quitar la formula más adelante de esta tabla y llamar a la otra segun el compound id encontrado
			infoFormulas= stm.executeQuery("SELECT compound_id,formula FROM compound_elements WHERE (C BETWEEN " + Cmin+ " AND " + Cmax+ ") AND (N BETWEEN " +Nmin+ " AND " + Nmax+ ") AND (Cl BETWEEN " +Clmin+ " AND " +Clmax+ ") AND (O BETWEEN " +Omin+ " AND " +Omax+ ") AND (H BETWEEN " +Hmin+ " AND " +Hmax+ ") AND (P BETWEEN " +Pmin+ " AND " +Pmax+ ") AND (S BETWEEN " +Smin+ " AND " +Smax+ ")");
			
			while(infoFormulas.next())
			{
				String formula = infoFormulas.getString("formula"); //como lo hago si enseña la formula, pero en mi tabla no la hay
				int compound_id = infoFormulas.getInt("compound_id");//enseñar el compound id?
				if(formulas.containsKey(formula))
				{
					Set<Integer> compound_ids_from_formula = formulas.get(formula);
					compound_ids_from_formula.add(compound_id);
				}
				else {
					Set compound_ids = new TreeSet<Integer>();
					compound_ids.add(compound_id);
					formulas.put(formula, compound_ids);
				}
				//System.out.println(compound_id);
			}
				
			
			
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
