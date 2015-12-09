package ar.com.larreta.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.larreta.compiler.model.JavaProgramUnit;
import ar.com.larreta.compiler.reservedwords.RWAmpersand;
import ar.com.larreta.compiler.reservedwords.RWAt;
import ar.com.larreta.compiler.reservedwords.RWBar;
import ar.com.larreta.compiler.reservedwords.RWCatch;
import ar.com.larreta.compiler.reservedwords.RWClass;
import ar.com.larreta.compiler.reservedwords.RWCloseBracket;
import ar.com.larreta.compiler.reservedwords.RWCloseParenthesis;
import ar.com.larreta.compiler.reservedwords.RWComma;
import ar.com.larreta.compiler.reservedwords.RWDenier;
import ar.com.larreta.compiler.reservedwords.RWDistinct;
import ar.com.larreta.compiler.reservedwords.RWDot;
import ar.com.larreta.compiler.reservedwords.RWDoubleQuotes;
import ar.com.larreta.compiler.reservedwords.RWElse;
import ar.com.larreta.compiler.reservedwords.RWEqual;
import ar.com.larreta.compiler.reservedwords.RWEqualEqual;
import ar.com.larreta.compiler.reservedwords.RWExtends;
import ar.com.larreta.compiler.reservedwords.RWFinal;
import ar.com.larreta.compiler.reservedwords.RWFinally;
import ar.com.larreta.compiler.reservedwords.RWIf;
import ar.com.larreta.compiler.reservedwords.RWImport;
import ar.com.larreta.compiler.reservedwords.RWMayor;
import ar.com.larreta.compiler.reservedwords.RWMinor;
import ar.com.larreta.compiler.reservedwords.RWNew;
import ar.com.larreta.compiler.reservedwords.RWOpenBracket;
import ar.com.larreta.compiler.reservedwords.RWOpenParenthesis;
import ar.com.larreta.compiler.reservedwords.RWPackage;
import ar.com.larreta.compiler.reservedwords.RWPipe;
import ar.com.larreta.compiler.reservedwords.RWPlus;
import ar.com.larreta.compiler.reservedwords.RWPrivate;
import ar.com.larreta.compiler.reservedwords.RWProtected;
import ar.com.larreta.compiler.reservedwords.RWPublic;
import ar.com.larreta.compiler.reservedwords.RWReturn;
import ar.com.larreta.compiler.reservedwords.RWScapeBar;
import ar.com.larreta.compiler.reservedwords.RWSemicolon;
import ar.com.larreta.compiler.reservedwords.RWSingleQuotes;
import ar.com.larreta.compiler.reservedwords.RWStar;
import ar.com.larreta.compiler.reservedwords.RWStatic;
import ar.com.larreta.compiler.reservedwords.RWThis;
import ar.com.larreta.compiler.reservedwords.RWTry;
import ar.com.larreta.compiler.reservedwords.RWVoid;
import ar.com.larreta.compiler.reservedwords.RWWhile;
import ar.com.larreta.compiler.reservedwords.ReservedWord;

public class JavaTokenizerTransformer {
	
	private ReaderTokenizer tokenizer;
	
	private Map<String, JavaProgramUnit> expressions = new HashMap<String, JavaProgramUnit>();

	public JavaTokenizerTransformer(String fileName){
		tokenizer = new ReaderTokenizer(fileName);
		addExpression(new RWImport());
		addExpression(new RWPackage());
		addExpression(new RWSemicolon());
		addExpression(new RWPublic());
		addExpression(new RWProtected());
		addExpression(new RWPrivate());
		addExpression(new RWClass());
		addExpression(new RWOpenBracket());
		addExpression(new RWCloseBracket());
		addExpression(new RWVoid());
		addExpression(new RWExtends());
		addExpression(new RWOpenParenthesis());
		addExpression(new RWCloseParenthesis());
		addExpression(new RWComma());
		addExpression(new RWTry());
		addExpression(new RWCatch());
		addExpression(new RWFinally());
		addExpression(new RWIf());
		addExpression(new RWWhile());
		addExpression(new RWReturn());
		addExpression(new RWNew());
		addExpression(new RWEqual());
		addExpression(new RWDistinct());
		addExpression(new RWDot());
		addExpression(new RWAt());
		addExpression(new RWStatic());
		addExpression(new RWFinal());
		addExpression(new RWDoubleQuotes());
		addExpression(new RWThis());
		addExpression(new RWEqualEqual());
		addExpression(new RWDenier());
		addExpression(new RWBar());
		addExpression(new RWStar());
		addExpression(new RWMinor());
		addExpression(new RWMayor());
		addExpression(new RWPlus());
		addExpression(new RWSingleQuotes());
		addExpression(new RWAmpersand());
		addExpression(new RWPipe());
		addExpression(new RWElse());
		addExpression(new RWScapeBar());
	}
	
	private void addExpression(ReservedWord expression){
		expressions.put(expression.getText(), expression);
	}
	
	public List<JavaProgramUnit> tokens(){
		List<JavaProgramUnit> tokens = new ArrayList<JavaProgramUnit>();
		String actual;
		while ((actual = tokenizer.nextToken())!=null) {
			JavaProgramUnit expression = expressions.get(actual);
			if (expression==null){
				expression = new Expression(actual);
			}
			tokens.add(expression);
		}
		return tokens;
	}
	
	
	
}
