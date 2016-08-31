/**
 * Creado 26/11/2010 15:03:24
 */
package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.vo.ServiceHourVO;

/**
 * Envuelve los datos de una jornada agregando los campos que determinan las horas de inicio
 * y fin para evitar problemas de localización entre servidores.
 * 
 * Fecha de Creación: 26/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ServiceHourDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5217430650507805423L;

	
	private ServiceHourVO serviceHour;
	
	private int initHour;
	
	private int initMinute;
	
	private int endHour;
	
	private int endMinute;
	
	public ServiceHourDTO() {
		super();
	}

	public ServiceHourDTO(ServiceHourVO serviceHour) {
		super();
		this.serviceHour = serviceHour;
	}

	public ServiceHourVO getServiceHour() {
		return serviceHour;
	}

	public void setServiceHour(ServiceHourVO serviceHour) {
		this.serviceHour = serviceHour;
	}

	public int getInitHour() {
		return initHour;
	}

	public void setInitHour(int initHour) {
		this.initHour = initHour;
	}

	public int getInitMinute() {
		return initMinute;
	}

	public void setInitMinute(int initMinute) {
		this.initMinute = initMinute;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
}
