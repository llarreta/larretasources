package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;

public class WarehouseInfoResponseDetailDTO extends CollectionBase implements
		Serializable {
	private static final long serialVersionUID = 1L;
	private List<WarehouseInfoDetailDTO> warehouseInfoDetailDTOList;
	
	public WarehouseInfoResponseDetailDTO() {
		warehouseInfoDetailDTOList = new ArrayList<WarehouseInfoDetailDTO>();
	}
	
	public List<WarehouseInfoDetailDTO> getWarehouseInfoDetailDTOList() {
		return warehouseInfoDetailDTOList;
	}
	public void setWarehouseInfoDetailDTOList(List<WarehouseInfoDetailDTO> warehouseInfoDetailDTOList) {
		this.warehouseInfoDetailDTOList = warehouseInfoDetailDTOList;
	}


}
