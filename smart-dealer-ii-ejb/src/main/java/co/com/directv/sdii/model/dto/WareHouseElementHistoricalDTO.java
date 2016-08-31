package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.vo.WarehouseElementVO;

/**
 * 
 * Representa el objeto a interactuar entre el cliente del web service y el server para encapsular
 * y extender el modelo de un WarehouseElement permitiendo identificar tambien la remision y 
 * el registro de importacion al que pertenece el elemento
 * 
 * Fecha de Creacion: Aug 23, 2010
 * @author garciniegas <a href="mailto:garciniegas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WareHouseElementHistoricalDTO extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6075621275834359839L;
	
	private WarehouseElementVO wareHouseElement;
	/** El identificador de la remision asociada **/
	private Long referenceId;
	/** el identificador del registro de importacion asociado **/
	private Long importLogId;
	
	public WareHouseElementHistoricalDTO() {
	}

	public WareHouseElementHistoricalDTO(WarehouseElementVO wareHouseElement ) {
		super();
		this.wareHouseElement = wareHouseElement;
	}

	public WarehouseElementVO getWareHouseElement() {
		return wareHouseElement;
	}

	public void setWareHouseElement(WarehouseElementVO wareHouseElement) {
		this.wareHouseElement = wareHouseElement;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public Long getImportLogId() {
		return importLogId;
	}

	public void setImportLogId(Long importLogId) {
		this.importLogId = importLogId;
	}
	
}
