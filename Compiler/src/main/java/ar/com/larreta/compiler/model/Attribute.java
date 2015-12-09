package ar.com.larreta.compiler.model;

import java.util.List;
import java.util.ListIterator;

import ar.com.larreta.compiler.Expression;
import ar.com.larreta.compiler.reservedwords.RWFinal;
import ar.com.larreta.compiler.reservedwords.RWScope;
import ar.com.larreta.compiler.reservedwords.RWStatic;
import ar.com.larreta.compiler.reservedwords.ReservedWord;

public class Attribute extends Instruction {

	private Scope scope;
	private Expression type;
	private Expression name;
	private Boolean staticAttribute = Boolean.FALSE;
	private Boolean finalAttribute = Boolean.FALSE;
	private Assignation assignation;

	public Expression getType() {
		return type;
	}

	public void setType(Expression type) {
		this.type = type;
	}

	public Expression getName() {
		return name;
	}

	public void setName(Expression name) {
		this.name = name;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	@Override
	//FIXME: Mejorar, tomar de ejemplo el process de method
	public void process(List<JavaProgramUnit> inProgress) {
		ListIterator<JavaProgramUnit> it = inProgress.listIterator();
		
		while (it.hasNext()) {
			JavaProgramUnit javaProgramUnit = (JavaProgramUnit) it.next();
			
			if ((scope==null) && (javaProgramUnit instanceof RWScope)){
				scope =  Scope.getScope((ReservedWord) javaProgramUnit);
			}
			
			if (javaProgramUnit instanceof RWStatic) {
				staticAttribute = Boolean.TRUE;
			}

			if (javaProgramUnit instanceof RWFinal) {
				finalAttribute = Boolean.TRUE;
			}
		
			if (javaProgramUnit instanceof Assignation){
				assignation = (Assignation) javaProgramUnit;
				name = (Expression) assignation.getExpressions().iterator().next();
				
			}
			
			assignTypeAndName(javaProgramUnit);
			
		}
		
		// si al final tenemos tipo pero no nombre, no puede ser
		// entonces intercambiamos ya que el tipo seguramente es el nombre
		if ((name==null) && (type!=null)){
			name=type;
			type=null;
		}		
	}
	
	private void assignTypeAndName(JavaProgramUnit javaProgramUnit) {
		if (javaProgramUnit instanceof Expression) {
			if (type==null){
				type = (Expression) javaProgramUnit;
			} else {
				if (name==null){
					name = (Expression) javaProgramUnit;
				}
			}
		}
	}

	
}
