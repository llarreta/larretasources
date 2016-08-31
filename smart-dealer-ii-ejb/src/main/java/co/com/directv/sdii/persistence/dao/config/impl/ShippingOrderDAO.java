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
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ShippingOrder
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ShippingOrder
 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal
 */
@Stateless(name="ShippingOrderDAOLocal",mappedName="ejb/ShippingOrderDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShippingOrderDAO extends BaseDao implements ShippingOrderDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderDAO.class);

    /**
     * Crea una ShippingOrder en el sistema
     * @param obj - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createShippingOrder(ShippingOrder obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createShippingOrder/DAOShippingOrderBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ShippingOrder ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createShippingOrder/DAOShippingOrderBean ==");
        }
    }

    /**
     * Obtiene un shippingorder con el id especificado
     * @param id - Long
     * @return - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrder getShippingOrderByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getShippingOrderByID/DAOShippingOrderBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select shippingorder from ");
        	stringQuery.append("ShippingOrder shippingorder ");
        	stringQuery.append("where ");
        	stringQuery.append("shippingorder.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select shippingorder from ShippingOrder shippingorder where shippingorder.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ShippingOrder) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ShippingOrder por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getShippingOrderByID/DAOShippingOrderBean ==");
        }
    }

    /**
     * Actualiza un shippingorder especificado
     * @param obj - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateShippingOrder(ShippingOrder obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateShippingOrder/DAOShippingOrderBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ShippingOrder ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateShippingOrder/DAOShippingOrderBean ==");
        }

    }

    /**
     * Elimina un shippingorder del sistema
     * @param obj - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteShippingOrder(ShippingOrder obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteShippingOrder/DAOShippingOrderBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ShippingOrder ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteShippingOrder/DAOShippingOrderBean ==");
        }

    }

    /**
     * Obtiene todos los shippingorders del sistema
     * @return - List<ShippingOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrder> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOShippingOrderBean ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("from ShippingOrder");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ShippingOrder ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOShippingOrderBean ==");
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<ShippingOrder> getShippingOrdersByWorkOrder(Long woId)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getShippingOrdersByWorkOrder/DAOShippingOrderBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ShippingOrder.class.getName());
        	stringQuery.append(" shippingorder where shippingorder.workOrderId = ");
        	stringQuery.append(woId);
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+ShippingOrder.class.getName()+" shippingorder where shippingorder.workOrder.id = " + woId + " ");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error listando ShippingOrdersByWorkOrder ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getShippingOrdersByWorkOrder/DAOShippingOrderBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal#getShippingOrderByCodeAndWOCodeAndCountry(java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrder getShippingOrderByCodeAndWOCodeAndCountry(String shipOrderID, String woCode, String countryCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getShippingOrderByCodeAndWOCodeAndCountry/DAOShippingOrderBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ShippingOrder.class.getName());
        	stringQuery.append(" shippingorder where shippingorder.shippingOrderCode = :id ");
        	stringQuery.append(" and exists (select 1 from ");
        	stringQuery.append(WorkOrder.class.getName());
        	stringQuery.append(" workorder where workorder.id = shippingorder.workOrderId ");
        	stringQuery.append(" and workorder.country.countryCode = :country  ");
        	stringQuery.append(" and workorder.woCode = :woCode ) ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("id", shipOrderID);
        	query.setString("country", countryCode);
        	query.setString("woCode", woCode);
        	Object obj = query.uniqueResult();
            if (obj != null) {
                return (ShippingOrder) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ShippingOrder por Codigo, work order y pais ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getShippingOrderByCodeAndWOCodeAndCountry/DAOShippingOrderBean ==");
        }
    }
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal#getShippingOrderByWOCodeAndCountry(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrder> getShippingOrderByWOCodeAndCountry(String woCode, String countryCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getShippingOrderByWOCodeAndCountry/DAOShippingOrderBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ShippingOrder.class.getName());
        	stringQuery.append(" shippingorder where exists (select 1 from ");
        	stringQuery.append(WorkOrder.class.getName());
        	stringQuery.append(" workorder where workorder.id = shippingorder.workOrderId ");
        	stringQuery.append(" and workorder.country.countryCode = :country ");
        	stringQuery.append(" and workorder.woCode = :woCode ) ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("woCode", woCode);
        	query.setString("country", countryCode);
        	Object obj = query.list();
            if (obj != null) {
                return (List<ShippingOrder>) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ShippingOrder por work order y pais ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getShippingOrderByWOCodeAndCountry/DAOShippingOrderBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal#deleteShippingOrderByWoId(java.lang.Long)
	 */
	@Override
	public void deleteShippingOrderByWoId(Long workOrderId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteShippingOrderByWoId/DAOShippingOrderBean ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ShippingOrder so where so.workOrderId = :aWorkOrderId");
            query.setLong("aWorkOrderId", workOrderId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ShippingOrder ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteShippingOrderByWoId/DAOShippingOrderBean ==");
        }
	}

}
