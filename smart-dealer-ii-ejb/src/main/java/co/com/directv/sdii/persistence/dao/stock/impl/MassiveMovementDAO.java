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
import co.com.directv.sdii.model.pojo.MassiveMovement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad MassiveMovement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MassiveMovement
 * @see co.com.directv.sdii.model.hbm.MassiveMovement.hbm.xml
 */
@Stateless(name="MassiveMovementDAOLocal",mappedName="ejb/MassiveMovementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MassiveMovementDAO extends BaseDao implements MassiveMovementDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(MassiveMovementDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal#createMassiveMovement(co.com.directv.sdii.model.pojo.MassiveMovement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MassiveMovement createMassiveMovement(MassiveMovement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createMassiveMovement/MassiveMovementDAO ==");
        Session session = super.getSession();
        try {
            obj.setId( (Long) session.save(obj) );
            this.doFlush(session);
            return obj;
        } catch (Throwable ex) {
            log.debug("== Error creando el MassiveMovement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMassiveMovement/MassiveMovementDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal#updateMassiveMovement(co.com.directv.sdii.model.pojo.MassiveMovement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateMassiveMovement(MassiveMovement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateMassiveMovement/MassiveMovementDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el MassiveMovement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMassiveMovement/MassiveMovementDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal#deleteMassiveMovement(co.com.directv.sdii.model.pojo.MassiveMovement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteMassiveMovement(MassiveMovement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteMassiveMovement/MassiveMovementDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from MassiveMovement entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el MassiveMovement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMassiveMovement/MassiveMovementDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal#getMassiveMovementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MassiveMovement getMassiveMovementByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMassiveMovementByID/MassiveMovementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MassiveMovement.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (MassiveMovement) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMassiveMovementByID/MassiveMovementDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal#getAllMassiveMovements()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MassiveMovement> getAllMassiveMovements() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMassiveMovements/MassiveMovementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MassiveMovement.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllMassiveMovements/MassiveMovementDAO ==");
        }
    }

}
