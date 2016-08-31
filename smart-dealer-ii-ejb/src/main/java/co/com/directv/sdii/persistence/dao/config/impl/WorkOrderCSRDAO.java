package co.com.directv.sdii.persistence.dao.config.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

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
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.WorkorderCSRStatus;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkOrderCSR
 * 
 * Fecha de Creacion: Mar 09, 2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkOrderCSR
 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal
 */
@Stateless(name="WorkOrderCSRLocal",mappedName="ejb/WorkOrderCSRDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderCSRDAO extends BaseDao implements WorkOrderCSRDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderCSRDAO.class);
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

    /**
     * Crea una WorkOrderCSR en el sistema
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkOrderCSR(WorkOrderCSR obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createWorkOrderCSR/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();
        obj.setDateLastChange(getDateLastChangeOfUser(obj.getCountry().getId()));
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error creando WorkOrderCSR ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderCSR/WorkOrderCSRDAO ==");
        }
    }
    
    private Date getDateLastChangeOfUser(Long countryId){
    	return UtilsBusiness.getDateLastChangeOfUser( countryId, userDao,systemParameterDAO);
    }
    
    /**
     * Obtiene un workorderCSR con el id especificado
     * @param id - Long
     * @return - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderCSR getWorkOrderCSRByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderCSRByID/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select workordercsr from ");
            stringQuery.append("WorkOrderCSR workordercsr ");
            stringQuery.append("where ");
            stringQuery.append("workordercsr.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();

            if (obj != null) {
                return (WorkOrderCSR) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderCSR por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderCSRByID/WorkOrderCSRDAO ==");
        }
    }
    
    /**
     * Obtiene un workorderCSR con el woCode especificado
     * @param id - Long
     * @return - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderCSR getWorkOrderCSRByWoCodePending(String woCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderCSRByWoCodePending/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select workordercsr ");
            stringQuery.append("   from " + WorkOrderCSR.class.getName() + " workordercsr ");
            stringQuery.append("  where workordercsr.woCode = :woCode ");
            stringQuery.append("        and workordercsr.workOrderCSRStatus.code = :workorderCSRStatusCode ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("woCode",woCode);
            query.setString("workorderCSRStatusCode",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_PENDIENTE.getCodeEntity());

            return (WorkOrderCSR) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderCSR por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderCSRByWoCodePending/WorkOrderCSRDAO ==");
        }
    }
    
    /**
     * Obtiene un workorderCSR con el woCode especificado
     * @param id - Long
     * @return - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderCSR getWorkOrderCSRByWoCode(String woCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderCSRByWoCode/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select workordercsr ");
            stringQuery.append("   from " + WorkOrderCSR.class.getName() + " workordercsr ");
            stringQuery.append("  where workordercsr.woCode = :woCode and rownum=1 and workordercsr.workOrderCSRStatus.code =:aWorkOrderCSRStatusCodeAgendada ");
            
            Query query = session.createQuery(stringQuery.toString());
            query.setString("aWorkOrderCSRStatusCodeAgendada",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_AGENDADA.getCodeEntity());
            query.setString("woCode",woCode);

            return (WorkOrderCSR) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderCSR por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderCSRByWoCode/WorkOrderCSRDAO ==");
        }
    }
    
    /**
     * Obtiene un workorderCSR con el woCode especificado
     * @param id - Long
     * @return - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderCSR getWorkOrderCSRByWoCode(String woCode,String workorderCSRStatusCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderCSRByWoCode/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select workordercsr ");
            stringQuery.append("   from " + WorkOrderCSR.class.getName() + " workordercsr ");
            stringQuery.append("  where workordercsr.woCode = :woCode ");
            stringQuery.append("        and workordercsr.workOrderCSRStatus.code = :workorderCSRStatusCode ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("woCode",woCode);
            query.setString("workorderCSRStatusCode",workorderCSRStatusCode);

            return (WorkOrderCSR) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderCSR por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderCSRByWoCode/WorkOrderCSRDAO ==");
        }
    }
    
    /**
     * Actualiza un workorderCSR especificado
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkOrderCSR(WorkOrderCSR obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkOrderCSR/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            obj.setDateLastChange(getDateLastChangeOfUser(obj.getCountry().getId()));
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WorkOrderCSR ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateWorkOrderCSR/WorkOrderCSRDAO ==");
        }

    }

    /**
     * Elimina un workorderCSR del sistema
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkOrderCSR(WorkOrderCSR obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkOrderCSR/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando WorkOrderCSR  ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteWorkOrderCSR/WorkOrderCSRDAO ==");
        }

    }

    /**
     * Obtiene todos los workorderCSR del sistema
     * @return - List<WorkOrderCSR>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderCSR> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkOrderCSR");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkOrderCSR ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/WorkOrderCSRDAO ==");
        }
    }
    
    /**
     * Actualiza un workorderCSR especificado
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void traceAssignmentWorkOrderCSR(Long id,String trace) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkOrderCSR/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" update " + WorkOrderCSR.class.getName() + " workordercsr set ");
            stringQuery.append(" descriptionError = :aTrace  ");
            stringQuery.append("  where workordercsr.id = :aId ");
            stringQuery.append("        and workordercsr.workOrderCSRStatus.id = :aWorkorderCSRStatusId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWorkorderCSRStatusId",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_PENDIENTE.getIdEntity(WorkorderCSRStatus.class.getName()));
            query.setString("aTrace",trace);
            query.setLong("aId",id);
            query.executeUpdate();

        } catch (Throwable ex) {
            log.debug("== Error actualizando WorkOrderCSR ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateWorkOrderCSR/WorkOrderCSRDAO ==");
        }

    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal#getCountWorkOrderCSRByWoCodeStatusReSchedule(java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean getCountWorkOrderCSRByWoCodeStatusReSchedule(String woCode,String actualStatusCode) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getCountWorkOrderCSRByWoCodeStatusReSchedule/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();
        StringBuffer stringQuery = new StringBuffer();
        
        boolean isSchedule = false;

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            if(actualStatusCode.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity())){
	            stringQuery.append(" select count(distinct entity.woCode) ");
	            stringQuery.append(" from WorkOrderCSR entity ");
	            stringQuery.append(" where entity.woCode = :aWoCode ");
	            stringQuery.append("       and entity.reSchedule=:aNotReSchedule "); 
	            stringQuery.append("       and (entity.workOrderCSRStatus.code=:aWorkOrderCSRStatusCodeAgendada "); 
	            stringQuery.append("            or entity.workOrderCSRStatus.code=:aWorkOrderCSRStatusCodeTerminada) ");
	            isSchedule = true;
            }else if(actualStatusCode.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity())){
            	stringQuery.append(" select count(distinct entity.woCode) ");
	            stringQuery.append(" from WorkOrderCSR entity ");
	            stringQuery.append(" where entity.woCode = :aWoCode ");
	            stringQuery.append("       and entity.reSchedule=:aReSchedule "); 
	            stringQuery.append("       and entity.workOrderCSRStatus.code=:aWorkOrderCSRStatusCodeTerminada ");
            }else{
            	return false;
            }
            
            Query query = session.createQuery(stringQuery.toString());
            
            if(isSchedule){
	            query.setString("aWoCode",woCode);
	            query.setString("aNotReSchedule",CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
	            query.setString("aWorkOrderCSRStatusCodeAgendada",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_AGENDADA.getCodeEntity());
	            query.setString("aWorkOrderCSRStatusCodeTerminada",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_TERMINADA.getCodeEntity());
            }else{
	            query.setString("aWoCode",woCode);
	            query.setString("aReSchedule",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
	            query.setString("aWorkOrderCSRStatusCodeTerminada",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_TERMINADA.getCodeEntity());
            }
            
            Long cantidadWorkOrderCSR = (Long) query.uniqueResult();
            if(cantidadWorkOrderCSR.longValue()>0)
            	return true;
            return false;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando getCountWorkOrderCSRByWoCodeStatusReSchedule ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCountWorkOrderCSRByWoCodeStatusReSchedule/WorkOrderCSRDAO ==");
        }

    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal#getCountWorkOrderCSRByWoCodeStatusReSchedule(java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderCSR getWorkOrderCSRByWoCodeStatusReSchedule(String woCode,String actualStatusCode) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getCountWorkOrderCSRByWoCodeStatusReSchedule/WorkOrderCSRDAO ==");
        Session session = ConnectionFactory.getSession();
        StringBuffer stringQuery = new StringBuffer();
        
        boolean isSchedule = false;

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            if(actualStatusCode.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity())){
            	stringQuery.append(" select workOrderCSR ");
	            stringQuery.append("   from " + WorkOrderCSR.class.getName() + " workOrderCSR ");
	            stringQuery.append("  where workOrderCSR.id = (select max(entity.id) ");
	            stringQuery.append("                         from " + WorkOrderCSR.class.getName() + " entity ");
	            stringQuery.append("                        where entity.woCode = :aWoCode ");
	            stringQuery.append("                          and entity.reSchedule=:aNotReSchedule "); 
	            stringQuery.append("                          and (entity.workOrderCSRStatus.code=:aWorkOrderCSRStatusCodeAgendada "); 
	            stringQuery.append("                               or entity.workOrderCSRStatus.code=:aWorkOrderCSRStatusCodeTerminada)) ");
	            isSchedule = true;
            }else if(actualStatusCode.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity())){
            	stringQuery.append(" select workOrderCSR ");
	            stringQuery.append("   from " + WorkOrderCSR.class.getName() + " workOrderCSR ");
	            stringQuery.append("  where workOrderCSR.id = (select max(entity.id) ");
	            stringQuery.append("                             from WorkOrderCSR entity ");
	            stringQuery.append("                            where entity.woCode = :aWoCode ");
	            stringQuery.append("                              and entity.reSchedule=:aReSchedule "); 
	            stringQuery.append("                              and entity.workOrderCSRStatus.code=:aWorkOrderCSRStatusCodeTerminada) ");
            }else{
            	return null;
            }
            
            Query query = session.createQuery(stringQuery.toString());
            
            if(isSchedule){
	            query.setString("aWoCode",woCode);
	            query.setString("aNotReSchedule",CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
	            query.setString("aWorkOrderCSRStatusCodeAgendada",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_AGENDADA.getCodeEntity());
	            query.setString("aWorkOrderCSRStatusCodeTerminada",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_TERMINADA.getCodeEntity());
            }else{
	            query.setString("aWoCode",woCode);
	            query.setString("aReSchedule",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
	            query.setString("aWorkOrderCSRStatusCodeTerminada",CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_TERMINADA.getCodeEntity());
            }
            
            WorkOrderCSR workOrderCSR = (WorkOrderCSR) query.uniqueResult();
            
            return workOrderCSR;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando getCountWorkOrderCSRByWoCodeStatusReSchedule ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCountWorkOrderCSRByWoCodeStatusReSchedule/WorkOrderCSRDAO ==");
        }

    }

    //REQ002 - WO Agendadas en linea.
    @SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(ReportsSucceedWorkOrderFilterDTO filter, RequestCollectionInfo requestInfo) throws DAOServiceException, DAOSQLException {

        try {
        	log.debug("== Inicia getReportsSucceedWorkOrderCSR/WorkOrderCSRDAO ==");
        	Session session = super.getSession();
        	StringBuilder querySelect = new StringBuilder();
        	StringBuilder queryFrom   = new StringBuilder();
        	StringBuilder queryWhere  = new StringBuilder();
        	StringBuilder queryUnion  = new StringBuilder();
        	StringBuilder querySelect2 = new StringBuilder();
        	StringBuilder queryFrom2   = new StringBuilder();
        	StringBuilder queryWhere2  = new StringBuilder();
        	StringBuilder queryOrder  = new StringBuilder();
           	
        	querySelect.append(" SELECT wo.wo_code woCode, ");
            querySelect.append("        null dateLastChange, ");
            querySelect.append("        null scheduleDate, ");
            querySelect.append("        us.name userName, ");
            querySelect.append("        us.login login, ");
            querySelect.append("        pc.postal_code_code postalCode, ");
            querySelect.append("        pc.postal_code_name postalCodeName, ");
            querySelect.append("        d.dealer_name dealerName, ");
            querySelect.append("        d.dealer_code dealerCode, ");
            querySelect.append("        s.service_code||s.service_name service, ");
            querySelect.append("        'WO Susceptible' isScheduled ");
			queryFrom.append("   FROM   work_orders wo ");
			queryFrom.append("          INNER JOIN users us ON wo.user_id = us.id ");
			queryFrom.append("          INNER JOIN postal_codes pc ON wo.postal_code_id = pc.id ");
			queryFrom.append("          INNER JOIN dealers d ON wo.dealer_id = d.id ");
			queryFrom.append("          INNER JOIN work_order_services wos ON wos.wo_id = wo.id ");
			queryFrom.append("          INNER JOIN services s ON s.id = wos.service_id ");
			queryFrom.append("          INNER JOIN dealer_coverages dc ON (dc.dealer_id=wo.dealer_id and dc.postal_code_id=wo.postal_code_id) ");
			queryWhere.append(" WHERE   dc.coverage_type_id = (select id from coverage_types where code=:coverageCode)  ");
			queryWhere.append("         AND wo.wo_code not in (select wo_code from csr_work_orders where schedule_date is not null) ");
			queryWhere.append("         AND wo.import_date BETWEEN TO_DATE(:fromDate, 'yyyy-mm-dd hh24:mi:ss') ");
			queryWhere.append("         AND TO_DATE(:toDate, 'yyyy-mm-dd hh24:mi:ss')");
			
			queryUnion.append(" UNION ALL ");
        	
            querySelect2.append(" SELECT wc.wo_code woCode, ");
            querySelect2.append("        wc.date_last_change dateLastChange, ");
            querySelect2.append("        wc.schedule_date scheduleDate, ");
            querySelect2.append("        us.name userName, ");
            querySelect2.append("        us.login login, ");
            querySelect2.append("        pc.postal_code_code postalCode, ");
            querySelect2.append("        pc.postal_code_name postalCodeName, ");
            querySelect2.append("        d.dealer_name dealerName, ");
            querySelect2.append("        d.dealer_code dealerCode, ");
            querySelect2.append("        s.service_code||s.service_name service, ");
            querySelect2.append("        'WO Agendada' isScheduled ");
			queryFrom2.append(" FROM csr_work_orders wc ");
			queryFrom2.append(" INNER JOIN users us ON wc.user_id = us.id ");
			queryFrom2.append(" INNER JOIN work_orders wo ON wc.wo_code = wo.wo_code ");
			queryFrom2.append(" INNER JOIN postal_codes pc ON wo.postal_code_id = pc.id ");
			queryFrom2.append(" INNER JOIN dealers d ON wo.dealer_id = d.id ");
			queryFrom2.append(" INNER JOIN work_order_services wos ON wos.wo_id = wo.id ");
			queryFrom2.append(" INNER JOIN services s ON s.id = wos.service_id ");
			queryWhere2.append(" WHERE wc.schedule_date IS NOT null ");
			queryWhere2.append(" AND wc.date_last_change BETWEEN TO_DATE(:fromDate, 'yyyy-mm-dd hh24:mi:ss') ");
			queryWhere2.append(" AND TO_DATE(:toDate, 'yyyy-mm-dd hh24:mi:ss')");
			queryOrder.append(" ORDER BY 2 DESC");
            
			Query query = session.createSQLQuery(querySelect.toString() + queryFrom.toString() + queryWhere.toString() + queryUnion.toString() + querySelect2.toString() + queryFrom2.toString() + queryWhere2.toString() + queryOrder.toString() )
					.addScalar("woCode",Hibernate.STRING)
					.addScalar("dateLastChange",Hibernate.TIMESTAMP)
					.addScalar("scheduleDate",Hibernate.TIMESTAMP)
					.addScalar("userName",Hibernate.STRING)
					.addScalar("login",Hibernate.STRING)
					.addScalar("postalCode",Hibernate.STRING)
					.addScalar("postalCodeName",Hibernate.STRING)
					.addScalar("dealerName",Hibernate.STRING)
					.addScalar("dealerCode",Hibernate.STRING)
					.addScalar("service",Hibernate.STRING)
					.addScalar("isScheduled",Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(ReportsSucceedWorkOrderCSRDTO.class));			
			          
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			query.setString("fromDate", dateFormat.format( filter.getFromDate().getTime() ));
			query.setString("toDate", dateFormat.format( filter.getToDate().getTime() ));
			query.setString("coverageCode",CodesBusinessEntityEnum.COVERAGE_TYPE_PERMANENT.getCodeEntity());
			
        	Long recordQty = 0l;
        	if( requestInfo != null ){
          		Query countQuery = session.createSQLQuery(" select sum(cnt) as count_final from (" +" select count(*) as cnt  "+ queryFrom.toString() + queryWhere.toString()+queryUnion.toString() + "select count (*) as cnt" + queryFrom2.toString() + queryWhere2.toString()+" )");
        		countQuery.setString("fromDate", dateFormat.format( filter.getFromDate().getTime() ));
    			countQuery.setString("toDate", dateFormat.format( filter.getToDate().getTime() ));
    			countQuery.setString("coverageCode",CodesBusinessEntityEnum.COVERAGE_TYPE_PERMANENT.getCodeEntity());
        		recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestInfo.getFirstResult() );
				query.setMaxResults( requestInfo.getMaxResults() );			
        	}
            ReportsSucceedWorkOrderCSRAndFileResponseDTO response = new ReportsSucceedWorkOrderCSRAndFileResponseDTO();
        	List<ReportsSucceedWorkOrderCSRDTO> responseList = query.list();
        	    	
        	if( requestInfo != null )
				populatePaginationInfo(response, requestInfo.getPageSize(), requestInfo.getPageIndex(), responseList.size(), recordQty.intValue() );
        	
        	response.setReportsSucceedWorkOrderCSRDTOList(responseList);
        	return response;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando getReportsSucceedWorkOrderCSR ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getReportsSucceedWorkOrderCSR/WorkOrderCSRDAO ==");
        }		
		
	}
    
}
