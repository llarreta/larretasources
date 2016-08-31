package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;


/**
 * DTO que encapsula una lista de objeto de tipo WarehouseInfoDTO y extiene de 
 * CollectionBase para el maenjo del paginamiento
 * @author clopez
 *
 */

public class WarehouseInfoResponseDTO extends CollectionBase implements	Serializable {

	private static final long serialVersionUID = 1L;
	private List<WarehouseInfoDTO> warehouseInfoDTOList;
	
	public WarehouseInfoResponseDTO() {
		warehouseInfoDTOList = new ArrayList<WarehouseInfoDTO>();
	}
	
	public List<WarehouseInfoDTO> getWarehouseInfoDTOList() {
		return warehouseInfoDTOList;
	}
	public void setWarehouseInfoDTOList(List<WarehouseInfoDTO> warehouseInfoDTOList) {
		this.warehouseInfoDTOList = warehouseInfoDTOList;
	}

	
}
