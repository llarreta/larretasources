package co.com.directv.sdii.model.pojo;



/**
 * LoadFileType entity
 * 
 * Fecha de Creación: 25/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class LoadFileType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3222460643584178118L;
	// Fields

	private Long id;
	private String code;
	private String description;

	// Constructors

	/** default constructor */
	public LoadFileType() {
	}

	/** full constructor */
	public LoadFileType(Long id, String code, String description) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}