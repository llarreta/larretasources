package co.com.directv.sdii.ejb.business.broker.toa;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.WorkOrder;

/**
 * 
 * Interfaz que declara las operaciones de negocio
 * basados en objetos de Contratos de Web Services y
 * objetos de negocio HSP+
 * 
 * Fecha de Creación: 14/07/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CoreWorkorderImporterServiceLocalTOA {

	/**
	 * Metodo encargado de extraer la informacion de localizacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateLocaleInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException;

	/**
	 * Metodo encargado de extraer la informacion de fecha de creacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 */
	public void populateCreationDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto);
	
	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del cliente de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	public void populateCustomerInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;

	public void populateDealerDummy(WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;
	
	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del dealer de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateDealerInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException;

	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del dealer vendedor de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateDealerSaleInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del history event de la work order
	 * @param pIbsWorkOrder pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 */
	public void populateHistoryCodeEventInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto);
	
	/**
	 * Spira 22159 - REPORTES FTP:Campos inconsistentes
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del history reason de la work order
	 * @param pIbsWorkOrder pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 */
	public void populateHistoryReasonEventInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto)throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;
	
	/**
	 * Metodo encargado de extraer la fecha de programacion del objeto del servicio de ESB para llenar la DTO de HSP+
	 * @param pIbsWorkOrder pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	public void populateProgrammingDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;
	
	/**
	 * 
	 * @param pIbsWorkOrder
	 * @param workOrderDto
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	public void populateUserInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;
	
	/**
	 * Metodo encargado de extraer del objeto de ESB la informacion general de la work order que es necesaria sin importar el estado en que venga la work order
	 * tales como no dejar la agenda vencida, la Accion a tomar, codigo de la WorkOrder, descripcion de la WorkOrder
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	public void populateGeneralInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws BusinessException, PropertiesException;
	
	/**
	 * Metodo encargado de extraer la informacion de fecha de realizacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateRealizationDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * Metodo encargado de extraer la informacion de fecha de finalizacion de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateFinalizationDateInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * Metodo encargado de extraer la informacion de la Shipping Order de la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateShippingOrderInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException;
	
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
	public void populateWorkOrderStatusInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;

	/**
	 * 
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @param actualWorkOrder Work order como se encuentra actualmente en HSP+
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateWOAssignmentInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException;

	/**
	 * Metodo encargado de extraer la informacion de la agenda la work order y dejarlo en la DTO de hsp+
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException
	 */
	public void populataWOAgendaInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException;

	/**
	 * 
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	public void populateBuildingInformacion(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws BusinessException, PropertiesException;
	
	public void populateWOImportationDateInformation(WorkOrderDTO workOrderDto) throws BusinessException, PropertiesException;

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
	public void populateWorkOrderPendingStatusInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;

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
	public void populateWorkOrderIbsActualStatus(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;
	
	public void populateWorkOrderProcessSource(WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;
	
	/**
	 * Metodo: Pobla la agenda de la WO cuando baja de IBS atentida o finalizada
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException
	 */
	public void populateWOAgendaInformationForRealizedAndFinalizedWO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException;
	
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
	public void populateWOAgendaInformationForScheduledReScheduled(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException;

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
	public void populateWOMovementInventoryDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException;
	
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
	public void populateWOAgendaInformationForAssign(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, String countryCode) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException;
	
	/**
	 * Metodo: permite poblar la fecha de cancelacion de la work order en la descarga
	 * @param workOrderDto <tipo> <descripcion>
	 * @author
	 */
	public void populataWOCancelationDate(WorkOrderDTO workOrderDto);
	
	/**
	 * Metodo: Permite colocar a la workOrder el process Status en ejecutado sin errores en el flujo de cancelacion de la descarga
	 * @param workOrderDto
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void populataWOCancelProcessStatus(WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException;
	
	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del técnico encargado, de la work order
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateTechnicalInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException;

	/**
	 * Metodo encargado de llenar en la DTO de HSP+ la informacion del del edificio al que corresponde el customer de la wo.
	 * @param pIbsWorkOrder objeto proveniente del servicio de ESB
	 * @param workOrderDto DTO donde se llenaran los datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void populateBuildingInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException;
}
