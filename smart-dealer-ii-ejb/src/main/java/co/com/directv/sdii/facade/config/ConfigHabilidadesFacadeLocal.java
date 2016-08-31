package co.com.directv.sdii.facade.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillTypeVO;
import co.com.directv.sdii.model.vo.SkillVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Habilidades Determinantes y Eliminantes.
 *
 * En la consulta de SKILLS se recupera la relación con SKILL_TYPES.
 *
 * Caso de Uso CFG - 08 - Gestionar Habilidades Determinantes y Eliminantes
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigHabilidadesFacadeLocal {

	/**
     * Metodo: Obtiene una habilidad dado el identificador
     * @param id identificador de la entidad a consultar
     * @return habilidad cuyo identificador coincide con el especificado, nulo en caso que no exista
     * @throws BusinessException en caso de errror al ejecutar la operación
     * @author jjimenezh
     */
    public SkillVO getSkillsByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de SKILLS por SKILL_CODE.
     *
     * @param code
     * @return
     * @throws BusinessException
     */
    public SkillVO getSkillByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de SKILLS por SKILL_NAME.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<SkillVO> getSkillsByName(String name) throws BusinessException;

    /**
     *  Este método retorna una lista de Todas las SKILLS por Tipo.
     *
     * @param typeId
     * @return
     * @throws BusinessException
     */
    public List<SkillVO> getSkillsByType(Long typeId) throws BusinessException;

    /**
     * Este método retorna una lista de Todas las SKILLS.
     *
     * @return
     * @throws BusinessException
     */
    public List<SkillVO> getAll() throws BusinessException;
    
    /**
     * Obtiene una lista de las habilidades regionalizadas y filtradas por el país determinado
     * @param countryId identificador del país por el cual se filtrará la lista
     * @return Lista con las habilidades regionalizadas
     * @throws BusinessException en caso de error al tratar de obtener la lista
     * @author jjimenezh Agergado por control de cambios 2010-04-26
     */
    public List<SkillVO> getAllSkillsByCountryId(Long countryId) throws BusinessException;

    /**
     * Obtiene una lista de las habilidades regionalizadas y filtradas por el país determinado
     * @param skillTypeId id del tipo de habilidad que se desea obtener
     * @param countryId identificador del país por el cual se filtrará la lista
     * @return Lista con las habilidades regionalizadas
     * @throws BusinessException en caso de error al tratar de obtener la lista
     * @author jjimenezh Agregado por control de cambios 2010-04-26
     */
    public List<SkillVO> getAllSkillsBySkillTypeAndCountryId(Long skillTypeId, Long countryId) throws BusinessException;


    /**
     * Este método crea un SKILL.
     *
     * @param obj
     * @throws BusinessException
     */
    public void createSkill(SkillVO obj) throws BusinessException;

    /**
     * Este método actualiza una SKILL.
     *
     * @param obj
     * @throws BusinessException
     */
    public void updateSkill(SkillVO obj) throws BusinessException;

    /**
     * Este método elimina un SKILL.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deleteSkill(SkillVO obj) throws BusinessException;

    /**
     * 
     * @return
     * @throws BusinessException
     */
	public List<SkillTypeVO> getAllSkillTypes()throws BusinessException;
	
	/**
	 * Obtiene una lista de los tipos de habilidad regionalizados por país
	 * @param countryId identificador del país
	 * @return Lista con los tipos de habilidad por país
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author jjimenezh Agregado por control de cambios 2010-04-26
	 */
	public List<SkillTypeVO> getAllSkillTypesByCountryId(Long countryId)throws BusinessException;

}
