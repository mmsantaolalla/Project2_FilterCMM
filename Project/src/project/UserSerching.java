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
	public static Map<String, Set<Integer>> searchingElement(String compound,int min, int max){
		Map<String, Set<Integer>> formulas = new HashMap();
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		ResultSet infoFormulas = null;

		try {
			cn = conexion.conect();
			stm = cn.createStatement();
			//for(int i=0; i<=rangeElement.length; i++)
			//{
			//	int num = rangeElement[i];
			
				infoFormulas= stm.executeQuery("SELECT * FROM compound_elements WHERE" +compound+ "BETWEEN " + min+ "AND" + max);
			//}
			
			while(infoFormulas.next())
			{

				String formula = infoFormulas.getString("formula");
				int compound_id = infoFormulas.getInt("compound_id");
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
			}
				//System.out.println(formula);
			
			
		} catch (SQLException e) {
			e.printStackTrace();

		}finally { //cerramos todo
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
}
