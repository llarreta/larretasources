/**
 * Creado Oct 25, 2011 4:05:16 PM
 */
package co.com.directv.sdii.model.dto;


import co.com.directv.sdii.model.pojo.MovCmdStatus;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * DTO encargado de encapsular la informacion de un elemento para realizar un movimiento
 * 
 * Fecha de Creación: 20/12/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class MovementElementDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;

	private WarehouseVO sourceWh;
	
	private WarehouseVO targetWs;
	
	private Serialized serialized;
	
	private NotSerialized notSerialized;
	
	private Double quantity;
	
	private Long documentId;
	
	private String documentClass;
	
	private MovementType movTypeCodeE;
	
	private MovementType movTypeCodeS;
	
	private RecordStatus recordStatusU;
	
	private RecordStatus recordStatusH;
	
	private boolean reportIbs;
	
	private String processCode;
	
	private MovCmdQueueVO movCmdQueueVO;
	
	private Long elementTypeId;
	
	private String elementTypeCode;
	
	private MovCmdStatus movConfigStatusPending;
	
	private MovCmdStatus movConfigStatusNoConfig;
		
	/**
	 * 
	 * Constructor: Constructor vacio
	 * @author waguilera
	 */
	public MovementElementDTO(){}
	
	

	
	
	

	/**
	 * 
	 * Constructor: Para realizar el movimiento de elementos serializados
	 * entre bodegas 
	 * @param user
	 * @param sourceWh
	 * @param targetWs
	 * @param serialized
	 * @param documentId
	 * @param documentClass
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @param recordStatusU
	 * @param recordStatusH
	 * @param reportIbs
	 * @param processCode
	 * @author waguilera
	 */
	public MovementElementDTO(User user, WarehouseVO sourceWh,
			WarehouseVO targetWs, Serialized serialized, Long documentId,
			String documentClass, MovementType movTypeCodeE, MovementType movTypeCodeS,
			RecordStatus recordStatusU, RecordStatus recordStatusH, boolean reportIbs, 
			String processCode,
			MovCmdStatus movConfigStatusPending, MovCmdStatus movConfigStatusNoConfig) {
		super();
		this.user = user;
		this.sourceWh = sourceWh;
		this.targetWs = targetWs;
		this.serialized = serialized;
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.movTypeCodeE = movTypeCodeE;
		this.movTypeCodeS = movTypeCodeS;
		this.recordStatusU = recordStatusU;
		this.recordStatusH = recordStatusH;
		this.reportIbs = reportIbs;
		this.processCode = processCode;
		this.movConfigStatusPending = movConfigStatusPending;
		this.movConfigStatusNoConfig = movConfigStatusNoConfig;
	}
	
	/**
	 * 
	 * Constructor: Para realizar la salida del inventario de elementos 
	 * serializados
	 * @param user
	 * @param sourceWh
	 * @param serialized
	 * @param documentId
	 * @param documentClass
	 * @param movTypeCodeS
	 * @param recordStatusU
	 * @param recordStatusH
	 * @param reportIbs
	 * @param processCode
	 * @author waguilera
	 */
	public MovementElementDTO(User user, WarehouseVO sourceWh,
			Serialized serialized, Long documentId,
			String documentClass, MovementType movTypeCodeS,
			RecordStatus recordStatusU, RecordStatus recordStatusH, boolean reportIbs, 
			String processCode,
			MovCmdStatus movConfigStatusPending, MovCmdStatus movConfigStatusNoConfig) {
		super();
		this.user = user;
		this.sourceWh = sourceWh;
		this.serialized = serialized;
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.movTypeCodeS = movTypeCodeS;
		this.recordStatusU = recordStatusU;
		this.recordStatusH = recordStatusH;
		this.reportIbs = reportIbs;
		this.processCode = processCode;
		this.movConfigStatusPending = movConfigStatusPending;
		this.movConfigStatusNoConfig = movConfigStatusNoConfig;
	}
	
	/**
	 * 
	 * Constructor: Para realizar el movimiento de elementos no serializados
	 * entre bodegas 
	 * @param user
	 * @param sourceWh
	 * @param targetWs
	 * @param notSerialized
	 * @param elementTypeId
	 * @param elementTypeCode
	 * @param documentId
	 * @param documentClass
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @param recordStatusU
	 * @param recordStatusH
	 * @param quantity
	 * @author waguilera
	 */
	public MovementElementDTO(User user, WarehouseVO sourceWh,
			WarehouseVO targetWs, NotSerialized notSerialized, Long elementTypeId, String elementTypeCode, Long documentId,
			String documentClass, MovementType movTypeCodeE, MovementType movTypeCodeS,
			RecordStatus recordStatusU, RecordStatus recordStatusH,Double quantity,
			MovCmdStatus movConfigStatusPending, MovCmdStatus movConfigStatusNoConfig) {
		super();
		this.user = user;
		this.sourceWh = sourceWh;
		this.targetWs = targetWs;
		this.notSerialized = notSerialized;
		this.elementTypeId = elementTypeId;
		this.elementTypeCode = elementTypeCode;
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.movTypeCodeE = movTypeCodeE;
		this.movTypeCodeS = movTypeCodeS;
		this.recordStatusU = recordStatusU;
		this.recordStatusH = recordStatusH;
		this.quantity = quantity;
		this.movConfigStatusPending = movConfigStatusPending;
		this.movConfigStatusNoConfig = movConfigStatusNoConfig;
	}
	
	/**
	 * 
	 * Constructor: Para realizar la salida de elementos no serializados
	 * de una bodega
	 * @param user
	 * @param sourceWh
	 * @param notSerialized
	 * @param elementTypeId
	 * @param elementTypeCode
	 * @param documentId
	 * @param documentClass
	 * @param movTypeCodeS
	 * @param recordStatusU
	 * @param recordStatusH
	 * @param quantity
	 * @author waguilera
	 */
	public MovementElementDTO(User user, WarehouseVO sourceWh,
			NotSerialized notSerialized, Long elementTypeId, String elementTypeCode, 
			Long documentId, String documentClass, MovementType movTypeCodeS, 
			RecordStatus recordStatusU, RecordStatus recordStatusH,Double quantity,
			MovCmdStatus movConfigStatusPending, MovCmdStatus movConfigStatusNoConfig) {
		super();
		this.user = user;
		this.sourceWh = sourceWh;
		this.notSerialized = notSerialized;
		this.elementTypeId = elementTypeId;
		this.elementTypeCode = elementTypeCode;
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.movTypeCodeS = movTypeCodeS;
		this.recordStatusU = recordStatusU;
		this.recordStatusH = recordStatusH;
		this.quantity = quantity;
		this.movConfigStatusPending = movConfigStatusPending;
		this.movConfigStatusNoConfig = movConfigStatusNoConfig;
	}
	
	/**
	 * 
	 * Constructor: :Para realizar el movimiento de elementos serializados
	 * hacia una bodega
	 * @param user
	 * @param targetWs
	 * @param elementId
	 * @param documentId
	 * @param documentClass
	 * @param movTypeCodeE
	 * @param recordStatusU
	 * @param recordStatusH
	 * @param reportIbs
	 * @param processCode
	 * @author waguilera
	 */
	public MovementElementDTO(User user, WarehouseVO targetWs, Long elementId,
			Long documentId, String documentClass, MovementType movTypeCodeE,
			RecordStatus recordStatusU, RecordStatus recordStatusH,
			boolean reportIbs, String processCode, 
			MovCmdStatus movConfigStatusPending, MovCmdStatus movConfigStatusNoConfig) {
		super();
		this.user = user;
		this.targetWs = targetWs;
		this.serialized = new Serialized();
		this.serialized.setElementId(elementId);
		this.documentId = documentId;
		this.documentClass = documentClass;
		this.movTypeCodeE = movTypeCodeE;
		this.recordStatusU = recordStatusU;
		this.recordStatusH = recordStatusH;
		this.reportIbs = reportIbs;
		this.processCode = processCode;
		this.movConfigStatusPending = movConfigStatusPending;
		this.movConfigStatusNoConfig = movConfigStatusNoConfig;
	}
	
	

	public WarehouseVO getSourceWh() {
		return sourceWh;
	}

	public void setSourceWh(WarehouseVO sourceWh) {
		this.sourceWh = sourceWh;
	}

	public WarehouseVO getTargetWs() {
		return targetWs;
	}

	public void setTargetWs(WarehouseVO targetWs) {
		this.targetWs = targetWs;
	}

	public Serialized getSerialized() {
		return serialized;
	}

	public void setSerialized(Serialized serialized) {
		this.serialized = serialized;
	}

	public NotSerialized getNotSerialized() {
		return notSerialized;
	}

	public void setNotSerialized(NotSerialized notSerialized) {
		this.notSerialized = notSerialized;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	public MovementType getMovTypeCodeE() {
		return movTypeCodeE;
	}

	public void setMovTypeCodeE(MovementType movTypeCodeE) {
		this.movTypeCodeE = movTypeCodeE;
	}

	public MovementType getMovTypeCodeS() {
		return movTypeCodeS;
	}

	public void setMovTypeCodeS(MovementType movTypeCodeS) {
		this.movTypeCodeS = movTypeCodeS;
	}

	public boolean isReportIbs() {
		return reportIbs;
	}

	public void setReportIbs(boolean reportIbs) {
		this.reportIbs = reportIbs;
	}

	public RecordStatus getRecordStatusU() {
		return recordStatusU;
	}

	public void setRecordStatusU(RecordStatus recordStatusU) {
		this.recordStatusU = recordStatusU;
	}

	public RecordStatus getRecordStatusH() {
		return recordStatusH;
	}

	public void setRecordStatusH(RecordStatus recordStatusH) {
		this.recordStatusH = recordStatusH;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}

	public MovCmdStatus getMovConfigStatusPending() {
		return movConfigStatusPending;
	}

	public void setMovConfigStatusPending(MovCmdStatus movConfigStatusPending) {
		this.movConfigStatusPending = movConfigStatusPending;
	}

	public MovCmdStatus getMovConfigStatusNoConfig() {
		return movConfigStatusNoConfig;
	}

	public void setMovConfigStatusNoConfig(MovCmdStatus movConfigStatusNoConfig) {
		this.movConfigStatusNoConfig = movConfigStatusNoConfig;
	}	
	

}
