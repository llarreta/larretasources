package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * RecordStatus entity. @author MyEclipse Persistence Tools
 */

public class RecordStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3393819681864765947L;
	private Long id;
	private String recordStatusCode;
	private String recordStatusName;

	// Constructors

	/** default constructor */
	public RecordStatus() {
	}

	/** minimal constructor */
	public RecordStatus(String recordStatusCode, String recordStatusName) {
		this.recordStatusCode = recordStatusCode;
		this.recordStatusName = recordStatusName;
	}
	
	public RecordStatus(Long id , String recordStatusCode, String recordStatusName) {
		this.id = id;
		this.recordStatusCode = recordStatusCode;
		this.recordStatusName = recordStatusName;
	}
	
	public RecordStatus(Long id , String recordStatusCode){
		this.id = id;
		this.recordStatusCode = recordStatusCode;
	}
	
	public RecordStatus(Long id){
		this.id = id;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecordStatusCode() {
		return this.recordStatusCode;
	}

	public void setRecordStatusCode(String recordStatusCode) {
		this.recordStatusCode = recordStatusCode;
	}

	public String getRecordStatusName() {
		return this.recordStatusName;
	}

	public void setRecordStatusName(String recordStatusName) {
		this.recordStatusName = recordStatusName;
	}

	@Override
	public String toString() {
		return "RecordStatus [id=" + id + ", recordStatusCode="
				+ recordStatusCode + "]";
	}
}