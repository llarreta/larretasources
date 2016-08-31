package co.com.ig.common.error.pojo;


/**
 * ErrorReason entity. @author MyEclipse Persistence Tools
 */

public class ErrorReason implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062901569611881964L;
	private Long id;
	private String reasonCode;
	private String reasonName;

	// Constructors

	/** default constructor */
	public ErrorReason() {
	}

	/** minimal constructor */
	public ErrorReason(String reasonCode, String reasonName) {
		this.reasonCode = reasonCode;
		this.reasonName = reasonName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReasonCode() {
		return this.reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonName() {
		return this.reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
}