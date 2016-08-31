/**
 * 
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

/**
 * Contiene la ubicacion de la bodega y una bandera de alarma
 * 
 * Fecha de Creación: 26/10/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ResponseWareHouseIndicatorAlarmDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6718834657317747910L;
	
	
	/**
	 * Ubicacion de la bodega 
	 */
	private String locationWareHouse;
	
	/**
	 * Si la alarma esta activa 
	 */
	private boolean alarmIsActive;
	
	/**
	 * Constructor: vacio
	 * @author
	 */
	public ResponseWareHouseIndicatorAlarmDTO() {
		super();
	}

	/**
	 * Constructor: Con todos los atributos 
	 * @param locationWareHouse
	 * @param alarmIsActive
	 * @author
	 */
	public ResponseWareHouseIndicatorAlarmDTO(String locationWareHouse,
			boolean alarmIsActive) {
		super();
		this.locationWareHouse = locationWareHouse;
		this.alarmIsActive = alarmIsActive;
	}
	
	/**
	 * @return the locationWareHouse
	 */
	public String getLocationWareHouse() {
		return locationWareHouse;
	}
	/**
	 * @param locationWareHouse the locationWareHouse to set
	 */
	public void setLocationWareHouse(String locationWareHouse) {
		this.locationWareHouse = locationWareHouse;
	}
	/**
	 * @return the alarmIsActive
	 */
	public boolean isAlarmIsActive() {
		return alarmIsActive;
	}
	/**
	 * @param alarmIsActive the alarmIsActive to set
	 */
	public void setAlarmIsActive(boolean alarmIsActive) {
		this.alarmIsActive = alarmIsActive;
	}
	
}
