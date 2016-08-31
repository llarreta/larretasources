package co.com.directv.sdii.persistence.dao.config;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoAssignment;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WoAssignment
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WoAssignmentDAOLocal {
	
	/**
     * Crea una WoAssignment en el sistema
     * @param obj - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWoAssignment(WoAssignment obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un woassignment con el id especificado
     * @param id - Long
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WoAssignment getWoAssignmentByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un woassignment especificado
     * @param obj - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWoAssignment(WoAssignment obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un woassignment del sistema
     * @param obj - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWoAssignment(WoAssignment obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los woassignments del sistema
     * @return - List<WoAssignment>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WoAssignment> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene la ultima asignacion dealer de una work order 
     * @param woID - Id de la work order a buscar
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WoAssignment getLastDealerAssignmentByWoId(Long woID) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: <Descripcion>
     * @param woID
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public WoAssignment getLastDealerAssignmentByWoIdWithoutWo(Long woID) throws DAOServiceException, DAOSQLException;
    
    /**
     * Consutla la �ltima programaci�n de la asignaci�n.
     * @param woID - Long
     * @return WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public WoAssignment getLastProgramByWoId(Long woID) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta la ultima programacion de un dealer especificado
     * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
     * @param dealerId
     * @return WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WoAssignment getLastProgramByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;

    /**
     * 
     * Metodo: Operacion para consultar una WoAssignment
     * filtrando por el id de la Work Order y por
     * el id del Dealer.
     * @param pWoId Long
     * @param pDealerId Long
     * @return WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public WoAssignment getWoAssignmentByAssignmentCrew(Long pWoId,Long pDealerId)throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Retorna el numero de work orders
     * asignadas a una cuadrilla segun el dealer
     * y que se encuentran en estado:
     * WORKORDER_STATUS_SCHEDULED, WORKORDER_STATUS_RESCHEDULED, 
     * WORKORDER_STATUS_REASSIGN, WORKORDER_STATUS_ASSIGN y 
     * WORKORDER_STATUS_RALIZED.
     * @param pDealerId
     * @return Object
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public Object getAmountWoAssigment(WoAssignment woAssignment)throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene una lita de las asignaciones relacionadas a una orden de trabajo específica.
     * @param workOrderIds listo con los identificadores de las órdenes de trabajao
     * @return una lista con los las asignaciones realizadas a una orden, nulo en otro caso.
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public List<WoAssignment> getLastDealerAssignmentsByWoIds(List<Long> workOrderIds)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los woassigment de una lista de work_orders que se encuentran activos
	 * @param workOrderIds
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WoAssignment> getLastDealerAssignmentsByWoIdsAndStatusActive(List<Long> workOrderIds)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene las asignaciones sobre wo's relacionadas con un programa específico
	 * @param programId identificador del programa
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Long countWoAssignmenstByProgramId(Long programId) throws DAOServiceException,DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta la primera asignacion de una WO a un dealer
	 * @param woId identificador de la WO
	 * @param dealerId  identificador del dealer
	 * @return Date Primera fecha de asignacion por WO y dealer 
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author jnova
	 */
	public Date getFirstAsigmentDateByDealerIdAndWoId(Long woId , Long dealerId) throws DAOServiceException,DAOSQLException;
	
	/**
     * Obtiene la ultima asignacion dealer de una work order 
     * @param woID - Id de la work order a buscar
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WoAssignment getWorkOrdersAssignment(Long pWoId) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Obtiene la ultima asignacion de una wo que tenga asignada cuadrilla 
     * @param pWoId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public WoAssignment getWorkOrdersCrewActiveAssignment(Long pWoId) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Obtiene la ultima asignacion de una wo que tenga asignada programa 
     * @param pWoId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public WoAssignment getWorkOrdersProgramActiveAssignment(Long pWoId) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Obtiene las asignaciones de WO que se encuentran en los estados pasados por parametro y asignadas a
     * la cuadrilla pasada por parametro
     * @param crewId identificador de la cuadrilla
     * @param woStatussCode Lista de estados por los cuales se van a buscar las WO
     * @return Lista de asignaciones de la cuadrilla en los estados pasados por parametro
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public List<WoAssignment> getWorkOrdersCrewActiveAssignmentByCrewIdAndWoStatus(Long crewId,List<String> woStatussCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta las ultimas asignacion de las ultimas dos workorder de un customer ordenadas por fecha de finalizacion
     * que se encuentran en estado finalizado 
     * @param customerCode codigo ibs del cliente
     * @return List<WoAssignment> Lista de asignaciones de las WO del cliente en estado finalizado
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public List<WoAssignment> getWorkOrdersByCustomerCodeAndStatusOrderByFinalizationDate(String customerCode ) throws DAOServiceException, DAOSQLException ;
    
	/**
     * Obtiene la ultima asignacion dealer de una work order 
     * ordenada por la ultima fecha de asignacion
     * @param woID - Id de la work order a buscar
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WoAssignment getLastDealerAssignmentByAssignmentDate(Long woID) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene todas las asignaciones de una WO ordenadas por fecha
     * @param pWoId - Id de la work order a buscar
     * @return - List<WoAssignment>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * */
    public List<WoAssignment> getWorkOrderAssignments(Long pWoId) throws DAOServiceException, DAOSQLException;

}
