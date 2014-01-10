package de.openedu.jfunktion.solver;

import java.util.ArrayList;

import de.openedu.jfunktion.model.MinMax;

public class RootSolver {

	private double[] x;
	private double[] y;
	private double[] roots = new double[] { };
	private int[] rootId = new int[] { };
	
	private double epsilon = 0.00001;
//	private double epsilon_t = 0.00001;
	
	private ArrayList<Double> rootList = new ArrayList<Double>();
	private ArrayList<Integer> rootIdList = new ArrayList<Integer>();

	public RootSolver(double[] x, double[] y, double epsilon, double epsilon_t)
	{
		setValues(x, y, epsilon, epsilon_t);
	}
	
	public void setValues(double[] x, double[] y, double epsilon, double epsilon_t)
	{
		this.x = x;
		this.y = y;
		this.epsilon = epsilon;
//		this.epsilon_t = epsilon_t;
	}
	
	public double[] getRoots() {
		return roots;
	}
	
	public int[] getRootIds() {
		return rootId;
	}

	public boolean solve()
	{
//		System.out.println("RootSolver: Solving roots!");
		
		boolean hasRoot = false;

		rootList.clear();

		for(int i=0, n=y.length; i<n-1; i++)
		{
			if(simpleRoots(i))
				hasRoot = true;
		}
		
		// now returning the solved roots
		if(hasRoot)
		{
			this.roots = new double[rootList.size()];
			this.rootId = new int[rootIdList.size()];
			
			for(int i=0; i<rootList.size(); i++)
				this.roots[i] = rootList.get(i);
			
			for(int i=0; i<rootIdList.size(); i++)
				this.rootId[i] = rootIdList.get(i);
		}
		
		return hasRoot;
	}
	
	/**
	 * Searching simple roots
	 * @param i
	 * @return
	 */
	private boolean simpleRoots(int i)
	{
		boolean retV = false;
		
		double root = 0, dRoot = 0;
		
//		System.out.println("x="+x+" y="+y);

		// Testen auf exakte Nullstelle
		if(y[i] == 0)
		{
			this.rootList.add(x[i]);
			this.rootIdList.add(i);
			
			retV = true;
		}
		
		// Testen auf Vorzeichenwechsel von y[n] nach y[n+1]
		if( ( y[i] > 0 && y[i+1] < 0) || (y[i] < 0 && y[i+1] > 0) )
		{
			// Naeherung mit Strahlensatz
			dRoot = Math.abs((x[i+1]-x[i]) * (y[i] / (Math.abs(y[i])+Math.abs(y[i+1]))) );
			root = x[i] + dRoot;
			
			if((root > 0 && root <= epsilon) || (root < 0 && root >= -epsilon))
				root = 0;
			
			this.rootList.add(root);
			this.rootIdList.add(i);

			retV = true;
		}
		
		return retV;
	}	
}
