package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SkillEvaluationType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad SkillEvaluationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillEvaluationTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un SkillEvaluationType
	 * @param obj objeto que encapsula la información de un SkillEvaluationType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillEvaluationType(SkillEvaluationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un SkillEvaluationType
	 * @param obj objeto que encapsula la información de un SkillEvaluationType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillEvaluationType(SkillEvaluationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SkillEvaluationType
	 * @param obj información del SkillEvaluationType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSkillEvaluationType(SkillEvaluationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un SkillEvaluationType por su identificador
	 * @param id identificador del SkillEvaluationType a ser consultado
	 * @return objeto con la información del SkillEvaluationType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SkillEvaluationType getSkillEvaluationTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los SkillEvaluationType almacenados en la persistencia
	 * @return Lista con los SkillEvaluationType existentes, una lista vacia en caso que no existan SkillEvaluationType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SkillEvaluationType> getAllSkillEvaluationTypes() throws DAOServiceException, DAOSQLException;


}