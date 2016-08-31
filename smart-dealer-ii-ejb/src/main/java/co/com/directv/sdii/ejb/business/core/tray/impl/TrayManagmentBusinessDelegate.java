package co.com.directv.sdii.ejb.business.core.tray.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.impl.ExternalInventoryServiceBrokerImpl;
import co.com.directv.sdii.ejb.business.broker.impl.ShippingOrderServiceBrokerImpl;
import co.com.directv.sdii.ejb.business.broker.impl.SupportAndReadinessBrokerImpl;
import co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOInventoryBusinessLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayManagmentBusinessDelegateLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.ServiceAttendResponseDTO;
import co.com.directv.sdii.model.dto.ServiceAttentionRequestDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;

/**
 * 
 * Implementa las operaciones de negocio para 
 * realizar la atencion y/o finalizacion de la Workorder, 
 * actua como un componente tipo proxy para redireccionar
 * las invocaciones de las operaciones a componentes de la
 * aplicacion o externos.
 * 
 * Fecha de Creación: 17/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="TrayManagmentBusinessDelegateLocal",mappedName="ejb/TrayManagmentBusinessDelegateLocal")
@Local({TrayManagmentBusinessDelegateLocal.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TrayManagmentBusinessDelegate implements TrayManagmentBusinessDelegateLocal {

	@EJB(name="CoreWOAttentionsBusinessLocal",beanInterface=CoreWOAttentionsBusinessLocal.class)
	private CoreWOAttentionsBusinessLocal attentionBusiness;
	
//	@EJB(name="SerializedBusinessBeanLocal",beanInterface=SerializedBusinessBeanLocal.class)
//	private SerializedBusinessBeanLocal elementBusiness;
	
	@EJB(name="CoreWOInventoryBusinessLocal",beanInterface=CoreWOInventoryBusinessLocal.class)
	private CoreWOInventoryBusinessLocal inventoryBusiness;
	
	@EJB(name="ManageWorkForceServiceBrokerLocal",beanInterface=ManageWorkForceServiceBrokerLocal.class)
	private ManageWorkForceServiceBrokerLocal workForceBroker;
		
	private ExternalInventoryServiceBrokerImpl brokerInventoryInterface;
	
	private SupportAndReadinessBrokerImpl brokerSupportAndReadiness;
	
	private ShippingOrderServiceBrokerImpl brokerShippingOrderInterface;
	
	@Override
	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request) throws BusinessException {
		attentionBusiness.activarElementosEnIBS(request);
		
	}

	@Override
	public void actualizarCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException {
		attentionBusiness.actualizarCambioDeEstadoWO(request);
	}

	@Override
	public void addingServicesReport(WorkOrderServiceVO workorderService, Long woId) throws BusinessException {
		attentionBusiness.addingServicesReport(workorderService, woId);
	}

	@Override
	public ServiceAttendResponseDTO atenderService(ServiceAttentionRequestDTO request) throws BusinessException {
		return attentionBusiness.atenderService(request);
	}

	@Override
	public void informarCambioDeIRDIBS(WOAttentionElementsRequestDTO request) throws BusinessException {
		attentionBusiness.informarCambioDeIRDIBS(request);
	}

	@Override
	public void reportarElementosRecuperadosEnServicios(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException {
		attentionBusiness.reportarElementosRecuperadosEnServicios(request,codeTypeMovement);
	}

	@Override
	public void reportarElementosUtilizadosEnServicios(	WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException {
		attentionBusiness.reportarElementosUtilizadosEnServicios(request,codeTypeMovement);
	}

	@Override
	public void validateDataBusinessWOAttentions(WOAttentionsRequestDTO request) throws BusinessException {
		attentionBusiness.validateDataBusinessWOAttentions(request);
	}

//	@Override
//	public void validateExistenceElements(Long dealerId, List<WorkOrderServiceVO> woServices, Long woId) throws BusinessException {
//		attentionBusiness.validateExistenceElements(dealerId, woServices);
//	}

	@Override
	public void reportElementsUsedFinalization(	WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException {
		attentionBusiness.reportElementsUsedFinalization(request,codeTypeMovement);
	}

//	@Override
//	public void reportedRecoveryNotSerializedFinalization(WOAttentionElementsRequestDTO request) throws BusinessException {
//		attentionBusiness.reportedRecoveryNotSerializedFinalization(request);
//	}

	@Override
	public ServiceAttendResponseDTO serviceFinalization(ServiceAttentionRequestDTO request) throws BusinessException {
		return attentionBusiness.serviceFinalization(request);
	}

	@Override
	public WOAttentionsRequestDTO stateChangeUpdateWOFinalization(WOAttentionsRequestDTO request)	throws BusinessException {
		return attentionBusiness.stateChangeUpdateWOFinalization(request);		
	}

	@Override
	public void validateDataBusinessWOFinalization(WOAttentionsRequestDTO request) throws BusinessException {
		attentionBusiness.validateDataBusinessWOFinalization(request);
	}
	
	@Override
	public void validateDataWOFinalization(TrayWOManagmentDTO request) throws BusinessException {
		attentionBusiness.validateDataWOFinalization(request);
	}

	@Override
	public void validateDataWOAttention(TrayWOManagmentDTO request)	throws BusinessException {
		attentionBusiness.validateDataWOAttention(request);
	}

	@Override
	public ElementDTO getElementBySerialCode(InventoryDTO inventoryDTO) throws BusinessException {					
		//Llamado a operacion de negocio para consumo de inventarios externo.
		return inventoryBusiness.getElementBySerialCodeExternal(inventoryDTO);
	}

	@Override
	public void notifyStatusChangeFinalizationIBS(WOAttentionsRequestDTO request) throws BusinessException {
		attentionBusiness.notifyStatusChangeFinalizationIBS(request);
	}

	@Override
	public void notificarIbsCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException {
		attentionBusiness.notificarIbsCambioDeEstadoWO(request);
	}

	@Override
	public EnvelopeEncapsulateResponse massiveValidationWorkOrders(WOAttentionsRequestDTO woAttentionDTO) throws BusinessException {
		return attentionBusiness.massiveValidationWorkOrders(woAttentionDTO);
	}

	@Override
	public RequiredServiceElementDTO getRequiredServiceElements(InventoryDTO inventoryDTO) throws BusinessException {
		//return attentionBusiness.getRequiredServiceElements(inventoryDTO);
		brokerInventoryInterface = new ExternalInventoryServiceBrokerImpl();
		return brokerInventoryInterface.getResourcesByServiceType(inventoryDTO);		
	}

	@Override
	public EnvelopeEncapsulateResponse updateRecordNotSerializedInventory(InventoryDTO inventoryDTO) throws BusinessException {
		brokerInventoryInterface = new ExternalInventoryServiceBrokerImpl();
		return brokerInventoryInterface.registerNotSerializedResources(inventoryDTO);
	}

	@Override
	public EnvelopeEncapsulateResponse updateRecordSerializedInventory(InventoryDTO inventoryDTO) throws BusinessException {
		brokerInventoryInterface = new ExternalInventoryServiceBrokerImpl();
		return brokerInventoryInterface.registerSerializedResources(inventoryDTO);
	}

	@Override
	public void completeWorkOrderFinalization(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException {
		workForceBroker.completeWorkOrder(attentionRequestDTO);
	}

	@Override
	public void upgradeDowngradeResource(WOAttentionElementsRequestDTO request) throws BusinessException {
		brokerSupportAndReadiness = new SupportAndReadinessBrokerImpl();
		brokerSupportAndReadiness.upgradeDowngradeCustomerResource(request);
	}
	
	@Override
	public String shipOrder(int pSubscriber, int pWorkOrder,String pSerialNumerSC, String pSerialNumerIRD, String pComment,String pInstaller) throws BusinessException {
		brokerShippingOrderInterface = new ShippingOrderServiceBrokerImpl();
		return brokerShippingOrderInterface.shipOrder(pSubscriber, pWorkOrder, pSerialNumerSC, pSerialNumerIRD, pComment, pInstaller);
	}
}
