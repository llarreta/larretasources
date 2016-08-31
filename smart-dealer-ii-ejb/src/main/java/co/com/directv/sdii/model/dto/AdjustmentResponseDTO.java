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
public class AdjustmentResponseDTO extends BaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3672865520470359473L;
	
	private Long documentNumber;//Tanto para consulta como de respuesta
	private Long adjustmentTypeId;//El tipo de ajuste para la consulta
	private String adjustmentTypeName;//El tipo de ajuste para la respuesta
	/***********************
	 * VARIABLES DE ORIGEN *
	 ***********************/
	private Long wareHouseSourceId;//Ubicacion de origen, para la consulta
	private String wareHouseSourceName;//Ubicacion de origen, para la respuesta

	/************************
	 * VARIABLES DE DESTINO *
	 ************************/
	private Long wareHouseDestinationId;//Ubicacion de destino, para la consulta
	private String wareHouseDestinationName;//Ubicacion de destino, para la respuesta
	
	/*****************************************
	 * VARIABLES EXCLUSIVAMENTE DE RESPUESTA *
	 *****************************************/
	private String adjustmentStatus;//Estado del documento o ajuste
	private String creationUser;
	private Date creationDate;

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
	
	public String getAdjustmentStatus() {
		return adjustmentStatus;
	}

	public void setAdjustmentStatus(String adjustmentStatus) {
		this.adjustmentStatus = adjustmentStatus;
	}

	public String getAdjustmentTypeName() {
		return adjustmentTypeName;
	}

	public void setAdjustmentTypeName(String adjustmentTypeName) {
		this.adjustmentTypeName = adjustmentTypeName;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	
}
