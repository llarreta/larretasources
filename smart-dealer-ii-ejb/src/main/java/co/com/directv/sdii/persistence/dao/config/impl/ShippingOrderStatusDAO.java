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
import co.com.directv.sdii.model.pojo.ShippingOrderStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ShippingOrderStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ShippingOrderStatus
 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal
 */
@Stateless(name="ShippingOrderStatusDAOLocal",mappedName="ejb/ShippingOrderStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShippingOrderStatusDAO extends BaseDao implements ShippingOrderStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderStatusDAO.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal#createShippingOrderStatus(co.com.directv.sdii.model.pojo.ShippingOrderStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createShippingOrderStatus(ShippingOrderStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createShippingOrderStatus/ShippingOrderStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ShippingOrderStatus ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createShippingOrderStatus/ShippingOrderStatusDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal#getShippingOrderStatusByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderStatus getShippingOrderStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getShippingOrderStatusByID/ShippingOrderStatusDAO ==");
        Session session = super.getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select shippingorderstatus from ShippingOrderStatus shippingorderstatus ");
            queryBuffer.append("where shippingorderstatus.id = ");
            queryBuffer.append(id);
            //Object obj = session.createQuery("select shippingorderstatus from ShippingOrderStatus shippingorderstatus where shippingorderstatus.id = " + id + " ").uniqueResult();
            Object obj = session.createQuery(queryBuffer.toString()).uniqueResult();
            if (obj != null) {
                return (ShippingOrderStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ShippingOrderStatus por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderStatusByID/ShippingOrderStatusDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal#updateShippingOrderStatus(co.com.directv.sdii.model.pojo.ShippingOrderStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateShippingOrderStatus(ShippingOrderStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateShippingOrderStatus/ShippingOrderStatusDAO ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ShippingOrderStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateShippingOrderStatus/ShippingOrderStatusDAO ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal#deleteShippingOrderStatus(co.com.directv.sdii.model.pojo.ShippingOrderStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteShippingOrderStatus(ShippingOrderStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteShippingOrderStatus/ShippingOrderStatusDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("delete from ");
            queryBuffer.append(ShippingOrderStatus.class.getName());
            queryBuffer.append(" a where a.id = :anAllId");
            Query query = session.createQuery(queryBuffer.toString());
            //Query query = session.createQuery("delete from " + ShippingOrderStatus.class.getName() + " a where a.id = :anAllId");
            query.setLong("anAllId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ShippingOrderStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteShippingOrderStatus/ShippingOrderStatusDAO ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal#getAll()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderStatus> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/ShippingOrderStatusDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ShippingOrderStatus soStatus ");
            queryBuffer.append("order by soStatus.soStatusName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from ShippingOrderStatus soStatus " +
            		"order by soStatus.soStatusName asc");*/
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando los ShippingOrderStatus ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ShippingOrderStatusDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal#getShippingOrderStatusByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderStatus getShippingOrderStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getShippingOrderStatusByCode/ShippingOrderStatusDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(ShippingOrderStatus.class.getName());
            queryBuffer.append(" soStatus where soStatus.soStatusCode = :soStatusCode");
            //Query query = session.createQuery("from "+ShippingOrderStatus.class.getName()+" soStatus where soStatus.soStatusCode = :soStatusCode");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("soStatusCode", code);
            ShippingOrderStatus soStatus = null;
            if(query.list()!=null && query.list().size()>0){
                soStatus = (ShippingOrderStatus) query.list().get(0);
            }
            return soStatus;

        } catch (Throwable ex) {
            log.debug("== Error consultando los ShippingOrderStatus por Código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderStatusByCode/ShippingOrderStatusDAO ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal#getShippingOrderStatusByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderStatus> getShippingOrderStatusByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getShippingOrderStatusByName/ShippingOrderStatusDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(ShippingOrderStatus.class.getName());
            queryBuffer.append(" soStatus where soStatus.soStatusName = :soStatusName ");
            queryBuffer.append("order by soStatus.soStatusName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from "+ShippingOrderStatus.class.getName()+" soStatus where soStatus.soStatusName = :soStatusName " +
            		"order by soStatus.soStatusName asc");*/
            query.setString("soStatusName", name);
            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error consultando el Dealer por Nombre ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderStatusByName/ShippingOrderStatusDAO ==");
        }
    }

}
