/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.RefQuantityControlItem;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;

/**
 * @author jvelezmu
 *
 */
public class RefQuantityControlItemsResponse extends	CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3699652096021574264L;
	private List<RefQuantityControlItem> refQuantityControlItems;
	private List<RefQuantityControlItemVO> refQuantityControlItemsVO;
	
	
	public List<RefQuantityControlItem> getRefQuantityControlItems() {
		return refQuantityControlItems;
	}
	public void setRefQuantityControlItems(
			List<RefQuantityControlItem> refQuantityControlItems) {
		this.refQuantityControlItems = refQuantityControlItems;
	}
	public List<RefQuantityControlItemVO> getRefQuantityControlItemsVO() {
		return refQuantityControlItemsVO;
	}
	public void setRefQuantityControlItemsVO(
			List<RefQuantityControlItemVO> refQuantityControlItemsVO) {
		this.refQuantityControlItemsVO = refQuantityControlItemsVO;
	}
	
	
	

	
}
