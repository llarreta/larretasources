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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AdjustmentStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AdjustmentStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AdjustmentStatus
 * @see co.com.directv.sdii.model.hbm.AdjustmentStatus.hbm.xml
 */
@Stateless(name="AdjustmentStatusDAOLocal",mappedName="ejb/AdjustmentStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentStatusDAO extends BaseDao implements AdjustmentStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal#createAdjustmentStatus(co.com.directv.sdii.model.pojo.AdjustmentStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAdjustmentStatus(AdjustmentStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAdjustmentStatus/AdjustmentStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el AdjustmentStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentStatus/AdjustmentStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal#updateAdjustmentStatus(co.com.directv.sdii.model.pojo.AdjustmentStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAdjustmentStatus(AdjustmentStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateAdjustmentStatus/AdjustmentStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el AdjustmentStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAdjustmentStatus/AdjustmentStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal#deleteAdjustmentStatus(co.com.directv.sdii.model.pojo.AdjustmentStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAdjustmentStatus(AdjustmentStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteAdjustmentStatus/AdjustmentStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from AdjustmentStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el AdjustmentStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustmentStatus/AdjustmentStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal#getAdjustmentStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentStatus getAdjustmentStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentStatusByID/AdjustmentStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (AdjustmentStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentStatusByID/AdjustmentStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal#getAllAdjustmentStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AdjustmentStatus> getAllAdjustmentStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllAdjustmentStatuss/AdjustmentStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentStatus.class.getName());
        	stringQuery.append(" entity where entity.code not in (:code)");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", CodesBusinessEntityEnum.ADJUSTMENT_STATUS_CREATE.getCodeEntity());
            
            return (List<AdjustmentStatus>) query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAdjustmentStatuss/AdjustmentStatusDAO ==");
        }
    }



	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public AdjustmentStatus getAdjustmentStatusByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAdjustmentStatusByCode/AdjustmentStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentStatus.class.getName());
        	stringQuery.append(" entity where entity.code = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (AdjustmentStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentStatusByCode/AdjustmentStatusDAO ==");
        }
	}

}
