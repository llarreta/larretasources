/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO;

/**
 * @author cduarte
 *
 */
public class NotSerializedElementInSelectImportLogToPrintPaginationResponse extends
		CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4305859884015553671L;
	private List<FilterImportLogToPrintDTO> collection;
	private Long recordCount;
	
	public List<FilterImportLogToPrintDTO> getCollection() {
		return collection;
	}
	public void setCollection(List<FilterImportLogToPrintDTO> collection) {
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
