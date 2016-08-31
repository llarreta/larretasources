package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ServiceHour
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceHour
 * @see co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal
 */
@Stateless(name="ServiceHourDAOLocal",mappedName="ejb/ServiceHourDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceHourDAO extends BaseDao implements ServiceHourDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceHourDAO.class);

    /**
     * Crea una jornada de servicio en el sistema
     * @param obj - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceHour(ServiceHour obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceHour/DAOServiceHourBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ServiceHour ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createServiceHour/DAOServiceHourBean ==");
        }
    }

    /**
     * Obtiene un servicehour con el id especificado
     * @param id - Long
     * @return - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceHour getServiceHourByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceHourByID/DAOServiceHourBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select servicehour from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" servicehour where servicehour.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select servicehour from " + ServiceHour.class.getName() + " servicehour where servicehour.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ServiceHour) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHourByID/DAOServiceHourBean ==");
        }
    }

    /**
     * Obtiene un servicehour con el id especificado
     * @param id - Long
     * @return - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceHour> getServiceHourByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceHourByName/DAOServiceHourBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select servicehour from ");
        	stringQuery.append("ServiceHour servicehour ");
        	stringQuery.append("where ");
        	stringQuery.append("servicehour.serviceHoursName = :serviceHoursName ");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select servicehour from ServiceHour servicehour where servicehour.serviceHoursName = :serviceHoursName");
            query.setString("serviceHoursName", name);

            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour por Name ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHourByName/DAOServiceHourBean ==");
        }
    }

    /**
     * Actualiza un servicehour especificado
     * @param obj - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceHour(ServiceHour obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateServiceHour/DAOServiceHourBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
        	throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateServiceHour/DAOServiceHourBean ==");
        }

    }

    /**
     * Elimina un servicehour del sistema
     * @param obj - ServiceHour
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceHour(ServiceHour obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteServiceHour/DAOServiceHourBean ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + ServiceHour.class.getName() + " sh where sh.id = :id");
            query.setLong("id", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceHour ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteServiceHour/DAOServiceHourBean ==");
        }

    }

    /**
     * Obtiene todos los servicehours del sistema
     * @return - List<ServiceHour>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceHour> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceHourBean ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("from ServiceHour");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceHour ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceHourBean ==");
        }
    }

    /**
     * Obtiene las jornadas de servicios dentro de un rango de tiempo especifico
     * @param init - Date
     * @param end - Date
     * @return -List<ServiceHourVO>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceHour> getServiceHoursByDate(Date init, Date end) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceHourBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select sh from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.initTime >= :initTime and sh.endTime <= :endTime");
            /*String strQuery = "select sh from " + ServiceHour.class.getName() + " sh where sh.initTime >= :initTime and sh.endTime <= :endTime";
            Query query = session.createQuery(strQuery);*/
        	Query query = session.createQuery(stringQuery.toString());
            query.setDate("initTime", init);
            query.setDate("endTime", end);

            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceHour ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceHourBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal#getAllServiceHoursByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	public List<ServiceHour> getAllServiceHoursByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getAllServiceHoursByCountryId/DAOServiceHourBean ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.country.id = :aCountryId");
            //Query query = session.createQuery("from " + ServiceHour.class.getName() + " sh where sh.country.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            List<ServiceHour> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour getAllServiceHoursByCountryId ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAllServiceHoursByCountryId/DAOServiceHourBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal#getAllServiceHoursByCountryIdAndStatusCode(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceHour> getAllServiceHoursByCountryIdAndStatusCode(Long countryId,String statusCode)
			throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getAllServiceHoursByCountryIdAndStatusCode/DAOServiceHourBean ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.country.id = :aCountryId");
        	stringQuery.append(" and sh.serviceHourStatus.serviceHoursStatusCode = :statusCode");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            query.setString("statusCode", statusCode);
            List<ServiceHour> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour getAllServiceHoursByCountryIdAndStatusCode ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAllServiceHoursByCountryIdAndStatusCode/DAOServiceHourBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal#getServiceHoursByCountryIdAndServiceHourStatusId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ServiceHour> getServiceHoursByCountryIdAndServiceHourStatusId(
			Long countryId, Long serviceHourStatusId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getServiceHoursByCountryIdAndServiceHourStatusId/DAOServiceHourBean ==");
		Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.country.id = :aCountryId");
        	stringQuery.append(" and sh.serviceHourStatus.id = :aServiceHourStatusId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            query.setLong("aServiceHourStatusId", serviceHourStatusId);
            List<ServiceHour> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour getServiceHoursByCountryIdAndServiceHourStatusId ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHoursByCountryIdAndServiceHourStatusId/DAOServiceHourBean ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ServiceHour> getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(
			String countryCode, String serviceHourStatusCode, Date anHour )
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getServiceHoursByCountryIdAndServiceHourStatusId/DAOServiceHourBean ==");
		Session session = super.getSession();
        try {
        	//Convirtiendo la fecha al primer día del primer año:
        	Date aRealHour = UtilsBusiness.convert2FirstYearMonthAndDay(anHour);
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.country.countryCode = :aCountryCode");
        	stringQuery.append(" and sh.serviceHourStatus.serviceHoursStatusCode = :aServiceHourStatusCode");
        	stringQuery.append(" and to_date('1900-01-01 ' || to_char(sh.initTime, 'hh24-mi-ss'), 'yyyy-mm-dd hh24:mi:ss') <= to_date(:anInitTime, 'yyyy-mm-dd hh24:mi:ss') ");
        	stringQuery.append(" and to_date('1900-01-01 ' || to_char(sh.endTime, 'hh24-mi-ss'), 'yyyy-mm-dd hh24:mi:ss') >= to_date(:anEndTime, 'yyyy-mm-dd hh24:mi:ss')");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCountryCode", countryCode);
            query.setString("aServiceHourStatusCode", serviceHourStatusCode);
            query.setString("anInitTime", UtilsBusiness.dateToString(aRealHour, "yyyy-MM-dd HH:mm:ss"));
            query.setString("anEndTime", UtilsBusiness.dateToString(aRealHour, "yyyy-MM-dd HH:mm:ss"));
            
            List<ServiceHour> result = query.list();
            
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour getServiceHoursByCountryIdAndServiceHourStatusId ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHoursByCountryIdAndServiceHourStatusId/DAOServiceHourBean ==");
        }
	}

	@Override
	@SuppressWarnings("unchecked")
	public ServiceHour getServiceHoursByNameAndCountryId(
			String serviceHourName, Long countryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getServiceHoursByNameAndCountryId/DAOServiceHourBean ==");
		Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.country.id = :aCountryId");
        	stringQuery.append(" and upper(sh.serviceHoursName) = :aServiceHourName");
        	stringQuery.append(" and sh.serviceHourStatus.serviceHoursStatusCode = :aServiceHourStatusCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            query.setString("aServiceHourName", serviceHourName.toUpperCase());
            query.setString("aServiceHourStatusCode", CodesBusinessEntityEnum.SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity());
            List<ServiceHour> result = query.list();
            if(result.isEmpty()){
            	return null;
            }
            return result.get(0);
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour getServiceHoursByNameAndCountryId ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHoursByNameAndCountryId/DAOServiceHourBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal#getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<ServiceHour> getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(Long countryId, Long serviceHourStatusId, Date initHour,Date endHour) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getServiceHoursByCountryIdAndServiceHourStatusId/DAOServiceHourBean ==");
		Session session = super.getSession();
        try {
        	//Convirtiendo la fecha al primer día del primer año:
        	Date aRealInitHour = UtilsBusiness.convert2FirstYearMonthAndDay(initHour);
        	Date aRealEndHour = UtilsBusiness.convert2FirstYearMonthAndDay(endHour);
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.country.id = :countryId");
        	stringQuery.append(" and sh.serviceHourStatus.id = :serviceHourStatusId");
        	stringQuery.append(" and (  ");
        	stringQuery.append(" ( to_date('1900-01-01 ' || to_char(sh.initTime, 'hh24-mi-ss'), 'yyyy-mm-dd hh24:mi:ss') <= :initHourInit ");
        	stringQuery.append(" and to_date('1900-01-01 ' || to_char(sh.endTime, 'hh24-mi-ss'), 'yyyy-mm-dd hh24:mi:ss') >= :initHourEnd )");
        	stringQuery.append(" or ( to_date('1900-01-01 ' || to_char(sh.initTime, 'hh24-mi-ss'), 'yyyy-mm-dd hh24:mi:ss') <= :endHourInit ");
        	stringQuery.append(" and to_date('1900-01-01 ' || to_char(sh.endTime, 'hh24-mi-ss'), 'yyyy-mm-dd hh24:mi:ss') >= :endHourEnd )");
        	stringQuery.append(" )  ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setLong("serviceHourStatusId", serviceHourStatusId);
            query.setTimestamp("initHourInit", aRealInitHour);
            query.setTimestamp("initHourEnd", aRealInitHour);
            query.setTimestamp("endHourInit", aRealEndHour);
            query.setTimestamp("endHourEnd", aRealEndHour);
            
            List<ServiceHour> result = query.list();
            
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour getServiceHoursByCountryIdAndServiceHourStatusId ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHoursByCountryIdAndServiceHourStatusId/DAOServiceHourBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal#getAllServiceHoursByCountryIdAndStatusCode(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceHour> getAllServiceHoursByCountryIdAndStatusCode(Long countryId,String statusCode,Long serviceHourToCompare)
			throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getAllServiceHoursByCountryIdAndStatusCode/DAOServiceHourBean ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceHour.class.getName());
        	stringQuery.append(" sh where sh.country.id = :aCountryId");
        	stringQuery.append(" and sh.serviceHourStatus.serviceHoursStatusCode = :statusCode");
        	stringQuery.append(" and sh.id <> :serviceHourToCompare");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            query.setLong("serviceHourToCompare", serviceHourToCompare);
            query.setString("statusCode", statusCode);
            List<ServiceHour> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceHour getAllServiceHoursByCountryIdAndStatusCode ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAllServiceHoursByCountryIdAndStatusCode/DAOServiceHourBean ==");
        }
	}
}
