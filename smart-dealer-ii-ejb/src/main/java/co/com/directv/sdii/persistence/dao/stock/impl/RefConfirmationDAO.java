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
import co.com.directv.sdii.model.pojo.RefConfirmation;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.RefConfirmationDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad RefConfirmation
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RefConfirmation
 * @see co.com.directv.sdii.model.hbm.RefConfirmation.hbm.xml
 */
@Stateless(name="RefConfirmationDAOLocal",mappedName="ejb/RefConfirmationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefConfirmationDAO extends BaseDao implements RefConfirmationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(RefConfirmationDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationsDAOLocal#createRefConfirmation(co.com.directv.sdii.model.pojo.RefConfirmation)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRefConfirmation(RefConfirmation obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createRefConfirmation/RefConfirmationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el RefConfirmation ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefConfirmation/RefConfirmationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationsDAOLocal#updateRefConfirmation(co.com.directv.sdii.model.pojo.RefConfirmation)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateRefConfirmation(RefConfirmation obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateRefConfirmation/RefConfirmationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RefConfirmation ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefConfirmation/RefConfirmationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationsDAOLocal#deleteRefConfirmation(co.com.directv.sdii.model.pojo.RefConfirmation)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRefConfirmation(RefConfirmation obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteRefConfirmation/RefConfirmationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from RefConfirmation entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el RefConfirmation ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefConfirmation/RefConfirmationDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationsDAOLocal#getRefConfirmationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefConfirmation getRefConfirmationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRefConfirmationByID/RefConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefConfirmation.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (RefConfirmation) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefConfirmationByID/RefConfirmationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationsDAOLocal#getAllRefConfirmations()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RefConfirmation> getAllRefConfirmations() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllRefConfirmations/RefConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefConfirmation.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllRefConfirmations/RefConfirmationDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationsDAOLocal#getConfirmationsByElementId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RefConfirmation> getConfirmationsByElementId(Long elementId) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getConfirmationsByElementId/RefConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefConfirmation.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.referenceElementItem.id=:elementId ");
        	Query query = session.createQuery(stringQuery.toString()); 
        	query.setLong("elementId", elementId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getConfirmationsByElementId/RefConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getConfirmationsByElementId/RefConfirmationDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationsDAOLocal#countElementConfirmedQuatity(java.lang.Long)
     */
    public Double countElementConfirmedQuatity(Long referenceElementID) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia countElementConfirmedQuatity/RefConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select sum(entity.confirmedQuantity) from ");
        	stringQuery.append(RefConfirmation.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.referenceElementItem.id = :referenceElementID");        	
        	Query query = session.createQuery(stringQuery.toString()); 
        	query.setLong("referenceElementID", referenceElementID);
        	Double response = (Double) query.uniqueResult();
        	if(response == null)
        		response = 0D;
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error countElementConfirmedQuatity/RefConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina countElementConfirmedQuatity/RefConfirmationDAO ==");
        }
    }
    
    public List<RefConfirmation> getConfirmationsByReferenceId( Long referenceId ) throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicia getConfirmationsByReferenceId/RefConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefConfirmation.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.referenceElementItem.reference.id =:referenceId ");
        	Query query = session.createQuery(stringQuery.toString()); 
        	query.setLong("referenceId", referenceId);
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getConfirmationsByReferenceId/RefConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getConfirmationsByReferenceId/RefConfirmationDAO ==");
        }
    }
    
    public List<RefConfirmation> getConfirmationsByReferenceIdAndElementId( Long referenceId,Long elementId ) throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicia getConfirmationsByReferenceIdAndElementId/RefConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefConfirmation.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.referenceElementItem.reference.id =:referenceId ");
        	stringQuery.append( "and entity.referenceElementItem.element.id =:elementId " );
        	Query query = session.createQuery(stringQuery.toString()); 
        	query.setLong("referenceId", referenceId);
        	query.setLong("elementId", elementId);
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getConfirmationsByReferenceIdAndElementId/RefConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getConfirmationsByReferenceIdAndElementId/RefConfirmationDAO ==");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
	public List<RefConfirmation> getConfirmationsByReferenceElementId( Long referenceElementId ) throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicia getConfirmationsByReferenceElementId/RefConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append( RefConfirmation.class.getName() );
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.referenceElementItem.id = :referenceElementId ");
        	stringQuery.append("ORDER BY entity.comfirmDate DESC ");
        	Query query = session.createQuery( stringQuery.toString() ); 
        	query.setLong("referenceElementId", referenceElementId );
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getConfirmationsByReferenceElementId/RefConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getConfirmationsByReferenceElementId/RefConfirmationDAO ==");
        }
    }
}
