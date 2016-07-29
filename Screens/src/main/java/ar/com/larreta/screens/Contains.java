package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.persistence.dao.impl.Like;
import ar.com.larreta.commons.persistence.dao.impl.Where;

@Entity
@DiscriminatorValue(value = Contains.CONTAINS)
public class Contains extends FilterMatchMode {

	public static final String CONTAINS = "contains";

	public Contains(){
	}

	public Where toWhere(LoadArguments args) {
		return new Like(args, getDescription(), getValue());
	}
	
}
