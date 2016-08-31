package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad SkillConfiguration.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillConfigurationFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto SkillConfiguration
	 * @param obj - SkillConfigurationVO  objeto que encapsula la información de un SkillConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillConfiguration(SkillConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un SkillConfiguration
	 * @param obj - SkillConfigurationVO  objeto que encapsula la información de un SkillConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillConfiguration(SkillConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un SkillConfiguration
	 * @param obj - SkillConfigurationVO  información del SkillConfigurationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSkillConfiguration(SkillConfigurationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un SkillConfiguration por su identificador
	 * @param id - Long identificador del SkillConfiguration a ser consultado
	 * @return objeto con la información del SkillConfigurationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SkillConfigurationVO getSkillConfigurationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SkillConfiguration almacenados en la persistencia
	 * @return List<SkillConfigurationVO> Lista con los SkillConfigurationVO existentes, una lista vacia en caso que no existan SkillConfigurationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SkillConfigurationVO> getAllSkillConfigurations() throws BusinessException;

	/**
	 * 
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
