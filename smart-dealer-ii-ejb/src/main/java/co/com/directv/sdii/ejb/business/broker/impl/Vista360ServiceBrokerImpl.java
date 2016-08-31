package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.toa.Vista360ServiceTOA;
import co.com.directv.sdii.ejb.business.dealers.DomainBussinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerInfoAddressDTO;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.CustomerInfoDTO;
import co.com.directv.sdii.model.dto.CustomerInfoIndividualIdentifiedDTO;
import co.com.directv.sdii.model.dto.CustomerValidationVista360DTO;
import co.com.directv.sdii.model.dto.CustomerWorkOrderByStatusDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.AddressType;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerAddresses;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.CustomerMediaContact;
import co.com.directv.sdii.model.pojo.CustomerTitle;
import co.com.directv.sdii.model.pojo.DocumentType;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DomainVO;
import co.com.directv.sdii.persistence.dao.config.AddressTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerClassTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerTitleDAOLocal;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DocumentTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;


/**
 * Implementa la comunicación de servicios web para obtener la información del cliente desde IBS 
 * 
 * Fecha de Creación: 27/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="Vista360ServiceBrokerLocal",mappedName="ejb/Vista360ServiceBrokerLocal")
public class Vista360ServiceBrokerImpl extends IBSWSBase implements Vista360ServiceBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(Vista360ServiceBrokerImpl.class);
	
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countriesDao;
	
	@EJB private AddressTypeDAOLocal addressTypeDAOLocal;
	
	@EJB(name="DocumentTypesDAOLocal", beanInterface=DocumentTypesDAOLocal.class)
	private DocumentTypesDAOLocal documentTypesDao;
	
	@EJB(name="CustomerTitleDAOLocal", beanInterface=CustomerTitleDAOLocal.class)
	private CustomerTitleDAOLocal customerTitleDao;
	

	
	@EJB
	private CustomerClassTypeDAOLocal customerClassTypeDAOLocal;
	
	@EJB(name="PostalCodesDAOLocal", beanInterface=PostalCodesDAOLocal.class)
	private PostalCodesDAOLocal postalCodesDao;
	
	@EJB(name="Ibs6StatusDAOLocal", beanInterface=Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal ibs6StatusDAO;
	
	@EJB(name="DomainBussinessBeanLocal", beanInterface=DomainBussinessBeanLocal.class)
	private DomainBussinessBeanLocal domainBussinessBeanLocal;
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal#getVista360(java.lang.String, java.lang.String)
	 */
	public CustomerInfoAggregatedDTO getVista360(String customerKey, 
			                                     String sourceId,
			                                     CustomerValidationVista360DTO validations)	throws BusinessException {
		
		log.debug("== Inicia la operación getVista360/Vista360ServiceBrokerImpl ==");
		com.directvla.schema.crm.vista360.v1.GetVista360Request request = new com.directvla.schema.crm.vista360.v1.GetVista360Request();
		
		try {
			request.setRequestMetadata(Vista360ServiceTOA.getVista360MetadataType(sourceId));
			request.setGetVista360(Vista360ServiceTOA.getObjectGetVista360(customerKey));
			com.directvla.contract.crm.vista360.v1.PtVista360 portType = Vista360ServiceTOA.getVista360ServicePort();
			Vista360ServiceTOA.logOperationInvoke("getVista360", sourceId);
			//Se consume el servicio getVista360
			com.directvla.schema.crm.vista360.v1.GetVista360Response getVista360Response = portType.getVista360(request);
			//Se pobla la DTO CustomerInfoAggregatedDTO con la informacion que retorna ESB
			
			if(validations ==null){
				validations = new CustomerValidationVista360DTO();
			}
			CustomerInfoAggregatedDTO customerInfoAggregatedDTO = populateCustomerInfoAggregatedDTO(getVista360Response,sourceId, validations);
			return customerInfoAggregatedDTO;
		} catch (Throwable e){
			log.error("Error al consultar la información básica del cliente", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operación getVista360/Vista360ServiceBrokerImpl ==");
		}
	}
	
	/**
	 * Metodo: Permite poblar la informacion del cliente en la dto CustomerInfoAggregatedDTO
	 * @param response 
	 * @param sourceId 
	 * @return CustomerInfoAggregatedDTO 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws PropertiesException 
	 */
	private CustomerInfoAggregatedDTO populateCustomerInfoAggregatedDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response, 
			                                                            String sourceId, CustomerValidationVista360DTO validations) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		
		CustomerInfoAggregatedDTO customerInfoAggregatedDTO = new CustomerInfoAggregatedDTO();
		populateCustomerInfoDTO(response,customerInfoAggregatedDTO,sourceId, validations);
		Vista360ServiceTOA.populateCustomerFinancialDTO(response,customerInfoAggregatedDTO, domainBussinessBeanLocal, sourceId);
		Vista360ServiceTOA.populateCustomerInquiriesDTO(response,customerInfoAggregatedDTO);
		Vista360ServiceTOA.populateCustomerProductDTO(response,customerInfoAggregatedDTO);
		Vista360ServiceTOA.populateCustomerCharacteristicDTO(response,customerInfoAggregatedDTO);
		populateCustomerWorkOrderByStatusDTO(response,customerInfoAggregatedDTO);
		return customerInfoAggregatedDTO;
		
	}
	
	public void populateCustomerWorkOrderByStatusDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
            										 CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		CustomerWorkOrderByStatusDTO customerWorkOrderByStatusDTO = null;
		List<CustomerWorkOrderByStatusDTO> customerWorkOrdersByStatusDTO = new ArrayList<CustomerWorkOrderByStatusDTO>();
		
		List<com.directvla.schema.crm.common.vista360.v1.CustomerWorkOrder> customerWorkOrders = response.getGetVista360Result().getGetCustomerWorkOrderByStatusResult().getWorkorders().getCustomerWorkOrder();
		customerInfoAggregatedDTO.setCustomerWorkOrdersByStatusDTO(customerWorkOrdersByStatusDTO);
		
		List<DomainVO> domainVO = domainBussinessBeanLocal.getDomainByDomainNameDomainValueCountryId(CodesBusinessEntityEnum.GET_SDII_CODE_V360_DOMAIN_NAME_WORKORDER_TYPESERVICE.getCodeEntity(),customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCountry().getId());
		
		for (com.directvla.schema.crm.common.vista360.v1.CustomerWorkOrder customerWorkOrder : customerWorkOrders) {
			
			customerWorkOrderByStatusDTO = new CustomerWorkOrderByStatusDTO();
			customerWorkOrderByStatusDTO.setTypeCode(customerWorkOrder.getType());
			customerWorkOrderByStatusDTO.setTypeName(UtilsBusiness.findDomainValue(domainVO,customerWorkOrder.getType()));
			customerWorkOrderByStatusDTO.setDescription(customerWorkOrder.getDescription());
			customerWorkOrderByStatusDTO.setStatusCode(customerWorkOrder.getInteractionStatus());
			Ibs6Status ibs6Status = ibs6StatusDAO.getIbs6StatusByIbsStateCode(customerWorkOrder.getInteractionStatus());
			
			if(ibs6Status != null){
				customerWorkOrderByStatusDTO.setStatusName(ibs6Status.getIbs6StateName());
			}
			customerWorkOrderByStatusDTO.setInvolves(customerWorkOrder.getInvolves());
			customerWorkOrderByStatusDTO.setInvolvesName(customerWorkOrder.getInvolvesName());
			customerWorkOrderByStatusDTO.setInteractionDate(UtilsBusiness.dateFromGregorianCalendar(customerWorkOrder.getInteractionDate()));
			customerWorkOrdersByStatusDTO.add(customerWorkOrderByStatusDTO);
		}
	}

	/**
	 * Metodo: Metodo que pobla la informacion basica del cliente CustomerInfoDTO
	 * @param response 
	 * @param customerInfoAggregatedDTO 
	 * @param sourceId 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	private void populateCustomerInfoDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
			                             CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
			                             String sourceId,
			                             CustomerValidationVista360DTO validations) throws BusinessException{
		
		log.debug("== Inicia la operación populateCustomerInfoDTO/Vista360ServiceBrokerImpl ==");
		try{
			
			CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
			customerInfoAggregatedDTO.setCustomerInfoDTO(customerInfoDTO);
			
			Vista360ServiceTOA.populateCustomerInfoIndividualINamed(response,customerInfoDTO);
			Vista360ServiceTOA.populateCustomerInfoIndividualIdentified(response,customerInfoDTO);
			Vista360ServiceTOA.populateCustomerInfoAddressDTO(response,customerInfoDTO);
			populateCustomerVO(response,customerInfoAggregatedDTO,sourceId, validations);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación populateCustomerInfoDTO/Vista360ServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación populateCustomerInfoDTO/Vista360ServiceBrokerImpl ==");
		}
	}
	
	/**
	 * Metodo: Pobla la informacion del CustomerVO
	 * @param response 
	 * @param customerInfoAggregatedDTO 
	 * @param sourceId 
	 * @throws DAOServiceException 
	 * @throws DAOSQLException 
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 * @throws HelperException <tipo> <descripcion>
	 * @author cduarte
	 */
	private void populateCustomerVO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
									CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
						            String sourceId,
						            CustomerValidationVista360DTO validations) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException, HelperException{
		
		CustomerVO customerVO = new CustomerVO();
		customerInfoAggregatedDTO.getCustomerInfoDTO().setCustomerVO(customerVO);
		
		if(response.getGetVista360Result()!=null){
			if(response.getGetVista360Result().getGetCustomerCrmResult()!=null){
				if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer()!=null){
					customerVO.setCustomerCode(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getID());
					customerInfoAggregatedDTO.getCustomerInfoDTO().setCustomerStatus(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerStatus());
				}
			}
		}
		
		Country country = countriesDao.getCountriesByCode(sourceId);
		Vista360ServiceTOA.validateResult("No se encontró el país del cliente por el código: " + sourceId, country, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		customerVO.setCountry(country);
		
		
		if(response.getGetVista360Result() != null){
			if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
				customerVO.setCustType(populateCustomerInfoCustType(response.getGetVista360Result().getGetCustomerCrmResult(), country.getId()));
				
				if(response!=null
						&& response.getGetVista360Result()!=null
						&& response.getGetVista360Result().getGetCustomerCrmResult()!=null
						&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer()!=null
						&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerRank()!=null
						&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerClass()!=null){
					customerVO.setCustomerClassCode(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerRank());
					customerVO.setCustomerTypeCode(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerClass().toString());
				}
				
				
			}
		}
		
		populateCustomerVOAddresses(customerInfoAggregatedDTO, customerVO, validations);
		populateCustomerMediaContactDTO(response,customerInfoAggregatedDTO.getCustomerInfoDTO());
		populateCustomerVODocumentAndName(response,customerInfoAggregatedDTO,customerVO);
		
	}
	

	/**
	 * Metodo: determinar el tipo de cliente desde IBS
	 * @author
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws PropertiesException 
	 */
	private CustomerClassType populateCustomerInfoCustType(com.directvla.schema.crm.customer.vista360.v1.GetCustomerCrmResult customer, Long countryId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		
		CustomerClassType customerClassTypes = null;
		String customerTypeCode = null;
		String customerClassCode = null;
		if(customer.getCustomer()!=null){
			if(customer.getCustomer().getCustomerRank()!=null && customer.getCustomer().getCustomerClass()!=null){
				customerTypeCode = customer.getCustomer().getCustomerRank();
				customerClassCode = customer.getCustomer().getCustomerClass().toString();
				customerClassTypes = customerClassTypeDAOLocal.getCustomerClassTypeByCodeTypeAndClassCode(customerTypeCode, customerClassCode, countryId);
			}
		}
		//Se valida que exista el tipo de cliente en SmartDealerII
		if( customerClassTypes == null ){
			log.error("No se obtuvo la combinacion del codigo del tipo de cliente y clase de cliente desde IBS. ");
			customerTypeCode = CodesBusinessEntityEnum.CUSTOMER_CODE_TYPE_DEFAULT.getCodeEntity();
			customerClassCode = CodesBusinessEntityEnum.CUSTOMER_CODE_CLASS_DEFAULT.getCodeEntity();
			customerClassTypes = customerClassTypeDAOLocal.getCustomerClassTypeByCodeTypeAndClassCode(customerTypeCode, customerClassCode, countryId);
		}
		
		return customerClassTypes;
	}
	
	/**
	 * Metodo: Pobla la informacion del nombre y el documento del cliente
	 * @param response 
	 * @param customerInfoAggregatedDTO 
	 * @param customerVO 
	 * @throws DAOServiceException 
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 */
	private void populateCustomerVODocumentAndName(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
			                                       CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
			                                       CustomerVO customerVO) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
	
		if(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualIdentifiedDTOS()!=null){
			
			populateCustomerFromIBSCustDocumentNumberAndDocumentType(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualIdentifiedDTOS(), customerVO,customerVO.getCountry());
			
		}
		
		Vista360ServiceTOA.validateResult("customer.getCustomer().getIndividualRole().getNameUsing()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		Vista360ServiceTOA.validateResult("customer.getCustomer().getIndividualRole().getNameUsing().get(0)", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		Vista360ServiceTOA.validateResult("customer.getCustomer().getIndividualRole().getNameUsing().get(0).getAristocraticTitle()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getAristocraticTitle(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());

		customerVO.setFirstName(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getGivenNames());
		customerVO.setLastName(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getFamilyNames());
		customerVO.setLegalRepresentative(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getLegalName());

		populateCustomerInfoFirstLastNameAndLegalRepresentative(customerInfoAggregatedDTO,customerVO);
		customerVO.setCustomerTitle(populateCustomerFromIBSCustCustomerTitle(customerInfoAggregatedDTO.getCustomerInfoDTO()));
		
	}
	
	/**
	 * Metodo: Permite obtener la informacion del primer nombre, ultimo nombre y representante legal
	 * @param customer
	 * @param sdiiCustomer <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 */
	private void populateCustomerInfoFirstLastNameAndLegalRepresentative(CustomerInfoAggregatedDTO customerInfoAggregatedDTO,CustomerVO sdiiCustomer) throws PropertiesException, BusinessException{
		String firstName = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getGivenNames();
		String lastName = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getFamilyNames();
		
		sdiiCustomer.setFirstName(firstName);
		
		//Se setea el valor del lastName si no viene valor por default se coloca el parametro CUSTOMER_LAST_NAME_DEFAULT
		
		if(lastName==null || lastName.trim().equals("")){
			lastName = CodesBusinessEntityEnum.CUSTOMER_LAST_NAME_DEFAULT.getCodeEntity();
		}
		sdiiCustomer.setLastName(lastName);
		sdiiCustomer.setLegalRepresentative(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getLegalName());
	}
	
	private void populateCustomerFromIBSCustDocumentNumberAndDocumentType(CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTO, Customer sdiiCustomer,Country country) throws PropertiesException, DAOServiceException, DAOSQLException, BusinessException{
		
		//Variables utilizadas para identificar el numero de documento y el tipo de documento
		DocumentType documentType = null;
		String documentNumber = null;
		String documentTypeCode = null;
		boolean isEmptyDocumentNumber =false;
		boolean isEmptyDocumentTypeCode =false;
		
		//Si customer.getCustomer().getIndividualRole() esta vacio se coloca el customer.getCustomer().getID() como documentNumber y el tipo de documento por default
		if(customerInfoIndividualIdentifiedDTO==null){
			
			isEmptyDocumentNumber =true;
			isEmptyDocumentTypeCode =true;
			
		}else {
			
			documentNumber = customerInfoIndividualIdentifiedDTO.getCardNr();
			documentTypeCode = customerInfoIndividualIdentifiedDTO.getScan();;
		
			//Si documentNumber esta vacio se coloca el customer.getCustomer().getID() como documentNumber
			isEmptyDocumentNumber =documentNumber == null || documentNumber.trim().equals("");
			
			//Si documentTypeCode esta vacio se coloca el tipo de documento por default
			isEmptyDocumentTypeCode =documentTypeCode == null || documentTypeCode.trim().equals("") || documentTypeCode.trim().equals("0");
			
		}
		
		if(isEmptyDocumentNumber){
			log.info("No se encontró la información de identificación del cliente de la work order se va a colocar el customerCode " + sdiiCustomer.getCustomerCode() + " como documentNumber");
			documentNumber = sdiiCustomer.getCustomerCode();
		}else{
			log.info("El número de documento del cliente recuperado desde ibs es: " + documentNumber);
		}
		
		if(isEmptyDocumentTypeCode){
			log.info("No se encontró la información del tipo de identificación del cliente de la work order se va a colocar el valor por defaul " + CodesBusinessEntityEnum.DOCUMENT_TYPE_NA.getCodeEntity());
			documentTypeCode = CodesBusinessEntityEnum.DOCUMENT_TYPE_NA.getCodeEntity();
		}
		
		sdiiCustomer.setDocumentNumber(documentNumber);
		documentType = documentTypesDao.getDocumentTypesByCodeAndCountryId(documentTypeCode, sdiiCustomer.getCountry().getId());
		Vista360ServiceTOA.validateResult("No se encontró en SDII el tipo de identificación por el código de IBS: " + documentTypeCode + " en el país " + country.getCountryCode(), documentType, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode() + " No se encontró el tipo de identificación del cliente");
		sdiiCustomer.setDocumentTypeId(documentType.getId());
		
	}
	
	/**
	 * Metodo: Permite obtener el CustomerTitle, si no lo trae coloca por defaul N/A
	 * @param customer
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private CustomerTitle populateCustomerFromIBSCustCustomerTitle(co.com.directv.sdii.model.dto.CustomerInfoDTO customer) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		
		CustomerTitle customerTitle = null;
		String titleCode = null;
		
		if(customer.getCustomerInfoIndividualINamedDTOS()!=null)
				if(customer.getCustomerInfoIndividualINamedDTOS().get(0)!=null)
					if(customer.getCustomerInfoIndividualINamedDTOS().get(0).getQualifications()!=null && !customer.getCustomerInfoIndividualINamedDTOS().get(0).getQualifications().trim().equals("")){
		
			titleCode = customer.getCustomerInfoIndividualINamedDTOS().get(0).getQualifications();
			customerTitle = customerTitleDao.getCustomerTitleByCode(titleCode);
		}
		
		if(customerTitle == null){
			log.error("No se encontró el customerTitle en SDII por el código de ibs: " + titleCode);
			titleCode = CodesBusinessEntityEnum.CUSTOMER_TITLE_NA.getCodeEntity();
			customerTitle = customerTitleDao.getCustomerTitleByCode(titleCode);
			Vista360ServiceTOA.validateResult("No se encontró el customerTitle en SDII por el código de ibs: " + titleCode, customerTitle, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		}
		
		return customerTitle;
		
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion de los medios de contactos del cliente CustomerMediaContactVO
	 * @param response 
	 * @param customerInfoDTO 
	 * @throws DAOServiceException 
	 * @throws DAOSQLException 
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 * @throws HelperException <tipo> <descripcion>
	 * @author cduarte
	 */
	private void populateCustomerMediaContactDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
            CustomerInfoDTO customerInfoDTO) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException, HelperException{

		Set<CustomerMediaContact> customerMediaContacts = new HashSet<CustomerMediaContact>();
		customerInfoDTO.getCustomerVO().setCustomerMediaContacts(customerMediaContacts);
		com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumCollection = null;
		if(response.getGetVista360Result() != null){
			if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
				if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer() != null){
					contactMediumCollection = response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getContactableVia();
				}
			}
		}

		String [] mobilePhones = super.getArrayMobileFromContactMediumListV1_1(contactMediumCollection, 
				CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity());
		String [] emails = super.getArrayFromContactMediumListV1_1(contactMediumCollection, EMAIL_OPTION);
		String [] faxes = super.getArrayFaxesFromContactMediumListV1_1(contactMediumCollection, 
				CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity());
		String [] homePhones = super.getArrayTelephoneNumberFromContactMediumListV1_1(contactMediumCollection, 
				CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
		String [] workPhones = super.getArrayTelephoneNumberFromContactMediumListV1_1(contactMediumCollection, 
				CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity());

		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getIdEntity(MediaContactType.class.getName()), ApplicationTextEnum.TELEPHONE.getApplicationTextValue(), CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity(), homePhones, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getIdEntity(MediaContactType.class.getName()), ApplicationTextEnum.TELEPHONE.getApplicationTextValue(), CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity(), workPhones, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getIdEntity(MediaContactType.class.getName()), ApplicationTextEnum.CELL_PHONE.getApplicationTextValue(), CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity(), mobilePhones, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getIdEntity(MediaContactType.class.getName()), ApplicationTextEnum.MAIL.getApplicationTextValue(), CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getCodeEntity(), emails, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getIdEntity(MediaContactType.class.getName()), ApplicationTextEnum.FAX.getApplicationTextValue(), CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity(), faxes, customerMediaContacts);

	}
	
	/**
	 * Metodo: Metodo que pobla la informacion de los medios de contactos del cliente CustomerMediaContactVO
	 * @param mediaContactTypeId 
	 * @param mediaContactTypeName 
	 * @param mediaContactTypeCode 
	 * @param mediaContactValues 
	 * @param targetMediaContacts <tipo> <descripcion>
	 * @author cduarte
	 */
	private void populateCustomerMediaContact(Long mediaContactTypeId, 
			                                  String mediaContactTypeName, 
			                                  String mediaContactTypeCode, 
			                                  String [] mediaContactValues, 
			                                  Set<CustomerMediaContact> targetMediaContacts){
        
		MediaContactType mediaContactType = super.buidMediaContactType(mediaContactTypeId, mediaContactTypeName, mediaContactTypeCode);

        CustomerMediaContact customerMediaContact = null;

        if(mediaContactValues == null || mediaContactValues.length == 0){
            return;
        }

        for(String value : mediaContactValues){
        	if(value!=null && !value.trim().equals("")){
	        	customerMediaContact = new CustomerMediaContact();
	        	customerMediaContact.setMediaContactType(mediaContactType);
	        	customerMediaContact.setMediaContactValue(value);
	            targetMediaContacts.add(customerMediaContact);
            }
        }
    }
	
	/**
	 * Metodo: Obtiene la información de la ubicación geográfica del cliente a partir de la operación getCustomerAddresses del servicio de
	 * clientes se desarrolla para soportar el control de cambios de 2010-10-20
	 * @param customerInfoAggregatedDTO 
	 * @param customer información del cliente respuesta de la operación getCustomerAddresses 
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @throws DAOSQLException  En caso de error al ejecutar la operación
	 * @throws DAOServiceException En caso de error al ejecutar la operación
	 * @author cduarte 
	 */
	private void populateCustomerVOAddresses(CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
			Customer customer,
			CustomerValidationVista360DTO validations) throws BusinessException{
		try{
			CustomerInfoAddressDTO customerInfoAddressDTO = Vista360ServiceTOA.getAddressesCustomer(customerInfoAggregatedDTO,null);
			customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO().getPostcode();
			Vista360ServiceTOA.validateResult("customer.getCustomer().getAddressList().getUrbanPropertyAddress().get(0)", customerInfoAddressDTO, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());

			if(customerInfoAddressDTO.getId()==null){
				log.error("Error al tratar de generar la información del cliente de la work order desde IBS, no trae información de dirección: customer.getAddressList().getUrbanPropertyAddress()");
				throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			}
			Vista360ServiceTOA.populateCustomerAddressesDummyInfo(customerInfoAddressDTO);
			
			Long addressLong = 0L;
			String addressStr = null;
			try{
				addressStr = customerInfoAddressDTO.getId();
				if(addressStr != null){
					addressLong = Long.parseLong(addressStr);
				}
			}catch(NumberFormatException e){
				e.printStackTrace();
				log.error("Al tratar de convertir a número el código de dirección de un cliente: " + addressStr, e);
			}
			
			customer.setAddressCode(addressLong);
			customer.setAddressType(0L);
			
			customer.setAditionalIndications(customerInfoAddressDTO.getCareOfName());
			Country country = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCountry();
			customer.setCountry(country);
			
			String streetName   = customerInfoAddressDTO.getStreetName();
			String streetSuffix = customerInfoAddressDTO.getStreetSufix();
			String locality     = customerInfoAddressDTO.getLocality();

			if((streetName==null && streetSuffix==null) || (streetName.equals("") && streetSuffix.equals(""))){
				Vista360ServiceTOA.validateResult("No se encontró el streetName y el streetSuffix en la información de IBS.", streetName, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			}
			
			customer.setCustomeraddress(streetName + " " +
										  (customerInfoAddressDTO.getStreetNrFirst()==null || customerInfoAddressDTO.getStreetNrFirst().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrFirst())
										+ " "
										+ (customerInfoAddressDTO.getStreetNrLast()==null || customerInfoAddressDTO.getStreetNrLast().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrLast())
										+ streetSuffix+" "+
										locality);
			
			customer.setNeighborhood(locality);
			
			String postalCodeCode = customerInfoAddressDTO.getPostcode();
			PostalCode postCode = postalCodesDao.getPostalCodesByCodeByCountryCode(postalCodeCode, country.getCountryCode());
			if(validations!=null && (validations.isValidatePostalCodeDefault()||validations.isValidateAllAddresses())){
				//Vista360ServiceTOA.validateResult("No se encontró el código postal asociado al cliente: " + postalCodeCode, postCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
				if(postCode ==null){
					Object[] params=new Object[3];
					params[0]=customerInfoAddressDTO.getPostcode();
					params[1]=customerInfoAddressDTO.getId();
					params[2]="Default";
					List<String> parameters = new ArrayList<String>();
					parameters.add(customerInfoAddressDTO.getPostcode());
					parameters.add(customerInfoAddressDTO.getId());
					parameters.add("Default");
					throw new BusinessException(ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST_VISTA360.getCode(),
							ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST_VISTA360.getMessage(params), parameters);
				}
			}
			
			customer.setPostalCode(postCode);
			customer.setAditionalIndications(customerInfoAddressDTO.getCareOfName());
			customer.setExtraIndications(customerInfoAddressDTO.getExtraIndications());
			Set<CustomerAddresses> addresses=new HashSet<CustomerAddresses>();
			AddressType addressType = null;
			
			if(customerInfoAggregatedDTO!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO().getId()!=null
					&& !customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO().getId().trim().equalsIgnoreCase("")){
				addressType = addressTypeDAOLocal.getAddressTypeByCode(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT.getCodeEntity());
				CustomerAddresses adressDefault = createCustomerAddresses(customer, customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO(), addressType, validations , country);
				if(adressDefault!=null){
					addresses.add(adressDefault);
				}else{
					setWarningMessagePostalCodeAdresses(customerInfoAggregatedDTO, 
							addressType, 
							customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO().getPostcode());
				}
			}
			if(customerInfoAggregatedDTO!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO().getId()!=null
					&& !customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO().getId().trim().equalsIgnoreCase("")){
				addressType = addressTypeDAOLocal.getAddressTypeByCode(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_BILLING.getCodeEntity());
				CustomerAddresses adressBilling = createCustomerAddresses(customer, customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO(), addressType,validations, country); 
				if(adressBilling!=null){
					addresses.add(adressBilling);
				}else{
					setWarningMessagePostalCodeAdresses(customerInfoAggregatedDTO, 
							addressType, 
							customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO().getPostcode());
				}
			}
			if(customerInfoAggregatedDTO!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO().getId()!=null
					&& !customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO().getId().trim().equalsIgnoreCase("")){
				addressType = addressTypeDAOLocal.getAddressTypeByCode(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_SHIPPING.getCodeEntity());
				CustomerAddresses adressShipping =createCustomerAddresses(customer, customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO(), addressType,validations, country); 
				if(adressShipping!=null){
					addresses.add(adressShipping);
				}else{
					setWarningMessagePostalCodeAdresses(customerInfoAggregatedDTO, 
							addressType, 
							customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO().getPostcode());
				}
			}
			customer.setCustomerAddresses(addresses);			
		}catch (Throwable e){
			log.error("Error populateCustomerVOAddresses", e);
			throw manageServiceException(e);
		} finally {
			log.info("== Termina la operación populateCustomerVOAddresses/Vista360ServiceBrokerImpl ==");
		}

	}
	
	private void setWarningMessagePostalCodeAdresses(CustomerInfoAggregatedDTO customerInfoAggregatedDTO, AddressType addressType, String postalCode) throws BusinessException{
		try{
			String code = ErrorBusinessMessages.CORE_CR076.getCode();
			//Parametros del errror.
			Object[] params = null;
			params = new Object[2];	
			params[0] = addressType.getName();
			params[1] = postalCode;
			
			String message = ErrorBusinessMessages.CORE_CR076.getMessage(params);
			customerInfoAggregatedDTO.setErrorCode(code);
			customerInfoAggregatedDTO.setWarning(true);
			customerInfoAggregatedDTO.setErrorMessage(message);
		}catch (Throwable e){
			log.error("Error setWarningMessage", e);
			throw manageServiceException(e);
		} finally {
			log.info("== Termina la operación setWarningMessage/Vista360ServiceBrokerImpl ==");
		}
	}
	
	/**
	 * metodo encargado de crear una direccion de cliente como objeto para persistir
	 * @param customer cliente al cual pertenece la direccion
	 * @param customerInfoAddressDTO informacion de la direccion
	 * @param type tipo de direccion
	 * @param country pais del cliente
	 * @return 
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author Aharker
	 */
	private CustomerAddresses createCustomerAddresses(Customer customer, CustomerInfoAddressDTO customerInfoAddressDTO, AddressType type, CustomerValidationVista360DTO validations, Country country) throws DAOServiceException, DAOSQLException, BusinessException{
		
		try{
			//Se valida la existencia del postalCode en HSP+

			PostalCode postalCodeAddress=null;

			//Determina si debe o no validar el postal code de la dirección
			boolean isAdressCodeValidatorPostalCode = false;
			for(String adressCodeValidator: validations.getListAdressCodesValidate()){
				if(adressCodeValidator.equals(customerInfoAddressDTO.getId())){
					isAdressCodeValidatorPostalCode = true;
					break;
				}
			}

			//Determina si es la dirección default y si debe validarla segpu lo recibido como parametro 
			boolean validateAddressDefault = false;
			if(type.getCode().equals(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT.getCodeEntity())&&
					validations.isValidatePostalCodeDefault()){
				validateAddressDefault = true;
			}


			//Consulta el postal code
			if(customerInfoAddressDTO!=null
					&& customerInfoAddressDTO.getPostcode()!=null
					&& country!=null
					&& country.getCountryCode()!=null){
				postalCodeAddress = postalCodesDao.getPostalCodesByCodeByCountryCode(customerInfoAddressDTO.getPostcode(), country.getCountryCode());
			}
			
			//Valida si se debe o no consultar el portal code
			if(validations.isValidateAllAddresses()||isAdressCodeValidatorPostalCode||validateAddressDefault){
				if(postalCodeAddress==null || postalCodeAddress.getId()==null ){
					String postalCodeString = customerInfoAddressDTO!=null?customerInfoAddressDTO.getPostcode()!=null?customerInfoAddressDTO.getPostcode():"null":"null";
					Object[] params=new Object[3];
					params[0]=postalCodeString;
					params[1]=customerInfoAddressDTO.getId();
					params[2]=type.getName();
					List<String> parameters = new ArrayList<String>();
					parameters.add(postalCodeString);
					parameters.add(customerInfoAddressDTO.getId());
					parameters.add(type.getName());
					throw new BusinessException(ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST_VISTA360.getCode(),
							ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST_VISTA360.getMessage(params), parameters);
				}
			}


			//Se creav y retorna el objeto CustomerAddresses basados en la informacion de los parametros y la que fue consultada en HSP+
			if(postalCodeAddress!=null){
				return new CustomerAddresses(
						customer.getId(),
						customerInfoAddressDTO.getStreetName(), 
						postalCodeAddress, 
						customerInfoAddressDTO.getStreetSufix(), 
						customerInfoAddressDTO.getLocality(), 
						customerInfoAddressDTO.getCareOfName(), 
						customerInfoAddressDTO.getExtraIndications(), 
						customerInfoAddressDTO.getId(), new AddressType(type.getId()), null
						, (customerInfoAddressDTO.getStreetNrLast()==null || customerInfoAddressDTO.getStreetNrLast().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrLast())
						, (customerInfoAddressDTO.getStreetNrFirst()==null || customerInfoAddressDTO.getStreetNrFirst().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrFirst()));
			}else{
				return null;
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación populateCustomerFromIBSCust/Vista360ServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación populateCustomerFromIBSCust/Vista360ServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal#populateCustomerFromIBSCust(java.lang.String, co.com.directv.sdii.model.pojo.Country, co.com.directv.sdii.model.pojo.Customer, java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.WorkOrder, co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO)
	 */
	public CustomerInfoAggregatedDTO populateCustomerFromIBSCust(String customerCode, 
			                                Country country, 
			                                Customer sdiiCustomer, 
			                                String woCountryCode, 
			                                WorkOrderDTO workOrderDto,
			                                CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException {
		log.debug("== Inicia la operación populateCustomerFromIBSCust/Vista360ServiceBrokerImpl ==");
		try {
			CustomerValidationVista360DTO validationVista360DTO = new CustomerValidationVista360DTO();
			validationVista360DTO.setValidatePostalCodeDefault(true);
			validationVista360DTO.setValidateAllAddresses(false);
			List<String> listAdressCode = new ArrayList<String>();
			listAdressCode.add(workOrderDto.getWorkOrder().getWoAddressCode());
			validationVista360DTO.setListAdressCodesValidate(listAdressCode);
			
		customerInfoAggregatedDTO = this.getVista360(customerCode,country.getCountryCode(), validationVista360DTO);
			
			if(customerInfoAggregatedDTO.isWarning()){
				workOrderDto.setWarning(true);
				workOrderDto.setErrorCode(customerInfoAggregatedDTO.getErrorCode());
				workOrderDto.setErrorMessage(customerInfoAggregatedDTO.getErrorMessage());
			}
			
			Vista360ServiceTOA.populateCustomerFromIBSCust(customerCode, country, sdiiCustomer, woCountryCode, workOrderDto.getWorkOrder(),customerInfoAggregatedDTO);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación populateCustomerFromIBSCust/Vista360ServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación populateCustomerFromIBSCust/Vista360ServiceBrokerImpl ==");
		}
		return customerInfoAggregatedDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#populateBuildingFromIbsCust(java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.Building)
	 */
	public void populateBuildingFromIbsCust(String buildingCode, String countryCode,Building sdiiBuilding) throws BusinessException {
		log.debug("== Inicia la operación populateBuildingFromIbsCust/Vista360ServiceBrokerImpl ==");
		try {
			
			CustomerValidationVista360DTO validationVista360DTO = new CustomerValidationVista360DTO();
			validationVista360DTO.setValidatePostalCodeDefault(true);
			validationVista360DTO.setValidateAllAddresses(false);
			List<String> listAdressCode = new ArrayList<String>();
			//listAdressCode.add(sdiiBuilding.getAddressCode().toString());
			validationVista360DTO.setListAdressCodesValidate(listAdressCode);
			
			CustomerInfoAggregatedDTO customerInfoAggregatedDTO = this.getVista360(countryCode, buildingCode, validationVista360DTO);
			Vista360ServiceTOA.populateBuildingFromIbsCust(buildingCode, countryCode,sdiiBuilding,customerInfoAggregatedDTO);
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación populateBuildingFromIbsCust/Vista360ServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación populateBuildingFromIbsCust/Vista360ServiceBrokerImpl ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal#getCustomerCharacteristicFromIBS(java.lang.String, co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO)
	 */
	public String getCustomerCharacteristicFromIBS(String characteristicName,
			                                       CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException {
		log.debug("== Inicia la operación getCustomerCharacteristicFromIBS/Vista360ServiceBrokerImpl ==");
		try {

			return Vista360ServiceTOA.getCustomerCharacteristicFromIBS(characteristicName,customerInfoAggregatedDTO);

		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación getCustomerCharacteristicFromIBS/Vista360ServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getCustomerCharacteristicFromIBS/Vista360ServiceBrokerImpl ==");
		}
	}
	
//-----------------------------------INICIA EL MANEJO DE EXCEPCIONES---------------------------------------------------------------------------------------------------	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IServiceBroker#manageServiceException(java.lang.Throwable)
	 */
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if (e instanceof com.directvla.contract.crm.vista360.v1.GetVista360Exception) {
			errorMessage = getGetVista360Exception_ExceptionMessage(((com.directvla.contract.crm.vista360.v1.GetVista360Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else{
			be = super.manageException(e);
		}
		return be;
	}

	/**
	 * Metodo: <Descripcion>
	 * @param faultInfo 
	 * @param errorMessage 
	 * @return String <descripcion>
	 * @author cduarte
	 */
	private String getGetVista360Exception_ExceptionMessage(com.directvla.schema.crm.vista360.v1.GetVista360Exception faultInfo, 
			                                                String errorMessage) {
		
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException() != null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}
		
		return errorMessage;
	}
	//------------------------------------FIN DEL MANEJO DE EXCEPCIONES---------------------------------------------------------------------------------------------------
	
}
