package co.com.directv.sdii.persistence.dao.core.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoCrewAssigments;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal;

/**
 * 
 * @author jnova
 *
 */
@Stateless(name="WoCrewAssigmentsDAOLocal",mappedName="ejb/WoCrewAssigmentsDAOLocal")
public class WoCrewAssigmentsDAO extends BaseDao implements WoCrewAssigmentsDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(WoCrewAssigmentsDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal#createWoCrewAssigments(co.com.directv.sdii.model.pojo.WoCrewAssigments)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWoCrewAssigments(WoCrewAssigments obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio createWoCrewAssigments/WoCrewAssigmentsDAO ==");
        Session session = super.getSession();
        try {
        	
        	WoCrewAssigments oldWoCrewAssigments = getLastWoCrewAssigments(obj.getWoId().getId());
        	
        	if(oldWoCrewAssigments != null){
        		oldWoCrewAssigments.setIsActive(CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity());
        		updateWoCrewAssigments(oldWoCrewAssigments);
        	}
        	
        	obj.setIsActive( CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );	 
        	session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error createWoCrewAssigments/WoCrewAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoCrewAssigments/WoCrewAssigmentsDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal#getLastWoCrewAssigments(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WoCrewAssigments getLastWoCrewAssigments(Long woId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getLastWoCrewAssigments/WoCrewAssigmentsDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoCrewAssigments.class.getName());
        	stringQuery.append(" entity where entity.woId = :anId and entity.isActive = :aRecordStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", woId);
            query.setString("aRecordStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            List response = query.list();
            if( response != null && !response.isEmpty() )
            	return (WoCrewAssigments) response.get(0);
            else
            	return null;
        } catch (Throwable ex) {
            log.error("== Error getLastWoCrewAssigments/WoCrewAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getLastWoCrewAssigments/WoCrewAssigmentsDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal#getLastWoCrewAssigments(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WoCrewAssigments> getActiveWoCrewAssigmentsByWoIds(List<WorkOrder> workOrderList) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getActiveWoCrewAssigmentById/WoCrewAssigmentsDAO ==");
        Session session = super.getSession();
        try {
        	List<Long> woIds = new ArrayList<Long>();
        	for(WorkOrder workOrder : workOrderList){
        		woIds.add(workOrder.getId());		
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoCrewAssigments.class.getName());
        	stringQuery.append(" entity where entity.woId.id in (:woIds) and entity.isActive = :aRecordStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setParameterList("woIds", woIds);
            query.setString("aRecordStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            return query.list();
            
        } catch (Throwable ex) {
            log.error("== Error getActiveWoCrewAssigmentById/WoCrewAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveWoCrewAssigmentById/WoCrewAssigmentsDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal#updateWoCrewAssigments(co.com.directv.sdii.model.pojo.WoCrewAssigments)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWoCrewAssigments(WoCrewAssigments obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateWoCrewAssigments/WoCrewAssigmentsDAO ==");
        Session session = super.getSession();
        try {	 
        	session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error updateWoCrewAssigments/WoCrewAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoCrewAssigments/WoCrewAssigmentsDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal#unassignWoCrewAssigment(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void unassignWoCrewAssigment(Long woId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio unassignWoCrewAssigment/WoCrewAssigmentsDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("update ");
        	stringQuery.append(WoCrewAssigments.class.getName());
        	stringQuery.append(" entity ");
        	stringQuery.append("set entity.isActive = :aInactiveStatus ");
        	stringQuery.append("where entity.woId = :woId and entity.isActive = :aRecordStatus ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("woId", woId);
            query.setString("aRecordStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            query.setString("aInactiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity());
        	query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error unassignWoCrewAssigment/WoCrewAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina unassignWoCrewAssigment/WoCrewAssigmentsDAO ==");
        }
		
	}

}
