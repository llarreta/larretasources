package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderMark;

/**
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkOrderMark
 * 
 * Fecha de Creación: 19/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface WorkOrderMarkDAOLocal {
	
    /**
     * Permite crear una orden de servicio
     * @param obj - WorkOrderMark
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkOrderMark(WorkOrderMark workOrderMark) throws DAOServiceException, DAOSQLException;

    /**
     * permite consutlar una orden de servicio por su ID
     * @param id - Long
     * @return WorkOrderMark
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrderMark getWorkOrderMarkByID(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * permite consultar un codigo de WorkOrderMark de servicio por su ID
     * @param id - Long
     * @return code
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public String getCodeWorkOrderMarkByID(Long id) throws DAOServiceException, DAOSQLException;
   
    /**
     * Metodo: permite consultar un WorkOrderMark por su code
     * @param code
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public WorkOrderMark getWorkOrderMarkByCode(String code) throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Metodo: permite consultar las workOrderMark que se tienen que desmarcar en la atencion
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderMark> getWorkOrderMarkByIsUnMarkAttention() throws DAOServiceException, DAOSQLException;
    /**
     * Permite actualizar una orden de servicio
     * @param obj - WorkOrderMark
	 * @return Object
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Object updateWorkOrderMark(WorkOrderMark obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite eliminar una orden de servicio
     * @param obj - WorkOrderMark
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkOrderMark(WorkOrderMark obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consutlar todas las ordenes de servicio
     * @return List<WorkOrderMark>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrderMark> getAll() throws DAOServiceException, DAOSQLException;
    
}
