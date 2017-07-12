package ar.com.larreta.persistence.query;

import java.io.Serializable;

public abstract class Join implements Serializable {

	private String 		joinType;
	private Reference	reference;
	
	public Join(String joinType){
		this.joinType = joinType;
	}
	
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Join) {
			Join join = (Join) obj;
			return join.getReference().equals(this);
		}
		return Boolean.FALSE;
	}

	@Override
	public int hashCode() {
		return getReference().hashCode();
	}	
}
