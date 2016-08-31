/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.MeasureUnit;

/**
 * Objeto que encapsula la información de un MeasureUnit
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MeasureUnit    
 */
public class MeasureUnitVO extends MeasureUnit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 649561509445821055L;
	
	private String codeNameToPrint;

	public String getCodeNameToPrint() {
		return codeNameToPrint;
	}

	public void setCodeNameToPrint(String codeNameToPrint) {
		this.codeNameToPrint = codeNameToPrint;
	}
	
	
	

}
