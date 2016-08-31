/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.vo.SerializedVO;

/**
 * @author jvelezmu
 *
 */
public class SerializedElementInReferencePaginationResponse extends
		CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3257401714389440203L;
	private List<Serialized> collection;
	private List<SerializedVO> collectionVO;
	private Long recordCount;

	public List<SerializedVO> getCollectionVO() {
		return collectionVO;
	}

	public void setCollectionVO(List<SerializedVO> collectionVO) {
		this.collectionVO = collectionVO;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public List<Serialized> getCollection() {
		return collection;
	}

	public void setCollection(List<Serialized> collection) {
		this.collection = collection;
	}
	
	
}
