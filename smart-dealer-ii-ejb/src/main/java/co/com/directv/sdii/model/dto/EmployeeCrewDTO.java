package co.com.directv.sdii.model.dto;

/**
 * 
 * Clase para mapear informacion necesaria en presentacion para desplegar lista de cuadrillas
 * con nombres de los responsables  
 * 
 * Fecha de Creación: 12/05/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class EmployeeCrewDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3323389121943137323L;
	
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private Long crewId;
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	public Long getCrewId() {
		return crewId;
	}
	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}

}
