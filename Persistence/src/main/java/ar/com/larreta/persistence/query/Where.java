package ar.com.larreta.persistence.query;

import java.io.Serializable;

import org.hibernate.Query;

public abstract class Where implements Serializable {

	public void setValues(Query query){}
	
}
