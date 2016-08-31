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
public class SerializedWhElementsByCriteriaPaginationResponse extends
		CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2308953496155877139L;
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
