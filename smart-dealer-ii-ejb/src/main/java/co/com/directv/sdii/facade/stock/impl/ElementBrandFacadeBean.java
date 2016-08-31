package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementBrandVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ElementBrand
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ElementBrandFacadeLocal
 */
@Stateless(name="ElementBrandFacadeLocal",mappedName="ejb/ElementBrandFacadeLocal")
public class ElementBrandFacadeBean implements ElementBrandFacadeBeanLocal {

		
    @EJB(name="ElementBrandBusinessBeanLocal", beanInterface=ElementBrandBusinessBeanLocal.class)
    private ElementBrandBusinessBeanLocal businessElementBrand;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementBrandFacadeLocal#getAllElementBrands()
     */
    public List<ElementBrandVO> getAllElementBrands() throws BusinessException {
    	return businessElementBrand.getAllElementBrands();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementBrandFacadeLocal#getElementBrandsByID(java.lang.Long)
     */
    public ElementBrandVO getElementBrandByID(Long id) throws BusinessException {
    	return businessElementBrand.getElementBrandByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementBrandFacadeLocal#createElementBrand(co.com.directv.sdii.model.vo.ElementBrandVO)
	 */
	public void createElementBrand(ElementBrandVO obj) throws BusinessException {
		businessElementBrand.createElementBrand(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementBrandFacadeLocal#updateElementBrand(co.com.directv.sdii.model.vo.ElementBrandVO)
	 */
	public void updateElementBrand(ElementBrandVO obj) throws BusinessException {
		businessElementBrand.updateElementBrand(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementBrandFacadeLocal#deleteElementBrand(co.com.directv.sdii.model.vo.ElementBrandVO)
	 */
	public void deleteElementBrand(ElementBrandVO obj) throws BusinessException {
		businessElementBrand.deleteElementBrand(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal#getElementBrandByCode(java.lang.String)
	 */
	public ElementBrandVO getElementBrandByCode(String code)
			throws BusinessException {
		return businessElementBrand.getElementBrandByCode(code);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal#getElementBrandByActiveStatus()
	 */
	public List<ElementBrandVO> getElementBrandByActiveStatus()
			throws BusinessException {
		return businessElementBrand.getElementBrandByActiveStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal#getElementBrandByActiveStatus(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ElementBrandResponse getElementBrandByActiveStatus(RequestCollectionInfo requestCollInfo)
		throws BusinessException {
		return businessElementBrand.getElementBrandByActiveStatus(requestCollInfo);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal#getElementBrandByInActiveStatus()
	 */
	public List<ElementBrandVO> getElementBrandByInActiveStatus()
			throws BusinessException {
		return businessElementBrand.getElementBrandByInActiveStatus();
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal#getElementBrandByAllStatuPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementBrandResponse getElementBrandByAllStatuPage(String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessElementBrand.getElementBrandByAllStatuPage(code,requestCollInfo);
	}
	
}
