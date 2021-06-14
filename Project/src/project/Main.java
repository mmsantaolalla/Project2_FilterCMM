package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		
		try {
			while (true) {
				int option = 3;
				boolean correctOption = false;
				do {
					System.out.println("\nOPTIONS:");
					System.out.println("1. Call the data base and filter it saving the results in the new table");
					System.out.println("2. Search for a concrete formula according to the number of occurrences of each element");
					System.out.println("0. Exit");
					System.out.print("\nPlease select an option: ");

					try {
						option = Integer.parseInt(reader.readLine());
						correctOption = true;
					} catch (NumberFormatException e) {
						System.out.println("\nInsert an integer please;");
					}
				} while (correctOption == false);
				
				switch (option) {
				case 1://call this function to create and store the filter
					mainFilterTable();
					break;
				case 2:
					mainUser(); //user main
					break;
				case 0:
					System.out.println("\nThank you for using our program!");
					System.exit(0);
					break;
				default:
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void mainFilterTable() throws IOException {
		System.out.println("**WARNING: It will only work if the table (compound_elements) is empty or a new formulas have been added to the database.*");
		System.out.println(" 	*If not, an exception will be shown as the are already filtered formulas**");
		System.out.println("Do you want to continue? YES/NO");
		String response= "";
		response = reader.readLine();
		while (!(response.equalsIgnoreCase("YES") | response.equalsIgnoreCase("NO"))) {
			System.out.println("Introduce instruction not recogniced, introduce YES or NO");
			response=reader.readLine();
		}
		if (response.equalsIgnoreCase("YES"))
		{
			Map<String, Set<Integer>> allFormulas;
			allFormulas = Testconnection.conectionStatement();
	    
			System.out.println("_________");
			System.out.println(allFormulas.keySet());
			System.out.println("_________");
	    
			Filter.createCompoundMap(allFormulas);
			System.out.println("The filter has been made and save for the formulas");
		}
		else  //(response.equalsIgnoreCase("NO"))
		{
			return;
		}
	}
	
	public static void mainUser() throws IOException {
		
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
	}
	
}
