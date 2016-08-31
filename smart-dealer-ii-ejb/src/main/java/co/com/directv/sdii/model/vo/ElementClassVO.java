/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ElementClass;

/**
 * Objeto que encapsula la información de un ElementClass
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementClass    
 */
public class ElementClassVO extends ElementClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3967156550817201060L;
	
	private String elementClassNameToPrint;

	public String getElementClassNameToPrint() {
		return elementClassNameToPrint;
	}

	public void setElementClassNameToPrint(String elementClassNameToPrint) {
		this.elementClassNameToPrint = elementClassNameToPrint;
	}
	
	
	

}
