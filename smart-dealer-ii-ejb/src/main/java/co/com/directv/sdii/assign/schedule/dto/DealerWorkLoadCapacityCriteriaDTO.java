package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;

/**
 * encapsula la informaci�n de la carga de trabajo para unacategor�a de servicio
 * en una fecha y jornada
 * @author jjimenezh
 * @version 1.0
 * @created 26-may-2011 15:11:46
 */
/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 10/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class DealerWorkLoadCapacityCriteriaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3839407071146764780L;
	
	/**
	 * capacidad m�xima de atenci�n sumando las capacidades m�ximas de las compa��as
	 */
	private int maxCapacity;
	/**
	 * capacidad usada hasta el momento sumando las capacidades usadas de las compa��as
	 */
	private int usedCapacity;
	
	/**
	 * identifica si el dealer se sobreagenda
	 */
	private boolean aboveScheduled;
	
	public DealerWorkLoadCapacityCriteriaDTO(){

	}
	
	public DealerWorkLoadCapacityCriteriaDTO(int maxCapacity, int usedCapacity,
			boolean aboveScheduled) {
		super();
		this.maxCapacity = maxCapacity;
		this.usedCapacity = usedCapacity;
		this.aboveScheduled = aboveScheduled;
	}



	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public boolean isAboveScheduled() {
		return aboveScheduled;
	}

	public void setAboveScheduled(boolean aboveScheduled) {
		this.aboveScheduled = aboveScheduled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}