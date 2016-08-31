package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * SystemParameter entity. @author MyEclipse Persistence Tools
 */

public class SystemParameter implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1367059468018288367L;

	private Long id;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ParameterType parameterType;
    @BusinessRequired
	private String parameterName;
    @BusinessRequired
	private String value;
    @BusinessRequired
	private String parameterCode;
    @BusinessRequired
    private Country country;

	// Constructors

	/** default constructor */
	public SystemParameter() {
	}

	/** minimal constructor */
	public SystemParameter(ParameterType parameterType, String parameterName,
			String value, String parameterCode) {
		this.parameterType = parameterType;
		this.parameterName = parameterName;
		this.value = value;
		this.parameterCode = parameterCode;
	}
	
	/** full constructor */
	public SystemParameter(ParameterType parameterType, String parameterName,
			String value, String parameterCode,Country country) {
		this.parameterType = parameterType;
		this.parameterName = parameterName;
		this.value = value;
		this.parameterCode = parameterCode;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ParameterType getParameterType() {
		return this.parameterType;
	}

	public void setParameterType(ParameterType parameterType) {
		this.parameterType = parameterType;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParameterCode() {
		return this.parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "SystemParameter [country=" + country + ", id=" + id
				+ ", parameterCode=" + parameterCode + ", value=" + value + "]";
	}
}