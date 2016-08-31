package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 5/09/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
//Modificado para Requerimiento: CC057
public class CustomerElementsDTO implements Serializable {

	private static final long serialVersionUID = -6075621275834359839L;

	/**
	 * ID IBS Cliente	
	 */
	private String customerCode;//Customer
	
	/**
	 * ID IBS Edificio	
	 */
	private String buildingCode;//Edificio
	
	/**
	 * Ciudad del IBS	
	 */
	private String postalCodeCity;//Cliente

	/**
	 * Dirección del IBS	
	 */
	private String woAddress;//Cliente

	/**
	 * Numero de WorkOrder	
	 */
	private String woCode;//WorkOrder

	/**
	 * Tipo de elemento	
	 */
	private String typeElement;//ElementType

	/**
	 * Codigo Tipo de elemento	
	 */
	private String typeElementCode;//ElementTypeCode

	/**
	 * Name Tipo de elemento	
	 */
	private String typeElementName;//ElementTypeName

	/**
	 * Unidad de medida del elemento	
	 */
	private String unitName;//Element

	/**
	 * Lote	Elementos
	 */
	private String lote;//Element

	/**
	 * Cantidad actual	Elementos en Bodega
	 */
	private Double actualQuantity;//WarehouseElement
	
	/**
	 * Modelo	Elementos
	 */
	private String modelName;//ElementModel

	/**
	 * Serial del elemento	
	 */
	private String serialCode;//Elementos

	/**
	 * RID	
	 */
	private String rid;//Elementos

	/**
	 * Serial del elemento vinculado	
	 */
	private String serialCodeLinked;//Element

	/**
	 * Fecha de ingreso del elemento	
	 */
	private Date registrationDateIn;//Elementos en Bodega
	
	/**
	 * Fecha de ingreso del elemento	
	 */
	private Date registrationDateOut;//Elementos en Bodega
	
	/**
	 * causal del movimiento
	 */
	private String movementCausal;
	
	//Tipo numero documento
	private String typeDocument;
	
	
	/**
	 * Constructor: vacio
	 * @author cduarte
	 */
	public CustomerElementsDTO() {
		super();
	}
	
	/**
	 * Constructor: Constructor con todos los atributos de la clase
	 * @param customerCode
	 * @param buildingCode
	 * @param postalCodeCity
	 * @param woAddress
	 * @param woCode
	 * @param typeElement
	 * @param unitName
	 * @param lote
	 * @param actualQuantity
	 * @param modelName
	 * @param serialCode
	 * @param rid
	 * @param serialCodeLinked
	 * @param registrationDateIn
	 * @param registrationDateOut
	 * @param movementCause
	 * @author
	 */
	public CustomerElementsDTO(String customerCode, String buildingCode,
			String postalCodeCity, String woAddress, String woCode,
			String typeElement,String typeElementCode,String typeElementName,String unitName, String lote,
			Double actualQuantity, String modelName, String serialCode,
			String rid, String serialCodeLinked, Date registrationDateIn,
			Date registrationDateOut, String movementCause,
			String typeDocument) {
		super();
		this.customerCode = customerCode;
		this.buildingCode = buildingCode;
		this.postalCodeCity = postalCodeCity;
		this.woAddress = woAddress;
		this.woCode = woCode;
		this.typeElement = typeElement;
		this.typeElementCode=typeElementCode;
		this.typeElementName=typeElementName;
		this.unitName = unitName;
		this.lote = lote;
		this.actualQuantity = actualQuantity;
		this.modelName = modelName;
		this.serialCode = serialCode;
		this.rid = rid;
		this.serialCodeLinked = serialCodeLinked;
		this.registrationDateIn = registrationDateIn;
		this.registrationDateOut = registrationDateOut;
		this.movementCausal = movementCause;
		this.typeDocument = typeDocument; 
	}

	public CustomerElementsDTO(String customerCode, String buildingCode,
			String postalCodeCity, String woAddress, String woCode,
			String typeElement,String unitName, String lote,
			Double actualQuantity, String modelName, String serialCode,
			String rid, String serialCodeLinked, Date registrationDateIn,
			Date registrationDateOut, String movementCause,
			String typeDocument) {
		super();
		this.customerCode = customerCode;
		this.buildingCode = buildingCode;
		this.postalCodeCity = postalCodeCity;
		this.woAddress = woAddress;
		this.woCode = woCode;
		this.typeElement = typeElement;
		this.unitName = unitName;
		this.lote = lote;
		this.actualQuantity = actualQuantity;
		this.modelName = modelName;
		this.serialCode = serialCode;
		this.rid = rid;
		this.serialCodeLinked = serialCodeLinked;
		this.registrationDateIn = registrationDateIn;
		this.registrationDateOut = registrationDateOut;
		this.movementCausal = movementCause;
		this.typeDocument = typeDocument; 
	}

	public String getTypeElementCode() {
		return typeElementCode;
	}

	public void setTypeElementCode(String typeElementCode) {
		this.typeElementCode = typeElementCode;
	}

	public String getTypeElementName() {
		return typeElementName;
	}

	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}


	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}


	/**
	 * @return the buildingCode
	 */
	public String getBuildingCode() {
		return buildingCode;
	}


	/**
	 * @param buildingCode the buildingCode to set
	 */
	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}


	/**
	 * @return the postalCodeCity
	 */
	public String getPostalCodeCity() {
		return postalCodeCity;
	}


	/**
	 * @param postalCodeCity the postalCodeCity to set
	 */
	public void setPostalCodeCity(String postalCodeCity) {
		this.postalCodeCity = postalCodeCity;
	}


	/**
	 * @return the woAddress
	 */
	public String getWoAddress() {
		return woAddress;
	}


	/**
	 * @param woAddress the woAddress to set
	 */
	public void setWoAddress(String woAddress) {
		this.woAddress = woAddress;
	}


	/**
	 * @return the woCode
	 */
	public String getWoCode() {
		return woCode;
	}


	/**
	 * @param woCode the woCode to set
	 */
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}


	/**
	 * @return the typeElement
	 */
	public String getTypeElement() {
		return typeElement;
	}


	/**
	 * @param typeElement the typeElement to set
	 */
	public void setTypeElement(String typeElement) {
		this.typeElement = typeElement;
	}


	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}


	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	/**
	 * @return the lote
	 */
	public String getLote() {
		return lote;
	}


	/**
	 * @param lote the lote to set
	 */
	public void setLote(String lote) {
		this.lote = lote;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the registrationDateIn
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getRegistrationDateIn() {
		return registrationDateIn;
	}

	/**
	 * @param registrationDateIn the registrationDateIn to set
	 */
	public void setRegistrationDateIn(Date registrationDateIn) {
		this.registrationDateIn = registrationDateIn;
	}

	/**
	 * @return the registrationDateOut
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getRegistrationDateOut() {
		return registrationDateOut;
	}

	/**
	 * @param registrationDateOut the registrationDateOut to set
	 */
	public void setRegistrationDateOut(Date registrationDateOut) {
		this.registrationDateOut = registrationDateOut;
	}

	public String getMovementCausal() {
		return movementCausal;
	}

	public void setMovementCausal(String movementCausal) {
		this.movementCausal = movementCausal;
	}

	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}
	
}
