package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * ContractType entity. @author MyEclipse Persistence Tools
 */

public class ContractType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3593604166304418458L;
	private Long id;
	private String contractTypeName;
	private String contractTypeCode;
	private Country country;

	// Constructors

	/** default constructor */
	public ContractType() {
	}

	/**
	 * 
	 * @param contractTypeName
	 * @param contractTypeCode
	 */
	public ContractType(String contractTypeName, String contractTypeCode) {
		this.contractTypeName = contractTypeName;
		this.contractTypeCode = contractTypeCode;
	}
	
	/**
	 * 
	 * @param contractTypeName
	 * @param contractTypeCode
	 * @param country
	 */
	public ContractType(String contractTypeName, String contractTypeCode,Country country) {
		this.contractTypeName = contractTypeName;
		this.contractTypeCode = contractTypeCode;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractTypeName() {
		return this.contractTypeName;
	}

	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}

	public String getContractTypeCode() {
		return this.contractTypeCode;
	}

	public void setContractTypeCode(String contractTypeCode) {
		this.contractTypeCode = contractTypeCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ContractType [contractTypeCode=" + contractTypeCode + ", id="
				+ id + "]";
	}

}