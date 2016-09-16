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
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.pojo.AdjustmentElements;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AdjustmentElements
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AdjustmentElements
 * @see co.com.directv.sdii.model.hbm.AdjustmentElements.hbm.xml
 */
@Stateless(name="AdjustmentElementsDAOLocal",mappedName="ejb/AdjustmentElementsDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentElementsDAO extends BaseDao implements AdjustmentElementsDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentElementsDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal#createAdjustmentElements(co.com.directv.sdii.model.pojo.AdjustmentElements)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAdjustmentElements(AdjustmentElements obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAdjustmentElements/AdjustmentElementsDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el AdjustmentElements ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentElements/AdjustmentElementsDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal#updateAdjustmentElements(co.com.directv.sdii.model.pojo.AdjustmentElements)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAdjustmentElements(AdjustmentElements obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateAdjustmentElements/AdjustmentElementsDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el AdjustmentElements ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAdjustmentElements/AdjustmentElementsDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal#deleteAdjustmentElements(co.com.directv.sdii.model.pojo.AdjustmentElements)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAdjustmentElements(AdjustmentElements obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteAdjustmentElements/AdjustmentElementsDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from AdjustmentElements entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el AdjustmentElements ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustmentElements/AdjustmentElementsDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal#getAdjustmentElementssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentElements getAdjustmentElementsByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentElementsByID/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElements.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (AdjustmentElements) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsByID/AdjustmentElementsDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal#getAllAdjustmentElementss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AdjustmentElements> getAllAdjustmentElementss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllAdjustmentElementss/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElements.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAdjustmentElementss/AdjustmentElementsDAO ==");
        }
    }



	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AdjustmentElements> getAdjustmentElementsByAdjusmentId(Long idAdjustment)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllAdjustmentElementss/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElements.class.getName()+" entity");
        	stringQuery.append(" where entity.adjustment.id = :idAdjustment");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("idAdjustment", idAdjustment);
        	return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAdjustmentElementss/AdjustmentElementsDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AdjustmentElements> getAdjustmentElementsForAuthorization(Long adjustmentId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAdjustmentElementsForAuthorization/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("FROM ");
        	stringQuery.append(AdjustmentElements.class.getName() + " entity");
        	stringQuery.append(" WHERE entity.adjustment.id = :adjustmentId AND entity.adjustmentElementsStatus.code = :needAuthorization AND entity.adjustment.transferReason.transferReasonAuthorized = :needAuthorizationReason");

        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("adjustmentId", adjustmentId);

        	query.setString("needAuthorization", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	query.setString("needAuthorizationReason", CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity());
        	
        	List<AdjustmentElements> adjustmentElements = query.list();
        	
        	return adjustmentElements;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsForAuthorization/AdjustmentElementsDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<AdjustmentElements> getAdjustmentElementsForAuthorizationMassive(Long adjustmentId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAdjustmentElementsForAuthorization/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("FROM ");
        	stringQuery.append(AdjustmentElements.class.getName() + " entity");
        	stringQuery.append(" WHERE entity.adjustment.id = :adjustmentId AND entity.adjustmentElementsStatus.code = :needAuthorization AND entity.adjustment.transferReason.transferReasonAuthorized = :needAuthorizationReason");

        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("adjustmentId", adjustmentId);

        	query.setString("needAuthorization", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	query.setString("needAuthorizationReason", CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity());
        	      	
        	List<AdjustmentElements> adjustmentElements = query.list();
        	
        	return adjustmentElements;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsForAuthorization/AdjustmentElementsDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(
			   AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAdjustmentElementsForAuthorization/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	boolean isSerialCode = (request.getSerialCode() != null && !request.getSerialCode().equals(""));
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElements.class.getName() + " entity");
        	stringQuery.append(" where entity.adjustment.id = :adjustmentId");
        	
        	if(isSerialCode){
        		stringQuery.append(" and (UPPER(entity.serialized.serialCode) = UPPER(:serialCode) or EXISTS(SELECT 1 FROM Serialized s WHERE s.id = entity.serialized.serialized.id AND UPPER(s.serialCode) = UPPER(:serialCode)))");
        	}
        	
			//Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append( stringQuery.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );

        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("adjustmentId", request.getAdjustmentId());
        	countQuery.setLong("adjustmentId", request.getAdjustmentId());
        
			if(isSerialCode){
				query.setString("serialCode", request.getSerialCode());
				countQuery.setString("serialCode", request.getSerialCode());
			}
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	AdjustmentElementCollDTO response = new AdjustmentElementCollDTO();        	
        	List<AdjustmentElements> adjustmentElements = query.list();
        	if( requestCollInfo != null ){
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), adjustmentElements.size(), recordQty.intValue() );
        	}
        	response.setAdjustmentElements( adjustmentElements );

        	return response;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentElementsForAuthorization/AdjustmentElementsDAO ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long countAdjustmentElementsByStatus(Long adjustmentId, String statusCode)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	Long result;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("select count(*) ");
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentElements.class.getName() + " entity ");
        	stringQuery.append(" where entity.adjustment.id = :adjustmentId ");
        	stringQuery.append(" and entity.adjustmentElementsStatus.code = :statusCode ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("adjustmentId", adjustmentId);
        	query.setString("statusCode", statusCode);
        
        	result = (Long) query.uniqueResult();
        	
        	return result;
        } catch (Throwable ex){
			log.error("== Error ejecutando countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Object[] countAdjustmentElementsByAllStatus(Long adjustmentId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	Object[] result;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append(" select sum(case when entity.adjustmentElementsStatus.code = :adjustmentElementStatusPending then  ");
        	stringQuery.append("                 1  ");
        	stringQuery.append("            else  ");
        	stringQuery.append("                 0  ");
        	stringQuery.append("            end), ");
        	stringQuery.append("        sum(case when entity.adjustmentElementsStatus.code = :adjustmentElementStatusProcess then ");
        	stringQuery.append("                 1 ");
        	stringQuery.append("            else ");
        	stringQuery.append("                 0 ");
        	stringQuery.append("            end), ");
        	stringQuery.append("        sum(case when entity.adjustmentElementsStatus.code = :adjustmentElementStatusAuthorized then ");
        	stringQuery.append("                 1 ");
        	stringQuery.append("            else ");
        	stringQuery.append("                 0 ");
        	stringQuery.append("            end) ");
        	stringQuery.append(" from AdjustmentElements entity  ");
        	stringQuery.append(" where entity.adjustment.id = :adjustmentId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("adjustmentId", adjustmentId);
        	query.setString("adjustmentElementStatusPending", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	query.setString("adjustmentElementStatusProcess", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
        	query.setString("adjustmentElementStatusAuthorized", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_AUTHORIZED.getCodeEntity());
        	result = (Object[]) query.uniqueResult();
        	
        	return result;
        } catch (Throwable ex){
			log.error("== Error ejecutando countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Object[] countAdjustmentElementsByAllStatusMassive(Long adjustmentId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
        Session session = super.getSession();

        try {
        	Object[] result;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append(" select sum(case when entity.adjustmentElementsStatus.code = :adjustmentElementStatusPending then  ");
        	stringQuery.append("                 1  ");
        	stringQuery.append("            else  ");
        	stringQuery.append("                 0  ");
        	stringQuery.append("            end), ");
        	stringQuery.append("        sum(case when entity.adjustmentElementsStatus.code = :adjustmentElementStatusProcess then ");
        	stringQuery.append("                 1 ");
        	stringQuery.append("            else ");
        	stringQuery.append("                 0 ");
        	stringQuery.append("            end), ");
        	stringQuery.append("        sum(case when entity.adjustmentElementsStatus.code = :adjustmentElementStatusAuthorized then ");
        	stringQuery.append("                 1 ");
        	stringQuery.append("            else ");
        	stringQuery.append("                 0 ");
        	stringQuery.append("            end) ");
        	stringQuery.append(" from AdjustmentElements entity  ");
        	stringQuery.append(" where entity.adjustment.id = :adjustmentId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("adjustmentId", adjustmentId);
        	query.setString("adjustmentElementStatusPending", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	query.setString("adjustmentElementStatusProcess", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
        	query.setString("adjustmentElementStatusAuthorized", CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_AUTHORIZED.getCodeEntity());
        	result = (Object[]) query.uniqueResult();
        	
        	return result;
        } catch (Throwable ex){
			log.error("== Error ejecutando countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina countAdjustmentElementsByStatus/AdjustmentElementsDAO ==");
        }
	}
	
}
