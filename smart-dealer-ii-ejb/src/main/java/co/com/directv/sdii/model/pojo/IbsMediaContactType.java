package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * MediaContactType entity. 
 * @author MyEclipse Persistence Tools
 */

public class IbsMediaContactType implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6796481612428840502L;
	
	// Fields

	private Long id;
	private MediaContactType mediaContactType;
	private String ibsCode;

	// Constructors

	/** default constructor */
	public IbsMediaContactType() {
	}

	/** full constructor */
	public IbsMediaContactType(MediaContactType mediaContactType,
			String ibsCode) {
		this.mediaContactType = mediaContactType;
		this.ibsCode = ibsCode;
	}
	
	/** minimal constructor */
	public IbsMediaContactType(MediaContactType mediaContactType) {
		this.mediaContactType = mediaContactType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MediaContactType getMediaContactType() {
		return mediaContactType;
	}

	public void setMediaContactType(MediaContactType mediaContactType) {
		this.mediaContactType = mediaContactType;
	}

	public String getIbsCode() {
		return ibsCode;
	}

	public void setIbsCode(String ibsCode) {
		this.ibsCode = ibsCode;
	}

	@Override
	public String toString() {
		return "IbsMediaContactType [ibsCode=" + ibsCode + ", id=" + id
				+ ", mediaContactType=" + mediaContactType + "]";
	}
}