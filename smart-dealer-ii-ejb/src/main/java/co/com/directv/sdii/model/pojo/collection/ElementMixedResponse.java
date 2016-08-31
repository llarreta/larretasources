package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.ws.model.dto.RefElementItemDTO;

public class ElementMixedResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3454609396115165462L;

	private List<RefElementItemDTO> elementDTOs;

	public List<RefElementItemDTO> getElementDTOs() {
		return elementDTOs;
	}

	public void setElementDTOs(List<RefElementItemDTO> elementDTOs) {
		this.elementDTOs = elementDTOs;
	}
	
}
