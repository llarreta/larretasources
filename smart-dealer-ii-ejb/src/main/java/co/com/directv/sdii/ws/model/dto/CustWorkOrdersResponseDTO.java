/**
 * Creado 18/02/2011 15:58:18
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.model.vo.IBSWorkOrderInfoVO;

/**
 * Encapsula la información de WorkOrders de un cliente 
 * 
 * Fecha de Creación: 18/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustWorkOrdersResponseDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3976659975583641594L;
	
	private String customerCode;
	
	private String countryCode;
	
	private List<IBSWorkOrderInfoVO> workOrders;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<IBSWorkOrderInfoVO> getWorkOrders() {
		return workOrders;
	}

	public void setWorkOrders(List<IBSWorkOrderInfoVO> workOrders) {
		this.workOrders = workOrders;
	}

	public CustWorkOrdersResponseDTO() {
		super();
		workOrders = new ArrayList<IBSWorkOrderInfoVO>();
	}
	
	
}
