package co.com.directv.sdii.facade.stock.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
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
 * Implementación de la fachada de las operaciones Tipo CRUD ImportLog
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeLocal
 */

@Stateless(name="ImportLogFacadeLocal",mappedName="ejb/ImportLogFacadeLocal")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ImportLogFacadeBean implements ImportLogFacadeBeanLocal {


	@EJB(name="ImportLogBusinessBeanLocal", beanInterface=ImportLogBusinessBeanLocal.class)
	private ImportLogBusinessBeanLocal businessImportLog;
	
	@EJB(name="ImportLogItemBusinessBeanLocal", beanInterface=ImportLogItemBusinessBeanLocal.class)
	private ImportLogItemBusinessBeanLocal businessImportLogItem;
	
	@EJB(name="ImportLogInconsistencyBusinessBeanLocal", beanInterface=ImportLogInconsistencyBusinessBeanLocal.class)
    private ImportLogInconsistencyBusinessBeanLocal businessImportLogInconsistency;


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getAllImportLogs()
	 */
	public List<ImportLogVO> getAllImportLogs() throws BusinessException {
		return businessImportLog.getAllImportLogs();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getAllImportLogsBylogisticOp(java.lang.Long)
	 */
	public List<ImportLogVO> getAllImportLogsBylogisticOp(Long logisticOpId) throws BusinessException {
		return businessImportLog.getAllImportLogsBylogisticOp(logisticOpId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getImportLogByIDAndByLogisticOp(java.lang.Long, java.lang.Long)
	 */
	public ImportLogVO getImportLogByIDAndByLogisticOp(Long id,Long logisticOpId) throws BusinessException {
		return businessImportLog.getImportLogByIDAndByLogisticOp(id, logisticOpId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#createImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	public void createImportLog(ImportLogVO obj) throws BusinessException {
		businessImportLog.createImportLog(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#registerImportLog(co.com.directv.sdii.model.vo.ImportLogVO, java.util.List)
	 */
	public Long registerImportLog(ImportLogVO importLog,
			List<ImportLogItemVO> importLogItems, Long userId) throws BusinessException {
		return businessImportLog.registerImportLog(importLog, importLogItems, userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#modifiedImportLog(co.com.directv.sdii.model.vo.UserVO, co.com.directv.sdii.model.vo.ImportLogVO, java.util.List, java.util.List, java.util.List, java.util.List, boolean)
	 */
	public void modifiedImportLog(UserVO user,ImportLogVO importLogId,
			List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems,
			boolean isFinished)throws BusinessException{
		businessImportLog.modifiedImportLog(user, importLogId, crearblesVO, actualizablesVO, eliminablesVO, inconsistencyItems, isFinished);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#deleteImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	public void deleteImportLog(ImportLogVO obj, Long userId) throws BusinessException {
		businessImportLog.deleteImportLog(obj, userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getPendingImportLogsByLogisticOp(java.lang.Long)
	 */
	public List<ImportLogVO> getPendingImportLogsByLogisticOp(Long logisticOpId) throws BusinessException {
		return businessImportLog.getPendingImportLogsByLogisticOp(logisticOpId);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#confirmSerializedElementItemFromImportLog(co.com.directv.sdii.model.vo.ImportLogVO, co.com.directv.sdii.model.vo.ImportLogItemVO, java.lang.String, co.com.directv.sdii.model.vo.UserVO)
	 */
	public void confirmSerializedElementItemFromImportLog(ConfirmSerializedElementItemFromImportLogDTO dto) throws BusinessException {
		businessImportLogItem.confirmSerializedElementItemFromImportLog(dto);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getImportLogByCriteria(co.com.directv.sdii.model.dto.ModifyImportLogDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ImportLogResponse getImportLogByCriteria(ModifyImportLogDTO modifyImportLogCriteria, Long userId, RequestCollectionInfo requestCollInfo)throws BusinessException{
		return businessImportLog.getImportLogByCriteria(modifyImportLogCriteria, userId, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getImportLogsToConfirm(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ImportLogResponse getImportLogsToConfirm(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessImportLog.getImportLogsToConfirm(modifyImportLogCriteria, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getImportLogsByIdCreationDateAndLogisticOp(java.lang.Long, java.util.Date, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(Long importLogId,Date creationDate,Long logisticOpId, RequestCollectionInfo requestCollInfo) throws BusinessException
	{
		return businessImportLog.getImportLogsByIdCreationDateAndLogisticOp(importLogId, creationDate, logisticOpId, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getElementsInImportLog(java.lang.Long, java.lang.Boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ImportLogItemResponse getElementsInImportLog(Long importLogId,Boolean serialized, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessImportLog.getElementsInImportLog( importLogId, serialized, requestCollInfo );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getImpLogConfirmationsByImpLogItemId(java.lang.Long)
	 */
	public List<ImpLogConfirmationVO> getImpLogConfirmationsByImpLogItemId( Long importLogItemId )throws BusinessException
	{
		return businessImportLog.getImpLogConfirmationsByImpLogItemId( importLogItemId );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getImportLogInconsistenciesByImportLogId(java.lang.Long)
	 */
	public List<ImportLogInconsistencyVO> getImportLogInconsistenciesByImportLogId( Long importLogId ) throws BusinessException
	{
		return businessImportLog.getImportLogInconsistenciesByImportLogId( importLogId );
	}
	
	public ImportLogInconsistencyResponse getImportLogInconsistenciesByImportLogId( Long importLogId, RequestCollectionInfo requestCollInfo ) throws BusinessException
	{
		return businessImportLogInconsistency.getImportLogInconsistenciesByImportLogId( importLogId, requestCollInfo );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#createImportLogInconsistencies(java.util.List, co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	public void createImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList, ImportLogVO importLog ) throws BusinessException
	{
		businessImportLog.createImportLogInconsistencies(inconsistencyList, importLog);		
	}
	
	@Override
	public void saveImportLogInconsistencies(
			List<ImportLogInconsistencyVO> inconsistencyListCreate, 
			List<ImportLogInconsistencyVO> inconsistencyListUpdate, 
			List<ImportLogInconsistencyVO> inconsistencyListClose, 
			List<ImportLogInconsistencyVO> inconsistencyListDelete,
			List<ImportLogInconsistencyVO> addElementInconsistencyList, 
			List<ImportLogInconsistencyVO> delElementInconsistencyList, 
			ImportLogVO importLog, Long userId) throws BusinessException {
		businessImportLog.saveImportLogInconsistencies(inconsistencyListCreate, inconsistencyListUpdate, inconsistencyListClose, inconsistencyListDelete, addElementInconsistencyList, delElementInconsistencyList, importLog, userId);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getInconsisntencyImportLogByIdCreationDateAndLogisticOp(java.lang.Long, java.util.Date, java.lang.Long)
	 */
	public List<ImportLogVO> getInconsisntencyImportLogByIdCreationDateAndLogisticOp(
			Long idImportLog, Date creationDate,Long logisticOpId) throws BusinessException {
		return businessImportLog.getInconsisntencyImportLogByIdCreationDateAndLogisticOp(idImportLog, creationDate, logisticOpId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#validateToFinishImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	public void validateToFinishImportLog(ImportLogVO importLog)
	throws BusinessException {
		businessImportLog.validateToFinishImportLog(importLog);

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#closeImportLogInconsistencies(java.util.List)
	 */
	public void closeImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList )throws BusinessException
	{
		businessImportLog.closeImportLogInconsistencies(inconsistencyList);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#addImportLogItemsToImportLog(java.util.List, co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	public void addImportLogItemsToImportLog( List<ImportLogItemVO>itemList, ImportLogVO importLog )throws BusinessException
	{
		businessImportLog.addImportLogItemsToImportLog(itemList, importLog);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#deleteImportLogItemsInImportLog(java.util.List)
	 */
	public void deleteImportLogItemsInImportLog( List<Long>itemList )throws BusinessException
	{
		businessImportLog.deleteImportLogItemsInImportLog(itemList);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#modifyImportLogItemQuantity(java.util.List)
	 */
	public void modifyImportLogItemQuantity( List<ImportLogItemVO>itemList )throws BusinessException
	{
		businessImportLog.modifyImportLogItemQuantity( itemList );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getImportLogByElementId(java.lang.Long)
	 */
	public ImportLogVO getImportLogByElementId(Long elementId) throws BusinessException
	{
		return businessImportLog.getImportLogByElementId( elementId );		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#modifyImportLogWithInconsistencies(co.com.directv.sdii.model.vo.UserVO, co.com.directv.sdii.model.vo.ImportLogVO, java.util.List, java.util.List, java.util.List, java.util.List, boolean)
	 */
	public void modifyImportLogWithInconsistencies(UserVO user,ImportLogVO importLogId,
			List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems,
			boolean isFinished)
	throws BusinessException{
		businessImportLog.modifyImportLogWithInconsistencies(user, importLogId, crearblesVO, actualizablesVO, eliminablesVO, inconsistencyItems, isFinished);
	}

	@Override
	public ImportLogResponse getImportLogInconsistencesByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		
		return businessImportLog.getImportLogInconsistencesByCriteria(modifyImportLogCriteria, requestCollInfo);
	}

	@Override
	public List<ImportLogInconsistency> getImportLogInconsistencysByImportLog(
			Long importLog) throws BusinessException {
		return businessImportLog.getImportLogInconsistencysByImportLog(importLog);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal#getAllImportLogByIdDatesStatus(co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO,co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(FilterImportLogToPrintDTO filterImportLogToPrintDTO, RequestCollectionInfo requestCollectionInfo)throws BusinessException{
		return businessImportLog.getAllImportLogByIdDatesStatus(filterImportLogToPrintDTO,requestCollectionInfo);
	}

	@Override
	public void modifyImportLog(List<ImportLogItemVO> creables,
			List<ImportLogItemVO> actualizables,
			List<ImportLogItemVO> eliminables, Long userId,
			ImportLogVO importLogId, boolean isFinish) throws BusinessException {
		businessImportLog.modifyImportLog(creables, actualizables, eliminables, userId, importLogId, isFinish);
		
	}

	

}
