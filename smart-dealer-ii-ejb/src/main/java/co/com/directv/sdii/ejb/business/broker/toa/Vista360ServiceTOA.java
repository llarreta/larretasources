package co.com.directv.sdii.ejb.business.broker.toa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.dealers.DomainBussinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.CustomerCharacteristicDTO;
import co.com.directv.sdii.model.dto.CustomerFinancialDTO;
import co.com.directv.sdii.model.dto.CustomerInfoAddressDTO;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.CustomerInfoDTO;
import co.com.directv.sdii.model.dto.CustomerInfoIndividualINamedDTO;
import co.com.directv.sdii.model.dto.CustomerInfoIndividualIdentifiedDTO;
import co.com.directv.sdii.model.dto.CustomerInquiriesDTO;
import co.com.directv.sdii.model.dto.CustomerProductDTO;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.WorkOrder;

import com.directvla.schema.businessdomain.common.vista360.v1_0.Characteristics;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 4/07/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class Vista360ServiceTOA {
	
	private final static Logger log = UtilsBusiness.getLog4J(Vista360ServiceTOA.class);
	
	/**
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customer
	 * @return PtVista360 Puerto de invocacion para el WS de RESB de customer
	 * @throws ServiceLocatorException 
	 */
	public static com.directvla.contract.crm.vista360.v1.PtVista360 getVista360ServicePort() throws ServiceLocatorException  {
		com.directvla.contract.crm.vista360.v1.PtVista360 ptVista360 = ServiceLocator.getInstance().getVista360Service();
		return ptVista360;
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones relacionadas con el servicio de clientes
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del servicio web de clientes
	 * @author cduarte
	 */
	public static com.directvla.schema.crm.common.vista360.v1.RequestMetadataType getVista360MetadataType(String countryCode) {
		com.directvla.schema.crm.common.vista360.v1.RequestMetadataType requestMetadataType = new com.directvla.schema.crm.common.vista360.v1.RequestMetadataType();
		requestMetadataType.setRequestID(countryCode);
		requestMetadataType.setSourceID(countryCode);
		return requestMetadataType;
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones relacionadas con el servicio de clientes
	 * @param customerKey 
	 * @return com.directvla.schema.crm.vista360.v1.GetVista360
	 * @throws NumberFormatException 
	 * @throws PropertiesException <tipo> <descripcion> 
	 * @author cduarte
	 */
	public static com.directvla.schema.crm.vista360.v1.GetVista360 getObjectGetVista360(String customerKey) throws NumberFormatException, PropertiesException {

		com.directvla.schema.crm.vista360.v1.GetVista360 infoCustomer = new com.directvla.schema.crm.vista360.v1.GetVista360();
		if(customerKey!=null)
			customerKey = customerKey.trim();
		infoCustomer.setCustomerNr(customerKey.trim());
		int days;
		Integer i = new Integer(CodesBusinessEntityEnum.GET_VISTA_360_DAYS.getCodeEntity());
		days = i.intValue();
		infoCustomer.setDays(days);
		infoCustomer.setStatus(CodesBusinessEntityEnum.GET_VISTA_360_STATUS.getCodeEntity());
		return infoCustomer;
		
	}
	
	/**
	 * Metodo: Permite poblar CustomerInquiriesDTO con la informacion de Inquiries del cliente
	 * @param response
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author
	 */
	public static void populateCustomerInquiriesDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,CustomerInfoAggregatedDTO customerInfoAggregatedDTO){
		customerInfoAggregatedDTO.setCustomerInquiriesDTO(new ArrayList<CustomerInquiriesDTO>());
		for(com.directvla.schema.crm.common.vista360.v1.CustomerInquiry ci: response.getGetVista360Result().getGetCustomerInquiriesByDaysResult().getCustomerinquiries().getCustomerInquiry()){
			customerInfoAggregatedDTO.getCustomerInquiriesDTO().add(new CustomerInquiriesDTO(ci.getCharacteristicValue(),UtilsBusiness.dateFromGregorianCalendar(ci.getInteractionDateComplete()),ci.getInvolvesName(),ci.getDescription(),ci.getType(), ci.getInvolvesCreationName()));
		}
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion financiera del cliente CustomerFinancialDTO
	 * @param getVista360Request 
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 */
	public static void populateCustomerFinancialDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response getVista360Request,CustomerInfoAggregatedDTO customerInfoAggregatedDTO, DomainBussinessBeanLocal domainBussinessBeanLocal, String countryCode) throws BusinessException, PropertiesException{
		customerInfoAggregatedDTO.setCustomerFinancialDTO(new ArrayList<CustomerFinancialDTO>());
		
		if(getVista360Request!=null
				&& getVista360Request.getGetVista360Result()!=null
				&& getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult()!=null
				&& getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice()!=null){
			
			BigDecimal totalBalance = new BigDecimal(0);
			List<com.directvla.schema.businessdomain.customer.vista360.v1_0.CustomerAccount> customerAccountList = getVista360Request.getGetVista360Result().getGetCustomerFinancialInfoResult().getCustomer().getCustomerAccountList().getCustomerAccount();
			totalBalanceLoop:{
				for(com.directvla.schema.businessdomain.customer.vista360.v1_0.CustomerAccount ca : customerAccountList){
					List<com.directvla.schema.businessdomain.customer.vista360.v1_0.CustomerAccountChargeSumBalance> caChargeSumBalanceList = ca.getCustomerAccountChargeSumBalanceCollection().getCustomerAccountChargeSumBalance();
					for(com.directvla.schema.businessdomain.customer.vista360.v1_0.CustomerAccountChargeSumBalance caChargeSumBalance : caChargeSumBalanceList){
						if("Balance".equals(caChargeSumBalance.getValidFor())){
							totalBalance = caChargeSumBalance.getCustomerAccountChargeSum().getAmount();
							break totalBalanceLoop;
						}
					}
				}
			}
			
			//totalBalance = getVista360Request.getGetVista360Result().getGetCustomerLastInvoiceResult().getInvoice().getTotalAmount();
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
	
	/**
	 * Metodo: Metodo que pobla la informacion financiera del cliente CustomerFinancialDTO
	 * @param response
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author cduarte
	 */
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

	/**
	 * Metodo: Metodo que pobla la informacion de los productos del cliente CustomerProductDTO
	 * @param response 
	 * @param customerInfoAggregatedDTO <tipo> <descripcion>
	 * @author
	 */
	public static void populateCustomerProductDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,CustomerInfoAggregatedDTO customerInfoAggregatedDTO){ 
		customerInfoAggregatedDTO.setCustomerProductDTO(new ArrayList<CustomerProductDTO>());
		if(response.getGetVista360Result()!=null && response.getGetVista360Result().getGetCustomerProductByStatusResult()!=null  
				&& response.getGetVista360Result().getGetCustomerProductByStatusResult().getProducts()!=null  
				&& response.getGetVista360Result().getGetCustomerProductByStatusResult().getProducts().getProduct()!=null){
			for(com.directvla.schema.crm.common.vista360.v1.CustomerProduct cp : response.getGetVista360Result().getGetCustomerProductByStatusResult().getProducts().getProduct()){
				customerInfoAggregatedDTO.getCustomerProductDTO().add(new CustomerProductDTO(cp.getProductName(), cp.getProductStatusName(), cp.getProvisionedDevicesRelated(), cp.getProvisionedDevices(), cp.getProductStatus()));
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
	public static void populateCustomerInfoIndividualINamed(com.directvla.schema.crm.vista360.v1.GetVista360Response response,CustomerInfoDTO customerInfoDTO) throws BusinessException{
		
		List<CustomerInfoIndividualINamedDTO> customerInfoIndividualINamedDTOS = new ArrayList<CustomerInfoIndividualINamedDTO>();
		customerInfoDTO.setCustomerInfoIndividualINamedDTOS(customerInfoIndividualINamedDTOS);
		List<com.directvla.schema.crm.common.vista360.v1.IndividualIName> IndividualINames = new ArrayList<com.directvla.schema.crm.common.vista360.v1.IndividualIName>(); 
		if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualINamedUsing().getNames() != null){
			if(response.getGetVista360Result() != null){
				if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
					if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer() != null)
					{
						populateCustomerDummyInfo(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer());
						if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole() != null){
							if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualINamedUsing() != null){
								IndividualINames = response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualINamedUsing().getNames();
							}
						}
					}
				}
			}
		}
		
		
		CustomerInfoIndividualINamedDTO customerInfoIndividualINamedDTO = null;
		for (com.directvla.schema.crm.common.vista360.v1.IndividualIName individualIName : IndividualINames) {
			customerInfoIndividualINamedDTO = new CustomerInfoIndividualINamedDTO();
			customerInfoIndividualINamedDTO.setFormattedName(individualIName.getFormattedName());
			customerInfoIndividualINamedDTO.setLegalName(individualIName.getLegalName());
			customerInfoIndividualINamedDTO.setAristocraticTitle(individualIName.getAristocraticTitle());
			customerInfoIndividualINamedDTO.setFormOfAddress(individualIName.getFormOfAddress());
			customerInfoIndividualINamedDTO.setGeneration(individualIName.getGeneration());
			customerInfoIndividualINamedDTO.setGivenNames(individualIName.getGivenNames());
			customerInfoIndividualINamedDTO.setPreferredGivenName(individualIName.getPreferredGivenName());
			customerInfoIndividualINamedDTO.setMiddleNames(individualIName.getMiddleNames());
			customerInfoIndividualINamedDTO.setFamilyNamePrefix(individualIName.getFamilyNamePrefix());
			customerInfoIndividualINamedDTO.setFamilyNames(individualIName.getFamilyNames());
			customerInfoIndividualINamedDTO.setFamilyGeneration(individualIName.getFamilyGeneration());
			customerInfoIndividualINamedDTO.setQualifications(individualIName.getQualifications());
			customerInfoIndividualINamedDTOS.add(customerInfoIndividualINamedDTO);
		}
		
	}
	
	/**
	 * Metodo: Pobla la informacion con informacion por default
	 * @param customer <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	private static void populateCustomerDummyInfo(com.directvla.schema.crm.common.vista360.v1.CustomerCrm customer) throws BusinessException {
		String customerTitleCode = "1";
		if(customer.getIndividualRole() == null){
			customer.setIndividualRole(new com.directvla.schema.crm.common.vista360.v1.Individual());
		}
		if(customer.getIndividualRole().getIndividualINamedUsing().getNames().isEmpty()){
			com.directvla.schema.crm.common.vista360.v1.IndividualIName in = new com.directvla.schema.crm.common.vista360.v1.IndividualIName();
			in.setAristocraticTitle(customerTitleCode);
			customer.getIndividualRole().getIndividualINamedUsing().getNames().add(in);
		}else if(customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getAristocraticTitle() == null){
			customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).setAristocraticTitle(customerTitleCode);
		}
		
		String firstName = customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getGivenNames();
		String lastName = customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getFamilyNames();
		
		if((firstName==null && lastName==null) || ((firstName!=null && firstName.trim().equals("")) && (lastName!=null && lastName.trim().equals("")))){
			throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage()+", Debido a que no se encontro ningun valor en firstName y en lastName.");
		}

		if(customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getGivenNames() == null 
			|| customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).getGivenNames().trim().length() == 0){
				customer.getIndividualRole().getIndividualINamedUsing().getNames().get(0).setGivenNames("NA");
		}
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion basica de la direccion del cliente CustomerInfoAddressDTO
	 * @param response 
	 * @param customerInfoDTO <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 */
	public static void populateCustomerInfoAddressDTO(com.directvla.schema.crm.vista360.v1.GetVista360Response response,CustomerInfoDTO customerInfoDTO) throws PropertiesException{
		
		CustomerInfoAddressDTO customerInfoAddressDTO = new CustomerInfoAddressDTO();
		customerInfoDTO.setCustomerInfoAddressDTO(customerInfoAddressDTO);
		com.directvla.schema.crm.common.vista360.v1.Address address = null;
		if(response.getGetVista360Result() != null){
			if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
				if(response.getGetVista360Result().getGetCustomerCrmResult().getAddress() != null){
					
					address = response.getGetVista360Result().getGetCustomerCrmResult().getAddress();
					customerInfoAddressDTO.setExtraIndications(address.getAdditionalAttribute2());
					customerInfoAddressDTO.setCustomerKey(address.getCustomerKey());
					customerInfoAddressDTO.setId(address.getId());
					customerInfoAddressDTO.setCountry(address.getCountry());
					customerInfoAddressDTO.setStateOrProvince(address.getStateOrProvince());
					customerInfoAddressDTO.setStreetName(address.getStreetName());
					customerInfoAddressDTO.setStreetType(address.getStreetType());
					customerInfoAddressDTO.setCity(address.getCity());
					customerInfoAddressDTO.setStreetNrFirst(address.getStreetNrFirst());
					customerInfoAddressDTO.setStreetNrLast(address.getStreetNrLast());
					customerInfoAddressDTO.setStreetSufix(address.getStreetSufix());
					customerInfoAddressDTO.setPostcode(address.getPostcode());
					customerInfoAddressDTO.setElementInvolves(address.getElementInvolves());
					customerInfoAddressDTO.setLocality(address.getLocality());
					customerInfoAddressDTO.setCareOfName(address.getCareOfName());
					customerInfoAddressDTO.setCategorizedByname(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_DEFAULT.getCodeEntity());
					
				}
			}
		}
		
		CustomerInfoAddressDTO customerInfoBillingAddressDTO = new CustomerInfoAddressDTO();
		customerInfoDTO.setCustomerInfoBillingAddressDTO(customerInfoBillingAddressDTO);
		com.directvla.schema.crm.common.vista360.v1.Address billingAddress = null;
		if(response.getGetVista360Result() != null){
			if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
				if(response.getGetVista360Result().getGetCustomerCrmResult().getBillingAddress() != null){
						
					billingAddress = response.getGetVista360Result().getGetCustomerCrmResult().getBillingAddress();
					customerInfoBillingAddressDTO.setExtraIndications(billingAddress.getAdditionalAttribute2());
					customerInfoBillingAddressDTO.setCustomerKey(billingAddress.getCustomerKey());
					customerInfoBillingAddressDTO.setId(billingAddress.getId());
					customerInfoBillingAddressDTO.setCountry(billingAddress.getCountry());
					customerInfoBillingAddressDTO.setStateOrProvince(billingAddress.getStateOrProvince());
					customerInfoBillingAddressDTO.setStreetName(billingAddress.getStreetName());
					customerInfoBillingAddressDTO.setStreetType(billingAddress.getStreetType());
					customerInfoBillingAddressDTO.setCity(billingAddress.getCity());
					customerInfoBillingAddressDTO.setStreetNrFirst(billingAddress.getStreetNrFirst());
					customerInfoBillingAddressDTO.setStreetNrLast(billingAddress.getStreetNrLast());
					customerInfoBillingAddressDTO.setStreetSufix(billingAddress.getStreetSufix());
					customerInfoBillingAddressDTO.setPostcode(billingAddress.getPostcode());
					customerInfoBillingAddressDTO.setElementInvolves(billingAddress.getElementInvolves());
					customerInfoBillingAddressDTO.setLocality(billingAddress.getLocality());
					customerInfoBillingAddressDTO.setCareOfName(billingAddress.getCareOfName());
					customerInfoBillingAddressDTO.setCategorizedByname(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_BILLING.getCodeEntity());
					
				}
			}
		}
		
		CustomerInfoAddressDTO customerInfoShippingAddressDTO = new CustomerInfoAddressDTO();
		customerInfoDTO.setCustomerInfoShippingAddressDTO(customerInfoShippingAddressDTO);
		com.directvla.schema.crm.common.vista360.v1.Address shippingAddress = null;
		if(response.getGetVista360Result() != null){
			if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
				if(response.getGetVista360Result().getGetCustomerCrmResult().getShippingAddress() != null){
					
					shippingAddress = response.getGetVista360Result().getGetCustomerCrmResult().getShippingAddress();
					customerInfoShippingAddressDTO.setExtraIndications(shippingAddress.getAdditionalAttribute2());
					customerInfoShippingAddressDTO.setCustomerKey(shippingAddress.getCustomerKey());
					customerInfoShippingAddressDTO.setId(shippingAddress.getId());
					customerInfoShippingAddressDTO.setCountry(shippingAddress.getCountry());
					customerInfoShippingAddressDTO.setStateOrProvince(shippingAddress.getStateOrProvince());
					customerInfoShippingAddressDTO.setStreetName(shippingAddress.getStreetName());
					customerInfoShippingAddressDTO.setStreetType(shippingAddress.getStreetType());
					customerInfoShippingAddressDTO.setCity(shippingAddress.getCity());
					customerInfoShippingAddressDTO.setStreetNrFirst(shippingAddress.getStreetNrFirst());
					customerInfoShippingAddressDTO.setStreetNrLast(shippingAddress.getStreetNrLast());
					customerInfoShippingAddressDTO.setStreetSufix(shippingAddress.getStreetSufix());
					customerInfoShippingAddressDTO.setPostcode(shippingAddress.getPostcode());
					customerInfoShippingAddressDTO.setElementInvolves(shippingAddress.getElementInvolves());
					customerInfoShippingAddressDTO.setLocality(shippingAddress.getLocality());
					customerInfoShippingAddressDTO.setCareOfName(shippingAddress.getCareOfName());
					customerInfoShippingAddressDTO.setCategorizedByname(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_SHIPPING.getCodeEntity());
					
				}
			}
		}
		
	}
	
	/**
	 * Metodo: Metodo que pobla la informacion de la identificacion individual del cliente CustomerInfoIndividualIdentifiedDTO
	 * @param response 
	 * @param customerInfoDTO <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	public static void populateCustomerInfoIndividualIdentified(com.directvla.schema.crm.vista360.v1.GetVista360Response response,CustomerInfoDTO customerInfoDTO) throws BusinessException{
		
		CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTO = null;
		customerInfoDTO.setCustomerInfoIndividualIdentifiedDTOS(customerInfoIndividualIdentifiedDTO);
		List<com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification> nationalIdentityCardIdentifications = new ArrayList<com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification>();
		if(response.getGetVista360Result() != null){
			if(response.getGetVista360Result().getGetCustomerCrmResult() != null){
				if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer() != null){
					if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole() != null){
						if(response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy() != null){
							nationalIdentityCardIdentifications = response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getIndividualIdentifiedBy().getIdentified();
						}
					}
				}
			}
		}
		
		if(nationalIdentityCardIdentifications.isEmpty()){
			log.error("No se encontró la información de identificación del cliente de la work order");
			throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		}
		
		for (com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification nationalIdentityCardIdentification : nationalIdentityCardIdentifications) {
			if(nationalIdentityCardIdentification instanceof com.directvla.schema.crm.common.vista360.v1.NationalIdentityCardIdentification){
				customerInfoIndividualIdentifiedDTO = new CustomerInfoIndividualIdentifiedDTO();
				customerInfoIndividualIdentifiedDTO.setCardNr(nationalIdentityCardIdentification.getCardNr());
				customerInfoIndividualIdentifiedDTO.setScan(nationalIdentityCardIdentification.getScan());
				customerInfoDTO.setCustomerInfoIndividualIdentifiedDTOS(customerInfoIndividualIdentifiedDTO);
				
				break;
			}
		}
		
		if(response!=null
				&& response.getGetVista360Result()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getAliveDuring()!=null
				&& response.getGetVista360Result().getGetCustomerCrmResult().getCustomer().getIndividualRole().getAliveDuring().getStartDateTime()!=null){
			
			Date date=UtilsBusiness.dateFromGregorianCalendar(response.getGetVista360Result().getGetCustomerCrmResult()
					.getCustomer().getIndividualRole().getAliveDuring().getStartDateTime().getValue());
			if(date!=null){
				customerInfoDTO.setBirthDate(new Date(date.getTime()));
			}
		}
		
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
	public static void validateResult(String parameterName, 
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
	 * Metodo: Obtiene el codigo de la direccion de la wo 
	 * @param customerInfoAggregatedDTO 
	 * @param sdiiWorkorder <tipo> <descripcion> 
	 * @author cduarte, aharker
	 */
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
	public static CustomerInfoAddressDTO getAddressesCustomer(CustomerInfoAggregatedDTO customerInfoAggregatedDTO, 
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
		return customerInfoAddressDTO;
	}
	
	/**
	 * Metodo: Llena con información vacia los datos obligatorios del cliente
	 * @param customerInfoAddressDTO <tipo> <descripcion>
	 * @author
	 */
	public static void populateCustomerAddressesDummyInfo(CustomerInfoAddressDTO customerInfoAddressDTO) {
		String emptyStr = "N/A";
		String customerLocality = customerInfoAddressDTO.getLocality();
		if(customerLocality == null || customerLocality.trim().length() == 0){
			customerInfoAddressDTO.setLocality(emptyStr);
		}
	}

	/**
	 * 
	 * Metodo: Escribe en el log la operacion
	 * consumida del servicio
	 * @param operation String
	 * @param sourceId String
	 * @author cduarte
	 */
	public static void logOperationInvoke(String operation, String sourceId){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		buffer.append(logMetadata(sourceId));
		
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
			sdiiCustomer.setAditionalIndications(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getAditionalIndications());
			sdiiCustomer.setCustomeraddress(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomeraddress());
			sdiiCustomer.setNeighborhood(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getNeighborhood());
			sdiiCustomer.setPostalCode(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getPostalCode());
			
			//Poblando la información de la dirección de la WO.
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
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#populateBuildingFromIbsCust(java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.Building)
	 */
	public static void populateBuildingFromIbsCust(String buildingCode, 
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
	public static String getCustomerCharacteristicFromIBS(String characteristicName,
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
	 * Metodo: Retorna el log de la informacion
	 * @param sourceId
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static String logMetadata(String sourceId){
		StringBuilder buffer = new StringBuilder();
		com.directvla.schema.crm.common.vista360.v1.RequestMetadataType metadata = getVista360MetadataType(sourceId);
		buffer.append(" Metadata [");
		buffer.append(" RequestID: "+metadata.getRequestID());
		buffer.append(" SourceID: "+metadata.getSourceID());
		buffer.append(" ]");
		return buffer.toString();
	}

}
