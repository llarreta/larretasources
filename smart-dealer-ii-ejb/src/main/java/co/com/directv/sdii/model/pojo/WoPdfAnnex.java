package co.com.directv.sdii.model.pojo;


/**
 * WoPdfAnnex entity. 
 */

public class WoPdfAnnex implements java.io.Serializable {

	private static final long serialVersionUID = 5151206697228442536L;
	private Long annexId;
	private String formatName;
	private String formatTypeId;
	private String formatCategory;
	private String dealerType;
	private String serviceType;
	private String countryId;
	private String departmentId;
	private String cityId;
	private Integer priority;
	
	public WoPdfAnnex() {
	}

	public WoPdfAnnex(Long annexId, String formatName, String formatTypeId, String formatCategory, String dealerType, String serviceType, String countryId,
			String departmentId, String cityId, Integer priority) {
		super();
		this.annexId = annexId;
		this.formatName = formatName;
		this.formatTypeId = formatTypeId;
		this.formatCategory = formatCategory;
		this.dealerType = dealerType;
		this.serviceType = serviceType;
		this.countryId = countryId;
		this.departmentId = departmentId;
		this.cityId = cityId;
		this.priority = priority;
	}

	public Long getAnnexId() {
		return annexId;
	}

	public void setAnnexId(Long annexId) {
		this.annexId = annexId;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public String getFormatTypeId() {
		return formatTypeId;
	}

	public void setFormatTypeId(String formatTypeId) {
		this.formatTypeId = formatTypeId;
	}

	public String getFormatCategory() {
		return formatCategory;
	}

	public void setFormatCategory(String formatCategory) {
		this.formatCategory = formatCategory;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WoPdfAnnex [annexId=" + annexId + ", formatName=" + formatName + "]";
	}
}