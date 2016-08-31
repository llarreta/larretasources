package co.com.directv.sdii.persistence.dao.assign.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
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
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.pojo.KpiConfiguration;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad KpiConfiguration
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.KpiConfiguration
 * @see co.com.directv.sdii.model.hbm.KpiConfiguration.hbm.xml
 */
@Stateless(name="KpiConfigurationDAOLocal",mappedName="ejb/KpiConfigurationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiConfigurationDAO extends BaseDao implements KpiConfigurationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(KpiConfigurationDAO.class);
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#createKpiConfiguration(co.com.directv.sdii.model.pojo.KpiConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createKpiConfiguration(KpiConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createKpiConfiguration/KpiConfigurationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el KpiConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiConfiguration/KpiConfigurationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#updateKpiConfiguration(co.com.directv.sdii.model.pojo.KpiConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateKpiConfiguration(KpiConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateKpiConfiguration/KpiConfigurationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el KpiConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiConfiguration/KpiConfigurationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#deleteKpiConfiguration(co.com.directv.sdii.model.pojo.KpiConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteKpiConfiguration(KpiConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteKpiConfiguration/KpiConfigurationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from KpiConfiguration entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el KpiConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiConfiguration/KpiConfigurationDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#getKpiConfigurationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiConfiguration getKpiConfigurationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getKpiConfigurationByID/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiConfiguration.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (KpiConfiguration) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getKpiConfigurationByID/KpiConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiConfigurationByID/KpiConfigurationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#getAllKpiConfigurations()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiConfiguration> getAllKpiConfigurations() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllKpiConfigurations/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiConfiguration.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllKpiConfigurations/KpiConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllKpiConfigurations/KpiConfigurationDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiConfiguration> getKpiConfigurationsByCalculationTypeCode(
			String calculationTypeCode) throws DAOServiceException,
			DAOSQLException {
    	log.debug("== Inicio getKpiConfigurationsByCalculationTypeCode/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(KpiConfiguration.class.getName());
        	stringQuery.append(" entity where entity.kpiCalculationType.code = :calculationTypeCode and entity.status = 'S'");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("calculationTypeCode", calculationTypeCode);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error en getKpiConfigurationsByCalculationTypeCode ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiConfigurationsByCalculationTypeCode/KpiConfigurationDAO ==");
        }
	}
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiCalculateDTO> getKpiCalculateDTOByCalculationTypeCode(String calculationTypeCode,
			                                                             Long countryId,
			                                                             Long userId) throws DAOServiceException,DAOSQLException {
    	log.debug("== Inicio getKpiCalculateDTOByCalculationTypeCode/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	
        	Calendar sysdate = Calendar.getInstance();
			sysdate.setTime(UtilsBusiness.getCurrentTimeZoneDateByUserId( userId, userDao));
			
        	stringQuery.append("select new " + KpiCalculateDTO.class.getName() ); 
        	stringQuery.append("          (entity.kpi.code, ");
        	stringQuery.append("	       entity.country.id, ");
        	stringQuery.append("	       entity.dayCount, ");
        	stringQuery.append("	       entity.kpi.id, ");
        	stringQuery.append("           entity.kpi.kpiType.id, ");
        	stringQuery.append("	       entity.serviceSuperCategory.id, ");
        	stringQuery.append("	       entity.zoneTypes.id, ");
        	stringQuery.append("	       entity.id, ");
        	stringQuery.append("           entity.frecuencyExpression) ");
        	stringQuery.append("	from " + KpiConfiguration.class.getName() + " entity ");
        	stringQuery.append("	where entity.kpiCalculationType.code = :calculationTypeCode "); 
        	stringQuery.append("	      and entity.status = 'S' ");
        	stringQuery.append("	      and entity.country.id = :aCountryId ");
        	stringQuery.append("	      and entity.nextExecutionDate <= :aSysdate ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("calculationTypeCode", calculationTypeCode);
            query.setLong("aCountryId", countryId);
            query.setTimestamp("aSysdate", new Timestamp( sysdate.getTimeInMillis() ) );
            return query.list();

        } catch (Throwable ex){
			log.error("== Error en getKpiCalculateDTOByCalculationTypeCode ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiCalculateDTOByCalculationTypeCode/KpiConfigurationDAO ==");
        }
	}



	@Override
	public KpiConfiguration getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(
			Long countryId, Long superCategoryId, Long kpiId, Long zoneTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(KpiConfiguration.class.getName());
        	stringQuery.append(" entity where entity.status = 'S'");
        	stringQuery.append(" and entity.country.id = :countryId");
        	stringQuery.append(" and entity.serviceSuperCategory.id = :superCategoryId");
        	stringQuery.append(" and entity.kpi.id = :kpiId");
        	stringQuery.append(" and entity.zoneTypes.id = :zoneTypeId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setLong("superCategoryId", superCategoryId);
            query.setLong("kpiId", kpiId);
            query.setLong("zoneTypeId", zoneTypeId);

            return (KpiConfiguration) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error en getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public KpiConfiguration getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(Long countryId, Long superCategoryId, String kpiCode, Long zoneTypeId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + KpiConfiguration.class.getName() + " entity ");
        	stringQuery.append(" where entity.status = :statusCode ");
        	stringQuery.append("       and entity.country.id = :countryId ");
        	stringQuery.append("       and entity.serviceSuperCategory.id = :superCategoryId ");
        	stringQuery.append("       and entity.zoneTypes.id = :zoneTypeId ");
        	stringQuery.append("       and entity.kpi.code = :kpiCode ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("statusCode", CodesBusinessEntityEnum.KPI_CONFIGURATION_STATUS_ACTIVE.getCodeEntity());
            query.setLong("countryId", countryId);
            query.setLong("superCategoryId", superCategoryId);
            query.setLong("zoneTypeId", zoneTypeId);
            query.setString("kpiCode", kpiCode);

            return (KpiConfiguration) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error en getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode/KpiConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode/KpiConfigurationDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiConfiguration> getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(
			Long countryId, Long serviceSuperCategoryId, Long zoneTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(KpiConfiguration.class.getName());
        	stringQuery.append(" entity where entity.status = :statusCode ");
        	stringQuery.append("and entity.country.id = :countryId ");
        	stringQuery.append("and entity.serviceSuperCategory.id = :superCategoryId ");
        	stringQuery.append("and entity.zoneTypes.id = :zoneTypeId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("statusCode", CodesBusinessEntityEnum.KPI_CONFIGURATION_STATUS_ACTIVE.getCodeEntity());
            query.setLong("countryId", countryId);
            query.setLong("superCategoryId", serviceSuperCategoryId);
            query.setLong("zoneTypeId", zoneTypeId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error en getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType/KpiConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode/KpiConfigurationDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiConfiguration getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(
			String countryCode, String serviceSuperCategoryCode,
			String zoneTypeCode, String kpiCode) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode/KpiConfigurationDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(KpiConfiguration.class.getName());
        	stringQuery.append(" entity where entity.status = :statusCode ");
        	stringQuery.append("and entity.country.countryCode = :countryCode ");
        	stringQuery.append("and entity.serviceSuperCategory.code = :superCategoryCode ");
        	stringQuery.append("and entity.zoneTypes.zoneTypeCode = :zoneTypeCode ");
        	stringQuery.append("and entity.kpi.code = :kpiCode ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("statusCode", CodesBusinessEntityEnum.KPI_CONFIGURATION_STATUS_ACTIVE.getCodeEntity());
            query.setString("countryCode", countryCode);
            query.setString("superCategoryCode", serviceSuperCategoryCode);
            query.setString("zoneTypeCode", zoneTypeCode);
            query.setString("kpiCode", kpiCode);

            return (KpiConfiguration) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error en getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode/KpiConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode/KpiConfigurationDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal#getKPIConfigurationsByCountryIdAndSupercategoryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<KpiConfiguration> getKPIConfigurationsByCountryIdAndSupercategoryId(
			Long countryId, Long serviceSuperCategoryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getKPIConfigurationsByCountryIdAndSupercategoryId/KpiConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ")
	        	.append(KpiConfiguration.class.getName())
	        	.append(" entity where entity.country.id = :countryId ")
	        	.append("and entity.serviceSuperCategory.id = :superCategoryId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setLong("superCategoryId", serviceSuperCategoryId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error en getKPIConfigurationsByCountryIdAndSupercategoryId/KpiConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKPIConfigurationsByCountryIdAndSupercategoryId/KpiConfigurationDAO ==");
        }
	}

}
