package co.com.directv.sdii.assign.assignment.skill.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentSkill;

/**
 * Construye y/o administra las implementaciones de las habilidades
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AssignmentSkillFactoryImpl implements AssignmentSkillFactory {

	private static Log log = LogFactory.getLog(AssignmentSkillFactoryImpl.class);
	
	private AssignmentSkillConfigLoader skillConfigLoader;

	public AssignmentSkillFactoryImpl(){
		skillConfigLoader = AssignmentSkillConfigLoader.getInstance();
	}

	public void finalize() throws Throwable {

	}
	/**
	 * Construye a partir del c�digo de una habilidad, la clase concreta que la
	 * implementa
	 * 
	 * @param skillCode    c�digo de la habilidad que ser� construida
	 */
	public AssignmentSkill buildAssignmentSkill(String skillCode){
		if(log.isDebugEnabled()){
			log.debug("Se inicia la construcción de la habilidad con código: " + skillCode);
		}
		try {
			String skillClassName = skillConfigLoader.getAssignmentSkillClassName(skillCode);
			AssignmentSkill skill = (AssignmentSkill)Class.forName(skillClassName).newInstance();
			return skill;
		} catch (InstantiationException e) {
			String message ="Error al tratar de construir la habilidad con el código: " + skillCode + " error: " + e.getMessage(); 
			log.error(message,e);
			e.printStackTrace();
			throw new IllegalStateException(message);
		} catch (IllegalAccessException e) {
			String message ="Error al tratar de construir la habilidad con el código: " + skillCode + " error: " + e.getMessage(); 
			log.error(message,e);
			e.printStackTrace();
			throw new IllegalStateException(message);
		} catch (ClassNotFoundException e) {
			String message ="Error al tratar de construir la habilidad con el código: " + skillCode + " error: " + e.getMessage(); 
			log.error(message,e);
			e.printStackTrace();
			throw new IllegalStateException(message);
		}finally{
			if(log.isDebugEnabled()){
				log.debug("Finaliza la construcción de la habilidad con código: " + skillCode);
			}
		}
	}
}