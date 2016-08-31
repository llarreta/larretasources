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

import co.com.directv.sdii.business.CodesBusinessEntityHelper;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.SpearRuleStatus;
import co.com.directv.sdii.model.pojo.WoProcessSource;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkorderStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkorderStatus
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal
 */
@Stateless(name="WorkorderStatusDAOLocal",mappedName="ejb/WorkorderStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkorderStatusDAO extends BaseDao implements WorkorderStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkorderStatusDAO.class);

    /**
     * Crea una WorkorderStatus en el sistema
     * @param obj - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkorderStatus(WorkorderStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkorderStatus/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando WorkorderStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderStatus/DAOWorkorderStatusBean ==");
        }
    }

    /**
     * Obtiene un workorderstatus con el id especificado
     * @param id - Long
     * @return - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkorderStatus getWorkorderStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderStatusByID/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderstatus from ");
            stringQuery.append(WorkorderStatus.class.getName());
            stringQuery.append(" workorderstatus where workorderstatus.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select workorderstatus from " + WorkorderStatus.class.getName() + " workorderstatus where workorderstatus.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WorkorderStatus) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkorderStatus por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderStatusByID/DAOWorkorderStatusBean ==");
        }
    }

    /**
     * Actualiza un workorderstatus especificado
     * @param obj - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkorderStatus(WorkorderStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkorderStatus/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error actualizando WorkorderStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkorderStatus/DAOWorkorderStatusBean ==");
        }
    }

    /**
     * Elimina un workorderstatus del sistema
     * @param obj - WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkorderStatus(WorkorderStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkorderStatus/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("delete from ");
            stringQuery.append(WorkorderStatus.class.getName());
            stringQuery.append(" ws where ws.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + WorkorderStatus.class.getName() + " ws where ws.id = :id");
            query.setLong("id", obj.getId());
            
            query.executeUpdate();
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error borrando WorkorderStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWorkorderStatus/DAOWorkorderStatusBean ==");
        }

    }

    /**
     * Obtiene todos los workorderstatuss del sistema
     * @return - List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatus> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkorderStatus");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderStatus ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/DAOWorkorderStatusBean ==");
        }
    }

    /**
     * Obtiene un workorderstatus con el codigo especificado
     * @param code - String
     * @return WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkorderStatus getWorkorderStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderStatusByCode/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderstatus from ");
            stringQuery.append(WorkorderStatus.class.getName());
            stringQuery.append(" workorderstatus where workorderstatus.woStateCode = :woStateCode");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select workorderstatus from " + WorkorderStatus.class.getName() + " workorderstatus where workorderstatus.woStateCode = :woStateCode");
            query.setString("woStateCode", code);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (WorkorderStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderStatus por Código ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderStatusByCode/DAOWorkorderStatusBean ==");
        }
    }

    /**
     * Obtiene un listado de workorderstatus con el nombre especificado
     * @param code - String
     * @return WorkorderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	public List<WorkorderStatus> getWorkorderStatusByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderStatusByName/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderstatus from ");
            stringQuery.append(WorkorderStatus.class.getName());
            stringQuery.append(" workorderstatus where workorderstatus.woStateName = :name");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select workorderstatus from " + WorkorderStatus.class.getName() + " workorderstatus where workorderstatus.woStateName = :name");
            query.setString("name", name);

            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkorderStatus por nombre ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderStatusByName/DAOWorkorderStatusBean ==");
        }
    }

    /**
     * Obtiene las workorderstatus por el Ibs6Status
     * @param status - Ibs6Status
     * @return List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	public List<WorkorderStatus> getWorkorderStatusByIBS6Status(Ibs6Status status) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderStatusByIBS6Status/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workorderstatus from ");
            stringQuery.append(WorkorderStatus.class.getName());
            stringQuery.append(" workorderstatus where workorderstatus.ibs6Status.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", status.getId());

            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderStatus por Ibs6Status ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByIBS6Status/DAOWorkorderStatusBean ==");
        }
    } 
    
    /**
     * 
     * Metodo: Consulta para obtener un SpearRuleStatus
     * filtrando por el estado anterior y actual de la
     * work order, para la validacion de la maquina de 
     * estados.
     * @param WorkOrder workOrder
     * @param Long statusChange
     * @return SpearRuleStatus
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jlopez
     */
    @Deprecated
    public SpearRuleStatus getWorkorderStatusBySpearRule(WorkOrder workOrder,Long statusChange)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getWorkorderStatusBySpearRule/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(SpearRuleStatus.class.getName());
            queryBuffer.append(" spearrule where ");
            queryBuffer.append(" spearrule.workorderStatusByToStatus.id = :idTo and ");
            queryBuffer.append(" spearrule.workorderStatusByFromStatus.id = :idFrom ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("idFrom",workOrder.getWorkorderStatusByActualStatusId().getId());
            query.setLong("idTo", statusChange );           

            Object res = query.uniqueResult();
            if (res != null) {
                return (SpearRuleStatus) res;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkorderStatus por WO y Status a cambiar==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderStatusBySpearRule/DAOWorkorderStatusBean ==");
        }
    }
    
    /**
     * 
     * Metodo: Consulta para obtener un SpearRuleStatus
     * filtrando por el codigo del estado anterior y actual de la
     * work order, para la validacion de la maquina de 
     * estados.
     * @param WorkOrder workOrder
     * @param String statusChange
     * @return SpearRuleStatus
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jlopez
     */
    public SpearRuleStatus getWorkorderStatusBySpearRuleByCodes(WorkOrder workOrder,String statusChange)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getWorkorderStatusBySpearRule/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();
        CodesBusinessEntityHelper helper;  
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            helper = new CodesBusinessEntityHelper(WorkorderStatus.class.getName(), workOrder.getWorkorderStatusByActualStatusId().getWoStateCode(),  "woStateCode");               
            Long actualStatus = helper.getIdEntityByCodeEntity();
            helper = new CodesBusinessEntityHelper(WorkorderStatus.class.getName(), statusChange,  "woStateCode");    
            Long previusStatus = helper.getIdEntityByCodeEntity();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(SpearRuleStatus.class.getName());
            queryBuffer.append(" spearrule where ");
            queryBuffer.append(" spearrule.workorderStatusByToStatus.id = :idTo and ");
            queryBuffer.append(" spearrule.workorderStatusByFromStatus.id = :idFrom ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("idFrom",actualStatus);
            query.setLong("idTo", previusStatus );           

            Object res = query.uniqueResult();
            if (res != null) {
                return (SpearRuleStatus) res;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkorderStatus por WO y Status a cambiar ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderStatusBySpearRule/DAOWorkorderStatusBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal#getWorkorderStatusByIBS6StatusCode(java.lang.String)
	 */
    @Override
    @SuppressWarnings("unchecked")
	public WorkorderStatus getWorkorderStatusByIBS6StatusCode(String ibs6StatusCode) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getWorkorderStatusByIBS6StatusCode/DAOWorkorderStatusBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WorkorderStatus.class.getName());
        	stringQuery.append(" wos where wos.ibs6Status.ibs6StateCode = :anIbs6StatusCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anIbs6StatusCode", ibs6StatusCode);

            List<WorkorderStatus> result = query.list();
            if(result.isEmpty()){
            	return null;
            }
            
            WorkorderStatus resultWoStatus = result.get(0); 
            return resultWoStatus; 
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkorderStatus por Ibs6StatusCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByIBS6StatusCode/DAOWorkorderStatusBean ==");
        }
	}
	
    @Override
    @SuppressWarnings("unchecked")
	public List<WorkorderStatus> getWorkorderStatusByIBS6StatusCode(Ibs6Status ibs6Status) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getWorkorderStatusByIBS6StatusCode/DAOWorkorderStatusBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WorkorderStatus.class.getName());
        	stringQuery.append(" wos where wos.ibs6Status.ibs6StateCode = :anIbs6StatusCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anIbs6StatusCode", ibs6Status.getIbs6StateCode());
            
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando getWorkorderStatusByIBS6StatusCode/DAOWorkorderStatusBean==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByIBS6StatusCode/DAOWorkorderStatusBean ==");
        }
	}
    
	/**
     * Obtiene todos los workorderstatuss del sistema para la lista de work orders
     * @return - List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jnova
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatus> getWorkOrderStatusForDealerTray() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getWorkOrderStatusForDealerTray/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
        	stringQuery.append(WorkorderStatus.class.getName());
        	stringQuery.append(" wos where ");
        	stringQuery.append("wos.woStateCode <> :wosActive ");
        	stringQuery.append("and wos.woStateCode <> :wosRejected ");
        	stringQuery.append("and wos.woStateCode <> :wosCancel ");
        	stringQuery.append(" order by wos.woStateName asc ");
        	
            Query query = session.createQuery(stringQuery.toString());
            query.setString("wosActive", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
            query.setString("wosRejected", CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity());
            query.setString("wosCancel", CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderStatus getWorkOrderStatusForDealerTray/DAOWorkorderStatusBean==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderStatusForDealerTray/DAOWorkorderStatusBean ==");
        }
    }
    
    /**
     * Obtiene todos los workorderstatuss del sistema para asigancion de WO
     * @return - List<WorkorderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jnova
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatus> getAllWorkOrdersToAsign() throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getAllWorkOrdersToAsign/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
        	stringQuery.append(WorkorderStatus.class.getName());
        	stringQuery.append(" wos where ");
        	stringQuery.append("wos.woStateCode = :wosActive ");
        	stringQuery.append("or wos.woStateCode = :wosPending ");
        	
            Query query = session.createQuery(stringQuery.toString());
            query.setString("wosActive", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
            query.setString("wosPending", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());

            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderStatus getAllWorkOrdersToAsign/DAOWorkorderStatusBean==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllWorkOrdersToAsign/DAOWorkorderStatusBean ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal#getDealerTrayFilterStatuss()
     */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WorkorderStatus> getDealerTrayFilterStatuss() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerTrayFilterStatuss/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
        	stringQuery.append(WorkorderStatus.class.getName());
        	stringQuery.append(" wos where ");
        	stringQuery.append("wos.woStateCode in (?,?,?) order by wos.woStateName asc");
        	
            Query query = session.createQuery(stringQuery.toString());
            /*query.setString(0, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
            query.setString(0, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());*/
            //query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            query.setString(0, CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
            query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity());

            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderStatus getDealerTrayFilterStatuss/DAOWorkorderStatusBean==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getDealerTrayFilterStatuss/DAOWorkorderStatusBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal#getWoProcessSourceByWoStatus(java.lang.String)
	 */
	@Override
	public WoProcessSource getWoProcessSourceByWoStatus(String actualStatusCode,String processSourceSource) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoProcessSourceByWoStatus/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
        	stringQuery.append(WoProcessSource.class.getName());
        	stringQuery.append(" wps where ");
        	stringQuery.append("wps.woStatusId.woStateCode=:actualStatusCode");
        	stringQuery.append(" and wps.processSourceSource=:aProcessSourceSource");
        	
            Query query = session.createQuery(stringQuery.toString());
            query.setString("actualStatusCode", actualStatusCode);
            query.setString("aProcessSourceSource", processSourceSource);
            return (WoProcessSource) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderStatus getWoProcessSourceByWoStatus/DAOWorkorderStatusBean==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWoProcessSourceByWoStatus/DAOWorkorderStatusBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal#getWoProcessSourceByProcessId(java.lang.Long)
	 */
	@Override
	public WoProcessSource getWoProcessSourceByProcessId(Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWoProcessSourceByProcessId/DAOWorkorderStatusBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
        	stringQuery.append(WoProcessSource.class.getName());
        	stringQuery.append(" wps where ");
        	stringQuery.append("wps.id=:id");
        	
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", id);
            return (WoProcessSource) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkorderStatus getWoProcessSourceByProcessId/DAOWorkorderStatusBean==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWoProcessSourceByProcessId/DAOWorkorderStatusBean ==");
        }
	}
}
