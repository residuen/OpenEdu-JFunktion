package de.openedu.jfunktion.interfaces;

public interface Parser {

	public String getVariables();
	public void setFunction(String function);
	public void setVariables(String variables);
	public String substituteVariables();
	public String getFunction();
}