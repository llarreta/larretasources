package co.com.ig.common.error.pojo;


/**
 * ErrorMessageReason entity. @author MyEclipse Persistence Tools
 */

public class ErrorMessageReason implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6260980517162583150L;

	private Long id;
	private ErrorMessage errorMessage;
	private ErrorReason errorReason;

	// Constructors

	/** default constructor */
	public ErrorMessageReason() {
	}

	/** full constructor */
	public ErrorMessageReason(ErrorMessage errorMessage, ErrorReason errorReason) {
		this.errorMessage = errorMessage;
		this.errorReason = errorReason;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ErrorMessage getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorReason getErrorReason() {
		return this.errorReason;
	}

	public void setErrorReason(ErrorReason errorReason) {
		this.errorReason = errorReason;
	}

}