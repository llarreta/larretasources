package ar.com.larreta.compiler.model;

import java.util.List;

/**
 * Representa la firma de un metodo que puede detectarse en el encabezado de un metodo declarado o cuando este se invoca
 */
public class MethodSignature extends Instruction {
	
	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		expressions = inProgress;
	}
	
}
