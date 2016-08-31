package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.dto.AdjustmenElementsResponseDTO;
import co.com.directv.sdii.model.pojo.AdjustmentElements;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 22/03/2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AdjustmentElementCollDTO extends CollectionBase implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3958448045072234740L;
	
	private List<AdjustmentElements> adjustmentElements;
	private List<AdjustmenElementsResponseDTO> adjustmentElementsResponse;
	private boolean isAdjust;
	private boolean isSerialized;
	
	public AdjustmentElementCollDTO() {
		super();
	}

	public void setAdjustmentElements(List<AdjustmentElements> adjustmentElements) {
		this.adjustmentElements = adjustmentElements;
	}

	public List<AdjustmentElements> getAdjustmentElements() {
		return adjustmentElements;
	}

	public void setAdjust(boolean isAdjust) {
		this.isAdjust = isAdjust;
	}
	
	public boolean isAdjust() {
		return isAdjust;
	}

	public void setAdjustmentElementsResponse(
			List<AdjustmenElementsResponseDTO> adjustmentElementsResponse) {
		this.adjustmentElementsResponse = adjustmentElementsResponse;
	}

	public List<AdjustmenElementsResponseDTO> getAdjustmentElementsResponse() {
		return adjustmentElementsResponse;
	}

	public void setSerialized(boolean isSerialized) {
		this.isSerialized = isSerialized;
	}

	public boolean isSerialized() {
		return isSerialized;
	}
	
}
