package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

public class ReportsSucceedWorkOrderCSRAndFileResponseDTO extends CollectionBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8995022902165813477L;
	
	/* 
	 * Archivo de respuesta del reporte
	 * */
	private FileResponseDTO file;
	
	/* 
	 * Lista de elementos de respuesta del reporte
	 * */
	private List<ReportsSucceedWorkOrderCSRDTO> reportsSucceedWorkOrderCSRDTOList;

	public ReportsSucceedWorkOrderCSRAndFileResponseDTO() {
		super();
	}
	
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO(
			FileResponseDTO file,
			List<ReportsSucceedWorkOrderCSRDTO> reportsSucceedWorkOrderCSRDTOList) {
		super();
		this.file = file;
		this.reportsSucceedWorkOrderCSRDTOList = reportsSucceedWorkOrderCSRDTOList;
	}	
	
	public FileResponseDTO getFile() {
		return file;
	}
	public void setFile(FileResponseDTO file) {
		this.file = file;
	}
	public List<ReportsSucceedWorkOrderCSRDTO> getReportsSucceedWorkOrderCSRDTOList() {
		return reportsSucceedWorkOrderCSRDTOList;
	}
	public void setReportsSucceedWorkOrderCSRDTOList(
			List<ReportsSucceedWorkOrderCSRDTO> reportsSucceedWorkOrderCSRDTOList) {
		this.reportsSucceedWorkOrderCSRDTOList = reportsSucceedWorkOrderCSRDTOList;
	}	
	
}
