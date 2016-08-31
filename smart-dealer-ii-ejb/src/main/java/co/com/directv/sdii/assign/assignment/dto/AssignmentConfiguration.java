package co.com.directv.sdii.assign.assignment.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Encapsula la información de las hablidades que se deben invocar y la modalidad
 * de evaluación de cada uno de los resultados de dichas evaluaciones
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AssignmentConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7038609959181296568L;

	private AssignmentCriteria assignmentCriteria;
	
	/*
	 * Lista con las configuraciones de las habilidades y su orden
	 */
	private List<SkillExecConfiguration> skillConfigurations;

	public AssignmentConfiguration(){

	}
	
	public AssignmentConfiguration(AssignmentCriteria assignmentCriteria,
			List<SkillExecConfiguration> skillConfigurations) {
		super();
		this.assignmentCriteria = assignmentCriteria;
		this.skillConfigurations = skillConfigurations;
	}

	public AssignmentCriteria getAssignmentCriteria() {
		return assignmentCriteria;
	}

	public void setAssignmentCriteria(AssignmentCriteria assignmentCriteria) {
		this.assignmentCriteria = assignmentCriteria;
	}

	public List<SkillExecConfiguration> getSkillConfigurations() {
		if(skillConfigurations != null && ! skillConfigurations.isEmpty()){
			Collections.sort(skillConfigurations);
		}
		return skillConfigurations;
	}

	public void setSkillConfigurations(
			List<SkillExecConfiguration> skillConfigurations) {
		this.skillConfigurations = skillConfigurations;
	}
	
	public void finalize() throws Throwable {

	}
}