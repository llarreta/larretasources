package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.reports.dto.FileResponseDTO;



/**
 * DTo que encapsula una lista de objeto de tipo DealerInfoDTO y extiene de 
 * CollectionBase para el maenjo del paginamiento
 * @author waguilera
 *
 */
public class DealerInfoResponseDTO extends CollectionBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private FileResponseDTO file;
	private List<DealerInfoDTO> dealerInfoDTOList;
	public FileResponseDTO getFile() {
		return file;
	}
	public void setFile(FileResponseDTO file) {
		this.file = file;
	}
	public List<DealerInfoDTO> getDealerInfoDTOList() {
		return dealerInfoDTOList;
	}
	public void setDealerInfoDTOList(List<DealerInfoDTO> dealerInfoDTOList) {
		this.dealerInfoDTOList = dealerInfoDTOList;
	}
	public DealerInfoResponseDTO(FileResponseDTO file,
			List<DealerInfoDTO> dealerInfoDTOList) {
		super();
		this.file = file;
		this.dealerInfoDTOList = dealerInfoDTOList;
	}
	public DealerInfoResponseDTO() {
		super();
	}
	public DealerInfoResponseDTO(DealerInfoResponseDTO copy) {
		super();
		this.file = copy.file;
		this.dealerInfoDTOList = copy.dealerInfoDTOList;
	}
	
	
}
