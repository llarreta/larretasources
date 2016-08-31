package co.com.directv.sdii.model.dto;

import java.io.Serializable;
/***
 * 
 * Esta clase esta enfocada a encapsular los datos que necesita HSP+ de la consulta de los elementos que tiene un cliente de RESB
 * 
 * @author Aharker
 *
 */
public class CustomersResourcesResbDTO implements Serializable{
	
	private String serial;
	private String state;
	private String type;
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public CustomersResourcesResbDTO(String serial, String state, String type) {
		super();
		this.serial = serial;
		this.state = state;
		this.type = type;
	}
	public CustomersResourcesResbDTO() {
		super();
	}
	
	
}
