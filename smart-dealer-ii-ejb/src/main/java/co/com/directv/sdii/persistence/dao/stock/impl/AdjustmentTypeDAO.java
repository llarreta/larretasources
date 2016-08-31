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
import co.com.directv.sdii.model.pojo.AdjustmentType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AdjustmentType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AdjustmentType
 * @see co.com.directv.sdii.model.hbm.AdjustmentType.hbm.xml
 */
@Stateless(name="AdjustmentTypeDAOLocal",mappedName="ejb/AdjustmentTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentTypeDAO extends BaseDao implements AdjustmentTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentTypeDAOLocal#createAdjustmentType(co.com.directv.sdii.model.pojo.AdjustmentType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAdjustmentType(AdjustmentType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAdjustmentType/AdjustmentTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el AdjustmentType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentType/AdjustmentTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentTypeDAOLocal#updateAdjustmentType(co.com.directv.sdii.model.pojo.AdjustmentType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAdjustmentType(AdjustmentType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateAdjustmentType/AdjustmentTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el AdjustmentType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAdjustmentType/AdjustmentTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentTypeDAOLocal#deleteAdjustmentType(co.com.directv.sdii.model.pojo.AdjustmentType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAdjustmentType(AdjustmentType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteAdjustmentType/AdjustmentTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from AdjustmentType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el AdjustmentType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustmentType/AdjustmentTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentTypeDAOLocal#getAdjustmentTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentType getAdjustmentTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentTypeByID/AdjustmentTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (AdjustmentType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentTypeByID/AdjustmentTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentTypeDAOLocal#getAllAdjustmentTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AdjustmentType> getAllAdjustmentTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllAdjustmentTypes/AdjustmentTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAdjustmentTypes/AdjustmentTypeDAO ==");
        }
    }



	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public AdjustmentType getAdjustmentTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAdjustmentTypeByCode/AdjustmentTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentType.class.getName());
        	stringQuery.append(" entity where entity.code = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (AdjustmentType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentTypeByCode/AdjustmentTypeDAO ==");
        }
	}

}
