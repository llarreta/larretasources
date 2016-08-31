package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * Clase creada para manejar las excepciones generadas de las validaciones
 * creadas para generar PDF´s para work orders
 * @author waltuzarra
 *
 */
public class EnvelopeEncapsulateDetailResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3730426409633699681L;
	
	private String exceptionCode;
	private String exceptionMessage;
	
	public EnvelopeEncapsulateDetailResponse() {
	}
	
	public EnvelopeEncapsulateDetailResponse(String exceptionCode, String exceptionMessage) {
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	}
