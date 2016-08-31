package co.com.directv.sdii.ejb.business.core;

import javax.ejb.Local;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.ServiceAttendResponseDTO;
import co.com.directv.sdii.model.dto.ServiceAttentionRequestDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.pojo.AttentionStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.WoAttentionStatusVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;

/**
 * Interfaz que define los métodos de negocio para el proceso de atenciones
 * de una WorkOrder.
 * 
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface CoreWOAttentionsBusinessLocal {

	/**
	 * Valida los datos de la petición de atención de una nueva WorkOrder.
	 * 
	 * @author jvelezmu
	 * @param request
	 * @throws BusinessException
	 */
	public void validateDataBusinessWOAttentions(WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * Realiza la atención de un servicio de una WorkOrder. 
	 * 
	 * @author jvelezmu
	 * @param request
	 * @return ServiceAttendResponseDTO
	 * @throws BusinessException
	 */
	public ServiceAttendResponseDTO atenderService(ServiceAttentionRequestDTO request) throws BusinessException;
	
	/**
	 * Reporta a SDII los elementos utilizados en la atención de los servicios de una WO.
	 * 
	 * @author jvelezmu
	 * @param request
	 * @throws BusinessException
	 */
	public void reportarElementosUtilizadosEnServicios(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException;
	
	/**
	 * Reporta a SDII los elementos recuperados en la atención de los servicios de una WO.
	 * 
	 * @author jvelezmu
	 * @param request
	 * @throws BusinessException
	 */
	public void reportarElementosRecuperadosEnServicios(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException;
	
	/**
	 * Activa los elementos utilizados en la atención de los servicios en IBS.
	 * 
	 * @author jvelezmu
	 * @param request
	 * @throws BusinessException
	 */
	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * Informa los cambios de IRD a IBS.
	 * 
	 * @author jvelezmu
	 * @param request
	 * @throws BusinessException
	 */
	public void informarCambioDeIRDIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * Actualiza cambio de estado de la WorkOrder en SDII.
	 * 
	 * @author jvelezmu
	 * @param request
	 * @throws BusinessException
	 */
	public void actualizarCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException;
	
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
	 * Metodo: Persiste la información del estado de la atención de una work order
	 * @param woAttentionStatus información del estado de atención de una WorkOrder
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author jjimenezh
	 */
	public void reportWoAttentionStatus(WoAttentionStatusVO woAttentionStatus)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Validaciones generales para la Atención y/o Finalización
	 * de la WO.
	 * Validar que esté asignada a un dealer
	 * Validar que tenga agenda
	 * Validar que tenga cliente
	 * @param request WOAttentionsRequestDTO 
	 * @param changeStatusCode String
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateDataWOAttentionFinalization(WOAttentionsRequestDTO request,String changeStatusCode) throws BusinessException;
	
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
	 * Metodo: Realiza el cambio de estado de la WO a Finalizada
	 * @param request WOAttentionsRequestDTO
	 * @param WOAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public WOAttentionsRequestDTO stateChangeUpdateWOFinalization (WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Reporta a IBS que se adicionaron servicios
	 * @param workorderService WorkOrderServiceVO 
	 * @param woId Long
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void addingServicesReport(WorkOrderServiceVO workorderService, Long woId) throws BusinessException;
	
	/**
	 * Metodo: Retorna la informacion 
	 * de un objeto AttentionStatus por medio del codigo.
	 * @param woAttentionStatusCode
	 * @return AttentionStatus
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public AttentionStatus getAttentionStatus(String woAttentionStatusCode) throws BusinessException;

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
	 * Metodo: Realiza la actualizacion del estado
	 * de la WO en IBS.
	 * @param request WOAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void notifyStatusChangeFinalizationIBS(WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Envia un mail con la inconsistencia de
	 * la Atencion y/o Finalizacion
	 * @param workOrderId Long
	 * @param messageCode String
	 * @param statusVo WoAttentionStatusVO
	 * @param message String
	 * @throws BusinessException 
	 * @author waltuzarra
	 */
	public void sendTrayManagementErrorMail(Long workOrderId, String messageCode, WoAttentionStatusVO statusVo, String message, User ... user) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Validacion Masiva de Workorders
	 * 1. Valida que las Workorders sean del mismo Dealer.
	 * 2. Valida que las Workorders sean del mismo tipo.
	 * 3. Valida que las WorkOrders sean del mismo cliente.
	 * 4. Valida que las Workorders sean de la misma direccion 
	 * atencion del servicio.
	 * @param woAttentionDTO WOAttentionsRequestDTO
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public EnvelopeEncapsulateResponse massiveValidationWorkOrders (WOAttentionsRequestDTO woAttentionDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna los elementos requeridos por servicio
	 * @param inventoryDTO InventoryDTO
	 * @return RequiredServiceElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public RequiredServiceElementDTO getRequiredServiceElements(InventoryDTO inventoryDTO) throws BusinessException;
	
}
