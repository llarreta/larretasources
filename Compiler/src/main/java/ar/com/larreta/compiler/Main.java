package ar.com.larreta.compiler;

import ar.com.larreta.compiler.exceptions.CompilerException;
import ar.com.larreta.compiler.model.JavaFile;
import ar.com.larreta.compiler.model.ModelManager;

public class Main {

	public static void main(String[] args) {
		//JavaTokenizerTransformer tokenizer = new JavaTokenizerTransformer("C://Ambientes/Workspaces/Larreta/JavaParser/src/main/java/ar/com/larreta/java/parser/SourceCodeExtractor.java");
		//JavaTokenizerTransformer tokenizer = new JavaTokenizerTransformer("C://Ambientes/Workspaces/Larreta/Commons/src/main/java/ar/com/larreta/commons/AppManager.java");
		//JavaTokenizerTransformer tokenizer = new JavaTokenizerTransformer("C://Ambientes/Workspaces/Larreta/Compiler/src/main/java/ar/com/larreta/compiler/ReaderTokenizer.java");
		JavaTokenizerTransformer tokenizer = new JavaTokenizerTransformer("C://Ambientes/Workspaces/Larreta/Compiler/src/main/java/ar/EasyTest.java");

		
		ModelManager manager = new ModelManager(tokenizer.tokens());
		try {
			JavaFile javaFile = manager.getJava();
			javaFile.toString();
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
