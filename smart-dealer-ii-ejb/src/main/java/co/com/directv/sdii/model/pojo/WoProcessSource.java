package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

/**
 * 
 * Pojo para origen de descarga de una WO 
 * 
 * Fecha de Creación: 10/10/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WoProcessSource implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5760726364260823627L;
	
	private Long id;
	private WorkorderStatus woStatusId;
	private String processSourceDescription;
	private String processSourceCode;
	private String processSourceSource;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WorkorderStatus getWoStatusId() {
		return woStatusId;
	}
	public void setWoStatusId(WorkorderStatus woStatusId) {
		this.woStatusId = woStatusId;
	}
	public String getProcessSourceDescription() {
		return processSourceDescription;
	}
	public void setProcessSourceDescription(String processSourceDescription) {
		this.processSourceDescription = processSourceDescription;
	}
	public String getProcessSourceCode() {
		return processSourceCode;
	}
	public void setProcessSourceCode(String processSourceCode) {
		this.processSourceCode = processSourceCode;
	}
	public String getProcessSourceSource() {
		return processSourceSource;
	}
	public void setProcessSourceSource(String processSourceSource) {
		this.processSourceSource = processSourceSource;
	}
}
