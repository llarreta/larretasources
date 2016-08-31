/**
 * Creado 3/12/2010 19:21:58
 */
package co.com.directv.sdii.ejb.business.core.tray.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal;
import co.com.directv.sdii.ejb.business.core.tray.ReportHelperBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayManagmentBusinessDelegateLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayManagmentBusinessHelperLocal;
import co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateDetailResponse;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.ServiceAttendResponseDTO;
import co.com.directv.sdii.model.dto.ServiceAttentionRequestDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * Implementa los métodos de negocio para el proceso de atenciones
 * de una WorkOrder, se encarga de gestionar los errores presentados
 * en el proceso de atencion y/o finalizacion.
 * 
 * Fecha de Creación: 3/12/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="TrayManagmentBusinessHelperLocal",mappedName="ejb/TrayManagmentBusinessHelperLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TrayManagmentBusinessHelper extends BusinessBase implements TrayManagmentBusinessHelperLocal {

	private final static Logger log = UtilsBusiness.getLog4J(TrayManagmentBusinessHelper.class);
	
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="CoreWOAttentionsBusinessLocal", beanInterface=CoreWOAttentionsBusinessLocal.class)
	private CoreWOAttentionsBusinessLocal coreWOAttentionsBusiness;
	
	@EJB(name="TrayManagmentBusinessDelegateLocal", beanInterface=TrayManagmentBusinessDelegateLocal.class)
	private TrayManagmentBusinessDelegateLocal trayBusinessDelegate;
	
	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDao;	
	@EJB
	private WorkOrderBusinessBeanLocal workOrderBusiness;	
	
	@EJB(name="CoreStockBusinessLocal", beanInterface=CoreStockBusinessLocal.class)
	private CoreStockBusinessLocal coreStockBusiness;
	
	@EJB(name="ReportHelperBusinessBeanLocal", beanInterface=ReportHelperBusinessBeanLocal.class)
	private ReportHelperBusinessBeanLocal reportHelperBusinessBeanLocal;
	
	@Override
	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request) throws BusinessException {
		log.debug("== Inicia activarElementosEnIBS/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_AEI.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage AEI ", e1);
			}
		try{
			trayBusinessDelegate.activarElementosEnIBS(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion activarElementosEnIBS/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina activarElementosEnIBS/TrayManagmentBusinessHelper ==");
		}			
	}

	@Override
	public void actualizarCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException {
		log.debug("== Inicia actualizarCambioDeEstadoWO/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_ACE.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage AEI ", e1);
			}
		try{
			trayBusinessDelegate.actualizarCambioDeEstadoWO(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion actualizarCambioDeEstadoWO/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina actualizarCambioDeEstadoWO/TrayManagmentBusinessHelper ==");
		}			
	}

	@Override
	public void addingServicesReport(WorkOrderServiceVO workorderService, Long woId)	throws BusinessException {
		log.debug("== Inicia addingServicesReport/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_ASR.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage ASR", e1);
			}
		try{
			trayBusinessDelegate.addingServicesReport(workorderService, woId);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion addingServicesReport/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(woId, WoAttentionStatusCode, e.getMessageCode(), e.getMessage());
			throw e;
		} finally {
			log.debug("== Termina addingServicesReport/TrayManagmentBusinessHelper ==");
		}					
	}

	@Override
	public ServiceAttendResponseDTO atenderService(ServiceAttentionRequestDTO request) throws BusinessException {
		log.debug("== Inicia atenderService/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_ATS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage ATS", e1);
			}
		try{
			ServiceAttendResponseDTO response = trayBusinessDelegate.atenderService(request);
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion atenderService/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina atenderService/TrayManagmentBusinessHelper ==");
		}		
	}

	@Override
	public void informarCambioDeIRDIBS(WOAttentionElementsRequestDTO request) throws BusinessException {
		log.debug("== Inicia informarCambioDeIRDIBS/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_ICE.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage ICE", e1);
			}
		try{
			trayBusinessDelegate.informarCambioDeIRDIBS(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion informarCambioDeIRDIBS/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina informarCambioDeIRDIBS/TrayManagmentBusinessHelper ==");
		}			
	}

	@Override
	public void reportElementsUsedFinalization(WOAttentionElementsRequestDTO request) throws BusinessException {
		log.debug("== Inicia reportElementsUsedFinalization/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_RNS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage RNS", e1);
			}
		try{
			trayBusinessDelegate.reportElementsUsedFinalization(request,WoAttentionStatusCode);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportElementsUsedFinalization/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina reportElementsUsedFinalization/TrayManagmentBusinessHelper ==");
		}			
	}

	@Override
	public void reportarElementosRecuperadosEnServicios(WOAttentionElementsRequestDTO request) throws BusinessException {
		log.debug("== Inicia reportarElementosRecuperadosEnServicios/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_RER.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage RER", e1);
			}
		try{
			trayBusinessDelegate.reportarElementosRecuperadosEnServicios(request,WoAttentionStatusCode);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportarElementosRecuperadosEnServicios/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina reportarElementosRecuperadosEnServicios/TrayManagmentBusinessHelper ==");
		}	
	}

	@Override
	public void reportarElementosUtilizadosEnServicios(WOAttentionElementsRequestDTO request) throws BusinessException {
		log.debug("== Inicia reportarElementosUtilizadosEnServicios/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_REU.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage REU", e1);
			}
		try{
			trayBusinessDelegate.reportarElementosUtilizadosEnServicios(request,WoAttentionStatusCode);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportarElementosUtilizadosEnServicios/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina reportarElementosUtilizadosEnServicios/TrayManagmentBusinessHelper ==");
		}	
	}

//	@Override
//	public void reportedRecoveryNotSerializedFinalization(WOAttentionElementsRequestDTO request) throws BusinessException {
//		log.debug("== Inicia reportedRecoveryNotSerializedFinalization/TrayManagmentBusinessHelper ==");
//		String WoAttentionStatusCode = null;
//		try {
//			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_RES.getCodeEntity();
//		} catch (PropertiesException e1) {
//			log.error("Al tratar de obtener el código del estado de la atención para el stage RES", e1);
//			}
//		try{
//			trayBusinessDelegate.reportedRecoveryNotSerializedFinalization(request);
//		} catch (Throwable ex) {
//			log.debug("== Error al tratar de ejecutar la operacion reportedRecoveryNotSerializedFinalization/TrayManagmentBusinessHelper");
//			BusinessException e = super.manageException(ex);
//			this.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage());
//			throw e;
//		} finally {
//			log.debug("== Termina reportedRecoveryNotSerializedFinalization/TrayManagmentBusinessHelper ==");
//		}	
//	}

	@Override
	public ServiceAttendResponseDTO serviceFinalization(ServiceAttentionRequestDTO request) throws BusinessException {
		log.debug("== Inicia serviceFinalization/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_FSW.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage FSW", e1);
			}
		try{
			ServiceAttendResponseDTO response = trayBusinessDelegate.serviceFinalization(request);
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion serviceFinalization/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina serviceFinalization/TrayManagmentBusinessHelper ==");
		}	
	}

	@Override
	public WOAttentionsRequestDTO stateChangeUpdateWOFinalization(WOAttentionsRequestDTO request)	throws BusinessException {
		log.debug("== Inicia stateChangeUpdateWOFinalization/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_IFS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage IFS", e1);
			}
		try{
			return trayBusinessDelegate.stateChangeUpdateWOFinalization(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion stateChangeUpdateWOFinalization/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina stateChangeUpdateWOFinalization/TrayManagmentBusinessHelper ==");
		}
	}

	@Override
	public void validateDataBusinessWOAttentions(WOAttentionsRequestDTO request) throws BusinessException {
		log.debug("== Inicia validateDataBusinessWOAttentions/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_VAL.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage VAL", e1);
			}
		try{
			trayBusinessDelegate.validateDataBusinessWOAttentions(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataBusinessWOAttentions/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina validateDataBusinessWOAttentions/TrayManagmentBusinessHelper ==");
		}
	}

	@Override
	public void validateDataBusinessWOFinalization( WOAttentionsRequestDTO request) throws BusinessException {
		log.debug("== Inicia validateDataBusinessWOFinalization/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_AFS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage AFS", e1);
			}
		try{
			trayBusinessDelegate.validateDataBusinessWOFinalization(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataBusinessWOFinalization/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina validateDataBusinessWOFinalization/TrayManagmentBusinessHelper ==");
		}
	}

//	@Override
//	public void validateExistenceElements(Long dealerId, List<WorkOrderServiceVO> woServices, Long woId) throws BusinessException {
//		log.debug("== Inicia validateExistenceElements/TrayManagmentBusinessHelper ==");
//		String WoAttentionStatusCode = null;
//		try {
//			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_VEE.getCodeEntity();
//		} catch (PropertiesException e1) {
//			log.error("Al tratar de obtener el código del estado de la atención para el stage VEE", e1);
//			}
//		try{
//			trayBusinessDelegate.validateExistenceElements(dealerId, woServices, woId);
//		} catch (Throwable ex) {
//			log.debug("== Error al tratar de ejecutar la operacion validateExistenceElements/TrayManagmentBusinessHelper");
//			BusinessException e = super.manageException(ex);
//			this.reportWoAttentionStatus(woId, WoAttentionStatusCode, e.getMessageCode(), e.getMessage());
//			throw e;
//		} finally {
//			log.debug("== Termina validateExistenceElements/TrayManagmentBusinessHelper ==");
//		}
//	}
	
	@Override
	public void validateDataWOFinalization(TrayWOManagmentDTO request) throws BusinessException {
		log.debug("== Inicia validateDataWOFinalization/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_AFS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage AFS", e1);
			}
		try{
			trayBusinessDelegate.validateDataWOFinalization(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataWOFinalization/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkOrderId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina validateDataWOFinalization/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public void validateDataWOAttention(TrayWOManagmentDTO request) throws BusinessException{
		log.debug("== Inicia validateDataWOFinalization/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_VAL.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage VAL", e1);
			}
		try{
			trayBusinessDelegate.validateDataWOAttention(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataWOFinalization/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkOrderId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina validateDataWOFinalization/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public ElementDTO getElementBySerialCode(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia getElementBySerialCode/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_VEE.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage VEE", e1);
			}
		try{
			ElementDTO element = new ElementDTO();
			//Se realiza la validacion para invocar los servicios de inventarios de HSP, de
			//lo contrario se invocan los de FieldServices (SDI)			
			if ( inventoryDTO.getInvokeInternalInventory().equals( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) ) {
				element = coreStockBusiness.getSerializedResource( inventoryDTO );
			}else { 
				element = trayBusinessDelegate.getElementBySerialCode(inventoryDTO);
			}	
			
			return element;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion getElementBySerialCode/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(inventoryDTO.getWorkOrderVO().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), inventoryDTO.getUserId());
			throw e;
		} finally {
			log.debug("== Termina getElementBySerialCode/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public void notifyStatusChangeFinalizationIBS(WOAttentionsRequestDTO request) throws BusinessException {
		log.debug("== Inicia notifyStatusChangeFinalizationIBS/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_NWS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage NWS", e1);
		}
		try{
			trayBusinessDelegate.notifyStatusChangeFinalizationIBS(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion notifyStatusChangeFinalizationIBS/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina notifyStatusChangeFinalizationIBS/TrayManagmentBusinessHelper ==");
		}
	}	
	
	@Override
	public void notificarIbsCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException {
		log.debug("== Inicia notificarIbsCambioDeEstadoWO/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_NWS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage NWS", e1);
		}
		try{
			trayBusinessDelegate.notificarIbsCambioDeEstadoWO(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion notificarIbsCambioDeEstadoWO/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina notificarIbsCambioDeEstadoWO/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public EnvelopeEncapsulateResponse massiveValidationWorkOrders(WOAttentionsRequestDTO woAttentionDTO) throws BusinessException {
		log.debug("== Inicia massiveValidationWorkOrders/TrayManagmentBusinessHelper ==");				
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_AFS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage NWS", e1);
		}
		try{
			EnvelopeEncapsulateResponse response = trayBusinessDelegate.massiveValidationWorkOrders(woAttentionDTO);
			//Si una de la WO no cumple con las validaciones necesarias se procesan los errores
			if( !response.getExceptions().isEmpty() ){				
				for (EnvelopeEncapsulateDetailResponse detail : response.getExceptions()) {	
					WorkOrder workOrder = workOrderDao.getWorkOrderByCode(response.getWoCode());
					reportHelperBusinessBeanLocal.reportWoAttentionStatus(workOrder.getId(), WoAttentionStatusCode, detail.getExceptionCode(), detail.getExceptionMessage(), woAttentionDTO.getUserId());					
				}
				//Se lanza la exception para detener el proceso
				Object[] params = null;
				params = new Object[1];	
				params[0] = response.getWoCode();	
				List<String> listParams = new ArrayList<String>();
				listParams.add( response.getWoCode() );
				throw new BusinessException(ErrorBusinessMessages.CORE_CR047.getCode(),ErrorBusinessMessages.CORE_CR047.getMessage(params),listParams);
			}
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion massiveValidationWorkOrders/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			throw e;
		} finally {
			log.debug("== Termina massiveValidationWorkOrders/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public RequiredServiceElementDTO getRequiredServiceElements(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia getRequiredServiceElements/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_ERS.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage NWS", e1);
		}
		try{
			RequiredServiceElementDTO serviceElements = new RequiredServiceElementDTO();
			//Se realiza la validacion para invocar los servicios de inventarios de HSP, de
			//lo contrario se invocan los de FieldServices (SDI)
			if ( inventoryDTO.getInvokeInternalInventory().equals( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) ) {
				serviceElements =  coreStockBusiness.getResourcesByServiceType(inventoryDTO);
			}else { 
				serviceElements = trayBusinessDelegate.getRequiredServiceElements(inventoryDTO);
			}			
			
			return serviceElements;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion getRequiredServiceElements/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(inventoryDTO.getWorkOrderVO().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), inventoryDTO.getUserId());
			throw e;
		} finally {
			log.debug("== Termina getRequiredServiceElements/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public EnvelopeEncapsulateResponse updateRecordNotSerializedInventory(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia updateRecordNotSerializedInventory/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_ENI.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage NWS", e1);
		}
		try{
			EnvelopeEncapsulateResponse envelope = new EnvelopeEncapsulateResponse();
			//Se realiza la validacion para invocar los servicios de inventarios de HSP, de
			//lo contrario se invocan los de FieldServices (SDI)
			if ( inventoryDTO.getInvokeInternalInventory().equals( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) ) {
				envelope = coreStockBusiness.registerNotSerializedResources(inventoryDTO);
			} else {
				envelope = trayBusinessDelegate.updateRecordNotSerializedInventory(inventoryDTO);
			}
			
			return envelope;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion updateRecordNotSerializedInventory/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(inventoryDTO.getWoAttentionDTO().getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), inventoryDTO.getUserId());
			throw e;
		} finally {
			log.debug("== Termina updateRecordNotSerializedInventory/TrayManagmentBusinessHelper ==");
		}
	}

	@Override
	public EnvelopeEncapsulateResponse updateRecordSerializedInventory(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia updateRecordNotSerializedInventory/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_ESI.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage NWS", e1);
		}
		try{
			EnvelopeEncapsulateResponse envelope = new EnvelopeEncapsulateResponse();
			//Se realiza la validacion para invocar los servicios de inventarios de HSP, de
			//lo contrario se invocan los de FieldServices (SDI)
			if ( inventoryDTO.getInvokeInternalInventory().equals( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) ) {
				envelope = coreStockBusiness.registerSerializedResources(inventoryDTO);
			} else {
				//XXX: Esto es valido solo en algunos paises
				envelope = trayBusinessDelegate.updateRecordSerializedInventory(inventoryDTO);
			}
			
			return envelope;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion updateRecordNotSerializedInventory/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(inventoryDTO.getWorkOrderVO().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), inventoryDTO.getUserId());
			throw e;
		} finally {
			log.debug("== Termina updateRecordNotSerializedInventory/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public EnvelopeEncapsulateResponse completeWorkOrderFinalization(InventoryDTO inventoryDTO) throws BusinessException {
		return null;
	}
	
	@Override
	public void completeWorkOrderFinalization(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException {
		log.debug("== Inicia completeWorkOrderFinalization/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_CFW.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage NWS", e1);
		}
		try{
			trayBusinessDelegate.completeWorkOrderFinalization(attentionRequestDTO);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion completeWorkOrderFinalization/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(attentionRequestDTO.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), attentionRequestDTO.getUserId());
			throw e;
		} finally {
			log.debug("== Termina completeWorkOrderFinalization/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	public void upgradeDowngradeResource(WOAttentionElementsRequestDTO request)	throws BusinessException {
		log.debug("== Inicia UpgradeDowngradeResource/TrayManagmentBusinessHelper ==");
		String WoAttentionStatusCode = null;
		try {
			WoAttentionStatusCode = CodesBusinessEntityEnum.WO_ATTENTION_UDI.getCodeEntity();
		} catch (PropertiesException e1) {
			log.error("Al tratar de obtener el código del estado de la atención para el stage UDI ", e1);
			}
		try{
			trayBusinessDelegate.upgradeDowngradeResource(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion UpgradeDowngradeResource/TrayManagmentBusinessHelper");
			BusinessException e = super.manageException(ex);
			reportHelperBusinessBeanLocal.reportWoAttentionStatus(request.getWorkorderVo().getId(), WoAttentionStatusCode, e.getMessageCode(), e.getMessage(), request.getUserId());
			throw e;
		} finally {
			log.debug("== Termina UpgradeDowngradeResource/TrayManagmentBusinessHelper ==");
		}	
	}	
	
}
