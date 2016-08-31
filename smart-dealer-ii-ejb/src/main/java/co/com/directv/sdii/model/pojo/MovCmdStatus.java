package co.com.directv.sdii.model.pojo;


/**
 * MovCmdStatus entity. @author MyEclipse Persistence Tools
 */

public class MovCmdStatus implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8169399920110359274L;
	private Long id;
	private String cmdStatusCode;
	private String cmdStatusName;

	// Constructors

	/** default constructor */
	public MovCmdStatus() {
	}

	/** full constructor */
	public MovCmdStatus(String cmdStatusCode, String cmdStatusName) {
		this.cmdStatusCode = cmdStatusCode;
		this.cmdStatusName = cmdStatusName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCmdStatusCode() {
		return this.cmdStatusCode;
	}

	public void setCmdStatusCode(String cmdStatusCode) {
		this.cmdStatusCode = cmdStatusCode;
	}

	public String getCmdStatusName() {
		return this.cmdStatusName;
	}

	public void setCmdStatusName(String cmdStatusName) {
		this.cmdStatusName = cmdStatusName;
	}
}