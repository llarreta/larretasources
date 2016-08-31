package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.model.dto.collection.ReferenceElementItemsDTO;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ReferenceElementItem
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeLocal
 */
@Stateless(name="ReferenceElementItemFacadeLocal",mappedName="ejb/ReferenceElementItemFacadeLocal")
public class ReferenceElementItemFacadeBean implements ReferenceElementItemFacadeBeanLocal {

		
    @EJB(name="ReferenceElementItemBusinessBeanLocal", beanInterface=ReferenceElementItemBusinessBeanLocal.class)
    private ReferenceElementItemBusinessBeanLocal businessReferenceElementItem;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceElementItemFacadeLocal#getAllReferenceElementItems()
     */
    public List<ReferenceElementItemVO> getAllReferenceElementItems() throws BusinessException {
    	return businessReferenceElementItem.getAllReferenceElementItems();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceElementItemFacadeLocal#getReferenceElementItemsByID(java.lang.Long)
     */
    public ReferenceElementItemVO getReferenceElementItemByID(Long id) throws BusinessException {
    	return businessReferenceElementItem.getReferenceElementItemByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceElementItemFacadeLocal#createReferenceElementItem(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	public void createReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException {
		businessReferenceElementItem.createReferenceElementItem(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceElementItemFacadeLocal#updateReferenceElementItem(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	public void updateReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException {
		businessReferenceElementItem.updateReferenceElementItem(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceElementItemFacadeLocal#deleteReferenceElementItem(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	public void deleteReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException {
		businessReferenceElementItem.deleteReferenceElementItem(obj);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#getReferenceElementItemsByReferenceID(java.lang.Long)
	 */
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessReferenceElementItem.getReferenceElementItemsByReferenceID(refID, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#getNotSerializedReferenceElementItemByReferenceId(java.lang.Long)
	 */
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceId(Long referenceId) throws BusinessException{
		return businessReferenceElementItem.getNotSerializedReferenceElementItemByReferenceId(referenceId);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#receptRefSerialElementItem(java.util.List)
	 */
	public void receptRefSerialElementItem(
			List<ReferenceElementItemVO> refElements) throws BusinessException {
		businessReferenceElementItem.receptRefSerialElementItem(refElements);
		
	}


	@Override 
	public ReferenceElementItemsDTO getReferenceElementSerializedNotSerialized(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {		
		return businessReferenceElementItem.getReferenceElementSerializedNotSerialized(refID, requestCollInfo);
	}


//	
//	/*
//	 * (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#addElementToReference(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
//	 */
//	@Override
//	public void addElementToReference(ReferenceElementItemVO refElementVO) throws BusinessException {
//		businessReferenceElementItem.addElementToReference(refElementVO);
//		
//	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#removeElementOfReference(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	@Override
	public void removeElementOfReference(ReferenceElementItemVO item, Long userId) throws BusinessException {
		this.businessReferenceElementItem.removeElementOfReference(item, userId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#getReferenceElementsByReferenceIdAndSerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long, boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementMixedResponse getReferenceElementsByReferenceIdAndSerialOrElementType(
			Long refID, String serial, Long elementTypeId, boolean isSerialized, RequestCollectionInfo requestCollInfo) throws BusinessException {		
		return this.businessReferenceElementItem.getReferenceElementsByReferenceIdAndSerialOrElementType(refID, serial, elementTypeId, isSerialized, requestCollInfo);
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#partialReceptionOfReferenceElementItem(java.util.List, java.lang.Long)
	 */
	@Override
	public void partialReceptionOfReferenceElementItem(List<ReferenceElementItemVO> elements,Long userId) throws BusinessException {
		this.businessReferenceElementItem.partialReceptionOfReferenceElementItem(elements,userId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#getAddedElements(java.lang.Long)
	 */
	@Override
	public List<ReferenceElementItemVO> getAddedElements(Long refInconsistencyId)
			throws BusinessException {
		return businessReferenceElementItem.getReferenceElementsByRefInconsistencyId(refInconsistencyId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal#getReferenceElementItemsByReferenceIDAndSerOrNotSer(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo, boolean)
	 */
	@Override
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDAndSerOrNotSer(Long refID, RequestCollectionInfo requestCollInfo,boolean isSerialized) throws BusinessException {
		return this.businessReferenceElementItem.getReferenceElementItemsByReferenceIDAndSerOrNotSer(refID, requestCollInfo, isSerialized);
	}


	@Override
	public void addElementSerialized(String serialCode, 
			Long referenceID, boolean isPrepaid, Long userId) throws BusinessException {
		this.businessReferenceElementItem.addElementSerialized(serialCode, referenceID, isPrepaid, userId);
		
	}


	@Override
	public void addElementNotSerialized(
			List<ReferenceElementItemVO> listElementNotSerializedToAdd, Long referenceID, Long userId)throws BusinessException {
		this.businessReferenceElementItem.addElementNotSerialized(listElementNotSerializedToAdd, referenceID, userId);
		
	}


	@Override
	public ReferenceElementItemsResponse getElementNotSerializedFromWarehouse(
			RequestCollectionInfo requestCollInfo, Long referenceID,
			boolean isPrepaid, Long elementTypeId, Long modelId) throws BusinessException {
		return this.businessReferenceElementItem.getElementNotSerializedFromWarehouse(requestCollInfo, referenceID, isPrepaid, elementTypeId, modelId);
	}


	@Override
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDSerAndNotSerPending(
			Long refID, RequestCollectionInfo requestCollInfo, String serialCode, boolean isSerialized)
			throws BusinessException {
		return this.businessReferenceElementItem.getReferenceElementItemsByReferenceIDSerAndNotSerPending(refID, requestCollInfo, serialCode, isSerialized);
	}
	
}
