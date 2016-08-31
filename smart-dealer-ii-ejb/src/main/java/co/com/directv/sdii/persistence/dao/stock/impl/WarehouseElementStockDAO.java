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
import co.com.directv.sdii.model.pojo.WarehouseElementStock;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WarehouseElemetStockResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementStockDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad WarehouseElementStock
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WarehouseElementStock
 * @see co.com.directv.sdii.model.hbm.WarehouseElementStock.hbm.xml
 */
@Stateless(name="WarehouseElementStockDAOLocal",mappedName="ejb/WarehouseElementStockDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WarehouseElementStockDAO extends BaseDao implements WarehouseElementStockDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WarehouseElementStockDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStocksDAOLocal#createWarehouseElementStock(co.com.directv.sdii.model.pojo.WarehouseElementStock)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWarehouseElementStock(WarehouseElementStock obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWarehouseElementStock/WarehouseElementStockDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el WarehouseElementStock ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouseElementStock/WarehouseElementStockDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStocksDAOLocal#updateWarehouseElementStock(co.com.directv.sdii.model.pojo.WarehouseElementStock)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWarehouseElementStock(WarehouseElementStock obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateWarehouseElementStock/WarehouseElementStockDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el WarehouseElementStock ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWarehouseElementStock/WarehouseElementStockDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStocksDAOLocal#deleteWarehouseElementStock(co.com.directv.sdii.model.pojo.WarehouseElementStock)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWarehouseElementStock(WarehouseElementStock obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteWarehouseElementStock/WarehouseElementStockDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from WarehouseElementStock entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el WarehouseElementStock ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWarehouseElementStock/WarehouseElementStockDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStocksDAOLocal#getWarehouseElementStocksByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WarehouseElementStock getWarehouseElementStockByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehouseElementStockByID/WarehouseElementStockDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseElementStock.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (WarehouseElementStock) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseElementStockByID/WarehouseElementStockDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStocksDAOLocal#getAllWarehouseElementStocks()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WarehouseElementStock> getAllWarehouseElementStocks() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllWarehouseElementStocks/WarehouseElementStockDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseElementStock.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehouseElementStocks/WarehouseElementStockDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStockDAOLocal#getWarehouseElementStockByElementCode(java.lang.String)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementStock> getWarehouseElementStockByElementCode(
			String code) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementStockByElementCode/WarehouseElementStockDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseElementStock.class.getName());
        	stringQuery.append(" stel where stel.elementType.typeElementCode = :anEtCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("anEtCode", code);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseElementStockByElementCode/WarehouseElementStockDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStockDAOLocal#getWarehouseElementsStockByElIdAndDealerIdAndWarehouseId(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementStock> getWarehEleStockByEleTypeIdAndDealerIdAndWarehId(
			Long elementId, Long dealerId, Long warehouseId)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getWarehouseElementsStockByElIdAndDealerIdAndWarehouseId/WarehouseElementStockDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseElementStock.class.getName());
        	stringQuery.append(" stel where stel.elementType.id = :anElementId and stel.warehouse.dealerId = :aDealerId and stel.warehouse.id = :aWarehouseId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("anElementId", elementId);
        	query.setLong("aDealerId", dealerId);
        	query.setLong("aWarehouseId", warehouseId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseElementsStockByElIdAndDealerIdAndWarehouseId/WarehouseElementStockDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStockDAOLocal#getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(java.lang.String, java.lang.String, java.lang.String)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WarehouseElemetStockResponse getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(
			String elementTypeCode, Long dealerId, String warehouseCode, Long countryId, RequestCollectionInfo requestCollInfo, Long dealerBranchId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode/WarehouseElementStockDAO ==");
        Session session = super.getSession();

        try {
        	WarehouseElemetStockResponse response = new WarehouseElemetStockResponse();
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseElementStock.class.getName());
        	stringQuery.append(" stel where 1=1 ");
        	
        	if(elementTypeCode != null && elementTypeCode.trim().length() > 0){
        		stringQuery.append("and stel.elementType.typeElementCode = :anElementTypeCode ");	
        	}
        	
        	if ( dealerBranchId!=null && dealerBranchId.longValue()!=0 ){
        		stringQuery.append("AND stel.warehouse.dealerId= :aDealerBranchId ");
        	}else{
        		if( dealerId!=null && dealerId.longValue()!= 0 ){
        			stringQuery.append("AND (stel.warehouse.dealerId= :aDealerId OR stel.warehouse.dealerId.id IN (select dea.id FROM Dealer AS dea WHERE dea.dealer.id= :aDealerId )) ");
        		}
        	}
        	
        	if(warehouseCode != null && warehouseCode.trim().length() > 0){
        		stringQuery.append("and stel.warehouse.whCode = :aWarehouseCode ");
        	}
        	
        	stringQuery.append("and stel.warehouse.country.id = :aWhCountryId ");
        	
			//Paginacion
			StringBuffer stringQueryCount = new StringBuffer("select count(*) ");
			stringQueryCount.append(stringQuery.toString());			
			Query countQuery = session.createQuery(stringQueryCount.toString());	
			
        	Query query = session.createQuery(stringQuery.toString());
        	
        	if(elementTypeCode != null && elementTypeCode.trim().length() > 0){
        		query.setString("anElementTypeCode", elementTypeCode);	
        		countQuery.setString("anElementTypeCode", elementTypeCode);	
        	}
        	
        	if ( dealerBranchId!=null && dealerBranchId.longValue()!= 0 ){
        		query.setLong("aDealerBranchId", dealerBranchId);
    			countQuery.setLong("aDealerBranchId", dealerBranchId);
        	}else{
        		if( dealerId!=null && dealerId.longValue()!= 0){
        			query.setLong("aDealerId", dealerId);
        			countQuery.setLong("aDealerId", dealerId);
        		}
        	}
        	
        	if(warehouseCode != null && warehouseCode.trim().length() > 0){
        		query.setString("aWarehouseCode", warehouseCode);
        		countQuery.setString("aWarehouseCode", warehouseCode);
        	}
        	query.setLong("aWhCountryId", countryId);
        	countQuery.setLong("aWhCountryId", countryId);
        	
        	//Paginacion
        	Long recordQty = 0L;        	
        	if( requestCollInfo != null ){	      
        		recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );						
        	}
        	
        	List<WarehouseElementStock> elementsStok = query.list();
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementsStok.size(), recordQty.intValue());
        	response.setWhElementsStock( elementsStok );        	
        	return response;            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode/WarehouseElementStockDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStockDAOLocal#getWarehouseElementStockByWareHouseId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WarehouseElementStock> getWarehouseElementStockByWareHouseId( Long warehouseId )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicia getWarehouseElementStockByWareHouseId/WarehouseElementStockDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseElementStock.class.getName());
        	stringQuery.append(" stel where stel.warehouse.id =:aWarehouseId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setParameter( "aWarehouseId" , warehouseId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseElementStockByWareHouseId/WarehouseElementStockDAO ==");
        }
    }

}
