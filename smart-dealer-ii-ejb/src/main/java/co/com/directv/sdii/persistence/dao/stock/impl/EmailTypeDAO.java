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
import co.com.directv.sdii.model.pojo.EmailType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad EmailType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmailType
 * @see co.com.directv.sdii.model.hbm.EmailType.hbm.xml
 */
@Stateless(name="EmailTypeDAOLocal",mappedName="ejb/EmailTypeDAOLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmailTypeDAO extends BaseDao implements EmailTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmailTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal#createEmailType(co.com.directv.sdii.model.pojo.EmailType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmailType(EmailType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createEmailType/EmailTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el EmailType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmailType/EmailTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal#updateEmailType(co.com.directv.sdii.model.pojo.EmailType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmailType(EmailType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateEmailType/EmailTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el EmailType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmailType/EmailTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal#deleteEmailType(co.com.directv.sdii.model.pojo.EmailType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmailType(EmailType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteEmailType/EmailTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from EmailType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el EmailType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmailType/EmailTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal#getEmailTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EmailType getEmailTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmailTypeByID/EmailTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmailType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (EmailType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getEmailTypeByID/EmailTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal#getAllEmailTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmailType> getAllEmailTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmailTypes/EmailTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmailType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllEmailTypes/EmailTypeDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal#getAllEmailTypes()
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmailType> getActiveEmailTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getActiveEmailTypes/EmailTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmailType.class.getName());
        	stringQuery.append(" et where et.isActive = :anActiveEmailTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("anActiveEmailTypeCode", CodesBusinessEntityEnum.EMAIL_TYPE_STATUS_ACTIVE.getCodeEntity());
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getActiveEmailTypes/EmailTypeDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal#getEmailTypeByCode(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public EmailType getEmailTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getEmailTypeByCode/EmailTypeDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmailType.class.getName());
        	stringQuery.append(" entity where entity.emailTypeCode = :anEmailTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anEmailTypeCode", code);

            return (EmailType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getEmailTypeByCode/EmailTypeDAO ==");
        }
	}

}
