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
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.DocumentType;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;

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
@Stateless(name="CustomerDAOLocal",mappedName="ejb/CustomerDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerDAO extends BaseDao implements CustomerDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CustomerDAO.class);

    /**
     * Crea un Customer en el sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCustomer(Customer obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCustomer/DAOCustomerBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el Customer ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createCustomer/CustomerDAO ==");
        }
    }

    /**
     * Obtiene un customer con el id especificado
     * @param id - Long
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Customer getCustomerByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerByID/CustomerDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select customer from Customer customer ");
        	stringQuery.append("where ");
        	stringQuery.append("customer.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select customer from Customer customer where customer.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (Customer) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando Customer por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerByID/CustomerDAO ==");
        }
    }
    
    /**
     * Obtiene un customer con el customerCode especificado
     * @param code - String
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Customer getCustomerByCode(String customerCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerByID/CustomerDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select customer from Customer customer ");
        	stringQuery.append("where ");
        	stringQuery.append("customer.customerCode = '");
        	stringQuery.append(customerCode);
        	stringQuery.append("' ");
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select customer from Customer customer where customer.customerCode = '" + customerCode + "' ").uniqueResult();
            if (obj != null) {
                return (Customer) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando Customer por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerByID/CustomerDAO ==");
        }
    }

    /**
     * Actualiza un customer especificado
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCustomer(Customer obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCustomer/CustomerDAO ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando Customer ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateCustomer/CustomerDAO ==");
        }

    }

    /**
     * Elimina un customer del sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCustomer(Customer obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCustomer/CustomerDAO ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error eliminando Customer ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteCustomer/CustomerDAO ==");
        }

    }

    /**
     * Obtiene todos los customers del sistema
     * @return - List<Customer>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Customer> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/CustomerDAO ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("from Customer c order by c.firstName");
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando todos los Customer ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/CustomerDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal#getCustomerByWoCode(java.lang.String)
	 */
	@Override
	public Customer getCustomerByWoCode(String workOrderCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCustomerByWoCode/CustomerDAO ==");
		Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select c from ");
        	stringQuery.append("Customer c, ");
        	stringQuery.append("WorkOrder wo ");
        	stringQuery.append("where ");
        	stringQuery.append("c.id = wo.customer.id and wo.woCode = :aWoCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select c from Customer c, WorkOrder wo where c.id = wo.customer.id and wo.woCode = :aWoCode");
            query.setString("aWoCode", workOrderCode);
            return (Customer)query.uniqueResult();
        } catch (Throwable ex) {
            log.error("== Error consultando el Customer por WoCode ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerByWoCode/CustomerDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal#getCustomerByWoId(java.lang.Long)
	 */
	@Override
	public Customer getCustomerByWoId(Long workOrderId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCustomerByWoId/CustomerDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select c from Customer c, ");
        	stringQuery.append("WorkOrder wo where c.id = wo.customer.id and wo.id = :aWoId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select c from Customer c, WorkOrder wo where c.id = wo.customer.id and wo.id = :aWoId");
            query.setLong("aWoId", workOrderId);
            return (Customer)query.uniqueResult();
        } catch (Throwable ex) {
            log.error("== Error consultando el Customer por WoID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerByWoId/CustomerDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal#getCustomerByCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	public Customer getCustomerByCodeAndCountryId(String code, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getCustomerByCodeAndCountryId/CustomerDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Customer.class.getName());
        	stringQuery.append(" cust where cust.customerCode = :aCustCode and cust.country.id = :aCustCountryId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aCustCode", code);
        	query.setLong("aCustCountryId", countryId);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (Customer) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando Customer por código y país ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerByCodeAndCountryId/CustomerDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal#getCustomerByDocTypeIdAndDocNumber(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Customer getCustomerByDocTypeIdAndDocNumber(Long documentTypeId,
			String documentNumber, Long countryId) throws DAOServiceException,
			DAOSQLException {
		
		log.debug("== Inicio getCustomerByCodeAndCountryId/CustomerDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Customer.class.getName());
        	stringQuery.append(" cust where cust.documentTypeId = :aDocTypeId and cust.documentNumber = :aDocNumber and cust.country.id = :aCustCountryId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aDocTypeId", documentTypeId);
        	query.setString("aDocNumber", documentNumber);
        	query.setLong("aCustCountryId", countryId);
            List<Customer> result = query.list();
            if (! result.isEmpty()) {
                return (Customer) result.get(0);
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando Customer por código y país ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerByCodeAndCountryId/CustomerDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal#getCustomerByDocTypeIdDocNumberAndIbsCode(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerVO> getCustomerByDocTypeIdDocNumberAndIbsCode(Long documentTypeId,
															  		String documentNumber, 
															  		String ibsCode,
															  		Long countryId) throws DAOServiceException,
			DAOSQLException {
		
		log.debug("== Inicio getCustomerByDocTypeIdDocNumberAndIbsCode/CustomerDAO ==");
        Session session = super.getSession();
        boolean documentTypeIdSpecified= false,
                documentNumberSpecified= false,
                ibsCodeSpecified= false, 
                countryIdSpecified= false;
        
        String strWhereOrAnd= " WHERE "; 
        try {
        	
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append(" select new " + CustomerVO.class.getName() + "(cust, ");
        	stringQuery.append("                                               nvl(''||(Select min(to_char(b.code)) ");
        	stringQuery.append("                                                  from " + Building.class.getName() + " b ");
        	stringQuery.append("                                                 where b.addressCode=cust.addressCode ");
        	stringQuery.append("													   and b.addressType=cust.addressType ");
        	stringQuery.append("													   and b.address=cust.customeraddress),' '), ");
        	stringQuery.append("                                               (Select dt.documentTypeName ");
        	stringQuery.append("                                                  from " + DocumentType.class.getName() + " dt ");
        	stringQuery.append("                                                 where dt.id=cust.documentTypeId)  ) ");
        	stringQuery.append("   from " + Customer.class.getName() + " cust ");
        	
        	
        	if(documentTypeId != null && documentTypeId.longValue()>0){
        		documentTypeIdSpecified = true;
        		stringQuery.append( strWhereOrAnd + " cust.documentTypeId = :aDocTypeId ");
        		strWhereOrAnd = " AND ";
        	}
        	
        	if(documentNumber != null && documentNumber.trim().length()>0){
        		documentNumberSpecified = true;
        		stringQuery.append( strWhereOrAnd + " cust.documentNumber = :aDocNumber ");
            	strWhereOrAnd = " AND ";
        	}
        	
        	if(ibsCode != null && ibsCode.trim().length()>0){
        		ibsCodeSpecified = true;
        		stringQuery.append( strWhereOrAnd + " cust.customerCode = :acustomerCode ");
            	strWhereOrAnd = " AND ";
        	}
        	
        	if(countryId != null && countryId.longValue()>0){
        		countryIdSpecified = true;
        		stringQuery.append( strWhereOrAnd + " cust.country.id = :aCustCountryId ");
            	strWhereOrAnd = " AND ";
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	if(documentTypeIdSpecified){
        		query.setLong("aDocTypeId", documentTypeId);
        	}
        	
        	if(documentNumberSpecified){
        		query.setString("aDocNumber", documentNumber);
        	}
        	
        	if(ibsCodeSpecified){
        		query.setString("acustomerCode", ibsCode);
        	}
        	
        	if(countryIdSpecified){
        		query.setLong("aCustCountryId", countryId);
        	}
            List<CustomerVO> result = query.list();

            return result;
            
        }catch (Throwable ex) {
            log.error("== Error consultando Customer por código y país ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerByDocTypeIdDocNumberAndIbsCode/CustomerDAO ==");
        }
	}

}
