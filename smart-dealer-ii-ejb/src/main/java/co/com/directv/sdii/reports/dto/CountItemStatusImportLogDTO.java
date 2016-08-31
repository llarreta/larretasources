package co.com.directv.sdii.reports.dto;


public class CountItemStatusImportLogDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319436798176021359L;

	private String statusCode;
	
	private Long itemsQuatity;

	
	
	
	public CountItemStatusImportLogDTO(String statusCode, Long itemsQuatity) {
		super();
		this.statusCode = statusCode;
		this.itemsQuatity = itemsQuatity;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Long getItemsQuatity() {
		return itemsQuatity;
	}

	public void setItemsQuatity(Long itemsQuatity) {
		this.itemsQuatity = itemsQuatity;
	}

}
