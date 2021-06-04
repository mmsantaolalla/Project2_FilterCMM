package project;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

//import com.sun.tools.javac.util.List;

public class TestFormulasElements {
	
	public static void main(String[] args) {
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		ResultSet infoFormulas = null;
		//Set<String> tryingFormulas = new TreeSet();
		
		// añades formulas del tipo C4H6
	/*	Map<String, Set<Integer>> formulasToTest = new HashMap();
		Set<Integer> compound_ids_invented = new TreeSet();
		compound_ids_invented.add(1);
		formulasToTest.put("(C3H6NS2)3.Fe",compound_ids_invented);
		Filter.createCompoundMap(formulasToTest);
		compound_ids_invented.add(2);
		formulasToTest.put("C2H6O",compound_ids_invented);
		Filter.createCompoundMap(formulasToTest);
		compound_ids_invented.add(3);
		formulasToTest.put("(2(C14Na15C3)Cl)mon",compound_ids_invented);//shouldn't found any like it, as it should be discard
		Filter.createCompoundMap(formulasToTest);
		*/
		
		/*
		compound_ids_invented.add(4);
		formulasToTest.put("C20H30O2",compound_ids_invented);
		Filter.createCompoundMap(formulasToTest);
		compound_ids_invented.add(5);
		formulasToTest.put("C39H65NO14",compound_ids_invented);
		Filter.createCompoundMap(formulasToTest);
		compound_ids_invented.add(6);
		formulasToTest.put("C39H65NO14",compound_ids_invented);
		Filter.createCompoundMap(formulasToTest);
		*/
		
		// select de la BBDD de los compound_ids que tengan esa formula
		try {
				cn = conexion.conect();
				
				// obtengo una lista de formulas
				infoFormulas= stm.executeQuery("SELECT distinct formula FROM compounds");
				// Filtrar formulas y coger solo validas
				// Set formulas (PUEDE VENIR DE UNA QUERY O FIJADO POR TI)
				
				// meter en un bucle for para cada formula
				{
					// get IDS from formula para cada formula (COMPOUNDS)
					
					// get IDS from formula para cada formula (COMPOUND_ELEMENTS)
					
					// COMPARAR SET DE IDS FROM COMPOUNDS Y FROM ELEMENTS. PRINT FOMRULA OK IF THEY CONTAIN SAME ELEMENTS
					// PRINT ERROR AND IDS FROM BOTH TABLES IF THEY HAVE DIFFERENCES 
					
				}
				stm = cn.createStatement();
				
				while(infoFormulas.next())
				{
					String eachFormula = infoFormulas.getString("formula");
					Set<Integer> compound_ids = new HashSet<>();
					//List<Set<Integer>> compound_ids =  new ArrayList<>();
					compound_ids.add(infoFormulas.getInt("compound_id"));
					System.out.println(eachFormula + "ids:" +compound_ids);
				}
			}
		catch (SQLException e) {
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
	
		// sé el resultado a priori. 
		/*//este no vale!!!!!!!
		 System.out.println("(C3H6NS2)3.Fe: ids = 1 y  153713");
		 System.out.println("C2H6O: ids = 2 y  153711");
		 System.out.println("(2(C14Na15C3)Cl)mon: ids = 3"); 
		 //solo el introducido por mi, solo debe de tener el mio, ya que en la base de datos se deberia haber descartado
		 System.out.prinln("C39H65NO14" : ids = 
		 */
		
		
		// compruebo de forma automatica que para cada formula obtenga los resultados esperados. 
		//select C,N, ... , from compound_elements where compound_id = 5; La formula de 5 la conozco
		/*
		try {
			cn = conexion.conect();
			stm = cn.createStatement();
			//infoFormulas= stm.executeQuery("SELECT C,N,Cl,O,H,P,S,formula FROM compound_elements WHERE compound_id= 153718");
			infoFormulas= stm.executeQuery("SELECT C,N,Cl,O,H,P,S,formula FROM compound_elements WHERE compound_id= 153713");
			while(infoFormulas.next())
			{
				String eachFormula = infoFormulas.getString("formula");
				int C = infoFormulas.getInt("C");				
				int N = infoFormulas.getInt("N");
				int Cl = infoFormulas.getInt("Cl");
				int O = infoFormulas.getInt("O");
				int H = infoFormulas.getInt("H");
				int P = infoFormulas.getInt("P");
				int S = infoFormulas.getInt("S");	
				System.out.println("By hand:");
				//System.out.println("compound_id 153718 formula = C20H22N2O2");
				int expectedC = 20;
				
				if(check all elements con los elementos esperados
				
				//System.out.println("C=20, N=2, Cl=0, O=2, H=22, P=0, S=0");
				System.out.println("compound_id 153713 formula = (C3H6NS2)3.Fe");
				System.out.println("C=9, N=3, Cl=0, O=0, H=18, P=0, S=6");
				
				System.out.println("Filter:");
				//System.out.println("compound_id 153718 formula = " + eachFormula);
				System.out.println("compound_id 153713 formula = " + eachFormula);
				System.out.println("C=" +C+ ", N=" +N+ ", Cl=" +Cl+ ", O=" +O+ ", H=" +H+ ", P=" +P+ ", S=" +S);
			}
		}
	catch (SQLException e) {
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
	}*/
		
		
		// ["C","N","Cl","O","H","P","S"]
		// select C,N, ... , from compound_elements where compound_id = 5; La formula de 5 la conozco
		
		// compruebo que los elementos son los correctos para 10/15 formulas. 
		
	}
	
}


