package co.com.directv.sdii.facade.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ConfirmSerializedElementItemFromImportLogDTO;
import co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.collection.ImportLogInconsistencyResponse;
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
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ImportLog.
 * 
 * Fecha de Creacion: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogFacadeBeanLocal {

	/**
	 * @return
	 * @throws BusinessException
	 */
	public List<ImportLogVO> getAllImportLogs() throws BusinessException;

	/**
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	public List<ImportLogVO> getAllImportLogsBylogisticOp(Long logisticOpId) throws BusinessException;

	/**
	 * @param id
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogVO getImportLogByIDAndByLogisticOp(Long id,Long logisticOpId) throws BusinessException;

	/**
	 * @param obj
	 * @throws BusinessException
	 */
	public void createImportLog(ImportLogVO obj) throws BusinessException;

	/**
	 * @param importLog
	 * @param importLogItems
	 * @return
	 * @throws BusinessException
	 */
	public Long registerImportLog(ImportLogVO importLog, List<ImportLogItemVO> importLogItems, Long userId) throws BusinessException;

	/**
	 * @param user
	 * @param importLogId
	 * @param crearblesVO
	 * @param actualizablesVO
	 * @param eliminablesVO
	 * @param inconsistencyItems
	 * @param isFinished
	 * @throws BusinessException
	 */
	public void modifiedImportLog(UserVO user,ImportLogVO importLogId,
			List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems,
			boolean isFinished)throws BusinessException;

	/**
	 * @param obj
	 * @throws BusinessException
	 */
	public void deleteImportLog(ImportLogVO obj, Long userId) throws BusinessException;

	/**
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	public List<ImportLogVO> getPendingImportLogsByLogisticOp(Long logisticOpId) throws BusinessException;

	/**
	 * @param importLog
	 * @param item
	 * @param user
	 * @param qaControl
	 * @throws BusinessException
	 */
	public void confirmSerializedElementItemFromImportLog(ConfirmSerializedElementItemFromImportLogDTO dto) throws BusinessException;

	/**
	 * @param modifyImportLogCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogResponse getImportLogByCriteria(ModifyImportLogDTO modifyImportLogCriteria, Long userId, RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * @param dealerId
	 * @param elementTypeId
	 * @param serialCode
	 * @param impLogId
	 * @param serialized
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogResponse getImportLogsToConfirm(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * @param importLogId
	 * @param creationDate
	 * @param logisticOpId
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(Long importLogId,Date creationDate,Long logisticOpId, RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * @param importLogId
	 * @param serialized
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogItemResponse getElementsInImportLog(Long importLogId,Boolean serialized, RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * @param importLogItemId
	 * @return
	 * @throws BusinessException
	 */
	public List<ImpLogConfirmationVO> getImpLogConfirmationsByImpLogItemId( Long importLogItemId )throws BusinessException;

	/**
	 * @param importLogId
	 * @return
	 * @throws BusinessException
	 */
	public List<ImportLogInconsistencyVO> getImportLogInconsistenciesByImportLogId( Long importLogId ) throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: Retorna lista paginada de inconsistencias en un registro de Importación.
	 * @param importLogId
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public ImportLogInconsistencyResponse getImportLogInconsistenciesByImportLogId(Long importLogId, RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * @param inconsistencyList
	 * @param importLog
	 * @throws BusinessException
	 */
	public void createImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList, ImportLogVO importLog ) throws BusinessException;

	/**
	 * @param idImportLog
	 * @param creationDate
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	public List<ImportLogVO> getInconsisntencyImportLogByIdCreationDateAndLogisticOp(
			Long idImportLog, Date creationDate,Long logisticOpId) throws BusinessException;

	/**
	 * @param importLog
	 * @throws BusinessException
	 */
	public void validateToFinishImportLog(ImportLogVO importLog) throws BusinessException;

	/**
	 * @param inconsistencyList
	 * @throws BusinessException
	 */
	public void closeImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList )throws BusinessException;

	/**
	 * @param itemList
	 * @param importLog
	 * @throws BusinessException
	 */
	public void addImportLogItemsToImportLog( List<ImportLogItemVO>itemList, ImportLogVO importLog )throws BusinessException;

	/**
	 * @param itemList
	 * @throws BusinessException
	 */
	public void deleteImportLogItemsInImportLog( List<Long>itemList )throws BusinessException;

	/**
	 * @param itemList
	 * @throws BusinessException
	 */
	public void modifyImportLogItemQuantity( List<ImportLogItemVO>itemList )throws BusinessException;

	/**
	 * @param elementId
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogVO getImportLogByElementId(Long elementId) throws BusinessException;

	/**
	 * @param user
	 * @param importLogId
	 * @param crearblesVO
	 * @param actualizablesVO
	 * @param eliminablesVO
	 * @param inconsistencyItems
	 * @param isFinished
	 * @throws BusinessException
	 */
	public void modifyImportLogWithInconsistencies(UserVO user,ImportLogVO importLogId,
			List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems,
			boolean isFinished) throws BusinessException;

	/**
	 * 
	 * @param modifyImportLogCriteria
	 * @param requestCollInfo
	 * @return
	 */
	public ImportLogResponse getImportLogInconsistencesByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria,
			RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * 
	 * @param importLog
	 * @return
	 */
	public List<ImportLogInconsistency> getImportLogInconsistencysByImportLog(
			Long importLog) throws BusinessException;

	/**
	 * @param obj objeto que encapsula los filtros ingresados
	 * @return NotSerializedElementInSelectImportLogToPrintPaginationResponse
	 * @throws BusinessException
	 * @author cduarte
	 */
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(FilterImportLogToPrintDTO filterImportLogToPrintDTO, RequestCollectionInfo requestCollectionInfo)throws BusinessException;

	/**
	 * 
	 * Metodo: Metodo encargado de almacenar y actualizar inconsistencias.
	 * 		CU INV24
	 * @param inconsistencyListCreate
	 * @param inconsistencyListUpdate
	 * @param importLog <tipo> <descripcion>
	 * @author hcorredor
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

	
	/**
	 * Operación encargada de modificar un registro de importación, ya sea guardar o terminar
	 * @param creables
	 * @param actualizables
	 * @param eliminables
	 * @param userId
	 * @param importLogId
	 * @param isFinish
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void modifyImportLog(List<ImportLogItemVO> creables,List<ImportLogItemVO> actualizables,
			List<ImportLogItemVO> eliminables, Long userId,ImportLogVO importLogId,boolean isFinish) throws BusinessException;
	
}
