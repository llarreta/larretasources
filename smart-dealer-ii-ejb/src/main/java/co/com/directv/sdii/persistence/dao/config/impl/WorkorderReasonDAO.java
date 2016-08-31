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
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderReasonCategory;
import co.com.directv.sdii.model.pojo.WorkorderReasonType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkorderReason
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author <a href="mailto:jalopez@intergrupo.com">jalopez</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkorderReason
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal
 */
@Stateless(name="WorkorderReasonDAOLocal",mappedName="ejb/WorkorderReasonDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkorderReasonDAO extends BaseDao implements WorkorderReasonDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkorderReasonDAO.class);

    /**
     * Crea una WorkorderReason en el sistema
     * @param obj - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkorderReason(WorkorderReason obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkorderReason/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando WorkorderReason ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina createWorkorderReason/DAOWorkorderReasonBean ==");
        }
    }

    /**
     * Obtiene un workorderreason con el id especificado
     * @param id - Long
     * @return - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkorderReason getWorkorderReasonByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonByID/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreason from ");
            stringQuery.append(WorkorderReason.class.getName());
            stringQuery.append(" workorderreason where workorderreason.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select workorderreason from "+WorkorderReason.class.getName()+" workorderreason where workorderreason.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WorkorderReason) obj;
            }
            return null;
        }  catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReason por ID ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getWorkorderReasonByID/DAOWorkorderReasonBean ==");
        }
    }

    /**
     * Actualiza un workorderreason especificado
     * @param obj - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkorderReason(WorkorderReason obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkorderReason/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WorkorderReason  ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina updateWorkorderReason/DAOWorkorderReasonBean ==");
        }

    }

    /**
     * Elimina un workorderreason del sistema
     * @param obj - WorkorderReason
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkorderReason(WorkorderReason obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkorderReason/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("delete from ");
            stringQuery.append(WorkorderReason.class.getName());
            stringQuery.append(" wor where wor.id = :aWorId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + WorkorderReason.class.getName() + " wor where wor.id = :aWorId");
            query.setLong("aWorId", obj.getId());
            query.executeUpdate();
            this.doFlush(session);
            
        } catch (Throwable ex) {
            log.debug("== Error eliminando WorkorderReason ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina deleteWorkorderReason/DAOWorkorderReasonBean ==");
        }

    }

    /**
     * Obtiene todos los workorderreasons del sistema
     * @return - List<WorkorderReason>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderReason> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(WorkorderReason.class.getName());
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + WorkorderReason.class.getName());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderReason ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOWorkorderReasonBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal#getWorkorderReasonByCode(java.lang.String)
     */
    public WorkorderReason getWorkorderReasonByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonByCode/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreason from ");
            stringQuery.append(WorkorderReason.class.getName());
            stringQuery.append(" workorderreason where upper(workorderreason.workorderReasonCode) = :aWORCode");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select workorderreason from "+WorkorderReason.class.getName()+" workorderreason where upper(workorderreason.workorderReasonCode) = :aWORCode");
            query.setString("aWORCode", code.toUpperCase());
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (WorkorderReason) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReason por Código ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getWorkorderReasonByCode/DAOWorkorderReasonBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal#getWorkorderReasonByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	public List<WorkorderReason> getWorkorderReasonByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonByName/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        List<WorkorderReason> list = null;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreason from ");
            stringQuery.append(WorkorderReason.class.getName());
            stringQuery.append(" workorderreason where upper(workorderreason.workorderReasonName) = :aWORName");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select workorderreason from "+WorkorderReason.class.getName()+" workorderreason where upper(workorderreason.workorderReasonName) = :aWORName");
            query.setString("aWORName", name.toUpperCase());
            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReason por nombre ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getWorkorderReasonByName/DAOWorkorderReasonBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal#getWorkorderReasonByCategory(co.com.directv.sdii.model.pojo.WorkorderReasonCategory)
     */
    @SuppressWarnings("unchecked")
	public List<WorkorderReason> getWorkorderReasonByCategory(WorkorderReasonCategory category) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonByCategory/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        List<WorkorderReason> list = null;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreason from ");
            stringQuery.append(WorkorderReason.class.getName());
            stringQuery.append(" workorderreason where workorderreason.workorderReasonCategory.id = :aWORCatId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select workorderreason from "+WorkorderReason.class.getName()+" workorderreason where workorderreason.workorderReasonCategory.id = :aWORCatId");
            query.setLong("aWORCatId", category.getId());
            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReason por WOReasonCategory ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getWorkorderReasonByCategory/DAOWorkorderReasonBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal#getWorkorderReasonByCategoryType(co.com.directv.sdii.model.pojo.WorkorderReasonCategory, co.com.directv.sdii.model.pojo.WorkorderReasonType)
     */
    @SuppressWarnings("unchecked")
	public List<WorkorderReason> getWorkorderReasonByCategoryType(WorkorderReasonCategory category, WorkorderReasonType type) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonByCategoryType/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        List<WorkorderReason> list = null;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreason from ");
            stringQuery.append(WorkorderReason.class.getName());
            stringQuery.append(" workorderreason where ");
            stringQuery.append("workorderreason.workorderReasonCategory.id = :aWORCatId and ");
            stringQuery.append("workorderreason.workorderReasonCategory.workorderReasonType.id = :aWorCatTypeId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select workorderreason from "+WorkorderReason.class.getName()+" workorderreason where workorderreason.workorderReasonCategory.id = :aWORCatId and workorderreason.workorderReasonCategory.workorderReasonType.id = :aWorCatTypeId");
            query.setLong("aWORCatId", category.getId());
            query.setLong("aWorCatTypeId", type.getId());
            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReason por WOReasonCategoryType ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getWorkorderReasonByCategoryType/DAOWorkorderReasonBean ==");
        }
    }

	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal#getWorkOrderReasonByWoStatus(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkorderReason> getWorkOrderReasonByWoStatus(Long id)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWorkOrderReasonByWoStatus/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        List<WorkorderReason> list = null;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreason from ");
            stringQuery.append(WorkorderReason.class.getName() );
            stringQuery.append(" workorderreason where ");
            stringQuery.append("workorderreason.workorderStatus.id = :idWoStatus ");
            
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("idWoStatus", id);
            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderReason por WOReason ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getWorkOrderReasonByWoStatus/DAOWorkorderReasonBean ==");
        }
		
	}
	
	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkorderReason> getWorkOrderReasonByWoStatusAndBySolveByCI(Long id,String isSolveByCI)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWorkOrderReasonByWoStatus/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        List<WorkorderReason> list = null;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderreason from ");
            stringQuery.append(WorkorderReason.class.getName() );
            stringQuery.append(" workorderreason where ");
            stringQuery.append("workorderreason.workorderStatus.id = :idWoStatus ");
            stringQuery.append("and workorderreason.isSolveByCi = :isSolveByCI ");
            
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("idWoStatus", id);
            query.setString("isSolveByCI", isSolveByCI);
            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error consultando getWorkOrderReasonByWoStatus por WOReason ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getWorkOrderReasonByWoStatus/DAOWorkorderReasonBean ==");
        }
		
	}

}
