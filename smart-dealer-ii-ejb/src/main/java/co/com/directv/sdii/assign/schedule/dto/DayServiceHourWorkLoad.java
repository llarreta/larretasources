package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

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
public class DayServiceHourWorkLoad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3839407071146764780L;
	/**
	 * identificador de la super categor�a de servicio
	 */
	private Long serviceSuperCategoryId;
	/**
	 * fecha para la que se realizó el cálculo de la carga de trabajo
	 */
	private java.util.Date date;
	/**
	 * identificador de la jornada
	 */
	private Long serviceHourId;
	/**
	 * hora de fin de la jornada
	 */
	private java.util.Date serviceHourEndTime;
	/**
	 * hora de inicio de la jornada
	 */
	private java.util.Date serviceHourStartTime;
	/**
	 * capacidad m�xima de atenci�n sumando las capacidades m�ximas de las compa��as
	 */
	private int maxCapacity;
	/**
	 * capacidad usada hasta el momento sumando las capacidades usadas de las compa��as
	 */
	private int usedCapacity;
	
	/**
	 * capacidad usada hasta el momento sumando las capacidades usadas de las compa��as
	 */
	private Double actualCalculation;
	
	/**
	 * color con el que se debe visualizar en la presentación la carga de trabajo en la jornada y
	 * fecha especificadas
	 */
	private String color;

	public DayServiceHourWorkLoad(){

	}
	
	public DayServiceHourWorkLoad(Long serviceSuperCategoryId, Date date,
			Long serviceHourId, Date serviceHourEndTime,
			Date serviceHourStartTime, int maxCapacity, int usedCapacity) {
		super();
		this.serviceSuperCategoryId = serviceSuperCategoryId;
		this.date = date;
		this.serviceHourId = serviceHourId;
		this.serviceHourEndTime = serviceHourEndTime;
		this.serviceHourStartTime = serviceHourStartTime;
		this.maxCapacity = maxCapacity;
		this.usedCapacity = usedCapacity;
	}



	public Long getServiceSuperCategoryId() {
		return serviceSuperCategoryId;
	}



	public void setServiceSuperCategoryId(Long serviceSuperCategoryId) {
		this.serviceSuperCategoryId = serviceSuperCategoryId;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public java.util.Date getDate() {
		return date;
	}



	public void setDate(java.util.Date date) {
		this.date = date;
	}



	public Long getServiceHourId() {
		return serviceHourId;
	}



	public void setServiceHourId(Long serviceHourId) {
		this.serviceHourId = serviceHourId;
	}


	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public java.util.Date getServiceHourEndTime() {
		return serviceHourEndTime;
	}



	public void setServiceHourEndTime(java.util.Date serviceHourEndTime) {
		this.serviceHourEndTime = serviceHourEndTime;
	}


	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public java.util.Date getServiceHourStartTime() {
		return serviceHourStartTime;
	}



	public void setServiceHourStartTime(java.util.Date serviceHourStartTime) {
		this.serviceHourStartTime = serviceHourStartTime;
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
	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getActualCalculation() {
		return actualCalculation;
	}

	public void setActualCalculation(Double actualCalculation) {
		this.actualCalculation = actualCalculation;
	}

	public void finalize() throws Throwable {

	}
}