package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

/**
 * Entidad del estado del procesamiento en paralelo de core y asignador
 * @author Aharker
 *
 */
public class WoInfoEsbState implements Serializable{
	
	private Long id;
	private String code;
	private String description;
	
	public WoInfoEsbState() {
		super();
	}
	
	public WoInfoEsbState(Long id, String code, String description) {
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
	
	
	
}