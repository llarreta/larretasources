/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.dto.NotSerRefElementItemDTO;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.vo.NotSerializedVO;

/**
 * @author jvelezmu
 *
 */
public class NotSerializedElementInReferencePaginationResponse extends
		CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4305859884015553671L;
	private List<NotSerialized> collection;
	private List<NotSerializedVO> collectionVO;
	private Long recordCount;
	
	private List<NotSerRefElementItemDTO> collectionObjects;
	
	 
	public List<NotSerializedVO> getCollectionVO() {
		return collectionVO;
	}

	public void setCollectionVO(List<NotSerializedVO> collectionVO) {
		this.collectionVO = collectionVO;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public List<NotSerialized> getCollection() {
		return collection;
	}

	public void setCollection(List<NotSerialized> collection) {
		this.collection = collection;
	}

	public List<NotSerRefElementItemDTO> getCollectionObjects() {
		return collectionObjects;
	}

	public void setCollectionObjects(List<NotSerRefElementItemDTO> collectionObjects) {
		this.collectionObjects = collectionObjects;
	}

	
	
	
}
