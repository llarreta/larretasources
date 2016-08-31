package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * ImpLogConfirmation entity. @author MyEclipse Persistence Tools
 */

public class ImpLogConfirmation implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6295370241044834481L;
	private Long id;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ImportLogItem importLogItem;
	@BusinessRequired
	private Double confirmedQuantity;
	@BusinessRequired
	private Double pendQuantity;
	@BusinessRequired
	private Date confirmationDate;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private User user;

	// Constructors

	/** default constructor */
	public ImpLogConfirmation() {
	}

	/** full constructor */
	public ImpLogConfirmation(User user, ImportLogItem importLogItem,
			double confirmedQuantity, double pendQuantity,
			Date confirmationDate) {
		this.user = user;
		this.importLogItem = importLogItem;
		this.confirmedQuantity = confirmedQuantity;
		this.pendQuantity = pendQuantity;
		this.confirmationDate = confirmationDate;
	}
	
	// Property accessors
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ImportLogItem getImportLogItem() {
		return importLogItem;
	}

	public void setImportLogItem(ImportLogItem importLogItem) {
		this.importLogItem = importLogItem;
	}

	public Double getConfirmedQuantity() {
		return confirmedQuantity;
	}

	public void setConfirmedQuantity(Double confirmedQuantity) {
		this.confirmedQuantity = confirmedQuantity;
	}

	public Double getPendQuantity() {
		return pendQuantity;
	}

	public void setPendQuantity(Double pendQuantity) {
		this.pendQuantity = pendQuantity;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ImpLogConfirmation [id=" + id + "]";
	}	
	
	
}