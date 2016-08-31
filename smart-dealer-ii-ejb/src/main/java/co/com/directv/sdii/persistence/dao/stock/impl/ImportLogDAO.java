package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ImportLogStatus;
import co.com.directv.sdii.model.pojo.Supplier;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectImportLogToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal;
import co.com.directv.sdii.reports.dto.ImportLogDTO;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ImportLog
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImportLog
 * @see co.com.directv.sdii.model.hbm.ImportLog.hbm.xml
 */
@Stateless(name="ImportLogDAOLocal",mappedName="ejb/ImportLogDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogDAO extends BaseDao implements ImportLogDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ImportLogDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#createImportLog(co.com.directv.sdii.model.pojo.ImportLog)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createImportLog(ImportLog obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createImportLog/ImportLogDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ImportLog ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLog/ImportLogDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#updateImportLog(co.com.directv.sdii.model.pojo.ImportLog)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImportLog(ImportLog obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateImportLog/ImportLogDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ImportLog ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLog/ImportLogDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#deleteImportLog(co.com.directv.sdii.model.pojo.ImportLog)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImportLog(ImportLog obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImportLog/ImportLogDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImportLog entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ImportLog ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLog/ImportLogDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogByIDAndByLogisticOp(java.lang.Long, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLog getImportLogByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImportLogByID/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ImportLog) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getImportLogByID/ImportLogDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogByIDAndByLogisticOp/ImportLogDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogByIDAndByLogisticOp(java.lang.Long, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLog getImportLogByIDAndByLogisticOp(Long id, Long LogisticOpId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImportLogByIDAndByLogisticOp/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	stringQuery.append(" and entity.dealer.id = :dealerId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);
            query.setLong("dealerId", LogisticOpId);

            return (ImportLog) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getImportLogByIDAndByLogisticOp/ImportLogDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogByIDAndByLogisticOp/ImportLogDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getAllImportLogs()
     */
    @SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLog> getAllImportLogs() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImportLogs/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLog.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImportLogs/ImportLogDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getAllImportLogsByLogisticOp(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLog> getAllImportLogsByLogisticOp(Long logisticOpId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImportLogsByLogisticOp/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" entity where entity.dealer.id = :logisticOpId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("logisticOpId", logisticOpId);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getAllImportLogsByLogisticOp/ImportLogDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImportLogsByLogisticOp/ImportLogDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogsToConfirm(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public ImportLogResponse getImportLogsToConfirm(Long dealerId, Long elementTypeId, String serialCode, Long impLogId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getImportLogsToConfirm/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringBody = new StringBuffer("from ");
        	StringBuffer stringSubBody = new StringBuffer("select ser.elementId from Serialized ser join ser.element elem ");
        	
        	stringQuery.append("select item.importLog ");
        	stringBody.append(ImportLogItem.class.getName());
        	stringBody.append(" item ");
        	stringBody.append(" join item.importLog il ");
        	stringBody.append(" where (il.importLogStatus.statusCode = ? OR il.importLogStatus.statusCode = ? OR il.importLogStatus.statusCode = ?) and il.dealer.id = ? ");
        	
        	if (impLogId != null && impLogId.longValue() > 0L)
        		stringBody.append(" and il.id = :impLogId ");
        	
        	Query query = null;
        	System.out.println(stringBody.toString());
        	stringQuery.append(stringBody.toString());
        	query = session.createQuery(stringQuery.toString());
        	query.setString(0, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity());
        	query.setString(1, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        	query.setString(2, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity());
        	query.setLong(3, dealerId);
        	if (impLogId != null && impLogId.longValue() > 0L)
            	query.setLong("impLogId", impLogId);
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	
        		stringCount.append("select count(*) ");
            	stringCount.append( stringBody.toString() );  
            	System.out.println(stringCount.toString());
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setString(0, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity());
            	countQuery.setString(1, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity());
            	countQuery.setLong(3, dealerId);
            	if (impLogId != null && impLogId.longValue() > 0L)
                	query.setLong("impLogId", impLogId);
            	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogResponse response = new ImportLogResponse();
        	List<ImportLog> list = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setImportLog( list );
        	
        	return response;
            
        } catch (Throwable ex){
            log.error("== Error getImportLogsToConfirm/ImpLogConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogsToConfirm/ImpLogConfirmationDAO ==");
        }
    }
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
	public ImportLogResponse getSerialCodeImportLogElement(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo)
	                                                                             throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSerialCodeImportLogElement/ImportLogDAO ==");
		StringBuffer stringBody; 
		StringBuffer stringSelect;
		StringBuffer stringCount;
		try {
			Session session = super.getSession();
			stringCount  = new StringBuffer("select count(*) \n");
			stringSelect = new StringBuffer("SELECT impl.id, impl.creation_date, usr.name, impl.shipping_date, ils.status_name, impl.import_doc, impl.purchase_order, 'S' as serializado, 'N' as noSerializado \n");
			stringBody   = new StringBuffer( "FROM   IMPORT_LOGS impl, users usr, import_log_status ils, import_log_items ili, elements e \n"+
											"WHERE  impl.import_log_status_id = ils.id \n"+
											"AND    ili.import_log_id = impl.id \n");
	                   if (modifyImportLogCriteria.getPurchaseOrder() != null){
	                	   stringBody.append( "AND    impl.purchase_order = :purchaseOrder \n");
	                   }
	                     
	                   if (modifyImportLogCriteria.getImportDoc() != null){
	                     stringBody.append( "AND    impl.import_doc = :importDoc \n");
	                   }
	                     
	                   if (modifyImportLogCriteria.getDealerId() != null){
	                     stringBody.append( "AND    impl.dealer_id = :dealerId \n");
	                   }

	                   stringBody.append( "AND    impl.user_id = usr.id \n"
			            		           +"AND    ili.element_id in ( SELECT ser.element_id \n"
			            		           +"                           FROM   serialized ser \n"
			            		           +"                           WHERE  ser.serial_code = :serialCode) \n");
			             
					   if (modifyImportLogCriteria.getElementTypeId() != null)					
						 stringBody.append( "AND    e.element_type_id = :elementTypeId \n");
			             
			             stringBody.append(	"AND    e.id = ili.element_id");
			
			SQLQuery query = session.createSQLQuery(stringSelect.toString() + stringBody.toString());

			if (modifyImportLogCriteria.getPurchaseOrder() != null)
			  query.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder());
			if (modifyImportLogCriteria.getImportDoc() != null)
			  query.setString("importDoc", modifyImportLogCriteria.getImportDoc());
			if (modifyImportLogCriteria.getDealerId() != null)
			  query.setLong("dealerId", modifyImportLogCriteria.getDealerId());
			//El codigo serial es obligatorio para este caso.
			query.setString("serialCode", modifyImportLogCriteria.getSerialNumber());
			if (modifyImportLogCriteria.getElementTypeId() != null)
			  query.setLong("elementTypeId", modifyImportLogCriteria.getElementTypeId());
			
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	
            	stringCount.append( stringBody.toString() );  
            	
            	Query countQuery = session.createSQLQuery( stringCount.toString() );
            	if (modifyImportLogCriteria.getPurchaseOrder() != null)
            	  countQuery.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder());
      			if (modifyImportLogCriteria.getImportDoc() != null)
      			  countQuery.setString("importDoc", modifyImportLogCriteria.getImportDoc());
      			if (modifyImportLogCriteria.getDealerId() != null)
      			  countQuery.setLong("dealerId", modifyImportLogCriteria.getDealerId());
      			//El codigo serial es obligatorio para este caso.
      			countQuery.setString("serialCode", modifyImportLogCriteria.getSerialNumber());
      			if (modifyImportLogCriteria.getElementTypeId() != null)
      			  countQuery.setLong("elementTypeId", modifyImportLogCriteria.getElementTypeId());
            	
            	
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	List listImportLog = query.list();
			List<ImportLogDTO> importLogDTOList = new ArrayList<ImportLogDTO>();
			
			for (Object obj: listImportLog ){
				Object[] obja = (Object [])obj;
				ImportLogDTO importLogDTO_i = new ImportLogDTO();
				importLogDTO_i.setId( ((BigDecimal) obja[0]).longValue() );
				importLogDTO_i.setCreationDate((Timestamp) obja[1]);
				importLogDTO_i.setCreationUserName((String) obja[2]);
				importLogDTO_i.setDeliveryDate((Timestamp) obja[3] );
				importLogDTO_i.setDescripcionEstado((String) obja[4] );
				importLogDTO_i.setImportDoc((String) obja[5] );
				importLogDTO_i.setPurchaseOrder((String) obja[6] );
				String[] strSer = haveSerializedElementsImportLog(((BigDecimal) obja[0]).longValue());
				importLogDTO_i.setSerialized( strSer[0] );
				importLogDTO_i.setNotSerialized( strSer[1] );
				importLogDTOList.add(importLogDTO_i);
			}
			
			
			ImportLogResponse response = new ImportLogResponse();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLogDTOList.size(), recordQty.intValue() );
        	response.setImportLogDTO(importLogDTOList);
        	
        	return response;

		} catch(Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getSerialCodeImportLogElement/ImportLogDAO ==");
		}
	}
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
	public ImportLogResponse getImportLogsElement(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo)
	                                                                             throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getImportLogsElement/ImportLogDAO ==");
		StringBuffer stringBody; 
		StringBuffer stringSelect;
		StringBuffer stringCount;
		try {
			Session session = super.getSession();
			stringCount  = new StringBuffer("select count(*) \n");
			stringSelect = new StringBuffer("SELECT impl.id, impl.creation_date, usr.name, impl.shipping_date, ils.status_name, impl.import_doc, impl.purchase_order, 'S' as serializado, 'N' as noSerializado \n");
			stringBody   = new StringBuffer( "FROM   IMPORT_LOGS impl, users usr, import_log_status ils, import_log_items ili, elements e \n"+
											"WHERE  impl.import_log_status_id = ils.id \n"+
											"AND    ili.import_log_id = impl.id \n");
	                   if (modifyImportLogCriteria.getPurchaseOrder() != null)
	                     stringBody.append( "AND    impl.purchase_order = :purchaseOrder \n");
	                     
	                   if (modifyImportLogCriteria.getImportDoc() != null)
	                     stringBody.append( "AND    impl.import_doc = :importDoc \n");
	                     
	                   if (modifyImportLogCriteria.getDealerId() != null)
	                     stringBody.append( "AND    impl.dealer_id = :dealerId \n");
			                             
			             stringBody.append( "AND    impl.user_id = usr.id \n");
			             
					   if (modifyImportLogCriteria.getElementTypeId() != null)					
						 stringBody.append( "AND    e.element_type_id = :elementTypeId \n");
			             
			             stringBody.append(	"AND    e.id = ili.element_id");
			
			SQLQuery query = session.createSQLQuery(stringSelect.toString() + stringBody.toString());

			if (modifyImportLogCriteria.getPurchaseOrder() != null)
			  query.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder());
			if (modifyImportLogCriteria.getImportDoc() != null)
			  query.setString("importDoc", modifyImportLogCriteria.getImportDoc());
			if (modifyImportLogCriteria.getDealerId() != null)
			  query.setLong("dealerId", modifyImportLogCriteria.getDealerId());
			if (modifyImportLogCriteria.getElementTypeId() != null)
			  query.setLong("elementTypeId", modifyImportLogCriteria.getElementTypeId());
			
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	
            	stringCount.append( stringBody.toString() );  
            	
            	Query countQuery = session.createSQLQuery( stringCount.toString() );
            	if (modifyImportLogCriteria.getPurchaseOrder() != null)
            	  countQuery.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder());
      			if (modifyImportLogCriteria.getImportDoc() != null)
      			  countQuery.setString("importDoc", modifyImportLogCriteria.getImportDoc());
      			if (modifyImportLogCriteria.getDealerId() != null)
      			  countQuery.setLong("dealerId", modifyImportLogCriteria.getDealerId());
      			if (modifyImportLogCriteria.getElementTypeId() != null)
      			  countQuery.setLong("elementTypeId", modifyImportLogCriteria.getElementTypeId());
            	
            	
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	List listImportLog = query.list();
			List<ImportLogDTO> importLogDTOList = new ArrayList<ImportLogDTO>();
			
			for (Object obj: listImportLog ){
				Object[] obja = (Object [])obj;
				ImportLogDTO importLogDTO_i = new ImportLogDTO();
				importLogDTO_i.setId( ((BigDecimal) obja[0]).longValue() );
				importLogDTO_i.setCreationDate((Timestamp) obja[1]);
				importLogDTO_i.setCreationUserName((String) obja[2]);
				importLogDTO_i.setDeliveryDate((Timestamp) obja[3] );
				importLogDTO_i.setDescripcionEstado((String) obja[4] );
				importLogDTO_i.setImportDoc((String) obja[5] );
				importLogDTO_i.setPurchaseOrder((String) obja[6] );
				String[] strSer = haveSerializedElementsImportLog(((BigDecimal) obja[0]).longValue());
				importLogDTO_i.setSerialized( strSer[0] );
				importLogDTO_i.setNotSerialized( strSer[1] );
				importLogDTOList.add(importLogDTO_i);
			}
			
			
			ImportLogResponse response = new ImportLogResponse();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLogDTOList.size(), recordQty.intValue() );
        	response.setImportLogDTO(importLogDTOList);
        	
        	return response;

		} catch(Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogsElement/ImportLogDAO ==");
		}
	}
    
    private String[] haveSerializedElementsImportLog(Long idImportLog) throws DAOServiceException {
    	
    	Session session = super.getSession();
    	                  //Ser  NoSer
    	String[] retorno = {"N", "N"};
    	
    	StringBuffer stringQuery;
    	stringQuery = new StringBuffer("SELECT ili.id, e.is_serialized \n"
    									+ "FROM   import_log_items ili, elements e \n"
    									+ "WHERE  ili.import_log_id  = "+idImportLog.longValue()+" \n"
    									+ "AND    e.id = ili.element_id \n");
    	SQLQuery query = session.createSQLQuery(stringQuery.toString());
    	List listImportLogSer = query.list();
    	
    	for (Object obj: listImportLogSer){
			Object[] obja = (Object [])obj;
			String tmp = (String)obja[1];
			if (tmp.equals("S")){
				retorno[0] = "S";
				break;
			}
    	}	
			
		for (Object obj: listImportLogSer ){
			Object[] obja = (Object [])obj;
			String tmp = (String)obja[1];
			if (tmp.equals("N")){
				retorno[1] = "S";
				break;
			}	
		}		
    	
    	return retorno;
    	
    }
    

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogsToConfirm(java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public ImportLogResponse getImportLogsToConfirm(Long dealerId, Long impLogId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getImportLogsToConfirm/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringBody = new StringBuffer();
        		
        	stringQuery.append("select il from ");
        	stringBody.append(ImportLog.class.getName());
        	stringBody.append(" il ");
        	stringBody.append(" where (il.importLogStatus.statusCode = ? OR il.importLogStatus.statusCode = ? OR il.importLogStatus.statusCode = ?) and il.dealer.id = ? ");
        	
        	if (impLogId != null && impLogId.longValue() > 0L)
        		stringBody.append(" and il.id = ? ");
        	
        	Query query = null;
        	stringQuery.append(stringBody.toString());
        	query = session.createQuery(stringQuery.toString());
        	query.setString(0, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity());
        	query.setString(1, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        	query.setString(2, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity());
        	query.setLong(3, dealerId);
        	if (impLogId != null && impLogId.longValue() > 0L)
            	query.setLong(4, impLogId);
        		

        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	
        		stringCount.append("select count(*) from ");
            	stringCount.append( stringBody.toString() );  
            	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setString(0, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity());
            	countQuery.setString(1, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity());
            	countQuery.setLong(3, dealerId);
            	if (impLogId != null && impLogId.longValue() > 0L)
                	query.setLong(4, impLogId);
            	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogResponse response = new ImportLogResponse();
        	List<ImportLog> list = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setImportLog( list );
        	
        	return response;
            
        } catch (Throwable ex){
            log.error("== Error getImportLogsToConfirm/ImpLogConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogsToConfirm/ImpLogConfirmationDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogBySupplierPurchaseOrderAndLogisticOp(co.com.directv.sdii.model.pojo.Supplier, java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLog getImportLogBySupplierPurchaseOrderAndLogisticOp(Supplier supplier,
			String purchaseOrder,Long logisticOpId) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getImportLogBySupplierPurchaseOrderAndLogisticOp/ImportLogDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(ImportLog.class.getName());
	        	stringQuery.append(" entity where entity.supplier.id = :aSupplierId and");
	        	stringQuery.append(" entity.purchaseOrder = :aPurchaseorder");
	        	stringQuery.append(" and entity.dealer.id = :logisticOpId");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("aSupplierId", supplier.getId());
	            query.setString("aPurchaseorder", purchaseOrder);
	            query.setLong("logisticOpId", logisticOpId);
	            return (ImportLog) query.uniqueResult();

	        } catch (Throwable ex){
				log.error("== Error getImportLogBySupplierPurchaseOrderAndLogisticOp/ImportLogDAO ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getImportLogBySupplierPurchaseOrderAndLogisticOp/ImportLogDAO ==");
	        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogBySupplierPurchaseOrderAndLogisticOp(co.com.directv.sdii.model.pojo.Supplier, java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLog> getImportLogByPurchaseOrder(String purchaseOrder,Long countryId) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getImportLogByPurchaseOrder/ImportLogDAO ==");
		 
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from " + ImportLog.class.getName() + " entity ");
	        	stringQuery.append(" where entity.purchaseOrder = :aPurchaseorder");
	        	stringQuery.append("       and entity.country.id = :aCountryId ");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setString("aPurchaseorder", purchaseOrder);
	            query.setLong("aCountryId", countryId);  
	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error getImportLogBySupplierPurchaseOrderAndLogisticOp/ImportLogDAO ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getImportLogBySupplierPurchaseOrderAndLogisticOp/ImportLogDAO ==");
	        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogsByStatusCodeAndLogisticOp(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ImportLog> getImportLogsByStatusCodeAndLogisticOp(String statusCode,Long logisticOpId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getImportLogsByStatusCodeAndLogisticOp/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append(" from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" il where il.importLogStatus.statusCode = :anIlStCode");
        	stringQuery.append(" and il.dealer.id = :logisticOpId");
        	
        	Query query = null;
        	query = session.createQuery(stringQuery.toString());
        	query.setString("anIlStCode", statusCode);
        	query.setLong("logisticOpId", logisticOpId);

        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getImportLogsByStatusCodeAndLogisticOp/ImpLogConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogsByStatusCodeAndLogisticOp/ImpLogConfirmationDAO ==");
        }
	}
	
	   private static String stringToStringForQuery(String cad,String characterSeparator, String newCharacterSeparator){
		   StringBuffer sb = new StringBuffer();
		   if(cad != null && !cad.isEmpty()){
			   String[] values =  cad.split(characterSeparator);
			   for(String value : values){		
				   sb.append("'");
				   sb.append(value.trim());
				   sb.append("'");
				   sb.append(newCharacterSeparator);			   
			   }		   
		   }
		   return StringUtils.removeEnd(sb.toString(),newCharacterSeparator);
	   }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogByCriteria(co.com.directv.sdii.model.dto.ModifyImportLogDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogResponse getImportLogByCriteria(ModifyImportLogDTO modifyImportLogCriteria, Long countryId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getImportLogByCriteria/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringOrder = new StringBuffer(" order by il.id desc ");
        	StringBuffer stringSelect = new StringBuffer();
        	stringSelect.append(" SELECT distinct il, ");
        	stringSelect.append(" 		 (SELECT count(ili.id) ");
        	stringSelect.append("           FROM ImportLogItem ili ");
        	stringSelect.append("          WHERE ili.importLog.id=il.id ");
        	stringSelect.append("                AND ili.element.isSerialized = :anIsSerialized ");
        	stringSelect.append("          GROUP BY il.id ),");
        	stringSelect.append("        (SELECT count(ili.id) ");
        	stringSelect.append("           FROM ImportLogItem ili ");
        	stringSelect.append("          WHERE ili.importLog.id=il.id ");
        	stringSelect.append("                AND ili.element.isSerialized = :anIsSerialized2 ");
        	stringSelect.append("          GROUP BY il.id ) ");
        	stringQuery.append("  FROM " + ImportLog.class.getName() + " il ");
        	stringQuery.append(" WHERE 1=1 "); 
        	if(modifyImportLogCriteria.getImportLogId() != null && modifyImportLogCriteria.getImportLogId().longValue() > 0){
        		stringQuery.append("and il.id = :anId ");
        	}
        	if(modifyImportLogCriteria.getCreationDate() != null){
        		stringQuery.append("and il.creationDate >= :anCreationDate1 and  il.creationDate <= :anCreationDate2 ");
        	}else if(modifyImportLogCriteria.getCreationDateINI() != null && modifyImportLogCriteria.getCreationDateEND() != null){
        		stringQuery.append("and il.creationDate >= :anCreationDate1 and  il.creationDate <= :anCreationDate2 ");
        	}
        	if(modifyImportLogCriteria.getDeliveryDate() != null){
        		stringQuery.append("and il.deliveryDate >= :anDeliveryDate1 and il.deliveryDate <= :anDeliveryDate2 ");
        	}
        	if(modifyImportLogCriteria.getShippingDate() != null){
        		stringQuery.append("and il.shippingDate >= :anShippingDate1 and il.shippingDate <= :anShippingDate2 ");
        	}
        	if(modifyImportLogCriteria.getImportLogStatusCode() != null && modifyImportLogCriteria.getImportLogStatusCode().trim().length() > 0){
        		stringQuery.append("and il.importLogStatus.statusCode in ("+stringToStringForQuery(modifyImportLogCriteria.getImportLogStatusCode(), ",", ",") +")");
        	}
        	if(modifyImportLogCriteria.getDealerId() != null && modifyImportLogCriteria.getDealerId().longValue() > 0){
        		stringQuery.append("and il.dealer.id = :anDealerId ");
        	}
        	if(modifyImportLogCriteria.getPurchaseOrder()!=null && !modifyImportLogCriteria.getPurchaseOrder().equals("") ){
        		stringQuery.append("and upper(il.purchaseOrder) = :purchaseOrder ");
        	}
        	if(modifyImportLogCriteria.getImportDoc()!=null && !modifyImportLogCriteria.getImportDoc().equals("") ){
        		stringQuery.append("and upper(il.importDoc) = :importDoc ");
        	}
        	if(modifyImportLogCriteria.getLegalDoc()!=null && !modifyImportLogCriteria.getLegalDoc().equals("") ){
        		stringQuery.append("and upper(il.legalDoc) = :legalDoc ");
        	}
        	if(modifyImportLogCriteria.getSupplierId() != null){
        		stringQuery.append("and il.supplier.id = :supplierID ");
        	}
        	if(modifyImportLogCriteria.getSerialNumber() != null && modifyImportLogCriteria.getSerialNumber().trim().length() > 0){
        		stringQuery.append(" AND EXISTS(SELECT ili ");
        		stringQuery.append("              FROM ImportLogItem ili , ");
        		stringQuery.append("                   Serialized s left join s.serialized ls "); 
        		stringQuery.append("             WHERE il.id = ili.importLog.id  ");
        		stringQuery.append("                   AND ili.element.id=s.element.id ");
        		stringQuery.append("                   AND (s.serialCode=:aSerialCode or ls.serialCode=:aSerialCode))  ");
        	}
        	if(modifyImportLogCriteria.getElementTypeId() != null && modifyImportLogCriteria.getElementTypeId().longValue() > 0D){
        		//Llama metodo, que retorna el listado de Registros de importación, según el tipo de elemento
        		//return getImportLogResponseByTypeElementCode(modifyImportLogCriteria.getElementTypeId(), requestCollInfo);
        		stringQuery.append("and " + getSelectInImportLogByTypeElementCode (modifyImportLogCriteria.getElementTypeId()));
        	}
        	stringQuery.append(" and il.dealer.postalCode.city.state.country.id = :countryId ");
        	
        	//Eliminar el primer and del Where.
    		/*int iw = stringQuery.toString().indexOf("and ");
    		if (iw >= 0){
	    		String rt = stringQuery.toString().substring(0, iw) + stringQuery.toString().substring(iw+4, stringQuery.toString().length());
	    		stringQuery  = new StringBuffer(rt);
    		}else { 
    			iw = stringQuery.toString().indexOf("where ");
    			String rt = stringQuery.toString().substring(0, iw);
	    		stringQuery  = new StringBuffer(rt);
    		}*/
        	
        	
        	Query query = session.createQuery(stringSelect.toString() + stringQuery.toString() + stringOrder.toString());
        	query.setString("anIsSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        	query.setString("anIsSerialized2", CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
        	//Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append(stringQuery.toString());        	
        	Query countQuery = session.createQuery(stringCount.toString());
        	
        	query.setLong("countryId", countryId);
    		countQuery.setLong("countryId", countryId);
        	
        	if(modifyImportLogCriteria.getImportLogId() != null && modifyImportLogCriteria.getImportLogId().longValue() > 0){
        		query.setLong("anId", modifyImportLogCriteria.getImportLogId());
        		countQuery.setLong("anId", modifyImportLogCriteria.getImportLogId());
        	}
        	if(modifyImportLogCriteria.getCreationDate() != null){
        		query.setParameter("anCreationDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getCreationDate()));        		
        		query.setParameter("anCreationDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getCreationDate()));
        		
        		countQuery.setParameter("anCreationDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getCreationDate()));        		
        		countQuery.setParameter("anCreationDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getCreationDate()));
        	}else if(modifyImportLogCriteria.getCreationDateINI() != null && modifyImportLogCriteria.getCreationDateEND() != null){
        		query.setParameter("anCreationDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getCreationDateINI()));        		
        		query.setParameter("anCreationDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getCreationDateEND()));
        		
        		countQuery.setParameter("anCreationDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getCreationDateINI()));        		
        		countQuery.setParameter("anCreationDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getCreationDateEND()));
        	}
        	if(modifyImportLogCriteria.getDeliveryDate() != null){
        		query.setParameter("anDeliveryDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getDeliveryDate()));        		
        		query.setParameter("anDeliveryDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getDeliveryDate()));
        		
        		countQuery.setParameter("anDeliveryDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getDeliveryDate()));        		
        		countQuery.setParameter("anDeliveryDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getDeliveryDate()));
        	}
        	if(modifyImportLogCriteria.getShippingDate() != null){
        		query.setParameter("anShippingDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getShippingDate()));        		
        		query.setParameter("anShippingDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getShippingDate()));
        		
        		countQuery.setParameter("anShippingDate1", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getShippingDate()));        		
        		countQuery.setParameter("anShippingDate2", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getShippingDate()));
        	}
        	
        	if(modifyImportLogCriteria.getDealerId() != null && modifyImportLogCriteria.getDealerId().longValue() > 0){
        		query.setLong("anDealerId", modifyImportLogCriteria.getDealerId());
        		countQuery.setLong("anDealerId", modifyImportLogCriteria.getDealerId());
        	}
        	if(modifyImportLogCriteria.getPurchaseOrder()!=null && !modifyImportLogCriteria.getPurchaseOrder().equals("") ){
        		query.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder().trim().toUpperCase());
        		countQuery.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder().trim().toUpperCase());
        	}
        	if(modifyImportLogCriteria.getImportDoc()!=null && !modifyImportLogCriteria.getImportDoc().equals("") ){
        		query.setString("importDoc", modifyImportLogCriteria.getImportDoc().trim().toUpperCase());
        		countQuery.setString("importDoc", modifyImportLogCriteria.getImportDoc().trim().toUpperCase());
        	}
        	if(modifyImportLogCriteria.getLegalDoc()!=null && !modifyImportLogCriteria.getLegalDoc().equals("") ){
        		query.setString("legalDoc", modifyImportLogCriteria.getLegalDoc().trim().toUpperCase());
        		countQuery.setString("legalDoc", modifyImportLogCriteria.getLegalDoc().trim().toUpperCase());
        	}
        	if(modifyImportLogCriteria.getSupplierId() != null && modifyImportLogCriteria.getSupplierId().longValue() > 0){
        		query.setLong("supplierID", modifyImportLogCriteria.getSupplierId());
        		countQuery.setLong("supplierID", modifyImportLogCriteria.getSupplierId());
        	}
        	if(modifyImportLogCriteria.getSerialNumber() != null && modifyImportLogCriteria.getSerialNumber().trim().length() > 0){
        		query.setString("aSerialCode", modifyImportLogCriteria.getSerialNumber().trim().toUpperCase());
        		countQuery.setString("aSerialCode", modifyImportLogCriteria.getSerialNumber().trim().toUpperCase());
        	}
        	
        	/**
        	 * serialNumber, número Serial
        	 */
        	//private Long serialNumber;
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	             	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ImportLogResponse response = new ImportLogResponse();
        	List importLog_query = query.list();
        	
        	List<ImportLog> importLog = new ArrayList<ImportLog>(importLog_query.size());
        	
        	for(int i=0; i<importLog_query.size(); ++i){
        		Object[] resultItem=(Object[])importLog_query.get(i);
        		ImportLog resultItemImportLog=(ImportLog) resultItem[0];
        		importLog.add(resultItemImportLog);
        	}
        	
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLog.size(), recordQty.intValue() );
        	response.setImportLog(importLog);
        	List<ImportLogVO> importLogVO = UtilsBusiness.convertList(importLog, ImportLogVO.class);
        	for(int i=0; i<importLog_query.size(); ++i){
        		Object[] resultItem=(Object[])importLog_query.get(i);
        		importLogVO.get(i).setHaveNotSerializedElements(resultItem[2]!=null && (Long)resultItem[2]>=1L);
        		importLogVO.get(i).setHaveSerializedElements(resultItem[1]!=null && (Long)resultItem[1]>=1L);
        	}
        	response.setImportLogVO(importLogVO);
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error en getImportLogByCriteria/ImportLogDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogByCriteria/ImportLogDAO ==");
        }
	}	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogByCriteria(co.com.directv.sdii.model.dto.ModifyImportLogDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getImportLogByPurchaseOrderAndStatus(ModifyImportLogDTO modifyImportLogCriteria, Long countryId, List<String> importStatus)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getImportLogByPurchaseOrderAndStatus/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	Long recordQty;
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append(" select count(*) ");
        	stringQuery.append(" from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" il where il.dealer.postalCode.city.state.country.id = :countryId ");
        	
        	if(modifyImportLogCriteria.getSupplierId() != null){
        		stringQuery.append(" and il.supplier.id = :supplierID ");
        	}

        	if(modifyImportLogCriteria.getPurchaseOrder()!=null && !modifyImportLogCriteria.getPurchaseOrder().equals("") ){
        		stringQuery.append("and upper(il.purchaseOrder) = :purchaseOrder ");
        	}

        	if(importStatus != null && importStatus.size() > 0){
        	
        		stringQuery.append(" and il.importLogStatus.statusCode not in ("+UtilsBusiness.stringListToStringForQuery( importStatus , ",")+") ");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("countryId", countryId);
        	
        	if(modifyImportLogCriteria.getSupplierId() != null && modifyImportLogCriteria.getSupplierId().longValue() > 0){
        		query.setLong("supplierID", modifyImportLogCriteria.getSupplierId());
        	}

        	if(modifyImportLogCriteria.getPurchaseOrder()!=null && !modifyImportLogCriteria.getPurchaseOrder().equals("") ){
        		query.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder().trim().toUpperCase());
        	}
        	
        	recordQty = (Long)query.uniqueResult();
        	
        	return recordQty;
            
        } catch (Throwable ex){
			log.error("== Error en getImportLogByPurchaseOrderAndStatus/ImportLogDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogByPurchaseOrderAndStatus/ImportLogDAO ==");
        }
	}
	
	private String getSelectInImportLogByTypeElementCode(Long elementTypeId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSelectInImportLogByTypeElementCode/ImportLogDAO ==");
        Session session = super.getSession();
        
        String selectInRegistroImportacion = " exists (";
        
        

        try {
        	
        	selectInRegistroImportacion += " Select 1 ";
        	selectInRegistroImportacion += " from ";
        	selectInRegistroImportacion += " " + ImportLogItem.class.getName() + " ";
        	selectInRegistroImportacion += " entity where entity.element.elementType.id = "+elementTypeId+" ";

            selectInRegistroImportacion += " and il.id=entity.importLog.id )";
            
        	return selectInRegistroImportacion;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSelectInImportLogByTypeElementCode/ImportLogDAO ==");
        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogByElementId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ImportLog getImportLogByElementId(Long elementId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getImportLogByElementId/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" entity where entity.element.id = :elementId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementId", elementId);
            
            List<ImportLogItem>items = query.list();
            
            if( items!=null && items.size()>0 )
               return items.get( 0 ).getImportLog();
            
            return null;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogByElementId/ImportLogDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogsByIdCreationDateAndLogisticOp(java.lang.Long, java.util.Date, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(Long importLogId,Date creationDate,Long logisticOpId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getImportLogsByIdCreationDateAndLogisticOp/ImpLogConfirmationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	//Paginacion
        	stringCount.append("select count(*) ");
        	//Cuerpo de la cinsulta
        	stringQuery.append(" from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append( " il where il.dealer.id = :logisticOpId and il.importLogStatus.id in(:idShipmentState,:idPCState) " );
        	if( importLogId!=null )
               stringQuery.append("and il.id =:importLogId ");
        	if( creationDate!=null )
        	   stringQuery.append( "and il.creationDate between :initialDate and :endDate" );	
        	
        	//Paginacion
        	stringCount.append( stringQuery.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );
        	//Cuerpo de la cinsulta
        	Query query = session.createQuery(stringQuery.toString());        	
        	query.setLong("logisticOpId", logisticOpId);
        	query.setParameter( "idShipmentState" , CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getIdEntity( ImportLogStatus.class.getName() ) );
        	query.setParameter( "idPCState" , CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getIdEntity( ImportLogStatus.class.getName() ) );
        	
        	//Pagincion
        	countQuery.setLong("logisticOpId", logisticOpId);
        	countQuery.setParameter( "idShipmentState" , CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getIdEntity( ImportLogStatus.class.getName() ) );
        	countQuery.setParameter( "idPCState" , CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getIdEntity( ImportLogStatus.class.getName() ) );
        	
        	
        	if( importLogId!=null ){
        	   query.setParameter( "importLogId" , importLogId );
        	   countQuery.setParameter( "importLogId" , importLogId );
        	}
        	   
        	if( creationDate!=null ){
        		
        		Date initialDate,endDate;
        		
        		Calendar c = Calendar.getInstance();
        		c.setTime( creationDate );
        		
        		c.set( Calendar.HOUR ,0);
        		c.set( Calendar.MINUTE , 0);
        		c.set( Calendar.SECOND , 0);
        		initialDate = c.getTime();
        		
        		c.set( Calendar.HOUR ,23);
        		c.set( Calendar.MINUTE , 59);
        		c.set( Calendar.SECOND , 59);
        		endDate = c.getTime();
        		
         	   query.setParameter( "initialDate" , initialDate );
               query.setParameter( "endDate" , endDate );
               //Paginacion
               countQuery.setParameter( "initialDate" , initialDate );
               countQuery.setParameter( "endDate" , endDate );
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	ImportLogResponse response = new ImportLogResponse();        	
        	List<ImportLog> lista = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), lista.size(), recordQty.intValue() );
        	response.setImportLog( lista );
        	
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error getImportLogsByIdCreationDateAndLogisticOp/ImpLogConfirmationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogsByIdCreationDateAndLogisticOp/ImpLogConfirmationDAO ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#getImportLogByIdCreationDateStatusAndLogisticOp(java.lang.Long, java.util.Date, java.util.Date, java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ImportLog> getImportLogByIdCreationDateStatusAndLogisticOp(Long idImportLog, Date inicDate, Date endDate, String status,Long logisticOpId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getImportLogByIdCreationDateStatusAndLogisticOp/ImportLogDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" entity where entity.dealer.id = :logisticOpId   and entity.importLogStatus.statusCode = :status");
        	if(idImportLog!=0){
        		stringQuery.append(" and entity.id = :idImportLog");
        	}
        	if(inicDate!=null){
        		stringQuery.append(" and entity.creationDate >= :inicDate");
        	}
        	if(endDate!=null){
        		stringQuery.append(" and entity.creationDate <= :endDate");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("status", status);
            query.setLong("logisticOpId", logisticOpId);
            if(idImportLog!=0){
            	query.setLong("idImportLog", idImportLog);
        	}
        	if(inicDate!=null){
        		query.setParameter("inicDate", inicDate);
        	}
        	if(endDate!=null){
        		query.setParameter("endDate", endDate);
        	}
            
           
            return query.list();

        } catch (Throwable ex){
			log.error("== Error getImportLogByIdCreationDateStatusAndLogisticOp/ImportLogDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogByIdCreationDateStatusAndLogisticOp/ImportLogDAO ==");
        }
	}

	@Override
	public ImportLogResponse getImportLogInconsistencesByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getImportLogInconsistencesByCriteria/ImpLogConfirmationDAO ==");
        Session session = super.getSession();
        try{
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append(" from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" il where ");
        	stringQuery.append(" il.importLogStatus.statusCode = '"+CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity()+"' OR il.importLogStatus.statusCode = '"+CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity()+"' OR il.importLogStatus.statusCode = '"+CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity()+"' ");
        	
        	
        	if(modifyImportLogCriteria.getDealerId() != null && modifyImportLogCriteria.getDealerId().longValue() > 0){
        		stringQuery.append(" AND il.dealer.id = :dealerId ");
        	}
        	if(modifyImportLogCriteria.getPurchaseOrder() != null){
        		stringQuery.append(" AND il.purchaseOrder = :purchaseOrder ");
        	}
        	if(modifyImportLogCriteria.getImportDoc() != null){
        		stringQuery.append(" AND il.importDoc = :importDoc ");
        	}
        	if(modifyImportLogCriteria.getCreationDateINI() != null && modifyImportLogCriteria.getCreationDateEND() != null){
        		stringQuery.append("and il.creationDate >= :creationDateINI and  il.creationDate <= :creationDateEND ");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	//Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append( stringQuery.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );
        	
        	//Llena los filtros 
        	if(modifyImportLogCriteria.getDealerId() != null && modifyImportLogCriteria.getDealerId().longValue() > 0){
        		query.setLong("dealerId", modifyImportLogCriteria.getDealerId());
        		countQuery.setLong("dealerId", modifyImportLogCriteria.getDealerId());
        	}
        	if(modifyImportLogCriteria.getPurchaseOrder() != null ){
        		query.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder());
        		countQuery.setString("purchaseOrder", modifyImportLogCriteria.getPurchaseOrder());
        	}
        	if(modifyImportLogCriteria.getImportDoc() != null ){
        		query.setLong("importDoc", modifyImportLogCriteria.getDealerId());
        		countQuery.setLong("importDoc", modifyImportLogCriteria.getDealerId());
        	}
        	if(modifyImportLogCriteria.getCreationDateINI() != null && modifyImportLogCriteria.getCreationDateEND() != null){
        		query.setParameter("creationDateINI", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getCreationDateINI()));        		
        		query.setParameter("creationDateEND", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getCreationDateEND()));
        		
        		countQuery.setParameter("creationDateINI", UtilsBusiness.obtenerPrimeraHoraDia(modifyImportLogCriteria.getCreationDateINI()));        		
        		countQuery.setParameter("creationDateEND", UtilsBusiness.obtenerUltimaHoraDia(modifyImportLogCriteria.getCreationDateEND()));
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	             	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}

        	ImportLogResponse response = new ImportLogResponse();
        	List<ImportLog> importLog = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLog.size(), recordQty.intValue() );
        	response.setImportLog(importLog);
        	
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error en getImportLogInconsistencesByCriteria/ImpLogConfirmationDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogInconsistencesByCriteria/ImpLogConfirmationDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal#NotSerializedElementInSelectImportLogToPrintPaginationResponse(co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO,co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(
			FilterImportLogToPrintDTO filterImportLogToPrintDTO,
			RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicio getAllImportLogByIdDatesStatus/ImportLogDAO ==");
        Session session = super.getSession();
        String sqlAndWhere=" where ";
        
        NotSerializedElementInSelectImportLogToPrintPaginationResponse r = new NotSerializedElementInSelectImportLogToPrintPaginationResponse();
		Long totalRowCount = 0L;
		int firstResult = 0;
		int maxResult = 0;

        try {

        	boolean idImportLogSpecified = false, 
    				deliveryDateSpecified = false , 
    				shippingDateSpecified = false, 
    				creationDateSpecified = false, 
    		        idImportLogStatusSpecified = false;
    		
        	if(filterImportLogToPrintDTO.getId() != null && filterImportLogToPrintDTO.getId().longValue() > 0){
        		idImportLogSpecified = true;
        	}
        	if(filterImportLogToPrintDTO.getDeliveryDate() != null){
        		deliveryDateSpecified = true;
        	}
        	if(filterImportLogToPrintDTO.getShippingDate() != null){
        		shippingDateSpecified = true;
        	}
        	
        	if(filterImportLogToPrintDTO.getCreationDate() != null){
        		creationDateSpecified = true;
        	}
        	
        	if(filterImportLogToPrintDTO.getImportLogStatusId() != null && filterImportLogToPrintDTO.getImportLogStatusId().longValue() > 0){
        		idImportLogStatusSpecified = true;
        	}        	

        	StringBuffer stringQuery = new StringBuffer();
        	
        	StringBuffer stringQueryCount = new StringBuffer();
        	StringBuffer stringQueryTemp = new StringBuffer();
        	
        	stringQueryCount.append( " select count(*) ");
        	
        	stringQueryTemp.append( "select new ");
        	stringQueryTemp.append(FilterImportLogToPrintDTO.class.getName());
        	stringQueryTemp.append("(entity.id,entity.deliveryDate,entity.shippingDate,entity.creationDate,entity.importLogStatus.id) " );
        	
        	stringQuery.append("from ");
        	stringQuery.append(ImportLog.class.getName());
        	stringQuery.append(" entity ");
        	
        	if(idImportLogSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append("entity.id = :aId");
        		sqlAndWhere = " and ";
        	}
        	
        	if(deliveryDateSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append(" entity.deliveryDate = :aDeliveryDate");
        		sqlAndWhere = " and ";
        	}
        	
        	if(shippingDateSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append(" entity.shippingDate = :aShippingDate");
        		sqlAndWhere = " and ";
        	}
        	
        	if(creationDateSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append(" entity.creationDate = :aCreationDate");
        		sqlAndWhere = " and ";
        	}
        	
        	if(idImportLogStatusSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append(" entity.importLogStatus.id = :aILSId");
        		sqlAndWhere = " and ";
        	}
        	
        	stringQuery.append(" order by entity.creationDate,entity.id ");
        	
        	stringQueryCount.append(stringQuery);
        	Query countQuery = session.createQuery(stringQueryCount.toString());
        	
        	stringQueryTemp.append(stringQuery);
        	Query query = session.createQuery(stringQueryTemp.toString());
        	
            if(idImportLogSpecified){
            	countQuery.setLong("aId", filterImportLogToPrintDTO.getId());
            	query.setLong("aId", filterImportLogToPrintDTO.getId());
            }
        	  
            if(deliveryDateSpecified){
            	countQuery.setDate("aDeliveryDate", filterImportLogToPrintDTO.getDeliveryDate());
            	query.setDate("aDeliveryDate", filterImportLogToPrintDTO.getDeliveryDate());
            }
            
            if(shippingDateSpecified){
            	countQuery.setDate("aShippingDate", filterImportLogToPrintDTO.getShippingDate());
            	query.setDate("aShippingDate", filterImportLogToPrintDTO.getShippingDate());
            }
            
            if(creationDateSpecified){
            	countQuery.setDate("aCreationDate", filterImportLogToPrintDTO.getCreationDate());
            	query.setDate("aCreationDate", filterImportLogToPrintDTO.getCreationDate());
            }
            
            if(idImportLogStatusSpecified){
            	countQuery.setLong("aILSId", filterImportLogToPrintDTO.getImportLogStatusId());
            	query.setLong("aILSId", filterImportLogToPrintDTO.getImportLogStatusId());
            }
			          	
        	
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
                r.setCollection(query.list());
                populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }

        	return r;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImportLogByIdDatesStatus/ImportLogDAO ==");
        }
	}

}
