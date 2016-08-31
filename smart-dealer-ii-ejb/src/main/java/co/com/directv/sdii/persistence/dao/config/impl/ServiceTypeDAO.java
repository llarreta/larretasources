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
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ServiceTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ServiceType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceType
 * @see co.com.directv.sdii.persistence.dao.config.ServiceTypeDAOLocal
 */
@Stateless(name="ServiceTypeDAOLocal",mappedName="ejb/ServiceTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceTypeDAO extends BaseDao implements ServiceTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceTypeDAO.class);

    /**
     * Crea una ServiceType en el sistema
     * @param obj - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceType(ServiceType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceType/DAOServiceTypeBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ServiceType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createServiceType/DAOServiceTypeBean ==");
        }
    }

    /**
     * Obtiene un servicetype con el id especificado
     * @param id - Long
     * @return - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceType getServiceTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceTypeByID/DAOServiceTypeBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select servicetype from ");
        	stringQuery.append("ServiceType servicetype ");
        	stringQuery.append("where ");
        	stringQuery.append("servicetype.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select servicetype from ServiceType servicetype where servicetype.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ServiceType) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceType por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceTypeByID/DAOServiceTypeBean ==");
        }
    }

    /**
     * Actualiza un servicetype especificado
     * @param obj - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceType(ServiceType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateServiceType/DAOServiceTypeBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ServiceType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateServiceType/DAOServiceTypeBean ==");
        }

    }

    /**
     * Elimina un servicetype del sistema
     * @param obj - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceType(ServiceType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteServiceType/DAOServiceTypeBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteServiceType/DAOServiceTypeBean ==");
        }

    }

    /**
     * Obtiene todos los servicetypes del sistema
     * @return - List<ServiceType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceType> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceTypeBean ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceType.class.getName());
        	stringQuery.append(" st order by st.serviceTypeName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + ServiceType.class.getName() + " st order by st.serviceTypeName");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceTypeBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceTypeDAOLocal#getServiceTypeByServiceCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ServiceType getServiceTypeByServiceCode(String serviceCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getServiceTypeByServiceCode/DAOServiceTypeBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select ser.serviceCategory.serviceType from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" ser where ser.serviceCode = :aServceCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aServceCode", serviceCode);
        	Object obj = query.uniqueResult();
            if (obj != null) {
                return (ServiceType) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceType por Código del Service que lo tiene asociado ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceTypeByServiceCode/DAOServiceTypeBean ==");
        }
	}
	
	
	/* 
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceTypeDAOLocal#getServiceTypeByServiceCode(java.lang.String)
	 */
	@Override
	public  List<ServiceType> getServiceTypeByBusinessArea(Long businessAreaId)throws DAOServiceException, DAOSQLException{
		
		log.debug("== Inicio getServiceTypeByBusinessArea/DAOServiceTypeBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from "+ServiceType.class.getName()+" st ");
        	stringQuery.append(" where st.businessAreaId = :businessAreaId");
        	Query query = session.createQuery(stringQuery.toString());
			query.setLong("businessAreaId", businessAreaId);				
			            
            return (List<ServiceType>)query.list();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceType por Código del Service que lo tiene asociado ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceTypeByBusinessArea/DAOServiceTypeBean ==");
        }
	}

}
