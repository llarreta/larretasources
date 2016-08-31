/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.text.SimpleDateFormat;

import co.com.directv.sdii.model.pojo.ImportLog;

/**
 * Objeto que encapsula la información de un ImportLog
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImportLog    
 */
public class ImportLogVO extends ImportLog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -341059194873128644L;
	private boolean esCreacion;
	
	private boolean haveSerializedElements;
	private boolean haveNotSerializedElements;
	
	public boolean isEsCreacion() {
		return esCreacion;
	}
	public void setEsCreacion(boolean esCreacion) {
		this.esCreacion = esCreacion;
	}
	
	public void setHaveSerializedElements(boolean haveSerializedElements) {
		this.haveSerializedElements = haveSerializedElements;
	}
	public boolean isHaveSerializedElements() {
		return haveSerializedElements;
	}
	public void setHaveNotSerializedElements(boolean haveNotSerializedElements) {
		this.haveNotSerializedElements = haveNotSerializedElements;
	}
	public boolean isHaveNotSerializedElements() {
		return haveNotSerializedElements;
	}
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
		buff.append(getId());
		buff.append("|");
		buff.append(getPurchaseOrder() == null ? "" : getPurchaseOrder());
		buff.append("|");
		buff.append(getImportDoc() == null ? "" : getImportDoc());
		buff.append("|");
		buff.append(getCreationDate() == null ? "" : df.format(getCreationDate()));
		buff.append("|");
		buff.append(getDeliveryDate() == null ? "" : df.format(getDeliveryDate()));
		buff.append("|");
		buff.append(getShippingDate() == null ? "" : df.format(getShippingDate()));
		buff.append("|");
		buff.append(getSupplier() == null ? "" : getSupplier().getSupplierName());
		buff.append("|");
		buff.append(getImportLogStatus() == null ? "" : getImportLogStatus().getStatusName());
		buff.append("|");
		buff.append(getDealer() == null ? "" : getDealer().getDealerName());
		return buff.toString();
	}
}
