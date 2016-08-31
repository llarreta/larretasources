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
import co.com.directv.sdii.model.dto.WorkOrderMarkDTO;
import co.com.directv.sdii.model.pojo.WorkOrderWorkOrderMark;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkOrderWorkOrderMark
 * 
 * Fecha de Creación: 19/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="WorkOrderWorkOrderMarkDAOLocal",mappedName="ejb/WorkOrderWorkOrderMarkDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderWorkOrderMarkDAO extends BaseDao implements WorkOrderWorkOrderMarkDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderWorkOrderMarkDAO.class);

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#createWorkOrderWorkOrderMark(co.com.directv.sdii.model.pojo.WorkOrderWorkOrderMark)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkOrderWorkOrderMark(WorkOrderWorkOrderMark obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkOrderWorkOrderMark/WorkOrderWorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error creando WorkOrderWorkOrderMark ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderWorkOrderMark/WorkOrderWorkOrderMarkDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderWorkOrderMarkByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderWorkOrderMark getWorkOrderWorkOrderMarkByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderWorkOrderMarkByID/WorkOrderWorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from " + WorkOrderWorkOrderMark.class.getName() + " wom ");
            queryBuffer.append(" where wom.id = :id ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (WorkOrderWorkOrderMark) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderWorkOrderMarkByID/WorkOrderWorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#updateWorkOrderWorkOrderMark(co.com.directv.sdii.model.pojo.WorkOrderWorkOrderMark)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Object updateWorkOrderWorkOrderMark(WorkOrderWorkOrderMark obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkOrderWorkOrderMark/WorkOrderWorkOrderMarkDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Object respuesta =  session.merge(obj);
            this.doFlush(session);
            return respuesta;
        } catch (Throwable ex) {
            log.error("== Error actualizando WorkOrderWorkOrderMark ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkOrderWorkOrderMark/WorkOrderWorkOrderMarkDAO ==");
        }

    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#deleteWorkOrderWorkOrderMark(co.com.directv.sdii.model.pojo.WorkOrderWorkOrderMark)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkOrderWorkOrderMark(WorkOrderWorkOrderMark obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkOrderWorkOrderMark/WorkOrderWorkOrderMarkDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error eliminando WorkOrderWorkOrderMark ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteWorkOrderWorkOrderMark/WorkOrderWorkOrderMarkDAO ==");
        }

    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderWorkOrderMark> getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(Long woId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();

            queryBuffer.append(" SELECT wowom ");
            queryBuffer.append("   FROM " + WorkOrderWorkOrderMark.class.getName() + " wowom INNER JOIN wowom.workOrderMark wom ");
            queryBuffer.append("                                                             INNER JOIN wowom.workOrder wo ");
            queryBuffer.append("  WHERE wowom.isActive=:aIsActive ");
            queryBuffer.append("    AND wom.isUnMarkAttention=:aIsActive ");
            queryBuffer.append("    AND wo.id=:aWoId ");
             
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("aWoId", woId);
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            List<WorkOrderWorkOrderMark> workOrderWorkOrderMark =  (List<WorkOrderWorkOrderMark>) query.list();
            
            return workOrderWorkOrderMark;
        }catch (Throwable ex) {
            log.debug("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderWorkOrderMark> getWorkOrderWorkOrderMarkIsUnMarkAttention(Long woId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderWorkOrderMarkIsUnMarkAttention/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();

            queryBuffer.append(" SELECT wowom ");
            queryBuffer.append("   FROM " + WorkOrderWorkOrderMark.class.getName() + " wowom INNER JOIN wowom.workOrderMark wom ");
            queryBuffer.append("                                                             INNER JOIN wowom.workOrder wo ");
            queryBuffer.append("  WHERE wom.isUnMarkAttention=:aIsActive ");
            queryBuffer.append("    AND wo.id=:aWoId ");
             
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("aWoId", woId);
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            List<WorkOrderWorkOrderMark> workOrderWorkOrderMark =  (List<WorkOrderWorkOrderMark>) query.list();
            
            return workOrderWorkOrderMark;
        }catch (Throwable ex) {
            log.debug("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderWorkOrderMarkIsUnMarkAttention/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderMarkDTOIsActiveByWoId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderMarkDTO> getWorkOrderMarkDTOIsActive(Long woId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();

            queryBuffer.append(" SELECT new " + WorkOrderMarkDTO.class.getName() + "(wom.code,wom.name,wom.color) ");
            queryBuffer.append("   FROM " + WorkOrderWorkOrderMark.class.getName() + " wowom INNER JOIN wowom.workOrderMark wom ");
            queryBuffer.append("                                     INNER JOIN wowom.workOrder wo ");
            queryBuffer.append("  WHERE wowom.isActive=:aIsActive ");
            queryBuffer.append("    AND wo.id=:aWoId ");
             
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("aWoId", woId);
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            List<WorkOrderMarkDTO> workOrderMarkDTOS =  (List<WorkOrderMarkDTO>) query.list();
            
            return workOrderMarkDTOS;
        }catch (Throwable ex) {
            log.debug("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderMarkDTOIsActiveByWoId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderMarkDTO> getWorkOrderMarkDTOIsActive(String woCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();

            queryBuffer.append(" SELECT new " + WorkOrderMarkDTO.class.getName() + "(wom.code,wom.name,wom.color) ");
            queryBuffer.append("   FROM " + WorkOrderWorkOrderMark.class.getName() + " wowom INNER JOIN wowom.workOrderMark wom ");
            queryBuffer.append("                                     INNER JOIN wowom.workOrder wo ");
            queryBuffer.append("  WHERE wowom.isActive=:aIsActive ");
            queryBuffer.append("    AND wo.woCode=:aWoCode ");
             
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("aWoCode", woCode);
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            List<WorkOrderMarkDTO> workOrderMarkDTOS =  (List<WorkOrderMarkDTO>) query.list();
            
            return workOrderMarkDTOS;
        }catch (Throwable ex) {
            log.debug("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderMarkDTOIsActiveByWoId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderWorkOrderMark getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark(Long woId,Long workOrderMarkId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            
            queryBuffer.append(" SELECT wowom ");
            queryBuffer.append("   FROM " + WorkOrderWorkOrderMark.class.getName() + " wowom INNER JOIN wowom.workOrderMark wom ");
            queryBuffer.append("                                     INNER JOIN wowom.workOrder wo ");
            queryBuffer.append("  WHERE wowom.isActive=:aIsActive ");
            queryBuffer.append("    AND wo.id=:aWoId ");
            queryBuffer.append("    AND wom.id=:aWorkOrderMarkId ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("aWoId", woId);
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            query.setLong("aWorkOrderMarkId",workOrderMarkId);
            
            WorkOrderWorkOrderMark workOrderWorkOrderMark = (WorkOrderWorkOrderMark) query.uniqueResult();
            
            return workOrderWorkOrderMark;
        }catch (Throwable ex) {
            log.debug("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderMarkDTOIsActiveByWoId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderWorkOrderMark> getWorkOrderWorkOrderMarkIsActiveByWoCodesAndCodeWorkOrderMark(List<String> woCodes, Long workOrderMarkId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            
            queryBuffer.append(" SELECT wowom ");
            queryBuffer.append("   FROM " + WorkOrderWorkOrderMark.class.getName() + " wowom INNER JOIN wowom.workOrderMark wom ");
            queryBuffer.append("                                     INNER JOIN wowom.workOrder wo ");
            queryBuffer.append("  WHERE wowom.isActive=:aIsActive ");
            String stringWoCodes = " '' ";
            for(String woCode : woCodes){
            	stringWoCodes += " , '"+woCode+"'";
            }
            queryBuffer.append("    AND wo.woCode in ("+stringWoCodes+") ");
            queryBuffer.append("    AND wom.id=:aWorkOrderMarkId ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            query.setLong("aWorkOrderMarkId",workOrderMarkId);
            
            List<WorkOrderWorkOrderMark> result  = (List<WorkOrderWorkOrderMark>)query.list();
            
            return result;
        }catch (Throwable ex) {
            log.debug("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal#getWorkOrderMarkDTOIsActiveByWoId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderWorkOrderMark getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark(String woCode,Long workOrderMarkId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            
            queryBuffer.append(" SELECT wowom ");
            queryBuffer.append("   FROM " + WorkOrderWorkOrderMark.class.getName() + " wowom INNER JOIN wowom.workOrderMark wom ");
            queryBuffer.append("                                     INNER JOIN wowom.workOrder wo ");
            queryBuffer.append("  WHERE wowom.isActive=:aIsActive ");
            queryBuffer.append("    AND wo.woCode=:aWoCode ");
            queryBuffer.append("    AND wo.id=:aWorkOrderMarkId ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("aWoCode", woCode);
            query.setString("aIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
            query.setLong("aWorkOrderMarkId",workOrderMarkId);
            
            WorkOrderWorkOrderMark workOrderWorkOrderMark = (WorkOrderWorkOrderMark) query.uniqueResult();
            
            return workOrderWorkOrderMark;
        }catch (Throwable ex) {
            log.debug("== Error consultando codeWorkOrderMark por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkDAO ==");
        }
    }

}

