package project;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class User { //sin comentarios...
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public void search() throws IOException {	
		System.out.println("Do you want to search for a formula? YES/NO");
		String response = reader.readLine();
		if(response.equalsIgnoreCase("YES")) {
			String searchingElements []= {"Carbons", "Nitrogens", "Chlorines"};
			for (int i =0; i<searchingElements.length; i++)
			{
				System.out.println("Do you want search by " +searchingElements[i]+ ":  YES/NO");
				response = reader.readLine();
				if (response.equalsIgnoreCase("YES"))
				{
					int minC=0, maxC=0, minN=0, maxN=0, minCl=0, maxCl=0; 
					//int[] range = {};
					//range=getrange();
					System.out.println("Introduce the minimum value of the searching filter range for ");
					int minvalue = Integer.parseInt(reader.readLine());
					System.out.println("Introduce the maximum value of the searching filter range for ");
					int maxvalue = Integer.parseInt(reader.readLine());
					
					if(searchingElements.equals("Carbons")) {
											
					}
					else if (searchingElements.equals("Nitrogens")) {
						
					}
					else if (searchingElements.equals("Chlorines")) {
						
					}
				}
			}
			
			
		}
		else if (response.equalsIgnoreCase("NO")) {
			System.out.println("Finished");
		}
		else {
			System.out.println("Please introduce YES or NO");
			search();
		}
	}
	/*private static int[] getrange() throws NumberFormatException, IOException {
		System.out.println("Introduce the minimum value of the searching filter range for ");
		int minvalue = Integer.parseInt(reader.readLine());
		System.out.println("Introduce the maximum value of the searching filter range for ");
		int maxvalue = Integer.parseInt(reader.readLine());
		int[] range = {};
		for (int i=0; i<=(minvalue-maxvalue+1); i++) {
			range[i]=minvalue;
			minvalue++;
		}
		return range;
	}*/
	
}
