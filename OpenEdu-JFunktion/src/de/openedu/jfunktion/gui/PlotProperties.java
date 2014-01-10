package de.openedu.jfunktion.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import de.openedu.jfunktion.listener.PlotPropertiesListener;

public class PlotProperties extends JFrame
{
	private HashMap<String,Component> inputComponents = null;
	
	private PlotPropertiesListener propertiesListener = null;
	
	public PlotProperties(HashMap<String,Component> inputComponents)
	{
		super("Einstellungen");
		
		this.inputComponents = inputComponents;
		
		propertiesListener = new PlotPropertiesListener(inputComponents);
		
		setLayout(new BorderLayout());
		
		initFrame();
				
		setSize(430, 220);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setAlwaysOnTop(true);
	}
	
	private void initFrame()
	{
		Font font = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);
		
		JPanel panel = new JPanel(new BorderLayout()); // new GridLayout(1, 1));
		JPanel buttonPanel = new JPanel();
		Box centerBox = Box.createVerticalBox();
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		JToggleButton button = new JToggleButton("<html>Plotter</html>",
				new ImageIcon(getClass().getResource("/de/openedu/jfunktion/images/icons/32px-Nuvola_apps_kmplot.svg.png")));
		button.setSelected(true);
		button.setFont(font);
		button.setToolTipText("<html>Einstellung f&uuml;r die grafische Ausgabe</html>");
		button.setName("plotpropbtn");
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonGroup.add(button);
		buttonPanel.add(button);
		button.addActionListener(propertiesListener);
		
		button = new JToggleButton("<html>Wertelisten</html>",
				new ImageIcon(getClass().getResource("/de/openedu/jfunktion/images/icons/x-office-calendar.png")));
		button.setFont(font);
		button.setToolTipText("<html>Einstellung f&uuml;r die Wertelisten</html>");
		button.setName("valuelistbtn");
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonGroup.add(button);
		buttonPanel.add(button);
		button.addActionListener(propertiesListener);
		
		button = new JToggleButton("<html>Solver</html>",
				new ImageIcon(getClass().getResource("/de/openedu/jfunktion/images/icons/accessories-calculator.png")));
		button.setFont(font);
		button.setToolTipText("<html>Auswahl des Solvers</html>");
		button.setName("solverbtn");
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonGroup.add(button);
		buttonPanel.add(button);
		button.addActionListener(propertiesListener);

		panel.add(buttonPanel, BorderLayout.WEST);
		
		add(panel, BorderLayout.NORTH);
		
		centerBox.add(initPlotterSettings());
		centerBox.add(initValueListSettings());
		centerBox.add(initSolverSettings());
		
		add(centerBox, BorderLayout.CENTER);
	}
	
	private JPanel initPlotterSettings()
	{
		JPanel retPanel = new JPanel(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());
		Box hBox = Box.createHorizontalBox(); //
		
		Dimension dimTextfield = new Dimension(45, 22);
		
		retPanel.setBackground(Color.WHITE);
		retPanel.setName("plotproppanel");
		addComponent(retPanel);
				
		panel.setBackground(Color.WHITE);
		
		JLabel label = new JLabel("<html>Aufl&ouml;sung x-Richtung</html>");
		JTextField textfield = new JTextField("0.1");
//		textfield.addActionListener(inputListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setMaximumSize(dimTextfield);
		textfield.setName("solvestep");

		addComponent(textfield);

		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(label);
		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		
		panel.add(hBox, BorderLayout.WEST);
		
		retPanel.add(panel, BorderLayout.NORTH);
		
		return retPanel;
	}
	
	private JPanel initValueListSettings()
	{
		JPanel retPanel = new JPanel(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());
		Box hBox = Box.createHorizontalBox(); //
		Box vBox = Box.createVerticalBox();
		
		Dimension dimTextfield = new Dimension(90, 22);
		
		retPanel.setBackground(Color.WHITE);
		retPanel.setName("valuelistproppanel");
		addComponent(retPanel);
		
		panel.setBackground(Color.WHITE);
		
		JLabel label = new JLabel("<html>Nachkommastellen (1 bis 9)</html>");
		JTextField textfield = new JTextField("5");
		textfield.addKeyListener(propertiesListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setMaximumSize(dimTextfield);
		textfield.setName("decimalplaces");
		addComponent(textfield);

		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(label);
		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		vBox.add(hBox);
		
		hBox = Box.createHorizontalBox();
		label = new JLabel("<html>N&auml;herung Nullstellen: Epsilon (1E-9 bis 1)</html>");
		textfield = new JTextField("0.00001");
		textfield.addKeyListener(propertiesListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setMaximumSize(dimTextfield);
		textfield.setName("epsilon");
		addComponent(textfield);

		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(label);
		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		vBox.add(hBox);

		hBox = Box.createHorizontalBox();
		label = new JLabel("<html>Mindest&auml;nderung Wendepunkte: Epsilon (1E-9 bis 1)</html>");
		textfield = new JTextField("0.001");
		textfield.addKeyListener(propertiesListener);
		textfield.setPreferredSize(dimTextfield);
		textfield.setMaximumSize(dimTextfield);
		textfield.setName("epsilon_t");
		addComponent(textfield);

		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(label);
		hBox.add(Box.createHorizontalStrut(5));
		hBox.add(textfield);
		hBox.add(Box.createHorizontalStrut(5));
		vBox.add(hBox);

		panel.add(vBox, BorderLayout.WEST);
		
		retPanel.add(panel, BorderLayout.NORTH);
		
		retPanel.setVisible(false);
		
		return retPanel;
	}
	
	private JPanel initSolverSettings()
	{
		JPanel retPanel = new JPanel(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());
		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();
		
		Dimension dimTextfield = new Dimension(90, 22);
		
		retPanel.setBackground(Color.WHITE);
		retPanel.setName("solverplotpanel");
		addComponent(retPanel);
		
		panel.setBackground(Color.WHITE);
		
		ButtonGroup bGroup = new ButtonGroup();
		JRadioButton rButton = new JRadioButton("Octave-Solver");
		rButton.setBackground(Color.white);
		rButton.setSelected(true);
		rButton.addActionListener(propertiesListener);
		bGroup.add(rButton);
		rButton.setName("octavesolver");
		addComponent(rButton);
		vBox.add(rButton);
		
		rButton = new JRadioButton("Javascript-Solver");
		rButton.setBackground(Color.white);
		rButton.setSelected(true);
		rButton.addActionListener(propertiesListener);
		bGroup.add(rButton);
		rButton.setName("javascriptsolver");
		addComponent(rButton);
		vBox.add(rButton);
		
		rButton = new JRadioButton("Imported-Solver");
		rButton.setBackground(Color.white);
		rButton.setSelected(true);
		rButton.addActionListener(propertiesListener);
		bGroup.add(rButton);
		rButton.setName("importedsolver");
		addComponent(rButton);
		vBox.add(rButton);
		vBox.add(hBox);

		panel.add(vBox, BorderLayout.WEST);
		retPanel.add(panel, BorderLayout.NORTH);
		retPanel.setVisible(false);
		
		return retPanel;
	}
	
	/**
	 * Add a input-component to the hashmap in the SolvingController
	 * @param comp
	 */
	private void addComponent(Component comp)
	{
		inputComponents.put(comp.getName(), comp);
	}
}
