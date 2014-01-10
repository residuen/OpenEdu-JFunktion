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

package de.openedu.jfunktion.solver;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.openedu.jfunktion.interfaces.Solver;
import de.openedu.jfunktion.interfaces.SolverInterface;
import de.openedu.jfunktion.tools.ErrorHandler;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.type.OctaveDouble;

public class JavaScriptSolver extends MainSolver {

	ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("js");
       
	public boolean solve()
	{
//		System.out.println("JavaScriptSolver");
		
		boolean retV = true;
		String expr;
		Object result;
		
		this.x = super.solveXRange(x_int_left, x_int_right, step);
		
		this.f_x = new double[this.x.length];
		
		try
		{
			for(int i=0; i<this.x.length; i++)
			{
				expr = this.getFunction().replace("x", ""+this.x[i]);
				
				System.out.println("expr="+expr);
				
				result = engine.eval(expr);
						
				this.f_x[i] = Double.parseDouble(result.toString());
				
			}
		}
		catch (ScriptException e)
		{
			status.setText("Info: Fehlerhafter Funktionsausdruck fuer Solver! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e));
		}
		 
		 return retV;
	}
}