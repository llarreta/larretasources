package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

public class ReportsPendingServicesByDateAndFileResponseDTO  extends CollectionBase implements Serializable {
	private FileResponseDTO file;
	private List<ReportsPendingServicesByDateDTO> reportsPendingServicesByDateDTOList;
	public ReportsPendingServicesByDateAndFileResponseDTO(
			FileResponseDTO file,
			List<ReportsPendingServicesByDateDTO> reportsPendingServicesByDateDTOList) {
		super();
		this.file = file;
		this.reportsPendingServicesByDateDTOList = reportsPendingServicesByDateDTOList;
	}
	public ReportsPendingServicesByDateAndFileResponseDTO() {
		super();
	}
	public FileResponseDTO getFile() {
		return file;
	}
	public void setFile(FileResponseDTO file) {
		this.file = file;
	}
	public List<ReportsPendingServicesByDateDTO> getReportsPendingServicesByDateDTOList() {
		return reportsPendingServicesByDateDTOList;
	}
	public void setReportsPendingServicesByDateDTOList(
			List<ReportsPendingServicesByDateDTO> reportsPendingServicesByDateDTOList) {
		this.reportsPendingServicesByDateDTOList = reportsPendingServicesByDateDTOList;
	}
	
	
	
}
