package co.com.directv.sdii.model.dto;

import java.util.List;

/**
 * 
 * Filter References To Print
 * 
 * Fecha de Creaci�n: Aug 25, 2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class FilterReferencesToPrintDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781211330002198185L;
	
	private Long idReferences;
	private Long whSource;
	private Long whTarget;
	private Long referenceStatusId;
	/**
	 * @return the idReferences
	 */
	public Long getIdReferences() {
		return idReferences;
	}
	/**
	 * @param idReferences the idReferences to set
	 */
	public void setIdReferences(Long idReferences) {
		this.idReferences = idReferences;
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
	 * @return the referenceStatusId
	 */
	public Long getReferenceStatusId() {
		return referenceStatusId;
	}
	/**
	 * @param referenceStatusId the referenceStatusId to set
	 */
	public void setReferenceStatusId(Long referenceStatusId) {
		this.referenceStatusId = referenceStatusId;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
