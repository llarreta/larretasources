/**
 * Creado Oct 25, 2011 4:05:16 PM
 */
package co.com.directv.sdii.model.dto;

import java.util.List;

import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * Clase que encapsula informacion necesaria para la toma de decisión
 * del estado a ser informado a IBS 
 * 
 * Fecha de Creación: Oct 25, 2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ElementMovementDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6047819131045879099L;
	
	private String processCode;
	private MovCmdQueueVO movCmdQueueVO;
	private Long sourceWhID;
	private Long targetWsId;
	private Long elementId;
	private Double quantity;
	private String movTypeCodeE;
	private Long elementTypeId;
	private String movTypeCodeS;
	private boolean serialized;
	private Long documentId;
	private String documentClass;
	private String ibsCustomerCode;
	private String serial;
	private String ird;
	private List<SerializedVO> serializedList;
	private List<NotSerializedVO> notSerializedList;
	private WarehouseVO warehouseSourceReference;
	private boolean reportIbs;
	private ItemStatus itemStatus;
	private Warehouse realSourceWh;
	private Warehouse realTargetWh;
	private Long countryID;
	private String elementTypeCode;
	
	//Variable necesaria para crear ajuste en finalizacion de WO
	private Long userId;

	
	public ElementMovementDTO(){}
	
	
	/**
	 * 
	 * Constructor: Crea DTO para movimientos de elementos no serializados uno a uno
	 * @param sourceWhID
	 * @param targetWsId
	 * @param elementTypeId
	 * @param quantity
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @param reportIbs
	 * @param documentId
	 * @param documentClass
	 * @author jnova
	 */
	public ElementMovementDTO(Long sourceWhID, Long targetWsId, Long elementTypeId,
			Double quantity, String movTypeCodeE, String movTypeCodeS, boolean reportIbs,Long documentId , String documentClass) {
		super();
		this.sourceWhID = sourceWhID;
		this.targetWsId = targetWsId;
		this.quantity = quantity;
		this.movTypeCodeE = movTypeCodeE;
		this.movTypeCodeS = movTypeCodeS;
		this.serialized = false;
		this.reportIbs = reportIbs;
		this.elementTypeId = elementTypeId;
		this.documentId = documentId;
		this.documentClass = documentClass;
	}
	
	
	/**
	 * 
	 * Constructor: Crea DTO  para moviemientos de elementos serializados uno a uno
	 * @param sourceWhID
	 * @param targetWsId
	 * @param elementId
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @param documentId
	 * @param documentClass
	 * @param serial
	 * @param ird
	 * @param reportIbs
	 * @param processCode
	 * @author jnova
	 */
	public ElementMovementDTO(Long sourceWhID, Long targetWsId, Long elementId,
			String movTypeCodeE, String movTypeCodeS, Long documentId,
			String documentClass, String serial, String ird, boolean reportIbs,String processCode,ItemStatus itemStatus) {
		super();
		this.sourceWhID = sourceWhID;
		this.targetWsId = targetWsId;
		this.elementId = elementId;
		this.movTypeCodeE = movTypeCodeE;
		this.movTypeCodeS = movTypeCodeS;
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.serial = serial;
		this.ird = ird;
		this.reportIbs = reportIbs;
		this.serialized = true;
		this.quantity = 1D;
		this.processCode = processCode;
		this.itemStatus = itemStatus;
		this.elementTypeId = null;
	}

	/**
	 * 
	 * Constructor: Crea el dto para movimientos masivos de serializados
	 * @param processCode
	 * @param sourceWhID
	 * @param targetWsId
	 * @param serializedList
	 * @param reportIbs
	 * @param documentId
	 * @param documentClass
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @author
	 */
	public ElementMovementDTO(String processCode, Long sourceWhID,
			Long targetWsId, List<SerializedVO> serializedList,
			boolean reportIbs,Long documentId, String documentClass,
			String movTypeCodeE, String movTypeCodeS,ItemStatus itemStatus) {
		super();
		this.processCode = processCode;
		this.sourceWhID = sourceWhID;
		this.targetWsId = targetWsId;
		this.serializedList = serializedList;
		this.reportIbs = reportIbs;
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.serialized = true;
		this.movTypeCodeE = movTypeCodeE;
		this.movTypeCodeS = movTypeCodeS;
		this.itemStatus = itemStatus;
	}
	
	/**
	 * 
	 * Constructor: Crea el DTO para movimientos masivos de elementos no serializados
	 * @param processCode
	 * @param sourceWhID
	 * @param targetWsId
	 * @param documentId
	 * @param documentClass
	 * @param notSerializedList
	 * @param reportIbs
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @author jnova
	 */
	public ElementMovementDTO(String processCode, Long sourceWhID,
			Long targetWsId, Long documentId, String documentClass,
			List<NotSerializedVO> notSerializedList, boolean reportIbs,
			String movTypeCodeE, String movTypeCodeS) {
		super();
		this.processCode = processCode;
		this.sourceWhID = sourceWhID;
		this.targetWsId = targetWsId;
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.notSerializedList = notSerializedList;
		this.reportIbs = reportIbs;
		this.movTypeCodeE = movTypeCodeE;
		this.movTypeCodeS = movTypeCodeS;
		this.serialized = false;
	}





	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public MovCmdQueueVO getMovCmdQueueVO() {
		return movCmdQueueVO;
	}

	public void setMovCmdQueueVO(MovCmdQueueVO movCmdQueueVO) {
		this.movCmdQueueVO = movCmdQueueVO;
	}

	public Long getSourceWhID() {
		return sourceWhID;
	}

	public void setSourceWhID(Long sourceWhID) {
		this.sourceWhID = sourceWhID;
	}

	public Long getTargetWsId() {
		return targetWsId;
	}

	public void setTargetWsId(Long targetWsId) {
		this.targetWsId = targetWsId;
	}

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getMovTypeCodeE() {
		return movTypeCodeE;
	}

	public void setMovTypeCodeE(String movTypeCodeE) {
		this.movTypeCodeE = movTypeCodeE;
	}

	public Long getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public String getMovTypeCodeS() {
		return movTypeCodeS;
	}

	public void setMovTypeCodeS(String movTypeCodeS) {
		this.movTypeCodeS = movTypeCodeS;
	}

	public boolean isSerialized() {
		return serialized;
	}

	public void setSerialized(boolean serialized) {
		this.serialized = serialized;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentClass() {
		return documentClass;
	}

	public void setDocumentClass(String documentClass) {
		this.documentClass = documentClass;
	}

	public String getIbsCustomerCode() {
		return ibsCustomerCode;
	}

	public void setIbsCustomerCode(String ibsCustomerCode) {
		this.ibsCustomerCode = ibsCustomerCode;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = ( serial == null ? null : serial.trim().toUpperCase() );
	}

	public String getIrd() {
		return ird;
	}

	public void setIrd(String ird) {
		this.ird = ird;
	}

	public List<SerializedVO> getSerializedList() {
		return serializedList;
	}

	public void setSerializedList(List<SerializedVO> serializedList) {
		this.serializedList = serializedList;
	}

	public List<NotSerializedVO> getNotSerializedList() {
		return notSerializedList;
	}

	public void setNotSerializedList(List<NotSerializedVO> notSerializedList) {
		this.notSerializedList = notSerializedList;
	}

	public WarehouseVO getWarehouseSourceReference() {
		return warehouseSourceReference;
	}

	public void setWarehouseSourceReference(WarehouseVO warehouseSourceReference) {
		this.warehouseSourceReference = warehouseSourceReference;
	}

	public boolean isReportIbs() {
		return reportIbs;
	}

	public void setReportIbs(boolean reportIbs) {
		this.reportIbs = reportIbs;
	}

	public ItemStatus getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}


	public Warehouse getRealSourceWh() {
		return realSourceWh;
	}


	public void setRealSourceWh(Warehouse realSourceWh) {
		this.realSourceWh = realSourceWh;
	}


	public Warehouse getRealTargetWh() {
		return realTargetWh;
	}


	public void setRealTargetWh(Warehouse realTargetWh) {
		this.realTargetWh = realTargetWh;
	}


	public Long getCountryID() {
		return countryID;
	}


	public void setCountryID(Long countryID) {
		this.countryID = countryID;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getElementTypeCode() {
		return elementTypeCode;
	}


	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
