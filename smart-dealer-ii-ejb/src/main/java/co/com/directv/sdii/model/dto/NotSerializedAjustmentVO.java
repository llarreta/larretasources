package co.com.directv.sdii.model.dto;

import java.util.List;

import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;

/**
 * Clase utilitaria para envio a la capa de presentacion de la informacion de elementos
 * no serializados junto con sus respectivos movimientos parciales
 * @author jnova
 *
 */
public class NotSerializedAjustmentVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2056739520478154829L;
	
	/**
	 * Elemento no serializado que se encuentra en la bodega
	 */
	private WarehouseElementVO element;
	
	/**
	 * Lista de movimientos parciales asociados al elemento
	 */
	private List<NotSerPartialRetirementVO> elementPartialRetirement;

	public WarehouseElementVO getElement() {
		return element;
	}

	public void setElement(WarehouseElementVO element) {
		this.element = element;
	}

	public List<NotSerPartialRetirementVO> getElementPartialRetirement() {
		return elementPartialRetirement;
	}

	public void setElementPartialRetirement(
			List<NotSerPartialRetirementVO> elementPartialRetirement) {
		this.elementPartialRetirement = elementPartialRetirement;
	}
}
