package co.com.directv.sdii.reports.dto;

public class WorkorderDescriptionDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1154399192177713237L;
	
	private String description;
	
	public WorkorderDescriptionDTO() {
		super();
	}

	public WorkorderDescriptionDTO(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
