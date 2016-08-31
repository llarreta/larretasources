
package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.CSRBusinessLocal;
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.ConfigTiposServicioBusinessLocal;
import co.com.directv.sdii.ejb.business.core.ContactCSRBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.model.pojo.AuditExternalSystemSchedule;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.WorkorderCSRStatus;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.BuildingVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.persistence.dao.config.AuditExternalSystemScheduleDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO;

@Stateless(name="CSRBusinessLocal",mappedName="ejb/CSRBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CSRBusiness extends BusinessBase implements CSRBusinessLocal {
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	@EJB(name = "PostalCodesDAOLocal", beanInterface = PostalCodesDAOLocal.class)
	private PostalCodesDAOLocal postalCodesDAO;
	
    @EJB(name="BuildingBusinessBeanLocal", beanInterface=BuildingBusinessBeanLocal.class)
    private BuildingBusinessBeanLocal businessBuilding;
    
	@EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
	
	@EJB(name="SystemParameterDAOLocal",beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;	
	
	@EJB(name="ConfigTiposServicioBusinessLocal",beanInterface=ConfigTiposServicioBusinessLocal.class)
	private ConfigTiposServicioBusinessLocal configServiceType;
	
	@EJB(name="WorkOrderCSRDAOLocal", beanInterface=WorkOrderCSRDAOLocal.class)
	private WorkOrderCSRDAOLocal workOrderCSRDAO;
	
	@EJB(name="ServiceHourDAOLocal", beanInterface=ServiceHourDAOLocal.class)
	private ServiceHourDAOLocal serviceHourDAO;
	
	@EJB(name="CoreWOBusinessLocal", beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal coreWOBusiness;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;
	
	@EJB(name="WorkorderReasonDAOLocal", beanInterface=WorkorderReasonDAOLocal.class)
	private WorkorderReasonDAOLocal woReasonDAO;
	
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;

	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDAO;
	
	@EJB(name="ManageWorkForceServiceBrokerLocal",beanInterface=ManageWorkForceServiceBrokerLocal.class)
	private ManageWorkForceServiceBrokerLocal manageWorkForceServiceBroker;
	
	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAOBean;
	
	@EJB(name="ContactCSRBusinessBeanLocal", beanInterface=ContactCSRBusinessBeanLocal.class)
	private ContactCSRBusinessBeanLocal contactCSRBusinessBean;
	
	@EJB(name="AuditExternalSystemScheduleDAOLocal", beanInterface=AuditExternalSystemScheduleDAOLocal.class)
	private AuditExternalSystemScheduleDAOLocal auditExternalSystemScheduleDAOBean;
	
	private final static Logger log = UtilsBusiness.getLog4J(CSRBusiness.class);
	
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#agendaWorkOrdersCSR(co.com.directv.sdii.model.dto.WorkOrderCSRDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void agendaWorkOrdersCSR(WorkOrderCSRDTO request) throws BusinessException {

		UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWoCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getServiceHourId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getScheduleDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getContactPerson(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getCommentManagment(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		/*
		 *Para agendar una work order:
		 *0. Validar que la fecha de agendamiento no puede ser menor a la fecha actual
		 *1. Validar que la workOrder exista en la tabla WORK_ORDERS
		 *2. Validar que exista un registro relacionado en WO_ASSIGMENTS
		 *3. Se valida si la WO ya tiene agenda, para determinar si es un reagendamiento y se asigna la reason.
		 *4. Usando la máquina de estados validar que se pueda modificar el estado de la workorder a AGENDADA
		 *5. Generar registro en la tabla WORK_ORDER_AGENDA
		 *6. Afectar la capacidad del dealer en DealerServiceCapacity invocando el caso de uso ADS-34
		 *7. Actualizar la fecha de programación de la workOrder por la de agendamiento
		 *8. Cambiar el estado de la workorder
		 *9. Notificar el cambio de estado de la workOrder a IBS usando el CU-ADS 33
		 */

		log.debug("== Inicia agendaWorkOrder/CSRBusiness ==");
		try {

			//Se consulta la jornada
			//Se valida que la jornada sea enviada
			Object[] params = null;
			params = new Object[2];	
			params[1] = "agenda - reagendamiento";
			params[0] = "jornada";
			UtilsBusiness.validateRequestResponseWebService(params, request.getServiceHourId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
				
			ServiceHour  serviceHourComplete = serviceHourDAO.getServiceHourByID(request.getServiceHourId());
			UtilsBusiness.validateRequestResponseWebService(params, serviceHourComplete, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			request.setScheduleDate(UtilsBusiness.addHoursToDate(serviceHourComplete.getEndTime(), request.getScheduleDate()));
			
			
			//Se verifica si ya paso la jornada del dia actual y que la fecha no sea antes de la actual
			coreWOBusiness.checkServiceHourForWoDownload(request.getUserId(),
								                         request.getCountryId(),
					                                     request.getScheduleDate(),
					                                     serviceHourComplete);
			
			WorkorderReason workorderReason=null;
			WorkorderStatus woStatus = null;
			if ( request.isReSchedule() ) {				
				woStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
				workorderReason = woReasonDAO.getWorkorderReasonByID( request.getWoReasonId());
				
			}else {				
				woStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
				
				//Se consulta la wo reason para el caso de agendamiento.
				String workOrderReasonAssignedCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity(), request.getCountryId(), systemParameterDao);
				//String workOrderReasonAssignedCode = CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity();
				workorderReason = woReasonDAO.getWorkorderReasonByCode(workOrderReasonAssignedCode);
				if(workorderReason == null){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
				}
			}
			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
			
			Country country=countryDao.getCountriesByID(request.getCountryId());
			String countryCode=null;
			if(country!=null)
				countryCode=country.getCountryCode();
			
			Long dealerCodeIbs=null;
			if(request.getDealerId()!=null){
				Dealer dealer=dealersDAO.getDealerByID(request.getDealerId());
				if(dealer!=null)
					dealerCodeIbs=dealer.getDealerCode();
			}
			
			WorkorderStatus workorderStatus = workorderStatusDAO.getWorkorderStatusByID(request.getActualStatusId());
			String ibs6StateCode="";
			if(workorderStatus!=null)
				ibs6StateCode=workorderStatus.getIbs6Status().getIbs6StateCode();
			
			//Ejecutando 8:			
			changeWorkOrderStatus(countryCode,
					              dealerCodeIbs,
							      workorderReason,
							      ibs6StateCode,
							      woStatus,
							      request);
			
			WorkOrderCSR workOrderCSR=UtilsBusiness.buildWorkOrderCSR(request);
			updateWorkOrderCSRToSchedule(workOrderCSR);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CSRBusiness/agendaWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina agendaWorkOrder/CSRBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrderCSRByWoCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderCSR getWorkOrderCSRByWoCode(String woCode) throws BusinessException {
		log.debug("== Inicia getWorkOrderCSRByWoCode/CSRBusiness ==");
		WorkOrderCSR workOrderCSR=null;
		try{
			workOrderCSR = workOrderCSRDAO.getWorkOrderCSRByWoCodePending(woCode);
			return workOrderCSR;
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrderCSRByWoCode/CSRBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderCSRByWoCode/CSRBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#createWorkOrderCSR(co.com.directv.sdii.model.pojo.WorkOrderCSR)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWorkOrderCSR(WorkOrderCSR workOrderCSR) throws BusinessException {
		log.debug("== Inicia createWorkOrderCSR/CSRBusiness ==");
		try{
			workOrderCSRDAO.createWorkOrderCSR(workOrderCSR);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación createWorkOrderCSR/CSRBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createWorkOrderCSR/CSRBusiness ==");
		}
	}
	
	
	/**
	 * Metodo: Permite actualizar un WorkOrderCSr dependiendo si tiene dealer
	 * @param workOrderCSR
	 * @throws BusinessException
	 * @throws HelperException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	private void updateWorkOrderCSRToSchedule(WorkOrderCSR workOrderCSR) throws BusinessException, HelperException, PropertiesException {
		
		WorkorderCSRStatus workOrderCSRStatus;
		
		if(workOrderCSR.getReSchedule().equals(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()))
			workOrderCSRStatus = UtilsBusiness.getWorkorderCSRStatusAgen();
		else
			workOrderCSRStatus = UtilsBusiness.getWorkorderCSRStatusTerm();
		
		workOrderCSR.setWorkOrderCSRStatus(workOrderCSRStatus);
		updateWorkOrderCSR(workOrderCSR);
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#updateWorkOrderCSR(co.com.directv.sdii.model.pojo.WorkOrderCSR)
	 */
	public void updateWorkOrderCSR(WorkOrderCSR workOrderCSR) throws BusinessException {
		log.debug("== Inicia updateWorkOrderCSR/CSRBusiness ==");
		try{
			workOrderCSRDAO.updateWorkOrderCSR(workOrderCSR);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación updateWorkOrderCSR/CSRBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina updateWorkOrderCSR/CSRBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#traceAssignmentWorkOrderCSR(java.lang.Long, java.lang.String)
	 */
	public void traceAssignmentWorkOrderCSR(Long id,String trace) throws BusinessException {
		log.debug("== Inicia traceAssignmentWorkOrderCSR/CSRBusiness ==");
		try{
			if(trace != null && trace.length() > 0){
				if(trace.length() > 3900){
					trace = trace.substring(0, 3899);
				}
			}
			workOrderCSRDAO.traceAssignmentWorkOrderCSR(id,trace);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación traceAssignmentWorkOrderCSR/CSRBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina traceAssignmentWorkOrderCSR/CSRBusiness ==");
		}
	}
	
	/**
	 * Metodo: Cambia el estado de una orden de trabajo, realizando los procesos necesarios:<br>
	 * 1. pasar el estado actual a estado anterior<br>
	 * 2. Asignar a estado actual el nuevo estado que se debe especificar usando las constantes de <code>CodesBusinessEntityEnum.WORKORDER_STATUS_</code><br>
	 * 3. Generar un registro en el histórico de estados de la workOrder<br>
	 * @param countryCode
	 * @param dealerCodeIbs
	 * @param woReason
	 * @param ibs6StateCode
	 * @param woStatus
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh 2010-04-20
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void changeWorkOrderStatus(String countryCode,
									   Long dealerCodeIbs,
									   WorkorderReason woReason,
									   String ibs6StateCode,
									   WorkorderStatus woStatus,
									   WorkOrderCSRDTO request) throws BusinessException{
		
		log.debug("== Inicia changeWorkOrderStatus/CSRBusiness ==");
		try{
			
			//Actualizacion del estado de la Base de Datos.
			WoStatusHistory woStatusHistory = null;
//			String ibsHistoryEventCode = this.coreWOServiceBroker.updateWorkOrderStatus(countryCode,
//																						request.getWoCode(),
//																						request.getScheduleDate(),
//																			            dealerCodeIbs,
//																			            woReason.getWorkorderReasonCode(),
//																			            ibs6StateCode);
			EditCustomerWorkOrderDTO editCustomerWorkOrderDTO = new EditCustomerWorkOrderDTO(request.getWoCode(),
																							 countryCode,
																							 dealerCodeIbs,
																		                     null,
																		                     woReason.getWorkorderReasonCode(),
																		                     request.getScheduleDate());
			String ibsHistoryEventCode = manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderDTO);
			
			WorkOrder workOrderPojo = workOrderDAOBean.getWorkOrderByCode(request.getWoCode());
			
			//Creacion del historico del cambio de estado de la WO
			if(workOrderPojo!=null){
				
//				workOrderPojo.setWorkorderStatusByPreviusStatusId(workOrderPojo.getWorkorderStatusByActualStatusId());
//				workOrderPojo.setWoProgrammingDate(request.getScheduleDate());
//				workOrderPojo.setWorkorderStatusByActualStatusId(woStatus);
//				workOrderDAOBean.updateWorkOrder(workOrderPojo);
				
				if(workOrderPojo!=null){
					woStatusHistory = coreWOBusiness.createWoStatusHistoryHSP(workOrderPojo, woReason, request.getCommentManagment(), woStatus,null, request.getUserId()); 
				}
				
			}
			
			//CC Creacion de Contacts jalopez			
			contactCSRBusinessBean.createWoContact(woStatusHistory, 
											        woReason,
											        countryCode,
											        woStatus,
											        request,
											        workOrderPojo);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CSRBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CSRBusiness ==");
		}
		
	}
	
	/**
	 * Metodo: Obtiene la workOrderCsr a partir de un WorkOrderCSRDTO, si no existe la crea y si existe la actualiza
	 * @param workOrderCSRDTO
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void getWorkOrderCSRDTOByWoCode(WorkOrderCSRDTO workOrderCSRDTO) throws BusinessException, PropertiesException{
		
		log.debug("== Inicia la operaci�n getWorkOrderCSRDTOByWoCode/CSRBusiness ==");
		try{
			WorkOrderCSR workOrderCSRId = getWorkOrderCSRByWoCode(workOrderCSRDTO.getWoCode());
			
			WorkorderCSRStatus workOrderCSRStatus = UtilsBusiness.getWorkorderCSRStatusPend();
			
			WorkOrderCSR workOrderCSR = UtilsBusiness.buildWorkOrderCSR(workOrderCSRDTO);
			workOrderCSR.setWorkOrderCSRStatus(workOrderCSRStatus); 
			
			if(workOrderCSRId==null){
				createWorkOrderCSR(workOrderCSR);
			}else{
				workOrderCSR.setId(workOrderCSRId.getId());
				updateWorkOrderCSR(workOrderCSR);
			}
			
			workOrderCSRDTO.setId(workOrderCSR.getId());
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrderCSRDTOByWoCode/CSRBusiness ==", e);
			throw new BusinessException(e.getMessage());
		} finally{
			log.debug("== Termina getWorkOrderCSRDTOByWoCode/CSRBusiness ==");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal#getDealerFromLastWoFromCustomer(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void sendMailCsr(AssignRequestDTO assignRequestDTO, Long countryId){
        
		log.debug("== Inicia la operaci�n sendMailCSR/CSRBusiness ==");
		try{
			boolean sendMail = false;
			BuildingVO buildingVO = null;
			Long dealerId = null;
			PostalCode postalCode = null;
			
			StringBuffer sMail = new StringBuffer(ApplicationTextEnum.ERROR_REPORT.getApplicationTextValue()+" ");
			SystemParameter sysParam = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL.getCodeEntity(), countryId);
			String recipientMail = sysParam.getValue();
			
			//si trae dealer se consulta si existe en HSP
			if(assignRequestDTO.getDealerIdIBS()!=null && !assignRequestDTO.getDealerIdIBS().equals("")){
					
				dealerId = dealersDAO.getDealerIdByDealerCode(assignRequestDTO.getDealerIdIBS());
				if(dealerId == null || dealerId.longValue() == 0L){
					sendMail = true;
					sMail.append("\n");
					sMail.append(ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());
				}
			}
			
			//si trae postalCode se consulta si existe en HSP
			if(assignRequestDTO.getPostalCode() != null && !assignRequestDTO.getPostalCode().equals("")){
				
				postalCode = postalCodesDAO.getPostalCodesByCode(assignRequestDTO.getPostalCode());
				if(postalCode == null || postalCode.getPostalCodeCode() == null){
					sendMail = true;
					sMail.append("\n");
					sMail.append(ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST.getMessage());
				}		
			
			}
			
			//si trae postalCode se consulta si existe en HSP
			if(assignRequestDTO.getIbsBuildingCode() != null && !assignRequestDTO.getIbsBuildingCode().equals("") && !assignRequestDTO.getIbsBuildingCode().equals("0")){
				buildingVO = businessBuilding.getBuildingByCode(Long.parseLong(assignRequestDTO.getIbsBuildingCode()));
				if(buildingVO == null || buildingVO.getId() == 0){
					sendMail = true;
					sMail.append("\n");
					sMail.append(ErrorBusinessMessages.BUILDING_DOES_NOT_EXIST.getMessage());
				}
			}
			
			//si trae Service se consulta si existe en HSP
			for(String service : assignRequestDTO.getServices()){
				ServiceVO serviceVO = configServiceType.getServiceByCode(service);
	
					if(serviceVO == null){
						sendMail = true;
						sMail.append("\n");
						sMail.append(ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage() + " " + service);
					}
			}
			if(sendMail){
				String document = "CSR - " + assignRequestDTO.getWoCode();
				SendEmailDTO emailDto = createEmail(recipientMail, sMail.toString(), countryId, document);
				businessEmailType.sendEmailByEmailTypeCodeAsic( emailDto, countryId);			
				log.debug("Correo enviado con el informe de errores CSR");
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion sendMailCSR/CSRBusiness ==");
		}finally{
			log.debug("== Termina la operacion sendMailCSR/CSRBusiness ==");
		}
    	
    }
	
	private SendEmailDTO createEmail(String recipientMail, String mailContent,
			Long id,String document) throws BusinessException {
		
		SendEmailDTO emailDto= new SendEmailDTO();
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipientMail);
		emailDto.setRecipient(recipients);
		emailDto.setNewsType(EmailTypesEnum.WO_LOAD_REPORT.getEmailTypecode());
		emailDto.setNewsObservation(mailContent);
		emailDto.setNewsDocument(document);
		emailDto.setNewsSourceUser(ApplicationTextEnum.SHIPPING_ERRORS_PROCESS.getApplicationTextValue());
		return emailDto;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createAuditExternalSystemSchedule(AuditExternalSystemSchedule auditExternalSystemSchedule) throws BusinessException {
		log.debug("== Inicia createAuditExternalSystemSchedule/CSRBusiness ==");
		try{
			auditExternalSystemScheduleDAOBean.createAuditExternalSystemSchedule(auditExternalSystemSchedule);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación createAuditExternalSystemSchedule/CSRBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createAuditExternalSystemSchedule/CSRBusiness ==");
		}
	}
	
}
