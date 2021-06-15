package project;

import java.io.IOException;
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


public class TestFormulasElements {

	public static void testCompound () {
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		Statement stm_compounds = null;
		Statement stm_compound_elements =null;
		ResultSet infoFormulas = null;
		ResultSet id_compounds = null;
		ResultSet id_compound_elements = null;
		Set <String> FormulasToTest = new TreeSet();
		
		// select de la BBDD de los compound_ids que tengan esa formula
		try {
			cn = conexion.conect();
			// obtengo una lista de formulas
			//infoFormulas= stm.executeQuery("SELECT formula FROM compounds WHERE formula = '(C3H6NS2)3.Fe'");
			//while (infoFormulas.next()) {
				//tryingFormulas.add(infoFormulas.getString("formula"));
				FormulasToTest.add("(C3H6NS2)3.Fe");
				FormulasToTest.add("C39H65NO14");
				Filter.discardformulas(FormulasToTest); // Filtrar formulas y coger solo validas
				// Set formulas (PUEDE VENIR DE UNA QUERY O FIJADO POR TI)
				for (String formula : FormulasToTest)// meter en un bucle for para cada formula
				{
					Boolean correct = true;
					id_compounds = stm_compounds.executeQuery("SELECT compound_id FROM compounds WHERE formula = " + formula ); //hacen falta las comillas? '(C3H6NS2)3.Fe'
					// get IDS from formula para cada formula (COMPOUNDS)
					id_compound_elements = stm_compound_elements.executeQuery("SELECT compound_id FROM compound_elements WHERE formula = " + formula ); 
					// get IDS from formula para cada formula (COMPOUND_ELEMENTS)
					
					int tamcompounds = id_compounds.getFetchSize();
					int tamcompound_elements = id_compound_elements.getFetchSize();
					if(tamcompounds == tamcompound_elements) {// comprobar tamaño de las dos
						while (id_compounds.next()) {// recorrer resultSet y comparar uno por uno los ids.
							while(id_compound_elements.next()) {
								if(!(id_compounds.equals(id_compound_elements))) {
									correct = false;
								}
							}
						}
					}
					else {
						correct = false;
					}
					//for(int i =0; i<=tamcompounds; i++) {
						//Set<Integer> compound_ids = new HashSet<>();
						//compound_ids.add(id_compounds.getInt("compound_id"));
						//Set<Integer> compound_elements_ids = new HashSet<>();
						//compound_elements_ids.add(id_compound_elements.getInt("compound_id"));
					if(!correct) {
						System.out.println("It is incorrect!"); // PRINT ERROR AND IDS FROM BOTH TABLES IF THEY HAVE DIFFERENCES
						System.out.println("The ids from the formula: " + formula);
						System.out.println("In the table compounds = "); //+ compound_ids);
						System.out.println("In the table compound_elements = "); //+ compound_elements_ids);
					}
				}	
			
				//stm = cn.createStatement();
			}catch (SQLException e) {
				e.printStackTrace();

			}finally { //cerramos todo
				try {
					if (infoFormulas!=null) {
						infoFormulas.close();
					} 
					if (id_compounds!=null) {
						id_compounds.close();
					} 
					if (id_compound_elements!=null) {
						id_compound_elements.close();
					} 
					if(stm!=null) {
						stm.close();
					}
					if(stm_compounds!=null) {
						stm_compounds.close();
					}
					if(stm_compound_elements!=null) {
						stm_compound_elements.close();
					}
					if(cn!=null) {
						cn.close();
					}

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void manualFormulas () { //añades formulas del tipo C4H6
		Map<String, Set<Integer>> formulasToTest = new HashMap();
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
	}
	
	public static void checkFilter () {
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		ResultSet infoFormulas = null;
		try {
			cn = conexion.conect();
			stm = cn.createStatement();
			Boolean correct = false;
			String eachFormula = "";
			int C=0, N=0, Cl=0, O=0, H=0, P=0, S=0;
			int expectedC =0, expectedN =0, expectedCl =0, expectedO =0, expectedH =0, expectedP =0, expectedS =0; 
			int id_introduced = 153713; //153718;

			infoFormulas= stm.executeQuery("SELECT C,N,Cl,O,H,P,S,formula FROM compound_elements WHERE compound_id= " + id_introduced );
			while(infoFormulas.next())
			{
				
				eachFormula = infoFormulas.getString("formula");
				C = infoFormulas.getInt("C");				
				N = infoFormulas.getInt("N");
				Cl = infoFormulas.getInt("Cl");
				O = infoFormulas.getInt("O");
				H = infoFormulas.getInt("H");
				P = infoFormulas.getInt("P");
				S = infoFormulas.getInt("S");	
				
				expectedC = 9; //20;
				expectedN = 3; //2;
				expectedCl = 0;
				expectedO = 0; //2;
				expectedH = 18; //22;
				expectedP = 0;
				expectedS = 6; //0;
				// compruebo de forma automatica que para cada formula obtenga los resultados esperados.
				//check all elements con los elementos esperados
				if(C ==expectedC & N == expectedN & Cl == expectedCl & O == expectedO & H == expectedH & P == expectedP & S == expectedS) {
					correct= true;
				}

			}
			if(correct == true) {
				System.out.println ("The filtered elements from the formula are the same as the expected ones");
				System.out.println ("The filter works correctly");
			}
			else { //correct == false
				System.out.println ("The filtered elements from the formula and the expected ones doesn't ");
				System.out.println ("The expected elementes for the formula: " + eachFormula);
				System.out.println("C=" +expectedC+ ", N=" +expectedN+ ", Cl=" +expectedCl+ ", O=" +expectedO+ ", H=" +expectedH+ ", P=" +expectedP+ ", S="+ expectedS);
				System.out.println("and the filtered ones: ");
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
		}
	}
	
	public static void substractFormula() {
		Conexion conexion = new Conexion(); 
		Connection cn= null;
		Statement stm = null;
		ResultSet infoFormulas = null;
		try {
			cn = conexion.conect();
			stm.executeQuery("ALTER TABLE compound_elements DROP COLUMN formula");
			System.out.println("Column discarded successfully");
		}catch(Exception e) {
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
		
	}
	
	/*public static void main(String[] args) throws IOException {	
		//checkFilter();
		testCompound();
	}*/
}
