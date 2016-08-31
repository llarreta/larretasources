package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerTitle;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerTitleDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de CustomerTitle
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CustomerTitle
 * @see co.com.directv.sdii.persistence.dao.config.CustomerTitleDAOLocal
 */
@Stateless(name="CustomerTitleDAOLocal",mappedName="ejb/CustomerTitleDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerTitleDAO extends BaseDao implements CustomerTitleDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CustomerTitleDAO.class);

    /**
     * Crea un CustomerTitle en el sistema
     * @param obj - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCustomerTitle(CustomerTitle obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCustomerTitle/DAOCustomerTitleBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (HibernateException ex) {
            log.error("== Error creando el CustomerTitle ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createCustomerTitle/DAOCustomerTitleBean ==");
        }
    }

    /**
     * Obtiene un customertitle con el id especificado
     * @param id - Long
     * @return - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CustomerTitle getCustomerTitleByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerTitleByID/DAOCustomerTitleBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select customertitle from ");
        	stringQuery.append("CustomerTitle customertitle where customertitle.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select customertitle from CustomerTitle customertitle where customertitle.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (CustomerTitle) obj;
            }
            return null;
        } catch (HibernateException ex) {
            log.error("== Error consultando el CustomerTitle por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerTitleByID/DAOCustomerTitleBean ==");
        }
    }

    /**
     * Actualiza un customertitle especificado
     * @param obj - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCustomerTitle(CustomerTitle obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCustomerTitle/DAOCustomerTitleBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (HibernateException ex) {
            log.error("== Error actualizando el CustomerTitle ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateCustomerTitle/DAOCustomerTitleBean ==");
        }

    }

    /**
     * Elimina un customertitle del sistema
     * @param obj - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCustomerTitle(CustomerTitle obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCustomerTitle/DAOCustomerTitleBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (HibernateException ex) {
            log.error("== Error borrando el CustomerTitle ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteCustomerTitle/DAOCustomerTitleBean ==");
        }

    }

    /**
     * Obtiene todos los customertitles del sistema
     * @return - List<CustomerTitle>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CustomerTitle> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOCustomerTitleBean ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from CustomerTitle ct ");
        	stringQuery.append("order by ct.titleName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from CustomerTitle ct order by ct.titleName");
            return query.list();
        } catch (HibernateException ex) {
            log.error("== Error borrando el CustomerTitle ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOCustomerTitleBean ==");
        }
    }
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.CustomerTitleDAOLocal#getCustomerTitleByCode(java.lang.String)
	 */
	public CustomerTitle getCustomerTitleByCode(String titleCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCustomerTitleByCode/DAOCustomerTitleBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from CustomerTitle ct where ct.titleCode = :aCtTitleCode order by ct.titleName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCtTitleCode", titleCode);
            return (CustomerTitle)query.uniqueResult();
        } catch (HibernateException ex) {
            log.error("== Error consultando el CustomerTitle por código ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getCustomerTitleByCode/DAOCustomerTitleBean ==");
        }
	}

}
