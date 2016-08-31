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
import co.com.directv.sdii.model.pojo.KpiType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad KpiType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.KpiType
 * @see co.com.directv.sdii.model.hbm.KpiType.hbm.xml
 */
@Stateless(name="KpiTypeDAOLocal",mappedName="ejb/KpiTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiTypeDAO extends BaseDao implements KpiTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(KpiTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal#createKpiType(co.com.directv.sdii.model.pojo.KpiType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createKpiType(KpiType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createKpiType/KpiTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el KpiType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiType/KpiTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal#updateKpiType(co.com.directv.sdii.model.pojo.KpiType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateKpiType(KpiType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateKpiType/KpiTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el KpiType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiType/KpiTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal#deleteKpiType(co.com.directv.sdii.model.pojo.KpiType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteKpiType(KpiType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteKpiType/KpiTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from KpiType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el KpiType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiType/KpiTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal#getKpiTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiType getKpiTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getKpiTypeByID/KpiTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (KpiType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getKpiTypeByID/KpiTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiTypeByID/KpiTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal#getAllKpiTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiType> getAllKpiTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllKpiTypes/KpiTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllKpiTypes/KpiTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllKpiTypes/KpiTypeDAO ==");
        }
    }

}
