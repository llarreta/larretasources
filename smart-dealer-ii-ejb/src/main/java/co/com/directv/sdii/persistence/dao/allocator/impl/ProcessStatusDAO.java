package co.com.directv.sdii.persistence.dao.allocator.impl;

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
import co.com.directv.sdii.model.pojo.ProcessStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ProcessStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ProcessStatus
 * @see co.com.directv.sdii.model.hbm.ProcessStatus.hbm.xml
 */
@Stateless(name="ProcessStatusDAOLocal",mappedName="ejb/ProcessStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProcessStatusDAO extends BaseDao implements ProcessStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ProcessStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal#createProcessStatus(co.com.directv.sdii.model.pojo.ProcessStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createProcessStatus(ProcessStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createProcessStatus/ProcessStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ProcessStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createProcessStatus/ProcessStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal#updateProcessStatus(co.com.directv.sdii.model.pojo.ProcessStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateProcessStatus(ProcessStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateProcessStatus/ProcessStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ProcessStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateProcessStatus/ProcessStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal#deleteProcessStatus(co.com.directv.sdii.model.pojo.ProcessStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteProcessStatus(ProcessStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteProcessStatus/ProcessStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ProcessStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ProcessStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteProcessStatus/ProcessStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal#getProcessStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ProcessStatus getProcessStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getProcessStatusByID/ProcessStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ProcessStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ProcessStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getProcessStatusByID/ProcessStatusDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getProcessStatusByID/ProcessStatusDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ProcessStatus getProcessStatusByCode(String processStatusCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getProcessStatusByCode/ProcessStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ProcessStatus.class.getName());
        	stringQuery.append(" entity where entity.statusCode = :aProcessStatusCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aProcessStatusCode", processStatusCode);

            return (ProcessStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getProcessStatusByCode/ProcessStatusDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getProcessStatusByCode/ProcessStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal#getAllProcessStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ProcessStatus> getAllProcessStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllProcessStatuss/ProcessStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ProcessStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllProcessStatuss/ProcessStatusDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllProcessStatuss/ProcessStatusDAO ==");
        }
    }

}
