package de.openedu.jfunktion.solver;

import java.util.ArrayList;

import de.openedu.jfunktion.interfaces.Solver;
import de.openedu.jfunktion.model.MinMax;

public class TurningPointSolver implements Solver {

	private double[] x;
	private double[] y;
	private int[] turningPoints = new int[] { };

	private double epsilon = 0.00001;
	private double epsilon_t = 0.00001;

	public TurningPointSolver(int i)
	{
		// ...
	}
			
	public TurningPointSolver(double[] x, double[] y, double epsilon, double epsilon_t)
	{
		setValues(x, y, epsilon, epsilon_t);
		
		solve();
	}

	public void setValues(double[] x, double[] y, double epsilon, double epsilon_t)
	{
		this.x = x;
		this.y = y;
		this.epsilon = epsilon;
		this.epsilon_t = epsilon_t;
	}
	
	public int[] getTurningPoints() {
		return turningPoints;
	}

	/**
	 * Searching minima and maxima
	 * @return
	 */
	public boolean solve()
	{
//		System.out.println("TurningPointSolver: solve()");
		
		boolean retV = false;
		boolean isTurningPoint = false;
		
		double[] x_value_roots;
		double[] y_value_roots;
		
		int[] x_ids;
		
		ArrayList<Integer> turningPointList = null;;
		
		Solver derivationSolver;
			
		RootSolver rs;
		
		// Solving the 2. derivation
		derivationSolver = new DerivateSolver();
		derivationSolver.setDefinitionDomain(x);
		derivationSolver.setResults(y);
		retV = derivationSolver.solve();
		
		// if there is no error, solve the roots of the 2. derivation
		if(retV)
		{
			retV = false;
			
			rs = new RootSolver(derivationSolver.getDefinitionDomain(), derivationSolver.getResults(), epsilon, epsilon_t);
			
			retV = rs.solve();
			
			x_value_roots = rs.getRoots();
			x_ids = rs.getRootIds();
			
			// if there is no error, solve the 3. derivation of the function
			if(retV)
			{
				derivationSolver = new DerivateSolver();
				derivationSolver.setDefinitionDomain(x);
				derivationSolver.setResults(y);
				retV = derivationSolver.solve();
				
				// if there is a 3. derivation, test the values on position 'x_ids', if they are lesser or bigger than zero
				if(retV)
				{
					retV = false;
					
					turningPointList = new ArrayList<Integer>();
					
					for(int i=0; i<x_ids.length; i++)
					{
						// if 3. derivation is not 0, wie found a turning point
						if(derivationSolver.getResults()[x_ids[i]] != 0)
						{
							isTurningPoint = true;
							turningPointList.add(x_ids[i]+1);	// shift one step to right
						}
					}
				}
			}
		}

		if(isTurningPoint)
		{
			turningPoints = new int[turningPointList.size()];
			              
			for(int i=0; i<turningPointList.size(); i++)
				turningPoints[i] = turningPointList.get(i);

//			for(int tp : turningPoints)
//				System.out.println("Turningpoint at position "+tp);
		}
		
		return isTurningPoint;
	}

	@Override
	public int[] getResultIds() {
		return turningPoints;
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
	public void setDefinitionDomain(double[] definitionDomain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResults(double[] results) {
		// TODO Auto-generated method stub
		
	}
}
