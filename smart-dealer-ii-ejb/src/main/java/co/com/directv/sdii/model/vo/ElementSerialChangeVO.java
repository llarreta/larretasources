/**
 * Creado 12/08/2010 11:15:58
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

/**
 * Encapsula la información de un cambio de serial de un elemento
 * 
 * Fecha de Creación: 12/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ElementSerialChangeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8655946543427717842L;

	private String oldSerial;
	
	private String newSerial;
	
	private String newTypeCode;

	public String getNewTypeCode() {
		return newTypeCode;
	}

	public void setNewTypeCode(String newTypeCode) {
		this.newTypeCode = newTypeCode;
	}

	public String getOldSerial() {
		return oldSerial;
	}

	public void setOldSerial(String oldSerial) {
		this.oldSerial = oldSerial;
	}

	public String getNewSerial() {
		return newSerial;
	}

	public void setNewSerial(String newSerial) {
		this.newSerial = newSerial;
	}
	
	
	
}
