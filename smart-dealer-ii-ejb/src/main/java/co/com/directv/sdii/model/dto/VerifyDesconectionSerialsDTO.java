package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;

/**
 * Fecha de Creación: 30/11/2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * @see
 */
public class VerifyDesconectionSerialsDTO extends BaseDTO implements
		Serializable {

	/**
	 * atributo encargado de tener el serial principal que se desea validar que sea valido para la atencion del 
	 * servicio de la work order de desconexion
	 * @author aharker
	 */
	private String principalSerial;
	
	/**
	 * atributo encargado de tener el serial vinculado que se desea validar que sea valido para la atencion del 
	 * servicio de la work order de desconexion
	 * @author aharker
	 */
	private String linkedSerial;
	
	/**
	 * atributo encargado de tener el id de la work order de la cual se van a validar sus seriales.
	 * @author aharker
	 */
	private Long workOrderId;
	
	/**
	 * atributo encargado de tener el id del servicio de la work order de la cual se van a validar sus seriales.
	 * @author aharker
	 */
	private Long workOrderServiceId;

	public VerifyDesconectionSerialsDTO() {
		super();
	}

	public VerifyDesconectionSerialsDTO(String principalSerial,
			String linkedSerial, Long workOrderId, Long workOrderServiceId) {
		super();
		this.principalSerial = principalSerial;
		this.linkedSerial = linkedSerial;
		this.workOrderId = workOrderId;
		this.workOrderServiceId = workOrderServiceId;
	}



	public String getPrincipalSerial() {
		return principalSerial;
	}



	public void setPrincipalSerial(String principalSerial) {
		this.principalSerial = principalSerial;
	}



	public String getLinkedSerial() {
		return linkedSerial;
	}



	public void setLinkedSerial(String linkedSerial) {
		this.linkedSerial = linkedSerial;
	}



	public Long getWorkOrderId() {
		return workOrderId;
	}



	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}



	public Long getWorkOrderServiceId() {
		return workOrderServiceId;
	}



	public void setWorkOrderServiceId(Long workOrderServiceId) {
		this.workOrderServiceId = workOrderServiceId;
	}



	public VerifyDesconectionSerialsDTO(VerifyDesconectionSerialsDTO copy) {
		super();
		this.principalSerial = copy.principalSerial;
		this.linkedSerial = copy.linkedSerial;
		this.workOrderId = copy.workOrderId;
		this.workOrderServiceId = copy.workOrderServiceId;
	}
}
