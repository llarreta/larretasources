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
import co.com.directv.sdii.model.pojo.InconsistencyType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad InconsistencyType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.InconsistencyType
 * @see co.com.directv.sdii.model.hbm.InconsistencyType.hbm.xml
 */
@Stateless(name="InconsistencyTypeDAOLocal",mappedName="ejb/InconsistencyTypeDAOLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InconsistencyTypeDAO extends BaseDao implements InconsistencyTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(InconsistencyTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypesDAOLocal#createInconsistencyType(co.com.directv.sdii.model.pojo.InconsistencyType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createInconsistencyType(InconsistencyType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createInconsistencyType/InconsistencyTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el InconsistencyType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createInconsistencyType/InconsistencyTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypesDAOLocal#updateInconsistencyType(co.com.directv.sdii.model.pojo.InconsistencyType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateInconsistencyType(InconsistencyType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateInconsistencyType/InconsistencyTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el InconsistencyType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateInconsistencyType/InconsistencyTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypesDAOLocal#deleteInconsistencyType(co.com.directv.sdii.model.pojo.InconsistencyType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteInconsistencyType(InconsistencyType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteInconsistencyType/InconsistencyTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from InconsistencyType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el InconsistencyType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteInconsistencyType/InconsistencyTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypesDAOLocal#getInconsistencyTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InconsistencyType getInconsistencyTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getInconsistencyTypeByID/InconsistencyTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (InconsistencyType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getInconsistencyTypeByID/InconsistencyTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypesDAOLocal#getAllInconsistencyTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<InconsistencyType> getAllInconsistencyTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllInconsistencyTypes/InconsistencyTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllInconsistencyTypes/InconsistencyTypeDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal#getInconsistencyTypeByCode(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public InconsistencyType getInconsistencyTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getInconsistencyTypeByCode/InconsistencyTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyType.class.getName());
        	stringQuery.append(" entity where entity.incTypeCode = :anIncTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anIncTypeCode", code);

            return (InconsistencyType) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getInconsistencyTypeByCode/InconsistencyTypeDAO ==");
        }
	}
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal#getInconsistencyTypeByCodeAndActive(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public InconsistencyType getInconsistencyTypeByCodeAndActive(String code)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getInconsistencyTypeByCode/InconsistencyTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyType.class.getName());
        	stringQuery.append(" entity where entity.incTypeCode = :anIncTypeCode and entity.isActive = :status");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("anIncTypeCode", code);
            query.setString("status", CodesBusinessEntityEnum.INCONSISTENCY_TYPE_STATUS_ACTIVE.getCodeEntity());

            return (InconsistencyType) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getInconsistencyTypeByCode/InconsistencyTypeDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal#getActiveInconsistencyTypes()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<InconsistencyType> getActiveInconsistencyTypes()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveInconsistencyTypes/InconsistencyTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyType.class.getName());
        	stringQuery.append(" ict where ict.isActive = :anIctIsActive");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("anIctIsActive", CodesBusinessEntityEnum.INCONSISTENCY_TYPE_STATUS_ACTIVE.getCodeEntity());
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getActiveInconsistencyTypes/InconsistencyTypeDAO ==");
        }
	}

}
