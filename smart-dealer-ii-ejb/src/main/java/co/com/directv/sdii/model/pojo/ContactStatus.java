package co.com.directv.sdii.model.pojo;


/**
 * ContactStatus entity. @author MyEclipse Persistence Tools
 */

public class ContactStatus implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6795984641562547489L;
	private Long id;
	private String conStatusCode;
	private String ibsCode;
	private String conStatusName;

	// Constructors

	/** default constructor */
	public ContactStatus() {
	}

	/**
	 * 
	 * @param conStatusCode
	 * @param conStatusName
	 * @param ibsCode
	 */
	public ContactStatus(String conStatusCode, String conStatusName,String ibsCode) {
		this.conStatusCode = conStatusCode;
		this.conStatusName = conStatusName;
		this.ibsCode=ibsCode;
	}

	// Property accessors
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConStatusCode() {
		return conStatusCode;
	}

	public void setConStatusCode(String conStatusCode) {
		this.conStatusCode = conStatusCode;
	}

	public String getConStatusName() {
		return conStatusName;
	}

	public void setConStatusName(String conStatusName) {
		this.conStatusName = conStatusName;
	}

	public String getIbsCode() {
		return ibsCode;
	}

	public void setIbsCode(String ibsCode) {
		this.ibsCode = ibsCode;
	}

	@Override
	public String toString() {
		return "ContactStatus [conStatusCode=" + conStatusCode + ", id=" + id
				+ "]";
	}
	
}