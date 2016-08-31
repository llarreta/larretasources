package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceStatus;
import co.com.directv.sdii.model.pojo.TypeServiceWorkOrders;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Service
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Service
 * @see co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal
 */
@Stateless(name="ServiceDAOLocal",mappedName="ejb/ServiceDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceDAO extends BaseDao implements ServiceDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceDAO.class);

    /**
     * Crea un Service en el sistema
     * @param obj - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createService(Service obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createService/DAOServiceBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Service ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createService/DAOServiceBean ==");
        }
    }

    /**
     * Obtiene un service con el id especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Service getServiceByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceByID/DAOServiceBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select service from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" service where service.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select service from " + Service.class.getName() + " service where service.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (Service) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Service por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceByID/DAOServiceBean ==");
        }
    }

    /**
     * Actualiza un service especificado
     * @param obj - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateService(Service obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateService/DAOServiceBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Service ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateService/DAOServiceBean ==");
        }

    }

    /**
     * Elimina un service del sistema
     * @param obj - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteService(Service obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteService/DAOServiceBean ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" s where s.id =:id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + Service.class.getName() + " s where s.id =:id");
            query.setLong("id", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
            
        } catch (Throwable ex) {
            log.debug("== Error borrando el Service ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteService/DAOServiceBean ==");
        }

    }

    /**
     * Obtiene todos los services del sistema
     * @return - List<Service>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Service> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceBean ==");
        Session session = super.getSession();
        StringBuffer stringQuery = new StringBuffer("from Service service ");
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery(stringQuery.toString());
            
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el Service ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceBean ==");
        }
    }
    
    /**
     * Obtiene todos los services del sistema
     * @return - List<Service>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Service> getAllByServiceCodeOpening(boolean serviceCodeOpening) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceBean ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer(" from " + Service.class.getName() + " service "); 
            if(serviceCodeOpening)
            	//stringQuery.append("  where service.serviceCode not in('"+CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity().replaceAll(",", "','")+"') ");
            	stringQuery.append("  where length(service.serviceCode) = 3 ");
            Query query = session.createQuery(stringQuery.toString());
        	
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el Service ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceBean ==");
        }
    }

    /**
     * Obtiene un listado de services con el estado especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Service> getServiceByState(ServiceStatus status) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceByState/DAOServiceBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select service from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" service where service.serviceStatus.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select service from " + Service.class.getName() + " service where service.serviceStatus.id = :id");
            query.setString("id", status.getId().toString());

            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el Service por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceByState/DAOServiceBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal#getActiveServicesByServiceCategories(java.util.List, co.com.directv.sdii.model.pojo.ServiceStatus)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Service> getActiveServicesByServiceCategories(
			List<Long> categoriesId, ServiceStatus status)
			throws DAOServiceException, DAOSQLException {
    	 log.debug("== Inicio getActiveServicesByServiceCategories/DAOServiceBean ==");
         Session session = super.getSession();
         try {
         	StringBuffer stringQuery = new StringBuffer();
         	stringQuery.append("select service from ");
         	stringQuery.append(Service.class.getName());
         	stringQuery.append(" service where service.serviceStatus.id = :status");
         	
         	if(categoriesId.size()>0){
         		stringQuery.append(" and (");
         		for(int i = 0; i<categoriesId.size();i++){
         			if(i==0){
         				stringQuery.append(" service.serviceCategory.id = :id"+i);
         			}else{
         				stringQuery.append(" or service.serviceCategory.id = :id"+i);
         			}
         		}
         		stringQuery.append(" )");
         	}
         	
         	stringQuery.append(" order by service.serviceName asc");
         	
         	Query query = session.createQuery(stringQuery.toString());
            query.setString("status", status.getId().toString());
            if(categoriesId.size()>0){
         		for(int i = 0; i<categoriesId.size();i++){
         			 query.setLong("id"+i,categoriesId.get(i));
         		}
         	}

             return query.list();
         } catch (Throwable ex) {
             log.debug("== Error consultando el Service por estado y categoría ==");
             throw super.manageException(ex);
         }finally {
             log.debug("== Termina getActiveServicesByServiceCategories/DAOServiceBean ==");
         }
	}

    /**
     * Obtiene un service con el codigo especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Service getServiceByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceByCode/DAOServiceBean ==");
        Session session = super.getSession();
        try {
        	/*
        	 * upper(replace(model.id.identificacion,\' \',\'\'))
        	 */
        	if(code==null)
        		return null;
    		
        	String serivceCode = code.toUpperCase().replace(" ", "");
    		
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("select service from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" service where ");
        	stringQuery.append("upper(replace(service.serviceCode,\' \',\'\')) ");
        	stringQuery.append("= ");
        	stringQuery.append("\'");
        	stringQuery.append(serivceCode);
        	stringQuery.append("\'");
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select service from " + Service.class.getName() + " service where service.serviceCode = '" + code + "' ").uniqueResult();
            if (obj != null) {
                return (Service) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Service por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceByCode/DAOServiceBean ==");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Service getServiceBySimpleCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceByCode/DAOServiceBean ==");
        Session session = super.getSession();
        try {
        	
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" service where ");
        	stringQuery.append(" service.serviceCode = :aCode");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aCode", code);
            
            return (Service) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando el Service por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceByCode/DAOServiceBean ==");
        }
    } 
    
    /**
     * Obtiene un listado de services con el estado especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Service> getServiceByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceByName/DAOServiceBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select service from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" service where service.serviceName = :name");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select service from " + Service.class.getName() + " service where service.serviceName = :name");
            query.setString("name", name);

            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el Service por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceByName/DAOServiceBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal#getServicesByWoTypeId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Service> getServicesByWoTypeId(Long woTypeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getServicesByWoTypeId/DAOServiceBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select s from ");
        	stringQuery.append(Service.class.getName() + " s, ");
        	stringQuery.append(TypeServiceWorkOrders.class.getName() + " tsw where ");
        	stringQuery.append("s.serviceCategory.serviceType.id = tsw.serviceType.id ");
        	stringQuery.append("and tsw.workOrderType.id = :woTypeId ");
        	stringQuery.append("and s.serviceStatus.serviceStatusCode = :activeStatus ");
        	stringQuery.append("and s.allowAddService = :allowService ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("woTypeId", woTypeId);
        	query.setString("activeStatus", CodesBusinessEntityEnum.CODIGO_SERVICE_STATUS_ACTIVE.getCodeEntity());
        	query.setString("allowService", CodesBusinessEntityEnum.ALLOW_ADD_SERVICE.getCodeEntity());
        	
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getServicesByWoTypeId/DAOServiceBean ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServicesByWoTypeId/DAOServiceBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal#calculateWoServicesInSuperCategory(java.util.List, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long calculateWoServicesInSuperCategory(List<String> servicesCodes,String superCatCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia calculateWoServicesInSuperCategory/DAOServiceBean ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryString = new StringBuffer();
            queryString.append(" select count(s.id) ");
            queryString.append("   from " + Service.class.getName() + " s ");
            queryString.append("  where s.serviceCategory.serviceType.superCategory.code = :superCatCode ");
            queryString.append("        and s.serviceCode in (");
            StringBuffer sb = new StringBuffer();
            for( String sCode : servicesCodes ){
            	sb.append("'");
            	sb.append(sCode);
            	sb.append("'");
            	sb.append(",");
            }
            queryString.append( StringUtils.removeEnd(sb.toString(), ",") );
            queryString.append( ") " );
            Query query = session.createQuery(queryString.toString());
            query.setString("superCatCode", superCatCode);
            Long count = (Long) query.uniqueResult();
            if( count != null )
            	return count;
            else
            	return 0L;
        } catch (Throwable ex) {
            log.debug("== Error calculateWoServicesInSuperCategory/DAOServiceBean ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina calculateWoServicesInSuperCategory/DAOServiceBean ==");
        }
	}

	
}
