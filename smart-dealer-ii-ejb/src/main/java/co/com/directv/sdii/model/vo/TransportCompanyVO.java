/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.TransportCompany;

/**
 * Objeto que encapsula la información de un TransportCompany
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.TransportCompany    
 */
public class TransportCompanyVO extends TransportCompany implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8694521168931709600L;
	
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(getId());
		buff.append("|");
		buff.append(getCompanyCode());
		buff.append("|");
		buff.append(getCompanyName());
		buff.append("|");
		buff.append(getCompanyDescription() == null ? "" : getCompanyDescription());
		buff.append("|");
		buff.append(getCompanyAddress() == null ? "" : getCompanyAddress());
		buff.append("|");
		buff.append(getCountryCodeId() == null ? "" : getCountryCodeId().getCountryName());
		buff.append("|");
		buff.append(getIsActive());
		return buff.toString();
	}
}
