package co.com.directv.sdii.ejb.business.broker.toa;

import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerResourcesDTO;

import com.directvla.contract.crm.customer.v1_0.GetCustomerResourcesResult;
import com.directvla.schema.businessdomain.common.v1_0.PhysicalResource;

/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * Fecha de Creación: 18/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public final class CustomerServiceTOA {
	
	private CustomerServiceTOA(){}
	
	/**
	 * 
	 * Metodo: Transforma la respuesta del servicio en objetos de negocio para obtener los recursos activos de un cliente
	 * @param serviceResponse
	 * @return
	 * @throws PropertiesException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public static List<CustomerResourcesDTO> populateCustomerResourcesDTO(GetCustomerResourcesResult serviceResponse) throws PropertiesException, BusinessException {
		validateGetCustomerResourcesResult(serviceResponse);
		List<CustomerResourcesDTO> response = null;
		
		response = new ArrayList<CustomerResourcesDTO>();
		for (PhysicalResource resource : serviceResponse.getResourcesList().getPhysicalResources()) {
			validatePhysicalResource(resource);
			if (resource.getStatus().getId().equals(CodesBusinessEntityEnum.IBS_RESOURCE_ACTIVE_STATUS.getCodeEntity())) {
				CustomerResourcesDTO dto = new CustomerResourcesDTO();
				dto.setDescription(resource.getDescription());
				dto.setObjectID(resource.getObjectID());
				dto.setSerialNumber(resource.getSerialNumber());
				dto.setTechnicalProdcutId(resource.getTechnicalProductID());
				dto.setProductId(resource.getProductID());
				dto.setStatusId(resource.getStatus().getId());
				dto.setStatusName(resource.getStatus().getName());
				if (resource.getDeviceModel() != null) {
					dto.setDeviceModelId(resource.getDeviceModel().getId());
					dto.setDeviceModelName(resource.getDeviceModel().getName());
				}
				response.add(dto);
			}

		}
		return response;
	}
	
	/**
	 * 
	 * Metodo: Valida que el objeto de resource venga con los valores minimos para ser tenido en cuenta en la respuesta
	 * @param resource
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private static void validatePhysicalResource(PhysicalResource resource) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Elemento por cliente";		
		params[0] = "PhysicalResource";
		UtilsBusiness.validateRequestResponseWebService(params, resource, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "PhysicalResource.getStatus()";
		UtilsBusiness.validateRequestResponseWebService(params, resource.getStatus(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: Valida la respuesta del servicio que obtiene los elementos por cliente
	 * @param serviceResponse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private static void validateGetCustomerResourcesResult(GetCustomerResourcesResult serviceResponse ) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Obtener Lista de resources por cliente";		
		params[0] = "GetCustomerResourcesResult";
		UtilsBusiness.validateRequestResponseWebService(params, serviceResponse, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "GetCustomerResourcesResult.getResourcesList";
		UtilsBusiness.validateRequestResponseWebService(params,  serviceResponse.getResourcesList(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "GetCustomerResourcesResult.getResourcesList";
		UtilsBusiness.validateRequestResponseWebService(params,  serviceResponse.getResourcesList(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}

}
