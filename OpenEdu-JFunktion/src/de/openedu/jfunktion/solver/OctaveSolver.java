package de.openedu.jfunktion.solver;

import java.util.ArrayList;

import javax.swing.JTextField;

import de.openedu.jfunktion.interfaces.Solver;
import de.openedu.jfunktion.interfaces.SolverInterface;
import de.openedu.jfunktion.model.MinMax;
import de.openedu.jfunktion.tools.ErrorHandler;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import dk.ange.octave.type.OctaveDouble;

/**
 * 
 * @author bettray
 *
 */
public class OctaveSolver extends MainSolver { // implements SolverInterface {
	
	private final boolean TEST = false;

	public OctaveSolver()
	{
		super();
	}
		
	public OctaveSolver(String equation, double x_int_left, double x_int_right, double step)
	{
		super.solver(equation, x_int_left, x_int_right, step);
	}

	public void setFunction(String equation) {
		this.equation = equation;
	}

	public boolean solve()
	{
		boolean retV = true;
		
		this.x = solveXRange(x_int_left, x_int_right, step);
		
		OctaveEngine octave = validateEquation(equation);
		
		OctaveDouble x = new OctaveDouble(this.x, this.x.length, 1);
		try {
			octave.put("x", x);
		} catch (Exception e1) {
			status.setText("Info: Fehlerhafter Funktionsausdruck fuer Solver! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e1));
//			e1.printStackTrace();
		}
		
		// Solve the equation by sending the wrapper-script
		if(validEquation)
		{
			try {
				status.setText("Status: Solving equation!");
				System.out.println("Status: Solving equation!");
				
				octave.eval("y = my_func(x);");
				status.setText("Status: Ok!");
			} catch (Exception e) {
				status.setText("Info: Fehlerhafter Funktionsausdruck! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e));
				e.printStackTrace();
			}

			OctaveDouble y = octave.get(OctaveDouble.class, "y");
			 
			this.f_x = y.getData();
		}
		
		solveIntegral(octave);

		try {
			octave.close();
			status.setText("Status: Ok!");
		} catch (Exception e) {
			status.setText("Info: Fehlerhafter Funktionsausdruck! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e)); //Dicision by zero
		}
		 
		 return retV;
	}

	/**
	 * Solving the integral (the area) of the function
	 */
	public void solveIntegral(OctaveEngine octave)
	{
		status.setText("Status: Solving integral!");
		System.out.println("Status: Solving integral!");
		
		String func =  ""
			+ variables
			+ "F = @(x)"+equation+";\n"
			+ "Q = quad(F,"+x_int_left+","+x_int_right+");";
		
//		System.out.println(func);

		// Solve the equation by sending the wrapper-script
		try {
			octave.eval(func);
			
			OctaveDouble y = octave.get(OctaveDouble.class, "Q");
			 
			octave.eval("y = Q");

			integral = y.getData()[0];

//			for(double d : integral)
//				System.out.println(integral.length+": integral(d)="+d);

			status.setText("Status: Ok!");
		} catch (Exception e) {
			status.setText("Info: Fehlerhafter Funktionsausdruck! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e));
			System.out.println("Info: Fehlerhafter Funktionsausdruck! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e));
			e.printStackTrace();
		}
	}
	
	private OctaveEngine validateEquation(String equation)
	{
		validEquation = true;
		
		String func = "";
		
		OctaveEngine octave = new OctaveEngineFactory().getScriptEngine();
		OctaveDouble x = new OctaveDouble(this.x, this.x.length, 1);
		try {
			octave.put("x", x);
		} catch (Exception e1) {
			status.setText("Info: Fehlerhafter Funktionsausdruck fuer Solver! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e1));
//			e1.printStackTrace();
		}
		
		funcEquation =  ""
			+ "function res = my_func(x)\n"
			+ variables
			+ " res = " + equation + ";\n"
			+ "endfunction\n"
			+ "";
		
		func = funcEquation;
		
		// Solve the equation by sending the wrapper-script
		try {
			octave.eval(func);
			status.setText("Status: Ok!");
		} catch (Exception e) {
			validEquation = false;
			
			status.setText("Info: Fehlerhafter Funktionsausdruck! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e));
			e.printStackTrace();
		}

		return octave;
	}
}
