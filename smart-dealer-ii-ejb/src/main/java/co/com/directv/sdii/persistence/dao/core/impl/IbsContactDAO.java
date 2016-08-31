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
import co.com.directv.sdii.model.dto.IbsContactDTO;
import co.com.directv.sdii.model.pojo.IbsContact;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.IbsContactDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad IbsContactDAO 
 * 
 * Fecha de Creación: 29/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="IbsContactDAOLocal",mappedName="ejb/IbsContactDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IbsContactDAO extends BaseDao implements IbsContactDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(IbsContactDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactDAOLocal#createIbsContact(co.com.directv.sdii.model.pojo.IbsContact)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createIbsContact(IbsContact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createIbsContact/IbsContactDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el IbsContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createIbsContact/IbsContactDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactDAOLocal#updateIbsContact(co.com.directv.sdii.model.pojo.IbsContact)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateIbsContact(IbsContact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateIbsContact/IbsContactDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el IbsContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateIbsContact/IbsContactDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactDAOLocal#deleteIbsContact(co.com.directv.sdii.model.pojo.IbsContact)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteIbsContact(IbsContact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteIbsContact/IbsContactDAO ==");
        Session session = super.getSession();
        try {
            
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" delete ");
        	stringQuery.append("   from " + IbsContact.class.getName() + " entity ");
        	stringQuery.append("  where entity.id = :anEntityId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el IbsContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteIbsContact/IbsContactDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactDAOLocal#getIbsContactByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public IbsContact getIbsContactByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getIbsContactByID/IbsContactDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + IbsContact.class.getName() + " entity ");
        	stringQuery.append(" where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (IbsContact) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getIbsContactByID/IbsContactDAO ==");
        }
    }
    
        
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactDAOLocal#getAllIbsContacts(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<IbsContact> getAllIbsContacts(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllIbsContacts/IbsContactDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + IbsContact.class.getName() + " entity ");
        	stringQuery.append(" where  entity.country.id= :aCountryId");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            
            return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllIbsContacts/IbsContactDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactDAOLocal#getIbsContactByIbsContactCode(java.lang.String, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public IbsContact getIbsContactByIbsContactCode(String ibsContactCode,Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getIbsContactReasonByCode/IbsContactReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + IbsContact.class.getName() + " entity ");
        	stringQuery.append(" where entity.ibsContactCode = :aIbsContactCode ");
        	stringQuery.append("   and entity.country.id = :aCountryId ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aIbsContactCode", ibsContactCode);
            query.setLong("aCountryId", countryId);

            return (IbsContact) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getIbsContactReasonByCode/IbsContactReasonDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactDAOLocal#getIbsContactByIbsContactCode(java.lang.String, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<IbsContactDTO> getIbsContactDTOByWorkOrderId(Long workOrderId,Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getIbsContactDTOByWorkOrderId/IbsContactReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("  select new "+IbsContactDTO.class.getName()+"(ic.ibsContactCode, ");
        	stringQuery.append("         icr.code, ");
        	stringQuery.append("         icr.name, ");
        	stringQuery.append("         ic.description, ");
        	stringQuery.append("         ic.creationDate, ");
        	stringQuery.append("         cs.ibsCode, ");
        	stringQuery.append("         cs.conStatusName, ");
        	stringQuery.append("         ic.characteristicValue, ");
        	stringQuery.append("         icr.category, ");
        	stringQuery.append("         icr.subCategory) ");
        	stringQuery.append("  from " + IbsContact.class.getName() + " ic ");
        	stringQuery.append("           inner join ic.ibsContactReason icr  ");
        	stringQuery.append("           inner join ic.contactStatus cs  ");
        	stringQuery.append(" where ic.workOrder.id = :aWorkOrderId ");
        	stringQuery.append("   and ic.country.id = :aCountryId ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWorkOrderId", workOrderId);
            query.setLong("aCountryId", countryId);
            
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getIbsContactDTOByWorkOrderId/IbsContactReasonDAO ==");
        }
    }

}
