package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.reports.dto.WarehouseElementCustomerDTO;

public class WareHouseElementCustomerResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5834345624597584227L;
	
	private List<WarehouseElementCustomerDTO> wareHouseElementsDto;

	private List<WarehouseElement> wareHouseElements;

	public List<WarehouseElementCustomerDTO> getWareHouseElementsDto() {
		return wareHouseElementsDto;
	}

	public void setWareHouseElementsDto(
			List<WarehouseElementCustomerDTO> wareHouseElementsDto) {
		this.wareHouseElementsDto = wareHouseElementsDto;
	}

	public List<WarehouseElement> getWareHouseElements() {
		return wareHouseElements;
	}

	public void setWareHouseElements(List<WarehouseElement> wareHouseElements) {
		this.wareHouseElements = wareHouseElements;
	}

}
