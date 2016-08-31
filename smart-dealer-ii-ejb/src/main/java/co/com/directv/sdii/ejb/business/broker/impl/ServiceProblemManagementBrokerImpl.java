package co.com.directv.sdii.ejb.business.broker.impl;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.IServiceProblemManagementBroker;
import co.com.directv.sdii.ejb.business.broker.toa.ServiceProblemManagementTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;

import com.directvla.contract.businessdomain.serviceproblemmanagement.v1_0.ServiceProblemManagementPt;
import com.directvla.schema.businessdomain.serviceproblemmanagement.v1_0.ResourceChangeDamagedRequest;
import com.directvla.schema.businessdomain.serviceproblemmanagement.v1_0.ResourceChangeDamagedResponse;


/**
 * Implementa las operaciones para interactuar
 * con el servicio externo del ESB ServiceProblemManagement
 * 
 * Fecha de Creación: 8/09/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ServiceProblemManagementBrokerImpl implements IServiceProblemManagementBroker, IServiceBroker {

	private static ServiceProblemManagementBrokerImpl broker;
	private final static Logger log = UtilsBusiness.getLog4J(ServiceProblemManagementBrokerImpl.class);
	
	
	/**
	 * 
	 * Metodo: retorna una instacia de ResourceProvisioningServiceBrokerImpl, patron singleton.
	 * @return ContactCoreBrokerImpl
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public static ServiceProblemManagementBrokerImpl getInstance() throws ServiceLocatorException {
		try {
			if ( broker == null ) {
				broker = new ServiceProblemManagementBrokerImpl();
			}
			return broker;
		} catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(), e);
		}
	}
	
	@Override
	public void resourceChangeDamaged(WOAttentionElementsRequestDTO attentionElements) throws BusinessException {
		log.info("== Inicia resourceChangeDamaged/ServiceProblemManagementBrokerImpl ==");
		try {
			ServiceProblemManagementPt serviceProblemManagement = null;
			ResourceChangeDamagedResponse response = null; 
			serviceProblemManagement = ServiceLocator.getInstance().getServiceProblemManagementService();
			ResourceChangeDamagedRequest request = ServiceProblemManagementTOA.populateResourceChangeDamaged( attentionElements );
			response = serviceProblemManagement.resourceChangeDamaged(request);
			log.info("== Resultado de la invocacion de la Operacion addIRDChanges RequestID: "+response.getResponseMetadata().getRequestID()+" ==");
		} catch(Throwable ex){
        	log.fatal("== Error al tratar de ejecutar la operación resourceChangeDamaged/ServiceProblemManagementBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.info("== Termina resourceChangeDamaged/ServiceProblemManagementBrokerImpl ==");
        }
	}

	@Override
	public final BusinessException manageServiceException(Throwable e) {
		
		return null;
	}

}
