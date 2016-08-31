package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementClassVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ElementClass
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ElementClassFacadeLocal
 */
@Stateless(name="ElementClassFacadeLocal",mappedName="ejb/ElementClassFacadeLocal")
public class ElementClassFacadeBean implements ElementClassFacadeBeanLocal {

		
    @EJB(name="ElementClassBusinessBeanLocal", beanInterface=ElementClassBusinessBeanLocal.class)
    private ElementClassBusinessBeanLocal businessElementClass;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementClassFacadeLocal#getAllElementClasss()
     */
    public List<ElementClassVO> getAllElementClasss() throws BusinessException {
    	return businessElementClass.getAllElementClasss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementClassFacadeLocal#getElementClasssByID(java.lang.Long)
     */
    public ElementClassVO getElementClassByID(Long id) throws BusinessException {
    	return businessElementClass.getElementClassByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementClassFacadeLocal#createElementClass(co.com.directv.sdii.model.vo.ElementClassVO)
	 */
	public void createElementClass(ElementClassVO obj) throws BusinessException {
		businessElementClass.createElementClass(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementClassFacadeLocal#updateElementClass(co.com.directv.sdii.model.vo.ElementClassVO)
	 */
	public void updateElementClass(ElementClassVO obj) throws BusinessException {
		businessElementClass.updateElementClass(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementClassFacadeLocal#deleteElementClass(co.com.directv.sdii.model.vo.ElementClassVO)
	 */
	public void deleteElementClass(ElementClassVO obj) throws BusinessException {
		businessElementClass.deleteElementClass(obj);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal#getElementClassByCode(java.lang.String)
	 */
	public ElementClassVO getElementClassByCode(String code)
			throws BusinessException {
		return businessElementClass.getElementClassByCode(code);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal#getElementClassByActiveStatus()
	 */
	public List<ElementClassVO> getElementClassByActiveStatus()
			throws BusinessException {
		return businessElementClass.getElementClassByActiveStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal#getElementClassByActiveStatus(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ElementClassResponse getElementClassByActiveStatus(RequestCollectionInfo requestCollInfo) throws BusinessException{
		return businessElementClass.getElementClassByActiveStatus(requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal#getElementClassByInactiveStatus()
	 */
	public List<ElementClassVO> getElementClassByInactiveStatus()
			throws BusinessException {
		return businessElementClass.getElementClassByInactiveStatus();
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal#getElementClassByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementClassResponse getElementClassByAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessElementClass.getElementClassByAllStatusPage(code,requestCollInfo);
	}
	
}
