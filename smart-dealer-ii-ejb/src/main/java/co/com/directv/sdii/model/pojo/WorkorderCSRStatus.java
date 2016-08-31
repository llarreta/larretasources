package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;


/**
 * WorkorderStatus entity. @author MyEclipse Persistence Tools
 */

public class WorkorderCSRStatus implements java.io.Serializable,Auditable {

	// Fields

//	/**
//	 * 
//	 */
	private static final long serialVersionUID = -4843610133059735391L;

	private Long id;

	@BusinessRequired
	private String code;

	@BusinessRequired 
    private String name;

    // Constructors

	/** default constructor */
	public WorkorderCSRStatus() {
	}
	
	public WorkorderCSRStatus(Long id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
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

	

}