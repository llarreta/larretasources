package co.com.directv.sdii.common.util;

import java.security.SecureRandom;


/**
 * 
 * Utilidad para generar un numero aleatorio. 
 * 
 * Fecha de Creación: 23/09/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class KeyUtil{
	
	/**
	 * 
	 * Constructor: genera numeros long con base 
	 * en un algoritmo de composicion aleatoria de numeros
	 * @author jalopez
	 */
	public KeyUtil(){
		
	}
	
	/**
	 * 
	 * Metodo:  Metodo que genera numeros long con base 
	 * en un algoritmo de composicion aleatoria de numeros.	 
	 * @return long
	 * @author jalopez
	 */
	public long getKey() {		
		StringBuffer strB = new StringBuffer("");
		strB.append(Math.abs(System.identityHashCode(this)));
		SecureRandom secRandom = new SecureRandom();
		int bValue = secRandom.nextInt(Integer.MAX_VALUE);
		bValue = (int) bValue/100;
		strB.append(Math.abs(bValue));
		return Long.parseLong(strB.toString());				
	}

	/**
	 * 
	 * Metodo: Metodo que genera numeros long con base 
	 * en un algoritmo de composicion aleatoria de numeros
	 * @return String Numero aleatorio
	 * @author jalopez
	 */
	public String getCompositeKey(){
		StringBuffer composite = new StringBuffer();
		composite.append(" ");
		composite.append("[TX: ");
		composite.append(this.getKey());
		composite.append("]");
		composite.append(" ");
		return composite.toString();
	}
}
