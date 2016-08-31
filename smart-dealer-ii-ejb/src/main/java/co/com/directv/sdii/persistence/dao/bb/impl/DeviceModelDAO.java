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
import co.com.directv.sdii.model.pojo.DeviceModel;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DeviceModel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DeviceModel
 * @see co.com.directv.sdii.model.hbm.DeviceModel.hbm.xml
 */
@Stateless(name="DeviceModelDAOLocal",mappedName="ejb/DeviceModelDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DeviceModelDAO extends BaseDao implements DeviceModelDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DeviceModelDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal#createDeviceModel(co.com.directv.sdii.model.pojo.DeviceModel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDeviceModel(DeviceModel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDeviceModel/DeviceModelDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DeviceModel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDeviceModel/DeviceModelDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal#updateDeviceModel(co.com.directv.sdii.model.pojo.DeviceModel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDeviceModel(DeviceModel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDeviceModel/DeviceModelDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DeviceModel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDeviceModel/DeviceModelDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal#deleteDeviceModel(co.com.directv.sdii.model.pojo.DeviceModel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDeviceModel(DeviceModel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDeviceModel/DeviceModelDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DeviceModel entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DeviceModel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDeviceModel/DeviceModelDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal#getDeviceModelsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DeviceModel getDeviceModelByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDeviceModelByID/DeviceModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DeviceModel.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DeviceModel) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDeviceModelByID/DeviceModelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDeviceModelByID/DeviceModelDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal#getAllDeviceModels()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DeviceModel> getAllDeviceModels() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDeviceModels/DeviceModelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DeviceModel.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDeviceModels/DeviceModelDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDeviceModels/DeviceModelDAO ==");
        }
    }

}
