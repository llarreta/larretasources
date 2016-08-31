/**
 * Creado 10/06/2011 16:23:25
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * Encapsula los parámetros de consulta para la generación de la agenda disponible
 * para la prestación de servicios
 * 
 * Fecha de Creación: 10/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AssignScheduleRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1802181520475471164L;

	/**
	 * Datos de entrada de la petición con las características principales 
	 * del servicio que se le prestará al cliente
	 */
	private AssignRequestDTO serviceCharacteristics;
	
	/**
	 * fecha de inicio de la consulta de la agenda
	 */
	private Date startingDate;
	
	/**
	 * identificador del dealer en caso que la agenda se deba consultar para un dealer específico
	 */
	private Long dealerId;

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public AssignRequestDTO getServiceCharacteristics() {
		return serviceCharacteristics;
	}

	public void setServiceCharacteristics(AssignRequestDTO serviceCharacteristics) {
		this.serviceCharacteristics = serviceCharacteristics;
	}
	
}
