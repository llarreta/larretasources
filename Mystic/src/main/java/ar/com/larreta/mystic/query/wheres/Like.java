package ar.com.larreta.mystic.query.wheres;

import java.io.Serializable;

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.query.Reference;
import ar.com.larreta.tools.Const;

@Component(Like.NAME)
@Scope(Const.PROTOTYPE)
public class Like extends Where {
	public static final String NAME = "Like";
	
	private Reference reference;
	private Serializable value;
	
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	public Serializable getValue() {
		return value;
	}
	public void setValue(Serializable value) {
		this.value = value;
	}

	@Override
	public void setValues(Query query) {
		query.setParameter(reference.getAlias(), "%" + value + "%");
	}
	
	@Override
	public String toString() {
		return getReferenceToString() + " LIKE :" + reference.getAlias();
	}
	
	protected String getReferenceToString() {
		return reference.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Equal) {
			Equal equal = (Equal) obj;
			return reference.equals(equal.getReference());			
		} 
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return reference.hashCode();
	}


}
