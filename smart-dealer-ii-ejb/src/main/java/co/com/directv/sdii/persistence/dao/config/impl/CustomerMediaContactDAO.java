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
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerMediaContact;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de CustomerMediaContact
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CustomerMediaContact
 * @see co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal
 */
@Stateless(name="CustomerMediaContactDAOLocal",mappedName="ejb/CustomerMediaContactDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerMediaContactDAO extends BaseDao implements CustomerMediaContactDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CustomerMediaContactDAO.class);

    /**
     * Crea una cCustomerMediaContact en el sistema
     * @param obj - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCustomerMediaContact(CustomerMediaContact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCustomerMediaContact/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando el CustomerMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createCustomerMediaContact/DAOCustomerMediaContactBean ==");
        }
    }

    /**
     * Obtiene un customermediacontact con el id especificado
     * @param id - Long
     * @return - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CustomerMediaContact getCustomerMediaContactByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerMediaContactByID/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select customermediacontact from ");
            stringQuery.append("CustomerMediaContact customermediacontact ");
            stringQuery.append("where ");
            stringQuery.append("customermediacontact.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select customermediacontact from CustomerMediaContact customermediacontact where customermediacontact.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (CustomerMediaContact) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando el CustomerMediaContact por id ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCustomerMediaContactByID/DAOCustomerMediaContactBean ==");
        }
    }

    /**
     * Actualiza un customermediacontact especificado
     * @param obj - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCustomerMediaContact(CustomerMediaContact obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCustomerMediaContact/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error actualizando el CustomerMediaContact ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateCustomerMediaContact/DAOCustomerMediaContactBean ==");
        }

    }

    /**
     * Elimina un customermediacontact del sistema
     * @param obj - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCustomerMediaContact(CustomerMediaContact obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCustomerMediaContact/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error borrando el CustomerMediaContact ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteCustomerMediaContact/DAOCustomerMediaContactBean ==");
        }

    }

    /**
     * Obtiene todos los customermediacontacts del sistema
     * @return - List<CustomerMediaContact>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CustomerMediaContact> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from CustomerMediaContact cmc ");
            stringQuery.append("order by cmc.contactTypeId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from CustomerMediaContact cmc order by cmc.contactTypeId");
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultado todos los CustomerMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOCustomerMediaContactBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal#deleteCustomerMediaContactByCustomerId(java.lang.Long)
	 */
	@Override
	public void deleteCustomerMediaContactByCustomerId(Long customerId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteCustomerMediaContactByCustomerId/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("delete from CustomerMediaContact cmc ");
            stringQuery.append("where ");
            stringQuery.append("cmc.customerId = :aCustomerId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from CustomerMediaContact cmc where cmc.customerId = :aCustomerId");
            query.setLong("aCustomerId", customerId);
            query.executeUpdate();
        }catch (Throwable ex) {
            log.error("== Error borrando el deleteCustomerMediaContactByCustomerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteCustomerMediaContactByCustomerId/DAOCustomerMediaContactBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal#getCustomerMediaContactByCustomerId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CustomerMediaContact> getCustomerMediaContactByCustomerId(Long customerId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCustomerMediaContactByCustomerId/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerMediaContact.class.getName());
            stringQuery.append(" cmc ");
            stringQuery.append("where cmc.customerId = :customerId");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("customerId", customerId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error getCustomerMediaContactByCustomerId/DAOCustomerMediaContactBean ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerMediaContactByCustomerId/DAOCustomerMediaContactBean ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CustomerMediaContact> getCustomerMediaContactByCustomerCode(String customerCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCustomerMediaContactByCustomerCode/DAOCustomerMediaContactBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerMediaContact.class.getName());
            stringQuery.append(" cmc ");
            stringQuery.append("where cmc.customerId = ");
            stringQuery.append("( select customer.id from ");
            stringQuery.append(Customer.class.getName());
            stringQuery.append(" customer ");
        	stringQuery.append("where ");
        	stringQuery.append("customer.customerCode = :customerCode )");
            
            Query query = session.createQuery(stringQuery.toString());
            query.setString("customerCode", customerCode);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error getCustomerMediaContactByCustomerCode/DAOCustomerMediaContactBean ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerMediaContactByCustomerCode/DAOCustomerMediaContactBean ==");
        }
	}

}
