package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * CustomerTitle entity. @author MyEclipse Persistence Tools
 */

public class CustomerTitle implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3900745931267340922L;
	private Long id;
	private String titleCode;
	private String titleName;

	// Constructors

	/** default constructor */
	public CustomerTitle() {
	}

	/** minimal constructor */
	public CustomerTitle(String titleCode, String titleName) {
		this.titleCode = titleCode;
		this.titleName = titleName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitleCode() {
		return this.titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	@Override
	public String toString() {
		return "CustomerTitle [id=" + id + ", titleCode=" + titleCode + "]";
	}

}