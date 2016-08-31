package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * PostalCode entity. @author MyEclipse Persistence Tools
 */

public class PostalCode implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6097607897169722138L;
	private Long id;
	private ZoneType zoneType;
	private City city;
	private String postalCodeName;
	private String postalCodeCode;

	// Constructors

	/** default constructor */
	public PostalCode() {
	}

	public PostalCode(Long id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public PostalCode(Long id, ZoneType zoneType, City city, String postalCodeName,
			String postalCodeCode) {
		this.id = id;
		this.zoneType = zoneType;
		this.city = city;
		this.postalCodeName = postalCodeName;
		this.postalCodeCode = postalCodeCode;
	}

	/** minimal constructor */
	public PostalCode(ZoneType zoneType, String postalCodeName,
			String postalCodeCode) {
		this.zoneType = zoneType;
		this.postalCodeName = postalCodeName;
		this.postalCodeCode = postalCodeCode;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZoneType getZoneType() {
		return this.zoneType;
	}

	public void setZoneType(ZoneType zoneType) {
		this.zoneType = zoneType;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getPostalCodeName() {
		return this.postalCodeName;
	}

	public void setPostalCodeName(String postalCodeName) {
		this.postalCodeName = postalCodeName;
	}

	public String getPostalCodeCode() {
		return this.postalCodeCode;
	}

	public void setPostalCodeCode(String postalCodeCode) {
		this.postalCodeCode = postalCodeCode;
	}

	@Override
	public String toString() {
		return "PostalCode [id=" + id + ", postalCodeCode=" + postalCodeCode
				+ ", postalCodeName=" + postalCodeName + "]";
	}
}