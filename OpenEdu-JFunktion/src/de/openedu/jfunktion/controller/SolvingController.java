package de.openedu.jfunktion.controller;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTextField;

import de.openedu.jfunktion.gui.PlotProperties;
import de.openedu.jfunktion.interfaces.Parser;
import de.openedu.jfunktion.solver.ExperimentalSolver;
import de.openedu.jfunktion.solver.JavaScriptSolver;
import de.openedu.jfunktion.solver.JavascriptParser;
import de.openedu.jfunktion.solver.MainSolver;
//import de.openedu.jfunktion.solver.OctaveParser;
//import de.openedu.jfunktion.solver.OctaveSolver;
import de.openedu.jfunktion.view.EquationPlotPanel;

public class SolvingController {

//	private Parser parser = new OctaveParser();
//	private MainSolver solver = new OctaveSolver();
	
	private Parser parser = new JavascriptParser();
	private MainSolver solver = new JavaScriptSolver();
//	private MainSolver solver = new ExperimentalSolver();

	private HashMap<String,Component> inputComponents = new HashMap<String,Component>();
	private HashMap<String, String> equationMap = new HashMap<String, String>();
	
	PlotProperties plotProperties = new PlotProperties(inputComponents);
	
	private EquationPlotPanel equationPlotPanel = new EquationPlotPanel();
	
	public SolvingController()
	{
		inputComponents.put("plotprop", plotProperties);
	}
	
	public MainSolver getSolver() {
		return solver;
	}
	
	public EquationPlotPanel getEquationPlotPanel() {
		return equationPlotPanel;
	}

	public HashMap<String,Component> getInputComponents() {
		return inputComponents;
	}
	
	public HashMap<String,String> getEquationMap() {
		return equationMap;
	}
	
	/**
	 * set the original equation
	 * @return
	 */
	public void setFunction(String function)
	{
		parser.setFunction(function);
		
		solver.setFunction(parser.getFunction());
	}
	
	/**
	 * Get the original equation
	 * @return
	 */
	public String getEquation() {
		return parser.getFunction();
	}
	
	public void setVariables(String variables)
	{
		parser.setVariables(variables);
		parser.substituteVariables();
		solver.setVariables(variables);
	}
	
	/**
	 * substitute special signs
	 * @return
	 */
	public String substituteVariables(String variables)
	{
		parser.setVariables(variables);
		return parser.substituteVariables();
	}
	
	/** Get the Octave-equation
	 * 
	 * @return
	 */
	public String getOctaveEquation() {
		return parser.getFunction();
	}
	
	public double getX_int_left() {
		return solver.getX_int_left() ;
	}

	public void setX_int_left(double x_int_left) {
		solver.setX_int_left(x_int_left);
	}

	public double getX_int_right() {
		return solver.getX_int_right();
	}

	public void setX_int_right(double x_int_right) {
		solver.setX_int_right(x_int_right);
	}

	public double getStep() {
		return solver.getStep();
	}

	public void setStep(double step) {
		solver.setStep(step);
	}
	
	public double[] getF_x() {
		return solver.getF_x();
	}

	public double getIntegral() {
		return solver.getIntegral();
	}
	
	public double[] getF_x_Derivates() {
		return solver.getF_x_Derivates();
	}

	public double[] getX() {
		return solver.getX();
	}
	
	public double[] getRoots() {
		return solver.getRoots();
	}
	
	public int[] getMinMax() {
		return solver.getMinMax();
	}
	
	public int[] getTurningPoints() {
		return solver.getTurningPoints();
	}

	public double[] getX_Derivates() {
		return solver.getX_Derivates();
	}

	public void solve() {
		
		solver.setComp((JTextField)inputComponents.get("status"));

		solver.solve();
	}
	
	public void solveIntegral() {
		solver.solveIntegral();
	}
	
	public void setX_int_left_Derivates(double x_int_left_Derivates) {
		solver.setX_int_left_Derivates(x_int_left_Derivates);
	}

	public void setX_int_right_Derivates(double x_int_right_Derivates) {
		solver.setX_int_right_Derivates(x_int_right_Derivates);
	}

	public void setStep_Derivates(double step_Derivates) {
		solver.setStep_Derivates(step_Derivates);
	}
	
	public void solveRoots() {
		
		double epsilon = Double.parseDouble(((JTextField)inputComponents.get("epsilon")).getText());
		double epsilon_t = Double.parseDouble(((JTextField)inputComponents.get("epsilon_t")).getText());
		
		solver.solveExtendedValues(epsilon, epsilon_t);
	}
	
	public void solveFirstDerivative(double epsilon) {
		solver.solveFirstDerivative(epsilon);
	}
}
