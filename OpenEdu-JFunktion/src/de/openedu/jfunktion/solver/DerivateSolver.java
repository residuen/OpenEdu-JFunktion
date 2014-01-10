package de.openedu.jfunktion.solver;

import de.openedu.jfunktion.interfaces.Solver;

public class DerivateSolver implements Solver {

	private double[] x, y;
	private double[] result, definitionDomain;
	
	@Override
	public void setFunction(String function) { 	}

	@Override
	public void setInterval(double xStart, double xStop) { 	}

	@Override
	public void step(double step) {  }

	@Override
	public double[] getDefinitionDomain() {
		return definitionDomain; }

	@Override
	public double[] getResults() {
		return result;
	}

	@Override
	public int[] getResultIds() {
		return null;
	}

	@Override
	public void setDefinitionDomain(double[] x) {
		this.x = x;
	}

	@Override
	public void setResults(double[] y) {
		this.y = y;
	}

	@Override
	public boolean solve() {

//		System.out.println("solveDerivatives");
		boolean retV = false;
		int elements = y.length - 1;
 
		
//		System.out.println("elements="+elements);
		
		if(this.y != null)
		{
			double dx, dy, m, dxCenter;
			retV = true;
			
			this.result = new double[elements-1];
			this.definitionDomain = new double[elements-1];
			
			for(int i=0, n = elements-1; i<n; i++)
			{
				dx = (x[i+1] - x[i]);
				dxCenter = x[i] + dx/2.0;
				dy = (y[i+1] - y[i]);
				m = dy/dx;
				
				this.result[i] = m;
				this.definitionDomain[i] = dxCenter;
	
	//			System.out.println(i+": x="+this.x_Derivates[i]+" - dx="+dx+" - dy="+dy+" m="+f_x_Derivates[i]);
			}
		}
		 
		 return retV;
	}
}
