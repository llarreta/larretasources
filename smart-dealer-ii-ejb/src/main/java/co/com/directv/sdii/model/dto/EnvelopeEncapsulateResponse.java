package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * Clase creada para mandar el tipo de excepcion que se 
 * genera cuando se va a generar el PDF de una WO
 * 
 * Fecha de Creación: 11/06/2011
 * @author  waltuzarra <a href="mailto: waltuzarra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class EnvelopeEncapsulateResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3730426409633699681L;
	
	private String woCode;
	private List<EnvelopeEncapsulateDetailResponse> exceptions;
	
	public EnvelopeEncapsulateResponse() {
		exceptions = new ArrayList<EnvelopeEncapsulateDetailResponse>(); 
	}
	
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public List<EnvelopeEncapsulateDetailResponse> getExceptions() {
		return exceptions;
	}
	public void setExceptions(List<EnvelopeEncapsulateDetailResponse> exceptions) {
		this.exceptions = exceptions;
	}

}
