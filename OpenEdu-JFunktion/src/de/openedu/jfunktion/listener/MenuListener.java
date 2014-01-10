package de.openedu.jfunktion.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.openedu.jfunktion.gui.About;
import de.openedu.jfunktion.gui.PlotProperties;

public class MenuListener implements ActionListener, MouseListener
{
	private HashMap<String,Component> inputComponents = null;
	
	public MenuListener(HashMap<String,Component> inputComponents)
	{
		this.inputComponents = inputComponents;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String cmd = ((Component) arg0.getSource()).getName();

		event(cmd);
	}
	
	private void event(String event)
	{
		System.out.println("cmd=" + event);
		
		if(event.equals("properties"))
			inputComponents.get("plotprop").setVisible(true);
		else
			if(event.equals("about"))
				new About();
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		String cmd = ((Component) arg0.getSource()).getName();

		event(cmd);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

}
