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
import co.com.directv.sdii.model.pojo.SpecialComment;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.SpecialCommentDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad SpecialComment
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SpecialComment
 * @see co.com.directv.sdii.model.hbm.SpecialComment.hbm.xml
 */
@Stateless(name="SpecialCommentDAOLocal",mappedName="ejb/SpecialCommentDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SpecialCommentDAO extends BaseDao implements SpecialCommentDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SpecialCommentDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentsDAOLocal#createSpecialComment(co.com.directv.sdii.model.pojo.SpecialComment)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSpecialComment(SpecialComment obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSpecialComment/SpecialCommentDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el SpecialComment ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSpecialComment/SpecialCommentDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentsDAOLocal#updateSpecialComment(co.com.directv.sdii.model.pojo.SpecialComment)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSpecialComment(SpecialComment obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSpecialComment/SpecialCommentDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el SpecialComment ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSpecialComment/SpecialCommentDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentsDAOLocal#deleteSpecialComment(co.com.directv.sdii.model.pojo.SpecialComment)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSpecialComment(SpecialComment obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSpecialComment/SpecialCommentDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SpecialComment entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el SpecialComment ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSpecialComment/SpecialCommentDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentsDAOLocal#getSpecialCommentsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SpecialComment getSpecialCommentByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSpecialCommentByID/SpecialCommentDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SpecialComment.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (SpecialComment) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSpecialCommentByID/SpecialCommentDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentsDAOLocal#getAllSpecialComments()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SpecialComment> getAllSpecialComments() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSpecialComments/SpecialCommentDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SpecialComment.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSpecialComments/SpecialCommentDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentsDAOLocal#getSpecialCommentsByReferenceId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SpecialComment> getSpecialCommentsByReferenceId(Long referenceId) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getSpecialCommentsByReferenceId/SpecialCommentDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SpecialComment.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.reference.id= :referenceId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("referenceId", referenceId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getSpecialCommentsByReferenceId/SpecialCommentDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSpecialCommentsByReferenceId/SpecialCommentDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentDAOLocal#getSpecialCommentsByReferenceId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SpecialCommentResponse getSpecialCommentsByReferenceId(
			Long referenceId, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSpecialCommentsByReferenceId/SpecialCommentDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(SpecialComment.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.reference.id= :referenceId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("referenceId", referenceId);
        	 //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setLong("referenceId", referenceId);
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	SpecialCommentResponse response = new SpecialCommentResponse();
        	List<SpecialComment> specialCommentList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), specialCommentList.size(), recordQty.intValue() );
        	response.setSpecialComment(specialCommentList );
			return response;
            
        } catch (Throwable ex){
			log.error("== Error getSpecialCommentsByReferenceId/SpecialCommentDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSpecialCommentsByReferenceId/SpecialCommentDAO ==");
        }
	}
}
