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
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ReferenceStatusEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO;
import co.com.directv.sdii.model.dto.InfoToPrintReferencesDTO;
import co.com.directv.sdii.model.dto.NotSerRefElementItemDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.SelectReferenceToPrintDTO;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectReferenceToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Reference
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Reference
 * @see co.com.directv.sdii.model.hbm.Reference.hbm.xml
 */
@Stateless(name="ReferenceDAOLocal",mappedName="ejb/ReferenceDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceDAO extends BaseDao implements ReferenceDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ReferenceDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferencesDAOLocal#createReference(co.com.directv.sdii.model.pojo.Reference)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createReference(Reference obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createReference/ReferenceDAO ==");
        Session session = super.getSession();
        try {
        	session.save(obj);
            this.doFlush(session);
            session.refresh(obj);
        } catch (Throwable ex) {
            log.debug("== Error creando el Reference ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReference/ReferenceDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferencesDAOLocal#updateReference(co.com.directv.sdii.model.pojo.Reference)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateReference(Reference obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateReference/ReferenceDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Reference ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReference/ReferenceDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferencesDAOLocal#deleteReference(co.com.directv.sdii.model.pojo.Reference)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteReference(Reference obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteReference/ReferenceDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Reference entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Reference ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReference/ReferenceDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferencesDAOLocal#getReferencesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reference getReferenceByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceByID/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Reference) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByID/ReferenceDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferencesDAOLocal#getReferencesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Reference> getReferenceByRNNumber(String rnNumber, String refStatus) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceByRNNumber/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.rnNumber = :anRNNumber");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode != :anRefStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anRNNumber", rnNumber);
            query.setString("anRefStatus", refStatus);

            //return (Reference) query.uniqueResult();
            List<Reference> response = null;
            response = query.list();
            return response;            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByRNNumber/ReferenceDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferencesDAOLocal#getAllReferences()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reference> getAllReferences() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllReferences/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllReferences/ReferenceDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getAllReferencesAndByCountry(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reference> getAllReferencesAndByCountry(Long country) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllReferencesAndByCountry/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.countryCodeId.id = :country");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("country", country);

            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllReferencesAndByCountry/ReferenceDAO ==");
        }
    }



	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceStatusAndByCountry(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesByReferenceStatusAndByCountry(Long idStatus,Long country)
			throws DAOServiceException, DAOSQLException {
		 	log.debug("== Inicio getReferencesByReferenceStatusAndByCountry/ReferenceDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Reference.class.getName());
	        	stringQuery.append(" entity where entity.referenceStatus.id = :aStatusId");
	        	stringQuery.append(" and entity.countryCodeId.id = :country");
	        	stringQuery.append(" order by entity.creationReferenceDate");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("aStatusId", idStatus);
	            query.setLong("country", country);

	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getReferencesByReferenceStatusAndByCountry/ReferenceDAO ==");
	        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceStatusAndWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesByReferenceStatusAndWh(
			Long idStatus, Long idWhSource, Long idWhTarget)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceStatusAndWh/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.referenceStatus.id = :aStatusId and");
        	stringQuery.append(" entity.warehouseBySourceWh.id = :aWhSourceId and");
        	stringQuery.append(" entity.warehouseByTargetWh.id = :aWhTargerId");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aStatusId", idStatus);
            query.setLong("aWhSourceId", idWhSource);
            query.setLong("aWhTargerId", idWhTarget);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceStatusAndWh/ReferenceDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceAndTargetWareHouse(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesBySourceAndTargetWareHouse(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesBySourceAndTargetWareHouse/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	
        	boolean idWhSourceSpecified = false , idWhTargetSpecified = false;
        	
        	if(idWhSource != null && idWhSource.longValue() > 0){
        		idWhSourceSpecified = true;
        	}
        	
        	if(idWhTarget != null && idWhTarget.longValue() > 0){
        		idWhTargetSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where ");
        	if(idWhSourceSpecified){
        		stringQuery.append("entity.warehouseBySourceWh.id = :aWhSourceId");
        	}
        	if(idWhSourceSpecified && idWhTargetSpecified){
        		stringQuery.append(" and entity.warehouseByTargetWh.id = :aWhTargerId");
        	}else if(idWhTargetSpecified){
        		stringQuery.append("entity.warehouseByTargetWh.id = :aWhTargerId");
        	}
        	
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            if(idWhSourceSpecified){
            	query.setLong("aWhSourceId", idWhSource);
            }
            if(idWhTargetSpecified){
            	query.setLong("aWhTargerId", idWhTarget);
            }

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouse/ReferenceDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceWareHouse(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesBySourceWareHouse(Long idWhSource)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesBySourceWareHouse/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseBySourceWh.id = :aWhSourceId ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhSourceId", idWhSource);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceWareHouse/ReferenceDAO ==");
        }
	}



	


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByTargetWareHouse(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesByTargetWareHouse(Long idWhTarget)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesBySourceAndTargetWareHouse/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseByTargetWh.id = :aWhTargerId ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhTargerId", idWhTarget);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouse/ReferenceDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceStatusAndSourceWh(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesByReferenceStatusAndSourceWh(
			Long idStatus, Long idWhSource) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceStatusAndSourceWh/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.referenceStatus.id = :aStatusId and");
        	stringQuery.append(" entity.warehouseBySourceWh.id = :aWhSourceId ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aStatusId", idStatus);
            query.setLong("aWhSourceId", idWhSource);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceStatusAndSourceWh/ReferenceDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceStatusAndTargerWh(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesByReferenceStatusAndTargerWh(
			Long idStatus, Long idWhTarget) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceStatusAndTargerWh/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.referenceStatus.id = :aStatusId and");
        	stringQuery.append(" entity.warehouseByTargetWh.id = :aWhTargerId");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aStatusId", idStatus);
            query.setLong("aWhTargerId", idWhTarget);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceStatusAndTargerWh/ReferenceDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName(java.lang.Long, java.lang.Long, java.util.Date, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName(
			Long sourceWhId, Long targetWhId, Date referenceCreationDate,
			String recorderUserName) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	
        	boolean someFilterSpecified = false, idWhSourceSpecified = false , idWhTargetSpecified = false, referenceCreationDateSpecified = false, recorderUserNameSpecified = false;
        	
        	if(sourceWhId != null && sourceWhId.longValue() > 0){
        		idWhSourceSpecified = true;
        	}
        	if(targetWhId != null && targetWhId.longValue() > 0){
        		idWhTargetSpecified = true;
        	}
        	if(referenceCreationDate != null){
        		referenceCreationDateSpecified = true;
        	}
        	if(recorderUserName != null && recorderUserName.trim().length() > 0){
        		recorderUserNameSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where ");
        	if(idWhSourceSpecified){
        		stringQuery.append("entity.warehouseBySourceWh.id = :aWhSourceId");
        		someFilterSpecified = true;
        	}
        	if(idWhTargetSpecified){
        		if(someFilterSpecified){
        			stringQuery.append(" and");
        		}
        		someFilterSpecified = true;
        		stringQuery.append(" entity.warehouseByTargetWh.id = :aWhTargerId");
        	}
        	
        	if(referenceCreationDateSpecified){
        		if(someFilterSpecified){
        			stringQuery.append(" and");
        		}
        		someFilterSpecified = true;
        		stringQuery.append(" entity.creationReferenceDate = :aCreRefDate");
        	}
        	
        	if(recorderUserNameSpecified){
        		if(someFilterSpecified){
        			stringQuery.append(" and");
        		}
        		someFilterSpecified = true;
        		stringQuery.append(" entity.createUserId.login = :aCreUserName");
        	}
        	
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            if(idWhSourceSpecified){
            	query.setLong("aWhSourceId", sourceWhId);
            }
            if(idWhTargetSpecified){
            	query.setLong("aWhTargerId", targetWhId);
            }
            
            if(referenceCreationDateSpecified){
            	query.setDate("aCreRefDate", referenceCreationDate);
            }
            
            if(recorderUserNameSpecified){
            	query.setString("aCreUserName", recorderUserName);
            }

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName/ReferenceDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getAllReferencesByIdSourceTargetWareHouseStatus(co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO.FilterReferencesToPrintDTO,co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NotSerializedElementInSelectReferenceToPrintPaginationResponse getAllReferencesByIdSourceTargetWareHouseStatus(
			FilterReferencesToPrintDTO referencesFilterToPrintDTO, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getAllReferencesByIdSourceTargetWareHouseStatus/ReferenceDAO ==");
        Session session = super.getSession();
        String sqlAndWhere=" where ";
        
        NotSerializedElementInSelectReferenceToPrintPaginationResponse r = new NotSerializedElementInSelectReferenceToPrintPaginationResponse();
		Long totalRowCount = 0L;
		int firstResult = 0;
		int maxResult = 0;

        try {
        	
    		boolean idWhSourceSpecified = false , 
    		        idWhTargetSpecified = false, 
    		        idReferenceSpecified = false, 
    		        idReferenceStatusSpecified = false;
        	
        	if(referencesFilterToPrintDTO.getWhSource() != null && referencesFilterToPrintDTO.getWhSource().longValue() > 0){
        		idWhSourceSpecified = true;
        	}
        	if(referencesFilterToPrintDTO.getWhTarget() != null && referencesFilterToPrintDTO.getWhTarget().longValue() > 0){
        		idWhTargetSpecified = true;
        	}
        	if(referencesFilterToPrintDTO.getIdReferences() != null && referencesFilterToPrintDTO.getIdReferences().longValue() > 0){
        		idReferenceSpecified = true;
        	}
        	
        	if(referencesFilterToPrintDTO.getReferenceStatusId() != null && referencesFilterToPrintDTO.getReferenceStatusId().longValue() > 0){
        		idReferenceStatusSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	

        	StringBuffer stringQueryCount = new StringBuffer();
        	StringBuffer stringQueryTemp = new StringBuffer();
        	
        	stringQueryCount.append( " select count(*) ");
        	
        	stringQueryTemp.append( "select new ");
        	stringQueryTemp.append(SelectReferenceToPrintDTO.class.getName());
        	stringQueryTemp.append("(entity.id,entity.warehouseBySourceWh.id,entity.warehouseByTargetWh.id,entity.creationReferenceDate,entity.createUserId.login) " );
        	
        	stringQuery.append(" from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity ");
        	
        	if(idWhSourceSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append("entity.warehouseBySourceWh.id = :aWhSourceId");
        		sqlAndWhere = " and ";
        	}
        	
        	if(idWhTargetSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append(" entity.warehouseByTargetWh.id = :aWhTargerId");
        		sqlAndWhere = " and ";
        	}
        	
        	if(idReferenceSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append(" entity.id = :aId");
        		sqlAndWhere = " and ";
        	}
        	
        	if(idReferenceStatusSpecified){
        		stringQuery.append(sqlAndWhere);
        		stringQuery.append(" entity.referenceStatus.id = :aRSId");
        		sqlAndWhere = " and ";
        	}
        	
        	stringQuery.append(" order by entity.creationReferenceDate,entity.id ");
        	
        	stringQueryCount.append(stringQuery);
        	Query countQuery = session.createQuery(stringQueryCount.toString());
        	
        	stringQueryTemp.append(stringQuery);
        	Query query = session.createQuery(stringQueryTemp.toString());
        	
        	
            if(idWhSourceSpecified){
            	countQuery.setLong("aWhSourceId", referencesFilterToPrintDTO.getWhSource());
            	query.setLong("aWhSourceId", referencesFilterToPrintDTO.getWhSource());
            }
            if(idWhTargetSpecified){
            	countQuery.setLong("aWhTargerId", referencesFilterToPrintDTO.getWhTarget());
            	query.setLong("aWhTargerId", referencesFilterToPrintDTO.getWhTarget());
            }
            
            if(idReferenceSpecified){
            	countQuery.setLong("aId", referencesFilterToPrintDTO.getIdReferences());
            	query.setLong("aId", referencesFilterToPrintDTO.getIdReferences());
            }
            
            if(idReferenceStatusSpecified){
            	countQuery.setLong("aRSId", referencesFilterToPrintDTO.getReferenceStatusId());
            	query.setLong("aRSId", referencesFilterToPrintDTO.getReferenceStatusId());
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
            log.debug("== Termina getAllReferencesByIdSourceTargetWareHouseStatus/ReferenceDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getInfoToPrintReferencesById(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public InfoToPrintReferencesDTO getInfoToPrintReferencesById(Long referenceId)
			throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	
    		StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append( "select new ");
        	stringQuery.append(InfoToPrintReferencesDTO.class.getName());
        	stringQuery.append("(entity.id,entity.warehouseBySourceWh.id,entity.warehouseByTargetWh.id,entity.creationReferenceDate,entity.createUserId.login) " );
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity ");
        	stringQuery.append(" where entity.id = :aId");
        	
        	Query query = session.createQuery(stringQuery.toString());
            	query.setLong("aId", referenceId);
            return (InfoToPrintReferencesDTO) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName/ReferenceDAO ==");
        }
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#registerReferenceShipment(co.com.directv.sdii.model.pojo.Reference, co.com.directv.sdii.model.pojo.ReferenceStatus)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void registerReferenceShipment( Reference reference, ReferenceStatus statusSended )throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio registerReferenceShipment/ReferenceDAO ==");
        Session session = super.getSession();
        try{
        	
        	Reference partialReference = getReferenceByID( reference.getId() );
        	
        	partialReference.setReferenceStatus( statusSended );
        	partialReference.setShippingDate( new Date() );
        	partialReference.setTransportCompany( reference.getTransportCompany() );
        	partialReference.setDriverName( reference.getDriverName() );
        	partialReference.setTransportGuide( reference.getTransportGuide() );
        	partialReference.setVehiclePlate( reference.getVehiclePlate() );
        	partialReference.setVolume( reference.getVolume() );
        	partialReference.setSendUnits(reference.getSendUnits());
        	partialReference.setDeliveryDate(reference.getDeliveryDate());
        	session.update( partialReference );
        	doFlush(session);        	
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina registerReferenceShipment/ReferenceDAO ==");
        }
	}
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y el codigo de pais
	 * @param referenceId Long - El identificador de la remision
	 * @param countryCode Long - El codigo del pais
	 * @throws DAOServiceException en caso de error al ejecutar la consulta remisiones 
	 * @throws DAOSQLException en caso de error al ejecutar la consulta de remisiones
	 * @author garciniegas 
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reference>getReferenceByIdAndCountryCode( Long referenceId,Long countryCode )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicio getReferenceByIdAndCountryCode/ReferenceDAO ==");
        Session session = super.getSession();
        List<Reference>listaRemisiones = null;
        
        try{
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append( "from " );
        	stringQuery.append( Reference.class.getName() );
        	stringQuery.append( " as rem where rem.referenceStatus.refStatusCode = :refStatusCode " );
        	if( referenceId!=null )
        	stringQuery.append( "and rem.id = :referenceId " );
        	if( countryCode!=null )
        	stringQuery.append( "and rem.countryCodeId.countryCode = :countryCode" );
        	
        	Query query = session.createQuery( stringQuery.toString() );
        	//ReferenceStatusEnum.CREATED.getRefStatudCode()
        	query.setParameter( "refStatusCode", CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
        	
        	if( referenceId!=null )
        		query.setParameter( "referenceId" , referenceId);
        	if( countryCode!=null )
        		query.setParameter( "countryCode",countryCode );
        	
        	listaRemisiones = query.list();
        	
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIdAndCountryCode/ReferenceDAO ==");
        }

        return listaRemisiones;
    }
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long,java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reference> getReferenceByIdAndWarhouseTarget(Long referenceId, Long warehouseId )throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicio getReferenceByIdAndWarhouseTarget/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append("entity.warehouseByTargetWh.id = :warehouseId ");
        	stringQuery.append("and entity.referenceStatus.refStatusCode in (:statusSend,:statusPartialConfirmed) ");
        	if(referenceId != null){
        		stringQuery.append("and entity.id = :referenceId ");
        	}        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("warehouseId", warehouseId);
            query.setString("statusSend", ReferenceStatusEnum.SHIPMENT.getRefStatudCode());
            query.setString("statusPartialConfirmed", ReferenceStatusEnum.PARTIAL_CONFORMATION.getRefStatudCode());
            if(referenceId != null){
            	query.setLong("referenceId", referenceId);
            }            

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getReferenceByIdAndWarhouseTarget/ReferenceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIdAndWarhouseTarget/ReferenceDAO ==");
        }
    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long,java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferenceByComplexFilterToFindInconsistenceReferences( 
			WarehouseVO whSource,WarehouseVO whTarget,ReferenceVO reference,Long country )throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicio getReferenceByComplexFilterToFindInconsistenceReferences/ReferenceDAO ==");

        try {
		
        	Session session = super.getSession();
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append( "from " );
        	stringQuery.append( Reference.class.getName() );
        	stringQuery.append( " refer where refer.referenceStatus.id = :refStatusId" );
        	
        	if( whSource!=null && whSource.getId()!=null )
        	 stringQuery.append( " and refer.warehouseBySourceWh.id = :whSource" );
        	if( whTarget!=null && whTarget.getId()!=null )
        	 stringQuery.append( " and refer.warehouseByTargetWh.id = :whTarget" );
        	if( reference!=null && reference.getId()!=null )
        	 stringQuery.append( " and refer.id = :referId" );
        	if( country!=null )
        		stringQuery.append( " and refer.countryCodeId.id = :countryId" );
        	
        	Query query = session.createQuery( stringQuery.toString() );
        	query.setParameter( "refStatusId" , CodesBusinessEntityEnum.REFERENCE_STATUS_INCONSISTENCY_PROCESS.getIdEntity( ReferenceStatus.class.getName() ));
        	if( whSource!=null && whSource.getId()!=null ){
        		query.setParameter( "whSource",whSource.getId() );
        	}
           	if( whTarget!=null && whTarget.getId()!=null ){
           		query.setParameter( "whTarget",whTarget.getId() );
           	}
           	if( reference!=null && reference.getId()!=null ){
           		query.setParameter( "referId",reference.getId() );
           	}
           	if( country!=null  ){
           		query.setParameter( "countryId",country );
           	}
           	 return query.list();
           	
        } catch (Throwable ex){
			log.error("== Error getReferenceByComplexFilterToFindInconsistenceReferences/ReferenceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByComplexFilterToFindInconsistenceReferences/ReferenceDAO ==");
        }

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long,java.lang.Long)
	 */

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
	
		log.debug("== Inicio getNotSerializedElementInReference/ReferenceDAO ==");
		NotSerializedElementInReferencePaginationResponse r = new NotSerializedElementInReferencePaginationResponse();
		Long totalRowCount = 0L;
		int firstResult = 0;
		int maxResult = 0;

        try {
        	//jnova se modifica la implementacion para no hacer llamado a DAO y que se haga la consulta por query
        	Session session = super.getSession();
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCountQuery = new StringBuffer();
        	stringQuery.append( "select notSer from " );
        	stringQuery.append( ReferenceElementItem.class.getName() +" item, ");
        	stringQuery.append( NotSerialized.class.getName() +" notSer ");
        	stringQuery.append( "inner join item.element " );
        	stringQuery.append( "where " );
        	stringQuery.append( "item.element.id = notSer.elementId " );
        	stringQuery.append( "and item.reference.id = :referenceId " );
        	
        	// Paginación
        	stringCountQuery.append( "select count(*) from " );
        	stringCountQuery.append( ReferenceElementItem.class.getName() +" item, ");
        	stringCountQuery.append( NotSerialized.class.getName() +" notSer ");
        	stringCountQuery.append( "inner join item.element " );
        	stringCountQuery.append( "where " );
        	stringCountQuery.append( "item.element.id = notSer.elementId " );
        	stringCountQuery.append( "and item.reference.id = :referenceId " );
        	
        	
        	Query query = session.createQuery( stringQuery.toString() );
        	Query countQuery = session.createQuery(stringCountQuery.toString());
        	
        	query.setLong("referenceId", reference.getId());
        	countQuery.setLong("referenceId", reference.getId());
        	
            // Paginación
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
		
            /*List<ReferenceElementItem>items      = daoReferenceElementItem.getReferenceElementItemsByReferenceID( reference.getId() );
            List<NotSerialized>notSerializedList = new ArrayList<NotSerialized>();
            
               for( ReferenceElementItem item:items )
               {
            	   String isSerialized  = item.getElement().getIsSerialized();
            	     if( !isSerialized.equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ){
            	    	 NotSerialized ns = daoNotSerialized.getNotSerializedByID( item.getElement().getId() );
            	    	 notSerializedList.add( ns );
            	      }
               }
            
               return notSerializedList;*/
               
        } catch (Throwable ex){
			log.error("== Error getNotSerializedElementInReference/ReferenceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerializedElementInReference/ReferenceDAO ==");
        }
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getNotSerializedElementInReferenceObjectsReturn(co.com.directv.sdii.model.vo.ReferenceVO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReferenceObjectsReturn(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
	
		log.debug("== Inicio getNotSerializedElementInReferenceObjectsReturn/ReferenceDAO ==");
		NotSerializedElementInReferencePaginationResponse r = new NotSerializedElementInReferencePaginationResponse();
		Long totalRowCount = 0L;
		int firstResult = 0;
		int maxResult = 0;

        try {
        	//jnova se modifica la implementacion para no hacer llamado a DAO y que se haga la consulta por query
        	Session session = super.getSession();
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCountQuery = new StringBuffer();
        	stringQuery.append( "select new ").append(NotSerRefElementItemDTO.class.getName()).append(" (notSer,item) from " );
        	stringQuery.append( ReferenceElementItem.class.getName() +" item, ");
        	stringQuery.append( NotSerialized.class.getName() +" notSer ");
        	stringQuery.append( "inner join item.element " );
        	stringQuery.append( "where " );
        	stringQuery.append( "item.element.id = notSer.elementId " );
        	stringQuery.append( "and item.reference.id = :referenceId " );
        	
        	// Paginación
        	stringCountQuery.append( "select count(*) from " );
        	stringCountQuery.append( ReferenceElementItem.class.getName() +" item, ");
        	stringCountQuery.append( NotSerialized.class.getName() +" notSer ");
        	stringCountQuery.append( "inner join item.element " );
        	stringCountQuery.append( "where " );
        	stringCountQuery.append( "item.element.id = notSer.elementId " );
        	stringCountQuery.append( "and item.reference.id = :referenceId " );
        	
        	
        	Query query = session.createQuery( stringQuery.toString() );
        	Query countQuery = session.createQuery(stringCountQuery.toString());
        	
        	query.setLong("referenceId", reference.getId());
        	countQuery.setLong("referenceId", reference.getId());
        	
            // Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
                
            	r.setCollectionObjects(query.list());
                
                populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollectionObjects().size(), totalRowCount.intValue());
            }

        	return r;
               
        } catch (Throwable ex){
			log.error("== Error getNotSerializedElementInReferenceObjectsReturn/ReferenceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerializedElementInReferenceObjectsReturn/ReferenceDAO ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long,java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public SerializedElementInReferencePaginationResponse getSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedElementInReference/ReferenceDAO ==");
		SerializedElementInReferencePaginationResponse r = new SerializedElementInReferencePaginationResponse();
		Long totalRowCount = 0L;
		int firstResult = 0;
		int maxResult = 0;
		
        try {
        	
        	//jnova se modifica la implementacion para no hacer llamado a DAO y que se haga la consulta por query
        	Session session = super.getSession();
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCountQuery = new StringBuffer();
        	stringQuery.append( "select ser from " );
        	stringQuery.append( ReferenceElementItem.class.getName() +" item, ");
        	stringQuery.append( Serialized.class.getName() +" ser ");
        	stringQuery.append( "inner join item.element " );
        	stringQuery.append( "where " );
        	stringQuery.append( "item.element.id = ser.elementId " );
        	stringQuery.append( "and item.reference.id = :referenceId " );
        	
        	// Paginación
        	stringCountQuery.append("select count(*) from ");
        	stringCountQuery.append( ReferenceElementItem.class.getName() +" item, ");
        	stringCountQuery.append( Serialized.class.getName() +" ser ");
        	stringCountQuery.append( "inner join item.element " );
        	stringCountQuery.append( "where " );
        	stringCountQuery.append( "item.element.id = ser.elementId " );
        	stringCountQuery.append( "and item.reference.id = :referenceId " );
        	
        	Query query = session.createQuery( stringQuery.toString() );
        	Query countQuery = session.createQuery(stringCountQuery.toString());
        	
        	query.setLong("referenceId", reference.getId());
        	countQuery.setLong("referenceId", reference.getId());
        	
            // Paginación
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
		
            /*List<ReferenceElementItem>items      = daoReferenceElementItem.getReferenceElementItemsByReferenceID( reference.getId() );
            List<Serialized>serializedList = new ArrayList<Serialized>();
            
               for( ReferenceElementItem item:items )
               {
            	   String isSerialized  = item.getElement().getIsSerialized();
            	     if( isSerialized.equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ){
            	    	 serializedList.add( daoSerialized.getSerializedByID( item.getElement().getId() ) );
            	      }
               }
            
               return serializedList;*/
               
        } catch (Throwable ex){
			log.error("== Error getSerializedElementInReference/ReferenceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedElementInReference/ReferenceDAO ==");
        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndSourceWh(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndSourceWh(Long ref,
			Long whSource) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getReferencesByReferenceAndSourceWh/ReferenceDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Reference.class.getName());
	        	stringQuery.append(" entity where entity.id = :anId and");
	        	stringQuery.append(" entity.warehouseBySourceWh = :anWhSID");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("anId", ref);
	            query.setLong("anWhSID", whSource);

	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getReferencesByReferenceAndSourceWh/ReferenceDAO ==");
	        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndTargerWh(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndTargerWh(Long ref,
			Long whTarger) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndTargerWh/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseByTargetWh = :anWhTID");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhTID", whTarger);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndTargerWh/ReferenceDAO ==");
        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndWh(Long ref,
			Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndWh/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseBySourceWh.id = :anWhSID and");
        	stringQuery.append(" entity.warehouseByTargetWh.id = :anWhTID");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhSID", whSource);
            query.setLong("anWhTID", whTarger);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndWh/ReferenceDAO ==");
        }
	}
	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferenceElementItemsByReferenceID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reference> getReferencesByElementId(Long elementId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceByElementId/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select distinct entity.reference from ");
        	stringQuery.append(ReferenceElementItem.class.getName());
        	stringQuery.append(" entity where entity.element.id = :elementId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementId", elementId);
           
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByElementId/ReferenceDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndWhRefStatus(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public ReferenceResponse getReferencesByReferenceAndWhRefStatus(Long ref,
			Long whSource, Long whTarger, Long refStatus, User user, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndWhRefStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	stringCount.append("select count(*) ");
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where 1=1");
        	
        	if(refStatus!=null && refStatus!=0){
        		stringQuery.append(" and entity.referenceStatus.id = :refStatusId");
        	}
        	
        	if(ref!=null && ref!=0){
        		stringQuery.append(" and entity.id = :anId");
        	}
        	if(whSource!=null && whSource!=0){
        		stringQuery.append(" and entity.warehouseBySourceWh.id = :anWhSID");
        	}
        	if(whTarger!=null && whTarger!=0){
        		stringQuery.append(" and entity.warehouseByTargetWh.id = :anWhTID");
        	}
        	
        	stringQuery.append(" and entity.countryCodeId.id = :countryId ");
        	
        	StringBuffer stringExists = getRolExistsWarehousesSourceTarjetByUser(user);
        	stringQuery.append(stringExists.toString());
        	
        	stringQuery.append(" order by entity.id asc ");

        	Query queryCount = session.createQuery(stringCount.toString()+stringQuery.toString());
    		Query query = session.createQuery(stringQuery.toString());
        	
    		query.setLong("countryId", user.getCountry().getId());
    		queryCount.setLong("countryId", user.getCountry().getId());
    		
    		if(refStatus!=null &&refStatus!=0){
        		query.setLong("refStatusId", refStatus);
        		queryCount.setLong("refStatusId", refStatus);
        	}
        	if(ref!=null&&ref!=0){
        		query.setLong("anId", ref);
        		queryCount.setLong("anId", ref);
        	}
        	if(whSource!=null &&whSource!=0){
        		  query.setLong("anWhSID", whSource);
        		  queryCount.setLong("anWhSID", whSource);
        	}
        	if(whTarger!=null && whTarger!=0){
        		 query.setLong("anWhTID", whTarger);
        		 queryCount.setLong("anWhTID", whTarger);
        	}
        	
        	Long recordQty = 0L;
        	if( requestCollectionInfo != null ){
        		recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollectionInfo.getFirstResult() );
				query.setMaxResults( requestCollectionInfo.getMaxResults() );	
        	}

        	ReferenceResponse response = new ReferenceResponse();
        	List<Reference> reference= query.list();
        	if( requestCollectionInfo != null )
				populatePaginationInfo( response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), reference.size(), recordQty.intValue() );
        	response.setReferences(reference);
            return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndWhRefStatus/ReferenceDAO ==");
        }
	}
	
	private StringBuffer getRolExistsWarehousesSourceTarjetByUser(User user) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicio getRolExistsWarehousesSourceTarjetByUser/WarehouseDAO ==");
        
		boolean searchSingleDealer = true,searchSingleDealerSourceWh=true, includeCrews = false, includeBranches = false;
		StringBuffer stringQuery = new StringBuffer();
		
        try {
        	
        	if(user.getDealer()!=null){
        		
        			Long dealerID=user.getDealer().getId();
                
	            	String roleTypeCode = user.getRol().getRoleType().getRoleTypeCode();
		        	
		        	if(roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity())) {
		        		searchSingleDealer = false;
		        		includeCrews = true;
		        		includeBranches = true;
		        	}
		        	
		        	if (roleTypeCode.equals( CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity())) {//operador logistico
			            	searchSingleDealerSourceWh = true;
			            	searchSingleDealer = false;
			        		includeCrews = false;
			        } else if (roleTypeCode.equals( CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity())) {//analista de logistica dealer
			            	searchSingleDealerSourceWh = true;
			            	searchSingleDealer = true;
			        		includeCrews = true;
			        } else if (roleTypeCode.equals( CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity())) {//analista de logistica DTV
			            	searchSingleDealerSourceWh = false;
			            	searchSingleDealer = false;
			        		includeCrews = false;
			        		includeBranches = true;
			        }
				
		        	stringQuery.append(" and exists (select 1 from ");
		        	stringQuery.append(  Warehouse.class.getName()).append(" warehouse  ");
		        	stringQuery.append("              where warehouse.isActive = '"+CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity()+"'");
		        	stringQuery.append("                    and  warehouse.warehouseType.isVirtual = '"+CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity()+"' ");
		        	stringQuery.append("                    and  warehouse.warehouseType.isActive = '"+CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity()+"' ");
		        	stringQuery.append("                    and  warehouse.customerId IS NULL ");
		        	stringQuery.append("                    and  entity.warehouseBySourceWh = warehouse.id ");

		        	if(searchSingleDealerSourceWh){
		        		stringQuery.append(" and (warehouse.dealerId.id = "+dealerID+" ");
		        		if(includeBranches){
		            		stringQuery.append(" or warehouse.dealerId.dealer.id = "+dealerID+" ");        			
		        		}
		        		stringQuery.append(") ");
		        	}else if(!includeBranches){
		        		stringQuery.append(" and warehouse.dealerId.dealer IS NULL ");
		        	}
		        	
		        	if(!includeCrews){
		        		stringQuery.append(" and warehouse.crewId IS NULL ");
		        	}
		        	stringQuery.append(" ) and ");
		        	
		        	stringQuery.append(" exists (select 1 from ");
		        	stringQuery.append(Warehouse.class.getName());
		        	stringQuery.append(" warehouse where warehouse.isActive = '"+CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity()+"'");
		        	stringQuery.append(" and  warehouse.warehouseType.isVirtual = '"+CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity()+"' ");
		        	stringQuery.append(" and  warehouse.warehouseType.isActive = '"+CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity()+"' ");
		        	stringQuery.append(" and  warehouse.customerId IS NULL ");
		        	stringQuery.append(" and  entity.warehouseByTargetWh = warehouse.id ");
		        	
		        	if(searchSingleDealer){
		        		stringQuery.append(" and (warehouse.dealerId.id = "+dealerID+" ");
		        		if(includeBranches){
		            		stringQuery.append(" or warehouse.dealerId.dealer.id = "+dealerID+" ");        			
		        		}
		        		stringQuery.append(") ");
		        	}else if(!includeBranches){
		        		stringQuery.append(" and warehouse.dealerId.dealer IS NULL ");
		        	}
		        	
		        	if(!includeCrews){
		        		stringQuery.append(" and warehouse.crewId IS NULL ");
		        	}
		        	
		        	stringQuery.append(" ) ");
		        	
            }
        	
            return stringQuery;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRolExistsWarehousesSourceTarjetByUser/WarehouseDAO ==");
        }
	}
	
	
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByConfirmationDateAndByCountry(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ReferenceResponse getReferencesByConfirmationDateAndByCountry(Long userId,Long country, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicio getReferencesByConfirmationDateAndByCountry/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringSelect = new StringBuffer(" select reference ");
        	StringBuffer stringCount = new StringBuffer(" select count(*) ");
        	StringBuffer stringOrder = new StringBuffer(" order by reference.id ");
        	
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" reference, ");
        	stringQuery.append(Warehouse.class.getName());
        	stringQuery.append(" warehouse where ");
        	stringQuery.append(" reference.countryCodeId.id = :country ");
        	stringQuery.append("and reference.deliveryDate < :actualDate ");
        	stringQuery.append("and (reference.referenceStatus.refStatusCode = :sendedStatusCode ");
        	stringQuery.append("or reference.referenceStatus.refStatusCode = :partialReceptedStatusCode) ");
        	stringQuery.append("and warehouse.id = reference.warehouseByTargetWh.id ");
        	stringQuery.append("and ( warehouse.dealerId.id = :userId )");
        	
        	Query query = session.createQuery(stringSelect.toString() + stringQuery.toString() + stringOrder.toString());
        	Query countQuery = session.createQuery(stringCount.toString() + stringQuery.toString());
        	
        	query.setLong("country", country);
        	countQuery.setLong("country", country);
        	query.setDate("actualDate", UtilsBusiness.fechaActual());
        	countQuery.setDate("actualDate", UtilsBusiness.fechaActual());
        	query.setString("sendedStatusCode", CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
        	countQuery.setString("sendedStatusCode", CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
        	query.setString("partialReceptedStatusCode", CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        	countQuery.setString("partialReceptedStatusCode", CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        	query.setLong("userId", userId);
        	countQuery.setLong("userId", userId);
        	
          //Paginacion
        	Long recordQty = 0L;
        	if( requestCollectionInfo != null ){            	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollectionInfo.getFirstResult() );
				query.setMaxResults( requestCollectionInfo.getMaxResults() );				
        	}
        	
        	ReferenceResponse response = new ReferenceResponse();
        	List<Reference> reference = query.list();
        	if( requestCollectionInfo != null )
				populatePaginationInfo( response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), reference.size(), recordQty.intValue() );
        	response.setReferences( reference );
        	
        	return response;

        } catch (Throwable ex){
			log.error("== Error getReferencesByConfirmationDateAndByCountry/ReferenceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByConfirmationDateAndByCountry/ReferenceDAO ==");
        }
	}
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferencesByConfirmationDate()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reference>getReferenceByIdAndWarehouseTarget( Long referenceId,Long warehouseId )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicio getReferenceByIdAndWarehouseTarget/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.referenceStatus.id in(:statusSent,:statusPartialCon) ");
        	if( referenceId!=null )
        	stringQuery.append("and entity.id = :referenceId ");
        	if( warehouseId!=null )
        	stringQuery.append("and entity.warehouseByTargetWh.id = :warehouseId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            
        	query.setParameter("statusSent", CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getIdEntity( ReferenceStatus.class.getName() ) );
        	query.setParameter("statusPartialCon", CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getIdEntity( ReferenceStatus.class.getName() ) );
            
            if( referenceId!=null )
              query.setParameter("referenceId", referenceId);
            if( warehouseId!=null )
              query.setParameter("warehouseId", warehouseId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIdAndWarehouseTarget/ReferenceDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferencesByConfirmationDate()
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reference>getReferenceByIdAndWarehouseSourceAndWareHouseTarget( Long referenceId,Long whSourceId,Long whTargetId, String referenceStatusCode )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicio getReferenceByIdAndWarehouseSource/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.referenceStatus.refStatusCode =:statusCreated ");
        	if( referenceId!=null )
        	stringQuery.append("and entity.id = :referenceId ");
        	if( whSourceId!=null )
        	stringQuery.append("and entity.warehouseBySourceWh.id = :whSourceId ");
        	if( whTargetId!=null )
            stringQuery.append("and entity.warehouseByTargetWh.id = :whTargetId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            
        	query.setParameter("statusCreated", referenceStatusCode );
            
            if( referenceId!=null )
              query.setParameter("referenceId", referenceId);
            if( whSourceId!=null )
              query.setParameter("whSourceId",whSourceId);
            if( whTargetId!=null )
              query.setParameter("whTargetId",whSourceId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIdAndWarehouseSource/ReferenceDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal#getReferencesByConfirmationDate()
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reference>getReferenceByIdAndWarehouseSource( Long referenceId,Long warehouseId, String referenceStatusCode )throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicio getReferenceByIdAndWarehouseSource/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.referenceStatus.refStatusCode = :statusCreated ");
        	if( referenceId!=null )
        	stringQuery.append("and entity.id = :referenceId ");
        	if( warehouseId!=null )
        	stringQuery.append("and entity.warehouseBySourceWh.id = :warehouseId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            
        	query.setString("statusCreated", referenceStatusCode);
            
            if( referenceId!=null )
              query.setParameter("referenceId", referenceId);
            if( warehouseId!=null )
              query.setParameter("warehouseId", warehouseId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIdAndWarehouseSource/ReferenceDAO ==");
        }
    }
    

	/*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByTargetWareHouseAndCreatedStatus(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesByTargetWareHouseAndCreatedStatus(Long idWhTarget)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByTargetWareHouseAndCreatedStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseByTargetWh.id = :aWhTargerId ");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode = :statusOpen ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhTargerId", idWhTarget);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByTargetWareHouseAndCreatedStatus/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceWareHouseAndCreatedStatus(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesBySourceWareHouseAndCreatedStatus(Long idWhSource)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesBySourceWareHouseAndCreatedStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseBySourceWh.id = :aWhSourceId ");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode = :statusOpen ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhSourceId", idWhSource);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceWareHouseAndCreatedStatus/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceAndTargetWareHouseAndCreatedStatus(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndCreatedStatus(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesBySourceAndTargetWareHouseAndCreatedStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	
        	boolean idWhSourceSpecified = false , idWhTargetSpecified = false;
        	
        	if(idWhSource != null && idWhSource.longValue() > 0){
        		idWhSourceSpecified = true;
        	}
        	
        	if(idWhTarget != null && idWhTarget.longValue() > 0){
        		idWhTargetSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" entity.referenceStatus.refStatusCode = :statusOpen ");
        	if(idWhSourceSpecified){
        		stringQuery.append(" and entity.warehouseBySourceWh.id = :aWhSourceId");
        	}
        	if(idWhSourceSpecified && idWhTargetSpecified){
        		stringQuery.append(" and entity.warehouseByTargetWh.id = :aWhTargerId");
        	}else if(idWhTargetSpecified){
        		stringQuery.append(" and entity.warehouseByTargetWh.id = :aWhTargerId");
        	}
        	
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            if(idWhSourceSpecified){
            	query.setLong("aWhSourceId", idWhSource);
            }
            if(idWhTargetSpecified){
            	query.setLong("aWhTargerId", idWhTarget);
            }
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndCreatedStatus/ReferenceDAO ==");
        }
	}
	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferencesDAOLocal#getReferencesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reference getReferenceByIDAndCreatedStatus(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceByIDAndCreatedStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode = :statusOpen ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );

            return (Reference) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIDAndCreatedStatus/ReferenceDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndSourceWhAndCreatedStatus(java.lang.Long, java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndSourceWhAndCreatedStatus(Long ref,
			Long whSource) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getReferencesByReferenceAndSourceWh/ReferenceDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Reference.class.getName());
	        	stringQuery.append(" entity where entity.id = :anId and");
	        	stringQuery.append(" entity.warehouseBySourceWh = :anWhSID");
	        	stringQuery.append(" and entity.referenceStatus.refStatusCode = :statusOpen ");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("anId", ref);
	            query.setLong("anWhSID", whSource);
	            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );

	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getReferencesByReferenceAndSourceWh/ReferenceDAO ==");
	        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndTargerWhAndCreatedStatus(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndTargerWhAndCreatedStatus(Long ref,
			Long whTarger) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndTargerWh/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseByTargetWh = :anWhTID");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode = :statusOpen ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhTID", whTarger);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndTargerWh/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndWhAndCreatedStatus(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndWhAndCreatedStatus(Long ref,
			Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndWh/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseBySourceWh.id = :anWhSID and");
        	stringQuery.append(" entity.warehouseByTargetWh.id = :anWhTID");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode = :statusOpen ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhSID", whSource);
            query.setLong("anWhTID", whTarger);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndWh/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByTargetWareHouseAndCreatedStatusAndCreationStatus(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesByTargetWareHouseAndCreatedStatusAndCreationStatus(Long idWhTarget)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByTargetWareHouseAndCreatedStatusAndCreationStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseByTargetWh.id = :aWhTargerId ");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusOpen,:creationStatus) ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhTargerId", idWhTarget);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
            query.setString("creationStatus",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByTargetWareHouseAndCreatedStatusAndCreationStatus/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceWareHouseAndCreatedStatusAndCreationStatus(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesBySourceWareHouseAndCreatedStatusAndCreationStatus(Long idWhSource)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesBySourceWareHouseAndCreatedStatusAndCreationStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseBySourceWh.id = :aWhSourceId ");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusOpen,:creationStatus) ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhSourceId", idWhSource);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
            query.setString("creationStatus",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceWareHouseAndCreatedStatusAndCreationStatus/ReferenceDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndCreatedStatusAndCreationStatus(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesBySourceAndTargetWareHouseAndCreatedStatusAndCreationStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	
        	boolean idWhSourceSpecified = false , idWhTargetSpecified = false;
        	
        	if(idWhSource != null && idWhSource.longValue() > 0){
        		idWhSourceSpecified = true;
        	}
        	
        	if(idWhTarget != null && idWhTarget.longValue() > 0){
        		idWhTargetSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" entity.referenceStatus.refStatusCode in (:statusOpen,:creationStatus)  ");
        	if(idWhSourceSpecified){
        		stringQuery.append(" and entity.warehouseBySourceWh.id = :aWhSourceId");
        	}
        	if(idWhSourceSpecified && idWhTargetSpecified){
        		stringQuery.append(" and entity.warehouseByTargetWh.id = :aWhTargerId");
        	}else if(idWhTargetSpecified){
        		stringQuery.append(" and entity.warehouseByTargetWh.id = :aWhTargerId");
        	}
        	
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            if(idWhSourceSpecified){
            	query.setLong("aWhSourceId", idWhSource);
            }
            if(idWhTargetSpecified){
            	query.setLong("aWhTargerId", idWhTarget);
            }
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
            query.setString("creationStatus",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() );
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndCreatedStatusAndCreationStatus/ReferenceDAO ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reference getReferenceByIDAndCreatedStatusAndCreatedStatus(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceByIDAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusOpen,:creationStatus) ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
            query.setString("creationStatus",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() );

            return (Reference) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIDAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
        }
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndSourceWhAndCreatedStatusAndCreatedStatus(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndSourceWhAndCreatedStatusAndCreatedStatus(Long ref,
			Long whSource) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getReferencesByReferenceAndSourceWhAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Reference.class.getName());
	        	stringQuery.append(" entity where entity.id = :anId and");
	        	stringQuery.append(" entity.warehouseBySourceWh = :anWhSID");
	        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusOpen,:creationStatus) ");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("anId", ref);
	            query.setLong("anWhSID", whSource);
	            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
	            query.setString("creationStatus",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() );

	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getReferencesByReferenceAndSourceWhAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
	        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndTargerWhAndCreatedStatusAndCreatedStatus(Long ref,
			Long whTarger) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndTargerWhAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseByTargetWh = :anWhTID");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusOpen,:creationStatus) ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhTID", whTarger);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
            query.setString("creationStatus",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndTargerWhAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndWhAndCreatedStatusAndCreatedStatus(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndWhAndCreatedStatusAndCreatedStatus(Long ref,
			Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndWhAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseBySourceWh.id = :anWhSID and");
        	stringQuery.append(" entity.warehouseByTargetWh.id = :anWhTID");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusOpen,:creationStatus) ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhSID", whSource);
            query.setLong("anWhTID", whTarger);
            query.setString("statusOpen",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() );
            query.setString("creationStatus",  CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndWhAndCreatedStatusAndCreatedStatus/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByTargetWareHouseAndSendedOrPartialConfirmed(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesByTargetWareHouseAndSendedOrPartialConfirmed(Long idWhTarget)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByTargetWareHouseAndSendedOrPartialConfirmed/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseByTargetWh.id = :aWhTargerId ");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusSended,:partialConfirmed) ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhTargerId", idWhTarget);
            query.setString("statusSended",  CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() );
            query.setString("partialConfirmed",  CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByTargetWareHouseAndSendedOrPartialConfirmed/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceWareHouseAndSendedOrPartialConfirmed(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesBySourceWareHouseAndSendedOrPartialConfirmed(Long idWhSource)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesBySourceWareHouse/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where  entity.warehouseBySourceWh.id = :aWhSourceId ");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusSended,:partialConfirmed) ");
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWhSourceId", idWhSource);
            query.setString("statusSended",  CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() );
            query.setString("partialConfirmed",  CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceWareHouse/ReferenceDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndSendedOrPartialConfirmed(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesBySourceAndTargetWareHouseAndSendedOrPartialConfirmed/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	
        	boolean idWhSourceSpecified = false , idWhTargetSpecified = false;
        	
        	if(idWhSource != null && idWhSource.longValue() > 0){
        		idWhSourceSpecified = true;
        	}
        	
        	if(idWhTarget != null && idWhTarget.longValue() > 0){
        		idWhTargetSpecified = true;
        	}
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" entity.referenceStatus.refStatusCode in (:statusSended,:partialConfirmed)  ");
        	if(idWhSourceSpecified){
        		stringQuery.append("and entity.warehouseBySourceWh.id = :aWhSourceId");
        	}
        	if(idWhSourceSpecified && idWhTargetSpecified){
        		stringQuery.append(" and entity.warehouseByTargetWh.id = :aWhTargerId");
        	}else if(idWhTargetSpecified){
        		stringQuery.append("and entity.warehouseByTargetWh.id = :aWhTargerId");
        	}
        	
        	stringQuery.append(" order by entity.creationReferenceDate");
        	Query query = session.createQuery(stringQuery.toString());
            if(idWhSourceSpecified){
            	query.setLong("aWhSourceId", idWhSource);
            }
            if(idWhTargetSpecified){
            	query.setLong("aWhTargerId", idWhTarget);
            }
            query.setString("statusSended",  CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() );
            query.setString("partialConfirmed",  CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndSendedOrPartialConfirmed/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferenceByIDAndSendedOrPartialConfirmed(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reference getReferenceByIDAndSendedOrPartialConfirmed(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceByIDAndSendedOrPartialConfirmed/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusSended,:partialConfirmed)  ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);
            query.setString("statusSended",  CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() );
            query.setString("partialConfirmed",  CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );

            return (Reference) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByIDAndSendedOrPartialConfirmed/ReferenceDAO ==");
        }
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndSourceWhAndSendedOrPartialConfirmed(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndSourceWhAndSendedOrPartialConfirmed(Long ref,
			Long whSource) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getReferencesByReferenceAndSourceWhAndSendedOrPartialConfirmed/ReferenceDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Reference.class.getName());
	        	stringQuery.append(" entity where entity.id = :anId and");
	        	stringQuery.append(" entity.warehouseBySourceWh = :anWhSID");
	        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusSended,:partialConfirmed)  ");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("anId", ref);
	            query.setLong("anWhSID", whSource);
	            query.setString("statusSended",  CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() );
	            query.setString("partialConfirmed",  CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );

	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getReferencesByReferenceAndSourceWhAndSendedOrPartialConfirmed/ReferenceDAO ==");
	        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndTargerWhAndSendedOrPartialConfirmed(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndTargerWhAndSendedOrPartialConfirmed(Long ref,
			Long whTarger) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndTargerWhAndSendedOrPartialConfirmed/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseByTargetWh = :anWhTID");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusSended,:partialConfirmed)  ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhTID", whTarger);
            query.setString("statusSended",  CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() );
            query.setString("partialConfirmed",  CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndTargerWhAndSendedOrPartialConfirmed/ReferenceDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByReferenceAndWhAndSendedOrPartialConfirmed(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> getReferencesByReferenceAndWhAndSendedOrPartialConfirmed(Long ref,
			Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getReferencesByReferenceAndWhAndSendedOrPartialConfirmed/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.id = :anId and");
        	stringQuery.append(" entity.warehouseBySourceWh.id = :anWhSID and");
        	stringQuery.append(" entity.warehouseByTargetWh.id = :anWhTID");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode in (:statusSended,:partialConfirmed)  ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", ref);
            query.setLong("anWhSID", whSource);
            query.setLong("anWhTID", whTarger);
            query.setString("statusSended",  CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() );
            query.setString("partialConfirmed",  CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByReferenceAndWhAndSendedOrPartialConfirmed/ReferenceDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getCreatedReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ReferenceResponse getCreatedReferencesByFilter(
			ReferencesFilterDTO referenceDTO,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getCreatedReferencesByFilter/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	boolean isDealer = ( referenceDTO.getDealer() != null && referenceDTO.getDealer().longValue() > 0 ) ? true : false;
        	boolean isBranchSource  = ( referenceDTO.getBranchDealerSource() != null && referenceDTO.getBranchDealerSource().longValue() > 0 ) ? true : false;
        	boolean isPrincipalSource = ( !isBranchSource && referenceDTO.getPrincipalDealerSource() != null && referenceDTO.getPrincipalDealerSource().longValue() > 0 ) ? true : false;
        	boolean isBranchTarget  = ( referenceDTO.getBranchDealerTarget() != null && referenceDTO.getBranchDealerTarget().longValue() > 0 ) ? true : false;
        	boolean isPrincipalTarget = ( !isBranchTarget && referenceDTO.getPrincipalDealerTarget() != null && referenceDTO.getPrincipalDealerTarget().longValue() > 0 ) ? true : false;
        	boolean isCrewSource = ( referenceDTO.getCrewSource() != null && referenceDTO.getCrewSource().longValue() > 0 ) ? true : false;
        	boolean isCrewTarget = ( referenceDTO.getCrewTarget() != null && referenceDTO.getCrewTarget().longValue() > 0 ) ? true : false;
        	boolean isId = ( referenceDTO.getId() != null && referenceDTO.getId().longValue() > 0 ) ? true : false;
        	boolean isWhSource = ( referenceDTO.getWhSource() != null && referenceDTO.getWhSource().longValue() > 0 ) ? true : false;
        	boolean isWhTarget = ( referenceDTO.getWhTarget() != null && referenceDTO.getWhTarget().longValue() > 0 ) ? true : false;
        	boolean isWhSourceType = ( referenceDTO.getWhTypeSource() != null && referenceDTO.getWhTypeSource().longValue() > 0 ) ? true : false;
        	boolean isWhTargetType = ( referenceDTO.getWhTypeTarget() != null && referenceDTO.getWhTypeTarget().longValue() > 0 ) ? true : false;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where (entity.referenceStatus.refStatusCode = :statusCreated or entity.referenceStatus.refStatusCode = :statusProcCreated) ");
        	if(isDealer){
        		stringQuery.append(" and entity.createUserId.dealer.id = :dealer");
        	}
        	if(isBranchSource){
        		stringQuery.append(" and entity.warehouseBySourceWh.dealerId.id = :branchDealerSource");
        	} 
        	if(isPrincipalSource){
        		stringQuery.append(" and entity.warehouseBySourceWh.dealerId.id = :principalDealerSource");
        	}
        	if(isBranchTarget){
        		stringQuery.append(" and entity.warehouseByTargetWh.dealerId.id = :branchDealerTarget");
        	}
        	if(isPrincipalTarget){
    			stringQuery.append(" and entity.warehouseByTargetWh.dealerId.id = :principalDealerTarget");
        	}
        	if(isCrewSource){
        		stringQuery.append(" and entity.warehouseBySourceWh.crewId.id = :crewSource");
        	}
        	if(isCrewTarget){
        		stringQuery.append(" and entity.warehouseByTargetWh.dealerId.dealer.id = :crewTarget");
        	}
        	if(isId){
        		stringQuery.append(" and entity.id = :id");
        	}
    		if(isWhSource){
    			stringQuery.append(" and entity.warehouseBySourceWh.id = :whSource");
        	}
    		if(isWhTarget){
    			stringQuery.append(" and entity.warehouseByTargetWh.id = :whTarget");
        	}
    		if(isWhSourceType){
    			stringQuery.append(" and entity.warehouseBySourceWh.warehouseType.id = :whTypeSource");
        	}
    		if(isWhTargetType){
    			stringQuery.append(" and entity.warehouseByTargetWh.warehouseType.id = :whTypeTarget");
        	}
    		
        	stringQuery.append(" order by entity.id asc");
        	
        	stringCount.append(stringQuery.toString());
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setString("statusCreated", CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
    		query.setString("statusProcCreated", CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity());
    		
    		if(isDealer){
    			query.setLong("dealer", referenceDTO.getDealer());
        	}
        	if(isBranchSource){
        		query.setLong("branchDealerSource", referenceDTO.getBranchDealerSource());
        	} 
        	if(isPrincipalSource){
        		query.setLong("principalDealerSource", referenceDTO.getPrincipalDealerSource());
        	}
        	if(isBranchTarget){
        		query.setLong("branchDealerTarget", referenceDTO.getBranchDealerTarget());
        	}
        	if(isPrincipalTarget){
    			query.setLong("principalDealerTarget", referenceDTO.getPrincipalDealerTarget());
        	}
        	if(isCrewSource){
        		query.setLong("crewSource", referenceDTO.getCrewSource());
        	}
        	if(isCrewTarget){
        		query.setLong("crewTarget", referenceDTO.getCrewTarget());
        	}
        	if(isId){
        		query.setLong("id", referenceDTO.getId());
        	}
    		if(isWhSource){
    			query.setLong("whSource", referenceDTO.getWhSource());
        	}
    		if(isWhTarget){
    			query.setLong("whTarget", referenceDTO.getWhTarget());
        	}
    		if(isWhSourceType){
    			query.setLong("whTypeSource", referenceDTO.getWhTypeSource());
        	}
    		if(isWhTargetType){
    			query.setLong("whTypeTarget", referenceDTO.getWhTypeTarget());
        	}
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(isDealer){
        			queryCount.setLong("dealer", referenceDTO.getDealer());
            	}
            	if(isBranchSource){
            		queryCount.setLong("branchDealerSource", referenceDTO.getBranchDealerSource());
            	} 
            	if(isPrincipalSource){
            		queryCount.setLong("principalDealerSource", referenceDTO.getPrincipalDealerSource());
            	}
            	if(isBranchTarget){
            		queryCount.setLong("branchDealerTarget", referenceDTO.getBranchDealerTarget());
            	}
            	if(isPrincipalTarget){
            		queryCount.setLong("principalDealerTarget", referenceDTO.getPrincipalDealerTarget());
            	}
            	if(isCrewSource){
            		queryCount.setLong("crewSource", referenceDTO.getCrewSource());
            	}
            	if(isCrewTarget){
            		queryCount.setLong("crewTarget", referenceDTO.getCrewTarget());
            	}
            	if(isId){
            		queryCount.setLong("id", referenceDTO.getId());
            	}
        		if(isWhSource){
        			queryCount.setLong("whSource", referenceDTO.getWhSource());
            	}
        		if(isWhTarget){
        			queryCount.setLong("whTarget", referenceDTO.getWhTarget());
            	}
        		if(isWhSourceType){
        			queryCount.setLong("whTypeSource", referenceDTO.getWhTypeSource());
            	}
        		if(isWhTargetType){
        			queryCount.setLong("whTypeTarget", referenceDTO.getWhTypeTarget());
            	}
        		queryCount.setString("statusCreated", CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
        		queryCount.setString("statusProcCreated", CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity());
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ReferenceResponse response = new ReferenceResponse();
        	List<Reference> reference= query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), reference.size(), recordQty.intValue() );
        	response.setReferences(reference);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCreatedReferencesByFilter/WarehouseDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesBySourceWhIdAndPreloadStatus(java.lang.Long, java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public ReferenceResponse getReferencesBySourceWhIdAndPreloadStatus(Long sourceWhId , String preloadStatus,String refStatusCode,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getReferencesBySourceWhIdAndPreloadStatus/WarehouseDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringCount.append("select count(*) ");
        	stringQuery.append("from ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where entity.isPreloadRef = :preloadStatus ");
        	stringQuery.append(" and entity.warehouseBySourceWh.id = :sourceWhId");
        	stringQuery.append(" and entity.referenceStatus.refStatusCode = :refStatusCode");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("preloadStatus", preloadStatus);
        	query.setString("refStatusCode", refStatusCode);
        	query.setLong("sourceWhId", sourceWhId);
        	
        	stringCount.append(stringQuery.toString());
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setString("preloadStatus", preloadStatus);
        		queryCount.setString("refStatusCode", refStatusCode);
        		queryCount.setLong("sourceWhId", sourceWhId);
        		recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );	
        	}
        	
        	ReferenceResponse response = new ReferenceResponse();
        	List<Reference> reference= query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), reference.size(), recordQty.intValue() );
        	response.setReferences(reference);
			
			return response;
        } catch (Throwable ex){
			log.error("== Error getReferencesBySourceWhIdAndPreloadStatus/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesBySourceWhIdAndPreloadStatus/WarehouseDAO ==");
        }
	}
	
	@Override
	public List<Reference> getReferencesByParentReferenceId(Long parentReferenceId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferencesByParentReferenceId/ReferenceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("select new ")
	        	.append(Reference.class.getName()).append("(entity.id) from ")
	        	.append(Reference.class.getName())
	        	.append(" entity where entity.parentReferenceId = :parentReferenceId");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("parentReferenceId", parentReferenceId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error en getReferencesByParentReferenceId/ReferenceDAO == " + ex.getMessage());
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByParentReferenceId/ReferenceDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public ReferenceResponse getReferencesByFilter(ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio getReferencesByFilter/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	boolean isDealer = ( referenceDTO.getDealer() != null && referenceDTO.getDealer().longValue() > 0 ) ? true : false;
        	boolean isBranchSource  = ( referenceDTO.getBranchDealerSource() != null && referenceDTO.getBranchDealerSource().longValue() > 0 ) ? true : false;
        	boolean isPrincipalSource = ( !isBranchSource && referenceDTO.getPrincipalDealerSource() != null && referenceDTO.getPrincipalDealerSource().longValue() > 0 ) ? true : false;
        	boolean isBranchTarget  = ( referenceDTO.getBranchDealerTarget() != null && referenceDTO.getBranchDealerTarget().longValue() > 0 ) ? true : false;
        	boolean isPrincipalTarget = ( !isBranchTarget && referenceDTO.getPrincipalDealerTarget() != null && referenceDTO.getPrincipalDealerTarget().longValue() > 0 ) ? true : false;
        	boolean isCrewSource = ( referenceDTO.getCrewSource() != null && referenceDTO.getCrewSource().longValue() > 0 ) ? true : false;
        	boolean isCrewTarget = ( referenceDTO.getCrewTarget() != null && referenceDTO.getCrewTarget().longValue() > 0 ) ? true : false;
        	boolean isId = ( referenceDTO.getId() != null && referenceDTO.getId().longValue() > 0 ) ? true : false;
        	boolean isWhSource = ( referenceDTO.getWhSource() != null && referenceDTO.getWhSource().longValue() > 0 ) ? true : false;
        	boolean isWhTarget = ( referenceDTO.getWhTarget() != null && referenceDTO.getWhTarget().longValue() > 0 ) ? true : false;
        	boolean isWhSourceType = ( referenceDTO.getWhTypeSource() != null && referenceDTO.getWhTypeSource().longValue() > 0 ) ? true : false;
        	boolean isWhTargetType = ( referenceDTO.getWhTypeTarget() != null && referenceDTO.getWhTypeTarget().longValue() > 0 ) ? true : false;
        	boolean isReferenceStatus = ( referenceDTO.getReferenceStatusIds() != null && !referenceDTO.getReferenceStatusIds().isEmpty() ) ? true : false;
        	boolean isRnNumber = ( referenceDTO.getRnNumber() != null && !referenceDTO.getRnNumber().isEmpty() ) ? true : false;
        	boolean isPreloadRef = ( referenceDTO.getIsPreloadRef() != null && !referenceDTO.getIsPreloadRef().isEmpty() ) ? true : false;
        	boolean isPrepaidRef = ( referenceDTO.getIsPrepaidRef() != null && !referenceDTO.getIsPrepaidRef().isEmpty() ) ? true : false;
        	boolean isCountryId  = ( referenceDTO.getCountryId() != null && referenceDTO.getCountryId().longValue() > 0 ) ? true : false;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(Reference.class.getName());
        	stringQuery.append(" entity where 1=1 ");
        	
        	if( isRnNumber ){
        		stringQuery.append(" and entity.rnNumber = :rnNumber ");
        	}
        	if( isPreloadRef ){
        		stringQuery.append(" and entity.isPreloadRef = :isPreloadRef ");
        	}
        	if( isPrepaidRef ){
        		stringQuery.append(" and entity.isPrepaidRef = :isPrepaidRef ");
        	}
        	
        	if( isReferenceStatus ){
        		stringQuery.append(" and entity.referenceStatus.id in (" + UtilsBusiness.longListToString(referenceDTO.getReferenceStatusIds(), ",") + ") ");
        	}
        	if(isDealer){
        		stringQuery.append(" and entity.createUserId.dealer.id = :dealer");
        	}
        	if(isPrincipalSource){
        		stringQuery.append(" and (entity.warehouseBySourceWh.dealerId.id = :principalDealerSource or entity.warehouseBySourceWh.dealerId.dealer.id = :principalDealerSource)");
        	}
        	if(isBranchSource){
        		stringQuery.append(" and entity.warehouseBySourceWh.dealerId.id = :branchDealerSource");
        	} 
        	if(isPrincipalTarget){
    			stringQuery.append(" and (entity.warehouseByTargetWh.dealerId.id = :principalDealerTarget or entity.warehouseByTargetWh.dealerId.dealer.id = :principalDealerTarget)");
        	}
        	
        	if(isBranchTarget){
        		stringQuery.append(" and entity.warehouseByTargetWh.dealerId.id = :branchDealerTarget");
        	}
        	
        	if(isCrewSource){
        		stringQuery.append(" and entity.warehouseBySourceWh.crewId.id = :crewSource");
        	}
        	if(isCrewTarget){
        		stringQuery.append(" and entity.warehouseByTargetWh.crewId.id = :crewTarget");
        	}
        	if(isId){
        		stringQuery.append(" and entity.id = :id");
        	}
    		if(isWhSource){
    			stringQuery.append(" and entity.warehouseBySourceWh.id = :whSource");
        	}
    		if(isWhTarget){
    			stringQuery.append(" and entity.warehouseByTargetWh.id = :whTarget");
        	}
    		if(isWhSourceType){
    			stringQuery.append(" and entity.warehouseBySourceWh.warehouseType.id = :whTypeSource");
        	}
    		if(isWhTargetType){
    			stringQuery.append(" and entity.warehouseByTargetWh.warehouseType.id = :whTypeTarget");
        	}
    		
    		if(isCountryId){
    			stringQuery.append(" and entity.countryCodeId.id = :countryId");
        	}
    		
        	stringQuery.append(" order by entity.id desc");
        	
        	stringCount.append(stringQuery.toString());
        	Query query = session.createQuery(stringQuery.toString());
        	
        	if( isRnNumber ){
        		query.setString("rnNumber", referenceDTO.getRnNumber());
        	}
        	if( isPreloadRef ){
        		query.setString("isPreloadRef", referenceDTO.getIsPreloadRef());
        	}
        	if( isPrepaidRef ){
        		query.setString("isPrepaidRef", referenceDTO.getIsPrepaidRef());
        	}
        	
    		if(isDealer){
    			query.setLong("dealer", referenceDTO.getDealer());
        	}
        	if(isBranchSource){
        		query.setLong("branchDealerSource", referenceDTO.getBranchDealerSource());
        	} 
        	if(isPrincipalSource){
        		query.setLong("principalDealerSource", referenceDTO.getPrincipalDealerSource());
        	}
        	if(isBranchTarget){
        		query.setLong("branchDealerTarget", referenceDTO.getBranchDealerTarget());
        	}
        	if(isPrincipalTarget){
    			query.setLong("principalDealerTarget", referenceDTO.getPrincipalDealerTarget());
        	}
        	if(isCrewSource){
        		query.setLong("crewSource", referenceDTO.getCrewSource());
        	}
        	if(isCrewTarget){
        		query.setLong("crewTarget", referenceDTO.getCrewTarget());
        	}
        	if(isId){
        		query.setLong("id", referenceDTO.getId());
        	}
    		if(isWhSource){
    			query.setLong("whSource", referenceDTO.getWhSource());
        	}
    		if(isWhTarget){
    			query.setLong("whTarget", referenceDTO.getWhTarget());
        	}
    		if(isWhSourceType){
    			query.setLong("whTypeSource", referenceDTO.getWhTypeSource());
        	}
    		if(isWhTargetType){
    			query.setLong("whTypeTarget", referenceDTO.getWhTypeTarget());
        	}
    		
    		if(isCountryId){
    			query.setLong("countryId", referenceDTO.getCountryId());
        	}
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		Query queryCount = session.createQuery(stringCount.toString());
        		
        		if( isRnNumber ){
        			queryCount.setString("rnNumber", referenceDTO.getRnNumber());
            	}
            	if( isPreloadRef ){
            		queryCount.setString("isPreloadRef", referenceDTO.getIsPreloadRef());
            	}
            	if( isPrepaidRef ){
            		queryCount.setString("isPrepaidRef", referenceDTO.getIsPrepaidRef());
            	}
        		if(isDealer){
        			queryCount.setLong("dealer", referenceDTO.getDealer());
            	}
            	if(isBranchSource){
            		queryCount.setLong("branchDealerSource", referenceDTO.getBranchDealerSource());
            	} 
            	if(isPrincipalSource){
            		queryCount.setLong("principalDealerSource", referenceDTO.getPrincipalDealerSource());
            	}
            	if(isBranchTarget){
            		queryCount.setLong("branchDealerTarget", referenceDTO.getBranchDealerTarget());
            	}
            	if(isPrincipalTarget){
            		queryCount.setLong("principalDealerTarget", referenceDTO.getPrincipalDealerTarget());
            	}
            	if(isCrewSource){
            		queryCount.setLong("crewSource", referenceDTO.getCrewSource());
            	}
            	if(isCrewTarget){
            		queryCount.setLong("crewTarget", referenceDTO.getCrewTarget());
            	}
            	if(isId){
            		queryCount.setLong("id", referenceDTO.getId());
            	}
        		if(isWhSource){
        			queryCount.setLong("whSource", referenceDTO.getWhSource());
            	}
        		if(isWhTarget){
        			queryCount.setLong("whTarget", referenceDTO.getWhTarget());
            	}
        		if(isWhSourceType){
        			queryCount.setLong("whTypeSource", referenceDTO.getWhTypeSource());
            	}
        		if(isWhTargetType){
        			queryCount.setLong("whTypeTarget", referenceDTO.getWhTypeTarget());
            	}
        		
        		if(isCountryId){
        			queryCount.setLong("countryId", referenceDTO.getCountryId());
            	}
        		
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );		
				
        	}
        	
        	ReferenceResponse response = new ReferenceResponse();
        	List<Reference> reference= query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), reference.size(), recordQty.intValue() );
        	response.setReferences(reference);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error getReferencesByFilter/WarehouseDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferencesByFilter/WarehouseDAO ==");
        }
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal#getCountUploadFilesByFileStatusAndReference(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getCountUploadFilesByFileStatusAndReference(String fileStatusCodePending, String fileStatusCodeProcessing, String uploadFileParam, String referenceId) throws DAOServiceException, DAOSQLException{
        try {
			log.debug("== Inicio getCountUploadFilesByFileStatusAndReference/ReferenceDAO ==");			
			Session session = super.getSession();

            StringBuilder query = new StringBuilder();
            
            query.append(" SELECT COUNT(*) ");
            query.append("    FROM REFERENCES R ");
            query.append("    INNER JOIN UPLOAD_FILE_PARAMS UFP ON UFP.PARA_VALUE = R.ID ");
            query.append("    INNER JOIN UPLOAD_FILES UF ON UF.ID = UFP.UPLOAD_FILE_ID ");
            query.append("    INNER JOIN FILE_STATUS FS ON FS.ID = UF.FILE_STATUS_ID ");
            query.append("    WHERE UFP.PARAM_NAME = :uploadFileParam ");
            query.append("    AND ( FS.CODE = :fileStatusCodeProcessing ");
            query.append("    OR FS.CODE = :fileStatusCodePending ) ");
            query.append("    AND UFP.PARA_VALUE = :referenceId ");
            
            Query querySQL = session.createSQLQuery(query.toString());
			
            querySQL.setParameter("uploadFileParam", uploadFileParam, Hibernate.STRING);
            querySQL.setParameter("fileStatusCodeProcessing", fileStatusCodeProcessing, Hibernate.STRING);
            querySQL.setParameter("fileStatusCodePending", fileStatusCodePending, Hibernate.STRING);
            querySQL.setParameter("referenceId", referenceId, Hibernate.STRING);
            
            Long result = ((BigDecimal)querySQL.uniqueResult()).longValue();
            
            return result;

        } catch (Throwable ex){
			log.error("== Error getCountUploadFilesByFileStatusAndReference ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCountUploadFilesByFileStatusAndReference/ReferenceDAO ==");
        }
	}
	
}
