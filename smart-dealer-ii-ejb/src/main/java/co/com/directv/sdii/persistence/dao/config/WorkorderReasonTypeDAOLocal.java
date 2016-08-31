package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkorderReasonType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkorderReasonType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkorderReasonTypeDAOLocal {
	
	/**
     * Crea un WorkorderReasonType en el sistema
     * @param obj - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkorderReasonType(WorkorderReasonType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un workorderreasontype con el id especificado
     * @param id - Long
     * @return - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderReasonType getWorkorderReasonTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un workorderreasontype especificado
     * @param obj - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkorderReasonType(WorkorderReasonType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un workorderreasontype del sistema
     * @param obj - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkorderReasonType(WorkorderReasonType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los workorderreasontypes del sistema
     * @return - List<WorkorderReasonType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderReasonType> getAll() throws DAOServiceException, DAOSQLException;

}
