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
import co.com.directv.sdii.model.pojo.WorkorderReasonCategory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonCategoryDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkorderReasonCategory
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkorderReasonCategory
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonCategoryDAOLocal
 */
@Stateless(name="WorkorderReasonCategoryDAOLocal",mappedName="ejb/WorkorderReasonCategoryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkorderReasonCategoryDAO extends BaseDao implements WorkorderReasonCategoryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkorderReasonCategoryDAO.class);

    /**
     * Crea una WorkorderReasonCategory en el sistema
     * @param obj - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkorderReasonCategory(WorkorderReasonCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkorderReasonCategory/WorkorderReasonCategoryDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando WorkorderReasonCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderReasonCategory/WorkorderReasonCategoryDAO ==");
        }
    }

    /**
     * Obtiene un workorderreasoncategory con el id especificado
     * @param id - Long
     * @return - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkorderReasonCategory getWorkorderReasonCategoryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonCategoryByID/WorkorderReasonCategoryDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreasoncategory from ");
            stringQuery.append(WorkorderReasonCategory.class.getName());
            stringQuery.append(" workorderreasoncategory where workorderreasoncategory.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select workorderreasoncategory from "+WorkorderReasonCategory.class.getName()+" workorderreasoncategory where workorderreasoncategory.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WorkorderReasonCategory) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReasonCategory por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderReasonCategoryByID/WorkorderReasonCategoryDAO ==");
        }
    }

    /**
     * Actualiza un workorderreasoncategory especificado
     * @param obj - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkorderReasonCategory(WorkorderReasonCategory obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkorderReasonCategory/WorkorderReasonCategoryDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WorkorderReasonCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkorderReasonCategory/WorkorderReasonCategoryDAO ==");
        }

    }

    /**
     * Elimina un workorderreasoncategory del sistema
     * @param obj - WorkorderReasonCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkorderReasonCategory(WorkorderReasonCategory obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkorderReasonCategory/WorkorderReasonCategoryDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando WorkorderReasonCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWorkorderReasonCategory/WorkorderReasonCategoryDAO ==");
        }

    }

    /**
     * Obtiene todos los workorderreasoncategorys del sistema
     * @return - List<WorkorderReasonCategory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderReasonCategory> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WorkorderReasonCategoryDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkorderReasonCategory");
            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando las WorkorderReasonCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/WorkorderReasonCategoryDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonCategoryDAOLocal#getWOReasonCategoriesByReasonTypeId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	public List<WorkorderReasonCategory> getWOReasonCategoriesByReasonTypeId(Long woReasonTypeId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getWOReasonCategoriesByReasonTypeId/WorkorderReasonCategoryDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(WorkorderReasonCategory.class.getName());
            stringQuery.append(" worc where worc.workorderReasonType.id = :aWorctId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + WorkorderReasonCategory.class.getName() + " worc where worc.workorderReasonType.id = :aWorctId");
            query.setLong("aWorctId", woReasonTypeId);
            
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error las WorkorderReasonCategory por Tipo de WOReason ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWOReasonCategoriesByReasonTypeId/WorkorderReasonCategoryDAO ==");
        }
    }

}
