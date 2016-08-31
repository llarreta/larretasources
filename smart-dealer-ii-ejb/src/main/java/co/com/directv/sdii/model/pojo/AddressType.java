package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

public class AddressType implements Serializable{

	private Long id;
	private String code;
	private String name;
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
	public AddressType() {
		super();
	}
	public AddressType(Long id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	public AddressType(AddressType copy) {
		super();
		this.id = copy.id;
		this.code = copy.code;
		this.name = copy.name;
	}
	public AddressType(Long id) {
		super();
		this.id = id;
	}
	
	
	
}
