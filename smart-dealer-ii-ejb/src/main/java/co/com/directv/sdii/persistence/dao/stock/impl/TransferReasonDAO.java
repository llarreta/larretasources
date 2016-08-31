package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.TransferReason;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.TransferReasonDAOLocal;

@Stateless(name="TransferReasonDAOLocal",mappedName="ejb/TransferReasonDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TransferReasonDAO extends BaseDao implements TransferReasonDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(TransferReasonDAO.class);
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void createTransferReason(TransferReason obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio createTransferReason/TransferReasonDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el TransferReason ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createTransferReason/TransferReasonDAO ==");
        }
		
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void updateTransferReason(TransferReason obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateTransferReason/TransferReasonDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el TransferReason ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateTransferReason/TransferReasonDAO ==");
        }
		
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void deleteTransferReason(TransferReason obj)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteTransferReason/TransferReasonDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from TransferReason entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el TransferReason ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTransferReason/TransferReasonDAO ==");
        }
		
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransferReason getTransferReasonByID(Long id)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getTransferReasonByID/TransferReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransferReason.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (TransferReason) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransferReasonByID/TransferReasonDAO ==");
        }
	}
	
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TransferReason> getAllTransferReasons()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllTransferReasons/TransferReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransferReason.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllTransferReasons/TransferReasonDAO ==");
        }
	}

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransferReasonResponse getTransferReasonByFilter(
			String transferReasonName, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getTransferReasonByElementModelIdAndStatus/ElementModelDAO ==");
        Session session = super.getSession();
        String filter = transferReasonName==null?"":transferReasonName.toUpperCase();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(TransferReason.class.getName());
        	if(filter.length() > 0){
        		stringQuery.append(" entity where UPPER(entity.transferReasonName) = :transferReasonName");
        	}
        	Query query = session.createQuery(stringQuery.toString());
        	if(filter.length() > 0){
        		query.setString("transferReasonName", filter);
        	}


            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(filter.length() > 0){
        			queryCount.setString("transferReasonName", filter);
        		}
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	TransferReasonResponse response = new TransferReasonResponse();
        	List<TransferReason> TransferReasonList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), TransferReasonList.size(), recordQty.intValue() );
        	response.setTransferReason(TransferReasonList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransferReasonByElementModelIdAndStatus/ElementModelDAO ==");
        }
	}	

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TransferReason> getTransferReasonsByIsActive(String status)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getTransferReasonsByIsActive/TransferReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransferReason.class.getName());
        	stringQuery.append(" et where et.isActive = :aStatus");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aStatus", status);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransferReasonsByIsActive/TransferReasonDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public  List<TransferReason> getTransferReasonByAdjustmentType(
			String codeAdjustmentType, String isActive)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getTransferReasonByAdjustmentType/TransferReasonDAO ==");
        Session session = super.getSession();
        boolean codeAdjustmentTypeSpecified = false; 
        boolean isActiveSpecified = false;
        
        try {
        	if(codeAdjustmentType !=null && !StringUtils.isBlank(codeAdjustmentType)){
        		codeAdjustmentTypeSpecified = true;
        	}
        	
        	if(isActive !=null && !StringUtils.isBlank(isActive)){
        		isActiveSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransferReason.class.getName());
        	stringQuery.append(" et where 1 = 1 ");
        	if(codeAdjustmentTypeSpecified){
        		stringQuery.append(" and et.adjustmentType.code = :codeAdjustmentTypeSpecified ");
        	}
        	if(isActiveSpecified){
        		stringQuery.append(" and et.isActive = :isActive ");
        	} 
        	
        	Query query = session.createQuery(stringQuery.toString());
        	if(codeAdjustmentTypeSpecified){
        		query.setString("codeAdjustmentTypeSpecified", codeAdjustmentType);
        	}
        	if(isActiveSpecified){
        		query.setString("isActive", isActive);
        	}
            
            return  query.list();
        } catch (Throwable ex){
			log.error("== Error  getTransferReasonByAdjustmentType ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransferReasonByAdjustmentType/TransferReasonDAO ==");
        }
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.TransferReasonDAOLocal#getTransferReasonByMovementTypeInCode(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferReason> getTransferReasonByMovementTypeInCode(String movementTypeInCode, String isActive)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getTransferReasonByMovementTypeInCode/TransferReasonDAO ==");
        Session session = super.getSession();
        boolean codeMovementTypeInSpecified = false; 
        boolean isActiveSpecified = false;
        
        try {
        	if(movementTypeInCode !=null && !StringUtils.isBlank(movementTypeInCode)){
        		codeMovementTypeInSpecified = true;
        	}
        	
        	if(isActive !=null && !StringUtils.isBlank(isActive)){
        		isActiveSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransferReason.class.getName());
        	stringQuery.append(" et where 1 = 1 ");
        	if(codeMovementTypeInSpecified){
        		stringQuery.append(" and et.movTypeIn.movTypeCode = :movementTypeInCode ");
        	}
        	if(isActiveSpecified){
        		stringQuery.append(" and et.isActive = :isActive ");
        	} 
        	
        	Query query = session.createQuery(stringQuery.toString());
        	if(codeMovementTypeInSpecified){
        		query.setString("movementTypeInCode", movementTypeInCode);
        	}
        	if(isActiveSpecified){
        		query.setString("isActive", isActive);
        	}
            
            return  query.list();
        } catch (Throwable ex){
			log.error("== Error  getTransferReasonByMovementTypeInCode/TransferReasonDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransferReasonByMovementTypeInCode/TransferReasonDAO ==");
        }
	}	
}
