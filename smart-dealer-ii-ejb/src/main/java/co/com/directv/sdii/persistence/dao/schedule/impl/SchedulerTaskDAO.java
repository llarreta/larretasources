package co.com.directv.sdii.persistence.dao.schedule.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SchedulerTask;
import co.com.directv.sdii.model.pojo.SchedulerTaskDetails;
import co.com.directv.sdii.model.pojo.SchedulerTaskDetailsStatus;
import co.com.directv.sdii.model.pojo.SchedulerTaskStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.schedule.SchedulerTaskDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * Session Bean implementation class SchedulerTaskDAO
 */
@Stateless
public class SchedulerTaskDAO extends BaseDao implements SchedulerTaskDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(SchedulerTaskDAO.class);
	
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAOLocal;
	
    /**
     * Default constructor. 
     */
    public SchedulerTaskDAO() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createSchedulerTask(SchedulerTask obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createSchedulerTask/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando SchedulerTask ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSchedulerTask/SchedulerTaskDAO ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteSchedulerTask(SchedulerTask obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteSchedulerTask/SchedulerTaskDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append(" delete from ");
            stringQuery.append(SchedulerTask.class.getName());
            stringQuery.append(" st where st.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", obj.getId());
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando SchedulerTask ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteSchedulerTask/SchedulerTaskDAO ==");
        }
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<SchedulerTask> getAllSchedulerTask() throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getAll/SchedulerTaskDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(SchedulerTask.class.getName());
            Query query = session.createQuery(stringQuery.toString());
            return (List<SchedulerTask>)query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando todos los SchedulerTaskDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/SchedulerTaskDAO ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<SchedulerTask> getAllSchedulerTaskByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllSchedulerTaskByCountryId/SchedulerTaskDAO ==");
		Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(SchedulerTask.class.getName());
            stringQuery.append(" st where st.country.id = :countryId");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            List<SchedulerTask> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.error("== Error consultando todos los SchedulerTask por país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSchedulerTaskByCountryId/SchedulerTaskDAO ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<SchedulerTask> getSchedulerTaskByCode(String schedulerTaskCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSchedulerTaskByCode/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SchedulerTask.class.getName());
            strBuffer.append(" tp where tp.schedulerTaskTypes.code = :schedulerTaskCode");
            Query query = session.createQuery(strBuffer.toString());
            query.setString("schedulerTaskCode", schedulerTaskCode);
            List<SchedulerTask> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.error("== Error consultando todos los SchedulerTask por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSchedulerTaskByCode/SchedulerTaskDAO ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public SchedulerTask getSchedulerTaskByCodeAndCountryId(
			String code, Long countryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getSchedulerTaskByCodeAndCountryId/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SchedulerTask.class.getName());
            strBuffer.append(" st where st.schedulerTaskTypes.code = :code and tp.country.id = :countryId");
            
            
            Query query = session.createQuery(strBuffer.toString());
            query.setString("code", code);
            query.setLong("countryId", countryId);

            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (SchedulerTask) obj;

        } catch (Throwable ex) {
            log.error("== Error consultando todos los SchedulerTask por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSchedulerTaskByCodeAndCountryId/SchedulerTaskDAO ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public SchedulerTask getSchedulerTaskByID(Long id)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSchedulerTaskByID/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("select st from ");
            stringQuery.append(SchedulerTask.class.getName());
            stringQuery.append(" st where st.id = :id ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (SchedulerTask) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.error("== Error consultando SchedulerTask por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getSchedulerTaskByID/SchedulerTaskDAO ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateSchedulerTask(SchedulerTask obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateSchedulerTask/SchedulerTaskDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando SchedulerTask ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateSchedulerTask/SchedulerTaskDAO ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<SchedulerTask> getSchedulerTaskForExecute(String status, Long id)//trae todos los registros de 
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSchedulerTaskByCode/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            String queryCountry = "select distinct st.country.id from "+SchedulerTask.class.getName()+" st";
            Query queryCountries = session.createQuery(queryCountry);
            List<Long> countriesId = queryCountries.list();
            Map<Long, Date> systemDate = new HashMap<Long, Date>();
            for(Long idCountry : countriesId){
            	systemDate.put(idCountry, co.com.directv.sdii.common.util.UtilsBusiness.getDateLastChangeOfUser(idCountry, userDao, systemParameterDAOLocal));
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SchedulerTask.class.getName());
            strBuffer.append(" st where st.schedulerTaskStatus.code = :status and not exists ( select 1 from "+SchedulerTaskDetails.class.getName()+" " 
            		       + "std where std.schedulerTaskDetailsStatus.code = :codeDetail and std.schedulerTask.id = st.id ) and ( ");
            Set<Long> keys = systemDate.keySet();
            boolean isFirstCondition=true;
            for(Long key : keys){
            	if(isFirstCondition){
            		strBuffer.append(" ( st.nextExecutionDate <= :dateNow"+key+" and st.country.id = :countryId"+key+" ) ");
            		isFirstCondition=false;
            	}else{
            		strBuffer.append(" or ( st.nextExecutionDate <= :dateNow"+key+" and st.country.id = :countryId"+key+" ) ");
            	}
            }
            if(!isFirstCondition){
            	strBuffer.append(" ) ");
            }else{
            	strBuffer.append(" 1=1 ) ");
            }
            if(id!=null && id!=0L){
            	strBuffer.append(" and st.id = :id ");
            }
            Query query = session.createQuery(strBuffer.toString());
            query.setString("status", status);
            for(Long key : keys){
            	query.setTimestamp("dateNow"+key, new Timestamp(systemDate.get(key).getTime()));
            	query.setLong("countryId"+key, key);
            }
            query.setString("codeDetail", CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_DETAIL_NO_FINISHED_PROCCESS.getCodeEntity());
            if(id!=null && id!=0L){
            	query.setLong("id", id);
            }
            List<SchedulerTask> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.error("== Error consultando todos los SchedulerTask por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSchedulerTaskByCode/SchedulerTaskDAO ==");
        }
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public SchedulerTaskStatus getSchedulerTaskStatusByCode(String statusCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSchedulerTaskByCode/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SchedulerTaskStatus.class.getName());
            strBuffer.append(" s where s.code = :statusCode ");
            Query query = session.createQuery(strBuffer.toString());
            query.setString("statusCode", statusCode);
            SchedulerTaskStatus result = (SchedulerTaskStatus) query.uniqueResult();
            return result;
        } catch (Throwable ex) {
            log.error("== Error consultando todos los SchedulerTask por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSchedulerTaskByCode/SchedulerTaskDAO ==");
        }
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public SchedulerTaskDetailsStatus getSchedulerTaskDetailStatusByCode(String statusCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSchedulerTaskByCode/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SchedulerTaskDetailsStatus.class.getName());
            strBuffer.append(" s where s.code = :statusCode ");
            Query query = session.createQuery(strBuffer.toString());
            query.setString("statusCode", statusCode);
            SchedulerTaskDetailsStatus result = (SchedulerTaskDetailsStatus) query.uniqueResult();
            return result;
        } catch (Throwable ex) {
            log.error("== Error consultando todos los SchedulerTask por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSchedulerTaskByCode/SchedulerTaskDAO ==");
        }
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
    public void createSchedulerTaskDetail(SchedulerTaskDetails obj) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio createSchedulerTask/SchedulerTaskDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando SchedulerTask ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSchedulerTask/SchedulerTaskDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
    public Long finishSchedulerTaskDetail(Long id, String detail, boolean fail)throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia updateSchedulerTask/SchedulerTaskDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SchedulerTaskDetails.class.getName());
            strBuffer.append(" s where s.id = :id ");
            Query query = session.createQuery(strBuffer.toString());
            query.setLong("id", id);
            SchedulerTaskDetails std = (SchedulerTaskDetails) query.uniqueResult();
            if(std == null){
            	throw new DAOServiceException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode());
            }
            if(fail){
            	std.setSchedulerTaskDetailsStatus(getSchedulerTaskDetailStatusByCode(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_DETAIL_FINISHED_PROCCESS_WITH_ERRORS.getCodeEntity()));
            }else{
            	std.setSchedulerTaskDetailsStatus(getSchedulerTaskDetailStatusByCode(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_DETAIL_FINISHED_PROCCESS.getCodeEntity()));
            }          
            if(detail!=null && detail.length()>3900){
            	detail = detail.substring(0, 3900);
            }
            std.setDescription(detail);
            std.setEndDate(co.com.directv.sdii.common.util.UtilsBusiness.getDateLastChangeOfUser(std.getSchedulerTask().getCountry().getId(), userDao, systemParameterDAOLocal));
            session.merge(std);
            this.doFlush(session);
            return std.getSchedulerTask().getId();
        } catch (Throwable ex) {
            log.error("== Error actualizando SchedulerTask ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateSchedulerTask/SchedulerTaskDAO ==");
        }
    }
}
