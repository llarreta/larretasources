package co.com.directv.sdii.reports.dto;

import java.util.Date;

import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Serialized;


public class WarehouseSerializedNotSerializedActualDTO implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 9054376266261478984L;
	
	private NotSerialized notSerialized;
	private Serialized serialized;
	private Double actualQuantity;
	private Date movementDate;
	
	/**
	 * Constructor: <Descripcion>
	 * @author
	 */
	public WarehouseSerializedNotSerializedActualDTO() {
		super();
	}
	
	/**
	 * Constructor: <Descripcion>
	 * @param notSerialized
	 * @param serialized
	 * @param actualQuantity
	 * @param movementDate
	 * @author
	 */
	public WarehouseSerializedNotSerializedActualDTO(
			NotSerialized notSerialized, Serialized serialized,
			Double actualQuantity, Date movementDate) {
		super();
		this.notSerialized = notSerialized;
		this.serialized = serialized;
		this.actualQuantity = actualQuantity;
		this.movementDate = movementDate;
	}

	/**
	 * @return the notSerialized
	 */
	public NotSerialized getNotSerialized() {
		return notSerialized;
	}
	/**
	 * @param notSerialized the notSerialized to set
	 */
	public void setNotSerialized(NotSerialized notSerialized) {
		this.notSerialized = notSerialized;
	}
	/**
	 * @return the serialized
	 */
	public Serialized getSerialized() {
		return serialized;
	}
	/**
	 * @param serialized the serialized to set
	 */
	public void setSerialized(Serialized serialized) {
		this.serialized = serialized;
	}
	/**
	 * @return the actualQuantity
	 */
	public Double getActualQuantity() {
		return actualQuantity;
	}
	/**
	 * @param actualQuantity the actualQuantity to set
	 */
	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the movementDate
	 */
	public Date getMovementDate() {
		return movementDate;
	}

	/**
	 * @param movementDate the movementDate to set
	 */
	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}
	
	
	
	
}
