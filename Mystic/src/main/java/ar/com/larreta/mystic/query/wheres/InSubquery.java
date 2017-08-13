package ar.com.larreta.mystic.query.wheres;

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.query.Reference;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.tools.Const;

@Component(InSubquery.NAME)
@Scope(Const.PROTOTYPE)
public class InSubquery extends Where {

	public static final String NAME = "InSubquery";
	
	protected Reference reference;
	protected Select select;
	
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	public Select getSelect() {
		return select;
	}
	public void setSelect(Select select) {
		this.select = select;
	}
	
	@Override
	public void setValues(Query query) {
		select.setValues(query);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getReferenceToString()); 
		builder.append(getPrefix());
		builder.append("(");
		builder.append(select.toString());
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
