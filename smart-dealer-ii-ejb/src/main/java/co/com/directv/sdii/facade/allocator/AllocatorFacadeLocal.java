package co.com.directv.sdii.facade.allocator;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.schedule.dto.ScheduleColorsConfig;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Interfaz que define los métodos del módulo de Allocator.
 * 
 * @author Jimmy Vélez Muñoz
 *
 */
@Local
public interface AllocatorFacadeLocal {

	/**
	 * Caso de Uso ADS - 02 - Asignar Compañia Instaladora.
	 * 
	 * La finalidad de este metodo es asignar una compañia instaladora 
	 * en base a una fecha y jornada previamente establecidas.
	 * @param workOrder work order a la cual se le buscara un dealer
	 * @param allocatorEventMasterVO evento de asignador donde se registrara la asignacion
	 * @return
	 * @throws BusinessException
	 */
	public DealerVO assignDealer(WorkOrderVO workOrder, AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException;
	
	/**
	 * Retorna los dias de capacidad leidos por parametro del sistema
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param country
	 * @return
	 * @throws BusinessException
	 */
	public Long getCapacityDaysParam(CountryVO country) throws BusinessException;

	/**
	 * Caso de Uso ADS - 12 - Evaluar Habilidad Atendi� Ultimo Servicio.
	 * 
	 * La finalidad de este m�todo es definir para un servicio 
	 * en un cliente existente si la compa��a que realizo el �ltimo servicio 
	 * realizar� la atenci�n de este servicio.
	 * 
	 * @param dealers - List<DealerVO>
	 * @param customer - CustomerVO
	 * @param postalCode - PostalCodeVO
	 * @param services - List<ServiceVO>
	 * @param date - Date
	 * @param serviceHour - ServiceHourVO
	 * @param buildingCode - Long
	 * @return List<DealerVO>
	 * @throws BusinessException
	 * @author gfandino
	 */
	public List<DealerVO> getDealerByLastServiceAttend(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 15 - Evaluar Habilidad Atiende Edificio.
	 * 
	 * La finalidad de este m�todo es asignar para un servicio la compa��a instaladora 
	 * que realizo el �ltimo servicio a ese edificio.
	 * 
	 * @param dealers - List<DealerVO>
	 * @param customer - CustomerVO
	 * @param postalCode - PostalCodeVO  
	 * @param services - List<ServiceVO>
	 * @param date - Date
	 * @param serviceHour - ServiceHourVO
	 * @param buildingCode - Long
	 * @return List<DealerVO>
	 * @throws BusinessException
	 * @author gfandino
	 */
	public DealerVO getDealerByBuildingAttend(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
//	/**
//	 * Metodo: Realiza las validaciones previas a la asignación de un work order así:<br>
//	 * 1. La fecha ingresada debe ser válida e igual o posterior a la fecha del día siguiente<br>
//	 * 2. La jornada debe corresponder a una jornada registrada en el sistema<br>
//	 * 3. Los servicios deben corresponder a servicios registrados en el sistema<br>
//	 * 4. El Código Postal debe venir en la estructura de códigos correcta<br>
//	 * 5. Validar que la work order pueda cambiar a estado "asignada" <br>
//	 * @param wo objeto con la información de la work order solo es necesario el id
//	 * @param agendaDate fecha de agendamiento de la work order
//	 * @param serviceHour jornada en la que se realizará la atención de la work order solo es necesario el id
//	 * @throws BusinessException En caso de error al realizar las validaciones.
//	 * @author jjimenezh
//	 */
//	public void doWorkorderValidations(WorkOrderVO wo, Date agendaDate, ServiceHourVO serviceHour)throws BusinessException;

	/**
	 * Metodo: Actualiza el estado de proceso de una lista de work orders
	 * @param workOrders actualiza el estado de proceso de una lista de work orders
	 * @param processStatusCode código de estado de proceso de work order
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author jjimenezh
	 */
	public void changeProcessStatus2WorkOrders(List<WorkOrderVO> workOrders, String processStatusCode)throws BusinessException;

	/**
	 * Metodo: cambia el estado de procesamiento de una work order
	 * @param workOrder work order a la que se le actualizará el estado de procesamiento
	 * @param processStatusCode código de estado de proceso
	 * @throws BusinessException en caso de error
	 * @author jjimeenzh
	 */
	public void changeProcessStatus2WorkOrder(WorkOrderVO workOrder, String processStatusCode)throws BusinessException;

	/**
	 * Metodo: Reporta el evento de una work order en caso que se genere error al tratar de asignarla
	 * @param workorder work order que se está asignando
	 * @param eventCode código de error lanzado por la business excepcion que se captura
	 * @param eventMessage mensaje de error que tiene la business excepcion
	 * @param allocatorEventMasterVO evento de asignador donde se reportara la work order
	 * @throws BusinessException en caso de error al registrar el evento de la work order
	 */
	public void reportAllocatorEvent(WorkOrderVO workorder, String eventCode, String eventMessage, AllocatorEventMasterVO allocatorEventMasterVO)throws BusinessException;

	/**
	 * Metodo: Implementacion de la Logica de asignador para un pais dado
	 * @param countryId id del pais para el que se ejecuta el proceso de asignador
	 * @throws BusinessException excepcion encapsulada en caso de fallar el proceso de asignador para dejar los eventos en la tabla de procesamiento
	 * 								en paralelo de core y asignador
	 * 
	 */
	public void allocWorkOrders(Long countryId) throws BusinessException;
	
	/**
	 * 
	 * @param serviceSuperCategoryByID
	 * @param countryId
	 * @return
	 * @throws BusinessException
	 */
	public ScheduleColorsConfig loadScheduleColorsConfig(Long serviceSuperCategoryByID,Long countryId) throws BusinessException;
	
}
