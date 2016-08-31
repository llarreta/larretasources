/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ElementModel;

/**
 * Objeto que encapsula la información de un ElementModel
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementModel    
 */
public class ElementModelVO extends ElementModel implements Serializable {
	
	private String elementClassName;
	private String technologyName;
	//gfandino - 07/06/2011 Se agrega por INV109 2.4
	private String codeNameToPrint;

	/**
	 * 
	 */
	private static final long serialVersionUID = -344883067372946409L;
	
	
	
	public String getElementClassName() {
		this.elementClassName = getElementClass().getElementClassName();
		return this.elementClassName;
	}

	public void setElementClassName(String elementClassName) {
		this.elementClassName = elementClassName;
	}
	
	

	public String getTechnologyName() {
		if (getTechnology() != null && getTechnology().getName() != null ){
		  technologyName = getTechnology().getName();
		}
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}
	
	
	public String getCodeNameToPrint() {
		return codeNameToPrint;
	}

	public void setCodeNameToPrint(String codeNameToPrint) {
		this.codeNameToPrint = codeNameToPrint;
	}

	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(getId());
		buff.append("|");
		buff.append(getModelCode());
		buff.append("|");
		buff.append(getModelName());
		buff.append("|");
		buff.append(getModelDescription() == null ? "" : getModelDescription());
		buff.append("|");
		buff.append(getIsActive());
		buff.append("|");
		buff.append(getElementClass() == null ? "" : getElementClass().getElementClassName());
		buff.append("|");
		buff.append(getTechnology() == null ? "" : getTechnology().getName());
		buff.append("|");
		buff.append(getIsPrepaidModel() == null ? "" : getIsPrepaidModel());
		return buff.toString();
	}

}
