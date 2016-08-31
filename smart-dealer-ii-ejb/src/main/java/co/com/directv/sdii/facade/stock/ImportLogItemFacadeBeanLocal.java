package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogItemDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ImportLogItem.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogItemFacadeBeanLocal {

    /**
     * @return
     * @throws BusinessException
     */
    public List<ImportLogItemVO> getAllImportLogItems() throws BusinessException;

    /**
     * @param id
     * @return
     * @throws BusinessException
     */
    public ImportLogItemVO getImportLogItemByID(Long id) throws BusinessException;

	/**
	 * @param obj
	 * @throws BusinessException
	 */
	public void createImportLogItem(ImportLogItemVO obj) throws BusinessException;

	/**
	 * @param obj
	 * @throws BusinessException
	 */
	public void updateImportLogItem(ImportLogItemVO obj) throws BusinessException;

	/**
	 * @param obj
	 * @throws BusinessException
	 */
	public void deleteImportLogItem(ImportLogItemVO obj) throws BusinessException;

	/**
	 * @param importLogId
	 * @param isSerialized
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	/*public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			Long importLogId, boolean isSerialized,RequestCollectionInfo requestCollInfo) throws BusinessException;*/

	/**
	 * 
	 * @param impLogItems2Confirm
	 * @param userId
	 * @param logisticOperatorDealerId
	 * @param isWHQ
	 * @throws BusinessException
	 */
	public void confirmNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, boolean isWHQ)
			throws BusinessException;


	
	/**
	 * @param inconsistentLogItems
	 * @param comment
	 * @param incTypeId
	 * @param userId
	 * @throws BusinessException
	 */
	public void reportInconsistentItems(
			List<ImportLogItemVO> inconsistentLogItems, String comment, Long incTypeId,
			Long userId) throws BusinessException;
	
	/**
	 * @param importLogId
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogId(Long importLogId,RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	/**
	 * @param idImportLog
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogWithConfirmations(Long idImportLog,RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * @param pImpLogId
	 * @param pImpLogItems
	 * @param logisticOpId
	 * @throws BusinessException
	 */
	public void confirmSerializedElementsImportLog(Long pImpLogId,	List<ImportLogItemVO> pImpLogItems, Long logisticOpId) throws BusinessException;

	/**
	 * @param pImpLogId
	 * @param impLogItems
	 * @param comment
	 * @param pUserId
	 * @param incType
	 * @param logisticOpId
	 * @throws BusinessException
	 */
	public void reportInconsistentSerializedImpLogElements(Long pImpLogId, List<ImportLogItemVO> impLogItems, String comment, Long pUserId, Long incType,Long logisticOpId) throws BusinessException;

	/**
	 * @param impLogItems2Confirm
	 * @param userId
	 * @param logisticOperatorDealerId
	 * @throws BusinessException
	 */
	public void confirmSerializedAndNotSerializedImportLogItems(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, Dealer dealer)
			throws BusinessException;

	/**
	 * 
	 * @param modifyImportLogItemCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException 
	 */
	public ImportLogItemResponse getImportLogByCriteria(
			ModifyImportLogItemDTO modifyImportLogItemCriteria,
			RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * 
	 * @param importLogItemVO
	 * @throws BusinessException 
	 */
	public void updateQuantityItemImportLogNotSerializedElements(
			List<ImportLogItemVO> importLogItemVO) throws BusinessException;

	/**
	 * 
	 * @param impLogItems2Confirm
	 * @param userId
	 * @param logisticOperatorDealerId
	 * @param company
	 * @param codWHDestiny
	 * @throws BusinessException 
	 */
	public void confirmNotSerializedImportLogItemsCompany(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, long company, String codWHDestiny) throws BusinessException;

	/**
	 * 
	 * @param impLogItems2Confirm
	 * @param userId
	 * @param logisticOperatorDealerId
	 * @param company
	 * @param codWHDestiny
	 * @throws BusinessException 
	 */
	public void confirmNotSerializedImportLogItemsLogisticOperator(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, long company, String codWHDestiny) throws BusinessException;

	/**
	 * 
	 * Metodo: Metodo encargado de consultar los items del registro de importacion
	 * po import log id y si es serialzizado
	 * @param importLogId
	 * @param isSerialized
	 * @param requestCollInfo
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements,
			RequestCollectionInfo requestCollInfo) throws BusinessException;	
}
