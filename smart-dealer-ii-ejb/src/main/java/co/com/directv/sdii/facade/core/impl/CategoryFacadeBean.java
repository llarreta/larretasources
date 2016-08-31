package co.com.directv.sdii.facade.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.core.CategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CategoryFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CategoryVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Category
 * 
 * Fecha de Creación: Oct 7, 2014
 * @author ssanabri
 * @version 1.0
 * 
 * @see facade.core.CategoryFacadeLocal
 */
@Stateless(name="CategoryFacadeLocal",mappedName="ejb/CategoryFacadeLocal")
public class CategoryFacadeBean implements CategoryFacadeBeanLocal {

    @EJB(name="CategoryBusinessBeanLocal", beanInterface=CategoryBusinessBeanLocal.class)
    private CategoryBusinessBeanLocal businessCategory;
        
	@Override
	public List<CategoryVO> getParentCategories() throws BusinessException {
		return businessCategory.getParentCategories();
	}

	@Override
	public List<CategoryVO> getChildrenCategories(Long parentId) throws BusinessException {
		return businessCategory.getChildrenCategories(parentId);
	}
}
