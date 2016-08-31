/**
 * 
 */
package co.com.directv.sdii.reports.commands;

import java.util.HashMap;
import java.util.StringTokenizer;

import co.com.directv.sdii.ejb.business.BusinessBase;

/**
 * Clase base de los comandos.
 * 
 * Fecha: 15-02-2011
 * 
 * @author jvelezmu
 *
 */
public class BaseCommand extends BusinessBase{

	/**
	 * Obtiene un HashMap con los parametros que han sido pasados
	 * al comando que se desea ejecutar.
	 * La lista de parametros son parejas param1=valor1;param2=valor2 ...
	 * 
	 * @param args
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getParams(String args){
		HashMap<String, String> map = new HashMap<String, String>();
		if (args != null && !args.trim().isEmpty()){
			StringTokenizer strTokenizer = new StringTokenizer(args, ";");
			StringTokenizer tkTokenizer;
			String token;
			String parmName;
			String parmValue;
			for(;strTokenizer.hasMoreTokens();){
				token = strTokenizer.nextToken();
				tkTokenizer = new StringTokenizer(token, "=");
				if (tkTokenizer.hasMoreTokens())
					parmName = tkTokenizer.nextToken();
				else 
					parmName = "";
				
				if (tkTokenizer.hasMoreTokens())
					parmValue = tkTokenizer.nextToken();
				else
					parmValue = "";
				
				map.put(parmName, parmValue);
			}
		}
		return map;
	}
}
