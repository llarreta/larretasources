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
import co.com.directv.sdii.model.pojo.ServiceStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ServiceStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ServiceStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceStatus
 * @see co.com.directv.sdii.persistence.dao.config.ServiceStatusDAOLocal
 */
@Stateless(name="ServiceStatusDAOLocal",mappedName="ejb/ServiceStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceStatusDAO extends BaseDao implements ServiceStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceStatusDAO.class);

    /**
     * Crea una ServiceStatus en el sistema
     * @param obj - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceStatus(ServiceStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceStatus/DAOServiceStatusBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ServiceStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createServiceStatus/DAOServiceStatusBean ==");
        }
    }

    /**
     * Obtiene un servicestatus con el id especificado
     * @param id - Long
     * @return - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceStatus getServiceStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceStatusByID/DAOServiceStatusBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select servicestatus from ");
        	stringQuery.append("ServiceStatus servicestatus ");
        	stringQuery.append("where ");
        	stringQuery.append("servicestatus.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select servicestatus from ServiceStatus servicestatus where servicestatus.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ServiceStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceStatus por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceStatusByID/DAOServiceStatusBean ==");
        }
    }

    /**
     * Actualiza un servicestatus especificado
     * @param obj - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceStatus(ServiceStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateServiceStatus/DAOServiceStatusBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ServiceStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateServiceStatus/DAOServiceStatusBean ==");
        }

    }

    /**
     * Elimina un servicestatus del sistema
     * @param obj - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceStatus(ServiceStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteServiceStatus/DAOServiceStatusBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteServiceStatus/DAOServiceStatusBean ==");
        }

    }

    /**
     * Obtiene todos los servicestatuss del sistema
     * @return - List<ServiceStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceStatus> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceStatusBean ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("from ServiceStatus");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceStatusBean ==");
        }
    }

	@Override
	public ServiceStatus getServiceStatusByCode(String serviceStatusCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getServiceStatusByCode/DAOServiceStatusBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceStatus.class.getName());
        	stringQuery.append(" ss where ");
        	stringQuery.append("ss.serviceStatusCode = :aSSCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aSSCode", serviceStatusCode);
        	Object obj = query.uniqueResult();
            //Object obj = session.createQuery("select servicestatus from ServiceStatus servicestatus where servicestatus.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ServiceStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceStatus por codigo ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceStatusByCode/DAOServiceStatusBean ==");
        }
	}

}
