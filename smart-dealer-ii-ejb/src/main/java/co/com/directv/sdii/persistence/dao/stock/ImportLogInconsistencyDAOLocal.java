package co.com.directv.sdii.persistence.dao.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.InconsistencyStatus;
import co.com.directv.sdii.model.pojo.collection.ImportLogInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ImportLogInconsistency
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */ 
@Local
public interface ImportLogInconsistencyDAOLocal {

	/**
	 * Metodo:  persiste la información de un ImportLogInconsistency
	 * @param obj objeto que encapsula la información de un ImportLogInconsistency
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ImportLogInconsistency
	 * @author gfandino
	 */
	public void createImportLogInconsistency(ImportLogInconsistency obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ImportLogInconsistency
	 * @param obj objeto que encapsula la información de un ImportLogInconsistency
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ImportLogInconsistency
	 * @author gfandino
	 */
	public void updateImportLogInconsistency(ImportLogInconsistency obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLogInconsistency
	 * @param obj información del ImportLogInconsistency a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ImportLogInconsistency
	 * @author gfandino
	 */
	public void deleteImportLogInconsistency(ImportLogInconsistency obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ImportLogInconsistency por su identificador
	 * @param id identificador del ImportLogInconsistency a ser consultado
	 * @return objeto con la información del ImportLogInconsistency dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ImportLogInconsistency por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ImportLogInconsistency por ID
	 * @author gfandino
	 */
	public ImportLogInconsistency getImportLogInconsistencyByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogInconsistency almacenados en la persistencia
	 * @return Lista con los ImportLogInconsistency existentes, una lista vacia en caso que no existan ImportLogInconsistency en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ImportLogInconsistency
	 * @author gfandino
	 */
	public List<ImportLogInconsistency> getAllImportLogInconsistencys() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de ImportLogInconsistency almacenados en la persistencia dentro del rango de fechas
	 * @param inic - Date fecha inicial de la consulta
	 * @param end - Date fecha final de la consulta
	 * @return List<ImportLogInconsistency> correspondiente al rango de fechas especificado, vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ImportLogInconsistency por rango de fechas
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ImportLogInconsistency por rango de fechas
	 * @author gfandino
	 */
	public List<ImportLogInconsistency> getImportLogInconsitencysByCreationDate(
			Date inic, Date end)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de ImportLogInconsistency almacenados en la persistencia dado un estado
	 * @param idInconsitencysStatus - Long estado que se va a consultar
	 * @return List<ImportLogInconsistency> correspondiente al idInconsitencysStatus definido
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ImportLogInconsistency por su estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ImportLogInconsistency por su estado
	 * @author gfandino
	 */
	public List<ImportLogInconsistency> getImportLogInconsitencysByStatus(Long idInconsitencysStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la informacion de todos los ImportLogInconsistency asociados a un registro de importacion
	 * INV-24
	 * @param importLogId el identificador del registro de importacion
	 * @return Lista con los ImportLogInconsistency existentes, una lista vacia en caso que no existan ImportLogInconsistency en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ImportLogInconsistency
	 * @author garciniegas
	 */
	public List<ImportLogInconsistency> getImportLogInconsistenciesByImportLogId( Long importLogId ) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Crea una lista de registros ImportLogInconsistency asociados a un registro de importacion
	 * INV-24
	 * @param  inconsistencyList Una lista con los ImportLogInconsistency a registrar
	 * @param importLogId el identificador del registro de importacion
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @author garciniegas
	 */ 
	public void createImportLogInconsistenciesList( List<ImportLogInconsistency>inconsistencyList,ImportLog importLogId ) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar las inconsistencias asociadas a un registro de impostación
	 * @param importLog - Long registro de importación que se va a consultar
	 * @return List<ImportLogInconsistency> correspondiente al registro de importación consultado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de consultar inconsistencias de registros de importación
	 * @throws DAOSQLException en caso de error al tratar de consultar inconsistencias de registros de importación
	 * @author gfandino
	 */
	public List<ImportLogInconsistency> getImporLogInconsistencysByImportLog (Long importLog)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: cierra el estado de una lista de objetos ImportLogInconsistency asociados a un registro de importacion
	 * INV-17
	 * @param  inconsistencyList Una lista con los ImportLogInconsistency a cerrar
	 * @param InconsistencyStatus statusClose Estado cerrado de una inconsistencia
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @author garciniegas
	 */ 
	public void closeImportLogInconsistencies( List<ImportLogInconsistency>inconsistencyList , InconsistencyStatus statusClose)throws  DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna lista paginada de las inconsistencias dado un registro de importación.
	 * @param importLogId
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public ImportLogInconsistencyResponse getImportLogInconsistenciesByImportLogId(
			Long importLogId, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException;

	public Long countByStatus(Long importLogId, InconsistencyStatus inconsistencyStatus) throws DAOServiceException, DAOSQLException;

	public List<ImportLogInconsistency> getImportLogInconsistenciesByImportLogItemId(Long importLogItem) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * 
	 * Metodo: Borra las inconsistencias de un registro de importación
	 * @param importLogId
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public void deleteImportLogInconsistencysForImportLog(Long importLogId) throws DAOServiceException, DAOSQLException;

}