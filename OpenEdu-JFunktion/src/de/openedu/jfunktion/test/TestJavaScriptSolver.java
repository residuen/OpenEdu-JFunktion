/*
Math Functions of JavaScript:

Math.abs(a)     // the absolute value of a
Math.acos(a)    // arc cosine of a
Math.asin(a)    // arc sine of a
Math.atan(a)    // arc tangent of a
Math.atan2(a,b) // arc tangent of a/b
Math.ceil(a)    // integer closest to a and not less than a
Math.cos(a)     // cosine of a
Math.exp(a)     // exponent of a
Math.floor(a)   // integer closest to and not greater than a
Math.log(a)     // log of a base e
Math.max(a,b)   // the maximum of a and b
Math.min(a,b)   // the minimum of a and b
Math.pow(a,b)   // a to the power b
Math.random()   // pseudorandom number in the range 0 to 1
Math.round(a)   // integer closest to a 
Math.sin(a)     // sine of a
Math.sqrt(a)    // square root of a
Math.tan(a)     // tangent of a

to get sinh():
function sinh(aValue)
{
	var myTerm1 = Math.pow(Math.E, aValue);
	var myTerm2 = Math.pow(Math.E, -aValue);
   
	return (myTerm1-myTerm2)/2;
}

to get cosh():
function cosh(aValue)
{
	var myTerm1 = Math.pow(Math.E, aValue);
	var myTerm2 = Math.pow(Math.E, -aValue);
   
	return (myTerm1+myTerm2)/2;
}

to get tanh():
function tanh(aValue)
{
	var myTerm1 = Math.pow(Math.E, aValue);
	var myTerm2 = Math.pow(Math.E, -aValue);
   
	return (myTerm1-myTerm2) / (myTerm1+myTerm2);
}

 */

package de.openedu.jfunktion.test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.openedu.jfunktion.interfaces.Solver;

public class TestJavaScriptSolver implements Solver {
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{	
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("js");        
	    
	    try {
	    	Object result;
	    	
	    	System.out.println("Start solving");
	    	
	    	for(double i=0; i<=2*Math.PI; i+=0.0001)
			{
	    		result = engine.eval("Math.sin("+i+")/"+i);
//	    		result = engine.eval("pow("+i+",2)");

//	    		System.out.println(i+":"+result.toString());
			}
			
	    	System.out.println("Stop solving");

	    } catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setFunction(String function) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInterval(double xStart, double xStop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step(double step) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double[] getDefinitionDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getResultIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefinitionDomain(double[] definitionDomain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResults(double[] results) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean solve() {
		// TODO Auto-generated method stub
		return false;
	}
}
