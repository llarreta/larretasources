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
import co.com.directv.sdii.model.pojo.ImportLogStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ImportLogStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ImportLogStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImportLogStatus
 * @see co.com.directv.sdii.model.hbm.ImportLogStatus.hbm.xml
 */
@Stateless(name="ImportLogStatusDAOLocal",mappedName="ejb/ImportLogStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogStatusDAO extends BaseDao implements ImportLogStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ImportLogStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogStatussDAOLocal#createImportLogStatus(co.com.directv.sdii.model.pojo.ImportLogStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createImportLogStatus(ImportLogStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createImportLogStatus/ImportLogStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ImportLogStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogStatus/ImportLogStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogStatussDAOLocal#updateImportLogStatus(co.com.directv.sdii.model.pojo.ImportLogStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImportLogStatus(ImportLogStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateImportLogStatus/ImportLogStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ImportLogStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLogStatus/ImportLogStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogStatussDAOLocal#deleteImportLogStatus(co.com.directv.sdii.model.pojo.ImportLogStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImportLogStatus(ImportLogStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImportLogStatus/ImportLogStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImportLogStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ImportLogStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogStatus/ImportLogStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogStatussDAOLocal#getImportLogStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogStatus getImportLogStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImportLogStatusByID/ImportLogStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ImportLogStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogStatusByID/ImportLogStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImportLogStatussDAOLocal#getAllImportLogStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLogStatus> getAllImportLogStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImportLogStatuss/ImportLogStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImportLogStatuss/ImportLogStatusDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogStatusDAOLocal#getImportLogStatusByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogStatus getImportLogStatusByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getImportLogStatusByCode/ImportLogStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImportLogStatus.class.getName());
        	stringQuery.append(" entity where entity.statusCode = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);

            return (ImportLogStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImportLogStatusByCode/ImportLogStatusDAO ==");
        }
	}

}
