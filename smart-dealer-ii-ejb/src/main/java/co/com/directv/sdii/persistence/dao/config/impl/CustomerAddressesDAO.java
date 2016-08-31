package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerAddresses;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerAddressesDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Customer
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Customer
 * @see co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal
 */
@Stateless(name="CustomerAddressesDAOLocal",mappedName="ejb/CustomerAddressesDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerAddressesDAO extends BaseDao implements CustomerAddressesDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(CustomerAddressesDAO.class);
	
	@Override
	public void createCustomerAddresses(CustomerAddresses obj)
			throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCustomerAddresses/CustomerAddressesDAO ==");
        Session session = super.getSession();
        try {
        	Long caExist=getCustomerAddressesByCodeAndCustomerId(obj.getAddressCode(), obj.getCustomerId());
        	obj.setActive(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	if(caExist!=null){
        		obj.setId(caExist);
        		session.merge(obj);
        	}
        	else{
        		session.save(obj);
        	}
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el createCustomerAddresses ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createCustomerAddresses/CustomerAddressesDAO ==");
        }
		
	}

	@Override
	public void deleteCustomerAddresses(CustomerAddresses obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCustomerAddresses/CustomerAddressesDAO ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error deleteCustomerAddresses ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteCustomerAddresses/CustomerAddressesDAO ==");
        }
	}

	@Override
	public List<CustomerAddresses> getAll() throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getAll/CustomerAddressesDAO ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery(" select c from "+CustomerAddresses.class.getName()+" c order by c.id ");
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error getAll/CustomerAddressesDAO ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/CustomerAddressesDAO ==");
        }
	}

	@Override
	public CustomerAddresses getCustomerAddressesByID(Long id)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerAddressesByID/CustomerAddressesDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select ca from "+CustomerAddresses.class.getName()+" ca ");
        	stringQuery.append("where ");
        	stringQuery.append("ca.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (CustomerAddresses) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error getCustomerAddressesByID/CustomerAddressesDAO ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerAddressesByID/CustomerAddressesDAO ==");
        }
	}

	private Long getCustomerAddressesByCodeAndCustomerId(String code, Long customerID)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerAddressesByID/CustomerAddressesDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select ca.id from "+CustomerAddresses.class.getName()+" ca ");
        	stringQuery.append("where ");
        	stringQuery.append("ca.addressCode = '");
        	stringQuery.append(code+"' ");
        	stringQuery.append("and ca.customerId = :customerId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setParameter("customerId", customerID, Hibernate.LONG);
        	List<Object> obj = query.list();
            if (obj != null && obj.size()>=1) {
                return (Long) obj.get(0);
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error getCustomerAddressesByID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerAddressesByID/CustomerAddressesDAO ==");
        }
	}
	
	@Override
	public void updateCustomerAddresses(CustomerAddresses obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCustomerAddresses/CustomerAddressesDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error updateCustomerAddresses ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateCustomerAddresses/CustomerAddressesDAO ==");
        }
	}

	@Override
	public void deleteCustomerAddressesByCustomerId(Long customerId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteCustomerAddressesByCustomerId/CustomerAddressesDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" UPDATE CUSTOMER_ADDRESSES SET IS_ACTIVE = '"+CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()+"' WHERE CUSTOMER_ID = :customerId");
            Query query = session.createSQLQuery(stringQuery.toString());
            query.setParameter("customerId", customerId, Hibernate.LONG);
            query.executeUpdate();
        }catch (Throwable ex) {
            log.error("== Error borrando el deleteCustomerAddressesByCustomerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteCustomerAddressesByCustomerId/CustomerAddressesDAO ==");
        }
	}

}
