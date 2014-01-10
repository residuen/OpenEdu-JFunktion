package de.openedu.jfunktion.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.openedu.jfunktion.controller.SolvingController;

public class ValueListPanel extends JPanel
{
	private SolvingController solvingController = null;
	
	public ValueListPanel(SolvingController solvingController)
	{
		super(new GridLayout(1, 2));
		
		this.solvingController = solvingController;
		
		initPanel();
	}
	
	private void initPanel()
	{
		setBackground(Color.WHITE);
		
		JPanel valuesPanel = new JPanel(new BorderLayout());
		JPanel rootPanel = new JPanel(new BorderLayout());
		JPanel extremaPanel = new JPanel(new BorderLayout());
		JPanel turningPointPanel = new JPanel(new BorderLayout());
		
		ValueList valuesList = null;
		
		JPanel rightPanel = new JPanel(new GridLayout(3,1));
		
		JPanel panel;
		
		valuesList = new ValueList(ValueList.DATA);
		valuesList.setName("valuelist");
		panel = new JPanel(new GridLayout(1, 1));
		panel.setBackground(Color.LIGHT_GRAY);		
		panel.add(new JScrollPane(valuesList));
		addComponent(valuesList);
		valuesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		valuesPanel.add(new JLabel("Werteliste"), BorderLayout.NORTH);
		valuesPanel.add(panel, BorderLayout.CENTER);
		
		valuesList = new ValueList(ValueList.DATA);
		valuesList.setName("rootslist");
		panel = new JPanel(new GridLayout(1, 1));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(new JScrollPane(valuesList));
		addComponent(valuesList);
		rootPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		rootPanel.add(new JLabel("Nullstellen"), BorderLayout.NORTH);
		rootPanel.add(panel, BorderLayout.CENTER);
		
		valuesList = new ValueList(ValueList.DATA);
		valuesList.setName("minmax");
		panel = new JPanel(new GridLayout(1, 1));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(new JScrollPane(valuesList));
		addComponent(valuesList);
		extremaPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		extremaPanel.add(new JLabel("Extremwerte"), BorderLayout.NORTH);
		extremaPanel.add(panel, BorderLayout.CENTER);
		
		valuesList = new ValueList(ValueList.DATA);
		valuesList.setName("turningpoint");
		panel = new JPanel(new GridLayout(1, 1));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(new JScrollPane(valuesList));
		addComponent(valuesList);
		turningPointPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		turningPointPanel.add(new JLabel("Wendepunkte"), BorderLayout.NORTH);
		turningPointPanel.add(panel, BorderLayout.CENTER);
		
		rightPanel.add(rootPanel);
		rightPanel.add(extremaPanel);
		rightPanel.add(turningPointPanel);
		
		add(valuesPanel);
		add(rightPanel);
		
	}
	
	/**
	 * Add a input-component to the hashmap in the SolvingController
	 * @param comp
	 */
	private void addComponent(Component comp)
	{
		solvingController.getInputComponents().put(comp.getName(), comp);
	}
}
