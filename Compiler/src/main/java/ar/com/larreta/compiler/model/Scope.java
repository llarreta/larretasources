package ar.com.larreta.compiler.model;

import java.util.HashMap;
import java.util.Map;

import ar.com.larreta.compiler.reservedwords.RWPrivate;
import ar.com.larreta.compiler.reservedwords.RWProtected;
import ar.com.larreta.compiler.reservedwords.RWPublic;
import ar.com.larreta.compiler.reservedwords.ReservedWord;

public abstract class Scope extends JavaProgramUnit {

	public static Scope PUBLIC_SCOPE = new Public();
	public static Scope PRIVATE_SCOPE = new Private();
	public static Scope PROTECTED_SCOPE = new Protected();
	
	private static Map<ReservedWord, Scope> scopes = getScopes();
	
	private static Map<ReservedWord, Scope> getScopes(){
		Map<ReservedWord, Scope> scopes = new HashMap<ReservedWord, Scope>();
		scopes.put(new RWPublic(), new Public());
		scopes.put(new RWProtected(), new Protected());
		scopes.put(new RWPrivate(), new Private());
		return scopes;
	}

	public static Scope getScope(ReservedWord word){
		return scopes.get(word);
	}
	
}
