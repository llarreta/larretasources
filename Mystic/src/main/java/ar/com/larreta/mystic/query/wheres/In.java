package ar.com.larreta.mystic.query.wheres;

import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.query.Reference;
import ar.com.larreta.tools.Const;

@Component(In.NAME)
@Scope(Const.PROTOTYPE)
public class In extends Where {
	public static final String NAME = "In";
	
	protected Reference reference;
	private Collection values;
	
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	public Collection getValues() {
		return values;
	}
	public void setValues(Collection values) {
		this.values = values;
	}

	@Override
	public void setValues(Query query) {
		Iterator it = values.iterator();
		Integer index = 0;
		while (it.hasNext()) {
			Object value = it.next();
			query.setParameter(reference.getAlias() + index, value);
			index++;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getReferenceToString()); 
		builder.append(getPrefix());
		builder.append("(");
	
		Iterator it = values.iterator();
		Integer index = 0;
		Boolean first = Boolean.TRUE;
		while (it.hasNext()) {
			it.next();
			if (!first){
				builder.append(",");
			}
			builder.append(":");
			builder.append(reference.getAlias());
			builder.append(index);
			index++;
			first = Boolean.FALSE;
		}
		builder.append(")");
		
		return builder.toString();
	}
	protected String getPrefix() {
		return " IN ";
	}
	
	protected String getReferenceToString() {
		return reference.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof In) {
			In in = (In) obj;
			return reference.equals(in.getReference());			
		} 
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return reference.hashCode();
	}

}
