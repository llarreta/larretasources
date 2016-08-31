/**
 * Creado 18/02/2011 15:59:21
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Encapsula la información que será visualizada desde el módulo de atención
 * al cliente de una Work Order de un cliente
 * 
 * Fecha de Creación: 18/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class IBSWorkOrderInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3921266343186776103L;

	private String workOrderType;
	
	private String workOrderDescription;
	
	private String workOrderStatus;
	
	private String installationCompany;
	
	private Date workOrderDate;

	public String getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}

	public String getWorkOrderDescription() {
		return workOrderDescription;
	}

	public void setWorkOrderDescription(String workOrderDescription) {
		this.workOrderDescription = workOrderDescription;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getInstallationCompany() {
		return installationCompany;
	}

	public void setInstallationCompany(String installationCompany) {
		this.installationCompany = installationCompany;
	}

	public Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}
}
