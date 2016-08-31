package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.pojo.ReportedElement;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal;
import co.com.directv.sdii.ws.model.dto.RefElementItemDTO;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ReferenceElementItem
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ReferenceElementItem
 * @see co.com.directv.sdii.model.hbm.ReferenceElementItem.hbm.xml
 */
@Stateless(name="ReferenceElementItemDAOLocal",mappedName="ejb/ReferenceElementItemDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceElementItemDAO extends BaseDao implements ReferenceElementItemDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ReferenceElementItemDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemsDAOLocal#createReferenceElementItem(co.com.directv.sdii.model.pojo.ReferenceElementItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createReferenceElementItem(ReferenceElementItem obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createReferenceElementItem/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ReferenceElementItem ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceElementItem/ReferenceElementItemDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemsDAOLocal#updateReferenceElementItem(co.com.directv.sdii.model.pojo.ReferenceElementItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateReferenceElementItem(ReferenceElementItem obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateReferenceElementItem/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ReferenceElementItem ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceElementItem/ReferenceElementItemDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * Recibe un objeto referencia y actualiza todos sus elementos al estado "Enviado"
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemsDAOLocal#updateReferenceElementItem(co.com.directv.sdii.model.pojo.ReferenceElementItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateMasiveReferenceElementItem(ReferenceVO referenceVO, Long newItemStatusId) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateMasiveReferenceElementItem/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();

        	stringQuery.append(" UPDATE reference_element_items ");
        	stringQuery.append(" SET item_status_id = :P_ITEM_STATUS_ID ");
        	stringQuery.append(" WHERE id          IN ");
        	stringQuery.append(" (SELECT rei.id ");
        	stringQuery.append(" FROM reference_element_items rei, ");
        	stringQuery.append(" elements e ");
        	stringQuery.append(" WHERE rei.element_id  =e.id ");
        	stringQuery.append(" AND e.is_serialized   = 'S' ");
        	stringQuery.append(" AND rei.references_id = :P_ID_REMISION )");        	      	  
        	  
        	Query query = session.createSQLQuery(stringQuery.toString());
        	query.setLong("P_ITEM_STATUS_ID", newItemStatusId);
        	query.setLong("P_ID_REMISION", referenceVO.getId());
        	
        	query.executeUpdate();
        } catch (Throwable ex) {
            log.debug("== Error actualizando el updateMasiveReferenceElementItem ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMasiveReferenceElementItem/ReferenceElementItemDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemsDAOLocal#deleteReferenceElementItem(co.com.directv.sdii.model.pojo.ReferenceElementItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteReferenceElementItem(ReferenceElementItem obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteReferenceElementItem/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ReferenceElementItem entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ReferenceElementItem ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceElementItem/ReferenceElementItemDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemsDAOLocal#getReferenceElementItemsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ReferenceElementItem getReferenceElementItemByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceElementItemByID/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ReferenceElementItem) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByID/ReferenceElementItemDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemsDAOLocal#getAllReferenceElementItems()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ReferenceElementItem> getAllReferenceElementItems() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllReferenceElementItems/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllReferenceElementItems/ReferenceElementItemDAO ==");
        }
    }



    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemsByReferenceID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(Long refID, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceElementItemsByReferenceID/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :aRefId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aRefId", refID);

          //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
        		stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("aRefId", refID);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	ReferenceElementItemsResponse response = new ReferenceElementItemsResponse();
        	List<ReferenceElementItem> list = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setReferenceElementItems( list );
        	
            return response;
        } catch (Throwable ex){
			log.error("== Error en la operacion getReferenceElementItemsByReferenceID/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemsByReferenceID/ReferenceElementItemDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemsByReferenceID(java.lang.Long)
     */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void updateElementItemStatusToReferenceShipment( ReferenceElementItem reference )throws DAOServiceException, DAOSQLException{    	
    	log.debug("== Inicio updateElementItemStatusToReferenceShipment/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {        	
        	session.update( reference );     	
        } catch (Throwable ex){
			log.error("== Error en la operacion updateElementItemStatusToReferenceShipment/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina updateElementItemStatusToReferenceShipment/ReferenceElementItemDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getNotSerializeReferenceElementItemByReferenceId(java.lang.Long)
     */
	
    @SuppressWarnings("unchecked")	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ReferenceElementItem> getNotSerializedReferenceElementItemByReferenceId(Long referenceId)throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getNotSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where  ");
        	stringQuery.append("entity.element.isSerialized=:notSerialize ");
        	stringQuery.append("and entity.reference.id=:referenceId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("notSerialize", CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
        	query.setLong("referenceId", referenceId);
        	List<ReferenceElementItem> items = query.list();
        	if(items != null ){
        		for(ReferenceElementItem item : items ){
        			Reference reference = new Reference();
        			reference.setId(item.getReference().getId());
        			item.setReference(reference);
        		}
        	}
        	return items;
            
        } catch (Throwable ex){
			log.error("== Error getNotSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getSerializedReferenceElementItemByReferenceId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceElementItem> getSerializedReferenceElementItemByReferenceId(Long referenceId)throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where  ");
        	stringQuery.append("entity.element.isSerialized=:serialize ");
        	stringQuery.append("and entity.reference.id=:referenceId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("serialize", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        	query.setLong("referenceId", referenceId);
        	List<ReferenceElementItem> items = query.list();
        	if(items != null ){
        		for(ReferenceElementItem item : items ){
        			Reference reference = new Reference();
        			reference.setId(item.getReference().getId());
        			item.setReference(reference);
        		}
        	}
        	return items;
            
        } catch (Throwable ex){
			log.error("== Error getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        }
    }
    
        
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getSerializedReferenceElementItemByReferenceIdSQL(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ReferenceElementItem> getSerializedReferenceElementItemByReferenceIdSQL(Long referenceId)throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getSerializedReferenceElementItemByReferenceIdSQL/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();

        	stringQuery.append("SELECT rei.id rid, ");
        	stringQuery.append("e.id eid, rei.ref_quantity rq ");
        	stringQuery.append("FROM reference_element_items rei, ");
        	stringQuery.append("elements e ");
        	stringQuery.append("WHERE rei.element_id =e.ID ");
        	stringQuery.append("AND e.is_serialized   = :isSerialize ");
        	stringQuery.append("AND rei.references_id= :referenceId ");

        	Query query = session.createSQLQuery(stringQuery.toString())
                    .addScalar("rid", Hibernate.LONG)
                    .addScalar("eid", Hibernate.LONG)
                    .addScalar("rq", Hibernate.DOUBLE);
      	
        	
        	query.setString("isSerialize", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        	query.setLong("referenceId", referenceId);


        	List<Object[]> items = query.list();
        	
        	Reference r = new Reference();
        	r.setId(referenceId);
        	List<ReferenceElementItem> refElemItemList = new ArrayList<ReferenceElementItem>();
        	for(Object[] item : items){
        		Long rid = (Long)item[0];
        		Long eid = (Long)item[1];
        		Double rq = (Double)item[2];
        		Element elem = new Element();
        		elem.setId(eid);
        		ReferenceElementItem refElem = new ReferenceElementItem();
        		refElem.setRefQuantity(rq);
        		refElem.setId(rid);
        		refElem.setElement(elem);
        		refElem.setReference(r);
        		refElemItemList.add(refElem);
        	}
        	
        	return refElemItemList;
            
        } catch (Throwable ex){
			log.error("== Error getSerializedReferenceElementItemByReferenceIdSQL/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedReferenceElementItemByReferenceIdSQL/ReferenceElementItemDAO ==");
        }
    }    
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getSerializedReferenceElementItemByReferenceId(java.lang.Long)
     */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void modifyReferenceElementItemCuantityByReferenceAndItemList( List<ReferenceElementItem>items,Reference reference,Boolean finished )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicia modifyReferenceElementItemCuantityByReferenceAndItemList/ReferenceElementItemDAO ==");

        try {
        	
        	for( ReferenceElementItem item:items ){
        	   ReferenceElementItem itemPojo = getReferenceElementItemByID( item.getId() );
        	    if( itemPojo!=null && (item.getRefQuantity()>= itemPojo.getRefQuantity()) ){
        	          itemPojo.setRefQuantity( item.getRefQuantity() );  
        	          itemPojo.setReference( reference );
        	          updateReferenceElementItem( itemPojo );
        	    } else{
        	    	ReferenceElementItem newPojo = UtilsBusiness.copyObject(ReferenceElementItem.class, item);
        	    	newPojo.setReference(reference);
        	    }
        	}
            
        } catch (Throwable ex){
			log.error("== Error modifyReferenceElementItemCuantityByReferenceAndItemList/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina modifyReferenceElementItemCuantityByReferenceAndItemList/ReferenceElementItemDAO ==");
        }
    }
    
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void addReferenceElementItemToReference( List<ReferenceElementItem>items,Reference reference )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicia addReferenceElementItemToReference/ReferenceElementItemDAO ==");
    	 try {
         	
         	for(ReferenceElementItem item:items){
         	  item.setReference(reference);
         	  createReferenceElementItem(item);
         	}
             
         } catch (Throwable ex){
 			log.error("== Error addReferenceElementItemToReference/ReferenceElementItemDAO ==");
 			throw this.manageException(ex);
 		} finally {
             log.debug("== Termina addReferenceElementItemToReference/ReferenceElementItemDAO ==");
         }
    }

	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(java.lang.Long,java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceElementItem> getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(Long referenceId,String elementItemStatus,String elementType)throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.itemStatus.statusCode <> :elementItemStatus ");
        	stringQuery.append("and entity.reference.id = :referenceId ");
        	stringQuery.append("and entity.element.isSerialized = :elementType ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("elementItemStatus", elementItemStatus);
        	query.setLong("referenceId", referenceId);
        	query.setString("elementType", elementType);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
        }
    }
	
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemByElementId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ReferenceElementItem getReferenceElementItemByElementIdAndReferenceId(
			Long elementId, Long referenceId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceElementItemByElementId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.element.id = :anElementId");
        	stringQuery.append(" and entity.reference.id = :aReferenceId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anElementId", elementId);
            query.setLong("aReferenceId", referenceId);

            List<ReferenceElementItem> result = query.list();
            
            if(result.isEmpty()){
            	return null;
            }
            
            return result.get(0);

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByElementId/ReferenceElementItemDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemByElementTypeIdAndReferenceId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ReferenceElementItem getReferenceElementItemByElementTypeIdAndReferenceId(
			Long elementTypeId, Long referenceId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceElementItemByElementId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.element.elementType.id = :elementTypeId");
        	stringQuery.append(" and entity.reference.id = :aReferenceId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementTypeId", elementTypeId);
            query.setLong("aReferenceId", referenceId);

            List<ReferenceElementItem> result = query.list();
            
            if(result.isEmpty()){
            	return null;
            }
            
            return result.get(0);

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByElementId/ReferenceElementItemDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemByElementId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ReferenceElementItem getReferenceElementItemByReferenceIdAndElementId(Long referenceId, Long elementId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceElementItemByReferenceIdAndElementId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :aReferenceId and entity.element.id = :anElementId");
        	Query query = session.createQuery(stringQuery.toString());

        	query.setLong("aReferenceId", referenceId);
        	query.setLong("anElementId", elementId);

            List<ReferenceElementItem> result = query.list();
            
            if(result.isEmpty()){
            	return null;
            }
            
            return result.get(0);

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndElementId/ReferenceElementItemDAO ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemByRefTargetWhIdAndElementId(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReferenceElementItem> getReferenceElementItemByRefTargetWhIdAndElementId(
			Long targetWhId, Long elementId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferenceElementItemByReferenceIdAndElementId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.reference.warehouseByTargetWh.id = :aReferenceTargetWhId and entity.element.id = :anElementId order by entity.reference.creationReferenceDate desc");
        	Query query = session.createQuery(stringQuery.toString());

        	query.setLong("aReferenceTargetWhId", targetWhId);
        	query.setLong("anElementId", elementId);

            List<ReferenceElementItem> result = query.list();
            return result;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndElementId/ReferenceElementItemDAO ==");
        }
	}
	
	@Override
	public Double getReferenceElementItemQuantityByReferenceAndElement(Long referenceId, Long elementId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceElementItemQuantityByReferenceAndElement/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	Double quantity = 0D;
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select entity.refQuantity from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :referenceId and entity.id = :elementId");
        	Query query = session.createQuery(stringQuery.toString());

        	query.setLong("referenceId", referenceId);
        	query.setLong("elementId", elementId);

            quantity = (Double) query.uniqueResult();
            return quantity;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemQuantityByReferenceAndElement/ReferenceElementItemDAO ==");
        }
	}
	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getNotSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(java.lang.Long,java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceElementItem> getNotSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(Long referenceId,String elementItemStatus)throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.itemStatus.statusCode <> :elementItemStatus ");
        	stringQuery.append("and entity.reference.id = :referenceId ");
        	stringQuery.append("and entity.element.isSerialized=:serialize ");
        	
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("serialize", CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity());
        	query.setString("elementItemStatus", elementItemStatus);
        	query.setLong("referenceId", referenceId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getNotSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(java.lang.Long,java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceElementItem> getSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(Long referenceId,String elementItemStatus)throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.itemStatus.statusCode <> :elementItemStatus ");
        	stringQuery.append("and entity.reference.id = :referenceId ");
        	stringQuery.append("and entity.element.isSerialized=:serialize ");
        	
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("serialize", CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
        	query.setString("elementItemStatus", elementItemStatus);
        	query.setLong("referenceId", referenceId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemDAO ==");
        }
    }


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemByReferenceIdAndByNotItemStatus(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReferenceElementItem> getReferenceElementItemByReferenceIdAndByNotItemStatus(
			Long referenceId, String elementItemStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getReferenceElementItemByReferenceIdAndByItemStatus/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.itemStatus.statusCode <> :elementItemStatus ");
        	stringQuery.append("and entity.reference.id = :referenceId ");
        	
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("elementItemStatus", elementItemStatus);
        	query.setLong("referenceId", referenceId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getReferenceElementItemByReferenceIdAndByItemStatus/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndByItemStatus/ReferenceElementItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getItemStatusCodeByRefElementItemId(java.lang.Long)
	 */
	@Override
	public String getItemStatusCodeByRefElementItemId(Long refElementItemId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getItemStatusCodeByRefElementItemId/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select rei.itemStatus.statusCode from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" rei where rei.id = :aReiId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aReiId", refElementItemId);
        	String itemStatusCode = (String)query.uniqueResult();
        	return itemStatusCode;
            
        } catch (Throwable ex){
			log.error("== Error getItemStatusCodeByRefElementItemId/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getItemStatusCodeByRefElementItemId/ReferenceElementItemDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemsByReferenceIdAndSerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementMixedResponse getReferenceElementsSerializedByReferenceIdAndSerialOrElementType(
			Long refID, String serial, Long elementTypeId,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		
		log.debug("== Inicio getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        boolean isElementType = (elementTypeId != null && elementTypeId.doubleValue() != 0);
        boolean isSerialCode = (serial != null && !serial.equals(""));
        
        try {
        	StringBuffer stringCount = new StringBuffer("select count(*)");
        	StringBuffer stringQuery = new StringBuffer("select new ").append( RefElementItemDTO.class.getName() )        	
        	.append("(element, refItem.id, refItem.refQuantity)");//si no hay registro de confirmaación, se supone que la cantidad de elementos en la remisión es la cantidad faltante
        	
        	StringBuffer complementHql = new StringBuffer(" from ").append(Serialized.class.getName()).append(" element, ");
        	complementHql.append(ReferenceElementItem.class.getName()).append(" refItem");
        	complementHql.append(" where element.elementId = refItem.element.id");
        	if(isElementType){
        		complementHql.append(" and element.element.elementType.id = :elementTypeId");
        	}
        	if(isSerialCode){
        		complementHql.append(" and UPPER(element.serialCode) = UPPER(:serialCode)");
        	}
        	complementHql.append(" and refItem.reference.id = :refID ");
        	
        	
        	//incluir elementos con estado ENVIADO únicamente. (ver CU_INV_36 salida 3)
        	complementHql.append(" and refItem.itemStatus.statusCode = '")
	        	.append(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity()).append("'");
        	
        	
        	stringQuery.append(complementHql);
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setLong("refID", refID);
            
            if(isElementType) {
            	query.setLong("elementTypeId", elementTypeId);
            } 
            if(isSerialCode){
            	query.setString("serialCode", serial);
            }
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
        		stringCount.append(complementHql);
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("refID", refID);
            	
            	if(isElementType){
                	countQuery.setLong("elementTypeId", elementTypeId);
                }
            	if(isSerialCode){
            		countQuery.setString("serialCode", serial);
                }
            	
	        	recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}
        	ElementMixedResponse response = new ElementMixedResponse();
        	List<RefElementItemDTO> list = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setElementDTOs(list);
        	
            return response;
        } catch (Throwable ex){
			log.error("== Error en la operacion getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemsByReferenceIdAndSerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementMixedResponse getReferenceElementsNotSerializedByReferenceIdElementType(
			Long refID, Long elementTypeId,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		
		log.debug("== Inicio getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringCount = new StringBuffer("select count(*)");
        	StringBuffer stringQuery = new StringBuffer("select new ").append( RefElementItemDTO.class.getName() )        	
        	.append("(element, refItem.id, refItem.refQuantity)");
        	
        	boolean isElementType = (elementTypeId != null && elementTypeId.doubleValue() != 0);
        	
        	StringBuffer complementHql = new StringBuffer(" from ").append(NotSerialized.class.getName()).append(" element, ");
        	complementHql.append(ReferenceElementItem.class.getName()).append(" refItem");
        	complementHql.append(" where element.elementId = refItem.element.id");
        	if(isElementType){
        		complementHql.append(" and element.element.elementType.id = :elementTypeId");
        	}
        	complementHql.append(" and refItem.reference.id = :refID ");
        	
        	//mostrar solo elementos en Enviado o Confirmado Parcial (ver CU_INV_36 salida 3)
        	complementHql.append(" and refItem.itemStatus.statusCode in ('")
	        	.append(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity()).append("','")
	        	.append(CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity()).append("')");
        	
        	stringQuery.append(complementHql);
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setLong("refID", refID);
            
            if(isElementType) {
            	query.setLong("elementTypeId", elementTypeId);
            }
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
        		stringCount.append(complementHql);
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("refID", refID);
            	
            	if(isElementType){
                	countQuery.setLong("elementTypeId", elementTypeId);
                }
            	
	        	recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}
        	ElementMixedResponse response = new ElementMixedResponse();
        	List<RefElementItemDTO> list = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setElementDTOs(list);
        	
            return response;
        } catch (Throwable ex){
			log.error("== Error en la operacion getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemDAO ==");
        }
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getAddedElements(java.lang.Long)
	 */
	@Override
	public List<ReferenceElementItemVO> getReferenceElementsByRefInconsistencyId(Long refInconsistencyId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAddedElements/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer("select re.referenceElementItem from ")
	        	.append(ReportedElement.class.getName())
	        	.append(" re where re.refInconsistency.id = :refInconsistencyId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("refInconsistencyId", refInconsistencyId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getAddedElements/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAddedElements/ReferenceElementItemDAO ==");
        }		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemByReferenceId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo, boolean)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceElementItemsResponse getReferenceElementItemByReferenceId(Long referenceId, RequestCollectionInfo requestCollInfo,boolean isSerialized)throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	String stringIsSerialized = isSerialized ? CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() : CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity();
        	
        	StringBuffer stringCount = new StringBuffer("select count(*)");
        	stringCount.append("from ");
        	stringCount.append(ReferenceElementItem.class.getName()+" ");
        	stringCount.append("entity where  ");
        	stringCount.append("entity.element.isSerialized=:serialize ");
        	stringCount.append("and entity.reference.id=:referenceId ");
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select entity , ");
        	stringQuery.append("(select ser from Serialized ser where ser.elementId = entity.element.id), ");
        	stringQuery.append("(select notSer from NotSerialized notSer where notSer.elementId = entity.element.id) ");
        	
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName()+" ");
        	stringQuery.append("entity where  ");
        	stringQuery.append("entity.element.isSerialized=:serialize ");
        	stringQuery.append("and entity.reference.id=:referenceId ");
        	Query query = session.createQuery(stringQuery.toString());
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query countQuery = session.createQuery( stringCount.toString() );
        		countQuery.setString("serialize", stringIsSerialized);
        		countQuery.setLong("referenceId", referenceId);
        		recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}
        	
        	
        	query.setString("serialize", stringIsSerialized);
        	query.setLong("referenceId", referenceId);
        	
        	ReferenceElementItemsResponse response = new ReferenceElementItemsResponse();
        	
        	List<ReferenceElementItemVO> listElementsReference = new ArrayList<ReferenceElementItemVO>();
        	List<Object[]> listTemp = query.list();
        	for(Object[] objTemp:listTemp){
        		ReferenceElementItem referenceItemPojo = (ReferenceElementItem) objTemp[0];
        		
        		ReferenceElementItemVO referenceElementItemVO = UtilsBusiness.copyObject(ReferenceElementItemVO.class, referenceItemPojo);
        		Serialized serPojo = null;
        		NotSerialized notSerPojo = null;
        		if(referenceItemPojo.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
        			serPojo = (Serialized) objTemp[1];
        			if(serPojo!=null){
        				referenceElementItemVO.setSerializeElement(UtilsBusiness.copyObject(SerializedVO.class, serPojo));
        			}
        		}else{
        			notSerPojo = (NotSerialized) objTemp[2];
        			if(notSerPojo!=null){
        				referenceElementItemVO.setNotSerializedElement(UtilsBusiness.copyObject(NotSerializedVO.class, notSerPojo));
        			}
        		}
        		listElementsReference.add(referenceElementItemVO);
        		
        	}
//        	
//        	if(listElementsReference != null ){
//        		for(ReferenceElementItem item : list ){
//        			Reference reference = new Reference();
//        			reference.setId(item.getReference().getId());
//        			item.setReference(reference);
//        		}
//        	}
        	
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), listElementsReference.size(), recordQty.intValue() );
        	response.setReferenceElementItemsVO(listElementsReference);
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        }
    }
    
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ReferenceElementItemsResponse getNotSerizalizedElementByWarehouseIdAndLastStatus(Long wareHouseId, Long elementTypeId, String isPrepaid, RequestCollectionInfo requestCollInfo, Long modelId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	ReferenceElementItemsResponse response = new ReferenceElementItemsResponse();
        	boolean iselementTypeID = ( elementTypeId != null && elementTypeId.longValue() > 0 ) ? true : false;
        	boolean isModelID = ( modelId != null && modelId.longValue() > 0 ) ? true : false;
        	
        	StringBuffer stringCount = new StringBuffer("");
        	stringCount.append(" select count(1) from (select  element2_.ELEMENT_TYPE_ID as col_0_0_  from  WAREHOUSE_ELEMENTS warehousee0_, ");
        	stringCount.append("  NOT_SERIALIZED notseriali1_,  ELEMENTS element2_,  RECORD_STATUS recordstat3_,  ELEMENT_TYPES elementtyp6_,  ");
        	stringCount.append("   ELEMENT_MODELS elementmod7_  where  warehousee0_.NOT_SER_ID=notseriali1_.ELEMENT_ID   and notseriali1_.ELEMENT_ID=element2_.ID  ");
        	stringCount.append("   and warehousee0_.RECORD_STATUS_ID=recordstat3_.ID   and element2_.ELEMENT_TYPE_ID=elementtyp6_.ID   and elementtyp6_.ELEMENT_MODEL_ID=elementmod7_.ID  ");
        	stringCount.append(" and recordstat3_.RECORD_STATUS_CODE=:recordStaus   and warehousee0_.WAREHOUSE_ID= :warehouseId  ");
        	stringCount.append(" and (   warehousee0_.NOT_SER_ID is not null  )  ");
        	stringCount.append(" and elementmod7_.IS_PREPAID_MODEL=:isPrepaidModel  ");
        	if(isModelID){
        		stringCount.append(" and elementmod7_.ID=:modelID  ");
        	}
        	if(iselementTypeID){
        		stringCount.append(" and element2_.ELEMENT_TYPE_ID=:elementTypeId  ");
        	}
        	stringCount.append(" group by  element2_.ELEMENT_TYPE_ID )");
        	
        	

        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" select we.notSerialized.element.elementType.id, ");
        	stringQuery.append(" (select et from ElementType et where et.id =we.notSerialized.element.elementType.id), ");
        	stringQuery.append(" sum(we.actualQuantity)   ");
        	stringQuery.append(" from WarehouseElement we   ");
        	stringQuery.append(" where we.recordStatus.recordStatusCode = :recordStaus   ");
        	stringQuery.append(" and we.warehouseId.id = :warehouseId    ");
        	stringQuery.append(" and we.notSerialized is not null   ");
        	stringQuery.append(" and we.notSerialized.element.elementType.elementModel.isPrepaidModel = :isPrepaidModel   ");
        	if(isModelID){
        		stringQuery.append(" and we.notSerialized.element.elementType.elementModel.id=:modelID  ");
        	}
        	if(iselementTypeID){
        		stringQuery.append(" and we.notSerialized.element.elementType.id = :elementTypeId   ");
        	}
        	stringQuery.append(" group by we.notSerialized.element.elementType.id ");
        	
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query countQuery = session.createSQLQuery( stringCount.toString() );
        		countQuery.setString("recordStaus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
        		countQuery.setLong("warehouseId", wareHouseId);
        		countQuery.setString("isPrepaidModel", isPrepaid);
        		if(isModelID){
        			countQuery.setLong("modelID", modelId);
        		}
        		if(iselementTypeID){
        			countQuery.setLong("elementTypeId", elementTypeId);
        		}
        		
        		recordQty = new BigDecimal(countQuery.uniqueResult().toString()).longValue();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}
        	
        	
        	query.setString("recordStaus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
        	query.setLong("warehouseId", wareHouseId);
        	query.setString("isPrepaidModel", isPrepaid);
        	if(isModelID){
        		query.setLong("modelID", modelId);
    		}
    		if(iselementTypeID){
    			query.setLong("elementTypeId", elementTypeId);
    		}
        	
        	
        	
        	List<ReferenceElementItemVO> listElementsReference = new ArrayList<ReferenceElementItemVO>();
        	List<Object[]> listTemp = query.list();
        	for(Object[] objTemp:listTemp){
        		ElementType elementTypePojo = (ElementType) objTemp[1];
        		NotSerializedVO notSerializedVO = new NotSerializedVO(); 
        		notSerializedVO.setElement(new Element());
        		notSerializedVO.getElement().setElementType(elementTypePojo);
        		Double warehouseQuantity = new Double(objTemp[2].toString());
        		
        		ReferenceElementItemVO referenceElementItemVO = new ReferenceElementItemVO();
        		referenceElementItemVO.setNotSerializedElement(notSerializedVO);
        		referenceElementItemVO.setWarehouseQuantity(warehouseQuantity);
        		listElementsReference.add(referenceElementItemVO);
        		
        	}

        	response.setReferenceElementItemsVO(listElementsReference);
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), listElementsReference.size(), recordQty.intValue() );
        	response.setReferenceElementItemsVO(listElementsReference);
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        }
	}

    @SuppressWarnings("unchecked")
	@Override
	public List<ReferenceElementItem> getReferenceElementsByReferenceIdAndSerial(Long referenceID, String serialCode) throws DAOServiceException, DAOSQLException {		
		log.debug("== Inicio getReferenceElementsByReferenceIdAndSerial/ReferenceElementItemDAO ==");
        Session session = super.getSession();
        
        try {
        	StringBuffer stringQuery = new StringBuffer("SELECT rei");
        	stringQuery.append(" FROM ReferenceElementItem rei");
        	if (serialCode!=null && serialCode.length()>0){
        		stringQuery.append(", Serialized s");
        		stringQuery.append(" WHERE s.elementId=rei.element.id");
        		stringQuery.append(" AND rei.reference.id = :referenceID");
        		stringQuery.append(" AND s.serialCode = :serialCode ");
        	}else{
        		stringQuery.append(" WHERE rei.reference.id = :referenceID");
        	}
        	
        	Query query = session.createQuery( stringQuery.toString() );
        	
        	query.setLong("referenceID", referenceID);
        	if ( serialCode!=null && serialCode.length()>0 ){
        		query.setString("serialCode", serialCode.toUpperCase());
        	}
            
        	List<ReferenceElementItem> list = query.list();
        	
            return list;
        } catch (Throwable ex){
			log.error("== Error en la operacion getReferenceElementsByReferenceIdAndSerial/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceElementsByReferenceIdAndSerial/ReferenceElementItemDAO ==");
        }
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#areAllReferenceElementsInState(java.lang.Long, java.lang.String)
     */
	@Override
	public boolean areAllReferenceElementsInState(Long refId, String status)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer("select count(*) from ")
	        	.append(ReferenceElementItem.class.getName())
	        	.append(" entity where entity.reference.id = :refId")
	        	.append(" and entity.itemStatus.statusCode <> :itemStatusCode");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("refId", refId);
            query.setString("itemStatusCode", status);

            
            
            ////////// eliminar fragmento
            /*
            StringBuffer stringQuery1 = new StringBuffer("from ")
        	.append(ReferenceElementItem.class.getName())
        	.append(" entity where entity.reference.id = :refId")
        	.append(" and entity.itemStatus.statusCode <> :itemStatusCode");
            
            Query query1 = session.createQuery(stringQuery1.toString());
            query1.setLong("refId", refId);
            query1.setString("itemStatusCode", status);
            
            List list = query1.list();
            System.out.println(list);
            */
            ///////////////
            
            
            Long count = (Long) query.uniqueResult();
            count = (count == null ? 0L : count);
        	if(count > 0) {
        		 return false;//se encontraron estados diferentes al buscado
        	}
        	return true;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#areSomeReferenceElementsInState(java.lang.Long, java.lang.String)
	 */
	@Override
	public boolean areSomeReferenceElementsInState(Long refId, String status)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer("select count(*) from ")
	        	.append(ReferenceElementItem.class.getName())
	        	.append(" entity where entity.reference.id = :refId")
	        	.append(" and entity.itemStatus.statusCode = :itemStatusCode");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("refId", refId);
            query.setString("itemStatusCode", status);

            Long count = (Long) query.uniqueResult();
            count = (count == null ? 0L : count);
        	if(count > 0) {
        		 return true;//se encontró al menos un elemento en el estado buscado
        	}
        	return false;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        }
	}



	@SuppressWarnings("unchecked")
	@Override
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDSerAndNotSerPending(
			Long referenceId, RequestCollectionInfo requestCollInfo,
			String serialCode, boolean isSerialized) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        Session session = super.getSession();

        try {
        	String stringIsSerialized = isSerialized ? CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() : CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity();
        	boolean isSerialCode = serialCode!=null && !serialCode.isEmpty();
        	if(!isSerialized){
        		isSerialCode = false;
        	}
        	
        	StringBuffer stringCount = new StringBuffer("select count(*)");
        	stringCount.append("from ");
        	stringCount.append(ReferenceElementItem.class.getName()+" ");
        	stringCount.append("entity where  ");
        	stringCount.append("entity.element.isSerialized=:serialize ");
        	stringCount.append("and entity.reference.id=:referenceId ");
        	stringCount.append("and (entity.itemStatus.statusCode = :statusCode or entity.itemStatus.statusCode = :statusCode2)");
        	if(isSerialCode){
        		stringCount.append("and exists (select 1 from Serialized ser where ser.elementId = entity.element.id and UPPER(ser.serialCode) = TRIM(UPPER(:serialCode))) ");
        	}
        	
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select entity , ");
        	stringQuery.append("(select ser from Serialized ser where ser.elementId = entity.element.id), ");
        	stringQuery.append("(select notSer from NotSerialized notSer where notSer.elementId = entity.element.id) ");
        	
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceElementItem.class.getName()+" ");
        	stringQuery.append("entity where  ");
        	stringQuery.append("entity.element.isSerialized=:serialize ");
        	stringQuery.append("and entity.reference.id=:referenceId ");
        	stringQuery.append("and (entity.itemStatus.statusCode = :statusCode or entity.itemStatus.statusCode = :statusCode2)");
        	if(isSerialCode){
        		stringQuery.append("and exists (select 1 from Serialized ser where ser.elementId = entity.element.id and UPPER(ser.serialCode) = TRIM(UPPER(:serialCode))) ");
        	}
        	Query query = session.createQuery(stringQuery.toString());
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if(requestCollInfo!=null){
        		Query countQuery = session.createQuery(stringCount.toString());
        		countQuery.setString("serialize", stringIsSerialized);
        		countQuery.setLong("referenceId", referenceId);
        		countQuery.setString("statusCode", CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
        		countQuery.setString("statusCode2", CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        		if(isSerialCode){
        			countQuery.setString("serialCode", serialCode);
        		}
        		recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());
        	}
        	
        	if(isSerialCode){
        		query.setString("serialCode", serialCode);
        	}
        	query.setString("serialize", stringIsSerialized);
        	query.setLong("referenceId", referenceId);
        	query.setString("statusCode", CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
        	query.setString("statusCode2", CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        	
        	ReferenceElementItemsResponse response = new ReferenceElementItemsResponse();
        	
        	List<ReferenceElementItemVO> listElementsReference = new ArrayList<ReferenceElementItemVO>();
        	List<Object[]> listTemp = query.list();
        	for(Object[] objTemp:listTemp){
        		ReferenceElementItem referenceItemPojo = (ReferenceElementItem) objTemp[0];
        		
        		ReferenceElementItemVO referenceElementItemVO = UtilsBusiness.copyObject(ReferenceElementItemVO.class, referenceItemPojo);
        		Serialized serPojo = null;
        		NotSerialized notSerPojo = null;
        		if(referenceItemPojo.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
        			serPojo = (Serialized) objTemp[1];
        			if(serPojo!=null){
        				referenceElementItemVO.setSerializeElement(UtilsBusiness.copyObject(SerializedVO.class, serPojo));
        			}
        		}else{
        			notSerPojo = (NotSerialized) objTemp[2];
        			if(notSerPojo!=null){
        				referenceElementItemVO.setNotSerializedElement(UtilsBusiness.copyObject(NotSerializedVO.class, notSerPojo));
        			}
        		}
        		listElementsReference.add(referenceElementItemVO);
        		
        	}
//        	
//        
        	
        	if(requestCollInfo != null){
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), listElementsReference.size(), recordQty.intValue());
        	}
        	response.setReferenceElementItemsVO(listElementsReference);
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedReferenceElementItemByReferenceId/ReferenceElementItemDAO ==");
        }
	}
	
}
