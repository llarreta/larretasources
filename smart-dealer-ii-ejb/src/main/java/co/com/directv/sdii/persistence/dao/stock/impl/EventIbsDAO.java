package co.com.directv.sdii.persistence.dao.stock.impl;

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
import co.com.directv.sdii.model.pojo.EventIbs;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.EventIbsDAOLocal;

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

@Stateless(name="EventIbsDAOLocal",mappedName="ejb/EventIbsDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EventIbsDAO extends BaseDao implements EventIbsDAOLocal {
	private static final Log log = LogFactory.getLog(EventIbsDAO.class);
	// property constants

	@Override
	public EventIbs getEventbyCode(String eventCode, Long countryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getEventbyCode/EventIbsDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(EventIbs.class.getName());
			stringQuery.append(" entity where ");
			stringQuery.append(" entity.eventCode = :eventCode ");
			stringQuery.append(" and entity.country.id = :countryId ");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("eventCode", eventCode);
			query.setLong("countryId", countryId);
			return (EventIbs) query.uniqueResult();
		} catch (Throwable ex) {
			log.debug("== Error eliminando el EventIbsDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina delete/EventIbsDAO ==");
		}
		
	}

}