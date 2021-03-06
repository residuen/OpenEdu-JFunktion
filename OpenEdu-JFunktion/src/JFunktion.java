/*
JFunktion:
A simple equation solver and plotter.
It's using GnuOctave. Octave and is implemented with JavaOctave (a simple JavaOctave-Wrapper)

Copyright (C) 2010 Karsten Bettray

Dieses Programm ist freie Software. Sie koennen es unter den Bedingungen der GNU General Public License,
wie von der Free Software Foundation veroeffentlicht, weitergeben und/oder modifizieren, entweder gemaess
Version 3 der Lizenz oder (nach Ihrer Option) jeder spaeteren Version.
Die Veroeffentlichung dieses Programms erfolgt in der Hoffnung, dass es Ihnen von Nutzen sein wird, aber
OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FUER
EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben.
Falls nicht, siehe <http://www.gnu.org/licenses/>.
*/
import de.openedu.jfunktion.gui.GuiBuilder;

public class JFunktion
{

	public JFunktion()
	{
		System.out.println("CPU-Cores: "+ Runtime.getRuntime().availableProcessors() );
		
		new GuiBuilder();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{	
		new JFunktion();
	}

}
