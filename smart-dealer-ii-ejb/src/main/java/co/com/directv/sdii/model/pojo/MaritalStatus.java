package co.com.directv.sdii.model.pojo;




public class MaritalStatus implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2350695506999769470L;
	
	private Long id;
	private String maritalStatusCode;
	private String maritalStatusName;
	private String maritalStatusDesc;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}
	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}
	public String getMaritalStatusName() {
		return maritalStatusName;
	}
	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}
	public String getMaritalStatusDesc() {
		return maritalStatusDesc;
	}
	public void setMaritalStatusDesc(String maritalStatusDesc) {
		this.maritalStatusDesc = maritalStatusDesc;
	}

	

}