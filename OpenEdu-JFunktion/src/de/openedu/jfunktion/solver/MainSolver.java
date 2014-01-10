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
public class MainSolver implements SolverInterface {
	
//	private final boolean TEST = true;
	private final boolean TEST = false;
	
	public static int EQUATION = 0;
	public static int EQUATION_DERIVATE = 1;
	
	protected String equation = null;
	
	protected double x_int_left = 0;

	protected double x_int_right = 1;

	protected double step = 0.1;
	private double x_int_left_Derivates = 0, x_int_right_Derivates = 1, step_Derivates = 0.1;
	
	protected double[] x_Derivates = null;
	protected double[] f_x_Derivates = null;
	
	protected double[] x = null;
	protected double[] roots = null;
	protected double[] f_x = null;
	protected int[] minMax = null;
	protected int[] turningPoints = null;

	protected double integral = 0;
	
	protected String variables = "";
	
	protected String funcEquation = "";
	
	protected boolean validEquation = true;
	private boolean validEquation_derivate = true;
	
	protected JTextField status = null;
	
//	private MinMaxSolver minMaxSolver = new MinMaxSolver();

	public MainSolver()
	{
		super();
	}
	
	public void setComp(JTextField comp)
	{
		this.status = comp;
	}
		
	public MainSolver(String equation)
	{
		this.equation = equation;
		
		solver(equation, 0, 1, 0.1);
	}
	
	public MainSolver(String equation, double x_int_left, double x_int_right, double step)
	{
		solver(equation, x_int_left, x_int_right, step);
	}
	
	protected void solver(String equation, double x_int_left, double x_int_right, double step)
	{
		this.equation = equation;
	}

	public String getFunction() {
		return equation;
	}

	public boolean isValidFunction() {
		return validEquation;
	}
	
	public boolean isValidFunction_derivate() {
		return validEquation_derivate;
	}

	public void setFunction(String equation) {
		this.equation = equation;
	}

	public double getX_int_left() {
		return x_int_left;
	}

	public void setX_int_left(double x_int_left) {
		this.x_int_left = x_int_left;
	}

	public double getX_int_right() {
		return x_int_right;
	}

	public void setX_int_right(double x_int_right) {
		this.x_int_right = x_int_right;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}
	
	public void setX_int_left_Derivates(double x_int_left_Derivates) {
		this.x_int_left_Derivates = x_int_left_Derivates;
	}

	public void setX_int_right_Derivates(double x_int_right_Derivates) {
		this.x_int_right_Derivates = x_int_right_Derivates;
	}

	public void setStep_Derivates(double step_Derivates) {
		this.step_Derivates = step_Derivates;
	}

	public double[] getResults() {
		return f_x;
	}
	
	public double[] getF_x() {
		return f_x;
	}
	
	public double[] getF_x_Derivates() {
		return f_x_Derivates;
	}
	
	public double getIntegral()
	{
		return integral;
	}

	public double[] getX() {
		return x;
	}
	
	public double[] getX_Derivates() {
		return x_Derivates;
	}
	
	public double[] getRoots() {
		return roots;
	}
	
	public int[] getMinMax() {
		return minMax;
	}
	
	public int[] getTurningPoints() {
		return turningPoints;
	}
	
	public void setVariables(String variables)
	{
		this.variables = variables;
	}

	public boolean solve()
	{
		boolean retV = true;
		
		this.x = solveXRange(x_int_left, x_int_right, step);
		
		return retV;
	}

	
	/**
	 * Solving the 1. derivate of the function
	 * @param epsilon
	 * @return
	 */
	public boolean solveFirstDerivative(double epsilon)
	{
//		System.out.println("MainSolver: solveFirstDerivative");
		boolean retV = false;

		Solver solver = new DerivateSolver();
		
		solver.setDefinitionDomain(x);
		solver.setResults(f_x);
		retV = solver.solve();

		if(retV)
		{
			this.f_x_Derivates = solver.getResults();
			this.x_Derivates = solver.getDefinitionDomain();
		}

		return retV;
	}

	/**
	 * Solving the integral (the area) of the function
	 */
	public void solveIntegral()
	{
		this.integral = 0;
		
		double width, miny, maxy;
		
		for(int i=1; i<x.length; i++)
		{
			width = x[i] - x[i-1];
			miny = Math.min(f_x[i], f_x[i-1]);
			maxy = Math.max(f_x[i], f_x[i-1]);
			
			this.integral += 0.5*width*(miny + maxy);
		}
	}
	
	protected double[] solveXRange(double left, double right, double step)
	{
		ArrayList<Double> rangeList = new ArrayList<Double>();
		double[] dArr;
		double value = left;
		
		do
		{
			rangeList.add(value);
			value += step;
		}
		while(value <= right);
		
		rangeList.add(value);	// add the last value to the list
		
		dArr = new double[rangeList.size()];
		
		for(int i=0; i<rangeList.size(); i++)
			dArr[i] = rangeList.get(i);
		
		return dArr;
	}
	
	public void solveExtendedValues(double epsilon, double epsilon_t)
	{
		// Solving the roots
		status.setText("Status: Solving roots!");	
		System.out.println("Status: Solving roots!");

		RootSolver rootSolver = new RootSolver(this.x, this.f_x, epsilon, epsilon_t);
		rootSolver.solve();
		 
		roots = rootSolver.getRoots();

		// Solving the minima and maxima
		status.setText("Status: Solving minMax!");	
		System.out.println("Status: Solving minMax!");
		
		MinMaxSolver minMaxSolver = new MinMaxSolver(this.x, this.f_x, epsilon, epsilon_t);
		 
		minMax = minMaxSolver.getMinMax();
		 
		// Solving the turning points
		status.setText("Status: Solving turningpoints!");	
		System.out.println("Status: Solving turningpoints!");
		TurningPointSolver tpSolver = new TurningPointSolver(this.x_Derivates, this.f_x_Derivates, epsilon, epsilon_t);
		 
		turningPoints = tpSolver.getResultIds();
		 
		status.setText("Status: Ok!");
		System.out.println("Status: Ok!");
	}
}
