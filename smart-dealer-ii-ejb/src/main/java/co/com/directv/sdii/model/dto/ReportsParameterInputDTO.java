package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import co.com.directv.sdii.model.pojo.ScheduleReportParameter;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

public class ReportsParameterInputDTO  extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codeScheduleReportType;
	private Long idScheduleReport;
	private String codeScheduleReport;
	private Date beginDate;
	private Date endDate;
	private Long countryId;
	private String nameFileResponse;
	private Set<ScheduleReportParameter> scheduleReportParameters;
	
	private Date dateNow;

	public Date getDateNow() {
		return dateNow;
	}

	public void setDateNow(Date dateNow) {
		this.dateNow = dateNow;
	}

	public ReportsParameterInputDTO(String codeScheduleReportType,
			Long idScheduleReport, String codeScheduleReport, Date beginDate,
			Date endDate, Long countryId, String nameFileResponse,
			FileResponseDTO fileResponseDTO) {
		super();
		this.codeScheduleReportType = codeScheduleReportType;
		this.idScheduleReport = idScheduleReport;
		this.codeScheduleReport = codeScheduleReport;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.countryId = countryId;
		this.nameFileResponse = nameFileResponse;
		this.fileResponseDTO = fileResponseDTO;
	}

	public String getNameFileResponse() {
		return nameFileResponse;
	}

	public void setNameFileResponse(String nameFileResponse) {
		this.nameFileResponse = nameFileResponse;
	}

	private FileResponseDTO fileResponseDTO;
	
	public ReportsParameterInputDTO() {
		super();
	}

	public ReportsParameterInputDTO(String codeScheduleReportType,
			Long idScheduleReport, String codeScheduleReport, Date beginDate,
			Date endDate, Long countryId, FileResponseDTO fileResponseDTO) {
		super();
		this.codeScheduleReportType = codeScheduleReportType;
		this.idScheduleReport = idScheduleReport;
		this.codeScheduleReport = codeScheduleReport;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.countryId = countryId;
		this.fileResponseDTO = fileResponseDTO;
	}

	public String getCodeScheduleReportType() {
		return codeScheduleReportType;
	}

	public void setCodeScheduleReportType(String codeScheduleReportType) {
		this.codeScheduleReportType = codeScheduleReportType;
	}

	public Long getIdScheduleReport() {
		return idScheduleReport;
	}

	public void setIdScheduleReport(Long idScheduleReport) {
		this.idScheduleReport = idScheduleReport;
	}

	public String getCodeScheduleReport() {
		return codeScheduleReport;
	}

	public void setCodeScheduleReport(String codeScheduleReport) {
		this.codeScheduleReport = codeScheduleReport;
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

	public FileResponseDTO getFileResponseDTO() {
		return fileResponseDTO;
	}

	public void setFileResponseDTO(FileResponseDTO fileResponseDTO) {
		this.fileResponseDTO = fileResponseDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<ScheduleReportParameter> getScheduleReportParameters() {
		return scheduleReportParameters;
	}

	public void setScheduleReportParameters(
			Set<ScheduleReportParameter> scheduleReportParameters) {
		this.scheduleReportParameters = scheduleReportParameters;
	}
	
}
