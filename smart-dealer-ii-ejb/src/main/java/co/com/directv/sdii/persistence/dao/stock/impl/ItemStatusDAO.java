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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ItemStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ItemStatus
 * @see co.com.directv.sdii.model.hbm.ItemStatus.hbm.xml
 */
@Stateless(name="ItemStatusDAOLocal",mappedName="ejb/ItemStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ItemStatusDAO extends BaseDao implements ItemStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ItemStatusDAO.class);
    
    //cache items
    private ItemStatus itemStatusRecepted;	
	private ItemStatus itemStatusSended;
	private ItemStatus itemStatusCreated;
	private ItemStatus 	itemStatusPartial;
	private ItemStatus itemStatusInconsistency;
	
    public ItemStatus getItemStatusSended() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(itemStatusSended == null) {
    		itemStatusSended = getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity() );
    	}
    	return itemStatusSended;
    }
    
    public ItemStatus getItemStatusRecepted() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(itemStatusRecepted == null) {
    		itemStatusRecepted = getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity() );
    	}
    	return itemStatusRecepted;
    }
        
    public ItemStatus getItemStatusCreated() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(itemStatusCreated == null) {
    		itemStatusCreated = getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity() );
    	}
    	return itemStatusCreated;
    }
    
    public ItemStatus getItemStatusInconsistency() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(itemStatusInconsistency == null) {
    		itemStatusInconsistency = getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity() );
    	}
    	return itemStatusInconsistency;
    }
    
    public ItemStatus getItemStatusPartial() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(itemStatusPartial == null) {
    		itemStatusPartial = getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
    	}
    	return itemStatusPartial;
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ItemStatussDAOLocal#createItemStatus(co.com.directv.sdii.model.pojo.ItemStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createItemStatus(ItemStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createItemStatus/ItemStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ItemStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createItemStatus/ItemStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ItemStatussDAOLocal#updateItemStatus(co.com.directv.sdii.model.pojo.ItemStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateItemStatus(ItemStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateItemStatus/ItemStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ItemStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateItemStatus/ItemStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ItemStatussDAOLocal#deleteItemStatus(co.com.directv.sdii.model.pojo.ItemStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteItemStatus(ItemStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteItemStatus/ItemStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ItemStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ItemStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteItemStatus/ItemStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ItemStatussDAOLocal#getItemStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ItemStatus getItemStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getItemStatusByID/ItemStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ItemStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ItemStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getItemStatusByID/ItemStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ItemStatussDAOLocal#getAllItemStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ItemStatus> getAllItemStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllItemStatuss/ItemStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ItemStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllItemStatuss/ItemStatusDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal#getItemStatusByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ItemStatus getItemStatusByCode(String itemStatusCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getItemStatusByCode/ItemStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ItemStatus.class.getName());
        	stringQuery.append(" entity where entity.statusCode = :anItemStatusCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anItemStatusCode", itemStatusCode);

            return (ItemStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getItemStatusByCode/ItemStatusDAO ==");
        }
	}

}
