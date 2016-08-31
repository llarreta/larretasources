package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * Encapsula la información de disponibilidad de una lista de servicios en una fecha, con jornadas específicas
 * 
 * Fecha de Creación: 20/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
public class ServiceAvailabilityVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 251786484329104007L;
	
	private Date serviceAvailabilityDate;
	
	private List<ServiceHourVO> serviceHours;

	public Date getServiceAvailabilityDate() {
		return serviceAvailabilityDate;
	}

	public void setServiceAvailabilityDate(Date serviceAvailabilityDate) {
		this.serviceAvailabilityDate = serviceAvailabilityDate;
	}

	public List<ServiceHourVO> getServiceHours() {
		return serviceHours;
	}

	public void setServiceHours(List<ServiceHourVO> serviceHours) {
		this.serviceHours = serviceHours;
	}

	public ServiceAvailabilityVO(Date serviceAvailabilityDate,
			List<ServiceHourVO> serviceHours) {
		super();
		this.serviceAvailabilityDate = serviceAvailabilityDate;
		this.serviceHours = serviceHours;
	}

	public ServiceAvailabilityVO() {
		super();
	}

	
	
}
