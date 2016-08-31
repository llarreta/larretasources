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
import co.com.directv.sdii.model.pojo.ServiceHourStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ServiceHourStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ServiceHourStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceHourStatus
 * @see co.com.directv.sdii.persistence.dao.config.ServiceHourStatusDAOLocal
 */
@Stateless(name="ServiceHourStatusDAOLocal",mappedName="ejb/ServiceHourStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceHourStatusDAO extends BaseDao implements ServiceHourStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceHourStatusDAO.class);
    /**
     * Crea un ServiceHourStatus en el sistema
     * @param obj - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceHourStatus(ServiceHourStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceHourStatus/DAOServiceHourStatusBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ServiceHourStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createServiceHourStatus/DAOServiceHourStatusBean ==");
        }
    }

    /**
     * Obtiene un servicehourstatus con el id especificado
     * @param id - Long
     * @return - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceHourStatus getServiceHourStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceHourStatusByID/DAOServiceHourStatusBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select servicehourstatus from ");
        	stringQuery.append(ServiceHourStatus.class.getName());
        	stringQuery.append(" servicehourstatus where servicehourstatus.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select servicehourstatus from " + ServiceHourStatus.class.getName() +" servicehourstatus where servicehourstatus.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ServiceHourStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHourStatus por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHourStatusByID/DAOServiceHourStatusBean ==");
        }
    }
    
    /**
     * Obtiene un servicehourstatus con el id especificado
     * @param code - Code
     * @return - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceHourStatus getServiceHourStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceHourStatusByCode/DAOServiceHourStatusBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select servicehourstatus from ");
        	stringQuery.append(ServiceHourStatus.class.getName());
        	stringQuery.append(" servicehourstatus where servicehourstatus.serviceHoursStatusCode = '");
        	stringQuery.append(code);
        	stringQuery.append("' ");
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select servicehourstatus from " + ServiceHourStatus.class.getName() +" servicehourstatus where servicehourstatus.serviceHoursStatusCode = '" + code + "' ").uniqueResult();
            if (obj != null) {
                return (ServiceHourStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHourStatus por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHourStatusByID/DAOServiceHourStatusBean ==");
        }
    }

    /**
     * Actualiza un servicehourstatus especificado
     * @param obj - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceHourStatus(ServiceHourStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateServiceHourStatus/DAOServiceHourStatusBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ServiceHourStatus ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceHourStatus/DAOServiceHourStatusBean ==");
        }

    }

    /**
     * Elimina un servicehourstatus del sistema
     * @param obj - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceHourStatus(ServiceHourStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteServiceHourStatus/DAOServiceHourStatusBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceHourStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteServiceHourStatus/DAOServiceHourStatusBean ==");
        }

    }

    /**
     * Obtiene todos los servicehourstatuss del sistema
     * @return - List<ServiceHourStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceHourStatus> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceHourStatusBean ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHourStatus.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + ServiceHourStatus.class.getName());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceHourStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceHourStatusBean ==");
        }
    }

}
