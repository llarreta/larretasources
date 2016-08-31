package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Alliance entity. @author MyEclipse Persistence Tools
 */

public class Alliance implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772829126946511610L;

	// Fields

	private Long id;
        
    @Override
	public String toString() {
		return "Alliance [allianceCode=" + allianceCode + ", id=" + id + "]";
	}

	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private AllianceCompany allianceCompany;
    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ComercialProduct comercialProduct;
    @BusinessRequired
	private String allianceName;
    @BusinessRequired
	private String allianceCode;
    @BusinessRequired
	private Date initDate;
    @BusinessRequired
	private Date endDate;
    @BusinessRequired
    private Country country;
	// Constructors

	/** default constructor */
	public Alliance() {
	}

	/**
	 * 
	 * @param allianceCompany
	 * @param comercialProduct
	 * @param allianceName
	 * @param allianceCode
	 * @param initDate
	 */
	public Alliance(AllianceCompany allianceCompany,
			ComercialProduct comercialProduct, String allianceName,
			String allianceCode, Date initDate) {
		this.allianceCompany = allianceCompany;
		this.comercialProduct = comercialProduct;
		this.allianceName = allianceName;
		this.allianceCode = allianceCode;
		this.initDate = initDate;
	}
	
	public Alliance(AllianceCompany allianceCompany,
			ComercialProduct comercialProduct, String allianceName,
			String allianceCode, Date initDate,Country country) {
		this.allianceCompany = allianceCompany;
		this.comercialProduct = comercialProduct;
		this.allianceName = allianceName;
		this.allianceCode = allianceCode;
		this.initDate = initDate;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AllianceCompany getAllianceCompany() {
		return this.allianceCompany;
	}

	public void setAllianceCompany(AllianceCompany allianceCompany) {
		this.allianceCompany = allianceCompany;
	}

	public ComercialProduct getComercialProduct() {
		return this.comercialProduct;
	}

	public void setComercialProduct(ComercialProduct comercialProduct) {
		this.comercialProduct = comercialProduct;
	}

	public String getAllianceName() {
		return this.allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public String getAllianceCode() {
		return this.allianceCode;
	}

	public void setAllianceCode(String allianceCode) {
		this.allianceCode = allianceCode;
	}

	public Date getInitDate() {
		return this.initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}