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
import co.com.directv.sdii.model.pojo.CustomerClass;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.vo.CustomerClassVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerClassDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de CustomerClass
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CustomerClass
 * @see co.com.directv.sdii.persistence.dao.config.CustomerClassDAOLocal
 */

@Stateless(name="CustomerClassDAOLocal",mappedName="ejb/CustomerClassDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerClassDAO extends BaseDao implements CustomerClassDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CustomerClassDAO.class);

    /**
     * Crea un CustomerClass en el sistema
     * @param obj - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCustomerClass(CustomerClass obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCustomerClass/DAOCustomerClassBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando CustomerClass ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina createCustomerClass/CustomerClassDAO ==");
        }
    }

    /**
     * Obtiene un customerclass con el id especificado
     * @param id - Long
     * @return - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CustomerClass getCustomerClassByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerClassByID/CustomerClassDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select customerclass from CustomerClass customerclass ");
            stringQuery.append("where customerclass.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select customerclass from CustomerClass customerclass where customerclass.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (CustomerClass) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando CustomerClass por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerClassByID/CustomerClassDAO ==");
        }
    }

    /**
     * Actualiza un customerclass especificado
     * @param obj - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCustomerClass(CustomerClass obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCustomerClass/CustomerClassDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error actualizando CustomerClass ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateCustomerClass/CustomerClassDAO ==");
        }

    }

    /**
     * Elimina un customerclass del sistema
     * @param obj - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCustomerClass(CustomerClass obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCustomerClass/CustomerClassDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error eliminando CustomerClass ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina deleteCustomerClass/CustomerClassDAO ==");
        }

    }

    /**
     * Obtiene todos los customerclasss del sistema
     * @return - List<CustomerClass>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CustomerClass> getAll(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/CustomerClassDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" from "+CustomerClass.class.getName()+" cc ");
            stringQuery.append(" where cc.country.id=:countryId ");
            stringQuery.append(" order by cc.customerClassName ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando todas las CustomerClass ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getAll/CustomerClassDAO ==");
        }
    }    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.CustomerClassDAOLocal#getAllByCustomerTypeCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CustomerClass getAllByCustomerTypeCode(String customerTypeCode, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllByCustomerTypeCode/CustomerClassDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" from CustomerClass cc ");
            stringQuery.append(" where Exists( from "+CustomerClassType.class.getName()+" cct  ");
            stringQuery.append("              where cct.customerClass=cc ");
            stringQuery.append("                    and cct.customerType.customerTypeCode=:customerTypeCode and cct.customerType.country.id = :countryId )");
            stringQuery.append(" and cc.country.id = :countryId ");
            stringQuery.append(" order by cc.customerClassName ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("customerTypeCode", customerTypeCode);
            query.setLong("countryId", countryId);
            return (CustomerClass) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error getAllByCustomerTypeCode/CustomerClassDAO==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getAllByCustomerTypeCode/CustomerClassDAO ==");
        }
    }

	@Override
	public CustomerClass getCustomerClassByCode(String code, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerClassByCode/CustomerClassDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select customerclass from "+CustomerClass.class.getName()+" customerclass ");
            stringQuery.append(" where customerclass.customerClassCode = '");
            stringQuery.append(code+"' and customerclass.country.id = :countryId ");
            Query q = session.createQuery(stringQuery.toString());
            q.setLong("countryId", countryId);
            Object obj = q.uniqueResult();
            if (obj != null) {
                return (CustomerClass) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando getCustomerClassByCode por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerClassByCode/CustomerClassDAO ==");
        }
	}

	@Override
	public List<CustomerClassVO> getCustomerClassByCategoryId(Long categoryId, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerClassByCategoryId/CustomerClassDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+CustomerClassVO.class.getName()+"(cc) from "+CustomerClass.class.getName()+" cc ");
            stringQuery.append(" where cc.category.id = :customerCategoryId ");
            stringQuery.append(" and cc.country.id = :countryId ");
            Query q = session.createQuery(stringQuery.toString());
            q.setLong("countryId", countryId);
            q.setLong("customerCategoryId", categoryId);
            List<CustomerClassVO> returnValue = q.list();
            return returnValue;
        }catch (Throwable ex) {
            log.error("== Error getCustomerClassByCategoryId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerClassByCategoryId/CustomerClassDAO ==");
        }
	}
	
}
