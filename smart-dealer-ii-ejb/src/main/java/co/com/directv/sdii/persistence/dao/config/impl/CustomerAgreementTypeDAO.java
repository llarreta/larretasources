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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerAgreementType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerAgreementTypeDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de CustomerAgreementType
 * @author waguilera
 *
 */
@Stateless(name="CustomerAgreementTypeDAOLocal",mappedName="ejb/CustomerAgreementTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerAgreementTypeDAO extends BaseDao implements CustomerAgreementTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CustomerAgreementTypeDAO.class);

	@Override
	public void createCustomerAgreementType(CustomerAgreementType obj)
			throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio createCustomerAgreement/CustomerAgreementTypeDAO ==");
	        Session session = super.getSession();
	        try {
	            session.save(obj);
	            this.doFlush(session);
	        } catch (Throwable ex) {
	            log.error("== Error creando el CustomerAgreementType ==");
	            throw super.manageException(ex);
	        } finally {
	            log.debug("== Termina createCustomerAgreement/CustomerAgreementTypeDAO ==");
	        }
	}

	@Override
	public void deleteCustomerAgreementType(CustomerAgreementType obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteCustomerAgreement/CustomerAgreementTypeDAO ==");
        Session session = super.getSession();
        try {
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error eliminando CustomerAgreementType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteCustomerAgreement/CustomerAgreementTypeDAO ==");
        }

		
	}

	@Override
	public Customer getCustomerAgreementTypeByCode(String customerCode,
			Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getCustomerAgreementByCode/CustomerAgreementTypeDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select entity from CustomerAgreements entity ");
        	stringQuery.append("where ");
        	stringQuery.append(" entity.customerAgreementCode = :customerCode");
        	stringQuery.append(" and  entity.country.id = :countryId ");
        	Query query = session.createQuery(stringQuery.toString());
        	Object obj = query.uniqueResult();
            if (obj != null) {
                return (Customer) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando CustomerAgreementType por codigo y pais==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerAgreementByCode/CustomerAgreementTypeDAO ==");
        }
	}

	@Override
	public Customer getCustomerAgreementTypeByID(Long id)
			throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getCustomerAgreementByID/CustomerAgreementTypeDAO ==");
	        Session session = super.getSession();
	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("select entity from CustomerAgreements entity ");
	        	stringQuery.append("where ");
	        	stringQuery.append("entity.id = :id  ");
	        	Query query = session.createQuery(stringQuery.toString());
	        	query.setLong("id", id);
	        	Object obj = query.uniqueResult();
	            if (obj != null) {
	                return (Customer) obj;
	            }
	            return null;
	        }catch (Throwable ex) {
	            log.error("== Error consultando CustomerAgreementType por ID ==");
	            throw super.manageException(ex);
	        } finally {
	            log.debug("== Termina getCustomerAgreementByID/CustomerAgreementTypeDAO ==");
	        }
	}

	@Override
	public void updateCustomerAgreementType(CustomerAgreementType obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia updateCustomerAgreement/CustomerAgreementTypeDAO ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando CustomerAgreementType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateCustomerAgreement/CustomerAgreementTypeDAO ==");
        }
		
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerAgreementType> getCustomerAgreementTypes(
			String customerAgreementTypesCodes) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getCustomerAgreementTypes/CustomerAgreementTypeDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select entity from CustomerAgreementType entity ");
        	stringQuery.append("where ");
        	stringQuery.append("(" + customerAgreementTypesCodes+")" );
        	Query query = session.createQuery(stringQuery.toString());
        	return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando getCustomerAgreementTypes por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerAgreementTypes/CustomerAgreementTypeDAO ==");
        }
	}

   

}
