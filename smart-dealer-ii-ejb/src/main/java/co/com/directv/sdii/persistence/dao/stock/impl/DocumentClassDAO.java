package co.com.directv.sdii.persistence.dao.stock.impl;

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
import co.com.directv.sdii.model.pojo.DocumentClass;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DocumentClass
 * 
 * Fecha de Creación: 16/11/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DocumentClass
 * @see co.com.directv.sdii.model.hbm.DocumentClass.hbm.xml
 */
@Stateless(name="DocumentClassDAOLocal",mappedName="ejb/DocumentClassDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentClassDAO extends BaseDao implements DocumentClassDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DocumentClassDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal#createDocumentClass(co.com.directv.sdii.model.pojo.DocumentClass)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDocumentClass(DocumentClass obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDocumentClass/DocumentClassDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el DocumentClass ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDocumentClass/DocumentClassDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal#updateDocumentClass(co.com.directv.sdii.model.pojo.DocumentClass)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDocumentClass(DocumentClass obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDocumentClass/DocumentClassDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el DocumentClass ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDocumentClass/DocumentClassDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal#deleteDocumentClass(co.com.directv.sdii.model.pojo.DocumentClass)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDocumentClass(DocumentClass obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDocumentClass/DocumentClassDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DocumentClass entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el DocumentClass ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDocumentClass/DocumentClassDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal#getDocumentClassByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentClass getDocumentClassByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDocumentClassByID/DocumentClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DocumentClass.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DocumentClass) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDocumentClassByID/DocumentClassDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal#getAllDocumentClass()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DocumentClass> getAllDocumentClass() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDocumentClass/DocumentClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DocumentClass.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDocumentClass/DocumentClassDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal#getDocumentClassByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DocumentClass getDocumentClassByCode(String code)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getDocumentClassByCode/DocumentClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DocumentClass.class.getName());
        	stringQuery.append(" dc where upper(dc.documentClassCode) = :anEtCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anEtCode", code.toUpperCase());
            return (DocumentClass)query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDocumentClassByCode/DocumentClassDAO ==");
        }
	}
    
    
}
