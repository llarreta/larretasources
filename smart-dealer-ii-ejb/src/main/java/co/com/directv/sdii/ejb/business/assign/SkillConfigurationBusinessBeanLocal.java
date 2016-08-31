package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad SkillConfiguration.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillConfigurationBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SkillConfigurationVO
	 * @param obj objeto que encapsula la información de un SkillConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillConfiguration(SkillConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SkillConfigurationVO
	 * @param obj objeto que encapsula la información de un SkillConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillConfiguration(SkillConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SkillConfigurationVO
	 * @param obj información del SkillConfigurationVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSkillConfiguration(SkillConfigurationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SkillConfigurationVO por su identificador
	 * @param id identificador del SkillConfigurationVO a ser consultado
	 * @return objeto con la información del SkillConfigurationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SkillConfigurationVO getSkillConfigurationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SkillConfigurationVO almacenados en la persistencia
	 * @return Lista con los SkillConfigurationVO existentes, una lista vacia en caso que no existan SkillConfigurationVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SkillConfigurationVO> getAllSkillConfigurations() throws BusinessException;

	/**
	 * Metodo: Obtiene las configuraciones de habilidades dados los criterios de filtro
	 * @param countryCode código del país
	 * @param serviceTypeCode código del tipo de servicio
	 * @param skillModeTypeCode código del modo de ejecución de las habilidades
	 * @return Lista con las configuraciones de ejecución de las habilidades
	 * @throws BusinessException En caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public List<SkillConfigurationVO> getSkillConfigurationsByCriteria(String countryCode, String serviceTypeCode, String skillModeTypeCode) throws BusinessException;

	/**
	 * Metodo: Permite consultar las configuraciones de todas las habilidades filtradas de acuerdo
	 * a los parámetros
	 * @param countryId identificador del país por el que se quiere buscar
	 * @param serviceTypeId tipo de servicio por el que se quiere buscar
	 * @return listado de configuraciones de habilidades
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public List<SkillConfigurationVO> getSkillConfigurationsByCountryIdAndCategoryId(
			Long countryId, Long serviceTypeId) throws BusinessException;

	/**
	 * Metodo: persiste los cambios a un grupo de configuraciones de habilidades
	 * La actualización hace parte de una sola transacción, es decir, o se actualizan todos
	 * o no se actualiza ninguno.
	 * @param skillConfigurations listado de configuraciones de habilidades que se desean actualizar
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void updateSkillConfigurations(List<SkillConfigurationVO> skillConfigurations) throws BusinessException;

}
