package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkOrderAgenda
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkOrderAgendaDAOLocal {
	
	 /**
     * Crea una WorkOrderAgenda en el sistema
     * @param obj - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkOrderAgenda(WorkOrderAgenda obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un workorderagenda con el id especificado
     * @param id - Long
     * @return - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrderAgenda getWorkOrderAgendaByID(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * Consulta la �ltima agendaci�n de una WorkOrder
     * @param idWo - Long
     * @return WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public WorkOrderAgenda getLastWorkOrderAgendaByWoID(Long idWo) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un workorderagenda especificado
     * @param obj - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkOrderAgenda(WorkOrderAgenda obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un workorderagenda del sistema
     * @param obj - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkOrderAgenda(WorkOrderAgenda obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los workorderagendas del sistema
     * @return - List<WorkOrderAgenda>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrderAgenda> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Retorna un listado de WorkOrderAgenda, filtrando por
     * el dealerId y WorkOrderId por medio de la WorkOrder Assignment
     * @param woId Long
     * @param dealerId Long
     * @return List<WorkOrderAgenda>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public List<WorkOrderAgenda> getWorkOrderAgendaByWoAssignment(Long woId,Long dealerId)	throws DAOServiceException, DAOSQLException;
    
    /**
     * Consulta la ultima agenda asignada a un cliente por estado de WO
     * @param customerId Long id del cliente
     * @param woStatusCode estado de la WO
     * @return WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jnova
     */
    public WorkOrderAgenda getLastWorkOrderAgendaByCustomerIdAndWoStatus(Long customerId , String woStatusCode) throws DAOServiceException, DAOSQLException;
    
    /**
	 * 
	 * Metodo: Consulta la primera agenda de una WO a un dealer
	 * @param woId identificador de la WO
	 * @param dealerId  identificador del dealer
	 * @return Date Primera fecha de agenda por WO y dealer 
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author jnova
	 */
	public WorkOrderAgenda getFirstAgendaByDealerIdAndWoId(Long woId , Long dealerId) throws DAOServiceException,DAOSQLException;
    
    
    
    /**  
     * Obtiene las workorderagenda donde de una workorderassignment especifica, teniendo en cuenta que su estado sea activo
     * @param woAssignmentId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrderAgenda getLastWorkOrderAgendaByWoAssignmentId(Long woAssignmentId)throws DAOServiceException, DAOSQLException ;
    
    /**
     * 
     * Metodo: Consulta las ultimas tres agendas de una WO
     * @param idWo id de la worlorder de la cual se estan consultando las agendas
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderAgenda> getLastWorkOrderAgendaByWoIDOrderByAgendaDate(Long idWo)throws DAOServiceException, DAOSQLException;

    /**
     * 
     * Metodo: retorna los agendamientos de una
     * WorkOrder por Dealer
     * @param woId Long
     * @param dealerId Long
     * @return List<WorkOrderAgenda>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
    public List<WorkOrderAgenda> getWorkOrderAgendaByWoDealer(Long woId, Long dealerId) throws DAOServiceException, DAOSQLException;
    
}
