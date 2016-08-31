/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.WarehouseElementVO;

/**
 * @author jvelezmu
 *
 */
public class WhElementsByCriteriaPaginationResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5514743724781261986L;
	private List<WarehouseElement> collection;
	private List<WarehouseElementVO> collectionVO;
	private Long recordCount;

	public List<WarehouseElementVO> getCollectionVO() {
		return collectionVO;
	}

	public void setCollectionVO(List<WarehouseElementVO> collectionVO) {
		this.collectionVO = collectionVO;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public List<WarehouseElement> getCollection() {
		return collection;
	}

	public void setCollection(List<WarehouseElement> collection) {
		this.collection = collection;
	}
	
	
}
