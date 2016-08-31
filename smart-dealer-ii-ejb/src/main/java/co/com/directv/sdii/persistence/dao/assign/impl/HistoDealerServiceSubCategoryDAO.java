package co.com.directv.sdii.persistence.dao.assign.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.HistoDealerServiceSubCategory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad HistoDealerServiceSubCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.HistoDealerServiceSubCategory
 * @see co.com.directv.sdii.model.hbm.HistoDealerServiceSubCategory.hbm.xml
 */
@Stateless(name="HistoDealerServiceSubCategoryDAOLocal",mappedName="ejb/HistoDealerServiceSubCategoryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerServiceSubCategoryDAO extends BaseDao implements HistoDealerServiceSubCategoryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerServiceSubCategoryDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal#createHistoDealerServiceSubCategory(co.com.directv.sdii.model.pojo.HistoDealerServiceSubCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createHistoDealerServiceSubCategory(HistoDealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el HistoDealerServiceSubCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal#updateHistoDealerServiceSubCategory(co.com.directv.sdii.model.pojo.HistoDealerServiceSubCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateHistoDealerServiceSubCategory(HistoDealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el HistoDealerServiceSubCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal#deleteHistoDealerServiceSubCategory(co.com.directv.sdii.model.pojo.HistoDealerServiceSubCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteHistoDealerServiceSubCategory(HistoDealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from HistoDealerServiceSubCategory entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el HistoDealerServiceSubCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal#getHistoDealerServiceSubCategorysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerServiceSubCategory getHistoDealerServiceSubCategoryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getHistoDealerServiceSubCategoryByID/HistoDealerServiceSubCategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerServiceSubCategory.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (HistoDealerServiceSubCategory) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getHistoDealerServiceSubCategoryByID/HistoDealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getHistoDealerServiceSubCategoryByID/HistoDealerServiceSubCategoryDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal#getAllHistoDealerServiceSubCategorys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerServiceSubCategory> getAllHistoDealerServiceSubCategorys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllHistoDealerServiceSubCategorys/HistoDealerServiceSubCategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerServiceSubCategory.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllHistoDealerServiceSubCategorys/HistoDealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllHistoDealerServiceSubCategorys/HistoDealerServiceSubCategoryDAO ==");
        }
    }

}
