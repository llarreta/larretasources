package co.com.directv.sdii.persistence.dao.assign.impl;

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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerBulding;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerBulding
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerBulding
 * @see co.com.directv.sdii.model.hbm.DealerBulding.hbm.xml
 */
@Stateless(name="DealerBuldingDAOLocal",mappedName="ejb/DealerBuldingDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerBuldingDAO extends BaseDao implements DealerBuldingDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerBuldingDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#createDealerBulding(co.com.directv.sdii.model.pojo.DealerBulding)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerBulding(DealerBulding obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerBulding/DealerBuldingDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerBulding ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerBulding/DealerBuldingDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#updateDealerBulding(co.com.directv.sdii.model.pojo.DealerBulding)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerBulding(DealerBulding obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerBulding/DealerBuldingDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DealerBulding ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerBulding/DealerBuldingDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#deleteDealerBulding(co.com.directv.sdii.model.pojo.DealerBulding)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerBulding(DealerBulding obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerBulding/DealerBuldingDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerBulding entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DealerBulding ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerBulding/DealerBuldingDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#getDealerBuldingsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerBulding getDealerBuldingByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerBuldingByID/DealerBuldingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerBulding.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DealerBulding) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerBuldingByID/DealerBuldingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerBuldingByID/DealerBuldingDAO ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<Dealer> getDealersWhoAttendsABuilding(Long ibsBuildingCode, String countryCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerWhoAttendsABuilding/DealerBuldingDAO ==");
        Session session = super.getSession();
        List<Dealer> listDealer = null ;
        
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" select db.dealerCoverage.dealer ");
        	stringQuery.append("   from " + DealerBulding.class.getName() + " db ");
        	stringQuery.append("  where db.building.code = :ibsBuildingCode ");
        	stringQuery.append("        and db.building.country.countryCode = :aCountryCode ");
        	stringQuery.append("        and db.status = :aDbActiveStatus ");
        	stringQuery.append("        and db.dealerCoverage.status = :aDcActiveStatus ");
            
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("ibsBuildingCode", ibsBuildingCode);
        	query.setString("aCountryCode", countryCode);
        	query.setString("aDbActiveStatus", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	query.setString("aDcActiveStatus", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());

            listDealer = query.list();
            return listDealer ;

        } catch (Throwable ex){
			log.error("== Error getDealerWhoAttendsABuilding/DealerBuldingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerWhoAttendsABuilding/DealerBuldingDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#getAllDealerBuldings()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerBulding> getAllDealerBuldings() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerBuldings/DealerBuldingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerBulding.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerBuldings/DealerBuldingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerBuldings/DealerBuldingDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerBulding> getDealerBuldings() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerBuldings/DealerBuldingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerBulding.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerBuldings/DealerBuldingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerBuldings/DealerBuldingDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#getDealerBuildingsByPostalCodeId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerBuildingResponse getDealerBuildingsByPostalCodeId(Long postalCodeId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getBuildingByIbsBuldingCodeAndCountryCode/BuildingDAO ==");
        Session session = super.getSession();

        try {
        	String mainData = new StringBuffer("select new ").append(DealerBulding.class.getName()).append("(entity) from ")
	    		.append(DealerBulding.class.getName())
	    		.append(" entity where entity.building.postalCodeId = :postalCodeId")
	    		//deben cargarse activos o inactivos, pero teniendo en cuenta solo el último cambio
        		.append(" and entity.dateLastChange = (select max(db2.dateLastChange) from ")
        		.append(DealerBulding.class.getName()).append(" db2")
        		.append(" where db2.building.id = entity.building.id)")
        		.append(" order by entity.building.id").toString();
			
	    	Query query = session.createQuery(mainData);
	    	query.setLong("postalCodeId", postalCodeId);
	    	List<DealerBulding> mainResult = query.list();
	    	
	    	List<Long> buildingsWithCoverage = extractDistinctBuildingsIds(mainResult);
	    	
	    	String complementData = new StringBuffer(" select new ").append(DealerBulding.class.getName())
	    		.append("(cct) from ")
	    		.append(Building.class.getName())
	    		.append(" cct where cct.postalCodeId = :postalCodeId")
	    		.append( buildingsWithCoverage.isEmpty() ? "" : " and cct.id not in (:buildingsWithCoverage)")
	    		.append(" order by cct.id").toString();
	    	
	    	query = session.createQuery(complementData);
	    	query.setLong("postalCodeId", postalCodeId);
	    	if(!buildingsWithCoverage.isEmpty()) {
	    		query.setParameterList("buildingsWithCoverage", buildingsWithCoverage);
	    	}
	    	List<DealerBulding> complementResult = query.list();
	    	
	    	mainResult.addAll(complementResult);
	    	
        	// Paginación
	    	DealerBuildingResponse response = new DealerBuildingResponse();
	    	int totalRowCount = mainResult.size();
	    	
	    	mainResult = requestCollectionInfo.getPaginatedList(mainResult, requestCollectionInfo);
            response.setDealerBuildings(mainResult);
            
            if (requestCollectionInfo != null) {
            	populatePaginationInfo(response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), response.getDealerBuildings().size(), totalRowCount);
            }
        	
        	return response;
        } catch (Throwable ex){
			log.error("== Error getBuildingByIbsBuldingCodeAndCountryCode/BuildingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getBuildingByIbsBuldingCodeAndCountryCode/BuildingDAO ==");
        }
	}

	/**
	 * Metodo: extrae de <code>mainResult</code>, los distintos ids de los Buildings
	 * @param mainResult listado de DealerBuilding pertenecientes a un código postal,
	 * ordenados por buildingId activos o inactivos con una configuración
	 * vigente (la última realizada)
	 * @return
	 * @throws PropertiesException
	 * @author wjimenez
	 */
	private List<Long> extractDistinctBuildingsIds(
			List<DealerBulding> mainResult) throws PropertiesException {
		List<Long> buildingsWithCoverage = new ArrayList<Long>();
    	Long lastBuildingId = Long.MIN_VALUE;
    	for (DealerBulding dealerBuilding : mainResult) {
    		Long currentBuildingId = dealerBuilding.getBuilding().getId();
    		if(!lastBuildingId.equals(currentBuildingId)) {
    			//if(dealerBuilding.getStatus().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())) {
    				buildingsWithCoverage.add(currentBuildingId);
    			//}
    			lastBuildingId = currentBuildingId;
    		}
			
		}
		return buildingsWithCoverage;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#getDealerBuildingByDealerCoverageIdAndBuildingId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerBulding getDealerBuildingByDealerCoverageIdAndBuildingId(
			Long dealerCoverageId, Long buildingId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getDealerBuildingByDealerCoverageIdAndBuildingId/DealerBuldingDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(DealerBulding.class.getName());
        	stringQuery.append(" entity where entity.dealerCoverage.id = :dealerCoverageId and entity.building.id = :buildingId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("dealerCoverageId", dealerCoverageId);
        	query.setLong("buildingId", buildingId);

            return (DealerBulding) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerBuildingByDealerCoverageIdAndBuildingId/DealerBuldingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerBuildingByDealerCoverageIdAndBuildingId/DealerBuldingDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal#getDealerBuldingsByCountryAndStatus(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerBulding> getDealerBuldingsByCountryAndStatus(
			Long idCountry, String status) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getDealerBuldingsByCountryAndStatus/DealerBuldingDAO ==");
        Session session = super.getSession();
        
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerBulding.class.getName());
        	stringQuery.append(" entity where entity.dealerCoverage.country.id = :idCountry");
        	stringQuery.append(" and entity.status = :status");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("idCountry", idCountry);
            query.setString("status", status);
            return query.list() ;

        } catch (Throwable ex){
			log.error("== Error getDealerBuldingsByCountryAndStatus/DealerBuldingDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerBuldingsByCountryAndStatus/DealerBuldingDAO ==");
        }
	}
    
}
