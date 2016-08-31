package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

public class CreateScheduleReportDTO implements Serializable {

	private Long userId;
	private Date beginDate;
	private Date endDate;
	private Long countryId;
	private Long typeId;
	private String code;
	
	
	public CreateScheduleReportDTO(Long userId, Date beginDate, Date endDate,
			Long countryId, Long typeId, String code) {
		super();
		this.userId = userId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.countryId = countryId;
		this.typeId = typeId;
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public CreateScheduleReportDTO(Long userId, Date beginDate, Date endDate,
			Long countryId, Long typeId) {
		super();
		this.userId = userId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.countryId = countryId;
		this.typeId = typeId;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public CreateScheduleReportDTO() {
		super();
	}

}
