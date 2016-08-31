package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.dto.CustomerElementsDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;

/**
 * 
 * Transporta Warehouse entre la capa de DAO, Business y Services. 
 * 
 * Fecha de Creación: 4/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class CustomerElementsResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2655965608586718690L;

	private List<CustomerElementsDTO> customerElementDTO;
	
	public CustomerElementsResponse() {
		super();
	}

	public CustomerElementsResponse(
			List<CustomerElementsDTO> customerElementDTO) {
		super();
		this.customerElementDTO = customerElementDTO;
	}

	public List<CustomerElementsDTO> getCustomerElementDTO() {
		return customerElementDTO;
	}

	public void setCustomerElementDTO(List<CustomerElementsDTO> customerElementDTO) {
		this.customerElementDTO = customerElementDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
