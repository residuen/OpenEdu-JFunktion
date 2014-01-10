package de.openedu.jfunktion.tools;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ThreadTools {
	
	public static void updateComponent(final JTextField textfield, final String text)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		    	textfield.setText(text);
		    	textfield.repaint();
		    }
		});
	}
	
	public static void doThread(Thread t)
	{
		t.start();

		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
