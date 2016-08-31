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
import co.com.directv.sdii.model.pojo.MassiveMovementDealer;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad MassiveMovementDealer
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MassiveMovementDealer
 * @see co.com.directv.sdii.model.hbm.MassiveMovementDealer.hbm.xml
 */
@Stateless(name="MassiveMovementDealerDAOLocal",mappedName="ejb/MassiveMovementDealerDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MassiveMovementDealerDAO extends BaseDao implements MassiveMovementDealerDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(MassiveMovementDealerDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal#createMassiveMovementDealer(co.com.directv.sdii.model.pojo.MassiveMovementDealer)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createMassiveMovementDealer(MassiveMovementDealer obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createMassiveMovementDealer/MassiveMovementDealerDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el MassiveMovementDealer ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMassiveMovementDealer/MassiveMovementDealerDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal#updateMassiveMovementDealer(co.com.directv.sdii.model.pojo.MassiveMovementDealer)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateMassiveMovementDealer(MassiveMovementDealer obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateMassiveMovementDealer/MassiveMovementDealerDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el MassiveMovementDealer ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMassiveMovementDealer/MassiveMovementDealerDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal#deleteMassiveMovementDealer(co.com.directv.sdii.model.pojo.MassiveMovementDealer)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteMassiveMovementDealer(MassiveMovementDealer obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteMassiveMovementDealer/MassiveMovementDealerDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from MassiveMovementDealer entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el MassiveMovementDealer ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMassiveMovementDealer/MassiveMovementDealerDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal#getMassiveMovementDealersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MassiveMovementDealer getMassiveMovementDealerByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMassiveMovementDealerByID/MassiveMovementDealerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MassiveMovementDealer.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (MassiveMovementDealer) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMassiveMovementDealerByID/MassiveMovementDealerDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal#getMassiveMovementDealersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MassiveMovementDealer> getMassiveMovementDealerByMovementID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMassiveMovementDealerByMovementID/MassiveMovementDealerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MassiveMovementDealer.class.getName());
        	stringQuery.append(" entity where entity.massiveMovement.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMassiveMovementDealerByID/MassiveMovementDealerDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal#getAllMassiveMovementDealers()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MassiveMovementDealer> getAllMassiveMovementDealers() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMassiveMovementDealers/MassiveMovementDealerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MassiveMovementDealer.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllMassiveMovementDealers/MassiveMovementDealerDAO ==");
        }
    }

}
