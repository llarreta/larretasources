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
import co.com.directv.sdii.model.pojo.Delivery;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.DeliveryDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Delivery
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Delivery
 * @see co.com.directv.sdii.model.hbm.Delivery.hbm.xml
 */
@Stateless(name="DeliveryDAOLocal",mappedName="ejb/DeliveryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DeliveryDAO extends BaseDao implements DeliveryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DeliveryDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DeliverysDAOLocal#createDelivery(co.com.directv.sdii.model.pojo.Delivery)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDelivery(Delivery obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDelivery/DeliveryDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Delivery ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDelivery/DeliveryDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DeliverysDAOLocal#updateDelivery(co.com.directv.sdii.model.pojo.Delivery)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDelivery(Delivery obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDelivery/DeliveryDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Delivery ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDelivery/DeliveryDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DeliverysDAOLocal#deleteDelivery(co.com.directv.sdii.model.pojo.Delivery)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDelivery(Delivery obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDelivery/DeliveryDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Delivery entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Delivery ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDelivery/DeliveryDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DeliverysDAOLocal#getDeliverysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Delivery getDeliveryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDeliveryByID/DeliveryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Delivery.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Delivery) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDeliveryByID/DeliveryDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DeliverysDAOLocal#getAllDeliverys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Delivery> getAllDeliverys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDeliverys/DeliveryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Delivery.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDeliverys/DeliveryDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DeliverysDAOLocal#getDeliveriesByReferenceID(java.lang.Long)
     */
    public List<Object[]> getDeliveriesByReferenceID(Long referenceId) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getDeliveriesByReferenceID/DeliveryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(User.class.getName());
        	stringQuery.append(" user, ");
        	stringQuery.append(Delivery.class.getName());
        	stringQuery.append(" delivery ");
        	stringQuery.append("where delivery.reference.id = :referenceId ");
        	stringQuery.append("and delivery.userId=user.id ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("referenceId", referenceId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getDeliveriesByReferenceID/DeliveryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDeliveriesByReferenceID/DeliveryDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.DeliverysDAOLocal#getTotalDeliveriesByElementIdAndReferenceId(java.lang.Long,java.lang.Long)
     */
    public Double getTotalDeliveriesByElementIdAndReferenceId(Long referenceId , Long elementId) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getTotalDeliveriesByElementIdAndReferenceId/DeliveryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select sum(delivery.deliveryQuantity) from ");
        	stringQuery.append(Delivery.class.getName());
        	stringQuery.append(" delivery ");
        	stringQuery.append("where delivery.reference.id = :referenceId ");
        	stringQuery.append("and delivery.referenceElementItem.id = :elementId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("referenceId", referenceId);
        	query.setLong("elementId", elementId);
        	Double result = (Double) query.uniqueResult(); 
        	if(result == null)
        		result = 0D;
        	return result;
        } catch (Throwable ex){
			log.error("== Error getTotalDeliveriesByElementIdAndReferenceId/DeliveryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTotalDeliveriesByElementIdAndReferenceId/DeliveryDAO ==");
        }
    }
}
