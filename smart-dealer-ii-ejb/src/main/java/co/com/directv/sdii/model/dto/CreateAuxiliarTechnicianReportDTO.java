package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.com.directv.sdii.model.vo.WorkorderStatusVO;

public class CreateAuxiliarTechnicianReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6313760973760579704L;
	
	private Long userId;	
	private String countryCode;
	private Date beginDate;
	private Date endDate;
	private List<WorkorderStatusVO> woStatusList;
	
		
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
	public List<WorkorderStatusVO> getWoStatusList() {
		return woStatusList;
	}
	public void setWoStatusList(List<WorkorderStatusVO> woStatusList) {
		this.woStatusList = woStatusList;
	}
	
	
}
