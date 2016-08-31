package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerTitle;
import co.com.directv.sdii.model.pojo.PostalCode;

/**
 * 
 * Customer Value Object
 * 
 * Fecha de Creaci�n: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Customer
 */
public class CustomerVO extends Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2483823163781133401L;

	private String documentTypeName;
	private List<SerializedVO> customerResources;
	private String codeBuilding;
	private String customerName;
	
	private transient String customerClassCode;
	private transient String customerTypeCode;

	public String getCustomerClassCode() {
		return customerClassCode;
	}

	public void setCustomerClassCode(String customerClassCode) {
		this.customerClassCode = customerClassCode;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}

	public CustomerVO(String documentTypeName,
			List<SerializedVO> customerResources) {
		super();
		this.documentTypeName = documentTypeName;
		this.customerResources = customerResources;
	}
	
	public CustomerVO() {
		super();
	}

	public CustomerVO(Customer customer,String codeBuilding,String documentTypeName) {
		super(customer.getId(),
			  customer.getCustomerTitle(),
			  customer.getPostalCode(), 
			  customer.getCustomerCode(), 
			  customer.getFirstName(),
			  customer.getLastName(), 
			  customer.getDocumentTypeId(), 
			  customer.getDocumentNumber(),
			  customer.getAddressCode(), 
			  customer.getAddressType(), 
			  customer.getCustomeraddress(),
			  customer.getAditionalIndications(), 
			  customer.getNeighborhood(),
			  customer.getExtraIndications(), 
			  customer.getLegalRepresentative(),
			  customer.getCountry(), 
			  customer.getCustType());
		this.customerName = customer.getFirstName() + " " + customer.getLastName();
		this.documentTypeName = documentTypeName;
		this.codeBuilding = codeBuilding;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public List<SerializedVO> getCustomerResources() {
		return customerResources;
	}

	public void setCustomerResources(List<SerializedVO> customerResources) {
		this.customerResources = customerResources;
	}

	public String getCodeBuilding() {
		return codeBuilding;
	}

	public void setCodeBuilding(String codeBuilding) {
		this.codeBuilding = codeBuilding;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	
}
