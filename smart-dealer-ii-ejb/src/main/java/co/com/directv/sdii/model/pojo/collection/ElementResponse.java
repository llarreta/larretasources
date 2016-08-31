package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.vo.ElementVO;

public class ElementResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4291649260191326163L;

	private List<Element> elements;
	private List<ElementVO> elementsVO;
	
	public List<Element> getElements() {
		return elements;
	}
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	public List<ElementVO> getElementsVO() {
		return elementsVO;
	}
	public void setElementsVO(List<ElementVO> elementsVO) {
		this.elementsVO = elementsVO;
	}
}
