
package co.com.directv.sdii.ws.business.stock;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

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
import co.com.directv.sdii.model.vo.ImpLogModificationTypeVO;
import co.com.directv.sdii.model.vo.ImpLogModificationVO;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogStatusVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.InconsistencyStatusVO;
import co.com.directv.sdii.model.vo.InconsistencyTypeVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Servicio web que expone las operaciones relacionadas con ImportLog
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="ImportLogWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IImportLogWS {

	/**
	 * @param objImportLog
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createImportLog", action="createImportLog")
	public void createImportLog(@WebParam(name="objImportLog")ImportLogVO objImportLog) throws BusinessException;
	
	/**
	 * @param objImportLog
	 * @param importLogItems
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="registerImportLog", action="registerImportLog")
	public Long registerImportLog(@WebParam(name="objImportLog")ImportLogVO objImportLog, @WebParam(name="importLogItems")List<ImportLogItemVO> importLogItems, @WebParam(name="userId") Long userId) throws BusinessException;
	

	/**
	 * @param user
	 * @param importLogId
	 * @param crearblesVO
	 * @param actualizablesVO
	 * @param eliminablesVO
	 * @param inconsistencyItems
	 * @param isFinished
	 */
	@WebMethod(operationName="modifiedImportLog", action="modifiedImportLog")
	public void modifiedImportLog(@WebParam(name="user")UserVO user,@WebParam(name="importLogId")ImportLogVO importLogId,
			@WebParam(name="crearblesVO")List<ImportLogItemVO> crearblesVO,@WebParam(name="actualizablesVO")List<ImportLogItemVO> actualizablesVO,@WebParam(name="eliminablesVO")List<ImportLogItemVO> eliminablesVO,@WebParam(name="inconsistencyItems")List<ImportLogInconsistencyVO> inconsistencyItems,
			@WebParam(name="isFinished")boolean isFinished) throws BusinessException;
			
	/**
	 * @param objImportLog
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteImportLog", action="deleteImportLog")
	public void deleteImportLog(@WebParam(name="objImportLog")ImportLogVO objImportLog, @WebParam(name="userId")Long userId) throws BusinessException;
	
	/**
	 * @param id
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogByIDAndByLogisticOp", action="getImportLogByIDAndByLogisticOp")
	public ImportLogVO getImportLogByIDAndByLogisticOp(@WebParam(name="id")Long id,@WebParam(name="logisticOpId")Long logisticOpId) throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllImportLogs", action="getAllImportLogs")
	public List<ImportLogVO> getAllImportLogs() throws BusinessException;
	
	/**
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllImportLogsByLogisticOp", action="getAllImportLogsByLogisticOp")
	public List<ImportLogVO> getAllImportLogsByLogisticOp(@WebParam(name="logisticOpId")Long logisticOpId) throws BusinessException;
	
	/**
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getPendingImportLogsByLogisticOp", action="getPendingImportLogsByLogisticOp")
	public List<ImportLogVO> getPendingImportLogsByLogisticOp(@WebParam(name="logisticOpId")Long logisticOpId) throws BusinessException;

	/**
	 * @param objImportLogStatus
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createImportLogStatus", action="createImportLogStatus")
	public void createImportLogStatus(@WebParam(name="objImportLogStatus")ImportLogStatusVO objImportLogStatus) throws BusinessException;
	
	/**
	 * @param objImportLogStatus
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateImportLogStatus", action="updateImportLogStatus")
	public void updateImportLogStatus(@WebParam(name="objImportLogStatus")ImportLogStatusVO objImportLogStatus) throws BusinessException;
	
	/**
	 * @param objImportLogStatus
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteImportLogStatus", action="deleteImportLogStatus")
	public void deleteImportLogStatus(@WebParam(name="objImportLogStatus")ImportLogStatusVO objImportLogStatus) throws BusinessException;
	
	/**
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogStatusByID", action="getImportLogStatusByID")
	public ImportLogStatusVO getImportLogStatusByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllImportLogStatuss", action="getAllImportLogStatuss")
	public List<ImportLogStatusVO> getAllImportLogStatuss() throws BusinessException;

	/**
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogStatusByCode", action="getImportLogStatusByCode")
	public ImportLogStatusVO getImportLogStatusByCode(@WebParam(name="code")String code)
			throws BusinessException;

	/**
	 * @param objImportLogInconsistency
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createImportLogInconsistency", action="createImportLogInconsistency")
	public void createImportLogInconsistency(@WebParam(name="objImportLogInconsistency")ImportLogInconsistencyVO objImportLogInconsistency) throws BusinessException;
	
	/**
	 * @param objImportLogInconsistency
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateImportLogInconsistency", action="updateImportLogInconsistency")
	public void updateImportLogInconsistency(@WebParam(name="objImportLogInconsistency")ImportLogInconsistencyVO objImportLogInconsistency) throws BusinessException;
	

	/**
	 * @param objImportLogInconsistency
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteImportLogInconsistency", action="deleteImportLogInconsistency")
	public void deleteImportLogInconsistency(@WebParam(name="objImportLogInconsistency")ImportLogInconsistencyVO objImportLogInconsistency) throws BusinessException;
	
	/**
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogInconsistencyByID", action="getImportLogInconsistencyByID")
	public ImportLogInconsistencyVO getImportLogInconsistencyByID(@WebParam(name="id")Long id) throws BusinessException;
	

	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllImportLogInconsistencys", action="getAllImportLogInconsistencys")
	public List<ImportLogInconsistencyVO> getAllImportLogInconsistencys() throws BusinessException;
	
	/**
	 * @param objImpLogModification
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createImpLogModification", action="createImpLogModification")
	public void createImpLogModification(@WebParam(name="objImpLogModification")ImpLogModificationVO objImpLogModification) throws BusinessException;
	
	/**
	 * @param objImpLogModification
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateImpLogModification", action="updateImpLogModification")
	public void updateImpLogModification(@WebParam(name="objImpLogModification")ImpLogModificationVO objImpLogModification) throws BusinessException;
	
	/**
	 * @param objImpLogModification
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteImpLogModification", action="deleteImpLogModification")
	public void deleteImpLogModification(@WebParam(name="objImpLogModification")ImpLogModificationVO objImpLogModification) throws BusinessException;
	
	/**
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImpLogModificationByID", action="getImpLogModificationByID")
	public ImpLogModificationVO getImpLogModificationByID(@WebParam(name="id")Long id) throws BusinessException;
	
	@WebMethod(operationName="getImpLogModificationByImportLogID", action="getImpLogModificationByImportLogID")
	public List<ImpLogModificationVO> getImpLogModificationByImportLogID(@WebParam(name="id") Long id) throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllImpLogModifications", action="getAllImpLogModifications")
	public List<ImpLogModificationVO> getAllImpLogModifications() throws BusinessException;
	
	/**
	 * @param objImpLogModificationType
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createImpLogModificationType", action="createImpLogModificationType")
	public void createImpLogModificationType(@WebParam(name="objImpLogModificationType")ImpLogModificationTypeVO objImpLogModificationType) throws BusinessException;
	
	/**
	 * @param objImpLogModificationType
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateImpLogModificationType", action="updateImpLogModificationType")
	public void updateImpLogModificationType(@WebParam(name="objImpLogModificationType")ImpLogModificationTypeVO objImpLogModificationType) throws BusinessException;
	
	/**
	 * @param objImpLogModificationType
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteImpLogModificationType", action="deleteImpLogModificationType")
	public void deleteImpLogModificationType(@WebParam(name="objImpLogModificationType")ImpLogModificationTypeVO objImpLogModificationType) throws BusinessException;
	
	/**
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImpLogModificationTypeByID", action="getImpLogModificationTypeByID")
	public ImpLogModificationTypeVO getImpLogModificationTypeByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImpLogModificationTypeByCode", action="getImpLogModificationTypeByCode")
	public ImpLogModificationTypeVO getImpLogModificationTypeByCode(@WebParam(name="code")String  code) throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllImpLogModificationTypes", action="getAllImpLogModificationTypes")
	public List<ImpLogModificationTypeVO> getAllImpLogModificationTypes() throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getActiveImpLogModificationTypes", action="getActiveImpLogModificationTypes")
	public List<ImpLogModificationTypeVO> getActiveImpLogModificationTypes() throws BusinessException;

	/**
	 * @param objInconsistencyStatus
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createInconsistencyStatus", action="createInconsistencyStatus")
	public void createInconsistencyStatus(@WebParam(name="objInconsistencyStatus")InconsistencyStatusVO objInconsistencyStatus) throws BusinessException;
	
	/**
	 * @param objInconsistencyStatus
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateInconsistencyStatus", action="updateInconsistencyStatus")
	public void updateInconsistencyStatus(@WebParam(name="objInconsistencyStatus")InconsistencyStatusVO objInconsistencyStatus) throws BusinessException;
	
	/**
	 * @param objInconsistencyStatus
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteInconsistencyStatus", action="deleteInconsistencyStatus")
	public void deleteInconsistencyStatus(@WebParam(name="objInconsistencyStatus")InconsistencyStatusVO objInconsistencyStatus) throws BusinessException;
	
	/**
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getInconsistencyStatusByID", action="getInconsistencyStatusByID")
	public InconsistencyStatusVO getInconsistencyStatusByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllInconsistencyStatuss", action="getAllInconsistencyStatuss")
	public List<InconsistencyStatusVO> getAllInconsistencyStatuss() throws BusinessException;
	
	/**
	 * @param objInconsistencyType
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createInconsistencyType", action="createInconsistencyType")
	public void createInconsistencyType(@WebParam(name="objInconsistencyType")InconsistencyTypeVO objInconsistencyType) throws BusinessException;
	
	/**
	 * @param objInconsistencyType
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateInconsistencyType", action="updateInconsistencyType")
	public void updateInconsistencyType(@WebParam(name="objInconsistencyType")InconsistencyTypeVO objInconsistencyType) throws BusinessException;
	
	/**
	 * @param objInconsistencyType
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteInconsistencyType", action="deleteInconsistencyType")
	public void deleteInconsistencyType(@WebParam(name="objInconsistencyType")InconsistencyTypeVO objInconsistencyType) throws BusinessException;
	
	/**
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getInconsistencyTypeByID", action="getInconsistencyTypeByID")
	public InconsistencyTypeVO getInconsistencyTypeByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getAllInconsistencyTypes", action="getAllInconsistencyTypes")
	public List<InconsistencyTypeVO> getAllInconsistencyTypes() throws BusinessException;
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getActiveInconsistencyTypes", action="getActiveInconsistencyTypes")
	public List<InconsistencyTypeVO> getActiveInconsistencyTypes() throws BusinessException;

	/**
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getInconsistencyTypeByCode", action="getInconsistencyTypeByCode")
	public InconsistencyTypeVO getInconsistencyTypeByCode(@WebParam(name="code")String code)
			throws BusinessException ;

	/**
	 * @param importLog
	 * @param item
	 * @param logisticOperatorDealerId
	 * @param user
	 * @param qaControl
	 * @throws BusinessException
	 */
	@WebMethod(operationName="confirmSerializedElements", action="confirmSerializedElements")
	public void confirmSerializedElements(@WebParam(name = "dto") ConfirmSerializedElementItemFromImportLogDTO dto) throws BusinessException;
	
	/**
	 * @param modifyImportLogCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogByCriteria", action="getImportLogByCriteria")
	public ImportLogResponse getImportLogByCriteria(@WebParam(name="modifyImportLogCriteria")ModifyImportLogDTO modifyImportLogCriteria, 
			@WebParam(name="userId") Long userId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;

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
	@WebMethod(operationName="getImportLogsToConfirm", action="getImportLogsToConfirm")
    public ImportLogResponse getImportLogsToConfirm(@WebParam(name="modifyImportLogCriteria")ModifyImportLogDTO modifyImportLogCriteria, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * @param importLogId
	 * @param creationDate
	 * @param logisticOpId
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogsByIdCreationDateAndLogisticOp", action="getImportLogsByIdCreationDateAndLogisticOp")
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(@WebParam(name="importLogId")Long importLogId,
			@WebParam(name="creationDate")Date creationDate,@WebParam(name="logisticOpId")Long logisticOpId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * @param importLogId
	 * @param serialized
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getElementsInImportLog", action="getElementsInImportLog")
	public ImportLogItemResponse getElementsInImportLog(@WebParam(name="importLogId")Long importLogId,
			@WebParam(name="serialized")Boolean serialized, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * @param importLogItemId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImpLogConfirmationsByImpLogItemId", action="getImpLogConfirmationsByImpLogItemId")
	public List<ImpLogConfirmationVO> getImpLogConfirmationsByImpLogItemId( @WebParam(name="importLogItemId")Long importLogItemId )throws BusinessException;
	
	/**
	 * @param importLogId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogInconsistenciesByImportLogId", action="getImportLogInconsistenciesByImportLogId")
	public List<ImportLogInconsistencyVO> getImportLogInconsistenciesByImportLogId( @WebParam(name="importLogId")Long importLogId ) throws BusinessException;
	
	/**
	 * @param inconsistencyList
	 * @param importLog
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createImportLogInconsistencies", action="createImportLogInconsistencies")
	public void createImportLogInconsistencies( @WebParam(name="inconsistencyList")List<ImportLogInconsistencyVO> inconsistencyList,@WebParam(name="importLog")ImportLogVO importLog) throws BusinessException;
	

	/**
	 * @param idImportLog
	 * @param creationDate
	 * @param logisticOpId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getInconsisntencyImportLogByIdCreationDateAndLogisticOp", action="getInconsisntencyImportLogByIdCreationDateAndLogisticOp")
	public List<ImportLogVO> getInconsisntencyImportLogByIdCreationDateAndLogisticOp(
			@WebParam(name = "idImportLog") Long idImportLog, @WebParam(name="creationDate")Date creationDate,@WebParam(name="logisticOpId")Long logisticOpId) throws BusinessException;

	/**
	 * @param importLog
	 * @throws BusinessException
	 */
	@WebMethod(operationName="validateToFinishImportLog", action="validateToFinishImportLog")
	public void validateToFinishImportLog(@WebParam(name="importLog")ImportLogVO importLog)
			throws BusinessException;
	
	/**
	 * @param inconsistencyList
	 * @throws BusinessException
	 */
	@WebMethod(operationName="closeImportLogInconsistencies", action="closeImportLogInconsistencies")
	public void closeImportLogInconsistencies( @WebParam(name="inconsistencyList")List<ImportLogInconsistencyVO> inconsistencyList )throws BusinessException;
	
	/**
	 * @param itemList
	 * @param importLog
	 * @throws BusinessException
	 */
	@WebMethod(operationName="addImportLogItemListToImportLog", action="addImportLogItemListToImportLog")
	public void addImportLogItemListToImportLog( @WebParam(name="itemList")List<ImportLogItemVO>itemList,@WebParam(name="importLog")ImportLogVO importLog)throws BusinessException;
	
	/**
	 * @param itemList
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteImportLogItemsInImportLog", action="deleteImportLogItemsInImportLog")
	public void deleteImportLogItemsInImportLog( @WebParam(name="itemList")List<Long>itemList )throws BusinessException;
	
	/**
	 * @param itemList
	 * @throws BusinessException
	 */
	@WebMethod(operationName="modifyImportLogItemQuantity", action="modifyImportLogItemQuantity")
	public void modifyImportLogItemQuantity( @WebParam(name="itemList")List<ImportLogItemVO>itemList )throws BusinessException;
	
	/**
	 * @param elementId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogByElementId", action="getImportLogByElementId")
	public ImportLogVO getImportLogByElementId(@WebParam(name="elementId")Long elementId) throws BusinessException;

	/**
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogItemByID", action="getImportLogItemByID")
	public ImportLogItemVO getImportLogItemByID(@WebParam(name="id")Long id) throws BusinessException;

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
	@WebMethod(operationName="modifyImportLogWithInconsistencies", action="modifyImportLogWithInconsistencies")
	public void modifyImportLogWithInconsistencies(@WebParam(name="user")UserVO user,
			@WebParam(name="importLogId")ImportLogVO importLogId, @WebParam(name="crearblesVO")List<ImportLogItemVO> crearblesVO,
			@WebParam(name="actualizablesVO")List<ImportLogItemVO> actualizablesVO,
			@WebParam(name="eliminablesVO")List<ImportLogItemVO> eliminablesVO,
			@WebParam(name="inconsistencyItems")List<ImportLogInconsistencyVO> inconsistencyItems,
			@WebParam(name="isFinished")boolean isFinished) throws BusinessException;

	/**
	 * 
	 * @param modifyImportLogCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogInconsistencesByCriteria", action="getImportLogInconsistencesByCriteria")
	public ImportLogResponse getImportLogInconsistencesByCriteria(
			@WebParam(name="modifyImportLogCriteria")ModifyImportLogDTO modifyImportLogCriteria,
			@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * 
	 * @param importLog
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogInconsistencysByImportLog", action="getImportLogInconsistencysByImportLog")
	public List<ImportLogInconsistency> getImportLogInconsistencysByImportLog(
			@WebParam(name="importLog")Long importLog) throws BusinessException;

	/**
	 * @param obj objeto que encapsula los filtros ingresados
	 * @return NotSerializedElementInSelectImportLogToPrintPaginationResponse
	 * @throws BusinessException
	 * @author cduarte
	 */
	@WebMethod(operationName="getAllImportLogByIdDatesStatus", action="getAllImportLogByIdDatesStatus")
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(@WebParam(name="filterImportLogToPrintDTO")FilterImportLogToPrintDTO filterImportLogToPrintDTO, @WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)throws BusinessException;

	/**
	 * 
	 * Metodo: Realiza la creación y actualización de inconsistencias.
	 * 		CU Inv24.
	 * @param inconsistencyListCreate
	 * @param inconsistencyListUpdate
	 * @param addElementInconsistencyList
	 * @param delElementInconsistencyList
	 * @param importLog
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="saveImportLogInconsistencies", action="saveImportLogInconsistencies")
	public void saveImportLogInconsistencies(
			@WebParam(name="inconsistencyListCreate") List<ImportLogInconsistencyVO> inconsistencyListCreate,
			@WebParam(name="inconsistencyListUpdate") List<ImportLogInconsistencyVO> inconsistencyListUpdate,
			@WebParam(name="inconsistencyListClose") List<ImportLogInconsistencyVO> inconsistencyListClose,
			@WebParam(name="inconsistencyListDelete") List<ImportLogInconsistencyVO> inconsistencyListDelete,
			@WebParam(name="addElementInconsistencyList") List<ImportLogInconsistencyVO> addElementInconsistencyList,
			@WebParam(name="delElementInconsistencyList") List<ImportLogInconsistencyVO> delElementInconsistencyList,
			@WebParam(name="importLog") ImportLogVO importLog,
			@WebParam(name="userId") Long userId
			) throws BusinessException;

	/**
	 * 
	 * Metodo: Retorna lista paginada de inconsistencias de un registro de importación
	 * @param importLogId
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getImportLogInconsistenciesRequestCollInfoByImportLogId", action="getImportLogInconsistenciesByImportLogId")
	public ImportLogInconsistencyResponse getImportLogInconsistenciesRequestCollInfoByImportLogId(
			@WebParam(name = "importLogId") Long importLogId, 
			@WebParam(name = "requestCollInfo") RequestCollectionInfo requestCollInfo)
			throws BusinessException;
	

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
	@WebMethod(operationName="modifyImportLog", action="modifyImportLog")
	public void modifyImportLog(@WebParam(name = "creables")List<ImportLogItemVO> creables,
			@WebParam(name = "actualizables")List<ImportLogItemVO> actualizables,
			@WebParam(name = "eliminables")List<ImportLogItemVO> eliminables, 
			@WebParam(name = "userId")Long userId,
			@WebParam(name = "importLogId")ImportLogVO importLogId, 
			@WebParam(name = "isFinish")boolean isFinish) throws BusinessException;
	
}
