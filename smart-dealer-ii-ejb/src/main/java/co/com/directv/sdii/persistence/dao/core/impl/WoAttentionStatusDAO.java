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
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoAttentionStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.WoAttentionStatusDAOLocal;

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
@Stateless(name="WoAttentionStatusDAOLocal",mappedName="ejb/WoAttentionStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoAttentionStatusDAO extends BaseDao implements WoAttentionStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoAttentionStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoAttentionStatusDAOLocal#createWoAttentionStatus(co.com.directv.sdii.model.pojo.WoAttentionStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWoAttentionStatus(WoAttentionStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWoAttentionStatus/WoAttentionStatusDAO ==");
        Session session = super.getSession();
        try {
        	 if (session == null) {
                 throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
             }
             session.save(obj);
             this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el WoAttentionStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoAttentionStatus/WoAttentionStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoAttentionStatusDAOLocal#updateWoAttentionStatus(co.com.directv.sdii.model.pojo.WoAttentionStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWoAttentionStatus(WoAttentionStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateWoAttentionStatus/WoAttentionStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el WoAttentionStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoAttentionStatus/WoAttentionStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoAttentionStatusDAOLocal#deleteWoAttentionStatus(co.com.directv.sdii.model.pojo.WoAttentionStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWoAttentionStatus(WoAttentionStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteWoAttentionStatus/WoAttentionStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from WoAttentionStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el WoAttentionStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWoAttentionStatus/WoAttentionStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.WoAttentionStatusDAOLocal#getWoAttentionStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WoAttentionStatus getWoAttentionStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoAttentionStatusByID/WoAttentionStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoAttentionStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (WoAttentionStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoAttentionStatusByID/WoAttentionStatusDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoAttentionStatusDAOLocal#getWoAttentionStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WoAttentionStatus getLastWoAttentionStatusByWoId(Long workOrderId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoAttentionStatusByID/WoAttentionStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoAttentionStatus.class.getName());
        	stringQuery.append(" entity where entity.woId = :anId and entity.recordStatus = :aRecordStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", workOrderId);
            query.setString("aRecordStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());

            return (WoAttentionStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoAttentionStatusByID/WoAttentionStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoAttentionStatusDAOLocal#getAllWoAttentionStatus()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WoAttentionStatus> getAllWoAttentionStatus() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllWoAttentionStatuss/WoAttentionStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoAttentionStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWoAttentionStatuss/WoAttentionStatusDAO ==");
        }
    }

}
