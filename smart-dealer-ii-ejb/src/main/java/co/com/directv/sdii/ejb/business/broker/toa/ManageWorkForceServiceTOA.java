package co.com.directv.sdii.ejb.business.broker.toa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.core.CoreWOInventoryBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.pojo.City;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.IbsMediaContactType;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.model.pojo.State;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.vo.EmployeeMediaContactVO;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.model.vo.IBSWorkOrderInfoVO;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CitiesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.MediaContactTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.StatesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO;

import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.Category;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.ContactMediumCollection;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.EmailContact;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.FaxNumber;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.Individual;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.IndividualIdentificationCollection;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.IndividualName;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.IndividualNameCollection;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.NationalIdentityCardIdentification;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.PhysicalDeviceCollection;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.Response;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.ServiceProvider;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.TelecomTechnician;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.TelephoneNumber;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddServiceToWorkOrder;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddServiceToWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddTelecomTechnician;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddTelecomTechnicianRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.CancelWorkOrder;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.CancelWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditCustomerWorkOrder;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditCustomerWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditTelecomTechnician;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditTelecomTechnicianRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.RemoveWorkOrderService;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.RemoveWorkOrderServiceRequest;
import com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType;


/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * 
 * Fecha de Creación: 20/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="ManageWorkForceServiceLocalTOA",mappedName="ejb/ManageWorkForceServiceLocalTOA")
@Local({ManageWorkForceServiceLocalTOA.class})
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManageWorkForceServiceTOA  implements ManageWorkForceServiceLocalTOA{
	
	private final static Logger log = UtilsBusiness.getLog4J(ManageWorkForceServiceTOA.class);
	
	@EJB(name="Ibs6StatusDAOLocal",beanInterface=Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal daoIbs6Status;
	
	@EJB
	private SystemParameterDAOLocal systemParameterDAOLocal;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDao;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDao;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDAO;
	
	@EJB(name="ServiceHourDAOLocal", beanInterface=ServiceHourDAOLocal.class)
	private ServiceHourDAOLocal serviceHourDao;
	
	@EJB(name="ServiceDAOLocal", beanInterface=ServiceDAOLocal.class)
	private ServiceDAOLocal serviceDao;
	
	@EJB(name="CoreWOInventoryBusinessLocal",beanInterface=CoreWOInventoryBusinessLocal.class)
	private CoreWOInventoryBusinessLocal coreWOInventoryBusiness;
	
	@EJB(name="WoTypeDAOLocal", beanInterface=WoTypeDAOLocal.class)
	private WoTypeDAOLocal woTypeDao;
	
	@EJB(name="MediaContactTypesDAOLocal", beanInterface=MediaContactTypesDAOLocal.class)
	private MediaContactTypesDAOLocal mediaContactDAO;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealerDAO;

	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countriesDAO;
	
	@EJB(name="CitiesDAOLocal", beanInterface=CitiesDAOLocal.class)
	private CitiesDAOLocal citiesDAO;
	
	@EJB(name="StatesDAOLocal", beanInterface=StatesDAOLocal.class)
	private StatesDAOLocal statesDAO;
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param countryCode
	 * @param woCollection
	 * @return CustWorkOrdersResponseDTO
	 * @author jjimenezh
	 */
	public CustWorkOrdersResponseDTO buildCustomerWorkOrdersResponseDtoFromIbsResult(String customerKey, String countryCode, com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderCollection woCollection) {
		CustWorkOrdersResponseDTO result = new CustWorkOrdersResponseDTO();
		result.setCountryCode(countryCode);
		result.setCustomerCode(customerKey);
		
		List<IBSWorkOrderInfoVO> workOrders = buildWorkOrdersInfoFromResult(woCollection.getWorkOrder());
		
		result.setWorkOrders(workOrders);

		return result;
	}

	/**
	 * Metodo: <Descripcion>
	 * @param assignRequestDTO
	 * @param workOrder
	 * @return assignRequestDTO
	 */
	public AssignRequestDTO buildCustomerWorkOrdersResponseDtoFromIdResult(
			          AssignRequestDTO assignRequestDTO, com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder workOrder) {
		
		List<Long> shippingOrders = new ArrayList<Long>();
		List<String> services = new ArrayList<String>();
		
		assignRequestDTO.setActualStatusId(Long.parseLong(workOrder.getInteractionStatus().getId()));
		assignRequestDTO.setCountryIso2Code(assignRequestDTO.getCountryIso2Code());
		assignRequestDTO.setIbsCustomerCode(workOrder.getCustomerKey());
		
		List<com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderItem> workOrderItems= 
			workOrder.getWorkOrderItemList().getWorkOrderItem();
		
		for (com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderItem workOrderItem : workOrderItems) {
			
			services.add(workOrderItem.
					  getBusinessInteractionItemInvolvesService().getCode());
		}
		
		assignRequestDTO.setServices(services);
		
		assignRequestDTO.setIbsCustomerAddressCode(workOrder.getAddressId());
		
		assignRequestDTO.setIbsSaleDealerCode(
				workOrder.getStockhandler().getPartyRoleId());
		
		shippingOrders.add(Long.parseLong(
				workOrder.getShippingOrder().getShippingOrderKey()));
		
		assignRequestDTO.setShippingOrders(shippingOrders);
		assignRequestDTO.setDealerIdIBS(Long.parseLong(workOrder.getServiceProvider().getID()));
		assignRequestDTO.setScheduleDateIBS(UtilsBusiness.dateFromGregorianCalendar(workOrder.getRequestedDeliveryDate()));
		
		return assignRequestDTO;
	}
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param ibsWorkOrders
	 * @return List<IBSWorkOrderInfoVO>
	 * @author jjimenezh
	 */
	public List<IBSWorkOrderInfoVO> buildWorkOrdersInfoFromResult(	List<com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder> ibsWorkOrders) {
		List<IBSWorkOrderInfoVO> result = new ArrayList<IBSWorkOrderInfoVO>();
		
		IBSWorkOrderInfoVO localWoInfo;
		for (com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder ibsWo : ibsWorkOrders) {
			localWoInfo = buildIbsWoInfoFromIBSWo(ibsWo);
			result.add(localWoInfo);
		}
		return result;
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param ibsWo
	 * @return IBSWorkOrderInfoVO
	 * @author jjimenezh
	 */
	public IBSWorkOrderInfoVO buildIbsWoInfoFromIBSWo(com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder ibsWo) {
		IBSWorkOrderInfoVO result = new IBSWorkOrderInfoVO();
		result.setInstallationCompany(ibsWo.getServiceProvider() == null ? null : ibsWo.getServiceProvider().getName());
		Date iterationDate = UtilsBusiness.dateFromGregorianCalendar(ibsWo.getInteractionDate());
		result.setWorkOrderDate(iterationDate);
		result.setWorkOrderDescription(ibsWo.getDescription());
		result.setWorkOrderStatus(ibsWo.getInteractionStatus() == null ? null : ibsWo.getInteractionStatus().getName());
		if( ibsWo.getType() != null)
			result.setWorkOrderType(ibsWo.getType().getName());
		else
			result.setWorkOrderType("");
		return result;
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param ibsWorkOrderStatusCode
	 * @param countryCode
	 * @return GetCustomerWorkOrderByStatusRequest
	 * @author jjimenezh
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest buildGetCustomerWorkOrderByStatusRequest(String customerKey, String ibsWorkOrderStatusCode, String countryCode) {
		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest request = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest();
		
		com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType metadata = getMetadataType(countryCode);
		request.setRequestMetadata(metadata);
		
		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatus data = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatus();
		data.setCustomerKey(customerKey);
		data.setStatusID(Integer.parseInt(ibsWorkOrderStatusCode));
		
		request.setGetCustomerWorkOrderByStatus(data);
		return request;
	}
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param ibsWorkOrderStatusCode
	 * @param countryCode
	 * @return GetCustomerWorkOrderByStatusRequest
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	public EditCustomerWorkOrderRequest buildEditCustomerWorkOrderRequest(EditCustomerWorkOrderDTO editCustomerWorkOrderDTO) throws BusinessException {
		EditCustomerWorkOrderRequest request = new EditCustomerWorkOrderRequest();
		try{
			RequestMetadataType metadata = getMetadataType(editCustomerWorkOrderDTO.getCountryCode());
			request.setRequestMetadata(metadata);
			
			EditCustomerWorkOrder data = new EditCustomerWorkOrder();
			
			WorkOrder workOrder = new WorkOrder();
			workOrder.setID(editCustomerWorkOrderDTO.getWoCode());
			
			SystemParameter systemParameter = systemParameterDAOLocal.getSysParamByCodeAndCountryCode(CodesBusinessEntityEnum.SP_VALIDATE_TECHNICAL_WITH_DOCUMENT.getCodeEntity(),editCustomerWorkOrderDTO.getCountryCode());
			
			String validateWithDocument=systemParameter.getValue();
			
				TelecomTechnician technician = new TelecomTechnician();
				
				technician.setOwnsResource(new PhysicalDeviceCollection());
				technician.setDealer(new ServiceProvider());


				if(validateWithDocument.equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) && editCustomerWorkOrderDTO.getIbsTechnicalDocument() != null){
					NationalIdentityCardIdentification nci = new NationalIdentityCardIdentification();
					
					nci.setCardNr(editCustomerWorkOrderDTO.getIbsTechnicalDocument());
					
					IndividualIdentificationCollection iic = new IndividualIdentificationCollection();
					iic.getIndividualIdentifications().add(nci);
					
					technician.setIndividualRole(new Individual());
					technician.getIndividualRole().setIdentifiedBy(iic);
				}
				else{
					if(editCustomerWorkOrderDTO.getIbsTechnical()!=null){
						technician.setID(""+editCustomerWorkOrderDTO.getIbsTechnical());
					}
					
				}

				workOrder.setTechnician(technician);
			
			if(editCustomerWorkOrderDTO.getDealerCode() != null){
				ServiceProvider serviceProvider = new ServiceProvider();
				serviceProvider.setID(""+editCustomerWorkOrderDTO.getDealerCode());
				workOrder.setServiceProvider(serviceProvider);
			}
			
			//solo se envia la fecha en caso de ser un agendamiento.
			if(editCustomerWorkOrderDTO.getAgendationDate()!=null){
				workOrder.setRequestedDeliveryDate(UtilsBusiness.dateToGregorianCalendar(editCustomerWorkOrderDTO.getAgendationDate()));
			}
			
			if(editCustomerWorkOrderDTO.getWoDescription() != null && !editCustomerWorkOrderDTO.getWoDescription().equals("")){
				workOrder.setDescription(editCustomerWorkOrderDTO.getWoDescription());
			}
			
			data.setCustWorkOrd(workOrder);
			
			validateResult("No se asignó el código de la razón de la WO", editCustomerWorkOrderDTO.getReasonCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			data.setReason(editCustomerWorkOrderDTO.getReasonCode());

//			validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId()", workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//			
//			if(workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status() != null){
//				validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode()", workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//				String statusCode = workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode();
//				updateWorkOrder.setStatusCode(statusCode);
//			}
			
			Response r = new Response();
			data.setResponse(r);
			
			data.setReturnHistoryId(true);
			//Registra los parametros enviados en el log
			logUpdateWorkOrderRequest( data );
			
			request.setEditCustomerWorkOrder(data);
			
		}catch (Exception e) {
			if(!(e instanceof BusinessException)){
				throw new BusinessException(e.getMessage());
			}
		}
		return request;
	}
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param  assignRequestDTO
	 * @return GetCustomerWorkOrderByIdRequest
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest buildGetCustomerWorkOrderByIdRequest(
			          AssignRequestDTO assignRequestDTO) {
		
		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest request = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest();
		
		com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType metadata = getMetadataType(assignRequestDTO.getCountryIso2Code());
		request.setRequestMetadata(metadata);

		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderById data = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderById();
		data.setWorkOrderId(Integer.parseInt(assignRequestDTO.getWoCode()));

		request.setGetCustomerWorkOrderById(data);
		
		return request;
	}
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param  assignRequestDTO
	 * @return GetCustomerWorkOrderByIdRequest
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest buildGetCustomerWorkOrderByIdRequest(String countryCode,String woCode) {
		
		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest request = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest();
		
		com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType metadata = getMetadataType(countryCode);
		request.setRequestMetadata(metadata);

		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderById data = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderById();
		data.setWorkOrderId(Integer.parseInt(woCode));

		request.setGetCustomerWorkOrderById(data);
		
		return request;
	}
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param countryCode
	 * @return GetCustomerWorkOrderByStatusRequest
	 * @author jjimenezh
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest buildGetCustomerWorkOrderByStatusRequest(String customerKey, String countryCode) {
		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest request = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest();
		
		com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType metadata = getMetadataType(countryCode);
		request.setRequestMetadata(metadata);
		
		com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatus data = new com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatus();
		data.setCustomerKey(customerKey);
		
		request.setGetCustomerWorkOrderByStatus(data);
		
		logCustomerWorkOrdersRequest(request);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Construye el objeto request que es
	 * enviado al ivocar la operacion de CompleteWorkOrder
	 * @param attentionRequestDTO
	 * @return CompleteWorkOrderRequest
	 * @author jalopez
	 * @throws BusinessException 
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderRequest buildCompleteWorkOrderRequest(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException{
		com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderRequest request = new com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderRequest();
		validateCompleteWorkOrderRequest(attentionRequestDTO);
		com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType metadata = getMetadataType(attentionRequestDTO.getCountryCode());
		com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrder completeWO = new com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrder();
		com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder custWorkOrd = new com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder();
		com.directvla.schema.businessdomain.common.manageworkforce.v1_0.Response responseWorkForce = new com.directvla.schema.businessdomain.common.manageworkforce.v1_0.Response();
		
		custWorkOrd.setCustomerKey( attentionRequestDTO.getWorkorderVo().getCustomer().getCustomerCode() );
		custWorkOrd.setID( attentionRequestDTO.getWorkorderVo().getWoCode() );//Codigo de la WorkOrder
		custWorkOrd.setInteractionDate( UtilsBusiness.dateToGregorianCalendar( attentionRequestDTO.getFinalizationDate() ) );//Fecha de Modificacion
		//custWorkOrd.setDescription( attentionRequestDTO.getComments() );//Observaciones en la finalizacion
		custWorkOrd.setActionTaken( attentionRequestDTO.getComments() );//Observaciones en la finalizacion
		custWorkOrd.setInteractionDateComplete( UtilsBusiness.dateToGregorianCalendar( attentionRequestDTO.getFinalizationDate() ) );//Fecha de la Finalizacion
		custWorkOrd.setInvolves( String.valueOf( attentionRequestDTO.getDealerCode() ) );//Compañia Instaladora
		custWorkOrd.setWorkingTime( attentionRequestDTO.getWorkingTime() );//Horas de Trabajo
		
		responseWorkForce.setInteractionDate(  UtilsBusiness.dateToGregorianCalendar( attentionRequestDTO.getFinalizationDate() ) );//Fecha de Finalizacion
		//responseWorkForce.setDescription( attentionRequestDTO.getComments() );//Observaciones en la finalizacion
		responseWorkForce.setActionTaken(attentionRequestDTO.getComments());
		responseWorkForce.setInvolves( String.valueOf( attentionRequestDTO.getDealerCode() ) );//Compañia Instaladora
		
		completeWO.setResponse( responseWorkForce );
		completeWO.setCustWorkOrd( custWorkOrd );
		completeWO.setReasonComplete( Integer.parseInt( attentionRequestDTO.getWoReasonFinalization().getWorkorderReasonCode() ) );//Reason de Cierre
		request.setCompleteWorkOrder( completeWO );	
		request.setRequestMetadata( metadata );
		//log
		logCompleteWorkOrderRequest(request);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Valida la informacion requerida para
	 * la invocacion del servicio de CompleteWorkOrder
	 * @param attentionRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateCompleteWorkOrderRequest(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException {
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateCompleteWorkOrderRequest";		
		params[0] = "attentionRequestDTO";
		UtilsBusiness.validateRequestResponseWebService(params,attentionRequestDTO, ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "Workorder";
		UtilsBusiness.validateRequestResponseWebService(params,attentionRequestDTO.getWorkorderVo(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "Customer";
		UtilsBusiness.validateRequestResponseWebService(params,attentionRequestDTO.getWorkorderVo().getCustomer(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "WoCode";
		UtilsBusiness.validateRequestResponseWebService(params,attentionRequestDTO.getWorkorderVo().getWoCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "CustomerCode";
		UtilsBusiness.validateRequestResponseWebService(params,attentionRequestDTO.getWorkorderVo().getCustomer().getCustomerCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "CountryCode";
		UtilsBusiness.validateRequestResponseWebService(params,attentionRequestDTO.getCountryCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "FinalizationDate";
		UtilsBusiness.validateRequestResponseWebService(params,attentionRequestDTO.getFinalizationDate(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "Comments";
		UtilsBusiness.validateRequestResponseWebService(params, attentionRequestDTO.getComments(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "DealerCode";
		UtilsBusiness.validateRequestResponseWebService(params, attentionRequestDTO.getDealerCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "WorkingTime";
		UtilsBusiness.validateRequestResponseWebService(params, attentionRequestDTO.getWorkingTime(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "WoReasonFinalization";
		UtilsBusiness.validateRequestResponseWebService(params, attentionRequestDTO.getWoReasonFinalization(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "WorkorderReasonCode";
		UtilsBusiness.validateRequestResponseWebService(params, attentionRequestDTO.getWoReasonFinalization().getWorkorderReasonCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: imprime el log de los
	 * parametros enviados al servicio.
	 * @param request CompleteWorkOrderRequest
	 * @author jalopez
	 */
	public void logCompleteWorkOrderRequest(com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderRequest request){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" CustWorkOrd CustomerKey :" + request.getCompleteWorkOrder().getCustWorkOrd().getCustomerKey());
		buffer.append(" CustWorkOrd ID :" + request.getCompleteWorkOrder().getCustWorkOrd().getID());
		buffer.append(" CustWorkOrd InteractionDate :" + request.getCompleteWorkOrder().getCustWorkOrd().getInteractionDate());
		buffer.append(" CustWorkOrd Description :" + request.getCompleteWorkOrder().getCustWorkOrd().getDescription());
		buffer.append(" CustWorkOrd InteractionDateComplete :" + request.getCompleteWorkOrder().getCustWorkOrd().getInteractionDateComplete());
		buffer.append(" CustWorkOrd Involves :" + request.getCompleteWorkOrder().getCustWorkOrd().getInvolves());
		buffer.append(" CustWorkOrd WorkingTime :" + request.getCompleteWorkOrder().getCustWorkOrd().getWorkingTime());
		buffer.append(" Response InteractionDate :" + request.getCompleteWorkOrder().getResponse().getInteractionDate());
		//buffer.append(" Response Description :" + request.getCompleteWorkOrder().getResponse().getDescription());
		buffer.append(" Response Action Taken :" + request.getCompleteWorkOrder().getResponse().getActionTaken());
		buffer.append(" Response Involves :" + request.getCompleteWorkOrder().getResponse().getInvolves());
		buffer.append(" ReasonComplete :" + request.getCompleteWorkOrder().getReasonComplete());
		buffer.append(logMetadata(request.getRequestMetadata()));
		
		//Construcccion del mensaje
		params[0] = "CompleteWorkOrder";
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
	}
	
	/**
	 * 
	 * Metodo: imprime el log de los
	 * parametros enviados al servicio.
	 * @param request CompleteWorkOrderRequest
	 * @author jalopez
	 */
	public void logCustomerWorkOrdersRequest(com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest request){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" GetCustomerWorkOrderByStatus CustomerKey :" + request.getGetCustomerWorkOrderByStatus());		
		buffer.append(logMetadata(request.getRequestMetadata()));
		
		//Construcccion del mensaje
		params[0] = "CustomerWorkOrderByStatus";
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
	}
	
	/**
	 * 
	 * Metodo: Retorna el log de la informacion
	 * enviada de la Metadata
	 * @param inventoryDTO
	 * @return String
	 * @author jalopez
	 */
	private String logMetadata(com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType requestMetadataType){
		StringBuilder buffer = new StringBuilder();
		buffer.append(" Metadata [");
		buffer.append(" RequestID: "+requestMetadataType.getRequestID());
		buffer.append(" SourceID: "+requestMetadataType.getSourceID());
		buffer.append(" ]");
		return buffer.toString();
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones
	 * expuestas por el servicio web de core en ibs
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del rervicio web de core
	 * @author jjimenezh
	 */
	private com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType getMetadataType(String countryCode){
		com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.manageworkforce.v1_0.RequestMetadataType();		
		requestMetadataType.setRequestID(countryCode);
		requestMetadataType.setSourceID(countryCode);
		requestMetadataType.setValidate(false);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getMetadataType/ManageWorkForceServiceTOA ==");
		} finally {
			log.debug("== Termina la operaciÃ³n getMetadataType/ManageWorkForceServiceTOA ==");
		}
		return requestMetadataType;
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
	 * @author cduarte
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
	 * 
	 * Metodo: Registra en el log los parametros enviados
	 * @param UpdateWorkOrder updateWorkOrder
	 * @author jalopez
	 */
	private void logUpdateWorkOrderRequest(EditCustomerWorkOrder editCustomerWorkOrder){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" WorkOrderKey :" + editCustomerWorkOrder.getCustWorkOrd().getID());
		//buffer.append(" StatusCode: " + editCustomerWorkOrder.getCustWorkOrd().getInteractionStatus().getId());
		buffer.append(" ReasonCode: " + editCustomerWorkOrder.getReason());
		//buffer.append(" fecha:  " + editCustomerWorkOrder.getCustWorkOrd().get);				
		//buffer.append(" serviceProviderId: " + editCustomerWorkOrder.getCustWorkOrd().getServiceProvider());
		
		//Construcccion del mensaje
		params[0] = "UpdateWorkOrder";
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
	}

	public AddServiceToWorkOrderRequest buildAddServiceToWorkOrderRequest(
			String workOrderCode, String serviceCode, String serial,
			String countryCode, SystemParameterDAOLocal systemParameters) throws NumberFormatException, DAOServiceException, DAOSQLException, PropertiesException {
		AddServiceToWorkOrderRequest request = new AddServiceToWorkOrderRequest();
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadata);
		
		AddServiceToWorkOrder data = new AddServiceToWorkOrder();
		
		if(serial != null && serial.trim().length() > 0){
			data.setSerialNumber(serial);
		}else{
			data.setSerialNumber("");
		}
		
		data.setServiceCode(serviceCode);
		data.setWorkOrderID(Integer.valueOf(workOrderCode));
		
		data.setReasonId(Integer.parseInt(systemParameters.getSysParamByCodeAndCountryCode(CodesBusinessEntityEnum.REASON_FOR_ADD_SERVICE_TO_WORK_ORDER.getCodeEntity(), countryCode).getValue()));
		
		request.setAddServiceToWorkOrder(data);
		
		return request;
	}

	public RemoveWorkOrderServiceRequest buildRemoveWorkOrderServiceRequest(String woCode, String serviceCode, String countryCode, 
			SystemParameterDAOLocal systemParameters) throws NumberFormatException, DAOServiceException, DAOSQLException, PropertiesException {
		RemoveWorkOrderServiceRequest request = new RemoveWorkOrderServiceRequest();
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		requestMetadata.setSystemID("");
		request.setRequestMetadata(requestMetadata);
		RemoveWorkOrderService data = new RemoveWorkOrderService();
		data.setServiceCode(serviceCode);
		data.setWorkOrderID(woCode);
		data.setReasonId(Integer.parseInt(systemParameters.getSysParamByCodeAndCountryCode(CodesBusinessEntityEnum.REASON_FOR_REMOVE_WORK_ORDER_SERVICE.getCodeEntity(), countryCode).getValue()));
		request.setRemoveWorkOrderService(data);
		return request;
	}

	public CancelWorkOrderRequest buildCancelWorkOrderServiceRequest(
			String woCode, String comment, String reasonCode, String countryCode, String customerCode) {
		CancelWorkOrderRequest request = new CancelWorkOrderRequest();
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadata);
		CancelWorkOrder data = new CancelWorkOrder();
		data.setCustWorkOrd(new WorkOrder());
		data.getCustWorkOrd().setID(woCode);
		data.setReason(reasonCode);
		data.getCustWorkOrd().setCustomerKey(customerCode);
		request.setCancelWorkOrder(data);
		return request;
	}
	
	public EditTelecomTechnicianRequest buildEditTelecomTechnicianRequest(EmployeeVO eVo) throws DAOServiceException, DAOSQLException, HelperException, PropertiesException{
		EditTelecomTechnicianRequest  request = new EditTelecomTechnicianRequest();
		EditTelecomTechnician data = new EditTelecomTechnician();
		TelecomTechnician technician = new TelecomTechnician();
		ServiceProvider serviceProvider = new ServiceProvider();
		Individual individualRole = new Individual();
		ContactMediumCollection contactableVia = new ContactMediumCollection();
		Category status = new Category();
		//conseguir el USUARIO DEL TECNICO
		technician.setName(eVo.getFirstName());
		//if (eVo.getIbsTechnical()==null){
			//technician.setID("0");
		//}
		MediaContactType mediaCT = null;	
		for(EmployeeMediaContactVO empMediaContact : eVo.getEmployeesMediaContact()){
			mediaCT = null;
			mediaCT = mediaContactDAO.getMediaContactTypesByID(empMediaContact.getMediaContactType().getId());			
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity()) ){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					TelephoneNumber tel = new TelephoneNumber();
					tel.setType(ibsMediaContactType.getIbsCode());
					tel.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(tel);			
				}
			}
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity())){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					TelephoneNumber tel = new TelephoneNumber();
					tel.setType(ibsMediaContactType.getIbsCode());
					tel.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(tel);			
				}
			}
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity())){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					TelephoneNumber tel = new TelephoneNumber();
					tel.setType(ibsMediaContactType.getIbsCode());
					tel.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(tel);			
				}
			}
			if (mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getCodeEntity())){				
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					EmailContact email = new EmailContact();
					email.setEMailAddress(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(email);			
				}
			}
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity())){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					FaxNumber fax = new FaxNumber();
					fax.setType(ibsMediaContactType.getIbsCode());
					fax.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(fax);			
				}
			}
		}
		technician.setContactableVia(contactableVia);

		//IndividualIdentificationCollection iICollection = new IndividualIdentificationCollection();
		//InternetIdentification internetId = new InternetIdentification();
		//no consigo USUARIO IBS QUE PERTENECE AL TECNICO
		//iI.setScan();
		//iICollection.getIndividualIdentifications().add(internetId);

		//NationalIdentityCardIdentification nationalICI = new NationalIdentityCardIdentification();
		//nationalICI.setCardNr(eVo.getDocumentNumber());
		//iICollection.getIndividualIdentifications().add(nationalICI);
		//individualRole.setIdentifiedBy(iICollection);

		
		IndividualNameCollection iNCollectionName = new IndividualNameCollection();
		IndividualName individualName = new IndividualName();
		individualName.setGivenNames(eVo.getFirstName());
		individualName.setFamilyNames(eVo.getLastName());
		iNCollectionName.getIndividualNames().add(individualName);
		individualRole.setNameUsing(iNCollectionName);

		//dealer.setID(Long.toString(eVo.getDealerCode())); 
		Dealer dealer = dealerDAO.getDealerByID(eVo.getDealerId());
		serviceProvider.setID(Long.toString(dealer.getDealerCode()));

		technician.setDealer(serviceProvider);
		technician.setIndividualRole(individualRole);
		technician.setID(Long.toString(eVo.getIbsTechnical()));
		
		
		if(eVo.getEmployeeStatus().getStatusCode().equals(CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity())){
			status.setName("true");			
		}else { 
			status.setName("false"); 
		}
		technician.setStatus(status);
		data.setTechnician(technician);
		
		//cities
		City city = citiesDAO.getCitiesByID(eVo.getCity().getId());
		State state = statesDAO.getStatesByID(city.getState().getId());
		Country country = countriesDAO.getCountriesByID(state.getCountry().getId());
		RequestMetadataType requestMetadata = getMetadataType(country.getCountryCode());
		//requestMetadata.setSystemID("");
		request.setRequestMetadata(requestMetadata);
		request.setEditTelecomTechnician(data);

		return request;
	} 
	
	public AddTelecomTechnicianRequest buildAddTelecomTechnicianRequest(EmployeeVO eVo) throws DAOServiceException, DAOSQLException, HelperException, PropertiesException{
		AddTelecomTechnicianRequest  request = new AddTelecomTechnicianRequest();
		AddTelecomTechnician data = new AddTelecomTechnician();
		TelecomTechnician technician = new TelecomTechnician();
		ServiceProvider serviceProvider = new ServiceProvider();
		Individual individualRole = new Individual();
		ContactMediumCollection contactableVia = new ContactMediumCollection();
		Category status = new Category();
		
		technician.setName(eVo.getFirstName());
		//if (eVo.getIbsTechnical()==null){
			//technician.setID("0");
		//}
		MediaContactType mediaCT = null;	
		for(EmployeeMediaContactVO empMediaContact : eVo.getEmployeesMediaContact()){
			mediaCT = null;
			mediaCT = mediaContactDAO.getMediaContactTypesByID(empMediaContact.getMediaContactType().getId());			
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity()) ){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					TelephoneNumber tel = new TelephoneNumber();
					tel.setType(ibsMediaContactType.getIbsCode());
					tel.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(tel);			
				}
			}
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity())){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					TelephoneNumber tel = new TelephoneNumber();
					tel.setType(ibsMediaContactType.getIbsCode());
					tel.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(tel);			
				}
			}
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity())){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					TelephoneNumber tel = new TelephoneNumber();
					tel.setType(ibsMediaContactType.getIbsCode());
					tel.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(tel);			
				}
			}
			if (mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getCodeEntity())){				
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					EmailContact email = new EmailContact();
					email.setEMailAddress(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(email);			
				}
			}
			if(mediaCT.getMediaCode().equals(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity())){
				List<IbsMediaContactType> ibsMediaContactTypeList = mediaContactDAO.getIbsMediaContactTypeByMediaContactTypeId(empMediaContact.getMediaContactType().getId());	
				for(IbsMediaContactType ibsMediaContactType : ibsMediaContactTypeList){
					FaxNumber fax = new FaxNumber();
					fax.setType(ibsMediaContactType.getIbsCode());
					fax.setNumber(empMediaContact.getMediaContactValue());
					contactableVia.getContactMedium().add(fax);			
				}
			}
		}
		technician.setContactableVia(contactableVia);

		IndividualIdentificationCollection iICollection = new IndividualIdentificationCollection();
		//InternetIdentification internetId = new InternetIdentification();
		//no consigo USUARIO IBS QUE PERTENECE AL TECNICO
		//iI.setScan();
		//iICollection.getIndividualIdentifications().add(internetId);

		NationalIdentityCardIdentification nationalICI = new NationalIdentityCardIdentification();
		nationalICI.setCardNr(eVo.getDocumentNumber());
		iICollection.getIndividualIdentifications().add(nationalICI);
		individualRole.setIdentifiedBy(iICollection);
		
		IndividualNameCollection iNCollectionName = new IndividualNameCollection();
		IndividualName individualName = new IndividualName();
		individualName.setGivenNames(eVo.getFirstName());
		individualName.setFamilyNames(eVo.getLastName());
		iNCollectionName.getIndividualNames().add(individualName);
		individualRole.setNameUsing(iNCollectionName);

		//dealer.setID(Long.toString(eVo.getDealerCode())); 
		Dealer dealer = dealerDAO.getDealerByID(eVo.getDealerId());
		serviceProvider.setID(Long.toString(dealer.getDealerCode()));

		technician.setDealer(serviceProvider);
		technician.setIndividualRole(individualRole);
		//TODO: setear el valor que corresponda
		//technician.setID(Long.toString(new Long(0)));
		status.setName("true");
		technician.setStatus(status);
		
		data.setTechnician(technician);
		data.setReason(0);
		//cities
		City city = citiesDAO.getCitiesByID(eVo.getCity().getId());
		State state = statesDAO.getStatesByID(city.getState().getId());
		Country country = countriesDAO.getCountriesByID(state.getCountry().getId());
		RequestMetadataType requestMetadata = getMetadataType(country.getCountryCode());
		//requestMetadata.setSystemID("");
		request.setRequestMetadata(requestMetadata);
		request.setAddTelecomTechnician(data);

		return request;
	} 

}
