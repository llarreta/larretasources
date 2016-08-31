package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * CustomerClass entity. @author MyEclipse Persistence Tools
 */

public class CustomerCategory implements java.io.Serializable,Auditable {

	private static final long serialVersionUID = 2415755728191780478L;

	private Long id;
	private String name;
	private String code;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public CustomerCategory(Long id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}
	public CustomerCategory() {
		super();
	}
	public CustomerCategory(CustomerCategory copy) {
		super();
		this.id = copy.id;
		this.name = copy.name;
		this.code = copy.code;
	}
	
	

}