package co.com.directv.sdii.persistence.dao.core.impl;

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
import co.com.directv.sdii.model.pojo.ShippingOrderDetail;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ShippingOrderDetail
 * 
 * Fecha de Creación: 1/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="ShippingOrderDetailDAOLocal",mappedName="ejb/ShippingOrderDetailDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShippingOrderDetailDAO extends BaseDao implements ShippingOrderDetailDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderDetailDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal#createShippingOrderDetail(co.com.directv.sdii.model.pojo.ShippingOrderDetail)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void createShippingOrderDetail(ShippingOrderDetail detail)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio la operacion createShippingOrderDetail/ShippingOrderDetailDAO ==");
        Session session = super.getSession();
        try {
            session.save(detail);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error en la operacion createShippingOrderDetail/ShippingOrderDetailDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion createShippingOrderDetail/ShippingOrderDetailDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal#getShippingOrderDetailsByShippingOrderCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<ShippingOrderDetail> getShippingOrderDetailsByShippingOrderCode(String shippingOrderCode) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio la operacion getShippingOrderDetailsByShippingOrderCode/ShippingOrderDetailDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(ShippingOrderDetail.class.getName());
			stringQuery.append(" detail where detail.shippingOrder.shippingOrderCode = :shippingOrderCode ");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("shippingOrderCode", shippingOrderCode);

			return query.list();
		} catch (Throwable ex) {
			log.error("== Error en la operacion getShippingOrderDetailsByShippingOrderCode/ShippingOrderDetailDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina la operacion getShippingOrderDetailsByShippingOrderCode/ShippingOrderDetailDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal#getShippingOrderDetailsByShippingOrderId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<ShippingOrderDetail> getShippingOrderDetailsByShippingOrderId(Long shippingOrderId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio la operacion getShippingOrderDetailsByShippingOrderId/ShippingOrderDetailDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(ShippingOrderDetail.class.getName());
			stringQuery.append(" detail where detail.shippingOrder.id = :shippingOrderId ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("shippingOrderId", shippingOrderId);

			return query.list();
		} catch (Throwable ex) {
			log.error("== Error en la operacion getShippingOrderDetailsByShippingOrderId/ShippingOrderDetailDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina la operacion getShippingOrderDetailsByShippingOrderId/ShippingOrderDetailDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal#updateShippingOrderDetail(co.com.directv.sdii.model.pojo.ShippingOrderDetail)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void updateShippingOrderDetail(ShippingOrderDetail detail)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio la operacion updateShippingOrderDetail/ShippingOrderDetailDAO ==");
		Session session = super.getSession();
		try {
			session.merge(detail);
			this.doFlush(session);
		} catch (Throwable ex) {
			log.error("== Error en la operacion updateShippingOrderDetail/ShippingOrderDetailDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina la operacion updateShippingOrderDetail/ShippingOrderDetailDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal#deleteShippingOrderDetailsByWorkOrderId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void deleteShippingOrderDetailsByWorkOrderId(Long workOrderId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteShippingOrderDetailsByWorkOrderId/ShippingOrderDetailDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("delete from ");
			stringQuery.append(ShippingOrderDetail.class.getName());
			stringQuery.append(" entity ");
			stringQuery.append(" where entity.id in ");
			stringQuery.append("( select detail.id from ");
			stringQuery.append(ShippingOrderDetail.class.getName());
			stringQuery.append(" detail where detail.shippingOrder.workOrderId = :aWorkOrderId ) ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWorkOrderId", workOrderId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el deleteShippingOrderDetailsByWorkOrderId/ShippingOrderDetailDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteShippingOrderDetailsByWorkOrderId/ShippingOrderDetailDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal#getShippingOrderTechnologiesBySOId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Technology> getShippingOrderTechnologiesBySOId(Long soId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio la operacion getShippingOrderTechnologiesBySOId/ShippingOrderDetailDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select detail.technology from ");
			stringQuery.append(ShippingOrderDetail.class.getName());
			stringQuery.append(" detail where detail.shippingOrder.id = :soId ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("soId", soId);

			return query.list();
		} catch (Throwable ex) {
			log.error("== Error en la operacion getShippingOrderTechnologiesBySOId/ShippingOrderDetailDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina la operacion getShippingOrderTechnologiesBySOId/ShippingOrderDetailDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal#getShippingOrderTechnologiesBySOIdAndTecCode(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Technology> getShippingOrderTechnologiesBySOIdAndTecCode(Long soId, String tecCode) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio la operacion getShippingOrderTechnologiesBySOIdAndTecCode/ShippingOrderDetailDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select detail.technology from ");
			stringQuery.append(ShippingOrderDetail.class.getName());
			stringQuery.append(" detail where detail.shippingOrder.id = :soId ");
			stringQuery.append(" and detail.technology.code = :tecCode ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("soId", soId);
			query.setString("tecCode", tecCode);

			return query.list();
		} catch (Throwable ex) {
			log.error("== Error en la operacion getShippingOrderTechnologiesBySOIdAndTecCode/ShippingOrderDetailDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina la operacion getShippingOrderTechnologiesBySOIdAndTecCode/ShippingOrderDetailDAO ==");
		}
	}

}
