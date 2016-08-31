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
import co.com.directv.sdii.model.pojo.EmployeeDevices;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad EmployeeDevices
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeDevices
 * @see co.com.directv.sdii.model.hbm.EmployeeDevices.hbm.xml
 */
@Stateless(name="EmployeeDevicesDAOLocal",mappedName="ejb/EmployeeDevicesDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeDevicesDAO extends BaseDao implements EmployeeDevicesDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmployeeDevicesDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal#createEmployeeDevices(co.com.directv.sdii.model.pojo.EmployeeDevices)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployeeDevices(EmployeeDevices obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createEmployeeDevices/EmployeeDevicesDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el EmployeeDevices ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeeDevices/EmployeeDevicesDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal#updateEmployeeDevices(co.com.directv.sdii.model.pojo.EmployeeDevices)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployeeDevices(EmployeeDevices obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateEmployeeDevices/EmployeeDevicesDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el EmployeeDevices ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeeDevices/EmployeeDevicesDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal#deleteEmployeeDevices(co.com.directv.sdii.model.pojo.EmployeeDevices)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeeDevices(EmployeeDevices obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteEmployeeDevices/EmployeeDevicesDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from EmployeeDevices entity where entity.id.mobileDevice.id = :aMovDevId and entity.id.employee.id = :anEmployeeDevId");
            query.setLong("aMovDevId", obj.getId().getMobileDevice().getId());
            query.setLong("anEmployeeDevId", obj.getId().getEmployee().getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el EmployeeDevices ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeDevices/EmployeeDevicesDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal#getEmployeeDevicessByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EmployeeDevices getEmployeeDevicesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeDevicesByID/EmployeeDevicesDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeDevices.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (EmployeeDevices) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getEmployeeDevicesByID/EmployeeDevicesDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getEmployeeDevicesByID/EmployeeDevicesDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal#getAllEmployeeDevicess()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmployeeDevices> getAllEmployeeDevicess() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeDevicess/EmployeeDevicesDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeDevices.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllEmployeeDevicess/EmployeeDevicesDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllEmployeeDevicess/EmployeeDevicesDAO ==");
        }
    }

}
