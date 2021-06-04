package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Testconnection {
	public static Map<String, Set<Integer>> conectionStatement (){
		Map<String, Set<Integer>> formulas = new HashMap();
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		ResultSet infoFormulas = null;

		try {
			cn = conexion.conect();
			stm = cn.createStatement();
			infoFormulas= stm.executeQuery("SELECT formula, compound_id FROM compounds WHERE formula is not null ORDER BY compound_id LIMIT 152948,20");
			while(infoFormulas.next())
			{

				String formula = infoFormulas.getString("formula");
				int compound_id = infoFormulas.getInt("compound_id");
				boolean formulaValida = Filter.isCorrectFormula(formula);
				if(formulaValida){

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
			}
			
		} catch (SQLException e) {
			e.printStackTrace();

		}finally { //close
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
