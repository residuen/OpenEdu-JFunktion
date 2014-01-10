package de.openedu.jfunktion.solver;

import de.openedu.jfunktion.interfaces.Parser;

/**
 * Parsing the equation, to prearrangement for the GnuOctave solver
 * @author bettray
 *
 */
public class OctaveParser implements Parser {

	private String function = "x";
	private String octaveEquation = "x";
	private String variables = "";

	public String getVariables()
	{
		return variables;
	}

	/**
	 * Set the original equation
	 * and substitute special signs
	 * @return
	 */
	public void setFunction(String function)
	{
		this.function = function;
		
		octaveEquation = function.replace("^", ".^");
		octaveEquation = octaveEquation.replace("/", "./");
		octaveEquation = octaveEquation.replace("*", ".*");
		
//		System.out.println("octaveEquation="+octaveEquation);
	}
	
	/**
	 * substitute special signs
	 * @return
	 */
	public String substituteVariables()
	{
		StringBuffer retStr = new StringBuffer();
		String[] varList = variables.split(";");
		
		for(String s : varList)
		{
			retStr.append(s+";\n");
		}

//		System.out.println("retStr: "+retStr.toString());
		
		return retStr.toString();
	}

	/**
	 * Get the Octave-equation
	 * 
	 * @return
	 */
	public String getFunction() {
		return octaveEquation;
	}

	@Override
	public void setVariables(String variables) {
		this.variables = variables;
		
	}
}
