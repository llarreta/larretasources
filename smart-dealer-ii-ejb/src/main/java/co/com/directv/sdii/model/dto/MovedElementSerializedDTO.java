package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * <Descripcion>
 * 
 * Fecha de Creación: 2/09/2011
 * 
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class MovedElementSerializedDTO implements Serializable,Comparable<MovedElementSerializedDTO> {

	private static final long serialVersionUID = -6075621275834359839L;

	/**
	 *Serial
	 */
	private String serialCode;// Serialized

	/**
	 *RID
	 */
	private String ird;// Serialized

	/**
	 *Serial Vinculado
	 */
	private String serialCodeLinked;// Serialized

	/**
	 *Codigo Modelo
	 */
	private String modelCode;// Element model

	/**
	 *Nombre Modelo
	 */
	private String modelName;// Element model

	/**
	 *Codigo Tipo Elemento
	 */
	private String typeElementCode;//ElementType

	/**
	 *nombre Tipo Elemento
	 */
	private String typeElementName;//ElementType

	/**
	 *Causal de Movimiento
	 */
	private String movTypeNameOut;//MovementType

	/**
	 *Tipo de Movimiento
	 */
	private String movClassOut;//MovementType

	/**
	 *Compañía Origen
	 */
	private String dealerNameCompanyOut;//Dealer

	/**
	 *Sucursal Origen
	 */
	private String dealerNameBranchOut;//Dealer

	/**
	 *Cuadrilla Origen
	 */
	private String isResponsibleOut;//EmployeeCrew

	/**
	 *Codigo Bodega origen
	 */
	private String whCodeOut;//Warehouse

	/**
	 *Fecha y hora de salida
	 */
	private Date movementDateOut;//WarehouseElement

	/**
	 *Nombre del Tipo de Movimiento
	 */
	private String movTypeNameIn;//MovementType

	/**
	 *Clase de Movimiento
	 */
	private String movClassIn;//MovementType

	/**
	 *Compañía Destino
	 */
	private String dealerNameCompanyIn;//Dealer

	/**
	 *Sucursal destino
	 */
	private String dealerNameBranchIn;//Dealer

	/**
	 *Cuadrilla destino
	 */
	private String isResponsibleIn;//EmployeeCrew

	/**
	 *Codigo Bodega destino
	 */
	private String whCodeIn;//Warehouse

	/**
	 *Fecha y hora de entrada
	 */
	private Date movementDateIn;//WarehouseElement

	/**
	 *Cantidad
	 */
	private Double movedQuantity;//WarehouseElement
	
	/**
	 *Tipo numero documento Entrada
	 */
	private String typeDocumentIn;//WarehouseElement
	
	/**
	 *Tipo numero documento Salida
	 */
	private String typeDocumentOut;//WarehouseElement

	/**
	 * Constructor: vacio
	 * @author cduarte
	 */
	public MovedElementSerializedDTO() {
		super();
	}

	/**
	 * Constructor: insertion de todos los atributos 
	 * 
	 * @param serialCode
	 * @param ird
	 * @param serialCodeLinked
	 * @param modelCode
	 * @param modelName
	 * @param typeElementCode
	 * @param typeElementName
	 * @param movTypeNameOut
	 * @param movClassOut
	 * @param dealerNameCompanyOut
	 * @param dealerNameBranchOut
	 * @param isResponsibleOut
	 * @param whCodeOut
	 * @param movementDateOut
	 * @param movTypeNameIn
	 * @param movClassIn
	 * @param dealerNameCompanyIn
	 * @param dealerNameBranchIn
	 * @param isResponsibleIn
	 * @param whCodeIn
	 * @param movementDateIn
	 * @param movedQuantity
	 * @author cduarte
	 */
	public MovedElementSerializedDTO(String serialCode, String ird,
			String serialCodeLinked, String modelCode, String modelName,
			String typeElementCode, String typeElementName,
			String movTypeNameOut, String movClassOut,
			String dealerNameCompanyOut, String dealerNameBranchOut,
			String isResponsibleOut, String whCodeOut, Date movementDateOut,
			String movTypeNameIn, String movClassIn,
			String dealerNameCompanyIn, String dealerNameBranchIn,
			String isResponsibleIn, String whCodeIn, Date movementDateIn,
			Double movedQuantity,String typeDocumentIn,String typeDocumentOut) {
		super();
		this.serialCode = serialCode;
		this.ird = ird;
		this.serialCodeLinked = serialCodeLinked;
		this.modelCode = modelCode;
		this.modelName = modelName;
		this.typeElementCode = typeElementCode;
		this.typeElementName = typeElementName;
		this.movTypeNameOut = movTypeNameOut;
		this.movClassOut = movClassOut;
		this.dealerNameCompanyOut = dealerNameCompanyOut;
		this.dealerNameBranchOut = dealerNameBranchOut;
		this.isResponsibleOut = isResponsibleOut;
		this.whCodeOut = whCodeOut;
		this.movementDateOut = movementDateOut;
		this.movTypeNameIn = movTypeNameIn;
		this.movClassIn = movClassIn;
		this.dealerNameCompanyIn = dealerNameCompanyIn;
		this.dealerNameBranchIn = dealerNameBranchIn;
		this.isResponsibleIn = isResponsibleIn;
		this.whCodeIn = whCodeIn;
		this.movementDateIn = movementDateIn;
		this.movedQuantity = movedQuantity;
		
		this.typeDocumentIn = typeDocumentIn;

		if(typeDocumentOut==null || typeDocumentOut == "")
			this.typeDocumentOut = typeDocumentIn;
		else
			this.typeDocumentOut = typeDocumentOut;

	}

	/**
	 * @return the serialCode
	 */
	public String getSerialCode() {
		return serialCode;
	}

	/**
	 * @param serialCode
	 *            the serialCode to set
	 */
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	/**
	 * @return the ird
	 */
	public String getIrd() {
		return ird;
	}

	/**
	 * @param ird
	 *            the ird to set
	 */
	public void setIrd(String ird) {
		this.ird = ird;
	}

	/**
	 * @return the serialCodeLinked
	 */
	public String getSerialCodeLinked() {
		return serialCodeLinked;
	}

	/**
	 * @param serialCodeLinked
	 *            the serialCodeLinked to set
	 */
	public void setSerialCodeLinked(String serialCodeLinked) {
		this.serialCodeLinked = serialCodeLinked;
	}

	/**
	 * @return the modelCode
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * @param modelCode
	 *            the modelCode to set
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName
	 *            the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the typeElementCode
	 */
	public String getTypeElementCode() {
		return typeElementCode;
	}

	/**
	 * @param typeElementCode
	 *            the typeElementCode to set
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
	 * @param typeElementName
	 *            the typeElementName to set
	 */
	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}

	/**
	 * @return the movTypeNameOut
	 */
	public String getMovTypeNameOut() {
		return movTypeNameOut;
	}

	/**
	 * @param movTypeNameOut
	 *            the movTypeNameOut to set
	 */
	public void setMovTypeNameOut(String movTypeNameOut) {
		this.movTypeNameOut = movTypeNameOut;
	}

	/**
	 * @return the movClassOut
	 */
	public String getMovClassOut() {
		return movClassOut;
	}

	/**
	 * @param movClassOut
	 *            the movClassOut to set
	 */
	public void setMovClassOut(String movClassOut) {
		this.movClassOut = movClassOut;
	}

	/**
	 * @return the dealerNameCompanyOut
	 */
	public String getDealerNameCompanyOut() {
		return dealerNameCompanyOut;
	}

	/**
	 * @param dealerNameCompanyOut
	 *            the dealerNameCompanyOut to set
	 */
	public void setDealerNameCompanyOut(String dealerNameCompanyOut) {
		this.dealerNameCompanyOut = dealerNameCompanyOut;
	}

	/**
	 * @return the dealerNameBranchOut
	 */
	public String getDealerNameBranchOut() {
		return dealerNameBranchOut;
	}

	/**
	 * @param dealerNameBranchOut
	 *            the dealerNameBranchOut to set
	 */
	public void setDealerNameBranchOut(String dealerNameBranchOut) {
		this.dealerNameBranchOut = dealerNameBranchOut;
	}

	/**
	 * @return the isResponsibleOut
	 */
	public String getIsResponsibleOut() {
		return isResponsibleOut;
	}

	/**
	 * @param isResponsibleOut
	 *            the isResponsibleOut to set
	 */
	public void setIsResponsibleOut(String isResponsibleOut) {
		this.isResponsibleOut = isResponsibleOut;
	}

	/**
	 * @return the whCodeOut
	 */
	public String getWhCodeOut() {
		return whCodeOut;
	}

	/**
	 * @param whCodeOut
	 *            the whCodeOut to set
	 */
	public void setWhCodeOut(String whCodeOut) {
		this.whCodeOut = whCodeOut;
	}

	/**
	 * @return the movementDateOut
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getMovementDateOut() {
		return movementDateOut;
	}

	/**
	 * @param movementDateOut
	 *            the movementDateOut to set
	 */
	public void setMovementDateOut(Date movementDateOut) {
		this.movementDateOut = movementDateOut;
	}

	/**
	 * @return the movTypeNameIn
	 */
	public String getMovTypeNameIn() {
		return movTypeNameIn;
	}

	/**
	 * @param movTypeNameIn
	 *            the movTypeNameIn to set
	 */
	public void setMovTypeNameIn(String movTypeNameIn) {
		this.movTypeNameIn = movTypeNameIn;
	}

	/**
	 * @return the movClassIn
	 */
	public String getMovClassIn() {
		return movClassIn;
	}

	/**
	 * @param movClassIn
	 *            the movClassIn to set
	 */
	public void setMovClassIn(String movClassIn) {
		this.movClassIn = movClassIn;
	}

	/**
	 * @return the dealerNameCompanyIn
	 */
	public String getDealerNameCompanyIn() {
		return dealerNameCompanyIn;
	}

	/**
	 * @param dealerNameCompanyIn
	 *            the dealerNameCompanyIn to set
	 */
	public void setDealerNameCompanyIn(String dealerNameCompanyIn) {
		this.dealerNameCompanyIn = dealerNameCompanyIn;
	}

	/**
	 * @return the dealerNameBranchIn
	 */
	public String getDealerNameBranchIn() {
		return dealerNameBranchIn;
	}

	/**
	 * @param dealerNameBranchIn
	 *            the dealerNameBranchIn to set
	 */
	public void setDealerNameBranchIn(String dealerNameBranchIn) {
		this.dealerNameBranchIn = dealerNameBranchIn;
	}

	/**
	 * @return the isResponsibleIn
	 */
	public String getIsResponsibleIn() {
		return isResponsibleIn;
	}

	/**
	 * @param isResponsibleIn
	 *            the isResponsibleIn to set
	 */
	public void setIsResponsibleIn(String isResponsibleIn) {
		this.isResponsibleIn = isResponsibleIn;
	}

	/**
	 * @return the whCodeIn
	 */
	public String getWhCodeIn() {
		return whCodeIn;
	}

	/**
	 * @param whCodeIn
	 *            the whCodeIn to set
	 */
	public void setWhCodeIn(String whCodeIn) {
		this.whCodeIn = whCodeIn;
	}

	/**
	 * @return the movementDateIn
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getMovementDateIn() {
		return movementDateIn;
	}

	/**
	 * @param movementDateIn
	 *            the movementDateIn to set
	 */
	public void setMovementDateIn(Date movementDateIn) {
		this.movementDateIn = movementDateIn;
	}

	/**
	 * @return the movedQuantity
	 */
	public Double getMovedQuantity() {
		return movedQuantity;
	}

	/**
	 * @param movedQuantity
	 *            the movedQuantity to set
	 */
	public void setMovedQuantity(Double movedQuantity) {
		this.movedQuantity = movedQuantity;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the typeDocumentIn
	 */
	public String getTypeDocumentIn() {
		return typeDocumentIn;
	}

	/**
	 * @param typeDocumentIn the typeDocumentIn to set
	 */
	public void setTypeDocumentIn(String typeDocumentIn) {
		this.typeDocumentIn = typeDocumentIn;
	}

	/**
	 * @return the typeDocumentOut
	 */
	public String getTypeDocumentOut() {
		return typeDocumentOut;
	}

	/**
	 * @param typeDocumentOut the typeDocumentOut to set
	 */
	public void setTypeDocumentOut(String typeDocumentOut) {
		this.typeDocumentOut = typeDocumentOut;
	}

	@Override
	public int compareTo(MovedElementSerializedDTO movedElementSerializedDTO) {
		
		if(movementDateIn != null && movedElementSerializedDTO.movementDateIn != null){
			return movementDateIn.compareTo(movedElementSerializedDTO.movementDateIn)*-1;
		}
		if(movementDateOut != null && movedElementSerializedDTO.movementDateOut != null){
			return movementDateOut.compareTo(movedElementSerializedDTO.movementDateOut)*-1;
		}else{
			return 0;
		}
	    
	}
	
}
