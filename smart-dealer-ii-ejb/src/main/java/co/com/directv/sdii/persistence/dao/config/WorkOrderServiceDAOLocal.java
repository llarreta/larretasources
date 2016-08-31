package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WorkOrderServiceResponse;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkOrderService
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkOrderServiceDAOLocal {
	
	/**
     * Crea una WorkOrderService en el sistema
     * @param obj - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public void createWorkOrderService(WorkOrderService obj) throws DAOServiceException, DAOSQLException;

	 /**
     * Obtiene un workorderservice con el id especificado
     * @param id - Long
     * @return - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrderService getWorkOrderServiceByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un workorderservice especificado
     * @param obj - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkOrderService(WorkOrderService obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un workorderservice del sistema
     * @param obj - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkOrderService(WorkOrderService obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los workorderservices del sistema
     * @return - List<WorkOrderService>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrderService> getAll() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene WorkOrderServices por WorkOrder, Service y SerialNumber
	 * @param workOrderId - Long
	 * @param serviceId - Long
	 * @param decoderSerialNumber - String
	 * @return List<WorkOrderService>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<WorkOrderService> getWOServiceByWorkorderIdAndServiceIdAndSerailNumber(Long workOrderId, Long serviceId, String decoderSerialNumber) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Permite consultar las WOService asociadas a una WO
	 * @param workOrderId - Long
	 * @param RequestCollectionInfo requestCollectionInfo
	 * @return WorkOrderServiceResponse
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public WorkOrderServiceResponse getWOServiceByWorkorderId(Long workOrderId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	 /**
     * 
     * Metodo: Retorna un listado de Servicios de una work order
     * a partir de la relacion en work order service
     * @param workOrderId
     * @return List<Service>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author Joan Lopez
     */
	public List<Service> getServicesOfWorkOrderServiceByWorkorderId(Long workOrderId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Borra los servicios relacionados con una work order
	 * @param workorderId identificador de la work order
	 * @throws DAOServiceException en caso de error al realizar el cambio
	 * @throws DAOSQLException en caso de error al realizar el cambio
	 * @author jjimenezh
	 */
	public void deleteWorkOrderServiceByWoId(Long workorderId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los servicios asociados a una WO
	 * @param workorderId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<WorkOrderService> getWorkOrderServiceByWoId(Long workorderId)throws DAOServiceException, DAOSQLException;
	
	

	/**
	 * 
	 * @return
	 */
	public List<WorkOrderService> getWorkOrderServiceByWoIds(List<Long> woIDs) throws DAOServiceException, DAOSQLException;
	
}