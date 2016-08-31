package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.CustomerCategory;
import co.com.directv.sdii.model.pojo.CustomerClass;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerCategoryDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * Session Bean implementation class CustomerCategoryDAO
 */
@Stateless
public class CustomerCategoryDAO extends BaseDao implements CustomerCategoryDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(CustomerCategoryDAO.class);
	
    public CustomerCategoryDAO() {
    }

	@Override
	public void createCustomerCategory(CustomerCategory cc)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createCustomerCategory/CustomerCategoryDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(cc);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error createCustomerCategory ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina createCustomerCategory/CustomerCategoryDAO ==");
        }
	}

	@Override
	public void deleteCustomerCategory(CustomerCategory cc)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCustomerCategory/CustomerCategoryDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(cc);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error deleteCustomerCategory ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina deleteCustomerCategory/CustomerCategoryDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCategoryVO> getAllCustomerCategory()
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCustomerCategory/CustomerCategoryDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+CustomerCategoryVO.class.getName()+"(cc) from "+CustomerCategory.class.getName()+" cc ");
            Query query = session.createQuery(stringQuery.toString());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error getAllCustomerCategory ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getAllCustomerCategory/CustomerCategoryDAO ==");
        }
	}
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * 
     * Metodo: Obtiene categorias de clientes
     * @return lista con las categorias de cliente definidas en la propiedad CUSTOMER_CATEGORY_DEALER_CONF
     * , una lista vacia en caso que no exista ninguna categoría de cliente
     * @throws DAOServiceException , DAOSQLException
     * @author ialessan
     * @throws PropertiesException 
     */		
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCategoryVO> getCustomerCategoryForDealerConf(List<String> customerCategoryDealerConfList)
			throws DAOServiceException, DAOSQLException {
			
		log.debug("== Inicia getCustomerCategoryForDealerConf/CustomerCategoryDAO ==");
		if (!customerCategoryDealerConfList.isEmpty()) {
		        	Session session = ConnectionFactory.getSession();		
					try {
						
						if (session == null) {
		        			throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
		        		}
		        		StringBuffer stringQuery = new StringBuffer();
		        		stringQuery.append(" select new "+CustomerCategoryVO.class.getName()+"(cc) from "+CustomerCategory.class.getName()+" cc ");
		        		String stringCustomerCategoryDealerConf=" '' ";
		        		
		        		for(String customerCategoryDealerConf:customerCategoryDealerConfList){
		        			stringCustomerCategoryDealerConf+=" , '"+customerCategoryDealerConf+"'";
		        		}		
		        		
		        		/*
		        		 * consultar por codigo y no por nombre
		        		 */
		        		 stringQuery.append(" where cc.code in ("+stringCustomerCategoryDealerConf+") ");
		        		 
		        		/* tener en cuenta que los codigos difieren por pais            
		        		 */
		        		
		        		//stringQuery.append(" where upper(cc.name) in ("+stringCustomerCategoryDealerConf+") ");
		        		stringQuery.append(" order by cc.name ");
		        		Query query = session.createQuery(stringQuery.toString());		        		
		        		return query.list();
			    	}catch (Throwable ex) {
			    		log.error("== Error consultando todas las CustomerClass ==");
			    		throw this.manageException(ex);
			    	}finally {
			    		log.debug("== Termina getCustomerClassForDealerConf/CustomerClassDAO ==");
			    	}
					}else{
						log.error("== Error lista sdii_customer_category_dealer_conf es vacia ==");
						List<CustomerCategoryVO> customerCategoryVOEmptyList = new ArrayList<CustomerCategoryVO>();
						return customerCategoryVOEmptyList;
	                }	        	
    }

	@Override
	public CustomerCategoryVO getCustomerCategoryByCode(String code)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerCategoryByCode/CustomerCategoryDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+CustomerCategoryVO.class.getName()+"(cc) from "+CustomerCategory.class.getName()+" cc ");
            stringQuery.append(" where cc.code = :code ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);
            return (CustomerCategoryVO) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error getCustomerCategoryByCode ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getCustomerCategoryByCode/CustomerCategoryDAO ==");
        }
	}

	@Override
	public CustomerCategoryVO getCustomerCategoryById(Long id)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerCategoryById/CustomerCategoryDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+CustomerCategoryVO.class.getName()+"(cc) from "+CustomerCategory.class.getName()+" cc ");
            stringQuery.append(" where cc.id = :id ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", id);
            return (CustomerCategoryVO) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error getCustomerCategoryById ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getCustomerCategoryById/CustomerCategoryDAO ==");
        }
	}

	@Override
	public void updateCustomerCategory(CustomerCategory cc)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCustomerCategory/CustomerTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(cc);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error updateCustomerCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateCustomerCategory/CustomerTypeDAO ==");
        }
	}

	@Override
	public CustomerCategory getCustomerCategoryByCustomerClassCode(String customerClassCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerCategoryByCustomerClassCode/CustomerCategoryDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" FROM "+CustomerCategory.class.getName());
            stringQuery.append(" cc WHERE cc.id IN ( ");
            stringQuery.append(" 	SELECT cclass.category.id FROM "+ CustomerClass.class.getName());
            stringQuery.append(" 	cclass WHERE cclass.customerClassCode = :customerClassCode ) ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("customerClassCode", customerClassCode);
            return (CustomerCategory) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error getCustomerCategoryByCustomerClassCode ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getCustomerCategoryByCustomerClassCode/CustomerCategoryDAO ==");
        }
	}
	
}
