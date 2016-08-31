/**
 * Creado 11/11/2010 16:02:52
 */
package co.com.directv.sdii.ejb.business.allocator;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.hibernate.annotations.Parameter;
import org.junit.experimental.theories.ParametersSuppliedBy;

import co.com.directv.sdii.assign.schedule.dto.ScheduleColorsConfig;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 11/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface AllocatorBeanHelperLocal {

	/**
	 * Caso de Uso ADS - 02 - Asignar Compa�ia Instaladora.
	 * 
	 * La finalidad de este m�todo es asignar una compa��a instaladora 
	 * en base a una fecha y jornada previamente establecidas.
	 * 
	 * @param workOrder Work order a la cual se le asignara un dealer
	 * @param allocatorEventMasterVO evento principal del proceso de asignador en el cual se llevara traza de la asignacion del dealer
	 * @return
	 * @throws BusinessException
	 */
	public DealerVO assignDealer(WorkOrderVO workOrder,AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException;

	
	/**
	 * Caso de Uso ADS - 11 - Evaluar Habilidad Vende Instala.
	 * 
	 * La finalidad de este m�todo es definir para una instalaci�n de servicio 
	 * en un cliente nuevo si la compa��a que realizo la venta realizar� la atenci�n del servicio.
	 * 
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return
	 * @throws BusinessException
	 */
	public List<DealerVO> getDealerBySaleAndInstall(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;

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
	 * @return
	 * @throws BusinessException
	 * @author gfandino
	 */
	public List<DealerVO> getDealerByLastServiceAttend(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 13 - Evaluar Habilidad Inventario.
	 * 
	 * La finalidad de este m�todo es definir para una orden de trabajo 
	 * si las compa��as tiene el inventario suficiente para darle atenci�n.
	 * 
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return
	 * @throws BusinessException
	 */
	public List<DealerVO> getDealersByStock(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
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
	 */
	public DealerVO getDealerByBuildingAttend(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	
	/**
	 * 
	 * Método de negocio que evalúa que compañías cumplen con las habilidades determinantes.
	 * 
	 * @param dealers
	 * @param wo
	 * @param agendaDate
	 * @param serviceHour
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public List<DealerVO> evaluarHabilidadesDeterminantes(List<DealerVO> dealers, WorkOrderVO wo, Date agendaDate, ServiceHourVO serviceHour) throws BusinessException;
	
	/**
	 * 
	 * Método de negocio que evalúa que compañías cumplen con las habilidades eliminantes.
	 * 
	 * @param dealers
	 * @param wo
	 * @param agendaDate
	 * @param serviceHour
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public List<DealerVO> evaluarHabilidadesEliminantes(List<DealerVO> dealers, WorkOrderVO wo, Date agendaDate, ServiceHourVO serviceHour) throws BusinessException;
	
	/**
	 * 
	 * Método de negocio que evalúa que compañías cumplen con las habilidades puntuables.
	 * 
	 * @param dealers
	 * @param wo
	 * @param agendaDate
	 * @param serviceHour
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public List<DealerVO> evaluarHabilidadesPuntuables(List<DealerVO> dealers, WorkOrderVO wo, Date agendaDate, ServiceHourVO serviceHour) throws BusinessException;
	
	/**
	 * 
	 * Método de negocio que evalúa que compañía es la que se debe asignar.
	 * 
	 * @param dealers
	 * @param wo
	 * @param agendaDate
	 * @param serviceHour
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public DealerVO asignarDealerPorRotacion(List<DealerVO> dealers, WorkOrderVO wo, Date agendaDate, ServiceHourVO serviceHour) throws BusinessException;
	
	/**
	 * Retorna los días de capacacidad permitidos definidos por un parámetro del sistema
	 * @author lcardozo
	 * @param country
	 * @return Long
	 * @throws BusinessException
	 */
	public Long getCapacityDaysParam(CountryVO country) throws BusinessException;
	
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
	 * @param allocatorEventMasterVO evento de asignador donde se reportara el evento
	 * @throws BusinessException
	 */
	public void reportAllocatorEvent(WorkOrderVO workorder, String eventCode, String eventMessage, AllocatorEventMasterVO allocatorEventMasterVO)throws BusinessException;
	
	/**
	 * Metodo: Implementacion de la Logica de asignador para un pais dado
	 * @param countryId id del pais para el que se ejecuta el proceso de asignador
	 * @throws BusinessException excepcion encapsulada en caso de fallar el proceso de asignador para dejar los eventos en la tabla de procesamiento
	 * 								en paralelo de core y asignador
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
	
	/**
	 * 
	 * @param woCode codigo de la orden de trabajo a la cual se le colocara el dealer mediante el proceso de asignador
	 * @param countryCode codigo del pais al que pertenece la WO
	 * @param woInfoEsbServiceId id del registro a procesar de procesamiento en paralelo de asignador
	 * @param countryId id del pais al que pertenece la WO
	 * @throws BusinessException Excepcion de negocio que encapsula cualquier error en la asignacion de un dealer a una wo
	 * @author Aharker
	 * @throws PropertiesException 
	 */
	public void assignDealer(String woCode, String countryCode, Long woInfoEsbServiceId, Long countryId) throws BusinessException, PropertiesException;

	
}
