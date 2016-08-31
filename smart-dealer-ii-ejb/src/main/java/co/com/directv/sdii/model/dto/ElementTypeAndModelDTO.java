package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.vo.ElementModelVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;

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
public class ElementTypeAndModelDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4853519002183700792L;

	private List<ElementModelVO> elementModelVO=null;
	private List<ElementTypeVO> elementTypeVO=null;
	/**
	 * @return the elementModelVO
	 */
	public List<ElementModelVO> getElementModelVO() {
		return elementModelVO;
	}
	/**
	 * @param elementModelVO the elementModelVO to set
	 */
	public void setElementModelVO(List<ElementModelVO> elementModelVO) {
		this.elementModelVO = elementModelVO;
	}
	/**
	 * @return the elementTypeVO
	 */
	public List<ElementTypeVO> getElementTypeVO() {
		return elementTypeVO;
	}
	/**
	 * @param elementTypeVO the elementTypeVO to set
	 */
	public void setElementTypeVO(List<ElementTypeVO> elementTypeVO) {
		this.elementTypeVO = elementTypeVO;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
		
}
