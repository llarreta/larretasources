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
import co.com.directv.sdii.model.pojo.RefQuantityControlItem;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad RefQuantityControlItem
 * 
 * Fecha de Creación: Jul 12, 2011
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0

 */
@Stateless(name="RefQuantityControlItemDAOLocal",mappedName="ejb/RefQuantityControlItemDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefQuantityControlItemDAO extends BaseDao implements
		RefQuantityControlItemDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(ReferenceModificationDAO.class);

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#createRefQuantityControlItem(co.com.directv.sdii.model.pojo.RefQuantityControlItem)
	 */
	@Override
	public void createRefQuantityControlItem(RefQuantityControlItem obj)
			throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio createRefQuantityControlItem/RefQuantityControlItemDAO ==");
	        Session session = super.getSession();
	        try {
	            session.save(obj);
	            this.doFlush(session);
	        } catch (Throwable ex) {
	            log.debug("== Error creando el RefQuantityControlItemDAO ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina createRefQuantityControlItem/RefQuantityControlItemDAO ==");
	        }

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#deleteRefQuantityControlItem(co.com.directv.sdii.model.pojo.RefQuantityControlItem)
	 */
	@Override
	public void deleteRefQuantityControlItem(RefQuantityControlItem obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteRefQuantityControlItem/RefQuantityControlItemDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from RefQuantityControlItem entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el RefQuantityControlItemDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefQuantityControlItem/RefQuantityControlItemDAO ==");
        }

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#getAllRefQuantityControlItems()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RefQuantityControlItem> getAllRefQuantityControlItems()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllRefQuantityControlItems/RefQuantityControlItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefQuantityControlItem.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllRefQuantityControlItems/RefQuantityControlItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#getRefQtyCtrlItemsByReference(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public RefQuantityControlItemsResponse getRefQtyCtrlItemsByReference(
			Long refId, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getRefQtyCtrlItemsByReference/RefQuantityControlItemDAO ==");
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(RefQuantityControlItem.class.getName());
        	stringQuery.append(" entity where entity.reference = :refId");
        	stringQuery.append(" order by entity.elementType.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("refId", refId);

            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setLong("refId", refId);
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	RefQuantityControlItemsResponse response = new RefQuantityControlItemsResponse();
        	List<RefQuantityControlItem> refQtCtrlItems = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), refQtCtrlItems.size(), recordQty.intValue() );
        	response.setRefQuantityControlItems(refQtCtrlItems );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefQtyCtrlItemsByReference/RefQuantityControlItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#getRefQuantityControlItemByID(java.lang.Long)
	 */
	@Override
	public RefQuantityControlItem getRefQuantityControlItemByID(Long id)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getRefQuantityControlItemByID/RefQuantityControlItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefQuantityControlItem.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (RefQuantityControlItem) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefQuantityControlItemByID/RefQuantityControlItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#updateRefQuantityControlItem(co.com.directv.sdii.model.pojo.RefQuantityControlItem)
	 */
	@Override
	public void updateRefQuantityControlItem(RefQuantityControlItem obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateRefQuantityControlItem/RefQuantityControlItemDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RefQuantityControlItemDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefQuantityControlItem/RefQuantityControlItemDAO ==");
        }

	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#getReferencesPreloadByCreationProcess(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public RefQuantityControlItemsResponse getReferencesPreloadByCreationProcess(
			Long idSourceWh,RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesPreloadByCreationProcess/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(RefQuantityControlItem.class.getName());
        	stringQuery.append(" entity where  ");
        	stringQuery.append(" entity.reference.warehouseBySourceWh.id = :whSource");
    		stringQuery.append(" and entity.reference.referenceStatus.refStatusCode = :statusProcCreated");
    		stringQuery.append(" and entity.reference.isPreloadRef = :isPreloadRef");
        	
        	stringQuery.append(" order by entity.id asc");
        	
        	stringCount.append(stringQuery.toString());
        	Query query = session.createQuery(stringQuery.toString());
    		query.setLong("whSource", idSourceWh);
    		query.setString("statusProcCreated", CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity());
    		query.setString("isPreloadRef", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setLong("whSource", idSourceWh);
        		queryCount.setString("statusProcCreated", CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity());
        		queryCount.setString("isPreloadRef", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	RefQuantityControlItemsResponse response = new RefQuantityControlItemsResponse();
        	List<RefQuantityControlItem> reference= query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), reference.size(), recordQty.intValue() );
        	response.setRefQuantityControlItems(reference);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesPreloadByCreationProcess/WarehouseDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#getRefQuantityControlItemByElmtTypeAndRef(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public RefQuantityControlItem getRefQuantityControlItemByElmtTypeAndRef(
			Long elementType, Long reference) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesPreloadByCreationProcess/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefQuantityControlItem.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" entity.reference.id = :reference");
    		stringQuery.append(" and entity.elementType.id = :elementType");        	
        	Query query = session.createQuery(stringQuery.toString());
    		query.setLong("reference", reference);
    		query.setLong("elementType", elementType);
			
			return (RefQuantityControlItem) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesPreloadByCreationProcess/WarehouseDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal#validateExistenceOfReferenceQuantityDifferences(java.lang.Long)
	 */
	@Override
	public boolean validateExistenceOfReferenceQuantityDifferences(
			Long referenceId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio validateExistenceOfReferenceQuantityDiferences/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("select count(id) from ");
        	stringQuery.append(RefQuantityControlItem.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" entity.reference.id = :referenceId");
        	stringQuery.append(" and entity.requiredQty > 0");
    		stringQuery.append(" and entity.requiredQty <> entity.includedQty");
        	Query query = session.createQuery(stringQuery.toString());
    		query.setLong("referenceId", referenceId);
			
    		Long differences = (Long) query.uniqueResult();
    		
			return (differences > 0);

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina validateExistenceOfReferenceQuantityDiferences/WarehouseDAO ==");
        }
	}
}
