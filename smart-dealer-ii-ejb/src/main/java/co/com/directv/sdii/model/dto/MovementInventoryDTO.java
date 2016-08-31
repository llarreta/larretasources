package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * Data Transfer Object para transportar objetos a la cola entre el modulo
 * de CORE y ser procesada con Inventarios.
 * 
 * Fecha de Creación: 14/02/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class MovementInventoryDTO implements Serializable{

	private static final long serialVersionUID = 5281149691490940693L;
	
	/**
	 * Declaracion de atributos Privados 
	 */
	
	private Long woId;
	private String serviceCode;
	private String serial;
	private Long customerId;
	private String serial2;
	private String woType;
	private String errorCode;
	private String messageError;
	private Long swapHistoryEvent;
	private Long upgradeAndDowngradeHistoryEvent;
	private String eventId;
	private String reasonId;
	private Long userId;
	private Long dealerCode;
	
	
	
	/**
	 * Constructor vacio
	 * @author
	 */
	public MovementInventoryDTO() {
		super();
	}

	/**
	 * Constructor con todos los atributos
	 * @param woId
	 * @param serviceCode
	 * @param woCode
	 * @param customerCode
	 * @param serial
	 * @param linkedSerial
	 * @param woType
	 * @param errorCode
	 * @param messageError
	 * @param swapHistoryEvent
	 * @author
	 */
	public MovementInventoryDTO(Long woId, String serviceCode, 
			Long customerId, String serial, String serial2,
			String woType, String errorCode, String messageError,
			Long swapHistoryEvent) {
		super();
		this.woId = woId;
		this.serviceCode = serviceCode;
		this.customerId = customerId;
		this.serial = serial;
		this.serial2 = serial2;
		this.woType = woType;
		this.errorCode = errorCode;
		this.messageError = messageError;
		this.swapHistoryEvent = swapHistoryEvent;
	}
	
	public Long getWoId() {
		return woId;
	}
	public void setWoId(Long woId) {
		this.woId = woId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSerial2() {
		return serial2;
	}

	public void setSerial2(String serial2) {
		this.serial2 = serial2;
	}

	public String getWoType() {
		return woType;
	}
	public void setWoType(String woType) {
		this.woType = woType;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessageError() {
		return messageError;
	}
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	public Long getSwapHistoryEvent() {
		return swapHistoryEvent;
	}
	public void setSwapHistoryEvent(Long swapHistoryEvent) {
		this.swapHistoryEvent = swapHistoryEvent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Long getUpgradeAndDowngradeHistoryEvent() {
		return upgradeAndDowngradeHistoryEvent;
	}

	public void setUpgradeAndDowngradeHistoryEvent(
			Long upgradeAndDowngradeHistoryEvent) {
		this.upgradeAndDowngradeHistoryEvent = upgradeAndDowngradeHistoryEvent;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" MovementInventoryDTO ");
		if( woId != null )
			sb.append(" woId: "+woId.toString());
		if( serviceCode != null )
			sb.append(" serviceCode: "+serviceCode);
		if( serial != null )
			sb.append(" serial: "+serial);
		if( customerId != null )
			sb.append(" customerId: "+customerId.toString());
		if( serial2 != null )
			sb.append(" serial2: "+serial2);
		if( woType != null )
			sb.append(" woType: "+woType);
		if( errorCode != null )
			sb.append(" errorCode: "+errorCode);
		if( messageError != null )
			sb.append(" messageError: "+messageError);
		if( swapHistoryEvent != null )
			sb.append(" swapHistoryEvent: "+swapHistoryEvent.toString());
		if( eventId != null )
			sb.append(" eventId: "+eventId);
		if( reasonId != null )
			sb.append(" reasonId: "+reasonId);
		if( userId != null )
			sb.append(" userId: "+userId.toString());
		if( dealerCode != null )
			sb.append(" dealerCode: "+dealerCode.toString());
		return sb.toString();
	}

	
}
