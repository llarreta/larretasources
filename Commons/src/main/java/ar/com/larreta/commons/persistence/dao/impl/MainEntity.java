package ar.com.larreta.commons.persistence.dao.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.persistence.exceptions.UnreportedEntityException;

public class MainEntity implements Serializable {
	
	private static final String HIBERNATE_PROXY_PREFIX = "_$$";
	
	private Class type;
	protected String uniqueProjectionField;
	private String alias = StringUtils.EMPTY;
	private LoadArguments args;

	public MainEntity(Class type) throws UnreportedEntityException{
		if (type==null){
			throw new UnreportedEntityException();
		}
		this.type = type;
		alias = QueryElement.generateSymbol(this.args, type.getSimpleName());
	}
	
	public MainEntity(LoadArguments args, Class type) throws UnreportedEntityException{
		this(type);
		setLoadArguments(args);
		alias = QueryElement.generateSymbol(this.args, type.getSimpleName());
	}

	public void setLoadArguments(LoadArguments args) {
		this.args = args;
	}
	
	public MainEntity(LoadArguments args, Class type, String uniqueProjectionField) throws UnreportedEntityException{
		this(args, type);
		this.uniqueProjectionField=uniqueProjectionField;
	}
	
	public MainEntity(Class type, String uniqueProjectionField) throws UnreportedEntityException{
		this(type);
		this.uniqueProjectionField=uniqueProjectionField;
	}	
	
	public Boolean isUniqueProjectionField(){
		return (uniqueProjectionField!=null);
	}
	
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}

	public String getProjection(){
		if (isUniqueProjectionField()){
			return getAlias() + StandardDAOImpl.DOT + uniqueProjectionField;
		}
		return getAlias();
	}
	
	public String getTypeName(){
		return getEntityName(type);
	}
	
	/**
	 * Normaliza el nombre de la entidad para el caso de que este bajo un nombre proxie de hibernate
	 * @param entity
	 * @return
	 */
	public static String getEntityName(Class entity) {
		String name = entity.getName();
		Integer index = name.indexOf(HIBERNATE_PROXY_PREFIX);
		if (index>=0){
			name = name.substring(0, index);
		}
		return name;
	}

	public String getAlias() {
		return alias;
	}
}
