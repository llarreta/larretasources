package ar.com.larreta.compiler.reservedwords;

public class RWCloseComment extends ReservedWord {
	private static final String EXPRESSION = "*/";

	public RWCloseComment() {
		super(EXPRESSION);
	}
}
