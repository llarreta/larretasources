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
import co.com.directv.sdii.model.pojo.WorkorderReasonType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonTypeDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkorderReasonType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkorderReasonType
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonTypeDAOLocal
 */
@Stateless(name="WorkorderReasonTypeDAOLocal",mappedName="ejb/WorkorderReasonTypeDAOLocalo s")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkorderReasonTypeDAO extends BaseDao implements WorkorderReasonTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkorderReasonTypeDAO.class);

    /**
     * Crea un WorkorderReasonType en el sistema
     * @param obj - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkorderReasonType(WorkorderReasonType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkorderReasonType/WorkorderReasonTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando WorkorderReasonType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderReasonType/WorkorderReasonTypeDAO ==");
        }
    }

    /**
     * Obtiene un workorderreasontype con el id especificado
     * @param id - Long
     * @return - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkorderReasonType getWorkorderReasonTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonTypeByID/WorkorderReasonTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreasontype from ");
            stringQuery.append("WorkorderReasonType workorderreasontype where ");
            stringQuery.append("workorderreasontype.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select workorderreasontype from WorkorderReasonType workorderreasontype where workorderreasontype.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WorkorderReasonType) obj;
            }
            return null;
        }  catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReasonType por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderReasonTypeByID/WorkorderReasonTypeDAO ==");
        }
    }

    /**
     * Actualiza un workorderreasontype especificado
     * @param obj - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkorderReasonType(WorkorderReasonType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkorderReasonType/WorkorderReasonTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WorkorderReasonType ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina updateWorkorderReasonType/WorkorderReasonTypeDAO ==");
        }

    }

    /**
     * Elimina un workorderreasontype del sistema
     * @param obj - WorkorderReasonType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkorderReasonType(WorkorderReasonType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkorderReasonType/WorkorderReasonTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando WorkorderReasonType ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina deleteWorkorderReasonType/WorkorderReasonTypeDAO ==");
        }

    }

    /**
     * Obtiene todos los workorderreasontypes del sistema
     * @return - List<WorkorderReasonType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderReasonType> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WorkorderReasonTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkorderReasonType");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los WorkorderReasonType  ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/WorkorderReasonTypeDAO ==");
        }
    }

}
