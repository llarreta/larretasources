/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.vo.WoAssignmentVO;

/**
 * @author jvelezmu
 *
 */
public class WOByCustomerQBEPaginationResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 792388504829154207L;
	private List<WoAssignment> collection;
	private List<WoAssignmentVO> collectionVO;
	private Long recordCount;

	public List<WoAssignmentVO> getCollectionVO() {
		return collectionVO;
	}

	public void setCollectionVO(List<WoAssignmentVO> collectionVO) {
		this.collectionVO = collectionVO;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public List<WoAssignment> getCollection() {
		return collection;
	}

	public void setCollection(List<WoAssignment> collection) {
		this.collection = collection;
	}
	
	
}
