package de.openedu.jfunktion.solver;

import java.util.Arrays;

import de.openedu.jfunktion.interfaces.Parser;

/**
 * Parsing the equation, to prearrangement for the Javascript solver
 * @author bettray
 *
 */
public class JavascriptParser implements Parser  {

	private String function = "x";
	private String variables = "";

	/**
	 * Set the original equation
	 * and substitute special signs
	 * @return
	 */
	public void setFunction(String function)
	{
		this.function = function;
		
		this.function = substituteVariables();
		
//		System.out.println("this.function="+this.function);
		
		this.function = this.function.replace("sin", "Math.sin");
		this.function = this.function.replace("cos", "Math.cos");
		this.function = this.function.replace("tan", "Math.tan");
		this.function = this.function.replace("asin", "Math.asin");
		this.function = this.function.replace("acos", "Math.acos");
		this.function = this.function.replace("atan", "Math.atan");
		this.function = this.function.replace("pow", "Math.pow");
		this.function = this.function.replace("sqrt", "Math.sqrt");
		this.function = this.function.replace("log", "Math.log");
		this.function = this.function.replace("round", "Math.round");
		this.function = this.function.replace("abs", "Math.abs");

		this.function = replaceExponentByFunction(this.function, 0);
		
		System.out.println("this.function="+this.function);
	}
	
	private String replaceExponentByFunction(String function, int pos)
	{
		while(function.contains("^"))
			function = substitudeOperand(function, function.indexOf("^"));
		
		return function;
	}
	
	private String substitudeOperand(String function, int pos)
	{
		char[] fncArr = function.toCharArray();
		int beginIndex = 0, endIndex = fncArr.length;
		String exponent;
		String base;
		
		for(int i=pos; i < fncArr.length; i++)
		{	
			if( fncArr[i]=='+' || fncArr[i]=='-' || fncArr[i]=='*' || fncArr[i]=='/')
			{
				endIndex = i;
		
				break;
			}
		}
		
		for(int i=pos; i >=0; i--)
		{
			if( fncArr[i]=='+' || fncArr[i]=='-' || fncArr[i]=='*' || fncArr[i]=='/')
			{	
				beginIndex = i+1;
				
				break;
			}
		}
//		System.out.println("exponent="+function.substring(beginIndex, pos));
//		System.out.println("basis="+function.substring(pos+1, endIndex));
		
		
		base = function.substring(beginIndex, pos);
		exponent = function.substring(pos+1, endIndex);
		
		if(base.equals("e"))
			base = ""+Math.E;
		
		if(exponent.equals("e"))
			exponent = ""+Math.E;
		
		function = function.replace(function.substring(beginIndex, endIndex), "Math.pow("+base+","+exponent+")");
		function = function.replace("()","").replace("-x", "-1*x");
		
		return function;
		
	}
	
//	private String
	
	/**
	 * substitute special signs
	 * example: variable = "s=3.1415926;a=2;z=9.87639"
	 * @return
	 */
	public String substituteVariables()
	{
		String[] functionArr = spacingExpression(this.function).split(" ");

		StringBuffer retStr = new StringBuffer();
		String[] varList = variables.split(";");
		String atom[], varname, value;
		
		for(int i=0; i<functionArr.length; i++)
		{
//			System.out.print(functionArr[i]+"| ");
			
			for(String s : varList)
			{
//				System.out.println("varLst:"+s);
				
				atom = s.split("=");
				varname = atom[0];
				value = atom[1];
				
				if(functionArr[i].equals(varname))
				{
					functionArr[i] = value;
					
					break;
				}	
			}
		}
		
		for(String s : functionArr)
			retStr.append(s);
		
		return retStr.toString();
	}

	/**
	 * Fuegt der Funktion Leerzeichen zwischen Operanden und Operationszeichen zu
	 * @param function
	 * @return
	 */
	private String spacingExpression(String function)
	{
		String[] OPERATOR = new String[] { "+", "-", "*", "/" };
		
		StringBuffer strB = new StringBuffer();
		
		boolean isOperator = false;		// Flag. ob es sich um einen Operator handelt
		boolean isSpecialSign = false;	// Flag, ob es sich um ein Sonderzeichen (z.B. "(" handelt)
		
		for(char c : this.function.toCharArray())
		{
			// ueberpruefe, ob eines der Operationszeichen vorhanden ist
			for(String sign : OPERATOR)
			{
				isSpecialSign = (""+c).equals(sign);
				
				if(isSpecialSign)
					break;
			}
			
			// Wenn ein Operationszeichen vorhanden ist, fuege Leerzeichen in neue Zeichenkette ein
			if(isSpecialSign)
			{
				// Wenn schon ein Leerzeichen gesetzt wurde, keines vor dem Operator einfuegen
				if(!isOperator)
					strB.append(' ');
				
				strB.append(c);		// Zeichen in neue Zeichenkette einfuegen ...
				strB.append(' ');	// ... und ein Leerzeichen anfuegen
				
				isOperator = true;	// Kennzeichnen, dass schon ein Operator gesetzt wurde
			}
			else
			{
				strB.append(c);		// normales Zeichen in zeichenkette schreiben
				
				isOperator = false;	// Operator Kennzeichnung zurücksetzen
			}
		}
		
//		System.out.println("strB.toString().trim()="+strB.toString().trim());
		
		return strB.toString().trim();
		
	}

	/**
	 * Get the Octave-equation
	 * 
	 * @return
	 */
	public String getFunction() {
		return this.function;
	}

	@Override
	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	public String getVariables()
	{
		return variables;
	}

//	public class FunctionToken
//	{
//		String operator = null;
//		String operand = null;
//		
//	}
}
