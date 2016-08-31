/**
 * Creado 18/02/2011 16:41:30
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerRemote;
import co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.toa.ManageWorkForceServiceLocalTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.CustomerValidationVista360DTO;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.pojo.CustomerAddresses;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.persistence.dao.config.AddressTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO;

import com.directvla.contract.businessdomain.manageworkforce.v1_0.AddCustomerWorkOrderException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.AddServiceToWorkOrderException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.CancelWorkOrderException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.CompleteWorkOrderException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.EditCustomerWorkOrderException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrdersByCriteriaException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.GetNewWorkOrderServiceException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.GetWorkOrderException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.ManageWorkforcePt;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.RemoveWorkOrderServiceException;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.AddTelecomTechnicianException;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder;
import com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderCollection;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddServiceToWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddServiceToWorkOrderResponse;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddTelecomTechnicianRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddTelecomTechnicianResponse;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.CancelWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.CancelWorkOrderResponse;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditCustomerWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditCustomerWorkOrderResponse;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditTelecomTechnicianRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditTelecomTechnicianResponse;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdResponse;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdResult;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusResponse;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusResult;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.RemoveWorkOrderServiceRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.RemoveWorkOrderServiceResponse;



/**
 * Desacopla la invocación de servicio web externo de manageWorkForce
 * para convertir la respuesta en objetos locales 
 * 
 * Fecha de Creación: 18/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal
 */
@Stateless(name="ManageWorkForceServiceBrokerLocal")
@Local({ManageWorkForceServiceBrokerLocal.class})
@Remote({ManageWorkForceServiceBrokerRemote.class})
public class ManageWorkForceServiceBrokerImpl extends IBSWSBase implements ManageWorkForceServiceBrokerLocal, ManageWorkForceServiceBrokerRemote, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(ManageWorkForceServiceBrokerImpl.class);
	
	@EJB(name = "ManageWorkForceServiceLocalTOA", beanInterface = ManageWorkForceServiceLocalTOA.class)
	private ManageWorkForceServiceLocalTOA manageWorkForceServiceTOA;
	
	@EJB(name = "Vista360ServiceBrokerLocal", beanInterface = Vista360ServiceBrokerLocal.class)
	private Vista360ServiceBrokerLocal vista360ServiceBroker;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	
	@EJB(name = "AddressTypeDAOLocal", beanInterface = AddressTypeDAOLocal.class)
	private AddressTypeDAOLocal addressTypeDAOLocal;
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal#getCustomerWorkOrdersFromIBS(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(String customerKey, String ibsWorkOrderStatusCode, String countryCode) throws BusinessException {
		
		log.debug("== Inicia la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();
			
			GetCustomerWorkOrderByStatusRequest request = manageWorkForceServiceTOA.buildGetCustomerWorkOrderByStatusRequest(customerKey, ibsWorkOrderStatusCode, countryCode);
			
			GetCustomerWorkOrderByStatusResponse response = service.getCustomerWorkOrderByStatus(request);
			GetCustomerWorkOrderByStatusResult result = response.getGetCustomerWorkOrderByStatusResult();
			
			WorkOrderCollection woCollection = result.getWorkorders();
			
			CustWorkOrdersResponseDTO resultDto = manageWorkForceServiceTOA.buildCustomerWorkOrdersResponseDtoFromIbsResult(customerKey, countryCode, woCollection);
			
			return resultDto;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal#getCustomerWorkOrdersFromIBS(java.lang.String, java.lang.String)
	 */
	@Override
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS( String customerKey,	String countryCode) throws BusinessException {
		
		log.debug("== Inicia la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();
			
			GetCustomerWorkOrderByStatusRequest request = manageWorkForceServiceTOA.buildGetCustomerWorkOrderByStatusRequest(customerKey, countryCode);
			
			GetCustomerWorkOrderByStatusResponse response = service.getCustomerWorkOrderByStatus(request);
			GetCustomerWorkOrderByStatusResult result = response.getGetCustomerWorkOrderByStatusResult();
			
			WorkOrderCollection woCollection = result.getWorkorders();
			
			CustWorkOrdersResponseDTO resultDto = manageWorkForceServiceTOA.buildCustomerWorkOrdersResponseDtoFromIbsResult(customerKey, countryCode, woCollection);
			
			return resultDto;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal#editCustomerWorkOrder(co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO)
	 */
	@Override
	public String editCustomerWorkOrder(EditCustomerWorkOrderDTO editCustomerWorkOrderDTO) throws BusinessException {
		
		log.debug("== Inicia la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();
			
			EditCustomerWorkOrderRequest request = manageWorkForceServiceTOA.buildEditCustomerWorkOrderRequest(editCustomerWorkOrderDTO);
			
			EditCustomerWorkOrderResponse response = service.editCustomerWorkOrder(request);
			
			return String.valueOf(response.getEditCustomerWorkOrderResult().getHistoryId());
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getCustomerWorkOrdersFromIBS/ManageWorkForceServiceBrokerImpl ==");
		}
	}

	@Override
	public String addServiceToWorkOrder(String workOrderCode, String serviceCode, String serial, String countryCode) throws BusinessException {
		String returnValue = "";
		log.debug("== Inicia la operación addServiceToWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();
			AddServiceToWorkOrderRequest request = manageWorkForceServiceTOA.buildAddServiceToWorkOrderRequest(workOrderCode,serviceCode,serial,countryCode, systemParameterDAO);
			AddServiceToWorkOrderResponse response = service.addServiceToWorkOrder(request);
			UtilsBusiness.assertNotNull(response, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(response.getAddServiceToWorkOrderResult(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(response.getAddServiceToWorkOrderResult().getWorkOrderServiceKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			returnValue = response.getAddServiceToWorkOrderResult().getWorkOrderServiceKey();
			return returnValue;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación addServiceToWorkOrder/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación addServiceToWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		}
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal#getCustomerWorkOrdersFromIBS(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public AssignRequestDTO getInfoCustomerWorkOrdersById(AssignRequestDTO assignRequestDTO, Long countryId) throws BusinessException {

		log.debug("== Inicia la operación getInfoCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
		try {
			assignRequestDTO = getCustomerWorkOrdersById(assignRequestDTO);
			
			CustomerValidationVista360DTO validationVista360DTO = new CustomerValidationVista360DTO();
			validationVista360DTO.setValidatePostalCodeDefault(true);
			List<String> listAddressCodes = new ArrayList<String>();
			listAddressCodes.add(assignRequestDTO.getIbsCustomerAddressCode());
			validationVista360DTO.setListAdressCodesValidate(listAddressCodes);
			
			CustomerInfoAggregatedDTO customerInfoAggregatedDTO = vista360ServiceBroker.getVista360(assignRequestDTO.getIbsCustomerCode(), assignRequestDTO.getCountryIso2Code(), validationVista360DTO);
			
			CustomerVO customerVO = customerInfoAggregatedDTO.getCustomerInfoDTO().getCustomerVO();
			
			assignRequestDTO.setIbsCustomerName(customerVO.getFirstName() + " "+ customerVO.getLastName());
			assignRequestDTO.setIbsCustomerTypeCode(customerVO.getCustType().getCustomerType().getCustomerTypeCode());
			assignRequestDTO.setIbsCustomerTypeName(customerVO.getCustType().getCustomerType().getCustomerTypeName());
	
			assignRequestDTO.setIbsCustomerClassCode(customerVO.getCustType().getCustomerClass().getCustomerClassCode());
			assignRequestDTO.setIbsCustomerClassName(customerVO.getCustType().getCustomerClass().getCustomerClassName());
	
			CustomerAddresses customerAddresses = getCustomerAddressesByAddressCode(assignRequestDTO.getIbsCustomerAddressCode(),customerInfoAggregatedDTO);
			assignRequestDTO.setIbsCustomerAddressCode(customerAddresses.getAddressCode());
			assignRequestDTO.setPostalCode(customerAddresses.getPostalCode().getPostalCodeCode());
			assignRequestDTO.setPostalName(customerAddresses.getPostalCode().getPostalCodeName());
			assignRequestDTO.setIbsBuildingCode(customerVO.getCodeBuilding());
			//assignRequestDTO.setIbsCustomerAddress(customerVO.getCustomeraddress());
			assignRequestDTO.setIbsCustomerAddress(customerAddresses.getStreetName() + " " + customerAddresses.getStreetSufix() + " " + customerAddresses.getNeighborhood() );
			
			return assignRequestDTO;
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getInfoCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getInfoCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
		}
	}
	
	private CustomerAddresses getCustomerAddressesByAddressCode(String addressCode,CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		
		if (customerInfoAggregatedDTO != null
				&& customerInfoAggregatedDTO.getCustomerInfoDTO() != null
				&& customerInfoAggregatedDTO.getCustomerInfoDTO()
						.getCustomerVO() != null
				&& customerInfoAggregatedDTO.getCustomerInfoDTO()
						.getCustomerVO().getCustomerAddresses() != null) {
			for (CustomerAddresses ca : customerInfoAggregatedDTO
					.getCustomerInfoDTO().getCustomerVO()
					.getCustomerAddresses()) {
				if (ca.getAddressCode().equalsIgnoreCase(addressCode)) {
					return ca;
				}
			}
			Long idDefault = addressTypeDAOLocal.getAddressTypeByCode(
						CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT
								.getCodeEntity()).getId();
			for (CustomerAddresses ca : customerInfoAggregatedDTO
					.getCustomerInfoDTO().getCustomerVO()
					.getCustomerAddresses()) {
				if (ca.getAddressType().getId().equals(idDefault)) {
					return ca;
				}
			}
			
		}

		throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL051.getCode(), ErrorBusinessMessages.ALLOCATOR_AL051.getMessage());
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal#getCustomerWorkOrdersFromIBS(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public AssignRequestDTO getCustomerWorkOrdersById(AssignRequestDTO assignRequestDTO) throws BusinessException {
	
		log.debug("== Inicia la operación getCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
    	Object[] params = new Object[1];
		try {
			
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();
			
			GetCustomerWorkOrderByIdRequest request = manageWorkForceServiceTOA.buildGetCustomerWorkOrderByIdRequest(assignRequestDTO);
			
			GetCustomerWorkOrderByIdResponse response = service.getCustomerWorkOrderById(request);
			
			GetCustomerWorkOrderByIdResult result = response.getGetCustomerWorkOrderByIdResult();
			
			WorkOrder workOrder = result.getWorkorder();
			
			assignRequestDTO = manageWorkForceServiceTOA.buildCustomerWorkOrdersResponseDtoFromIdResult(assignRequestDTO, workOrder);
			
			return assignRequestDTO;
			
		} catch(GetCustomerWorkOrderByIdException gcE){
			
			com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdException faultInfo;
			faultInfo = ((GetCustomerWorkOrderByIdException)gcE).getFaultInfo();
			if(faultInfo.getEntityNotFoundException() != null){
		
				params[0] = assignRequestDTO.getWoCode();
				
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_NOT_FOUND_IBS.getCode(), ErrorBusinessMessages.WORKORDER_NOT_FOUND_IBS.
						getMessage(params), UtilsBusiness.getParametersWS(params));
				
			}else if(faultInfo.getServiceUnavailableException() != null){
				params[0] = "getGetCustomerWorkOrderByIdResult";
				
				throw new BusinessException(ErrorBusinessMessages.ERROR_INVOQUE_SERVICE_IBS.getCode(), ErrorBusinessMessages.ERROR_INVOQUE_SERVICE_IBS.
						getMessage(params), UtilsBusiness.getParametersWS(params));
				
			}else{
				throw manageServiceException(gcE);
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
			
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal#getCustomerWorkOrdersFromIBS(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder getCustomerWorkOrdersById(String countryCode,String woCode) throws BusinessException {
	
		log.debug("== Inicia la operación getCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
		Object[] params = new Object[1];
    	try {
			
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();
			
			GetCustomerWorkOrderByIdRequest request = manageWorkForceServiceTOA.buildGetCustomerWorkOrderByIdRequest(countryCode,woCode);
			
			GetCustomerWorkOrderByIdResponse response = service.getCustomerWorkOrderById(request);
			
			GetCustomerWorkOrderByIdResult result = response.getGetCustomerWorkOrderByIdResult();
			
			return result.getWorkorder();
			
    		} catch(GetCustomerWorkOrderByIdException gcE){
			
			com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdException faultInfo;
			faultInfo = ((GetCustomerWorkOrderByIdException)gcE).getFaultInfo();
			if(faultInfo.getEntityNotFoundException() != null){
		
				params[0] = woCode;
				
				throw new BusinessException(
						  ErrorBusinessMessages.WORKORDER_NOT_FOUND_IBS.getCode(),
						  ErrorBusinessMessages.WORKORDER_NOT_FOUND_IBS.getMessage(params), 
						      UtilsBusiness.getParametersWS(params));
				
			}else if(faultInfo.getServiceUnavailableException() != null){
				params[0] = "getGetCustomerWorkOrderByIdResult";
				
				throw new BusinessException(
						  ErrorBusinessMessages.ERROR_INVOQUE_SERVICE_IBS.getCode(),
						  ErrorBusinessMessages.ERROR_INVOQUE_SERVICE_IBS.getMessage(params), 
						      UtilsBusiness.getParametersWS(params));
				
			}else{
				throw manageServiceException(gcE);
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
			
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getCustomerWorkOrdersById/ManageWorkForceServiceBrokerImpl ==");
		}
	}
	
	@Override
	public void completeWorkOrder(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException {
		log.debug("== Inicia la operación completeWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();
			CompleteWorkOrderRequest request = manageWorkForceServiceTOA.buildCompleteWorkOrderRequest(attentionRequestDTO);			
			service.completeWorkOrder( request );
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación completeWorkOrder/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación completeWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IServiceBroker#manageServiceException(java.lang.Throwable)
	 */
	@Override
	public BusinessException manageServiceException(Throwable e) {
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		BusinessException be = null;
		//Excepción para la operación addCustomerContact
		if(e instanceof AddCustomerWorkOrderException){
			errorMessage = getAddCustomerWorkOrderExceptionMessage(((AddCustomerWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof AddServiceToWorkOrderException){
			errorMessage = getAddServiceToWorkOrderExceptionMessage(((AddServiceToWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof CancelWorkOrderException){
			errorMessage = getCancelWorkOrderExceptionMessage(((CancelWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof CompleteWorkOrderException){
			errorMessage = getCompleteWorkOrderExceptionMessage(((CompleteWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof EditCustomerWorkOrderException){
			errorMessage = getEditCustomerWorkOrderExceptionMessage(((EditCustomerWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof GetCustomerWorkOrderByIdException){
			errorMessage = getGetCustomerWorkOrderByIdExceptionMessage(((GetCustomerWorkOrderByIdException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof GetCustomerWorkOrderByStatusException){
			errorMessage = getGetCustomerWorkOrderByStatusExceptionMessage(((GetCustomerWorkOrderByStatusException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof GetCustomerWorkOrdersByCriteriaException){
			errorMessage = getGetCustomerWorkOrdersByCriteriaExceptionMessage(((GetCustomerWorkOrdersByCriteriaException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof GetNewWorkOrderServiceException){
			errorMessage = getGetNewWorkOrderServiceExceptionMessage(((GetNewWorkOrderServiceException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof GetWorkOrderException){
			errorMessage = getGetWorkOrderExceptionMessage(((GetWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof RemoveWorkOrderServiceException){
			errorMessage = getRemoveWorkOrderServiceExceptionMessage(((RemoveWorkOrderServiceException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof AddServiceToWorkOrderException ){			
			errorMessage = getAddServiceToWorkOrderExceptionMessage(((AddServiceToWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof RemoveWorkOrderServiceException ){			
			errorMessage = getRemoveWorkOrderServiceExceptionMessage(((RemoveWorkOrderServiceException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof CancelWorkOrderException ){			
			errorMessage = getCancelWorkOrderExceptionMessage(((CancelWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof AddTelecomTechnicianException ){
			errorMessage = getAddTelecomTechnicianExceptionMessage(((AddTelecomTechnicianException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else{
			be = super.manageException(e);
		}
		
		log.error("Excepción al invocar un servicio de IBS, el mensaje retornado es: código: " + be.getMessageCode() + " mensaje: "  + be.getMessage(), be);
		return be;
		
	}
	
	/**
	 * Metodo: Obtiene el mensaje de excepción
	 * @param faultInfo información de la excepción
	 * @param defaultMessage mensaje por defecto a mostrar
	 * @return <tipo> <descripcion>
	 * @author jjimenezh
	 */
	
		private String getCancelWorkOrderExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.CancelWorkOrderException faultInfo,
			String errorMessage) {
		if(faultInfo.getEntityAlreadyExistsException()!=null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityNotFoundException()!=null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getEntityInUseException()!=null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getInternalErrorException()!=null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getMissingParameterException()!=null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException()!=null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException()!=null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException()!=null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException()!=null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException()!=null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getAccessDeniedException()!=null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException()!=null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException()!=null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}



	private String getAddCustomerWorkOrderExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.AddCustomerWorkOrderException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getAddServiceToWorkOrderExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.AddServiceToWorkOrderException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getCompleteWorkOrderExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getEditCustomerWorkOrderExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.EditCustomerWorkOrderException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getRemoveWorkOrderServiceExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.RemoveWorkOrderServiceException faultInfo,
			String defaultMessage) {
		if(faultInfo.getEntityAlreadyExistsException()!=null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityNotFoundException()!=null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getEntityInUseException()!=null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getInternalErrorException()!=null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getMissingParameterException()!=null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException()!=null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException()!=null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException()!=null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException()!=null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException()!=null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getAccessDeniedException()!=null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException()!=null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException()!=null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return defaultMessage;
	}

	private String getGetCustomerWorkOrderByIdExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getGetCustomerWorkOrderByStatusExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getGetCustomerWorkOrdersByCriteriaExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrdersByCriteriaException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getGetNewWorkOrderServiceExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.GetNewWorkOrderServiceException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getGetWorkOrderExceptionMessage(
			com.directvla.schema.businessdomain.manageworkforce.v1_0.GetWorkOrderException faultInfo,
			String defaultMessage) {
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
		return defaultMessage;
	}
	
	private String getAddTelecomTechnicianExceptionMessage( 
			com.directvla.schema.businessdomain.manageworkforce.v1_0.AddTelecomTechnicianException faultInfo, String defaultMessage ) {
		if(faultInfo.getEntityAlreadyExistsException()!=null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}
		return defaultMessage;
	}
		
	public String removeServiceFromWorkOrder(String woCode, String serviceCode,String countryCode)throws BusinessException{
		String returnValue = "";
		log.debug("== Inicia la operación removeServiceFromWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();

			RemoveWorkOrderServiceRequest request = manageWorkForceServiceTOA.buildRemoveWorkOrderServiceRequest(woCode,serviceCode,countryCode, systemParameterDAO);
			RemoveWorkOrderServiceResponse response = service.removeWorkOrderService(request);
			UtilsBusiness.assertNotNull(response, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(response.getRemoveWorkOrderServiceResult(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(response.getRemoveWorkOrderServiceResult().getWorkOrderServiceKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			returnValue = response.getRemoveWorkOrderServiceResult().getWorkOrderServiceKey();
			return returnValue;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación removeServiceFromWorkOrder/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación removeServiceFromWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		}

	}
	
	public void cancelWorkOrder(String woCode, String comment, String reasonCode, String countryCode, String customerCode)throws BusinessException{
		log.debug("== Inicia la operación removeServiceFromWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		try {
			UtilsBusiness.assertNotNull(woCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(reasonCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(customerCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();

			CancelWorkOrderRequest request = manageWorkForceServiceTOA.buildCancelWorkOrderServiceRequest(woCode,comment,reasonCode, countryCode, customerCode);
			CancelWorkOrderResponse response = service.cancelWorkOrder(request);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación removeServiceFromWorkOrder/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación removeServiceFromWorkOrder/ManageWorkForceServiceBrokerImpl ==");
		}

	}
	
	public void editTelecomTechnician(EmployeeVO eVo)throws BusinessException{
		log.debug("== Inicia la operación editTelecomTechnician/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();

			EditTelecomTechnicianRequest request = manageWorkForceServiceTOA.buildEditTelecomTechnicianRequest(eVo);
			EditTelecomTechnicianResponse response = service.editTelecomTechnician(request);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación editTelecomTechnician/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación editTelecomTechnician/ManageWorkForceServiceBrokerImpl ==");
		}

	}
	
	public Long addTelecomTechnician(EmployeeVO eVo)throws BusinessException{
		Long returnValue = new Long(0);
		log.debug("== Inicia la operación addTelecomTechnician/ManageWorkForceServiceBrokerImpl ==");
		try {
			ManageWorkforcePt service = ServiceLocator.getInstance().getManageWorkForceService();

			AddTelecomTechnicianRequest request = manageWorkForceServiceTOA.buildAddTelecomTechnicianRequest(eVo);
			AddTelecomTechnicianResponse response = service.addTelecomTechnician(request);
			UtilsBusiness.assertNotNull(response, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(response.getAddTelecomTechnicianResult(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(response.getAddTelecomTechnicianResult().getTelecomTechnicianId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			returnValue = Long.valueOf(response.getAddTelecomTechnicianResult().getTelecomTechnicianId());
			return returnValue;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación addTelecomTechnician/ManageWorkForceServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación addTelecomTechnician/ManageWorkForceServiceBrokerImpl ==");
		}

	}
}
