package de.openedu.jfunktion.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import de.openedu.jfunktion.controller.SolvingController;
import de.openedu.jfunktion.listener.InputListener;
import de.openedu.jfunktion.tools.FileHandler;

public class InputPanel extends JInternalFrame
{
	public final String DEFAULT_EQUATION = "s*x^2";
	
	private SolvingController solvingController = null;
	
	public InputPanel(SolvingController solvingController)
	{
		this.solvingController = solvingController;
		
		setLayout(new BorderLayout());
		setSize(400, 400);
		setTitle("Gleichung & Kurve");
		setResizable(true);
		setMaximizable(true);
		
		initPanel();
		
		setVisible(true);
	}
	
	/**
	 * add the components to the inputpanel
	 */
	private void initPanel()
	{
		InputListener inputListener = new InputListener(solvingController, solvingController.getEquationMap(), DEFAULT_EQUATION);
		
		Dimension dimTextfield = new Dimension(45, 22);
		Dimension dimButton = new Dimension(90, 22);
		Dimension dimSmallButton = new Dimension(26, 22);
		
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		Box chkHBox = Box.createHorizontalBox();
		Box chkHBox2 = Box.createHorizontalBox();
		Box hIntegralBox = Box.createHorizontalBox();
		
		JLabel label;
		JTextField textfield;
		JButton button;
		JCheckBox checkbox;
		JComboBox combobox;
		JPanel panel;
		
		// add first horizontal strut
		vBox.add(Box.createVerticalStrut(5));
		
		// edit the function
		label = new JLabel("f(x)=");
		
		// Reading equationfile, if possible
		FileHandler fh = new FileHandler();
		File equations = null;
		String[] equationList = null;
		
		if(new File(System.getProperty("user.home").replace("\\", "/")+"/equations.txt").exists())
			equations = new File(System.getProperty("user.home").replace("\\", "/")+"/equations.txt");
		else
			if(new File("equations.txt").exists())
				equations = new File("equations.txt");

		if(equations != null)
		{
			equationList = fh.getTextLines(equations);
			for(String s : equationList) solvingController.getEquationMap().put(s, s);
		}
		else
			equationList = new String[] { DEFAULT_EQUATION };
		
		hBox.add(Box.createHorizontalStrut(5));
		button = new JButton(new ImageIcon(getClass().getResource("/de/openedu/jfunktion/images/icons/user-trash.png")));
		button.addActionListener(inputListener);
		button.setToolTipText("<html>Funktionsgleichung aus Liste löschen</html>");
		button.setName("deleteequation");
		hBox.add(button);
		hBox.add(Box.createHorizontalStrut(5));
			
		combobox = new JComboBox(fillComboBox());
		combobox.setEditable(true);
		combobox.addItemListener(inputListener);
		combobox.setPreferredSize(dimTextfield);
		combobox.setName("equation");
		addComponent(combobox);
		hBox.add(label);
		hBox.add(combobox);
		hBox.add(Box.createHorizontalStrut(5));
		
		label = new JLabel("s=");
		textfield = new JTextField("1");
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setMaximumSize(dimTextfield);
		textfield.setName("scharweight");
		addComponent(textfield);
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));

		label = new JLabel("step");
		textfield = new JTextField("0.1");
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setMaximumSize(dimTextfield);
		textfield.setName("scharstep");
		addComponent(textfield);
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(5));

		// Edit the x-intervall
		hBox = Box.createHorizontalBox();
		label = new JLabel("x-Interval von ");
		textfield = new JTextField("-4");
		textfield.addKeyListener(inputListener); // ActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setName("x_int_left");
		addComponent(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));

		label = new JLabel("bis ");
		textfield = new JTextField("4");
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setName("x_int_right");
		addComponent(textfield);
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));

		button = new JButton("Schar");
		button.setPreferredSize(dimButton);
		button.setMaximumSize(dimButton);
		button.addActionListener(inputListener);
		button.setName("schar");
		addComponent(button);
		hBox.add(button);
		hBox.add(Box.createHorizontalStrut(5));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(5));
		
		// Edit the y-intervall
		hBox = Box.createHorizontalBox();
		label = new JLabel("y-Interval von ");
		textfield = new JTextField("Auto");
		textfield.setEnabled(false);
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setName("y_int_left");
		addComponent(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));

		label = new JLabel("bis ");
		textfield = new JTextField("Auto");
		textfield.setEnabled(false);
		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setName("y_int_right");
		addComponent(textfield);
		hBox.add(label);
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));

		button = new JButton("Parameter");
		button.setPreferredSize(dimButton);
		button.setMaximumSize(dimButton);
		button.addActionListener(inputListener);
		button.setName("parameter");
		addComponent(button);
		hBox.add(button);
		hBox.add(Box.createHorizontalStrut(5));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(5));

		// Show/Hide derivatives
		panel = new JPanel(new BorderLayout());
		hBox = Box.createHorizontalBox();
		checkbox = new JCheckBox("1. Ableitung", false);
		checkbox.addActionListener(inputListener);
		checkbox.setName("derivatives_1");
		addComponent(checkbox);
		chkHBox.add(Box.createHorizontalStrut(5));
		chkHBox.add(checkbox);
		chkHBox.add(Box.createHorizontalStrut(5));
		
		label = new JLabel("<html>&nbsp;Intervall kopieren</html>");
		button = new JButton(new ImageIcon(getClass().getResource("/de/openedu/jfunktion/images/icons/media-seek-forward.png")));
		button.setPreferredSize(dimSmallButton);
		button.setMaximumSize(dimSmallButton);
		button.addActionListener(inputListener);
		button.setToolTipText("<html>Die Werte des Intervalls kopieren</html>");
		button.setName("copytoright");
		chkHBox.add(button);
		chkHBox.add(label);
		chkHBox.add(Box.createHorizontalStrut(5));

		button = new JButton("Zeichnen");
		button.setPreferredSize(dimButton);
		button.setMaximumSize(dimButton);
		button.addActionListener(inputListener);
		button.setName("paint");
		addComponent(button);
		chkHBox2.add(button);
		chkHBox2.add(Box.createHorizontalStrut(5));

		panel.add(chkHBox, BorderLayout.WEST);
		panel.add(chkHBox2, BorderLayout.EAST);

		hBox.add(Box.createHorizontalStrut(5));

		vBox.add(panel);
		vBox.add(Box.createVerticalStrut(5));

		add(vBox, BorderLayout.NORTH);
		
		add(solvingController.getEquationPlotPanel(), BorderLayout.CENTER);
		
		hIntegralBox.add(Box.createHorizontalStrut(5));
		hIntegralBox.add(new JLabel("Integralwert: "));
		label = new JLabel("");
		label.setName("integral");
		panel = new JPanel(new GridLayout(1, 1));
		panel.add(label);
		addComponent(label);
		hIntegralBox.add(panel);
		hIntegralBox.add(Box.createHorizontalStrut(5));
		add(hIntegralBox, BorderLayout.SOUTH);
		
	}
	
	private String[] fillComboBox()
	{
		// Reading equationfile, if possible
		FileHandler fh = new FileHandler();
		File equations = null;
		String[] equationList = null;
		
		if(new File(System.getProperty("user.home").replace("\\", "/")+"/equations.txt").exists())
			equations = new File(System.getProperty("user.home").replace("\\", "/")+"/equations.txt");
		else
			if(new File("equations.txt").exists())
				equations = new File("equations.txt");

		if(equations != null)
		{
			equationList = fh.getTextLines(equations);
			for(String s : equationList) solvingController.getEquationMap().put(s, s);
		}
		else
			equationList = new String[] { DEFAULT_EQUATION };
		
		return equationList;
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
