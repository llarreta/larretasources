package ar.com.larreta.compiler.reservedwords;

public class RWOpenComment extends ReservedWord {
	private static final String EXPRESSION = "/*";

	public RWOpenComment() {
		super(EXPRESSION);
	}
}
