package de.openedu.jfunktion.interfaces;

import dk.ange.octave.OctaveEngine;

public interface SolverInterface {

	public String getFunction();

	public boolean isValidFunction();
	
	public boolean isValidFunction_derivate();

	public void setFunction(String function);

	public void setX_int_left(double x_int_left);

	public void setX_int_right(double x_int_right);

	public void setStep(double step);
	
	public void setX_int_left_Derivates(double x_int_left_Derivates);

	public void setX_int_right_Derivates(double x_int_right_Derivates);

	public void setStep_Derivates(double step_Derivates);

	public double[] getResults();
	
	public double[] getF_x();
	
	public double[] getF_x_Derivates();
	
	public double getIntegral();

	public double[] getX();
	
	public double[] getX_Derivates();
	
	public double[] getRoots();
	
	public int[] getMinMax();
	
	public int[] getTurningPoints();
	
	public void setVariables(String variables);

	public boolean solve();
	
	public void solveExtendedValues(double epsilon, double epsilon_t);
	
	public void solveIntegral();
}
