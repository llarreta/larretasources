package co.com.directv.sdii.model.dto;

public class WorkOrderMarkDTO implements  java.io.Serializable{

	private String code;
	private String name;
	private String color;
	
	public WorkOrderMarkDTO() {
		super();
	}
	
	public WorkOrderMarkDTO(String code, String name, String color) {
		super();
		this.code = code;
		this.name = name;
		this.color = color;
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
	
	
	
}
