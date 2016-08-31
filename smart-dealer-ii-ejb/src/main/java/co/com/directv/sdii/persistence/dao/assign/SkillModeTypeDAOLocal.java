package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SkillModeType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad SkillModeType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillModeTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un SkillModeType
	 * @param obj objeto que encapsula la información de un SkillModeType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillModeType(SkillModeType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un SkillModeType
	 * @param obj objeto que encapsula la información de un SkillModeType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillModeType(SkillModeType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SkillModeType
	 * @param obj información del SkillModeType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSkillModeType(SkillModeType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un SkillModeType por su identificador
	 * @param id identificador del SkillModeType a ser consultado
	 * @return objeto con la información del SkillModeType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SkillModeType getSkillModeTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los SkillModeType almacenados en la persistencia
	 * @return Lista con los SkillModeType existentes, una lista vacia en caso que no existan SkillModeType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SkillModeType> getAllSkillModeTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene el modo de ejecución de una habilidad por su código
	 * @param skillModeTypeCode código del modo de ejecución de la habilidad
	 * @return modo de ejecución de la habilidad
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public SkillModeType getSkillModeTypeByCode(String skillModeTypeCode)throws DAOServiceException, DAOSQLException;


}