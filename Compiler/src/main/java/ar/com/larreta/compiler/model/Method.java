package ar.com.larreta.compiler.model;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ar.com.larreta.compiler.Expression;
import ar.com.larreta.compiler.reservedwords.RWCloseParenthesis;
import ar.com.larreta.compiler.reservedwords.RWOpenParenthesis;
import ar.com.larreta.compiler.reservedwords.RWScope;
import ar.com.larreta.compiler.reservedwords.RWStatic;
import ar.com.larreta.compiler.reservedwords.ReservedWord;

public class Method extends Instruction {
	
	protected Scope scope;
	protected Expression type;
	protected Expression name;
	protected Body body;
	
	protected Boolean voidMethod = Boolean.FALSE;
	protected Boolean staticMethod = Boolean.FALSE;

	public Boolean getStaticMethod() {
		return staticMethod;
	}

	public void setStaticMethod(Boolean staticMethod) {
		this.staticMethod = staticMethod;
	}

	public Boolean getVoidMethod() {
		return voidMethod;
	}

	public void setVoidMethod(Boolean voidMethod) {
		this.voidMethod = voidMethod;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

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

	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		ListIterator<JavaProgramUnit> it = inProgress.listIterator();
		
		while (it.hasNext()) {
			JavaProgramUnit javaProgramUnit = (JavaProgramUnit) it.next();
			
			if (javaProgramUnit instanceof RWOpenParenthesis){
				break;
			}
			
			if ((scope==null) && (javaProgramUnit instanceof RWScope)){
				scope =  Scope.getScope((ReservedWord) javaProgramUnit);
			}
			
			if (javaProgramUnit instanceof RWStatic) {
				staticMethod = Boolean.TRUE;
			}
			
			if (javaProgramUnit instanceof Body) {
				body = (Body) javaProgramUnit;
			}

			if (javaProgramUnit instanceof MethodSignature) {
				MethodSignature methodSignature = (MethodSignature) javaProgramUnit;
				Iterator<JavaProgramUnit> itMethod = methodSignature.getExpressions().iterator();
				while (itMethod.hasNext()) {
					JavaProgramUnit unitMethod = (JavaProgramUnit) itMethod.next();
					if (unitMethod instanceof RWOpenParenthesis){
						break;
					}
					assignTypeAndName(unitMethod);
				}
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
