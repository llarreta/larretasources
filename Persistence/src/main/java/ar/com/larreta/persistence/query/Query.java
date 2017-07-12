package ar.com.larreta.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class Query implements Serializable {

	@Autowired
	protected ApplicationContext 		applicationContext;
	
	@Autowired
	private ReferencesManager 			referencesManager;
	
	
	//FIXME: las collections deberian ser Set para que sus elementos no puedan repetirseS
	private Instruction 				instruction;
	private Collection<Reference> 		projections = new ArrayList<>();
	private Reference					mainEntity;
	private Collection<Join> 			joins = new ArrayList<>();
	private Collection<Where> 			wheres;
	private Collection<Aggregate>		aggregates;

	@PostConstruct
	public void initialize(){
		referencesManager.setQuery(this);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(instruction.getDescription());
		builder.append(Const.SPACE);
		
		
		
		return super.toString();
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
			reference.toString();
		}
	}


	
}
