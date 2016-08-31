package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Representa el objeto a interactuar entre el cliente del web service y el server para encapsular
 * y extender el modelo de un WarehouseElement permitiendo identificar tambien la remision y 
 * el registro de importacion al que pertenece el elemento
 * 
 * Fecha de Creacion: Aug 23, 2010
 * @author garciniegas <a href="mailto:garciniegas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WareHouseElementClientFilterRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6075621275834359839L;
	private Long customerId; 
	private String serial; 
	private Date startDate; 
	private Date endDate;
	private boolean isActualElements;
	
	public WareHouseElementClientFilterRequestDTO() {
		super();
	}

	public WareHouseElementClientFilterRequestDTO(Long customerId, String serial,
			Date startDate, Date endDate) {
		super();
		this.customerId = customerId;
		this.serial = serial;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isActualElements() {
		return isActualElements;
	}

	public void setActualElements(boolean isActualElements) {
		this.isActualElements = isActualElements;
	}
	
}
