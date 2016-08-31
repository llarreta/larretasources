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
import co.com.directv.sdii.model.pojo.MeasureUnit;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad MeasureUnit
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MeasureUnit
 * @see co.com.directv.sdii.model.hbm.MeasureUnit.hbm.xml
 */
@Stateless(name="MeasureUnitDAOLocal",mappedName="ejb/MeasureUnitDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MeasureUnitDAO extends BaseDao implements MeasureUnitDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(MeasureUnitDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitsDAOLocal#createMeasureUnit(co.com.directv.sdii.model.pojo.MeasureUnit)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createMeasureUnit(MeasureUnit obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createMeasureUnit/MeasureUnitDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el MeasureUnit ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMeasureUnit/MeasureUnitDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitsDAOLocal#updateMeasureUnit(co.com.directv.sdii.model.pojo.MeasureUnit)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateMeasureUnit(MeasureUnit obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateMeasureUnit/MeasureUnitDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el MeasureUnit ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMeasureUnit/MeasureUnitDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitsDAOLocal#deleteMeasureUnit(co.com.directv.sdii.model.pojo.MeasureUnit)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteMeasureUnit(MeasureUnit obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteMeasureUnit/MeasureUnitDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from MeasureUnit entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el MeasureUnit ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMeasureUnit/MeasureUnitDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitsDAOLocal#getMeasureUnitsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MeasureUnit getMeasureUnitByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMeasureUnitByID/MeasureUnitDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MeasureUnit.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (MeasureUnit) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMeasureUnitByID/MeasureUnitDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitsDAOLocal#getAllMeasureUnits()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MeasureUnit> getAllMeasureUnits() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMeasureUnits/MeasureUnitDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MeasureUnit.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllMeasureUnits/MeasureUnitDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal#getMeasureUnitByCode(java.lang.String)
	 */
	public MeasureUnit getMeasureUnitByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMeasureUnitByCode/MeasureUnitDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MeasureUnit.class.getName());
        	stringQuery.append(" entity where entity.unitCode = :anUnitCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anUnitCode", code);

            return (MeasureUnit) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMeasureUnitByCode/MeasureUnitDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal#getMeasureUnitByName(java.lang.String)
	 */
	public MeasureUnit getMeasureUnitByName(String name)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMeasureUnitByName/MeasureUnitDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(MeasureUnit.class.getName());
			stringQuery.append(" entity where upper(entity.unitName) = :anUnitName");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("anUnitName", name.toUpperCase());

			return (MeasureUnit) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getMeasureUnitByName/MeasureUnitDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal#getMeasureUnitsByStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MeasureUnit> getMeasureUnitsByStatus(String status)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMeasureUnitsByStatus/MeasureUnitDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MeasureUnit.class.getName());
        	stringQuery.append(" entity where entity.isActive = :status");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("status", status);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMeasureUnitsByStatus/MeasureUnitDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal#getMeasureUnitsByStatus(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MeasureUnitResponse getMeasureUnitsByStatus(String status,RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMeasureUnitsByStatus/MeasureUnitDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(MeasureUnit.class.getName());
        	stringQuery.append(" entity where entity.isActive = :status");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("status", status);

            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setString("status", status);
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	MeasureUnitResponse response = new MeasureUnitResponse();
        	List<MeasureUnit> measureUnitList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), measureUnitList.size(), recordQty.intValue() );
        	response.setMeasureUnit(measureUnitList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMeasureUnitsByStatus/MeasureUnitDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal#getMeasureUnitsByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MeasureUnitResponse getMeasureUnitsByAllStatusPage(
			String codeEntity, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMeasureUnitsByStatus/MeasureUnitDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(MeasureUnit.class.getName());
        	stringQuery.append(" entity ");
        	if(codeEntity!=null){
        		stringQuery.append(" where entity.unitCode = :unitCode ");
        	}
        	stringQuery.append(" order by entity.unitName asc");
        	Query query = session.createQuery(stringQuery.toString());
        	if(codeEntity!=null){
       		 query.setString("unitCode", codeEntity);
        	}
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(codeEntity!=null){
        			queryCount.setString("unitCode", codeEntity);
               	}
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	MeasureUnitResponse response = new MeasureUnitResponse();
        	List<MeasureUnit> measureUnitList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), measureUnitList.size(), recordQty.intValue() );
        	response.setMeasureUnit(measureUnitList);
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMeasureUnitsByStatus/MeasureUnitDAO ==");
        }
	}

}
