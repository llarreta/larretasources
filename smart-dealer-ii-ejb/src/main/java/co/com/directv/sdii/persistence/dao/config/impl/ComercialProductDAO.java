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
import co.com.directv.sdii.model.pojo.ComercialProduct;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ComercialProductDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ComercialProduct
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ComercialProduct
 * @see co.com.directv.sdii.persistence.dao.config.ComercialProductDAOLocal
 */
@SuppressWarnings("unused")
@Stateless(name="ComercialProductDAOLocal",mappedName="ejb/ComercialProductDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ComercialProductDAO extends BaseDao implements ComercialProductDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ComercialProductDAO.class);

    /**
     * Crea una compañia en el sistema
     * @param obj - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createComercialProduct(ComercialProduct obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createComercialProduct/DAOComercialProductBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ComercialProduct ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createComercialProduct/ComercialProductDAO ==");
        }
    }

    /**
     * Obtiene un comercialproduct con el id especificado
     * @param id - Long
     * @return - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ComercialProduct getComercialProductByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getComercialProductByID/ComercialProductDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select comercialproduct from ComercialProduct comercialproduct ");
            stringQuery.append("where ");
            stringQuery.append("comercialproduct.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (ComercialProduct) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando ComercialProduct por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getComercialProductByID/ComercialProductDAO ==");
        }
    }

    /**
     * Actualiza un comercialproduct especificado
     * @param obj - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateComercialProduct(ComercialProduct obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateComercialProduct/ComercialProductDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando ComercialProduct ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateComercialProduct/ComercialProductDAO ==");
        }

    }

    /**
     * Elimina un comercialproduct del sistema
     * @param obj - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteComercialProduct(ComercialProduct obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteComercialProduct/ComercialProductDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando ComercialProduct ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina deleteComercialProduct/ComercialProductDAO ==");
        }

    }

    /**
     * Obtiene todos los comercialproducts del sistema
     * @return - List<ComercialProduct>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ComercialProduct> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/ComercialProductDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ComercialProduct cp ");
            stringQuery.append("order by cp.productName");
            Query query = session.createQuery(stringQuery.toString());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando todos los ComercialProduct ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ComercialProductDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ComercialProductDAOLocal#getComercialProductByCode(java.lang.String)
     */
    public ComercialProduct getComercialProductByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllianceCompanyByCode/AllianceDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(ComercialProduct.class.getName());
            stringQuery.append(" comercialProduct where comercialProduct.productCode = :productCode");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("productCode", code);
            ComercialProduct comercialProduct = null;
            if(query.list()!=null && query.list().size()>0){
                comercialProduct = (ComercialProduct) query.list().get(0);
            }
            return comercialProduct;

        }catch (Throwable ex) {
            log.error("== Error consultando los ComercialProduct por Código ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceCompanyByCode/ComercialProductDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ComercialProductDAOLocal#getAllComercialProductsByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ComercialProduct> getAllComercialProductsByCountryId(
			Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllComercialProductsByCountryId/ComercialProductDAO ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ComercialProduct cp ");
            stringQuery.append("where ");
            stringQuery.append("cp.country.id = :aCountryId order by cp.productName");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            List<ComercialProduct> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.error("== Error consultando los ComercialProduct por ID de país ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getAllComercialProductsByCountryId/ComercialProductDAO ==");
        }
	}

}
