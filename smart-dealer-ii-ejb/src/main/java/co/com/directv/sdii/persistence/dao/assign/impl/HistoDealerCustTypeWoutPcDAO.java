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
import co.com.directv.sdii.model.pojo.HistoDealerCustTypeWoutPc;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad HistoDealerCustTypeWoutPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.HistoDealerCustTypeWoutPc
 * @see co.com.directv.sdii.model.hbm.HistoDealerCustTypeWoutPc.hbm.xml
 */
@Stateless(name="HistoDealerCustTypeWoutPcDAOLocal",mappedName="ejb/HistoDealerCustTypeWoutPcDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerCustTypeWoutPcDAO extends BaseDao implements HistoDealerCustTypeWoutPcDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerCustTypeWoutPcDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal#createHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.pojo.HistoDealerCustTypeWoutPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el HistoDealerCustTypeWoutPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal#updateHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.pojo.HistoDealerCustTypeWoutPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el HistoDealerCustTypeWoutPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal#deleteHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.pojo.HistoDealerCustTypeWoutPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from HistoDealerCustTypeWoutPc entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el HistoDealerCustTypeWoutPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal#getHistoDealerCustTypeWoutPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerCustTypeWoutPc getHistoDealerCustTypeWoutPcByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getHistoDealerCustTypeWoutPcByID/HistoDealerCustTypeWoutPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerCustTypeWoutPc.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (HistoDealerCustTypeWoutPc) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getHistoDealerCustTypeWoutPcByID/HistoDealerCustTypeWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getHistoDealerCustTypeWoutPcByID/HistoDealerCustTypeWoutPcDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal#getAllHistoDealerCustTypeWoutPcs()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerCustTypeWoutPc> getAllHistoDealerCustTypeWoutPcs() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllHistoDealerCustTypeWoutPcs/HistoDealerCustTypeWoutPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerCustTypeWoutPc.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllHistoDealerCustTypeWoutPcs/HistoDealerCustTypeWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllHistoDealerCustTypeWoutPcs/HistoDealerCustTypeWoutPcDAO ==");
        }
    }

}
