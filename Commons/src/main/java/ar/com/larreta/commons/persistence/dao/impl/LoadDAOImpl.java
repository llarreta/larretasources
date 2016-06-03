package ar.com.larreta.commons.persistence.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.persistence.CommonsSessionFactory;
import ar.com.larreta.commons.persistence.dao.LoadDao;
import ar.com.larreta.commons.persistence.dao.args.CountArguments;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

/**
 * DAO con la funcionalidad de poder cargar entidades desde la base  
 *
 */
public abstract class LoadDAOImpl extends AppObjectImpl implements LoadDao {

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
	protected transient CommonsSessionFactory commonsSessionFactory;
	
	public CommonsSessionFactory getCommonsSessionFactory() {
		return commonsSessionFactory;
	}

	public void setCommonsSessionFactory(CommonsSessionFactory commonsSessionFactory) {
		this.commonsSessionFactory = commonsSessionFactory;
	}

	public org.hibernate.SessionFactory getSessionFactory() {
		try {
			return commonsSessionFactory.getObject();
		} catch (Exception e) {		
			getLog().error(AppException.getStackTrace(e));
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
	 */
	public Collection load(Class type){
		
		LoadArguments args = new LoadArguments(type);
		
		Query query = makeQuery(args);
		
		//Seteamos los valores retornados
		List list = query.list();
		ResultProcessor processor = new ResultProcessor(list, args);
		return processor.getEntities();
	}
	
	/**
	 * Busca informaacion en la base de datos
	 * @param entityType
	 * @param properties
	 * @param wheres
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public Collection load(LoadArguments args){
		args.splitProperties();
		Query query = makeQuery(args);
		
		//Seteamos los valores retornados
		List list = query.list();
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
	public Long count(CountArguments args){
		Query query = makeQuery(args);
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
	 */
	public Query makeQuery(LoadArguments args) {
		StringBuilder hql = makeHQL(args);
		
		Query query = getQuery(args, hql);
		
		addWhereValues(args, query);
		return query;
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
		Iterator<JoinedEntity> itJoined = args.getJoins().iterator();
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
				addWhere(args, hql, (Where) it.next());
			} 
			
			while (it.hasNext()) {
				hql.append(VOID).append(AND);
				addWhere(args, hql, (Where) it.next());
			}
		}
	}
	
	/**
	 * Agrega una condicion al where
	 * @param splitter
	 * @param hql
	 * @param where
	 */
	protected void addWhere(LoadArguments args, StringBuilder hql, Where where) {
		hql.append(VOID);
		if (where.getMainReference()){
			hql.append(args.getMainEntity().getAlias()).append(DOT);
		}
		hql.append(where.getSymbolicName()).append(VOID).append(where.getOperator()).append(VOID).append(where.getPostOperatorSection());
	}
	
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
	 */
	public Query getQuery(LoadArguments args,	StringBuilder hql) {
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		if (args.getFirstResult()!=null){
			query.setFirstResult(args.getFirstResult());
		}
		if (args.getMaxResults()!=null){
			query.setMaxResults(args.getMaxResults());
		}
		return query;
	}
	
	/**
	 * Setea los valores para las restricciones del where
	 * @param wheres
	 * @param query
	 */
	protected void addWhereValues(LoadArguments args, Query query) {
		if ((args.getWheres()!=null) && (!args.getWheres().isEmpty())){
			Iterator<Where> it = args.getWheres().iterator();
			while (it.hasNext()) {
				Where where = (Where) it.next();
				where.setQueryValue(query);
			}
		}
	}

}
