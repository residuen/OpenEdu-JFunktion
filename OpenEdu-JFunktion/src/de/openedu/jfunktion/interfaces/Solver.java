package de.openedu.jfunktion.interfaces;

public interface Solver {

	public void setFunction(String function);
	public void setInterval(double xStart, double xStop);
	public void step(double step);
	
	public double[] getDefinitionDomain();	
	public double[] getResults();
	public int[] getResultIds();
	
	public void setDefinitionDomain(double[] definitionDomain);	
	public void setResults(double[] results);
	
	public boolean solve();
}