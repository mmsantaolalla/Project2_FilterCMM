package project;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class User { 
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static Map<String, Set<Integer>> search() throws IOException {	
		int minvalue =0,maxvalue=0;
		Map<String, Set<Integer>> concreteMap = new HashMap();
		int minC=0, maxC=0, minN=0, maxN=0, minCl=0, maxCl=0, minO=0, maxO=0, minH=0, maxH=0, minP=0, maxP=0, minS=0, maxS=0;
		String searchingElements []= {"Carbons", "Nitrogens", "Chlorines", "Oxygens", "Hydrogens", "Phosphorus", "Sulfurs"};
		for (int i =0; i<searchingElements.length; i++)
		{
			System.out.println("Do you want search by " +searchingElements[i]+ "?:  YES/NO");
			String response = reader.readLine();
			
			while (!(response.equalsIgnoreCase("YES") | response.equalsIgnoreCase("NO"))) {
				System.out.println("Introduced instruction not recogniced, please write YES or NO");
				response=reader.readLine();
			}
			if (response.equalsIgnoreCase("YES"))
			{
				System.out.println("Introduce the minimum value of the searching filter range for "+ searchingElements[i]+ ":");
				minvalue = Integer.parseInt(reader.readLine());
				System.out.println("Introduce the maximum value of the searching filter range for "+ searchingElements[i]+ ":");
				maxvalue = Integer.parseInt(reader.readLine());
			}
			else //(response.equalsIgnoreCase("NO")) 
			{
				minvalue= 0;
				maxvalue= 1000; //REVISAR ESTA REFERENCIA!!, ES PARA QUE COJA TODAS
			}

			//save the values for each element
			if(searchingElements[i].equals("Carbons")) {
				minC= minvalue;
				maxC= maxvalue;				
			}
			else if (searchingElements[i].equals("Nitrogens")) {
				minN= minvalue;
				maxN=maxvalue;
			}
			else if (searchingElements[i].equals("Chlorines")) {
				minCl = minvalue;
				maxCl=maxvalue;
			}
			else if (searchingElements[i].equals("Oxygens")) {
				minO = minvalue;
				maxO=maxvalue;
			}
			else if (searchingElements[i].equals("Hydrogens")) {
				minH = minvalue;
				maxH=maxvalue;
			}
			else if (searchingElements[i].equals("Phosphorus")) {
				minP = minvalue;
				maxP=maxvalue;
			}
			else{ //Sulfurs
				minS = minvalue;
				maxS=maxvalue;
			}
		}
			
		concreteMap = UserSerching.searchingElement(minC, maxC, minN, maxN, minCl, maxCl, minO, maxO, minH, maxH, minP, maxP, minS, maxS);
		return concreteMap;
	}

	
}
