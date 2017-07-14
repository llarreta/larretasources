package ar.com.larreta.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.tools.Const;
import ar.com.larreta.tools.StringUtils;

@Component @Scope(Const.PROTOTYPE)
public class Query implements Serializable {
	
	private static @Log Logger LOG;
	
	public static String FROM = "FROM";
	
	@Autowired
	protected ApplicationContext 				applicationContext;
	
	@Autowired
	private ReferencesManager 					referencesManager;
	
	@Autowired
	protected transient LocalSessionFactoryBean commonsSessionFactory;
	
	private Instruction 				instruction;
	private Set<Reference> 				projections;
	private Reference					mainEntity;
	private Set<Join> 					joins;
	private Set<Where> 					wheres;
	private Set<Aggregate>				aggregates;

	@PostConstruct
	public void initialize(){
		referencesManager.setQuery(this);
		projections = new HashSet<>();
		joins = new HashSet<>();
		wheres = new HashSet<>();
		aggregates = new HashSet<>();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(instruction.getDescription());
		builder.append(Const.SPACE);
		
		Boolean first = Boolean.TRUE;
		Iterator<Reference> it = projections.iterator();
		while (it.hasNext()) {
			if (!first){
				builder.append(StringUtils.COMMA);
			}
			first = Boolean.FALSE;
			Reference reference = (Reference) it.next();
			builder.append(reference);
		}
		
		builder.append(Const.SPACE);
		builder.append(FROM);
		builder.append(Const.SPACE);
		builder.append(mainEntity.getDescription());
		builder.append(Const.SPACE);
		builder.append(mainEntity.getAlias());
		builder.append(Const.SPACE);
		
		List<Join> listJoins = new ArrayList<>(joins);
		Collections.sort(listJoins, new Comparator<Join>() {
			@Override
			public int compare(Join joinA, Join joinB) {
				return joinA.getReference().getVirtualPath().compareTo(joinB.getReference().getVirtualPath());
			}
		});
		
		Iterator<Join> itJoins = listJoins.iterator();
		while (itJoins.hasNext()) {
			Join join = (Join) itJoins.next();
			builder.append(join.getJoinType());
			builder.append(Const.SPACE);
			builder.append(join.getReference().toString());
			builder.append(Const.SPACE);
			builder.append(join.getReference().getAlias());
			builder.append(Const.SPACE);
		}
		
		return builder.toString();
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

	public void addMainEntity(String mainEntity){
		Reference reference = applicationContext.getBean(Reference.class);
		reference.setDescription(mainEntity);
		reference.setQuery(this);
		this.mainEntity = reference;
	}
	
	public void addMainEntity(Reference	mainEntity){
		this.mainEntity = mainEntity;
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


	public org.hibernate.SessionFactory getSessionFactory() {
		try {
			return commonsSessionFactory.getObject();
		} catch (Exception e) {		
			//FIXME: Lanzar excepcion
			LOG.error("Ocurrio un error obteniendo SessionFactory", e);
		}
		return null;
	}
	
	public org.hibernate.Query getQuery(){
		return getSessionFactory().getCurrentSession().createQuery(toString());
	}
	
	public void execute(){
		Collection list = getQuery().list();
		list.iterator();
	}
	
}
