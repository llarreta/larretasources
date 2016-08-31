package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class CustomerAgreementTypesDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerCode;
	
	private String ibsAdreementTypeId;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getIbsAdreementTypeId() {
		return ibsAdreementTypeId;
	}

	public void setIbsAdreementTypeId(String ibsAdreementTypeId) {
		this.ibsAdreementTypeId = ibsAdreementTypeId;
	}
	
	
}
