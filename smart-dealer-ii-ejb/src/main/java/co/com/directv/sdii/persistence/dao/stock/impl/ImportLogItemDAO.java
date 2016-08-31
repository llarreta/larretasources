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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogItemDTO;
import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.reports.dto.CountItemStatusImportLogDTO;
import co.com.directv.sdii.reports.dto.SerializedElementImportLogDTO;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ImportLogItem
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImportLogItem
 * @see co.com.directv.sdii.model.hbm.ImportLogItem.hbm.xml
 */
@Stateless(name="ImportLogItemDAOLocal",mappedName="ejb/ImportLogItemDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogItemDAO extends BaseDao implements ImportLogItemDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ImportLogItemDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemsDAOLocal#createImportLogItem(co.com.directv.sdii.model.pojo.ImportLogItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createImportLogItem(ImportLogItem obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createImportLogItem/ImportLogItemDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ImportLogItem ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogItem/ImportLogItemDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemsDAOLocal#updateImportLogItem(co.com.directv.sdii.model.pojo.ImportLogItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImportLogItem(ImportLogItem obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateImportLogItem/ImportLogItemDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ImportLogItem ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLogItem/ImportLogItemDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemsDAOLocal#deleteImportLogItem(co.com.directv.sdii.model.pojo.ImportLogItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImportLogItem(ImportLogItem obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImportLogItem/ImportLogItemDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImportLogItem entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ImportLogItem ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogItem/ImportLogItemDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemsDAOLocal#deleteImportLogItem(co.com.directv.sdii.model.pojo.ImportLogItem)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImportLogItemsFromImportLog(Long importLogId) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImportLogItemsFromImportLog/ImportLogItemDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from "+ImportLogItem.class.getName()+" entity where entity.importLog.id = :importLogId");
            query.setLong("importLogId", importLogId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando ImportLogItems ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogItemsFromImportLog/ImportLogItemDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemsDAOLocal#getImportLogItemsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogItem getImportLogItemByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImportLogItemByID/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ImportLogItem) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemByID/ImportLogItemDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemsDAOLocal#getAllImportLogItems()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLogItem> getAllImportLogItems() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImportLogItems/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImportLogItems/ImportLogItemDAO ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogItemResponse getImportLogItemsByImportLog(Long pImportLog, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getImportLogItemsByImportLog/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	String select="select ili, " 
        		           + "(select nvl(sum(entity.confirmedQuantity),0D) from "+ImpLogConfirmation.class.getName()+" entity where entity.importLogItem.id = ili.id), " 
        		           + "(select ser from "+Serialized.class.getName()+" ser where ser.elementId = ili.element.id), " 
        		           + "(select nser from "+NotSerialized.class.getName()+" nser where nser.elementId = ili.element.id)";
        	stringQuery.append(" from ");
        	stringQuery.append(ImportLogItem.class.getName()); 
        	stringQuery.append(" ili where ili.importLog.id = :pImportLog");
        	stringQuery.append(" and ili.element.id not in (SELECT s.serialized.elementId FROM "+Serialized.class.getName()+"  s where s.serialized.elementId is not null ) ");
        	stringQuery.append(" and ili.itemStatus.statusCode <> '"+CodesBusinessEntityEnum.ITEM_STATUS_DELETED.getCodeEntity()+"' ");
        	stringQuery.append(" order by ili.id asc ");
        	
        	Query query = null;
        	query = session.createQuery(select+stringQuery.toString());
        	query.setLong("pImportLog", pImportLog);
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	 
        		stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("pImportLog", pImportLog);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();
        	List result = query.list();
        	List<ImportLogItem> importLogItems = new ArrayList<ImportLogItem>(result.size());
        	List<ImportLogItemVO> importLogItemsVO = new ArrayList<ImportLogItemVO>(result.size());
        	for(int i=0; i<result.size(); ++i){
        		ImportLogItem ili = (ImportLogItem)((Object[])result.get(i))[0];
        		Double iliSum = (Double)((Object[])result.get(i))[1];
        		ImportLogItemVO iliVO = (ImportLogItemVO)UtilsBusiness.copyObject(ImportLogItemVO.class, ili);
        		iliVO.setConfirmedQuantity(iliSum);
        		if(((Object[])result.get(i))[2] != null){
        			iliVO.setSerializedVO(UtilsBusiness.copyObject(SerializedVO.class, (Serialized)((Object[])result.get(i))[2]));
        		}
        		if(((Object[])result.get(i))[3]!=null){
        			iliVO.setNotSerializedVO(UtilsBusiness.copyObject(NotSerializedVO.class, (NotSerialized)((Object[])result.get(i))[3]));
        		}
        		importLogItemsVO.add(iliVO);
        		importLogItems.add(ili);
        	}
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLogItems.size(), recordQty.intValue() );
        	response.setImportLogItems( importLogItems );        	
        	response.setImportLogItemsVO(importLogItemsVO);
        	return response;            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemsByImportLog/ImportLogItemDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogItemResponse getImportLogItemsAndSumByImportLog(Long pImportLog, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getImportLogItemsAndSumByImportLog/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName()); 
        	stringQuery.append(" ili where ili.importLog.id = :pImportLog");
        	stringQuery.append(" and ili.element.id not in (SELECT s.serialized.elementId FROM "+Serialized.class.getName()+"  s where s.serialized.elementId is not null ) ");
        	stringQuery.append(" and ili.itemStatus.statusCode <> '"+CodesBusinessEntityEnum.ITEM_STATUS_DELETED.getCodeEntity()+"' ");
        	
        	Query query = null;
        	query = session.createQuery(stringQuery.toString());
        	query.setLong("pImportLog", pImportLog);
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	 
        		stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("pImportLog", pImportLog);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();
        	List<ImportLogItem> importLogItems = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLogItems.size(), recordQty.intValue() );
        	response.setImportLogItems( importLogItems );        	
        	
        	return response;            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemsAndSumByImportLog/ImportLogItemDAO ==");
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogItemResponse getImportLogItemsByCriteria(ModifyImportLogItemDTO modifyImportLogItemCriteria, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getImportLogItemsByCriteria/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName()); 
        	stringQuery.append(" ili where ili.importLog.id = :pImportLog");
        	
        	if (modifyImportLogItemCriteria.getImportLogStatusId() != null){
        		stringQuery.append(" and ili.itemStatus.id = :pImportLogStatusId");
        	}
        	
        	if (modifyImportLogItemCriteria.getIsSerialized() != null){
        		stringQuery.append(" and ili.element.isSerialized = :pIsSerialized");
        	}
        	
        	if (modifyImportLogItemCriteria.getSerialNumber() != null){
        		Serialized ser = getSerializedBySerial(modifyImportLogItemCriteria.getSerialNumber());
        		stringQuery.append(" and ili.element.id = " + ser.getElementId() );
        	}
        	
        	Query query = null;
        	query = session.createQuery(stringQuery.toString());
        	query.setLong("pImportLog", modifyImportLogItemCriteria.getImportLogId());
        	
        	if (modifyImportLogItemCriteria.getImportLogStatusId() != null){
        		query.setString("pImportLogStatusId", modifyImportLogItemCriteria.getImportLogStatusId());
        	}
        	
        	if (modifyImportLogItemCriteria.getIsSerialized() != null){
        		query.setString("pIsSerialized", modifyImportLogItemCriteria.getIsSerialized());
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	 
        		stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("pImportLog", modifyImportLogItemCriteria.getImportLogId());
            	
            	if (modifyImportLogItemCriteria.getImportLogStatusId() != null){
            		countQuery.setString("pImportLogStatusId", modifyImportLogItemCriteria.getImportLogStatusId());
            	}
            	
            	if (modifyImportLogItemCriteria.getIsSerialized() != null){
            		countQuery.setString("pIsSerialized", modifyImportLogItemCriteria.getIsSerialized());
            	}
            	
            	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();
        	List<ImportLogItem> importLogItems = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLogItems.size(), recordQty.intValue() );
        	response.setImportLogItems( importLogItems );        	
        	
        	return response;            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemsByCriteria/ImportLogItemDAO ==");
        }
    }
    
   
	private Serialized getSerializedBySerial(String serial)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedBySerial/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" entity where entity.serialCode = :aSerial");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aSerial", serial);

            return (Serialized) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerial/SerializedDAO ==");
        }
	}




	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal#getImportLogItemsByImportLogIdAndIsSerialized(java.lang.Long, boolean)
	 */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			Long importLogId, boolean isSerialized, RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		
		log.debug("== Inicia getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" ili where ili.importLog.id = :anImpLogId and ili.element.isSerialized = :anIsSerialized");
        	stringQuery.append(" and ili.element.id not in (SELECT s.serialized.elementId FROM "+Serialized.class.getName()+"  s where s.serialized.elementId is not null ) ");
        	stringQuery.append(" and ili.itemStatus.statusCode <> '"+CodesBusinessEntityEnum.ITEM_STATUS_DELETED.getCodeEntity()+"' ");
        	Query query = null;
        	query = session.createQuery(stringQuery.toString());
        	query.setLong("anImpLogId", importLogId);
        	String strSerialized = "";
        	if(isSerialized){
        		strSerialized = CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity();
        	}else{
        		strSerialized = CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity();
        	}
        	query.setString("anIsSerialized", strSerialized);
        	
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	 
        		stringCount.append("select count(*) ");
        		stringCount.append( stringQuery.toString() );
        		Query countQuery = session.createQuery( stringCount.toString() );
        		countQuery.setLong("anImpLogId", importLogId);
            	countQuery.setString("anIsSerialized", strSerialized);
            	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );	
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();        	
            List<ImportLogItem> list = query.list(); 
            if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
            response.setImportLogItems( list );
        	
            return response;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemDAO ==");
        }
	}
    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ImportLogItemResponse getImportLogItemsByImportLogIdStatusAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements, RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		
		log.debug("== Inicia getImportLogItemsByImportLogIdStatusAndIsSerialized/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	boolean isSerialCode = filterImportLogElements.getSerialCode()!=null&&!filterImportLogElements.getSerialCode().isEmpty();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" ili where ili.importLog.id = :anImpLogId and ili.element.isSerialized = :anIsSerialized ");
        	if(isSerialCode){
        		stringQuery.append(" and exists (select 1 from Serialized ser where ser.elementId = ili.element.id and UPPER(ser.serialCode) = TRIM(UPPER(:serialCode)))");
        	}
        	
        	if(filterImportLogElements.getItemStatus() != null && filterImportLogElements.getItemStatus().trim().length() > 0){
        		stringQuery.append("and ili.itemStatus.statusCode in ( "+filterImportLogElements.getItemStatus()+" ) ");
        	}
        	
        	stringQuery.append(" and ili.itemStatus.statusCode <> '"+CodesBusinessEntityEnum.ITEM_STATUS_DELETED.getCodeEntity()+"' ");
        	
        	
        	Query query = null;
        	String orderBy = " order by ili.id asc ";
        	query = session.createQuery(stringQuery.toString() + orderBy);
        	query.setLong("anImpLogId", filterImportLogElements.getImportLogID());
        	String strSerialized = "";
        	if(filterImportLogElements.isSerialized()){
        		strSerialized = CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity();
        	}else{
        		strSerialized = CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity();
        	}
        	query.setString("anIsSerialized", strSerialized);
        	if(isSerialCode){
        		query.setString("serialCode", filterImportLogElements.getSerialCode());	
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if(requestCollInfo != null){	 
        		stringCount.append("select count(*) ");
        		stringCount.append(stringQuery.toString());
        		Query countQuery = session.createQuery(stringCount.toString());
        		countQuery.setLong("anImpLogId", filterImportLogElements.getImportLogID());
            	countQuery.setString("anIsSerialized", strSerialized);
            	if(isSerialCode){
            		countQuery.setString("serialCode", filterImportLogElements.getSerialCode());	
            	}
            	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());	
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();        	
            List<ImportLogItem> list = query.list(); 
            if(requestCollInfo != null){
            	populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue());
            }
            response.setImportLogItems(list);
        	
            return response;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemsByImportLogIdStatusAndIsSerialized/ImportLogItemDAO ==");
        }
	}
    
    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    //Modificado para Requerimiento: CC057
	public List<SerializedElementImportLogDTO> getImportLogItemsByImportLogIdAndIsSerializedForExcel(
			ImportLogElementsFilterDTO filterImportLogElements) throws DAOServiceException,
			DAOSQLException {
		
		log.debug("== Inicia getImportLogItemsByImportLogIdAndIsSerializedForExcel/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringSqlQuery = new StringBuffer();
        	
        	boolean isSerialCode = filterImportLogElements.getSerialCode()!=null&&!filterImportLogElements.getSerialCode().isEmpty();
        	
        	stringSqlQuery.append(" 	SELECT ET.TYPE_ELEMENT_NAME AS elementType,	 ");
        	stringSqlQuery.append(" 	       EM.MODEL_NAME AS modelName,	 ");
        	stringSqlQuery.append(" 	       EC.ELEMENT_CLASS_NAME AS className,	 ");
        	stringSqlQuery.append(" 	       EB.BRAND_NAME AS brandName,	 ");
        	stringSqlQuery.append(" 	       MU.UNIT_NAME AS measureUnitName,	 ");
        	stringSqlQuery.append(" 	       SER.SERIAL_CODE AS serialCode,	 ");
        	stringSqlQuery.append(" 	       SER.IRD AS rid,	 ");
        	stringSqlQuery.append(" 	       ET2.TYPE_ELEMENT_NAME AS elementTypeLinked,	 ");
        	stringSqlQuery.append(" 	       SER2.SERIAL_CODE AS serialLinked,	 ");        	
        	stringSqlQuery.append(" 	       ET.TYPE_ELEMENT_CODE AS elementTypeCode ");
        	stringSqlQuery.append(" 	  FROM IMPORT_LOG_ITEMS ili	 ");
        	stringSqlQuery.append(" 	       INNER JOIN ELEMENTS el	 ");
        	stringSqlQuery.append(" 	          ON EL.ID = ILI.ELEMENT_ID	 ");
        	stringSqlQuery.append(" 	       INNER JOIN SERIALIZED ser	 ");
        	stringSqlQuery.append(" 	          ON SER.ELEMENT_ID = EL.ID	 ");
        	stringSqlQuery.append(" 	       INNER JOIN ELEMENT_TYPES et	 ");
        	stringSqlQuery.append(" 	          ON ET.ID = EL.ELEMENT_TYPE_ID	 ");
        	stringSqlQuery.append(" 	       INNER JOIN ELEMENT_MODELS em	 ");
        	stringSqlQuery.append(" 	          ON EM.ID = ET.ELEMENT_MODEL_ID	 ");
        	stringSqlQuery.append(" 	       INNER JOIN ELEMENT_CLASSES ec	 ");
        	stringSqlQuery.append(" 	          ON ec.id = EM.ELEMENT_CLASS_ID	 ");
        	stringSqlQuery.append(" 	       INNER JOIN ELEMENT_BRANDS EB	 ");
        	stringSqlQuery.append(" 	          ON EB.ID = ET.ELEMENT_BRAND_ID	 ");
        	stringSqlQuery.append(" 	       INNER JOIN MEASURE_UNITS MU	 ");
        	stringSqlQuery.append(" 	          ON MU.ID = ET.MEASURE_UNIT_ID	 ");
        	stringSqlQuery.append(" 	       LEFT OUTER JOIN SERIALIZED SER2	 ");
        	stringSqlQuery.append(" 	          ON SER2.ELEMENT_ID = SER.LINKED_SERIAL_CODE	 ");
        	stringSqlQuery.append(" 	       LEFT OUTER JOIN ELEMENTS EL2	 ");
        	stringSqlQuery.append(" 	          ON EL2.ID = SER2.ELEMENT_ID	 ");
        	stringSqlQuery.append(" 	       LEFT OUTER JOIN ELEMENT_TYPES ET2	 ");
        	stringSqlQuery.append(" 	          ON ET2.ID = EL2.ELEMENT_TYPE_ID	 ");
        	stringSqlQuery.append("WHERE ILI.IMPORT_LOG_ID = :anImpLogId ");
        	stringSqlQuery.append("AND EL.IS_SERIALIZED = :anIsSerialized ");
        	stringSqlQuery.append("AND ((SER.SERIAL_CODE = :serialCode or :serialCode is null) or (SER2.SERIAL_CODE = :serialCode or :serialCode is null)) ");
        	
        	

        	
        	/*stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" ili where ili.importLog.id = :anImpLogId and ili.element.isSerialized = :anIsSerialized ");
        	if(isSerialCode){
        		stringQuery.append(" and exists (select 1 from Serialized ser where ser.elementId = ili.element.id and UPPER(ser.serialCode) = TRIM(UPPER(:serialCode)))");
        	}
        	
        	if(filterImportLogElements.getItemStatus() != null && filterImportLogElements.getItemStatus().trim().length() > 0){
        		stringQuery.append("and ili.itemStatus.statusCode in ( "+filterImportLogElements.getItemStatus()+" ) ");
        	}
        	
        	stringQuery.append(" and ili.itemStatus.statusCode <> '"+CodesBusinessEntityEnum.ITEM_STATUS_DELETED.getCodeEntity()+"' ");
        	*/
        	
        	
        	Query query = null;
        	String orderBy = " ORDER BY ILI.ID ASC ";
        	query = session.createSQLQuery(stringSqlQuery.toString() + orderBy)
        		.addScalar("elementType")
        		.addScalar("modelName")
        		.addScalar("className")
        		.addScalar("brandName")
        		.addScalar("measureUnitName")
        		.addScalar("serialCode")
        		.addScalar("rid")
        		.addScalar("elementTypeLinked")
        		.addScalar("serialLinked")
        		.addScalar("elementTypeCode")
        		.setResultTransformer(Transformers.aliasToBean(SerializedElementImportLogDTO.class));
        	String strSerialized = "";
        	if(filterImportLogElements.isSerialized()){
        		strSerialized = CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity();
        	}else{
        		strSerialized = CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity();
        	}
        	query.setLong("anImpLogId", filterImportLogElements.getImportLogID());
        	query.setString("anIsSerialized", strSerialized);
        	query.setString("serialCode", filterImportLogElements.getSerialCode());
        		
            List<SerializedElementImportLogDTO> list = query.list(); 
            
            return list;
            
        } catch (Throwable ex){
			log.error("== Error == getImportLogItemsByImportLogIdAndIsSerializedForExcel/ImportLogItemDAO");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerializedForExcel/ImportLogItemDAO ==");
        }
	}
    

    @Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogItem> getImpLogItemsByIndividualElements(Long importLogId) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getImpLogItemsByIndividualElements/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	//stringQuery.append(" implogItem where implogItem.element.isSerialized = :pSerialized");
        	//stringQuery.append(" and implogItem.importLog.id = :pImpLogId ");
        	stringQuery.append(" implogItem where ");
        	stringQuery.append(" implogItem.importLog.id = :pImpLogId ");
        	stringQuery.append(" and implogItem.element.elementStatus.elementStatusCode <> :pStatus");
        	
        	Query query = null;
        	query = session.createQuery(stringQuery.toString());
        	//query.setString("pSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        	query.setLong("pImpLogId", importLogId);
        	query.setString("pStatus", CodesBusinessEntityEnum.ELEMENT_STATUS_DUMMY.getCodeEntity());
        	        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getImpLogItemsByIndividualElements/ImportLogItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogItemsByIndividualElements/ImportLogItemDAO ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItemResponse getImpLogItemsByIndividualElementsSerialized(Long importLogId,RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getImpLogItemsByIndividualElementsSerialized/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" implogItem where implogItem.element.isSerialized = :pSerialized");
        	stringQuery.append(" and implogItem.importLog.id = :pImpLogId ");
        	stringQuery.append(" and implogItem.itemStatus.statusCode in ( :pEnviado, :pConfParcial )");
        	
        	Query query = null;
        	query = session.createQuery(stringQuery.toString());
        	query.setString("pSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        	query.setLong("pImpLogId", importLogId);
        	query.setString("pEnviado",CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
        	query.setString("pConfParcial",CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        	
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
            	stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setString("pSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
            	countQuery.setLong("pImpLogId", importLogId);
            	countQuery.setString("pEnviado",CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
            	countQuery.setString("pConfParcial",CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();        	
            List<ImportLogItem> list = query.list(); 
            if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setImportLogItems( list );
        	
        	        	
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error getImpLogItemsByIndividualElementsSerialized/ImportLogItemDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogItemsByIndividualElementsSerialized/ImportLogItemDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal#getImportLogItemByElementId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItem getImportLogItemByElementId(Long elementId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getImportLogItemByID/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" entity where entity.element.id = :anElementId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anElementId", elementId);

            return (ImportLogItem) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemByID/ImportLogItemDAO ==");
        }
	}
    
	public ImportLogItemResponse getElementsInImportLog(Long importLogId,boolean serialized, RequestCollectionInfo requestCollInfo )throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicio getElementsInImportLog/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	String isSerialized = serialized?CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity():CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity();
        	
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append( " entity where entity.element.isSerialized =:isSerialized" );
        	stringQuery.append( " and entity.importLog.id =:importLogId" );
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setParameter( "isSerialized" , isSerialized);
        	query.setParameter( "importLogId" , importLogId );
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
            	stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setParameter( "isSerialized" , isSerialized);
            	countQuery.setParameter( "importLogId" , importLogId );
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();        	
            List<ImportLogItem> list = query.list(); 
            if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setImportLogItems( list );
            
            return response;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementsInImportLog/ImportLogItemDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal#updateImportLogItemsStatusByImportLogId(java.lang.Long, co.com.directv.sdii.model.pojo.ItemStatus, co.com.directv.sdii.model.pojo.ItemStatus)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImportLogItemsStatusByImportLogId(Long importLogId,
			ItemStatus previousitemStatus, ItemStatus newitemStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateImportLogItemsStatusByImportLogId/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("update ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append( " entity set entity.itemStatus = :newItemStatus" );
        	stringQuery.append( " where entity.importLog.id = :anImpLogId and entity.itemStatus.id =:oldItemStatusId" );
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setParameter( "newItemStatus" , newitemStatus);
            query.setParameter("anImpLogId", importLogId);
        	query.setParameter( "oldItemStatusId" , previousitemStatus.getId() );
        	query.executeUpdate();
           
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina updateImportLogItemsStatusByImportLogId/ImportLogItemDAO ==");
        }
	}
	
	/**
	 * Metodo: Se encarga de validar si existe algún elemento del registro de importacion en
	 *         estado inconsistente
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean isImportLogItemStatus(Long importLogId, String statusCode ) throws DAOServiceException, DAOSQLException {
		
		boolean retorno = false;
		
		log.debug("== Inicio isImportLogItemInconsisten/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append( " entity WHERE entity.importLog.id = :importLogId  " );
        	stringQuery.append( " AND entity.itemStatus.statusCode = :statusCode  " );
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setParameter( "importLogId" , importLogId);
            query.setParameter( "statusCode" , statusCode);
            
            List<ImportLogItem> list = query.list();
            
            if (list != null && list.size() > 0 ){
            	retorno = true;
            }

            return retorno;
           
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina isImportLogItemInconsisten/ImportLogItemDAO ==");
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long conteoConfirmadas(Long importLogId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio conteoConfirmadas/ImportLogItemDAO ==");
        try {
        	Session session = super.getSession();
        	StringBuffer queryBuffer = new StringBuffer();
        	queryBuffer.append("SELECT COUNT( DISTINCT(il.ID) ) AS conteo FROM IMPORT_LOGS il INNER JOIN IMPORT_LOG_ITEMS ili ON il.ID=ili.IMPORT_LOG_ID INNER JOIN IMPORT_LOG_CONFIRMATIONS ilc ON ili.ID=ilc.IMP_LOG_ITEM_ID WHERE il.ID= :importLogId");
        	Query query = session.createSQLQuery( queryBuffer.toString() );
        	query.setLong("importLogId", importLogId);
        	return ( (BigDecimal) query.uniqueResult() ).longValue();
        	
        }catch (Throwable ex) {
            log.debug("== Error conteoConfirmadas/ImportLogItemDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina conteoConfirmadas/ImportLogItemDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Long countByStatus(Long importLogId, ItemStatus itemStatus) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio countByStatus/ImportLogItemDAO ==");
        try {
        	Session session = super.getSession();
        	StringBuffer queryBuffer = new StringBuffer();
        	queryBuffer.append("SELECT COUNT( DISTINCT(ili.ID) ) FROM IMPORT_LOGS il INNER JOIN IMPORT_LOG_ITEMS ili ON il.ID=ili.IMPORT_LOG_ID WHERE il.ID= :importLogId AND ili.ITEM_STATUS_ID= :status");
        	Query query = session.createSQLQuery( queryBuffer.toString() );
        	query.setLong("importLogId", importLogId);
        	query.setLong("status", itemStatus.getId());
        	return ( (BigDecimal) query.uniqueResult() ).longValue();
        	
        }catch (Throwable ex) {
            log.debug("== Error countByStatus/ImportLogItemDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina countByStatus/ImportLogItemDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogItemResponse getImportLogItemsByImportLogAll(Long pImportLog, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getImportLogItemsByImportLog/ImportLogItemDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName()); 
        	stringQuery.append(" ili where ili.importLog.id = :pImportLog");
        	
        	Query query = null;
        	query = session.createQuery(stringQuery.toString());
        	query.setLong("pImportLog", pImportLog);
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if(requestCollInfo != null){	 
        		stringCount.append("select count(*) ");
            	stringCount.append(stringQuery.toString());        	
            	Query countQuery = session.createQuery(stringCount.toString());
            	countQuery.setLong("pImportLog", pImportLog);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());				
        	}
        	
        	ImportLogItemResponse response = new ImportLogItemResponse();
        	List<ImportLogItem> importLogItems = query.list();
        	if(requestCollInfo != null){
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLogItems.size(), recordQty.intValue());
        	}
        	response.setImportLogItems(importLogItems);        	
        	
        	return response;            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogItemsByImportLog/ImportLogItemDAO ==");
        }
    }



	@Override
	public List<CountItemStatusImportLogDTO> getCountItemStatusForImportLog(
			Long importLogId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getCountItemStatusForImportLog/ImportLogItemDAO ==");
        try {
        	Session session = super.getSession();
        	StringBuffer queryBuffer = new StringBuffer();
        	queryBuffer.append("select new ");
        	queryBuffer.append(CountItemStatusImportLogDTO.class.getName());
        	queryBuffer.append("(status.statusCode, count(*)) FROM ImportLogItem ili ");
        	queryBuffer.append(" inner join ili.itemStatus status ");
        	queryBuffer.append(" where ili.importLog.id = :importLogId ");
        	queryBuffer.append(" group by status.statusCode  ");
        	Query query = session.createQuery( queryBuffer.toString() );
        	query.setLong("importLogId", importLogId);
        	return query.list();
        }catch (Throwable ex) {
            log.debug("== Error getCountItemStatusForImportLog/ImportLogItemDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCountItemStatusForImportLog/ImportLogItemDAO ==");
        }
	}
}
