package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkorderCSRStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkorderCSRStatus
 * 
 * Fecha de Creacion: Mar 13, 2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkorderCSRStatusDAOLocal {

	/**
     * Crea una WorkorderCSRStatus en el sistema
     * @param obj - WorkorderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkorderCSRStatus(WorkorderCSRStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un WorkOrderCSRStatus con el id especificado
     * @param id - Long
     * @return - WorkorderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderCSRStatus getWorkorderCSRStatusByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un WorkorderCSRStatus especificado
     * @param obj - WorkorderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkorderCSRStatus(WorkorderCSRStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los WorkorderCSRStatus del sistema
     * @return - List<WorkorderCSRStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderCSRStatus> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un WorkorderCSRStatus con el codigo especificado
     * @param code - String
     * @return WorkorderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderCSRStatus getWorkorderCSRStatusByCode(String code) throws DAOServiceException, DAOSQLException;

}
