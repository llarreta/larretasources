package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;


public class LinkSerializedVO implements Serializable {
	
	private static final long serialVersionUID = 8592217920821132714L;
	
	private List<ElementToLinkVO> elementOneList;
	private List<ElementToLinkVO> elementTwoList;
	
	public List<ElementToLinkVO> getElementOneList() {
		return elementOneList;
	}
	public void setElementOneList(List<ElementToLinkVO> elementOneList) {
		this.elementOneList = elementOneList;
	}
	public List<ElementToLinkVO> getElementTwoList() {
		return elementTwoList;
	}
	public void setElementTwoList(List<ElementToLinkVO> elementTwoList) {
		this.elementTwoList = elementTwoList;
	}
		
}
