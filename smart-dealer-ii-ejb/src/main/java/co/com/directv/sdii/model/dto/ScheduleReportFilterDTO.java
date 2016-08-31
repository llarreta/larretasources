package co.com.directv.sdii.model.dto;

/**
 * //CC053 - HSP Reportes - CRUD Programacion.
 * @author martlago
 *
 */
public class ScheduleReportFilterDTO {
	
	private Long countryId;
	private Long typeId;
	private Long statusId;
	
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	
	

}
