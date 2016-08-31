package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.CustomerType;
import co.com.directv.sdii.model.vo.CustomerTypeVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerTypeDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * Session Bean implementation class CustomerTypeDAO
 */
@Stateless
public class CustomerTypeDAO extends BaseDao implements CustomerTypeDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(CustomerTypeDAO.class);
	
    public CustomerTypeDAO() {
    }

	@Override
	public void createCustomerType(CustomerType ct) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicio createCustomerType/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(ct);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando CustomerTypeDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina createCustomerType/CustomerTypeDAO ==");
        }
	}

	@Override
	public void deleteCustomerType(CustomerType ct) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia deleteCustomerType/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(ct);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error deleteCustomerType ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina deleteCustomerType/CustomerTypeDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerTypeVO> getAllCustomerType(Long countryId)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCustomerType/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+CustomerTypeVO.class.getName()+"(ct) from "+CustomerType.class.getName()+" ct where ct.country.id = :countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error getAllCustomerType ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getAllCustomerType/CustomerTypeDAO ==");
        }
	}

	@Override
	public CustomerTypeVO getCustomerTypeByCode(String code, Long countryId)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerTypeByCode/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+CustomerTypeVO.class.getName()+"(ct) from "+CustomerType.class.getName()+" ct where ct.country.id ");
            stringQuery.append(" = :countryId and ct.customerTypeCode = :typeCode ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setString("typeCode", code);
            return (CustomerTypeVO) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error getCustomerTypeByCode ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getCustomerTypeByCode/CustomerTypeDAO ==");
        }
	}

	@Override
	public CustomerTypeVO getCustomerTypeById(Long id)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerTypeById/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+CustomerTypeVO.class.getName()+"(ct) from "+CustomerType.class.getName()+" ct where ct.id ");
            stringQuery.append(" = :id ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", id);
            return (CustomerTypeVO) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error getCustomerTypeById ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getCustomerTypeById/CustomerTypeDAO ==");
        }
	}

	@Override
	public void updateCustomerType(CustomerType ct) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia updateCustomerType/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(ct);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error updateCustomerType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateCustomerType/CustomerTypeDAO ==");
        }
	}

	//Req-0096 - Requerimiento Consolidado Asignador
	@SuppressWarnings("unchecked")
	@Override	
	public List<CustomerClassType> getCustomerTypeByCategory(Long categoryId,Long countryId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCustomerTypeByCategory/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try{
                
            StringBuffer stringQuery = new StringBuffer();            
			stringQuery.append(" from "+CustomerClassType.class.getName()+" as cct ");
			stringQuery.append(" where cct.customerClass.category.id=:categoryId ");
				
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("categoryId", categoryId);		
			            
            return query.list();
            
        }catch (Throwable ex) {
            log.error("== Error getCustomerTypeByCategory ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getCustomerTypeByCategory/CustomerTypeDAO ==");
        }
    }
}
