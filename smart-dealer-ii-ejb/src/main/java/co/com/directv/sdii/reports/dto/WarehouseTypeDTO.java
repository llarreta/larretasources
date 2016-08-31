package co.com.directv.sdii.reports.dto;

public class WarehouseTypeDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1154399192177713237L;
	
	private Long id;
	private String whTypeCode;
	private String whTypeName;
	private String whTypeDesc;
	private String isActive;
	private String isVisible;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWhTypeCode() {
		return whTypeCode;
	}
	public void setWhTypeCode(String whTypeCode) {
		this.whTypeCode = whTypeCode;
	}
	public String getWhTypeName() {
		return whTypeName;
	}
	public void setWhTypeName(String whTypeName) {
		this.whTypeName = whTypeName;
	}
	public String getWhTypeDesc() {
		return whTypeDesc;
	}
	public void setWhTypeDesc(String whTypeDesc) {
		this.whTypeDesc = whTypeDesc;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsVisible() {
		return isVisible;
	}

}
