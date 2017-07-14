package ar.com.larreta.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	
	public static final String COMMA 	= ",";
	public static final String MAYOR_A 	= "A";
	public static final String MAYOR_E 	= "E";
	public static final String MAYOR_I 	= "I";
	public static final String MAYOR_O 	= "O";
	public static final String MAYOR_U 	= "U";
	public static final String MINOR_A 	= "a";
	public static final String MINOR_E 	= "e";
	public static final String MINOR_I 	= "i";
	public static final String MINOR_O 	= "o";
	public static final String MINOR_U 	= "u";	

	public static final Collection<String> VOCALS = Arrays.asList(MAYOR_A, MAYOR_E, MAYOR_I, MAYOR_O, MAYOR_U,
																   MINOR_A, MINOR_E, MINOR_I, MINOR_O, MINOR_U);
	
	/**
	 * Remueve las vocales de un texto
	 * @param text
	 * @return
	 */
	public static String vocalRemove(String text){
		String result = text;
		Iterator<String> it = VOCALS.iterator();
		while (it.hasNext()) {
			String vocal = (String) it.next();
			result = org.apache.commons.lang3.StringUtils.remove(result, vocal);
		}
		return result;
	}
	/**
	 * Splitea la phrase segun la cantidad de palabras que contenga
	 * @param phrase
	 * @return
	 */
	public static Collection<String> splitWords(String phrase){
		Collection<String> splitted = new ArrayList<>();
		Integer index = 1;
		Integer lastIndex = 0;
		while (index<phrase.length()){
			String actual = phrase.substring(index, index+1);
			if (org.apache.commons.lang3.StringUtils.isAllUpperCase(actual)){
				splitted.add(phrase.substring(lastIndex, index));
				lastIndex = index;
			}
			index++;
		}
		splitted.add(phrase.substring(lastIndex));
		return splitted;
	}
	
}
