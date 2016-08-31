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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ReferenceModType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ReferenceModTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ReferenceModType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ReferenceModType
 * @see co.com.directv.sdii.model.hbm.ReferenceModType.hbm.xml
 */
@Stateless(name="ReferenceModTypeDAOLocal",mappedName="ejb/ReferenceModTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceModTypeDAO extends BaseDao implements ReferenceModTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ReferenceModTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModTypesDAOLocal#createReferenceModType(co.com.directv.sdii.model.pojo.ReferenceModType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createReferenceModType(ReferenceModType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createReferenceModType/ReferenceModTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ReferenceModType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceModType/ReferenceModTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModTypesDAOLocal#updateReferenceModType(co.com.directv.sdii.model.pojo.ReferenceModType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateReferenceModType(ReferenceModType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateReferenceModType/ReferenceModTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ReferenceModType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceModType/ReferenceModTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModTypesDAOLocal#deleteReferenceModType(co.com.directv.sdii.model.pojo.ReferenceModType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteReferenceModType(ReferenceModType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteReferenceModType/ReferenceModTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ReferenceModType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ReferenceModType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceModType/ReferenceModTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModTypesDAOLocal#getReferenceModTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ReferenceModType getReferenceModTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceModTypeByID/ReferenceModTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceModType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ReferenceModType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceModTypeByID/ReferenceModTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModTypesDAOLocal#getAllReferenceModTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ReferenceModType> getAllReferenceModTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllReferenceModTypes/ReferenceModTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceModType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllReferenceModTypes/ReferenceModTypeDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModTypeDAOLocal#getReferenceModTypeByCode(java.lang.String)
	 */
	public ReferenceModType getReferenceModTypeByCode(String refModTypeCode) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getReferenceModTypeByCode/ReferenceModTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceModType.class.getName());
        	stringQuery.append(" entity where entity.refModTypeCode = :aRefTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aRefTypeCode", refModTypeCode);

            return (ReferenceModType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceModTypeByCode/ReferenceModTypeDAO ==");
        }
	}

}
