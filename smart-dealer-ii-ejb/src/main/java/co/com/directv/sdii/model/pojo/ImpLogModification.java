package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * ImpLogModification entity. @author MyEclipse Persistence Tools
 */

public class ImpLogModification implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6021733066314972607L;
	private Long id;
	private ImportLog importLog;
	private ImpLogModificationType impLogModificationType;
	private User user;
	private Date modificationDate;

	// Constructors

	/** default constructor */
	public ImpLogModification() {
	}

	/** full constructor */
	public ImpLogModification(ImportLog importLog,
			ImpLogModificationType impLogModificationType, User user,
			Date modificationDate) {
		this.importLog = importLog;
		this.impLogModificationType = impLogModificationType;
		this.user = user;
		this.modificationDate = modificationDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ImportLog getImportLog() {
		return this.importLog;
	}

	public void setImportLog(ImportLog importLog) {
		this.importLog = importLog;
	}

	public ImpLogModificationType getImpLogModificationType() {
		return this.impLogModificationType;
	}

	public void setImpLogModificationType(
			ImpLogModificationType impLogModificationType) {
		this.impLogModificationType = impLogModificationType;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public String toString() {
		return "ImpLogModification [id=" + id + "]";
	}

}