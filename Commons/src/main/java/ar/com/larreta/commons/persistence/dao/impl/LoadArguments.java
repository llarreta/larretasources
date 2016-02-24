package ar.com.larreta.commons.persistence.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Representa los diferentes argumentos o variables que pueden ser modificables para traer entidades desde la base de datos 
 */
public class LoadArguments implements Serializable {

	private MainEntity mainEntity;
	private Collection<ProjectedProperty> projectedProperties = new ArrayList<ProjectedProperty>();
	private Collection<Where> wheres = new ArrayList<Where>();
	private Collection<JoinedEntity> joins = new ArrayList<JoinedEntity>();
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
	
	public Collection<JoinedEntity> getJoins() {
		return joins;
	}

	public void setJoins(Collection<JoinedEntity> joins) {
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
	 * Agrega una propiedad projectada
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades
	 * @param name
	 * @return
	 */
	public LoadArguments addProjectedProperties(String name){
		ProjectedProperty projectedProperty = new ProjectedProperty(this, name);
		projectedProperties.add(projectedProperty);
		return this;
	}
	
	/**
	 * Agrega una propiedad projectada
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades en left
	 * @param name, left
	 * @return
	 */
	public LoadArguments addProjectedPropertiesLeftJoin(String name){
		ProjectedProperty projectedProperty = new ProjectedProperty(this, name, true);
		projectedProperties.add(projectedProperty);
		return this;
	}
	
	/**
	 * Agrega una propiedad collection
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades
	 * @param name
	 * @return
	 */
	public LoadArguments addProjectedCollection(String name){
		ProjectedProperty projectedProperty = new ProjectedCollection(this, name, HashSet.class);
		projectedProperties.add(projectedProperty);
		return this;
	}
	
	/**
	 *  Agrega una propiedad collection
	 * Retorna la referencia al objeto actual para que pueda seguir appendeando propiedades
	 * @param name
	 * @return
	 */
	public LoadArguments addProjectedCollectionLeftJoin(String name){
		ProjectedProperty projectedProperty = new ProjectedCollection(this, name, HashSet.class, Boolean.TRUE);
		projectedProperties.add(projectedProperty);
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
	 */
	public LoadArguments addWhereNotInSubquery(String field, String subField, Class subClass){
		LoadArguments args = new LoadArguments(new MainEntity(this, subClass, subField));
		args.setSymbols(symbols);
		addWhere(new NotInSubquery(this, field, args));
		return args;
	}
	
	/**
	 * Agrega un where de tipo not exists
	 * @param subClass
	 * @param wheres
	 * @return
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
}
