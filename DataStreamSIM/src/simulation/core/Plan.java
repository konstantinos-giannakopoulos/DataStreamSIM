package simulation.core;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import datastorage.structures.Window;

import operators.*;
import util.exceptions.AttributeToValuePredicateException;
import util.exceptions.ParseExecutionPlanException;

/**
 * Parse the query execution plan.
 * 
 * @version 1.0
 * @author kostas
 */
public class Plan {
	
	private ArrayList<Operator> planOperatorsList;
	
	private ArrayList<Window> windowList;
	
	/**
	 * Constructor of class <class>Plan</class>. 
	 */
	public Plan() {
		this.planOperatorsList = new ArrayList<Operator>();
		this.windowList = new ArrayList<Window>();
	}//end constructor Plan()
	
	public ArrayList<Operator> getPlanOperatorsList() {
		return this.planOperatorsList;
	}
	
	public ArrayList<Window> getWindowList() {
		return this.windowList;
	}
	
	/**
	 * @return no. of operators the plan consists of.
	 */
	public int noOperators() {
		return planOperatorsList.size();
	}//end method noOperators()
	
	/**
	 * Parse the query execution plan and identifies the operators.
	 * 
	 * @param filename The path of the execution plan file.
	 * @throws ParseExecutionPlanException
	 */
	public void parsePlan(String filename) throws ParseExecutionPlanException {
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
		
			while ((strLine = br.readLine()) != null) { //parse the query
				if(strLine.startsWith("window")) {
					Window window = new Window();
					window.parseWinInfodata(strLine);
					windowList.add(window);
//					System.out.println(window.toString());//<--! printf 
				} else if(strLine.startsWith("project")) {
					Project project = new Project();
					project.setOpInfodata(strLine);
					project.parseOpInfodata();				
					planOperatorsList.add(project); //add operator to a list
//					System.out.println(project.toString());//<--! printf 
				} else if(strLine.startsWith("select")) {
					Select select = new Select();
					select.setOpInfodata(strLine);
					select.parseOpInfodata();			
					planOperatorsList.add(select); //add operator to a list
//					System.out.println(select.toString());//<--! printf 
				} else if(strLine.startsWith("join")) {
					Join join = new Join();
					join.setOpInfodata(strLine);		
					join.parseOpInfodata();
					planOperatorsList.add(join); //add operator to a list
//					System.out.println(join.toString());//<--! printf 
				} else if(strLine.startsWith("//")) {
					//this is a comment --ignore the line       
					//do-nothing
					continue;
				} else {
					throw new ParseExecutionPlanException("Parsing the input execution plan, failed.");
				}
			}//end while-loop --query parsed
//			System.out.println("Execution Plan parse is done!");//<--! printf 
			br.close();
			in.close();
			fstream.close();
		}catch (ParseExecutionPlanException pExecPlEx) {
			throw new ParseExecutionPlanException("Parsing the input execution plan, failed.");
		}catch (AttributeToValuePredicateException attrValPredEx) {
			System.err.println(attrValPredEx.getMessage());
			throw new ParseExecutionPlanException("Parsing the input execution plan, failed.");
		}catch (Exception e){
//			if(e instanceof ParseExecutionPlanException)
//				throw new ParseExecutionPlanException("Parsing the input execution plan, failed.");
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method parsePlan()

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Plan: [arplanOperators=" + planOperatorsList + "," + "\n" 
				+ "\t arwindowList="+ windowList + "]";
	}

}//end class Plan
