package co.com.directv.sdii.persistence.dao.stock.impl;

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
import co.com.directv.sdii.model.pojo.ShippingOrderElement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ShippingOrderElement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ShippingOrderElement
 * @see co.com.directv.sdii.model.hbm.ShippingOrderElement.hbm.xml
 */
@Stateless(name="ShippingOrderElementDAOLocal",mappedName="ejb/ShippingOrderElementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShippingOrderElementDAO extends BaseDao implements ShippingOrderElementDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderElementDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementsDAOLocal#createShippingOrderElement(co.com.directv.sdii.model.pojo.ShippingOrderElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createShippingOrderElement(ShippingOrderElement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createShippingOrderElement/ShippingOrderElementDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ShippingOrderElement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createShippingOrderElement/ShippingOrderElementDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementsDAOLocal#updateShippingOrderElement(co.com.directv.sdii.model.pojo.ShippingOrderElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateShippingOrderElement(ShippingOrderElement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateShippingOrderElement/ShippingOrderElementDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ShippingOrderElement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateShippingOrderElement/ShippingOrderElementDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementsDAOLocal#deleteShippingOrderElement(co.com.directv.sdii.model.pojo.ShippingOrderElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteShippingOrderElement(ShippingOrderElement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteShippingOrderElement/ShippingOrderElementDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ShippingOrderElement entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ShippingOrderElement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteShippingOrderElement/ShippingOrderElementDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementsDAOLocal#getShippingOrderElementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderElement getShippingOrderElementByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getShippingOrderElementByID/ShippingOrderElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ShippingOrderElement.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ShippingOrderElement) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getShippingOrderElementByID/ShippingOrderElementDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementsDAOLocal#getAllShippingOrderElements()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderElement> getAllShippingOrderElements() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllShippingOrderElements/ShippingOrderElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ShippingOrderElement.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllShippingOrderElements/ShippingOrderElementDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal#getShippingOrderElementBySOID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderElement> getShippingOrderElementBySOID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getShippingOrderElementBySOID/ShippingOrderElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ShippingOrderElement.class.getName());
        	stringQuery.append(" entity where entity.soId = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);
            return (List<ShippingOrderElement>) query.list();
        } catch (Throwable ex){
			log.error("== Error en getShippingOrderElementBySOID/ShippingOrderElementDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getShippingOrderElementBySOID/ShippingOrderElementDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal#getShippingOrderElementByWorkOrderId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ShippingOrderElement> getShippingOrderElementByWorkOrderId(Long workOrderId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getShippingOrderElementByWorkOrderId/ShippingOrderElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ShippingOrderElement.class.getName());
        	stringQuery.append(" entity where entity.soId in (select so.id from ShippingOrder so where so.workOrderId = :aWorkOrderId)");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWorkOrderId", workOrderId);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error en getShippingOrderElementByWorkOrderId/ShippingOrderElementDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getShippingOrderElementByWorkOrderId/ShippingOrderElementDAO ==");
        }
	}



	@Override
	public void deleteShippingOrderElementByWoId(Long workOrderId)
			throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio deleteShippingOrderElementByWoId/ShippingOrderElementDAO ==");
	        Session session = super.getSession();
	        try {
	            Query query = session.createQuery("delete from ShippingOrderElement entity where entity.soId in (select so.id from ShippingOrder so where so.workOrderId = :aWorkOrderId)");
	            query.setLong("aWorkOrderId", workOrderId);
	            query.executeUpdate();
	            super.doFlush(session);
	        } catch (Throwable ex) {
	            log.debug("== Error eliminando el ShippingOrderElement ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina deleteShippingOrderElementByWoId/ShippingOrderElementDAO ==");
	        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal#getShippingOrderElementByWorkOrderId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ShippingOrderElement> getSOrderElementByWorkOrderIdByElementTypeId(Long workOrderId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSOrderElementByWorkOrderIdByElementTypeId/ShippingOrderElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select entity");
        	stringQuery.append(" from ");
        	stringQuery.append(ShippingOrderElement.class.getName());
        	stringQuery.append(" entity join entity.element where ");
        	stringQuery.append(" entity.soId in (select so.id from ShippingOrder so where so.workOrderId = :aWorkOrderId)");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWorkOrderId", workOrderId);
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error en getSOrderElementByWorkOrderIdByElementTypeId/ShippingOrderElementDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSOrderElementByWorkOrderIdByElementTypeId/ShippingOrderElementDAO ==");
        }
	}


}
