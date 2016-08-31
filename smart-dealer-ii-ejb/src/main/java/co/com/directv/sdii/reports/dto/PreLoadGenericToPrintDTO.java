package co.com.directv.sdii.reports.dto;

public class PreLoadGenericToPrintDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3957947858431108337L;
	private String title;
	private String description;
	private String typeTitle;
	private String subTitle;
	
	/**
	 * 
	 * @param title
	 * @param description
	 * @param typeTitle
	 * @param subTitle
	 */
	public PreLoadGenericToPrintDTO(String title, String description,
			String typeTitle, String subTitle) {
		super();
		this.title = title;
		this.description = description;
		this.typeTitle = typeTitle;
		this.subTitle = subTitle;
	}
	
	/**
	 * Constructor: <Descripcion>
	 * @author
	 */
	public PreLoadGenericToPrintDTO() {
		super();
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTypeTitle() {
		return typeTitle;
	}
	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
