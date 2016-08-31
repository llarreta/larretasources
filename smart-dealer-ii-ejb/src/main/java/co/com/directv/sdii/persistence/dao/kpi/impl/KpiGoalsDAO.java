package co.com.directv.sdii.persistence.dao.kpi.impl;

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
import co.com.directv.sdii.model.pojo.KpiGoals;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad KpiGoals
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.KpiGoals
 * @see co.com.directv.sdii.model.hbm.KpiGoals.hbm.xml
 */
@Stateless(name="KpiGoalsDAOLocal",mappedName="ejb/KpiGoalsDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiGoalsDAO extends BaseDao implements KpiGoalsDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(KpiGoalsDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal#createKpiGoals(co.com.directv.sdii.model.pojo.KpiGoals)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createKpiGoals(KpiGoals obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createKpiGoals/KpiGoalsDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el KpiGoals ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiGoals/KpiGoalsDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal#updateKpiGoals(co.com.directv.sdii.model.pojo.KpiGoals)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateKpiGoals(KpiGoals obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateKpiGoals/KpiGoalsDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el KpiGoals ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiGoals/KpiGoalsDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal#deleteKpiGoals(co.com.directv.sdii.model.pojo.KpiGoals)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteKpiGoals(KpiGoals obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteKpiGoals/KpiGoalsDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from KpiGoals entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el KpiGoals ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiGoals/KpiGoalsDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal#getKpiGoalssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiGoals getKpiGoalsByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getKpiGoalsByID/KpiGoalsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiGoals.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (KpiGoals) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiGoalsByID/KpiGoalsDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal#getAllKpiGoalss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiGoals> getAllKpiGoalss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllKpiGoalss/KpiGoalsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiGoals.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllKpiGoalss/KpiGoalsDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal#getAllKpiGoalssAndByCountry(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiGoals> getAllKpiGoalssAndByCountry(Long country) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllKpiGoalssAndByCountry/KpiGoalsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiGoals.class.getName());
        	stringQuery.append(" entity where entity.countries.id = :country");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("country", country);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllKpiGoalssAndByCountry/KpiGoalsDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal#getKpiGoalsByIndicatorIdAndCountryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiGoals> getKpiGoalsByIndicatorIdAndCountryId(
			Long indicatorId, Long countryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getKpiGoalsByIndicatorIdAndCountryId/KpiGoalsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(KpiGoals.class.getName());
        	stringQuery.append(" kpig where kpig.indicators.id = :aIndicatorId and kpig.countries.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aIndicatorId", indicatorId);
        	query.setLong("aCountryId", countryId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiGoalsByIndicatorIdAndCountryId/KpiGoalsDAO ==");
        }
	}

}
