package co.com.directv.sdii.persistence.dao.config.impl;

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
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderMark;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkOrderMark
 * 
 * Fecha de Creación: 19/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="WorkOrderMarkDAOLocal",mappedName="ejb/WorkOrderMarkDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderMarkDAO extends BaseDao implements WorkOrderMarkDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderMarkDAO.class);

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#createWorkOrderMark(co.com.directv.sdii.model.pojo.WorkOrderMark)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkOrderMark(WorkOrderMark obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkOrderMark/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando WorkOrderMark ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderMark/WorkOrderMarkDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#getWorkOrderMarkByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderMark getWorkOrderMarkByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from " + WorkOrderMark.class.getName() + " wom ");
            queryBuffer.append(" where wom.id = :id");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (WorkOrderMark) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#getCodeWorkOrderMarkByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String getCodeWorkOrderMarkByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select wom.code ");
            queryBuffer.append(" from " + WorkOrderMark.class.getName() + " wom ");
            queryBuffer.append(" where wom.id = :id");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (String) obj;
            }
            return "";
        }catch (Throwable ex) {
            log.error("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#getCodeWorkOrderMarkByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderMark getWorkOrderMarkByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from " + WorkOrderMark.class.getName() + " wom ");
            queryBuffer.append(" where wom.code = :aCode");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("aCode", code);
            WorkOrderMark workOrderMark = (WorkOrderMark) query.uniqueResult();
            
            return workOrderMark;
        }catch (Throwable ex) {
            log.error("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#getWorkOrderMarkByIsUnMarkAttention()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderMark> getWorkOrderMarkByIsUnMarkAttention() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from " + WorkOrderMark.class.getName() + " wom ");
            queryBuffer.append(" where wom.isUnMarkAttention = :aIsActive");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            List<WorkOrderMark> workOrderMarks = (List<WorkOrderMark>) query.list();
            
            return workOrderMarks;
        }catch (Throwable ex) {
            log.error("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#updateWorkOrderMark(co.com.directv.sdii.model.pojo.WorkOrderMark)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Object updateWorkOrderMark(WorkOrderMark obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkOrderMark/WorkOrderMarkDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Object respuesta =  session.merge(obj);
            this.doFlush(session);
            return respuesta;
        } catch (Throwable ex) {
            log.error("== Error actualizando WorkOrderMark ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkOrderMark/WorkOrderMarkDAO ==");
        }

    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#deleteWorkOrderMark(co.com.directv.sdii.model.pojo.WorkOrderMark)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkOrderMark(WorkOrderMark obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkOrderMark/WorkOrderMarkDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error eliminando WorkOrderMark ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteWorkOrderMark/WorkOrderMarkDAO ==");
        }

    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal#getAll()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderMark> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WorkOrderMarkDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery(" from " +WorkOrderMark.class.getName() + " wom ");
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando todas las WorkOrderMark ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/WorkOrderMarkDAO ==");
        }
    }
	
}

