/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;

/**
 * @author jvelezmu
 *
 */
public class ReferenceElementItemsResponse extends	CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3699652096021574264L;
	private List<ReferenceElementItem> referenceElementItems;
	private List<ReferenceElementItemVO> referenceElementItemsVO;
	
	public List<ReferenceElementItem> getReferenceElementItems() {
		return referenceElementItems;
	}
	public void setReferenceElementItems(
			List<ReferenceElementItem> referenceElementItems) {
		this.referenceElementItems = referenceElementItems;
	}
	public List<ReferenceElementItemVO> getReferenceElementItemsVO() {
		return referenceElementItemsVO;
	}
	public void setReferenceElementItemsVO(
			List<ReferenceElementItemVO> referenceElementItemsVO) {
		this.referenceElementItemsVO = referenceElementItemsVO;
	}
	
}
