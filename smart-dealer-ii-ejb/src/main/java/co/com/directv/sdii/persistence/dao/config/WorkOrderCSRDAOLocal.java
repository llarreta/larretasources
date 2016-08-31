package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkOrderCSR
 * 
 * Fecha de Creacion: Mar 09, 2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkOrderCSRDAOLocal {
	
	 /**
     * Crea una WorkOrderCSR en el sistema
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkOrderCSR(WorkOrderCSR obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un workorderCSR con el id especificado
     * @param id - Long
     * @return - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrderCSR getWorkOrderCSRByID(Long id) throws DAOServiceException, DAOSQLException;
    
    public WorkOrderCSR getWorkOrderCSRByWoCodePending(String woCode) throws DAOServiceException, DAOSQLException;
    
    public WorkOrderCSR getWorkOrderCSRByWoCode(String woCode) throws DAOServiceException, DAOSQLException;
    
    public WorkOrderCSR getWorkOrderCSRByWoCode(String woCode,String workorderCSRStatusCode) throws DAOServiceException, DAOSQLException;
    /**
     * Actualiza un workorderCSR especificado
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWorkOrderCSR(WorkOrderCSR obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un workorderCSR del sistema
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkOrderCSR(WorkOrderCSR obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los workorderCSR del sistema
     * @return - List<WorkOrderCSR>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrderCSR> getAll() throws DAOServiceException, DAOSQLException;

    public void traceAssignmentWorkOrderCSR(Long id,String trace) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: permite consultar si la workOrder es de CSR para cambiar el process_source
     * @param woCode
     * @param actualStatusCode
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public boolean getCountWorkOrderCSRByWoCodeStatusReSchedule(String woCode,String actualStatusCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Permite consultar si la workOrder es de CSR para cambiar el contact person
     * @param woCode
     * @param actualStatusCode
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public WorkOrderCSR getWorkOrderCSRByWoCodeStatusReSchedule(String woCode,String actualStatusCode) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Consulta todas las WO de CSR exitosas por pais y fechas.
     * @param countryCode
     * @param fromDate
     * @param toDate
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */    
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(ReportsSucceedWorkOrderFilterDTO filter, RequestCollectionInfo requestInfo) throws DAOServiceException, DAOSQLException;

}
