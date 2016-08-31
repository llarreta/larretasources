package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.ElementClass;
import co.com.directv.sdii.model.vo.ElementClassVO;

public class ElementClassResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7525464263281762433L;
	
	private List<ElementClass> elementClass;
	private List<ElementClassVO> elementClassVO;
	
	public List<ElementClass> getElementClass() {
		return elementClass;
	}
	public void setElementClass(List<ElementClass> elementClass) {
		this.elementClass = elementClass;
	}
	public List<ElementClassVO> getElementClassVO() {
		return elementClassVO;
	}
	public void setElementClassVO(List<ElementClassVO> elementClassVO) {
		this.elementClassVO = elementClassVO;
	}
	
	
	

}
