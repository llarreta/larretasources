/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * @author jvelezmu
 *
 */
public class WOActiveAndSuspendByCountryIdPaginationResponse extends
		CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2124762529558718249L;
	private List<WorkOrder> collection;
	private List<WorkOrderVO> collectionVO;
	private Long recordCount;

	public List<WorkOrderVO> getCollectionVO() {
		return collectionVO;
	}

	public void setCollectionVO(List<WorkOrderVO> collectionVO) {
		this.collectionVO = collectionVO;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public List<WorkOrder> getCollection() {
		return collection;
	}

	public void setCollection(List<WorkOrder> collection) {
		this.collection = collection;
	}
	
	
}
