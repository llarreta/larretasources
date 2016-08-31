package co.com.directv.sdii.persistence.dao.assign.impl;

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
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Building
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Building
 * @see co.com.directv.sdii.model.hbm.Building.hbm.xml
 */
@Stateless(name="BuildingDAOLocal",mappedName="ejb/BuildingDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BuildingDAO extends BaseDao implements BuildingDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(BuildingDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal#createBuilding(co.com.directv.sdii.model.pojo.Building)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createBuilding(Building obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createBuilding/BuildingDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el Building ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createBuilding/BuildingDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal#updateBuilding(co.com.directv.sdii.model.pojo.Building)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateBuilding(Building obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateBuilding/BuildingDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el Building ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateBuilding/BuildingDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal#deleteBuilding(co.com.directv.sdii.model.pojo.Building)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteBuilding(Building obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteBuilding/BuildingDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Building entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el Building ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteBuilding/BuildingDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal#getBuildingByIBSBuildingCode(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Building getBuildingByIBSBuildingCode(Long ibsBuildingCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getBuildingByIBSBuildingCode/BuildingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Building.class.getName());
        	stringQuery.append(" entity where entity.code = :ibsBuildingCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("ibsBuildingCode", ibsBuildingCode);

            return (Building) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getBuildingByIBSBuildingCode/BuildingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getBuildingByIBSBuildingCode/BuildingDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal#getBuildingsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Building getBuildingByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getBuildingByID/BuildingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Building.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Building) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getBuildingByID/BuildingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getBuildingByID/BuildingDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal#getAllBuildings()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Building> getAllBuildings() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllBuildings/BuildingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Building.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllBuildings/BuildingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllBuildings/BuildingDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal#getBuildingByIbsBuldingCodeAndCountryCode(java.lang.Long, java.lang.String)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Building getBuildingByIbsBuldingCodeAndCountryCode(Long ibsBuildingCode,String countryCode)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getBuildingByIbsBuldingCodeAndCountryCode/BuildingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + Building.class.getName() + " entity ");
        	stringQuery.append(" where entity.code = :ibsBuildingCode ");
        	stringQuery.append("       and entity.country.countryCode = :countryCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("ibsBuildingCode", ibsBuildingCode);
            query.setString("countryCode", countryCode);

            return (Building) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getBuildingByIbsBuldingCodeAndCountryCode/BuildingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getBuildingByIbsBuldingCodeAndCountryCode/BuildingDAO ==");
        }
	}

}
