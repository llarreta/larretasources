package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportFilterDTO;
import co.com.directv.sdii.model.dto.ScheduleReportStatusDTO;
import co.com.directv.sdii.model.dto.ScheduleReportTypeDTO;
import co.com.directv.sdii.model.pojo.ScheduleReport;
import co.com.directv.sdii.model.pojo.ScheduleReportLog;
import co.com.directv.sdii.model.pojo.ScheduleReportParameter;
import co.com.directv.sdii.model.pojo.ScheduleReportPeriodType;
import co.com.directv.sdii.model.pojo.ScheduleReportStatus;
import co.com.directv.sdii.model.pojo.ScheduleReportType;
import co.com.directv.sdii.model.vo.ScheduleReportPeriodTypeVO;
import co.com.directv.sdii.model.vo.ScheduleReportTypeVO;
import co.com.directv.sdii.model.vo.ScheduleReportVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ScheduleReportDAOLocal;


/**
 * Session Bean implementation class ScheduleReportDAO
 */
@Stateless
public class ScheduleReportDAO extends BaseDao implements ScheduleReportDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ScheduleReportDAO.class);
	
    /**
     * Default constructor. 
     */
    public ScheduleReportDAO() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createScheduleReport(ScheduleReportVO sr) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio createScheduleReport/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            ScheduleReport srReal = new ScheduleReport(sr);
            /*
             *Se requiere que el atributo CODE posea el mismo valor que el atributo ID.
             *El atributo ID es un sequencial asignado por la BD, para obtenerlo, realizamos el codigo a continuacion.
             */
            session.persist(srReal);
            this.doFlush(session);
            srReal.setCode(srReal.getId().toString());
            session.merge(srReal);
            session.save(srReal);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error en el metodo del DAO createScheduleReport ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrder/ScheduleReportDAO ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createScheduleReport(ScheduleReport sr) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio createScheduleReport/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            /*
             *Se requiere que el atributo CODE posea el mismo valor que el atributo ID.
             *El atributo ID es un sequencial asignado por la BD, para obtenerlo, realizamos el codigo a continuacion.
             */
            session.persist(sr);
            this.doFlush(session);
            sr.setCode(sr.getId().toString());
            session.merge(sr);
            session.save(sr);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error en el metodo del DAO createScheduleReport ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrder/ScheduleReportDAO ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ScheduleReportVO> getAllScheduleReport() throws DAOServiceException, DAOSQLException{
    	List<ScheduleReportVO> returnValue=null;
        log.debug("== Inicio getAllScheduleReport/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select new "+ScheduleReportVO.class.getName()+"(sr) from "+ScheduleReport.class.getName()+" sr ");
            Query query = session.createQuery(queryBuffer.toString());
            returnValue= (List<ScheduleReportVO>)query.list();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getAllScheduleReport==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllScheduleReport/ScheduleReportDAO ==");
        }
    }

  //CC053 - HSP Reportes - CRUD Programacion.
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ScheduleReport getScheduleReportById(Long id) throws DAOServiceException, DAOSQLException{
    	ScheduleReport returnValue=null;
        log.debug("== Inicio getScheduleReportById/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from "+ScheduleReport.class.getName()+" sr where sr.id=:id ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            returnValue= (ScheduleReport)query.uniqueResult();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getScheduleReportById==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportById/ScheduleReportDAO ==");
        }
    }
    //retorna las fechas levantadas de la tabla
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ScheduleReportVO> getPendingReportsForThisTime(Long countryId , Date dateNow) throws DAOServiceException, DAOSQLException{
    	List<ScheduleReportVO> returnValue=null;
        log.debug("== Inicio getPendingReportsForThisTime/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select new "+ScheduleReportVO.class.getName()+"(sr) from "+ScheduleReport.class.getName()+" sr where sr.status.code = ");
            queryBuffer.append(" :code and sr.executionDate <= :dateNow and sr.country.id = :countryId");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("code", CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_PENDING.getCodeEntity());
            query.setLong("countryId", countryId);
            query.setTimestamp("dateNow", new java.sql.Timestamp(dateNow.getTime()));
            returnValue= (List<ScheduleReportVO>)query.list();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getPendingReportsForThisTime==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPendingReportsForThisTime/ScheduleReportDAO ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ScheduleReport> getPendingReportsForThisTimeSR(Long countryId , Date dateNow) throws DAOServiceException, DAOSQLException{
    	List<ScheduleReport> returnValue=null;
        log.debug("== Inicio getPendingReportsForThisTime/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from "+ScheduleReport.class.getName()+" sr where sr.status.code = ");
            queryBuffer.append(" :code and sr.executionDate <= :dateNow and sr.country.id = :countryId");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("code", CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_PENDING.getCodeEntity());
            query.setLong("countryId", countryId);
            query.setTimestamp("dateNow", new java.sql.Timestamp(dateNow.getTime()));
            returnValue= (List<ScheduleReport>)query.list();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getPendingReportsForThisTime==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPendingReportsForThisTime/ScheduleReportDAO ==");
        }
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateScheduleReport(ScheduleReportVO sr) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio updateScheduleReport/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            ScheduleReport srReal = new ScheduleReport(sr);
            session.merge(srReal);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error en el metodo del DAO updateScheduleReport ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateScheduleReport/ScheduleReportDAO ==");
        }
    }

  //CC053 - HSP Reportes - CRUD Programacion.
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteScheduleReport(ScheduleReport scheduleReport) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio deleteScheduleReport/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            session.delete(scheduleReport);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error en el metodo del DAO deleteScheduleReport ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteScheduleReport/ScheduleReportDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteScheduleReportLog(Long scheduleReportId) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio deleteScheduleReportLog/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append(" delete from  ");
            queryBuffer.append(ScheduleReportLog.class.getName());
            queryBuffer.append(" srl where srl.scheduleReport.id = :scheduleReportId ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("scheduleReportId", scheduleReportId);
            query.executeUpdate();
            this.doFlush(session);
                                  
        }catch (Throwable ex) {
            log.error("== Error en el metodo del DAO deleteScheduleReport ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteScheduleReportLog/ScheduleReportDAO ==");
        }
    }
    
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createScheduleReportLog(Long scheduleReportId, String message, String newStateCode, Date dateNow) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio createScheduleReportLog/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            ScheduleReport sr=getScheduleReportById(scheduleReportId);
            ScheduleReportStatus srs=getScheduleReportStateByCode(newStateCode);
            ScheduleReportLog srl=new ScheduleReportLog();
            srl.setDateLog(dateNow);
            srl.setLog(message);
            srl.setScheduleReport(sr);
            srl.setScheduleReportStatus(srs);
            session.save(srl);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error en el metodo del DAO createScheduleReportLog ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createScheduleReportLog/ScheduleReportDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ScheduleReportStatus getScheduleReportStateByCode(String code) throws DAOServiceException, DAOSQLException{
    	ScheduleReportStatus returnValue=null;
        log.debug("== Inicio getScheduleReportStateByCode/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select srs from "+ScheduleReportStatus.class.getName()+" srs where srs.code=:code ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("code", code);
            returnValue= (ScheduleReportStatus)query.uniqueResult();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getScheduleReportStateByCode==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportStateByCode/ScheduleReportDAO ==");
        }
    }

    //CC053 - HSP Reportes - CRUD Programacion.
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ScheduleReportStatusDTO> getAllScheduleReportStatus() throws DAOServiceException, DAOSQLException{
    	
        log.debug("== Inicio getAllScheduleReportStatus/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select new "+ScheduleReportStatusDTO.class.getName()+"(srs.id,srs.description) from "+ScheduleReportStatus.class.getName()+" srs ");
            Query query = session.createQuery(queryBuffer.toString());
            
        	return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getAllScheduleReportStatus==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllScheduleReportStatus/ScheduleReportDAO ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ScheduleReportTypeDTO> getScheduleReportTypes() throws DAOServiceException, DAOSQLException{
    	List<ScheduleReportTypeDTO> returnValue=null;
        log.debug("== Inicio getScheduleReportTypes/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
			//CC053 - HSP Reportes de Invetarios
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select new "+ScheduleReportTypeDTO.class.getName()+"(srt.id,srt.description) from "+ScheduleReportType.class.getName()+" srt ");
            queryBuffer.append(" where srt.reportClass=:reportClass ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("reportClass", CodesBusinessEntityEnum.REPORT_PROCESS_CLASS_USER.getCodeEntity()); 
            returnValue= (List<ScheduleReportTypeDTO>)query.list();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getScheduleReportTypes==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportTypes/ScheduleReportDAO ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ScheduleReportTypeVO getScheduleReportTypeId(Long id) throws DAOServiceException, DAOSQLException{
    	ScheduleReportTypeVO returnValue=null;
        log.debug("== Inicio getScheduleReportTypeId/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select new "+ScheduleReportTypeVO.class.getName()+"(srt) from "+ScheduleReportType.class.getName()+" srt ");
            queryBuffer.append(" where srt.id=:id ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            returnValue= (ScheduleReportTypeVO)query.uniqueResult();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getScheduleReportTypeId==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportTypeId/ScheduleReportDAO ==");
        }
    }

	@Override
	public ScheduleReportPeriodTypeVO getScheduleReportPeriodTypeByCode(
			String code) throws DAOServiceException, DAOSQLException {
		ScheduleReportPeriodTypeVO returnValue=null;
        log.debug("== Inicio getScheduleReportPeriodTypeByCode/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select new "+ScheduleReportPeriodTypeVO.class.getName()+"(srt) from "+ScheduleReportPeriodType.class.getName()+" srt ");
            queryBuffer.append(" where srt.code=:code ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("code", code);
            returnValue= (ScheduleReportPeriodTypeVO)query.uniqueResult();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getScheduleReportPeriodTypeByCode==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportPeriodTypeByCode/ScheduleReportDAO ==");
        }
	}

	//CC053 - HSP Reportes - CRUD Programacion.
	 @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ScheduleReportDTO> getScheduleReports(ScheduleReportFilterDTO filter, Long periodTypeId) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getScheduleReportByCountryId/ScheduleReportDAO ==");
		 List<ScheduleReportDTO> returnValue=null;
		 Session session = super.getSession();
	        try {
	        	if (session == null) {
	                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	            }
	        	StringBuffer queryBuffer = new StringBuffer();
	            queryBuffer.append(" SELECT      SR.ID id,  ");
	        	queryBuffer.append("             SRT.DESCRIPTION reportTypeName, ");
	        	queryBuffer.append("             SRS.DESCRIPTION statusName, ");
	        	queryBuffer.append("             SR.BEGIN_DATE beginDate, ");
	        	queryBuffer.append("             SR.END_DATE endDate, ");
	        	queryBuffer.append("             U.LOGIN login, ");
	        	queryBuffer.append("             (SELECT LOG  log ");
	        	queryBuffer.append("              FROM SCHEDULE_REPORT_LOGS ");
	        	queryBuffer.append("              WHERE ID=MAXLOGS.MAXID ) log ");
	        	queryBuffer.append(" FROM        SCHEDULE_REPORTS SR LEFT JOIN (SELECT     MAX(A.ID) MAXID, ");
	        	queryBuffer.append("                                                       A.SCHEDULE_REPORT_ID REPORTID ");
	        	queryBuffer.append("                                            FROM       SCHEDULE_REPORT_LOGS A, ");
	        	queryBuffer.append("                                                       SCHEDULE_REPORTS B");
	        	queryBuffer.append("                                            WHERE      A.SCHEDULE_REPORT_ID = B.ID ");
	        	queryBuffer.append("                                            GROUP BY   A.SCHEDULE_REPORT_ID) MAXLOGS ON (MAXLOGS.REPORTID = SR.ID) ");
	        	queryBuffer.append("                                 INNER JOIN SCHEDULE_REPORT_TYPES SRT ON(SR.REPORT_TYPE_ID = SRT.ID) ");
	        	queryBuffer.append("                                 INNER JOIN SCHEDULE_REPORT_STATUS SRS ON(SR.STATUS_ID = SRS.ID) ");
	        	queryBuffer.append("                                 INNER JOIN USERS U ON(SR.USER_ID = U.ID) ");
	        	queryBuffer.append(" WHERE       SR.COUNTRY_ID =:countryId ");
	        	queryBuffer.append("             AND SR.PERIOD_TYPE_ID=:periodTypeId ");
	        	queryBuffer.append("             AND SR.REPORT_TYPE_ID =:reportTypeId ");
	        	queryBuffer.append("             AND SR.STATUS_ID=:statusId ");
	        	
	        	Query query = session.createSQLQuery(queryBuffer.toString())
	        			             .addScalar("id", Hibernate.LONG)
	        			             .addScalar("reportTypeName",Hibernate.STRING)
	        			             .addScalar("statusName",Hibernate.STRING)
	        					     .addScalar("beginDate",Hibernate.DATE)
	        					     .addScalar("endDate",Hibernate.DATE)
	        					     .addScalar("login",Hibernate.STRING)
	        					     .addScalar("log",Hibernate.STRING)
	        					     .setResultTransformer(Transformers.aliasToBean(ScheduleReportDTO.class));
 	        		            
	            query.setParameter("countryId", filter.getCountryId(), Hibernate.LONG);
	            query.setParameter("periodTypeId", periodTypeId,Hibernate.LONG);
	            query.setParameter("reportTypeId", filter.getTypeId(), Hibernate.LONG);
	            query.setParameter("statusId", filter.getStatusId(), Hibernate.LONG);
	            
	            returnValue= (List<ScheduleReportDTO>)query.list();
	            return returnValue;
	        }catch(Throwable ex){
	        	log.error("== Error consultando en el metodo getScheduleReportByCountryId==");
	            throw this.manageException(ex);
	        }
	        finally{
	        	log.debug("== Termina getScheduleReportByCountryId/ScheduleReportDAO ==");
	        }
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getScheduleReportsQuantityByCountryId(Long countryId, Long periodTypeId,Date dateNow)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReportsNumberByCountry/ScheduleReportDAO ==");
		Session session = super.getSession();
		//dateNow = UtilsBusiness.fechaActual();
		try{
			if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
			StringBuffer queryBuffer = new StringBuffer();
			queryBuffer.append(" select count(*) ");
			queryBuffer.append(" from "+ScheduleReport.class.getName()+" sr ");
			queryBuffer.append(" where sr.country.id=:countryId");
			queryBuffer.append(" and sr.periodType.id=:periodTypeId");
			queryBuffer.append(" and trunc(sr.creationDate) = trunc(:creationDate)") ;
			Query query = session.createQuery(queryBuffer.toString());
            query.setLong("countryId", countryId);
            query.setLong("periodTypeId", periodTypeId);
            query.setDate("creationDate", dateNow);
            
    		return (Long) query.uniqueResult();

		}catch(Throwable ex){
			log.error("== Error consultando en el metodo getReportsNumberByCountry==");
            throw this.manageException(ex);
		}
		finally{
			log.debug("== Termina getReportsNumberByCountry/ScheduleReportDAO ==");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ScheduleReportTypeVO getScheduleReportTypeByCode(String code) throws DAOServiceException, DAOSQLException{
	    	ScheduleReportTypeVO returnValue=null;
	        log.debug("== Inicio getScheduleReportTypeId/ScheduleReportDAO ==");
	        Session session = super.getSession();
	        try {
	            if (session == null) {
	                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	            }
	            StringBuffer queryBuffer = new StringBuffer();
	            queryBuffer.append(" select new "+ScheduleReportTypeVO.class.getName()+"(srt) from "+ScheduleReportType.class.getName()+" srt ");
	            queryBuffer.append(" where srt.code=:code ");
	            Query query = session.createQuery(queryBuffer.toString());
	            query.setString("code", code);
	            returnValue= (ScheduleReportTypeVO)query.uniqueResult();
	        	return returnValue;
	        }catch (Throwable ex) {
	            log.error("== Error consultando en el metodo getScheduleReportTypeId==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getScheduleReportTypeId/ScheduleReportDAO ==");
	        }
	    }
	
	@Override
	public void createScheduleReportParameter(ScheduleReportParameter srp) throws DAOServiceException, DAOSQLException {
	     log.debug("== Inicio createScheduleReportParameter/ScheduleReportDAO ==");
	        Session session = super.getSession();
	        try {
	        	session.save(srp);
	            this.doFlush(session);
	            session.refresh(srp);
	        } catch (Throwable ex) {
	            log.error("== Error creando el createScheduleReportParameter ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina createScheduleReportParameter/ScheduleReportDAO ==");
	        }
	    }

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ScheduleReportParameter> getScheduleReportParamByScheduleReportId(Long id) throws DAOServiceException, DAOSQLException{
		List<ScheduleReportParameter> returnValue = null;
        log.debug("== Inicio getScheduleReportParamByScheduleReportId/ScheduleReportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from "+ScheduleReportParameter.class.getName()+" srp where srp.scheduleReport.id=:id ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            returnValue = query.list();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getScheduleReportParamByScheduleReportId==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportParamByScheduleReportId/ScheduleReportDAO ==");
        }
    }
}
