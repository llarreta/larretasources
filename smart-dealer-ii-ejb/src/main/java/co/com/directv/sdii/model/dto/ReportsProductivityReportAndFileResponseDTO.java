package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

public class ReportsProductivityReportAndFileResponseDTO  extends CollectionBase implements Serializable {
	private FileResponseDTO file;
	private List<ReportsProductivityReportDTO> reportsProductivityReportDTOList;
	public ReportsProductivityReportAndFileResponseDTO() {
		super();
	}
	public ReportsProductivityReportAndFileResponseDTO(FileResponseDTO file,
			List<ReportsProductivityReportDTO> reportsProductivityReportDTOList) {
		super();
		this.file = file;
		this.reportsProductivityReportDTOList = reportsProductivityReportDTOList;
	}
	public FileResponseDTO getFile() {
		return file;
	}
	public void setFile(FileResponseDTO file) {
		this.file = file;
	}
	public List<ReportsProductivityReportDTO> getReportsProductivityReportDTOList() {
		return reportsProductivityReportDTOList;
	}
	public void setReportsProductivityReportDTOList(
			List<ReportsProductivityReportDTO> reportsProductivityReportDTOList) {
		this.reportsProductivityReportDTOList = reportsProductivityReportDTOList;
	}
	
	
	
}
