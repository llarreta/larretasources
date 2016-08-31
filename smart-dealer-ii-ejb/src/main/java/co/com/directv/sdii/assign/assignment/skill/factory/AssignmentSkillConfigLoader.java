package co.com.directv.sdii.assign.assignment.skill.factory;

import java.util.ResourceBundle;

/**
 * Encapsula el manejo de la informaci�n de configuraci�n de una habilidad a fin
 * de a partir del c�digo establecer el artefacto espec�fico (clase) que
 * implementa dicha habilidad
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public final class AssignmentSkillConfigLoader {

	private static AssignmentSkillConfigLoader mySelf;
	
	private static String SKILL_CONFIG_FILE_LOCATION  = "co.com.directv.sdii.assign.config.skills_config";
	
	
	private AssignmentSkillConfigLoader(){

	}
	
	public static AssignmentSkillConfigLoader getInstance(){
		if(mySelf == null){
			mySelf = new AssignmentSkillConfigLoader();
		}
		return mySelf;
	}

	public void finalize() throws Throwable {

	}
	/**
	 * Obtiene el nombre cualificado de la clase que implementa una habilidad dado el código de la
	 * misma
	 * @param skillCode    código de la habilidad
	 */
	public String getAssignmentSkillClassName(String skillCode){
		ResourceBundle bundle = ResourceBundle.getBundle(SKILL_CONFIG_FILE_LOCATION);
		String result = bundle.getString(skillCode);
		return result;
	}
}