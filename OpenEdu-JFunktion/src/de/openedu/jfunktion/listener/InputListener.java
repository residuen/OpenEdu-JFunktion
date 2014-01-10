package de.openedu.jfunktion.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import de.openedu.jfunktion.controller.SolvingController;
import de.openedu.jfunktion.solver.OctaveParser;
import de.openedu.jfunktion.solver.OctaveSolver;
import de.openedu.jfunktion.tools.MathTools;
import de.openedu.jfunktion.tools.ThreadTools;
import de.openedu.jfunktion.view.EquationPlotPanel;
import de.openedu.jfunktion.view.ValueList;

public class InputListener implements ActionListener, ItemListener, KeyListener
{
	private final String left = "x_int_left";
	private final String right = "x_int_right";
	private final String step = "solvestep";
	
	private final String left_List = "range_left";
	private final String right_List = "range_right";
	private final String step_List = "stepsize";
	
	private MathTools mt = new MathTools();

	
	private String lastEquation = "";
	
	private String function = null;

	private SolvingController solvingController = null;
	
	private HashMap<String, String> equationMap = null;
	
//	private boolean functionSolved = false;
	private boolean functionPlotted = false;
	
	public InputListener(SolvingController solvingController, HashMap<String, String> equationMap, String function)
	{
		this.solvingController = solvingController;
		
		this.equationMap = equationMap;
		
		this.function = function;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		this.lastEquation = ((JComboBox)(solvingController.getInputComponents().get("equation"))).getSelectedItem().toString();
		
		action(((Component) arg0.getSource()).getName());
	}
	
	private void action(String cmd)
	{		
		System.out.println("cmd=" + cmd);
		
		JTextField textfield = null;
		
		// getting the equation
		JComboBox comboBox = ((JComboBox)(solvingController.getInputComponents().get("equation")));
		function = comboBox.getSelectedItem().toString();
		
		if(cmd.equals("deleteequation"))
			deleteEquation();
		
		if(cmd.contains("copyto"))
			copyTo(cmd);
			
		if(cmd.equals("schar") || cmd.equals("equation"))
		{
			// updating schar-values
			if(cmd.equals("schar"))
				updateScharValue();
			
//			functionPlotted = false;
			
			// Set the equation to the solver
			setFunction();

//			if(!this.lastEquation.equals(equation))
			{
//				ThreadTools.doThread(new Thread() { public void run() { solve(); } }); 
				ThreadTools.doThread(new Thread() { public void run() { plot(); } }); 
			}

			if(((JCheckBox)solvingController.getInputComponents().get("derivatives_1")).isSelected())
				ThreadTools.doThread(new Thread() { public void run() { plotDerivate(); } });
		}
		else
		{
			if(cmd.equals("paint")) //  || cmd.equals("derivatives_1")) //  || cmd.equals("equation"))
			{
				functionPlotted = true;
				// Set the equation to the solver
				setFunction();
				
				if(cmd.equals("paint"))	// !this.lastEquation.equals(equation) &&
					ThreadTools.doThread(new Thread() { public void run() { plot(); } }); 

				if(((JCheckBox)solvingController.getInputComponents().get("derivatives_1")).isSelected())
					ThreadTools.doThread(new Thread() { public void run() { plotDerivate(); } });
			}
			
			if(cmd.equals("derivatives_1")) //  || cmd.equals("equation"))
			{
//				functionPlotted = true;
				// Set the equation to the solver
				setFunction();
				
				if(((JCheckBox)solvingController.getInputComponents().get("derivatives_1")).isSelected())
					ThreadTools.doThread(new Thread() { public void run() { plotDerivate(); } });
				else
					ThreadTools.doThread(new Thread() { public void run() { plot(); } }); 
			}

			if(cmd.equals("solve"))
			{
				// Set the equation to the solver
				setFunction();
					
				ThreadTools.doThread(new Thread() { public void run() { solve(); } }); 
			}
		}
	}
	
	private void copyTo(String cmd)
	{
		if(cmd.equals("copytoright"))
		{
			JTextField textfield1 = ((JTextField)(solvingController.getInputComponents().get("x_int_left")));
			JTextField textfield2 = ((JTextField)(solvingController.getInputComponents().get("x_int_right")));
			
			((JTextField)(solvingController.getInputComponents().get("range_left"))).setText(textfield1.getText());
			((JTextField)(solvingController.getInputComponents().get("range_right"))).setText(textfield2.getText());
		}
		else
			if(cmd.equals("copytoleft"))
			{
				JTextField textfield1 = ((JTextField)(solvingController.getInputComponents().get("range_left")));
				JTextField textfield2 = ((JTextField)(solvingController.getInputComponents().get("range_right")));
				
				((JTextField)(solvingController.getInputComponents().get("x_int_left"))).setText(textfield1.getText());
				((JTextField)(solvingController.getInputComponents().get("x_int_right"))).setText(textfield2.getText());
			}
	}
	
	private void updateScharValue()
	{
		JTextField textfield = ((JTextField)(solvingController.getInputComponents().get("decimalplaces")));
		mt.initRound(Integer.parseInt(textfield.getText()));
		String newScharWeight = null;

		// updating schar-values
		double scharstep = Double.parseDouble(((JTextField)(solvingController.getInputComponents().get("scharstep"))).getText());
		double scharweight = Double.parseDouble(((JTextField)(solvingController.getInputComponents().get("scharweight"))).getText());
		
		scharweight = scharweight + scharstep;
		
		newScharWeight = ""+mt.round(scharweight);

		textfield = ((JTextField)(solvingController.getInputComponents().get("scharweight")));
		
		textfield.setText(newScharWeight);
	}

	/**
	 * Set function and the variables that have to be substituted to the solving-controller 
	 */
	private void setFunction()
	{
		if(!this.lastEquation.equals(function))
			this.lastEquation = function;
		
//		if(this.lastEquation.equals(equation))
		{
			// Set the equation to the solver
			JTextField textfield = (JTextField)solvingController.getInputComponents().get("scharweight");
			solvingController.setVariables("s="+textfield.getText()+";e="+Math.E );
//			solvingController.setVariables("s="+Double.parseDouble(textfield.getText()) );
			solvingController.setFunction(function);
		}
	}

	private void plot()
	{
		// set the properties to the solver for plotting
		JTextField textfield = ((JTextField)(solvingController.getInputComponents().get(left)));
		solvingController.setX_int_left(Double.parseDouble(textfield.getText()));
		
		textfield = ((JTextField)(solvingController.getInputComponents().get(right)));
		solvingController.setX_int_right(Double.parseDouble(textfield.getText()));
		
		textfield = ((JTextField)(solvingController.getInputComponents().get(step)));
		solvingController.setStep(Double.parseDouble(textfield.getText()));

		double epsilon = Double.parseDouble(((JTextField)(solvingController.getInputComponents().get("scharstep"))).getText());

		// solving the equation
		solvingController.solve();
		solvingController.solveFirstDerivative(epsilon);
		
		solvingController.
			getEquationPlotPanel().
				updateSeries(EquationPlotPanel.EQUATION,
						solvingController.getX(), solvingController.getF_x(),
						solvingController.getX_Derivates(), solvingController.getF_x_Derivates(),
						function);
		
		solvAndUpdateIntegral();
	}
	
	private void plotDerivate()
	{
		// set the derivate properties
		JTextField textfield = ((JTextField)(solvingController.getInputComponents().get(left)));
		solvingController.setX_int_left_Derivates(Double.parseDouble(textfield.getText()));
		
		textfield = ((JTextField)(solvingController.getInputComponents().get(right)));
		solvingController.setX_int_right_Derivates(Double.parseDouble(textfield.getText()));
		
		textfield = ((JTextField)(solvingController.getInputComponents().get(step)));
		solvingController.setStep_Derivates(Double.parseDouble(textfield.getText()));
		
		double epsilon = Double.parseDouble(((JTextField)(solvingController.getInputComponents().get("scharstep"))).getText());
		solvingController.solveFirstDerivative(epsilon);
		
		solvingController.
		getEquationPlotPanel().
			updateSeries(EquationPlotPanel.DERIVATE,
					solvingController.getX(), solvingController.getF_x(),
					solvingController.getX_Derivates(), solvingController.getF_x_Derivates(),
					function);
		
//		functionPlotted = true;
	}
	
	private void solve()
	{
		double epsilon;
		int decimalPlaces = Integer.parseInt( ((JTextField)(solvingController.getInputComponents().get("decimalplaces"))).getText() );
		
		// set the derivate properties to the solver and the valuelist
		JTextField textfield = ((JTextField)(solvingController.getInputComponents().get(left_List)));
		solvingController.setX_int_left(Double.parseDouble(textfield.getText()));
		
		textfield = ((JTextField)(solvingController.getInputComponents().get(right_List)));
		solvingController.setX_int_right(Double.parseDouble(textfield.getText()));
		
		textfield = ((JTextField)(solvingController.getInputComponents().get(step_List)));
		solvingController.setStep(Double.parseDouble(textfield.getText()));

		// solving the equation
		solvingController.solve();
		
		epsilon = Double.parseDouble(((JTextField)(solvingController.getInputComponents().get("scharstep"))).getText());
		solvingController.solveFirstDerivative(epsilon);
		
		textfield = ((JTextField)(solvingController.getInputComponents().get("decimalplaces")));
		
		ValueList valueListPanel = ((ValueList)(solvingController.getInputComponents().get("valuelist")));
		
		valueListPanel.updateList(solvingController.getX(), solvingController.getF_x(), decimalPlaces);

		solveRootsMinMaxAndTurningPoints(valueListPanel, textfield, epsilon, decimalPlaces);

		solvAndUpdateIntegral();
	}
	
	private void solveRootsMinMaxAndTurningPoints(ValueList valueListPanel,JTextField textfield, double epsilon, int decimalPlaces)
	{
		solvingController.solveRoots();
		
		valueListPanel = ((ValueList)(solvingController.getInputComponents().get("rootslist")));
		valueListPanel.updateList(solvingController.getRoots(), null, Integer.parseInt(textfield.getText()));
		
		valueListPanel = ((ValueList)(solvingController.getInputComponents().get("minmax")));
		valueListPanel.updateList(solvingController.getX(), solvingController.getF_x(), solvingController.getMinMax(), decimalPlaces);

		valueListPanel = ((ValueList)(solvingController.getInputComponents().get("turningpoint")));
		epsilon = Double.parseDouble(((JTextField)(solvingController.getInputComponents().get("epsilon_t"))).getText());
		valueListPanel.updateList(solvingController.getX(), solvingController.getF_x(), solvingController.getTurningPoints(), decimalPlaces, epsilon);

	}

	private void solvAndUpdateIntegral()
	{
		JTextField textfield = ((JTextField)(solvingController.getInputComponents().get("decimalplaces")));
		mt.initRound(Integer.parseInt(textfield.getText()));

		solvingController.solveIntegral();
		
		JLabel label = ((JLabel)(solvingController.getInputComponents().get("integral")));
		label.setText(""+ mt.round(solvingController.getIntegral()));
	}
	
	/**
	 * IMPORTANT -> REPAIR FUNCTION_ADDING
	 * @param arg0
	 */
	private void addEquationToList(ItemEvent arg0)
	{
		JComboBox cBox = (JComboBox)arg0.getSource();
		
//		System.out.println(solvingController.getEquationMap().get(arg0.getItem().toString()) +" | "+ arg0.getItem());
	
		if(solvingController.getEquationMap().get(arg0.getItem().toString()) == null)
		{
			cBox.addItem(arg0.getItem());
			
			solvingController.getEquationMap().put(arg0.getItem().toString(), arg0.getItem().toString());
		}

		action("equation");
	}
	
	private void deleteEquation()
	{
		JComboBox cBox = (JComboBox)solvingController.getInputComponents().get("equation");
		
		String item = cBox.getSelectedItem().toString();
		
		cBox.removeItem(item);
		
		solvingController.getEquationMap().remove(item);
		
//		functionPlotted = false;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0)
	{
		if(arg0.getSource() instanceof JComboBox)
		{
			addEquationToList(arg0);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
//		System.out.println("Test");
//		functionPlotted = false;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub		
	}
}
