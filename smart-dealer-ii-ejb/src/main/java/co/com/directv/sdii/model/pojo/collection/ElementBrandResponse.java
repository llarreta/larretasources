package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.ElementBrand;
import co.com.directv.sdii.model.vo.ElementBrandVO;

public class ElementBrandResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4143223425611921728L;
	
	private List<ElementBrand> elementBrand;
	private List<ElementBrandVO> elementBrandVO;
	
	public List<ElementBrand> getElementBrand() {
		return elementBrand;
	}
	public void setElementBrand(List<ElementBrand> elementBrand) {
		this.elementBrand = elementBrand;
	}
	public List<ElementBrandVO> getElementBrandVO() {
		return elementBrandVO;
	}
	public void setElementBrandVO(List<ElementBrandVO> elementBrandVO) {
		this.elementBrandVO = elementBrandVO;
	}
	
	

}
