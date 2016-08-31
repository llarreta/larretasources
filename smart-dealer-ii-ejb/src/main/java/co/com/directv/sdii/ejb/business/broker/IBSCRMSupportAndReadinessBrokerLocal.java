package co.com.directv.sdii.ejb.business.broker;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerAgreementTypesDTO;
import co.com.directv.sdii.model.dto.CustomerResponseByIdentificationResbDTO;
import co.com.directv.sdii.model.dto.CustomersResourcesResbDTO;

@Local
public interface IBSCRMSupportAndReadinessBrokerLocal {

	//public List<CustomerAgreementTypesDTO> getCustomerAgreements(String customerKey, String countryCode) throws BusinessException;
	
	/***
	 * Metodo que llama el metodo getCustomersByIdentification de ESB
	 * @param customerKey numero del documento del cliente
	 * @param sourceId codigo del pais
	 * @return CustomerResponseByIdentificationResbDTO la informacion requerida por HSP+ de la busqueda por numero de documento
	 * @author Aharker
	 */
	public List<CustomerResponseByIdentificationResbDTO> getCustomersByIdentification(String customerKey, String sourceId) throws BusinessException;
	
	/***
	 * Metodo que llama el metodo getCustomerResources de 	esb, el cual tiene la informacion de los elementos que tiene el cliente
	 * @param customerKey codigo ibs del cliente
	 * @param sourceId codigo del pais
	 * @return CustomersResourcesResbDTO la informacion requerida por HSP+ de la busqueda de elementos por codigo ibs del cliente
	 * @author Aharker
	 */
	public List<CustomersResourcesResbDTO> getCustomerResources(String customerKey, String sourceId) throws BusinessException;
	
}
