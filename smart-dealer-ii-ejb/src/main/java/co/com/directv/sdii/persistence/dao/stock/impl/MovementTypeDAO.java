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
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad MovementType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MovementType
 * @see co.com.directv.sdii.model.hbm.MovementType.hbm.xml
 */
@Stateless(name="MovementTypeDAOLocal",mappedName="ejb/MovementTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovementTypeDAO extends BaseDao implements MovementTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(MovementTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovementTypesDAOLocal#createMovementType(co.com.directv.sdii.model.pojo.MovementType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createMovementType(MovementType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createMovementType/MovementTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el MovementType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMovementType/MovementTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovementTypesDAOLocal#updateMovementType(co.com.directv.sdii.model.pojo.MovementType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateMovementType(MovementType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateMovementType/MovementTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el MovementType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMovementType/MovementTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovementTypesDAOLocal#deleteMovementType(co.com.directv.sdii.model.pojo.MovementType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteMovementType(MovementType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteMovementType/MovementTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from MovementType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el MovementType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMovementType/MovementTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovementTypesDAOLocal#getMovementTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MovementType getMovementTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMovementTypeByID/MovementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MovementType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (MovementType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMovementTypeByID/MovementTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovementTypesDAOLocal#getAllMovementTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovementType> getAllMovementTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMovementTypes/MovementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MovementType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllMovementTypes/MovementTypeDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal#getAllMovementTypesClass()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovementTypeClassDTO> getAllMovementTypesClass() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMovementTypesClass/MovementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();

        	stringQuery.append(" Select distinct new "+ MovementTypeClassDTO.class.getName() +"(entity.movClass AS movClassCode, ");
        	stringQuery.append(" case when entity.movClass = 'E' then ");
        	stringQuery.append(" 	'Entrada' ");
        	stringQuery.append(" else ");
        	stringQuery.append(" 	'Salida' end AS movClassName) ");
        	stringQuery.append(" from ").append(MovementType.class.getName()).append(" entity ");
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllMovementTypesClass/MovementTypeDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal#getMovementTypeByCode(java.lang.String)
	 */
	public MovementType getMovementTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMovementTypeByCode/MovementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MovementType.class.getName());
        	stringQuery.append(" entity where entity.movTypeCode = :aMtCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aMtCode", code);

            return (MovementType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMovementTypeByCode/MovementTypeDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal#getMovementTypeByName(java.lang.String)
	 */
	public MovementType getMovementTypeByName(String name)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMovementTypeByName/MovementTypeDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(MovementType.class.getName());
			stringQuery.append(" entity where upper(entity.movTypeName) = upper(:aMtName)");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("aMtName", name);

			return (MovementType) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getMovementTypeByName/MovementTypeDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal#getMovementTypesStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovementType> getMovementTypesStatus(String codeStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMovementTypesStatus/MovementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MovementType.class.getName());
        	stringQuery.append(" entity where entity.movTypeStatus = :anStatus");
        	stringQuery.append(" order by entity.movTypeCode asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anStatus", codeStatus);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMovementTypesStatus/MovementTypeDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal#getMovementTypesStatus(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovementTypeResponse getMovementTypesStatus(String codeStatus,RequestCollectionInfo requestCollInfo)
		throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getMovementTypesStatus/MovementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(MovementType.class.getName());
        	stringQuery.append(" entity where entity.movTypeStatus = :anStatus");
        	stringQuery.append(" order by entity.movTypeCode asc");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anStatus", codeStatus);

          //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setString("anStatus", codeStatus);
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	MovementTypeResponse response = new MovementTypeResponse();
        	List<MovementType> movementType = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), movementType.size(), recordQty.intValue() );
        	response.setMovType(movementType );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMovementTypesStatus/MovementTypeDAO ==");
        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal#getMovementTypesAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovementTypeResponse getMovementTypesAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getMovementTypesAllStatusPage/MovementTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(MovementType.class.getName());
        	stringQuery.append(" entity ");
        	if(code!=null){
        		stringQuery.append(" where entity.movTypeCode = :movTypeCode ");
        		
        	}
        	stringQuery.append(" order by entity.movTypeCode asc");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	if(code!=null){
        		query.setString("movTypeCode", code);
        	}

          //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(code!=null){
        			queryCount.setString("movTypeCode", code);
            	}
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	MovementTypeResponse response = new MovementTypeResponse();
        	List<MovementType> movementType = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), movementType.size(), recordQty.intValue() );
        	response.setMovType(movementType );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMovementTypesAllStatusPage/MovementTypeDAO ==");
        }
	}

	@Override
	public List<MovementType> getMovementTypesAtiveByClass(String moveClass)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getMovementTypesAtiveByClass/MovementTypeDAO ==");
        Session session = super.getSession();
        boolean moveClassSpicified=false;

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MovementType.class.getName());
        	stringQuery.append(" entity where entity.movTypeStatus = :anStatus");
        	
        	if(moveClass!=null && !moveClass.trim().equals("")){
        		moveClassSpicified=true;
        		stringQuery.append(" and entity.movClass = :movClass");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("anStatus", CodesBusinessEntityEnum.MOVEMENT_TYPE_STATUS_ACTIVE.getCodeEntity());
        	
        	if(moveClassSpicified)
        		query.setString("movClass", moveClass);
        	
        	return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMovementTypesAtiveByClass/MovementTypeDAO ==");
        }
	}


}
