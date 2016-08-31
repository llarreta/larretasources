package co.com.directv.sdii.persistence.dao.config.impl;


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
import co.com.directv.sdii.model.pojo.AllianceFile;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de AllianceFile
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AllianceFile
 * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal
 */
@Stateless(name="AllianceFileDAOLocal",mappedName="ejb/AllianceFileDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllianceFileDAO extends BaseDao implements AllianceFileDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AllianceFileDAO.class);

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal#createAllianceFile(co.com.directv.sdii.model.pojo.AllianceFile)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAllianceFile(AllianceFile obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAllianceFile/DAOAllianceFileBean ==");
        Session session = getSession();
        try {
            session.save(obj);
            this.doFlush(session);
            //Query query = session.createSQLQuery("UPDATE ALLIANCE_FILES SET DATA = " + obj.getData() + " WHERE ID = " + obj.getId()) ;
            //Query query = session.createQuery("insert into AllianceFile (allianceFile.data) select values (:datos)");
            //query.executeUpdate();
        } catch (Throwable ex) {
            log.error("== Error creando el AllianceFile ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAllianceFile/DAOAllianceFileBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal#getAllianceFileByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AllianceFile getAllianceFileByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAllianceFileByID/DAOAllianceFileBean ==");
        Session session = getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select alliancefile from AllianceFile alliancefile ");
            queryBuffer.append("where alliancefile.id = ");
            queryBuffer.append(id);
            //Object obj = session.createQuery("select alliancefile from AllianceFile alliancefile where alliancefile.id = " + id + " ").uniqueResult();
            Object obj = session.createQuery(queryBuffer.toString()).uniqueResult();
            if (obj != null) {
                return (AllianceFile) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.error("== Error consultando el AllianceFile por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceFileByID/DAOAllianceFileBean ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal#getAllianceFileByAllianceID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AllianceFile getAllianceFileByAllianceID(Long id)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getAllianceFileByID/DAOAllianceFileBean ==");
        Session session = getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select alliancefile from AllianceFile alliancefile ");
            queryBuffer.append("where alliancefile.alliance.id = ");
            queryBuffer.append(id);
            queryBuffer.append(" order by alliancefile.filename");
            //Object obj = session.createQuery("select alliancefile from AllianceFile alliancefile where alliancefile.alliance.id = " + id + " order by alliancefile.filename").uniqueResult();
            Object obj = session.createQuery(queryBuffer.toString()).uniqueResult();
            if (obj != null) {
                return (AllianceFile) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.error("== Error consultando el AllianceFile por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceFileByID/DAOAllianceFileBean ==");
        }
	}

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal#updateAllianceFile(co.com.directv.sdii.model.pojo.AllianceFile)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAllianceFile(AllianceFile obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateAllianceFile/DAOAllianceFileBean ==");
        Session session = getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el AllianceFile ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina updateAllianceFile/DAOAllianceFileBean ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal#deleteAllianceFile(co.com.directv.sdii.model.pojo.AllianceFile)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAllianceFile(AllianceFile obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteAllianceFile/DAOAllianceFileBean ==");
        Session session = getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error borrando el AllianceFile ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAllianceFile/DAOAllianceFileBean ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal#getAll()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AllianceFile> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOAllianceFileBean ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from AllianceFile af order by af.filename");
            //Query query = session.createQuery("from AllianceFile af order by af.filename");
            Query query = session.createQuery(queryBuffer.toString());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando todos los AllianceFile ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOAllianceFileBean ==");
        }
    }
}
