/**
 * Creado 22/07/2010 11:42:22
 */
package co.com.directv.sdii.ejb.business.customer.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IBSCRMSupportAndReadinessBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.CustomerResourcesDTO;
import co.com.directv.sdii.model.dto.CustomerResponseByIdentificationResbDTO;
import co.com.directv.sdii.model.dto.CustomersResourcesResbDTO;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerAddresses;
import co.com.directv.sdii.model.pojo.CustomerMediaContact;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.persistence.dao.config.CustomerAddressesDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

import com.directvla.contract.crm.customer.v1_0.GetCustomerResourcesResult;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByIdentificationResult;
import com.directvla.schema.businessdomain.common.v1_0.Category;
import com.directvla.schema.businessdomain.common.v1_0.PhysicalResourceCollection;
import com.directvla.schema.businessdomain.customer.v1_0.CustomerCollection;


/**
 * <Descripcion>
 * 
 * Fecha de Creación: 22/07/2010
 * 
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "CustomerBusinessBeanLocal", mappedName = "ejb/CustomerBusinessBeanLocal")
public class CustomerBusinessBean extends BusinessBase implements
		CustomerBusinessBeanLocal {

	private final static Logger log = UtilsBusiness
			.getLog4J(CustomerBusinessBean.class);

	@EJB private CustomerAddressesDAOLocal customerAddressesDAOLocal;
	
	@EJB(name = "CustomerDAOLocal", beanInterface = CustomerDAOLocal.class)
	private CustomerDAOLocal customerDao;

	@EJB(name = "IBSCRMSupportAndReadinessBroker", beanInterface = IBSCRMSupportAndReadinessBrokerLocal.class)
	private IBSCRMSupportAndReadinessBrokerLocal ibsCRMSupportAndReadinessBrokerLocal;
	
	@EJB(name = "CustomerMediaContactDAOLocal", beanInterface = CustomerMediaContactDAOLocal.class)
	private CustomerMediaContactDAOLocal customerMediaContactDao;
	
	@EJB(name = "Ibs6StatusDAOLocal", beanInterface = Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal ibs6StatusDao;

	@EJB(name = "CustomerServiceBrokerLocal", beanInterface = CustomerServiceBrokerLocal.class)
	private CustomerServiceBrokerLocal customerServiceBroker;
	
	@EJB(name = "ManageWorkForceServiceBrokerLocal", beanInterface = ManageWorkForceServiceBrokerLocal.class)
	private ManageWorkForceServiceBrokerLocal manageWorkForceServiceBroker;
	
	@EJB(name="TechnologyDAOLocal",beanInterface=TechnologyDAOLocal.class)
	private TechnologyDAOLocal technologyDAO;
	
	@EJB(name="SerializedDAOLocal",beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal serializedDAO;

	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	@EJB(name = "Vista360ServiceBrokerLocal", beanInterface = Vista360ServiceBrokerLocal.class)
	private Vista360ServiceBrokerLocal vista360ServiceBroker;
	
	@EJB(name = "SystemParameterDAOLocal", beanInterface = SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal sysParamDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal#
	 * createCustomer(co.com.directv.sdii.model.vo.CustomerVO)
	 */
	@Override
	public void createCustomer(CustomerVO customer) throws BusinessException {
		log.debug("== Inicia createCustomer/CustomerBusinessBean ==");
		try {

			Customer customerPojo = customerDao.getCustomerByCode(customer.getCustomerCode());
			if (customerPojo != null) {
				log.error("El cliente que está tratando de crear como nuevo ya existe");
				throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
			}
			
			customerPojo = UtilsBusiness.copyObject(Customer.class, customer);
			customerDao.createCustomer(customerPojo);

			if(customerPojo.getCustomerAddresses()!=null){
				for(CustomerAddresses ca : customerPojo.getCustomerAddresses()){
					ca.setCustomerId(customerPojo.getId());
					customerAddressesDAOLocal.createCustomerAddresses(ca);
				}
				
			}
			
			customer.setId(customerPojo.getId());
			this.createCustomerMediaContacts(customer);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CustomerBusinessBean/createCustomer");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createCustomer/CustomerBusinessBean ==");
		}
	}

	/**
	 * Metodo: Crea la información de los medios de contacto de un cliente
	 * 
	 * @param customer
	 *            información del cliente con los medios de contacto
	 * @throws DAOServiceException
	 *             en caso de error al crear los medios de contacto
	 * @throws DAOSQLException
	 *             En caso de error al crear los medios de contacto
	 * @author jjimenezh
	 */
	private void createCustomerMediaContacts(CustomerVO customer) throws DAOServiceException, DAOSQLException {
		Set<CustomerMediaContact> custMediaContacts = customer.getCustomerMediaContacts();
		for (CustomerMediaContact customerMediaContact : custMediaContacts) {
			customerMediaContact.setCustomerId(customer.getId());
			customerMediaContactDao.createCustomerMediaContact(customerMediaContact);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal#
	 * updateCustomer(co.com.directv.sdii.model.vo.CustomerVO)
	 */
	@Override
	public void updateCustomer(CustomerVO customer) throws BusinessException {
		log.debug("== Inicia updateCustomer/CustomerBusinessBean ==");
		try {

			Customer customerPojo = UtilsBusiness.copyObject(Customer.class, customer);
			customerPojo.setCustomerMediaContacts(new HashSet<CustomerMediaContact>());
			customerPojo.setCustomerAddresses(new HashSet<CustomerAddresses>());
			customerDao.updateCustomer(customerPojo);

			this.deleteCustomerMediaContacts(customer);
			this.createCustomerMediaContacts(customer);
			
			this.customerAddressesDAOLocal.deleteCustomerAddressesByCustomerId(customerPojo.getId());
			
			for(CustomerAddresses ca:customer.getCustomerAddresses()){
				ca.setCustomerId(customerPojo.getId());
				customerAddressesDAOLocal.createCustomerAddresses(ca);
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CustomerBusinessBean/updateCustomer",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina updateCustomer/CustomerBusinessBean ==");
		}
	}

	/**
	 * Metodo: Borra la información de los medios de contacto de un cliente
	 * 
	 * @param customer
	 *            información del cliente para borrar los medios de contacto
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la operación
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	private void deleteCustomerMediaContacts(CustomerVO customer)
			throws DAOServiceException, DAOSQLException {
		customerMediaContactDao.deleteCustomerMediaContactByCustomerId(customer
				.getId());
	}

	// jvelez 07-02-2011 Métodos utilizados en el módulo de Servicio al Cliente

	

	@Override
	public CustomerInfoAggregatedDTO getCustomerInfoFromIBS(String customerKey, String sourceId) throws BusinessException {
		log.debug("== Inicia getCustomerInfoFromIBS/CustomerBusinessBean ==");
		try {
			Object[] params = null;
			params = new Object[1];
			params[0] = customerKey;
			CustomerInfoAggregatedDTO customerInfoAggregatedDTO = vista360ServiceBroker.getVista360(customerKey, sourceId, null);
			//Solo es posible consultar clientes que no sean empleados de DTV.
			if(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustType().getCustomerType().getCustomerTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.CUSTOMER_EMPLOYEE_TYPE.getCodeEntity()) )
				throw new BusinessException( ErrorBusinessMessages.CUSTOMER_CS001.getCode(),ErrorBusinessMessages.CUSTOMER_CS001.getMessageCode(params),UtilsBusiness.getParametersWS(params));
			
			// ####!####
			// Agregado enmascarado del cliente
//			customerInfoAggregatedDTO.set
			SystemParameter sp = sysParamDao
					.getSysParamByCodeAndCountryCode( 
							CodesBusinessEntityEnum.SYSTEM_PARAM_IS_CUSTOMER_INFO_MASK
									.getCodeEntity(), sourceId);
			
			String isCustomerMask = sp.getValue();
//          Se enmascara el numero de documento y el tipo de documento
			if (CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()
					.equals(isCustomerMask)) {
			String cardNumber=customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualIdentifiedDTOS().getCardNr();
			cardNumber=UtilsBusiness.maskNumber(cardNumber);
			customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualIdentifiedDTOS().setCardNr(cardNumber);
			}
//          ####!####			
			
			return customerInfoAggregatedDTO;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CustomerBusinessBean/getCustomerInfoFromIBS",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerInfoFromIBS/CustomerBusinessBean ==");
		}
	}

	

	@Override
	public CustomerResourcesResponseDTO getCustomerResourcesFromIBS(String customerKey, String sourceId) throws BusinessException {
		List<CustomersResourcesResbDTO> customersResourcesResbDTOs =ibsCRMSupportAndReadinessBrokerLocal.getCustomerResources(customerKey, sourceId);
		CustomerResourcesResponseDTO returnValue = new CustomerResourcesResponseDTO();
		returnValue.setGetCustomerResourcesResult(new GetCustomerResourcesResult());
		returnValue.getGetCustomerResourcesResult().setResourcesList(new PhysicalResourceCollection());
		for(CustomersResourcesResbDTO c: customersResourcesResbDTOs){
			com.directvla.schema.businessdomain.common.v1_0.PhysicalResource physicalResource = new 
				com.directvla.schema.businessdomain.common.v1_0.PhysicalResource(); 
			physicalResource.setSerialNumber(c.getSerial());
			physicalResource.setStatus(new Category());
			physicalResource.getStatus().setName(c.getState());
			physicalResource.setDeviceModel(new Category());
			physicalResource.getDeviceModel().setName(c.getType());
			returnValue.getGetCustomerResourcesResult().getResourcesList().getPhysicalResources().add(physicalResource);
		}
		return returnValue;
	}

	
	@Override
	public CustomersByIdentificationResponseDTO getCustomersByIdentificationFromIBS(String customerKey, String sourceId) throws BusinessException {
		log.debug("== Inicia getCustomersByIdentificationFromIBS/CustomerBusinessBean ==");
		try {
			List<CustomerResponseByIdentificationResbDTO> response = ibsCRMSupportAndReadinessBrokerLocal.getCustomersByIdentification(customerKey, sourceId);
			CustomersByIdentificationResponseDTO returnValue = new CustomersByIdentificationResponseDTO();
			returnValue.setGetCustomersByIdentificationResult(new GetCustomersByIdentificationResult());
			returnValue.getGetCustomersByIdentificationResult().setCustomerList(new CustomerCollection());
			for(CustomerResponseByIdentificationResbDTO dto: response){
				com.directvla.schema.businessdomain.customer.v1_0.Customer cust=new com.directvla.schema.businessdomain.customer.v1_0.Customer();
				cust.setID(dto.getCustomerCode());
				returnValue.getGetCustomersByIdentificationResult().getCustomerList().getCustomer().add(cust);
			}
			return returnValue;
			//return customerServiceBroker.getCustomersByIdentificationFromIBS(customerKey, sourceId);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CustomerBusinessBean/getCustomersByIdentificationFromIBS",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomersByIdentificationFromIBS/CustomerBusinessBean ==");
		} 
	}


	/*
	 * @Override(non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal#getCustomerIBSCodes(java.lang.String, java.lang.String)
	 */
	public List<String> getCustomerIBSCodes(String customerKey, String sourceId) throws BusinessException {
		log.debug("== Inicia getCustomerIBSCodes/CustomerBusinessBean ==");
		List<String> customerIBSCodes = new ArrayList<String>();
		try {
			
			List<CustomerResponseByIdentificationResbDTO> response = ibsCRMSupportAndReadinessBrokerLocal.getCustomersByIdentification(customerKey, sourceId);
			if(response != null) {
				for (CustomerResponseByIdentificationResbDTO customer : response) {
					customerIBSCodes.add(customer.getCustomerCode());
				}
			}
			
			return customerIBSCodes;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getCustomerIBSCodes/CustomerBusinessBean",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerIBSCodes/CustomerBusinessBean ==");
		} 
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal#getCustomerWorkOrdersFromManageWorkForceWSIBS(java.lang.String, java.lang.String)
	 */
	@Override
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromManageWorkForceWSIBS(
			String customerKey, String sourceId) throws BusinessException {
		log.debug("== Inicia getCustomerWorkOrdersFromManageWorkForceWSIBS/CustomerBusinessBean ==");
		try {
			//List<Ibs6Status> allIbs6Status = ibs6StatusDao.getAll();
			
			CustWorkOrdersResponseDTO result = new CustWorkOrdersResponseDTO();
			
			//for (Ibs6Status ibs6Status : allIbs6Status) {
				//CustWorkOrdersResponseDTO partialResult = manageWorkForceServiceBroker.getCustomerWorkOrdersFromIBS(customerKey, ibs6Status.getIbs6StateCode(), sourceId);
				CustWorkOrdersResponseDTO partialResult = manageWorkForceServiceBroker.getCustomerWorkOrdersFromIBS(customerKey, sourceId);
				result.getWorkOrders().addAll(partialResult.getWorkOrders());
			//}
			result.setCountryCode(sourceId);
			result.setCustomerCode(customerKey);
			return result;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CustomerBusinessBean/getInvoiceFromIBS",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerWorkOrdersFromManageWorkForceWSIBS/CustomerBusinessBean ==");
		} 
	}

	

	@Override
	public CustomerVO getCustomerResources(String customerCode, Long userId) throws BusinessException {
		log.debug("== Inicia getCustomerResources/CustomerBusinessBean ==");
		try{
			CustomerVO customer = UtilsBusiness.copyObject(CustomerVO.class, customerDao.getCustomerByCode(customerCode) );
			if ( customer != null ){
				List<CustomerResourcesDTO> response = null;
				Technology technology = technologyDAO.getTechnologyByCode( CodesBusinessEntityEnum.TECHNOLOGY_SC.getCodeEntity() );
				if( technology!=null && technology.getIbsCode()!=null ){
					
					if(userId == null){
						throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),  ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
					}
					
					User user=daoUser.getUserById(userId);
					
					if(user == null){
						throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),  ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
					}
					
					if(user.getCountry()==null){
						throw new BusinessException(ErrorBusinessMessages.CORE_CR065.getCode(), ErrorBusinessMessages.CORE_CR065.getMessage());
					}
					
					List<CustomerResourcesDTO> serviceResponse = this.customerServiceBroker.getActiveCustomerResourcesFromIBSIntoSDModel(customerCode, user.getCountry().getCountryCode());
					
					if( serviceResponse != null && !serviceResponse.isEmpty() ){
						response = new ArrayList<CustomerResourcesDTO>();
						for( int i=0 ; i<serviceResponse.size(); i++ ){
							CustomerResourcesDTO decoAuxDto = serviceResponse.get(i);
							//Si no es una SC
							if( !decoAuxDto.getTechnicalProdcutId().equals( Integer.valueOf( technology.getIbsCode() ) ) ){
								response.add( decoAuxDto );
								serviceResponse.remove(i);
								i = 0;
							} else {
								continue;
							}
						}
					}
					
					List<SerializedVO> lista = new ArrayList<SerializedVO>();
					if( response != null && !response.isEmpty() ){
						for(CustomerResourcesDTO deco : response){
							for( int i = 0 ; i < serviceResponse.size() ; i++ ){
								CustomerResourcesDTO sc = serviceResponse.get(i);
								if( sc.getProductId().equals(deco.getProductId()) ){
									deco.setLinkedResource(sc);
									serviceResponse.remove(i);
									i=0;
								}
							}
						}
						
						//Recorremos los recursos que estan en la lista para ponerlos en el objeto customer
						for(CustomerResourcesDTO recurso : response){
							SerializedVO serializedVO = new SerializedVO();
							serializedVO.setSerialCode( recurso.getSerialNumber() );
							Serialized serializedLink = new Serialized();
							serializedLink.setSerialCode( recurso.getLinkedResource().getSerialNumber() );
							serializedVO.setSerialized( serializedLink );
							
							lista.add(serializedVO);
						}
					}
					
					customer.setCustomerResources(lista);
					
				}
			}
			return customer;
		} catch( Throwable ex ) {
			log.error("== Error al tratar de ejecutar la operación getCustomerResources/CustomerBusinessBean ==");
	    	throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerResources/CustomerBusinessBean ==");
		}	
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal#getCustomerByDocTypeIdDocNumberAndIbsCode(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public List<CustomerVO> getCustomerByDocTypeIdDocNumberAndIbsCode(Long documentTypeId,
															  		String documentNumber, 
															  		String ibsCode,
															  		Long countryId) throws BusinessException {
			
		    List<CustomerVO> clientResponseList = null;
		
			log.debug("== Inicia getCustomerByDocTypeIdDocNumberAndIbsCode/CustomerBusinessBean ==");
			try {
	
				clientResponseList = customerDao
						.getCustomerByDocTypeIdDocNumberAndIbsCode(documentTypeId,
								documentNumber, ibsCode, countryId);
				
				SystemParameter sp = sysParamDao.getSysParamByCodeAndCountryId(
						CodesBusinessEntityEnum.SYSTEM_PARAM_IS_CUSTOMER_INFO_MASK
						.getCodeEntity(), countryId);
				String isCustomerMask = sp.getValue();
				//Verifica si para el pais correspondiente debe enmascarar los datos o no
				if (clientResponseList!=null && CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(
						isCustomerMask)) {
					for (CustomerVO customers : clientResponseList) {
						customers.setDocumentNumber(UtilsBusiness
								.maskNumber(documentNumber));
						customers.setDocumentTypeName(UtilsBusiness
								.maskString(customers.getDocumentTypeName()));
					}
				}
				// ####!#### Enmascarado datos del cliente
				return clientResponseList;
				
			} catch (Throwable ex) {
				log.error("== Error al tratar de ejecutar la operación getCustomerByDocTypeIdDocNumberAndIbsCode/CustomerBusinessBean",ex);
				throw super.manageException(ex);
			} finally {
				log.debug("== Termina getCustomerByDocTypeIdDocNumberAndIbsCode/CustomerBusinessBean ==");
			}
	}

}
