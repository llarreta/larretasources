package co.com.directv.sdii.model.dto;

import java.io.Serializable;



/**
 * 
 * ElementSerializedLinkUnLinkVO Value Object
 * Clase utilitaria para enviar los filtros de la consulta 
 * de elementos serializados para su vinculacion y desvinculacion (Uno a Uno)
 * 
 * Fecha de Creación: 23/08/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ElementSerializedLinkUnLinkFilterVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 164009514825973468L;
	private Long idCompany;
	private Long idBranch;
	private Long idWarehouse;
	private String serialCodeElement;
	private String codeTypeElement;
	private Long idCountry;
		
	public ElementSerializedLinkUnLinkFilterVO(Long idCompany, Long idBranch,
			Long idWarehouse, String serialCodeElement, String codeTypeElement,Long idCountry) {
		super();
		this.idCompany = idCompany;
		this.idBranch = idBranch;
		this.idWarehouse = idWarehouse;
		this.serialCodeElement = serialCodeElement;
		this.codeTypeElement = codeTypeElement;
		this.idCountry = idCountry;
	}


	public ElementSerializedLinkUnLinkFilterVO(){}


	public Long getIdCompany() {
		return idCompany;
	}


	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}


	public Long getIdBranch() {
		return idBranch;
	}


	public void setIdBranch(Long idBranch) {
		this.idBranch = idBranch;
	}


	public Long getIdWarehouse() {
		return idWarehouse;
	}


	public void setIdWarehouse(Long idWarehouse) {
		this.idWarehouse = idWarehouse;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getSerialCodeElement() {
		return serialCodeElement;
	}


	public void setSerialCodeElement(String serialCodeElement) {
		this.serialCodeElement = serialCodeElement;
	}


	public String getCodeTypeElement() {
		return codeTypeElement;
	}


	public void setCodeTypeElement(String codeTypeElement) {
		this.codeTypeElement = codeTypeElement;
	}

	public Long getIdCountry() {
		return idCountry;
	}


	public void setIdCountry(Long idCountry) {
		this.idCountry = idCountry;
	}
	
}
