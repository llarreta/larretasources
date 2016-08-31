package co.com.directv.sdii.model.dto;

import java.io.Serializable;
/***
 * 
 * Esta clase esta enfocada a encapsular los datos que necesita HSP+ de la consulta de clientes de RESB por numero de identificacion
 * 
 * @author Aharker
 *
 */
public class CustomerResponseByIdentificationResbDTO implements Serializable{
	
	private String customerCode;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public CustomerResponseByIdentificationResbDTO(String customerCode) {
		super();
		this.customerCode = customerCode;
	}

	public CustomerResponseByIdentificationResbDTO() {
		super();
	}

	
}
