package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogItemDTO;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.CountItemStatusImportLogDTO;
import co.com.directv.sdii.reports.dto.SerializedElementImportLogDTO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ImportLogItem
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogItemDAOLocal {

	/**
	 * Metodo:  persiste la información de un ImportLogItem
	 * @param obj objeto que encapsula la información de un ImportLogItem
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ImportLogItem
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ImportLogItem
	 * @author gfandino
	 */
	public void createImportLogItem(ImportLogItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ImportLogItem
	 * @param obj objeto que encapsula la información de un ImportLogItem
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ImportLogItem
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ImportLogItem
	 * @author gfandino
	 */
	public void updateImportLogItem(ImportLogItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLogItem
	 * @param obj información del ImportLogItem a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ImportLogItem
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ImportLogItem
	 * @author gfandino
	 */
	public void deleteImportLogItem(ImportLogItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ImportLogItem por su identificador
	 * @param id identificador del ImportLogItem a ser consultado
	 * @return objeto con la información del ImportLogItem dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ImportLogItem por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ImportLogItem por ID
	 * @author gfandino
	 */
	public ImportLogItem getImportLogItemByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogItem almacenados en la persistencia
	 * @return Lista con los ImportLogItem existentes, una lista vacia en caso que no existan ImportLogItem en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ImportLogItem
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ImportLogItem
	 * @author gfandino
	 */
	public List<ImportLogItem> getAllImportLogItems() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los ImportLogItem almacenados en la persistencia correspondientes al ImportLog
	 * @param idImportLog - Long  identificadot del ImportLog
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ImportLogItemResponse, List<ImportLogItem> correspondiente al importlog especificado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ImportLogItem por ImportLog
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ImportLogItem por ImportLog
	 * @author gfandino
	 */
	public ImportLogItemResponse getImportLogItemsByImportLog(Long idImportLog, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de items de import log dado el identificador de registro de 
	 * importación y si los elementos son serializados o nó
	 * @param importLogId identificador de registro de importación
	 * @param isSerialized define si es serializado o nó
	 * @return Lista con los registros de importación
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ImportLogItem por ImportLog y es serializado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ImportLogItem por ImportLog y es serializado
	 * @author gfandino
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			Long importLogId, boolean isSerialized, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna un listado de los elementos individuales 
	 * del registro de importacion que no esten en estado Confirmado
	 * CU INV 04
	 * @param importLogId Long, id del registro de importacion
	 * @return List<ImportLogItem>, Lista con elementos del registro de importacion
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ImportLogItem> getImpLogItemsByIndividualElements(Long importLogId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Retorna un listado de los elementos individuales serializados
	 * del registro de importacion 
	 * CU INV 04
	 * @param importLogId Long, id del registro de importacion
	 * @return List<ImportLogItem>, Lista con elementos del registro de importacion
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ImportLogItemResponse getImpLogItemsByIndividualElementsSerialized(Long importLogId,RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los ítems de regtistros de importación dado el identificador del elemento
	 * @param elementId identificador del elemento
	 * @return item de registro de importación
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author jjimenezh
	 */
	public ImportLogItem getImportLogItemByElementId(Long elementId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los items serializados o no asociados a un registro de importacion
	 * INV-24
	 * @param importLogId el identificador del registro de importacion
	 * @param serialized un booleano que indica si se deben buscar serializados o no 
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ImportLogItemResponse, una lista con los item del registro de importacion
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author garcinieags
	 */
	public ImportLogItemResponse getElementsInImportLog(Long importLogId,boolean serialized, RequestCollectionInfo requestCollInfo )throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Se actualiza el estado de todos los items de un registro de importación en un estado previo a un estado nuevo
	 * @param importLogId identificador del registro de importación
	 * @param itemStatus estado del item
	 * @author jjimenezh
	 */
	public void updateImportLogItemsStatusByImportLogId(Long importLogId, ItemStatus previousitemStatus, ItemStatus newitemStatus)throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * @param modifyImportLogItemCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public ImportLogItemResponse getImportLogItemsByCriteria(ModifyImportLogItemDTO modifyImportLogItemCriteria, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * @param importLogId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public boolean isImportLogItemStatus(Long importLogId, String statusCode) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Metodo encargado de borrar todos los items de un registro de importación.
	 * @param id <tipo> <descripcion>
	 * @author
	 */
	public void deleteImportLogItemsFromImportLog(Long id) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna los items de un registro de importación según el estado.
	 * @param importLogId
	 * @param isSerialized
	 * @param itemStatus
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogIdStatusAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;
	
	
	/**
	 * 
	 * Metodo: Retorna los items de un registro de importación según el estado para generar el excel
	 * @param importLogId
	 * @param isSerialized
	 * @param itemStatus
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<SerializedElementImportLogDTO> getImportLogItemsByImportLogIdAndIsSerializedForExcel(
			ImportLogElementsFilterDTO filterImportLogElements) throws DAOServiceException,
			DAOSQLException;

	public Long conteoConfirmadas(Long importLogId) throws DAOServiceException, DAOSQLException;

	public Long countByStatus(Long importLogId, ItemStatus itemStatus) throws DAOServiceException, DAOSQLException;
	
	public ImportLogItemResponse getImportLogItemsByImportLogAll(Long pImportLog, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo encargado del conteo de los items del registro de importación 
	 * con respecto a los estados es que se encuentra.
	 * @param importLogId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public List<CountItemStatusImportLogDTO> getCountItemStatusForImportLog(Long importLogId) throws DAOServiceException, DAOSQLException; 
	
	public ImportLogItemResponse getImportLogItemsAndSumByImportLog(Long pImportLog, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;
	
}