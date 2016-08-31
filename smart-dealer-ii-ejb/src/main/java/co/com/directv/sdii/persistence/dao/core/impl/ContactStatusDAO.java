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
import co.com.directv.sdii.model.pojo.ContactStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.ContactStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ContactStatus
 * 
 * Fecha de Creación:Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ContactStatus
 * @see co.com.directv.sdii.model.hbm.ContactStatus.hbm.xml
 */
@Stateless(name="ContactStatusDAOLocal",mappedName="ejb/ContactStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactStatusDAO extends BaseDao implements ContactStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ContactStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactStatusDAOLocal#createContactStatus(co.com.directv.sdii.model.pojo.ContactStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createContactStatus(ContactStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createContactStatus/ContactStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ContactStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createContactStatus/ContactStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactStatusDAOLocal#updateContactStatus(co.com.directv.sdii.model.pojo.ContactStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContactStatus(ContactStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateContactStatus/ContactStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ContactStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateContactStatus/ContactStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactStatusDAOLocal#deleteContactStatus(co.com.directv.sdii.model.pojo.ContactStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteContactStatus(ContactStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteContactStatus/ContactStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ContactStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ContactStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteContactStatus/ContactStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.ContactStatusDAOLocal#getContactStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ContactStatus getContactStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContactStatusByID/ContactStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ContactStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getContactStatusByID/ContactStatusDAO ==");
        }
    }

    /* (non-Javadoc)
    * @see persistence.dao.core.ContactStatusDAOLocal#getContactStatusByCode(java.lang.String)
    */
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   public ContactStatus getContactStatusByCode(String code) throws DAOServiceException, DAOSQLException {
       log.debug("== Inicio getContactStatusByCode/ContactStatusDAO ==");
       Session session = super.getSession();

       try {
       	StringBuffer stringQuery = new StringBuffer();
       	stringQuery.append("from ");
       	stringQuery.append(ContactStatus.class.getName());
       	stringQuery.append(" entity where entity.conStatusCode = :aCode");
       	Query query = session.createQuery(stringQuery.toString());
        query.setString("aCode", code);

        return (ContactStatus) query.uniqueResult();

       } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
           log.debug("== Termina getContactStatusByCode/ContactStatusDAO ==");
       }
   }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.ContactStatusDAOLocal#getAllContactStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ContactStatus> getAllContactStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllContactStatuss/ContactStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllContactStatuss/ContactStatusDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.core.ContactStatusDAOLocal#getContactStatusByIbsCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ContactStatus getContactStatusByIbsCode(String ibsCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContactStatusByID/ContactStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactStatus.class.getName());
        	stringQuery.append(" entity where entity.ibsCode = :aIbsCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aIbsCode", ibsCode);

            return (ContactStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getContactStatusByID/ContactStatusDAO ==");
        }
    }

}
