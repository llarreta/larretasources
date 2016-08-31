package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * ReferenceModification entity. @author MyEclipse Persistence Tools
 */

public class ReferenceModification implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 456326954443337476L;
	private Long id;
	private Reference reference;
	private ReferenceModType referenceModType;
	private Date modificationDate;
	private User userModification;

	// Constructors

	/** default constructor */
	public ReferenceModification() {
	}

	/** full constructor */
	public ReferenceModification(Reference reference,
			ReferenceModType referenceModType, Date modificationDate,
			User userModification) {
		this.reference = reference;
		this.referenceModType = referenceModType;
		this.modificationDate = modificationDate;
		this.userModification = userModification;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reference getReference() {
		return this.reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public ReferenceModType getReferenceModType() {
		return this.referenceModType;
	}

	public void setReferenceModType(ReferenceModType referenceModType) {
		this.referenceModType = referenceModType;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	
	public User getUserModification() {
		return userModification;
	}

	public void setUserModification(User userModification) {
		this.userModification = userModification;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReferenceModification [id=" + id + "]";
	}
}