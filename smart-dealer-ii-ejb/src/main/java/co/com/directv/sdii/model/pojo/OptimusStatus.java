package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

public class OptimusStatus implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;
	private String description;

	
	//SETTERS & GETTERS
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
}
