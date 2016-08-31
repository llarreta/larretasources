package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ElementTypeAndModelDTO;
import co.com.directv.sdii.model.pojo.collection.ElementModelResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementModelVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ElementModel
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeLocal
 */
@Stateless(name="ElementModelFacadeLocal",mappedName="ejb/ElementModelFacadeLocal")
public class ElementModelFacadeBean implements ElementModelFacadeBeanLocal {

		
    @EJB(name="ElementModelBusinessBeanLocal", beanInterface=ElementModelBusinessBeanLocal.class)
    private ElementModelBusinessBeanLocal businessElementModel;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementModelFacadeLocal#getAllElementModels()
     */
    public ElementModelResponse getAllElementModels(RequestCollectionInfo requestCollInfo) throws BusinessException {
    	return businessElementModel.getAllElementModels(requestCollInfo);
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementModelFacadeLocal#getElementModelsByID(java.lang.Long)
     */
    public ElementModelVO getElementModelByID(Long id) throws BusinessException {
    	return businessElementModel.getElementModelByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementModelFacadeLocal#createElementModel(co.com.directv.sdii.model.vo.ElementModelVO)
	 */
	public void createElementModel(ElementModelVO obj) throws BusinessException {
		businessElementModel.createElementModel(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementModelFacadeLocal#updateElementModel(co.com.directv.sdii.model.vo.ElementModelVO)
	 */
	public void updateElementModel(ElementModelVO obj) throws BusinessException {
		businessElementModel.updateElementModel(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementModelFacadeLocal#deleteElementModel(co.com.directv.sdii.model.vo.ElementModelVO)
	 */
	public void deleteElementModel(ElementModelVO obj) throws BusinessException {
		businessElementModel.deleteElementModel(obj);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelByCode(java.lang.String)
	 */
	public ElementModelVO getElementModelByCode(String code)
			throws BusinessException {
		return businessElementModel.getElementModelByCode(code);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByActiveStatus()
	 */
	public List<ElementModelVO> getElementModelsByActiveStatus()
			throws BusinessException {
		return businessElementModel.getElementModelsByActiveStatus();
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByInActiveStatus()
	 */
	public List<ElementModelVO> getElementModelsByInActiveStatus()
			throws BusinessException {
		return businessElementModel.getElementModelsByInActiveStatus();
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClass(java.lang.String)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClass(
			String codeElementClass) throws BusinessException {
		return businessElementModel.getElementModelsByElementClass(codeElementClass);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClassAndActiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassAndActiveStatus(
			String codeElementClass) throws BusinessException {
		return businessElementModel.getElementModelsByElementClassAndActiveStatus(codeElementClass);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClassAndInactiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassAndInactiveStatus(
			String codeElementClass) throws BusinessException {
		return businessElementModel.getElementModelsByElementClassAndInactiveStatus(codeElementClass);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClassId(java.lang.Long)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassId(
			Long idElementClass) throws BusinessException {
		
		return businessElementModel.getElementModelsByElementClassId(idElementClass);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClassIdAndActiveStatus(java.lang.Long)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassIdAndActiveStatus(
			Long idElementClass) throws BusinessException {
		return businessElementModel.getElementModelsByElementClassIdAndActiveStatus(idElementClass);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClassIdAndActiveStatus(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementModelResponse getElementModelsByElementClassIdAndActiveStatus(
			Long idElementClass,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessElementModel.getElementModelsByElementClassIdAndActiveStatus(idElementClass,requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClassIdAndInactiveStatus(java.lang.Long)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassIdAndInactiveStatus(
			Long idElementClass) throws BusinessException {
		return businessElementModel.getElementModelsByElementClassIdAndInactiveStatus(idElementClass);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByElementClassIdAndAllStatusPage(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementModelResponse getElementModelsByElementClassIdAndAllStatusPage(
			Long idElementClass, String code,RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessElementModel.getElementModelsByElementClassIdAndAllStatusPage(idElementClass, code,requestCollInfo);
	}


	@Override
	public List<ElementModelVO> getElementModelsByActiveStatusAndIsPrepaid(
			String isPrepaid)throws BusinessException {
		return businessElementModel.getElementModelsByActiveStatusAndIsPrepaid(isPrepaid);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal#getElementModelsByWarehouseIdAndElementModelId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public ElementTypeAndModelDTO getElementModelsByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId)throws BusinessException {
		return businessElementModel.getElementModelsByWarehouseIdAndElementModelId(warehouseId,elementModelId);
	}
	
}
