/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {

	private String componente;
	private static Set rightFormulas = new TreeSet();
	private static Set wrongFormulas = new TreeSet();

	private static String regExpToDiscardRFormulas = "([)]+[a-z]|[a-z]+[a-z]|[R][^a-z]?[1-9]?[0-9]*)";//No R o mon... quitar las formulas que estan mal
	private String regExpToGetSubformulasWithElement;//subformulas
	//private static String regExpGetNumberAfterParenthesis = "[)][1-9]?[0-9]*"; //sacar los numeros que multiplican a las subformulas
	private String regexpGetElementsComponentInSimpleFormula;
	//private static String regExpEliminateParenthesis = "([^0-9]+)";
	//private static String regExpGetAllNumbers = "([1-9]+)";

	Map<String, Integer> FormulaMap = new HashMap<String, Integer>();

	public Filter(String componente) {
		this.componente = componente;
		this.regExpToGetSubformulasWithElement = "([(](.*)" + componente + "(.*)[)]+[1-9]?[0-9]*)";//subformulas
		this.regexpGetElementsComponentInSimpleFormula = "(" + componente + "[1-9][0-9]*|" + componente + "[^a-z])|" + componente + "\\Z"; //seleccionar los carbonos (para sumar)
	}

	private int getNumberElementsInFormula(String formulaToGetElements) {
		int numAparicionesComponente = 0;
		String copyOfFormula = formulaToGetElements;
		int multiplyingNumber = 0;
		int originalNumber = 0;
		Pattern pat = Pattern.compile(regExpToGetSubformulasWithElement);
		Matcher mat = pat.matcher(copyOfFormula);
		while (mat.find()) {
			String subformula = mat.group();
			copyOfFormula = copyOfFormula.replace(subformula, "");

			// recorremos el array desde detras hacia delante hasta que encontremos el primer parentesis
			String multiplicador = "";
			int multiplicadorInt = 0;
			for (int i = subformula.length() - 1; i >= 0; i--) {
				Character c = subformula.charAt(i);

				if (Character.isDigit(c)) {
					multiplicador = c + multiplicador;
				} 
				else {
					break;
				}
			}
			if (multiplicador.isEmpty()){// si esta vacio, el multiplicador es 1
				multiplicadorInt = 1;
			} 
			else {
				multiplicadorInt = Integer.parseInt(multiplicador);
				int lengthMultiplicador = multiplicador.length();
				subformula = subformula.substring(0, subformula.length() - lengthMultiplicador);
			}
			subformula = subformula.substring(1, subformula.length() - 1);
			// Debemos obtener el numero o numeros del final 
			// si no encuentro numeros detras de la subformula aparece 1 vez dicha subformula
			// si los encuentro, me quedo el numero y multiplico luego las aparciiones por ese nÃºmero.
			// Elimino los parentesis despues de quedarme con el numero. ¿Como? Substring desde 1 a length - 1

			if (subformula.contains("(")) {

				numAparicionesComponente += getNumberElementsInFormula(subformula);
			} else {
				numAparicionesComponente += getNumberofComponents(subformula);

			}
			numAparicionesComponente = numAparicionesComponente * multiplicadorInt;
		}
		numAparicionesComponente += getNumberofComponents(copyOfFormula);
		return numAparicionesComponente;
	}
	private int getNumberofComponents(String formula) {
		Pattern pat = Pattern.compile(regexpGetElementsComponentInSimpleFormula);
		Matcher mat = pat.matcher(formula);
		int num = 0;
		int num2 = 0;
		while (mat.find()) {
			String elementAndValue = mat.group();
			String numreplace= elementAndValue.replaceAll("([^0-9]+)", "");
			try{
				num2 = Integer.parseInt(numreplace);

			}
			catch (NumberFormatException nf)
			{
				num2=0;}

			if (num2 == 0) {
				num += 1; // si es C sin numero, debes sumar 1
			} else {

				num += num2;
			}
		}
		return num;
	}

	public static boolean isCorrectFormula(String formula)//Collection<String> formula) //funciona bien
	{
		Pattern pat = Pattern.compile(regExpToDiscardRFormulas);
		Matcher mat = pat.matcher(formula);
		if (mat.find()) {
			return false;
		}
		else {
			return true;
		}
	}

	public static Set discardformulas(Set<String> formula)//Collection<String> formula) //funciona bien
	{
		for (String s : formula) { 
			Pattern pat = Pattern.compile(regExpToDiscardRFormulas);
			Matcher mat = pat.matcher(s);
			if (mat.find()) {
				wrongFormulas.add(s);
			}
			else {
				rightFormulas.add(s);//se añada las buenas al array
			}
		}
		return rightFormulas;

	}
	private static void printFormulasAndCompounds(String componente, Map<String, Integer> element)
	{
		System.out.println("Componente "+ componente+ " :");
		Iterator<String> it = element.keySet().iterator();
		while(it.hasNext()){
			String form = it.next();
			System.out.println("Formula: " + form + " -> " + componente + " :" + element.get(form));
		}
	}

	public static void createCompoundMap (Map<String, Set<Integer>> mapFormulasCompoundIds) {
		Map<String, Integer> ElementoC = new HashMap<String, Integer>();
		Map<String, Integer> ElementoN = new HashMap<String, Integer>();
		Map<String, Integer> ElementoCl = new HashMap<String, Integer>();
		Map<String, Integer> ElementoO = new HashMap<String, Integer>();
		Map<String, Integer> ElementoH = new HashMap<String, Integer>();
		Map<String, Integer> ElementoP = new HashMap<String, Integer>();
		Map<String, Integer> ElementoS = new HashMap<String, Integer>();
		Set<String> formulas = mapFormulasCompoundIds.keySet();
		String compounds[] = {  "C","N","Cl","O","H","P","S"};
		for (String formula: formulas) {
			int numC=0,numN=0,numCl=0,numO=0,numH=0,numP=0,numS=0;
			for(int j=0; j<compounds.length; j++) {
				Filter filter = new Filter(compounds[j]);  
				if(compounds[j].equals("C")) {
					numC= filter.getNumberElementsInFormula(formula);
					ElementoC.put(formula, numC );
				}
				else if (compounds[j].equals("N")){
					numN= filter.getNumberElementsInFormula(formula);
					ElementoN.put(formula, numN );
				}
				else if (compounds[j].equals("Cl")){ 
					numCl= filter.getNumberElementsInFormula(formula);
					ElementoCl.put(formula, numCl );
				}
				else if (compounds[j].equals("O")){ 
					numO= filter.getNumberElementsInFormula(formula);
					ElementoO.put(formula, numO );
				}
				else if (compounds[j].equals("H")){ 
					numH= filter.getNumberElementsInFormula(formula);
					ElementoH.put(formula, numH );
				}
				else if (compounds[j].equals("P")){ 
					numP= filter.getNumberElementsInFormula(formula);
					ElementoP.put(formula, numP );
				}
				else if (compounds[j].equals("S")){ 
					numS= filter.getNumberElementsInFormula(formula);
					ElementoS.put(formula, numS );
				}
				//introducir la formula en la base de datos
			}
			Set<Integer> compound_ids = mapFormulasCompoundIds.get(formula);
			for(Integer compound_id : compound_ids)
			{
				InsertTabla.insertElement(compound_id, numC, numN, numCl, numO, numH, numP, numS,formula);
			}
		}
	}

	/* public static void main(String[] args) {

    	Set<String> formulasToTest = new TreeSet();
        formulasToTest.add("C12H3");
        formulasToTest.add("(H2C15Cl)2H3C4");
        formulasToTest.add("(2(C14Na15C3)Cl)mon");
        formulasToTest.add("(H4NaC2)Cl");
        formulasToTest.add("(H2C15Cl(C2)2)2H3C4");
        formulasToTest.add("(P2N15Cl(O2)2)2S3N4C4");

        Set <String> posibleFormulas = new TreeSet();
        posibleFormulas = discardformulas(formulasToTest);

        // crea conjunto de tests. 
        // definir una secuencia de strings concenitendo diferentes formulas
        // y calcular manulamente los C. 
        // posteriormente llamar a mi funcion calcular carbonos y comprobar que es correcta
        // Ejemplo

    	Map<String, Integer> ElementoC = new HashMap<String, Integer>();
		Map<String, Integer> ElementoN = new HashMap<String, Integer>();
		Map<String, Integer> ElementoCl = new HashMap<String, Integer>();
		Map<String, Integer> ElementoO = new HashMap<String, Integer>();
		Map<String, Integer> ElementoH = new HashMap<String, Integer>();
		Map<String, Integer> ElementoP = new HashMap<String, Integer>();
		Map<String, Integer> ElementoS = new HashMap<String, Integer>();

    	String compounds[] = {  "C","Cl","N","O","H","P","S"};
    	for(int j=0; j<compounds.length; j++) {
        	Filter filter = new Filter(compounds[j]);  
        	for (String formula: posibleFormulas) {

        		if(compounds[j].equals("C")) {
        			int numC= filter.getNumberElementsInFormula(formula);
        			ElementoC.put(formula, numC );
        		}
        		else if (compounds[j].equals("N")){
        			int numN= filter.getNumberElementsInFormula(formula);
        			ElementoN.put(formula, numN );
        		}
        		else if (compounds[j].equals("Cl")){ 
        			int numCl= filter.getNumberElementsInFormula(formula);
        			ElementoCl.put(formula, numCl );
        		}
        		else if (compounds[j].equals("O")){ 
        			int numO= filter.getNumberElementsInFormula(formula);
        			ElementoO.put(formula, numO );
        		}
        		else if (compounds[j].equals("H")){ 
        			int numH= filter.getNumberElementsInFormula(formula);
        			ElementoH.put(formula, numH );
        		}
        		else if (compounds[j].equals("P")){ 
        			int numP= filter.getNumberElementsInFormula(formula);
        			ElementoP.put(formula, numP );
        		}
        		else if (compounds[j].equals("S")){ 
        			int numS= filter.getNumberElementsInFormula(formula);
        			ElementoS.put(formula, numS );
        		}
        	}
        	if(compounds[j].equals("C")) {
        		filter.printFormulasAndCompounds(compounds[j],ElementoC);
        	}
        	else if (compounds[j].equals("N")){
        		filter.printFormulasAndCompounds(compounds[j],ElementoN);
        	}
        	else if (compounds[j].equals("Cl")){
        		filter.printFormulasAndCompounds(compounds[j],ElementoCl);
        	}
        	else if (compounds[j].equals("O")){ 
        		filter.printFormulasAndCompounds(compounds[j],ElementoO);
        	}
        	else if (compounds[j].equals("H")){ 
        		filter.printFormulasAndCompounds(compounds[j],ElementoH);
        	}
        	else if (compounds[j].equals("P")){ 
        		filter.printFormulasAndCompounds(compounds[j],ElementoP);
        	}
        	else if (compounds[j].equals("S")){ 
        		filter.printFormulasAndCompounds(compounds[j],ElementoS);
        	}
        }

    }*/
}
