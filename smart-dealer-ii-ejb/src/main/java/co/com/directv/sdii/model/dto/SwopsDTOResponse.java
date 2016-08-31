package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * 
 * DTO encargado de retornar a la capa de negocio los objetos de la respuesta del
 * servicio de SWOP de IBS 
 * 
 * Fecha de Creación: 14/02/2012
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class SwopsDTOResponse extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4912860094591357681L;
	
	private String eventId;
	private String reasonId;
	private Integer swapHistoryEvent;
	private String serialCode;
	private String dealerCode;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public Integer getSwapHistoryEvent() {
		return swapHistoryEvent;
	}

	public void setSwapHistoryEvent(Integer swapHistoryEvent) {
		this.swapHistoryEvent = swapHistoryEvent;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	

}
