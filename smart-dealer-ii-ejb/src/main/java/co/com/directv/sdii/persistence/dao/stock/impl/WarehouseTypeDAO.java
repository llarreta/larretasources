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
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad WarehouseType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WarehouseType
 * @see co.com.directv.sdii.model.hbm.WarehouseType.hbm.xml
 */
@Stateless(name="WarehouseTypeDAOLocal",mappedName="ejb/WarehouseTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WarehouseTypeDAO extends BaseDao implements WarehouseTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WarehouseTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypesDAOLocal#createWarehouseType(co.com.directv.sdii.model.pojo.WarehouseType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWarehouseType(WarehouseType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWarehouseType/WarehouseTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el WarehouseType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouseType/WarehouseTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypesDAOLocal#updateWarehouseType(co.com.directv.sdii.model.pojo.WarehouseType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWarehouseType(WarehouseType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateWarehouseType/WarehouseTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el WarehouseType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWarehouseType/WarehouseTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypesDAOLocal#deleteWarehouseType(co.com.directv.sdii.model.pojo.WarehouseType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWarehouseType(WarehouseType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteWarehouseType/WarehouseTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from WarehouseType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el WarehouseType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWarehouseType/WarehouseTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypesDAOLocal#getWarehouseTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WarehouseType getWarehouseTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehouseTypeByID/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (WarehouseType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseTypeByID/WarehouseTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypesDAOLocal#getAllWarehouseTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WarehouseType> getAllWarehouseTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllWarehouseTypes/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName()+" entity ");
        	stringQuery.append(" where entity.isVirtual = :isVirtual ");
        	stringQuery.append(" order by entity.whTypeName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehouseTypes/WarehouseTypeDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal#getWarehouseTypeByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseType getWarehouseTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehouseTypeByCode/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName());
        	stringQuery.append(" entity where entity.whTypeCode = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);

            return (WarehouseType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseTypeByCode/WarehouseTypeDAO ==");
        }
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseType getWarehouseTypeByName(String name)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehouseTypeByName/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName());
        	stringQuery.append(" entity where entity.whTypeName = :aName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aName", name);

            return (WarehouseType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseTypeByName/WarehouseTypeDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal#getWarehouseTypeByStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseType> getWarehouseTypeByStatus(String status)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehouseTypeByStatus/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName());
        	stringQuery.append(" entity where entity.isActive = :status ");
        	stringQuery.append(" and entity.isVisible = :visible ");
        	stringQuery.append(" order by entity.whTypeName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("status", status);
            query.setString("visible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
            

            return  query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseTypeByStatus/WarehouseTypeDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal#getWarehouseTypeByStatus(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareheouseTypeResponse getWarehouseTypeByStatus(String status,RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehouseTypeByStatus/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) from ");
        	stringQuery.append(WarehouseType.class.getName());
        	stringCount.append(WarehouseType.class.getName());
        	stringQuery.append(" entity where entity.isActive = :status ");
        	stringCount.append(" entity where entity.isActive = :status ");
        	stringQuery.append(" and entity.isVisible = :visible ");
        	stringCount.append(" and entity.isVisible = :visible ");
        	stringQuery.append(" order by entity.whTypeName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setString("status", status);
            query.setString("visible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
           
            

           //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setString("status", status);
                queryCount.setString("visible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareheouseTypeResponse response = new WareheouseTypeResponse();
        	List<WarehouseType> warehouseType = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseType.size(), recordQty.intValue() );
        	response.setWhTypePojo(warehouseType );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehouseTypeByStatus/WarehouseTypeDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal#getAllWarehouseTypes(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareheouseTypeResponse getAllWarehouseTypes(String code,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getAllWarehouseTypes/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringCount = new StringBuffer("select count(*) ");
        	StringBuffer stringQuery = new StringBuffer("from ");
        	
        	stringQuery.append(WarehouseType.class.getName());
        	stringQuery.append(" entity where entity.isVisible = :visible");
        	if(code!=null){
        		stringQuery.append(" and entity.whTypeCode = :whTypeCode");
        	}
        	stringQuery.append(" order by entity.whTypeName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("visible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
            if(code!=null){
            	query.setString("whTypeCode", code);
        	}
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query queryCount = session.createQuery(stringCount.append(stringQuery).toString());
                queryCount.setString("visible", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
                if(code!=null){
                	queryCount.setString("whTypeCode", code);
            	}
                recordQty = (Long)queryCount.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareheouseTypeResponse response = new WareheouseTypeResponse();
        	List<WarehouseType> warehouseType = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseType.size(), recordQty.intValue() );
        	response.setWhTypePojo(warehouseType );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehouseTypes/WarehouseTypeDAO ==");
        }
	}

	@Override
	public List<WarehouseType> getAllWarehouseTypesActiveAndNotVirtual()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllWarehouseTypesActive/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName()+" entity ");
        	stringQuery.append("where entity.isActive = :isActive ");
        	stringQuery.append("and entity.isVirtual = :isNotVirtual ");
        	stringQuery.append("order by  entity.whTypeName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	query.setString("isNotVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehouseTypesActive/WarehouseTypeDAO ==");
        }
	}

	@Override
	public List<WarehouseType> getAllWarehouseTypesActive()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllWarehouseTypesActive/WarehouseTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName()+" entity ");
        	stringQuery.append("where entity.isActive = :isActive ");
        	stringQuery.append("order by  entity.whTypeName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehouseTypesActive/WarehouseTypeDAO ==");
        }
	}
	
	@Override
	public List<WarehouseType> getAllWarehouseTypesByDealerID(Long dealerId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getAllWarehouseTypesByDealerID/WarehouseTypeDAO ==");
        Session session = super.getSession();
        
        try {
        	boolean isDealer = ( dealerId != null && dealerId.longValue() > 0 ) ? true : false;
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WarehouseType.class.getName()+" entity ");
        	stringQuery.append("where entity.isActive = :isActive ");
        	stringQuery.append(" and entity.isVirtual = :isVirtual ");
        	if (isDealer){
        		stringQuery.append(" and  entity.id IN ((select distinct(wt.id) from "+Warehouse.class.getName()+" wa inner join wa.warehouseType wt where (wa.dealerId.id = :dealerId or wa.dealerId.dealer.id = :dealerId))) ");
        	}
        	stringQuery.append(" order by  entity.whTypeName asc");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
        	query.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
        	if (isDealer){
        		query.setLong("dealerId", dealerId);
        	}
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWarehouseTypesByDealerID/WarehouseTypeDAO ==");
        }
	}

}
