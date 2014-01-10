package de.openedu.jfunktion.solver;
//package de.jFunktion.solver;
//
//import de.jFunktion.interfaces.Solver;
//import de.jFunktion.tools.ErrorHandler;
//import dk.ange.octave.OctaveEngine;
//import dk.ange.octave.OctaveEngineFactory;
//import dk.ange.octave.type.OctaveDouble;
//
//public class ValueSolverOctave implements Solver {
//
//	private String function = null;
//	
//	double[] definitionDomain = null;
//	double[] results = null;
//	
//	double xStart = 0, xStop = 0;
//	double step = 0;
//	
//	
//	@Override
//	public void setFunction(String function) {
//		
//		String octaveFunction = function.replace("^", ".^");
//		octaveFunction = octaveFunction.replace("/", "./");
//		octaveFunction = octaveFunction.replace("*", ".*");
//		
//		this.function = octaveFunction;
//	}
//
//	@Override
//	public void setInterval(double xStart, double xStop) {
//		this.xStart = xStart;
//		this.xStop = xStop;
//		
//		OctaveEngine octave = new OctaveEngineFactory().getScriptEngine();
//		
//		 // Solve the x-range
//		 String func = ""
//				+ function
//				+ "function res = my_func(x)\n"
//				+ "res = x;\n"
//				+ "endfunction\n"
//				+ "";
//
//		 octave.eval(func);
//		 octave.eval("domain = my_func(x);");
//			
//		 OctaveDouble domain = octave.get(OctaveDouble.class, "domain");
//
//		 definitionDomain = domain.getData();
//
//	}
//
//	@Override
//	public void step(double step) {
//		this.step = step;
//	}
//
//	@Override
//	public double[] getDefinitionDomain() {
//		return definitionDomain;
//	}
//
//	@Override
//	public double[] getResults() {
//		return results;
//	}
//
//	@Override
//	public void setDefinitionDomain(double[] definitionDomain) {
//		this.definitionDomain = definitionDomain;
//	}
//
//	@Override
//	public void setResults(double[] results) {
//		this.results = results;
//	}
//	
//	public boolean solve() {
//		boolean retV = true;
//////		System.out.println("solve: equation="+equation+" variables: "+variables);
////		
////		OctaveEngine octave = new OctaveEngineFactory().getScriptEngine();
////		
////		double stepsCount = ((xStop-xStart)/step);
////		
////		if(stepsCount%2==0)
////			stepsCount++;
////		
////		String funcEquation =  ""
////			+"x = linspace("+xStart+","+xStop+","+stepsCount+");\n"
////			+ "function res = my_func(x)\n"
////			+ variables
////			+ " res = " + function + ";\n"
////			+ "endfunction\n"
////			+ "";
////		
////		// Solve the equation by sending the wrapper-script
////		if(validEquation)
////		{
////			try {
//////				status.setText("Status: Solving equation!");
//////				System.out.println("Status: Solving equation!");
////				
////				octave.eval("y = my_func(x);");
//////				status.setText("Status: Ok!");
////			} catch (Exception e) {
//////				status.setText("Info: Fehlerhafter Funktionsausdruck! Bitte die Syntax kontrollieren!"+ErrorHandler.getErrorMassageInfo(e));
////				e.printStackTrace();
////			}
////
////			OctaveDouble y = octave.get(OctaveDouble.class, "y");
////			 
////			f_x = y.getData();
////
////			this.x = solveLinspace("x = linspace("+x_int_left+","+x_int_right+","+stepsCount+");\n", octave);
////		}
////
////		 octave.close();
////		 
//////		 for(double i : f_x)
//////			 System.out.println("f_x="+i);
////		 
//		 return retV;
//	}
//
//	@Override
//	public int[] getResultIds() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
