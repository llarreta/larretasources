package co.com.directv.sdii.persistence.dao.bb.impl;

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
import co.com.directv.sdii.model.pojo.DeviceBrand;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DeviceBrand
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DeviceBrand
 * @see co.com.directv.sdii.model.hbm.DeviceBrand.hbm.xml
 */
@Stateless(name="DeviceBrandDAOLocal",mappedName="ejb/DeviceBrandDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DeviceBrandDAO extends BaseDao implements DeviceBrandDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DeviceBrandDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal#createDeviceBrand(co.com.directv.sdii.model.pojo.DeviceBrand)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDeviceBrand(DeviceBrand obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDeviceBrand/DeviceBrandDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DeviceBrand ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDeviceBrand/DeviceBrandDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal#updateDeviceBrand(co.com.directv.sdii.model.pojo.DeviceBrand)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDeviceBrand(DeviceBrand obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDeviceBrand/DeviceBrandDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DeviceBrand ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDeviceBrand/DeviceBrandDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal#deleteDeviceBrand(co.com.directv.sdii.model.pojo.DeviceBrand)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDeviceBrand(DeviceBrand obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDeviceBrand/DeviceBrandDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DeviceBrand entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DeviceBrand ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDeviceBrand/DeviceBrandDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal#getDeviceBrandsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DeviceBrand getDeviceBrandByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDeviceBrandByID/DeviceBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DeviceBrand.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DeviceBrand) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDeviceBrandByID/DeviceBrandDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDeviceBrandByID/DeviceBrandDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal#getAllDeviceBrands()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DeviceBrand> getAllDeviceBrands() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDeviceBrands/DeviceBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DeviceBrand.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDeviceBrands/DeviceBrandDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDeviceBrands/DeviceBrandDAO ==");
        }
    }

}
