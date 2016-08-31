package co.com.directv.sdii.persistence.dao.assign;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;

/**
 * 
 * EJB encargado de realizar las consultas a la base de datos para realizar los calculos de los KPI 
 * 
 * Fecha de Creación: 2/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiGeneratorDAOLocal {
	
	/**
	 * 
	 * Metodo: Consulta los valores necesarios de las WO que cumplen con el filtro para realizar el calculo del KPI y estan en los estados
	 * enviados por parametro
	 * @param dealerIndicatorDto
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param List<Long> lista de estado de WO para realizar el filtro
	 * @return Lista de objetos que contiene informacion necesaria para realizar el calculo del indicador CycleTime
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Object[]> getWoIdAndRealizationDateByIndicatorDtoInStatus(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los valores necesarios de las WO que cumplen con el filtro para realizar el calculo del KPI y estan en los estados
	 * enviados por parametro para calcular el indicador CycleTime
	 * @param dealerIndicatorDto
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param List<Long> lista de estado de WO para realizar el filtro
	 * @return Lista de objetos que contiene informacion necesaria para realizar el calculo del indicador CycleTime
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Double getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,
            java.util.Date startDate,
            java.util.Date endDate,
            List<Long> woStatus) throws DAOServiceException,DAOSQLException;	
	
	/**
	 * 
	 * Metodo: Consulta los valores necesarios de las WO que cumplen con el filtro para realizar el calculo del KPI y estan en los estados
	 * enviados por parametro para calcular el indicador OnTime
	 * @param dealerIndicatorDto
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param List<Long> lista de estado de WO para realizar el filtro
	 * @return Lista de objetos que contiene informacion necesaria para realizar el calculo del indicador CycleTime
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Double getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,
            java.util.Date startDate,
            java.util.Date endDate,
            List<Long> woStatus,
            Long onTimeSystemHourquantity) throws DAOServiceException,DAOSQLException;
	
	/**
	 * 
	 * Metodo: Suma las WO que cumplen con el filtro y no se encuentran en los estados enviados por parametro
	 * @param dealerIndicatorDto
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param List<Long> lista de estados en los cuales NO se encuentran las WO
	 * @return Long Cantidad de WO que cumplen con el filtro y no se encuentran en los estados enviados por parametro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getWoToCalculateBackLogIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los valores necesarios de las WO que cumplen con el filtro para realizar el calculo del KPI y NO
	 * estan en los estados enviados por parametro
	 * @param dealerIndicatorDto
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param List<Long> lista de estado de WO para realizar el filtro
	 * @return Lista de objetos que contiene informacion necesaria para realizar el calculo del indicador CycleTime
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Object[]> getWoIdAndRealizationDateByIndicatorDtoNotInStatus(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los valores necesarios de las WO que cumplen con el filtro para realizar el calculo del KPI y NO
	 * estan en los estados enviados por parametro
	 * @param dealerIndicatorDto
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param List<Long> lista de estado de WO para realizar el filtro
	 * @return Double cantidad objetos para realizar el calculo del indicador CycleTime
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Double getWoIdAndRealizationDateByIndicatorDtoNotInStatusToCalculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException,DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene las fechas de creacion de las WO entre rango de dechas que son de super-categoria de asistencia tecnica
	 * @param dealerIndicatorDto dtoc on informacion de tipo de zona y pais para realizar el filtro
	 * @param startDate fecha de inicio de filtro
	 * @param endDate fecha de fin de filtro
	 * @return Lista de WO que cumplen con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Object[]> getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceCat(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene la cantidad de WO entre rango de dechas que son de super-categoria de asistencia tecnica y esten entre WO de 90 dias atras
	 * @param dealerIndicatorDto dtoc on informacion de tipo de zona y pais para realizar el filtro
	 * @param startDate fecha de inicio de filtro
	 * @param endDate fecha de fin de filtro
	 * @param woStatus lista de ids con estados en los cuales se desea consultar las wo
	 * @return cantidad de WO que cumplen con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Long getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceCat(DealerIndicatorDTO dealerIndicatorDto,
			                                                                           Date startDate, 
			                                                                           Date endDate, 
			                                                                           List<Long> woStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta las WO de un cliente asignadas a un dealer especifico que se encuentren en los estados enviados por parametro
	 * donde la fecha de atencion se encuentre entre las fechas de los parametros
	 * @param dealerIndicatorDto dto con informacion para filtrar las WO del cliente
	 * @param custId Identificador del cliente
	 * @param startDate fecha de inicio para filtrar fecha de atencion
	 * @param endDate fecha de fin para filtrar fecha de atencion
	 * @param woStatus lista de ids con estados en los cuales se desea consultar las wo
	 * @return List<Object[]> Lista de ids de WO que cumplen con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Object[]> getWoIdByDealerIdAndCustIdAndRealizedOrFinished(DealerIndicatorDTO dealerIndicatorDto, Long custId , java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta la cantidad de WO asignadas a una compañia instaladora en estado atendido o finalizado
	 * @param dealerIndicatorDto dto que contiene valores apra filtrar las wo
	 * @param startDate fecha de inicio para filtrar atencion
	 * @param endDate fecha de fin para filtrar atencion
	 * @param woStatus lista de estados finalizado y atendido en los cuales se van a buscar las wo
	 * @return cantidad de wo atendidas por el dealer en el lapso enviado
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getNumberOfWoInStatusByFilter(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta las WO de super categoria de recuperacion que fueron atendidas o finalizadas en el rango de fechas
	 * @param dealerIndicatorDto dto que contiene informacion para realizar el filtro
	 * @param startDate fecha inicial para consutlar wo atendidas o finalizadas
	 * @param endDate fecha final para consutlar wo atendidas o finalizadas
	 * @param woStatus estado finalizada o atendida
	 * @return cantidad de wo atendidas o finalizadas por el dealer en el rango de fechas
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getAttendedOrFinalizedWoOfRecoveryService(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta la cantidad de WO asignadas a un dealer de super categoria de recuperacion
	 * @param dealerIndicatorDto informacion para realizar filtro
	 * @param startDate fecha inicial para filtrar fecha de asignacion
	 * @param endDate fecha final para filtrar fecha de asignacion
	 * @return cantidad de WO asignadas la dealer en un rango de fechas
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getAssignedWoOfRecoveryService(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Calcula la cantidad de WO por dealer en un codigo postal y un pais que no estan en el los estados enviados 
	 * por parametro
	 * @param countryId id del pais
	 * @param dealerId id del dealer
	 * @param postalCodeId id del tipo de zona
	 * @param woStatus estados en los que NO estan las WO asignadas la dealer
	 * @return cantidad de WO que cumplen con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getWoToCalculateBackLogForBalancedSkill(Long countryId,
			                                            Long dealerId, 
			                                            Long postalCodeId,
			                                            List<Long> woStatus,
			                                            String codeServiceSuperCategory) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Calcula la cantidad de WO por dealer en un codigo postal ,un pais, un tipo de zona y un tipo de super categoria
	 * que no estan en el los estados enviados por parametro
	 * @param countryId id del pais
	 * @param dealerId id del dealer
	 * @param postalCodeId id del codigo postal
	 * @param zoneTypeId id del tipo de zona
	 * @param serviceSuperCategoryId id de la super categpria
	 * @param woStatus estados en los que NO estan las WO asignadas la dealer
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getWoToCalculateBackLogForPrioritizedSkill(Long countryId,Long dealerId, Long postalCodeId,Long zoneTypeId,Long serviceSuperCategoryId,List<Long> woStatus) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la cantidad de work orders atendidas o finalizadas para una
	 * compañía específica en la super categoría de servicio en el tipo de zona y en
	 * el rango de fechas especificado
	 * @param dealerIndicatorDto
	 * @param startDate
	 * @param endDate
	 * @param woStatus
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Long getWoAttendedOrFinishedCountToCalculateBackLogIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate,
			Date endDate, List<Long> woStatus) throws DAOServiceException,
			DAOSQLException;

}
