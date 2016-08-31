package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package

/**
 * EmployeeMediaContact entity. @author MyEclipse Persistence Tools
 */
public class EmployeeMediaContact implements java.io.Serializable,Auditable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 971839134568665000L;
	// Fields
    private Long id;
    private Employee employee;
    private MediaContactType mediaContactType;
    private String mediaContactValue;

    // Constructors
    /** default constructor */
    public EmployeeMediaContact() {
    }

    /** full constructor */
    public EmployeeMediaContact(Employee employee,
            MediaContactType mediaContactType, String mediaContactValue) {
        this.employee = employee;
        this.mediaContactType = mediaContactType;
        this.mediaContactValue = mediaContactValue;
    }

    public EmployeeMediaContact(MediaContactType mediaContactType, String mediaContactValue) {
        this.mediaContactType = mediaContactType;
        this.mediaContactValue = mediaContactValue;
    }
    
    public EmployeeMediaContact(MediaContactType mediaContactType, String mediaContactValue, Long id) {
    	this.mediaContactType = mediaContactType;
        this.mediaContactValue = mediaContactValue;
        this.id = id;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public MediaContactType getMediaContactType() {
        return this.mediaContactType;
    }

    public void setMediaContactType(MediaContactType mediaContactType) {
        this.mediaContactType = mediaContactType;
    }

    public String getMediaContactValue() {
        return this.mediaContactValue;
    }

    public void setMediaContactValue(String mediaContactValue) {
        this.mediaContactValue = mediaContactValue;
    }

	@Override
	public String toString() {
		return "EmployeeMediaContact [id=" + id + "]";
	}
    
}
