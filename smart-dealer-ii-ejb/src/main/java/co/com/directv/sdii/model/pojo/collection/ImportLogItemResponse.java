/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.vo.ImportLogItemVO;

/**
 * 
 * Transporta ImportLogItem entre la capa de DAO
 * y de Servicios.
 * 
 * Fecha de Creación: 8/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ImportLogItemResponse extends	CollectionBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2807977198551825976L;
	
	private List<ImportLogItem> importLogItems;
	private List<ImportLogItemVO> importLogItemsVO;
	
	private boolean isSerialized;
	
	public List<ImportLogItem> getImportLogItems() {
		return importLogItems;
	}
	public void setImportLogItems(List<ImportLogItem> importLogItems) {
		this.importLogItems = importLogItems;
	}
	public List<ImportLogItemVO> getImportLogItemsVO() {
		return importLogItemsVO;
	}
	public void setImportLogItemsVO(List<ImportLogItemVO> importLogItemsVO) {
		this.importLogItemsVO = importLogItemsVO;
	}
	public void setSerialized(boolean isSerialized) {
		this.isSerialized = isSerialized;
	}
	public boolean isSerialized() {
		return isSerialized;
	}	
}
