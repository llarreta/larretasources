package co.com.directv.sdii.assign.assignment.dto;

import java.io.Serializable;

/**
 * Encapsula la información necesaria para la ejecución de una habilidad, se han
 * identificado:
 * 1. Código de la habilidad
 * 2. Modalidad de evalauación (NDSC para notificar dealer sin cobertura y SNDSC
 * para continuar con la evaluación de la siguiente habilidad)
 * 3. Orden de evaluación de la habilidad
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:48
 */
public class SkillExecConfiguration implements Comparable<SkillExecConfiguration>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4125943029828343537L;
	/**
	 * Código de la modalidad de evaluación (NDSC para notificar dealer sin cobertura
	 * y SNDSC sin notificar dealer sin cobertura)
	 */
	private String evaluationModeCode;
	/**
	 * orden de evaluación de la habilidad
	 */
	private int evaluationOrder;
	/**
	 * código de la habilidad a ser evaluada
	 */
	private String skillCode;

	public SkillExecConfiguration(){

	}
	
	

	public SkillExecConfiguration(String evaluationModeCode,
			int evaluationOrder, String skillCode) {
		super();
		this.evaluationModeCode = evaluationModeCode;
		this.evaluationOrder = evaluationOrder;
		this.skillCode = skillCode;
	}



	public String getEvaluationModeCode() {
		return evaluationModeCode;
	}



	public void setEvaluationModeCode(String evaluationModeCode) {
		this.evaluationModeCode = evaluationModeCode;
	}



	public int getEvaluationOrder() {
		return evaluationOrder;
	}



	public void setEvaluationOrder(int evaluationOrder) {
		this.evaluationOrder = evaluationOrder;
	}



	public String getSkillCode() {
		return skillCode;
	}



	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}



	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SkillExecConfiguration o) {
		if(this.evaluationOrder < o.evaluationOrder){
			return -1;
		}else if(this.evaluationOrder == o.evaluationOrder){
			return 0;
		}else{
			return 1;
		}
	}
}