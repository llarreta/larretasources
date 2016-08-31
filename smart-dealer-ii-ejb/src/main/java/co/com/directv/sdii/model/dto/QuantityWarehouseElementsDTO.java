package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * 
 * 
 * Fecha de Creación: 8/09/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class QuantityWarehouseElementsDTO implements Serializable {

	private static final long serialVersionUID = -6075621275834359839L;
	
	/**
	 * Constructor: permite crear un objeto vacio
	 * @author
	 */
	public QuantityWarehouseElementsDTO() {
		super();
	}
	
	/**
	 * IdCompañía 
	 */
	private Long dealerIdCompany;//Dealer
	
	/**
	 * Compañía 
	 */
	private String dealerNameCompany;//Dealer
	
	/**
	 * IdSucursal
	*/
	private String dealerNameBranch;//Dealer
	
	/**
	 * Sucursal
	*/
	private Long dealerIdBranch;//Dealer
	
	/**
	 * IdCuadrilla
	*/
	private Long crewId;//EmployeeCrew
	
	/**
	 * Cuadrilla
	*/
	private String isResponsibleOut;//EmployeeCrew
	
	/**
	 * Tipo de Bodega
	*/
	private Long whTypeId;
	private String whTypeCode;
	private String whTypeName;
	
	/**
	 * Ubicación (de Bodega)
	*/
	private Long whId;
	private String whCode;
	private String whName;
	
	/**
	 * Modelo
	*/
	private Long elementModelId;
	private String modelName;
	private String modelCode;
	
	/**
	 * Tipo Elemento
	*/
	private Long typeElementId;
	private String typeElementCode;
	private String typeElementName;
	
	/**
	 * Cantidad Inicial
	*/
	private Double initialQuantity;
	
	/**
	 * Entradas
	*/
	private Double inQuantity;
	
	/**
	 * Salidas
	*/
	private Double outQuantity;
	
	/**
	 * Saldo Actual
	*/
	private Double actualQuantity;
	
	/**
	 * Serial
	*/
	private Long elementId;
	private String serialCode;
	
	/**
	 * RID
	*/
	private String rid;
	
	/**
	 * Serial Vinculado
	*/
	private String serialCodeLinked;
	
	/**
	 * Tipo de movimiento
	*/
	private Long movTypeId;
	private String movClass;
	
	/**
	 * Causal de movimiento
	*/
	private String movTypeName;
	
	/**
	 * Fecha y Hora Inicial
	*/
	private Date movementDateIn = null;
	
	/**
	 * Fecha y Hora Inicial
	*/
	private Date movementDateOut = null;
	
	/**
	 * min id del warehouseElement para consultar el valor inicial
	*/
	private Long minWarehouseElementId = null;
	
	/**
	 * min id del warehouseElement para consultar el valor actual
	*/
	private Long maxWarehouseElementId = null;

	/**
	 * Tiempo en dias del elemento en la bodega
	*/
	private String age;
	
	/**
	 *Tipo numero documento
	 */
	private Long documentId;
	private String documentName;
	private Long documentClassId;
	private String documentClassName;

	/**
	 * Constructor: Permite crear una instancia de la clase QuantityWarehouseElementsDTO seteando todos los atributos
	 * @param dealerIdCompany
	 * @param dealerNameCompany
	 * @param dealerNameBranch
	 * @param dealerIdBranch
	 * @param crewId
	 * @param isResponsibleOut
	 * @param whTypeId
	 * @param whTypeCode
	 * @param whTypeName
	 * @param whId
	 * @param whCode
	 * @param elementModelId
	 * @param modelName
	 * @param modelCode
	 * @param typeElementId
	 * @param typeElementCode
	 * @param typeElementName
	 * @param initialQuantity
	 * @param inQuantity
	 * @param outQuantity
	 * @param actualQuantity
	 * @param elementId
	 * @param serialCode
	 * @param rid
	 * @param serialCodeLinked
	 * @param movTypeId
	 * @param movClass
	 * @param movTypeName
	 * @param movementDateIn
	 * @param movementDateOut
	 * @param minWarehouseElementId
	 * @param maxWarehouseElementId
	 * @author
	 */
	public QuantityWarehouseElementsDTO(Long dealerIdCompany,
			String dealerNameCompany, String dealerNameBranch,
			Long dealerIdBranch, Long crewId, String isResponsibleOut,
			Long whTypeId, String whTypeCode, String whTypeName, Long whId,
			String whCode, Long elementModelId, String modelName,
			String modelCode, Long typeElementId, String typeElementCode,
			String typeElementName, Double initialQuantity, Double inQuantity,
			Double outQuantity, Double actualQuantity, Long elementId,
			String serialCode, String rid, String serialCodeLinked,
			Long movTypeId, String movClass, String movTypeName,
			Date movementDateIn, Date movementDateOut,
			Long minWarehouseElementId, Long maxWarehouseElementId) {
		super();
		this.dealerIdCompany = dealerIdCompany;
		this.dealerNameCompany = dealerNameCompany;
		this.dealerNameBranch = dealerNameBranch;
		this.dealerIdBranch = dealerIdBranch;
		this.crewId = crewId;
		this.isResponsibleOut = isResponsibleOut;
		this.whTypeId = whTypeId;
		this.whTypeCode = whTypeCode;
		this.whTypeName = whTypeName;
		this.whId = whId;
		this.whCode = whCode;
		this.elementModelId = elementModelId;
		this.modelName = modelName;
		this.modelCode = modelCode;
		this.typeElementId = typeElementId;
		this.typeElementCode = typeElementCode;
		this.typeElementName = typeElementName;
		this.initialQuantity = initialQuantity;
		this.inQuantity = inQuantity;
		this.outQuantity = outQuantity;
		this.actualQuantity = actualQuantity;
		this.elementId = elementId;
		this.serialCode = serialCode;
		this.rid = rid;
		this.serialCodeLinked = serialCodeLinked;
		this.movTypeId = movTypeId;
		this.movClass = movClass;
		this.movTypeName = movTypeName;
		this.movementDateIn = movementDateIn;
		this.movementDateOut = movementDateOut;
		this.minWarehouseElementId = minWarehouseElementId;
		this.maxWarehouseElementId = maxWarehouseElementId;
	}

	/**
	 * Constructor: Permite crear una instancia de la clase QuantityWarehouseElementsDTO seteando los atributos 
	 * 				de la salida1 del CU INV – 135
	 * @param dealerIdCompany
	 * @param dealerNameCompany
	 * @param dealerNameBranch
	 * @param dealerIdBranch
	 * @param crewId
	 * @param isResponsibleOut
	 * @param whId
	 * @param whTypeId
	 * @param whTypeCode
	 * @param whTypeName
	 * @param whCode
	 * @param elementModelId
	 * @param modelName
	 * @param modelCode
	 * @param typeElementId
	 * @param typeElementCode
	 * @param typeElementName
	 * @param actualQuantity
	 * @param elementId
	 * @param serialCode
	 * @param rid
	 * @param serialCodeLinked
	 * @param movTypeId
	 * @param movClass
	 * @param movTypeName
	 * @param movementDateIn
	 * @param movementDateOut
	 * @param minWarehouseElementId
	 * @param maxWarehouseElementId
	 * @param age
	 * @author
	 */
	public QuantityWarehouseElementsDTO(Long dealerIdCompany,
			String dealerNameCompany, String dealerNameBranch,
			Long dealerIdBranch, Long crewId, String isResponsibleOut,
			Long whId, Long whTypeId, String whTypeCode, String whTypeName, 
			String whCode, Long elementModelId, String modelName,
			String modelCode, Long typeElementId, String typeElementCode,
			String typeElementName, Double actualQuantity, Long elementId,
			String serialCode, String rid, String serialCodeLinked,
			Long movTypeId, String movClass, String movTypeName,
			Date movementDateIn, Date movementDateOut,
			Long minWarehouseElementId, Long maxWarehouseElementId, String age) {
		super();
		this.dealerIdCompany = dealerIdCompany;
		this.dealerNameCompany = dealerNameCompany;
		this.dealerNameBranch = dealerNameBranch;
		this.dealerIdBranch = dealerIdBranch;
		this.crewId = crewId;
		this.isResponsibleOut = isResponsibleOut;
		this.whTypeId = whTypeId;
		this.whTypeCode = whTypeCode;
		this.whTypeName = whTypeName;
		this.whId = whId;
		this.whCode = whCode;
		this.elementModelId = elementModelId;
		this.modelName = modelName;
		this.modelCode = modelCode;
		this.typeElementId = typeElementId;
		this.typeElementCode = typeElementCode;
		this.typeElementName = typeElementName;
		this.actualQuantity = actualQuantity;
		this.elementId = elementId;
		this.serialCode = serialCode;
		this.rid = rid;
		this.serialCodeLinked = serialCodeLinked;
		this.movTypeId = movTypeId;
		this.movClass = movClass;
		this.movTypeName = movTypeName;
		this.movementDateIn = movementDateIn;
		this.movementDateOut = movementDateOut;
		this.minWarehouseElementId = minWarehouseElementId;
		this.maxWarehouseElementId = maxWarehouseElementId;
		this.age = age;
	}


	/**
	 * Constructor: Permite crear una instancia de la clase QuantityWarehouseElementsDTO seteando los atributos 
	 * 				de la salida1 del CU INV – 52 Consultar movimiento de bodega _ compañía por rango de fechas
	 * @param dealerIdCompany
	 * @param dealerNameCompany
	 * @param dealerNameBranch
	 * @param dealerIdBranch
	 * @param crewId
	 * @param isResponsibleOut
	 * @param whId
	 * @param whTypeId
	 * @param whTypeCode
	 * @param whTypeName
	 * @param whCode
	 * @param elementModelId
	 * @param modelName
	 * @param modelCode
	 * @param typeElementId
	 * @param typeElementCode
	 * @param typeElementName
	 * @param initialQuantity
	 * @param inQuantity
	 * @param outQuantity
	 * @param actualQuantity
	 * @author
	 */
	public QuantityWarehouseElementsDTO(Long dealerIdCompany,
			String dealerNameCompany, String dealerNameBranch,
			Long dealerIdBranch, Long crewId, String isResponsibleOut,
			Long whId,Long whTypeId,String whTypeCode, String whTypeName, String whCode,
			Long elementModelId, String modelName, String modelCode,
			Long typeElementId, String typeElementCode,
			String typeElementName, Double initialQuantity, Double inQuantity,
			Double outQuantity, Long minWarehouseElementId,
			Long maxWarehouseElementId,Date movementDateIn, Date movementDateOut) {
		super();
		this.dealerIdCompany = dealerIdCompany;
		this.dealerNameCompany = dealerNameCompany;
		this.dealerNameBranch = dealerNameBranch;
		this.dealerIdBranch = dealerIdBranch;
		this.crewId = crewId;
		this.isResponsibleOut = isResponsibleOut;
		this.whId = whId;
		this.whTypeId=whTypeId;
		this.whTypeCode = whTypeCode;
		this.whTypeName = whTypeName;
		this.whCode = whCode;
		this.elementModelId = elementModelId;
		this.modelName = modelName;
		this.modelCode = modelCode;
		this.typeElementId = typeElementId;
		this.typeElementCode = typeElementCode;
		this.typeElementName = typeElementName;
		this.initialQuantity=initialQuantity;
		this.inQuantity = inQuantity;
		this.outQuantity = outQuantity;
		this.minWarehouseElementId=minWarehouseElementId;
		this.maxWarehouseElementId=maxWarehouseElementId;
		this.movementDateIn=movementDateIn;
		this.movementDateOut=movementDateOut;
	}

	/**
	 * Constructor: Permite crear una instancia de la clase QuantityWarehouseElementsDTO seteando los atributos 
	 * 				de la salida2 del CU INV – 52 Consultar movimiento de bodega _ compañía por rango de fechas
	 * @param dealerIdCompany
	 * @param dealerNameCompany
	 * @param dealerNameBranch
	 * @param dealerIdBranch
	 * @param crewId
	 * @param isResponsibleOut
	 * @param whId
	 * @param whTypeId
	 * @param whTypeCode
	 * @param whTypeName
	 * @param whCode
	 * @param elementModelId
	 * @param modelName
	 * @param modelCode
	 * @param typeElementId
	 * @param typeElementCode
	 * @param typeElementName
	 * @param actualQuantity
	 * @param elementId
	 * @param serialCode
	 * @param rid
	 * @param serialCodeLinked
	 * @param movTypeId
	 * @param movClass
	 * @param movTypeName
	 * @param movementDateIn
	 * @param movementDateOut
	 * @param documentName
	 * @author
	 */
	public QuantityWarehouseElementsDTO(Long dealerIdCompany,
			String dealerNameCompany, String dealerNameBranch,
			Long dealerIdBranch, Long crewId, String isResponsibleOut,
			Long whId,Long whTypeId, String whTypeCode, String whTypeName, String whCode,
			Long elementModelId, String modelName, String modelCode,
			Long typeElementId, String typeElementCode,
			String typeElementName, Double actualQuantity, Long elementId,
			String serialCode, String rid, String serialCodeLinked,
			Long movTypeId, String movClass, String movTypeName,
			Date movementDateIn, Date movementDateOut,Long minWarehouseElementId,
			Long maxWarehouseElementId,Long documentId,Long documentClassId,String documentClassName, String documentName) {
		super();
		this.dealerIdCompany = dealerIdCompany;
		this.dealerNameCompany = dealerNameCompany;
		this.dealerNameBranch = dealerNameBranch;
		this.dealerIdBranch = dealerIdBranch;
		this.crewId = crewId;
		this.isResponsibleOut = isResponsibleOut;
		this.whId = whId;
		this.whTypeId=whTypeId;
		this.whTypeCode = whTypeCode;
		this.whTypeName = whTypeName;
		this.whCode = whCode;
		this.elementModelId = elementModelId;
		this.modelName = modelName;
		this.modelCode = modelCode;
		this.typeElementId = typeElementId;
		this.typeElementCode = typeElementCode;
		this.typeElementName = typeElementName;
		this.actualQuantity = actualQuantity;
		this.elementId = elementId;
		this.serialCode = serialCode;
		this.rid = rid;
		this.serialCodeLinked = serialCodeLinked;
		this.movTypeId = movTypeId;
		this.movClass = movClass;
		this.movTypeName = movTypeName;
		this.movementDateIn = movementDateIn;
		this.movementDateOut = movementDateOut;
		this.minWarehouseElementId=minWarehouseElementId;
		this.maxWarehouseElementId=maxWarehouseElementId;
		this.documentId=documentId;
		this.documentClassId=documentClassId;
		this.documentClassName=documentClassName;
		this.documentName=documentName;
	}
	
	
	
	

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return the dealerIdCompany
	 */
	public Long getDealerIdCompany() {
		return dealerIdCompany;
	}

	/**
	 * @param dealerIdCompany the dealerIdCompany to set
	 */
	public void setDealerIdCompany(Long dealerIdCompany) {
		this.dealerIdCompany = dealerIdCompany;
	}

	/**
	 * @return the dealerNameCompany
	 */
	public String getDealerNameCompany() {
		return dealerNameCompany;
	}

	/**
	 * @param dealerNameCompany the dealerNameCompany to set
	 */
	public void setDealerNameCompany(String dealerNameCompany) {
		this.dealerNameCompany = dealerNameCompany;
	}

	/**
	 * @return the dealerNameBranch
	 */
	public String getDealerNameBranch() {
		return dealerNameBranch;
	}

	/**
	 * @param dealerNameBranch the dealerNameBranch to set
	 */
	public void setDealerNameBranch(String dealerNameBranch) {
		this.dealerNameBranch = dealerNameBranch;
	}

	/**
	 * @return the dealerIdBranch
	 */
	public Long getDealerIdBranch() {
		return dealerIdBranch;
	}

	/**
	 * @param dealerIdBranch the dealerIdBranch to set
	 */
	public void setDealerIdBranch(Long dealerIdBranch) {
		this.dealerIdBranch = dealerIdBranch;
	}

	/**
	 * @return the crewId
	 */
	public Long getCrewId() {
		return crewId;
	}

	/**
	 * @param crewId the crewId to set
	 */
	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}

	/**
	 * @return the isResponsibleOut
	 */
	public String getIsResponsibleOut() {
		return isResponsibleOut;
	}

	/**
	 * @param isResponsibleOut the isResponsibleOut to set
	 */
	public void setIsResponsibleOut(String isResponsibleOut) {
		this.isResponsibleOut = isResponsibleOut;
	}

	/**
	 * @return the whTypeId
	 */
	public Long getWhTypeId() {
		return whTypeId;
	}

	/**
	 * @param whTypeId the whTypeId to set
	 */
	public void setWhTypeId(Long whTypeId) {
		this.whTypeId = whTypeId;
	}

	/**
	 * @return the whTypeCode
	 */
	public String getWhTypeCode() {
		return whTypeCode;
	}

	/**
	 * @param whTypeCode the whTypeCode to set
	 */
	public void setWhTypeCode(String whTypeCode) {
		this.whTypeCode = whTypeCode;
	}

	/**
	 * @return the whTypeName
	 */
	public String getWhTypeName() {
		return whTypeName;
	}

	/**
	 * @param whTypeName the whTypeName to set
	 */
	public void setWhTypeName(String whTypeName) {
		this.whTypeName = whTypeName;
	}

	/**
	 * @return the whId
	 */
	public Long getWhId() {
		return whId;
	}

	/**
	 * @param whId the whId to set
	 */
	public void setWhId(Long whId) {
		this.whId = whId;
	}

	/**
	 * @return the whCode
	 */
	public String getWhCode() {
		return whCode;
	}

	/**
	 * @param whCode the whCode to set
	 */
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	/**
	 * @return the elementModelId
	 */
	public Long getElementModelId() {
		return elementModelId;
	}

	/**
	 * @param elementModelId the elementModelId to set
	 */
	public void setElementModelId(Long elementModelId) {
		this.elementModelId = elementModelId;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the modelCode
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * @param modelCode the modelCode to set
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * @return the typeElementId
	 */
	public Long getTypeElementId() {
		return typeElementId;
	}

	/**
	 * @param typeElementId the typeElementId to set
	 */
	public void setTypeElementId(Long typeElementId) {
		this.typeElementId = typeElementId;
	}

	/**
	 * @return the typeElementCode
	 */
	public String getTypeElementCode() {
		return typeElementCode;
	}

	/**
	 * @param typeElementCode the typeElementCode to set
	 */
	public void setTypeElementCode(String typeElementCode) {
		this.typeElementCode = typeElementCode;
	}

	/**
	 * @return the typeElementName
	 */
	public String getTypeElementName() {
		return typeElementName;
	}

	/**
	 * @param typeElementName the typeElementName to set
	 */
	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}

	/**
	 * @return the initialQuantity
	 */
	public Double getInitialQuantity() {
		return initialQuantity;
	}

	/**
	 * @param initialQuantity the initialQuantity to set
	 */
	public void setInitialQuantity(Double initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	/**
	 * @return the inQuantity
	 */
	public Double getInQuantity() {
		return inQuantity;
	}

	/**
	 * @param inQuantity the inQuantity to set
	 */
	public void setInQuantity(Double inQuantity) {
		this.inQuantity = inQuantity;
	}

	/**
	 * @return the outQuantity
	 */
	public Double getOutQuantity() {
		return outQuantity;
	}

	/**
	 * @param outQuantity the outQuantity to set
	 */
	public void setOutQuantity(Double outQuantity) {
		this.outQuantity = outQuantity;
	}

	/**
	 * @return the actualQuantity
	 */
	public Double getActualQuantity() {
		return actualQuantity;
	}

	/**
	 * @param actualQuantity the actualQuantity to set
	 */
	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	/**
	 * @return the elementId
	 */
	public Long getElementId() {
		return elementId;
	}

	/**
	 * @param elementId the elementId to set
	 */
	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	/**
	 * @return the serialCode
	 */
	public String getSerialCode() {
		return serialCode;
	}

	/**
	 * @param serialCode the serialCode to set
	 */
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	/**
	 * @return the rid
	 */
	public String getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(String rid) {
		this.rid = rid;
	}

	/**
	 * @return the serialCodeLinked
	 */
	public String getSerialCodeLinked() {
		return serialCodeLinked;
	}

	/**
	 * @param serialCodeLinked the serialCodeLinked to set
	 */
	public void setSerialCodeLinked(String serialCodeLinked) {
		this.serialCodeLinked = serialCodeLinked;
	}

	/**
	 * @return the movTypeId
	 */
	public Long getMovTypeId() {
		return movTypeId;
	}

	/**
	 * @param movTypeId the movTypeId to set
	 */
	public void setMovTypeId(Long movTypeId) {
		this.movTypeId = movTypeId;
	}

	/**
	 * @return the movClass
	 */
	public String getMovClass() {
		return movClass;
	}

	/**
	 * @param movClass the movClass to set
	 */
	public void setMovClass(String movClass) {
		this.movClass = movClass;
	}

	/**
	 * @return the movTypeName
	 */
	public String getMovTypeName() {
		return movTypeName;
	}

	/**
	 * @param movTypeName the movTypeName to set
	 */
	public void setMovTypeName(String movTypeName) {
		this.movTypeName = movTypeName;
	}

	/**
	 * @return the movementDateIn
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getMovementDateIn() {
		return movementDateIn;
	}

	/**
	 * @param movementDateIn the movementDateIn to set
	 */
	public void setMovementDateIn(Date movementDateIn) {
		this.movementDateIn = movementDateIn;
	}

	/**
	 * @return the movementDateOut
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getMovementDateOut() {
		return movementDateOut;
	}

	/**
	 * @param movementDateOut the movementDateOut to set
	 */
	public void setMovementDateOut(Date movementDateOut) {
		this.movementDateOut = movementDateOut;
	}

	/**
	 * @return the minWarehouseElementId
	 */
	public Long getMinWarehouseElementId() {
		return minWarehouseElementId;
	}

	/**
	 * @param minWarehouseElementId the minWarehouseElementId to set
	 */
	public void setMinWarehouseElementId(Long minWarehouseElementId) {
		this.minWarehouseElementId = minWarehouseElementId;
	}

	/**
	 * @return the maxWarehouseElementId
	 */
	public Long getMaxWarehouseElementId() {
		return maxWarehouseElementId;
	}

	/**
	 * @param maxWarehouseElementId the maxWarehouseElementId to set
	 */
	public void setMaxWarehouseElementId(Long maxWarehouseElementId) {
		this.maxWarehouseElementId = maxWarehouseElementId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	/**
	 * @return the documentId
	 */
	public Long getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	
	/**
	 * @return the documentClassName
	 */
	public String getDocumentClassName() {
		return documentClassName;
	}

	/**
	 * @param documentClassName the documentClassName to set
	 */
	public void setDocumentClassName(String documentClassName) {
		this.documentClassName = documentClassName;
	}

	/**
	 * @return the documentClassId
	 */
	public Long getDocumentClassId() {
		return documentClassId;
	}

	/**
	 * @param documentClassId the documentClassId to set
	 */
	public void setDocumentClassId(Long documentClassId) {
		this.documentClassId = documentClassId;
	}
	
	
	
	
	
}
