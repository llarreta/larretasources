package co.com.directv.sdii.ejb.business.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.NationalIdentityCardIdentification;
import co.com.directv.sdii.dto.esb.UrbanPropertyAddress;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.core.CoreWorkOrderEventInfoLocal;
import co.com.directv.sdii.ejb.business.dealers.DomainBussinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerCharacteristicDTO;
import co.com.directv.sdii.model.dto.CustomerInfoAddressDTO;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.CustomerInfoDTO;
import co.com.directv.sdii.model.dto.CustomerInfoIndividualINamedDTO;
import co.com.directv.sdii.model.dto.CustomerInfoIndividualIdentifiedDTO;
import co.com.directv.sdii.model.dto.CustomerProductDTO;
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
import co.com.directv.sdii.model.pojo.IbsMediaContactType;
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
import co.com.directv.sdii.persistence.dao.dealers.MediaContactTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;

@Stateless(name="CoreWorkOrderEventInfoLocal",mappedName="ejb/CoreWorkOrderEventInfoLocal")
public class CoreWorkOrderEventInfo extends IBSWSBase implements CoreWorkOrderEventInfoLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWorkOrderEventInfo.class);
	
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countriesDao;
	
	@EJB(name="PostalCodesDAOLocal", beanInterface=PostalCodesDAOLocal.class)
	private PostalCodesDAOLocal postalCodesDao;

	@EJB
	private CustomerClassTypeDAOLocal customerClassTypeDAOLocal;

	@EJB private AddressTypeDAOLocal addressTypeDAOLocal;
	
	@EJB(name="DocumentTypesDAOLocal", beanInterface=DocumentTypesDAOLocal.class)
	private DocumentTypesDAOLocal documentTypesDao;

	@EJB(name="CustomerTitleDAOLocal", beanInterface=CustomerTitleDAOLocal.class)
	private CustomerTitleDAOLocal customerTitleDao;

	@EJB(name="MediaContactTypesDAOLocal", beanInterface=MediaContactTypesDAOLocal.class)
	private MediaContactTypesDAOLocal mediaContactDAO;
	
	@EJB(name="DomainBussinessBeanLocal", beanInterface=DomainBussinessBeanLocal.class)
	private DomainBussinessBeanLocal domainBussinessBeanLocal;
	
	@EJB(name="Ibs6StatusDAOLocal", beanInterface=Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal ibs6StatusDAO;

	protected static final int EMAIL_OPTION = 1;
	protected static final int FAX_OPTION = 2;
	
	public CustomerInfoAggregatedDTO populateCustomerFromIBSCust(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, 
																Country country, 
																Customer sdiiCustomer, 
																String woCountryCode, 
																WorkOrderDTO workOrderDto,
																CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException {
		CustomerValidationVista360DTO validationVista360DTO = new CustomerValidationVista360DTO();
		validationVista360DTO.setValidatePostalCodeDefault(true);
		validationVista360DTO.setValidateAllAddresses(false);
		List<String> listAdressCode = new ArrayList<String>();
		listAdressCode.add(workOrderDto.getWorkOrder().getWoAddressCode());
		validationVista360DTO.setListAdressCodesValidate(listAdressCode);
		String customerCode = pIbsWorkOrder.getCustomer().getID();
									//Ex-getVista360	
		customerInfoAggregatedDTO = this.populateFromEventInfo(country.getCountryCode(), validationVista360DTO, pIbsWorkOrder);

		if(customerInfoAggregatedDTO.isWarning()){
			workOrderDto.setWarning(true);
			workOrderDto.setErrorCode(customerInfoAggregatedDTO.getErrorCode());
			workOrderDto.setErrorMessage(customerInfoAggregatedDTO.getErrorMessage());
		}
		//Vista360ServiceTOA.
		populateCustomerFromIBSCust(customerCode, country, sdiiCustomer, woCountryCode, workOrderDto.getWorkOrder(),customerInfoAggregatedDTO);

		return customerInfoAggregatedDTO;
	}	
	
	//Ex-getVista360
	public CustomerInfoAggregatedDTO populateFromEventInfo(String sourceId,
												CustomerValidationVista360DTO validations,
            									co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder)	
            									throws BusinessException {
			log.debug("== Inicia la operación populateFromEventInfo/CoreWorkOrderEventInfo ==");
			try {

				if(validations ==null){
					validations = new CustomerValidationVista360DTO();
				}
				CustomerInfoAggregatedDTO customerInfoAggregatedDTO = this.populateCustomerInfoAggregatedDTO(pIbsWorkOrder,sourceId,validations);
				return customerInfoAggregatedDTO;
			} catch (Throwable e){
				log.error("Error al consultar la información básica del cliente", e);
				e.printStackTrace();
				throw manageServiceException(e);
			} finally {
				log.debug("== Termina la operación populateFromEventInfo/CoreWorkOrderEventInfo ==");
			}
	}
	
	/**
	 * Metodo: pobla la informacion desde IBS
	 * @param customerCode 
	 * @param country 
	 * @param sdiiCustomer 
	 * @param woCountryCode 
	 * @param woPostalCodeCode 
	 * @param sdiiWorkorder 
	 * @param customerInfoAggregatedDTO 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	// Vista360ServiceTOA
	public static void populateCustomerFromIBSCust(String customerCode, 
			                                       Country country, 
			                                       Customer sdiiCustomer, 
			                                       String woCountryCode, 
			                                       WorkOrder sdiiWorkorder,
			                                       CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException {

			validateResult("getVista360Request.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getID()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			sdiiCustomer.setCustomerCode(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerCode());
			
			sdiiCustomer.setCountry(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCountry());

			validateResult("getVista360Request.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualINamedUsing().getNames().get(0).getAristocraticTitle()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerTitle(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			sdiiCustomer.setCustomerTitle(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerTitle());

			UtilsBusiness.assertNotNull(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustType(), ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getMessage());
			sdiiCustomer.setCustType(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustType());
			
			sdiiCustomer.setAddressCode(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getAddressCode());
			sdiiCustomer.setAddressType(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getAddressType());
			sdiiCustomer.setCustomeraddress(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomeraddress());
			sdiiCustomer.setNeighborhood(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getNeighborhood());
			sdiiCustomer.setPostalCode(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getPostalCode());
			
			//Poblando la información de la dirección de la WO. NO ENTIENDO PARA QUE ESTA ACA SI NO SE RECUPERA EL VALOR y no se modifican los params
			populateWorkOrderAddress(customerInfoAggregatedDTO, sdiiWorkorder);
			
			validateResult("getVista360Request.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy().getIdentified().get(0).getCardNr()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getDocumentNumber(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode() + " No se encontró el tipo de identificación del cliente");
			sdiiCustomer.setDocumentNumber(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getDocumentNumber());
			
			if(log.isInfoEnabled()){
				log.info("El número de documento del cliente recuperado desde ibs es: " + customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getDocumentNumber());
			}
			
			validateResult("No se encontró en SDII el tipo de identificación " + country.getCountryCode(), customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getDocumentTypeId(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode() + " No se encontró el tipo de identificación del cliente");
			sdiiCustomer.setDocumentTypeId(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getDocumentTypeId());			
			sdiiCustomer.setFirstName(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getFirstName());
			sdiiCustomer.setLastName(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getLastName());
			sdiiCustomer.setLegalRepresentative(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getLegalRepresentative());

			sdiiCustomer.setCustomerMediaContacts(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerMediaContacts());
			
			sdiiCustomer.setCustomerAddresses(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerAddresses());

	}
	
	/**
	 * Metodo: Obtiene el codigo de la direccion de la wo 
	 * @param customerInfoAggregatedDTO 
	 * @param sdiiWorkorder <tipo> <descripcion> 
	 * @author cduarte, aharker
	 */
//	public static CustomerInfoAddressDTO populateWorkOrderAddress(CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
//			                                    WorkOrder sdiiWorkorder) {
	public static CustomerInfoAddressDTO populateWorkOrderAddress(CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
																WorkOrder sdiiWorkorder) {
		CustomerInfoAddressDTO customerInfoAddressDTO = null;
		String addressCode = sdiiWorkorder.getWoAddressCode();
		if(addressCode == null || addressCode.trim().length() == 0){
			log.error("No se obtuvo el código de la dirección de la WO para poblarla desde la respuesta GetCustomerAddresses");
			return customerInfoAddressDTO;
		}
		customerInfoAddressDTO = getAddressesCustomer(customerInfoAggregatedDTO,addressCode);
		
		if(customerInfoAddressDTO == null && addressCode!=null){
			customerInfoAddressDTO = getAddressesCustomer(customerInfoAggregatedDTO,null);
		}
		
		if(customerInfoAddressDTO == null){
			log.error("Ninguno de los identificadores del campo urbanPropertyAddress.getID() coincidió con el código de dirección de la WO especificado: " + addressCode + " por tanto la dirección de la WO quedará nula.");
		}else{
			String streetName   = customerInfoAddressDTO.getStreetName();
			String streetSuffix = customerInfoAddressDTO.getStreetSufix();
			String locality     = customerInfoAddressDTO.getLocality();
			String streetNrFirst = customerInfoAddressDTO.getStreetNrFirst();
			String streetNrLast = customerInfoAddressDTO.getStreetNrLast();
			
			
			StringBuffer addressBuffer = new StringBuffer();
			addressBuffer.append(streetName);
			
			// Se adicionan los campos solicitados streetNrFirst y streetNrLast a la dirección
			if(streetNrFirst !=null && streetNrFirst.trim().equalsIgnoreCase("0")){
 				addressBuffer.append(streetNrFirst);
			}
			
			if(streetNrLast !=null && streetNrLast.trim().equalsIgnoreCase("0")){
				addressBuffer.append(streetNrLast);
			}
			addressBuffer.append(streetSuffix);
			addressBuffer.append(locality);
			
			sdiiWorkorder.setWoAddress(addressBuffer.toString());
			sdiiWorkorder.setWoAddressCode(customerInfoAddressDTO.getId());
		}
		return customerInfoAddressDTO;
	}
	
	/**
	 * Metodo: obtiene la primera direccion del cliente
	 * @param customerInfoAggregatedDTO 
	 * @param addressCode 
	 * @return CustomerInfoAddressDTO <descripcion>
	 * @author
	 */
	private static CustomerInfoAddressDTO getAddressesCustomer(CustomerInfoAggregatedDTO customerInfoAggregatedDTO, 
			                                            String addressCode) {
		
		CustomerInfoAddressDTO customerInfoAddressDTO = null;
		
		if(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO() != null){
			if(addressCode!=null && addressCode.equalsIgnoreCase(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO().getId())){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO();
				return customerInfoAddressDTO;
			}else if(addressCode==null || addressCode.equals("")){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO();
				return customerInfoAddressDTO;
			}
		}
		
		if(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO() != null){
			if(addressCode!=null && addressCode.equalsIgnoreCase(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO().getId())){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO();
				return customerInfoAddressDTO;
			}else if(addressCode==null || addressCode.equals("")){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBillingAddressDTO();
				return customerInfoAddressDTO;
			}
		}
		
		if(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO() != null){
			if(addressCode!=null && addressCode.equalsIgnoreCase(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO().getId())){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO();
				return customerInfoAddressDTO;
			}else if(addressCode==null || addressCode.equals("")){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoShippingAddressDTO();
				return customerInfoAddressDTO;
			}
		}

		if(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO() != null){
			if(addressCode!=null && addressCode.equalsIgnoreCase(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO().getId())){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO();
				return customerInfoAddressDTO;
			}else if(addressCode==null || addressCode.equals("")){
				customerInfoAddressDTO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO();
				return customerInfoAddressDTO;
			}
		}
		return customerInfoAddressDTO;
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
	private CustomerInfoAggregatedDTO populateCustomerInfoAggregatedDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, 
			                                                            String sourceId, CustomerValidationVista360DTO validations) 
			                                                            throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		
		CustomerInfoAggregatedDTO customerInfoAggregatedDTO = new CustomerInfoAggregatedDTO();
		
		populateCustomerInfoDTO(pIbsWorkOrder,customerInfoAggregatedDTO,sourceId, validations);  //llena datos excel
		//Vista360ServiceTOA.
/////	populateCustomerFinancialDTO(response,customerInfoAggregatedDTO, domainBussinessBeanLocal, sourceId);
		//Vista360ServiceTOA.
/////	populateCustomerInquiriesDTO(response,customerInfoAggregatedDTO);
		//Vista360ServiceTOA.
		populateCustomerProductDTO(pIbsWorkOrder,customerInfoAggregatedDTO);                     //llena datos excel producto del cliente
		//Vista360ServiceTOA.
/////	populateCustomerCharacteristicDTO(response,customerInfoAggregatedDTO);
		//populateCustomerWorkOrderByStatusDTO(response,customerInfoAggregatedDTO);
		populateCustomerWorkOrderByStatusDTO(pIbsWorkOrder,customerInfoAggregatedDTO);
		
		return customerInfoAggregatedDTO;
		
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion basica del cliente CustomerInfoDTO
	 * @param response 
	 * @param customerInfoAggregatedDTO 
	 * @param sourceId 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	private void populateCustomerInfoDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,
			                             CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
			                             String sourceId,
			                             CustomerValidationVista360DTO validations) throws BusinessException{
		
		log.debug("== Inicia la operación populateCustomerInfoDTO/CoreWorkOrderEventInfo ==");
		try{
			
			CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
			customerInfoAggregatedDTO.setCustomerInfoDTO(customerInfoDTO);       //aca se realiza el seteo del objeto 
			
			//Vista360ServiceTOA.
			populateCustomerInfoIndividualINamed(pIbsWorkOrder,customerInfoDTO);      //completa datos del objeto
			//Vista360ServiceTOA.
			populateCustomerInfoIndividualIdentified(pIbsWorkOrder,customerInfoDTO);  //completa datos del objeto 
			//Vista360ServiceTOA.
			populateCustomerInfoAddressDTO(pIbsWorkOrder,customerInfoDTO);            //completa datos del objeto que están en el excel
			populateCustomerVO(pIbsWorkOrder,customerInfoAggregatedDTO,sourceId, validations);  // llenarrrrrrr
 			
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación populateCustomerInfoDTO/CoreWorkOrderEventInfo ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación populateCustomerInfoDTO/CoreWorkOrderEventInfo ==");
		}
	}
	
	//public void populateCustomerWorkOrderByStatusDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
	private void populateCustomerWorkOrderByStatusDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,
            										 CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		CustomerWorkOrderByStatusDTO customerWorkOrderByStatusDTO = null;
		List<CustomerWorkOrderByStatusDTO> customerWorkOrdersByStatusDTO = new ArrayList<CustomerWorkOrderByStatusDTO>();
		
		//List<com.directvla.schema.crm.common.vista360.v1.CustomerWorkOrder> customerWorkOrders = response.getGetVista360Result().getGetCustomerWorkOrderByStatusResult().getWorkorders().getCustomerWorkOrder();
		customerInfoAggregatedDTO.setCustomerWorkOrdersByStatusDTO(customerWorkOrdersByStatusDTO);
		
		List<DomainVO> domainVO = domainBussinessBeanLocal.getDomainByDomainNameDomainValueCountryId(CodesBusinessEntityEnum.GET_SDII_CODE_V360_DOMAIN_NAME_WORKORDER_TYPESERVICE.getCodeEntity(),customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCountry().getId());
		
		//for (com.directvla.schema.crm.common.vista360.v1.CustomerWorkOrder customerWorkOrder : customerWorkOrders) {
			
			customerWorkOrderByStatusDTO = new CustomerWorkOrderByStatusDTO();
			//customerWorkOrderByStatusDTO.setTypeCode(customerWorkOrder.getType());
			//customerWorkOrderByStatusDTO.setTypeName(UtilsBusiness.findDomainValue(domainVO,customerWorkOrder.getType()));
			//customerWorkOrderByStatusDTO.setDescription(customerWorkOrder.getDescription());
			//customerWorkOrderByStatusDTO.setStatusCode(customerWorkOrder.getInteractionStatus());
			customerWorkOrderByStatusDTO.setStatusCode(pIbsWorkOrder.getInteractionStatus());
			//Ibs6Status ibs6Status = ibs6StatusDAO.getIbs6StatusByIbsStateCode(customerWorkOrder.getInteractionStatus());
			Ibs6Status ibs6Status = ibs6StatusDAO.getIbs6StatusByIbsStateCode(pIbsWorkOrder.getInteractionStatus());
			
			if(ibs6Status != null){
				customerWorkOrderByStatusDTO.setStatusName(ibs6Status.getIbs6StateName());
			}
			//customerWorkOrderByStatusDTO.setInvolves(customerWorkOrder.getInvolves());
			//customerWorkOrderByStatusDTO.setInvolvesName(customerWorkOrder.getInvolvesName());
			//customerWorkOrderByStatusDTO.setInteractionDate(UtilsBusiness.dateFromGregorianCalendar(customerWorkOrder.getInteractionDate()));
			customerWorkOrderByStatusDTO.setInteractionDate(UtilsBusiness.dateFromGregorianCalendar(pIbsWorkOrder.getInteractionDate()));
			customerWorkOrdersByStatusDTO.add(customerWorkOrderByStatusDTO);
		//}
	}
	
	//vista360...TOA
	/**
	 * Metodo: Metodo que pobla la informacion financiera del cliente CustomerFinancialDTO
	 * @param getVista360Request 
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 */
	/*
	public static void populateCustomerFinancialDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response getVista360Request,CustomerInfoAggregatedDTO customerInfoAggregatedDTO, DomainBussinessBeanLocal domainBussinessBeanLocal, String countryCode) throws BusinessException, PropertiesException{
		customerInfoAggregatedDTO.setCustomerFinancialDTO(new ArrayList<CustomerFinancialDTO>());
		
		if(getVista360Request!=null
				&& getVista360Request.getGetVista360Result()!=null
				&& getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult()!=null
				&& getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice()!=null){
			
			BigDecimal totalBalance = getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getTotalAmount();
			BigDecimal overDue = getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getAmountApplied();
			BigDecimal last = getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getAmountPaid();
			BigDecimal totalAmount = getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getAmountPaid();
			Calendar terminationDate = Calendar.getInstance();
			terminationDate.setTimeInMillis(UtilsBusiness.dateFromGregorianCalendar(getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getDueDate()).getTime());
			terminationDate.add(Calendar.MONTH, -1);
			String status= getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getStatus();
			String id="";
			if(getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getFinancialAccountID()!=null){
				id = getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getFinancialAccountID().toString();
			}
			customerInfoAggregatedDTO.getCustomerFinancialDTO().add(new CustomerFinancialDTO(id
					,new Date(terminationDate.getTimeInMillis()), status, totalBalance, overDue 
					,totalAmount, last));
		}
		
		//Se coloca null para ocultar la informacion en presentacion
		if(customerInfoAggregatedDTO.getCustomerFinancialDTO()!=null && customerInfoAggregatedDTO.getCustomerFinancialDTO().isEmpty()){
			customerInfoAggregatedDTO.setCustomerFinancialDTO(null);
		}
	}
	*/
	
	/**
	 * Metodo: Permite poblar CustomerInquiriesDTO con la informacion de Inquiries del cliente
	 * @param response
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author
	 */
	/*
	public static void populateCustomerInquiriesDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,CustomerInfoAggregatedDTO customerInfoAggregatedDTO){
		customerInfoAggregatedDTO.setCustomerInquiriesDTO(new ArrayList<CustomerInquiriesDTO>());
		for(com.directvla.schema.crm.common.vista360.v1.CustomerInquiry ci: response.getGetVista360Result().getGetCustomerInquiriesByDaysResult().getCustomerinquiries().getCustomerInquiry()){
			customerInfoAggregatedDTO.getCustomerInquiriesDTO().add(new CustomerInquiriesDTO(ci.getCharacteristicValue(),UtilsBusiness.dateFromGregorianCalendar(ci.getInteractionDateComplete()),ci.getInvolvesName(),ci.getDescription(),ci.getType(), ci.getInvolvesCreationName()));
		}
	}
	*/
	/**
	 * Metodo: Metodo que pobla la informacion de los productos del cliente CustomerProductDTO
	 * @param response 
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author
	 * 
	 * ialessan abril 2014
	 * IN400990 - Descarga de Eventos
	 * Se agregan validaciones para evitar NullPointerException y falla del proceso de descarga de WOs 
	 * 
	 */
	private static void populateCustomerProductDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,
											       CustomerInfoAggregatedDTO customerInfoAggregatedDTO
    ){ 
		customerInfoAggregatedDTO.setCustomerProductDTO(new ArrayList<CustomerProductDTO>());
		if(pIbsWorkOrder.getCustomer()!=null){
			if(pIbsWorkOrder.getCustomer().getProductList()!=null){
				for(co.com.directv.sdii.dto.esb.Product product : pIbsWorkOrder.getCustomer().getProductList().getProduct()){
					if (product != null){
						customerInfoAggregatedDTO.getCustomerProductDTO().add(
								new CustomerProductDTO(	product.getName(), 
														product.getProductStatusName(), 
														product.getProvisionedDevicesRelated(), 
														product.getProvisionedDevices(), 
														product.getProductStatus()
														)
																			 );						
					}
				} 
			}
		}
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion de los nombres del cliente CustomerInfoIndividualINamedDTO
	 * @param response 
	 * @param customerInfoDTO <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	private static void populateCustomerInfoIndividualINamed(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,CustomerInfoDTO customerInfoDTO) throws BusinessException{
		
		List<CustomerInfoIndividualINamedDTO> customerInfoIndividualINamedDTOS = new ArrayList<CustomerInfoIndividualINamedDTO>();
		customerInfoDTO.setCustomerInfoIndividualINamedDTOS(customerInfoIndividualINamedDTOS);
		//List<com.directvla.schema.crm.common.vista360.v1.IndividualIName> IndividualINames = new ArrayList<com.directvla.schema.crm.common.vista360.v1.IndividualIName>(); 
		List<co.com.directv.sdii.dto.esb.IndividualName> IndividualINames = new ArrayList<co.com.directv.sdii.dto.esb.IndividualName>(); 
		//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualINamedUsing().getNames() != null){
			//if(response.getGetVista360Result() != null){
				//if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
					//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer() != null)
					if (pIbsWorkOrder.getCustomer()!=null)
					{
						//populateCustomerDummyInfo(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer());
						populateCustomerDummyInfo(pIbsWorkOrder.getCustomer());
						//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole() != null){
						if(pIbsWorkOrder.getCustomer().getIndividualRole() != null){
							//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualINamedUsing() != null){
							if(pIbsWorkOrder.getCustomer().getIndividualRole().getIndividualName() != null){
								//IndividualINames = response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualINamedUsing().getNames();
								IndividualINames = pIbsWorkOrder.getCustomer().getIndividualRole().getIndividualName();
							}
						}
					}
				//}
			//}
		//}
		
		
		CustomerInfoIndividualINamedDTO customerInfoIndividualINamedDTO = null;
		//for (com.directvla.schema.crm.common.vista360.v1.IndividualIName individualIName : IndividualINames) {
		co.com.directv.sdii.dto.esb.IndividualName individualIName = IndividualINames.get(0);
		if (individualIName != null){
			customerInfoIndividualINamedDTO = new CustomerInfoIndividualINamedDTO();
						//customerInfoIndividualINamedDTO.setFormattedName(individualIName.getFormattedName());
			customerInfoIndividualINamedDTO.setLegalName(individualIName.getLegalName());
			customerInfoIndividualINamedDTO.setAristocraticTitle(individualIName.getAristocraticTitle());
						//customerInfoIndividualINamedDTO.setFormOfAddress(individualIName.getFormOfAddress());
						//customerInfoIndividualINamedDTO.setGeneration(individualIName.getGeneration());
			customerInfoIndividualINamedDTO.setGivenNames(individualIName.getGivenNames());
						//customerInfoIndividualINamedDTO.setPreferredGivenName(individualIName.getPreferredGivenName());
						//customerInfoIndividualINamedDTO.setMiddleNames(individualIName.getMiddleNames());
						//customerInfoIndividualINamedDTO.setFamilyNamePrefix(individualIName.getFamilyNamePrefix());
			customerInfoIndividualINamedDTO.setFamilyNames(individualIName.getFamilyNames());
						//customerInfoIndividualINamedDTO.setFamilyGeneration(individualIName.getFamilyGeneration());
			customerInfoIndividualINamedDTO.setQualifications(individualIName.getQualifications());
			customerInfoIndividualINamedDTOS.add(customerInfoIndividualINamedDTO);
		}
		
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion de la identificacion individual del cliente CustomerInfoIndividualIdentifiedDTO
	 * @param response 
	 * @param customerInfoDTO <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	private static void populateCustomerInfoIndividualIdentified(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,CustomerInfoDTO customerInfoDTO) throws BusinessException{
		
		CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTO = null;
		customerInfoDTO.setCustomerInfoIndividualIdentifiedDTOS(customerInfoIndividualIdentifiedDTO);
		
		//List<com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification> nationalIdentityCardIdentifications = new ArrayList<com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification>();
		List<co.com.directv.sdii.dto.esb.IndividualIdentification> nationalIdentityCardIdentifications = new ArrayList<co.com.directv.sdii.dto.esb.IndividualIdentification>();
		//if(response.getGetVista360Result() != null){
		if(pIbsWorkOrder.getCustomer()!=null){
			if(pIbsWorkOrder.getCustomer().getIndividualRole()!=null){
				if(pIbsWorkOrder.getCustomer().getIndividualRole().getIndividualIdentification()!=null){
			//if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
				//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer() != null){
					//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole() != null){
						//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy() != null){
							//nationalIdentityCardIdentifications = response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy().getIdentified();
							nationalIdentityCardIdentifications = pIbsWorkOrder.getCustomer().getIndividualRole().getIndividualIdentification();
					//	}
				//	}
				}
			}
		}

		if(nationalIdentityCardIdentifications.isEmpty()){
			log.error("No se encontró la información de identificación del cliente de la work order");
			throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		}
		
		
		//for (com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification nationalIdentityCardIdentification : nationalIdentityCardIdentifications) {
		for (co.com.directv.sdii.dto.esb.IndividualIdentification nationalIdentityCardIdentification : nationalIdentityCardIdentifications) {
			//if(nationalIdentityCardIdentification instanceof com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification){
			if(nationalIdentityCardIdentification instanceof co.com.directv.sdii.dto.esb.NationalIdentityCardIdentification){
				customerInfoIndividualIdentifiedDTO = new CustomerInfoIndividualIdentifiedDTO();
				customerInfoIndividualIdentifiedDTO.setCardNr(((NationalIdentityCardIdentification)nationalIdentityCardIdentification).getCardNr());
				customerInfoIndividualIdentifiedDTO.setScan(((NationalIdentityCardIdentification)nationalIdentityCardIdentification).getScan());
				customerInfoDTO.setCustomerInfoIndividualIdentifiedDTOS(customerInfoIndividualIdentifiedDTO);
				
				break;
			}
		}
		
		
		/*
		if(response!=null
				&& response.getGetVista360Result()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getAliveDuring()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getAliveDuring().getStartDateTime()!=null){
		*/
		if(pIbsWorkOrder.getCustomer()!=null
				&& pIbsWorkOrder.getCustomer().getIndividualRole()!=null
				&& pIbsWorkOrder.getCustomer().getIndividualRole().getAliveDuring()!=null
				&& pIbsWorkOrder.getCustomer().getIndividualRole().getAliveDuring().getStartDateTime()!=null){
			//Date date=UtilsBusiness.dateFromGregorianCalendar(response.getGetVista360Result().getGetCustomerCrmResult()
			//		.getCustomer().getIndividualRole().getAliveDuring().getStartDateTime().getValue());
			Date date=UtilsBusiness.dateFromGregorianCalendar(pIbsWorkOrder.getCustomer().getIndividualRole().getAliveDuring().getStartDateTime());
			if(date!=null){
				customerInfoDTO.setBirthDate(new Date(date.getTime()));
			}
		}
		
		/*
		if(response!=null
				&& response.getGetVista360Result()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy()
					.getLanguage()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy().getLanguage()
					.getDialectName()!=null){
					customerInfoDTO.setDialect(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole()
						.getIndividualIdentifiedBy().getLanguage().getDialectName());
			
		}
		*/
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion basica de la direccion del cliente CustomerInfoAddressDTO
	 * @param response 
	 * @param customerInfoDTO <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 */
	private static void populateCustomerInfoAddressDTO
	(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,
			CustomerInfoDTO customerInfoDTO) throws PropertiesException{
		
		CustomerInfoAddressDTO customerInfoAddressDTO = new CustomerInfoAddressDTO();
		customerInfoDTO.setCustomerInfoAddressDTO(customerInfoAddressDTO);
		
		CustomerInfoAddressDTO customerInfoBillingAddressDTO = new CustomerInfoAddressDTO();
		customerInfoDTO.setCustomerInfoBillingAddressDTO(customerInfoBillingAddressDTO);
		
		CustomerInfoAddressDTO customerInfoShippingAddressDTO = new CustomerInfoAddressDTO();
		customerInfoDTO.setCustomerInfoShippingAddressDTO(customerInfoShippingAddressDTO);
		
		CustomerInfoAddressDTO customerInfoBankAddressDTO = new CustomerInfoAddressDTO();
		customerInfoDTO.setCustomerInfoBankAddressDTO(customerInfoBankAddressDTO);
		
		//com.directvla.schema.crm.common.vista360.v1.Address address = null;
		//co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection address=null;
		List<UrbanPropertyAddress> urbanPropertyAdreesses = null;
		if(pIbsWorkOrder.getCustomer()!=null &&
		   pIbsWorkOrder.getCustomer().getAddressList()!=null &&
		   pIbsWorkOrder.getCustomer().getAddressList().getUrbanPropertyAddress()!=null){
			urbanPropertyAdreesses =  pIbsWorkOrder.getCustomer().getAddressList().getUrbanPropertyAddress();
		}
			//if(response.getGetVista360Result() != null){
			//if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
			//if(response.getGetVista360Result().getGetCustomerCrmResult().getAddress() != null){
		if(urbanPropertyAdreesses!=null && 
		  !urbanPropertyAdreesses.isEmpty()){
			for(co.com.directv.sdii.dto.esb.UrbanPropertyAddress upa : urbanPropertyAdreesses){
				if(upa.getCategorizedByName().equals(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_DEFAULT.getCodeEntity())){
					//address = response.getGetVista360Result().getGetCustomerCrmResult().getAddress();
					//customerInfoAddressDTO.setExtraIndications(address.getAdditionalAttribute2());
					customerInfoAddressDTO.setExtraIndications(upa.getAdditionalAttribute2());
					//	customerInfoAddressDTO.setCustomerKey(address.getCustomerKey());
					//customerInfoAddressDTO.setId(address.getId());
					customerInfoAddressDTO.setId(upa.getId());
					//	customerInfoAddressDTO.setCountry(address.getCountry());
					//	customerInfoAddressDTO.setStateOrProvince(address.getStateOrProvince());
					//customerInfoAddressDTO.setStreetName(address.getStreetName());
					customerInfoAddressDTO.setStreetName(upa.getStreetName());
					//	customerInfoAddressDTO.setStreetType(address.getStreetType());
					//	customerInfoAddressDTO.setCity(address.getCity());
					//customerInfoAddressDTO.setStreetNrFirst(address.getStreetNrFirst());
					customerInfoAddressDTO.setStreetNrFirst(upa.getStreetNrFirst());
					//customerInfoAddressDTO.setStreetNrLast(address.getStreetNrLast());
					customerInfoAddressDTO.setStreetNrLast(upa.getStreetNrLast());
					//customerInfoAddressDTO.setStreetSufix(address.getStreetSufix());
					customerInfoAddressDTO.setStreetSufix(upa.getStreetSuffix());
					//customerInfoAddressDTO.setPostcode(address.getPostcode());
					customerInfoAddressDTO.setPostcode(upa.getPostCode());
					//	customerInfoAddressDTO.setElementInvolves(address.getElementInvolves());
					//customerInfoAddressDTO.setLocality(address.getLocality());
					customerInfoAddressDTO.setLocality(upa.getLocality());
					//customerInfoAddressDTO.setCareOfName(address.getCareOfName());
					customerInfoAddressDTO.setCareOfName(upa.getAdditionalAttribute1());
					//customerInfoAddressDTO.setCategorizedByname(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_DEFAULT.getCodeEntity());
					customerInfoAddressDTO.setCategorizedByname(upa.getCategorizedByName());					
				}
				
				if(upa.getCategorizedByName().equals(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_BILLING.getCodeEntity())){
					customerInfoBillingAddressDTO.setExtraIndications(upa.getAdditionalAttribute2());
					customerInfoBillingAddressDTO.setId(upa.getId());
					customerInfoBillingAddressDTO.setStreetName(upa.getStreetName());
					customerInfoBillingAddressDTO.setStreetNrFirst(upa.getStreetNrFirst());
					customerInfoBillingAddressDTO.setStreetNrLast(upa.getStreetNrLast());
					customerInfoBillingAddressDTO.setStreetSufix(upa.getStreetSuffix());
					customerInfoBillingAddressDTO.setPostcode(upa.getPostCode());
					customerInfoBillingAddressDTO.setLocality(upa.getLocality());
					customerInfoBillingAddressDTO.setCareOfName(upa.getAdditionalAttribute1());
					customerInfoAddressDTO.setCategorizedByname(upa.getCategorizedByName());					
				}

				if(upa.getCategorizedByName().equals(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_SHIPPING.getCodeEntity())){	
					customerInfoShippingAddressDTO.setExtraIndications(upa.getAdditionalAttribute2());
					customerInfoShippingAddressDTO.setId(upa.getId());
					customerInfoShippingAddressDTO.setStreetName(upa.getStreetName());
					customerInfoShippingAddressDTO.setStreetNrFirst(upa.getStreetNrFirst());
					customerInfoShippingAddressDTO.setStreetNrLast(upa.getStreetNrLast());
					customerInfoShippingAddressDTO.setStreetSufix(upa.getStreetSuffix());
					customerInfoShippingAddressDTO.setPostcode(upa.getPostCode());
					customerInfoShippingAddressDTO.setLocality(upa.getLocality());
					customerInfoShippingAddressDTO.setCareOfName(upa.getAdditionalAttribute1());
					customerInfoAddressDTO.setCategorizedByname(upa.getCategorizedByName());					
				}
				
				if(upa.getCategorizedByName().equals(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_BANK.getCodeEntity())){	
					customerInfoBankAddressDTO.setExtraIndications(upa.getAdditionalAttribute2());
					customerInfoBankAddressDTO.setId(upa.getId());
					customerInfoBankAddressDTO.setStreetName(upa.getStreetName());
					customerInfoBankAddressDTO.setStreetNrFirst(upa.getStreetNrFirst());
					customerInfoBankAddressDTO.setStreetNrLast(upa.getStreetNrLast());
					customerInfoBankAddressDTO.setStreetSufix(upa.getStreetSuffix());
					customerInfoBankAddressDTO.setPostcode(upa.getPostCode());
					customerInfoBankAddressDTO.setLocality(upa.getLocality());
					customerInfoBankAddressDTO.setCareOfName(upa.getAdditionalAttribute1());
					customerInfoAddressDTO.setCategorizedByname(upa.getCategorizedByName());					
				}				
			}
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
	private void populateCustomerVO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,
									CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
						            String sourceId,
						            CustomerValidationVista360DTO validations) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException, HelperException{
		
		CustomerVO customerVO = new CustomerVO();
		customerInfoAggregatedDTO.getCustomerInfoDTO().setCustomerVO(customerVO);
		
		//if(response.getGetVista360Result()!=null){
			//if(response.getGetVista360Result().getGetCustomerCrmResult()!=null){
				//if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer()!=null){
		if(pIbsWorkOrder.getCustomer()!=null){
					//customerVO.setCustomerCode(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getID());
					customerVO.setCustomerCode(pIbsWorkOrder.getCustomer().getID());
					//customerInfoAggregatedDTO.getCustomerInfoDTO().setCustomerStatus(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerStatus())
					//no setea getCustomerStatus(): customerInfoAggregatedDTO.getCustomerInfoDTO().setCustomerStatus(pIbsWorkOrder.getCustomer().getCustomerStatus());
		}
		
		Country country = countriesDao.getCountriesByCode(sourceId);
		//Vista360ServiceTOA.
		validateResult("No se encontró el país del cliente por el código: " + sourceId, country, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		customerVO.setCountry(country);
		
		
		//if(response.getGetVista360Result() != null){
			//if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
		if(pIbsWorkOrder.getCustomer()!=null){
				//customerVO.setCustType(populateCustomerInfoCustType(response.getGetVista360Result().getGetCustomerCrmResult(), country.getId()));
				customerVO.setCustType(populateCustomerInfoCustType(pIbsWorkOrder.getCustomer(), country.getId()));
				
				//if(response!=null
					//	&& response.getGetVista360Result()!=null
					//	&& response.getGetVista360Result().getGetCustomerCrmResult()!=null
					//	&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer()!=null
					//	&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerRank()!=null
					//	&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerClass()!=null){
				if(pIbsWorkOrder.getCustomer()!=null
						&& pIbsWorkOrder.getCustomer().getCustomerRank()!=null
						&& pIbsWorkOrder.getCustomer().getCategorizedById() != null){
					//customerVO.setCustomerClassCode(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerRank());
					customerVO.setCustomerClassCode(pIbsWorkOrder.getCustomer().getCustomerRank());
					//customerVO.setCustomerTypeCode(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getCustomerClass().toString());
					customerVO.setCustomerTypeCode(pIbsWorkOrder.getCustomer().getCategorizedById());
				}
			
		}
		
		populateCustomerVOAddresses(customerInfoAggregatedDTO, customerVO, validations);
		//populateCustomerMediaContactDTO(response,customerInfoAggregatedDTO.getCustomerInfoDTO()); 
		populateCustomerMediaContactDTO(pIbsWorkOrder,customerInfoAggregatedDTO.getCustomerInfoDTO()); 
		//populateCustomerVODocumentAndName(response,customerInfoAggregatedDTO,customerVO);
		populateCustomerVODocumentAndName(customerInfoAggregatedDTO,customerVO);
		
	}

	/**
	 * Metodo: Metodo que pobla la informacion financiera del cliente CustomerFinancialDTO
	 * @param response
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author cduarte
	 */
	/*
	public static void populateCustomerCharacteristicDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,CustomerInfoAggregatedDTO customerInfoAggregatedDTO){
		CustomerCharacteristicDTO customerCharacteristicDTO = null;
		List<CustomerCharacteristicDTO> customerCharacteristicsDTO = new ArrayList<CustomerCharacteristicDTO>();
		
		List<Characteristics> characteristics = response.getGetVista360Result().getGetCustomerCharacteristicsResult().getCharacteristicsList().getCharacteristics();
		customerInfoAggregatedDTO.setCustomerCharacteristicsDTO(customerCharacteristicsDTO);
		for (Characteristics characteristic : characteristics) {
			customerCharacteristicDTO = new CustomerCharacteristicDTO();
			customerCharacteristicDTO.setCustomerKey(characteristic.getCustomerKey());
			if(characteristic.getCharacteristicSpec()!=null){
				customerCharacteristicDTO.setCharacteristicSpecValue(characteristic.getCharacteristicSpec().getName());
				customerCharacteristicDTO.setCharacteristicSpecDescription(characteristic.getCharacteristicSpec().getDescription());
			}
			
			customerCharacteristicDTO.setCharacteristicValue(characteristic.getCharacteristicValue());
			customerCharacteristicDTO.setDescription(characteristic.getDescription());
			customerCharacteristicDTO.setId(characteristic.getID());
			customerCharacteristicDTO.setCharacteristicCountValue(characteristic.getCharacteristicCountValue());
		    customerCharacteristicsDTO.add(customerCharacteristicDTO);
		}
	}
	*/
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IServiceBroker#manageServiceException(java.lang.Throwable)
	 */
	private BusinessException manageServiceException(Throwable e) {
		BusinessException be = super.manageException(e);
		
		//String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		//String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		//if (e instanceof com.directvla.contract.crm.vista360.v1.GetVista360Exception) {
			//errorMessage = getGetVista360Exception_ExceptionMessage(((com.directvla.contract.crm.vista360.v1.GetVista360Exception)e).getFaultInfo(), errorMessage);
			//be = new BusinessException(errorCode, errorMessage);
		//}else{
			//be =super.manageException(e);
		//}
		return be;
	}

	/**
	 * Metodo: Pobla la informacion con informacion por default
	 * @param customer <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	//private static void populateCustomerDummyInfo(com.directvla.schema.crm.common.vista360.v1.CustomerCrm customer) throws BusinessException {
	private static void populateCustomerDummyInfo(co.com.directv.sdii.dto.esb.Customer customer) throws BusinessException{
		String customerTitleCode = "1";
		if(customer.getIndividualRole() == null){
			//customer.setIndividualRole(new com.directvla.schema.crm.common.vista360.v1.Individual());
			customer.setIndividualRole(new co.com.directv.sdii.dto.esb.Individual());
		}
		//if(customer.getIndividualRole().getIndividualINamedUsing().getNames().isEmpty()){
		if(customer.getIndividualRole().getIndividualName().isEmpty()){
			//com.directvla.schema.crm.common.vista360.v1.IndividualIName in = new com.directvla.schema.crm.common.vista360.v1.IndividualIName();
			co.com.directv.sdii.dto.esb.IndividualName in = new co.com.directv.sdii.dto.esb.IndividualName();
			in.setAristocraticTitle(customerTitleCode);
			//customer.getIndividualRole().getIndividualINamedUsing().getNames().add(in);
			customer.getIndividualRole().getIndividualName().add(in);
		//}else if(customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getAristocraticTitle() == null){
		}else if(customer.getIndividualRole().getIndividualName().get(0).getAristocraticTitle() == null){
			//customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).setAristocraticTitle(customerTitleCode);
			customer.getIndividualRole().getIndividualName().get(0).setAristocraticTitle(customerTitleCode);
		}
		
		//String firstName = customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getGivenNames();
		String firstName = customer.getIndividualRole().getIndividualName().get(0).getGivenNames();
		//String lastName = customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getFamilyNames();
		String lastName = customer.getIndividualRole().getIndividualName().get(0).getFamilyNames();
		
		if((firstName==null && lastName==null) || ((firstName!=null && firstName.trim().equals("")) && (lastName!=null && lastName.trim().equals("")))){
			throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage()+", Debido a que no se encontro ningun valor en firstName y en lastName.");
		}

		//if(customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getGivenNames() == null 
		if(customer.getIndividualRole().getIndividualName().get(0).getGivenNames() == null 
		//	|| customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getGivenNames().trim().length() == 0){
			|| customer.getIndividualRole().getIndividualName().get(0).getGivenNames().trim().length() == 0){
		//		customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).setGivenNames("NA");
				customer.getIndividualRole().getIndividualName().get(0).setGivenNames("NA");
		}
	}
	/**
	 * Metodo: determinar el tipo de cliente desde IBS
	 * @author
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws PropertiesException 
	 */
	//private CustomerClassType populateCustomerInfoCustType(com.directvla.schema.crm.customer.vista360.v1.GetCustomerCrmResult customer, Long countryId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
	private CustomerClassType populateCustomerInfoCustType(co.com.directv.sdii.dto.esb.Customer	customer, Long countryId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
	
		CustomerClassType customerClassTypes = null;
		String customerTypeCode = null;
		String customerClassCode = null;
		if(customer!=null){
			if(customer.getCustomerRank()!=null && customer.getCategorizedById() != null){
				customerTypeCode = customer.getCategorizedById();
				customerClassCode = customer.getCustomerRank();
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
			//CustomerInfoAddressDTO customerInfoAddressDTO = Vista360ServiceTOA.getAddressesCustomer(customerInfoAggregatedDTO,null);
			CustomerInfoAddressDTO customerInfoAddressDTO = getAddressesCustomer(customerInfoAggregatedDTO,null);
			customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoAddressDTO().getPostcode();
			//Vista360ServiceTOA.
			validateResult("customer.getCustomer().getAddressList().getUrbanPropertyAddress().get(0)", customerInfoAddressDTO, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());

			if(customerInfoAddressDTO.getId()==null){
				log.error("Error al tratar de generar la información del cliente de la work order desde IBS, no trae información de dirección: customer.getAddressList().getUrbanPropertyAddress()");
				throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			}
			//Vista360ServiceTOA.
			populateCustomerAddressesDummyInfo(customerInfoAddressDTO);
			
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
				//Vista360ServiceTOA.
				validateResult("No se encontró el streetName y el streetSuffix en la información de IBS.", streetName, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			}
			
			customer.setCustomeraddress(streetName + " " +
										  (customerInfoAddressDTO.getStreetNrFirst()==null || customerInfoAddressDTO.getStreetNrFirst().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrFirst())
										+ " "
										+ (customerInfoAddressDTO.getStreetNrLast()==null || customerInfoAddressDTO.getStreetNrLast().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrLast())
										+ streetSuffix+" "
										+ locality);
			
			customer.setNeighborhood(locality);
			
			String postalCodeCode = customerInfoAddressDTO.getPostcode();
			PostalCode postCode = postalCodesDao.getPostalCodesByCodeByCountryCode(postalCodeCode, country.getCountryCode());
			if(validations!=null && (validations.isValidatePostalCodeDefault()||validations.isValidateAllAddresses())){
				//Vista360ServiceTOA.
				validateResult("No se encontró el código postal asociado al cliente: " + postalCodeCode, postCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
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
			if(customerInfoAggregatedDTO!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO()!=null
					&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO().getId()!=null
					&& !customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO().getId().trim().equalsIgnoreCase("")){
				addressType = addressTypeDAOLocal.getAddressTypeByCode(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_BANK.getCodeEntity());
				CustomerAddresses adressBank =createCustomerAddresses(customer, customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO(), addressType,validations, country); 
				if(adressBank!=null){
					addresses.add(adressBank);
				}else{
					setWarningMessagePostalCodeAdresses(customerInfoAggregatedDTO, 
							addressType, 
							customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoBankAddressDTO().getPostcode());
				}
			}			
			customer.setCustomerAddresses(addresses);			
		}catch (Throwable e){
			log.error("Error populateCustomerVOAddresses", e);
			throw manageServiceException(e);
		} finally {
			log.info("== Termina la operación populateCustomerVOAddresses/CoreWorkOrderEventInfo ==");
		}

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
	
	//private void populateCustomerMediaContactDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
	private void populateCustomerMediaContactDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,      
            CustomerInfoDTO customerInfoDTO) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException, HelperException{

		Set<CustomerMediaContact> customerMediaContacts = new HashSet<CustomerMediaContact>();
		customerInfoDTO.getCustomerVO().setCustomerMediaContacts(customerMediaContacts);

		co.com.directv.sdii.dto.esb.ContactMediumCollection contactMediumCollection = null;;
		if(pIbsWorkOrder!=null && pIbsWorkOrder.getCustomer()!=null){
			contactMediumCollection = pIbsWorkOrder.getCustomer().getContactableVia();
		}
		
		
		String [] mobilePhones = getArrayMobileFromContactMediumListV1_1(contactMediumCollection, CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity() );
		String [] emails = getArrayFromContactMediumListV1_1(contactMediumCollection, EMAIL_OPTION);
		String [] faxes = getArrayFaxesFromContactMediumListV1_1(contactMediumCollection, CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity());
		String [] homePhones = getArrayTelephoneNumberFromContactMediumListV1_1(contactMediumCollection, CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
		String [] workPhones = getArrayTelephoneNumberFromContactMediumListV1_1(contactMediumCollection, CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity());

		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getIdEntity(MediaContactType.class.getName()), "Telefono", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity(), homePhones, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getIdEntity(MediaContactType.class.getName()), "Telefono", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity(), workPhones, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getIdEntity(MediaContactType.class.getName()), "Celular", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity(), mobilePhones, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getIdEntity(MediaContactType.class.getName()), "Email", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getCodeEntity(), emails, customerMediaContacts);
		this.populateCustomerMediaContact(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getIdEntity(MediaContactType.class.getName()), "Fax", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity(), faxes, customerMediaContacts);

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
	//private void populateCustomerVODocumentAndName(com.directvla.schema.crm.vista360.v1.GetVista360Response response,
	private void populateCustomerVODocumentAndName(CustomerInfoAggregatedDTO customerInfoAggregatedDTO,
			                                       CustomerVO customerVO) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
	
		if(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualIdentifiedDTOS()!=null){
			
			populateCustomerFromIBSCustDocumentNumberAndDocumentType(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualIdentifiedDTOS(), customerVO,customerVO.getCountry());
			
		}
		
		//Vista360ServiceTOA.
		validateResult("customer.getCustomer().getIndividualRole().getNameUsing()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		//Vista360ServiceTOA.
		validateResult("customer.getCustomer().getIndividualRole().getNameUsing().get(0)", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		//Vista360ServiceTOA.
		validateResult("customer.getCustomer().getIndividualRole().getNameUsing().get(0).getAristocraticTitle()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getAristocraticTitle(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());

		customerVO.setFirstName(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getGivenNames());
		customerVO.setLastName(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getFamilyNames());
		customerVO.setLegalRepresentative(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getLegalName());

		populateCustomerInfoFirstLastNameAndLegalRepresentative(customerInfoAggregatedDTO,customerVO);
		customerVO.setCustomerTitle(populateCustomerFromIBSCustCustomerTitle(customerInfoAggregatedDTO.getCustomerInfoDTO()));
		
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


			//Se crea y retorna el objeto CustomerAddresses basados en la informacion de los parametros 
			//y la que fue consultada en HSP+
			if(postalCodeAddress!=null){
				return new CustomerAddresses(
						customer.getId(),
						customerInfoAddressDTO.getStreetName(), 
						postalCodeAddress, 
						customerInfoAddressDTO.getStreetSufix(), 
						customerInfoAddressDTO.getLocality(), 
						customerInfoAddressDTO.getCareOfName(), 
						customerInfoAddressDTO.getExtraIndications(), 
						customerInfoAddressDTO.getId(), 
						new AddressType(type.getId()), 
						null, 
						(	customerInfoAddressDTO.getStreetNrLast()==null ||
							customerInfoAddressDTO.getStreetNrLast().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrLast()
						), 
						(	customerInfoAddressDTO.getStreetNrFirst()==null || 
							customerInfoAddressDTO.getStreetNrFirst().trim().equalsIgnoreCase("0")?"":customerInfoAddressDTO.getStreetNrFirst())
						);
			}else{
				return null;
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación populateCustomerFromIBSCust/CoreWorkOrderEventInfo ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación populateCustomerFromIBSCust/CoreWorkOrderEventInfo ==");
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
			log.info("== Termina la operación setWarningMessage/CoreWorkOrderEventInfo ==");
		}
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
        
		//MediaContactType mediaContactType = super.buidMediaContactType(mediaContactTypeId, mediaContactTypeName, mediaContactTypeCode);
		MediaContactType mediaContactType = buidMediaContactType(mediaContactTypeId, mediaContactTypeName, mediaContactTypeCode);
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
		//Vista360ServiceTOA.
		validateResult("No se encontró en SDII el tipo de identificación por el código de IBS: " + documentTypeCode + " en el país " + country.getCountryCode(), documentType, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode() + " No se encontró el tipo de identificación del cliente");
		sdiiCustomer.setDocumentTypeId(documentType.getId());
		
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
					if(customer.getCustomerInfoIndividualINamedDTOS().get(0).getAristocraticTitle()!=null && !customer.getCustomerInfoIndividualINamedDTOS().get(0).getAristocraticTitle().trim().equals("")){
		
			titleCode = customer.getCustomerInfoIndividualINamedDTOS().get(0).getAristocraticTitle();
			customerTitle = customerTitleDao.getCustomerTitleByCode(titleCode);
		}
		
		if(customerTitle == null){
			log.info("No se encontró el customerTitle en SDII por el código de ibs: " + titleCode);
			titleCode = CodesBusinessEntityEnum.CUSTOMER_TITLE_NA.getCodeEntity();
			customerTitle = customerTitleDao.getCustomerTitleByCode(titleCode);
			//Vista360ServiceTOA.
			validateResult("No se encontró el customerTitle en SDII por el código de ibs: " + titleCode, customerTitle, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		}
		
		return customerTitle;
		
	}
	
	//Ini IBSWBase.java
	protected MediaContactType buidMediaContactType(Long mediaContactTypeId, String mediaContactTypeName, String mediaContactTypeCode){
    	MediaContactType mediaContactType = new MediaContactType();
        mediaContactType.setId(mediaContactTypeId);
        mediaContactType.setMediaName(mediaContactTypeName);
        mediaContactType.setMediaCode(mediaContactTypeCode);
        return mediaContactType;
    }
	/**
	 * Metodo que arma un arreglo de String con los valores de los medios de contacto traidos de ESB que segun la parametrizacion en HSP+ coincidan con un codigo de medio de contacto
	 * @param contactMediumList medios de contacto consultados de vista360 en esb
	 * @param mediaContactCode codigo del medio de contacto en HSP+ con el que se desea comparar los traidos de ESB
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 */
	//protected String[]  (com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumList,  String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
	protected String[] getArrayMobileFromContactMediumListV1_1(co.com.directv.sdii.dto.esb.ContactMediumCollection contactMediumCollection,  String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<co.com.directv.sdii.dto.esb.ContactMedium> contactList = contactMediumCollection.getContactMedium();
		
		for (co.com.directv.sdii.dto.esb.ContactMedium contactMedium : contactList) {		
			if( contactMedium instanceof co.com.directv.sdii.dto.esb.TelephoneNumber ){
				
				co.com.directv.sdii.dto.esb.TelephoneNumber telephoneNumber = ((co.com.directv.sdii.dto.esb.TelephoneNumber)contactMedium);
					
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, telephoneNumber.getType() );	
					
				if( ibsMediaContactType != null ){
					if(telephoneNumber.getNumber() != null){
						result.add( telephoneNumber.getNumber() );
					//}else{
						//No posee este dato
						//tiposIbs.append(telephoneNumber.getType());
						//tiposIbs.append(" ");					
					//}
					}
				}
			}else if( contactMedium instanceof co.com.directv.sdii.dto.esb.FaxNumber ){
				
				co.com.directv.sdii.dto.esb.FaxNumber movileNumber = ((co.com.directv.sdii.dto.esb.FaxNumber)contactMedium);
					
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, movileNumber.getType() );	
					
				if( ibsMediaContactType != null ){
					if(movileNumber.getNumber() != null){
						result.add( movileNumber.getNumber() );
					}
				}	
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray( resultStr );
	}

	//public String[] getArrayFromContactMediumListV1_1(com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumList, int arrayResultType) {
	private String[] getArrayFromContactMediumListV1_1(co.com.directv.sdii.dto.esb.ContactMediumCollection contactMediumCollection, int arrayResultType) {
		
		List<String> result = new ArrayList<String>();

		List<co.com.directv.sdii.dto.esb.ContactMedium> contactList = contactMediumCollection.getContactMedium();
		if(contactList == null){
			return new String[0];
		}

		for (co.com.directv.sdii.dto.esb.ContactMedium contactMedium : contactList) {
			if(contactMedium instanceof co.com.directv.sdii.dto.esb.EmailContact && arrayResultType == EMAIL_OPTION){
				result.add(((co.com.directv.sdii.dto.esb.EmailContact)contactMedium).getEMailAddress()); 
			}else if(contactMedium instanceof co.com.directv.sdii.dto.esb.FaxNumber && arrayResultType == FAX_OPTION){
				result.add(((co.com.directv.sdii.dto.esb.FaxNumber)contactMedium).getNumber());
			}
		}
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Metodo que arma un arreglo de String con los valores de los medios de contacto traidos de ESB que segun la parametrizacion en HSP+ coincidan con un codigo de medio de contacto
	 * @param contactMediumList medios de contacto consultados de vista360 en esb
	 * @param mediaContactCode codigo del medio de contacto en HSP+ con el que se desea comparar los traidos de ESB
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 */
	protected String[] getArrayFaxesFromContactMediumListV1_1(co.com.directv.sdii.dto.esb.ContactMediumCollection contactMediumList,  String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();

		List<co.com.directv.sdii.dto.esb.ContactMedium> contactList = contactMediumList.getContactMedium();
		for (co.com.directv.sdii.dto.esb.ContactMedium contactMedium : contactList) {
			if( contactMedium instanceof co.com.directv.sdii.dto.esb.FaxNumber ){
				co.com.directv.sdii.dto.esb.FaxNumber fax = ((co.com.directv.sdii.dto.esb.FaxNumber)contactMedium);
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType() );	
				
				if( ibsMediaContactType != null ){
					if(fax.getNumber()!=null){	
						result.add( fax.getNumber() );
					//}else{
					//	tiposIbs.append(fax.getType());
					//	tiposIbs.append(" ");					
					}
				}	
			}
			
			if( contactMedium instanceof co.com.directv.sdii.dto.esb.TelephoneNumber ){
				co.com.directv.sdii.dto.esb.TelephoneNumber fax = ((co.com.directv.sdii.dto.esb.TelephoneNumber)contactMedium);
					IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType() );	
					
					if( ibsMediaContactType != null ){
						if(fax.getNumber()!=null){	
							result.add( fax.getNumber() );
						//}else{
						//	tiposIbs.append(fax.getType());
						//	tiposIbs.append(" ");					
						}
					}
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray( resultStr );
	}
	/**
	 * Metodo que arma un arreglo de String con los valores de los medios de contacto traidos de ESB que segun la parametrizacion en HSP+ coincidan con un codigo de medio de contacto
	 * @param contactMediumList medios de contacto consultados de vista360 en esb
	 * @param mediaContactCode codigo del medio de contacto en HSP+ con el que se desea comparar los traidos de ESB
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 */
	//protected String[] getArrayTelephoneNumberFromContactMediumListV1_1(com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumList, String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
	protected String[] getArrayTelephoneNumberFromContactMediumListV1_1(co.com.directv.sdii.dto.esb.ContactMediumCollection contactMediumList, String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {	
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<co.com.directv.sdii.dto.esb.ContactMedium> contactList = contactMediumList.getContactMedium();
		if(contactList == null){
			return new String[0];
		}
		
		for (co.com.directv.sdii.dto.esb.ContactMedium contactMedium : contactList) {
			
			 if(contactMedium instanceof co.com.directv.sdii.dto.esb.TelephoneNumber){	
				 
				co.com.directv.sdii.dto.esb.TelephoneNumber tel = (co.com.directv.sdii.dto.esb.TelephoneNumber)contactMedium;
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, tel.getType() );	
					
				if( ibsMediaContactType != null ){
					if(tel.getNumber()!=null){	
						result.add( tel.getNumber() );
					//}else{
						//tiposIbs.append(tel.getType());
						//tiposIbs.append(" ");					
					}
				}
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	//Fin 
		/**
	 * Metodo: Llena con información vacia los datos obligatorios del cliente
	 * @param customerInfoAddressDTO <tipo> <descripcion>
	 * @author
	 */
	 
	 //Ini Vista360TOA
	private static void populateCustomerAddressesDummyInfo(CustomerInfoAddressDTO customerInfoAddressDTO) {
		String emptyStr = "N/A";
		String customerLocality = customerInfoAddressDTO.getLocality();
		if(customerLocality == null || customerLocality.trim().length() == 0){
			customerInfoAddressDTO.setLocality(emptyStr);
		}
	}
		/**
	 * Metodo: Valida un objeto determinando si es nulo, en caso que sea nulo lanza
	 * una excepción y escribe en el log el atributo que fué validado
	 * @param parameterName 
	 * @param value2Validate 
	 * @param errorCode 
	 * @param errorMessage 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private static void validateResult(String parameterName, 
			                          Object value2Validate, 
			                          String errorCode, 
			                          String errorMessage) throws BusinessException{
		
		try {
			UtilsBusiness.assertNotNull(value2Validate, errorCode, errorMessage + " Nombre del parámetro: " + parameterName);
		} catch (BusinessException e) {
			log.error("== Error de validación de parámetros de respuesta de un WS de ibs: el parámetro: \""+parameterName+"\" llegó nulo ==");
			e.printStackTrace();
			throw e;
		}
		
	}
		

	/**
	 * @param buildingCode
	 * @param countryCode
	 * @param sdiiBuilding
	 * @param customerInfoAggregatedDTO
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	private static void populateBuildingFromIbsCust(String buildingCode, 
			                                String countryCode,
			                                Building sdiiBuilding,
		                                    CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException, DAOServiceException, DAOSQLException {
		
			sdiiBuilding.setCode(new Long(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerCode()));
			
			validateResult("customer.getCustomer().getIndividualRole().getNameUsing().get(0).getFamilyNames()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getFamilyNames(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			sdiiBuilding.setName(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getGivenNames() + " " + customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerInfoIndividualINamedDTOS().get(0).getFamilyNames());
			
			validateResult("customer.getCustomer().getCategorizedBy().getId()", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustType(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			//sdiiBuilding.setCustomerTypeId(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustType().getId());
			
			populateBuildingAddresses(customerInfoAggregatedDTO, sdiiBuilding);
			
			sdiiBuilding.setImportDate(UtilsBusiness.fechaActual());
			sdiiBuilding.setLastUpdateDate(UtilsBusiness.fechaActual());
		
	}
		/**
	 * Metodo: Pobla la informacion del edificio
	 * @param customerInfoAggregatedDTO 
	 * @param sdiiBuilding 
	 * @throws BusinessException 
	 * @throws DAOServiceException 
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	private static void populateBuildingAddresses(CustomerInfoAggregatedDTO customerInfoAggregatedDTO, Building sdiiBuilding) throws BusinessException, DAOServiceException, DAOSQLException{
		
		CustomerInfoAddressDTO customerInfoAddressDTO = getAddressesCustomer(customerInfoAggregatedDTO,null);
		
		if(customerInfoAddressDTO == null){
			log.error("Error al tratar de generar la información del edificio de la work order desde IBS, no trae información de dirección: customer.getAddressList().getUrbanPropertyAddress()");
			throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		}
		
		populateCustomerAddressesDummyInfo(customerInfoAddressDTO);
		
		Long addressLong = 0L;
		String addressStr = null;
		try{
			addressStr = customerInfoAddressDTO.getId();
			if(addressStr != null){
				addressLong = Long.parseLong(addressStr);
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			log.error("Al tratar de convertir a número el código de dirección de un edificio: " + addressStr, e);
		}
		
		//sdiiBuilding.setAddressCode(addressLong);
		//sdiiBuilding.setAddressType(addressLong);
		//sdiiBuilding.setAditionalIndications(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getAditionalIndications());
		Country country = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCountry();
		sdiiBuilding.setCountry(country);
		//sdiiBuilding.setAddress(customerInfoAddressDTO.getStreetName());
		
		//sdiiBuilding.setNeighborhood(customerInfoAddressDTO.getLocality());
		validateResult("No se encontró el código postal asociado al cliente ", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getPostalCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		//sdiiBuilding.setPostalCodeId(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getPostalCode().getId());
	}
	
		/**
	 * Metodo: Obtiene el valor de una característica del cliente desde el servicio getCustomerCharacteristic
	 * @param characteristicName 
	 * @param customerInfoAggregatedDTO 
	 * @return String <descripcion>
	 * @author
	 */
	private static String getCustomerCharacteristicFromIBS(String characteristicName,
			                                              CustomerInfoAggregatedDTO customerInfoAggregatedDTO) {
			logOperationInvoke("getCustomerCharacteristics", customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCountry().getCountryCode());
			
			List<CustomerCharacteristicDTO> customerCharacteristicsDTO = customerInfoAggregatedDTO.getCustomerCharacteristicsDTO();
			String ibsCharName = null; 
			for (CustomerCharacteristicDTO customerCharacteristicDTO : customerCharacteristicsDTO) {
				ibsCharName =customerCharacteristicDTO.getCharacteristicSpecValue();
				if(ibsCharName == null){
					continue;
				}
				if(ibsCharName.equalsIgnoreCase(characteristicName)){
					return customerCharacteristicDTO.getCharacteristicValue();
				}
			}
			return null;
	}
	
	/**
	 * 
	 * Metodo: Escribe en el log la operacion
	 * consumida del servicio
	 * @param operation String
	 * @param sourceId String
	 * @author cduarte
	 */
	private static void logOperationInvoke(String operation, String sourceId){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
			
		//Construcccion del mensaje
		params[0] = operation;
		params[1] = buffer.toString();
		message.append("== ");
		message.append(ErrorBusinessMessages.BUSINESS_BL188.getCode());
		message.append(" : ");
		message.append(ErrorBusinessMessages.BUSINESS_BL188.getMessage(params));
		message.append(" ==");
		log.info(message.toString());
		
	}
	
	//Fin

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderEventInfoLocal#populateBuildingFromIbsCustInfo(java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.Building, co.com.directv.sdii.dto.esb.WorkOrder)
	 */
	public void populateBuildingFromIbsCustInfo(String buildingCode, String countryCode,Building sdiiBuilding, co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder) throws BusinessException {
		log.debug("== Inicia la operación populateBuildingFromIbsCust/CoreWorkOrderEventInfo ==");
		try {
			
			CustomerValidationVista360DTO validationVista360DTO = new CustomerValidationVista360DTO();
			validationVista360DTO.setValidatePostalCodeDefault(true);
			validationVista360DTO.setValidateAllAddresses(false);
			List<String> listAdressCode = new ArrayList<String>();
			//listAdressCode.add(sdiiBuilding.getAddressCode().toString());
			validationVista360DTO.setListAdressCodesValidate(listAdressCode);
			
			//CustomerInfoAggregatedDTO customerInfoAggregatedDTO = this.getVista360(countryCode, buildingCode, validationVista360DTO);
			CustomerInfoAggregatedDTO customerInfoAggregatedDTO = this.populateFromEventInfo(countryCode, validationVista360DTO, pIbsWorkOrder);
			//Vista360ServiceTOA
			populateBuildingFromIbsCust(buildingCode, countryCode,sdiiBuilding,customerInfoAggregatedDTO);
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación populateBuildingFromIbsCust/CoreWorkOrderEventInfo ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación populateBuildingFromIbsCust/CoreWorkOrderEventInfo ==");
		}
	}
		

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderEventInfoLocal#getCustomerCharacteristicFromIBSInfo(java.lang.String, co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO)
	 */
	public String getCustomerCharacteristicFromIBSInfo(String characteristicName,
			                                       CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException {
		log.debug("== Inicia la operación getCustomerCharacteristicFromIBSInfo/CoreWorkOrderEventInfo ==");
		try {

			//return Vista360ServiceTOA.getCustomerCharacteristicFromIBS(characteristicName,customerInfoAggregatedDTO);
			return getCustomerCharacteristicFromIBS(characteristicName,customerInfoAggregatedDTO);

		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación getCustomerCharacteristicFromIBSInfo/CoreWorkOrderEventInfo ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getCustomerCharacteristicFromIBSInfo/CoreWorkOrderEventInfo ==");
		}
	}
}
