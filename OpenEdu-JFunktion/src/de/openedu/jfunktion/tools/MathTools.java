package de.openedu.jfunktion.tools;

public class MathTools
{
	private double decimalplaces = 1.0;
	
	public void initRound(int decimalplaces)
	{
		this.decimalplaces = 1.0;
		
		for(int i=0; i<decimalplaces; i++)
			this.decimalplaces *= 10.0;
	}
	
	public double round(double value)
	{
		double retV = 0;		
		
		retV = Math.rint(value*(int)decimalplaces)/decimalplaces;
		
		return retV;
	}
}
