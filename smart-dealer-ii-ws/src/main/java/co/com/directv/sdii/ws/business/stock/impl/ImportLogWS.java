
package co.com.directv.sdii.ws.business.stock.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImpLogModificationFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImpLogModificationTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogInconsistencyFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.InconsistencyStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.InconsistencyTypeFacadeBeanLocal;
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
import co.com.directv.sdii.model.vo.ImpLogModificationTypeVO;
import co.com.directv.sdii.model.vo.ImpLogModificationVO;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogStatusVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.InconsistencyStatusVO;
import co.com.directv.sdii.model.vo.InconsistencyTypeVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.ws.business.stock.IImportLogWS;

/**
 * Servicio web que expone las operaciones relacionadas con ImportLog
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal
 */
/**
 * @author jvelezmu
 *
 */
@MTOM
@WebService(serviceName="ImportLogService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IImportLogWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="ImportLogPort")
@Stateless()
public class ImportLogWS implements IImportLogWS {

	@EJB
    private ImportLogFacadeBeanLocal ejbRef;
	@EJB
    private ImportLogStatusFacadeBeanLocal ejbRefStatus;	
	@EJB
    private ImportLogInconsistencyFacadeBeanLocal ejbRefInconsistency;
	@EJB
    private ImpLogModificationFacadeBeanLocal ejbRefMod;
	@EJB
    private ImpLogModificationTypeFacadeBeanLocal ejbRefModType;	
	@EJB
    private InconsistencyStatusFacadeBeanLocal ejbRefIncStatus;
	@EJB
    private InconsistencyTypeFacadeBeanLocal ejbRefIncType;
	@EJB
    private ImportLogItemFacadeBeanLocal ejbRefItem;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	public void createImportLog(ImportLogVO objImportLog) throws BusinessException{
		ejbRef.createImportLog(objImportLog);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#registerImportLog(co.com.directv.sdii.model.vo.ImportLogVO, java.util.List)
	 */
	@Override
	public Long registerImportLog(ImportLogVO objImportLog,
			List<ImportLogItemVO> importLogItems, Long userId) throws BusinessException {
		return ejbRef.registerImportLog(objImportLog, importLogItems, userId);
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#modifiedImportLog(co.com.directv.sdii.model.vo.UserVO, co.com.directv.sdii.model.vo.ImportLogVO, java.util.List, java.util.List, java.util.List, java.util.List, boolean)
	 */
	@Override
	public void modifiedImportLog(UserVO user,ImportLogVO importLogId,
			List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems,
			boolean isFinished)	throws BusinessException{
		ejbRef.modifiedImportLog(user, importLogId, crearblesVO, actualizablesVO, eliminablesVO, inconsistencyItems, isFinished);
	}
			
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	public void deleteImportLog(ImportLogVO objImportLog, Long userId) throws BusinessException{
		ejbRef.deleteImportLog(objImportLog, userId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogByIDAndByLogisticOp(java.lang.Long, java.lang.Long)
	 */
	@Override
	public ImportLogVO getImportLogByIDAndByLogisticOp(Long id,Long logisticOpId) throws BusinessException{
		return ejbRef.getImportLogByIDAndByLogisticOp(id, logisticOpId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllImportLogs()
	 */
	@Override
	public List<ImportLogVO> getAllImportLogs() throws BusinessException{
		return ejbRef.getAllImportLogs();
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllImportLogsByLogisticOp(java.lang.Long)
	 */
	@Override
	public List<ImportLogVO> getAllImportLogsByLogisticOp(Long logisticOpId) throws BusinessException{
		return ejbRef.getAllImportLogsBylogisticOp(logisticOpId);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getPendingImportLogsByLogisticOp(java.lang.Long)
	 */
	@Override
	public List<ImportLogVO> getPendingImportLogsByLogisticOp(Long logisticOpId) throws BusinessException {
		return ejbRef.getPendingImportLogsByLogisticOp(logisticOpId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	@Override
	public void createImportLogStatus(ImportLogStatusVO objImportLogStatus) throws BusinessException{
		ejbRefStatus.createImportLogStatus(objImportLogStatus);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#updateImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	@Override
	public void updateImportLogStatus(ImportLogStatusVO objImportLogStatus) throws BusinessException{
		ejbRefStatus.updateImportLogStatus(objImportLogStatus);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	@Override
	public void deleteImportLogStatus(ImportLogStatusVO objImportLogStatus) throws BusinessException{
		ejbRefStatus.deleteImportLogStatus(objImportLogStatus);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogStatusByID(java.lang.Long)
	 */
	@Override
	public ImportLogStatusVO getImportLogStatusByID(Long id) throws BusinessException{
		return ejbRefStatus.getImportLogStatusByID(id);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllImportLogStatuss()
	 */
	@Override
	public List<ImportLogStatusVO> getAllImportLogStatuss() throws BusinessException{
		return ejbRefStatus.getAllImportLogStatuss();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogStatusByCode(java.lang.String)
	 */
	@Override
	public ImportLogStatusVO getImportLogStatusByCode(String code)
			throws BusinessException {
		return ejbRefStatus.getImportLogStatusByCode(code);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	public void createImportLogInconsistency(ImportLogInconsistencyVO objImportLogInconsistency) throws BusinessException{
		ejbRefInconsistency.createImportLogInconsistency(objImportLogInconsistency);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#updateImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	public void updateImportLogInconsistency(ImportLogInconsistencyVO objImportLogInconsistency) throws BusinessException{
		ejbRefInconsistency.updateImportLogInconsistency(objImportLogInconsistency);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	public void deleteImportLogInconsistency(ImportLogInconsistencyVO objImportLogInconsistency) throws BusinessException{
		ejbRefInconsistency.deleteImportLogInconsistency(objImportLogInconsistency);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogInconsistencyByID(java.lang.Long)
	 */
	@Override
	public ImportLogInconsistencyVO getImportLogInconsistencyByID(Long id) throws BusinessException{
		return ejbRefInconsistency.getImportLogInconsistencyByID(id);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllImportLogInconsistencys()
	 */
	@Override
	public List<ImportLogInconsistencyVO> getAllImportLogInconsistencys() throws BusinessException{
		return ejbRefInconsistency.getAllImportLogInconsistencys();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	@Override
	public void createImpLogModification(ImpLogModificationVO objImpLogModification) throws BusinessException{
		ejbRefMod.createImpLogModification(objImpLogModification);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#updateImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	@Override
	public void updateImpLogModification(ImpLogModificationVO objImpLogModification) throws BusinessException{
		ejbRefMod.updateImpLogModification(objImpLogModification);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	@Override
	public void deleteImpLogModification(ImpLogModificationVO objImpLogModification) throws BusinessException{
		ejbRefMod.deleteImpLogModification(objImpLogModification);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImpLogModificationByID(java.lang.Long)
	 */
	@Override
	public ImpLogModificationVO getImpLogModificationByID(Long id) throws BusinessException{
		return ejbRefMod.getImpLogModificationByID(id);
	}
	
	@Override
	public List<ImpLogModificationVO> getImpLogModificationByImportLogID(@WebParam(name="id") Long id) throws BusinessException{
		return ejbRefMod.getImpLogModificationByImportLogID(id);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllImpLogModifications()
	 */
	@Override
	public List<ImpLogModificationVO> getAllImpLogModifications() throws BusinessException{
		return ejbRefMod.getAllImpLogModifications();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	@Override
	public void createImpLogModificationType(ImpLogModificationTypeVO objImpLogModificationType) throws BusinessException{
		ejbRefModType.createImpLogModificationType(objImpLogModificationType);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#updateImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	@Override
	public void updateImpLogModificationType(ImpLogModificationTypeVO objImpLogModificationType) throws BusinessException{
		ejbRefModType.updateImpLogModificationType(objImpLogModificationType);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	@Override
	public void deleteImpLogModificationType(ImpLogModificationTypeVO objImpLogModificationType) throws BusinessException{
		ejbRefModType.deleteImpLogModificationType(objImpLogModificationType);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImpLogModificationTypeByID(java.lang.Long)
	 */
	@Override
	public ImpLogModificationTypeVO getImpLogModificationTypeByID(Long id) throws BusinessException{
		return ejbRefModType.getImpLogModificationTypeByID(id);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImpLogModificationTypeByCode(java.lang.String)
	 */
	@Override
	public ImpLogModificationTypeVO getImpLogModificationTypeByCode(String  code) throws BusinessException{
		return ejbRefModType.getImpLogModificationTypeByCode(code);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllImpLogModificationTypes()
	 */
	@Override
	public List<ImpLogModificationTypeVO> getAllImpLogModificationTypes() throws BusinessException{
		return ejbRefModType.getAllImpLogModificationTypes();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getActiveImpLogModificationTypes()
	 */
	@Override
	public List<ImpLogModificationTypeVO> getActiveImpLogModificationTypes() throws BusinessException{
		return ejbRefModType.getActiveImpLogModificationTypes();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	@Override
	public void createInconsistencyStatus(InconsistencyStatusVO objInconsistencyStatus) throws BusinessException{
		ejbRefIncStatus.createInconsistencyStatus(objInconsistencyStatus);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#updateInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	@Override
	public void updateInconsistencyStatus(InconsistencyStatusVO objInconsistencyStatus) throws BusinessException{
		ejbRefIncStatus.updateInconsistencyStatus(objInconsistencyStatus);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	@Override
	public void deleteInconsistencyStatus(InconsistencyStatusVO objInconsistencyStatus) throws BusinessException{
		ejbRefIncStatus.deleteInconsistencyStatus(objInconsistencyStatus);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getInconsistencyStatusByID(java.lang.Long)
	 */
	@Override
	public InconsistencyStatusVO getInconsistencyStatusByID(Long id) throws BusinessException{
		return ejbRefIncStatus.getInconsistencyStatusByID(id);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllInconsistencyStatuss()
	 */
	@Override
	public List<InconsistencyStatusVO> getAllInconsistencyStatuss() throws BusinessException{
		return ejbRefIncStatus.getAllInconsistencyStatuss();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	@Override
	public void createInconsistencyType(InconsistencyTypeVO objInconsistencyType) throws BusinessException{
		ejbRefIncType.createInconsistencyType(objInconsistencyType);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#updateInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	@Override
	public void updateInconsistencyType(InconsistencyTypeVO objInconsistencyType) throws BusinessException{
		ejbRefIncType.updateInconsistencyType(objInconsistencyType);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	@Override
	public void deleteInconsistencyType(InconsistencyTypeVO objInconsistencyType) throws BusinessException{
		ejbRefIncType.deleteInconsistencyType(objInconsistencyType);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getInconsistencyTypeByID(java.lang.Long)
	 */
	@Override
	public InconsistencyTypeVO getInconsistencyTypeByID(Long id) throws BusinessException{
		return ejbRefIncType.getInconsistencyTypeByID(id);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllInconsistencyTypes()
	 */
	@Override
	public List<InconsistencyTypeVO> getAllInconsistencyTypes() throws BusinessException{
		return ejbRefIncType.getAllInconsistencyTypes();
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getActiveInconsistencyTypes()
	 */
	@Override
	public List<InconsistencyTypeVO> getActiveInconsistencyTypes() throws BusinessException{
		return ejbRefIncType.getActiveInconsistencyTypes();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getInconsistencyTypeByCode(java.lang.String)
	 */
	@Override
	public InconsistencyTypeVO getInconsistencyTypeByCode(String code)
			throws BusinessException {
		return ejbRefIncType.getInconsistencyTypeByCode(code);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#confirmSerializedElements(co.com.directv.sdii.model.vo.ImportLogVO, co.com.directv.sdii.model.vo.ImportLogItemVO, java.lang.Long, java.lang.String, co.com.directv.sdii.model.vo.UserVO)
	 */
	@Override
	public void confirmSerializedElements(ConfirmSerializedElementItemFromImportLogDTO dto) throws BusinessException {
		ejbRef.confirmSerializedElementItemFromImportLog(dto);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogByCriteria(co.com.directv.sdii.model.dto.ModifyImportLogDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ImportLogResponse getImportLogByCriteria(ModifyImportLogDTO modifyImportLogCriteria, Long userId, RequestCollectionInfo requestCollInfo)throws BusinessException{
		return ejbRef.getImportLogByCriteria(modifyImportLogCriteria, userId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogInconsistencesByCriteria(co.com.directv.sdii.model.dto.ModifyImportLogDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ImportLogResponse getImportLogInconsistencesByCriteria(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo)throws BusinessException{
		return ejbRef.getImportLogInconsistencesByCriteria(modifyImportLogCriteria, requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogsToConfirm(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
    public ImportLogResponse getImportLogsToConfirm(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getImportLogsToConfirm(modifyImportLogCriteria, requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogsByIdCreationDateAndLogisticOp(java.lang.Long, java.util.Date, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(Long importLogId,
			Date creationDate,Long logisticOpId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getImportLogsByIdCreationDateAndLogisticOp( importLogId,creationDate, logisticOpId, requestCollInfo );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getElementsInImportLog(java.lang.Long, java.lang.Boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ImportLogItemResponse getElementsInImportLog(Long importLogId,
			Boolean serialized, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getElementsInImportLog( importLogId,serialized, requestCollInfo );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImpLogConfirmationsByImpLogItemId(java.lang.Long)
	 */
	@Override
	public List<ImpLogConfirmationVO> getImpLogConfirmationsByImpLogItemId( Long importLogItemId )throws BusinessException
	{
		return ejbRef.getImpLogConfirmationsByImpLogItemId( importLogItemId );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogInconsistenciesByImportLogId(java.lang.Long)
	 */
	@Override
	public List<ImportLogInconsistencyVO> getImportLogInconsistenciesByImportLogId( Long importLogId ) throws BusinessException
	{
		return ejbRef.getImportLogInconsistenciesByImportLogId( importLogId );
	}
	
	@Override
	public ImportLogInconsistencyResponse getImportLogInconsistenciesRequestCollInfoByImportLogId( Long importLogId, RequestCollectionInfo requestCollInfo ) throws BusinessException
	{
		return ejbRef.getImportLogInconsistenciesByImportLogId( importLogId, requestCollInfo );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#createImportLogInconsistencies(java.util.List, co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	public void createImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList,ImportLogVO importLog) throws BusinessException
	{
		ejbRef.createImportLogInconsistencies(inconsistencyList, importLog);		
	}
	
	@Override
	public void saveImportLogInconsistencies( 
			List<ImportLogInconsistencyVO> inconsistencyListCreate, 
			List<ImportLogInconsistencyVO> inconsistencyListUpdate, 
			List<ImportLogInconsistencyVO> inconsistencyListClose,
			List<ImportLogInconsistencyVO> inconsistencyListDelete,
			List<ImportLogInconsistencyVO> addElementInconsistencyList, 
			List<ImportLogInconsistencyVO> delElementInconsistencyList, 
			ImportLogVO importLog, Long userId ) throws BusinessException {
		ejbRef.saveImportLogInconsistencies(inconsistencyListCreate, inconsistencyListUpdate, inconsistencyListClose, inconsistencyListDelete, addElementInconsistencyList, delElementInconsistencyList, importLog, userId);		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getInconsisntencyImportLogByIdCreationDateAndLogisticOp(java.lang.Long, java.util.Date, java.lang.Long)
	 */
	@Override
	public List<ImportLogVO> getInconsisntencyImportLogByIdCreationDateAndLogisticOp(
			Long idImportLog, Date creationDate,Long logisticOpId) throws BusinessException {
		return ejbRef.getInconsisntencyImportLogByIdCreationDateAndLogisticOp(idImportLog, creationDate, logisticOpId);
	}
		

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#validateToFinishImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	public void validateToFinishImportLog(ImportLogVO importLog) throws BusinessException {
		ejbRef.validateToFinishImportLog(importLog);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#closeImportLogInconsistencies(java.util.List)
	 */
	@Override
	public void closeImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList )throws BusinessException
	{
		ejbRef.closeImportLogInconsistencies( inconsistencyList );
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#addImportLogItemListToImportLog(java.util.List, co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	public void addImportLogItemListToImportLog( List<ImportLogItemVO>itemList,ImportLogVO importLog)throws BusinessException
	{
		ejbRef.addImportLogItemsToImportLog(itemList, importLog);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#deleteImportLogItemsInImportLog(java.util.List)
	 */
	@Override
	public void deleteImportLogItemsInImportLog( List<Long>itemList )throws BusinessException
	{
		ejbRef.deleteImportLogItemsInImportLog(itemList);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#modifyImportLogItemQuantity(java.util.List)
	 */
	@Override
	public void modifyImportLogItemQuantity( List<ImportLogItemVO>itemList )throws BusinessException
	{
		ejbRef.modifyImportLogItemQuantity( itemList );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogByElementId(java.lang.Long)
	 */
	@Override
	public ImportLogVO getImportLogByElementId(Long elementId) throws BusinessException
	{
      return ejbRef.getImportLogByElementId( elementId );		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getImportLogItemByID(java.lang.Long)
	 */
	@Override
	public ImportLogItemVO getImportLogItemByID(Long id) throws BusinessException{
		return ejbRefItem.getImportLogItemByID(id);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#modifyImportLogWithInconsistencies(co.com.directv.sdii.model.vo.UserVO, co.com.directv.sdii.model.vo.ImportLogVO, java.util.List, java.util.List, java.util.List, java.util.List, boolean, java.lang.Long)
	 */
	@Override
	public void modifyImportLogWithInconsistencies(UserVO user,
			ImportLogVO importLogId, List<ImportLogItemVO> crearblesVO,
			List<ImportLogItemVO> actualizablesVO,
			List<ImportLogItemVO> eliminablesVO,
			List<ImportLogInconsistencyVO> inconsistencyItems,
			boolean isFinished) throws BusinessException {
		ejbRef.modifyImportLogWithInconsistencies(user, importLogId, crearblesVO, actualizablesVO, eliminablesVO, inconsistencyItems, isFinished);
		
	}
	
	@Override
	public List<ImportLogInconsistency> getImportLogInconsistencysByImportLog(Long importLog) throws BusinessException{
		return ejbRef.getImportLogInconsistencysByImportLog(importLog);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogWS#getAllImportLogByIdDatesStatus(co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO,co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(FilterImportLogToPrintDTO filterImportLogToPrintDTO,RequestCollectionInfo requestCollectionInfo)throws BusinessException{
		return ejbRef.getAllImportLogByIdDatesStatus(filterImportLogToPrintDTO,requestCollectionInfo);
	}

	@Override
	public void modifyImportLog(List<ImportLogItemVO> creables,
			List<ImportLogItemVO> actualizables,
			List<ImportLogItemVO> eliminables, Long userId,
			ImportLogVO importLogId, boolean isFinish) throws BusinessException {
		this.ejbRef.modifyImportLog(creables, actualizables, eliminables, userId, importLogId, isFinish);
		
	}
}
