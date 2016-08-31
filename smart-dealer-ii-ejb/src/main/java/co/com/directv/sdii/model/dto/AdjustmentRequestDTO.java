package co.com.directv.sdii.model.dto;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * 
 * Clase encargada de encapsular la informacion necesaria para mostrar y consultar un ajuste 
 * 
 * Fecha de Creación: 22/03/2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class AdjustmentRequestDTO extends BaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3672865520470359473L;
	
	private Long documentNumber;//Tanto para consulta como de respuesta
	private Long adjustmentTypeId;//El tipo de ajuste para la consulta
	
	/***********************
	 * VARIABLES DE ORIGEN *
	 ***********************/
	private Long dealerSourceId;//Id del dealer de origen del ajuste, solo para la consulta
	private Long branchSourceId;//Id de la sucursal de origen, solo para consulta
	private Long wareHouseSourceTypeId;//Tipo de bodega origen, solo para la consulta
	private Long crewSourceId;//cuadrilla de origen, solo para la consulta
	private Long wareHouseSourceId;//Ubicacion de origen, para la consulta
	private String wareHouseSourceName;//Ubicacion de origen, para la respuesta

	/************************
	 * VARIABLES DE DESTINO *
	 ************************/
	private Long dealerDestinationId;//Id del dealer de destino del ajuste, solo para la consulta
	private Long branchDestinationId;//Id de la sucursal de destino, solo para consulta
	private Long wareHouseDestinationTypeId;//Tipo de bodega destino, solo para la consulta
	private Long crewDestinationId;//cuadrilla de destino, solo para la consulta
	private Long wareHouseDestinationId;//Ubicacion de destino, para la consulta
	private String wareHouseDestinationName;//Ubicacion de destino, para la respuesta
	
	private Date creationDate;//fecha de creacion
	
	private String serialElement;

	/*********************
	 * ESTADO DEL AJUSTE *
	 *********************/
	
	private Long adjustmentStateId;
	
	/*******************************
	 * CONSULTORES Y MODIFICADORES *
	 *******************************/
	
	public Long getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Long getAdjustmentTypeId() {
		return adjustmentTypeId;
	}

	public void setAdjustmentTypeId(Long adjustmentTypeId) {
		this.adjustmentTypeId = adjustmentTypeId;
	}

	public Long getDealerSourceId() {
		return dealerSourceId;
	}

	public void setDealerSourceId(Long dealerSourceId) {
		this.dealerSourceId = dealerSourceId;
	}

	public Long getBranchSourceId() {
		return branchSourceId;
	}

	public void setBranchSourceId(Long branchSourceId) {
		this.branchSourceId = branchSourceId;
	}

	public Long getWareHouseSourceTypeId() {
		return wareHouseSourceTypeId;
	}

	public void setWareHouseSourceTypeId(Long wareHouseSourceTypeId) {
		this.wareHouseSourceTypeId = wareHouseSourceTypeId;
	}

	public Long getCrewSourceId() {
		return crewSourceId;
	}

	public void setCrewSourceId(Long crewSourceId) {
		this.crewSourceId = crewSourceId;
	}

	public Long getWareHouseSourceId() {
		return wareHouseSourceId;
	}

	public void setWareHouseSourceId(Long wareHouseSourceId) {
		this.wareHouseSourceId = wareHouseSourceId;
	}

	public String getWareHouseSourceName() {
		return wareHouseSourceName;
	}

	public void setWareHouseSourceName(String wareHouseSourceName) {
		this.wareHouseSourceName = wareHouseSourceName;
	}

	public Long getDealerDestinationId() {
		return dealerDestinationId;
	}

	public void setDealerDestinationId(Long dealerDestinationId) {
		this.dealerDestinationId = dealerDestinationId;
	}

	public Long getBranchDestinationId() {
		return branchDestinationId;
	}

	public void setBranchDestinationId(Long branchDestinationId) {
		this.branchDestinationId = branchDestinationId;
	}

	public Long getWareHouseDestinationTypeId() {
		return wareHouseDestinationTypeId;
	}

	public void setWareHouseDestinationTypeId(Long wareHouseDestinationTypeId) {
		this.wareHouseDestinationTypeId = wareHouseDestinationTypeId;
	}

	public Long getCrewDestinationId() {
		return crewDestinationId;
	}

	public void setCrewDestinationId(Long crewDestinationId) {
		this.crewDestinationId = crewDestinationId;
	}

	public Long getWareHouseDestinationId() {
		return wareHouseDestinationId;
	}

	public void setWareHouseDestinationId(Long wareHouseDestinationId) {
		this.wareHouseDestinationId = wareHouseDestinationId;
	}

	public String getWareHouseDestinationName() {
		return wareHouseDestinationName;
	}

	public void setWareHouseDestinationName(String wareHouseDestinationName) {
		this.wareHouseDestinationName = wareHouseDestinationName;
	}
	
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSerialElement() {
		return serialElement;
	}

	public void setSerialElement(String serialElement) {
		this.serialElement = serialElement;
	}

	public Long getAdjustmentStateId() {
		return adjustmentStateId;
	}

	public void setAdjustmentStateId(Long adjustmentStateId) {
		this.adjustmentStateId = adjustmentStateId;
	}
	
}
