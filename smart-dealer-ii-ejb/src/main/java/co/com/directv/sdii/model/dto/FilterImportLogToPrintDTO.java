package co.com.directv.sdii.model.dto;

import java.util.Date;
import java.util.List;

import co.com.directv.sdii.rules.BusinessRequired;

/**
 * 
 * Filter ImportLog To Print
 * 
 * Fecha de Creaci�n: Aug 26, 2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class FilterImportLogToPrintDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781211330002198185L;
	
	private Long id;
	private Date deliveryDate;
	private Date shippingDate;
	private Date creationDate;
	private Long importLogStatusId;
	
	public FilterImportLogToPrintDTO(){
	}
	/**
	 * Constructor: <Descripcion>
	 * @param id
	 * @param deliveryDate
	 * @param shippingDate
	 * @param creationDate
	 * @param importLogStatusId
	 * @author
	 */
	public FilterImportLogToPrintDTO(Long id, Date deliveryDate,
			Date shippingDate, Date creationDate, Long importLogStatusId) {
		super();
		this.id = id;
		this.deliveryDate = deliveryDate;
		this.shippingDate = shippingDate;
		this.creationDate = creationDate;
		this.importLogStatusId = importLogStatusId;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return the shippingDate
	 */
	public Date getShippingDate() {
		return shippingDate;
	}
	/**
	 * @param shippingDate the shippingDate to set
	 */
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the importLogStatusId
	 */
	public Long getImportLogStatusId() {
		return importLogStatusId;
	}
	/**
	 * @param importLogStatusId the importLogStatusId to set
	 */
	public void setImportLogStatusId(Long importLogStatusId) {
		this.importLogStatusId = importLogStatusId;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
