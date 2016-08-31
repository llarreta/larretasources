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

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkorderCSRStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkorderCSRStatusDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkorderCSRStatus
 * 
 * Fecha de Creacion: Mar 13, 2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkorderCSRStatus
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderCSRStatusDAOLocal
 */
@Stateless(name="WorkorderCSRStatusDAOLocal",mappedName="ejb/WorkorderCSRStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkorderCSRStatusDAO extends BaseDao implements WorkorderCSRStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkorderCSRStatusDAO.class);

    /**
     * Crea una WorkorderCSRStatus en el sistema
     * @param obj - WorkOrderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkorderCSRStatus(WorkorderCSRStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkorderCSRStatus/WorkorderCSRStatusDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando WorkorderCSRStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderCSRStatus/WorkorderCSRStatusDAO ==");
        }
    }

    /**
     * Obtiene un WorkOrderCSRStatus con el id especificado
     * @param id - Long
     * @return - WorkorderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkorderCSRStatus getWorkorderCSRStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderCSRStatusByID/WorkorderCSRStatusDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workordercsrstatus from ");
            stringQuery.append(WorkorderCSRStatus.class.getName());
            stringQuery.append(" workordercsrstatus where workordercsrstatus.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (WorkorderCSRStatus) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkorderCSRStatus por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderCSRStatusByID/WorkorderCSRStatusDAO ==");
        }
    }

    /**
     * Actualiza un WorkOrderCSRStatus especificado
     * @param obj - WorkOrderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkorderCSRStatus(WorkorderCSRStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkorderCSRStatus/WorkorderCSRStatusDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error actualizando WorkorderCSRStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkorderCSRStatus/WorkorderCSRStatusDAO ==");
        }
    }

    /**
     * Obtiene todos los WorkOrderCSRStatus del sistema
     * @return - List<WorkOrderCSRStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderCSRStatus> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WorkorderCSRStatusDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkorderCSRStatus");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderCSRStatus ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/WorkorderCSRStatusDAO ==");
        }
    }

    /**
     * Obtiene un WorkOrderCSRStatus con el codigo especificado
     * @param code - String
     * @return WorkOrderCSRStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderCSRStatus getWorkorderCSRStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderCSRStatusByCode/WorkorderCSRStatusDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workordercsrstatus from ");
            stringQuery.append(WorkorderCSRStatus.class.getName());
            stringQuery.append(" workordercsrstatus where workordercsrstatus.code = :code");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (WorkorderCSRStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderCSRStatus por Código ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderCSRStatusByCode/WorkorderCSRStatusDAO ==");
        }
    }

}
