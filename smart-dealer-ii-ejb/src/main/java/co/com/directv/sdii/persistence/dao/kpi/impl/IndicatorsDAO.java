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
import co.com.directv.sdii.model.pojo.Indicators;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Indicators
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Indicators
 * @see co.com.directv.sdii.model.hbm.Indicators.hbm.xml
 */
@Stateless(name="IndicatorsDAOLocal",mappedName="ejb/IndicatorsDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IndicatorsDAO extends BaseDao implements IndicatorsDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(IndicatorsDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal#createIndicators(co.com.directv.sdii.model.pojo.Indicators)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createIndicators(Indicators obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createIndicators/IndicatorsDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Indicators ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createIndicators/IndicatorsDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal#updateIndicators(co.com.directv.sdii.model.pojo.Indicators)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateIndicators(Indicators obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateIndicators/IndicatorsDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Indicators ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateIndicators/IndicatorsDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal#deleteIndicators(co.com.directv.sdii.model.pojo.Indicators)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteIndicators(Indicators obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteIndicators/IndicatorsDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Indicators entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Indicators ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteIndicators/IndicatorsDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal#getIndicatorssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Indicators getIndicatorsByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getIndicatorsByID/IndicatorsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Indicators.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Indicators) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getIndicatorsByID/IndicatorsDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal#getAllIndicatorss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Indicators> getAllIndicatorss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllIndicatorss/IndicatorsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Indicators.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllIndicatorss/IndicatorsDAO ==");
        }
    }

}
