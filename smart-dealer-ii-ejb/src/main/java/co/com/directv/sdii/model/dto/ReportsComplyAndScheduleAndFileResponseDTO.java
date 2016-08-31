package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

public class ReportsComplyAndScheduleAndFileResponseDTO extends CollectionBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private FileResponseDTO file;
	private List<ReportsComplyAndScheduleDTO> reportsComplyAndScheduleDTOList;

	public ReportsComplyAndScheduleAndFileResponseDTO() {
		super();
	}
	public ReportsComplyAndScheduleAndFileResponseDTO(FileResponseDTO file,
			List<ReportsComplyAndScheduleDTO> reportsComplyAndScheduleDTOList) {
		super();
		this.file = file;
		this.reportsComplyAndScheduleDTOList = reportsComplyAndScheduleDTOList;
	}
	public FileResponseDTO getFile() {
		return file;
	}
	public void setFile(FileResponseDTO file) {
		this.file = file;
	}
	public List<ReportsComplyAndScheduleDTO> getReportsComplyAndScheduleDTOList() {
		return reportsComplyAndScheduleDTOList;
	}
	public void setReportsComplyAndScheduleDTOList(
			List<ReportsComplyAndScheduleDTO> reportsComplyAndScheduleDTOList) {
		this.reportsComplyAndScheduleDTOList = reportsComplyAndScheduleDTOList;
	}
	
	
	
}
