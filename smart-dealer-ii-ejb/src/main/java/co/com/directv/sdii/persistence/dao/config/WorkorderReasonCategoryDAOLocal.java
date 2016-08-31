package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkorderReasonCategory;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkorderReasonCategory
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkorderReasonCategoryDAOLocal {
	
	/**
     * Crea una WorkorderReasonCategory en el sistema
     * @param obj - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkorderReasonCategory(WorkorderReasonCategory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un workorderreasoncategory con el id especificado
     * @param id - Long
     * @return - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderReasonCategory getWorkorderReasonCategoryByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un workorderreasoncategory especificado
     * @param obj - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkorderReasonCategory(WorkorderReasonCategory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un workorderreasoncategory del sistema
     * @param obj - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkorderReasonCategory(WorkorderReasonCategory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los workorderreasoncategorys del sistema
     * @return - List<WorkorderReasonCategory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderReasonCategory> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Obtiene WorkorderReasonCategory por Id
     * @param woReasonTypeId - Long
     * @return List<WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    public List<WorkorderReasonCategory> getWOReasonCategoriesByReasonTypeId(Long woReasonTypeId)throws DAOServiceException, DAOSQLException;

}
