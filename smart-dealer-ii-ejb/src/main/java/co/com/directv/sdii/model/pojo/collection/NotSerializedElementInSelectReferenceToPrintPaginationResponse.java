/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.dto.SelectReferenceToPrintDTO;

/**
 * @author cduarte
 *
 */
public class NotSerializedElementInSelectReferenceToPrintPaginationResponse extends
		CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4305859884015553671L;
	private List<SelectReferenceToPrintDTO> collection;
	private Long recordCount;
	
	public List<SelectReferenceToPrintDTO> getCollection() {
		return collection;
	}
	public void setCollection(List<SelectReferenceToPrintDTO> collection) {
		this.collection = collection;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
		
}
