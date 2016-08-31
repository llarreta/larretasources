package co.com.directv.sdii.ejb.business.allocator;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.ws.model.dto.AssignResponseDTO;

/**
 * Interfaz que define los m�todos de negocio de Asignaci�n.
 *
 * @author Jimmy V�lez Mu�oz
 *
 */
@Local
public interface AllocatorBeanLocal {

	/**
	 * jjimenezh 2010-10-11
	 * Determina el identificador de la jornada nula, se usa para el proceso de negocio BPEL por control de cambios que 
	 * permite que la jornada y la fecha de agendamiento sean nulas en el proceso de asignación
	 */
	public static final Long NULL_SERVICE_HOUR_ID = -1L;
	
	/**
	 * jjimenezh 2010-10-11
	 * Determina la fecha de agendamiento nula, se realiza por control de cambios que permite la asignación de work orders que aún no han sido agendadas
	 */
	public static final Date NULL_AGENDATION_DATE = UtilsBusiness.stringToDate("01/01/1980");

	/**
	 * Caso de Uso ADS - 02 - Asignar Compañia Instaladora.
	 * 
	 * La finalidad de este metodo es asignar una compañia instaladora 
	 * en base a una fecha y jornada previamente establecidas.
	 * @param workOrder work order a la que se le asignara un dealer dependiendo de las habilidades configuradas para asignador
	 * @param allocatorEventMasterVO evento de asignador donde se reportara la asignacion
	 * @return
	 * @throws BusinessException
	 * @author gfandino
	 */
	public DealerVO assignDealer(WorkOrderVO workOrder,AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException;
	
	/**
	 * Metodo: Persiste en la base de datos la asignación de una Work Order a un dealer
	 * Sin realizar notificación de cambio de estado a IBS
	 * @param workOrder información de la WO a ser asignada
	 * @param dealer Información del dealer al cual se le realizará la asignación
	 * @throws BusinessException En caso de error al realizar la asignación
	 * @author jjimenezh
	 */
	public void createSdiiAssignment(WorkOrderVO workOrder, DealerVO dealer) throws BusinessException;
//	/**
//	 * Metodo: <Descripcion>
//	 * @param workOrder
//	 * @param woReasonCode
//	 * @param newWoStatusCode
//	 * @param notify2Ibs
//	 * @throws BusinessException <tipo> <descripcion>
//	 * @author jjimenezh
//	 */
//	public void changeWOStatus2Pending(WorkOrderVO workOrder, String woReasonCode, String newWoStatusCode, boolean notify2Ibs) throws BusinessException;
	/**
	 * Caso de Uso ADS - 03 - Compa��as que cumplen Habilidades Determinantes.
	 * 
	 * La finalidad de este m�todo es determinar, dado un conjunto de compa��as instaladoras, 
	 * cuales tienen las habilidades determinantes establecidas en el sistema.
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public List<DealerVO> getDeterminantSkillsDealers(CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode,CountryVO country) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 04 - Compa��as que cumplen Habilidades Eliminantes.
	 * 
	 * La finalidad de este m�todo es determinar, dado un conjunto de compa��as instaladoras, 
	 * cuales tienen las habilidades eliminantes establecidas en el sistema.
	 * 
	 * @param dealers - List<DealerVO>
	 * @param customer - CustomerVO
	 * @param postalCode - PostalCodeVO
	 * @param services - List<ServiceVO>
	 * @param date - Date
	 * @param serviceHour - ServiceHourVO
	 * @param buildingCode - Long
	 * @param country - Country
	 * @return List<DealerVO>
	 * @throws BusinessException
	 * @author gfandino
	 */
	//public List<DealerVO> getEliminanteSkillsDealers(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode,CountryVO country) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 05 - Compañías que cumplen Habilidades Puntuables.
	 * 
	 * La finalidad de este método es determinar, dado un conjunto de companías instaladoras, 
	 * y de acuerdo a las habilidades puntuables establecidas en el sistema, 
	 * cuales tienen el mayor valor de puntuación.
	 * 
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public List<DealerVO> getSkillsRatesDealers(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 06 - Asignación Por Rotación con porcentaje de participación.
	 * 
	 * La finalidad de este método es determinar, dado un conjunto de compañías instaladoras, 
	 * y de acuerdo a un porcentaje de participación en la zona, 
	 * cuales cual es la compañía que debe prestar el servicio.
	 * 
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return List<DealerVO>
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	//public List<DealerVO> getDealersByRotation(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 07 - Evaluar Habilidad Cobertura.
	 * 
	 * La finalidad de este m�todo es definir las compa��as instaladoras 
	 * que tienen cobertura para un c�digo postal determinado.
	 * 
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public List<DealerVO> getDealersByPostalCode(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 08 - Evaluar Habilidad Tipo de Servicio.
	 * 
	 * La finalidad de este m�todo es definir las compa��as instaladoras 
	 * que prestan unos servicios espec�ficos para un c�digo postal dado.
	 * 
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return List<DealerVO>
	 * @throws BusinessException
	 * @author gfandino
	 * @author jvelezm
	 */
	//public List<DealerVO> getDealersByServicesAndPostalCode(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 09 - Evaluar Habilidad Capacidad.
	 * 
	 * La finalidad de este m�todo es definir las compa��as instaladoras 
	 * que tiene capacidad disponible para atender un servicio 
	 * para una localidad o zona determinada en una fecha jornada determinada.
	 *  
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return List<DealerVO>
	 * @throws BusinessException
	 */
	//public List<DealerVO> getDealersByCapacityAndPostalCode(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 10 - Evaluar Habilidad Tipo de Cliente.
	 * 
	 * La finalidad de este m�todo es definir las compa��as instaladoras 
	 * que pueden brindar atenci�n a un tipo determinado de cliente en una zona determinada.
	 *  
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return List<DealerVO>
	 * @throws BusinessException
	 * @author gfandino
	 */
	//public List<DealerVO> getDealersByCustomerTypeAndPostalCode(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;

	/**
	 * Caso de Uso ADS - 11 - Evaluar Habilidad Vende Instala.
	 * 
	 * La finalidad de este metodo es definir para una instalacion de servicio 
	 * en un cliente nuevo si la compañiaa que realizo la venta realizara la atencion del servicio.
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
	 * @return List<DealerVO>
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
	 * @author gfandino
	 */
	public DealerVO getDealerByBuildingAttend(List<DealerVO> dealers, CustomerVO customer, PostalCodeVO postalCode, List<ServiceVO> services, Date date, ServiceHourVO serviceHour, String buildingCode) throws BusinessException;

	/**
	 * Retorna los días de capacacidad permitidos definidos por un parámetro del sistema
	 * @author lcardozo
	 * @param country
	 * @return Long
	 * @throws BusinessException
	 */
	public Long getCapacityDaysParam(CountryVO country) throws BusinessException;

	
	/**
	 * Metodo: Realiza las validaciones previas a la asignación de un work order así:<br>
	 * 1. La fecha ingresada debe ser válida e igual o posterior a la fecha del día siguiente<br>
	 * 2. La jornada debe corresponder a una jornada registrada en el sistema<br>
	 * 3. Los servicios deben corresponder a servicios registrados en el sistema<br>
	 * 4. El Código Postal debe venir en la estructura de códigos correcta<br>
	 * 5. Validar que la work order pueda cambiar a estado "asignada" <br>
	 * @param wo objeto con la información de la work order solo es necesario el id
	 * @param agendaDate fecha de agendamiento de la work order
	 * @param serviceHour jornada en la que se realizará la atención de la work order solo es necesario el id
	 * @throws BusinessException En caso de error al realizar las validaciones.
	 * @author jjimenezh
	 */
	public void doWorkorderValidations(WorkOrderVO wo, Date agendaDate, ServiceHourVO serviceHour)throws BusinessException;
	
	/**
	 * Metodo: Actualiza el estado de proceso de una lista de work orders
	 * @param workOrders actualiza el estado de proceso de una lista de work orders
	 * @param processStatusCode código de estado de proceso de work order
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author jjimenezh
	 */
	public void changeProcessStatus2WorkOrders(List<WorkOrderVO> workOrders, String processStatusCode,String originUpdateEvent)throws BusinessException;

	/**
	 * Metodo: cambia el estado de procesamiento de una work order
	 * @param workOrder work order a la que se le actualizará el estado de procesamiento
	 * @param processStatusCode código de estado de proceso
	 * @throws BusinessException en caso de error
	 * @author jjimeenzh
	 */
	public void changeProcessStatus2WorkOrder(WorkOrderVO workOrder, String processStatusCode,String originUpdateEvent)throws BusinessException;
	
	/**
	 * Metodo: Reporta el evento de una work order en caso que se genere error al tratar de asignarla
	 * @param workorder work order que se está asignando
	 * @param eventCode código de error lanzado por la business excepcion que se captura
	 * @param eventMessage mensaje de error que tiene la business excepcion
	 * @throws BusinessException en caso de error al registrar el evento de la work order
	 */
	public void reportAllocatorEvent(WorkOrderVO workorder, String eventCode, String eventMessage, AllocatorEventMasterVO allocatorEventMasterVO)throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar la workorderCSR
	 * @param woCode 
	 * @throws BusinessException <tipo> <descripcion> 
	 * @author 
	 */
	public void updateWorkOrderCSRByWoCode(String woCode) throws BusinessException;

	/**
	 * Metodo: Permite asignar al dealer en el proceso de asignador
	 * @param workOrder 
	 * @param response 
	 * @return 
	 * @throws BusinessException <tipo> <descripcion> 
	 * @author 
	 */
	public DealerVO assignDealer(WorkOrderVO workOrder, AssignResponseDTO response) throws BusinessException;

}
