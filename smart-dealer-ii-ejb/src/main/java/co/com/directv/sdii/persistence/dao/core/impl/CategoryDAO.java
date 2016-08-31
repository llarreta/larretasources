package co.com.directv.sdii.persistence.dao.core.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Category;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.CategoryDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Category
 * 
 * Fecha de Creación:Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Category
 * @see co.com.directv.sdii.model.hbm.Category.hbm.xml
 */
@Stateless(name="CategoryDAOLocal",mappedName="ejb/CategoryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoryDAO extends BaseDao implements CategoryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CategoryDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.CategoryDAOLocal#createCategory(co.com.directv.sdii.model.pojo.Category)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCategory(Category obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCategory/CategoryDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el Category ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createCategory/CategoryDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.CategoryDAOLocal#updateCategory(co.com.directv.sdii.model.pojo.Category)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCategory(Category obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateCategory/CategoryDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el Category ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateCategory/CategoryDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.CategoryDAOLocal#deleteCategory(co.com.directv.sdii.model.pojo.Category)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCategory(Category obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteCategory/CategoryDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Category entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el Category ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteCategory/CategoryDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.CategoryDAOLocal#getCategorysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Category getCategoryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCategoryByID/CategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Category.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Category) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCategoryByID/CategoryDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.CategoryDAOLocal#getParentCategories()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Category> getParentCategories() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getParentCategories/CategoryDAO ==");
        Session session = super.getSession();
        List<Category> result = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Category.class.getName());
        	stringQuery.append(" entity where entity.category.id is null and entity.filterCriteria = :filter ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("filter", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	result = query.list();
        	return result;
            
        } catch (Throwable ex){
			log.error("== Error getParentCategories/CategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getParentCategories/CategoryDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.CategoryDAOLocal#getChildrenCategories()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Category> getChildrenCategories(Long parentId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getChildrenCategories/CategoryDAO ==");
        Session session = super.getSession();
        List<Category> result = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Category.class.getName());
          	stringQuery.append(" entity where entity.category.id = :aParentId and entity.filterCriteria = :filter ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aParentId", parentId);
        	query.setString("filter", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	result = query.list();
        	return result;
            
        } catch (Throwable ex){
			log.error("== Error getChildrenCategories/CategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getChildrenCategories/CategoryDAO ==");
        }
    }

}
