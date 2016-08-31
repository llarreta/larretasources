package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.ArrayList;
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
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkOrderAgenda
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkOrderAgenda
 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal
 */
@Stateless(name="WorkOrderAgendaDAOLocal",mappedName="ejb/WorkOrderAgendaDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderAgendaDAO extends BaseDao implements WorkOrderAgendaDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderAgendaDAO.class);

    /**
     * Crea una WorkOrderAgenda en el sistema
     * @param obj - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkOrderAgenda(WorkOrderAgenda obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createWorkOrderAgenda/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            WorkOrderAgenda lastWOAgenda = getLastWorkOrderAgendaByWoAssignmentId(obj.getWoAssignment().getId());
            if(lastWOAgenda != null){
            	lastWOAgenda.setStatus(CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_INACTIVE.getCodeEntity());
            	updateWorkOrderAgenda(lastWOAgenda);
            }
            obj.setStatus(CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
            session.save(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error creando WorkOrderAgenda ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderAgenda/WorkOrderAgendaDAO ==");
        }
    }

    /**
     * Obtiene un workorderagenda con el id especificado
     * @param id - Long
     * @return - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderAgenda getWorkOrderAgendaByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderAgendaByID/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderagenda from ");
            stringQuery.append("WorkOrderAgenda workorderagenda ");
            stringQuery.append("where ");
            stringQuery.append("workorderagenda.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select workorderagenda from WorkOrderAgenda workorderagenda where workorderagenda.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WorkOrderAgenda) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderAgenda por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderAgendaByID/WorkOrderAgendaDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal#getLastWorkOrderAgendaByWoID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderAgenda getLastWorkOrderAgendaByWoID(Long idWo) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getLastWorkOrderAgendaByWoID/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderagenda ");
            stringQuery.append("from WorkOrderAgenda workorderagenda ");
            stringQuery.append("where ");
            stringQuery.append("workorderagenda.woAssignment.workOrder.id = ");
            stringQuery.append(idWo);
            stringQuery.append(" and workorderagenda.status = :aWoAgendaStatus order by workorderagenda.agendationDate desc");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("aWoAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
            List<WorkOrderAgenda> obj = query.list();
            if(obj == null || obj.isEmpty())
          	  return null;
            return obj.get(0);
        }  catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderAgenda por WOID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getLastWorkOrderAgendaByWoID/WorkOrderAgendaDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal#getLastWorkOrderAgendaByWoID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderAgenda getLastWorkOrderAgendaByWoAssignmentId(Long woAssignmentId)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getLastWorkOrderAgendaByWoAssignmentId/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderagenda ");
            stringQuery.append("from WorkOrderAgenda workorderagenda ");
            stringQuery.append("where ");
            stringQuery.append("workorderagenda.woAssignment.workOrder.id in (select woa.workOrder.id from WoAssignment woa where woa.id = :aWoAssignmentId) ");
            stringQuery.append(" and workorderagenda.status = :aWoAgendaStatus order by workorderagenda.agendationDate desc");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWoAssignmentId", woAssignmentId);
            query.setString("aWoAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
            List<WorkOrderAgenda> obj = query.list();
            if(obj == null || obj.isEmpty())
          	  return null;
            return obj.get(0);
        }  catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderAgenda por getLastWorkOrderAgendaByWoAssignmentId ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getLastWorkOrderAgendaByWoAssignmentId/WorkOrderAgendaDAO ==");
        }
	}

    /**
     * Actualiza un workorderagenda especificado
     * @param obj - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkOrderAgenda(WorkOrderAgenda obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkOrderAgenda/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WorkOrderAgenda ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateWorkOrderAgenda/WorkOrderAgendaDAO ==");
        }

    }

    /**
     * Elimina un workorderagenda del sistema
     * @param obj - WorkOrderAgenda
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkOrderAgenda(WorkOrderAgenda obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkOrderAgenda/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando WorkOrderAgenda  ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteWorkOrderAgenda/WorkOrderAgendaDAO ==");
        }

    }

    /**
     * Obtiene todos los workorderagendas del sistema
     * @return - List<WorkOrderAgenda>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderAgenda> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkOrderAgenda");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkOrderAgenda ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/WorkOrderAgendaDAO ==");
        }
    }

    /**
     * 
     * Metodo: Retorna un listado de WorkOrderAgenda, filtrando por
     * el dealerId y WorkOrderId por medio de la WorkOrder Assignment
     * @param woId Long
     * @param dealerId Long
     * @return List<WorkOrderAgenda>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderAgenda> getWorkOrderAgendaByWoAssignment(Long woId,Long dealerId)	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWorkOrderAgendaByWoAssignment/WorkOrderAgendaDAO ==");
		Session session = ConnectionFactory.getSession();
		List<WorkOrderAgenda> res = new ArrayList<WorkOrderAgenda>();
		try {
		    if (session == null) {
		        throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
		    }
		    StringBuffer stringQuery = new StringBuffer();	
		    stringQuery.append(" from ");
		    stringQuery.append(WorkOrderAgenda.class.getName());
		    stringQuery.append(" workOrderAgenda");
		    stringQuery.append(" where");
		    stringQuery.append(" workOrderAgenda.woAssignment.workOrder.id = ?");
		    stringQuery.append(" AND workOrderAgenda.woAssignment.dealerId = ? and workOrderAgenda.status = ?");
		    
		    Query query = session.createQuery(stringQuery.toString());
		    query.setLong(0, woId);
		    query.setLong(1, dealerId);
		    query.setString(2, CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
		    
		    Object obj = query.list();
		    if(obj != null){
		    	res=(List<WorkOrderAgenda>) obj;
		  	  	return res;
		    }
		    return res;
		} catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderAgenda por WOID y DealerID ==");
            throw this.manageException(ex);
        }  finally {
		    log.debug("== Termina getWorkOrderAgendaByWoAssignment/WorkOrderAgendaDAO ==");
		}
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderAgenda getLastWorkOrderAgendaByCustomerIdAndWoStatus(Long customerId , String woStatusCode) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicio getLastWorkOrderAgendaByCustomerIdAndWoStatus/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderagenda ");
            stringQuery.append("from WorkOrderAgenda workorderagenda ");
            stringQuery.append("where ");
            stringQuery.append("workorderagenda.woAssignment.workOrder.customer.id = :customerId ");
            stringQuery.append("and workorderagenda.woAssignment.workOrder.workorderStatusByActualStatusId.woStateCode = :woStatusCode order by workorderagenda.agendationDate desc ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("woStatusCode", woStatusCode);
            query.setLong("customerId", customerId);
            
            List<WorkOrderAgenda> obj = query.list();
            if(obj == null || obj.isEmpty())
          	  return null;
            return obj.get(0);
        }  catch (Throwable ex) {
            log.debug("== Error getLastWorkOrderAgendaByCustomerIdAndWoStatus/WorkOrderAgendaDAO ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getLastWorkOrderAgendaByCustomerIdAndWoStatus/WorkOrderAgendaDAO ==");
        }
	}
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderAgenda> getLastWorkOrderAgendaByWoIDOrderByAgendaDate(Long idWo)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getLastWorkOrderAgendaByWoIDOrderByAgendaDate/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderagenda ");
            stringQuery.append("from WorkOrderAgenda workorderagenda ");
            stringQuery.append("where ");
            stringQuery.append("workorderagenda.woAssignment.workOrder.id = :idWo ");
            stringQuery.append("order by workorderagenda.agendationDate desc");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("idWo", idWo);
            query.setMaxResults( 3 );
            return query.list();
        }  catch (Throwable ex) {
            log.debug("== Error getLastWorkOrderAgendaByWoIDOrderByAgendaDate/WorkOrderAgendaDAO ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getLastWorkOrderAgendaByWoIDOrderByAgendaDate/WorkOrderAgendaDAO ==");
        }
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal#getFirstAgendaDateByDealerIdAndWoId(java.lang.Long, java.lang.Long)
     */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderAgenda getFirstAgendaByDealerIdAndWoId(Long woId, Long dealerId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getFirstAgendaDateByDealerIdAndWoId/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(WorkOrderAgenda.class.getName());
            stringQuery.append(" woAgenda1 where ");
            stringQuery.append("woAgenda1.woAssignment.dealerId = :dealerId ");
            stringQuery.append("and woAgenda1.woAssignment.workOrder.id = :woId ");
            stringQuery.append("and woAgenda1.agendationDate = (");
            stringQuery.append("select min(woAgenda.agendationDate) from ");
            stringQuery.append(WorkOrderAgenda.class.getName());
            stringQuery.append(" woAgenda where ");
            stringQuery.append("woAgenda.woAssignment.dealerId = :dealerId ");
            stringQuery.append("and woAgenda.woAssignment.workOrder.id = :woId ");
            stringQuery.append(" )");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setLong("woId", woId);
            
            List<WorkOrderAgenda> minAgendas = query.list();
            
            if(minAgendas.isEmpty()){
            	return null;
            }
            
            return  minAgendas.get(0);
        }  catch (Throwable ex) {
            log.debug("== Error getFirstAgendaDateByDealerIdAndWoId/WorkOrderAgendaDAO ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getFirstAgendaDateByDealerIdAndWoId/WorkOrderAgendaDAO ==");
        }
	}

	 /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal#getLastWorkOrderAgendaByWoID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderAgenda> getWorkOrderAgendaByWoDealer(Long woId, Long dealerId) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getWorkOrderAgendaByWoDealer/WorkOrderAgendaDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(WorkOrderAgenda.class.getName());
            stringQuery.append(" workorderagenda ");
            stringQuery.append(" where ");
            stringQuery.append(" workorderagenda.woAssignment.workOrder.id = :woId ");
            stringQuery.append(" and workorderagenda.woAssignment.dealerId =:dealerId order by workorderagenda.agendationDate desc ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("woId", woId);
            query.setLong("dealerId", dealerId);
            
            return query.list();
            
        }  catch (Throwable ex) {
            log.debug("== Error consultando getWorkOrderAgendaByWoDealer/WorkOrderAgendaDAO ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderAgendaByWoDealer/WorkOrderAgendaDAO ==");
        }
	}
    
}
