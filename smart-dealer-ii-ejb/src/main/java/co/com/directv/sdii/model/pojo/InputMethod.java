package co.com.directv.sdii.model.pojo;


/**
 * InputMethod entity. @author MyEclipse Persistence Tools
 */

public class InputMethod implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6662632884296844954L;
	private Long id;
	private String inputCode;
	private String ibsCode;
	private String inputName;

	// Constructors

	/** default constructor */
	public InputMethod() {
	}

	/** full constructor */
	public InputMethod(String inputCode, String inputName,String ibsCode) {
		this.inputCode = inputCode;
		this.inputName = inputName;
		this.ibsCode=ibsCode;
	}

	// Property accessors
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getIbsCode() {
		return ibsCode;
	}

	public void setIbsCode(String ibsCode) {
		this.ibsCode = ibsCode;
	}

	@Override
	public String toString() {
		return "InputMethod [id=" + id + ", inputCode=" + inputCode + "]";
	}
	
	
}