package co.com.directv.sdii.ejb.business.broker.toa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.IndividualName;
import co.com.directv.sdii.dto.esb.UrbanPropertyAddress;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.Agreement;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.CustomerAgreementCollection;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.IBSCRMSupportAndReadinessBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.impl.ResourceProvisioningServiceBrokerImpl;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWorkOrderEventInfoLocal;
import co.com.directv.sdii.ejb.business.core.impl.CoreWorkOrderEventInfo;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerAgreementTypesDTO;
import co.com.directv.sdii.model.dto.CustomerInfoAddressDTO;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.SwopsDTORequest;
import co.com.directv.sdii.model.dto.SwopsDTOResponse;
import co.com.directv.sdii.model.dto.UpgradeAndDowngradeDTORequest;
import co.com.directv.sdii.model.dto.UpgradeAndDowngradeDTOResponse;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.AddressType;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.BuildingAddresses;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerAgreementType;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.ShippingOrderDetail;
import co.com.directv.sdii.model.pojo.ShippingOrderElement;
import co.com.directv.sdii.model.pojo.ShippingOrderStatus;
import co.com.directv.sdii.model.pojo.ShippingOrderType;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WoProcessSource;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal;
import co.com.directv.sdii.persistence.dao.config.AddressTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerAgreementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;

import com.directvla.schema.crm.common.v1_1.Product;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;


/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services y
 * objetos de negocio HSP+
 * 
 * Fecha de Creación: 14/07/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="CoreWorkorderImporterServiceLocalTOA",mappedName="ejb/CoreWorkorderImporterServiceLocalTOA")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoreWorkorderImporterServiceTOA extends BusinessBase implements CoreWorkorderImporterServiceLocalTOA{
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWorkorderImporterServiceTOA.class);
	
	private ResourceProvisioningServiceBrokerImpl resourceProvisioningBroker;
	
	@EJB(name = "Vista360ServiceBrokerLocal", beanInterface = Vista360ServiceBrokerLocal.class)
	private Vista360ServiceBrokerLocal vista360ServiceBroker;

	@EJB(name = "CoreWorkOrderEventInfoLocal", beanInterface = CoreWorkOrderEventInfoLocal.class)
	private CoreWorkOrderEventInfoLocal coreWorkOrderEventInfo;
	
	@EJB(name="IBSCRMSupportAndReadinessBrokerLocal", beanInterface=IBSCRMSupportAndReadinessBrokerLocal.class)
	private IBSCRMSupportAndReadinessBrokerLocal brokerIBSCRMSupportAndReadiness;
	
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countriesDao;
	
	@EJB(name="CustomerDAOLocal", beanInterface=CustomerDAOLocal.class)
	private CustomerDAOLocal customerDao;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDao;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	@EJB(name="TechnologyDAOLocal",beanInterface=TechnologyDAOLocal.class)
	private TechnologyDAOLocal technologyDAO;
	
	@EJB(name="ShippingOrderStatusDAOLocal", beanInterface=ShippingOrderStatusDAOLocal.class)
	private ShippingOrderStatusDAOLocal shippingOrderStatusDao;
	
	@EJB(name="ShippingOrderTypeDAOLocal", beanInterface=ShippingOrderTypeDAOLocal.class)
	private ShippingOrderTypeDAOLocal shippingOrderTypeDao;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDao;
	
	@EJB(name="ServiceHourDAOLocal", beanInterface=ServiceHourDAOLocal.class)
	private ServiceHourDAOLocal serviceHourDao;
	
	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDAO;
	
	@EJB(name="Ibs6StatusDAOLocal", beanInterface=Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal ibs6StatusDAO;
	
	@EJB(name="CoreWOBusinessLocal", beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal coreWOBusiness;
	
	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;
	
	@EJB(name="WorkOrderCSRDAOLocal", beanInterface=WorkOrderCSRDAOLocal.class)
	private WorkOrderCSRDAOLocal workOrderCSRDAO;
	
	@EJB(name="WoStatusHistoryDAOLocal",beanInterface=WoStatusHistoryDAOLocal.class)
	private WoStatusHistoryDAOLocal daoWoStatusHistory;
	
	@EJB(name="CustomerAgreementTypeDAOLocal",beanInterface=CustomerAgreementTypeDAOLocal.class)
	private CustomerAgreementTypeDAOLocal daoCustomerAgreementType;
	
	@EJB(name="PostalCodesDAOLocal", beanInterface=PostalCodesDAOLocal.class)
	private PostalCodesDAOLocal postalCodesDao;
	
	@EJB(name="AddressTypeDAOLocal", beanInterface=AddressTypeDAOLocal.class)
	private AddressTypeDAOLocal addressTypeDao;
	
	@EJB(name="ProcessStatusDAOLocal", beanInterface=ProcessStatusDAOLocal.class)
	private ProcessStatusDAOLocal processStatusDao;
	
	@EJB(name="EmployeeDAOLocal", beanInterface=EmployeeDAOLocal.class)
	private EmployeeDAOLocal employeeDAO;
	
	@EJB(name="WorkorderReasonDAOLocal", beanInterface=WorkorderReasonDAOLocal.class)
	private WorkorderReasonDAOLocal woReasonDAO;
	
	@EJB(name="BuildingDAOLocal", beanInterface=BuildingDAOLocal.class)
	private BuildingDAOLocal buildingDAO;
	
	
	/**
	 * Metodo encargado de extraer la informacion de localizacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateLocaleInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException{
				
		String countryCode = "";
		if(pIbsWorkOrder.getGeographicArea().getCountry().getIso2Code() instanceof ElementNSImpl){
			countryCode = ((ElementNSImpl)pIbsWorkOrder.getGeographicArea().getCountry().getIso2Code()).getTextContent();
		}
		else{
			if(pIbsWorkOrder.getGeographicArea().getCountry().getIso2Code() instanceof String){
				countryCode = (String)pIbsWorkOrder.getGeographicArea().getCountry().getIso2Code();
			}
		}
		Country country = countriesDao.getCountriesByCode(countryCode);
		validateResult("No se encontró el país en sdii por el código: " + countryCode, country, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
		workOrderDto.getWorkOrder().setCountry( country );		
		
	}
	
	/**
	 * Metodo encargado de extraer la informacion de fecha de creacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 */
	@Override
	public void populateCreationDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto){
		
		//Fecha Creacion de la WorkOrder
		Date date = UtilsBusiness.dateFromGregorianCalendar(pIbsWorkOrder.getInteractionDate());		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
		formatter.setTimeZone(pIbsWorkOrder.getInteractionDate().getTimeZone(DatatypeConstants.FIELD_UNDEFINED));
		log.info("Se recibe la fecha de creación de la work order desde IBS " + formatter.format(date) + " esta está formateada, los valores son: año: \"" +pIbsWorkOrder.getInteractionDate().getYear()+ "\" mes:" + "\""+pIbsWorkOrder.getInteractionDate().getMonth()+"\" día: \""+pIbsWorkOrder.getInteractionDate().getDay()+"\"");
		workOrderDto.getWorkOrder().setCreationDate( date );		
	}
	
	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del cliente de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateCustomerInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		
		//Se obtiene el código de la dirección para mas adelante ser poblado en el broker de customer
		//pIbsWorkOrder.getCustomer().getAddressList().getUrbanPropertyAddress().get(0).getAddressCode() , no viene mas ver de donde conseguir este dato
		//Se busca el address para ponerle a la WO.
		List<UrbanPropertyAddress> upaList = pIbsWorkOrder.getCustomer().getAddressList().getUrbanPropertyAddress();
		String woAddessId = null;
		String woAddressIdDefault = null;
		for(UrbanPropertyAddress upa : upaList){
			if(upa.getId().equals(pIbsWorkOrder.getAddressId())){
				woAddessId = upa.getId();
				
			}
			if(upa.getCategorizedByName().equals(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_DEFAULT.getCodeEntity())){
				woAddressIdDefault = upa.getId();
			}
		}
		//Si vino una address que no es de Default, Shiiping o billing o bank. Setteamos la defualt.
		if(woAddessId == null){
			woAddessId = woAddressIdDefault;
		}
		
		workOrderDto.getWorkOrder().setWoAddress(woAddessId);
		workOrderDto.getWorkOrder().setWoAddressCode(woAddessId);
		
		//Informacion del Cliente
		Country country = workOrderDto.getWorkOrder().getCountry();
		String countryCode = workOrderDto.getWorkOrder().getCountry().getCountryCode();
		Customer customer = customerDao.getCustomerByCodeAndCountryId( pIbsWorkOrder.getCustomer().getID(), workOrderDto.getWorkOrder().getCountry().getId() );
		
		CustomerInfoAggregatedDTO customerInfoAggregatedDTO = new CustomerInfoAggregatedDTO();
		
		if(customer == null){
			customer = new Customer();
			//customerInfoAggregatedDTO=vista360ServiceBroker.populateCustomerFromIBSCust(pIbsWorkOrder.getCustomer().getID(),  country, customer, countryCode, workOrderDto,customerInfoAggregatedDTO);
			customerInfoAggregatedDTO=coreWorkOrderEventInfo.populateCustomerFromIBSCust(pIbsWorkOrder,  country, customer, countryCode, workOrderDto,customerInfoAggregatedDTO);
		}else{
			//customerInfoAggregatedDTO=vista360ServiceBroker.populateCustomerFromIBSCust(pIbsWorkOrder.getCustomer().getID(), country, customer, countryCode, workOrderDto, customerInfoAggregatedDTO);
			customerInfoAggregatedDTO=coreWorkOrderEventInfo.populateCustomerFromIBSCust(pIbsWorkOrder,  country, customer, countryCode, workOrderDto,customerInfoAggregatedDTO);
		}
		
		//CustomerInfoAddressDTO customerInfoAddressDTO = Vista360ServiceTOA.populateWorkOrderAddress(customerInfoAggregatedDTO, workOrderDto.getWorkOrder());
		//ver si se debe crear otra clase para esto
		CustomerInfoAddressDTO customerInfoAddressDTO = CoreWorkOrderEventInfo.populateWorkOrderAddress(customerInfoAggregatedDTO, workOrderDto.getWorkOrder());
		if(customerInfoAddressDTO!=null){
			String postalCodeCode2 = customerInfoAddressDTO.getPostcode();
			PostalCode postalCode = postalCodesDao.getPostalCodesByCodeByCountryCode(postalCodeCode2, countryCode);
			validateResult("No se encontró en SDII el postal code con el código especificado desde IBS " + postalCodeCode2, postalCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			workOrderDto.getWorkOrder().setPostalCode( postalCode );
		}
		
		
		//Poblar la informacion del Tipo de agreement
		String isMigrated = this.populateCustomerAgreementForCustomer(pIbsWorkOrder.getCustomer().getID(), pIbsWorkOrder.getCustomer().getCustomerAgreement(),countryCode);
		customer.setIsMigrated(isMigrated);
		
		/*Paquete de programación del cliente*/
		if(pIbsWorkOrder.getCustomer().getProduct()!=null){
			customer.setProduct(pIbsWorkOrder.getCustomer().getProduct());
		}
		
		workOrderDto.setCustomerInfoAggregatedDTO( customerInfoAggregatedDTO );
		workOrderDto.getWorkOrder().setCustomer(customer);
		if(customerInfoAggregatedDTO!=null
				&& customerInfoAggregatedDTO.getCustomerInfoDTO()!=null
				&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO()!=null
				&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerClassCode()!=null
				&& customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerTypeCode()!=null){
			workOrderDto.setCustomerCodeClass(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerClassCode());
			workOrderDto.setCustomerCodeType(customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO().getCustomerTypeCode());			
		}

	}
	
	/**
	 * Metodo encargado de poblar la informacion del agreement del cliente
	 * y determinar si es o no migrado
	 * @param customerCode
	 * @param customerAgreementCollection
	 * @param countryCode
	 * @return
	 * @throws BusinessException
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws PropertiesException 
	 * @author waguilera
	 */
	private String populateCustomerAgreementForCustomer(String customerCode, 
														CustomerAgreementCollection customerAgreementCollection, 
														String countryCode) 
    throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
	
		//Se obtienen los agreement del cliente 
		List<CustomerAgreementTypesDTO> listCustomerAgreementsDTOs = new ArrayList<CustomerAgreementTypesDTO>();
		
		
			if(customerAgreementCollection!=null
					&& customerAgreementCollection.getCustomerAgreement()!=null
					&& customerAgreementCollection.getCustomerAgreement().size() > 0L){
				
				CustomerAgreementTypesDTO agreementsDTO = new CustomerAgreementTypesDTO();
				for(Agreement getCustomerAgreement: customerAgreementCollection.getCustomerAgreement()){
					agreementsDTO = new CustomerAgreementTypesDTO();
					agreementsDTO.setCustomerCode(customerCode);
					agreementsDTO.setIbsAdreementTypeId(getCustomerAgreement.getType().getId());
					listCustomerAgreementsDTOs.add(agreementsDTO);
				}
				
			}
		String isMigratedCustomer = CodesBusinessEntityEnum.CUSTOMER_AGREEMENT_NOT_MIGRATED.getCodeEntity();
		List<String> listAgreementTypesCodes = new ArrayList<String>();
		for(CustomerAgreementTypesDTO dto: listCustomerAgreementsDTOs){
			listAgreementTypesCodes.add(dto.getIbsAdreementTypeId());
		}
		

		//Consultar los tipos de agreement dentro de HSP
		//para determinar si es o no migrado
		List<CustomerAgreementType> listAgreement = new ArrayList<CustomerAgreementType>();
		if(listAgreementTypesCodes.size() > 0L){
			String customerAgreementTypesCodes = UtilsBusiness.stringListToOrInQuery(listAgreementTypesCodes, "entity.ibsAdreementId");
			listAgreement = this.daoCustomerAgreementType.getCustomerAgreementTypes(customerAgreementTypesCodes);
		}
		
		//Valida de la lista de tipos de agreement si al menos uno esta 
		//parametrizado como migrado
		for(CustomerAgreementType customerAgreementType: listAgreement){
			if(customerAgreementType.getIsMigrated().equals(CodesBusinessEntityEnum.CUSTOMER_AGREEMENT_MIGRATED.getCodeEntity())){
				isMigratedCustomer = CodesBusinessEntityEnum.CUSTOMER_AGREEMENT_MIGRATED.getCodeEntity();
				break;
			}
		}
		
		return isMigratedCustomer;
		
	}
	
	@Override
	public void populateDealerDummy(WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		
		//Se carga el dealer dummy	
		Country country = workOrderDto.getWorkOrder().getCountry();
		SystemParameter dealerDummyParam = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_DEALER_DUMMY_CODE.getCodeEntity(), country.getId());
		validateResult("No existe en sdii el parámetro de sistema que identifica el dealer dummy para el país coin id "+ country.getId() +" de la work order" , dealerDummyParam, ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage());
		String dealerDummyCode = dealerDummyParam.getValue();
		Long dealerDummyCodeLong = null;
		try {
			dealerDummyCodeLong = Long.parseLong(dealerDummyCode);
		} catch (NumberFormatException e) {
			log.error("El código del dealer Dummy debe ser un número, se ha configurado: " + dealerDummyCode, e);
			throw new BusinessException(ErrorBusinessMessages.INVALID_NUMBER.getCode(), ErrorBusinessMessages.INVALID_NUMBER.getMessage());
		}
		
		workOrderDto.setDealerDummyCode( dealerDummyCode );
		workOrderDto.setDealerDummyCodeLong( dealerDummyCodeLong );	
		
		Dealer d = dealersDao.getDealerByDealerCode( dealerDummyCodeLong);
		if(d==null){
			String error = ": el dealer dummy con código " + dealerDummyCodeLong + " del país " + country.getId() + " no existe.";
    		throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage() + error);
		}

	}
	
	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del dealer de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateDealerInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException{
		
		//Se carga el codigo del dealer que se asociara a la WorkOrder
		Country country = workOrderDto.getWorkOrder().getCountry();
		Long dealerCode = pIbsWorkOrder.getDealerId();
		validateResult("workOrder.getDealerId()", dealerCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		Dealer dealer = dealersDao.getDealerByDealerCodeAndCountryId(dealerCode, country.getId());
		validateResult("No se encontró el dealer en SDII por el código de IBS: " + dealerCode + " e id de país: " + country.getId(), dealer, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		
		workOrderDto.setDealerAssignmentCode( dealer.getDealerCode() );
		workOrderDto.getWorkOrder().setDealerId( dealer.getId() );		
	}

	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del dealer vendedor de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateDealerSaleInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException{
		
		//Informacion del vendedor
		workOrderDto.setDealerSalesId(pIbsWorkOrder.getDealerISalesd());	
		
	}

	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del history event de la work order
	 * @param pIbsWorkOrder pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 */
	@Override
	public void populateHistoryCodeEventInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) {
		
		Long hceId = pIbsWorkOrder.getHistoryCodeEventId();
		if (hceId != null && hceId.longValue() > 0) {
			workOrderDto.getWorkOrder().setHistoryCodeEventId(hceId);
		}else if(hceId != null && hceId.longValue() == -1l){
			workOrderDto.setUpdateWorkOrderIBSToHSP(true);
		}
		
	}

	//Spira 22159 - REPORTES FTP:Campos inconsistentes
	public void populateHistoryReasonEventInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto)
	throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		if (pIbsWorkOrder.getReason() != null && pIbsWorkOrder.getReason().getId() !=null) {					
		    workOrderDto.setCreateHistoryWoStatusHistory(true);		      	   
			WorkorderReason downloadWoReason = woReasonDAO.getWorkorderReasonByCode(pIbsWorkOrder.getReason().getId().toString());	
		workOrderDto.setHistoryWoReason(downloadWoReason); //solo se guarda la reason de la descarga para STATUS_SCHEDULED y STATUS_RESCHEDULED 
		/*
		 * Si el codigo de reason presente en el mensaje de la descarga NO existe en HSP se cargara NULL en tabla work_order_status_histories
		 * y no se contabilizara como agendamiento/reagendamiento                                        
		 */
		
		}/*si directamente codigo de reason presente en el mensaje de la descarga es NULO, NO se seteara nada para el campo reason
		 de la tabla work_order_status_histories*/		
	}

	/**
	 * Metodo encargado de extraer la fecha de programacion del objeto del servicio de ESB para llenar la DTO de HSP+
	 * @param pIbsWorkOrder pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateProgrammingDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		
		//Informacion de la fecha de agendamiento
		XMLGregorianCalendar requestDeliveryDate = pIbsWorkOrder.getRequestedDeliveryDate();
		Date woProgrammingDate = UtilsBusiness.dateFromGregorianCalendar(requestDeliveryDate);
		if (woProgrammingDate != null) {
			String isAppointment = CodesBusinessEntityEnum.WORKORDER_IS_APPOINTMENT.getCodeEntity();
			workOrderDto.getWorkOrder().setIsAppointment( isAppointment );
			workOrderDto.getWorkOrder().setWoProgrammingDate( woProgrammingDate) ;
		} else {
			workOrderDto.getWorkOrder().setIsAppointment( CodesBusinessEntityEnum.WORKORDER_IS_NOT_APPOINTMENT.getCodeEntity() );
		}		
	}

	/**
	 * 
	 * @param pIbsWorkOrder
	 * @param workOrderDto
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateUserInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		
		//Informacion del usuario de creacion de la WorkOrder
		PostalCode postalCode = workOrderDto.getWorkOrder().getPostalCode();
		Country country = workOrderDto.getWorkOrder().getCountry();
		List<User> users = null;
		SystemParameter woOwnerUserParam = null;
		User user = null;
		
		//Se obtiene el userName de los datos de ibs
		String userIbs = pIbsWorkOrder.getInitiatedBy();
		
		//Si trae algun valor del usuario de ibs
		if(userIbs != null && !userIbs.trim().equals("")){
			user = new User();
			user.setLogin(userIbs);
			user.setCountry( postalCode.getCity().getState().getCountry() );
			users = userDao.getUserBySample(user);
		}
		
		//si no se encontro ningun usuario con el login devuelto de ibs
		if(users==null || users.isEmpty()){
			log.info("No se encontró el usuario por el login reportado desde ibs: " + userIbs + " en el país:" + postalCode.getCity().getState().getCountry().getCountryName());
			woOwnerUserParam = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN.getCodeEntity(), country.getId());
			validateResult("No existe en sdii el parámetro de sistema con código \"" + CodesBusinessEntityEnum.SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN.getCodeEntity() + "\" que identifica el usuario de creación de las work orders el país coin id " + country.getId() + " de la work order" , woOwnerUserParam, ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage());
			String userId = woOwnerUserParam.getValue();
			
			user = new User();
			user.setLogin(userId);
			user.setCountry( postalCode.getCity().getState().getCountry() );
			users = userDao.getUserBySample(user);
			
			//Si no se encuentra creado el usuario IBSADMIN
			if (users.isEmpty()) {
				log.error("No se encontró el usuario por el login : " + userId + " en el país:" + postalCode.getCity().getState().getCountry().getCountryName());
				throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			}
		}
		
		//Se obtiene el primer valor de la consulta
		user = users.get(0);
		workOrderDto.getWorkOrder().setUserId( user.getId() );
	}

	/**
	 * Metodo encargado de extraer del objeto de ESB la informacion general de la work order que es necesaria sin importar el estado en que venga la work order
	 * tales como no dejar la agenda vencida, la Accion a tomar, codigo de la WorkOrder, descripcion de la WorkOrder
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateGeneralInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws BusinessException, PropertiesException {
		
		//Si la WO existe en HSP+ se copia la informacion actual, para solo reemplazar la info que se proviene de IBS
		if (actualWorkOrder != null) {
			workOrderDto.setWorkOrder( UtilsBusiness.copyObject(WorkOrder.class, actualWorkOrder)  );
		}
		
		//Por defecto la workorder no tiene la agenda vencida
		workOrderDto.getWorkOrder().setAgendationExpired( CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity() );
		
		//Informacion de la Accion a tomar
		workOrderDto.getWorkOrder().setWoAction(pIbsWorkOrder.getAction());		
		
		//Informacion del codigo de la WorkOrder
		workOrderDto.getWorkOrder().setWoCode(pIbsWorkOrder.getID());
		
		//Inoformacion de la descripcion de la Workorder
		String woDescription = pIbsWorkOrder.getDescription();
		if (woDescription == null || woDescription.trim().length() == 0) {
			woDescription = "N/A";
		}
		
		//Se almacena la descripcion de la WorkOrder
		workOrderDto.getWorkOrder().setWoDescription(woDescription);
	}
	
	/**
	 * Metodo encargado de extraer la informacion de fecha de realizacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateRealizationDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException {
		
		//se obtiene la fecha de atencion de la WO
		//No se puede obtener del servicio, por lo tanto si la WO no fue atendida en HSP se almacena del sistema.
		//Si la fecha de atencion de la WO es null se actualiza con la del sistema	
		//Si la WO existe en HSP
		if ( actualWorkOrder != null  ) {
			if ( actualWorkOrder.getWoRealizationDate() == null ) {
				workOrderDto.getWorkOrder().setWoRealizationDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrderDto.getWorkOrder().getUserId(), userDao) );
			} else {
				workOrderDto.getWorkOrder().setWoRealizationDate( actualWorkOrder.getWoRealizationDate() );
			}
		} else { //Si la WO no existe en HSP
			workOrderDto.getWorkOrder().setWoRealizationDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrderDto.getWorkOrder().getUserId(), userDao) );
		}
	}

	/**
	 * Metodo encargado de extraer la informacion de fecha de finalizacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateFinalizationDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException {
		
		//Si la WO existe en HSP
		if ( actualWorkOrder != null  ) {
			//obtiene la fecha de finalizacion de la WO	
			if ( actualWorkOrder.getFinalizationDate() == null ) {
				Date finalizationDate = UtilsBusiness.dateFromGregorianCalendar(pIbsWorkOrder.getInteractionDateComplete());
				workOrderDto.getWorkOrder().setFinalizationDate( finalizationDate );
			} else {
				workOrderDto.getWorkOrder().setFinalizationDate( actualWorkOrder.getFinalizationDate() );
			}
			
			//Informacion de las horas de trabajo de la WorkOrder
			if ( actualWorkOrder.getWorkingTime() == null ) {
				workOrderDto.getWorkOrder().setWorkingTime( pIbsWorkOrder.getWorkingTime() );
			} else {
				workOrderDto.getWorkOrder().setWorkingTime( actualWorkOrder.getWorkingTime() );
			}
		} else { //Si la WO no existe en HSP
			Date finalizationDate = UtilsBusiness.dateFromGregorianCalendar(pIbsWorkOrder.getInteractionDateComplete());
			workOrderDto.getWorkOrder().setFinalizationDate( finalizationDate );
			workOrderDto.getWorkOrder().setWorkingTime( pIbsWorkOrder.getWorkingTime() );
		}
	}

	/**
	 * Metodo encargado de extraer la informacion de la Shipping Order de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateShippingOrderInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException {
		
		//Informacion de la shipping order asociada a la work order
		ShippingOrder so = null;  
		List<ShippingOrderElement> soElements = null;
		List<ShippingOrderDetail> soDetails = null;
		if (pIbsWorkOrder.getShippingOrder() != null && !"0".equalsIgnoreCase(pIbsWorkOrder.getShippingOrder().getID())) {
			so = this.buildSdiiShippingOrderFromIbs(pIbsWorkOrder.getShippingOrder(), pIbsWorkOrder, workOrderDto.getWorkOrder());
			workOrderDto.setShippingOrder(so);
		
			//Obteniendo la información de los elementos asociados a la shipping order
			//jalopez se comenta la validacion de los elementos, puesto que hasta que no se construya inventarios de SDII, no existiran elementos en la Base de Datos.						
			//soElements = this.buildShippingOrderElementsFromIbs(workOrder.getShippingOrder());
			soDetails = this.buildShippingOrdersDetailsFromIbs(pIbsWorkOrder.getShippingOrder());
			workOrderDto.setShippingOrderElements(soElements);
			workOrderDto.setShippingOrderDetails(soDetails);
		}
	}

	/**
	 * Metodo encargado de extraer la informacion del estado actual de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateWorkOrderStatusInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		
		WorkorderStatus status = null;
		//Si la WO importada desde IBS fue asignada al Dealer Dummy se crea como activa en HSP+
		if ( workOrderDto.getDealerDummyCodeLong().compareTo( workOrderDto.getDealerAssignmentCode() ) == 0 ) {
			status = workorderStatusDao.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
		} else {		
			String woIbsStatusCode = pIbsWorkOrder.getWorkorderStatusByActualStatusId();
			status = workorderStatusDao.getWorkorderStatusByIBS6StatusCode( woIbsStatusCode );
		}
		
		//Si la WO ya existe en HSP+ y el estado de la WO de IBS es diferente al estado actual se actualiza el estado anterior.
		workOrderDto.getWorkOrder().setWorkorderStatusByActualStatusId( status );		
		if ( actualWorkOrder != null && ( !status.getWoStateCode().equalsIgnoreCase( actualWorkOrder.getWorkorderStatusByActualStatusId().getWoStateCode() ) ) ) {
			workOrderDto.getWorkOrder().setWorkorderStatusByPreviusStatusId( actualWorkOrder.getWorkorderStatusByActualStatusId() );
		}		
	}

	/**
	 * 
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateWorkOrderPendingStatusInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		
		WorkorderStatus status = null;
		if ( workOrderDto.getDealerDummyCodeLong().compareTo( workOrderDto.getDealerAssignmentCode() ) == 0 ) {
			status = workorderStatusDao.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
		} else {		
			String woIbsStatusCode = pIbsWorkOrder.getWorkorderStatusByActualStatusId();
			status = this.workOrderStatusSearch( woIbsStatusCode );
		}
		
		//Si la WO ya existe en HSP+ y el estado de la WO de IBS es diferente al estado actual se actualiza el estado anterior.
		workOrderDto.getWorkOrder().setWorkorderStatusByActualStatusId( this.validateStatusWorkOrder(status, actualWorkOrder) );		
		if ( actualWorkOrder != null && ( !status.getWoStateCode().equalsIgnoreCase( actualWorkOrder.getWorkorderStatusByActualStatusId().getWoStateCode() ) ) ) {
			workOrderDto.getWorkOrder().setWorkorderStatusByPreviusStatusId( actualWorkOrder.getWorkorderStatusByActualStatusId() );
		}	
	}

	/**
	 * 
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateWOAssignmentInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException {
		
		WoAssignment woAssignment = new WoAssignment();
		workOrderDto.setCreateAssignment( false );
		workOrderDto.setUpdateAssignment( false );
		
		//Si es el dealer dummy se crea una asignacion vacia sin importar el estado de la WO
		if ( workOrderDto.getDealerDummyCodeLong().compareTo( workOrderDto.getDealerAssignmentCode() ) == 0 ) {
			workOrderDto.setWoAssignment( woAssignment );
			//Indica si se debe crear una asignacion en el proceso en el cual se persiste la WO
			workOrderDto.setCreateAssignment( true );
		} else {
			//Si la WO no existe en HSP+ se crea la asignacion al dealer que viene en la WO
			if ( actualWorkOrder == null ) {						
				woAssignment = this.builWoAssignmentFromIbs( workOrderDto.getWorkOrder() );
				workOrderDto.setWoAssignment( woAssignment );
				//Indica si se debe crear una asignacion en el proceso en el cual se persiste la WO
				workOrderDto.setCreateAssignment( true );
			} else {				
				//Se consulta la ultima asignacion activa
				WoAssignment lastWoAssignment = woAssignmentDAO.getLastDealerAssignmentByWoId( workOrderDto.getWorkOrder().getId() );
				if ( lastWoAssignment == null ) {
					woAssignment = this.builWoAssignmentFromIbs( workOrderDto.getWorkOrder() );
					workOrderDto.setWoAssignment( woAssignment );
					//Indica si se debe crear una asignacion en el proceso en el cual se persiste la WO
					workOrderDto.setCreateAssignment( true );
				} else {
					//Si existe una asignacion para la WO pero sin dealer, se actualiza con la informacion del dealer recibido
					if ( lastWoAssignment.getDealerId() == null ) {
						lastWoAssignment.setDealerId( workOrderDto.getWorkOrder().getDealerId() );
						lastWoAssignment.setDealerAssignmentDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrderDto.getWorkOrder().getUserId(), userDao) );
						//Indica que se debe realizar una actualizacion de la asignacion en el proceso en el cual de persiste la WO
						workOrderDto.setUpdateAssignment( true );
						//Indica que no se debe crear una asignacion en el proceso en el cual se persiste la WO
						workOrderDto.setCreateAssignment( false );
						workOrderDto.setWoAssignment( lastWoAssignment );
					} else if (lastWoAssignment.getDealerId().longValue() != workOrderDto.getWorkOrder().getDealerId().longValue()) {
						woAssignment = this.builWoAssignmentFromIbs( workOrderDto.getWorkOrder() );
						//Indica si se debe crear una asignacion en el proceso en el cual se persiste la WO
						workOrderDto.setCreateAssignment( true );
						workOrderDto.setWoAssignment( woAssignment );
					} else {
						workOrderDto.setCreateAssignment( false );
						workOrderDto.setWoAssignment( lastWoAssignment );
					}
				}
			}	
		}			
	}

	/**
	 * Metodo encargado de extraer la informacion de la agenda la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException
	 */
	@Override
	public void populataWOAgendaInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		
		WorkOrderAgenda woAgenda = this.buildWoAgendaFromIbs( pIbsWorkOrder, workOrderDto.getWorkOrder(), workOrderDto.getWoAssignment(), workOrderDto.getWorkOrder().getCustomer() );
		workOrderDto.setWoAgenda( woAgenda );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.toa.CoreWorkorderImporterServiceLocalTOA#populataWOCancelationDate(co.com.directv.sdii.model.dto.WorkOrderDTO)
	 */
	public void populataWOCancelationDate(WorkOrderDTO workOrderDto) {
		
		if ( workOrderDto.getDealerDummyCodeLong().compareTo( workOrderDto.getDealerAssignmentCode() ) != 0 ) {
			workOrderDto.getWorkOrder().setCancelationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrderDto.getWorkOrder().getUserId(), userDao));
		}
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.toa.CoreWorkorderImporterServiceLocalTOA#populataWOCancelProcessStatus(co.com.directv.sdii.model.dto.WorkOrderDTO)
	 */
	public void populataWOCancelProcessStatus(WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		workOrderDto.setWorkOrderStatusCancel(true);
	}

	/**
	 * 
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateBuildingInformacion(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws BusinessException, PropertiesException{
		//obteniendo el código del edificio:
		CustomerInfoAggregatedDTO customerInfoAggregatedDTO = workOrderDto.getCustomerInfoAggregatedDTO();
		//String buildingCode = vista360ServiceBroker.getCustomerCharacteristicFromIBS(CodesBusinessEntityEnum.IBS_BULDING_CODE_CHARACTERISTIC_NAME.getCodeEntity(),customerInfoAggregatedDTO);
		//String buildingCode = coreWorkOrderEventInfo.getCustomerCharacteristicFromIBSInfo(CodesBusinessEntityEnum.IBS_BULDING_CODE_CHARACTERISTIC_NAME.getCodeEntity(),customerInfoAggregatedDTO);
		String buildingCode = null;
		workOrderDto.getWorkOrder().setBuildingCode( buildingCode );	
	}
	
	@Override
	public void populateWOImportationDateInformation(WorkOrderDTO workOrderDto) throws BusinessException, PropertiesException{
		//Se carga la fecha de importacion de la WorkOrder
		workOrderDto.getWorkOrder().setImportDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrderDto.getWorkOrder().getUserId(), userDao) );
	}
	
	private WorkOrderAgenda buildWoAgendaFromIbs(com.directvla.schema.crm.common.v1_1.WorkOrder workOrder, WorkOrder resulWorkOrder, WoAssignment woAssignment, Customer customer) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		WorkOrderAgenda woAgenda = new WorkOrderAgenda();
		Date woAgendaDate = UtilsBusiness.dateFromGregorianCalendar(workOrder.getRequestedDeliveryDate());
		if ( woAgendaDate == null ) {
			return null;
		}
		woAgenda.setAgendationDate(woAgendaDate);
		
		woAgenda.setContactPerson(customer.getFirstName() + " " + customer.getLastName());
		woAgenda.setDescription(ApplicationTextEnum.IBS_SCHEDULE_WORK_ORDER.getApplicationTextValue());
		
		List<ServiceHour> allServiceHours = serviceHourDao.getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(resulWorkOrder.getCountry().getCountryCode(), CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity(), woAgendaDate);
		if ( allServiceHours.isEmpty() ) {
			throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + ": No se encontró una jornada activa que pueda cubrir el horario de atención de la WO solicitado: " + UtilsBusiness.dateToString(woAgendaDate, UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
		}
		int size = allServiceHours.size();		
		ServiceHour serviceHour = allServiceHours.get( new Random().nextInt(size) );
		woAgenda.setServiceHour(serviceHour);		
		woAgenda.setWoAssignment(woAssignment);		
		
		return woAgenda;
	}	

	private WorkOrderAgenda buildWoAgendaFromIbs(co.com.directv.sdii.dto.esb.WorkOrder workOrder, WorkOrder resulWorkOrder, WoAssignment woAssignment, Customer customer) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		WorkOrderAgenda woAgenda = new WorkOrderAgenda();
		Date woAgendaDate = UtilsBusiness.dateFromGregorianCalendar(workOrder.getRequestedDeliveryDate());
		if ( woAgendaDate == null ) {
			return null;
		}
		woAgenda.setAgendationDate(woAgendaDate);
		
		woAgenda.setContactPerson(customer.getFirstName() + " " + customer.getLastName());
		woAgenda.setDescription(ApplicationTextEnum.IBS_SCHEDULE_WORK_ORDER.getApplicationTextValue());
		
		List<ServiceHour> allServiceHours = serviceHourDao.getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(resulWorkOrder.getCountry().getCountryCode(), CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity(), woAgendaDate);
		if ( allServiceHours.isEmpty() ) {
			throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + ": No se encontró una jornada activa que pueda cubrir el horario de atención de la WO solicitado: " + UtilsBusiness.dateToString(woAgendaDate, UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
		}
		int size = allServiceHours.size();		
		ServiceHour serviceHour = allServiceHours.get( new Random().nextInt(size) );
		woAgenda.setServiceHour(serviceHour);		
		woAgenda.setWoAssignment(woAssignment);		
		
		return woAgenda;
	}
	
	/**
	 * 
	 * Metodo: Se crea el registro de asignacion
	 * @param result WorkOrder
	 * @returnv WoAssignment
	 * @author jjimenezh
	 */
	private WoAssignment builWoAssignmentFromIbs(WorkOrder result) {
		WoAssignment woAssignment = new WoAssignment();
		woAssignment.setDealerAssignmentDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( result.getUserId(), userDao) );
		woAssignment.setDealerId( result.getDealerId() );
		woAssignment.setWorkOrder( result );
		return woAssignment;
	}
	
	
	/**
	 * 
	 * Metodo: Obtiene una lista de detalles de shipping order
	 * @param shippingOrder
	 * @return Lista de detalles de la shipping order
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	private List<ShippingOrderDetail> buildShippingOrdersDetailsFromIbs( co.com.directv.sdii.dto.esb.ShippingOrder shippingOrder ) throws BusinessException, DAOServiceException, DAOSQLException {
		List<ShippingOrderDetail> resultList = null;
		
		if( shippingOrder.getProductList() != null && shippingOrder.getProductList().getProduct() != null 
				&& !shippingOrder.getProductList().getProduct().isEmpty() ){
			resultList = new ArrayList<ShippingOrderDetail>();
			List<co.com.directv.sdii.dto.esb.Product> productInfo = shippingOrder.getProductList().getProduct();
			for (co.com.directv.sdii.dto.esb.Product product : productInfo) {
				ShippingOrderDetail detail = new ShippingOrderDetail();
				Technology technology = technologyDAO.getTechnologyByIbsCode( product.getType() );
				if( technology != null ){
					detail.setTechnology(technology);
					detail.setSerialCode( product.getProductSerialNumber() );
					detail.setIbsModelCode( product.getModelKey() );
					resultList.add( detail );
				} else {
					log.debug("El producto "+product.getCode()+" no se pudo mapear para agregar la tecnologia en la SO con codigo "+shippingOrder.getCode());
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * 
	 * Metodo: Obtiene una lista de detalles de shipping order
	 * @param shippingOrder
	 * @return Lista de detalles de la shipping order
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	private List<ShippingOrderDetail> buildShippingOrdersDetailsFromIbs( com.directvla.schema.crm.common.v1_1.ShippingOrder shippingOrder ) throws BusinessException, DAOServiceException, DAOSQLException {
		List<ShippingOrderDetail> resultList = null;
		
		if( shippingOrder.getProductList() != null && shippingOrder.getProductList().getProduct() != null 
				&& !shippingOrder.getProductList().getProduct().isEmpty() ){
			resultList = new ArrayList<ShippingOrderDetail>();
			List<Product> productInfo = shippingOrder.getProductList().getProduct();
			for (Product product : productInfo) {
				ShippingOrderDetail detail = new ShippingOrderDetail();
				Technology technology = technologyDAO.getTechnologyByIbsCode( product.getType() );
				if( technology != null ){
					detail.setTechnology(technology);
					detail.setSerialCode( product.getProductSerialNumber() );
					detail.setIbsModelCode( product.getModelKey() );
					resultList.add( detail );
				} else {
					log.debug("El producto "+product.getCode()+" no se pudo mapear para agregar la tecnologia en la SO con codigo "+shippingOrder.getCode());
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * Metodo: <Descripcion>
	 * @param shippingOrder
	 * @param workOrder
	 * @param workOrderSdii
	 * @return
	 * @author jjimenezh, aharker
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * Se duplica el metodo para PP
	 */
	private ShippingOrder buildSdiiShippingOrderFromIbs(co.com.directv.sdii.dto.esb.ShippingOrder IbsShippingOrder,co.com.directv.sdii.dto.esb.WorkOrder ibsWorkOrder, WorkOrder workOrderSdii) throws BusinessException, DAOServiceException, DAOSQLException {
		ShippingOrder result = new ShippingOrder();
		
		this.populateShippingOrderDummyData(IbsShippingOrder);
		
		result.setContractCode(IbsShippingOrder.getContractCode());
		validateResult("IbsShippingOrder.getContractNumber()", IbsShippingOrder.getContractNumber(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		result.setContractNumber(IbsShippingOrder.getContractNumber());
		
		Date creationEventDate = UtilsBusiness.dateFromGregorianCalendar(IbsShippingOrder.getInteractionDate());
		result.setCreationEventDate(creationEventDate);
		
		//result.setRealShippingDate(realShippingDate);
		
		Date shippingDate = UtilsBusiness.dateFromGregorianCalendar(IbsShippingOrder.getInteractionDateComplete());
		result.setShippingDate(shippingDate);
		
		result.setShippingMethodCode(IbsShippingOrder.getShippingMethodKey());
		result.setShippingOrderCode(IbsShippingOrder.getID());
		
		String shippingOrderStatusCode = IbsShippingOrder.getInteractionStatus();
		ShippingOrderStatus shippingOrderStatus = shippingOrderStatusDao.getShippingOrderStatusByCode(shippingOrderStatusCode);
		validateResult("No se encontró el estado shipping order en Sdii con el código de ibs: " + shippingOrderStatusCode, shippingOrderStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		result.setShippingOrderStatus(shippingOrderStatus);
		
		String shippingOrderTypeCode = IbsShippingOrder.getShippingOrderKey();
		ShippingOrderType shippingOrderType = shippingOrderTypeDao.getShippingOrderTypeByCode(shippingOrderTypeCode);
		validateResult("No se encontró el tipo shipping order en Sdii con el código de ibs: " + shippingOrderTypeCode, shippingOrderType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		result.setShippingOrderType(shippingOrderType);
		
		result.setSoDescription(IbsShippingOrder.getDescription());
		result.setVendorCode(IbsShippingOrder.getSoldBy());
		
		return result;
	}
	
	/**
	 * Metodo: <Descripcion>
	 * @param shippingOrder
	 * @param workOrder
	 * @param workOrderSdii
	 * @return
	 * @author jjimenezh
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private ShippingOrder buildSdiiShippingOrderFromIbs(com.directvla.schema.crm.common.v1_1.ShippingOrder IbsShippingOrder,com.directvla.schema.crm.common.v1_1.WorkOrder ibsWorkOrder, WorkOrder workOrderSdii) throws BusinessException, DAOServiceException, DAOSQLException {
		ShippingOrder result = new ShippingOrder();
		
		this.populateShippingOrderDummyData(IbsShippingOrder);
		
		result.setContractCode(IbsShippingOrder.getContractCode());
		validateResult("IbsShippingOrder.getContractNumber()", IbsShippingOrder.getContractNumber(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		result.setContractNumber(IbsShippingOrder.getContractNumber());
		
		Date creationEventDate = UtilsBusiness.dateFromGregorianCalendar(IbsShippingOrder.getInteractionDate());
		result.setCreationEventDate(creationEventDate);
		
		//result.setRealShippingDate(realShippingDate);
		
		Date shippingDate = UtilsBusiness.dateFromGregorianCalendar(IbsShippingOrder.getInteractionDateComplete());
		result.setShippingDate(shippingDate);
		
		result.setShippingMethodCode(IbsShippingOrder.getShippingMethodKey());
		result.setShippingOrderCode(IbsShippingOrder.getID());
		
		String shippingOrderStatusCode = IbsShippingOrder.getInteractionStatus();
		ShippingOrderStatus shippingOrderStatus = shippingOrderStatusDao.getShippingOrderStatusByCode(shippingOrderStatusCode);
		validateResult("No se encontró el estado shipping order en Sdii con el código de ibs: " + shippingOrderStatusCode, shippingOrderStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		result.setShippingOrderStatus(shippingOrderStatus);
		
		String shippingOrderTypeCode = IbsShippingOrder.getShippingOrderKey();
		ShippingOrderType shippingOrderType = shippingOrderTypeDao.getShippingOrderTypeByCode(shippingOrderTypeCode);
		validateResult("No se encontró el tipo shipping order en Sdii con el código de ibs: " + shippingOrderTypeCode, shippingOrderType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		result.setShippingOrderType(shippingOrderType);
		
		result.setSoDescription(IbsShippingOrder.getDescription());
		result.setVendorCode(IbsShippingOrder.getSoldBy());
		
		return result;
	}
	
	private void populateShippingOrderDummyData(com.directvla.schema.crm.common.v1_1.ShippingOrder ibsShippingOrder) {
		String strEmpty =ApplicationTextEnum.EMPTY.getApplicationTextValue();
		if(ibsShippingOrder.getContractNumber() == null || ibsShippingOrder.getContractNumber().trim().length() == 0){
			ibsShippingOrder.setContractNumber(strEmpty);
		}
		
		if(ibsShippingOrder.getContractCode() == null || ibsShippingOrder.getContractCode().trim().length() == 0){
			ibsShippingOrder.setContractCode(strEmpty);
		}
		
		if(ibsShippingOrder.getSoldBy() == null || ibsShippingOrder.getSoldBy().trim().length() == 0){
			ibsShippingOrder.setSoldBy(strEmpty);
		}
	}
	
	private void populateShippingOrderDummyData(co.com.directv.sdii.dto.esb.ShippingOrder ibsShippingOrder) {
		String strEmpty = ApplicationTextEnum.EMPTY.getApplicationTextValue();
		if(ibsShippingOrder.getContractNumber() == null || ibsShippingOrder.getContractNumber().trim().length() == 0){
			ibsShippingOrder.setContractNumber(strEmpty);
		}
		
		if(ibsShippingOrder.getContractCode() == null || ibsShippingOrder.getContractCode().trim().length() == 0){
			ibsShippingOrder.setContractCode(strEmpty);
		}
		
		if(ibsShippingOrder.getSoldBy() == null || ibsShippingOrder.getSoldBy().trim().length() == 0){
			ibsShippingOrder.setSoldBy(strEmpty);
		}
	}
	
	/**
	 * 
	 * Metodo: Implementacion para la separacion del tipo
	 * de estado N-Pendiente, puesto que para SDII existe
	 * dos estados que informan el cambio a IBS como N, 
	 * que son N-Dificultad y R-Rechazo (Pendientes en IBS)
	 * @param woStatus
	 * @return WorkorderStatus
	 * @throws BusinessException
	 * @author jalopez
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws PropertiesException 
	 */
	private WorkorderStatus workOrderStatusSearch (String woStatus) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		log.debug("== Inicia la operación workOrderStatusSearch/CoreWorkOrderServiceBrokerImpl ==");
	    Ibs6Status ibs6Status = new Ibs6Status();
		WorkorderStatus actualStatus = new WorkorderStatus();
				
		ibs6Status.setIbs6StateCode(woStatus);
		List<WorkorderStatus>  statusList = workorderStatusDao.getWorkorderStatusByIBS6StatusCode(ibs6Status);
		if(!statusList.isEmpty()){
			if (statusList.size() > 1) {
				//se verifica que el estado sea N-Pendiente que es el duplicado en Ibs6Status
				//para el caso de Pendiente y Rechazo
				for (WorkorderStatus workorderStatus : statusList) {
					if( workorderStatus.getWoStateCode().equalsIgnoreCase( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()) ){
						actualStatus = workorderStatus;
					}
				}
			}else
				actualStatus = statusList.get(0);
		}
		
		return actualStatus;
	}
	
	/**
	 * 
	 * Metodo: Valida el estado de la WO para determinar
	 * como proceder segun el estado en el cual llega de
	 * IBS.
	 * @param workOrder WorkOrder
	 * @param status WorkorderStatus
	 * @return Boolean
	 * @throws BusinessException
	 * @author jalopez
	 * @throws PropertiesException 
	 */
	private WorkorderStatus validateStatusWorkOrder(WorkorderStatus status, WorkOrder actualWorkOrder) throws BusinessException, PropertiesException{
		log.debug("== Inicia validateStatusWorkOrder/CoreWOBusiness ==");
		WorkorderStatus actualStatus = status;
		
		//se valida que el estado que proviene de la WO de IBS sea pendiente
		if (status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity())) {
			//se valida que el estado de la WO actual se encuentre rechazada, si es asi se deja el  estado actual
			if ( actualWorkOrder != null && actualWorkOrder.getWorkorderStatusByActualStatusId() != null ) {
				if ( actualWorkOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()) ) {
					actualStatus = actualWorkOrder.getWorkorderStatusByActualStatusId();
				}
			}
		}
		
		return actualStatus;
	}
	
	/**
	 * Metodo: Valida un objeto determinando si es nulo, en caso que sea nulo lanza
	 * una excepción y escribe en el log el atributo que fué validado
	 * @param parameterName Nombre del atributo a ser validado o mensaje para
	 * ser escrito en el log en caso que el objeto sea nulo
	 * @param value2Validate objeto a ser validado
	 * @param errorCode código de error a ser lanzado
	 * @param errorMessage mensaje de error a ser lanzado
	 * @throws BusinessException En caso que el objeto a validar sea nulo
	 * @author jjimenezh
	 */
	private void validateResult(String parameterName, Object value2Validate, String errorCode, String errorMessage) throws BusinessException{
		try {
			UtilsBusiness.assertNotNull(value2Validate, errorCode, errorMessage + " nombre del parámetro: " + parameterName);
		} catch (BusinessException e) {
			log.debug("== Error de validación de parámetros de respuesta de un WS de ibs: el parámetro: \""+parameterName+"\" llegó nulo ==");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Se almacena el estado actual que se bajo de ESB de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateWorkOrderIbsActualStatus(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder)throws DAOServiceException, DAOSQLException, BusinessException,PropertiesException {
		Ibs6Status ibsStatus = null;
		if( pIbsWorkOrder.getWorkorderStatusByActualStatusId() != null ){
			ibsStatus = this.ibs6StatusDAO.getIbs6StatusByIbsStateCode( pIbsWorkOrder.getWorkorderStatusByActualStatusId() );
			if( ibsStatus == null ){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR056.getCode(),ErrorBusinessMessages.CORE_CR056.getMessage());
			}
			workOrderDto.getWorkOrder().setIbsActualStatus( ibsStatus );
		}
	}
	
	@Override
	public void populateWorkOrderProcessSource(WorkOrderDTO workOrderDto)throws DAOServiceException, DAOSQLException, BusinessException,PropertiesException {
		WoProcessSource woProcessSource = null;
		if( workOrderDto != null && workOrderDto.getWorkOrder() != null
				&& workOrderDto.getWorkOrder().getWorkorderStatusByActualStatusId() != null 
				&& workOrderDto.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode() != null ){
			woProcessSource = coreWOBusiness.getWoProcessSourceByWoCodeActualStatus(workOrderDto.getWorkOrder().getWoCode(), workOrderDto.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode());
			
			if( woProcessSource != null ){
				workOrderDto.getWorkOrder().setProcessSourceId( woProcessSource.getId() );
			}
		}
	}

	/**
	 * Metodo: Pobla la agenda de la WO cuando baja de IBS atentida o finalizada
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException
	 */
	@Override
	public void populateWOAgendaInformationForRealizedAndFinalizedWO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		
		WorkOrderAgenda woAgenda = new WorkOrderAgenda();
		Date woAgendaDate = UtilsBusiness.dateFromGregorianCalendar(pIbsWorkOrder.getRequestedDeliveryDate());
		if ( woAgendaDate == null ) {
			woAgendaDate = UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrderDto.getWorkOrder().getUserId(), userDao);
		}
		woAgenda.setAgendationDate(woAgendaDate);
		
		woAgenda.setContactPerson(workOrderDto.getWorkOrder().getCustomer().getFirstName() + " " + workOrderDto.getWorkOrder().getCustomer().getLastName());
		woAgenda.setDescription(ApplicationTextEnum.IBS_SCHEDULE_WORK_ORDER.getApplicationTextValue());
		
		List<ServiceHour> allServiceHours = serviceHourDao.getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(workOrderDto.getWorkOrder().getCountry().getCountryCode(), CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity(), woAgendaDate);
		if ( allServiceHours.isEmpty() ) {
			throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + ": No se encontró una jornada activa que pueda cubrir el horario de atención de la WO solicitado: " + UtilsBusiness.dateToString(woAgendaDate, UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
		}
		int size = allServiceHours.size();		
		ServiceHour serviceHour = allServiceHours.get( new Random().nextInt(size) );
		woAgenda.setServiceHour(serviceHour);		
		woAgenda.setWoAssignment(workOrderDto.getWoAssignment());	
		workOrderDto.setWoAgenda( woAgenda );
	}

	/**
	 * Metodo: Pobla la agenda de la WO cuando baja de IBS agenda o reagenda.
	 * Se pone en otro metodo por control de cambios para realizar validacion que la WO este asignada al dummy, entonces quitarle
	 * la agenda, o que la jornada este vencida y se le quite la agenda y se envie correo
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException
	 * @author jnova
	 */
	@Override
	public void populateWOAgendaInformationForScheduledReScheduled(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		
//		//Cuando la WO baja agendada con el dealer dummy un proceso anterior la pone en estado activa y no se debe
//		//crear una agenda
//		boolean checkSchedule = checkPopulataWOAgendaInformationForScheduledReScheduled(pIbsWorkOrder, workOrderDto);
//		if(checkSchedule){
//			workOrderDto.setWoAgenda( null );
//			return;
//		}
		
		WorkOrderAgenda woAgenda = new WorkOrderAgenda();
		Date woAgendaDate = UtilsBusiness.dateFromGregorianCalendar(pIbsWorkOrder.getRequestedDeliveryDate());
		if ( woAgendaDate == null ) {
			woAgendaDate = UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrderDto.getWorkOrder().getUserId(), userDao);
		}
		
		woAgenda.setAgendationDate(woAgendaDate);
		
		woAgenda.setContactPerson(workOrderDto.getWorkOrder().getCustomer().getFirstName() + " " + workOrderDto.getWorkOrder().getCustomer().getLastName());
		woAgenda.setDescription(ApplicationTextEnum.IBS_SCHEDULE_WORK_ORDER.getApplicationTextValue());
		
		List<ServiceHour> allServiceHours = serviceHourDao.getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(workOrderDto.getWorkOrder().getCountry().getCountryCode(), CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity(), woAgendaDate);
		if ( allServiceHours.isEmpty() ) {
			throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + ": No se encontró una jornada activa que pueda cubrir el horario de atención de la WO solicitado: " + UtilsBusiness.dateToString(woAgendaDate, UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
		}
		int size = allServiceHours.size();		
		ServiceHour serviceHour = allServiceHours.get( new Random().nextInt(size) );
		
		//Se consulta el estado activo de la WO por si la validacion de la serviceHour no pasa ya que la transaccion se pierde
		//con la excepcion
		WorkorderStatus asignedStatus = workorderStatusDao.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity() );
		
		boolean isValidAgendationDate = this.coreWOBusiness.checkServiceHourForWoDownload(workOrderDto.getWorkOrder().getUserId(),
				                                                                          workOrderDto.getWorkOrder().getCountry().getId(),
				                                                                          woAgendaDate,
				                                                                          serviceHour);
		if( !isValidAgendationDate ){
			//Inactiva las agendas que existan activas en el sistema para la WO ya que pudo haberse agendado previamente desde HSP
			if( workOrderDto.getWorkOrder() != null && workOrderDto.getWorkOrder().getId() != null 
					&& workOrderDto.getWorkOrder().getId().longValue() > 0 ){
				//Se consulta la agenda activa
				WorkOrderAgenda activeAgenda =  workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(workOrderDto.getWorkOrder().getId());
				if( activeAgenda != null ){
					activeAgenda.setStatus( CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_INACTIVE.getCodeEntity() );
					workOrderAgendaDAO.updateWorkOrderAgenda(activeAgenda);
				}
			}
			String fechaAgendamiento = UtilsBusiness.dateToString(woAgendaDate, UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS);
			String dealerCode = workOrderDto.getDealerAssignmentCode().toString();
			String errorCode = ErrorBusinessMessages.CORE_CR060.getCode();
			Object[] params = {fechaAgendamiento ,dealerCode};
			String errorMsj = ErrorBusinessMessages.CORE_CR060.getMessage(params);
			workOrderDto.setWarning(true);
			workOrderDto.setErrorCode( errorCode );
			workOrderDto.setErrorMessage( errorMsj );
			workOrderDto.getWorkOrder().setAgendationExpired( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() );
			woAgenda = null;
		}
			
		//Caso en que no hubo ningun error con la jornada
		if( isValidAgendationDate ){
			woAgenda.setServiceHour(serviceHour);		
			woAgenda.setWoAssignment(workOrderDto.getWoAssignment());	
			workOrderDto.setWoAgenda( woAgenda );
			
			populateWOAgendaContactPerson(pIbsWorkOrder, workOrderDto);
			
		//En caso que haya algun error con la jornada se debe dejar en estado activa
		}else{
			workOrderDto.getWorkOrder().setWorkorderStatusByActualStatusId(asignedStatus);
		}
	}
	
	/**
	 * Metodo: Se consulta si fue agendada o reagendada por csr
	 * @author cduarte
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws PropertiesException 
	 */
	private void populateWOAgendaContactPerson(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException{
		//Se obtiene el codigo de la workOrder
		String woCode = workOrderDto.getWorkOrder().getWoCode();
		
		WorkOrderAgenda woAgenda = workOrderDto.getWoAgenda();
		
		//Se obtiene el estado de la workOrder en ibs
		String woIbsStatusCode = pIbsWorkOrder.getWorkorderStatusByActualStatusId();
		WorkorderStatus workorderStatus  = workorderStatusDao.getWorkorderStatusByIBS6StatusCode( woIbsStatusCode );
		String statusIBS=workorderStatus == null? "" : workorderStatus.getWoStateCode();
		//Se consulta si el codigo de la work order esta en la tabla de workorderCsr
		WorkOrderCSR workOrderCSR =  workOrderCSRDAO.getWorkOrderCSRByWoCodeStatusReSchedule(woCode,statusIBS);
		
		if(workOrderCSR!=null){
			if(woAgenda.getServiceHour().getId().equals(workOrderCSR.getServiceHour().getId())){
				Calendar woAgendaCalendar = Calendar.getInstance();
				woAgendaCalendar.setTime(woAgenda.getAgendationDate());
				Calendar agendaCSR = Calendar.getInstance();
				agendaCSR.setTime(workOrderCSR.getScheduleDate());
				if(woAgendaCalendar.get(Calendar.YEAR)==agendaCSR.get(Calendar.YEAR) &&
				   woAgendaCalendar.get(Calendar.MONTH)==agendaCSR.get(Calendar.MONTH)&&
				   woAgendaCalendar.get(Calendar.DATE)==agendaCSR.get(Calendar.DATE) ){
					woAgenda.setContactPerson(workOrderCSR.getContactPerson());
				}
				
			}
		}
		
	}

	/**
	 * 
	 * Metodo: Pobla los DTO's que son generados dependiendo del tipo de WO para relizar el movimiento de inventarios en HSP
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@Override
	public void populateWOMovementInventoryDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,WorkOrderDTO workOrderDto)throws DAOServiceException, DAOSQLException, PropertiesException,BusinessException {
		//Se consulta si la Wo ya estaba atendidad o finalizada para no procesar de nuevo el movimiento de inventario
		List<WoStatusHistory> listHistoryEventWo = new ArrayList<WoStatusHistory>();
		if(workOrderDto.getWorkOrder() != null && workOrderDto.getWorkOrder().getId() != null){
			listHistoryEventWo =  daoWoStatusHistory.getWoStatusHistoryByWoIDAttendOrFinish(workOrderDto.getWorkOrder().getId());
		}
		
		// Se realiza el proceso de movimiento de inventario solo si la Wo no habia sido atendida o finalizada
		if(listHistoryEventWo.size() == 0L){
			List<MovementInventoryDTO> movementInventoryDTOList = new ArrayList<MovementInventoryDTO>();
			if( workOrderDto.getWorkOrder() != null 
					&& workOrderDto.getWorkOrder().getWorkOrderServices() != null 
					&& !workOrderDto.getWorkOrder().getWorkOrderServices().isEmpty() ){
				//Variable para controlar que para el caso de instalacion solo se itere una sola se agreguen una sola vez los elementos de la SO
				boolean alreadyAddedSOElements = false;
				for ( WorkOrderService workOrderService : workOrderDto.getWorkOrder().getWorkOrderServices() ) {
					//Caso que el servicio sea de desconexion
					if ( workOrderService.getService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_DISCONNECTION.getCodeEntity()) ){
						movementInventoryDTOList.add( this.getDesinstallationMovementInventoryDTO(pIbsWorkOrder, workOrderService, workOrderDto) );
					//Si el servicio es de instalacion o de service
					}else if ( (  workOrderService.getService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL.getCodeEntity() ) )
							||  workOrderService.getService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE.getCodeEntity()) ){

						//Valida si es un servicio de SWAP
						if ( ( workOrderService.getService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL.getCodeEntity() ) 
								&& workOrderService.getService().getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_SWAP.getCodeEntity() ) ) 
								|| workOrderService.getService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE.getCodeEntity()) ) {

							if( workOrderService.getService().getAllowChangeElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
								movementInventoryDTOList.addAll( this.getSwopMovementInventoryDTO(workOrderService,workOrderDto) );
							}
						//Si es instalacion
						}else {
							if( workOrderService.getService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL.getCodeEntity() ) && (workOrderService.getService().getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_UPGRADE.getCodeEntity() )
									|| workOrderService.getService().getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_DOWNGRADE.getCodeEntity() ))){
								movementInventoryDTOList.addAll( this.getUpgradeDowngradeMovementInventoryDTO(workOrderService,workOrderDto) );
							}else if(!alreadyAddedSOElements){
								movementInventoryDTOList.add( this.getInstallationMovementInventoryDTO(pIbsWorkOrder, workOrderService, workOrderDto, alreadyAddedSOElements) );
							}
						}
					}
				}
			}
			workOrderDto.setMovementInventoryDTO(movementInventoryDTOList);
		}else{
			log.info("No se realiza movimiento de inventario en la descarga de Wo porque ya habia sido atendida o finalizada "
					+". WoCode "+workOrderDto.getWorkOrder().getWoCode());
		}
	}

	
	private MovementInventoryDTO getDesinstallationMovementInventoryDTO(com.directvla.schema.crm.common.v1_1.WorkOrder pIbsWorkOrder,WorkOrderService workOrderService,WorkOrderDTO workOrderDto){
		MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
		movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
		movementInventoryDTO.setDealerCode( workOrderDto.getDealerAssignmentCode() );
		movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );
		StringBuilder serial = new StringBuilder();
		StringBuilder serial2 = new StringBuilder();
		if( workOrderService.getSerialNumber() != null ){
			serial.append(workOrderService.getSerialNumber());
		}
		if( workOrderService.getLinkedSerialNumber() != null ){
			serial2.append(workOrderService.getLinkedSerialNumber());
		}
		movementInventoryDTO.setSerial( serial.toString() );
		movementInventoryDTO.setSerial2( serial2.toString() );
		return movementInventoryDTO;
	}
	
	private MovementInventoryDTO getDesinstallationMovementInventoryDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,WorkOrderService workOrderService,WorkOrderDTO workOrderDto){
		MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
		movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
		movementInventoryDTO.setDealerCode( workOrderDto.getDealerAssignmentCode() );
		movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );
		StringBuilder serial = new StringBuilder();
		StringBuilder serial2 = new StringBuilder();
		if( workOrderService.getSerialNumber() != null ){
			serial.append(workOrderService.getSerialNumber());
		}
		if( workOrderService.getLinkedSerialNumber() != null ){
			serial2.append(workOrderService.getLinkedSerialNumber());
		}
		movementInventoryDTO.setSerial( serial.toString() );
		movementInventoryDTO.setSerial2( serial2.toString() );
		return movementInventoryDTO;
	}
	
	/**
	 * 
	 * Metodo: Crea el objeto necesario para realizar la actualizacion del inventario de HSP a partir de una WO de instalacion
	 * @param pIbsWorkOrder
	 * @param workOrderService
	 * @param workOrderDto
	 * @param alreadyAddedSOElements
	 * @return <tipo> <descripcion>
	 * @author jnova
	 */
	private MovementInventoryDTO getInstallationMovementInventoryDTO(com.directvla.schema.crm.common.v1_1.WorkOrder pIbsWorkOrder,WorkOrderService workOrderService,WorkOrderDTO workOrderDto,boolean alreadyAddedSOElements){
		alreadyAddedSOElements = true;
		MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
		movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
		movementInventoryDTO.setDealerCode( workOrderDto.getDealerAssignmentCode() );
		movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );							
		if( pIbsWorkOrder.getShippingOrder() != null 
				&& pIbsWorkOrder.getShippingOrder().getProductList() != null
				&& pIbsWorkOrder.getShippingOrder().getProductList().getProduct() != null
				&& pIbsWorkOrder.getShippingOrder().getProductList().getProduct().size() >= 2 ){
			StringBuffer serial = new StringBuffer();
			StringBuffer serial1 = new StringBuffer();
			for( Product product : pIbsWorkOrder.getShippingOrder().getProductList().getProduct() ){
				if( product.getProductSerialNumber() != null && !product.getProductSerialNumber().equalsIgnoreCase("") ){
					if( serial.toString().equalsIgnoreCase("") ){
						serial.append( product.getProductSerialNumber() ); 
					} else if( serial1.toString().equalsIgnoreCase("") ){
						serial1.append( product.getProductSerialNumber() );
					} else if( !serial.toString().equalsIgnoreCase("") && !serial1.toString().equalsIgnoreCase("") ){
						break;
					}
				}
			}
			movementInventoryDTO.setSerial( serial.toString() );
			movementInventoryDTO.setSerial2( serial1.toString() );
		}
		return movementInventoryDTO;
	}
	
	/**
	 * 
	 * Metodo: Crea el objeto necesario para realizar la actualizacion del inventario de HSP a partir de una WO de instalacion
	 * @param pIbsWorkOrder
	 * @param workOrderService
	 * @param workOrderDto
	 * @param alreadyAddedSOElements
	 * @return <tipo> <descripcion>
	 * @author jnova, aharker, se duplica el metodo por PP
	 */
	private MovementInventoryDTO getInstallationMovementInventoryDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,WorkOrderService workOrderService,WorkOrderDTO workOrderDto,boolean alreadyAddedSOElements){
		alreadyAddedSOElements = true;
		MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
		movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
		movementInventoryDTO.setDealerCode( workOrderDto.getDealerAssignmentCode() );
		movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );							
		if( pIbsWorkOrder.getShippingOrder() != null 
				&& pIbsWorkOrder.getShippingOrder().getProductList() != null
				&& pIbsWorkOrder.getShippingOrder().getProductList().getProduct() != null ) {
				//&& (pIbsWorkOrder.getShippingOrder().getProductList().getProduct().size() >= 2) ) {
			StringBuffer serial = new StringBuffer();
			StringBuffer serial1 = new StringBuffer();
			for( co.com.directv.sdii.dto.esb.Product product : pIbsWorkOrder.getShippingOrder().getProductList().getProduct() ){
				if( product.getProductSerialNumber() != null && !product.getProductSerialNumber().equalsIgnoreCase("") ){
					if( serial.toString().equalsIgnoreCase("") ){
						serial.append( product.getProductSerialNumber() ); 
					} else if( serial1.toString().equalsIgnoreCase("") ){
						serial1.append( product.getProductSerialNumber() );
					} else if( !serial.toString().equalsIgnoreCase("") && !serial1.toString().equalsIgnoreCase("") ){
						break;
					}
				}
			}
			movementInventoryDTO.setSerial( serial.toString() );
			movementInventoryDTO.setSerial2( serial1.toString() );
		}
		return movementInventoryDTO;
	}
	
	/**
	 * 
	 * Metodo: Crea una coleccion con los objetos necesarios para actualziar el inventario de una WO de SWOP
	 * @param workOrderService
	 * @param workOrderDto
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author jnova
	 */
	private List<MovementInventoryDTO> getSwopMovementInventoryDTO(WorkOrderService workOrderService,WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException{
		List<MovementInventoryDTO> movementInventoryDTOList = new ArrayList<MovementInventoryDTO>();
		resourceProvisioningBroker = new ResourceProvisioningServiceBrokerImpl();
		SwopsDTORequest swopsDTORequest = new SwopsDTORequest();
		swopsDTORequest.setCountryCode( workOrderDto.getWorkOrder().getCountry().getCountryCode() );
		swopsDTORequest.setCustomerCode( workOrderDto.getWorkOrder().getCustomer().getCustomerCode() );
		swopsDTORequest.setCreateDateTime( this.getDateForSwopService(workOrderDto.getWorkOrder().getUserId(),workOrderDto.getWorkOrder().getCountry().getId()) );
		try{
			List<SwopsDTOResponse> swopResponse = resourceProvisioningBroker.getSwops( swopsDTORequest );
			if( swopResponse != null && !swopResponse.isEmpty() ){
				for( SwopsDTOResponse response : swopResponse ){
					MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
					movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
					if( workOrderDto.getDealerAssignmentCode() != null ){
						movementInventoryDTO.setDealerCode(workOrderDto.getDealerAssignmentCode());
					}
					movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );
					movementInventoryDTO.setReasonId( response.getReasonId() );
					movementInventoryDTO.setEventId(response.getEventId());
					movementInventoryDTO.setSwapHistoryEvent( response.getSwapHistoryEvent().longValue() );
					movementInventoryDTO.setSerial(response.getSerialCode());
					movementInventoryDTOList.add( movementInventoryDTO );
				}
			}
		} catch (BusinessException e) {
			MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
			movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
			movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );
			movementInventoryDTO.setErrorCode( e.getMessageCode() );
			movementInventoryDTO.setMessageError( e.getMessage() );
			movementInventoryDTO.setSwapHistoryEvent(-1L);
			movementInventoryDTOList.add( movementInventoryDTO );
		}
		return movementInventoryDTOList;
	}
	
	/**
	 *
	 * Metodo: Crea una coleccion con los objetos necesarios para actualziar el inventario de una WO de GetUpgradeDowngrade
	 * @param workOrderService
	 * @param workOrderDto
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author aharker
	 */
	private List<MovementInventoryDTO> getUpgradeDowngradeMovementInventoryDTO(WorkOrderService workOrderService,WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException{
		List<MovementInventoryDTO> movementInventoryDTOList = new ArrayList<MovementInventoryDTO>();
		resourceProvisioningBroker = new ResourceProvisioningServiceBrokerImpl();
		UpgradeAndDowngradeDTORequest upgradeAndDowngradeDTORequest = new UpgradeAndDowngradeDTORequest();
		upgradeAndDowngradeDTORequest.setCountryCode( workOrderDto.getWorkOrder().getCountry().getCountryCode() );
		upgradeAndDowngradeDTORequest.setCustomerCode( workOrderDto.getWorkOrder().getCustomer().getCustomerCode() );
		upgradeAndDowngradeDTORequest.setCreateDateTime( this.getDateForSwopService(workOrderDto.getWorkOrder().getUserId(),workOrderDto.getWorkOrder().getCountry().getId()) );
		try{
			List<UpgradeAndDowngradeDTOResponse> upgradeAndDowngradeDTOResponse = resourceProvisioningBroker.getUpgradeDowngrade( upgradeAndDowngradeDTORequest );
			if( upgradeAndDowngradeDTOResponse != null && !upgradeAndDowngradeDTOResponse.isEmpty() ){
				for( UpgradeAndDowngradeDTOResponse response : upgradeAndDowngradeDTOResponse ){
					MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
					movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
					if( workOrderDto.getDealerAssignmentCode() != null ){
						movementInventoryDTO.setDealerCode(workOrderDto.getDealerAssignmentCode());
					}
					movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );
					movementInventoryDTO.setReasonId( response.getReasonId() );
					movementInventoryDTO.setEventId(response.getEventId());
					if(response.getSwapHistoryEvent()!=null){
						movementInventoryDTO.setUpgradeAndDowngradeHistoryEvent( response.getSwapHistoryEvent().longValue() );
					}else{
						movementInventoryDTO.setUpgradeAndDowngradeHistoryEvent( -1L );
					}
					movementInventoryDTO.setSerial(response.getSerialCode());
					movementInventoryDTOList.add( movementInventoryDTO );
				}
			}
		} catch (BusinessException e) {
			MovementInventoryDTO movementInventoryDTO = new MovementInventoryDTO();
			movementInventoryDTO.setWoType( workOrderDto.getWorkOrder().getWoTypeByWoTypeId().getWoTypeCode() );
			movementInventoryDTO.setServiceCode( workOrderService.getService().getServiceCode() );
			movementInventoryDTO.setErrorCode( e.getMessageCode() );
			movementInventoryDTO.setMessageError( e.getMessage() );
			movementInventoryDTO.setSwapHistoryEvent(-1L);
			movementInventoryDTOList.add( movementInventoryDTO );
		}
		return movementInventoryDTOList;
	}
	
	/**
	 * 
	 * Metodo: Consulta la fecha para enviar al servicio de SWOP a partir del parametro de sistema cuando se baja una WO de SWAP
	 * @param userId
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author jnova
	 */
	private Date getDateForSwopService(Long userId , Long countryId) throws DAOServiceException, DAOSQLException, PropertiesException{
		SystemParameter param = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_SWOP_SERVICE_HOURS_BEFORE_ACTUAL_DATE.getCodeEntity(), countryId);
		Date ibsUserDate = UtilsBusiness.getCurrentTimeZoneDateByUserId( userId, userDao);
		//Por defecto se va a buscar 24 hrs antes de la fecha actual
		int numOfHoursBefore = 24;
		if( param != null && param.getValue() != null && !param.getValue().equals("") ){
			numOfHoursBefore = Integer.valueOf( param.getValue() );
		} 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( ibsUserDate );
		calendar.add(Calendar.HOUR_OF_DAY, -1 * numOfHoursBefore);
		return calendar.getTime();
	}

	/**
	 * 
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param countryCode codigo del pais de la work order
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException
	 */
	@Override
	public void populateWOAgendaInformationForAssign(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, String countryCode) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		//Se crea la agenda pero se controla que no se encuentra una jornada de servicio 
		WorkOrderAgenda woAgenda = null;
		try{
			woAgenda = this.buildWoAgendaFromIbs( pIbsWorkOrder, workOrderDto.getWorkOrder(), workOrderDto.getWoAssignment(), workOrderDto.getWorkOrder().getCustomer() );
		} catch (BusinessException e) {
			workOrderDto.setWarning(true);
			workOrderDto.setErrorCode(e.getMessageCode());
			workOrderDto.setErrorMessage(e.getMessage());
			return;
		}
		//En caso que en la WO venga con fecha de agendamiento se reporta a IBS un agendamiento y la WO queda en estado agendada
		if( woAgenda != null ){
			
			workOrderDto.setCallEsbChangeStateSchedule(true);
			WorkorderStatus scheduledStatus = this.workorderStatusDao.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
			workOrderDto.setWoAgenda(woAgenda);
			workOrderDto.getWorkOrder().setWorkorderStatusByActualStatusId( scheduledStatus );
//			//Se informa a IBS el agendamiento y se captura el error para que no afecte la descarga de WO
//			try{
//				
//				String workOrderReasonAssignedCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity(), countriesDao.getCountriesByCode(countryCode).getId(), systemParameterDao);
//			    //String workOrderReasonAssignedCode = CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity();
//			    
//				
//				WorkorderReason workorderReason = woReasonDAO.getWorkorderReasonByCode(workOrderReasonAssignedCode);
//				String ibsHistoryEventCode = this.coreWOServiceBroker.updateWorkOrderStatus(workOrderDto, workorderReason);
//				workOrderDto.setCreateHistoryWoStatusHistory( true );
//				workOrderDto.setHistoryWoReason( workorderReason );
//				workOrderDto.setHistoryDescription( woAgenda.getDescription() );
//				workOrderDto.setIbsHistoryEventCode( ibsHistoryEventCode );
//			} catch(BusinessException e){
//				workOrderDto.setWarning(true);
//				workOrderDto.setErrorCode(e.getMessageCode());
//				workOrderDto.setErrorMessage(e.getMessage());
//				return;
//			}
			
		}
		
	}

	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del técnico, de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Override
	public void populateTechnicalInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException{
		//Se carga el codigo del técnico encargado, de la WorkOrder
		validateResult("pIbsWorkOrder.getIbsTechnical()", pIbsWorkOrder.getIbsTechnical(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		Country country = workOrderDto.getWorkOrder().getCountry();
		Long ibsTechnical = Long.valueOf(pIbsWorkOrder.getIbsTechnical());
		Employee employee = this.employeeDAO.getEmployeeByIbsTechnical(null,ibsTechnical,country.getId());
		validateResult("No se encontró el técnico en SDII por el código de IBS: " + ibsTechnical + " e id de país: " + country.getId(), employee, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		workOrderDto.getWorkOrder().setIbsTechnical(employee.getIbsTechnical());			
	}
	
	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del building de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	@Override
	public void populateBuildingInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		
		//Informacion del Cliente
		String countryCode = workOrderDto.getWorkOrder().getCountry().getCountryCode();
		
		//Si hay edificio cargo toda la información.
		if(pIbsWorkOrder.getMDUCustomer() != null){
		
			validateResult("No se encuentra el ID del Building en el Msg", pIbsWorkOrder.getMDUCustomer().getID(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			validateResult("No se encuentra el IndividualName del Building en el Msg", pIbsWorkOrder.getMDUCustomer().getIndividualRole().getIndividualName().get(0), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			
			
			Long mduCustomerId = 0L;
			String mduCustomerIdStr = pIbsWorkOrder.getMDUCustomer().getID();
			try{
				mduCustomerId = Long.parseLong(mduCustomerIdStr);				
			}catch(NumberFormatException ex){
				String messageError = "Al tratar de convertir a número el código de dirección de un edificio: ";
				log.error(messageError + mduCustomerIdStr);
				throw new BusinessException(ErrorBusinessMessages.INVALID_NUMBER.getCode(), messageError + mduCustomerIdStr );
			}
			
			Building building = buildingDAO.getBuildingByIBSBuildingCode(mduCustomerId);
			
			if(building == null){
				building = new Building();
			}
			
			IndividualName individualName = pIbsWorkOrder.getMDUCustomer().getIndividualRole().getIndividualName().get(0);
			StringBuffer nameStringBuffer = new StringBuffer();
			if(individualName.getGivenNames() != null){
				nameStringBuffer.append(individualName.getGivenNames());
			}
			if(individualName.getMiddleNames() != null){
				nameStringBuffer.append(" ");
				nameStringBuffer.append(individualName.getMiddleNames());
			}
			if(individualName.getFamilyNames() != null){				
				nameStringBuffer.append(" ");
				nameStringBuffer.append(individualName.getFamilyNames());
			}
			
			building.setName(nameStringBuffer.toString());
			building.setCode(mduCustomerId);
			
			Country country = countriesDao.getCountriesByCode(countryCode);
			building.setCountry(country);
		
			building.setLastUpdateDate(new Date());
			
			building.setTypeChange(CodesBusinessEntityEnum.BUILDING_TYPE_CHANGE_DOWNLOAD.getCodeEntity());
			
			building.setBuildingAddresses(new HashSet<BuildingAddresses>());
			List<UrbanPropertyAddress> upaList = pIbsWorkOrder.getMDUCustomer().getAddressList().getUrbanPropertyAddress();
			for(UrbanPropertyAddress upa : upaList){
				String addressTypeCode = null;
				if(upa.getCategorizedByName().equalsIgnoreCase(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_DEFAULT.getCodeEntity())){
					addressTypeCode = CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT.getCodeEntity();
				}else if (upa.getCategorizedByName().equalsIgnoreCase(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_SHIPPING.getCodeEntity())){
					addressTypeCode = CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_SHIPPING.getCodeEntity();
				}else if (upa.getCategorizedByName().equalsIgnoreCase(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_BILLING.getCodeEntity())){
					addressTypeCode = CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_BILLING.getCodeEntity();
				}else if (upa.getCategorizedByName().equalsIgnoreCase(CodesBusinessEntityEnum.GET_VISTA_360_ADDRESS_TYPE_BANK.getCodeEntity())){
					addressTypeCode = CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_BANK.getCodeEntity();
				}
				
				validateResult("No se encontró en SDII el tpo de direccion con el name especificado desde IBS:" + upa.getCategorizedByName(), addressTypeCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
				AddressType addressType = addressTypeDao.getAddressTypeByCode(addressTypeCode);
				
				PostalCode postalCode = postalCodesDao.getPostalCodesByCodeByCountryCode(upa.getPostCode(), countryCode);
				validateResult("No se encontró en SDII el postal code con el código especificado desde IBS: " + upa.getPostCode(), postalCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
				
				BuildingAddresses buildingAddress = new BuildingAddresses();
				buildingAddress.setAddressCode(upa.getAddressCode());
				buildingAddress.setAddressType(addressType);
				buildingAddress.setStreetName(upa.getStreetName());
				buildingAddress.setStreetNrFirst(upa.getStreetNrFirst());
				buildingAddress.setStreetNrLast(upa.getStreetNrLast());
				buildingAddress.setStreetSuffix(upa.getStreetSuffix());
				buildingAddress.setExtraIndications(upa.getAdditionalAttribute2());
				buildingAddress.setNeighborhood(upa.getLocality());
				buildingAddress.setCareOfName(upa.getAdditionalAttribute1());
				buildingAddress.setPostalCode(postalCode);
				buildingAddress.setFlatApartment(upa.getFlatApartment());
				buildingAddress.setActive(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
				building.getBuildingAddresses().add(buildingAddress);
			}
			workOrderDto.setBuilding(building);
		}
	}
	

}
