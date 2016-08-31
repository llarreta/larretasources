package co.com.directv.sdii.persistence.dao.core.impl;

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
import co.com.directv.sdii.model.pojo.WoProgramAssigments;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.WoProgramAssigmentsDAOLocal;


@Stateless(name="WoProgramAssigmentsDAOLocal",mappedName="ejb/WoProgramAssigmentsDAOLocal")
public class WoProgramAssigmentsDAO extends BaseDao implements WoProgramAssigmentsDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(WoProgramAssigmentsDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoProgramAssigmentsDAOLocal#createWoProgramAssigments(co.com.directv.sdii.model.pojo.WoProgramAssigments)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWoProgramAssigments(WoProgramAssigments obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio createWoProgramAssigments/WoProgramAssigmentsDAO ==");
        Session session = super.getSession();
        try {
        	
        	WoProgramAssigments oldWoProgramAssigments = getLastWoProgramAssigments(obj.getWoId().getId());
        	
        	if(oldWoProgramAssigments != null){
        		oldWoProgramAssigments.setIsActive(CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity());
        		updateWoProgramAssigments(oldWoProgramAssigments);
        	}
        	
        	obj.setIsActive( CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );	 
        	session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error createWoProgramAssigments/WoProgramAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoProgramAssigments/WoProgramAssigmentsDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoProgramAssigmentsDAOLocal#updateWoProgramAssigments(co.com.directv.sdii.model.pojo.WoProgramAssigments)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWoProgramAssigments(WoProgramAssigments obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateWoProgramAssigments/WoProgramAssigmentsDAO ==");
        Session session = super.getSession();
        try {	 
        	session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error updateWoProgramAssigments/WoProgramAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoProgramAssigments/WoProgramAssigmentsDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoProgramAssigmentsDAOLocal#getLastWoProgramAssigments(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WoProgramAssigments getLastWoProgramAssigments(Long woId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getLastWoProgramAssigments/WoProgramAssigmentsDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoProgramAssigments.class.getName());
        	stringQuery.append(" entity where entity.woId = :anId and entity.isActive = :aRecordStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", woId);
            query.setString("aRecordStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());

            return (WoProgramAssigments) query.uniqueResult();
        } catch (Throwable ex) {
            log.error("== Error getLastWoProgramAssigments/WoProgramAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getLastWoProgramAssigments/WoProgramAssigmentsDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoProgramAssigmentsDAOLocal#unassignWoCrewAssigment(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void unassignWoProgramAssigment(Long woId) throws DAOServiceException,DAOSQLException {		
		log.debug("== Inicio unassignWoProgramAssigment/WoProgramAssigmentsDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("update ");
        	stringQuery.append(WoProgramAssigments.class.getName());
        	stringQuery.append(" entity ");
        	stringQuery.append("set entity.isActive = :aInactiveRecordStatus ");
        	stringQuery.append("  where entity.woId = :woId and entity.isActive = :aRecordStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aRecordStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            query.setString("aInactiveRecordStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity());
            query.setLong("woId", woId);
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error unassignWoProgramAssigment/WoProgramAssigmentsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina unassignWoProgramAssigment/WoProgramAssigmentsDAO ==");
        }
	}

}
