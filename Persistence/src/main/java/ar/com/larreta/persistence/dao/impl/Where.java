package ar.com.larreta.persistence.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public abstract class Where extends QueryElement {

	private String operator;
	private Object value;
	protected String alias = StringUtils.EMPTY;

	public Where(LoadArguments args, String field, String operator, Object value){
		this.args = args;
		this.name = field;
		this.operator = operator;
		this.value = value;
		if (!StringUtils.isEmpty(name)){
			this.alias = generateSymbol(args, name);
		}
		getSymbolicName();
	}
		
	public String getPostOperatorSection(){
		return StandardDAOImpl.TWO_POINTS + alias;
	}
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	public void setQueryValue(Query query){
		query.setParameter(alias, getValue());
	}
	
}
