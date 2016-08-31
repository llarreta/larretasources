package co.com.directv.sdii.assign.assignment.dto;

import java.io.Serializable;

/**
 * Encapsula los parámetros por los cuales podrán ser configuradas las habilidades.
 * los identificados inicialmente son:
 * 1. País
 * 2. Categoría de servicio
 * 3. Modalidad de ejecución (Asignación o Agenda)
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AssignmentCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969791652682798877L;
	/**
	 * código del país en estandar ISO 2
	 */
	private String countryIso2Code;
	/**
	 * modo de ejecución AG para modalidad de agendamiento AS para modalizadad de
	 * asignación
	 */
	private String executionMode;
	/**
	 * código de la categoría de servicio, debe estar estandarizado
	 */
	private String serviceCategoryCode;

	public AssignmentCriteria(){

	}
	
	public AssignmentCriteria(String countryIso2Code, String executionMode,
			String serviceCategoryCode) {
		super();
		this.countryIso2Code = countryIso2Code;
		this.executionMode = executionMode;
		this.serviceCategoryCode = serviceCategoryCode;
	}

	public String getCountryIso2Code() {
		return countryIso2Code;
	}



	public void setCountryIso2Code(String countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}



	public String getExecutionMode() {
		return executionMode;
	}



	public void setExecutionMode(String executionMode) {
		this.executionMode = executionMode;
	}



	public String getServiceCategoryCode() {
		return serviceCategoryCode;
	}



	public void setServiceCategoryCode(String serviceCategoryCode) {
		this.serviceCategoryCode = serviceCategoryCode;
	}

	 


	@Override
	public String toString() {
		return "AssignmentCriteria [countryIso2Code=" + countryIso2Code
				+ ", executionMode=" + executionMode + ", serviceCategoryCode="
				+ serviceCategoryCode + "]";
	}

	public void finalize() throws Throwable {

	}
}