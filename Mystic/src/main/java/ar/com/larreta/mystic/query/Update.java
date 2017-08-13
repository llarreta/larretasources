package ar.com.larreta.mystic.query;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.query.wheres.Equal;
import ar.com.larreta.mystic.query.wheres.Where;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class Update extends Query {
	
	private Set<Where> 	sets;
	
	@PostConstruct
	public void initialize(){
		super.initialize();
		sets = new HashSet<>();
	}
	
	public void addSet(String description,  Serializable value){
		Equal equal = (Equal) applicationContext.getBean(Equal.NAME);
		equal.setReference(getReferencesManager().buildReference(description));
		equal.setValue(value);
		sets.remove(equal);
		sets.add(equal);
	}

	
	@Override
	public void setValues(org.hibernate.Query query) {
		setValues(query, sets);
		super.setValues(query);
	}
	
	public Set<Where> getSets() {
		return sets;
	}

	@Autowired @Qualifier(UpdateBuilder.NAME)
	@Override
	public void setQueryBuilder(QueryBuilder queryBuilder) {
		super.setQueryBuilder(queryBuilder);
	}
	
	@Autowired
	public void setInstruction(UpdateInstruction instruction) {
		super.setInstruction(instruction);
	}
	
	public void addInnerJoin(String description){
		//FIXME:Not supported, throw exception
	}
	
	public void addInnerJoin(Reference reference){
		//FIXME:Not supported, throw exception
	}

	@Override
	public void addMainEntityProjection(String mainEntity) {
		//FIXME:Not supported, throw exception
	}

	@Override
	public void addProjections(String... projections) {
		//FIXME:Not supported, throw exception
	}
	
	@Override
	protected Collection execute(org.hibernate.Query query) {
		return Arrays.asList(query.executeUpdate());
	}
	
}
