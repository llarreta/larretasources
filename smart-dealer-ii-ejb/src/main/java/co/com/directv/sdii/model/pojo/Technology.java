package co.com.directv.sdii.model.pojo;

/**
 * Technology entity. @author MyEclipse Persistence Tools
 */

public class Technology implements java.io.Serializable {

	private static final long serialVersionUID = -6694877673791947031L;
	
	private Long id;
	private String code;
	private String name;
	private String isActive;
	private String ibsCode;
	
	/**
	 * Atributo encargado de indicar si  la tecnologia especifica es un IRD o no
	 * @author Aharker
	 */
	private String isIRDTechnology;

	// Constructors

	public String getIsIRDTechnology() {
		return isIRDTechnology;
	}

	public void setIsIRDTechnology(String isIRDTechnology) {
		this.isIRDTechnology = isIRDTechnology;
	}

	/** default constructor */
	public Technology() {
	}

	/**
	 * 
	 * @param id
	 * @param code
	 * @param name
	 */
	public Technology(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}


	public Technology(Technology copy) {
		super();
		this.id = copy.id;
		this.code = copy.code;
		this.name = copy.name;
		this.isActive = copy.isActive;
		this.ibsCode = copy.ibsCode;
		this.isIRDTechnology = copy.isIRDTechnology;
	}

	public Technology(Long id, String code, String name, String isActive,
			String ibsCode, String isIRDTechnology) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.isActive = isActive;
		this.ibsCode = ibsCode;
		this.isIRDTechnology = isIRDTechnology;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIbsCode() {
		return ibsCode;
	}

	public void setIbsCode(String ibsCode) {
		this.ibsCode = ibsCode;
	}
}