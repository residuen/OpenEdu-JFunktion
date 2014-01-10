package de.openedu.jfunktion.gui;

import java.awt.Color;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import de.openedu.jfunktion.controller.SolvingController;

public class MainPanel extends JDesktopPane
{
	
	public MainPanel(SolvingController solvingController)
	{		
		initPanel(solvingController);
		
//		JOptionPane.showMessageDialog(this, "Fehlerhafter Funktionsausdruck!");
	}

	private void initPanel(SolvingController solvingController)
	{
		setBackground(Color.WHITE);
		
		add(new InputPanel(solvingController));
		add(new ValuePanel(solvingController));
	}
}
