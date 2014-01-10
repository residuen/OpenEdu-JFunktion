package de.openedu.jfunktion.test;

import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import dk.ange.octave.type.OctaveDouble;

public class OctaveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 OctaveEngine octave = new OctaveEngineFactory().getScriptEngine();
		 OctaveDouble x = new OctaveDouble(new double[] { 0, 1.57, 3.1415926535897932384626433832795, 4.712, 6.283185307179586476925286766559 }, 1, 5);
		 octave.put("x", x);
		 String func = "" //
		         + "function res = my_func(x)\n" //
		         + " res = sin(x)./x;\n"
		         + "endfunction\n" //
		         + "";
		 octave.eval(func);
		 octave.eval("y = my_func(x);");
		 OctaveDouble y = octave.get(OctaveDouble.class, "y");
		 double[] data = y.getData();
		 octave.close();
		 
		 for(double i : data)
			 System.out.println(i);
	}
}
