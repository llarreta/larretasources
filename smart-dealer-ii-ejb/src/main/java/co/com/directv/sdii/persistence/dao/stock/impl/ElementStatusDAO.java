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
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ElementStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ElementStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementStatus
 * @see co.com.directv.sdii.model.hbm.ElementStatus.hbm.xml
 */
@Stateless(name="ElementStatusDAOLocal",mappedName="ejb/ElementStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementStatusDAO extends BaseDao implements ElementStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ElementStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementStatussDAOLocal#createElementStatus(co.com.directv.sdii.model.pojo.ElementStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createElementStatus(ElementStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createElementStatus/ElementStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ElementStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementStatus/ElementStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementStatussDAOLocal#updateElementStatus(co.com.directv.sdii.model.pojo.ElementStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateElementStatus(ElementStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateElementStatus/ElementStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ElementStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementStatus/ElementStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementStatussDAOLocal#deleteElementStatus(co.com.directv.sdii.model.pojo.ElementStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteElementStatus(ElementStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteElementStatus/ElementStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ElementStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ElementStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementStatus/ElementStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementStatussDAOLocal#getElementStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ElementStatus getElementStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getElementStatusByID/ElementStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ElementStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementStatusByID/ElementStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementStatussDAOLocal#getAllElementStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ElementStatus> getAllElementStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllElementStatuss/ElementStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllElementStatuss/ElementStatusDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementStatusDAOLocal#getElementStatusByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementStatus getElementStatusByCode(String elementStatusCode)
			throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getElementStatusByCode/ElementStatusDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(ElementStatus.class.getName());
	        	stringQuery.append(" entity where entity.elementStatusCode = :aCode");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setString("aCode", elementStatusCode);

	            return (ElementStatus) query.uniqueResult();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getElementStatusByCode/ElementStatusDAO ==");
	        }
	}

}
