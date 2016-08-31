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
import co.com.directv.sdii.model.pojo.RefIncStatus;
import co.com.directv.sdii.model.pojo.RefInconsistency;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.RefInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad RefInconsistency
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RefInconsistency
 * @see co.com.directv.sdii.model.hbm.RefInconsistency.hbm.xml
 */
@Stateless(name="RefInconsistencyDAOLocal",mappedName="ejb/RefInconsistencyDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefInconsistencyDAO extends BaseDao implements RefInconsistencyDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(RefInconsistencyDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#createRefInconsistency(co.com.directv.sdii.model.pojo.RefInconsistency)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRefInconsistency(RefInconsistency obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createRefInconsistency/RefInconsistencyDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el RefInconsistency ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefInconsistency/RefInconsistencyDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#updateRefInconsistency(co.com.directv.sdii.model.pojo.RefInconsistency)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefInconsistency updateRefInconsistency(RefInconsistency obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateRefInconsistency/RefInconsistencyDAO ==");
        Session session = super.getSession();
        try {
            Object refIncUpdated = session.merge(obj);
            this.doFlush(session);
            return (RefInconsistency) refIncUpdated;
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RefInconsistency ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefInconsistency/RefInconsistencyDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#deleteRefInconsistency(co.com.directv.sdii.model.pojo.RefInconsistency)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRefInconsistency(RefInconsistency obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteRefInconsistency/RefInconsistencyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from RefInconsistency entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el RefInconsistency ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefInconsistency/RefInconsistencyDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#getRefInconsistencysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefInconsistency getRefInconsistencyByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRefInconsistencyByID/RefInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefInconsistency.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (RefInconsistency) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefInconsistencyByID/RefInconsistencyDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#getAllRefInconsistencys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RefInconsistency> getAllRefInconsistencys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllRefInconsistencys/RefInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefInconsistency.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllRefInconsistencys/RefInconsistencyDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#getRefInconsistencysAndUserCreationByReferenceID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Object[]> getRefInconsistencysAndUserCreationByReferenceID(Long refID) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getRefInconsistencysAndUserCreationByReferenceID/DeliveryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefInconsistency.class.getName());
        	stringQuery.append(" inconsistency, ");
        	stringQuery.append(User.class.getName());
        	stringQuery.append(" user ");
        	stringQuery.append("where inconsistency.reference.id = :refID ");
        	stringQuery.append("and inconsistency.createUserId=user.id ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("refID", refID);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getRefInconsistencysAndUserCreationByReferenceID/DeliveryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefInconsistencysAndUserCreationByReferenceID/DeliveryDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#getRefInconsistencysAndUserCreationByReferenceID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RefInconsistency> getReferenceInconsistencyByReferenceID( Long refID )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicio getReferenceInconsistencyByReferenceID/RefInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefInconsistency.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :aRefId ");
        	stringQuery.append(" order by entity.id desc ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aRefId", refID);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getReferenceInconsistencyByReferenceID/RefInconsistencyDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencysDAOLocal#getRefInconsistencysAndUserCreationByReferenceID(java.lang.Long)
     */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void updateReferenceInconsistencyStatus( List<Long>inconsistenciesListIds,RefIncStatus status )throws DAOServiceException, DAOSQLException
	{
    	log.debug("== Inicio updateReferenceInconsistencyStatus/RefInconsistencyDAO ==");

        try {
        	
        	for(Long idRefInc:inconsistenciesListIds){
        		RefInconsistency refInc = getRefInconsistencyByID(idRefInc);
        	    if(refInc!=null){  
        	        refInc.setRefIncStatus(status);
        	        updateRefInconsistency(refInc);
        	    }
        	}

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio updateReferenceInconsistencyStatus/RefInconsistencyDAO ==");
        }
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal#getReferenceByInconsistencyId(java.lang.Long)
     */
	@Override
	public Reference getReferenceByInconsistencyId(Long incId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceByInconsistencyId/RefInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select entity.reference from ");
        	stringQuery.append(RefInconsistency.class.getName());
        	stringQuery.append(" entity where entity.id = :incId");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("incId", incId);

            return (Reference) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getReferenceByInconsistencyId/RefInconsistencyDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal#getReferenceInconsistencyByReferenceIdAndStatus(java.lang.Long, java.lang.String)
	 */
	@Override
	public RefInconsistencyResponse getReferenceInconsistencyByReferenceIdAndStatus(
			Long refId, String refStatusCode, RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefInconsistency.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :refId");
        	stringQuery.append(" and entity.refIncStatus.refIncStatusCode = :refStatusCode");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("refId", refId);
            query.setString("refStatusCode", refStatusCode);

            
            //Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append( stringQuery.toString() );
        	Query countQuery = session.createQuery( stringCount.toString() );
        	
        	

        	countQuery.setLong("refId", refId);
        	countQuery.setString("refStatusCode", refStatusCode);
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	              	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
            
        	RefInconsistencyResponse response = new RefInconsistencyResponse();
        	List<RefInconsistency> refInconsistencys = query.list();        	
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), refInconsistencys.size(), recordQty.intValue() );
        	response.setRefInconsistencys( refInconsistencys );
        	
        	return response;    

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal#getReferenceInconsistencyByReferenceIdAndStatusAndIncTtype(java.lang.Long, java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public RefInconsistencyResponse getReferenceInconsistencyByReferenceIdAndStatusAndIncTtype(
			Long refId, String refStatusCode, String refIncTypeCode, RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefInconsistency.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :refId");
        	stringQuery.append(" and entity.refIncStatus.refIncStatusCode = :refStatusCode");
        	stringQuery.append(" and entity.refIncType.refIncTypeCode = :refIncType");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("refId", refId);
            query.setString("refStatusCode", refStatusCode);
            query.setString("refIncType", refIncTypeCode);

            
            //Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append( stringQuery.toString() );
        	Query countQuery = session.createQuery( stringCount.toString() );
        	
        	

        	countQuery.setLong("refId", refId);
        	countQuery.setString("refStatusCode", refStatusCode);
        	query.setString("refIncType", refIncTypeCode);
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	              	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
            
        	RefInconsistencyResponse response = new RefInconsistencyResponse();
        	List<RefInconsistency> refInconsistencys = query.list();        	
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), refInconsistencys.size(), recordQty.intValue() );
        	response.setRefInconsistencys( refInconsistencys );
        	
        	return response;    

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal#areAllRefInconsistenciesClosed(java.lang.Long)
	 */
	@Override
	public boolean areAllRefInconsistenciesInStatus(Long referenceId, String refStatusCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceInconsistencyByReferenceIdAndStatus/RefInconsistencyDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer("select count(*) from ")
	        	.append(RefInconsistency.class.getName())
	        	.append(" entity where entity.reference.id = :refId")
	        	.append(" and entity.refIncStatus.refIncStatusCode <> :refStatusCode");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("refId", referenceId);
            query.setString("refStatusCode", refStatusCode);

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
	 * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal#getGeneratedReferencesByRefIncId(java.lang.Long)
	 */
	@Override
	public List<Reference> getGeneratedReferencesByRefIncId(Long refIncId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByParentReferenceId/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("select new ")
	        	.append(Reference.class.getName()).append("(refInc.generatedReference.id) from ")
	        	.append(RefInconsistency.class.getName())
	        	.append(" refInc where refInc.id = :refIncId");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("refIncId", refIncId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error en getReferencesByParentReferenceId/ReferenceDAO == " + ex.getMessage());
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByParentReferenceId/ReferenceDAO ==");
        }
	}
	
}
