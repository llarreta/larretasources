package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Skill;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Skill
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillDAOLocal {
	
	/**
     * Crea una Skill en el sistema
     * @param obj - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createSkill(Skill obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un skill con el id especificado
     * @param id - Long
     * @return - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Skill getSkillByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Consulta Skill por código
     * @param code - String
     * @return Skill
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public Skill getSkillByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Consulta Skill por nombre
     * @param name - String
     * @return List<Skill>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<Skill> getSkillByName(String name) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Consulta Skill por tipo de Skill
     * @param skillTypeId - Long
     * @return List<Skill>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<Skill> getSkillByTypeId(Long skillTypeId) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un skill especificado
     * @param obj - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateSkill(Skill obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un skill del sistema
     * @param obj - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteSkill(Skill obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los skills del sistema
     * @return - List<Skill>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Skill> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de las habilidades regionalizadas y filtradas por el país determinado
     * @param countryId identificador del país por el cual se filtrará la lista
     * @return Lista con las habilidades regionalizadas
     * @throws BusinessException en caso de error al tratar de obtener la lista
     * @author jjimenezh Agergado por control de cambios 2010-04-26
     */
    @Deprecated
    public List<Skill> getAllSkillsByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Permite consultar los Skill por el tipo y el pa�s, ordenados por Nombre
     * @param skillTypeId
     * @param countryId
     * @return List<Skill>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jjimenezh Agergado por control de cambios 2010-04-26
     */
    @Deprecated
    public List<Skill> getAllSkillsBySkillTypeAndCountryId(Long skillTypeId, Long countryId)throws DAOServiceException, DAOSQLException;
    
    /**
     * Permite consultar los Skill por el tipo y el pa�s, ordnados por ID
     * @param skillTypeId - Long
     * @param countryId - Long
     * @return List<Skill>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino 2010/05/13
     */
    @Deprecated
    public List<Skill> getAllSkillsBySkillTypeAndCountryIdOrderById(Long skillTypeId, Long countryId)throws DAOServiceException, DAOSQLException;


}
