package co.com.directv.sdii.ws.model.dto;


/**
 * 
 * Data Transfer Object para encapsular la
 * respuesta del Web Services de Work Order expuesto
 * en  SmartDealerII para IVR  
 * 
 * Fecha de Creación: 5/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ResponseIVRWoServiceDTO {
	

	private String serviceCode;
	private String serviceName;
	
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
}
