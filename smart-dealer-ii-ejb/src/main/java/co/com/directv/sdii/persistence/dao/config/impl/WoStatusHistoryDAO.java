package co.com.directv.sdii.persistence.dao.config.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WoStatusHistory
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoStatusHistory
 * @see co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal
 */
@Stateless(name="WoStatusHistoryDAOLocal",mappedName="ejb/WoStatusHistoryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoStatusHistoryDAO extends BaseDao implements WoStatusHistoryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoStatusHistoryDAO.class);

    /**
     * Crea una WoStatusHistory en el sistema
     * @param obj - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWoStatusHistory(WoStatusHistory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWoStatusHistory/DAOWoStatusHistoryBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error creando WoStatusHistory ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina createWoStatusHistory/DAOWoStatusHistoryBean ==");
        }
    }

    /**
     * Obtiene un wostatushistory con el id especificado
     * @param id - Long
     * @return - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoStatusHistory getWoStatusHistoryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoStatusHistoryByID/DAOWoStatusHistoryBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select wostatushistory from ");
            stringQuery.append("WoStatusHistory wostatushistory ");
            stringQuery.append("where ");
            stringQuery.append("wostatushistory.id =  ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select wostatushistory from WoStatusHistory wostatushistory where wostatushistory.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WoStatusHistory) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WoStatusHistory por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoStatusHistoryByID/DAOWoStatusHistoryBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoStatusHistory getWorkOrdersLastDay(Long countryId) throws DAOServiceException, DAOSQLException {
        /*log.debug("== Inicio getWoStatusHistoryByID/DAOWoStatusHistoryBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select distinct from ");
            stringQuery.append("WoStatusHistory wostatushistory ");
            stringQuery.append("where ");
            stringQuery.append("wostatushistory.id =  ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select wostatushistory from WoStatusHistory wostatushistory where wostatushistory.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WoStatusHistory) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WoStatusHistory por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoStatusHistoryByID/DAOWoStatusHistoryBean ==");
        }*/
    	return null;
    }

    
    /**
     * Actualiza un wostatushistory especificado
     * @param obj - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWoStatusHistory(WoStatusHistory obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWoStatusHistory/DAOWoStatusHistoryBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WoStatusHistory ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateWoStatusHistory/DAOWoStatusHistoryBean ==");
        }

    }

    /**
     * Elimina un wostatushistory del sistema
     * @param obj - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWoStatusHistory(WoStatusHistory obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWoStatusHistory/DAOWoStatusHistoryBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando WoStatusHistory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWoStatusHistory/DAOWoStatusHistoryBean ==");
        }

    }

    /**
     * Obtiene todos los wostatushistorys del sistema
     * @return - List<WoStatusHistory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WoStatusHistory> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOWoStatusHistoryBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WoStatusHistory");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WoStatusHistory ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/DAOWoStatusHistoryBean ==");
        }
    }
    
    /**
     * 
     * Metodo: Retorna un WoStatusHistory filtrando
     * por el id de la workorder y por el estado,
     * retornando la ultima workorder reason.
     * @param woId
     * @param woStatusId
     * @return WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoStatusHistory getWorkorderReasonByWoHistory(Long woId,Long woStatusId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkorderReasonByWoHistory/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        WoStatusHistory woHistory = new   WoStatusHistory();   
        
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" FROM ");
            queryBuffer.append(WoStatusHistory.class.getName());
            queryBuffer.append(" woHistory");
            queryBuffer.append(" WHERE");
            queryBuffer.append(" woHistory.statusDate = (SELECT MAX(woHistorySub.statusDate) FROM ");            
            queryBuffer.append(WoStatusHistory.class.getName());
            queryBuffer.append(" woHistorySub where woHistorySub.workOrder.id = ? AND woHistorySub.workorderStatus.id = ? )");
            queryBuffer.append(" AND woHistory.workOrder.id = ?");
            queryBuffer.append(" AND woHistory.workorderStatus.id = ?");
                       
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong(0, woId);
            query.setLong(1, woStatusId);
            query.setLong(2, woId);
            query.setLong(3, woStatusId);

            
            Object obj =  query.uniqueResult();
            if (obj != null) {
            	woHistory = (WoStatusHistory) obj ;
            	return woHistory ;
            }
            return woHistory;            
        } catch (Throwable ex) {
            log.debug("== Error consultando WoStatusHistory por WO y WOStatus ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkorderReasonByWoHistory/DAOWorkorderReasonBean ==");
        }
    }
    /**
     * 
     * Metodo: Retorna un WoStatusHistory filtrando
     * por el id de la workorder , por el estado, y por descripcion no nula.
     * @param woId
     * @param woStatusId
     * @return WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author ialessan
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoStatusHistory getWoStatusHistoryByWoIdDescNotNull(Long woId,Long woStatusId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoStatusHistoryByWoIdDescNotNull/DAOWorkorderReasonBean ==");
        Session session = ConnectionFactory.getSession();
        WoStatusHistory woHistory = new   WoStatusHistory();   
        
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" FROM ");
            queryBuffer.append(WoStatusHistory.class.getName());
            queryBuffer.append(" woHistory");
            queryBuffer.append(" WHERE");
            queryBuffer.append(" woHistory.id = (SELECT MAX(woHistorySub.id) FROM ");                        
            queryBuffer.append(WoStatusHistory.class.getName());
            queryBuffer.append(" woHistorySub where woHistorySub.workOrder.id = ? ");
            queryBuffer.append(" AND woHistorySub.workorderStatus.id = ?  ");
            queryBuffer.append(" AND woHistorySub.description IS NOT NULL)");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong(0, woId);
            query.setLong(1, woStatusId);

            
            Object obj =  query.uniqueResult();
            if (obj != null) {
            	woHistory = (WoStatusHistory) obj ;
            	return woHistory ;
            }
            return woHistory;            
        } catch (Throwable ex) {
            log.debug("== Error consultando WoStatusHistory por WO y WOStatus ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWoStatusHistoryByWoIdDescNotNull/DAOWorkorderReasonBean ==");
        }
    }

	@Override
	public List<WoStatusHistory> getWoStatusHistoryByDealer(Long woId, Long dealerId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWoStatusHistoryByDealer/DAOWoStatusHistoryBean ==");
        Session session = ConnectionFactory.getSession();
        StringBuffer stringQuery = new StringBuffer(); 
        
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            //Se realiza un DISTINCT ya que las WO pueden tener varios registros en WO_ASSIGNMENTS
            stringQuery.append("SELECT DISTINCT wsh.ID FROM WORK_ORDER_STATUS_HISTORIES wsh ");
            stringQuery.append("INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = wsh.WO_ID ");
            stringQuery.append("WHERE woa.WO_ID = :woId AND woa.DEALER_ID = :dealerId");
            
            SQLQuery sqlQuery = session.createSQLQuery(stringQuery.toString());
            
            sqlQuery.setLong("woId", woId);
            sqlQuery.setLong("dealerId", dealerId);
            
            List<Object[]> resultObj = sqlQuery.list();
            List<WoStatusHistory> res = new ArrayList<WoStatusHistory>();
            
            for (Object object : resultObj) {
            	WoStatusHistory woStatus = new WoStatusHistory();
            	BigDecimal id = (BigDecimal) object;
            	woStatus.setId( Long.valueOf(id.longValue()) );
            	res.add( woStatus );
			}
            
            return res;
        } catch (Throwable ex) {
            log.debug("== Error en la operacion getWoStatusHistoryByDealer/DAOWoStatusHistoryBean ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWoStatusHistoryByDealer/DAOWoStatusHistoryBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal#getWoStatusHistoryByWoCodeAndHitoryKey(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<WoStatusHistory> getWoStatusHistoryByWoCodeAndHistoryKey(String woCode, String historyKey) throws DAOServiceException,DAOSQLException {
		 log.debug("== Inicio getWoStatusHistoryByWoCodeAndHitoryKey/DAOWoStatusHistoryBean ==");
	        Session session = ConnectionFactory.getSession();
	        try {
	            if (session == null) {
	                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	            }
	            StringBuffer stringQuery = new StringBuffer();
	            stringQuery.append("from ");
	            stringQuery.append(WoStatusHistory.class.getName());
	            stringQuery.append(" wostatushistory ");
	            stringQuery.append("where ");
	            stringQuery.append("wostatushistory.workOrder.woCode = :woCode ");
	            stringQuery.append("and wostatushistory.ibsHistoryCodeEvent = :historyKey ");
	            
	            Query query = session.createQuery(stringQuery.toString());
	            query.setString("woCode", woCode);
	            query.setString("historyKey", historyKey);
	            return query.list();
	        } catch (Throwable ex) {
	            log.debug("== Error getWoStatusHistoryByWoCodeAndHitoryKey/DAOWoStatusHistoryBean ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getWoStatusHistoryByWoCodeAndHitoryKey/DAOWoStatusHistoryBean ==");
	        }
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<WoStatusHistory> getWoStatusHistoryByWoIDAttendOrFinish(Long woId) throws DAOServiceException, DAOSQLException{
		 log.debug("== Inicio getWoStatusHistoryByWoCodeAndHitoryKey/DAOWoStatusHistoryBean ==");
	        Session session = ConnectionFactory.getSession();
	        try {
	            if (session == null) {
	                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	            }
	            StringBuffer stringQuery = new StringBuffer();
	            stringQuery.append("from ");
	            stringQuery.append(WoStatusHistory.class.getName());
	            stringQuery.append(" wostatushistory ");
	            stringQuery.append("where ");
	            stringQuery.append("wostatushistory.workOrder.id = :woId ");
	            stringQuery.append("and (wostatushistory.workorderStatus.woStateCode = :woStateAttend  ");
	            stringQuery.append("or wostatushistory.workorderStatus.woStateCode = :woStateFinish ) ");
	            
	            Query query = session.createQuery(stringQuery.toString());
	            query.setLong("woId", woId);
	            query.setString("woStateAttend", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
	            query.setString("woStateFinish", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
	            return query.list();
	        } catch (Throwable ex) {
	            log.debug("== Error getWoStatusHistoryByWoCodeAndHitoryKey/DAOWoStatusHistoryBean ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getWoStatusHistoryByWoCodeAndHitoryKey/DAOWoStatusHistoryBean ==");
	        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WoStatusHistory getLastWoStatusHistoryByWoIdAndWoStatus(Long woId, Long woStatusId) throws DAOServiceException, DAOSQLException{
		 log.debug("== Inicio getLastWoStatusHistoryByWoIdAndWoStatus/DAOWoStatusHistoryBean ==");
	     Session session = ConnectionFactory.getSession();
	     try {
	         if (session == null) {
	             throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	         }
	         StringBuffer stringQuery = new StringBuffer();
	         stringQuery.append(" select wosh from ");
	         stringQuery.append(WoStatusHistory.class.getName());
	         stringQuery.append(" wosh where wosh.id = ( select max(wosh2.id) from ");
	         stringQuery.append(WoStatusHistory.class.getName());
	         stringQuery.append(" wosh2 where wosh2.workOrder.id=:woId and wosh2.workorderStatus.id=:woStatusId ) ");
	         Query query = session.createQuery(stringQuery.toString());
	         query.setLong("woId", woId);
	         query.setLong("woStatusId", woStatusId);
	         WoStatusHistory returnValue=(WoStatusHistory)query.uniqueResult();
	         return returnValue;
	     } catch (Throwable ex) {
	         log.error("== Error getLastWoStatusHistoryByWoIdAndWoStatus/DAOWoStatusHistoryBean ==");
	         throw this.manageException(ex);
	     } finally {
	         log.debug("== Termina getLastWoStatusHistoryByWoIdAndWoStatus/DAOWoStatusHistoryBean ==");
	     }
	}
	
}
