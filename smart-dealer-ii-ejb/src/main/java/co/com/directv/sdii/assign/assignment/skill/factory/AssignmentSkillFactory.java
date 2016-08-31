package co.com.directv.sdii.assign.assignment.skill.factory;

import co.com.directv.sdii.assign.assignment.AssignmentSkill;

/**
 * Define las operaciones de creación de habilidades
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public interface AssignmentSkillFactory {

	/**
	 * Construye a partir del código de una habilidad, la clase concreta que la
	 * implementa
	 * 
	 * @param skillCode    código de la habilidad que será construida
	 */
	public AssignmentSkill buildAssignmentSkill(String skillCode);

}