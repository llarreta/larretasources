package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD RefQuantityControlItem
 * 
 * Fecha de Creación: jul 12, 2011
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal
 */
@Stateless(name="RefQuantityControlItemFacadeBeanLocal",mappedName="ejb/RefQuantityControlItemFacadeBeanLocal")
public class RefQuantityControlItemFacadeBean implements
		RefQuantityControlItemFacadeBeanLocal {
	
	 @EJB(name="RefQuantityControlItemBusinessBeanLocal", beanInterface=RefQuantityControlItemBusinessBeanLocal.class)
	    private RefQuantityControlItemBusinessBeanLocal businessRefQtyCtrlItem;

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#createRefQuantityControlItem(co.com.directv.sdii.model.vo.RefQuantityControlItemVO)
	 */
	@Override
	public void createRefQuantityControlItem(RefQuantityControlItemVO obj)
			throws BusinessException {
		businessRefQtyCtrlItem.createRefQuantityControlItem(obj);

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#deleteRefQuantityControlItem(co.com.directv.sdii.model.vo.RefQuantityControlItemVO)
	 */
	@Override
	public void deleteRefQuantityControlItem(RefQuantityControlItemVO obj)
			throws BusinessException {
		businessRefQtyCtrlItem.deleteRefQuantityControlItem(obj);

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#getAllRefQuantityControlItems()
	 */
	@Override
	public List<RefQuantityControlItemVO> getAllRefQuantityControlItems()
			throws BusinessException {
		return businessRefQtyCtrlItem.getAllRefQuantityControlItems();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#getRefQtyCtrlItemsByReference(co.com.directv.sdii.model.vo.RefQuantityControlItemVO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public RefQuantityControlItemsResponse getRefQtyCtrlItemsByReference(
			RefQuantityControlItemVO refId,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessRefQtyCtrlItem.getRefQtyCtrlItemsByReference(refId,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#getRefQuantityControlItemByID(java.lang.Long)
	 */
	@Override
	public RefQuantityControlItemVO getRefQuantityControlItemByID(Long id)
			throws BusinessException {
		return businessRefQtyCtrlItem.getRefQuantityControlItemByID(id);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#updateRefQuantityControlItem(co.com.directv.sdii.model.vo.RefQuantityControlItemVO)
	 */
	@Override
	public void updateRefQuantityControlItem(RefQuantityControlItemVO obj)
			throws BusinessException {
		businessRefQtyCtrlItem.updateRefQuantityControlItem(obj);

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#saveQtyRefCtrlItems(co.com.directv.sdii.model.vo.ReferenceVO, java.util.List)
	 */
	@Override
	public void saveQtyRefCtrlItems(ReferenceVO reference,
			List<RefQuantityControlItemVO> qtyCtrlItems)
			throws BusinessException {
		businessRefQtyCtrlItem.saveQtyRefCtrlItems(reference,qtyCtrlItems);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#getReferencesPreloadByCreationProcess(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public RefQuantityControlItemsResponse getReferencesPreloadByCreationProcess(
			Long idWhSource, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessRefQtyCtrlItem.getReferencesPreloadByCreationProcess(idWhSource,requestCollInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal#validateExistenceOfReferenceQuantityDiferences(java.lang.Long)
	 */
	@Override
	public boolean validateExistenceOfReferenceQuantityDifferences(
			Long referenceId) throws BusinessException {
		return businessRefQtyCtrlItem.validateExistenceOfReferenceQuantityDifferences(referenceId);
	}

}
