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
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.collection.ElementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ElementType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementType
 * @see co.com.directv.sdii.model.hbm.ElementType.hbm.xml
 */
@Stateless(name="ElementTypeDAOLocal",mappedName="ejb/ElementTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementTypeDAO extends BaseDao implements ElementTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ElementTypeDAO.class);
    
	@EJB(name="RecordStatusDAOLocal", beanInterface=RecordStatusDAOLocal.class)
	private RecordStatusDAOLocal recordStatusDao;
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#createElementType(co.com.directv.sdii.model.pojo.ElementType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createElementType(ElementType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createElementType/ElementTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ElementType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementType/ElementTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#updateElementType(co.com.directv.sdii.model.pojo.ElementType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateElementType(ElementType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateElementType/ElementTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ElementType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementType/ElementTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#deleteElementType(co.com.directv.sdii.model.pojo.ElementType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteElementType(ElementType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteElementType/ElementTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ElementType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ElementType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementType/ElementTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public ElementType getElementTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getElementTypeByID/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ElementType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getElementTypeByID/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByID/ElementTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getAllElementTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ElementType> getAllElementTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllElementTypes/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName()+" et");
        	stringQuery.append(" inner join fetch et.elementModel em ");
        	stringQuery.append(" inner join fetch em.elementClass ec ");
        	stringQuery.append(" inner join fetch et.elementBrand eb ");
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllElementTypes/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllElementTypes/ElementTypeDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypeByCode(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementType getElementTypeByCode(String code)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getElementTypeByCode/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" et where upper(et.typeElementCode) = :anEtCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anEtCode", code.toUpperCase());
            return (ElementType)query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getElementTypeByCode/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByCode/ElementTypeDAO ==");
        }
	}
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementType> getElementsTypeByCode(List<String> codes)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getElementTypeByCode/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" et where upper(et.typeElementCode) IN(:anEtCodes)");
        	Query query = session.createQuery(stringQuery.toString());
            query.setParameterList("anEtCodes", codes);
            return (List<ElementType>)query.list();
        } catch (Throwable ex){
			log.error("== Error getElementTypeByCode/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByCode/ElementTypeDAO ==");
        }
	}
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypeByCode(java.lang.String)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementType> getElementTypeByMeasureUnitID(Long measureUnitID)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getElementTypeByMeasureUnitID/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" et where et.measureUnit.id = :measureUnitID");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("measureUnitID", measureUnitID);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error getElementTypeByMeasureUnitID/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByMeasureUnitID/ElementTypeDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypeByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementType getElementTypeByName(String name)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getElementTypeByName/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" et where upper(et.typeElementName)= :anEtName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anEtName", name.toUpperCase());
            return (ElementType)query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getElementTypeByName/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByName/ElementTypeDAO ==");
        }
	}
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypesByIsActive(java.lang.String, boolean)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementType> getElementTypesByIsActive(String status)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getElementTypesByIsActive/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName()+" et ");
        	stringQuery.append(" inner join fetch et.elementModel em");
        	stringQuery.append(" inner join fetch em.elementClass ec");
        	stringQuery.append(" inner join fetch et.elementBrand eb ");
        	stringQuery.append(" where et.isActive = :aStatus");
        	stringQuery.append(" order by et.typeElementCode asc");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aStatus", status);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error getElementTypesByIsActive/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypesByIsActive/ElementTypeDAO ==");
        }
	}
    
   
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementType> getElementTypesByIsActiveAndIsSerialized(String status,boolean isSerialized)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getElementTypesByIsActive/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName()+" et ");
        	stringQuery.append(" inner join fetch et.elementModel em ");
        	stringQuery.append(" inner join fetch em.elementClass ec ");
        	stringQuery.append(" inner join fetch et.elementBrand eb ");
        	stringQuery.append(" where et.isActive = :aStatus");
        	stringQuery.append(" and et.isSerialized = :isSerialized");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aStatus", status);
            if(isSerialized){
            	query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
            }else{
            	query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
            }
            return query.list();
        } catch (Throwable ex){
			log.error("== Error getElementTypesByIsActive/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypesByIsActive/ElementTypeDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypeByElementModel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementType> getElementTypeByElementModel(
			String elementModelCode) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModel/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where entity.elementModel.modelCode = :elementModelCode order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("elementModelCode", elementModelCode);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModel/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModel/ElementModelDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypeByElementModelAndStatus(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementType> getElementTypeByElementModelAndStatus(
			String elementModelCode, String elementTypeStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModel/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where entity.elementModel.modelCode = :elementModelCode");
        	stringQuery.append(" and entity.isActive = :elementTypeStatus order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("elementModelCode", elementModelCode);
            query.setString("elementTypeStatus", elementTypeStatus);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModel/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModel/ElementModelDAO ==");
        }
	}



	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementType> getElementTypeByElementModelId(Long elementModelId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModelId/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where entity.elementModel.id = :elementModelId order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementModelId", elementModelId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModelId/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModelId/ElementModelDAO ==");
        }
	}



	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementType> getElementTypeByElementModelIdAndStatus(
			Long elementModelId, String elementTypeStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where entity.elementModel.id = :elementModelId");
        	stringQuery.append(" and entity.isActive = :elementTypeStatus order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementModelId", elementModelId);
            query.setString("elementTypeStatus", elementTypeStatus);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementTypeResponse getElementTypeByElementModelIdAndStatus(Long elementModelId,String elementTypeStatus,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where entity.elementModel.id = :elementModelId");
        	stringQuery.append(" and entity.isActive = :elementTypeStatus order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementModelId", elementModelId);
            query.setString("elementTypeStatus", elementTypeStatus);

            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setLong("elementModelId", elementModelId);
        		queryCount.setString("elementTypeStatus", elementTypeStatus);
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementTypeResponse response = new ElementTypeResponse();
        	List<ElementType> elementTypeList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementTypeList.size(), recordQty.intValue() );
        	response.setElementType(elementTypeList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementType> getElementTypesByModelStatusAndIsSerialized(Boolean isSerialized, String elementModelCode,Boolean elementTypeStatus)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new co.com.directv.sdii.model.pojo.ElementType(entity.typeElementCode, entity.id, entity.typeElementName) from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where 1=1 ");
        	if(isSerialized!=null){
        		stringQuery.append(" and entity.isSerialized = :isSerialized");
        	}
        	if(elementModelCode!=null && !elementModelCode.trim().equalsIgnoreCase("")){
        		stringQuery.append(" and entity.elementModel.modelCode = :elementModelCode");
        	}
        	if(elementTypeStatus!=null){
        		stringQuery.append(" and entity.isActive = :elementTypeStatus");
        	}
        	stringQuery.append(" order by entity.typeElementName asc ");
        	Query query = session.createQuery(stringQuery.toString());
        	if(isSerialized!=null){
        		if(isSerialized){
        			query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        		}else{
        			query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
        		}
        	}
        	if(elementModelCode!=null && !elementModelCode.trim().equalsIgnoreCase("")){
        		query.setString("elementModelCode", elementModelCode);
        	}
        	if(elementTypeStatus!=null){
        		if(elementTypeStatus){
        			query.setString("elementTypeStatus", CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE.getCodeEntity());
        		}else{
        			query.setString("elementTypeStatus", CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_INACTIVE.getCodeEntity());
        		}
        	}
        	List<ElementType> elementTypeList = query.list();	
			return elementTypeList;
        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypesByElementModelIdAndAllStatusPage(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementTypeResponse getElementTypesByElementModelIdAndAllStatusPage(
			Long idElementModel, String code,RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where 1 = 1");
        	if(idElementModel!=-1){
        		stringQuery.append(" and entity.elementModel.id = :elementModelId");
        	}
        	if(code!=null){
        		stringQuery.append(" and entity.typeElementCode = :typeElementCode");
        	}
        	
        	stringQuery.append(" order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	if(idElementModel!=-1){
        		query.setLong("elementModelId", idElementModel);
        	}
        	if(code!=null){
        		query.setString("typeElementCode", code);
        	}
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(idElementModel!=-1){
        			queryCount.setLong("elementModelId", idElementModel);
        		}
        		if(code!=null){
        			queryCount.setString("typeElementCode", code);
            	}
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementTypeResponse response = new ElementTypeResponse();
        	List<ElementType> elementTypeList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementTypeList.size(), recordQty.intValue() );
        	response.setElementType(elementTypeList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal#getElementTypesByPrepaidIdAndActiveStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ElementTypeResponse getElementTypesByPrepaidIdAndActiveStatusPage(
			String prepaid, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" entity.isActive = :isActiveType");
        	stringQuery.append(" and entity.elementModel.isActive = :isActiveModel");
        	stringQuery.append(" and entity.elementModel.isPrepaidModel = :isPrepaidModel");
        	
        	stringQuery.append(" order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isActiveType", CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE.getCodeEntity());
        	query.setString("isActiveModel", CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity());
        	query.setString("isPrepaidModel", prepaid.equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity()) ?  CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity():CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity());
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setString("isActiveType", CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE.getCodeEntity());
        		queryCount.setString("isActiveModel", CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity());
        		queryCount.setString("isPrepaidModel", prepaid.equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity()) ?  CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity():CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity());
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementTypeResponse response = new ElementTypeResponse();
        	List<ElementType> elementTypeList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementTypeList.size(), recordQty.intValue() );
        	response.setElementType(elementTypeList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModelIdAndStatus/ElementModelDAO ==");
        }
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<ElementType> getElementTypeByElementModelAndIsSerialized(
			String elementModelCode, boolean isSerialized)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByElementModelAndIsSerilized/ElementModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName());
        	stringQuery.append(" entity where entity.elementModel.modelCode = :elementModelCode ");
        	stringQuery.append(" and entity.isSerialized = :isSerialized");
        	stringQuery.append(" order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("elementModelCode", elementModelCode);
            if(isSerialized){
            	query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
            }else{
            	query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
            }
            return query.list();

        } catch (Throwable ex){
			log.error("== Error getElementTypeByElementModelAndIsSerilized/ElementModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByElementModelAndIsSerilized/ElementModelDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ElementType> getElementTypeByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementTypeByWarehouseIdAndElementModelId/ElementTypeDAO ==");
        Session session = super.getSession();
        String strWhereOrAnd = " where ";
        boolean warehouseIdSpecified=false,elementModelIdSpecified=false;
        long lRecorStatusId=0L;
        try {
        	StringBuffer stringQuery = new StringBuffer();
			
			if(warehouseId!=null && warehouseId.longValue() > 0) {
				warehouseIdSpecified=true;
				
				stringQuery.append("SELECT DISTINCT elementtyp4_.* FROM WAREHOUSE_ELEMENTS warehousee0_ ");
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
				
				stringQuery.append(" from ").append(ElementType.class.getName()).append(" entity ");
				
				if(elementModelId!=null && elementModelId.longValue() > 0) {
				elementModelIdSpecified=true;
				stringQuery.append(strWhereOrAnd).append(" entity.elementModel.id=:aElementModelId ");
				strWhereOrAnd = " and ";
				}
			}
			
			Query query = null;
			
			if(warehouseIdSpecified){
				query = session.createSQLQuery(stringQuery.toString()).addEntity(ElementType.class);
			}else{
				query = session.createQuery(stringQuery.toString());
			}
			if(elementModelIdSpecified)
				query.setLong("aElementModelId", elementModelId);
            
			if(warehouseIdSpecified){
				query.setLong("aWarehouseId", warehouseId);
				query.setLong("recorStatusId",lRecorStatusId);
			}
			
            return query.list();

        } catch (Throwable ex){
			log.error("== Error getElementTypeByWarehouseIdAndElementModelId/ElementTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeByWarehouseIdAndElementModelId/ElementTypeDAO ==");
        }
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<ElementType> getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(
			String isPrepaid, String isSerialized) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeDAO ==");
        Session session = super.getSession();

        try {
        	boolean isPrep = (isPrepaid != null && !isPrepaid.equals(""));
        	boolean isSer = (isSerialized != null && !isSerialized.equals(""));
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementType.class.getName()+" entity where entity.isActive = :isActive");
        	if(isSer){
        		stringQuery.append(" and entity.isSerialized = :isSerialized");
        	}
        	if(isPrep){
        		stringQuery.append("  and entity.elementModel.isPrepaidModel = :isPrepaidModel ");	
        	}
        	stringQuery.append(" order by entity.typeElementName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isActive", CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE.getCodeEntity());
            
            if(isSer){
            	query.setString("isSerialized", isSerialized);
            }
            if(isPrep){
            	query.setString("isPrepaidModel", isPrepaid);
            }
            return query.list();

        } catch (Throwable ex){
			log.error("== Error getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeDAO == ");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeDAO ==");
        }
	}

}
