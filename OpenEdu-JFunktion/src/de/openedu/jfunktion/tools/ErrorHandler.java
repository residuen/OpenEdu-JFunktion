package de.openedu.jfunktion.tools;

public class ErrorHandler 
{
	public static String getErrorMassageInfo(Exception e)
	{
		String retStr = "";
		
		System.out.println("e="+e);
		
		if(e.getMessage().contains("factorial"))
			retStr =" (Fehler im Fakulaetsausdruck)";
		else
			if(e.getMessage().contains("undefined"))
				retStr =" (Undefinierte Variablen oder Funktionen)";
		
		return retStr;
	}
}
