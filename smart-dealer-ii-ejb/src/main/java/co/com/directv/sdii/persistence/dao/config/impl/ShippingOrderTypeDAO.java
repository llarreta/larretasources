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
import co.com.directv.sdii.model.pojo.ShippingOrderType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ShippingOrderType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ShippingOrderType
 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal
 */
@Stateless(name="ShippingOrderTypeDAOLocal",mappedName="ejb/ShippingOrderTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShippingOrderTypeDAO extends BaseDao implements ShippingOrderTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderTypeDAO.class);

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal#createShippingOrderType(co.com.directv.sdii.model.pojo.ShippingOrderType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createShippingOrderType(ShippingOrderType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createShippingOrderType/DAOShippingOrderTypeBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ShippingOrderType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createShippingOrderType/ShippingOrderTypeDAO ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal#getShippingOrderTypeByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderType getShippingOrderTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getShippingOrderTypeByID/ShippingOrderTypeDAO ==");
        Session session = super.getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select shippingordertype from ShippingOrderType shippingordertype ");
            queryBuffer.append("where shippingordertype.id =  ");
            queryBuffer.append(id);
            //Object obj = session.createQuery("select shippingordertype from ShippingOrderType shippingordertype where shippingordertype.id = " + id + " ").uniqueResult();
            Object obj = session.createQuery(queryBuffer.toString()).uniqueResult();
            if (obj != null) {
                return (ShippingOrderType) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ShippingOrderType por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderTypeByID/ShippingOrderTypeDAO ==");
        }
    }

  
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal#updateShippingOrderType(co.com.directv.sdii.model.pojo.ShippingOrderType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateShippingOrderType(ShippingOrderType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateShippingOrderType/ShippingOrderTypeDAO ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ShippingOrderType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateShippingOrderType/ShippingOrderTypeDAO ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal#deleteShippingOrderType(co.com.directv.sdii.model.pojo.ShippingOrderType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteShippingOrderType(ShippingOrderType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteShippingOrderType/ShippingOrderTypeDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("delete from ");
            queryBuffer.append(ShippingOrderType.class.getName());
            queryBuffer.append(" a where a.id = :anAllId");
            Query query = session.createQuery(queryBuffer.toString());
            //Query query = session.createQuery("delete from " + ShippingOrderType.class.getName() + " a where a.id = :anAllId");
            query.setLong("anAllId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ShippingOrderType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteShippingOrderType/ShippingOrderTypeDAO ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal#getAll()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderType> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/ShippingOrderTypeDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ShippingOrderType soType ");
            queryBuffer.append("order by soType.shippingOrderName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from ShippingOrderType soType " +
            		"order by soType.shippingOrderName asc");*/
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultnado los ShippingOrderType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ShippingOrderTypeDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal#getShippingOrderTypeByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderType getShippingOrderTypeByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getShippingOrderTypeByCode/ShippingOrderTypeDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(ShippingOrderType.class.getName());
            queryBuffer.append(" soType where soType.shippingOrderCode = :shippingOrderCode");
            //Query query = session.createQuery("from "+ShippingOrderType.class.getName()+" soType where soType.shippingOrderCode = :shippingOrderCode");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("shippingOrderCode", code);
            ShippingOrderType soType = null;
            if(query.list()!=null && query.list().size()>0){
                soType = (ShippingOrderType) query.list().get(0);
            }
            return soType;

        } catch (Throwable ex) {
            log.debug("== Error consultnado ShippingOrderType por código==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderTypeByCode/ShippingOrderTypeDAO ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal#getShippingOrderTypeByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderType> getShippingOrderTypeByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getShippingOrderTypeByName/ShippingOrderTypeDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(ShippingOrderType.class.getName());
            queryBuffer.append(" soType where soType.shippingOrderName = :shippingOrderName ");
            queryBuffer.append("order by soType.shippingOrderName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from "+ShippingOrderType.class.getName()+" soType where soType.shippingOrderName = :shippingOrderName " +
            		"order by soType.shippingOrderName asc");*/
            query.setString("shippingOrderName", name);
            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error consultando el ShippingOrderType por Nombre ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderTypeByName/ShippingOrderTypeDAO ==");
        }
    }

}
