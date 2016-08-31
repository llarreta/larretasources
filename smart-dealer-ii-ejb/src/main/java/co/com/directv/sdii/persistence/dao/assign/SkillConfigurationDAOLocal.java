package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SkillConfiguration;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad SkillConfiguration
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillConfigurationDAOLocal {

	/**
	 * Metodo:  persiste la información de un SkillConfiguration
	 * @param obj objeto que encapsula la información de un SkillConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillConfiguration(SkillConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un SkillConfiguration
	 * @param obj objeto que encapsula la información de un SkillConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillConfiguration(SkillConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SkillConfiguration
	 * @param obj información del SkillConfiguration a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSkillConfiguration(SkillConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un SkillConfiguration por su identificador
	 * @param id identificador del SkillConfiguration a ser consultado
	 * @return objeto con la información del SkillConfiguration dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SkillConfiguration getSkillConfigurationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los SkillConfiguration almacenados en la persistencia
	 * @return Lista con los SkillConfiguration existentes, una lista vacia en caso que no existan SkillConfiguration en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SkillConfiguration> getAllSkillConfigurations() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las configuraciones de habilidades dados los criterios de filtro
	 * @param countryCode código del país
	 * @param serviceTypeCode código del tipo de servicio
	 * @param skillModeTypeCode código del modo de ejecución de las habilidades
	 * @return Lista con las configuraciones de ejecución de las habilidades
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author jjimenezh
	 */
	public List<SkillConfiguration> getSkillConfigurationsByCriteria(String countryCode, String serviceTypeCode, String skillModeTypeCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar las configuraciones de todas las habilidades filtradas de acuerdo
	 * a los parámetros
	 * @param countryId identificador del país por el que se quiere buscar
	 * @param serviceTypeId tipo de servicio por el que se quiere buscar
	 * @return listado de configuraciones de habilidades
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */
	public List<SkillConfiguration> getSkillConfigurationsByCountryIdAndCategoryId(
			Long countryId, Long serviceTypeId) throws DAOServiceException, DAOSQLException;


}