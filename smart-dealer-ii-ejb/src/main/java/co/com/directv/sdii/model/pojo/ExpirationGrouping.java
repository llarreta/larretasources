package co.com.directv.sdii.model.pojo;


public class ExpirationGrouping implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6170442382143018480L;
	
	private Long id;
	private String expirationGroupingName;
	private String expirationGroupingCode;
	private Long expirationGroupingInitValue;
	private Long expirationGroupingEndValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExpirationGroupingName() {
		return expirationGroupingName;
	}
	public void setExpirationGroupingName(String expirationGroupingName) {
		this.expirationGroupingName = expirationGroupingName;
	}
	public String getExpirationGroupingCode() {
		return expirationGroupingCode;
	}
	public void setExpirationGroupingCode(String expirationGroupingCode) {
		this.expirationGroupingCode = expirationGroupingCode;
	}
	public Long getExpirationGroupingInitValue() {
		return expirationGroupingInitValue;
	}
	public void setExpirationGroupingInitValue(Long expirationGroupingInitValue) {
		this.expirationGroupingInitValue = expirationGroupingInitValue;
	}
	public Long getExpirationGroupingEndValue() {
		return expirationGroupingEndValue;
	}
	public void setExpirationGroupingEndValue(Long expirationGroupingEndValue) {
		this.expirationGroupingEndValue = expirationGroupingEndValue;
	}
}