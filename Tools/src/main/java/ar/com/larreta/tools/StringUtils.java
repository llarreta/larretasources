package ar.com.larreta.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	
	private static final String COMMA = ",";
	private static final String VOCAL_TEXT = "A,E,I,O,U,a,e,i,o,u";
	
	private static final Collection<String> VOCALS = getVocals();
	
	public static Collection<String> getVocals(){
		Collection<String> vocals = Arrays.asList(VOCAL_TEXT.split(COMMA));
		return vocals;
	}
	
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
	
}
