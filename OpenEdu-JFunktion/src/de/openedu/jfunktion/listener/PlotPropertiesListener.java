package de.openedu.jfunktion.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.openedu.jfunktion.controller.SolvingController;
import de.openedu.jfunktion.solver.OctaveParser;
import de.openedu.jfunktion.solver.OctaveSolver;
import de.openedu.jfunktion.view.EquationPlotPanel;
import de.openedu.jfunktion.view.ValueList;

public class PlotPropertiesListener implements ActionListener, KeyListener
{
	private HashMap<String,Component> inputComponents = null;
	
	public PlotPropertiesListener(HashMap<String,Component> inputComponents) // SolvingController solvingController)
	{
		this.inputComponents = inputComponents;
	}

	public boolean isInteger(String str)  
	{  
		try  
		{  
			Integer.parseInt(str);
			return true;  
		}  
		catch(Exception e)  
		{  
			return false;  
		}  
	} 
	
	public boolean isDouble(String str)  
	{  
		try  
		{  
			Double.parseDouble(str);
			return true;  
		}  
		catch(Exception e)  
		{  
			return false;  
		}  
	} 
	   
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String cmd = ((Component) arg0.getSource()).getName();

		System.out.println("cmd=" + cmd);
		
		if(cmd.equals("plotpropbtn"))
		{
			inputComponents.get("plotproppanel").setVisible(true);
			inputComponents.get("valuelistproppanel").setVisible(false);
			inputComponents.get("solverplotpanel").setVisible(false);
		}
		else
			if(cmd.equals("valuelistbtn"))
			{
				inputComponents.get("valuelistproppanel").setVisible(true);
				inputComponents.get("plotproppanel").setVisible(false);
				inputComponents.get("solverplotpanel").setVisible(false);
			}
			else
				if(cmd.equals("solverbtn"))
				{
					inputComponents.get("solverplotpanel").setVisible(true);
					inputComponents.get("valuelistproppanel").setVisible(false);
					inputComponents.get("plotproppanel").setVisible(false);
				}
				else
					if(cmd.equals("javascriptsolver"))
					{
						JOptionPane.showMessageDialog(null, "Javascriptsolver noch nicht implementiert!");
					}
					else
						if(cmd.equals("importedsolver"))
						{
							JOptionPane.showMessageDialog(null, "Laden eines externen noch nicht implementiert!");
						}


	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Checking the property-input
	 */
	@Override
	public void keyReleased(KeyEvent arg0)
	{
		JTextField textfield = (JTextField)arg0.getSource();
		
		// test, if input is in interval [1 ... 8] and if is numeric
		if(textfield.getName().equals("decimalplaces"))
		{
			if(textfield.getText().length()>1 || !isInteger(textfield.getText()))
				textfield.setText("9");
			
			if(Integer.parseInt(textfield.getText())<1)
				textfield.setText("1");
		}
		else
		{
			if(textfield.getName().equals("epsilon"))
			{
				System.out.println(arg0.getKeyCode());
				
				if(arg0.getKeyCode() == 37 || arg0.getKeyCode() == 39)
					System.out.println("Zeichen: Links");
				else
				{	
					if(textfield.getText().length()<1 || !isDouble(textfield.getText()))
						textfield.setText("0.0001");
					else
					{
					if(Double.parseDouble(textfield.getText())<=0)
						textfield.setText("0.0001");
					
					if(Double.parseDouble(textfield.getText())>1)
						textfield.setText("1");
					}
				}
			}
		}			
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
