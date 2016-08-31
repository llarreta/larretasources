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
import co.com.directv.sdii.model.pojo.HistoDealerCoverage;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad HistoDealerCoverage
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.HistoDealerCoverage
 * @see co.com.directv.sdii.model.hbm.HistoDealerCoverage.hbm.xml
 */
@Stateless(name="HistoDealerCoverageDAOLocal",mappedName="ejb/HistoDealerCoverageDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerCoverageDAO extends BaseDao implements HistoDealerCoverageDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerCoverageDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal#createHistoDealerCoverage(co.com.directv.sdii.model.pojo.HistoDealerCoverage)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createHistoDealerCoverage(HistoDealerCoverage obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createHistoDealerCoverage/HistoDealerCoverageDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el HistoDealerCoverage ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerCoverage/HistoDealerCoverageDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal#updateHistoDealerCoverage(co.com.directv.sdii.model.pojo.HistoDealerCoverage)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateHistoDealerCoverage(HistoDealerCoverage obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateHistoDealerCoverage/HistoDealerCoverageDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el HistoDealerCoverage ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerCoverage/HistoDealerCoverageDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal#deleteHistoDealerCoverage(co.com.directv.sdii.model.pojo.HistoDealerCoverage)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteHistoDealerCoverage(HistoDealerCoverage obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteHistoDealerCoverage/HistoDealerCoverageDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from HistoDealerCoverage entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el HistoDealerCoverage ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerCoverage/HistoDealerCoverageDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal#getHistoDealerCoveragesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerCoverage getHistoDealerCoverageByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getHistoDealerCoverageByID/HistoDealerCoverageDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerCoverage.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (HistoDealerCoverage) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getHistoDealerCoverageByID/HistoDealerCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getHistoDealerCoverageByID/HistoDealerCoverageDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal#getAllHistoDealerCoverages()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerCoverage> getAllHistoDealerCoverages() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllHistoDealerCoverages/HistoDealerCoverageDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerCoverage.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllHistoDealerCoverages/HistoDealerCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllHistoDealerCoverages/HistoDealerCoverageDAO ==");
        }
    }

}
