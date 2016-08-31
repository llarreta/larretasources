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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Kpi;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Kpi
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Kpi
 * @see co.com.directv.sdii.model.hbm.Kpi.hbm.xml
 */
@Stateless(name="KpiDAOLocal",mappedName="ejb/KpiDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiDAO extends BaseDao implements KpiDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(KpiDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal#createKpi(co.com.directv.sdii.model.pojo.Kpi)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createKpi(Kpi obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createKpi/KpiDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el Kpi ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpi/KpiDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal#updateKpi(co.com.directv.sdii.model.pojo.Kpi)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateKpi(Kpi obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateKpi/KpiDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el Kpi ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpi/KpiDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal#deleteKpi(co.com.directv.sdii.model.pojo.Kpi)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteKpi(Kpi obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteKpi/KpiDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Kpi entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el Kpi ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpi/KpiDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal#getKpisByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Kpi getKpiByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getKpiByID/KpiDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Kpi.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Kpi) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getKpiByID/KpiDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiByID/KpiDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal#getAllKpis()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Kpi> getAllKpis() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllKpis/KpiDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();

        	stringQuery.append("from ")
	        	.append(Kpi.class.getName())
	        	.append(" where status = '")
	        	.append(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()).append("'");
        	
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllKpis/KpiDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllKpis/KpiDAO ==");
        }
    }

}
