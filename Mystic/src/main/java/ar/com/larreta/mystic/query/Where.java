package ar.com.larreta.mystic.query;

import java.io.Serializable;

import org.hibernate.Query;

public abstract class Where implements Serializable {

	public void setValues(Query query){}
	
}
