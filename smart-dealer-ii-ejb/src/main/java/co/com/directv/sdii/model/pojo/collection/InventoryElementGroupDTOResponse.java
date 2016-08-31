package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.reports.dto.InventoryElementGroupDTO;

public class InventoryElementGroupDTOResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4291649260191326163L;
 
	private List<InventoryElementGroupDTO> elementsGroup;

	public List<InventoryElementGroupDTO> getElementsGroup() {
		return elementsGroup;
	}

	public void setElementsGroup(List<InventoryElementGroupDTO> elementsGroup) {
		this.elementsGroup = elementsGroup;
	}

	
	
}
