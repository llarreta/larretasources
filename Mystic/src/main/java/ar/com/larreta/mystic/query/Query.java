package ar.com.larreta.mystic.query;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.wheres.Equal;
import ar.com.larreta.mystic.query.wheres.EqualYear;
import ar.com.larreta.mystic.query.wheres.In;
import ar.com.larreta.mystic.query.wheres.InSubquery;
import ar.com.larreta.mystic.query.wheres.NotIn;
import ar.com.larreta.mystic.query.wheres.NotInSubquery;
import ar.com.larreta.mystic.query.wheres.Where;
import ar.com.larreta.tools.BeanUtils;

public class Query implements Serializable {
	
	private static @Log Logger LOG;
	
	@Autowired
	protected ApplicationContext 				applicationContext;
	
	@Autowired
	private ReferencesManager 					referencesManager;
	
	private QueryBuilder 						queryBuilder;
	
	@Autowired
	protected SessionFactory 					sessionFactory;
	
	@Autowired
	protected BeanUtils 						beanUtils;
	
	private Instruction 				instruction;
	private Set<Reference> 				projections;
	private Reference					mainEntity;
	private Set<Join> 					joins;
	private Set<Where> 					wheres;
	private Set<Aggregate>				aggregates;
	private Integer 					firstResult;
	private Integer 					maxResults;

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
	
	public void cleanResultsLimits(){
		setFirstResult(null);
		setMaxResults(null);
	}

	@PostConstruct
	public void initialize(){
		referencesManager.setQuery(this);
		queryBuilder.setQuery(this);
		projections = new HashSet<>();
		joins = new HashSet<>();
		wheres = new HashSet<>();
		aggregates = new HashSet<>();
	}
	
	@Override
	public String toString() {
		addMainEntityToOrphans();
		return queryBuilder.build();
	}

	private void addMainEntityToOrphans() {
		//Revisar huerfanos
		Collection<Reference> orphans = referencesManager.getOrphans();
		Iterator<Reference> it = orphans.iterator();
		while (it.hasNext()) {
			Reference reference = (Reference) it.next();
			reference.setParentReference(mainEntity);
		}
	}

	public Set<Aggregate> getAggregates() {
		return aggregates;
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}
	
	@Autowired @Qualifier(QueryBuilder.NAME)
	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public Set<Where> getWheres() {
		return wheres;
	}
	
	public Set<Join> getJoins() {
		return joins;
	}

	public Set<Reference> getProjections() {
		return projections;
	}
	
	public Reference getMainEntity() {
		return mainEntity;
	}
	
	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
	
	public ReferencesManager getReferencesManager() {
		return referencesManager;
	}

	public void addMainEntityProjection(String mainEntity){
		addMainEntity(mainEntity);
		this.projections.add(this.mainEntity);
	}
	
	public void addMainEntity(String mainEntity){
		Reference reference = applicationContext.getBean(Reference.class);
		reference.setDescription(mainEntity);
		reference.setQuery(this);
		addMainEntity(reference);
	}
	
	public void addMainEntity(Reference	mainEntity){
		this.mainEntity = mainEntity;
		mainEntity.setMainEntity(Boolean.TRUE);
	}

	public void addProjections(String ... projections){
		Collection<String> projectionsText = Arrays.asList(projections);
		Iterator<String> it = projectionsText.iterator();
		while (it.hasNext()) {
			String projectionDescription = (String) it.next();
			Reference reference = referencesManager.buildReference(projectionDescription);
			this.projections.add(reference);
		}
	}

	public void addInnerJoin(String description){
		addInnerJoin(referencesManager.buildReference(description));
	}
	
	public void addInnerJoin(Reference reference){
		Join join = applicationContext.getBean(InnerJoin.class);
		join.setReference(reference);
		joins.add(join);
	}

	public org.hibernate.Query getQuery() throws PersistenceException{
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(toString());
			if (firstResult!=null){
				query.setFirstResult(firstResult);
			}
			if (maxResults!=null){
				query.setMaxResults(maxResults);
			}
			return query;
		} catch (Exception e){
			LOG.error("Ocurrio un error creando la query:" + toString(), e);
			throw new PersistenceException();
		}
	}
	
	public Collection execute() throws PersistenceException{
		org.hibernate.Query query = getQuery();
		setValues(query);
		return execute(query);
	}

	protected Collection execute(org.hibernate.Query query) {
		return query.list();
	}

	public void setValues(org.hibernate.Query query) {
		setValues(query, this.wheres);
	}

	protected void setValues(org.hibernate.Query query, Set<Where> wheres) {
		if (wheres.size()>0){
			Iterator<Where> it = wheres.iterator();
			while (it.hasNext()) {
				Where where = (Where) it.next();
				where.setValues(query);
			}
		}
	}
	
	public void addWhereEqual(String description,  Serializable value){
		addWhereEqual(Equal.NAME, description, value);
	}

	public void addWhereEqualYear(String description,  Serializable value){
		addWhereEqual(EqualYear.NAME, description, value);
	}

	
	protected void addWhereEqual(String equalType, String description, Serializable value) {
		Equal equal = (Equal) applicationContext.getBean(equalType);
		equal.setReference(referencesManager.buildReference(description));
		equal.setValue(value);
		wheres.remove(equal);
		wheres.add(equal);
	}

	public void addWhereIn(String description, Collection values) {
		addWhereIn(In.NAME, description, values);
	}
	
	public void addWhereNotIn(String description, Collection values) {
		addWhereIn(NotIn.NAME, description, values);
	}

	protected void addWhereIn(String type, String description, Collection values) {
		if (values!=null && values.size()>0){
			In in = (In) applicationContext.getBean(type);
			in.setReference(referencesManager.buildReference(description));
			in.setValues(values);
			wheres.remove(in);
			wheres.add(in);
		}
	}

	public void addWhereInSubquery(String description, Select select) {
		addWhereInSubquery(InSubquery.NAME, description, select);
	}
	
	public void addWhereNotInSubquery(String description, Select select) {
		addWhereInSubquery(NotInSubquery.NAME, description, select);
	}
	
	protected void addWhereInSubquery(String type, String description, Select select) {
		InSubquery in = (InSubquery) applicationContext.getBean(type);
		in.setReference(referencesManager.buildReference(description));
		in.setSelect(select);
		wheres.remove(in);
		wheres.add(in);
	}
}
