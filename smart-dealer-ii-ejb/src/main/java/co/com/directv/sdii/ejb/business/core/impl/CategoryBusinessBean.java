package co.com.directv.sdii.ejb.business.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.CategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Category;
import co.com.directv.sdii.model.vo.CategoryVO;
import co.com.directv.sdii.persistence.dao.core.CategoryDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD Category
 * 
 * Fecha de Creación: Oct 7, 2014
 * @author ssanabri
 * @version 1.0
 * 
 * @see persistence.dao.core.CategoryDAOLocal
 * @see co.com.directv.sdii.ejb.business.core.CategoryBusinessBeanLocal
 */
@Stateless(name="CategoryBusinessBeanLocal",mappedName="ejb/CategoryBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoryBusinessBean extends BusinessBase implements CategoryBusinessBeanLocal {

    @EJB(name="CategoryDAOLocal", beanInterface=CategoryDAOLocal.class)
    private CategoryDAOLocal categoryDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(CategoryBusinessBean.class);

    @Override
	public List<CategoryVO> getParentCategories() throws BusinessException {
		log.debug("== Inicia la operacion getParentCategories/CategoryBusinessBean ==");
		try {
        	
        	List<Category> categories = categoryDAO.getParentCategories();
        	List<CategoryVO> categoryVOs = UtilsBusiness.convertList(categories, CategoryVO.class);	 
        	
        	return categoryVOs;
        } catch(Throwable ex){
        	log.error("== Error en la operacion getParentCategories/CategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getParentCategories/CategoryBusinessBean ==");
        }		
	}
    
    @Override
	public List<CategoryVO> getChildrenCategories(Long parentId) throws BusinessException {
		log.debug("== Inicia la operacion getChildrenCategories/CategoryBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(parentId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
        	List<Category> categories = categoryDAO.getChildrenCategories(parentId);
        	List<CategoryVO> categoryVOs = UtilsBusiness.convertList(categories, CategoryVO.class);	 
        	
        	return categoryVOs;
        } catch(Throwable ex){
        	log.error("== Error en la operacion getChildrenCategories/CategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getChildrenCategories/CategoryBusinessBean ==");
        }		
	}
	
}
