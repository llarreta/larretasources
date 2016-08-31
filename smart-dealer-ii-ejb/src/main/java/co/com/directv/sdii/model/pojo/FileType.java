package co.com.directv.sdii.model.pojo;


/**
 * FileType entity. @author MyEclipse Persistence Tools
 */

public class FileType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6962207535927310965L;
	private Long id;
	private String name;
	private String code;

	// Constructors

	/** default constructor */
	public FileType() {
	}

	/** minimal constructor */
	public FileType(Long id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}