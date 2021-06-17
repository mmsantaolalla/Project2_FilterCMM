package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		
		try {
			System.out.println("Do you want to search for a formula? YES/NO");
			String responseSearch = reader.readLine();
			while (!(responseSearch.equalsIgnoreCase("YES") | responseSearch.equalsIgnoreCase("NO"))) {
				System.out.println("Introduce instruction not recogniced, introduce YES or NO");
				responseSearch=reader.readLine();
			}
			if(responseSearch.equalsIgnoreCase("YES")) {
				Collection<Compound_pojo> implementedMap = new TreeSet<Compound_pojo>();
				implementedMap = User.search();
				if(implementedMap.isEmpty())
				{
					System.out.println("Formula not found for those characteristics");
				}
				for (Compound_pojo formula: implementedMap)
				{
					System.out.println(formula);
				}
			}	
			else if (responseSearch.equalsIgnoreCase("NO")) {
					return;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void mainUser() throws IOException {
		
		System.out.println("Do you want to search for a formula? YES/NO");
		String responseSearch = reader.readLine();
		if(responseSearch.equalsIgnoreCase("YES")) {
			Map<String, Set<Integer>> implementedMap = new HashMap();
			implementedMap = User.search();
			if(implementedMap.isEmpty())
			{
				System.out.println("Formula not found for those characteristics");
			}
			for (String formula: implementedMap.keySet())
			{
				System.out.println("\n" + formula);
				System.out.println(implementedMap.get(formula));	
			}
		}	
		else if (responseSearch.equalsIgnoreCase("NO")) {
				return;
		}
		else {
			System.out.println("Please introduce YES or NO");
			mainUser();
		}
	}*/
	
}
