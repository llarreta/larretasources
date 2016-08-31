/**
 * Creado 16/06/2011 10:32:00
 */
package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;

/**
 * Encapsula la información de los colores del agendamiento
 * 
 * Fecha de Creación: 16/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ScheduleColorsConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7493386560471641075L;

	private Double scheduleLimit;
	
	private String availableColor;
	
	private String unavailableColor;

	public Double getScheduleLimit() {
		return scheduleLimit;
	}

	public void setScheduleLimit(Double scheduleLimit) {
		this.scheduleLimit = scheduleLimit;
	}

	public String getAvailableColor() {
		return availableColor;
	}

	public void setAvailableColor(String availableColor) {
		this.availableColor = availableColor;
	}

	public String getUnavailableColor() {
		return unavailableColor;
	}

	public void setUnavailableColor(String unavailableColor) {
		this.unavailableColor = unavailableColor;
	}
}
