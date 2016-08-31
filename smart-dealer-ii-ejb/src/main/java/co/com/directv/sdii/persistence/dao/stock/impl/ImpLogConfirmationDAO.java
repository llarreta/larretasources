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
import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ImpLogConfirmation
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImpLogConfirmation
 * @see co.com.directv.sdii.model.hbm.ImpLogConfirmation.hbm.xml
 */
@Stateless(name="ImpLogConfirmationDAOLocal",mappedName="ejb/ImpLogConfirmationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImpLogConfirmationDAO extends BaseDao implements ImpLogConfirmationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ImpLogConfirmationDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationsDAOLocal#createImpLogConfirmation(co.com.directv.sdii.model.pojo.ImpLogConfirmation)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createImpLogConfirmation(ImpLogConfirmation obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createImpLogConfirmation/ImpLogConfirmationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ImpLogConfirmation ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImpLogConfirmation/ImpLogConfirmationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationsDAOLocal#updateImpLogConfirmation(co.com.directv.sdii.model.pojo.ImpLogConfirmation)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImpLogConfirmation(ImpLogConfirmation obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateImpLogConfirmation/ImpLogConfirmationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ImpLogConfirmation ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImpLogConfirmation/ImpLogConfirmationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationsDAOLocal#deleteImpLogConfirmation(co.com.directv.sdii.model.pojo.ImpLogConfirmation)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImpLogConfirmation(ImpLogConfirmation obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImpLogConfirmation/ImpLogConfirmationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImpLogConfirmation entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ImpLogConfirmation ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogConfirmation/ImpLogConfirmationDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationsDAOLocal#getImpLogConfirmationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImpLogConfirmation getImpLogConfirmationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImpLogConfirmationByID/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogConfirmation.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ImpLogConfirmation) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogConfirmationByID/ImpLogConfirmationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationsDAOLocal#getAllImpLogConfirmations()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImpLogConfirmation> getAllImpLogConfirmations() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImpLogConfirmations/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogConfirmation.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImpLogConfirmations/ImpLogConfirmationDAO ==");
        }
    }
    /**
     * Metodo: <Descripcion>
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLog> getAllImportLogsByStatusConfirmation() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImportLogsByStatusConfirmation/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("select il from ");
        	stringQuery.append(ImpLogConfirmation.class.getName());
        	stringQuery.append(" ilc ");
        	stringQuery.append(" join ilc.importLogItem as ili ");
        	stringQuery.append(" join ili.importLog as il ");
        	stringQuery.append(" where il.importLogStatus = ? OR il.importLogStatus = ? ");
     
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImportLogsByStatusConfirmation/ImpLogConfirmationDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal#getImpLogConfirmationsByImpLogItemId(java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ImpLogConfirmation> getImpLogConfirmationsByImpLogItemId(
			Long importLogItemId) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getImpLogConfirmationsByImpLogItemId/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogConfirmation.class.getName());
        	stringQuery.append(" entity where entity.importLogItem.id = :anImpLogItemId order by entity.confirmationDate desc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anImpLogItemId", importLogItemId);
            List<ImpLogConfirmation>list = query.list();
            return list;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogConfirmationsByImpLogItemId/ImpLogConfirmationDAO ==");
        }
	}
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal#getImpLogConfirmationsByImpLogItemId(java.lang.Long)
	 */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ImpLogConfirmation getImpLogConfirmationByImpLogItemId(Long importLogItemId) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogConfirmation.class.getName());
        	stringQuery.append(" entity where entity.importLogItem.id = :anImpLogItemId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anImpLogItemId", importLogItemId);

            return (ImpLogConfirmation) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
        }
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal#deleteImpLogConfirmationByImpLogItemId(java.lang.Long)
     */
	@Override
	public void deleteImpLogConfirmationByImpLogItemId(Long impLogItemId) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
	        Session session = super.getSession();
	        try {
	            Query query = session.createQuery("delete from ImpLogConfirmation entity where entity.importLogItem = :impLogItemId");
	            query.setLong("impLogItemId", impLogItemId);
	            query.executeUpdate();
	            super.doFlush(session);
	        } catch (Throwable ex) {
	            log.error("== Error deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
	        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal#getImportLogItemConfirmationSumByImpLogItemId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Double getImportLogItemConfirmationSumByImpLogItemId(Long impLogItemID) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
        Session session = super.getSession();
        try {
        	Double response = 0D;
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select sum(entity.confirmedQuantity) from ");
        	stringQuery.append(ImpLogConfirmation.class.getName());
        	stringQuery.append(" entity where entity.importLogItem.id = :impLogItemID");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("impLogItemID", impLogItemID);
            response = (Double) query.uniqueResult();
            return response;
        } catch (Throwable ex) {
            log.error("== Error deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationDAO ==");
        }
	}

}
