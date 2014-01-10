package de.openedu.jfunktion.test;

import java.util.Stack;
import java.util.Vector;

/**
 * ShuntingYard: Klasse zur implementierung eines Algorithmus
 * 				 zur Umwandlung eines mathematischen Ausdrucks
 * 				 von Infix-Notation zur Postfix-Notation
 *
 */
public class ShuntingYard
{
	private static final String EXAMPLE_EQUATION = "1+2+3*4+5/6*7";
	
	// Konstanten: Rechenoperatoren, in der Reihenfolge ihres Ranges
	private final Operator[] OPERATORS = new Operator[] { 	new Operator("+") , new Operator("-"),
															new Operator("*"), new Operator("/"),
															new Operator("^") };
	
//	private final String[] OPERATORS = new String[] { "+", "-", "*", "/", "^" };
	
	// Konstanten: Klammern 
	private final String[] ARROWS = new String[] { "(", ")" };
	                     
	// Konstanten: Rechenoperatoren und Klammern
	private final String[] SPECIAL_SIGNS = new String[] { "+", "-", "*", "/", "^", "(", ")" };
	
	// Ein mathematischer (Test)Ausdruck
	private String mathExpression = "1+2+3*4+5/6*7"; // 1 2 + 3 4 * + 5 6 / 7 * +
	
	private String rpnResult = null;

	/**
	 * Konstruktor: Mathematischer Infix-notierter
	 * 				Ausdruck als Uebergabeparameter
	 * 
	 * @param mathExpression
	 */
	public ShuntingYard(String mathExpression)
	{
		this.mathExpression = mathExpression;
	}

	/**
	 * Umformen und berechnen des mathematischen Ausdrucks
	 */
	public void compile()
	{
		// 1. Testen auf ungleich null und Gueltigkeit (bisher erst auf gueltige Klammerung) des Ausdrucks ...
		if(mathExpression != null && checkExpression())	
		{
			// ... dann start des Algorithmus
			spacingExpression();	// 2. Fuege Blanks zwischen Operanden und Operatoren ein
			transformToRpn();		// 3. Beginne die Infix-Postfix-Transformation
		}
		
		else
			System.err.println("Ausdruck fehlerhaft oder null!");
	}
	
	/**
	 * Ueberpruefung des Mathematischen Ausdrucks
	 * @return
	 */
	private boolean checkExpression()
	{
		boolean arrowCheck = checkArrows(); // Ueberpruefen auf korrekte Klammernanzahl

		return arrowCheck;
	}
	
	/**
	 * Teste, ob die Anzahl geoeffneter und geschlossener Klammern identisch sind
	 * @return
	 */
	private boolean checkArrows()
	{
		int leftArrow = 0, rightArrow = 0;
		
		for(char c : mathExpression.toCharArray())
		{
			if(c == '(')
				leftArrow++;
			else
				if(c == ')')
					rightArrow++;
		}
		
		return (leftArrow == rightArrow) ;
	}
	
	/**
	 * Fuegt zwischen Operanden und Operatoren leerstellen ein
	 */
	private void spacingExpression()
	{
		StringBuffer strB = new StringBuffer();
		
		boolean isOperator = false;		// Flag. ob es sich um einen Operator handelt
		boolean isSpecialSign = false;	// Flag, ob es sich um ein Sonderzeichen (z.B. "(" handelt)
		
		for(char c : mathExpression.toCharArray())
		{
			// ueberpruefe, ob eines der Sonderzeichen vorhanden ist
			for(String sign : SPECIAL_SIGNS)
			{
				isSpecialSign = ( (""+c).equals(sign) );
				
				if(isSpecialSign)
					break;
			}
			
			// Wenn ein Sonderzeichen vorhanden ist, fuege Leerzeichen in neue Zeichenkette ein
			if(isSpecialSign)
			{
				// Wenn schon ein Leerzeichen gesetzt wurde, keines vor dem Operator einfuegen
				if(!isOperator)
					strB.append(' ');
				
				strB.append(c);		// Zeichen in neue Zeichenkette einfuegen ...
				strB.append(' ');	// ... und ein Leerzeichen anfuegen
				
				isOperator = true;	// Kennzeichnen, dass schon ein Operator gesetzt wurde
			}
			else
			{
				strB.append(c);		// normales Zeichen in zeichenkette schreiben
				
				isOperator = false;	// Operator Kennzeichnung zurücksetzen
			}
		}
		
		mathExpression = strB.toString().trim();
		
//		System.out.println("expression="+ mathExpression);	// Ausgeben der Zeichenkette mit Leerzeichen
		
	}

	private void transformToRpn()
	{
		Stack<String> s1 = new Stack<String>();

		Vector<String> rpn = new Vector<String>();
		
		String[] ExprArr = mathExpression.split(" ");
		
		boolean isOperator;
//		boolean isArrow;
		
		for(String c : ExprArr)
		{
			isOperator = isOperator(c);	// Ueberpruefen, ob es sich um einen Operator handelt
			
			if(!isOperator) // c dem RPN-Queue zufuegen, wenn es kein Operator ist ...
			{
				rpn.add(c);	// Zeichen den PRN-Queue zufuegen
			}
			else			// ... ansonsten die Infix-nach-Postfix Operatorenvergleich beginnen
			{
				if(!s1.isEmpty())	// Sicherstellten, dass der Stack nicht leer ist
				{
					for(int i=0; i<2; i++)	// die obersten 2 bzw. 3 math.-Operatoren vergleichen
						doStackQueueOperation(s1, c, rpn);
				}
				
				s1.push(c);			// Operator auf Stack schreiben
			}
		}
		
		// Den Rest des Stacks in RPN-Queue schreiben
		while(!s1.isEmpty())
			rpn.add(s1.pop());
		
		rpnResult = rpn.toString().replaceAll(",", "").replaceAll("\\[", "").replaceAll("]", ""); // RPN-Ausdruck in String rpnResult schreiben
			
//		System.out.println("RPN   : "+rpnResult);
//		System.out.println("Lösung: 1 2 + 3 4 * + 5 6 / 7 * +");

	}
	
	/**
	 * Operatoren auf dem Stack vergleichen
	 * und gegebenfalls in RPN-Queue schreiben
	 * @param s1
	 * @param c
	 * @param rpn
	 */
	private void doStackQueueOperation(Stack<String> s1, String c,Vector<String> rpn)
	{
		if(s1.size() > 0)	// Sicherstellten, dass der Stack mindestens 1 Zeichen beinhaltet
		{
			int rankPrev = testPrecedence(s1.peek());	// Rang des vorherigen Operators ermitteln
			int rankCurrent = testPrecedence(c);		// Range des aktuellen Operators ermitteln
			
//			System.out.println("testPrecedence('"+c+"'))="+rankCurrent+" testPrecedence('"+s1.peek()+"')="+rankPrev);
			
			if(rankPrev >= rankCurrent)	// Wenn der Rang des vorherigen Operators hoeher ist als der aktuelle ...
			{							// ... wird das vorherige (Auf dem Stack noch oben) in den RPN-Queue geschrieben
				rpn.add(s1.pop());		// vorherigen Operator in RPN-Queue schreiben
			}
		}
	}

	/**
	 * Ueberpruefen des Ranges eines Operators
	 * @param precedence
	 * @return
	 */
	private int testPrecedence(String str)
	{
		for(int i=0; i<OPERATORS.length; i++)
			if( str.equals(OPERATORS[i].getOperator()) )
				return OPERATORS[i].getRank();

		return 0;
	}
	
	/**
	 * Ueberprueft, ob Zeichen ein Operator ist
	 * und gibt in diesem Fall true zurueck,
	 * ansonsten false.
	 * @param c
	 * @return
	 */
	private boolean isOperator(String c)
	{
		for(Operator op : OPERATORS)
		{
			if( c.equals(op.getOperator()) )
				return true;
		}
		
		return false;
	}
	
	/**
	 * Ueberprueft, ob Zeichen eine Klammer ist
	 * und gibt in diesem Fall true zurueck,
	 * ansonsten false.
	 * @param c
	 * @return
	 */
	private boolean isArrow(String c)
	{
		for(String sign : ARROWS)
		{
			if( c.equals(sign) )
				return true;
		}
		
		return false;
	}
	
	public String getRpnResult()
	{
		return rpnResult;
	}

	public String getMathExpression() {
		return mathExpression;
	}

	public void setMathExpression(String mathExpression) {
		this.mathExpression = mathExpression;
	}
	
	public class Operator
	{
		int rank = -1;
		
		String operator = null;
		
		public Operator(String operator)
		{
			this.operator = operator;
			
			if(operator.equals("+") || operator.equals("-"))
				rank = 0;
			else
				if(operator.equals("*") || operator.equals("/"))
					rank = 1;
				else
					rank = 2;
//					if(operator.equals("^"))
//						rank = 2;
		}
		
		public int getRank()
		{
			return rank;
		}
		
		public String getOperator()
		{
			return operator;
		}
	}
	
	public static void main(String[] args)
	{
		ShuntingYard sy = new ShuntingYard("x^2+4*x-4+sin{x}"); // EXAMPLE_EQUATION);
		
		sy.compile();
		
		System.out.println(sy.mathExpression);
		System.out.println(sy.getRpnResult());
	}
	
}
