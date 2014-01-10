package de.openedu.jfunktion.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.openedu.jfunktion.controller.SolvingController;
import de.openedu.jfunktion.listener.InputListener;
import de.openedu.jfunktion.view.ValueListPanel;

public class ValuePanel extends JInternalFrame
{
	private SolvingController solvingController = null;
	
	public ValuePanel(SolvingController solvingController)
	{
		this.solvingController = solvingController;
		
		setLayout(new BorderLayout());
		setSize(450, 400);
		setLocation(405, 0);
		setTitle("Wertetabelle");
		setResizable(true);
		setMaximizable(true);
		
		initPanel();
		
		setVisible(true);
	}
	
	private void initPanel()
	{
		InputListener inputListener = new InputListener(solvingController, solvingController.getEquationMap(), solvingController.getEquation());
		
		Dimension dimTextfield = new Dimension(45, 22);
		Dimension dimButton = new Dimension(90, 22);
		Dimension dimSmallButton = new Dimension(26, 22);
		
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		
		JLabel label;
		JTextField textfield;
		JButton button;
		JPanel panel;

		// add first horizontal strut
		vBox.add(Box.createVerticalStrut(5));
		
		// edit the table-range
		label = new JLabel("<html>Wertebereich f&uuml;r x in den Tabellen</html>");
		panel = new JPanel();
		panel.add(label);
		vBox.add(panel);
		vBox.add(Box.createVerticalStrut(5));
		
		label = new JLabel("von ");
		textfield = new JTextField("-5");
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setName("range_left");
		addComponent(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		
		label = new JLabel("bis ");
		textfield = new JTextField("5");
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setName("range_right");
		addComponent(textfield);
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));

		label = new JLabel("Schrittweite ");
		textfield = new JTextField("0.01");
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setName("stepsize");
		addComponent(textfield);
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		
		// Add textfields to vBox
		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(5));
		
		hBox = Box.createHorizontalBox();
		hBox.add(Box.createHorizontalStrut(5));
		label = new JLabel("<html>&nbsp;Intervall kopieren</html>");
		button = new JButton(new ImageIcon(getClass().getResource("/de/openedu/jfunktion/images/icons/media-seek-backward.png")));
		button.setPreferredSize(dimSmallButton);
		button.setMaximumSize(dimSmallButton);
		button.addActionListener(inputListener);
		button.setToolTipText("<html>Die Werte des Intervalls kopieren</html>");
		button.setName("copytoleft");
		hBox.add(button);
		hBox.add(label);
		hBox.add(Box.createHorizontalStrut(5));
		
		// Button to start the solver
		button = new JButton("Berechnen");
		button.setPreferredSize(dimButton);
		button.setMaximumSize(dimButton);

		button.addActionListener(inputListener);
		button.setName("solve");
		addComponent(button);
		
		hBox.add(button);
		hBox.add(Box.createHorizontalStrut(5));
		
		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(5));

		add(vBox, BorderLayout.NORTH);
		
		add(new ValueListPanel(solvingController), BorderLayout.CENTER);
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
