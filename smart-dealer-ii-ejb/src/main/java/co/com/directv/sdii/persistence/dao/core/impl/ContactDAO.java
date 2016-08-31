package co.com.directv.sdii.persistence.dao.core.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Contact;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.ContactDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Contact
 * 
 * Fecha de Creación:Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Contact
 * @see co.com.directv.sdii.model.hbm.Contact.hbm.xml
 */
@Stateless(name="ContactDAOLocal",mappedName="ejb/ContactDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactDAO extends BaseDao implements ContactDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ContactDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactDAOLocal#createContact(co.com.directv.sdii.model.pojo.Contact)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createContact(Contact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio la operacion createContact/ContactDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error en la operacion createContact/ContactDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion createContact/ContactDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactDAOLocal#updateContact(co.com.directv.sdii.model.pojo.Contact)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContact(Contact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio la operacion updateContact/ContactDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error en la operacion updateContact/ContactDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion updateContact/ContactDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactDAOLocal#deleteContact(co.com.directv.sdii.model.pojo.Contact)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteContact(Contact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio la operacion deleteContact/ContactDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Contact entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error en la operacion deleteContact/ContactDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion deleteContact/ContactDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.ContactDAOLocal#getContactsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Contact getContactByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContactByID/ContactDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Contact.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Contact) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error en la operacion getContactByID/ContactDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina la operacion getContactByID/ContactDAO ==");
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> getContactsByWorkOrder(String woCode) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio la operacion getContactsByWorkOrder/ContactDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Contact.class.getName());
        	stringQuery.append(" contact where contact.woCode = :woCode");
        	stringQuery.append(" and contact.category is null");
        	stringQuery.append(" order by contact.creationDate desc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("woCode", woCode);

            return query.list();
        } catch (Throwable ex){
			log.error("== Error en la operacion getContactsByWorkOrder/ContactDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina la operacion getContactsByWorkOrder/ContactDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> getContactsTriesByWorkOrder(String woCode) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio la operacion getContactsTriesByWorkOrder/ContactDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Contact.class.getName());
        	stringQuery.append(" contact where contact.woCode = :woCode");
        	stringQuery.append(" and contact.category is not null");
        	stringQuery.append(" order by contact.creationDate desc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("woCode", woCode);

            return query.list();
        } catch (Throwable ex){
			log.error("== Error en la operacion getContactsTriesByWorkOrder/ContactDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina la operacion getContactsTriesByWorkOrder/ContactDAO ==");
        }
	}
	
	@Override
	public Contact getContactsByWoStatusHistory(Long woStatusHistoryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio la operacion getContactsByWoStatusHistory/ContactDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Contact.class.getName());
        	stringQuery.append(" contact where contact.woStatusHistoryId.id = :woStatus");
        	stringQuery.append(" order by contact.creationDate desc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("woStatus", woStatusHistoryId);

            return (Contact) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error en la operacion getContactsByWoStatusHistory/ContactDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina la operacion getContactsByWoStatusHistory/ContactDAO ==");
        }
	}

}
