/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.SaleChanel;

/**
 * Objeto que encapsula la información de un SaleChanel
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SaleChanel    
 */
public class SaleChanelVO extends SaleChanel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2146924563472048719L;
	

	private List<DealerVO> sellers;
	
	private List<DealerVO> installers;

	public List<DealerVO> getSellers() {
		return sellers;
	}

	public void setSellers(List<DealerVO> sellers) {
		this.sellers = sellers;
	}

	public List<DealerVO> getInstallers() {
		return installers;
	}

	public void setInstallers(List<DealerVO> installers) {
		this.installers = installers;
	}
	
}
