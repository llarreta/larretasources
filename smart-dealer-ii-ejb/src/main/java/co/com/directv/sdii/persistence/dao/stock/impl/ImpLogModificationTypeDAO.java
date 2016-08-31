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
import co.com.directv.sdii.model.pojo.ImpLogModificationType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ImpLogModificationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImpLogModificationType
 * @see co.com.directv.sdii.model.hbm.ImpLogModificationType.hbm.xml
 */
@Stateless(name="ImpLogModificationTypeDAOLocal",mappedName="ejb/ImpLogModificationTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImpLogModificationTypeDAO extends BaseDao implements ImpLogModificationTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ImpLogModificationTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypesDAOLocal#createImpLogModificationType(co.com.directv.sdii.model.pojo.ImpLogModificationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createImpLogModificationType(ImpLogModificationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createImpLogModificationType/ImpLogModificationTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ImpLogModificationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImpLogModificationType/ImpLogModificationTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypesDAOLocal#updateImpLogModificationType(co.com.directv.sdii.model.pojo.ImpLogModificationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImpLogModificationType(ImpLogModificationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateImpLogModificationType/ImpLogModificationTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ImpLogModificationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImpLogModificationType/ImpLogModificationTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypesDAOLocal#deleteImpLogModificationType(co.com.directv.sdii.model.pojo.ImpLogModificationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImpLogModificationType(ImpLogModificationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImpLogModificationType/ImpLogModificationTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImpLogModificationType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ImpLogModificationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogModificationType/ImpLogModificationTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypesDAOLocal#getImpLogModificationTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImpLogModificationType getImpLogModificationTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImpLogModificationTypeByID/ImpLogModificationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModificationType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ImpLogModificationType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogModificationTypeByID/ImpLogModificationTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypesDAOLocal#getAllImpLogModificationTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImpLogModificationType> getAllImpLogModificationTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImpLogModificationTypes/ImpLogModificationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModificationType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImpLogModificationTypes/ImpLogModificationTypeDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypeDAOLocal#getImpLogModificationTypeByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ImpLogModificationType getImpLogModificationTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getImpLogModificationTypeByCode/ImpLogModificationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModificationType.class.getName());
        	stringQuery.append(" entity where entity.modTypeCode = :aModTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aModTypeCode", code);

            return (ImpLogModificationType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogModificationTypeByCode/ImpLogModificationTypeDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypeDAOLocal#getActiveImpLogModificationTypes()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ImpLogModificationType> getActiveImpLogModificationTypes()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveImpLogModificationTypes/ImpLogModificationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModificationType.class.getName());
        	stringQuery.append(" ilmt where ilmt.isActive = :anIlmtActiveCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("anIlmtActiveCode", CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_TYPE_STATUS_ACTIVE.getCodeEntity());
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getActiveImpLogModificationTypes/ImpLogModificationTypeDAO ==");
        }
	}

}
