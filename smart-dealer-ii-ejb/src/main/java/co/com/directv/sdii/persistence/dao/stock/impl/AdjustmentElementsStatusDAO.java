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
import co.com.directv.sdii.model.pojo.AdjustmentElementsStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AdjustmentElementsStatus
 * 
 * Fecha de Creación: Mar 16, 2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AdjustmentElementsStatus
 * @see co.com.directv.sdii.model.hbm.AdjustmentElementsStatus.hbm.xml
 */
@Stateless(name="AdjustmentElementsStatusDAOLocal",mappedName="ejb/AdjustmentElementsStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentElementsStatusDAO extends BaseDao implements AdjustmentElementsStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentElementsStatusDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal#createAdjustmentElementsStatus(co.com.directv.sdii.model.pojo.AdjustmentElementsStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAdjustmentElementsStatus(AdjustmentElementsStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAdjustmentElementsStatus/AdjustmentElementsStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el AdjustmentElementsStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentElementsStatus/AdjustmentElementsStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal#getAdjustmentElementsStatusByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentElementsStatus getAdjustmentElementsStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentElementsStatusByID/AdjustmentElementsStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElementsStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", id);

            return (AdjustmentElementsStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error == getAdjustmentElementsStatusByID/AdjustmentElementsStatusDAO");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsStatusByID/AdjustmentElementsStatusDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal#getAdjustmentElementsStatusByCode(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentElementsStatus getAdjustmentElementsStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElementsStatus.class.getName());
        	stringQuery.append(" entity where entity.code = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (AdjustmentElementsStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error == getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdjustmentElementsStatus getAdjustmentElementsStatusByCodeMAssive(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElementsStatus.class.getName());
        	stringQuery.append(" entity where entity.code = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (AdjustmentElementsStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error == getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdjustmentElementsStatus getAdjustmentElementsStatusByCodeRequired(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElementsStatus.class.getName());
        	stringQuery.append(" entity where entity.code = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (AdjustmentElementsStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error == getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal#getAllAdjustmentElementsStatus()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AdjustmentElementsStatus> getAllAdjustmentElementsStatus() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllAdjustmentElementsStatus/AdjustmentElementsStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElementsStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAdjustmentElementsStatus/AdjustmentElementsStatusDAO ==");
        }
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AdjustmentElementsStatus getAdjustmentElementsStatusByCodeMassive(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElementsStatus.class.getName());
        	stringQuery.append(" entity where entity.code = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (AdjustmentElementsStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error == getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsStatusByCode/AdjustmentElementsStatusDAO ==");
        }
	}

}
