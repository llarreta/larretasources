package ar.com.larreta.persistence.dao.args;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ar.com.larreta.persistence.dao.impl.Asc;
import ar.com.larreta.persistence.dao.impl.Desc;
import ar.com.larreta.persistence.dao.impl.Equal;
import ar.com.larreta.persistence.dao.impl.InSubquery;
import ar.com.larreta.persistence.dao.impl.InnerJoin;
import ar.com.larreta.persistence.dao.impl.JoinedEntity;
import ar.com.larreta.persistence.dao.impl.LeftJoin;
import ar.com.larreta.persistence.dao.impl.MainEntity;
import ar.com.larreta.persistence.dao.impl.NotExists;
import ar.com.larreta.persistence.dao.impl.NotInSubquery;
import ar.com.larreta.persistence.dao.impl.Order;
import ar.com.larreta.persistence.dao.impl.ProjectedCollection;
import ar.com.larreta.persistence.dao.impl.ProjectedPropertiesSplitter;
import ar.com.larreta.persistence.dao.impl.ProjectedProperty;
import ar.com.larreta.persistence.dao.impl.Where;

/**
 * Representa los diferentes argumentos o variables que pueden ser modificables para traer entidades desde la base de datos 
 */
public class LoadArguments implements Serializable {

	private static final String DOT = ".";
	private MainEntity mainEntity;
	private Collection<ProjectedProperty> projectedProperties = new ArrayList<ProjectedProperty>();
	private Collection<Where> wheres = new ArrayList<Where>();
	private Set<JoinedEntity> joins = new HashSet<JoinedEntity>();
	private Collection<Order> orders = new ArrayList<Order>();
	private Integer firstResult;
	private Integer maxResults;
	
	private Map<String, String> symbols = new HashMap<String, String>();
	private ProjectedPropertiesSplitter splitter;

	public LoadArguments(Class type){
		mainEntity = new MainEntity(this, type);
	}

	public LoadArguments(Class type, String uniqueProjectionField){
		mainEntity = new MainEntity(this, type, uniqueProjectionField);
	}

	public LoadArguments(MainEntity mainEntity){
		this.mainEntity = mainEntity;
		mainEntity.setLoadArguments(this);
	}
	
	public LoadArguments toLoadArguments(){
		LoadArguments args = new LoadArguments(mainEntity.getType());
		setCommonsProperties(args);
		return args;
	}

	public CountArguments toCountArguments(){
		CountArguments args = new CountArguments(mainEntity.getType());
		setCommonsProperties(args);
		return args;
	}
	
	public MaxArguments toMaxArguments(String field){
		MaxArguments args = new MaxArguments(mainEntity.getType(), field);
		setCommonsProperties(args);
		return args;
	}

	private void setCommonsProperties(LoadArguments args) {
		args.setProjectedProperties(getProjectedProperties());
		args.setWheres(getWheres());
		args.setJoins(getJoins());
		args.setOrders(getOrders());
		args.setFirstResult(getFirstResult());
		args.setMaxResults(getMaxResults());
		args.setSymbols(getSymbols());
		args.setSplitter(getSplitter());
	}
	
	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
	
	public Set<JoinedEntity> getJoins() {
		return joins;
	}

	public void setJoins(Set<JoinedEntity> joins) {
		this.joins = joins;
	}
	
	public MainEntity getMainEntity() {
		return mainEntity;
	}

	public Collection<ProjectedProperty> getProjectedProperties() {
		return projectedProperties;
	}

	public Collection<Where> getWheres() {
		return wheres;
	}
	private void setWheres(Collection<Where> wheres) {
		this.wheres = wheres;
	}
	
	/**
	 * Retorna verdadero si la propiedad q intenta utilizarse ya esta incluida 
	 * @param name
	 * @return
	 */
	private Boolean isProjectedPropertiesExist(String name){
		Iterator<ProjectedProperty> it = projectedProperties.iterator();
		while (it.hasNext()) {
			ProjectedProperty projectedProperty = (ProjectedProperty) it.next();
			if ((projectedProperty.getName().indexOf(name + DOT)>=0) || (projectedProperty.getName().equals(name))){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * Agrega una propiedad projectada
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades
	 * @param name
	 * @return
	 */
	public LoadArguments addProjectedProperties(String name){
		if (!isProjectedPropertiesExist(name)){
			ProjectedProperty projectedProperty = new ProjectedProperty(this, name);
			projectedProperties.add(projectedProperty);
		}
		return this;
	}
	
	/**
	 * Agrega una propiedad projectada
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades en left
	 * @param name, left
	 * @return
	 */
	public LoadArguments addProjectedPropertiesLeftJoin(String name){
		if (!isProjectedPropertiesExist(name)){
			ProjectedProperty projectedProperty = new ProjectedProperty(this, name, true);
			projectedProperties.add(projectedProperty);
		}
		return this;
	}
	
	/**
	 * Agrega una propiedad collection
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades
	 * @param name
	 * @return
	 */
	public LoadArguments addProjectedCollection(String name){
		if (!isProjectedPropertiesExist(name)){
			ProjectedProperty projectedProperty = new ProjectedCollection(this, name, HashSet.class);
			projectedProperties.add(projectedProperty);
		}
		return this;
	}
	
	/**
	 *  Agrega una propiedad collection
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades
	 * @param name
	 * @return
	 */
	public LoadArguments addProjectedCollectionLeftJoin(String name){
		if (!isProjectedPropertiesExist(name)){
			ProjectedProperty projectedProperty = new ProjectedCollection(this, name, HashSet.class, Boolean.TRUE);
			projectedProperties.add(projectedProperty);
		}
		return this;
	}
	
	/**
	 * Agrega un where de tipo equal	 
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando wheres
	 * @param field
	 * @param value
	 * @return
	 */
	public LoadArguments addWhereEqual(String field, Object value){
		addWhere(new Equal(this, field, value));
		return this;
	}
	
	/**
	 * Agrega un where de tipo Not In	 
	 * Retorna la referencia a los argumentos de la subquery
	 * @param field
	 * @param arguments
	 * @return
	 * @throws UnreportedEntityException 
	 */
	public LoadArguments addWhereNotInSubquery(String field, String subField, Class subClass){
		LoadArguments args = new LoadArguments(new MainEntity(this, subClass, subField));
		args.setSymbols(symbols);
		addWhere(new NotInSubquery(this, field, args));
		return args;
	}
	
	public LoadArguments addWhereInSubquery(String field, String subField, Class subClass){
		LoadArguments args = new LoadArguments(new MainEntity(this, subClass, subField));
		args.setSymbols(symbols);
		addWhere(new InSubquery(this, field, args));
		return args;
	}
	
	/**
	 * Agrega un where de tipo not exists
	 * @param subClass
	 * @param wheres
	 * @return
	 * @throws UnreportedEntityException 
	 */
	public LoadArguments addWhereNotExists(Class subClass, Collection<Where> wheres){
		LoadArguments args = new LoadArguments(new MainEntity(this, subClass, "id"));
		args.setWheres(wheres);
		args.setSymbols(symbols);
		addWhere(new NotExists(this, args));
		return args;
	}
	
	/**
	 * Agrega una condicion where al loadArguments actual
	 * @param where
	 */
	public void addWhere(Where where){
		wheres.add(where);
	}
	
	/**
	 * Agrega una propiedad a ser joineada
	 * @param property
	 * @return
	 */
	public LoadArguments addInnerJoin(String property){
		joins.add(new InnerJoin(this, property));
		return this;
	}

	/**
	 * Agrega una propiedad a ser joineada
	 * @param property
	 * @return
	 */
	public LoadArguments addLeftJoin(String property){
		joins.add(new LeftJoin(this, property));
		return this;
	}

	/**
	 * Agrega orden ascendente
	 * @param property
	 * @return
	 */
	public LoadArguments addAscOrder(String property){
		orders.add(new Asc(this, property));
		return this;
	}

	/**
	 * Agrega orden descendente
	 * @param property
	 * @return
	 */
	public LoadArguments addDescOrder(String property){
		orders.add(new Desc(this, property));
		return this;
	}

	/**
	 * Explota las propiedades para obtener cada uno de los objetos que se necesita
	 */
	public void splitProperties(){
		splitter = new ProjectedPropertiesSplitter(this);
	}
	
	/**
	 * Retorna todas las propiedades necesarias por procesar de manera ordenada
	 * @return
	 */
	public Collection<ProjectedProperty> getAllOrdereProperties(){
		if (splitter==null){
			return new ArrayList<ProjectedProperty>();
		}
		return splitter.getOrdererProperties();
	}

	public void setSymbols(Map<String, String> symbols) {
		this.symbols = symbols;
	}

	/**
	 * Agrega un simbolo a la tabla de simbolos
	 * @param property
	 * @param symbol
	 */
	public void addSymbol(String property, String symbol){
		symbols.put(property, symbol);
	}
	
	public Boolean containSymbol(String symbol){
		return symbols.containsValue(symbol);
	}
	
	public Boolean containPropertySymbol(String property){
		return symbols.containsKey(property);
	}
	
	/**
	 * Retorna el simbolo que representa a la propiedad en la query en construccion
	 * @param property
	 * @return
	 */
	public String getSymbol(String property){
		return symbols.get(property);
	}

	public void setMainEntity(MainEntity mainEntity) {
		this.mainEntity = mainEntity;
	}

	public void setProjectedProperties(Collection<ProjectedProperty> projectedProperties) {
		this.projectedProperties = projectedProperties;
	}


	public ProjectedPropertiesSplitter getSplitter() {
		return splitter;
	}

	public void setSplitter(ProjectedPropertiesSplitter splitter) {
		this.splitter = splitter;
	}

	public Map<String, String> getSymbols() {
		return symbols;
	}
}
