/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.DealerDetail;

/**
 * Objeto que encapsula la información de un DealerDetail
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerDetail    
 */
public class DealerDetailVO extends DealerDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4239042178768824605L;
	
	private boolean attendTypeOdd;
	private boolean attendTypeEven;
	
	public boolean isAttendTypeOdd() {
		return attendTypeOdd;
	}
	
	public void setAttendTypeOdd(boolean attendTypeOdd) {
		this.attendTypeOdd = attendTypeOdd;
	}
	
	public boolean isAttendTypeEven() {
		return attendTypeEven;
	}
	
	public void setAttendTypeEven(boolean attendTypeEven) {
		this.attendTypeEven = attendTypeEven;
	}
	
}
