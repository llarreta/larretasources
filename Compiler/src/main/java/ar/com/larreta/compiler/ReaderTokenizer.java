package ar.com.larreta.compiler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


public class ReaderTokenizer {
	
	public static final Character SPACE 				= ' ';
	public static final Character TAB 					= '\t';
	public static final Character LN 					= '\n';
	public static final Character RN 					= '\r';
	public static final Character OPEN_BRACKETS 		= '{';
	public static final Character CLOSE_BRACKETS 		= '}';
	public static final Character OPEN_PARENTHESIS		= '(';
	public static final Character CLOSE_PARENTHESIS 	= ')';
	public static final Character SEMICOLON 			= ';';
	public static final Character DOT 					= '.';
	public static final Character AT 					= '@';
	public static final Character DOUBLE_QUOTES 		= '"';
	public static final Character EQUAL					= '=';
	public static final Character DENIER				= '!';
	public static final Character BAR					= '/';
	public static final Character STAR					= '*';
	public static final Character MINOR					= '<';
	public static final Character MAYOR					= '>';
	public static final Character PLUS					= '+';
	public static final Character SINGLE_QUOTES 		= '\'';
	public static final Character AMPERSAND				= '&';
	public static final Character PIPE					= '|';
	public static final Character SCAPE_BAR				= '\\';
	
	private static Set<Character> noSymbolic = getNoSymbolic();
	private static Set<Character> stoppers = getStoppers();
	
	private String fileName;
	private InputStreamReader reader;
	private Character lastChar;
	
	private Integer consecutiveBars = 0;
	
	public static Set<Character> getNoSymbolic(){
		Set<Character> noSymbolic = new HashSet<Character>();
		noSymbolic.add(SPACE);
		noSymbolic.add(TAB);
		noSymbolic.add(LN);
		noSymbolic.add(RN);
		return noSymbolic;
	}
	
	public static Set<Character> getStoppers(){
		Set<Character> stoppers = new HashSet<Character>();
		stoppers.addAll(noSymbolic);
		stoppers.add(OPEN_BRACKETS);
		stoppers.add(CLOSE_BRACKETS);
		stoppers.add(OPEN_PARENTHESIS);
		stoppers.add(CLOSE_PARENTHESIS);
		stoppers.add(SEMICOLON);
		stoppers.add(DOT);
		stoppers.add(AT);
		stoppers.add(DOUBLE_QUOTES);
		stoppers.add(EQUAL);
		stoppers.add(DENIER);
		stoppers.add(BAR);
		stoppers.add(STAR);
		stoppers.add(MINOR);
		stoppers.add(MAYOR);
		stoppers.add(PLUS);
		stoppers.add(SINGLE_QUOTES);
		stoppers.add(AMPERSAND);
		stoppers.add(PIPE);
		stoppers.add(SCAPE_BAR);
		return stoppers;
	}
	
	public ReaderTokenizer(String fileName){
		this.fileName = fileName;
		try {
			reader = new InputStreamReader(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			//FIXME: corregir trazas
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna si lo que sigue a continuacion es un comentario
	 */
	public Boolean isComment(){
		return (consecutiveBars==2);
	}
	
	/**
	 * Verifica lo que se va retornando para poder determinar a posterior si lo que sigue es un comentario
	 * @param text
	 * @return
	 */
	public String verifyReturn(String text){
		if ((text==null)||(text.length()<=0)){
			return null;
		}
		if (BAR.toString().equals(text)){
			consecutiveBars++;
		} else {
			consecutiveBars=0;
		}
		return text;
		
	}
	
	public String nextToken(){
		int r;
		StringBuilder token = new StringBuilder();
		try {
			
			if ((lastChar!=null) && (stoppers.contains(lastChar))){
				String toReturn = lastChar.toString();
				lastChar = null;
				return verifyReturn(toReturn);
			}
			
			while ((r = reader.read()) != -1) {
				lastChar = new Character((char) r);
				
				if ((!isComment() && stoppers.contains(lastChar)) || (isComment() && lastChar.equals(LN))) {
					if (noSymbolic.contains(lastChar)){
						lastChar = null;
					}
					if (token.length()>0){
						return verifyReturn(token.toString());
					}
					if (lastChar!=null){
						String toReturn = lastChar.toString();
						lastChar = null;
						return verifyReturn(toReturn);					
					}
				} else {
					token.append(lastChar);
				}
			}
			if (token.length()>0){
				return verifyReturn(token.toString());
			}
			return verifyReturn(null);
		} catch (IOException e) {
			//FIXME: corregir trazas
			e.printStackTrace();
		}
		
		return verifyReturn(null);
	}
}

