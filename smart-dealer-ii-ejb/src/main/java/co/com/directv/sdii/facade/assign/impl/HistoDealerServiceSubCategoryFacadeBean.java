package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.HistoDealerServiceSubCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.HistoDealerServiceSubCategoryFacadeBeanLocal;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD HistoDealerServiceSubCategory
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.HistoDealerServiceSubCategoryFacadeLocal
 */
@Stateless(name="HistoDealerServiceSubCategoryFacadeLocal",mappedName="ejb/HistoDealerServiceSubCategoryFacadeLocal")
public class HistoDealerServiceSubCategoryFacadeBean implements HistoDealerServiceSubCategoryFacadeBeanLocal {

		
    @EJB(name="HistoDealerServiceSubCategoryBusinessBeanLocal", beanInterface=HistoDealerServiceSubCategoryBusinessBeanLocal.class)
    private HistoDealerServiceSubCategoryBusinessBeanLocal businessHistoDealerServiceSubCategory;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerServiceSubCategoryFacadeLocal#getAllHistoDealerServiceSubCategorys()
     */
    public List<HistoDealerServiceSubCategoryVO> getAllHistoDealerServiceSubCategorys() throws BusinessException {
    	return businessHistoDealerServiceSubCategory.getAllHistoDealerServiceSubCategorys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerServiceSubCategoryFacadeLocal#getHistoDealerServiceSubCategorysByID(java.lang.Long)
     */
    public HistoDealerServiceSubCategoryVO getHistoDealerServiceSubCategoryByID(Long id) throws BusinessException {
    	return businessHistoDealerServiceSubCategory.getHistoDealerServiceSubCategoryByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerServiceSubCategoryFacadeLocal#createHistoDealerServiceSubCategory(co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO)
	 */
	public void createHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException {
		businessHistoDealerServiceSubCategory.createHistoDealerServiceSubCategory(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerServiceSubCategoryFacadeLocal#updateHistoDealerServiceSubCategory(co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO)
	 */
	public void updateHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException {
		businessHistoDealerServiceSubCategory.updateHistoDealerServiceSubCategory(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerServiceSubCategoryFacadeLocal#deleteHistoDealerServiceSubCategory(co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO)
	 */
	public void deleteHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException {
		businessHistoDealerServiceSubCategory.deleteHistoDealerServiceSubCategory(obj);
	}
}
