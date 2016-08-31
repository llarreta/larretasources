/**
 * Creado 3/06/2010 9:48:23
 */
package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WorkOrderDTO;

/**
 * Provee la definición de las operaciones que ofrece el servicio
 * de WorkOrders en un sistema externo, se especifica para reducir el acoplamiento
 * con componentes generados desde definiciones de servicios web de terceros
 * 
 * Fecha de Creación: 3/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@Local
public interface CoreWorkOrderServiceBrokerLocal {
	
	/**
	 * Metodo: Importa desde ibs la información de work orders
	 * @param countryCode String codigo del pais 
	 * @return work order importada
	 * @throws BusinessException En caso de error al tratar de importar las
	 * work orders
	 * @author jjimenezh
	 */
	public List<com.directvla.schema.crm.common.v1_1.WorkOrder> importWorkOrders(String countryCode)throws BusinessException;

	
//	/**
//	 * Metodo: Actualiza el estado de una WorkOrder en IBS
//	 * @param workOrderSdii WorkOrderDTO workOrder de SmartDealer II cuyo estado será actualizado
//	 * Se debe tener en cuenta que el estado que se reportará a IBS es el que retorna
//	 * la operación <code>workOrderSdii.getWorkorderStatusByActualStatusId()</code>
//	 * @param workorderReason Razón para la actualización del estado
//	 * @throws BusinessException En caso de error al tratar de actualizar el estado de la workOrder en IBS
//	 * @author jjimenezh
//	 */
//	public String updateWorkOrderStatus(WorkOrderDTO workOrderSdii, WorkorderReason workorderReason) throws BusinessException;
//	
//	/**
//	 * Metodo: Actualiza el estado de una WorkOrder en IBS
//	 * @param workOrderSdii WorkOrderDTO workOrder de SmartDealer II cuyo estado será actualizado
//	 * Se debe tener en cuenta que el estado que se reportará a IBS es el que retorna
//	 * la operación <code>workOrderSdii.getWorkorderStatusByActualStatusId()</code>
//	 * @param workorderReason Razón para la actualización del estado
//	 * @throws BusinessException En caso de error al tratar de actualizar el estado de la workOrder en IBS
//	 * @author cduarte
//	 */
//	public String updateWorkOrderStatus(String countryCode,
//			 String woCode,
//            Date agendationDate,
//            Long dealerCodeIbs,
//            String workorderReasonCode,
//            String ibs6StateCode) throws BusinessException;
	
	/**
	 * Metodo: Notifica la cancelación de una WorkOrder en IBS
	 * @param woCode Código de la WO a ser actualizada
	 * @param comment comentario registrado por el usuario al realizar la cancelación
	 * @param workorderReasonCode Razón para la actualización del estado
	 * @throws BusinessException En caso de error al tratar de notificar la cancelación el estado de la workOrder en IBS
	 * @author jjimenezh
	 */
	public String notifyWoCancelation(String woCode, String comment, String reasonCode, String countryCode) throws BusinessException;
//	
//	/**
//	 * Metodo: Notifica la asignación de una work order a IBS
//	 * @param workOrderSdii información de la Work order
//	 * @param dealerCode código del dealer asignado a la WO
//	 * @param String workorderReasonCode
//	 * @throws BusinessException En caso de error al notificar la asignación
//	 * @author jjimenezh
//	 */
//	public String notifyWorkOrderAssignment(WorkOrder workOrderSdii, Long dealerCode, String workorderReasonCode) throws BusinessException;
	
	/**
	 * Metodo: Notifica a IBS que se ha agregado un servicio a una work order
	 * @param workOrderCode código de la workOrder
	 * @param serviceCode código del servicio
	 * @param serial Serial del servicio de la tabla WORK_ORDER_SERVICES
	 * @return Código de la relación creada en IBS
	 * @throws BusinessException En caso de error en la invocación del servicio
	 * @author jjimenezh
	 */
	public String addServiceToWorkOrder(String workOrderCode, String serviceCode, String serial, String countryCode)throws BusinessException;
	
	/**
	 * Metodo: Notifica a IBS que se ha borrado un servicio a una work order
	 * @param workOrderCode código de la workOrder
	 * @param serviceCode código del servicio
	 * @return Código de la relación borrada en IBS
	 * @throws BusinessException En caso de error en la invocación del servicio
	 * @author jjimenezh
	 */
	public String removeServiceFromWorkOrder(String workOrderCode, String serviceCode, String countryCode)throws BusinessException;
	
	
	/**
	 * Metodo: <Descripcion>
	 * @throws BusinessException 
	 * @author
	 */
	public void getNewWorkOrderServices(String countryCode) throws BusinessException;
	
	/**
	 * Metodo: Notifica a IBS la activación de servicios al cliente enviando los seriales
	 * @param workOrderCode Código de la work order
	 * @param serials Lista con los seriales
	 * @throws BusinessException en caso de error al reportar a IBS la activación de servicios
	 * @author jjimenezh
	 */
	public void addActivation(String workOrderCode, String countryCode,  List<String> serials) throws BusinessException;
	
	
	/**
	 * Metodo: Reporta a IBS el cambio en seriales de IRD.
	 * @param workOrderCode código de la work order
	 * @param oldSerials seriales antiguos
	 * @param newSerials nuevos seriales
	 * @throws BusinessException En caso de error al reportar los cambios a IBS
	 * @author jjimenezh
	 */
	public void addIRDChanges(String workOrderCode, String countryCode, List<String> oldSerials, List<String> newSerials)throws BusinessException;

	/**
	 * Metodo: Reporta a IBS el cambio en seriales de IRD. Se duplica el metodo para utilizar la DTO interna de HSP+
	 * @param workOrderCode código de la work order
	 * @param oldSerials seriales antiguos
	 * @param newSerials nuevos seriales
	 * @throws BusinessException En caso de error al reportar los cambios a IBS
	 * @author jjimenezh
	 */
	public WorkOrderDTO convertIbsWoIntoSdiiWo(co.com.directv.sdii.dto.esb.WorkOrder ibsWorkOrder, String countryCode) throws BusinessException;
	
}
