package de.openedu.jfunktion.model;

public class MinMax
{
	public static int HIGH = 0;
	public static int DOWN = 1;
	
	public int n = 0;
	
	public int highDown = HIGH;
	
	public MinMax(int n, int highDown)
	{
		this.n = n;
		this.highDown = highDown;
	}
}
