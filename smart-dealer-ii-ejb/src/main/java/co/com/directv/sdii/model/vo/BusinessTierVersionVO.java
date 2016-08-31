package co.com.directv.sdii.model.vo;

import java.io.Serializable;

/**
 * 
 * Value Object para transportar la version de la
 * capa de negocio.
 * 
 * Fecha de Creación: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class BusinessTierVersionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2820892495764122008L;

	private String versionBusiness;
	private String versionDate;
	
	public String getVersionBusiness() {
		return versionBusiness;
	}
	public void setVersionBusiness(String versionBusiness) {
		this.versionBusiness = versionBusiness;
	}
	public String getVersionDate() {
		return versionDate;
	}
	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}
}
