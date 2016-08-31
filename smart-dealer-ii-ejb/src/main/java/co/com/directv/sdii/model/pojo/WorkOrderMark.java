package co.com.directv.sdii.model.pojo;

/**
 * WorkOrder entity.
 * 
 * Fecha de Creación: 19/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WorkOrderMark implements java.io.Serializable {


	private static final long serialVersionUID = -4187864903532259553L;
	
	private Long id;
	private String code;
	private String name;
	private String color;
	private String isUserMark;
	
	private String isUnMarkAttention;
	
	public WorkOrderMark() {
		super();
	}
	
	public WorkOrderMark(Long id, String code, String name, String color,
			String isUserMark, String isUnMarkAttention) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.color = color;
		this.isUserMark = isUserMark;
		this.isUnMarkAttention = isUnMarkAttention;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getIsUserMark() {
		return isUserMark;
	}
	public void setIsUserMark(String isUserMark) {
		this.isUserMark = isUserMark;
	}
	public String getIsUnMarkAttention() {
		return isUnMarkAttention;
	}
	public void setIsUnMarkAttention(String isUnMarkAttention) {
		this.isUnMarkAttention = isUnMarkAttention;
	}
}