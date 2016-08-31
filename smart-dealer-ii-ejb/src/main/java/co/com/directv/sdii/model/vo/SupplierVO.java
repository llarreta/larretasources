/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Supplier;

/**
 * Objeto que encapsula la información de un Supplier
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Supplier    
 */
public class SupplierVO extends Supplier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4595186436408171038L;
	
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(getId());
		buff.append("|");
		buff.append(getSupplierCode());
		buff.append("|");
		buff.append(getSupplierNit() == null ? "" : getSupplierNit());
		buff.append("|");
		buff.append(getSupplierName() == null ? "" : getSupplierName());
		buff.append("|");
		buff.append(getContactName() == null ? "" : getContactName());
		buff.append("|");
		buff.append(getAddress() == null ? "" : getAddress());
		buff.append("|");
		buff.append(getDistrict() == null ? "" : getDistrict());
		buff.append("|");
		buff.append(getFax() == null ? "" : getFax());
		buff.append("|");
		buff.append(getPhoneNumber() == null ? "" : getPhoneNumber());
		buff.append("|");
		buff.append(getPostalCode() == null ? "" : getPostalCode().getPostalCodeCode());
		buff.append("|");
		buff.append(getCountryId() == null ? "" : getCountryId().getCountryName());
		buff.append("|");
		buff.append(getIsActive());
		return buff.toString();
	}
}
