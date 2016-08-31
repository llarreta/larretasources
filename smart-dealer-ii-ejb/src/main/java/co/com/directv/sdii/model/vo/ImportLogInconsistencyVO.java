/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ImportLogInconsistency;

/**
 * Objeto que encapsula la información de un ImportLogInconsistency
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImportLogInconsistency    
 */
public class ImportLogInconsistencyVO extends ImportLogInconsistency implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5158803117668067700L;
	
	private String userName;
	
	private ImportLogItemVO impLogItem;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public ImportLogItemVO getImpLogItem() {
		return impLogItem;
	}

	public void setImpLogItem(ImportLogItemVO impLogItem) {
		this.impLogItem = impLogItem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}
