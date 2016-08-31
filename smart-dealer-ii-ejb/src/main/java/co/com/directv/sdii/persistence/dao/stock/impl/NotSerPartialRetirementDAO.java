package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
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
import co.com.directv.sdii.model.pojo.NotSerPartialRetirement;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad NotSerPartialRetirement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.NotSerPartialRetirement
 * @see co.com.directv.sdii.model.hbm.NotSerPartialRetirement.hbm.xml
 */
@Stateless(name="NotSerPartialRetirementDAOLocal",mappedName="ejb/NotSerPartialRetirementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotSerPartialRetirementDAO extends BaseDao implements NotSerPartialRetirementDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(NotSerPartialRetirementDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementsDAOLocal#createNotSerPartialRetirement(co.com.directv.sdii.model.pojo.NotSerPartialRetirement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createNotSerPartialRetirement(NotSerPartialRetirement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createNotSerPartialRetirement/NotSerPartialRetirementDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el NotSerPartialRetirement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createNotSerPartialRetirement/NotSerPartialRetirementDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementsDAOLocal#updateNotSerPartialRetirement(co.com.directv.sdii.model.pojo.NotSerPartialRetirement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateNotSerPartialRetirement(NotSerPartialRetirement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateNotSerPartialRetirement/NotSerPartialRetirementDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el NotSerPartialRetirement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateNotSerPartialRetirement/NotSerPartialRetirementDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementsDAOLocal#deleteNotSerPartialRetirement(co.com.directv.sdii.model.pojo.NotSerPartialRetirement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteNotSerPartialRetirement(NotSerPartialRetirement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteNotSerPartialRetirement/NotSerPartialRetirementDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from NotSerPartialRetirement entity where entity.NotSerPartialRetirementId.id = :anEntityId");
            query.setLong("anEntityId", obj.getId().getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el NotSerPartialRetirement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteNotSerPartialRetirement/NotSerPartialRetirementDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementsDAOLocal#getNotSerPartialRetirementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public NotSerPartialRetirement getNotSerPartialRetirementByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getNotSerPartialRetirementByID/NotSerPartialRetirementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(NotSerPartialRetirement.class.getName());
        	stringQuery.append(" entity where entity.NotSerPartialRetirementId.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (NotSerPartialRetirement) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerPartialRetirementByID/NotSerPartialRetirementDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementsDAOLocal#getAllNotSerPartialRetirements()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<NotSerPartialRetirement> getAllNotSerPartialRetirements() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllNotSerPartialRetirements/NotSerPartialRetirementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(NotSerPartialRetirement.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllNotSerPartialRetirements/NotSerPartialRetirementDAO ==");
        }
    }


    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementsDAOLocal#getNotSerPartialRetirementSequence()
     */
	@Override
	public Long getNotSerPartialRetirementSequence()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getNotSerPartialRetirementSequence/NotSerPartialRetirementDAO ==");
        Session session = super.getSession();
        try {
        	Query query = session.createSQLQuery("select SEQ_NOT_SER_PARTIAL_RET.NextVal from DUAL");
        	BigDecimal sequenceNextValue = (BigDecimal) query.uniqueResult(); 
        	return Long.valueOf(sequenceNextValue.longValue());
        }catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerPartialRetirementSequence/NotSerPartialRetirementDAO ==");
        }
	}
	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementsDAOLocal#getNotSerPartialRetirementByWarehouseElementId(java.lang.Long)
     */
	public List<NotSerPartialRetirement> getNotSerPartialRetirementByElementId(Long elementId)  throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getNotSerPartialRetirementByWarehouseElementId/NotSerPartialRetirementDAO ==");
        try {
        	Session session = super.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select notserpart ");
        	stringQuery.append("from ");
        	stringQuery.append(NotSerPartialRetirement.class.getName());
        	stringQuery.append(" notserpart  , ");
        	stringQuery.append(WarehouseElement.class.getName());
        	stringQuery.append(" we ");
        	stringQuery.append("where notserpart.id.warehouseElement = we.id ");
        	stringQuery.append("and we.notSerialized.elementId = :elementId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("elementId", elementId);
        	List<NotSerPartialRetirement> returnList = query.list();
        	return returnList;
        }catch (Throwable ex){
			log.error("== Error getNotSerPartialRetirementByWarehouseElementId/NotSerPartialRetirementDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerPartialRetirementByWarehouseElementId/NotSerPartialRetirementDAO ==");
        }
	}

}
