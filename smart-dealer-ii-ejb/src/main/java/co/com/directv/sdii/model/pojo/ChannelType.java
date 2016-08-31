package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * ChannelType entity. @author MyEclipse Persistence Tools
 */

public class ChannelType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4950809475776128570L;
	private Long id;
	private String canalName;
	private String canalCode;

	// Constructors

	/** default constructor */
	public ChannelType() {
	}

	/**
	 * 
	 * @param canalName
	 * @param canalCode
	 */
	public ChannelType(String canalName, String canalCode) {
		this.canalName = canalName;
		this.canalCode = canalCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCanalName() {
		return this.canalName;
	}

	public void setCanalName(String canalName) {
		this.canalName = canalName;
	}

	public String getCanalCode() {
		return this.canalCode;
	}

	public void setCanalCode(String canalCode) {
		this.canalCode = canalCode;
	}

	@Override
	public String toString() {
		return "ChannelType [canalCode=" + canalCode + ", id=" + id + "]";
	}

}