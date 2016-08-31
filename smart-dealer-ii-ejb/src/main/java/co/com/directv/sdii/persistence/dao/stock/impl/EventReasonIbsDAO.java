package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EventReasonIbs;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.EventReasonIbsDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * MovCmdQueue entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see co.com.directv.sdii.persistence.MovCmdQueue
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="EventReasonIbsDAOLocal",mappedName="ejb/EventReasonIbsDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EventReasonIbsDAO extends BaseDao implements EventReasonIbsDAOLocal {
	private static final Log log = LogFactory.getLog(EventReasonIbsDAO.class);

	@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(EventReasonIbs persistentInstance)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio delete/EventReasonIbsDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
			.createQuery("delete from EventReasonIbs entity where entity.id = :anEntityId");
			query.setLong("anEntityId", persistentInstance.getId());
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error eliminando el EventReasonIbs ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina delete/EventReasonIbsDAO ==");
		}
	}

	@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EventReasonIbs findById(Long id) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio findById/EventReasonIbsDAO ==");
		try {
			EventReasonIbs instance = (EventReasonIbs) getSession().get(
					"co.com.directv.sdii.model.pojo.EventReasonIbs", id);
			return instance;
		} catch (Throwable re) {
			log.error("== Error findById/EventReasonIbsDAO ==");
			throw this.manageException(re);
		} finally {
			log.debug("== Termina findById/EventReasonIbsDAO ==");
		}
	}

	@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(EventReasonIbs transientInstance)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio save/EventReasonIbsDAO ==");
		try {
			Session session = this.getSession();
			session.save(transientInstance);
			this.doFlush(session);
		} catch (Throwable re) {
			log.error("== Error save/EventReasonIbsDAO ==");
			throw this.manageException(re);
		} finally {
			log.debug("== Termina save/EventReasonIbsDAO ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateEventReasonIbs(EventReasonIbs obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateEventReasonIbs/EventReasonIbsDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el updateEventReasonIbs/EventReasonIbsDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEventReasonIbs/EventReasonIbsDAO ==");
        }
	}
	
	@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EventReasonIbs> getConfigEventReasonIbsForEvent(Long eventId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getConfigEventReasonIbs/EventReasonIbsDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(EventReasonIbs.class.getName());
			stringQuery.append(" entity where ");
			stringQuery.append(" entity.eventIbs.id = :eventId ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("eventId", eventId);
			return query.list();
		} catch (Throwable ex) {
			log.debug("== Error actualizando el getConfigEventReasonIbs/EventReasonIbsDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getConfigEventReasonIbs/EventReasonIbsDAO ==");
		}
	}

}