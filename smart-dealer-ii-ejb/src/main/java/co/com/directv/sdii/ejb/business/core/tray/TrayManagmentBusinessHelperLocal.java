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
 * Interfaz que define los métodos de negocio para el proceso de atenciones
 * de una WorkOrder, se encarga de gestionar los errores presentados
 * en el proceso de atencion y/o finalizacion.
 * 
 * Fecha de Creación: 21/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrayManagmentBusinessHelperLocal {


	/**
	 * 
	 * Metodo:Valida los datos de la petición de atención de una nueva WorkOrder.
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void validateDataBusinessWOAttentions(WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza la atención de un servicio de una WorkOrder. 
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
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
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
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
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void reportarElementosUtilizadosEnServicios(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo:  Reporta a SDII los elementos recuperados en la atención de los servicios de una WO.
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void reportarElementosRecuperadosEnServicios(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo:Activa los elementos utilizados en la atención de los servicios en IBS.
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo:  Informa los cambios de IRD a IBS.
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void informarCambioDeIRDIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Reporta a IBS que se adicionaron servicios
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param WorkOrderServiceVO workorderService
	 * @param woId Long
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void addingServicesReport(WorkOrderServiceVO workorderService, Long woId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Actualiza cambio de estado de la WorkOrder en SDII.
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
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
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateDataBusinessWOFinalization(WOAttentionsRequestDTO request) throws BusinessException;	
	
	/**
	 * 
	 * Metodo: Registros de los elementos no serializdos
	 * empleados en la atencion de la WorkOrder.
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
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
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void reportElementsUsedFinalization(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Reportar los elementos no serializados
	 * recuperados en la finalizacion de la WorkOrder.
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	//public void reportedRecoveryNotSerializedFinalization(WOAttentionElementsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza el cambio de estado de la WO a Finalizada
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request WOAttentionsRequestDTO
	 * @return WOAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public WOAttentionsRequestDTO stateChangeUpdateWOFinalization (WOAttentionsRequestDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Validacion de objetos requeridos para
	 * la finalizacion de la WorkOrder
	 * @param request
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
	 * @param trayRequest
	 * @return WOAttentionsRequestDTO woAttentionDTO
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
	 * Atencion de la WorkOrder
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
	 * Atencion de la WorkOrder
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
	 * @param inventoryDTO
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public EnvelopeEncapsulateResponse completeWorkOrderFinalization(InventoryDTO inventoryDTO) throws BusinessException;
	
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
	 * Operacion encargada de gestionar los errores presentados en el proceso
	 * de negocio.
	 * @param request
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void upgradeDowngradeResource(WOAttentionElementsRequestDTO request) throws BusinessException;
}
