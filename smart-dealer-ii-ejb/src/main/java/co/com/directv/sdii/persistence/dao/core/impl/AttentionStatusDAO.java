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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AttentionStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.AttentionStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AttentionStatus
 * 
 * Fecha de Creación:Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AttentionStatus
 * @see co.com.directv.sdii.model.hbm.AttentionStatus.hbm.xml
 */
@Stateless(name="AttentionStatusDAOLocal",mappedName="ejb/AttentionStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AttentionStatusDAO extends BaseDao implements AttentionStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AttentionStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.AttentionStatusDAOLocal#createAttentionStatus(co.com.directv.sdii.model.pojo.AttentionStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAttentionStatus(AttentionStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAttentionStatus/AttentionStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el AttentionStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAttentionStatus/AttentionStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.AttentionStatusDAOLocal#updateAttentionStatus(co.com.directv.sdii.model.pojo.AttentionStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAttentionStatus(AttentionStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateAttentionStatus/AttentionStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el AttentionStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAttentionStatus/AttentionStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.AttentionStatusDAOLocal#deleteAttentionStatus(co.com.directv.sdii.model.pojo.AttentionStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAttentionStatus(AttentionStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteAttentionStatus/AttentionStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from AttentionStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el AttentionStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAttentionStatus/AttentionStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.AttentionStatusDAOLocal#getAttentionStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AttentionStatus getAttentionStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAttentionStatusByID/AttentionStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AttentionStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (AttentionStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAttentionStatusByID/AttentionStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.AttentionStatusDAOLocal#getAllAttentionStatus()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AttentionStatus> getAllAttentionStatus() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllAttentionStatuss/AttentionStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AttentionStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAttentionStatuss/AttentionStatusDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.AttentionStatusDAOLocal#getAttentionStatusByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public AttentionStatus getAttentionStatusByCode(String attentionStatusCode)	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAttentionStatusByCode/AttentionStatusDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AttentionStatus.class.getName());
        	stringQuery.append(" entity where entity.code = :anAttentionStatusCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anAttentionStatusCode", attentionStatusCode);
            return (AttentionStatus) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAttentionStatusByCode/AttentionStatusDAO ==");
        }
	}
}
