package co.com.directv.sdii.persistence.dao.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.Supplier;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectImportLogToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ImportLog
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogDAOLocal {

	/**
	 * Metodo:  persiste la información de un ImportLog
	 * @param obj objeto que encapsula la información de un ImportLog
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createImportLog(ImportLog obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ImportLog
	 * @param obj objeto que encapsula la información de un ImportLog
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImportLog(ImportLog obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLog
	 * @param obj información del ImportLog a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteImportLog(ImportLog obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ImportLog por su identificador y por el Id del
	 * Operador Logistico al cual pertenece.
	 * 
	 * @param id
	 * @param LogisticOpId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public ImportLog getImportLogByIDAndByLogisticOp(Long id, Long LogisticOpId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLog almacenados en la persistencia
	 * @return Lista con los ImportLog existentes, una lista vacia en caso que no existan ImportLog en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ImportLog> getAllImportLogs() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLog almacenados en la persistencia
	 * @param logisticOpId - Long identificador del operador logistico
	 * @return Lista con los ImportLog existentes, una lista vacia en caso que no existan ImportLog en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author Jimmy Vélez Muñoz
	 */
	public List<ImportLog> getAllImportLogsByLogisticOp(Long logisticOpId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de ImportLog almacenados en la persistencia por Proveedor, Orden de Compra y Operador Logistico 
	 * @param supplier - Supplier proveedor por el que se va a consultar
	 * @param purchaseOrder - String orden de compra por la que se va a consultar
	 * @param logisticOpId - Long identificador del país
	 * @return ImportLog correspondiente al proveedor y la orden de compra
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la de consulta de ImportLog por Supplier y purchaseOrder 
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author Jimmy Vélez Muñoz
	 */
	public ImportLog getImportLogBySupplierPurchaseOrderAndLogisticOp(Supplier supplier,
			String purchaseOrder,Long logisticOpId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los registros de importación dado el código
	 * @param statusCode código de estado de registro de importación
	 * @param logisticOpId - Long identificador del operador logistico
	 * @return Lista con los registros de importación que se encuentran en un estado específico
	 * @throws DAOServiceException En caso de error al ejecutar la operación
	 * @throws DAOSQLException En caso de error al ejecutar la operación
	 * @author Jimmy Vélez Muñoz
	 */
	public List<ImportLog> getImportLogsByStatusCodeAndLogisticOp(String statusCode,Long logisticOpId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: UC_INV_02 Permite consultar los registros de importacion y compra
	 * @param ModifyImportLogDTO Criterios de busqueda para los registro de importacion y compra
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ImportLogResponse,List<ImportLog> lista de registros que cumplen con el filtro 
	 * @throws DAOServiceException En caso de error al ejecutar la operación
	 * @throws DAOSQLException En caso de error al ejecutar la operación
	 * @author jnova
	 */
	public ImportLogResponse getImportLogByCriteria(ModifyImportLogDTO modifyImportLogCriteria, Long countryId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: UC_INV_02 Permite consultar los registros de importacion y compra, se tiene la validacion
	 * para que un registro que haya sido eliminado se pueda volver a crear en el sistema
	 * @param ModifyImportLogDTO Criterios de busqueda para los registro de importacion y compra
	 * @param countryId
	 * @param importStatus, Lista con los estados que son permitidos para crear el registro nuevamente
	 * @return Long,  cantidad de registros encontrados 
	 * @throws DAOServiceException En caso de error al ejecutar la operación
	 * @throws DAOSQLException En caso de error al ejecutar la operación
	 * @author 
	 */
	public Long getImportLogByPurchaseOrderAndStatus(ModifyImportLogDTO modifyImportLogCriteria, Long countryId, List<String>importStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo> UC_INV_17 Permite consultar los registros de importacion y compra segun los filtros del caso de uso Inv 17
	 * @param modifyImportLogCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public ImportLogResponse getImportLogInconsistencesByCriteria(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	

    /**
     * Método: UC_INV_125 Obtiene los registros de importación que se encuentran en estado Enviado, Inconsistente o Confirmado parcial
     * según los criterios de entrada.
     *  
     * @param dealerId
     * @param elementTypeId
     * @param serialCode
     * @param impLogId
     * @param requestCollInfo
     * @return ImportLogResponse
     * @throws DAOServiceException
     * @throws DAOSQLException
     * 
     * @author Jimmy Vélez Muñoz
     */
    public ImportLogResponse getImportLogsToConfirm(Long dealerId, Long elementTypeId, String serialCode, Long impLogId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

    /**
     * Método: UC_INV_125 Obtiene los registros de importación que se encuentran en estado Enviado, Inconsistente o Confirmado parcial
     * según los criterios de entrada.
     * 
     * @param dealerId
     * @param impLogId
     * @param requestCollInfo
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     * 
     * @author Jimmy Vélez Muñoz
     */
    public ImportLogResponse getImportLogsToConfirm(Long dealerId, Long impLogId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: UC_INV_20 Consulta el registro de importacion asociado al identificador de Element enviado 
	 * @param elementId Identificador del Element
	 * @return ImportLog el objeto ImportLog asociado al elementId
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public ImportLog getImportLogByElementId(Long elementId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: UC_INV_24 Consulta registros de importacion basados en un filtro con el identificador 
	 * y la fecha de creacion 
	 * @param importLogId El identificador del registro de importacion
	 * @param creationDate La fecha de creacion del registro de importacion
	 * @param logisticOpId - Long identificador del operador logistico
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ImportLogResponse, una lista de objetos ImportLog asociado al filtro
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author Jimmy Vélez Muñoz
	 */
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(Long importLogId,Date creationDate, Long logisticOpId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los registros de importación dado su estado y identificador del registro, y rango fecha creación
	 * @param idImportLog - Long identificador de la ImprotLog; id en cero si no hace parte d ela consulta
	 * @param inicDate - Date fecha inicial de consulta; nulo si no hace parte de la consulta
	 * @param endDate - Date fecha final de consulta; nulo si no hace parte de la consulta
	 * @param status - String código del estado del ImportLog. Obligatorio
	 * @param logisticOpId - Long identificador del operador logistico
	 * @return List<ImportLog> correspondiente al registro de importación con los filtros especificados
	 * @throws DAOServiceException en casos de error al consultar registro de importación con los filtros especificados
	 * @throws DAOSQLException en casos de error al consultar registro de importación con los filtros especificados
	 * @author Jimmy Vélez Muñoz
	 */
	public List<ImportLog> getImportLogByIdCreationDateStatusAndLogisticOp(Long idImportLog, Date inicDate,
			Date endDate, String status,Long logisticOpId)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Se agrega metodo para que busque el REgistro de importacion por ID
	 * @param importLogId El identificador del registro de importacion
	 * @return ImportLogREgistro de Importación
	 * @throws DAOServiceException, DAOSQLException
	 * @author hcorredor
	 */
	public ImportLog getImportLogByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: UC Inv 125, permite realizar la consutla de registros de importación por el filtro de código
	 * 			serial que retorna elementos serializados. 
	 * @param modifyImportLogCriteria
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author hcorredor
	 */
	public ImportLogResponse getSerialCodeImportLogElement(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo)
    										   throws DAOServiceException, DAOSQLException;

	/**
	 *  Metodo: UC Inv 125, permite realizar la consutla de registros de importación, en este caso sin el filtro del código serial..
	 * @param modifyImportLogCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public ImportLogResponse getImportLogsElement(
			ModifyImportLogDTO modifyImportLogCriteria,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;

	/**
	 * 
	 * Metodo: UC Inv 01, metodo que permite consultar por orden de compra, para validar que no encuentre un número de orden de compra
	 * 			repetido con el mismo operador.
	 * @param purchaseOrder
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<ImportLog> getImportLogByPurchaseOrder(String purchaseOrder,Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * @param obj objeto que encapsula los filtros ingresados
	 * @return NotSerializedElementInSelectImportLogToPrintPaginationResponse
	 * @throws BusinessException
	 * @author cduarte
	 */
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(FilterImportLogToPrintDTO filterImportLogToPrintDTO, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;	

}