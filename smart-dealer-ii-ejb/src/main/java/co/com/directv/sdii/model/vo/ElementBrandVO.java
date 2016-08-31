/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ElementBrand;

/**
 * Objeto que encapsula la información de un ElementBrand
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementBrand    
 */
public class ElementBrandVO extends ElementBrand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -341629429931117106L;
	
	private String elemenBrandNameToPrint;

	public String getElemenBrandNameToPrint() {
		return elemenBrandNameToPrint;
	}

	public void setElemenBrandNameToPrint(String elemenBrandNameToPrint) {
		this.elemenBrandNameToPrint = elemenBrandNameToPrint;
	}
	
	
	
	
	

}
