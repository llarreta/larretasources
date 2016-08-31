/**
 * Creado 01/06/2011 18:27:10
 */
package co.com.directv.sdii.assign.assignment.skill.exception;

import java.util.List;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Excepción propia del módulo de habilidades
 * 
 * Fecha de Creación: 01/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AssignmentSkillException extends BusinessException {

	private String skillName;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6357976073054036608L;
	
	
	public AssignmentSkillException(String codeMessage, String message, String skillName) {
		super(codeMessage, message);
		this.skillName = skillName;
	}
	
	
	
	
	public AssignmentSkillException(String codeMessage, String message,
			Throwable cause, String skillName, List<String> parameters) {
		super(codeMessage, message, cause);
		this.skillName = skillName;
		this.setParameters(parameters);
	}
	
	public AssignmentSkillException(String codeMessage, String message,
			String skillName, List<String> parameters) {
		super(codeMessage, message);
		this.skillName = skillName;
		this.setParameters(parameters);
	}
	
	public AssignmentSkillException(String codeMessage, String message,
			Throwable cause, String skillName) {
		super(codeMessage, message, cause);
		this.skillName = skillName;
	}




	public AssignmentSkillException() {
		super();
	}




	public String getSkillName() {
		return skillName;
	}
}
