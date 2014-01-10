package de.openedu.jfunktion.view;

import java.awt.Color;

import javax.swing.JList;

import de.openedu.jfunktion.model.MinMax;
import de.openedu.jfunktion.tools.MathTools;

public class ValueList extends JList {

	public static final String[] DATA =
    {
//      "test1",
//      "test2",
//      "test3",
//      "test4",
//      "test5",
//      "test6",
//      "test7",
//      "test8"
    };

	public ValueList(String[] data)
	{
		super(data);
		
		setBackground(Color.WHITE);
	}
	
	/**
	 * Update the MinMax-List
	 * @param x
	 * @param y
	 * @param minMax
	 * @param decimalplaces
	 */
	public void updateList(double[] x, double[] y, int[] minMax, int decimalplaces)
	{
		this.removeAll();
		
		MathTools mt = new MathTools();
		mt.initRound(decimalplaces);
		
		String[] data = new String[minMax.length];
		
//		System.out.println("minMax.length="+minMax.length);
		
		for(int i=0, n=minMax.length; i<n; i++)
		{
//			System.out.println(i+": "+minMax[i]);

			data[i] = ( (minMax[i] > 0 ) ? "H" : "T" ) +"("+mt.round(x[Math.abs(minMax[i])])+" | "+mt.round(y[Math.abs(minMax[i])])+")";
			
		}

		this.setListData(data);
		
	}
	
	/**
	 * update turningpoint-list
	 * @param x
	 * @param y
	 * @param turningPoints
	 * @param decimalplaces
	 */
	public void updateList(double[] x, double[] y, int[] turningPoints, int decimalplaces, double epsilon)
	{
		double xValue, yValue, ySwap;
		
		this.removeAll();
		
		MathTools mt = new MathTools();
		mt.initRound(decimalplaces);
		
		String[] data = new String[turningPoints.length];
		
		for(int i=0, n=turningPoints.length; i<n; i++)
		{
			xValue = mt.round(x[turningPoints[i]]);
			yValue = mt.round(y[turningPoints[i]]);
			
			xValue = ((xValue > -epsilon && xValue <=0) || (xValue < epsilon && xValue >=0)) ? 0 : xValue;
			yValue = ((yValue > -epsilon && yValue <=0) || (yValue < epsilon && yValue >=0)) ? 0 : yValue;

			data[i] = "W("+xValue+" | "+yValue+")";
		}
		this.setListData(data);
		
	}
	
	/**
	 * Update the value-list, or the root-list, from the solved function
	 * @param x
	 * @param y
	 * @param decimalplaces
	 */
	public void updateList(double[] x, double[] y, int decimalplaces)
	{
		this.removeAll();
		
		MathTools mt = new MathTools();
		mt.initRound(decimalplaces);
		
		String[] data = new String[x.length];
		
		if(y==null)
			for(int i=0; i<x.length; i++)
				data[i] = "x = "+mt.round(mt.round(x[i]));
		else
			for(int i=0, n=Math.min(x.length, y.length); i<n; i++)
				data[i] = "x = "+mt.round(x[i])+"  ->  f(x) = "+mt.round(y[i]);

		this.setListData(data);
	}
}
