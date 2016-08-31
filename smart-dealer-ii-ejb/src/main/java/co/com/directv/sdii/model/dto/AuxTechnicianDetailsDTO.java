package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class AuxTechnicianDetailsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Código de la Work Order
	private String woCode;
	//Código del Rol del colaborador correspondiente a la cuadrilla
	private String employeeCode;
	//Nombre del Colaborador
	private String employeeName;
	//Número de Documento del colaborador
	private String employeeDocument;
	
	
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeDocument() {
		return employeeDocument;
	}
	public void setEmployeeDocument(String employeeDocument) {
		this.employeeDocument = employeeDocument;
	}
	
	
}
