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
import co.com.directv.sdii.model.pojo.AddressType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.AddressTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de AllianceCompany
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AllianceCompany
 * @see co.com.directv.sdii.persistence.dao.config.AllianceCompanyDAOLocal
 */
@Stateless(name="AddressTypeDAOLocal",mappedName="ejb/AddressTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AddressTypeDAO extends BaseDao implements AddressTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AddressTypeDAO.class);

	@Override
	public void createAddressType(AddressType obj) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicio createAddressType/AddressTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error createAddressType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createAddressType/AddressTypeDAO ==");
        }
	}

	@Override
	public void deleteAddressType(AddressType obj) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia deleteAddressType/AddressTypeDAO ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error deleteAddressType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteAddressType/AddressTypeDAO ==");
        }
	}

	@Override
	public AddressType getAddressTypeByID(Long id) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicio getCustomerAddressesByID/AddressTypeDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select at from "+AddressType.class.getName()+" at ");
        	stringQuery.append(" where ");
        	stringQuery.append(" at.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (AddressType) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error getCustomerAddressesByID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerAddressesByID/AddressTypeDAO ==");
        }
	}

	@Override
	public AddressType getAddressTypeByCode(String code) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicio getCustomerAddressesByID/AddressTypeDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select at from "+AddressType.class.getName()+" at ");
        	stringQuery.append(" where ");
        	stringQuery.append(" at.code = '");
        	stringQuery.append(code);
        	stringQuery.append("' ");
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (AddressType) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error getCustomerAddressesByID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerAddressesByID/AddressTypeDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressType> getAll() throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getAll/AddressTypeDAO ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery(" select at from "+AddressType.class.getName()+" at order by at.id ");
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error getAll ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/AddressTypeDAO ==");
        }
	}

	@Override
	public void updateAddressType(AddressType obj) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia updateAddressType/AddressTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error updateAddressType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateAddressType/AddressTypeDAO ==");
        }
	}
    
    
}
