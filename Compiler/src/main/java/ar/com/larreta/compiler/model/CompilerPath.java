package ar.com.larreta.compiler.model;

import java.util.HashMap;

import ar.com.larreta.compiler.Expression;
import ar.com.larreta.compiler.reservedwords.RWAmpersand;
import ar.com.larreta.compiler.reservedwords.RWAnd;
import ar.com.larreta.compiler.reservedwords.RWAt;
import ar.com.larreta.compiler.reservedwords.RWBar;
import ar.com.larreta.compiler.reservedwords.RWCatch;
import ar.com.larreta.compiler.reservedwords.RWClass;
import ar.com.larreta.compiler.reservedwords.RWCloseBracket;
import ar.com.larreta.compiler.reservedwords.RWCloseComment;
import ar.com.larreta.compiler.reservedwords.RWCloseParenthesis;
import ar.com.larreta.compiler.reservedwords.RWComma;
import ar.com.larreta.compiler.reservedwords.RWComparetor;
import ar.com.larreta.compiler.reservedwords.RWDenier;
import ar.com.larreta.compiler.reservedwords.RWDistinct;
import ar.com.larreta.compiler.reservedwords.RWDot;
import ar.com.larreta.compiler.reservedwords.RWDoubleQuotes;
import ar.com.larreta.compiler.reservedwords.RWElse;
import ar.com.larreta.compiler.reservedwords.RWEqual;
import ar.com.larreta.compiler.reservedwords.RWEqualEqual;
import ar.com.larreta.compiler.reservedwords.RWExtends;
import ar.com.larreta.compiler.reservedwords.RWFinal;
import ar.com.larreta.compiler.reservedwords.RWIf;
import ar.com.larreta.compiler.reservedwords.RWImport;
import ar.com.larreta.compiler.reservedwords.RWLogical;
import ar.com.larreta.compiler.reservedwords.RWMayor;
import ar.com.larreta.compiler.reservedwords.RWMayorOrEqual;
import ar.com.larreta.compiler.reservedwords.RWMinor;
import ar.com.larreta.compiler.reservedwords.RWMinorOrEqual;
import ar.com.larreta.compiler.reservedwords.RWNew;
import ar.com.larreta.compiler.reservedwords.RWOpenBracket;
import ar.com.larreta.compiler.reservedwords.RWOpenComment;
import ar.com.larreta.compiler.reservedwords.RWOpenParenthesis;
import ar.com.larreta.compiler.reservedwords.RWOr;
import ar.com.larreta.compiler.reservedwords.RWPackage;
import ar.com.larreta.compiler.reservedwords.RWPipe;
import ar.com.larreta.compiler.reservedwords.RWPlus;
import ar.com.larreta.compiler.reservedwords.RWReturn;
import ar.com.larreta.compiler.reservedwords.RWScapeBar;
import ar.com.larreta.compiler.reservedwords.RWScope;
import ar.com.larreta.compiler.reservedwords.RWSemicolon;
import ar.com.larreta.compiler.reservedwords.RWSingleQuotes;
import ar.com.larreta.compiler.reservedwords.RWStar;
import ar.com.larreta.compiler.reservedwords.RWStatic;
import ar.com.larreta.compiler.reservedwords.RWThis;
import ar.com.larreta.compiler.reservedwords.RWTry;
import ar.com.larreta.compiler.reservedwords.RWVoid;
import ar.com.larreta.compiler.reservedwords.RWWhile;

public class CompilerPath extends HashMap<Class, Object> {
	
	public CompilerPath(Boolean initialize){
		if (initialize){
			initializeLevel0();			
		}
	}

	private void initializeLevel0() {
		// Package & Import
		addPath(RWPackage.class).addPath(Expression.class).addPath(RWSemicolon.class, Package.class);
		addPath(RWPackage.class).addPath(Expression.class).addPath(RWDot.class, addPath(RWPackage.class));
		addPath(RWImport.class).addPath(Expression.class).addPath(RWSemicolon.class, Import.class);
		addPath(RWImport.class).addPath(Expression.class).addPath(RWDot.class, addPath(RWImport.class));
		
		//Attributes declarations
		addPath(RWScope.class).addPath(Expression.class).addPath(Expression.class).addPath(RWSemicolon.class, Attribute.class);
		addPath(RWScope.class).addPath(Assignation.class, Attribute.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(Assignation.class, Attribute.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(RWFinal.class).addPath(Assignation.class, Attribute.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(RWFinal.class).addPath(Expression.class).addPath(Assignation.class, Attribute.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(RWFinal.class).addPath(Expression.class).addPath(Expression.class).addPath(RWSemicolon.class, Attribute.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(Expression.class).addPath(Expression.class).addPath(RWSemicolon.class, Attribute.class);

		//Attributes declarations without scope
		addPath(Expression.class).addPath(Expression.class).addPath(RWSemicolon.class, Attribute.class);
		addPath(RWStatic.class).addPath(Assignation.class, Attribute.class);
		addPath(RWStatic.class).addPath(RWFinal.class).addPath(Assignation.class, Attribute.class);
		addPath(RWStatic.class).addPath(RWFinal.class).addPath(Expression.class).addPath(Assignation.class, Attribute.class);
		addPath(RWStatic.class).addPath(RWFinal.class).addPath(Expression.class).addPath(Expression.class).addPath(RWSemicolon.class, Attribute.class);
		addPath(RWStatic.class).addPath(Expression.class).addPath(Expression.class).addPath(RWSemicolon.class, Attribute.class);

		
		//New instance
		addPath(RWNew.class).addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(RWCloseParenthesis.class, New.class);
		addPath(RWNew.class).addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(RWCloseParenthesis.class, New.class);
		addPath(RWNew.class).addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(RWComma.class, 
																				addPath(RWNew.class).addPath(Expression.class).addPath(RWOpenParenthesis.class));
		addPath(RWNew.class).addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWCloseParenthesis.class, New.class);
		addPath(RWNew.class).addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWComma.class, 
																				addPath(RWNew.class).addPath(Expression.class).addPath(RWOpenParenthesis.class));
		addPath(RWNew.class).addPath(MethodSignature.class, New.class);
		
		// Expression Type
		addPath(RWMinor.class).addPath(Expression.class).addPath(RWMayor.class, ExpressionType.class);
		
		// Expression with expression type
		addPath(Expression.class).addPath(ExpressionType.class, ExpressionWithExpressionType.class);
		
		// Instructions
		addPath(Expression.class).addPath(RWDot.class).addPath(MethodSignature.class, Instruction.class);
		addPath(MethodSignature.class).addPath(RWDot.class).addPath(MethodSignature.class, Instruction.class);

		// Body
		addPath(RWOpenBracket.class).addPath(RWCloseBracket.class, Body.class);
		addPath(RWOpenBracket.class).addPath(Instruction.class).addPath(RWCloseBracket.class, Body.class);
		addPath(RWOpenBracket.class).addPath(Instruction.class, addPath(RWOpenBracket.class));
		addPath(RWOpenBracket.class).addPath(Instruction.class).addPath(RWSemicolon.class, addPath(RWOpenBracket.class));
		addPath(RWOpenBracket.class).addPath(Comment.class, addPath(RWOpenBracket.class));
		
		// Constructor declaration
		addPath(RWScope.class).addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(RWCloseParenthesis.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWCloseParenthesis.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWComma.class, addPath(RWScope.class).addPath(Expression.class).addPath(RWOpenParenthesis.class));
		addPath(RWScope.class).addPath(MethodSignature.class).addPath(Body.class, Method.class);

		addPath(RWScope.class).addPath(RWStatic.class).addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(RWCloseParenthesis.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWCloseParenthesis.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWComma.class, addPath(RWScope.class).addPath(Expression.class).addPath(RWOpenParenthesis.class));
		addPath(RWScope.class).addPath(RWStatic.class).addPath(MethodSignature.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(RWStatic.class).addPath(Expression.class).addPath(MethodSignature.class).addPath(Body.class, Method.class);
		
		// Method Signature
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(RWCloseParenthesis.class, MethodSignature.class);
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(RWCloseParenthesis.class, MethodSignature.class);
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWCloseParenthesis.class, MethodSignature.class);
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(StringConstant.class).addPath(RWCloseParenthesis.class, MethodSignature.class);

		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(RWComma.class, 
																							addPath(Expression.class).addPath(RWOpenParenthesis.class));
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWComma.class, 
																							addPath(Expression.class).addPath(RWOpenParenthesis.class));
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(StringConstant.class).addPath(RWComma.class, 
																							addPath(Expression.class).addPath(RWOpenParenthesis.class));

		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(RWDot.class, 
																							addPath(Expression.class).addPath(RWOpenParenthesis.class));
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWDot.class, 
																							addPath(Expression.class).addPath(RWOpenParenthesis.class));
		
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(Expression.class).addPath(RWCloseParenthesis.class, MethodSignature.class);
		addPath(Expression.class).addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(Expression.class).addPath(RWComma.class, 
																							addPath(Expression.class).addPath(RWOpenParenthesis.class));

		// Method declaration
		addPath(RWScope.class).addPath(Expression.class).addPath(MethodSignature.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWCloseParenthesis.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(Expression.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWComma.class, addPath(RWScope.class).addPath(Expression.class).addPath(Expression.class).addPath(RWOpenParenthesis.class));
		
		addPath(RWScope.class).addPath(RWVoid.class).addPath(MethodSignature.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(RWVoid.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWCloseParenthesis.class).addPath(Body.class, Method.class);
		addPath(RWScope.class).addPath(RWVoid.class).addPath(Expression.class)
											.addPath(RWOpenParenthesis.class)
											.addPath(Expression.class).addPath(Expression.class)
											.addPath(RWComma.class, addPath(RWScope.class).addPath(RWVoid.class).addPath(Expression.class).addPath(RWOpenParenthesis.class));
		
		
		// Parenthesis agrupation
		addPath(RWOpenParenthesis.class).addPath(Assignation.class).addPath(RWCloseParenthesis.class, Instruction.class);
		
		// While
		addPath(RWWhile.class).addPath(RWOpenParenthesis.class).addPath(Comparation.class).addPath(RWCloseParenthesis.class).addPath(Body.class, While.class);
		
		// If
		addPath(RWIf.class).addPath(RWOpenParenthesis.class).addPath(Comparation.class).addPath(RWCloseParenthesis.class).addPath(Body.class, If.class);
		addPath(RWIf.class).addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWCloseParenthesis.class).addPath(Body.class, If.class);
		addPath(If.class).addPath(RWElse.class).addPath(Body.class, If.class);
		
		// Try
		addPath(RWTry.class).addPath(Body.class).addPath(RWCatch.class).addPath(RWOpenParenthesis.class)
												.addPath(Expression.class).addPath(Expression.class)
												.addPath(RWCloseParenthesis.class).addPath(Body.class, Try.class);
		// Class
		addPath(RWScope.class).addPath(RWClass.class).addPath(Expression.class).addPath(RWExtends.class).addPath(Expression.class).addPath(Body.class, JavaClass.class);
		addPath(RWScope.class).addPath(RWClass.class).addPath(Expression.class).addPath(Body.class, JavaClass.class);
		
		// Java File
		addPath(Package.class).addPath(JavaClass.class, JavaFile.class);
		addPath(Package.class).addPath(Import.class, addPath(Package.class));
		addPath(Package.class).addPath(Comment.class, addPath(Package.class));
		addPath(Package.class).addPath(Annotation.class, addPath(Package.class));
		
		// String constant
		addPath(RWDoubleQuotes.class).addPath(RWDoubleQuotes.class, StringConstant.class);
		addPath(RWDoubleQuotes.class).addPath(JavaProgramUnit.class).addPath(RWDoubleQuotes.class, StringConstant.class);
		addPath(RWDoubleQuotes.class).addPath(JavaProgramUnit.class).addPath(JavaProgramUnit.class, addPath(RWDoubleQuotes.class));
		addPath(RWDoubleQuotes.class).addPath(RWScapeBar.class).addPath(JavaProgramUnit.class, addPath(RWDoubleQuotes.class));
		addPath(RWDoubleQuotes.class).addPath(RWScapeBar.class).addPath(RWDoubleQuotes.class).addPath(RWDoubleQuotes.class, StringConstant.class);
		
		// Character constant
		addPath(RWSingleQuotes.class).addPath(RWSingleQuotes.class, CharacterConstant.class);
		addPath(RWSingleQuotes.class).addPath(JavaProgramUnit.class).addPath(RWSingleQuotes.class, CharacterConstant.class);
		addPath(RWSingleQuotes.class).addPath(RWScapeBar.class).addPath(JavaProgramUnit.class).addPath(RWSingleQuotes.class, CharacterConstant.class);
		addPath(RWSingleQuotes.class).addPath(RWScapeBar.class).addPath(RWSingleQuotes.class).addPath(RWSingleQuotes.class, CharacterConstant.class);
		
		// Comparators
		addPath(RWEqual.class).addPath(RWEqual.class, RWEqualEqual.class);
		addPath(RWDenier.class).addPath(RWEqual.class, RWDistinct.class);
		addPath(RWMinor.class).addPath(RWEqual.class, RWMinorOrEqual.class);
		addPath(RWMayor.class).addPath(RWEqual.class, RWMayorOrEqual.class);
		
		// Logical
		addPath(RWAmpersand.class).addPath(RWAmpersand.class, RWAnd.class);
		addPath(RWPipe.class).addPath(RWPipe.class, RWOr.class);
		
		// Comments
		addPath(RWBar.class).addPath(RWStar.class, RWOpenComment.class);
		addPath(RWStar.class).addPath(RWBar.class, RWCloseComment.class);
		addPath(RWOpenComment.class).addPath(RWCloseComment.class, Comment.class);
		addPath(RWOpenComment.class).addPath(JavaProgramUnit.class, addPath(RWOpenComment.class));
		addPath(RWBar.class).addPath(RWBar.class).addPath(Expression.class, Comment.class);
		
		// Cast
		addPath(RWOpenParenthesis.class).addPath(Expression.class).addPath(RWCloseParenthesis.class, Cast.class);
		
	}


	public void initializeLevel1() {
		// Return declarations
		addPath(RWReturn.class).addPath(RWSemicolon.class, Return.class);
		addPath(RWReturn.class).addPath(JavaProgramUnit.class, addPath(RWReturn.class));
		
		// Instructions
		addPath(Expression.class).addPath(RWDot.class).addPath(Expression.class, Instruction.class);
		addPath(RWThis.class).addPath(RWDot.class).addPath(Assignation.class, Instruction.class);
		addPath(Expression.class).addPath(RWDot.class).addPath(Instruction.class, Instruction.class);
		addPath(Expression.class).addPath(RWSemicolon.class, Instruction.class);
		addPath(RWThis.class).addPath(RWSemicolon.class, Instruction.class);
		addPath(Expression.class).addPath(RWPlus.class).addPath(RWPlus.class, Instruction.class);
		addPath(Instruction.class).addPath(RWPlus.class).addPath(Instruction.class, Instruction.class);
		addPath(Instruction.class).addPath(RWDot.class).addPath(MethodSignature.class, Instruction.class);
	}
	
	public void initializeLevel2() {
		// Comparations
		addPath(Expression.class).addPath(RWComparetor.class).addPath(Expression.class, Comparation.class);
		addPath(Instruction.class).addPath(RWComparetor.class).addPath(Expression.class, Comparation.class);
		addPath(Expression.class).addPath(RWComparetor.class).addPath(Instruction.class, Comparation.class);
		addPath(MethodSignature.class).addPath(RWComparetor.class).addPath(Expression.class, Comparation.class);
		addPath(Expression.class).addPath(RWComparetor.class).addPath(MethodSignature.class, Comparation.class);
		addPath(RWDenier.class).addPath(Instruction.class, Comparation.class);
		addPath(RWDenier.class).addPath(Expression.class, Comparation.class);
		
		addPath(RWOpenParenthesis.class).addPath(Comparation.class).addPath(RWCloseParenthesis.class).addPath(RWLogical.class)
								.addPath(RWOpenParenthesis.class).addPath(Comparation.class).addPath(RWCloseParenthesis.class, Comparation.class);
		addPath(RWOpenParenthesis.class).addPath(Comparation.class).addPath(RWCloseParenthesis.class).addPath(RWLogical.class)
								.addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWCloseParenthesis.class, Comparation.class);
		addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWCloseParenthesis.class).addPath(RWLogical.class)
								.addPath(RWOpenParenthesis.class).addPath(Comparation.class).addPath(RWCloseParenthesis.class, Comparation.class);
		addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWCloseParenthesis.class).addPath(RWLogical.class)
								.addPath(RWOpenParenthesis.class).addPath(Instruction.class).addPath(RWCloseParenthesis.class, Comparation.class);

		addPath(Comparation.class).addPath(RWLogical.class)
								.addPath(Comparation.class, Comparation.class);
		addPath(Comparation.class).addPath(RWLogical.class)
								.addPath(Instruction.class, Comparation.class);
		addPath(Instruction.class).addPath(RWLogical.class)
								.addPath(Comparation.class, Comparation.class);
		addPath(Instruction.class).addPath(RWLogical.class)
								.addPath(Instruction.class, Comparation.class);
		addPath(MethodSignature.class).addPath(RWLogical.class)
								.addPath(Instruction.class, Comparation.class);
		addPath(Instruction.class).addPath(RWLogical.class)
								.addPath(MethodSignature.class, Comparation.class);
		addPath(MethodSignature.class).addPath(RWLogical.class)
							.addPath(MethodSignature.class, Comparation.class);

		
		// Declaration with Assignation
		addPath(Expression.class).addPath(Expression.class).addPath(RWEqual.class).addPath(Instruction.class, Assignation.class);
		addPath(Expression.class).addPath(Expression.class).addPath(RWEqual.class).addPath(Expression.class, Assignation.class);
		addPath(Expression.class).addPath(Expression.class).addPath(RWEqual.class).addPath(StringConstant.class, Assignation.class);
		
		// Only Assignation
		addPath(Expression.class).addPath(RWEqual.class).addPath(Expression.class, Assignation.class);
		addPath(Expression.class).addPath(RWEqual.class).addPath(Instruction.class, Assignation.class);	
		addPath(Expression.class).addPath(RWEqual.class).addPath(StringConstant.class, Assignation.class);	
		addPath(Expression.class).addPath(RWEqual.class).addPath(CharacterConstant.class, Assignation.class);	
		
		// Annotation
		addPath(RWAt.class).addPath(Expression.class, Annotation.class);
		addPath(RWAt.class).addPath(MethodSignature.class, Annotation.class);
	}
	
	public CompilerPath(){
		this(Boolean.FALSE);
	}
	
	@Override
	public Object get(Object key) {
		Object toReturn = null;
		if (key instanceof Class) {
			Class type = (Class) key;

			while (((toReturn=super.get(type))==null) && !(type.equals(Object.class))){
				type = type.getSuperclass();
			}
		}
		return toReturn;
	}
	
	public CompilerPath addPath(Class type){
		// get without recursive
		CompilerPath compilerPath = (CompilerPath) super.get(type);
		if (compilerPath==null){
			compilerPath = new CompilerPath();
			put(type, compilerPath);
		}
		return compilerPath;
	}

	public void addPath(Class type, CompilerPath compilerPath){
		put(type, compilerPath);
	}
	
	public void addPath(Class type, Class model){
		put(type, model);
	}
	
	public CompilerPath getPath(Class type){
		return (CompilerPath) get(type);
	}
	
}
