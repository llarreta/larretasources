package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.vo.ElementTypeVO;

public class ElementTypeResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -411535626632758775L;
	
	
	private List<ElementType> elementType;
	private List<ElementTypeVO> elementTypeVO;
	
	public List<ElementType> getElementType() {
		return elementType;
	}
	public void setElementType(List<ElementType> elementType) {
		this.elementType = elementType;
	}
	public List<ElementTypeVO> getElementTypeVO() {
		return elementTypeVO;
	}
	public void setElementTypeVO(List<ElementTypeVO> elementTypeVO) {
		this.elementTypeVO = elementTypeVO;
	}
	
	
	
	

}
