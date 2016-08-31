package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogItemDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ImportLogItem
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeLocal
 */
@Stateless(name="ImportLogItemFacadeLocal",mappedName="ejb/ImportLogItemFacadeLocal")
public class ImportLogItemFacadeBean implements ImportLogItemFacadeBeanLocal {

		
    @EJB(name="ImportLogItemBusinessBeanLocal", beanInterface=ImportLogItemBusinessBeanLocal.class)
    private ImportLogItemBusinessBeanLocal businessImportLogItem;
    
   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#getAllImportLogItems()
     */
    public List<ImportLogItemVO> getAllImportLogItems() throws BusinessException {
    	return businessImportLogItem.getAllImportLogItems();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#getImportLogItemByID(java.lang.Long)
     */
    public ImportLogItemVO getImportLogItemByID(Long id) throws BusinessException {
    	return businessImportLogItem.getImportLogItemByID(id);
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#createImportLogItem(co.com.directv.sdii.model.vo.ImportLogItemVO)
	 */
	public void createImportLogItem(ImportLogItemVO obj) throws BusinessException {
		businessImportLogItem.createImportLogItem(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#updateImportLogItem(co.com.directv.sdii.model.vo.ImportLogItemVO)
	 */
	public void updateImportLogItem(ImportLogItemVO obj) throws BusinessException {
		businessImportLogItem.updateImportLogItem(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#deleteImportLogItem(co.com.directv.sdii.model.vo.ImportLogItemVO)
	 */
	public void deleteImportLogItem(ImportLogItemVO obj) throws BusinessException {
		businessImportLogItem.deleteImportLogItem(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#getImportLogItemsByImportLogIdAndIsSerialized(java.lang.Long, boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	/*public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			Long importLogId, boolean isSerialized,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLogId, isSerialized,requestCollInfo);
	}*/
	
	@Override
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements,
			RequestCollectionInfo requestCollInfo) throws BusinessException { 
		return businessImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(filterImportLogElements, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#confirmNotSerializedImportLogItems(java.util.List, java.lang.Long, java.lang.Long)
	 */
	public void confirmNotSerializedImportLogItems(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId)
			throws BusinessException {
		businessImportLogItem.confirmNotSerializedImportLogItems(impLogItems2Confirm, userId, logisticOperatorDealerId, true);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#reportInconsistentItems(java.util.List, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public void reportInconsistentItems(
			List<ImportLogItemVO> inconsistentLogItems, String comment, Long incTypeId,
			Long userId) throws BusinessException {
		businessImportLogItem.reportInconsistentItems(inconsistentLogItems, comment, incTypeId, userId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#getImportLogItemsByImportLogId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogId(Long importLogId,RequestCollectionInfo requestCollInfo) throws BusinessException{
		return businessImportLogItem.getImportLogItemsByImportLog(importLogId, requestCollInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#getImportLogItemsByImportLogWithConfirmations(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogWithConfirmations(Long idImportLog,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessImportLogItem.getImportLogItemsByImportLogWithConfirmations(idImportLog,requestCollInfo);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#confirmSerializedElementsImportLog(java.lang.Long, java.util.List, java.lang.Long)
	 */
	public void confirmSerializedElementsImportLog(Long pImpLogId,	List<ImportLogItemVO> pImpLogItems, Long logisticOpId) throws BusinessException {
		businessImportLogItem.confirmSerializedElementsImportLog(pImpLogId, pImpLogItems, logisticOpId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#reportInconsistentSerializedImpLogElements(java.lang.Long, java.util.List, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public void reportInconsistentSerializedImpLogElements(Long pImpLogId, List<ImportLogItemVO> impLogItems, String comment, Long pUserId, Long incType,Long logisticOpId) throws BusinessException {
		businessImportLogItem.reportInconsistentSerializedImpLogElements(pImpLogId, impLogItems, comment, pUserId, incType, logisticOpId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#confirmSerializedAndNotSerializedImportLogItems(java.util.List, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void confirmSerializedAndNotSerializedImportLogItems(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, Dealer dealer)
			throws BusinessException {
		businessImportLogItem.confirmSerializedAndNotSerializedImportLogItems(impLogItems2Confirm, userId, logisticOperatorDealerId, dealer);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal#confirmNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, boolean isWHQ)
	 */
	public void confirmNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, boolean isWHQ)
	throws BusinessException {
		businessImportLogItem.confirmNotSerializedImportLogItems(impLogItems2Confirm, userId, logisticOperatorDealerId, isWHQ);
	}

	@Override
	public ImportLogItemResponse getImportLogByCriteria(
			ModifyImportLogItemDTO modifyImportLogItemCriteria,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		
		return businessImportLogItem.getImportLogItemsByCriteria(modifyImportLogItemCriteria, requestCollInfo);
	}

	@Override
	public void updateQuantityItemImportLogNotSerializedElements(
			List<ImportLogItemVO> importLogItemVO) throws BusinessException {
		businessImportLogItem.updateQuantityItemImportLogNotSerializedElements(importLogItemVO);
		
	}

	@Override
	public void confirmNotSerializedImportLogItemsCompany(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, long company, String codWHDestiny) throws BusinessException {
		businessImportLogItem.confirmNotSerializedImportLogItemsCompany(impLogItems2Confirm, userId, logisticOperatorDealerId, company, codWHDestiny);
		
	}

	@Override
	public void confirmNotSerializedImportLogItemsLogisticOperator(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, long company, String codWHDestiny) throws BusinessException {
		businessImportLogItem.confirmNotSerializedImportLogItemsLogisticOperator(impLogItems2Confirm, userId, logisticOperatorDealerId, company, codWHDestiny);
		
	}


}
