package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * SaleChanel entity. @author MyEclipse Persistence Tools
 */

public class SaleChanel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4914444669244607207L;
	private Long id;
	private Country country;
	private String code;
	private String name;
	private Date creationDate;
	private String status;

	// Constructors

	/** default constructor */
	public SaleChanel() {
	}

	/** minimal constructor */
	public SaleChanel(Country country, String code, String name,
			Date creationDate) {
		this.country = country;
		this.code = code;
		this.name = name;
		this.creationDate = creationDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}