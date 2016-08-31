package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SkillType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad SkillType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillTypeDAOLocal {
	
	 /**
     * Crea un SkillType en el sistema
     * @param obj - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createSkillType(SkillType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un skilltype con el id especificado
     * @param id - Long
     * @return - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public SkillType getSkillTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un skilltype especificado
     * @param obj - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateSkillType(SkillType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un skilltype del sistema
     * @param obj - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteSkillType(SkillType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los skilltypes del sistema
     * @return - List<SkillType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<SkillType> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
	 * Obtiene una lista de los tipos de habilidad regionalizados por país
	 * @param countryId identificador del país
	 * @return Lista con los tipos de habilidad por país
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author jjimenezh Agregado por control de cambios 2010-04-26
	 */
	public List<SkillType> getAllSkillTypesByCountryId(Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene la habilidad por su c�digo
	 * @param code - String
	 * @return List<SkillType>
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author gfandino - 2010-05-13
	 */
	public List<SkillType> getSkillTypeByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene la habilidad por su c�digo
	 * @param code - String
	 * @param countryId - Long
	 * @return SkillType
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author gfandino - 2010-05-13
	 */
	public SkillType getSkillTypeByCodeAndCountry(String code, Long countryId)throws DAOServiceException, DAOSQLException;


}
