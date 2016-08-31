package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.SpearRuleStatus;
import co.com.directv.sdii.model.pojo.WoProcessSource;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkorderStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkorderStatusDAOLocal {

	/**
     * Crea una WorkorderStatus en el sistema
     * @param obj - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkorderStatus(WorkorderStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un workorderstatus con el id especificado
     * @param id - Long
     * @return - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderStatus getWorkorderStatusByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un workorderstatus especificado
     * @param obj - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkorderStatus(WorkorderStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un workorderstatus del sistema
     * @param obj - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkorderStatus(WorkorderStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los workorderstatuss del sistema
     * @return - List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderStatus> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un workorderstatus con el codigo especificado
     * @param code - String
     * @return WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderStatus getWorkorderStatusByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un listado de workorderstatus con el nombre especificado
     * @param code - String
     * @return WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderStatus> getWorkorderStatusByName(String name) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene las workorderstatus por el Ibs6Status
     * @param status - Ibs6Status
     * @return List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkorderStatus> getWorkorderStatusByIBS6Status(Ibs6Status status) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta para obtener un SpearRuleStatus
     * filtrando por el estado anterior y actual de la
     * work order, para la validacion de la maquina de 
     * estados.
     * @param WorkOrder workOrder
     * @param Long statusChange
     * @return SpearRuleStatus
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jlopez
     */
    @Deprecated
    public SpearRuleStatus getWorkorderStatusBySpearRule(WorkOrder workOrder,Long statusChange)throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta para obtener un SpearRuleStatus
     * filtrando por el codigo del estado anterior y actual de la
     * work order, para la validacion de la maquina de 
     * estados.
     * @param WorkOrder workOrder
     * @param String statusChange
     * @return SpearRuleStatus
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jlopez
     */
    public SpearRuleStatus getWorkorderStatusBySpearRuleByCodes(WorkOrder workOrder,String statusChange)throws DAOServiceException, DAOSQLException;

    
	/**
	 * Metodo: Obtiene un estado de work order en SDII por el código de estado de IBS
	 * @param ibs6StatusCode código de estado en IBS
	 * @return Estado de work order en SDII
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @author jjimenezh
	 */
	public WorkorderStatus getWorkorderStatusByIBS6StatusCode(String ibs6StatusCode) throws DAOServiceException, DAOSQLException;
	
	/**
     * Obtiene todos los workorderstatuss del sistema para la lista de work orders
     * @return - List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jnova
     */
    public List<WorkorderStatus> getWorkOrderStatusForDealerTray() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene todos los workorderstatuss del sistema para asigancion de WO
     * @return - List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jnova
     */
    public List<WorkorderStatus> getAllWorkOrdersToAsign() throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta los estado de workorder para la bandeja de dealer
     * @return List<WorkorderStatus> Lista para estados permitidos en el filtro de la bandeja de dealer
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public List<WorkorderStatus> getDealerTrayFilterStatuss()  throws DAOServiceException, DAOSQLException;
    
	/**
	 * Metodo: Obtiene un estado de work order en SDII por el código de estado de IBS
	 * @param ibs6StatusCode código de estado en IBS
	 * @return Estado de work order en SDII
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @author jjimenezh
	 */
    public List<WorkorderStatus> getWorkorderStatusByIBS6StatusCode(Ibs6Status ibs6Status) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta origen del proceso de importacion
     * @param actualStatusCode
     * @return WoProcessSource
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public WoProcessSource getWoProcessSourceByWoStatus(String actualStatusCode,String processSourceSource) throws DAOServiceException,DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta origen de proceso de importacion por id
     * @param id
     * @return WoProcessSource
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public WoProcessSource getWoProcessSourceByProcessId(Long id) throws DAOServiceException, DAOSQLException;
}
