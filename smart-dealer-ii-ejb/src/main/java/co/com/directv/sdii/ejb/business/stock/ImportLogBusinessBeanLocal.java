package co.com.directv.sdii.ejb.business.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectImportLogToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ImportLog.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@Local
public interface ImportLogBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ImportLogVO
	 * @param obj objeto que encapsula la información de un ImportLogVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ImportLog
	 * @author Jimmy Vélez Muñoz
	 */
	public void createImportLog(ImportLogVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: UC_INV_01 Permite registrar por primera vez la información de ImportLog con sus Items
	 * @param importLog - ImportLogVO Registro de importación que se va a realizar
	 * @param importLogItems - List<ImportLogItemVO> Items que se van a registrar en la importación
	 * @throws BusinessException en caso de error al ejecutar el registro de ImportLog
	 * @author Jimmy Vélez Muñoz
	 */
	public Long registerImportLog(ImportLogVO importLog, List<ImportLogItemVO> importLogItems, Long userId)throws BusinessException;
	
	/**
	 * Metodo: Permite modificar un registro de importación
	 * @param user - UserVO usuario que realiza la modificación
	 * @param importLogId - ImportLogVO Registro de importación que se va a actualizar
	 * @param crearblesVO - List<ImportLogItemVO> que se van a crear
	 * @param actualizablesVO - List<ImportLogItemVO> que se van a actualizar
	 * @param eliminablesVO - List<ImportLogItemVO> que se van a eliminar
	 * @param inconsistencyItems - List<ImportLogInconsistencyVO> Lista con las inconsistencias pertenecientes al registro de importación 
	 * @param isFinished -boolean indica si se debe validar que ha sido terminado o no
	 * @throws BusinessException en caso de error al ejecutar la modificación del registro de improtación
	 * @author Jimmy Vélez Muñoz
	 */
	public void modifiedImportLog(UserVO user,ImportLogVO importLogId, List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems, boolean isFinished) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ImportLogVO
	 * @param obj objeto que encapsula la información de un ImportLogVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ImportLog
	 * @author gfandino
	 */
	public void updateImportLog(ImportLogVO obj,Long userId) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLogVO
	 * @param obj información del ImportLogVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ImportLog
	 * @author gfandino
	 */
	public void deleteImportLog(ImportLogVO obj, Long userId) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ImportLogVO por su identificador.
	 * 
	 * @param id identificador del ImportLogVO a ser consultado
	 * @return objeto con la información del ImportLogVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta deImportLog por ID
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public ImportLogVO getImportLogByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ImportLogVO por su identificador y por el 
	 * Operador Logistico al cual pertenece.
	 * 
	 * @param id identificador del ImportLogVO a ser consultado
	 * @param logisticOpId - Long Id del Operador Logistico
	 * @return objeto con la información del ImportLogVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta deImportLog por ID
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public ImportLogVO getImportLogByIDAndByLogisticOp(Long id, Long logisticOpId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogVO almacenados en la persistencia
	 * @return Lista con los ImportLogVO existentes, una lista vacia en caso que no existan ImportLogVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ImportLog 
	 * @author gfandino
	 */
	public List<ImportLogVO> getAllImportLogs() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogVO almacenados en la persistencia
	 * @param logisticOpId - Long identificador del país
	 * @return Lista con los ImportLogVO existentes, una lista vacia en caso que no existan ImportLogVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ImportLog 
	 * @author Jimmy Vélez Muñoz
	 */
	public List<ImportLogVO> getAllImportLogsBylogisticOp(Long logisticOpId) throws BusinessException;
	

	/**
	 * Metodo: CU_INV_03 Confirmar elementos NO serializados de un registro de importación, esta operación
	 * Obtiene todos los registros de importación que estén en estado
	 * "Enviado", "Confirmado Parcial" o "En Inconsistencia"
	 * @param logisticOpId - Long identificador del país
	 * @return Lista con los registros de importación en estado Enviado o confirmado parcial,
	 * una lista vacia en caso que no se encuentren elementos en dicho estado 
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<ImportLogVO> getPendingImportLogsByLogisticOp(Long logisticOpId)throws BusinessException;
	
	/**
	 * Metodo: UC_INV_02 Permite consultar los registros de importacion y compra
	 * @param ModifyImportLogDTO Criterios de busqueda para los registro de importacion y compra
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return RequestCollectionInfo requestCollInfo, List<ImportLogVO> lista de registros que cumplen con el filtro 
	 * @throws BusinessException  En caso de error en la consulta de los import logs
	 * @author jnova
	 */
	public ImportLogResponse getImportLogByCriteria(ModifyImportLogDTO modifyImportLogCriteria, Long userId, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
    /**
     * Método: UC_INV_125 Obtiene los registros de importación que se encuentran en estado Enviado, Inconsistente o Confirmado parcial
     * según los criterios de entrada.
     *  
     * @param dealerId
     * @param elementTypeId
     * @param serialCode
     * @param impLogId
     * @param serialized
     * @param requestCollInfo
     * @return ImportLogResponse
     * @throws BusinessException
     * 
     * @author Jimmy Vélez Muñoz
     */
    public ImportLogResponse getImportLogsToConfirm(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: UC_INV_24 Consulta registros de importacion basados en un filtro con el identificador 
	 * y la fecha de creacion 
	 * @param importLogId El identificador del registro de importacion
	 * @param creationDate La fecha de creacion del registro de importacion
	 * @param logisticOpId - Long identificador del país
	 * @param RequestCollectionInfo requestCollInfo, parametros para la pagiancion
	 * @return una lista de objetos ImportLog asociado al filtro
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author Jimmy Vélez Muñoz
	 */
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(Long importLogId, Date creationDate, Long logisticOpId, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	/**
	 * Metodo: UC_INV_24 Obtiene los items serializados o no asociados a un registro de importacion
	 * @param importLogId el identificador del registro de importacion
	 * @param serialized un booleano que indica si se deben buscar serializados o no 
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ImportLogItemResponse, una lista con los item del registro de importacion
	 * @throws BusinessException  En caso de error en la consulta de los import logs
	 * @author garcinieags
	 */
	public ImportLogItemResponse getElementsInImportLog(Long importLogId, Boolean serialized, RequestCollectionInfo requestCollInfo )throws BusinessException;
	
	/**
	 * Metodo: UC_INV_24 Obtiene una lista de las confirmaciones de items de registros de importacion por item
	 * @param importLogItemId identificador del item de registro de importacion
	 * @return Lista con las confirmaciones que se han realizado ordenada por fecha
	 * @throws BusinessException  En caso de error en la consulta de los import logs
	 * @author garciniegas
	 */
	public List<ImpLogConfirmationVO> getImpLogConfirmationsByImpLogItemId( Long importLogItemId )throws BusinessException;
	
	/**
	 * Metodo: Consulta los registros de importación que se encuentran en estado inconsistencia por su fecha e identificador
	 * @param idImportLog - Long Corresponde al identificador de la ImportLog. ID en cero si no hace parte del filtro
	 * @param creationDate - Date Corresponde a la fecha de creación. Nulo si no hace parte del filtro
	 * @param logisticOpId - Long identificador del país
	 * @return List<ImportLogVO> correspondiente al filtro establecido; si no se establece retorna todos los ImportLog inconsistentes 
	 * @throws BusinessException en caso de error al consultar ImportLog por fecha e identificador
	 * @author Jimmy Vélez Muñoz
	 */
	public List<ImportLogVO> getInconsisntencyImportLogByIdCreationDateAndLogisticOp(Long idImportLog, Date creationDate, Long logisticOpId) throws BusinessException;
	
	
	/**
	 * Metodo: UC_INV_24 Obtiene la informacion de todos los ImportLogInconsistency asociados a un registro de importacion
	 * @param importLogId el identificador del registro de importacion
	 * @return Lista con los ImportLogInconsistency existentes, una lista vacia en caso que no existan ImportLogInconsistency en el sistema
	 * @throws BusinessException  En caso de error en la consulta de los import logs
	 * @author garciniegas
	 */
	public List<ImportLogInconsistencyVO> getImportLogInconsistenciesByImportLogId( Long importLogId ) throws BusinessException;
	
	/**
	 * Metodo: UC_INV_24 Crea una lista de registros ImportLogInconsistency asociados a un registro de importacion
	 * @param  inconsistencyList Una lista con los ImportLogInconsistency a registrar
	 * @param importLog Registro de importacion
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @author Jimmy Vélez Muñoz
	 */
	public void createImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList, ImportLogVO importLog) throws BusinessException;
	
	/**
	 * Metodo: Permite validar la finalización de reporte de inconsistensis de registros de importación
	 * @param importLog - ImportLogVO registro de importación a validar
	 * @throws BusinessException error validando el registro de importación
	 * @author Jimmy Vélez Muñoz
	 */
	public void validateToFinishImportLog(ImportLogVO importLog) throws BusinessException;
	
	/**
	 * Metodo: UC_INV_17 Cierra el estado de una lista de objetos ImportLogInconsistency asociados a un registro de importacion
	 * @param  inconsistencyList Una lista con los ImportLogInconsistency a cerrar
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la insercion de todos los ImportLogInconsistency
	 * @author garciniegas
	 */ 
	public void closeImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList )throws BusinessException;
	
	/**
	 * Metodo: UC_INV_17 Adicionar registros de ImportLogItem a un registro de importacion
	 * @param  itemLis Una lista con los ImportLogItem a registrar
	 * @param importLog - Registro de Importación
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la insercion
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la insercion
	 * @author Jimmy Vélez Muñoz
	 */ 
	public void addImportLogItemsToImportLog( List<ImportLogItemVO> itemList, ImportLogVO importLog )throws BusinessException;
	
	/**
	 * Metodo: UC_INV_17 Elimina registros de ImportLogItem asociados un registro de importacion
	 * Nota: solo elimina aquellos que no han sido confirmados
	 * @param  itemList Una lista con los ImportLogItem a eliminar
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminacion
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminacion
	 * @author garciniegas
	 */ 
	public void deleteImportLogItemsInImportLog( List<Long> itemList )throws BusinessException;
	
	/**
	 * Metodo: UC_INV_17 Modifica la cantidad esperada en los objetos ImportLogItem de un registro de importacion
	 * Nota: solo seran modificados aquellos que soliciten una cantidad mayor a la registrada
	 * @param  itemList Una lista con los ImportLogItem a modificar
	 * @throws DAOServiceException en caso de error al tratar de ejecutar actualizacion
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la actualizacion
	 * @author garciniegas
	 */ 
	public void modifyImportLogItemQuantity( List<ImportLogItemVO>itemList )throws BusinessException;
	
	
	/**
	 * Metodo: UC_INV_20 Consulta el registro de importacion asociado al identificador de Element enviado 
	 * @param elementId Identificador del Element
	 * @return ImportLog el objeto ImportLog asociado al elementId
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public ImportLogVO getImportLogByElementId(Long elementId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Permite cerrar inconsistencias asociadas a un registro de importacion
	 * @param user Usuario que realiza el cierre de inconsistencias
	 * @param importLogId Registro de importacion al que se le van a cerrar las inconsistencias
	 * @param crearblesVO
	 * @param actualizablesVO
	 * @param eliminablesVO
	 * @param inconsistencyItems Lista de inconsistencias que se van a cerrar
	 * @param isFinished
	 * @throws BusinessException
	 */
	public void modifyImportLogWithInconsistencies(UserVO user,ImportLogVO importLogId, List<ImportLogItemVO> crearblesVO, List<ImportLogItemVO> actualizablesVO, List<ImportLogItemVO> eliminablesVO, List<ImportLogInconsistencyVO> inconsistencyItems, boolean isFinished) throws BusinessException;
	
	
	public void updateItemStatusInImportLog(ImportLogItemVO item, UserVO user) throws BusinessException;
	
	public boolean allImportLogItemAreRecepted(List<ImportLogItem> originalList) throws PropertiesException, HelperException;
	
	public void sendMailToImportLogNotification(Warehouse wareHouse, UserVO user,Long importLogID) throws BusinessException, DAOServiceException, DAOSQLException;

    /**
     * 
     * @param modifyImportLogCriteria
     * @param requestCollInfo
     * @return
     */
	public ImportLogResponse getImportLogInconsistencesByCriteria(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException;

    /**
     * 
     * @param importLog
     * @return
     */
	public List<ImportLogInconsistency> getImportLogInconsistencysByImportLog(Long importLog) throws BusinessException;


	/**
	 * 
	 * @param importLog
	 * @return
	 * @throws BusinessException
	 */
	public boolean isNationalBuy(ImportLogVO importLog) throws BusinessException;


	
	
	/**
	 * @param obj objeto que encapsula los filtros ingresados
	 * @return NotSerializedElementInSelectImportLogToPrintPaginationResponse
	 * @throws BusinessException
	 * @author cduarte
	 */
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(FilterImportLogToPrintDTO filterImportLogToPrintDTO, RequestCollectionInfo requestCollectionInfo)throws BusinessException;


	/**
	 * 
	 * Metodo: Guarda i actualiza las inconsistencias de los elementos de un 
	 * 		registro de importación.
	 * 		CU INV24.
	 * @param inconsistencyListCreate
	 * @param inconsistencyListUpdate
	 * @param importLog <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	public void saveImportLogInconsistencies(
			List<ImportLogInconsistencyVO> inconsistencyListCreate, 
			List<ImportLogInconsistencyVO> inconsistencyListUpdate, 
			List<ImportLogInconsistencyVO> inconsistencyListClose, 
			List<ImportLogInconsistencyVO> inconsistencyListDelete,
			List<ImportLogInconsistencyVO> addElementInconsistencyList, 
			List<ImportLogInconsistencyVO> delElementInconsistencyList, 
			ImportLogVO importLog, Long userId) throws BusinessException;
	
	public void verificaEstadoImportLog(ImportLog importLog, Long userId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException;
	
	
	
	/**
	 * Operacion encargada de almacenar los items de un registro de importación
	 * @param listaNoSerialized
	 * @param listaSerialed
	 * @param isNationalBuy
	 * @param elementStatus
	 * @param objPojo
	 * @param itemStatus
	 * @param isFinished
	 * @param user
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void saveItemsforNewImportLog(List<ImportLogItemVO> listaNoSerialized,
			List<ImportLogItemVO> listaSerialed,boolean isNationalBuy,
			ImportLog importLog, boolean isSendStatus, boolean isNewItems, User user)
			throws BusinessException;
	
	/**
	 * Operación encargada de modificar un registro de importación, ya sea guardar o terminar
	 * @param creables
	 * @param actualizables
	 * @param eliminables
	 * @param user
	 * @param importLogId
	 * @param isFinish
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void modifyImportLog(List<ImportLogItemVO> creables,List<ImportLogItemVO> actualizables,
			List<ImportLogItemVO> eliminables, Long userId,ImportLogVO importLogId,boolean isFinish) throws BusinessException;
	
	public void sendMailToImportLogNotification(User destinyUser,UserVO user,Long importLogID) throws BusinessException, DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param uploadFile
	 * @param elementStatus
	 * @param itemStatus
	 * @param importLog
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<Object> doGlobalValidationsFileProcessorImportLogSerializedAllElements(UploadFile uploadFile) throws BusinessException;

    /**
     * Metodo: <Descripcion>
     * @param fileRecordDTO
     * @param elementStatus
     * @param itemStatus
     * @param importLog
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public void doGlobalValidationsFileProcessorImportLogSerializedAllElementsPost(FileRecordDTO fileRecordDTO,
																	               ElementStatus elementStatus,
																	               ItemStatus itemStatus,
																	               ImportLog importLog) throws BusinessException;
	
}
