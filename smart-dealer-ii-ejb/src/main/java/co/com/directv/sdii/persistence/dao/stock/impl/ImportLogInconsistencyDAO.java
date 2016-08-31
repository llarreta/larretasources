package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
import java.util.Date;
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
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.InconsistencyStatus;
import co.com.directv.sdii.model.pojo.collection.ImportLogInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ImportLogInconsistency
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImportLogInconsistency
 * @see co.com.directv.sdii.model.hbm.ImportLogInconsistency.hbm.xml
 */
@Stateless(name="ImportLogInconsistencyDAOLocal",mappedName="ejb/ImportLogInconsistencyDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogInconsistencyDAO extends BaseDao implements ImportLogInconsistencyDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ImportLogInconsistencyDAO.class);
	
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencysDAOLocal#createImportLogInconsistency(co.com.directv.sdii.model.pojo.ImportLogInconsistency)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createImportLogInconsistency(ImportLogInconsistency obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createImportLogInconsistency/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ImportLogInconsistency ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogInconsistency/ImportLogInconsistencyDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencysDAOLocal#updateImportLogInconsistency(co.com.directv.sdii.model.pojo.ImportLogInconsistency)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImportLogInconsistency(ImportLogInconsistency obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateImportLogInconsistency/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ImportLogInconsistency ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLogInconsistency/ImportLogInconsistencyDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencysDAOLocal#deleteImportLogInconsistency(co.com.directv.sdii.model.pojo.ImportLogInconsistency)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImportLogInconsistency(ImportLogInconsistency obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImportLogInconsistency/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImportLogInconsistency entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ImportLogInconsistency ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogInconsistency/ImportLogInconsistencyDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencysDAOLocal#getImportLogInconsistencysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogInconsistency getImportLogInconsistencyByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImportLogInconsistencyByID/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ImportLogInconsistency) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogInconsistencyByID/ImportLogInconsistencyDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencysDAOLocal#getAllImportLogInconsistencys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLogInconsistency> getAllImportLogInconsistencys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImportLogInconsistencys/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImportLogInconsistencys/ImportLogInconsistencyDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal#getImportLogInconsitencysByCreationDate(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogInconsistency> getImportLogInconsitencysByCreationDate(
			Date inic, Date end) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getImportLogInconsitencysByCreationDate/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	stringQuery.append(" entity where entity.inconsistencyDate >= :aInicDate and entity.inconsistencyDate <= :aEndDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setDate("aInicDate", inic);
            query.setDate("aEndDate", end);

            return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogInconsitencysByCreationDate/ImportLogInconsistencyDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal#getImportLogInconsitencysByStatus(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogInconsistency> getImportLogInconsitencysByStatus(
			Long idInconsitencysStatus) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getImportLogInconsitencysByStatus/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	stringQuery.append(" entity where entity.inconsistencyStatus.id = :aStatusId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aStatusId", idInconsitencysStatus);

            return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogInconsitencysByStatus/ImportLogInconsistencyDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ImportLogInconsistency> getImportLogInconsistenciesByImportLogId( Long importLogId ) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getImportLogInconsistenciesByImportLogId/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	stringQuery.append(" entity where entity.importLog = :importLogId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("importLogId", importLogId );
            List<ImportLogInconsistency> list = query.list();
            return list;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogInconsistenciesByImportLogId/ImportLogInconsistencyDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ImportLogInconsistencyResponse getImportLogInconsistenciesByImportLogId( Long importLogId, RequestCollectionInfo requestCollInfo ) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getImportLogInconsistenciesByImportLogId/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	stringQuery.append(" entity where entity.importLog = :importLogId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("importLogId", importLogId );
            
            //Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append( stringQuery.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );
            
            //private Long serialNumber;
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	             	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}

        	ImportLogInconsistencyResponse response = new ImportLogInconsistencyResponse();
        	List<ImportLogInconsistency> importLogInconsistency = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), importLogInconsistency.size(), recordQty.intValue() );
        	response.setImportLogInconsistency(importLogInconsistency);
        	
        	return response;

            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogInconsistenciesByImportLogId/ImportLogInconsistencyDAO ==");
        }
	}
	 



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal#getImporLogInconsistencysByImportLog(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<ImportLogInconsistency> getImporLogInconsistencysByImportLog(
			Long importLog) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getImporLogInconsistencysByImportLog/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	stringQuery.append(" entity where entity.importLog = :importLog");
        	stringQuery.append(" order by entity.id desc ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("importLog", importLog);
            return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImporLogInconsistencysByImportLog/ImportLogInconsistencyDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal#createImportLogInconsistenciesList(java.util.List, co.com.directv.sdii.model.pojo.ImportLog)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImportLogInconsistenciesList( List<ImportLogInconsistency>inconsistencyList,ImportLog importLogId ) throws DAOServiceException, DAOSQLException
	{
		 log.debug("== Inicio createImportLogInconsistenciesList/ImportLogInconsistencyDAO ==");
	      
	        try {
	        	
	          for(ImportLogInconsistency inconsistency:inconsistencyList){
	        	inconsistency.setImportLog(importLogId.getId());  
	            createImportLogInconsistency(inconsistency);
	          }
	          
	        } catch (Throwable ex) {
	            log.debug("== Error en createImportLogInconsistenciesList ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina createImportLogInconsistenciesList/ImportLogInconsistencyDAO ==");
	        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal#closeImportLogInconsistencies(java.util.List, co.com.directv.sdii.model.pojo.InconsistencyStatus)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void closeImportLogInconsistencies( List<ImportLogInconsistency>inconsistencyList , InconsistencyStatus statusClose)throws  DAOServiceException, DAOSQLException
	{
		log.debug("== Inicio closeImportLogInconsistencies/ImportLogInconsistencyDAO ==");
	      
        try {
        	
          for(ImportLogInconsistency inconsistency:inconsistencyList){
                 if(inconsistency!=null){
                    ImportLogInconsistency itemToUpdate = getImportLogInconsistencyByID(inconsistency.getId());
                    itemToUpdate.setAnswer(inconsistency.getAnswer());
                    itemToUpdate.setInconsistencyStatus(statusClose);
                    updateImportLogInconsistency(itemToUpdate);
                  }
          }
          
          } catch (Throwable ex) {
            log.debug("== Error en closeImportLogInconsistencies ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina closeImportLogInconsistencies/ImportLogInconsistencyDAO ==");
        }
	}



	@SuppressWarnings("unchecked")
	@Override
	public Long countByStatus(Long importLogId, InconsistencyStatus inconsistencyStatus) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio conteoConfirmadas/ImportLogItemDAO ==");
        try {
        	Session session = super.getSession();
        	StringBuffer queryBuffer = new StringBuffer();
        	queryBuffer.append("SELECT COUNT( DISTINCT(ili.ID) ) FROM IMPORT_LOGS il INNER JOIN IMPORT_LOG_INCONSISTENCIES ili ON il.ID=ili.IMP_LOG_ID WHERE il.ID= :importLogId AND ili.STATUS_ID= :status");
        	Query query = session.createSQLQuery( queryBuffer.toString() );
        	query.setLong("importLogId", importLogId);
        	query.setLong("status", inconsistencyStatus.getId());
        	return ( (BigDecimal) query.uniqueResult() ).longValue();
        	
        }catch (Throwable ex) {
            log.debug("== Error countByStatus/ImportLogInconsistencyDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina countByStatus/ImportLogInconsistencyDAO ==");
        }
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ImportLogInconsistency> getImportLogInconsistenciesByImportLogItemId( Long importLogItem ) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getImportLogInconsistenciesByImportLogItemId/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogInconsistency.class.getName());
        	stringQuery.append(" entity where importLogItem = :importLogItem");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("importLogItem", importLogItem );
            List<ImportLogInconsistency> list = query.list();
            return list;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogInconsistenciesByImportLogItemId/ImportLogInconsistencyDAO ==");
        }
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImportLogInconsistencysForImportLog(Long importLogId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteImportLogInconsistencysForImportLog/ImportLogInconsistencyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImportLogInconsistency entity where entity.importLog = :importLogId");
            query.setLong("importLogId", importLogId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error deleteImportLogInconsistencysForImportLog/ImportLogInconsistencyDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogInconsistencysForImportLog/ImportLogInconsistencyDAO ==");
        }
		
	}
}
