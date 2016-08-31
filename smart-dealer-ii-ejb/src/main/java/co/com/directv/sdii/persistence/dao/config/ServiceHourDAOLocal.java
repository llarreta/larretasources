package co.com.directv.sdii.persistence.dao.config;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceHour;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ServiceHour
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceHourDAOLocal {
	
	 /**
     * Crea una jornada de servicio en el sistema
     * @param obj - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createServiceHour(ServiceHour obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un servicehour con el id especificado
     * @param id - Long
     * @return - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ServiceHour getServiceHourByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un servicehour especificado
     * @param obj - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateServiceHour(ServiceHour obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un servicehour del sistema
     * @param obj - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteServiceHour(ServiceHour obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los servicehours del sistema
     * @return - List<ServiceHour>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceHour> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de jornadas dado el identificador de un país
     * @param countryId identificador del país
     * @return lista de jornadas que aplican a un país específico
     * @throws BusinessException en caso de error al tratar de obtener la lista de jornadas regionalizadas
     * @author jjimenezh agregado por control de cambios 2010-04-26
     */
    public List<ServiceHour> getAllServiceHoursByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de jornadas dado el identificador de un país y por codigo de estado
     * @param countryId identificador del país
     * @return lista de jornadas que aplican a un país específico
     * @throws BusinessException en caso de error al tratar de obtener la lista de jornadas regionalizadas
     * @author jnova agregado por control de cambios 2011-03-31
     */
    public List<ServiceHour> getAllServiceHoursByCountryIdAndStatusCode(Long countryId,String statusCode) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un servicehour con el id especificado
     * @param id - Long
     * @return - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceHour> getServiceHourByName(String name) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene las jornadas de servicios dentro de un rango de tiempo especifico
     * @param init - Date
     * @param end - Date
     * @return -List<ServiceHourVO>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceHour> getServiceHoursByDate(Date init, Date end) throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene una lista de las jornadas para un país y estado de jornada especificados
	 * @param countryId identificador del país
	 * @param serviceHourStatusId identificador de la jornada
	 * @return Lista de las jornadas que se encuentran en el país y con el estado especificados
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 */
	public List<ServiceHour> getServiceHoursByCountryIdAndServiceHourStatusId(Long countryId, Long serviceHourStatusId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param countryCode
	 * @param serviceHourStatusCode
	 * @param anHour
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<ServiceHour> getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(String countryCode, String serviceHourStatusCode, Date anHour )	throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la jornada por nombre y país
	 * @param serviceHourName nombre de la jornada
	 * @param countryId identificador del país
	 * @return información de la jornada
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public ServiceHour getServiceHoursByNameAndCountryId(String serviceHourName, Long countryId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los service hour que se sobrelapen con las fechas de inicio o fin enviadas por parametro
	 * @param countryId id del pais en donde se consultan los service hour
	 * @param serviceHourStatusCode estado por el cual se va a filtrar
	 * @param initHour fecha de inicio por la cual se va a filtrar
	 * @param endHour fecha de fin por la cual se va a filtrar
	 * @return Lista de service hour que se sobrelapan sea con la fecha de inicio o fecha de fin enviados por parametro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ServiceHour> getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(Long countryId, Long serviceHourStatusId, Date initHour , Date endHour )	throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta todos los service hours activos diferentes al enviado por parametro
	 * @param countryId
	 * @param statusCode
	 * @param serviceHourToCompare
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<ServiceHour> getAllServiceHoursByCountryIdAndStatusCode(Long countryId,String statusCode,Long serviceHourToCompare) throws DAOServiceException, DAOSQLException;
}
