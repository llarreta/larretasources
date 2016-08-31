package co.com.directv.sdii.persistence.dao.assign.impl;

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
import co.com.directv.sdii.model.pojo.HistoDealerServiceSubCatWithPc;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad HistoDealerServiceSubCatWithPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.HistoDealerServiceSubCatWithPc
 * @see co.com.directv.sdii.model.hbm.HistoDealerServiceSubCatWithPc.hbm.xml
 */
@Stateless(name="HistoDealerServiceSubCatWithPcDAOLocal",mappedName="ejb/HistoDealerServiceSubCatWithPcDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerServiceSubCatWithPcDAO extends BaseDao implements HistoDealerServiceSubCatWithPcDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerServiceSubCatWithPcDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal#createHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.pojo.HistoDealerServiceSubCatWithPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el HistoDealerServiceSubCatWithPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal#updateHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.pojo.HistoDealerServiceSubCatWithPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el HistoDealerServiceSubCatWithPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal#deleteHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.pojo.HistoDealerServiceSubCatWithPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from HistoDealerServiceSubCatWithPc entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el HistoDealerServiceSubCatWithPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal#getHistoDealerServiceSubCatWithPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerServiceSubCatWithPc getHistoDealerServiceSubCatWithPcByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getHistoDealerServiceSubCatWithPcByID/HistoDealerServiceSubCatWithPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerServiceSubCatWithPc.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (HistoDealerServiceSubCatWithPc) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getHistoDealerServiceSubCatWithPcByID/HistoDealerServiceSubCatWithPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getHistoDealerServiceSubCatWithPcByID/HistoDealerServiceSubCatWithPcDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal#getAllHistoDealerServiceSubCatWithPcs()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerServiceSubCatWithPc> getAllHistoDealerServiceSubCatWithPcs() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllHistoDealerServiceSubCatWithPcs/HistoDealerServiceSubCatWithPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerServiceSubCatWithPc.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllHistoDealerServiceSubCatWithPcs/HistoDealerServiceSubCatWithPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllHistoDealerServiceSubCatWithPcs/HistoDealerServiceSubCatWithPcDAO ==");
        }
    }

}
