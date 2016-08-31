package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.vo.SerializedVO;

/**
 * 
 * Data Transfer Object que encapsula la informacion 
 * de un elemento serializado o no serializado.
 * 
 * Fecha de Creación: 1/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ElementDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4853519002183700792L;

	private Long elementTypeId;
	private String elementTypeCode;
	private SerializedVO serializedVO;
	private Long quantity;

	public SerializedVO getSerializedVO() {
		return serializedVO;
	}

	public void setSerializedVO(SerializedVO serializedVO) {
		this.serializedVO = serializedVO;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}	
}
