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
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.RefIncType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.RefIncTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad RefIncType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RefIncType
 * @see co.com.directv.sdii.model.hbm.RefIncType.hbm.xml
 */
@Stateless(name="RefIncTypeDAOLocal",mappedName="ejb/RefIncTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefIncTypeDAO extends BaseDao implements RefIncTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(RefIncTypeDAO.class);
    
    //cache elements
    private RefIncType refIncTypeExceed;
    private RefIncType refIncTypeIncomplete;
	
    public RefIncType getRefIncTypeExceed() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(refIncTypeExceed == null) {
    		refIncTypeExceed = getRefIncTypeByCode( CodesBusinessEntityEnum.INCONSISTENCY_TYPE_EXCEEDED_QUANTITY.getCodeEntity() );
    	}
    	return refIncTypeExceed;
    }
    
    public RefIncType getRefIncTypeIncomplete() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(refIncTypeIncomplete == null) {
    		refIncTypeIncomplete = getRefIncTypeByCode( CodesBusinessEntityEnum.INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY.getCodeEntity() );
    	}
    	return refIncTypeIncomplete;
    }
    
    public RefIncType getRefIncTypeByCode(String refIncTypeCode) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getRefIncTypeByCode/RefIncTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefIncType.class.getName());
        	stringQuery.append(" entity where entity.refIncTypeCode = :refIncTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("refIncTypeCode", refIncTypeCode);

            return (RefIncType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefIncTypeByCode/RefIncTypeDAO ==");
        }
	}

	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncTypesDAOLocal#createRefIncType(co.com.directv.sdii.model.pojo.RefIncType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRefIncType(RefIncType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createRefIncType/RefIncTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el RefIncType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefIncType/RefIncTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncTypesDAOLocal#updateRefIncType(co.com.directv.sdii.model.pojo.RefIncType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateRefIncType(RefIncType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateRefIncType/RefIncTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RefIncType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefIncType/RefIncTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncTypesDAOLocal#deleteRefIncType(co.com.directv.sdii.model.pojo.RefIncType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRefIncType(RefIncType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteRefIncType/RefIncTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from RefIncType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el RefIncType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefIncType/RefIncTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncTypesDAOLocal#getRefIncTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefIncType getRefIncTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRefIncTypeByID/RefIncTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefIncType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (RefIncType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefIncTypeByID/RefIncTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncTypesDAOLocal#getAllRefIncTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RefIncType> getAllRefIncTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllRefIncTypes/RefIncTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefIncType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllRefIncTypes/RefIncTypeDAO ==");
        }
    }

}
