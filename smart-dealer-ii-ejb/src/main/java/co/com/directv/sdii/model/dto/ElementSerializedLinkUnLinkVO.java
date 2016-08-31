package co.com.directv.sdii.model.dto;

import java.io.Serializable;



/**
 * 
 * ElementSerializedLinkUnLinkVO Value Object
 * Clase utilitaria para enviar la informacion a la presentacion de la consulta 
 * de elementos serializados para su vinculacion y desvinculacion
 * 
 * Fecha de Creación: 23/08/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ElementSerializedLinkUnLinkVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7823072977237349391L;
	private String codeCompany;
	private String nameCompany;
	private String codeBranch;
	private String nameBranch;
	private Long warehouseId;
	private String nameWareHouse;
	private String codeTypeElement;
	private String nameTypeElement;
	private String modelElement;
	private String serialCodeElement;
	private Long elementId;
	private String irdElement;
	private Long elementIdLink;
	private String serialCodeLink;
	private String modelElementLink;
	private String typeElementLinkName;
	

	public ElementSerializedLinkUnLinkVO(){}

	

	public ElementSerializedLinkUnLinkVO(String codeCompany, String nameCompany,
			String codeBranch, String nameBranch, Long warehouseId,
			String codeTypeElement, String modelElement,
			String serialCodeElement, Long elementId, String irdElement,
			String serialCodeLink, String modelElementLink,
			String typeElementLinkName) {
		super();
		this.codeCompany = codeCompany;
		this.nameCompany = nameCompany;
		this.codeBranch = codeBranch;
		this.nameBranch = nameBranch;
		this.warehouseId = warehouseId;
		this.nameTypeElement = codeTypeElement;
		this.modelElement = modelElement;
		this.serialCodeElement = serialCodeElement;
		this.elementId = elementId;
		this.irdElement = irdElement;
		this.serialCodeLink = serialCodeLink;
		this.modelElementLink = modelElementLink;
		this.typeElementLinkName = typeElementLinkName;
	}


	public String getCodeCompany() {
		return codeCompany;
	}

	public void setCodeCompany(String codeCompany) {
		this.codeCompany = codeCompany;
	}

	public String getNameCompany() {
		return nameCompany;
	}

	public void setNameCompany(String nameCompany) {
		this.nameCompany = nameCompany;
	}


	public String getNameWareHouse() {
		return nameWareHouse;
	}


	public void setNameWareHouse(String nameWareHouse) {
		this.nameWareHouse = nameWareHouse;
	}

	public String getCodeTypeElement() {
		return codeTypeElement;
	}



	public void setCodeTypeElement(String codeTypeElement) {
		this.codeTypeElement = codeTypeElement;
	}



	public String getNameTypeElement() {
		return nameTypeElement;
	}



	public void setNameTypeElement(String nameTypeElement) {
		this.nameTypeElement = nameTypeElement;
	}



	public String getModelElement() {
		return modelElement;
	}


	public void setModelElement(String modelElement) {
		this.modelElement = modelElement;
	}


	public String getSerialCodeElement() {
		return serialCodeElement;
	}


	public void setSerialCodeElement(String serialCodeElement) {
		this.serialCodeElement = serialCodeElement;
	}

	public String getIrdElement() {
		return irdElement;
	}


	public void setIrdElement(String irdElement) {
		this.irdElement = irdElement;
	}



	public String getSerialCodeLink() {
		return serialCodeLink;
	}


	public void setSerialCodeLink(String serialCodeLink) {
		this.serialCodeLink = serialCodeLink;
	}


	public String getModelElementLink() {
		return modelElementLink;
	}


	public void setModelElementLink(String modelElementLink) {
		this.modelElementLink = modelElementLink;
	}


	public String getTypeElementLinkName() {
		return typeElementLinkName;
	}


	public void setTypeElementLinkName(String typeElementLinkName) {
		this.typeElementLinkName = typeElementLinkName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getElementId() {
		return elementId;
	}


	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}


	public Long getWarehouseId() {
		return warehouseId;
	}


	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}


	public String getCodeBranch() {
		return codeBranch;
	}


	public void setCodeBranch(String codeBranch) {
		this.codeBranch = codeBranch;
	}


	public String getNameBranch() {
		return nameBranch;
	}

	public void setNameBranch(String nameBranch) {
		this.nameBranch = nameBranch;
	}



	public Long getElementIdLink() {
		return elementIdLink;
	}



	public void setElementIdLink(Long elementIdLink) {
		this.elementIdLink = elementIdLink;
	}
	
	
	
}
