package co.com.directv.sdii.persistence.dao.assign.impl;

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
import co.com.directv.sdii.model.pojo.HistoDealerBuilding;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad HistoDealerBuilding
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.HistoDealerBuilding
 * @see co.com.directv.sdii.model.hbm.HistoDealerBuilding.hbm.xml
 */
@Stateless(name="HistoDealerBuildingDAOLocal",mappedName="ejb/HistoDealerBuildingDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerBuildingDAO extends BaseDao implements HistoDealerBuildingDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerBuildingDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal#createHistoDealerBuilding(co.com.directv.sdii.model.pojo.HistoDealerBuilding)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createHistoDealerBuilding(HistoDealerBuilding obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createHistoDealerBuilding/HistoDealerBuildingDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el HistoDealerBuilding ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerBuilding/HistoDealerBuildingDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal#updateHistoDealerBuilding(co.com.directv.sdii.model.pojo.HistoDealerBuilding)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateHistoDealerBuilding(HistoDealerBuilding obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateHistoDealerBuilding/HistoDealerBuildingDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el HistoDealerBuilding ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerBuilding/HistoDealerBuildingDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal#deleteHistoDealerBuilding(co.com.directv.sdii.model.pojo.HistoDealerBuilding)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteHistoDealerBuilding(HistoDealerBuilding obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteHistoDealerBuilding/HistoDealerBuildingDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from HistoDealerBuilding entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el HistoDealerBuilding ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerBuilding/HistoDealerBuildingDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal#getHistoDealerBuildingsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerBuilding getHistoDealerBuildingByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getHistoDealerBuildingByID/HistoDealerBuildingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerBuilding.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (HistoDealerBuilding) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getHistoDealerBuildingByID/HistoDealerBuildingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getHistoDealerBuildingByID/HistoDealerBuildingDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal#getAllHistoDealerBuildings()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerBuilding> getAllHistoDealerBuildings() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllHistoDealerBuildings/HistoDealerBuildingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerBuilding.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllHistoDealerBuildings/HistoDealerBuildingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllHistoDealerBuildings/HistoDealerBuildingDAO ==");
        }
    }

}
