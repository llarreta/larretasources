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
import co.com.directv.sdii.model.pojo.KpiCalculationType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad KpiCalculationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.KpiCalculationType
 * @see co.com.directv.sdii.model.hbm.KpiCalculationType.hbm.xml
 */
@Stateless(name="KpiCalculationTypeDAOLocal",mappedName="ejb/KpiCalculationTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiCalculationTypeDAO extends BaseDao implements KpiCalculationTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(KpiCalculationTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal#createKpiCalculationType(co.com.directv.sdii.model.pojo.KpiCalculationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createKpiCalculationType(KpiCalculationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createKpiCalculationType/KpiCalculationTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el KpiCalculationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiCalculationType/KpiCalculationTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal#updateKpiCalculationType(co.com.directv.sdii.model.pojo.KpiCalculationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateKpiCalculationType(KpiCalculationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateKpiCalculationType/KpiCalculationTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el KpiCalculationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiCalculationType/KpiCalculationTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal#deleteKpiCalculationType(co.com.directv.sdii.model.pojo.KpiCalculationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteKpiCalculationType(KpiCalculationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteKpiCalculationType/KpiCalculationTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from KpiCalculationType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el KpiCalculationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiCalculationType/KpiCalculationTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal#getKpiCalculationTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiCalculationType getKpiCalculationTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getKpiCalculationTypeByID/KpiCalculationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiCalculationType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (KpiCalculationType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getKpiCalculationTypeByID/KpiCalculationTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiCalculationTypeByID/KpiCalculationTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal#getAllKpiCalculationTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiCalculationType> getAllKpiCalculationTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllKpiCalculationTypes/KpiCalculationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiCalculationType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllKpiCalculationTypes/KpiCalculationTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllKpiCalculationTypes/KpiCalculationTypeDAO ==");
        }
    }

}
