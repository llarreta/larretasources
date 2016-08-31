package co.com.directv.sdii.model.dto;

import java.util.Date;

/**
 * 
 * Information To Print References
 * 
 * Fecha de Creaci�n: Aug 25, 2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class InfoToPrintReferencesDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781211330002198185L;
	
	private Long idReference;
	private Long whSource;
	private Long whTarget;
	private Date creationReferenceDate;
	private String createUserId;
	
	public InfoToPrintReferencesDTO() {}
	
	public InfoToPrintReferencesDTO(Long idReference, Long whSource,
			Long whTarget, Date creationReferenceDate, String createUserId) {
		super();
		this.idReference = idReference;
		this.whSource = whSource;
		this.whTarget = whTarget;
		this.creationReferenceDate = creationReferenceDate;
		this.createUserId = createUserId;
	}

	/**
	 * @return the idReference
	 */
	public Long getIdReference() {
		return idReference;
	}

	/**
	 * @param idReference the idReference to set
	 */
	public void setIdReference(Long idReference) {
		this.idReference = idReference;
	}

	/**
	 * @return the whSource
	 */
	public Long getWhSource() {
		return whSource;
	}

	/**
	 * @param whSource the whSource to set
	 */
	public void setWhSource(Long whSource) {
		this.whSource = whSource;
	}

	/**
	 * @return the whTarget
	 */
	public Long getWhTarget() {
		return whTarget;
	}

	/**
	 * @param whTarget the whTarget to set
	 */
	public void setWhTarget(Long whTarget) {
		this.whTarget = whTarget;
	}

	/**
	 * @return the creationReferenceDate
	 */
	public Date getCreationReferenceDate() {
		return creationReferenceDate;
	}

	/**
	 * @param creationReferenceDate the creationReferenceDate to set
	 */
	public void setCreationReferenceDate(Date creationReferenceDate) {
		this.creationReferenceDate = creationReferenceDate;
	}

	/**
	 * @return the createUserId
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
		
}
