package ar.com.larreta.persistence.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import ar.com.larreta.persistence.dao.LoadDao;
import ar.com.larreta.persistence.dao.args.CountArguments;
import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.exceptions.CantBuildQueryException;

/**
 * DAO con la funcionalidad de poder cargar entidades desde la base  
 *
 */
@Deprecated
public abstract class LoadDAOImpl implements LoadDao {

	private static final Logger LOGGER = Logger.getLogger(LoadDAOImpl.class);
	
	public static final String VOID = " ";
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String WHERE = "WHERE";
	public static final String INNER_JOIN = "INNER JOIN";
	public static final String LEFT_JOIN = "LEFT JOIN";
	public static final String FROM = "FROM";
	public static final String SELECT = "SELECT";
	public static final String TWO_POINTS = ":";
	public static final String EQUAL = "=";
	public static final String ID = "id";
	public static final String AS = "AS";
	public static final String AND = "AND";
	public static final String ORDER_BY = "ORDER BY";
	
	@Autowired
	protected transient LocalSessionFactoryBean commonsSessionFactory;
	
	public LocalSessionFactoryBean getCommonsSessionFactory() {
		return commonsSessionFactory;
	}

	public void setCommonsSessionFactory(LocalSessionFactoryBean commonsSessionFactory) {
		this.commonsSessionFactory = commonsSessionFactory;
	}

	public org.hibernate.SessionFactory getSessionFactory() {
		try {
			return commonsSessionFactory.getObject();
		} catch (Exception e) {		
			LOGGER.error("Ocurrio un error obteniendo SessionFactory", e);
		}
		return null;
	}

	/**
	 * Busca informaacion en la base de datos
	 * @param entityType
	 * @param properties
	 * @param wheres
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws CantBuildQueryException 
	 * @throws UnreportedEntityException 
	 */
	public Collection load(Class type) throws CantBuildQueryException{
		
		LoadArguments args = new LoadArguments(type);
		
		QueryMaked queryMaked = makeQuery(args);
		List list = execute(queryMaked);
		ResultProcessor processor = new ResultProcessor(list, args);
		return processor.getEntities();
	}

	public List execute(QueryMaked queryMaked) {
		Query query = queryMaked.getQuery();
		
		//Seteamos los valores retornados
		List list = query.list();
		return list;
	}
	
	/**
	 * Busca informaacion en la base de datos
	 * @param entityType
	 * @param properties
	 * @param wheres
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws CantBuildQueryException 
	 */
	public Collection load(LoadArguments args) throws CantBuildQueryException{
		args.splitProperties();

		QueryMaked queryMaked = makeQuery(args);
		List list = execute(queryMaked);
		ResultProcessor processor = new ResultProcessor(list, args);
		return processor.getEntities();
	}
	
	/**
	 * Cuenta la cantidad de entidades, segun los parametros indicados
	 * @param count
	 * @param properties
	 * @param wheres
	 * @return
	 */
	public Long count(CountArguments args)  throws CantBuildQueryException {
		QueryMaked queryMaked = makeQuery(args);
		Query query = queryMaked.getQuery();
		return (Long) query.uniqueResult();
	}
	

	/**
	 * Construye la query HQL a ejecutarse
	 * @param mainEntity
	 * @param wheres
	 * @param orders
	 * @param firstResult
	 * @param maxResults
	 * @param splitter
	 * @return
	 * @throws CantBuildQueryException 
	 */
	public QueryMaked makeQuery(LoadArguments args) throws CantBuildQueryException {
		StringBuilder hql = makeHQL(args);
		
		Query query = getQuery(args, hql);
		
		Collection values = addWhereValues(args, query);
		
		return new QueryMaked(hql.toString(), values, query);
	}
	
	/**
	 * Construye el HQL que se ejecutara
	 * @param entityType
	 * @param splitter
	 * @param wheres
	 * @param orders
	 * @return
	 */
	public StringBuilder makeHQL(LoadArguments args) {
		StringBuilder hql = new StringBuilder();
		
		MainEntity mainEntity = args.getMainEntity();
		
		hql.append(SELECT).append(VOID).append(mainEntity.getProjection());
		addProjectionAttributesToHQL(args, hql);
		
		hql.append(VOID).append(FROM).append(VOID);
		hql.append(mainEntity.getTypeName());
		hql.append(VOID).append(mainEntity.getAlias()).append(VOID);
		
		addJoinedEntities(args, hql);
		addWhereRestrictions(args, hql);
		addOrders(args, hql);
		return hql;
	}
	
	/**
	 * Agrega los atributos a obtener en la query HQL
	 * @param splitter
	 * @param hql
	 */
	protected void addProjectionAttributesToHQL(LoadArguments args, StringBuilder hql) {
		if (!args.getMainEntity().isUniqueProjectionField()){
			Iterator<ProjectedProperty> it = args.getAllOrdereProperties().iterator();
			while (it.hasNext()) {
				ProjectedProperty projectedProperty = (ProjectedProperty) it.next();
				
				String symbolicName = projectedProperty.getSymbolicName();
				hql.append(COMMA).append(VOID);
				if (projectedProperty.getMainReference()){
					hql.append(args.getMainEntity().getAlias()).append(DOT);
				}
				hql.append(symbolicName);
			}
		}
	}
	
	/**
	 * Agrega las entidades con las cuales se realizara un join
	 * @param splitter
	 * @param hql
	 */
	protected void addJoinedEntities(LoadArguments args, StringBuilder hql) {
		List<JoinedEntity> ordererElements = new ArrayList<JoinedEntity>(args.getJoins());
		Collections.sort(ordererElements, new Comparator<JoinedEntity>() {
			public int compare(JoinedEntity elementA, JoinedEntity elementB) {
				return elementA.getName().compareTo(elementB.getName());
			}
		});
		Iterator<JoinedEntity> itJoined = ordererElements.iterator();
		while (itJoined.hasNext()) {
			JoinedEntity join = (JoinedEntity) itJoined.next();
			hql.append(join.getHQL());
		}
	}

	/**
	 * Agrega las clausulas wheres 
	 * @param wheres
	 * @param splitter
	 * @param hql
	 */
	protected void addWhereRestrictions(LoadArguments args, StringBuilder hql) {
		if ((args.getWheres()!=null) && (!args.getWheres().isEmpty())){
			hql.append(VOID).append(WHERE).append(VOID);
			Iterator<Where> it = args.getWheres().iterator();
			if (it.hasNext()){
				it.next().addWhere(args, hql);
			} 
			
			while (it.hasNext()) {
				hql.append(VOID).append(AND);
				it.next().addWhere(args, hql);
			}
		}
	}
	
	/**
	 * Agrega una condicion al where
	 * @param splitter
	 * @param hql
	 * @param where
	 */
	/*protected void addWhere(LoadArguments args, StringBuilder hql, Where where) {
		hql.append(VOID);
		if (where.getMainReference()){
			hql.append(args.getMainEntity().getAlias());
		}
		
		if (!StringUtils.isEmpty(where.getSymbolicName())){
			hql.append(DOT).append(where.getSymbolicName());
		}
		
		hql.append(VOID).append(where.getOperator()).append(VOID);
		if (where.getValue()!=null){
			hql.append(where.getPostOperatorSection());
		}
	}
	
	protected void addWhere(LoadArguments args, StringBuilder hql, Or or) {
		hql.append(VOID);
		hql.append("(");
		addWhere(args, hql, or.getFirst());
		hql.append(VOID);
		hql.append("OR");
		hql.append(VOID);
		addWhere(args, hql, or.getSecond());
		hql.append(")");
		hql.append(VOID);
	}*/
	
	/**
	 * Agrega el ordenamiento
	 * @param orders
	 * @param splitter
	 * @param hql
	 */
	protected void addOrders(LoadArguments args, StringBuilder hql) {
		if ((args.getOrders()!=null) && (!args.getOrders().isEmpty())){
			hql.append(VOID).append(ORDER_BY).append(VOID);
			Iterator<Order> it = args.getOrders().iterator();
			if (it.hasNext()){
				addOrder(args, hql, it.next());
			} 
			
			while (it.hasNext()) {
				hql.append(VOID).append(COMMA);
				addOrder(args, hql, it.next());
			}
		}
	}

	/**
	 * Agregamos un ordenamiento
	 * @param splitter
	 * @param hql
	 * @param order
	 */
	protected void addOrder(LoadArguments args, StringBuilder hql, Order order) {
		String symbolicName = order.getSymbolicName();
		hql.append(VOID);
		if (order.getMainReference()){
			hql.append(args.getMainEntity().getAlias()).append(DOT);
		}
		hql.append(symbolicName).append(VOID).append(order.getDirection()).append(VOID);
	}

	/**
	 * Retorna el objeto query de hibernate
	 * @param firstResult
	 * @param maxResults
	 * @param hql
	 * @return
	 * @throws CantBuildQueryException 
	 */
	public Query getQuery(LoadArguments args,	StringBuilder hql) throws CantBuildQueryException {
		try {
			Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
			if (args.getFirstResult()!=null){
				query.setFirstResult(args.getFirstResult());
			}
			if (args.getMaxResults()!=null){
				query.setMaxResults(args.getMaxResults());
			}
			return query;
		} catch (Exception e){
			LOGGER.error("No fue posible construir la query para:\n" + hql, e);
			throw new CantBuildQueryException();
		}
	}
	
	/**
	 * Setea los valores para las restricciones del where
	 * @param wheres
	 * @param query
	 */
	protected Collection addWhereValues(LoadArguments args, Query query) {
		Collection values = new ArrayList();
		if ((args.getWheres()!=null) && (!args.getWheres().isEmpty())){
			Iterator<Where> it = args.getWheres().iterator();
			while (it.hasNext()) {
				Where where = (Where) it.next();
				values.add(where.getValue());
				where.setQueryValue(query);
			}
		}
		return values;
	}

}
