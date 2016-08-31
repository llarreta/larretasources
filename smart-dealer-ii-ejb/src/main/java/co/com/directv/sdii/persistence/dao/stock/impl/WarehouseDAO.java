package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
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
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.WarehouseInfoDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoDetailDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Warehouse
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Warehouse
 * @see co.com.directv.sdii.model.hbm.Warehouse.hbm.xml
 */
@Stateless(name="WarehouseDAOLocal",mappedName="ejb/WarehouseDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WarehouseDAO extends BaseDao implements WarehouseDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WarehouseDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehousesDAOLocal#createWarehouse(co.com.directv.sdii.model.pojo.Warehouse)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Warehouse createWarehouse(Warehouse obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWarehouse/WarehouseDAO ==");
        Session session = super.getSession();
        try {
            obj.setId( (Long) session.save(obj)  );
            this.doFlush(session);
            return obj;
        } catch (Throwable ex) {
            log.debug("== Error creando el Warehouse ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouse/WarehouseDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehousesDAOLocal#updateWarehouse(co.com.directv.sdii.model.pojo.Warehouse)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Warehouse updateWarehouse(Warehouse obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateWarehouse/WarehouseDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
            return obj;
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Warehouse ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWarehouse/WarehouseDAO ==");
        }
    }

    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean existWhCode(String code) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio existWhCode/WarehouseDAO ==");
        Session session = super.getSession();
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(" Warehouse wh where wh.whCode = :whCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("whCode", code);
        	
            List result = query.list();
            
            int count=0;
            
            if(result!=null){
            	count=result.size();
            }

            return count!=0;
        } catch (Throwable ex) {
            log.debug("== Error consultando el WH_CODE el Warehouse ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina existWhCode/WarehouseDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehousesDAOLocal#deleteWarehouse(co.com.directv.sdii.model.pojo.Warehouse)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWarehouse(Warehouse obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteWarehouse/WarehouseDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Warehouse entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Warehouse ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWarehouse/WarehouseDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehousesDAOLocal#getWarehousesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Warehouse getWarehouseByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehouseByID/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Warehouse) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseByID/WarehouseDAO ==");
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Warehouse> getWarehousesByCrewId(Long crewId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehousesByCrewId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.crewId.id = :ancrewId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("ancrewId", crewId);
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCrewId/WarehouseDAO ==");
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Warehouse> getNoVirtualWarehousesByCrewId(Long crewId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehousesByCrewId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	Warehouse wh;
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.crewId.id = :ancrewId and entity.warehouseType.isVirtual = :isVirtual");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("ancrewId", crewId);
            query.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCrewId/WarehouseDAO ==");
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Warehouse> getWarehousesByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehousesByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where (entity.dealerId.id = :andealerId or entity.dealerId.dealer.id = :andealerId)");
        	//gfandino-01/06/2011 Se modifica consulta para que incluya estado INV104 v2.2
        	stringQuery.append(" and entity.isActive = :isActive");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("andealerId", dealerId);
            query.setString("isActive", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByDealerId/WarehouseDAO ==");
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Warehouse> getWarehousesByCustomerId(Long customerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehousesByCustomerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.customerId.id = :ancustomerId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("ancustomerId", customerId);
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCustomerId/WarehouseDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehousesDAOLocal#getAllWarehouses()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Warehouse> getAllWarehouses() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllWarehouses/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehouses/WarehouseDAO ==");
        }
    }



	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehouseByCodeAndByCountry(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Warehouse getWarehouseByCodeAndByCountry(String code,Long country)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehouseByCodeAndByCountry/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.whCode = :aCode and entity.country.id = :country");
        	//gfandino-01/06/2011 Se modifica consulta para que incluya estado INV104 v2.2
        	stringQuery.append(" and  entity.isActive = :isActive");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);
            query.setLong("country", country);
            query.setString("isActive", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
            return (Warehouse) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseByCodeAndByCountry/WarehouseDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhByCrewAndWhType(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWhByCrewAndWhType(Long crewId, Long whTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByCrewAndWhType/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.warehouseType.id = :aWhType and");
        	stringQuery.append(" entity.crewId.id = :aCrewId");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append(" and entity.warehouseType.isActive = :whIsActive");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhType", whTypeId);
            query.setLong("aCrewId", crewId);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByCrewAndWhType/WarehouseDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWhByCrewAndWhTypeCode(Long crewId, String whTypeCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByCrewAndWhType/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.warehouseType.whTypeCode = :aWhTypeCode and");
        	stringQuery.append(" entity.crewId.id = :aCrewId");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append(" and entity.isActive = :whIsActive");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aWhTypeCode", whTypeCode);
            query.setLong("aCrewId", crewId);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());

            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByCrewAndWhType/WarehouseDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByDealerIdOwnAndRelated(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWarehousesByDealerIdOwnAndRelated(Long dealerId,
			Long warehouseTypeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByDealerIdOwnAndRelated/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	boolean warehouseTypeIdSpecified = false;
        	if(warehouseTypeId != null && warehouseTypeId.longValue() > 0){
        		warehouseTypeIdSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.id in (select wh.id from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" wh where wh.dealerId.id = :aDealerId");
        	if(warehouseTypeIdSpecified){
        		stringQuery.append(" and wh.warehouseType.id = :aWhType");
        		//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        		stringQuery.append(" and wh.warehouseType.isActive = :whIsActive");
        	}
        	stringQuery.append("  ) or entity.id in (select distinct whe.warehouseId.id from ");
        	stringQuery.append(WarehouseElement.class.getName());
        	stringQuery.append(" whe where whe.warehouseId.dealerId.id = :aDealerId");
        	
        	if(warehouseTypeIdSpecified){
        		stringQuery.append(" and whe.warehouseId.warehouseType.id =:aWhType ");
        		//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        		stringQuery.append(" and whe.warehouseId.warehouseType.isActive =:whIsActive2 ");
        	}
        	
        	stringQuery.append(" )");
        	Query query = session.createQuery(stringQuery.toString());
            
        	if(warehouseTypeIdSpecified){
        		query.setLong("aWhType", warehouseTypeId);
        		query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        		query.setString("whIsActive2", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	}
        	
            query.setLong("aDealerId", dealerId);

            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByDealerIdOwnAndRelated/WarehouseDAO ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByComplexFilter(co.com.directv.sdii.model.pojo.Warehouse, co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.Crew, co.com.directv.sdii.model.pojo.WarehouseType, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseResponse getWarehousesByComplexFilter( Warehouse wareHouseId,Dealer companyId,Dealer branchId,Crew crewId,
			WarehouseType wareHouseTypeId, RequestCollectionInfo requestCollInfo )throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getWarehousesByComplexFilter/WarehouseDAO =====");
        Session session           = super.getSession();
        
        try {
        	
        	StringBuffer stringCount = new StringBuffer(); 
        	StringBuffer buffer = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	//Paginacion
        	stringCount.append("select count(*) from ");
        	//Cuerpo de la consulta
        	stringQuery.append("select distinct whouse from ");
        	buffer.append( Warehouse.class.getName());
        	buffer.append( " as whouse where 1=1 " );
        	buffer.append( " and whouse.warehouseType.isVirtual = :isVirtual " );
        	if( wareHouseId!=null && wareHouseId.getId()!=null && !wareHouseId.getId().equals(0L) )
        	 buffer.append( " and whouse.id = :wareHouseId " );
        	
        	if( crewId!=null && crewId.getId()!=null && !crewId.getId().equals(0L) ){
        		buffer.append( " and whouse.crewId.id = :crewId " );
        	} else {
        		if( branchId!=null && branchId.getId()!=null && !branchId.getId().equals(0L) ){
        			buffer.append( " and whouse.dealerId.id = :branchId " );
        			
        			if(companyId!=null && companyId.getId()!=null && !companyId.getId().equals(0L)) {
        			
	        			if( !branchId.equals(companyId) ) {
	        				buffer.append( " and whouse.dealerId.dealer.id = :companyId " );
	        			}else {
	            			buffer.append( " and whouse.dealerId.id = :companyId " );
	            		}
        			
        			}
        			
        		}else if( companyId!=null && companyId.getId()!=null && !companyId.getId().equals(0L) ) {
        			buffer.append( " and (whouse.dealerId.id = :companyId or whouse.dealerId.dealer.id = :companyId) " );
        		}
        	}
        	
        	if( wareHouseTypeId!=null && wareHouseTypeId.getId()!=null && !wareHouseTypeId.getId().equals(0L) ){
	        	 buffer.append( " and whouse.warehouseType.id = :wareHouseTypeId " );
	        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
	        	 buffer.append( " and whouse.warehouseType.isActive = :whIsActive " );
        	}
        	
        	stringQuery.append( buffer.toString() );
        	
        	//Paginacion
        	stringCount.append( buffer.toString() ); 
        	Query countQuery = session.createQuery( stringCount.toString() );
        	Query query = session.createQuery( stringQuery.toString() );
        	query.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
        	countQuery.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
        	if( wareHouseId!=null && wareHouseId.getId()!=null && !wareHouseId.getId().equals(0L) ){
        	     query.setParameter( "wareHouseId" , wareHouseId.getId());	
        	     countQuery.setParameter( "wareHouseId" , wareHouseId.getId());	
        	}
        	
        	if( crewId!=null && crewId.getId()!=null && !crewId.getId().equals(0L) ){
        		 query.setParameter( "crewId" , crewId.getId());
        		 countQuery.setParameter( "crewId" , crewId.getId());
        	} else {
        		if( branchId!=null && branchId.getId()!=null && !branchId.getId().equals(0L) ){
        			query.setParameter( "branchId" , branchId.getId());
        			countQuery.setParameter( "branchId" , branchId.getId());
        			if( companyId!=null && companyId.getId()!=null && !companyId.getId().equals(0L) ){
        				query.setParameter( "companyId" , companyId.getId());
        				countQuery.setParameter( "companyId" , companyId.getId());
        			}
        		}else if( companyId!=null && companyId.getId()!=null && !companyId.getId().equals(0L) ){
          	         query.setParameter( "companyId" , companyId.getId());	 
          	         countQuery.setParameter( "companyId" , companyId.getId());	
        		}           	
        	}
        	
        	if( wareHouseTypeId!=null && wareHouseTypeId.getId()!=null && !wareHouseTypeId.getId().equals(0L) ){
      	         query.setParameter( "wareHouseTypeId" , wareHouseTypeId.getId());
      	         countQuery.setParameter( "wareHouseTypeId" , wareHouseTypeId.getId());
      	         query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
      	       countQuery.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){            	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseResponse response = new WareHouseResponse();
        	List<Warehouse> warehouse = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouse.size(), recordQty.intValue() );
        	response.setWareHouse( warehouse );
        	
        	return response;
        } catch (Throwable ex){
        	 log.error("== Error en la operacion getWarehousesByComplexFilter/WarehouseDAO =="+ ex);
			throw this.manageException(ex);
		} finally {
			 log.debug("== Termina getWarehousesByComplexFilter/WarehouseDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByAdjustNotSerElemCriteria(java.lang.Long, java.lang.Long,  java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(
			Long wareHouseId, Long dealerId, Long branchId, Long crewId,
			Long wareHouseTypeId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByAdjustNotSerElemCriteria/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	
        	//paginacion
        	stringCount.append("select count(*) ");
        	//cuerpo de la consulta
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.isActive = :isActive ");
        	stringQuery.append(" and entity.warehouseType.isVirtual = :isVirtual " );
        	if(wareHouseId != null && !wareHouseId.equals(0L)){
        		stringQuery.append("and entity.id = :aWareHouseId ");        		
        	}
        	if(dealerId != null && !dealerId.equals(0L)){
        		stringQuery.append("and (entity.dealerId.id = :aDealerId or entity.dealerId.dealer.id = :aDealerId )  ");
        	}
        	if(branchId != null && !branchId.equals(0L)){
        		stringQuery.append("and entity.dealerId.id = :aBranchId ");
        	}
        	if(crewId != null && !crewId.equals(0L)){
        		stringQuery.append("and entity.crewId.id = :aCrewId ");
        	}
        	if(wareHouseTypeId != null && !wareHouseTypeId.equals(0L)){
        		stringQuery.append("and entity.warehouseType.id = :aWareHouseTypeId ");
        		//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        		stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	} else {
        		stringQuery.append("and entity.warehouseType.whTypeCode <> :adjustWhCode ");
        		//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        		stringQuery.append("and entity.warehouseType.isActive = :whIsActive2 ");
        	}
        	
        	//Paginacion
        	stringCount.append(stringQuery.toString());        	
        	Query countQuery = session.createQuery(stringCount.toString());
        	//Cuerpo de la consulta
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isActive", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
        	countQuery.setString("isActive", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
        	
        	query.setString("isVirtual" , CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity() );
        	countQuery.setString("isVirtual" , CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity() );
        	
        	if(wareHouseId != null && !wareHouseId.equals(0L)){
        		query.setLong("aWareHouseId", wareHouseId);
        		countQuery.setLong("aWareHouseId", wareHouseId);
        	}
        	if(dealerId != null && !dealerId.equals(0L)){
        		query.setLong("aDealerId", dealerId);
        		countQuery.setLong("aDealerId", dealerId);
        	}
        	if(branchId != null && !branchId.equals(0L)){
        		query.setLong("aBranchId", branchId);
        		countQuery.setLong("aBranchId", branchId);
        	}
        	if(crewId != null && !crewId.equals(0L)){
        		query.setLong("aCrewId", crewId);
        		countQuery.setLong("aCrewId", crewId);
        	}
        	if(wareHouseTypeId != null && !wareHouseTypeId.equals(0L)){
        		query.setLong("aWareHouseTypeId", wareHouseTypeId);
        		countQuery.setLong("aWareHouseTypeId", wareHouseTypeId);
        		query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        		countQuery.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	} else {
        		query.setString("adjustWhCode", CodesBusinessEntityEnum.WAREHOUSE_TYPE_AJUSTE.getCodeEntity());
        		countQuery.setString("adjustWhCode", CodesBusinessEntityEnum.WAREHOUSE_TYPE_AJUSTE.getCodeEntity());
        		
        		query.setString("whIsActive2", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        		countQuery.setString("whIsActive2", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	}
        	//Paginacion
        	WareHouseResponse response = new WareHouseResponse();
        	List<Warehouse> wareHouse;
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	   
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
			
			wareHouse = query.list();	
			if( requestCollInfo != null )
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), wareHouse.size(), recordQty.intValue());
        	response.setWareHouse(wareHouse);      
            return  response;

        } catch (Throwable ex){
			log.error("== Error getWarehousesByAdjustNotSerElemCriteria/WarehouseDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByAdjustNotSerElemCriteria/WarehouseDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByDealerIdAndCrewId(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Warehouse> getWarehousesByDealerIdAndCrewId(Long dealerId, Long crewId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehousesByDealerIdAndCrewId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.crewId.dealer.id = :dealerId");
        	stringQuery.append(" and  entity.crewId.id = :crewId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setLong("crewId", crewId);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error en getWarehousesByDealerIdAndCrewId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByDealerIdAndCrewId/WarehouseDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseResponse getWarehousesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByCountryId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	//Paginacion
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery1 = new StringBuffer();
        	StringBuffer stringQuery2 = new StringBuffer();
        	StringBuffer stringQuery3 = new StringBuffer();
        	
        	stringCount.append("select count(*) ");
        	

        	stringQuery1.append("from ");
        	stringQuery1.append(Warehouse.class.getName()+" entity ");
        	stringQuery2.append(" inner join fetch entity.dealerId del ");
        	stringQuery2.append(" inner join fetch del.dealerType delType ");
        	stringQuery2.append(" inner join fetch del.postalCode pc ");
        	stringQuery2.append(" inner join fetch pc.city city ");
        	stringQuery2.append(" inner join fetch city.state state ");
        	stringQuery2.append(" inner join fetch entity.warehouseType warehouseType ");
        	stringQuery2.append(" left join fetch entity.crewId crew ");
        	stringQuery2.append(" left join fetch entity.crewId.vehicle veh ");
        	stringQuery2.append(" left join fetch del.dealer del2 ");
        	stringQuery2.append(" left join fetch del2.postalCode pc2 ");
        	stringQuery2.append(" left join fetch pc2.city city2 ");
        	stringQuery2.append(" left join fetch city2.state state2 ");
        	stringQuery3.append(" where entity.country.id = :aCountryId ");
        	stringQuery3.append(" and entity.customerId is null ");
        	stringQuery3.append(" order by entity.whCode ");
        	    
        	Query query = session.createQuery(stringQuery1.toString()+stringQuery2.toString()+stringQuery3.toString());  
        	query.setLong("aCountryId", countryId);

        	
            //Paginacion
            Long recordQty = 0L;
        	if( requestCollInfo != null ){	   
        		stringCount.append( stringQuery1.toString() +stringQuery3.toString());
        		Query countQuery = session.createQuery( stringCount.toString());
        		countQuery.setLong( "aCountryId", countryId );
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}
        	
        	WareHouseResponse response = new WareHouseResponse();
        	List<Warehouse> wareHouse = query.list();
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), wareHouse.size(), recordQty.intValue());
        	response.setWareHouse(wareHouse);      

            return response;
        } catch (Throwable ex){
			log.error("== Error en la operacion getWarehousesByCountryId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCountryId/WarehouseDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByCountryIdAndDealerCondition(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseInfoResponseDTO getWarehousesByCountryIdAndDealerCondition(Long countryId, RequestCollectionInfo requestCollInfo,
			String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByCountryIdAndDealerCondition/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	//Paginacion
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery1 = new StringBuffer();
        	StringBuffer stringQuery2 = new StringBuffer();
        	
        	stringCount.append("select count(*) ");
        	
        	stringQuery1.append(" SELECT WA.WH_CODE, DE.DEPOT_CODE, DE.DEALER_NAME, EMP.FIRST_NAME FNE, EMP.LAST_NAME LNE, WART.WH_TYPE_NAME ");
        	stringQuery2.append("   FROM WAREHOUSES WA ");
        	stringQuery2.append(" INNER JOIN DEALERS              DE     on DE.ID = WA.DEALER_ID       ");
        	stringQuery2.append(" INNER JOIN WAREHOUSE_TYPES      WART   on WART.ID = WA.WH_TYPE_ID     ");
        	stringQuery2.append("  LEFT JOIN CREWS                CREW   on CREW.ID = WA.CREW_ID "); 
        	stringQuery2.append("  LEFT JOIN EMPLOYEE_CREWS       EC     on (CREW.ID=EC.CREW_ID AND EC.IS_RESPONSIBLE = :aIsResponsible) ");
        	stringQuery2.append("  LEFT JOIN EMPLOYEES            EMP    on (EC.EMPLOYEE_ID=EMP.ID)     ");
        	stringQuery2.append(" WHERE WA.DEALER_ID = DE.ID ");
        	stringQuery2.append("   AND WA.COUNTRY_ID = :aCountryId ");
       		stringQuery2.append("   AND NVL(WA.CUSTOMER_ID,0) = 0 ");

       		if(isInstaller!=null)
           		stringQuery2.append("   AND DE.IS_INSTALLER = :aIsInstaller ");
        	if(isSeller!=null)
           		stringQuery2.append("   AND DE.IS_SELLER = :aIsSeller ");
        	stringQuery2.append(" ORDER BY WA.WH_CODE ");
        	        	    
        	Query query = session.createSQLQuery(stringQuery1.toString()+stringQuery2.toString());
        	query.setString("aIsResponsible",CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
        	query.setLong("aCountryId", countryId);      
        	if(isSeller!=null)
        		query.setString("aIsSeller", isSeller);        		
            if(isInstaller!=null)
        		query.setString("aIsInstaller", isInstaller);        		
        	
        	//Paginacion
        	BigDecimal recordQty = new BigDecimal(0);
        	if( requestCollInfo != null ){	   
        		Query countQuery = session.createSQLQuery( stringCount.toString()+stringQuery2.toString() );
            	countQuery.setString("aIsResponsible",CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
        		countQuery.setLong( "aCountryId", countryId );
        		if(isSeller!=null)
        			countQuery.setString("aIsSeller", isSeller);
	        	if(isInstaller!=null)
        			countQuery.setString("aIsInstaller", isInstaller);
	        	recordQty = (BigDecimal)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}
        	
        	WarehouseInfoResponseDTO response = new WarehouseInfoResponseDTO();

        	List<Object[]> warehousesLocationDetails = query.list();
        	for(Object[] resp : warehousesLocationDetails){
        		String warehouseCode = (String)resp[0];
        		String warehouseName= " ";
        		if(resp[1]!=null) { /*datos del dealers*/
        			String dealerCode = (String)resp[1];
        			String dealerName = (String)resp[2];
        			warehouseName = dealerCode+" - "+dealerName+" - ";
        		}
        		if(resp[3]!=null) /*employeeName*/
        			warehouseName=warehouseName.concat((String)resp[3]+" "+(String)resp[4]+" - ");
        		String whTypeName = (String)resp[5];
        		warehouseName=warehouseName.concat(whTypeName);
        		WarehouseInfoDTO warehouseInfo = new WarehouseInfoDTO();
        		warehouseInfo.setWarehouseCode(warehouseCode);
        		warehouseInfo.setWarehouseName(warehouseName);
        		response.getWarehouseInfoDTOList().add(warehouseInfo);
        	}
        		
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), response.getWarehouseInfoDTOList().size(), recordQty.intValue());

            return response;
        } catch (Throwable ex){
			log.error("== Error en la operacion getWarehousesByCountryIdAndDealerCondition/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCountryIdAndDealerCondition/WarehouseDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getAllWarehousesByCountryId(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros
	 */
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseInfoResponseDetailDTO getAllWarehousesByCountryId(Long countryId, 
																	  String code,
																	  RequestCollectionInfo requestCollInfo
	) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicio getAllWarehousesByCountryId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	//Paginacion
        	StringBuffer stringCount  = new StringBuffer();
        	StringBuffer stringQuery  = new StringBuffer();
        	StringBuffer stringQuery1 = new StringBuffer();
        	
        	stringCount.append("select count(*) ");
               	
        	//stringQuery1.append(" SELECT WA.WH_CODE, DE.DEPOT_CODE, DE.DEALER_NAME, EMP.FIRST_NAME FNE, EMP.LAST_NAME LNE, WART.WH_TYPE_NAME ");
        	//stringQuery1.append("      , DE.DEPOT_CODE, DE2.DEALER_NAME, DE2.DEALER_CODE, WA.WH_RESPONSIBLE, WA.RESPONSIBLE_EMAIL, WA.WH_ADDRESS, WA.IS_ACTIVE, WA.ID, DE.DEALER_CODE ");
        	stringQuery1.append(" SELECT ");       
			stringQuery1.append(" WA.WH_CODE			WA_WH_CODE			  , "); // -- 0 warehouseCode
			stringQuery1.append(" DE.DEPOT_CODE			DE_DEPOT_CODE		  , "); // -- 1 dealerCode -- parte de warehouseName
			stringQuery1.append(" DE.DEALER_NAME		DE_DEALER_NAME		  , "); // -- 2 dealerName -- parte de warehouseName
			stringQuery1.append(" EMP.FIRST_NAME 		EMP_FNE				  , "); // -- 3 -- parte de warehouseName -- parte de CrewName
			stringQuery1.append(" EMP.LAST_NAME 		EMP_LNE				  , "); // -- 4 -- parte de warehouseName -- parte de CrewName
			stringQuery1.append(" WART.WH_TYPE_NAME 	WART_WH_TYPE_NAME	  , "); // -- 5 whTypeName
			                    //DE.DEPOT_CODE			DE_DEPOT_CODE		  , "); // -- 6 repetido DepotCode dealer principal
			stringQuery1.append(" DE2.DEPOT_CODE		DE2_DEPOT_CODE		  , "); // -- 6 DepotCode sucursal
			stringQuery1.append(" DE2.DEALER_NAME 		DE2_DEALER_NAME		  , "); // -- 7
			stringQuery1.append(" DE2.DEALER_CODE 		DE2_DEALER_CODE		  , "); // -- 8
			stringQuery1.append(" WA.WH_RESPONSIBLE 	WA_WH_RESPONSIBLE	  , "); // -- 9
			stringQuery1.append(" WA.RESPONSIBLE_EMAIL 	WA_RESPONSIBLE_EMAIL  , "); // -- 10
			stringQuery1.append(" WA.WH_ADDRESS  		WA_WH_ADDRESS		  , "); // -- 11
			stringQuery1.append(" WA.IS_ACTIVE 			WA_IS_ACTIVE		  , "); // -- 12
			stringQuery1.append(" WA.ID 				WA_ID				  , "); // -- 13
			stringQuery1.append(" DE.DEALER_CODE 		DE_DEALER_CODE          "); // -- 14
        	
        	stringQuery.append("  FROM WAREHOUSES WA ");
          //stringQuery.append("  LEFT  JOIN DEALERS              DE     on DE.ID = WA.DEALER_ID ");
        	stringQuery.append("  INNER JOIN DEALERS              DE     on DE.ID = WA.DEALER_ID ");
        	stringQuery.append("  LEFT  JOIN DEALERS              DE2    on DE.DEALER_BRANCH_ID = DE2.ID ");
        	stringQuery.append("  LEFT  JOIN WAREHOUSE_TYPES      WART   on WART.ID = WA.WH_TYPE_ID ");
        	stringQuery.append("  LEFT  JOIN CREWS                CREW   on CREW.ID = WA.CREW_ID "); 
        	stringQuery.append("  LEFT  JOIN EMPLOYEE_CREWS       EC     on (CREW.ID=EC.CREW_ID AND EC.IS_RESPONSIBLE = :aIsResponsible) ");
        	stringQuery.append("  LEFT  JOIN EMPLOYEES            EMP    on (EC.EMPLOYEE_ID=EMP.ID) ");
          //stringQuery.append("  WHERE WA.DEALER_ID = DE.ID "); // ya se incluye en el left join
        	stringQuery.append("  WHERE WA.COUNTRY_ID = :aCountryId ");
        	stringQuery.append("  AND NVL(WA.CUSTOMER_ID,0) = 0 ");
     		if(code !=null && !code.isEmpty()){
        		stringQuery.append(" AND WA.WH_CODE = :whCode ");
        	}
     		stringQuery.append(" AND DE.IS_INSTALLER= :IsInstaller "); //Solo retornará dealers instaladores
        	stringQuery.append(" ORDER BY WA.WH_CODE ");
     	
        	//stringQuery.append("from ");
        	//stringQuery.append(Warehouse.class.getName());
        	//stringQuery.append(" entity where entity.country.id = :aCountryId ");
        	//stringQuery.append(" and entity.customerId is null ");
        	//if(code !=null){
        		//stringQuery.append(" and entity.whCode = :whCode ");
        	//}
        	//stringQuery.append(" order by entity.whCode");
        	       
        	Query query = session.createSQLQuery(stringQuery1.toString()+stringQuery.toString());  
        	query.setString("aIsResponsible",CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
        	query.setLong  ("aCountryId", countryId);
     		if(code !=null && !code.isEmpty()){
        		query.setString("whCode", code);
        	}
     		query.setString("IsInstaller",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()); 
        	
            //Paginacion
            BigDecimal recordQty = new BigDecimal(0);
            
        	if( requestCollInfo != null ){	   
        		
        		stringCount.append( stringQuery.toString() );
        		Query countQuery = session.createSQLQuery( stringCount.toString() );
        		countQuery.setString("aIsResponsible",CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
        		countQuery.setLong  ( "aCountryId"   , countryId );        		
        		if(code !=null && !code.isEmpty()){
        			countQuery.setString("whCode", code);
            	}
        		countQuery.setString("IsInstaller",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()); 
	        	
        		recordQty = (BigDecimal)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}

        	WarehouseInfoResponseDetailDTO response = new WarehouseInfoResponseDetailDTO();

        	List<Object[]> warehousesLocationDetails = query.list();
        	
        	for(Object[] resp : warehousesLocationDetails){
        		
        		String warehouseCode = (String)resp[0];
        		String warehouseName= " ";
        		WarehouseInfoDetailDTO warehouseInfo = new WarehouseInfoDetailDTO();
        		
        		if(resp[1]!=null) { /*datos del dealers*/
        			String dealerCode = (String)resp[1];
        			String dealerName = (String)resp[2];
        			warehouseName = dealerCode+" - "+dealerName+" - ";
        			warehouseInfo.setDealerName(dealerName);
        		}
        		
        		if(resp[3]!=null) { /*employeeName*/
        			warehouseName=warehouseName.concat((String)resp[3]+" "+(String)resp[4]+" - ");
        			warehouseInfo.setCrewName((String)resp[3]+" "+(String)resp[4]);
        		}
        		
        		String whTypeName = (String)resp[5];
        		warehouseName=warehouseName.concat(whTypeName);
        		warehouseInfo.setWarehouseCode(warehouseCode);
        		warehouseInfo.setWarehouseName(warehouseName);
        		warehouseInfo.setWarehouseType(whTypeName);
        		
        		if(resp[6]!=null)
        		  //warehouseInfo.setDepotCode((String)resp[6]); // si es el depot_code del dealer principal es el campo 1
        			                                             // si es el depot_code de  sucursal es el campo 6
        			warehouseInfo.setDepotCode((String)resp[1]); // si es el depot_code del dealer principal es el campo 1
        		
        		if(resp[7]!=null)
        			warehouseInfo.setBranchDealerName((String)resp[7]);
        		
        		if(resp[8]!=null)
        			warehouseInfo.setBranchDealerCode(((BigDecimal)resp[8]).toString());
        		
        		if(resp[9]!=null)
        			warehouseInfo.setResponsible((String)resp[9]);
        		
        		if(resp[10]!=null)
        			warehouseInfo.setResponsibleEmail((String)resp[10]);
        		
        		if(resp[11]!=null)
        			warehouseInfo.setWhAddress((String)resp[11]);
        		
        		if(resp[12]!=null)
        			warehouseInfo.setIsActive((String)resp[12]);
        		
        		if(resp[13]!=null)
        			warehouseInfo.setId(((BigDecimal)resp[13]).longValue());
           		
        		if(resp[14]!=null)
        			warehouseInfo.setPrincipalDealerCode(((BigDecimal)resp[14]).longValue());
        		
        		response.getWarehouseInfoDetailDTOList().add(warehouseInfo);
        	}
        	
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), response.getWarehouseInfoDetailDTOList().size(), recordQty.intValue());

            return response;
        } catch (Throwable ex){
			log.error("== Error en la operacion getAllWarehousesByCountryId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehousesByCountryId/WarehouseDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhByWhTypeDealerBranchCrewIds(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros
	 */
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseInfoResponseDetailDTO getWhByWhTypeDealerBranchCrewIds(  Long countryId, 
																				 Long warehouseTypeId, 
																				 Long dealerId, 
																				 Long branchId,
																				 Long crewId , 
																				 RequestCollectionInfo requestCollInfo
	) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicio getWhByWhTypeDealerBranchCrewIds/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	//Paginacion
        	StringBuffer stringCount  = new StringBuffer();
        	StringBuffer stringQuery  = new StringBuffer();
        	StringBuffer stringQuery1 = new StringBuffer();
        	
        	stringCount.append("select count(*) ");
        	stringQuery1.append(" SELECT ");       
			stringQuery1.append(" WA.WH_CODE			WA_WH_CODE			  , "); 
			//stringQuery1.append(" DECODE(de2.depot_code,null,DE.depot_code,dE2.depot_code) DE_DEPOT_CODE, "); 
			//stringQuery1.append(" DECODE(dE2.dealer_name,null,DE.dealer_name,dE2.dealer_name) DE_DEALER_NAME, "); 	
			stringQuery1.append(" DECODE(de.depot_code,null,DE2.depot_code,dE.depot_code) DE_DEPOT_CODE, "); 
			stringQuery1.append(" DECODE(de.dealer_name,null,de2.dealer_name,de.dealer_name) DE_DEALER_NAME, "); 
			stringQuery1.append(" EMP.FIRST_NAME 		EMP_FNE				  , "); 
			stringQuery1.append(" EMP.LAST_NAME 		EMP_LNE				  , "); 
			stringQuery1.append(" WART.WH_TYPE_NAME 	WART_WH_TYPE_NAME	  , "); 			
			stringQuery1.append(" DECODE(de2.depot_code,null,null,dE.depot_code) DE2_DEPOT_CODE, ");
			//stringQuery1.append("DECODE(de2.dealer_name,null,de.dealer_name,de2.dealer_name) DE2_DEALER_NAME,  ");
			stringQuery1.append("decode(de2.dealer_name,null,null,de.dealer_name)  DE2_DEALER_NAME,  ");
			stringQuery1.append(" DECODE(de2.dealer_code,null,null,de.dealer_code) DE2_DEALER_CODE, ");					
			stringQuery1.append(" WA.WH_RESPONSIBLE 	WA_WH_RESPONSIBLE	  , "); 
			stringQuery1.append(" WA.RESPONSIBLE_EMAIL 	WA_RESPONSIBLE_EMAIL  , "); 
			stringQuery1.append(" WA.WH_ADDRESS  		WA_WH_ADDRESS		  , "); 
			stringQuery1.append(" WA.IS_ACTIVE 			WA_IS_ACTIVE		  , "); 
			stringQuery1.append(" WA.ID 				WA_ID				  ,"); 
			stringQuery1.append(" DECODE(dE.dealer_code,null,DE2.dealer_code,dE.dealer_code) DE_DEALER_CODE , ");			
			stringQuery1.append(" DECODE(dE2.dealer_name,null,de.dealer_name,de2.dealer_name)  BRANCH				  "); 
			stringQuery.append("  FROM WAREHOUSES WA ");
        	stringQuery.append("  INNER JOIN DEALERS              DE     on DE.ID = WA.DEALER_ID ");        	
            stringQuery.append("  LEFT  JOIN DEALERS              DE2    on DE.DEALER_BRANCH_ID  = DE2.ID "); 
        	stringQuery.append("  LEFT  JOIN WAREHOUSE_TYPES      WART   on WART.ID = WA.WH_TYPE_ID ");
        	stringQuery.append("  LEFT  JOIN CREWS                CREW   on CREW.ID = WA.CREW_ID "); 
        	stringQuery.append("  LEFT  JOIN EMPLOYEE_CREWS       EC     on (CREW.ID=EC.CREW_ID AND EC.IS_RESPONSIBLE = :aIsResponsible) ");
        	stringQuery.append("  LEFT  JOIN EMPLOYEES            EMP    on (EC.EMPLOYEE_ID=EMP.ID) ");
        	stringQuery.append("  WHERE WA.COUNTRY_ID = :aCountryId ");
        	stringQuery.append("  AND NVL(WA.CUSTOMER_ID,0) = 0 ");
     		stringQuery.append("  AND DE.IS_INSTALLER= :IsInstaller "); //Solo retornará dealers instaladores
        	
        	
        	if(warehouseTypeId !=null){            	 
         		stringQuery.append(" AND WART.ID = :whTypeId ");
        	}	
        	
        	
        	 
        	 if((dealerId !=null)&&(branchId == null)){ // consulta solo compania ppal
        		 //stringQuery.append(" AND DE.ID = :deId ");
        		// stringQuery.append(" AND ((DE.ID   = 321) or (de.id in (select d3.id from dealers d3 where d3.dealer_branch_id = 321))) ");
        		   stringQuery.append(" AND ((DE.ID   = :deId) or (de.id in (select d3.id from dealers d3 where d3.dealer_branch_id = :deId))) ");

        	 } else if( (dealerId !=null)&&(branchId != null)){
        		 if(dealerId.equals(branchId)){ 
        			 stringQuery.append(" AND DE.ID   = :deId "); //consulta principal ysucursal del mismo dealer
        		 }
        		 else
        		 {
        			 stringQuery.append(" AND ((DE.ID   = :braId) and (de.dealer_branch_id = :deId)) ");	//consulta por compania ppal y sucursal
        		 }
        	 }
           		
           		      	        	
        	if(crewId !=null){            	 
          		stringQuery.append(" AND CREW.ID = :creId ");
         	}	        	
        	       
        	stringQuery.append("  ORDER BY WA.WH_CODE ");
        	
        	Query query = session.createSQLQuery(stringQuery1.toString()+stringQuery.toString());  
        	query.setString("aIsResponsible",CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
        	query.setLong  ("aCountryId", countryId);
     		query.setString("IsInstaller",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()); 
     		
     		if(warehouseTypeId !=null){            	 
     			query.setLong ("whTypeId", warehouseTypeId);
         	 }	
     		
     		
			if ((dealerId != null) && (branchId == null)) {
				query.setLong("deId", dealerId);
			} else if ((dealerId != null) && (branchId != null)) {
				if (dealerId.equals(branchId)) {
					query.setLong("deId", dealerId);
				} else {
					query.setLong("braId", branchId);// consulta por compania ppal y sucursal

					query.setLong("deId", dealerId);
				}
			}
     		     		 
     		 
     		 if(crewId !=null){            	            
         		query.setLong ("creId", crewId);
        	 }     		 
         		
        	
            //Paginacion
            BigDecimal recordQty = new BigDecimal(0);            
        	if( requestCollInfo != null ){	   
        		
        		stringCount.append( stringQuery.toString() );
        		Query countQuery = session.createSQLQuery( stringCount.toString() );
        		countQuery.setString("aIsResponsible",CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
        		countQuery.setLong  ("aCountryId", countryId );        		
        		countQuery.setString("IsInstaller",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()); 
         		if(warehouseTypeId !=null){            	 
         			countQuery.setLong ("whTypeId", warehouseTypeId);
             	 }	     		
         		             	            

				if ((dealerId != null) && (branchId == null)) {
					countQuery.setLong("deId", dealerId);
				} else if ((dealerId != null) && (branchId != null)) {
					if (dealerId.equals(branchId)) {
						countQuery.setLong("deId", dealerId);
					} else {
						countQuery.setLong("braId", branchId);// consulta por compania ppal y sucursal
						countQuery.setLong("deId", dealerId);
					}
				}
        		 
         		 if(crewId !=null){            	            
         			countQuery.setLong ("creId", crewId);
            	 }          		
	        	
        		recordQty = (BigDecimal)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}

        	WarehouseInfoResponseDetailDTO response = new WarehouseInfoResponseDetailDTO();

        	List<Object[]> warehousesLocationDetails = query.list();
        	
        	for(Object[] resp : warehousesLocationDetails){
        		
        		String warehouseCode = (String)resp[0];
        		String warehouseName= " ";
        		WarehouseInfoDetailDTO warehouseInfo = new WarehouseInfoDetailDTO();
        		
        		if(resp[1]!=null) { /*datos del dealers*/         		
        			String dealerCode = (String)resp[1];
        			String dealerName = (String)resp[2];
        			warehouseName = dealerCode+" - "+dealerName+" - ";
        			//warehouseInfo.setDealerName(dealerName);
        		}
        		if(resp[2]!=null){
        			warehouseInfo.setDealerName((String)resp[15]);
        		}
        		
        		if(resp[3]!=null) { /*employeeName*/
        			warehouseName=warehouseName.concat((String)resp[3]+" "+(String)resp[4]+" - ");
        			warehouseInfo.setCrewName((String)resp[3]+" "+(String)resp[4]);
        		}
        		
        		String whTypeName = (String)resp[5];
        		warehouseName=warehouseName.concat(whTypeName);
        		warehouseInfo.setWarehouseCode(warehouseCode);
        		warehouseInfo.setWarehouseName(warehouseName);
        		warehouseInfo.setWarehouseType(whTypeName);
        		
        		if(resp[6]!=null)
          		  warehouseInfo.setDepotCode((String)resp[6]); // si es el depot_code del dealer principal es el campo 1
                                                               // si es el depot_code de  sucursal es el campo 6
        		//warehouseInfo.setDepotCode((String)resp[1]); 
        		
        		if(resp[7]!=null)
        			warehouseInfo.setBranchDealerName((String)resp[7]);
        		
        		if(resp[8]!=null)
        			warehouseInfo.setBranchDealerCode((String)resp[8]);
        		
        		if(resp[9]!=null)
        			warehouseInfo.setResponsible((String)resp[9]);
        		
        		if(resp[10]!=null)
        			warehouseInfo.setResponsibleEmail((String)resp[10]);
        		
        		if(resp[11]!=null)
        			warehouseInfo.setWhAddress((String)resp[11]);
        		
        		if(resp[12]!=null)
        			warehouseInfo.setIsActive((String)resp[12]);
        		
        		if(resp[13]!=null)
        			warehouseInfo.setId(((BigDecimal)resp[13]).longValue());
           		
        		if(resp[14]!=null)
        			warehouseInfo.setPrincipalDealerCode(((BigDecimal)resp[14]).longValue());
        		
        		response.getWarehouseInfoDetailDTOList().add(warehouseInfo);
        	}
        	
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), response.getWarehouseInfoDetailDTOList().size(), recordQty.intValue());

            return response;
        } catch (Throwable ex){
			log.error("== Error en la operacion getAllWarehousesByCountryId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehousesByCountryId/WarehouseDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getAdjustWarehouseByDealerId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Warehouse getAdjustWarehouseByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getAdjustWarehouseByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("(entity.dealerId.id = :dealerId or entity.crewId.id = :dealerId) ");
        	stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append(" and entity.warehouseType.isActive = :whIsActive");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setString("whTypeCode", CodesBusinessEntityEnum.WAREHOUSE_TYPE_AJUSTE.getCodeEntity());
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            return (Warehouse) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getAdjustWarehouseByDealerId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustWarehouseByDealerId/WarehouseDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByDealerIdAndWhTypeCode(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Warehouse getWarehousesByDealerIdAndWhTypeCode(Long dealerId,
			String whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAdjustWarehouseByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.crewId is null ");
        	stringQuery.append("and entity.customerId is null ");
        	stringQuery.append("and entity.dealerId.id = :dealerId ");
        	stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode ");
        	stringQuery.append("and entity.isActive = :whIsActive ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setString("whTypeCode", whTypeCode);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
            return (Warehouse) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getAdjustWarehouseByDealerId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustWarehouseByDealerId/WarehouseDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByDealerCodeAndWhTypeCode(java.lang.Long, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWarehousesByDealerCodeAndWhTypeCode(Long dealerCode,
			String whTypeCode)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAdjustWarehouseByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.dealerId.dealerCode = :dealerCode ");
        	stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append(" and entity.isActive = :whIsActive");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerCode", dealerCode);
            query.setString("whTypeCode", whTypeCode);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());

            return query.list();
        } catch (Throwable ex){
			log.error("== Error getAdjustWarehouseByDealerId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustWarehouseByDealerId/WarehouseDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByDealerIdAndWhTypeCodeWithBranch(java.lang.Long, java.lang.String, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWarehousesByDealerIdAndWhTypeCodeWithBranch(Long dealerId,
			                                                              String whTypeCode,
			                                                              boolean withBranch) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAdjustWarehouseByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" ( entity.dealerId.id = :dealerId ");
        	if(withBranch){
        		stringQuery.append(" or entity.dealerId.dealer.id = :dealerId ");
        	}
        		
        	stringQuery.append(" ) ");
        	stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode");
        	stringQuery.append(" and entity.warehouseType.isActive = :whIsActive");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setString("whTypeCode", whTypeCode);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            return query.list();
        } catch (Throwable ex){
			log.error("== Error getAdjustWarehouseByDealerId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustWarehouseByDealerId/WarehouseDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhByDealerTypeAndCountry(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWhByDealerTypeAndCountry(String dealerType,
			Long country) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAdjustWarehouseByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.dealerId.dealerType.dealerTypeCode = :dealerType ");
        	stringQuery.append("and entity.dealerId.postalCode.city.state.country.id = :country");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("dealerType", dealerType);
            query.setLong("country", country);

            return query.list();
        } catch (Throwable ex){
			log.error("== Error getAdjustWarehouseByDealerId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustWarehouseByDealerId/WarehouseDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhByDealerTypeAndWhTypeCodeAndCountry(java.lang.String, java.lang.Long,java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWhByDealerTypeAndWhTypeCodeAndCountry(String dealerType,Long country, String whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByDealerTypeAndWhTypeCodeAndCountry/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.dealerId.dealerType.dealerTypeCode = :dealerType ");
        	stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode ");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	stringQuery.append("and entity.dealerId.postalCode.city.state.country.id = :country");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("dealerType", dealerType);
            query.setString("whTypeCode", whTypeCode);
            query.setLong("country", country);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            return query.list();
        } catch (Throwable ex){
			log.error("== Error getWhByDealerTypeAndWhTypeCodeAndCountry/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByDealerTypeAndWhTypeCodeAndCountry/WarehouseDAO ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWhByDealerIdAndWhTypeCodeAndCountry(Long dealerId,Long country, String whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByDealerTypeAndWhTypeCodeAndCountry/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.dealerId.id = :dealerId ");
        	stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode ");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	stringQuery.append("and entity.dealerId.postalCode.city.state.country.id = :country");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setString("whTypeCode", whTypeCode);
            query.setLong("country", country);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            return query.list();
        } catch (Throwable ex){
			log.error("== Error getWhByDealerTypeAndWhTypeCodeAndCountry/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByDealerTypeAndWhTypeCodeAndCountry/WarehouseDAO ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWhByDealerIdAndWhTypeCodeAndCountryCode(Long dealerId, String countryCode, String whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByDealerIdAndWhTypeCodeAndCountryCode/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.dealerId.id = :dealerId ");
        	stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode ");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	stringQuery.append("and entity.dealerId.postalCode.city.state.country.countryCode = :countryCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setString("whTypeCode", whTypeCode);
            query.setString("countryCode", countryCode);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            return query.list();
        } catch (Throwable ex){
			log.error("== Error getWhByDealerIdAndWhTypeCodeAndCountryCode/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByDealerIdAndWhTypeCodeAndCountryCode/WarehouseDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getCrewWarehouseByTypeAndCountry(java.lang.Long,java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Warehouse getCrewWarehouseByTypeAndCountry(Long crewId, String warehouseType, Long countryId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getAdjustWarehouseByDealerId/WarehouseDAO ==");
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.warehouseType.whTypeCode = :aWhType ");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append(" and entity.warehouseType.isActive = :whIsActive ");
        	stringQuery.append("and entity.crewId.id = :crewId ");
        	stringQuery.append("and entity.country.id = :countryId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setLong("crewId", crewId);
            query.setString("aWhType", warehouseType);
            query.setLong("countryId", countryId);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            List<Warehouse> list = query.list();
             if(list != null && list.size() > 0){
            	 return list.get(0);
             } else {
            	 return null;
             }
        } catch (Throwable ex){
			log.error("== Error getAdjustWarehouseByDealerId/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustWarehouseByDealerId/WarehouseDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getCrewWarehouseByTypeAndCountry(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Warehouse getCustomerWarehouseByCountry(Long customerId, Long countryId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getCustomerWarehouseByCountry/WarehouseDAO ==");
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.warehouseType.whTypeCode = :aWhType ");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	stringQuery.append("and entity.customerId.id = :customerId ");
        	stringQuery.append("and entity.country.id = :countryId ");
        	 
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setLong("customerId", customerId);
            query.setString("aWhType", CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
            query.setLong("countryId", countryId);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            List<Warehouse> list = query.list();
             if(list != null && list.size() > 0){
            	 return list.get(0);
             } else {
            	 return null;
             }
        } catch (Throwable ex){
			log.error("== Error getCustomerWarehouseByCountry/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCustomerWarehouseByCountry/WarehouseDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getCustomerWarehouseByCountry(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Warehouse getCustomerWarehouseByCountry(String customerIbsCode, String countryCode) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getCustomerWarehouseByCountry/WarehouseDAO ==");
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.warehouseType.whTypeCode = :aWhType ");
        	//gfandino-31/05/2011 Se modifica consulta para que incluya estado INV103 v2.2
        	stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	stringQuery.append("and entity.customerId.customerCode = :customerIbsCode ");
        	stringQuery.append("and entity.country.countryCode = :countryCode ");
        	 
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setString("customerIbsCode", customerIbsCode);
            query.setString("aWhType", CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
            query.setString("countryCode", countryCode);
            query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());

            List<Warehouse> list = query.list();
             if(list != null && list.size() > 0){
            	 return list.get(0);
             } else {
            	 return null;
             }
        } catch (Throwable ex){
			log.error("== Error getCustomerWarehouseByCountry/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCustomerWarehouseByCountry/WarehouseDAO ==");
        }
	}



	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByCountryIdAndDealerAndStatus(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Warehouse> getWarehousesByCountryIdAndStatus(Long countryId,Long dealerId,
			String warehouseStatus) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByCountryIdAndStatus/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity  ");
        	if(dealerId != null && dealerId.longValue()>0){
        		stringQuery.append(" left join fetch  entity.dealerId as dealer  ");
        		stringQuery.append(" left join  fetch  entity.crewId as crew  ");
        		stringQuery.append(" left join  fetch  crew.crewStatus as crewStatus  ");
        	}
        	
        	stringQuery.append(" where entity.country.id = :country ");
        	stringQuery.append(" and entity.isActive = :isActive");
        	stringQuery.append(" and entity.warehouseType.isActive =  :whisActive");
        	stringQuery.append(" and entity.warehouseType.isVisible = :whisVisible");
        	//Si aplica dealer
        	if(dealerId.longValue()!=-1){
        		stringQuery.append(" and (dealer.id = :dealerId ");	
        		stringQuery.append(" or (crew.dealer.id  = :dealerId and crewStatus.statusCode = :statusCode)) ");
        	}
        	stringQuery.append(" order by entity.dealerId.depotCode asc");
        	log.debug("QUERYYYYY: " + stringQuery.toString());
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("country", countryId);
            query.setString("isActive", warehouseStatus);
            query.setString("whisActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
            query.setString("whisVisible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
            if(dealerId != null && dealerId.longValue()>0){
            	 query.setLong("dealerId", dealerId);
            	 query.setString("statusCode", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            	 
        	}
            
            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCountryIdAndStatus/WarehouseDAO ==");
        }
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByCountryIdAndStatus(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseResponse  getWarehousesByCountryIdAndStatus(Long countryId,
			String warehouseStatus,RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByCountryIdAndStatus/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.country.id = :country ");
        	stringQuery.append(" and entity.isActive = :isActive");
        	stringQuery.append(" and entity.warehouseType.isActive =  :whisActive");
        	stringQuery.append(" and entity.warehouseType.isVisible = :whisVisible");
        	stringQuery.append(" order by entity.dealerId.depotCode asc");
        	stringCount.append(stringQuery.toString());
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("country", countryId);
            query.setString("isActive", warehouseStatus);
            query.setString("whisActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
            query.setString("whisVisible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
            
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setLong("country", countryId);
        		queryCount.setString("isActive", warehouseStatus);
        		queryCount.setString("whisActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        		queryCount.setString("whisVisible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseResponse response = new WareHouseResponse();
        	List<Warehouse> warehouse= query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouse.size(), recordQty.intValue() );
        	response.setWareHouse(warehouse);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCountryIdAndStatus/WarehouseDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhTransitDealerOrCrew(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Warehouse getWhTransitDealerOrCrew(WarehouseVO whSource)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhTransitDealerOrCrew/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where 1 = 1");
        	if(whSource.getDealerId()!=null && whSource.getDealerId().getId()!=null){
        		stringQuery.append(" and entity.dealerId.id = :dealerId ");
        	}
        	if(whSource.getCrewId()!=null && whSource.getCrewId().getId()!=null){
        		stringQuery.append(" and entity.crewId.id = :crewId ");
        	}
        	stringQuery.append(" and entity.warehouseType.whTypeCode = :typeCode ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId",  whSource.getDealerId().getId());
            query.setLong("crewId",  whSource.getCrewId().getId());
            query.setString("typeCode",  CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());

            return (Warehouse) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhTransitDealerOrCrew/WarehouseDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhByCrewAndWhType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Warehouse> getWhByCrewAndDealerAndWhType(Long dealerId, Long crewId,Long whTypeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByCrewAndWhType/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	boolean isDealer = ( dealerId != null && dealerId.longValue() > 0 ) ? true : false;
        	boolean isCrew = ( crewId != null && crewId.longValue() > 0 ) ? true : false;
        	boolean isWhType = ( whTypeId != null && whTypeId.longValue() > 0 ) ? true : false;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.isActive = :whActiveStatus ");
        	if( isDealer )
        		stringQuery.append("and entity.dealerId.id = :dealerId ");
        	
        	if( isCrew )
        		stringQuery.append("and entity.crewId.id = :crewId ");
        	//si es null el parametro crewId se trae solo los datos del dealer
        	else
        		stringQuery.append(" and entity.crewId is null ");
        	
        	if( isWhType ){
        		stringQuery.append("and entity.warehouseType.id = :whTypeId ");
        		stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	}        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
            
            if( isDealer )
            	query.setLong("dealerId", dealerId);
        	if( isCrew )
        		query.setLong("crewId", crewId);
        	if( isWhType ){
        		query.setLong("whTypeId", whTypeId);
        		 query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	}   

            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByCrewAndWhType/WarehouseDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhByCrewAndWhType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Warehouse> getWhByCrewAndDealerAndWhType(Long dealerId, Long crewId,String whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByCrewAndWhType/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	boolean isDealer = ( dealerId != null && dealerId.longValue() > 0 ) ? true : false;
        	boolean isCrew = ( crewId != null && crewId.longValue() > 0 ) ? true : false;
        	boolean isWhTypeCode = ( whTypeCode != null && whTypeCode.trim().length() > 0) ? true : false;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.isActive = :whActiveStatus ");
        	if( isDealer )
        		stringQuery.append("and entity.dealerId.id = :dealerId ");
        	
        	if( isCrew )
        		stringQuery.append("and entity.crewId.id = :crewId ");
        	//si es null el parametro crewId se trae solo los datos del dealer
        	else
        		stringQuery.append(" and entity.crewId is null ");
        	
        	if( isWhTypeCode ){
        		stringQuery.append("and entity.warehouseType.whTypeCode = :whTypeCode ");
        		stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	}        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
            
            if( isDealer )
            	query.setLong("dealerId", dealerId);
        	if( isCrew )
        		query.setLong("crewId", crewId);
        	if( isWhTypeCode ){
        		query.setString("whTypeCode", whTypeCode);
        		 query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	}   

            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByCrewAndWhType/WarehouseDAO ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWhByCrewAndWhType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Warehouse> getWhByCrewAndDealerAndWhTypeNotVirtual(Long dealerId, Long crewId,Long whTypeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWhByCrewAndWhType/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	boolean isDealer = ( dealerId != null && dealerId.longValue() > 0 ) ? true : false;
        	boolean isCrew = ( crewId != null && crewId.longValue() > 0 ) ? true : false;
        	boolean isWhType = ( whTypeId != null && whTypeId.longValue() > 0 ) ? true : false;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.isActive = :whActiveStatus ");
        	stringQuery.append(" and  entity.warehouseType.isVirtual = :isVirtual ");
        	if( isDealer )
        		stringQuery.append("and entity.dealerId.id = :dealerId ");
        	if( isCrew )
        		stringQuery.append("and entity.crewId.id = :crewId ");
        	if( isWhType ){
        		stringQuery.append("and entity.warehouseType.id = :whTypeId ");
        		stringQuery.append("and entity.warehouseType.isActive = :whIsActive ");
        	}        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
            query.setString("isVirtual" , CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity() );
            
            if( isDealer )
            	query.setLong("dealerId", dealerId);
        	if( isCrew )
        		query.setLong("crewId", crewId);
        	if( isWhType ){
        		query.setLong("whTypeId", whTypeId);
        		 query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	}   

            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWhByCrewAndWhType/WarehouseDAO ==");
        }
	}
	
	
	@Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Warehouse getWarehouseTypeByDealerId(Long dealerId,String warehouseType) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.dealerId.id = :andealerId");
        	stringQuery.append(" and entity.warehouseType.whTypeCode = :aWhType");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("andealerId", dealerId);
            query.setString("aWhType", warehouseType);
            
            return (Warehouse) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByDealerId/WarehouseDAO ==");
        }
    }
	

	public Warehouse getWarehouseBySerialCode(String warehouseCode) throws DAOServiceException, DAOSQLException{
		 log.debug("== Inicio getWarehouseBySerialCode/WarehouseDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Warehouse.class.getName());
	        	stringQuery.append(" entity where entity.whCode = :code");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setString("code", warehouseCode);

	            return (Warehouse) query.uniqueResult();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getWarehouseBySerialCode/WarehouseDAO ==");
	        }
	}



	@Override
	public Warehouse getWarehouseByDealerIDIBSCodeDocumentCustomer(
			Long warehouseId, String codeIBS, String documentCustomer)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByCustomerId/WarehouseDAO ==");
        Session session = super.getSession();

        boolean isWarehouse = ( warehouseId != null && warehouseId.longValue() > 0 ) ? true : false;
    	boolean isIBSCode = ( codeIBS != null && !codeIBS.equals("") ) ? true : false;
    	boolean isDocumentCustomer = ( documentCustomer != null && !documentCustomer.equals("") ) ? true : false;
    	
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.isActive = :whActiveStatus");
        	if(isWarehouse){
        		stringQuery.append(" and entity.id = :warehouseId ");
        	}
        	if(isIBSCode){
        		stringQuery.append(" and entity.customerId.customerCode = :codeIBS ");
        	}
        	if(isDocumentCustomer){
        		stringQuery.append(" and entity.customerId.documentNumber = :documentCustomer ");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
        	if(isWarehouse){
        		query.setLong("warehouseId", warehouseId);
        	}
        	if(isIBSCode){
        		query.setString("codeIBS", codeIBS);
        	}
        	if(isDocumentCustomer){
        		query.setString("documentCustomer", documentCustomer);
        	}
            
            return (Warehouse) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByCustomerId/WarehouseDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getWarehousesByCountryIdAndOptionalDealerId(java.lang.Long, java.lang.Long, boolean, boolean)
	 */
	@Override
	public List<Long> getWarehousesByCountryIdAndOptionalDealerId(
				Long dealerID, Long countryID, boolean searchSingleDealer, boolean includeCrews, boolean includeBranches, boolean includeCrewsForASingleDealer) throws DAOServiceException,
				DAOSQLException {
		        			
				log.debug("== Inicio getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseDAO ==");	
				Session session = super.getSession();
					
				boolean isDealer = ( dealerID != null && dealerID.longValue() > 0 ) ? true : false;
					
				if(searchSingleDealer && !isDealer){
		        	throw new DAOServiceException("Dealer requerido. Se indicó que no se debería buscar la información para todos los dealers, por tanto, se requiere especificar el id del dealer por el que se desea buscar");
		        }
					
				try {
		            StringBuffer stringQuerySelect = new StringBuffer("");
				    StringBuffer stringQueryFrom = new StringBuffer("");
				    StringBuffer stringQueryInnerJoin = new StringBuffer("");
				    StringBuffer stringQueryWhere = new StringBuffer("");
					stringQuerySelect.append(" SELECT WH.ID AS id ");
					stringQueryFrom.append(" FROM WAREHOUSES WH, WAREHOUSE_TYPES WT ");
					stringQueryInnerJoin.append(" WHERE WH.WH_TYPE_ID =WT.ID ");
					stringQueryWhere.append(" AND WH.IS_ACTIVE = :whActiveStatus ");
					stringQueryWhere.append(" AND WT.IS_VIRTUAL = :isVirtual ");
					stringQueryWhere.append(" AND WT.IS_ACTIVE = :isActiveWT ");	        	
					stringQueryWhere.append(" AND WH.CUSTOMER_ID IS NULL ");
					stringQueryWhere.append(" AND WH.COUNTRY_ID = :aCountryID ");
					
					if(searchSingleDealer){
						stringQueryWhere.append(" AND ( WH.DEALER_ID = :dealerId ");
						if(includeBranches){
							stringQueryFrom.append(", DEALERS DE ");
							stringQueryInnerJoin.append(" AND WH.DEALER_ID=DE.ID " );
							stringQueryWhere.append(" OR DE.DEALER_BRANCH_ID = :dealerId ");
							
						}
						stringQueryWhere.append(") ");
					}else if(!includeBranches){
						stringQueryFrom.append(", DEALERS DE ");
						stringQueryInnerJoin.append(" AND WH.DEALER_ID=DE.ID ");
						stringQueryWhere.append(" AND DE.DEALER_BRANCH_ID is null ");
					}
		        	
					if(!includeCrews){
						stringQueryWhere.append(" AND WH.CREW_ID IS NULL ");
					}else{
						if(includeCrewsForASingleDealer && isDealer){
							StringBuffer stringQueryWhereUnion = new StringBuffer("");
							stringQueryWhereUnion.append( stringQueryWhere.toString());
							stringQueryWhere.append(" AND WH.CREW_ID IS NULL ");
							stringQueryWhere.append(" UNION ");
							stringQueryWhere.append( stringQuerySelect.toString());  
							stringQueryWhere.append( stringQueryFrom.toString() + ", CREWS C");
							stringQueryWhere.append( stringQueryInnerJoin.toString() + " AND WH.CREW_ID =C.ID ");
							stringQueryWhere.append( stringQueryWhereUnion.toString() + " AND C.DEALER_ID = :dealerIdForCrews ");
						}
					}
					
					Query querySQL = session.createSQLQuery(stringQuerySelect.toString() + stringQueryFrom.toString() + stringQueryInnerJoin.toString() + stringQueryWhere.toString())
							.addScalar("id", Hibernate.LONG);       			

					querySQL.setParameter("aCountryID", countryID, Hibernate.LONG);	
					querySQL.setString("whActiveStatus", CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
					querySQL.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
					querySQL.setString("isActiveWT", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
					if(searchSingleDealer){
						querySQL.setLong("dealerId", dealerID);
					}
					if(includeCrews && includeCrewsForASingleDealer && isDealer){
						querySQL.setLong("dealerIdForCrews", dealerID);
					}
					List<Long> retorno = querySQL.list();
					return retorno;
					
				} catch (Throwable ex){
					log.error("== Error ==");
					throw this.manageException(ex);
				} finally {
		            log.debug("== Termina getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseDAO ==");
		        }
		    }

	@Override
	public Warehouse getWarehouseByCountryIdAndCodeAndNotVirtualAndNotCustomer(
			String codeWarehouse, Long countryID) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.isActive = :whActiveStatus");
        	stringQuery.append(" and  entity.warehouseType.isVirtual = :isVirtual ");
        	stringQuery.append(" and  entity.warehouseType.isActive = :isActiveWT ");
        	stringQuery.append(" and  entity.customerId IS NULL ");
        	stringQuery.append(" and  entity.whCode = :codeWarehouse ");
        	stringQuery.append(" and  entity.country.id = :aCountryID ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aCountryID" , countryID );
        	query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
        	query.setString("isVirtual" , CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity() );
        	query.setString("isActiveWT" , CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity() );
        	query.setString("codeWarehouse" , codeWarehouse );
        	
            return (Warehouse) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseDAO ==");
        }
	}

	@Override
	public Warehouse getWarehouseByCountryIdAndCodeAndNotVirtual(
			String codeWarehouse, Long countryID) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.isActive = :whActiveStatus");
        	stringQuery.append(" and  entity.warehouseType.isVirtual = :isVirtual ");
        	stringQuery.append(" and  entity.warehouseType.isActive = :isActiveWT ");
        	stringQuery.append(" and  entity.whCode = :codeWarehouse ");
        	stringQuery.append(" and  entity.country.id = :aCountryID ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aCountryID" , countryID );
        	query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
        	query.setString("isVirtual" , CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity() );
        	query.setString("isActiveWT" , CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity() );
        	query.setString("codeWarehouse" , codeWarehouse );
        	
            return (Warehouse) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getQuantityWarehouseCrewByDealersAndWarehouseType(java.util.List, java.util.List)
	 */
	@Override
	public Long getQuantityWarehouseCrewByDealersAndWarehouseType(List<Long> dealerId,List<String> whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" select count(distinct w.id) ");
			stringQuery.append("   from ").append(Warehouse.class.getName()).append(" w   ");       
			stringQuery.append("  inner join w.crewId c     ");     
			stringQuery.append("  inner join c.dealer d ");
			stringQuery.append(" where w.warehouseType.isActive = :whIsActive ");
			stringQuery.append(" and w.isActive = :whActiveStatus ");
			
			if(whTypeCode != null && whTypeCode.size()>0)
				stringQuery.append("        and w.warehouseType.whTypeCode in("+UtilsBusiness.stringListToString(whTypeCode,",")+") ");
			
			if(dealerId != null && dealerId.size()>0)
				stringQuery.append("        and d.id in("+UtilsBusiness.longListToString(dealerId,",")+") ");
			      
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
			query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
			
			return (Long) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getQuantityWarehouseDealerByDealersAndWarehouseType(java.util.List, java.util.List)
	 */
	@Override
	public Long getQuantityWarehouseDealerByDealersAndWarehouseType(List<Long> dealerId,List<String> whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" select count(distinct w.id) ");
			stringQuery.append("   from ").append(Warehouse.class.getName()).append(" w   ");       
			stringQuery.append("  inner join w.dealerId d ");
			stringQuery.append(" where w.warehouseType.isActive = :whIsActive ");
			stringQuery.append(" and w.isActive = :whActiveStatus ");
			stringQuery.append(" and w.crewId is null");
			
			if(whTypeCode != null && whTypeCode.size()>0)
				stringQuery.append("        and w.warehouseType.whTypeCode in("+UtilsBusiness.stringListToString(whTypeCode,",")+") ");
			
			if(dealerId != null && dealerId.size()>0)
				stringQuery.append("        and d.id in("+UtilsBusiness.longListToString(dealerId,",")+") ");
			      
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
			query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
			
			return (Long) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal#getQuantityWarehouseDealerByDealersAndWarehouseType(java.util.List, java.util.List)
	 */
	@Override
	public List<WarehouseVO> getWarehouseByIds(List<Long> warehouseIds,Long countryID) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("	SELECT * FROM (	");
			stringQuery.append("	SELECT WA.ID id,	");
			stringQuery.append("	       DEL.ID DEALER_ID,	");
			stringQuery.append("	       DEL.DEALER_NAME,	");
			stringQuery.append("	       CUS.ID CUSTOMER_ID,	");
			stringQuery.append("	       CUS.FIRST_NAME CUSTOMER_FIRST_NAME,	");
			stringQuery.append("	       CUS.LAST_NAME CUSTOMER_LAST_NAME,	");
			stringQuery.append("	       CR.ID CREW_ID,	");
			stringQuery.append("	       EM.FIRST_NAME EMPL_FIRST_NAME,	");
			stringQuery.append("	       EM.LAST_NAME EMPL_LAST_NAME,	");
			stringQuery.append("	       WT.WH_TYPE_NAME,	");
			stringQuery.append("	       WT.WH_TYPE_CODE,	");
			stringQuery.append("	       case WHEN  CR.ID IS NOT NULL THEN	");
			stringQuery.append("	           DEL.DEPOT_CODE||' - '||DEL.DEALER_NAME||' - '|| EM.FIRST_NAME||' '||EM.LAST_NAME||' - '|| WT.WH_TYPE_NAME 	");
			stringQuery.append("	       WHEN DEL.ID IS NOT NULL THEN	");
			stringQuery.append("	          DEL.DEPOT_CODE||' - '||DEL.DEALER_NAME||' - '|| WT.WH_TYPE_NAME	");
			stringQuery.append("	       WHEN CUS.ID IS NOT NULL THEN	");
			stringQuery.append("	          CUS.CUSTOMER_CODE||' - '||CUS.FIRST_NAME||' - '|| CUS.LAST_NAME||' - '|| WT.WH_TYPE_NAME	");
			stringQuery.append("	       END warehouseName	");
			stringQuery.append("	  FROM WAREHOUSES wa	");
			stringQuery.append("	       LEFT OUTER JOIN DEALERS del	");
			stringQuery.append("	          ON del.id = WA.DEALER_ID	");
			stringQuery.append("	       LEFT OUTER JOIN CUSTOMERS cus	");
			stringQuery.append("	          ON cus.id = WA.CUSTOMER_ID	");
			stringQuery.append("	       LEFT OUTER JOIN CREWS cr	");
			stringQuery.append("	          ON cr.id = WA.CREW_ID	");
			stringQuery.append("	       LEFT OUTER JOIN EMPLOYEE_CREWS ec	");
			stringQuery.append("	          ON (EC.CREW_ID = cr.id AND EC.IS_RESPONSIBLE = :isResponsible)	");
			stringQuery.append("	       LEFT OUTER JOIN EMPLOYEES em	");
			stringQuery.append("	          ON em.id = EC.EMPLOYEE_ID	");
			stringQuery.append("	       LEFT OUTER JOIN WAREHOUSE_TYPES WT	");
			stringQuery.append("	          ON WT.ID = WA.WH_TYPE_ID	");
			stringQuery.append("	       WHERE ("+ UtilsBusiness.longListToOrInQuery(warehouseIds, "WA.ID") +")	");
			stringQuery.append("	)	");

			Query query = session.createSQLQuery(stringQuery.toString())
						  .addScalar("id", Hibernate.LONG)
						  .addScalar("warehouseName")
						  .setResultTransformer(Transformers.aliasToBean(WarehouseVO.class));
			query.setString("isResponsible", CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
			List<WarehouseVO> listWarehouse = query.list();
			return listWarehouse;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		}
	}

	@Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Warehouse> getWarehouseByWhCode(String whCode, Long country) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehousesByWhCode/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" entity where entity.whCode = :aWhCode ");
        	stringQuery.append("   and entity.country.id = :aCountry ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aWhCode", whCode);
            query.setLong("aCountry", country);
            List<Warehouse> listWarehouse = query.list();
            return listWarehouse;
            
        } catch (Throwable ex){
			log.error("== Error ==", ex);
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByDealerId/WarehouseDAO ==");
        }
    }

}
