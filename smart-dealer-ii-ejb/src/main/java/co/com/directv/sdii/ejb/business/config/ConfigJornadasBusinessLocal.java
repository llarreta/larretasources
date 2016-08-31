package co.com.directv.sdii.ejb.business.config;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ServiceHourDTO;
import co.com.directv.sdii.model.vo.ServiceHourVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Jornadas.
 *
 * En la consulta de una SERVICE_HOURS se debe recuperar la colección de DEALERS_SERVICE_CAPACITY y
 * una referencia a SERVICE_HOURS_STATUS.
 *
 * Caso de Uso CFG - 01 - Gestionar Jornadas de Servicio
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigJornadasBusinessLocal {

    /**
     * Este método retorna una instancia de SERVICE_HOURS por ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public ServiceHourVO getServiceHoursByID(Long id) throws BusinessException;
    
    /**
     * Este método retorna una instancia de SERVICE_HOURS por ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public ServiceHourDTO getServiceHourDtoByID(Long id) throws BusinessException;

    /**
     * Este método retorna una lista de SERVICE_HOURS por SERVICE_HOURS_NAME.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<ServiceHourVO> getServiceHoursByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de Todas las SERVICE_HOURS.
     *
     * @return
     * @throws BusinessException
     */
    public List<ServiceHourVO> getAll() throws BusinessException;

    /**
     * Obtiene una lista de jornadas dado el identificador de un país
     * @param countryId identificador del país
     * @return lista de jornadas que aplican a un país específico
     * @throws BusinessException en caso de error al tratar de obtener la lista de jornadas regionalizadas
     * @author jjimenezh agregado por control de cambios 2010-04-26
     */
    public List<ServiceHourVO> getAllServiceHoursByCountryId(Long countryId) throws BusinessException;
    
    /**
     * Obtiene una lista de jornadas activas dado el identificador de un país
     * @param countryId identificador del país
     * @return lista de jornadas que aplican a un país específico
     * @throws BusinessException en caso de error al tratar de obtener la lista de jornadas regionalizadas
     * @author jjimenezh agregado por control de cambios 2010-04-26
     */
    public List<ServiceHourVO> getAllActiveServiceHoursByCountryId(Long countryId) throws BusinessException;

    
    /**
     * Obtiene una lista de jornadas dado el identificador de un país
     * @param countryId identificador del país
     * @return lista de jornadas que aplican a un país específico
     * @throws BusinessException en caso de error al tratar de obtener la lista de jornadas regionalizadas
     * @author jjimenezh agregado por control de cambios 2010-04-26
     */
    public List<ServiceHourDTO> getServiceHoursDtoByCountryId(Long countryId) throws BusinessException;

    
    /**
     * Este método retorna una lista de las SERVICE_HOURS que se encuentran
     * en el rango de fecha.
     *
     * @param init
     * @param end
     * @return
     * @throws BusinessException
     */
    public List<ServiceHourVO> getServiceHoursByDate(Date init, Date end) throws BusinessException;

    /**
     * Este método crea un SERVICE_HOURS.
     * @param obj
     * @param userId
     * @throws BusinessException
     */
    public void createServiceHours(ServiceHourVO obj, Long userId) throws BusinessException;

    /**
     * Este método actualiza una SERVICE_HOURS.
     *
     * @param obj
     * @param userId
     * @throws BusinessException
     */
    public void updateServiceHours(ServiceHourVO obj, Long userId) throws BusinessException;

    /**
     * Este método elimina un SERVICE_HOURS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deleteServiceHours(ServiceHourVO obj) throws BusinessException;

	/**
	 * Metodo: Obtiene las jornadas por nombre y país
	 * @param serviceHourName nombre de la jornada
	 * @param countryId identificador del país
	 * @return jornada identificada con el nombre y el país, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public ServiceHourVO getServiceHoursByNameAndCountryId(String serviceHourName, Long countryId)throws BusinessException;

}
