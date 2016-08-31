package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderReasonCategory;
import co.com.directv.sdii.model.pojo.WorkorderReasonType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkorderReason
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkorderReasonDAOLocal {
	
	/**
     * Crea una WorkorderReason en el sistema
     * @param obj - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkorderReason(WorkorderReason obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un workorderreason con el id especificado
     * @param id - Long
     * @return - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderReason getWorkorderReasonByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Obtiene una WorkorderReason por su código
     * @param code - String
     * @return WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderReason getWorkorderReasonByCode(String code) throws DAOServiceException, DAOSQLException;
    
    /**
     * Actualiza un workorderreason especificado
     * @param obj - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkorderReason(WorkorderReason obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un workorderreason del sistema
     * @param obj - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkorderReason(WorkorderReason obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los workorderreasons del sistema
     * @return - List<WorkorderReason>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderReason> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Obtiene WorkorderReason por su nombre
     * @param name - String
     * @return List<WorkorderReason>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderReason> getWorkorderReasonByName(String name) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Obtiene WorkorderReasons por su Categoría
     * @param category - WorkorderReasonCategory
     * @return List<WorkorderReason>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderReason> getWorkorderReasonByCategory(WorkorderReasonCategory category) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Obtiene WorkorderReasons por su categoría y tipo
     * @param category - WorkorderReasonCategory
     * @param type - WorkorderReasonType
     * @return List<WorkorderReason>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderReason> getWorkorderReasonByCategoryType(WorkorderReasonCategory category, WorkorderReasonType type) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las WorkorderReasons que se encuentran asociadas 
	 * @param id - Long
	 * @return List<WorkorderReason>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkorderReason> getWorkOrderReasonByWoStatus(Long id)throws DAOServiceException, DAOSQLException;


	/**
	 * Metodo: Obtiene las WorkorderReasons que se encuentran asociadas a un estado y en un estado especifico de
	 * solve by CI
	 * @param id - Long
	 * @param String isSolveByCI
	 * @return List<WorkorderReason>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public List<WorkorderReason> getWorkOrderReasonByWoStatusAndBySolveByCI(Long id,String isSolveByCI)throws DAOServiceException, DAOSQLException;


}
