package de.openedu.jfunktion.solver;

import java.util.ArrayList;

import de.openedu.jfunktion.model.MinMax;

public class MinMaxSolver {

	private double[] x;
	private double[] y;
	private int[] minMax = new int[] { };

	private double epsilon = 0.00001;
	private double epsilon_t = 0.00001;

	public MinMaxSolver(int i)
	{
		// ...
	}
			
	public MinMaxSolver(double[] x, double[] y, double epsilon, double epsilon_t)
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
	
	public int[] getMinMax() {
		return minMax;
	}

	/**
	 * Searching minima and maxima
	 * @return
	 */
	public boolean solve()
	{
		boolean retV = false;
		
		RootSolver rs = new RootSolver(x, y, epsilon, epsilon_t);
		
		ArrayList<Integer> minMaxList = new ArrayList<Integer>();
		
		for(int i=0, n=y.length; i<n-1; i++)
		{
			if(i <= n-3)
			{
				// Testen auf Vorzeichenwechsel von y[n] nach y[n+1]
				if( ( y[i] <  y[i+1] && y[i+2] < y[i+1]) )
				{
					// Naeherung durch Vergleich dreier aufeianderfolgender y-Werte
					minMaxList.add((i+1)); // new MinMax((i+1), MinMax.HIGH));
		
					retV = true;
		
//					System.out.println("Maximum gefunden bei "+x[i+1]+" -> f(x)="+y[i+1]);
				}
				else
				{
					if( ( y[i] >  y[i+1] && y[i+2] > y[i+1]) )
					{
						// Naeherung durch Vergleich dreier aufeianderfolgender y-Werte
						minMaxList.add(-(i+1)); // new MinMax((i+1), MinMax.DOWN));
			
						retV = true;
			
//						System.out.println("Minimum gefunden bei "+x[i+1]+" -> f(x)="+y[i+1]);
					}
				}
			}
		}
		
		if(retV)
		{
			minMax = new int[minMaxList.size()];
			
			for(int i=0; i<minMaxList.size(); i++)
			{
				minMax[i] = minMaxList.get(i);
				
//				System.out.println(minMax[i]);
			}
			
		}
		
		return retV;
	}
}
