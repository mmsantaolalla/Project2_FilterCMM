package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) {
		Map<String, Set<Integer>> allFormulas;
		allFormulas = Testconecction.conectionStatement();
	    
	    System.out.println("_________");
	    System.out.println(allFormulas.keySet());
	    System.out.println("_________");
	    
	    Filter.createCompoundMap(allFormulas);
	}
}
