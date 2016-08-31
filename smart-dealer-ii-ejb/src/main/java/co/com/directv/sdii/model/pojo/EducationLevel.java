package co.com.directv.sdii.model.pojo;




public class EducationLevel implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1717583388212166159L;
	
	private Long id;
	private String educationLevelCode;
	private String educationLevelName;
	private String educationLevelDesc;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEducationLevelCode() {
		return educationLevelCode;
	}
	public void setEducationLevelCode(String educationLevelCode) {
		this.educationLevelCode = educationLevelCode;
	}
	public String getEducationLevelName() {
		return educationLevelName;
	}
	public void setEducationLevelName(String educationLevelName) {
		this.educationLevelName = educationLevelName;
	}
	public String getEducationLevelDesc() {
		return educationLevelDesc;
	}
	public void setEducationLevelDesc(String educationLevelDesc) {
		this.educationLevelDesc = educationLevelDesc;
	}

}