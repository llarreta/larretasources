package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * MediaContactType entity. 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class MediaContactType implements java.io.Serializable,Auditable {

	// Fields

	private Long id;
	private String mediaName;
	private String mediaCode;

	// Constructors

	/** default constructor */
	public MediaContactType() {
	}

	/** minimal constructor */
	public MediaContactType(String mediaName, String mediaCode) {
		this.mediaName = mediaName;
		this.mediaCode = mediaCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMediaName() {
		return this.mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaCode() {
		return this.mediaCode;
	}

	public void setMediaCode(String mediaCode) {
		this.mediaCode = mediaCode;
	}

	@Override
	public String toString() {
		return "MediaContactType [id=" + id + ", mediaCode=" + mediaCode + "]";
	}

}