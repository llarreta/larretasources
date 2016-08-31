package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentResponseDTO;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.WarehouseElementVO;

public class AdjustmentElementsResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5834345624597584227L;
	
	private List<AdjustmentResponseDTO> adjustmentDTOElements;

	public List<AdjustmentResponseDTO> getAdjustmentDTOElements() {
		return adjustmentDTOElements;
	}

	public void setAdjustmentDTOElements(List<AdjustmentResponseDTO> adjustmentDTOElements) {
		this.adjustmentDTOElements = adjustmentDTOElements;
	}

}
