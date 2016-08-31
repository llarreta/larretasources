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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SkillConfiguration;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad SkillConfiguration
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SkillConfiguration
 * @see co.com.directv.sdii.model.hbm.SkillConfiguration.hbm.xml
 */
@Stateless(name="SkillConfigurationDAOLocal",mappedName="ejb/SkillConfigurationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillConfigurationDAO extends BaseDao implements SkillConfigurationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SkillConfigurationDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal#createSkillConfiguration(co.com.directv.sdii.model.pojo.SkillConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSkillConfiguration(SkillConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSkillConfiguration/SkillConfigurationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el SkillConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkillConfiguration/SkillConfigurationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal#updateSkillConfiguration(co.com.directv.sdii.model.pojo.SkillConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSkillConfiguration(SkillConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSkillConfiguration/SkillConfigurationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el SkillConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillConfiguration/SkillConfigurationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal#deleteSkillConfiguration(co.com.directv.sdii.model.pojo.SkillConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSkillConfiguration(SkillConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSkillConfiguration/SkillConfigurationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SkillConfiguration entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SkillConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkillConfiguration/SkillConfigurationDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal#getSkillConfigurationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillConfiguration getSkillConfigurationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSkillConfigurationByID/SkillConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SkillConfiguration.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (SkillConfiguration) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getSkillConfigurationByID/SkillConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSkillConfigurationByID/SkillConfigurationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal#getAllSkillConfigurations()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillConfiguration> getAllSkillConfigurations() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSkillConfigurations/SkillConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SkillConfiguration.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllSkillConfigurations/SkillConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSkillConfigurations/SkillConfigurationDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal#getSkillConfigurationsByCriteria(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SkillConfiguration> getSkillConfigurationsByCriteria(
			String countryCode, String serviceTypeCode, String skillModeTypeCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSkillConfigurationsByCriteria/SkillConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("from ");
        	stringQuery.append(SkillConfiguration.class.getName());
        	stringQuery.append(" skconf where skconf.skillModeType.code = :aSkModeTypeCode");
        	stringQuery.append(" and skconf.country.countryCode = :aCountryCode");
        	stringQuery.append(" and skconf.serviceType.serviceTypeCode = :aStCode");
        	stringQuery.append(" and skconf.status = :anActiveStatusCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aSkModeTypeCode", skillModeTypeCode);
        	query.setString("aCountryCode", countryCode);
        	query.setString("aStCode", serviceTypeCode);
        	query.setString("anActiveStatusCode", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	List<SkillConfiguration> result = query.list();
        	return result;
            
        } catch (Throwable ex){
			log.error("== Error getSkillConfigurationsByCriteria/SkillConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSkillConfigurationsByCriteria/SkillConfigurationDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal#getSkillConfigurationsByCountryIdAndCategoryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SkillConfiguration> getSkillConfigurationsByCountryIdAndCategoryId(
			Long countryId, Long serviceTypeId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getSkillConfigurationsByCountryIdAndCategoryId/SkillConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("from ");
        	stringQuery.append(SkillConfiguration.class.getName());
        	stringQuery.append(" skconf where skconf.country.id = :countryId");
        	stringQuery.append(" and skconf.serviceType.id = :serviceTypeId");
        	stringQuery.append(" order by skconf.skillModeType.id, skconf.skillOrder");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("countryId", countryId);
        	query.setLong("serviceTypeId", serviceTypeId);
        	List<SkillConfiguration> result = query.list();
        	return result;
            
        } catch (Throwable ex){
			log.error("== Error getSkillConfigurationsByCountryIdAndCategoryId/SkillConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSkillConfigurationsByCountryIdAndCategoryId/SkillConfigurationDAO ==");
        }
	}

}
