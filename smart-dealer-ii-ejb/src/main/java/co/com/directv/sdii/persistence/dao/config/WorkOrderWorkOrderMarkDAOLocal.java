package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.WorkOrderMarkDTO;
import co.com.directv.sdii.model.pojo.WorkOrderWorkOrderMark;

/**
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkOrderWorkOrderMark
 * 
 * Fecha de Creación: 19/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface WorkOrderWorkOrderMarkDAOLocal {
	
    /**
     * Permite crear una orden de servicio
     * @param obj - WorkOrderWorkOrderMark
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkOrderWorkOrderMark(WorkOrderWorkOrderMark workOrderWorkOrderMark) throws DAOServiceException, DAOSQLException;

    /**
     * permite consutlar una orden de servicio por su ID
     * @param id - Long
     * @return WorkOrderWorkOrderMark
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrderWorkOrderMark getWorkOrderWorkOrderMarkByID(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * Permite actualizar una orden de servicio
     * @param obj - WorkOrderWorkOrderMark
	 * @return Object
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Object updateWorkOrderWorkOrderMark(WorkOrderWorkOrderMark obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite eliminar una orden de servicio
     * @param obj - WorkOrderWorkOrderMark
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkOrderWorkOrderMark(WorkOrderWorkOrderMark obj) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite obtener todas las workorderworkordermark activas por un workorder id
     * @param woId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderWorkOrderMark> getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(Long woId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite obtener todas las workorderworkordermark
     * @param woId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderWorkOrderMark> getWorkOrderWorkOrderMarkIsUnMarkAttention(Long woId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite obtener las workordermark activas por un workorder id
     * @param woId 
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderMarkDTO> getWorkOrderMarkDTOIsActive(Long woId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite obtener las workordermark activas por un workorder code
     * @param woId 
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderMarkDTO> getWorkOrderMarkDTOIsActive(String woCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite obtener las workordermark activas por un workorder id y un code de workOrderMark
     * @param woId
     * @param workOrderMarkId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public WorkOrderWorkOrderMark getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark(Long woId,Long workOrderMarkId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite obtener una workordermark activas por una lista de codigos de wo  y un code de workOrderMark.
     * Solo importa que devuelva una de ellas.
     * @param woCodes
     * @param workOrderMarkId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderWorkOrderMark> getWorkOrderWorkOrderMarkIsActiveByWoCodesAndCodeWorkOrderMark(List<String> woCodes,Long workOrderMarkId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite obtener las workordermark activas por un workorder code y un code de workOrderMark
     * @param woId
     * @param codeWorkordermark
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public WorkOrderWorkOrderMark getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark(String woCode,Long workOrderMarkId) throws DAOServiceException, DAOSQLException;

}
