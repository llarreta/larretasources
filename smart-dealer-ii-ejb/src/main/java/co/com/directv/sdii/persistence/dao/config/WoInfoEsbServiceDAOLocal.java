package co.com.directv.sdii.persistence.dao.config;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MessageCoreAllocatorDTO;
import co.com.directv.sdii.model.dto.WoInfoEsbServiceDTO;
import co.com.directv.sdii.model.dto.WoInfoEsbServiceReportDTO;
import co.com.directv.sdii.model.pojo.WoInfoEsbParentDate;
import co.com.directv.sdii.model.pojo.WoInfoEsbService;
import co.com.directv.sdii.model.pojo.WoInfoEsbNotificationType;
import co.com.directv.sdii.model.pojo.WoInfoEsbState;
import co.com.directv.sdii.model.pojo.WoInfoEsbType;
import co.com.directv.sdii.model.vo.WorkOrderVO;

@Local
public interface WoInfoEsbServiceDAOLocal {
	
	/**
	 * Metodo encargado de la creacion de un evento en la tabla de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbService Informacion completa del evento de core o asignador
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void createWoInfoEsbService(WoInfoEsbService woInfoEsbService)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo encargado de la creacion de un evento en la tabla de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbService Informacion completa del evento de core o asignador
	 * @param responseXML XML que se desea guardar en Base de Datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void createWoInfoEsbService(WoInfoEsbService woInfoEsbService, byte[] responseXML)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Busca un evento en base de datos del procesamiento en paralelo de core y asignador
	 * @param id id del evento
	 * @return evento del procesamiento en paralelo
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public WoInfoEsbService findWoInfoEsbServiceById(Long id)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Crea el log para un evento de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbServiceId id del evento al que se le generara el log
	 * @param timeNow fecha y hora actual, con la cual se generara el log
	 * @param description mensaje que lleva el log
	 * @param codeTypeNotification tipo de notificacion en caso de ser necesaria la generacion del reporte de errores
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void createWoInfoEsbServiceLog(Long woInfoEsbServiceId, Date timeNow, String description, String codeTypeNotification)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Actualiza un evento de procesamiento en paralelo con la informacion traida en woInfoEsbService
	 * @param woInfoEsbService informacion que quedara en base de datos.
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void updateWoInfoEsbService(WoInfoEsbService woInfoEsbService)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Actualiza un evento de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbServiceId id del evento que se desea actualizar
	 * @param stateCode nuevo estado que tendra el evento
	 * @param tryNum numero de intento que se realizo
	 * @param dateNow fecha y hora actual con la que quedara la actualizacion
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void updateWoInfoEsbServiceSatate(Long woInfoEsbServiceId, String stateCode, boolean tryNum, Date dateNow)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Elimina un evento de procesamiento en paralelo de core y asignador
	 * @param id id del evento que se desea eliminar
	 * @return si se logro o no eliminar el evento
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public Boolean deleteWoInfoEsbService(Long id)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Extrae un tipo de notificacion de la base de datos dado su codigo
	 * @param codeTypeNotification codigo del tipo de notificacion
	 * @return el tipo de notificacion dado el codigo
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public WoInfoEsbNotificationType getWoInfoEsbNotificationTypeByCode(String codeTypeNotification) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * consulta las work orders que se deben enviar al procesamiento en paralelo tanto de core como de asignador y crea las DTO que se deben enviar a
	 * las respectivas colas de mensajes
	 * @param stateCodes codigos de estado en los cuales es valida la consulta de los eventos de core y asignador (ej pendiente, pendiente por reprocesar)
	 * @param limit limite maximo de eventos que se extraeran
	 * @param countryId pais del cual se desean consultar los eventos
	 * @param typeCode tipo de evento que se desea consultar (core, asignador)
	 * @param stateCodeNoQuery estados de work orders de los cuales debe ignorar para no cruzar los distintos prcesadores (ej iniciado)
	 * @param reProccesing si es una consulta de reprocesamiento o no
	 * @param dateNow fecha y hora actual para la comparacion de reprocesamiento
	 * @return lista de mensajes que se deben enviar a las respectivas colas de mensajes
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 * @throws PropertiesException 
	 */
	public List<MessageCoreAllocatorDTO> findAllWoInfoEsbServiceMessageByParameters(List<String> stateCodes, Long limit, Long countryId, String typeCode, 
			List<String> stateCodeNoQuery, boolean reProccesing, Date dateNow, String ... stateCodeNoQueryListUnic ) throws DAOServiceException,DAOSQLException, PropertiesException ;

	public List<MessageCoreAllocatorDTO> findAllWoInfoEsbServiceMessageByParameters(List<Long> stateIds, Long limit, Long countryId, 
			List<Long> stateIdNoQuery, boolean reProccesing, Date dateNow, String typeCode, Long delayTimeMs) throws DAOServiceException,DAOSQLException, PropertiesException ;
	
	/**
	 * Metodo encargado de buscar la fecha padre dado un pais y una fecha y hora actual
	 * @param actualDate fecha y hora actual
	 * @param countryId id del pais del que se desea la consulta
	 * @return fecha padre dado un pais y una fecha
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public WoInfoEsbParentDate getDateForTheCountry(Date actualDate, Long countryId) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Metodo encargado de encontrar un estado de eventos de procesamiento en paralelo de core y asignador dado un codigo de estado
	 * @param codeState codigo del estado que se desea buscar
	 * @return estado encontrado con el codigo suministrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public WoInfoEsbState getWoInfoEsbStateByCode(String codeState) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Metodo encargado de buscar un tipo de evento de procesamiento en paralelo de core y asignador dado un codigo de tipo de evento
	 * @param code codigo de tipo de evento
	 * @return tipo de evento encontrado con el codigo suministrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public WoInfoEsbType getWoInfoEsbTypeByCode(String code) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Busca procesos bloqueados dentro de la tabla de eventos de procesamiento en paralelo de core y asignador
	 * @param countryId pais en el cual se desean buscar los eventos bloqueados
	 * @param dateNow fecha y hora actual para comparar el tiempo que llevan bloqueados los eventos
	 * @return lista de eventos bloqueados mas tiempo de lo que permiten los parametros del sistema
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @author Aharker
	 */
	public List<WoInfoEsbServiceDTO> searchBlockInfoEsbService(Long countryId, Date dateNow) throws DAOSQLException, DAOServiceException;
	
	/**
	 * busca los log de eventos que necesiten generar reporte
	 * @param countryId id del pais del cual se desea generar el reporte
	 * @param typeReport tipo de reporte, si es de core o asignador
	 * @return lista de intentos y errores del proceso de core o asignador segun corresponda
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @author Aharker
	 */
	public List<WoInfoEsbServiceReportDTO> searchInfoReport(Long countryId, String typeReport) throws DAOSQLException, DAOServiceException;
	
	/**
	 * Actualiza los logs suministrados para que no se envien mas reportes con ellos
	 * @param woInfoEsbServiceReportDTOs logs que deben actualizarse
	 * @param notify el valor que se debe colocar en el campo de notificacion de los logs
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void updateStateProcesbyReport(List<WoInfoEsbServiceReportDTO> woInfoEsbServiceReportDTOs, String notify) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo enfocado a consultar que codigo de work orders de un conjunto especifico esta pendiente por el proceso en paralelo para un tipo de proceso dado 
	 * @param workOrders consjunto de work orders que se desea validar
	 * @param status estados de las work order que se necesitan validar
	 * @param process oproceso para el cual se validara
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public List<String> getPendingProccessForCore(List<WorkOrderVO> workOrders, List<String> status, String process) throws DAOServiceException, DAOSQLException;
	
	public void updateWoInfoEsbServiceStateInBlock(List<Long> idsRecord, Long idState)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo que crea una WoInfoEsbParentDate.
	 * En caso que exista una WoInfoEsbParentDate ya creada con la misma fecha DD/MM/AA se lanzará una excepción por restricción de unicidad.
	 * 
	 * @param woInfoEsbParentDate a crear
	 */
	public void createWoInfoEsbParentDate(WoInfoEsbParentDate woInfoEsbParentDate) throws DAOServiceException, DAOSQLException;

	public Date getLastWOEventDate(String woCode) throws DAOServiceException, DAOSQLException;
	
}
