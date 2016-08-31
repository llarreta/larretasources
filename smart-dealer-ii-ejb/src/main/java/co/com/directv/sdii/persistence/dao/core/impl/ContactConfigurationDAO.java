package co.com.directv.sdii.persistence.dao.core.impl;

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
import co.com.directv.sdii.model.pojo.ContactConfiguration;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.ContactConfigurationDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ContactConfiguration
 * 
 * Fecha de Creación:Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ContactConfiguration
 * @see co.com.directv.sdii.model.hbm.ContactConfiguration.hbm.xml
 */
@Stateless(name="ContactConfigurationDAOLocal",mappedName="ejb/ContactConfigurationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactConfigurationDAO extends BaseDao implements ContactConfigurationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ContactConfigurationDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactConfigurationDAOLocal#createContactConfiguration(co.com.directv.sdii.model.pojo.ContactConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createContactConfiguration(ContactConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createContactConfiguration/ContactConfigurationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ContactConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createContactConfiguration/ContactConfigurationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactConfigurationDAOLocal#updateContactConfiguration(co.com.directv.sdii.model.pojo.ContactConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContactConfiguration(ContactConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateContactConfiguration/ContactConfigurationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ContactConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateContactConfiguration/ContactConfigurationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactConfigurationDAOLocal#deleteContactConfiguration(co.com.directv.sdii.model.pojo.ContactConfiguration)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteContactConfiguration(ContactConfiguration obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteContactConfiguration/ContactConfigurationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ContactConfiguration entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ContactConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteContactConfiguration/ContactConfigurationDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.ContactConfigurationDAOLocal#getContactConfigurationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ContactConfiguration getContactConfigurationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContactConfigurationByID/ContactConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactConfiguration.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ContactConfiguration) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getContactConfigurationByID/ContactConfigurationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactConfigurationDAOLocal#getAllContactConfigurations()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ContactConfiguration> getAllContactConfigurations() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllContactConfigurations/ContactConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactConfiguration.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllContactConfigurations/ContactConfigurationDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
    public ContactConfiguration getContactConfigurationByRule(Long woTypeId, Long woStatusId, Long countryId) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicio getContactConfigurationByRule/ContactConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactConfiguration.class.getName());
        	stringQuery.append(" entity where entity.woTypeId = :woTypeId and entity.workorderStatus.id = :woStatusId");
        	stringQuery.append(" and entity.countryId = :countryId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("woTypeId", woTypeId);
            query.setLong("woStatusId", woStatusId);
            query.setLong("countryId", countryId);

            return (ContactConfiguration) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error en la operacion getContactConfigurationByRule/ContactConfigurationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getContactConfigurationByRule/ContactConfigurationDAO ==");
        }
    }

}
