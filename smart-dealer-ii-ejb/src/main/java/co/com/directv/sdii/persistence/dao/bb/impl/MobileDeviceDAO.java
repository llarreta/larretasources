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
import co.com.directv.sdii.model.pojo.MobileDevice;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad MobileDevice
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MobileDevice
 * @see co.com.directv.sdii.model.hbm.MobileDevice.hbm.xml
 */
@Stateless(name="MobileDeviceDAOLocal",mappedName="ejb/MobileDeviceDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MobileDeviceDAO extends BaseDao implements MobileDeviceDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(MobileDeviceDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal#createMobileDevice(co.com.directv.sdii.model.pojo.MobileDevice)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createMobileDevice(MobileDevice obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createMobileDevice/MobileDeviceDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el MobileDevice ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMobileDevice/MobileDeviceDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal#updateMobileDevice(co.com.directv.sdii.model.pojo.MobileDevice)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateMobileDevice(MobileDevice obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateMobileDevice/MobileDeviceDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el MobileDevice ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMobileDevice/MobileDeviceDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal#deleteMobileDevice(co.com.directv.sdii.model.pojo.MobileDevice)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteMobileDevice(MobileDevice obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteMobileDevice/MobileDeviceDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from MobileDevice entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el MobileDevice ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMobileDevice/MobileDeviceDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal#getMobileDevicesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MobileDevice getMobileDeviceByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMobileDeviceByID/MobileDeviceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MobileDevice.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (MobileDevice) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getMobileDeviceByID/MobileDeviceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMobileDeviceByID/MobileDeviceDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal#getAllMobileDevices()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MobileDevice> getAllMobileDevices() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMobileDevices/MobileDeviceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MobileDevice.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllMobileDevices/MobileDeviceDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllMobileDevices/MobileDeviceDAO ==");
        }
    }

}
