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

	private static String regExpToDiscardRFormulas = "([)]+[a-z]|[a-z]+[a-z]|[R][^a-z]?[1-9]?[0-9]*)";//No R or mon... takes out this wrong formulas
	private String regExpToGetSubformulasWithElement;//subformulas
	//private static String regExpGetNumberAfterParenthesis = "[)][1-9]?[0-9]*"; //know the subformulas multiplyier numbers
	private String regexpGetElementsComponentInSimpleFormula;
	//private static String regExpEliminateParenthesis = "([^0-9]+)";
	//private static String regExpGetAllNumbers = "([1-9]+)";

	Map<String, Integer> FormulaMap = new HashMap<String, Integer>();

	public Filter(String componente) {
		this.componente = componente;
		this.regExpToGetSubformulasWithElement = "([(](.*)" + componente + "(.*)[)]+[1-9]?[0-9]*)";//subformulas
		this.regexpGetElementsComponentInSimpleFormula = "(" + componente + "[1-9][0-9]*|" + componente + "[^a-z])|" + componente + "\\Z"; //select the component (to add)
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

			// we go through the array from back to front until we find the first parenthesis
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
			if (multiplicador.isEmpty()){ // if it is empty the multiplier is 1
				multiplicadorInt = 1;
			} 
			else {
				multiplicadorInt = Integer.parseInt(multiplicador);
				int lengthMultiplicador = multiplicador.length();
				subformula = subformula.substring(0, subformula.length() - lengthMultiplicador);
			}
			subformula = subformula.substring(1, subformula.length() - 1);
			
			// We must get the number or numbers at the end
			// if I can't find numbers behind the subformula, the subformula appears 1 time
			// if I find them, I keep the number and then multiply the appearances by that number.
			// I remove the parentheses after keeping the number. How? Substring from 1 to length - 1

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
				num += 1; // if C doesn't have a number after it, we add 1
			} else {

				num += num2;
			}
		}
		return num;
	}

	public static boolean isCorrectFormula(String formula)
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

	public static Set discardformulas(Set<String> formula)
	{
		for (String s : formula) { 
			Pattern pat = Pattern.compile(regExpToDiscardRFormulas);
			Matcher mat = pat.matcher(s);
			if (mat.find()) {
				wrongFormulas.add(s);
			}
			else {
				rightFormulas.add(s);//good one's in this array
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
		Set<String> formulas = mapFormulasCompoundIds.keySet();
		String compounds[] = {  "C","N","Cl","O","H","P","S"};
		for (String formula: formulas) {
			int numC=0,numN=0,numCl=0,numO=0,numH=0,numP=0,numS=0;
			for(int j=0; j<compounds.length; j++) {
				Filter filter = new Filter(compounds[j]);  
				if(compounds[j].equals("C")) {
					numC= filter.getNumberElementsInFormula(formula);
				}
				else if (compounds[j].equals("N")){
					numN= filter.getNumberElementsInFormula(formula);
				}
				else if (compounds[j].equals("Cl")){ 
					numCl= filter.getNumberElementsInFormula(formula);
				}
				else if (compounds[j].equals("O")){ 
					numO= filter.getNumberElementsInFormula(formula);
				}
				else if (compounds[j].equals("H")){ 
					numH= filter.getNumberElementsInFormula(formula);
				}
				else if (compounds[j].equals("P")){ 
					numP= filter.getNumberElementsInFormula(formula);
				}
				else if (compounds[j].equals("S")){ 
					numS= filter.getNumberElementsInFormula(formula);
				}
				//introduce the formula in the data base
			}
			Set<Integer> compound_ids = mapFormulasCompoundIds.get(formula);
			for(Integer compound_id : compound_ids)
			{
				InsertTable.insertElement(compound_id, numC, numN, numCl, numO, numH, numP, numS,formula);
			}
		}
	}

	//main used to check while implementing these functions
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
