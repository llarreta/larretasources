package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.EJB;
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
import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.collection.ElementModelResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ElementModel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementModel
 * @see co.com.directv.sdii.model.hbm.ElementModel.hbm.xml
 */
@Stateless(name="ElementModelDAOLocal",mappedName="ejb/ElementModelDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementModelDAO extends BaseDao implements ElementModelDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ElementModelDAO.class);
    
	@EJB(name="RecordStatusDAOLocal", beanInterface=RecordStatusDAOLocal.class)
	private RecordStatusDAOLocal recordStatusDao;
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementModelsDAOLocal#createElementModel(co.com.directv.sdii.model.pojo.ElementModel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createElementModel(ElementModel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createElementModel/ElementModelDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ElementModel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementModel/ElementModelDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementModelsDAOLocal#updateElementModel(co.com.directv.sdii.model.pojo.ElementModel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateElementModel(ElementModel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateElementModel/ElementModelDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ElementModel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementModel/ElementModelDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementModelsDAOLocal#deleteElementModel(co.com.directv.sdii.model.pojo.ElementModel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteElementModel(ElementModel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteElementModel/ElementModelDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ElementModel entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ElementModel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementModel/ElementModelDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementModelsDAOLocal#getElementModelsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ElementModel getElementModelByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getElementModelByID/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ElementModel) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByID/ElementModelDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementModelsDAOLocal#getAllElementModels()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ElementModelResponse getAllElementModels(RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllElementModels/ElementModelDAO ==");
        Session session = super.getSession();
        try {
        	//Paginacion      	
        	StringBuffer stringCount = new StringBuffer();        	        	
        	StringBuffer stringQuery = new StringBuffer();        	
        	
        	//Paginacion
        	stringCount.append("select count(*) ");
        	//Cuerpo de la consulta
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	
        	Query query = session.createQuery( stringQuery.toString() );
        
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	 
        		stringCount.append( stringQuery.toString() );
        		Query countQuery = session.createQuery( stringCount.toString() );
        		recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );
        	}
        	
        	ElementModelResponse response = new ElementModelResponse();
        	List<ElementModel> elements = query.list();
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elements.size(), recordQty.intValue());
        	response.setElementModel(elements);      
        	
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllElementModels/ElementModelDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementModel getElementModelByCode(String modelCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelByCode/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where upper(entity.modelCode) = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", modelCode.toUpperCase());

            return (ElementModel) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByCode/ElementModelDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelByName(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementModel getElementModelByName(String modelName)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelByName/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where upper(entity.modelName) = :aName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aName", modelName.toUpperCase());

            return (ElementModel) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByName/ElementModelDAO ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelsByStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementModel> getElementModelsByStatus(String codeEntity)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelsByStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.isActive = :codeEntity");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("codeEntity", codeEntity);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelsByStatus/ElementModelDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelsByElementTypeStatus(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementModel> getElementModelByElementClass(
			String elementClassCode) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getElementModelByElementClass/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.elementClass.elementClassCode = :elementClassCode order by entity.modelName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("elementClassCode", elementClassCode);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByElementClass/ElementModelDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelByElementClassAndStatus(java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementModel> getElementModelByElementClassAndStatus(
			String elementClassCode, String elementModelStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelByElementClassAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.elementClass.elementClassCode = :elementClassCode ");
        	stringQuery.append(" and entity.isActive = :elementModelStatus order by entity.modelName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("elementClassCode", elementClassCode);
            query.setString("elementModelStatus", elementModelStatus);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByElementClassAndStatus/ElementModelDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelByElementClassId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementModel> getElementModelByElementClassId(
			Long elementClassId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelByElementClassId/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.elementClass.id = :elementClassId order by entity.modelName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementClassId", elementClassId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByElementClassId/ElementModelDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelByElementClassIdAndStatus(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementModel> getElementModelByElementClassIdAndStatus(
			Long elementClassId, String elementModelStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelByElementClassIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.elementClass.id = :elementClassId ");
        	stringQuery.append(" and entity.isActive = :elementModelStatus order by entity.modelCode asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementClassId", elementClassId);
            query.setString("elementModelStatus", elementModelStatus);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByElementClassIdAndStatus/ElementModelDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelByElementClassIdAndStatus(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementModelResponse getElementModelByElementClassIdAndStatus(Long elementClassId,String elementModelStatus,RequestCollectionInfo requestCollInfo)
		throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getElementModelByElementClassIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.elementClass.id = :elementClassId ");
        	stringQuery.append(" and entity.isActive = :elementModelStatus order by entity.modelCode asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementClassId", elementClassId);
            query.setString("elementModelStatus", elementModelStatus);

            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setLong("elementClassId", elementClassId);
        		queryCount.setString("elementModelStatus", elementModelStatus);
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementModelResponse response = new ElementModelResponse();
        	List<ElementModel> elementModelList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementModelList.size(), recordQty.intValue() );
        	response.setElementModel(elementModelList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByElementClassIdAndStatus/ElementModelDAO ==");
        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal#getElementModelsByElementClassIdAndAllStatusPage(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementModelResponse getElementModelsByElementClassIdAndAllStatusPage(
			Long idElementClass,String code, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelByElementClassIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity  where 1=1");
        	if(idElementClass!=-1){
        		stringQuery.append("  and entity.elementClass.id = :elementClassId ");
        	}
        	if(code!=null){
        		stringQuery.append("  and entity.modelCode = :modelCode ");
        	}
        	
        	stringQuery.append(" order by entity.modelCode asc");
        	Query query = session.createQuery(stringQuery.toString());
        	if(idElementClass!=-1){
        		query.setLong("elementClassId", idElementClass);
        	}
        	if(code!=null){
        		query.setString("modelCode", code);
        	}

            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(idElementClass!=-1){
        			queryCount.setLong("elementClassId", idElementClass);
        		}
        		if(code!=null){
        			queryCount.setString("modelCode", code);
            	}
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementModelResponse response = new ElementModelResponse();
        	List<ElementModel> elementModelList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementModelList.size(), recordQty.intValue() );
        	response.setElementModel(elementModelList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelByElementClassIdAndStatus/ElementModelDAO ==");
        }
	}



	@Override
	public List<ElementModel> getElementModelsByElementTypeCodeStatus(String elementTypeCode, String codeEntity)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelsByElementTypeCodeStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.isActive = :codeEntity");
        	stringQuery.append(" and entity.elementType.typeElementCode = :elementTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("codeEntity", codeEntity);
            query.setString("elementTypeCode", elementTypeCode);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelsByElementTypeCodeStatus/ElementModelDAO ==");
        }
	}



	@Override
	public List<ElementModel> getElementModelsByActiveStatusAndIsPrepaid(
			String isPrepaid) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelsByActiveStatusAndIsPrepaid/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementModel.class.getName());
        	stringQuery.append(" entity where entity.isActive = :isActive");
        	stringQuery.append(" and  entity.isPrepaidModel = :isPrepaidModel");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("isActive", CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity());
            query.setString("isPrepaidModel", isPrepaid);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelsByActiveStatusAndIsPrepaid/ElementModelDAO ==");
        }
	}
	
	@Override
	public List<ElementModel> getElementModelsByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementModelsByWarehouseIdAndElementModelId/ElementModelDAO ==");
        Session session = super.getSession();
        String strWhereOrAnd = " where ";
        boolean warehouseIdSpecified=false,elementModelIdSpecified=false;
        long lRecorStatusId=0L;
        try {

			StringBuffer stringQuery = new StringBuffer();
			
			if(warehouseId!=null && warehouseId.longValue() > 0) {
				warehouseIdSpecified=true;
				
				stringQuery.append("SELECT DISTINCT elementmod5_.* FROM WAREHOUSE_ELEMENTS warehousee0_ ");
				stringQuery.append(" INNER JOIN ELEMENTS element3_");
				stringQuery.append(" ON ((WAREHOUSEE0_.SER_ID = ELEMENT3_.ID AND WAREHOUSEE0_.NOT_SER_ID is null) OR (WAREHOUSEE0_.NOT_SER_ID =ELEMENT3_.ID AND WAREHOUSEE0_.SER_ID is null ))");
				stringQuery.append(" INNER JOIN ELEMENT_TYPES elementtyp4_ ON element3_.ELEMENT_TYPE_ID=elementtyp4_.ID");
				stringQuery.append(" INNER JOIN ELEMENT_MODELS elementmod5_ ON elementtyp4_.ELEMENT_MODEL_ID=elementmod5_.ID");
				stringQuery.append(" WHERE WAREHOUSEE0_.WAREHOUSE_ID = :aWarehouseId");
				stringQuery.append(" AND WAREHOUSEE0_.RECORD_STATUS_ID= :recorStatusId");				
				
				//Obtenemos el ID del RecordStatus.
				RecordStatus redordStatus = recordStatusDao.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
				lRecorStatusId = redordStatus.getId();
				
				if(elementModelId!=null && elementModelId.longValue() > 0) {
					elementModelIdSpecified = true;
					stringQuery.append(" AND ELEMENTMOD5_.ID = :aElementModelId");
				}
			}else {
				
				stringQuery.append(" from ").append(ElementModel.class.getName()).append(" entity ");
				
				if(elementModelId!=null && elementModelId.longValue() > 0) {
					elementModelIdSpecified = true;
					stringQuery.append(strWhereOrAnd).append(" entity.id=:aElementModelId ");
					strWhereOrAnd = " and ";
				}
			}
			
			Query query = null;
			
			if(warehouseIdSpecified){
				query = session.createSQLQuery(stringQuery.toString()).addEntity(ElementModel.class);
			}else{
				query = session.createQuery(stringQuery.toString());
			}
			
			if(elementModelIdSpecified){
				query.setLong("aElementModelId", elementModelId);
			}
            
			if(warehouseIdSpecified){
				query.setLong("aWarehouseId", warehouseId);
				query.setLong("recorStatusId",lRecorStatusId);
			}
            
			return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementModelsByWarehouseIdAndElementModelId/ElementModelDAO ==");
        }
	}

}
