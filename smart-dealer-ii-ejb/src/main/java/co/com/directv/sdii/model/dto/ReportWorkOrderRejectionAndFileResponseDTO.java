package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

public class ReportWorkOrderRejectionAndFileResponseDTO  extends CollectionBase implements Serializable {
	private FileResponseDTO file;
	private List<ReportWorkOrderRejectionDTO> reportWorkOrderRejectionDTO;
	
	public ReportWorkOrderRejectionAndFileResponseDTO() {
		super();
	}
	
	public ReportWorkOrderRejectionAndFileResponseDTO(FileResponseDTO file,
			List<ReportWorkOrderRejectionDTO> reportWorkOrderRejectionDTO) {
		super();
		this.file = file;
		this.reportWorkOrderRejectionDTO = reportWorkOrderRejectionDTO;
	}
	
	public FileResponseDTO getFile() {
		return file;
	}
	public void setFile(FileResponseDTO file) {
		this.file = file;
	}
	public List<ReportWorkOrderRejectionDTO> getReportWorkOrderRejectionDTO() {
		return reportWorkOrderRejectionDTO;
	}
	public void setReportWorkOrderRejectionDTO(
			List<ReportWorkOrderRejectionDTO> reportWorkOrderRejectionDTO) {
		this.reportWorkOrderRejectionDTO = reportWorkOrderRejectionDTO;
	}
	
	
	
}
