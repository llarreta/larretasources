package co.com.directv.sdii.ejb.business.core.tray;

import java.util.List;

import javax.ejb.Local;

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
 *Interfaz que define las operaciones de negocio para 
 *realizar la atencion y/o finalizacion de la Workorder, 
 *actua como un componente tipo proxy para redireccionar
 *las invocaciones de las operaciones a componentes de la
 *aplicacion o externos.
 * 
 * Fecha de Creación: 17/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrayManagmentBusinessDelegateLocal {
	
	/**
	 * 
	 * Metodo:Valida los datos de la petición de atención de una nueva WorkOrder.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void validateDataBusinessWOAttentions(WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza la atención de un servicio de una WorkOrder. 
	 * @param request
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ServiceAttendResponseDTO atenderService(ServiceAttentionRequestDTO request) throws BusinessException;
	

	/**
	 * 
	 * Metodo: Realiza la invocacion al componente de negocio
	 * que valida la existencia de los elementos en la bodega deiponible
	 * de la cuadrilla, de las cuadrillas del dealer o en la del dealer.
	 * @param woServices List<WorkOrderServiceVO>
	 * @param dealerId Long 
	 * @param Long woId
	 * @throws BusinessException
	 * @author jalopez
	 */
	//public void validateExistenceElements(Long dealerId, List<WorkOrderServiceVO> woServices, Long woId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Reporta a SDII los elementos utilizados en la atención de los servicios de una WO.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void reportarElementosUtilizadosEnServicios(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException;
	
	/**
	 * 
	 * Metodo:  Reporta a SDII los elementos recuperados en la atención de los servicios de una WO.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void reportarElementosRecuperadosEnServicios(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException;
	
	/**
	 * 
	 * Metodo:Activa los elementos utilizados en la atención de los servicios en IBS.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo:  Informa los cambios de IRD a IBS.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void informarCambioDeIRDIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Reporta a IBS que se adicionaron servicios
	 * @param workorderServices WorkOrderServiceVO workorderService
	 * @param woId Long
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void addingServicesReport(WorkOrderServiceVO workorderService, Long woId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Actualiza cambio de estado de la WorkOrder en SDII.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void actualizarCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Validaciones para la finalización de la WO.
	 * Valida que la WO exista
	 * Valida que la WO se encuentre Atendida
	 * Valida que el cambio de estado sea permitido
	 * Validaciones Transversales a la Atencion y la Finalizacion
	 * Valida que la WoorOrder tenga una cuadrilla asociada
	 * @param request
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateDataBusinessWOFinalization(WOAttentionsRequestDTO request) throws BusinessException;	
	
	/**
	 * 
	 * Metodo: Registros de los elementos no serializdos
	 * empleados en la atencion de la WorkOrder.
	 * @param request
	 * @return ServiceAttendResponseDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public ServiceAttendResponseDTO serviceFinalization(ServiceAttentionRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza la invocacion del componente de negocio
	 * encargado de reportar los elementos no serializados
	 * empleados en la atencion de la WorkOrder.
	 * @param request WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void reportElementsUsedFinalization(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Reportar los elementos no serializados
	 * recuperados en la finalizacion de la WorkOrder.
	 * @param request WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	//public void reportedRecoveryNotSerializedFinalization(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza el cambio de estado de la WO a Finalizada
	 * @param request WOAttentionsRequestDTO
	 * @return WOAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public WOAttentionsRequestDTO stateChangeUpdateWOFinalization (WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza la validacion de los datos
	 * requeridos para realizar la finaliacion
	 * @param request TrayWOManagmentDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateDataWOFinalization(TrayWOManagmentDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza la validacion de los datos
	 * requeridos para realizar la atencion
	 * @param request
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateDataWOAttention(TrayWOManagmentDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un Elemento
	 * filtrando por el serial
	 * @param InventoryDTO inventoryDTO
	 * @return ElementDTO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ElementDTO getElementBySerialCode(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza la actualizacion del estado
	 * de la WO en IBS.
	 * @param request WOAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void notifyStatusChangeFinalizationIBS(WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Notificar el cambio de estado de la
	 * work order atendida
	 * @param request WOAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalpez
	 */
	public void notificarIbsCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Validacion Masiva de Workorders
	 * @param WOAttentionsRequestDTO woAttentionDTO
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public EnvelopeEncapsulateResponse massiveValidationWorkOrders (WOAttentionsRequestDTO woAttentionDTO) throws BusinessException;	
	
	/**
	 * 
	 * Metodo: Retorna los elementos requeridos
	 * por servicio
	 * @param InventoryDTO inventoryDTO
	 * @return RequiredServiceElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public RequiredServiceElementDTO getRequiredServiceElements(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Afecta el inventario con los
	 * elementos serializados empleados en la 
	 * Atencion de la WorkOrder, delegando la peticion
	 * el sistema encargado de realizar el proceso
	 * @param inventoryDTO
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public EnvelopeEncapsulateResponse updateRecordSerializedInventory(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Afecta el inventario con los
	 * elementos no serailizados empleados en la 
	 * Atencion de la WorkOrder, delegando la peticion
	 * el sistema encargado de realizar el proceso
	 * @param inventoryDTO
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public EnvelopeEncapsulateResponse updateRecordNotSerializedInventory(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: realiza el procso de finalizacion de
	 * la WorkOrder en IBS.
	 * @param WOAttentionsRequestDTO attentionRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void completeWorkOrderFinalization(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo:Activa los elementos utilizados en la atención de los servicios en IBS,
	 * para servicios Upgrade o Downgrade.
	 * de negocio.
	 * @param request
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void upgradeDowngradeResource(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Activa los elementos utilizados en la atencion llamando a servicio de Shipping Order
	 * @param pSubscriber IBS de cliente
	 * @param pWorkOrder codigo de WO
	 * @param pSerialNumerSC serial de SC
	 * @param pSerialNumerIRD serial de IRD
	 * @param pComment comentario de atencion
	 * @param pInstaller nombre completo de encargado de cuadrilla asignada a la WO
	 * @return String respuesta de servicio
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public String shipOrder(int pSubscriber, int pWorkOrder, String pSerialNumerSC, String pSerialNumerIRD, String pComment, String pInstaller) throws BusinessException;
}
