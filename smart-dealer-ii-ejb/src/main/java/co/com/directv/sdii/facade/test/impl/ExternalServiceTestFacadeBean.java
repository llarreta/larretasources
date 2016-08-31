package co.com.directv.sdii.facade.test.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import co.com.directv.sdii.common.locator.CustomerContactsServiceLocator;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.facade.test.ExternalServiceTestFacadeBeanLocal;

/**
 * 
 * Interfaz del facade con las operaciones para probar los Web services externos.
 * 
 * Fecha de CreaciÃ³n: feb 02, 2015
 * @author fsanabria <a href="mailto:facundo.sanabria@everis.com">e-mail</a>
 * @version 5.2.2
 * 
 * @see
 */
@Stateless(name="ExternalServiceTestFacadeBean",mappedName="ejb/ExternalServiceTestFacadeLocal")
public class ExternalServiceTestFacadeBean implements ExternalServiceTestFacadeBeanLocal {
   
	public List<String> getAllServicesPort() throws BusinessException{
		List<String> result = new ArrayList<String>();
		try {
			result.add(ServiceLocator.getInstance().getCoreService().toString());
			result.add(ServiceLocator.getInstance().getCustomerService().toString());
			result.add(ServiceLocator.getInstance().getVista360Service().toString());
			result.add(ServiceLocator.getInstance().getMarkWorkOrderService().toString());
			result.add(ServiceLocator.getInstance().getManageWorkForceService().toString());
			result.add(CustomerContactsServiceLocator.getInstance().getCustomerContactsService().toString());
			result.add(ServiceLocator.getInstance().getResourceProvisioningService().toString());
			result.add(ServiceLocator.getInstance().getServiceProblemManagementService().toString());
			result.add(ServiceLocator.getInstance().getSPRMSupportAndReadinessInventoryService().toString());
			result.add(ServiceLocator.getInstance().getCRMSupportAndReadinessService().toString());
			result.add(ServiceLocator.getInstance().getServiceConfigurationAndActivationService().toString());
			result.add(ServiceLocator.getInstance().getCustomerInterfaceManagment().toString());
			result.add(ServiceLocator.getInstance().getOrderHandlingService().toString());
			
		} catch (ServiceLocatorException e) {
			new BusinessException(e.getMessage(), e);
		}
		return result;
	}
	
}
