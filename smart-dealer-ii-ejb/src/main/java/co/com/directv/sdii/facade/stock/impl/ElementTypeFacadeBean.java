package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ElementType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeLocal
 */
@Stateless(name="ElementTypeFacadeLocal",mappedName="ejb/ElementTypeFacadeLocal")
public class ElementTypeFacadeBean implements ElementTypeFacadeBeanLocal {

		
    @EJB(name="ElementTypeBusinessBeanLocal", beanInterface=ElementTypeBusinessBeanLocal.class)
    private ElementTypeBusinessBeanLocal businessElementType;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementTypeFacadeLocal#getAllElementTypes()
     */
    public List<ElementTypeVO> getAllElementTypes() throws BusinessException {
    	return businessElementType.getAllElementTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementTypeFacadeLocal#getElementTypesByID(java.lang.Long)
     */
    public ElementTypeVO getElementTypeByID(Long id) throws BusinessException {
    	return businessElementType.getElementTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementTypeFacadeLocal#createElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	public void createElementType(ElementTypeVO obj) throws BusinessException {
		businessElementType.createElementType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementTypeFacadeLocal#updateElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	public void updateElementType(ElementTypeVO obj) throws BusinessException {
		businessElementType.updateElementType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementTypeFacadeLocal#deleteElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	public void deleteElementType(ElementTypeVO obj) throws BusinessException {
		businessElementType.deleteElementType(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypeByCode(java.lang.String)
	 */
	public ElementTypeVO getElementTypeByCode(String code)
			throws BusinessException {
		return businessElementType.getElementTypeByCode(code);
	}
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByActive()
	 */
	public List<ElementTypeVO> getElementTypesByActive()
			throws BusinessException {
		return businessElementType.getElementTypesByActive();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByActiveAndIsSerialized(boolean)
	 */
	public List<ElementTypeVO> getElementTypesByActiveAndIsSerialized(boolean isSerialized)
		throws BusinessException {
		return businessElementType.getElementTypesByActiveAndIsSerialized(isSerialized);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByElementModel(java.lang.String)
	 */
	@Override
	public List<ElementTypeVO> getElementTypesByElementModel(
			String codeElementModel) throws BusinessException {
		return businessElementType.getElementTypesByElementModel(codeElementModel);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByElementModelAndActiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementTypeVO> getElementTypesByElementModelAndActiveStatus(
			String codeElementModel) throws BusinessException {
		return businessElementType.getElementTypesByElementModelAndActiveStatus(codeElementModel);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByElementModelAndInactiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementTypeVO> getElementTypesByElementModelAndInactiveStatus(
			String codeElementModel) throws BusinessException {
		return businessElementType.getElementTypesByElementModelAndInactiveStatus(codeElementModel);
	}


	@Override
	public List<ElementTypeVO> getElementTypesByElementModelId(
			Long idElementModel) throws BusinessException {
		return businessElementType.getElementTypesByElementModelId(idElementModel);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByElementModelIdAndActiveStatus(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementTypeResponse getElementTypesByElementModelIdAndActiveStatus(
			Long idElementModel,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessElementType.getElementTypesByElementModelIdAndActiveStatus(idElementModel,requestCollInfo);
	}
	
	@Override
	public List<ElementTypeVO> getElementTypesByElementModelIdAndActiveStatus(
			Long idElementModel) throws BusinessException {
		return businessElementType.getElementTypesByElementModelIdAndActiveStatus(idElementModel);
	}


	@Override
	public List<ElementTypeVO> getElementTypesByElementModelIdAndInactiveStatus(
			Long idElementModel) throws BusinessException {
		return businessElementType. getElementTypesByElementModelIdAndInactiveStatus(idElementModel);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByElementModelIdAndAllStatusPage(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementTypeResponse getElementTypesByElementModelIdAndAllStatusPage(
			Long idElementModel, String code,RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessElementType. getElementTypesByElementModelIdAndAllStatusPage(idElementModel,code,requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal#getElementTypesByPrepaidIdAndActiveStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementTypeResponse getElementTypesByPrepaidIdAndActiveStatusPage(
			String prepaid, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessElementType. getElementTypesByPrepaidIdAndActiveStatusPage(prepaid,requestCollInfo);
	}


	@Override
	public List<ElementTypeVO> getElementTypeByElementModelAndIsSerialized(
			String elementModelCode, boolean isSerialized)
			throws BusinessException {
		return businessElementType.getElementTypeByElementModelAndIsSerialized(elementModelCode, isSerialized);
	}


	@Override
	public List<ElementTypeVO> getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(
			String isPrepaid, String isSerialized) throws BusinessException {
		return businessElementType.getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(isPrepaid, isSerialized);
	}
	
	@Override
    public List<ElementTypeVO> getElementTypesByModelStatusAndIsSerialized(Boolean isSerialized, String elementModelCode,Boolean elementTypeStatus) throws BusinessException {
    	return businessElementType.getElementTypesByModelStatusAndIsSerialized( isSerialized,  elementModelCode, elementTypeStatus);
    }
}
