package co.com.directv.sdii.persistence.dao.config.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.DateCodesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ReportWorkOrderCrewAttentionDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrdersLastDayDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.ProcessStatus;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.ShippingOrderElement;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WoCrewAssigments;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WoType;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ServiceResponse;
import co.com.directv.sdii.model.pojo.collection.WOActiveAndSuspendByCountryIdPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByCustomerQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerDateCrewQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerWorkOrderQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderResponse;
import co.com.directv.sdii.model.vo.TechnologyVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;
import co.com.directv.sdii.reports.dto.ReportWorkOrderDTO;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkOrder
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkOrder
 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal
 */
@Stateless(name="WorkOrderDAOLocal",mappedName="ejb/WorkOrderDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderDAO extends BaseDao implements WorkOrderDAOLocal {

	
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderDAO.class);

    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

	@EJB
	private TechnologyDAOLocal technologyDAO;
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<ReportWorkOrdersLastDayDTO> getWorkOrdersForLastDay(Long countryId) throws DAOServiceException, DAOSQLException {
    	List<ReportWorkOrdersLastDayDTO> returnValue=null;
        log.debug("== Inicio getWorkOrdersForLastDay/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            /*
             * 	public ReportWorkOrdersLastDayDTO(String customerCode, String woCode,
			Date statusDate, String employeeFistName, String employeeLastName,
			String dealerCode, String dealerDepodCode, String dealerName) {
             * */
            queryBuffer.append(" select new co.com.directv.sdii.model.dto.ReportWorkOrdersLastDayDTO ( ");
            queryBuffer.append("        wsh1.workOrder.customer.customerCode, ");
            queryBuffer.append("        wsh1.workOrder.woCode,wsh1.statusDate, ");
            queryBuffer.append("        (select ec.employee ");
            queryBuffer.append("           from "+EmployeeCrew.class.getName()+" ec ");
            queryBuffer.append("          where ec.isResponsible = :isResponsable ");
            queryBuffer.append("                and ec.crew.id = (select wca1.crewId.id ");
            queryBuffer.append("                                    from "+WoCrewAssigments.class.getName()+" wca1  ");
            queryBuffer.append("                                   where wca1.id = ( select max(wca.id)  ");
            queryBuffer.append("                                                       from "+WoCrewAssigments.class.getName()+" wca  ");
            queryBuffer.append("                                                      group by wca.woId.id  ");
            queryBuffer.append("                                                     having wca.woId.id = wsh1.workOrder.id))), ");
            queryBuffer.append("        (select d  ");
            queryBuffer.append("           from "+Dealer.class.getName()+" d  ");
            queryBuffer.append("          where d.id = (select wo.dealerId  ");
            queryBuffer.append("                          from "+WorkOrder.class.getName()+" wo  ");
            queryBuffer.append("                         where wo.id = wsh1.workOrder.id)) )  ");
            queryBuffer.append("   from "+WoStatusHistory.class.getName()+" wsh1  ");
            queryBuffer.append("  where wsh1.id = (select max(wsh.id)  ");
            queryBuffer.append("                     from "+WoStatusHistory.class.getName()+" wsh  ");
            queryBuffer.append("                    where TRUNC(wsh.statusDate) = TRUNC(:date)  ");
            queryBuffer.append("                          and wsh.workOrder.id=wsh1.workOrder.id  ");
            queryBuffer.append("                          and ( wsh.workorderStatus.woStateCode=:stateCode  ");
            queryBuffer.append("                                or wsh.workorderStatus.woStateCode=:stateCode2)) ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("isResponsable", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO).getTime());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            calendar.setTimeInMillis(calendar.getTimeInMillis());
            query.setTimestamp("date", new java.sql.Timestamp(calendar.getTimeInMillis()));
            query.setString("stateCode", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            query.setString("stateCode2", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
            returnValue= (List<ReportWorkOrdersLastDayDTO>)query.list();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getWorkOrdersForLastDay==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrdersForLastDay/WorkOrderDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#createWorkOrder(co.com.directv.sdii.model.pojo.WorkOrder)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkOrder(WorkOrder obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkOrder/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando WorkOrder ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrder/WorkOrderDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrder getWorkOrderByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderByID/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" workorder where workorder.id = :id");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (WorkOrder) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByID/WorkOrderDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String getCodeWorkOrderByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCodeWorkOrderByID/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select workorder.woCode ");
            queryBuffer.append(" from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" workorder where workorder.id = :id");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (String) obj;
            }
            return "";
        }catch (Throwable ex) {
            log.error("== Error consultando codeWorkOrder por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderByID/WorkOrderDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#updateWorkOrder(co.com.directv.sdii.model.pojo.WorkOrder)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Object updateWorkOrder(WorkOrder obj,String originUpdateEvent) throws DAOServiceException, DAOSQLException {
        
    	if(log.isDebugEnabled()) log.debug("== Inicia updateWorkOrder/WorkOrderDAO ==" + obj.getId() + " " + originUpdateEvent);
        
    	Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Object respuesta =  session.merge(obj);
            this.doFlush(session);
            return respuesta;
        } catch (Throwable ex) {
            log.error("== Error actualizando WorkOrder ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkOrder/WorkOrderDAO ==");
        }

    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#deleteWorkOrder(co.com.directv.sdii.model.pojo.WorkOrder)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkOrder(WorkOrder obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkOrder/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error eliminando WorkOrder ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteWorkOrder/WorkOrderDAO ==");
        }

    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getAll()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrder> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkOrder");
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando todas las WorkOrder ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/WorkOrderDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualStatus(co.com.directv.sdii.model.pojo.WorkorderStatus)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByActualStatus(
			WorkorderStatus actualStatus) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getWorkOrderByActualStatus/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.workorderStatusByActualStatusId.id = ?");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.workorderStatusByActualStatusId.id = ?");
            query.setLong(0, actualStatus.getId());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID por WoStatus actual ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByActualStatus/WorkOrderDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualStatusAndCountryId(co.com.directv.sdii.model.pojo.WorkorderStatus, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByActualStatusAndCountryId(
			WorkorderStatus actualStatus, Long countryId) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getWorkOrderByActualStatusAndCountryId/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.workorderStatusByActualStatusId.id = ? and wo.country.id = ?");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.workorderStatusByActualStatusId.id = ?");
            query.setLong(0, actualStatus.getId());
            query.setLong(1, countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID por WoStatus actual y country ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByActualStatusAndCountryId/WorkOrderDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualStatusCodeAndCountryId(java.lang.Long, int, int, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo, java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrderByActualStatusCodeAndCountryId(Long countryId, RequestCollectionInfo requestCollectionInfo, String... actualStatusCodes) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getWorkOrderByActualStatusCodeAndCountryId/WorkOrderDAO ==");
        WOActiveAndSuspendByCountryIdPaginationResponse r = new WOActiveAndSuspendByCountryIdPaginationResponse();
        Session session = super.getSession();
        int statusCodesLength = actualStatusCodes.length;
        Long totalRowCount = 0L;
        int firstResult = 0;
        int maxResult = 0;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringCountQuery = new StringBuffer("select count(*) ");
            StringBuffer stringQuery = new StringBuffer("from WorkOrder wo ");
            stringQuery.append("where wo.country.id = ? and (");
            for(int i = 0 ; i < statusCodesLength ; i++){
            	stringQuery.append(" wo.workorderStatusByActualStatusId.woStateCode = ? or");
            }
            String finalQuery = StringUtils.removeEnd(stringQuery.toString(), "or");
            stringQuery = new StringBuffer(finalQuery);
            stringQuery.append(" )");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong(0, countryId);
            for(int i = 0 ; i < statusCodesLength ; i++){
            	query.setString(i + 1, actualStatusCodes[i]);
            }
            stringCountQuery.append(stringQuery.toString());
            Query countQuery = session.createQuery(stringCountQuery.toString());
            
            // Paginación
            if (requestCollectionInfo != null){
            	countQuery.setLong(0, countryId); 
                for(int i = 0 ; i < statusCodesLength ; i++){
                	countQuery.setString(i + 1, actualStatusCodes[i]);
                }

            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
            }
            r.setCollection(query.list());
            if (requestCollectionInfo != null){
            	populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }
            
            return r;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID por WoStatus actual y country ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByActualStatusCodeAndCountryId/WorkOrderDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualStatus(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.WorkorderStatus)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByActualStatus(Dealer dealer,
			WorkorderStatus actualStatus) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getWorkOrderByActualStatus/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.workorderStatusByActualStatusId.id = :actStatus and ");
            stringQuery.append("wo.dealerId = :dealer");
            //Query query = session.createQuery("from WorkOrder wo where wo.workorderStatusByActualStatusId.id = ?");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("actStatus", actualStatus.getId());
            query.setLong("dealer", actualStatus.getId());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por WOStatusActual y Dealer ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByActualStatus/WorkOrderDAO ==");
        }
	}
	
    /**
     * Obtiene todos los workorders por dealer y que se encuentren en los siguientes estados:
     * WORKORDER_STATUS_SCHEDULED, WORKORDER_STATUS_RESCHEDULED, WORKORDER_STATUS_REASSIGN, WORKORDER_STATUS_ASSIGN y WORKORDER_STATUS_RALIZED.
     * 
     * Nota: Esta consulta es utilizada para visualizar la bandeja de WorkOrders que tiene el dealer.
     * 
     * 
     * @return WOByDealerPaginationResponse
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByDealerPaginationResponse getWorkOrderByDealer(Dealer dealer, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getWorkOrderByDealer/WorkOrderDAO ==");
        Session session = super.getSession();
        WOByDealerPaginationResponse r = new WOByDealerPaginationResponse();
        int firstResult = 0;
        int maxResult = 0;
        Long totalRowCount = 0L;

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            StringBuffer countQueryBuffer = new StringBuffer("select count(*) ");
            queryBuffer.append(" from ");
            queryBuffer.append(WoAssignment.class.getName());
            queryBuffer.append(" woa where woa.dealerId = ?");
            queryBuffer.append(" and woa.workOrder.workorderStatusByActualStatusId.woStateCode in (?,?,?,?,?,?) ");
            queryBuffer.append(" and woa.isActive = :isActive ");
            
            countQueryBuffer.append(queryBuffer.toString());       
            
            Query query = null;
            Query countQuery = null;
            
            if(queryBuffer.toString() != null && !queryBuffer.toString().trim().equals("")){
            	query = session.createQuery(queryBuffer.toString());
            	countQuery = session.createQuery(countQueryBuffer.toString());
            }else
            	throw new DAOServiceException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            
            query.setLong(0, dealer.getId());
            query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            query.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());

            // Paginación
            countQuery.setLong(0, dealer.getId());
            countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            countQuery.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            countQuery.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            // Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
                r.setCollection(query.list());
                populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }
            return r;
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByDealer/WorkOrderDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerAndCode(co.com.directv.sdii.model.pojo.Dealer, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrder getWorkOrderByDealerAndCode(Dealer dealer, String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerAndCode/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = ? and wo.woCode=? ");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.dealerId = ? and wo.woCode=?");
            query.setLong(1, dealer.getId());
            query.setString(2, code);
            return (WorkOrder) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer y código de WO ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByDealerAndCode/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByPreviusStatus(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.WorkorderStatus)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByPreviusStatus(Dealer dealer,
			WorkorderStatus workOrderStatus) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWorkOrderByPreviusStatus/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = ? and wo.workorderStatusByActualStatusId.id=? ");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.dealerId = ? and wo.workorderStatusByActualStatusId.id=?");
            query.setLong(1, dealer.getId());
            query.setLong(2, workOrderStatus.getId());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por WOStatusAnterior ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByPreviusStatus/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByPreviusType(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.WoType)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByPreviusType(Dealer dealer,
			WoType woType) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByPreviusType/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = ? and wo.woTypeByPreviusWoTypeId.id=? ");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.dealerId = ? and wo.woTypeByPreviusWoTypeId.id=?");
            query.setLong(1, dealer.getId());
            query.setLong(2, woType.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error cconsultando WorkOrder por Tipo de WO y Dealer ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByPreviusType/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByPreviusType(co.com.directv.sdii.model.pojo.WoType)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByPreviusType(WoType previusType) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByPreviusType/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.woTypeByPreviusWoTypeId.id=:prevType ");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.woTypeByPreviusWoTypeId.id=:prevType");
            query.setLong("prevType", previusType.getId());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Tipo de WO Anterior ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByPreviusType/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerPostalCodeProgrammingDate(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.PostalCode, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByDealerPostalCodeProgrammingDate(
			Dealer dealer, PostalCode postalCode, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerPostalCodeProgrammingDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = ? and wo.postalCode.id=? and ");
            stringQuery.append("WorkOrder.woProgrammingDate >= ? and WorkOrder.woProgrammingDate <= ? ");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where wo.dealerId = ? and wo.postalCode.id=? and " +
            		"WorkOrder.woProgrammingDate >= ? and WorkOrder.woProgrammingDate <= ?");*/
            query.setLong(1, dealer.getId());
            query.setLong(2, postalCode.getId());
            query.setDate(3, initDate);
            query.setDate(4, endDate);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer, Código postal, y rango de fechas de programación ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByDealerPostalCodeProgrammingDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerPostalCodeRealizationDate(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.PostalCode, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByDealerPostalCodeRealizationDate(
			Dealer dealer, PostalCode postalCode, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerPostalCodeRealizationDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = ? and wo.postalCode.id=? and ");
            stringQuery.append("wo.woRealizationDate >= ? and wo.woRealizationDate <= ?");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where wo.dealerId = ? and wo.postalCode.id=? and " +
            		"wo.woRealizationDate >= ? and wo.woRealizationDate <= ?");*/
            query.setLong(1, dealer.getId());
            query.setLong(2, postalCode.getId());
            query.setDate(3, initDate);
            query.setDate(4, endDate);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer, Código postal y rango de fechas de realización ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByDealerPostalCodeRealizationDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCustomerAndCreationDate(co.com.directv.sdii.model.pojo.Customer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByCustomerAndCreationDate(
			Customer customer, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCustomerAndCreationDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.customer.id = ? and ");
            stringQuery.append("wo.creationDate >= ? and wo.creationDate <= ?");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where wo.customer.id = ? and " +
            		"wo.creationDate >= ? and wo.creationDate <= ?");*/
            query.setLong(1, customer.getId());
            query.setDate(2, initDate);
            query.setDate(3, endDate);
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Cliente y rango de fechas de creación ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByCustomerAndCreationDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCustomerAndProgrammingDate(co.com.directv.sdii.model.pojo.Customer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByCustomerAndProgrammingDate(
			Customer customer, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCustomerAndProgrammingDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.customer.id = ? and ");
            stringQuery.append("wo.woProgrammingDate >= ? and wo.woProgrammingDate <= ? ");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where wo.customer.id = ? and " +
            		"wo.woProgrammingDate >= ? and wo.woProgrammingDate <= ?");*/
            query.setLong(1, customer.getId());
            query.setDate(2, initDate);
            query.setDate(3, endDate);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Cliente y rango de fechas de programación ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCustomerAndProgrammingDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualType(co.com.directv.sdii.model.pojo.WoType)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByActualType(WoType woType)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByActualType/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.woTypeByWoTypeId.id = ? ");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.woTypeByWoTypeId.id = ?");
            query.setLong(1, woType.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por tipo de WO ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByActualType/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualType(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.WoType)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByActualType(Dealer dealer, WoType woType)
			throws DAOServiceException, DAOSQLException  {
		log.debug("== Inicia getWorkOrderByActualType/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.woTypeByWoTypeId.id = :woType and ");
            stringQuery.append("wo.saleDealer.id = :dealer ");
            //Query query = session.createQuery("from WorkOrder wo where wo.woTypeByPreviusWoTypeId.id = ? ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("woType", woType.getId());
            query.setLong("dealer", dealer.getId());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer y Tipo Actual de la WO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByActualType/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerAndProgrammingDate(co.com.directv.sdii.model.pojo.Dealer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByDealerAndProgrammingDate(
			Dealer dealer, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerAndProgrammingDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = ? and  ");
            stringQuery.append("wo.woProgrammingDate >= ? and wo.woProgrammingDate <= ?  ");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where wo.dealerId = ? and " +
            		"wo.woProgrammingDate >= ? and wo.woProgrammingDate <= ?");*/
            query.setLong(1, dealer.getId());
            query.setDate(2, initDate);
            query.setDate(3, endDate);
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer y rango de fechas de programación ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByDealerAndProgrammingDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByProgrammingDate(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByProgrammingDate(Date initDate,
			Date endDate) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerAndProgrammingDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.woProgrammingDate >= ? and wo.woProgrammingDate <= ? ");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where " +
            		"wo.woProgrammingDate >= ? and wo.woProgrammingDate <= ?");*/
            query.setDate(1, initDate);
            query.setDate(2, endDate);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder prango de fechas de programación ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByDealerAndProgrammingDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCreationDate(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByCreationDate(Date initDate,
			Date endDate) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCreationDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.creationDate >= ? and wo.creationDate <= ? ");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where " +
            		"wo.creationDate >= ? and wo.creationDate <= ?");*/
            query.setDate(1, initDate);
            query.setDate(2, endDate);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder rango de fechas de creación==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCreationDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerPostalCodeCreationDate(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.PostalCode, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByDealerPostalCodeCreationDate(
			Dealer dealer, PostalCode postalCode, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerPostalCodeCreationDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            GregorianCalendar calInicio = new GregorianCalendar();
            calInicio.setTimeInMillis(initDate.getTime());
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            GregorianCalendar calFin = new GregorianCalendar();
            calFin.setTimeInMillis(endDate.getTime());
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.MILLISECOND, 0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = ? and ");
            stringQuery.append("wo.postalCode.id = ? and ");
            stringQuery.append("wo.creationDate >= ? and wo.creationDate <= ? ");
            Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from WorkOrder wo where wo.dealerId = ? and " +
            		"wo.postalCode.id = ? and " +
            		"wo.creationDate >= ? and wo.creationDate <= ?");*/
            query.setLong(1, dealer.getId());
            query.setLong(2, postalCode.getId());
            query.setDate(3, initDate);
            query.setDate(4, endDate);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer, Código Postal y rango de fechas de creación ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByDealerPostalCodeCreationDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByPreviusStatus(co.com.directv.sdii.model.pojo.WorkorderStatus)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrderByPreviusStatus(WorkorderStatus woStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByPreviusStatus/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.workorderStatusByPreviusStatusId.id = ? ");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WorkOrder wo where wo.workorderStatusByPreviusStatusId.id = ?");
            query.setLong(1, woStatus.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer, Código Postal y rango de fechas de creación ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByPreviusStatus/WorkOrderDAO ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrder getWorkOrderByCode(String workOrderCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCode/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(WorkOrder.class.getName());
            stringQuery.append(" wo where wo.woCode = :aWorkOrderCode");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+WorkOrder.class.getName()+" wo where wo.woCode = :aWorkOrderCode");
            query.setString("aWorkOrderCode", workOrderCode);
            WorkOrder workOrder = (WorkOrder)query.uniqueResult(); 
            return workOrder;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por código ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCode/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersByDealerCustomerQBE(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByCustomerQBEPaginationResponse getWorkOrdersByDealerCustomerQBE(Long dealerId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersByDealerCustomerQBE/WorkOrderDAO ==");
        Session session = super.getSession();
        StringBuffer queryBuffer = new StringBuffer();
        StringBuffer countQueryBuffer = new StringBuffer("select count(*) "); 
        WOByCustomerQBEPaginationResponse r = new WOByCustomerQBEPaginationResponse();
        Long totalRowCount = 0L;
        int firstResult = 0;
        int maxResult = 0;
        
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            // La consulta por dealerId e ibsCode excluye los otros criterios
            if (ibsCode != null && !ibsCode.trim().equals("")){
            	queryBuffer.append(" from ");
                queryBuffer.append(WoAssignment.class.getName());
                queryBuffer.append(" woa where woa.dealerId = ?");
            	queryBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?))");
            	queryBuffer.append(" and (woa.workOrder.customer.customerCode = ?) ");
            	queryBuffer.append(" and (woa.isActive = :isActive) ");
            } else if (idNumber != null && !idNumber.trim().equals("")){
            	queryBuffer.append(" from ");
                queryBuffer.append(WoAssignment.class.getName());
                queryBuffer.append(" woa where woa.dealerId = ?");
            	queryBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?))");
            	queryBuffer.append(" and (woa.workOrder.customer.documentNumber = ?)");
            	queryBuffer.append(" and (woa.isActive = :isActive) ");
            } else if ((name != null && !name.trim().equals("")) || (lastName != null && !lastName.trim().equals(""))){
            	if ((name != null && !name.trim().equals("")) && (lastName != null && !lastName.trim().equals(""))){
            		queryBuffer.append(" from ");
                    queryBuffer.append(WoAssignment.class.getName());
                    queryBuffer.append(" woa where woa.dealerId = ?");
                	queryBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?))");
                	queryBuffer.append(" and (woa.workOrder.customer.firstName like ? and woa.workOrder.customer.lastName like ?)");
                	queryBuffer.append(" and (woa.isActive = :isActive) ");
            	} else if ((name != null && !name.trim().equals(""))){
            		queryBuffer.append(" from ");
                    queryBuffer.append(WoAssignment.class.getName());
                    queryBuffer.append(" woa where woa.dealerId = ?");
                	queryBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?))");
                	queryBuffer.append(" and (woa.workOrder.customer.firstName like ?)");
                	queryBuffer.append(" and (woa.isActive = :isActive) ");
            	} else if ((lastName != null && !lastName.trim().equals(""))){
            		queryBuffer.append(" from ");
                    queryBuffer.append(WoAssignment.class.getName());
                    queryBuffer.append(" woa where woa.dealerId = ?");
                	queryBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?))");
                	queryBuffer.append(" and (woa.workOrder.customer.lastName like ?)");
                	queryBuffer.append(" and (woa.isActive = :isActive) ");
            	}
            } else if ((officePhone != null && !officePhone.trim().equals("")) 
            		   || (homePhone   != null && !homePhone.trim().equals("")) 
            		   || (faxPhone    != null && !faxPhone.trim().equals("")) 
            		   || (cellPhone   != null && !cellPhone.trim().equals(""))){
            	queryBuffer.append("select woa from ");
                queryBuffer.append(WoAssignment.class.getName());
                queryBuffer.append(" woa ");
                queryBuffer.append(" join woa.workOrder as wo");
                queryBuffer.append(" join wo.customer as cust");
                queryBuffer.append(" join cust.customerMediaContacts as mediaContact");
                queryBuffer.append(" where woa.dealerId = ?");
            	queryBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?))");
            	queryBuffer.append(" and (mediaContact.mediaContactValue in(?,?,?,?))");
            	queryBuffer.append(" and (woa.isActive = :isActive) ");
            }
            
            countQueryBuffer.append(queryBuffer.toString());
            Query query = null;
            Query countQuery = null;
            
            if(queryBuffer.toString() != null && !queryBuffer.toString().trim().equals("")){
            	query = session.createQuery(queryBuffer.toString());
            	countQuery = session.createQuery(countQueryBuffer.toString());
            }else
            	throw new DAOServiceException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());

            query.setLong(0, dealerId);
            query.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            // Paginación
            countQuery.setLong(0, dealerId);
            countQuery.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            
            if (ibsCode != null && !ibsCode.trim().equals("")){
                query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	query.setString(6, ibsCode);
            	// Paginación
            	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	countQuery.setString(6, ibsCode);
            } else if (idNumber != null && !idNumber.trim().equals("")){
                query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	query.setString(6, idNumber);
            	// Paginación
            	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	countQuery.setString(6, idNumber);
            } else if ((name != null && !name.trim().equals("")) || (lastName != null && !lastName.trim().equals(""))){
            	if ((name != null && !name.trim().equals("")) && (lastName != null && !lastName.trim().equals(""))){
                    query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                    query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                    query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                    query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                    query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                	query.setString(6, name+"%");
                	query.setString(7, lastName+"%");
                	// Paginación
                	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                	countQuery.setString(6, name+"%");
                	countQuery.setString(7, lastName+"%");
            	} else if ((name != null && !name.trim().equals(""))){
                    query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                    query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                    query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                    query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                    query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                	query.setString(6, name+"%");
                	// Paginación
                	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                	countQuery.setString(6, name+"%");
            	} else if ((lastName != null && !lastName.trim().equals(""))){
                    query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                    query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                    query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                    query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                    query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                	query.setString(6, lastName+"%");
                	// Paginación
                	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                	countQuery.setString(6, lastName+"%");
            	}
            } else if ((officePhone != null && !officePhone.trim().equals("")) 
            		  || (homePhone   != null && !homePhone.trim().equals("")) 
            		  || (faxPhone    != null && !faxPhone.trim().equals("")) 
            		  || (cellPhone   != null && !cellPhone.trim().equals(""))){
                query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	query.setString(6, officePhone);
            	query.setString(7, homePhone);
            	query.setString(8, faxPhone);
            	query.setString(9, cellPhone);
            	// Paginación
            	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	countQuery.setString(6, officePhone);
            	countQuery.setString(7, homePhone);
            	countQuery.setString(8, faxPhone);
            	countQuery.setString(9, cellPhone);
            }
            
            // Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
                r.setCollection(query.list());
                populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }
            return r;
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por dealerId, ibsCode,officePhone, homePhone, faxPhone," 
            		 +"cellPhone, idNumber, name, lastName ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByDealerCustomerQBE/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersByDealerDateCrewQBE(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByDealerDateCrewQBEPaginationResponse getWorkOrdersByDealerDateCrewQBE(Long dealerId,
			Long dateId, Long crewId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWorkOrdersByDealerDateCrewQBE/WorkOrderDAO ==");
        Session session = super.getSession();
        StringBuffer queryBuffer = new StringBuffer();
        StringBuffer countQueryBuffer = new StringBuffer("select count(*) ");
        Calendar cal = Calendar.getInstance();
        WOByDealerDateCrewQBEPaginationResponse r = new WOByDealerDateCrewQBEPaginationResponse();
        Long totalRowCount = 0L;
        int firstResult = 0;
        int maxResult = 0;

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            queryBuffer.append(" from ");
            queryBuffer.append(WoAssignment.class.getName());
            queryBuffer.append(" woa where (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?,?))");
            if( dealerId != null && dealerId > 0L ){
            	queryBuffer.append(" and woa.dealerId = ? ");
            }
        	if( crewId != null && crewId > 0L){
        		queryBuffer.append(" and (woa.crewId = ?) ");
        	}
        	queryBuffer.append(" and (woa.isActive = :isActive) ");
        	

            if (dateId.longValue() == DateCodesEnum.WO_DE_HOY.getCodeId().longValue()){
            	cal = Calendar.getInstance();
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_DE_UN_DIA_ADELANTE.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, 1);
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_DE_DOS_DIAS_ADELANTE.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, 2);
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_DE_TRES_DIAS_ADELANTE.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, 3);
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_DE_CUATRO_DIAS_ADELANTE.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, 4);
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_DE_CINCO_DIAS_ADELANTE.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, 5);
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_DE_SEIS_DIAS_ADELANTE.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, 6);
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_DE_SIETE_DIAS_ADELANTE.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, 7);
            	queryBuffer.append(" and (woa.crewAssignmentDate = ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_HACE_MAS_DE_DOS_DIAS.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, -2);
            	queryBuffer.append(" and (woa.crewAssignmentDate <= ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_HACE_MAS_DE_SIETE_DIAS.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, -7);
            	queryBuffer.append(" and (woa.crewAssignmentDate <= ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_HACE_MAS_DE_QUINCE_DIAS.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, -15);
            	queryBuffer.append(" and (woa.crewAssignmentDate <= ?)");
            } else if (dateId.longValue() == DateCodesEnum.WO_HACE_MAS_DE_TREINTA_DIAS.getCodeId().longValue()){
            	cal.add(Calendar.DAY_OF_MONTH, -30);
            	queryBuffer.append(" and (woa.crewAssignmentDate <= ?)");
            } else { //Sin fecha de agendamiento
            	cal = null;
            }
            
            countQueryBuffer.append(queryBuffer.toString());
            Query query = null;
            Query countQuery = null;
            
            if(queryBuffer.toString() != null && !queryBuffer.toString().trim().equals("")){
            	query = session.createQuery(queryBuffer.toString());
            	countQuery = session.createQuery(countQueryBuffer.toString());
            }else
            	throw new DAOServiceException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());

            query.setString(0, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            if( dealerId != null && dealerId > 0L )
            	query.setLong(6, dealerId);
        	if( crewId != null && crewId > 0L)
        		query.setLong(7, crewId);
            query.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            // Paginación
            countQuery.setString(0, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            if( dealerId != null && dealerId > 0L )
            	countQuery.setLong(6, dealerId);
        	if( crewId != null && crewId > 0L)
        		countQuery.setLong(7, crewId);
        	countQuery.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            if (cal != null){
            	query.setDate(7, cal.getTime());
            	// Paginación
            	countQuery.setDate(7, cal.getTime());
            }
            
            // Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
                r.setCollection(query.list());
                populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }
            return r;
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer, Fecha y Cuadrilla ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByDealerDateCrewQBE/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersByDealerWorkOrderQBE(java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByDealerWorkOrderQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersByDealerWorkOrderQBE/WorkOrderDAO ==");
        Session session = super.getSession();
        WOByDealerWorkOrderQBEPaginationResponse r = new WOByDealerWorkOrderQBEPaginationResponse();
        Calendar calCreationDate = null, calProgrammingDate = null;
        Long totalRowCount = 0L;
        int firstResult = 0;
        int maxResult = 0;

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            if (creationDate != null){
            	calCreationDate = Calendar.getInstance();
            	calCreationDate.setTime(UtilsBusiness.dateTo12am(creationDate));
            }
            
            if (programmingDate != null){
            	calProgrammingDate = Calendar.getInstance();
            	calProgrammingDate.setTime(UtilsBusiness.dateTo12am(programmingDate));
            }
            
            StringBuffer queryBuffer = new StringBuffer();
            StringBuffer countQueryBuffer = new StringBuffer("select count(*) ");
            StringBuffer bodyBuffer = new StringBuffer();
            

            // Todos los criterios de esta consulta son excluyentes en la siguiente prioridad:
            
            if (woCode != null && !woCode.trim().equals("")){
            	// Excluye todos los demás criterios
            	bodyBuffer.append(" from ");
            	bodyBuffer.append(WoAssignment.class.getName());
            	bodyBuffer.append(" woa where woa.dealerId = ?");
            	bodyBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?,?))");
            	bodyBuffer.append(" and (woa.workOrder.woCode = ?)");
            	bodyBuffer.append(" and (woa.isActive = :isActive) ");
            } else if (woStatusId != null && woStatusId != -1){
            	// Excluye todos los demás criterios
            	bodyBuffer.append(" from ");
            	bodyBuffer.append(WoAssignment.class.getName());
            	bodyBuffer.append(" woa where woa.dealerId = ?");
            	bodyBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.id = ?)");
            	bodyBuffer.append(" and (woa.isActive = :isActive) ");
            } else if (calCreationDate != null && calCreationDate.get(Calendar.YEAR) != 1900){
            	// Excluye todos los demás criterios
            	bodyBuffer.append(" from ");
            	bodyBuffer.append(WoAssignment.class.getName());
            	bodyBuffer.append(" woa where woa.dealerId = ?");
            	bodyBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?,?))");            	 
            	bodyBuffer.append(" and (to_char(woa.workOrder.creationDate,'");
            	bodyBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	bodyBuffer.append("') = to_char(to_date(?, '");
            	bodyBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	bodyBuffer.append("'),'");
            	bodyBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	bodyBuffer.append("'))");
            	bodyBuffer.append(" and (woa.isActive = :isActive) ");
            } else if (calProgrammingDate != null && calProgrammingDate.get(Calendar.YEAR) != 1900){
            	// Excluye todos los demás criterios
            	bodyBuffer.append(" from ");
            	bodyBuffer.append(WoAssignment.class.getName());
            	bodyBuffer.append(" woa where woa.dealerId = ?");
            	bodyBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?,?))");
            	bodyBuffer.append(" and (to_char(woa.workOrder.woProgrammingDate,'");
            	bodyBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	bodyBuffer.append("') = to_char(to_date(?, '");
            	bodyBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	bodyBuffer.append("'),'");
            	bodyBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	bodyBuffer.append("'))");
            	bodyBuffer.append(" and (woa.isActive = :isActive) ");
            } else if ((ServiceTypeId != null && ServiceTypeId != -1) && (serviceCategoryId == null || serviceCategoryId == -1)){
            	// Excluye todos los demás criterios
            	queryBuffer.append(" select woa ");
            	bodyBuffer.append(" from ");
            	bodyBuffer.append(WoAssignment.class.getName());
            	bodyBuffer.append(" woa ");
            	bodyBuffer.append(" join woa.workOrder as wo");
            	bodyBuffer.append(" join wo.workOrderServices as woService");
            	bodyBuffer.append(" join woService.service as s");
            	bodyBuffer.append(" join s.serviceCategory as sc");    
            	bodyBuffer.append(" join sc.serviceType as st");
            	bodyBuffer.append(" where woa.dealerId = ?");
            	bodyBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?,?))");            	
            	bodyBuffer.append(" and (st.id = ?)");
            	bodyBuffer.append(" and (woa.isActive = :isActive) ");
            }  else if ((ServiceTypeId != null && ServiceTypeId != -1) && (serviceCategoryId != null && serviceCategoryId != -1)){
            	// Excluye todos los demás criterios
            	queryBuffer.append(" select woa ");
            	bodyBuffer.append(" from ");
            	bodyBuffer.append(WoAssignment.class.getName());
            	bodyBuffer.append(" woa ");
            	bodyBuffer.append(" join woa.workOrder as wo");
            	bodyBuffer.append(" join wo.workOrderServices as woService");
            	bodyBuffer.append(" join woService.service as s");
            	bodyBuffer.append(" join s.serviceCategory as sc");    
            	bodyBuffer.append(" join sc.serviceType as st");
            	bodyBuffer.append(" where woa.dealerId = ?");
            	bodyBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?,?))");
            	bodyBuffer.append(" and (sc.id = ?)");
            	bodyBuffer.append(" and (st.id = ?)");
            	bodyBuffer.append(" and (woa.isActive = :isActive) ");
            }else if (postalCodeId != null && postalCodeId != -1){
            	// Excluye todos los demás criterios
            	bodyBuffer.append(" from ");
            	bodyBuffer.append(WoAssignment.class.getName());
            	bodyBuffer.append(" woa where woa.dealerId = ?");
            	bodyBuffer.append(" and (woa.workOrder.workorderStatusByActualStatusId.woStateCode in(?,?,?,?,?,?))");
            	bodyBuffer.append(" and (woa.workOrder.postalCode.id = ?)");
            	bodyBuffer.append(" and (woa.isActive = :isActive) ");
            }  
            
            countQueryBuffer.append(bodyBuffer.toString());
            Query query = null;
            Query countQuery = null;
            
            
            log.debug( "queryBuffer.toString() ::: "+queryBuffer.toString() );
            if(bodyBuffer.toString() != null && !bodyBuffer.toString().trim().equals("")){
            	query = session.createQuery(queryBuffer.toString() + bodyBuffer.toString());
            	countQuery = session.createQuery(countQueryBuffer.toString());
            }else{
            	log.error("No se han especificado parámetros para la consulta");
            	throw new DAOServiceException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            query.setLong(0, dealerId);
            query.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            // Paginación
            countQuery.setLong(0, dealerId);
            countQuery.setString("isActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            if (woCode != null && !woCode.trim().equals("")){
            	// Excluye todos los demás criterios
                query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            	query.setString(7, woCode);
            	// Paginación
            	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	countQuery.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            	countQuery.setString(7, woCode);
            } else if (woStatusId != null && woStatusId != -1){
            	// Excluye todos los demás criterios
            	query.setLong(1, woStatusId);
            	// Paginación
            	countQuery.setLong(1, woStatusId);
            } else if (calCreationDate != null && calCreationDate.get(Calendar.YEAR) != 1900){
            	// Excluye todos los demás criterios
                query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            	query.setString(7,  UtilsBusiness.formatYYYYMMDD(creationDate));
            	// Paginación
            	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	countQuery.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            	countQuery.setString(7,  UtilsBusiness.formatYYYYMMDD(creationDate));
            } else if (calProgrammingDate != null && calProgrammingDate.get(Calendar.YEAR) != 1900){
            	// Excluye todos los demás criterios
                query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
                query.setString(7,  UtilsBusiness.formatYYYYMMDD(programmingDate));
                // Paginación
                countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                countQuery.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
                countQuery.setString(7,  UtilsBusiness.formatYYYYMMDD(programmingDate));
            } else if ((ServiceTypeId != null && ServiceTypeId != -1) && (serviceCategoryId == null || serviceCategoryId == -1)){
            	// Excluye todos los demás criterios
            	query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
                query.setLong(7, ServiceTypeId);
                // Paginación
                countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                countQuery.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
                countQuery.setLong(7, ServiceTypeId);
            } else if ((ServiceTypeId != null && ServiceTypeId != -1) && (serviceCategoryId != null && serviceCategoryId != -1)){
            	// Excluye todos los demás criterios
            	query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
                query.setLong(7, serviceCategoryId);
                query.setLong(8, ServiceTypeId);
                // Paginación
                countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                countQuery.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
                countQuery.setLong(7, serviceCategoryId);
                countQuery.setLong(8, ServiceTypeId);
            } else if (postalCodeId != null && postalCodeId != -1){
            	// Excluye todos los demás criterios
            	query.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
                query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
                query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
                query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
                query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
                query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            	query.setLong(7, postalCodeId);
            	//Paginación
            	countQuery.setString(1, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            	countQuery.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            	countQuery.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            	countQuery.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            	countQuery.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            	countQuery.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            	countQuery.setLong(7, postalCodeId);
            }
            
            if(log.isDebugEnabled()){
            	log.debug(query.getQueryString());
            }
            
            // Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
            	r.setCollection(query.list() );
                populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }

            return r;
            
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por dealerId, woCode,serviceTypeId, serviceCategoryId, woStatusId," 
            		+ "stateId,  cityId,  postalCodeId,  creationDate,programmingDate ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByDealerWorkOrderQBE/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersByWorkOrderDataQBE(java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.util.Date)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId, Long countryId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersByWorkOrderDataQBE/WorkOrderDAO ==");
		WOActiveAndSuspendByCountryIdPaginationResponse r = new WOActiveAndSuspendByCountryIdPaginationResponse();
        Session session = super.getSession();
        Calendar calCreationDate = null, calProgrammingDate = null;
        Long totalRowCount = 0L;
        int firstResult = 0;
        int maxResult = 0;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }

            if (creationDate != null){
            	calCreationDate = Calendar.getInstance();
            	calCreationDate.setTime(creationDate);
            }

            if (programmingDate != null){
            	calProgrammingDate = Calendar.getInstance();
            	calProgrammingDate.setTime(programmingDate);
            }
            StringBuffer stringCountQuery = new StringBuffer("select count(*) ");
            StringBuffer queryBuffer = new StringBuffer();

            // Todos los criterios de esta consulta son excluyentes en la siguiente prioridad:
            queryBuffer.append(" from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" wo where wo.country.id = :aCountryId ");
            
            if (dealerId != null && dealerId != -1){
                queryBuffer.append(" and wo.dealerId = :aDealerId");
            } 
            
            if (woCode != null && !woCode.trim().equals("")){
            	queryBuffer.append(" and wo.woCode = :aWoCode");
            } 
            
            if (woStatusId != null && woStatusId != -1){
            	queryBuffer.append(" and wo.workorderStatusByActualStatusId.id = :aWoStatusId");
            }else{
            	queryBuffer.append(" and wo.workorderStatusByActualStatusId.woStateCode in (:aWoStatusCode1,:aWoStatusCode2) ");
            }
            
            if (calCreationDate != null && calCreationDate.get(Calendar.YEAR) != 1900){
            	queryBuffer.append(" and (to_char(wo.creationDate,'");
            	queryBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	queryBuffer.append("') = to_char(to_date(:aCreationDate, '");
            	queryBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	queryBuffer.append("'),'");
            	queryBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	queryBuffer.append("'))");
            	//queryBuffer.append(" and wo.creationDate = :aCreationDate");
            } 
            if (calProgrammingDate != null && calProgrammingDate.get(Calendar.YEAR) != 1900){
            	queryBuffer.append(" and (to_char(wo.woProgrammingDate,'");
            	queryBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	queryBuffer.append("') = to_char(to_date(:aProgrammingDate, '");
            	queryBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	queryBuffer.append("'),'");
            	queryBuffer.append(UtilsBusiness.DATE_FORMAT_YYYYMMDD);
            	queryBuffer.append("'))");
            	//queryBuffer.append(" and wo.woProgrammingDate = :aProgrammingDate");
            }
            if (ServiceTypeId != null && ServiceTypeId != -1){
                queryBuffer.append(" and wo.id in ( select wos.woId from WorkOrderService wos where wos.service.serviceCategory.serviceType.id = :aServiceTypeId) ");
            } 
            if (postalCodeId != null && postalCodeId != -1){
            	queryBuffer.append(" and wo.postalCode.id = :aPostalCodeId");
            } 
            
            if (stateId != null && stateId != -1){
            	queryBuffer.append(" and wo.postalCode.city.state.id = :aStateId");
            } 
            
            if (cityId != null && cityId != -1){
            	queryBuffer.append(" and wo.postalCode.city = :aCityId");
            } 
            
            Query query = session.createQuery(queryBuffer.toString());
            stringCountQuery.append(queryBuffer.toString());
            Query countQuery = session.createQuery(stringCountQuery.toString());
           
            query.setLong("aCountryId", countryId);
            countQuery.setLong("aCountryId", countryId);
            
            if (dealerId != null && dealerId != -1){
                query.setLong("aDealerId", dealerId);
                countQuery.setLong("aDealerId", dealerId);
            } 
            
            if (woCode != null && !woCode.trim().equals("")){
            	query.setString("aWoCode", woCode);
            	countQuery.setString("aWoCode", woCode);
            } 
            
            if (woStatusId != null && woStatusId != -1){
            	query.setLong("aWoStatusId", woStatusId);
            	countQuery.setLong("aWoStatusId", woStatusId);
            }else{
            	query.setString("aWoStatusCode1", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
                query.setString("aWoStatusCode2", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
                countQuery.setString("aWoStatusCode1", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
                countQuery.setString("aWoStatusCode2", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            }
            
            if (calCreationDate != null && calCreationDate.get(Calendar.YEAR) != 1900){
            	query.setString("aCreationDate", UtilsBusiness.formatYYYYMMDD(calCreationDate.getTime()));
            	countQuery.setString("aCreationDate", UtilsBusiness.formatYYYYMMDD(calCreationDate.getTime()));
            }
            
            if (calProgrammingDate != null && calProgrammingDate.get(Calendar.YEAR) != 1900){
            	query.setString("aProgrammingDate", UtilsBusiness.formatYYYYMMDD(calProgrammingDate.getTime()));
            	countQuery.setString("aProgrammingDate", UtilsBusiness.formatYYYYMMDD(calProgrammingDate.getTime()));
            }
            
            if (ServiceTypeId != null && ServiceTypeId != -1){
                query.setLong("aServiceTypeId", ServiceTypeId);
                countQuery.setLong("aServiceTypeId", ServiceTypeId);
            } 
            
            if (postalCodeId != null && postalCodeId != -1){
            	query.setLong("aPostalCodeId", postalCodeId);
            	countQuery.setLong("aPostalCodeId", postalCodeId);
            }
            
            if (stateId != null && stateId != -1){
            	query.setLong("aStateId", stateId);
            	countQuery.setLong("aStateId", stateId);
            } 
            
            if (cityId != null && cityId != -1){
            	query.setLong("aCityId", cityId);
            	countQuery.setLong("aCityId", cityId);
            }
            
         // Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
            }
            r.setCollection(query.list());
            if (requestCollectionInfo != null){
            	populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }
            
            return r;
            
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por dealerId, woCode, ServiceTypeId,  serviceCategoryId,  woStatusId," 
            		+ "stateId, cityId, postalCodeId,  creationDate,programmingDate ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByWorkOrderDataQBE/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersByCustomerDataQBE(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(Long countryId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersByCustomerDataQBE/WorkOrderDAO ==");
        Session session = super.getSession();
        WOActiveAndSuspendByCountryIdPaginationResponse r = new WOActiveAndSuspendByCountryIdPaginationResponse();
        Long totalRowCount = 0L;
        int firstResult = 0;
        int maxResult = 0;
        StringBuffer queryBuffer = new StringBuffer();
        
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringCountQuery = new StringBuffer("select count(*) ");
            queryBuffer.append(" from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" wo where wo.country.id = :aCountryId ");
            queryBuffer.append(" and wo.workorderStatusByActualStatusId.woStateCode in (:aWoStatusCode1, :aWoStatusCode2) ");
            
            // La consulta por dealerId e ibsCode excluye los otros criterios
            if (ibsCode != null && !ibsCode.trim().equals("")){
            	queryBuffer.append(" and wo.customer.customerCode = :aCustomerCode ");
            } 
            
            if (idNumber != null && !idNumber.trim().equals("")){
            	queryBuffer.append(" and wo.customer.documentNumber = :aDocNumber");
            } 
            
            if (name != null && !name.trim().equals("") ){
            	queryBuffer.append("and upper(wo.customer.firstName) = :aCustomerName");
            } 
            
            if (lastName != null && !lastName.trim().equals("")){
            	queryBuffer.append("and upper(wo.customer.lastName) = :aCustomerLastName");
            }
            
            if(officePhone != null && ! officePhone.trim().equals("")) {
            	queryBuffer.append(" and wo.customer.id in (select cmc1.customerId from CustomerMediaContact cmc1 where cmc1.mediaContactType.mediaCode = :anOfficeMcCode and cmc1.mediaContactValue = :anOfficeMcValue)");
            }
	     	if (homePhone != null && ! homePhone.trim().equals("")) {
	     		queryBuffer.append(" and wo.customer.id in (select cmc1.customerId from CustomerMediaContact cmc1 where cmc1.mediaContactType.mediaCode = :aHomeMcCode and cmc1.mediaContactValue = :aHomeMcValue)");
	     	}
	    	if (faxPhone != null && ! faxPhone.trim().equals("")) {
	    		queryBuffer.append(" and wo.customer.id in (select cmc1.customerId from CustomerMediaContact cmc1 where cmc1.mediaContactType.mediaCode = :aFaxMcCode and cmc1.mediaContactValue = :aFaxMcValue)");
	    	}
	    	if (cellPhone != null && ! cellPhone.trim().equals("")){
	    		queryBuffer.append(" and wo.customer.id in (select cmc1.customerId from CustomerMediaContact cmc1 where cmc1.mediaContactType.mediaCode = :aCellPhoneMcCode and cmc1.mediaContactValue = :aCellPhoneMcValue)");
            }
            
            Query query = session.createQuery(queryBuffer.toString());
            stringCountQuery.append(queryBuffer.toString());
            Query countQuery = session.createQuery(stringCountQuery.toString());
            
            query.setLong("aCountryId", countryId);
            query.setString("aWoStatusCode1", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
            query.setString("aWoStatusCode2", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            
            countQuery.setLong("aCountryId", countryId);
            countQuery.setString("aWoStatusCode1", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
            countQuery.setString("aWoStatusCode2", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            
            if (ibsCode != null && !ibsCode.trim().equals("")){
            	query.setString("aCustomerCode", ibsCode);
            	countQuery.setString("aCustomerCode", ibsCode);
            } 
            
            if (idNumber != null && !idNumber.trim().equals("")){
            	query.setString("aDocNumber", idNumber);
            	countQuery.setString("aDocNumber", idNumber);
            } 
            
            if (name != null && !name.trim().equals("") ){
            	query.setString("aCustomerName", name.toUpperCase());
            	countQuery.setString("aCustomerName", name.toUpperCase());
            } 
            
            if (lastName != null && !lastName.trim().equals("")){
            	query.setString("aCustomerLastName", lastName.toUpperCase());
            	countQuery.setString("aCustomerLastName", lastName.toUpperCase());
            }
            
            if(officePhone != null && ! officePhone.trim().equals("")) {
            	query.setString("anOfficeMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
            	query.setString("anOfficeMcValue", officePhone);
            	countQuery.setString("anOfficeMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
            	countQuery.setString("anOfficeMcValue", officePhone);
            }
	     	if (homePhone != null && ! homePhone.trim().equals("")) {
	     		query.setString("aHomeMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
            	query.setString("aHomeMcValue", homePhone);
            	countQuery.setString("aHomeMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
            	countQuery.setString("aHomeMcValue", homePhone);
	     	}
	    	if (faxPhone != null && ! faxPhone.trim().equals("")) {
	    		query.setString("aFaxMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity());
            	query.setString("aFaxMcValue", faxPhone);
            	countQuery.setString("aFaxMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity());
            	countQuery.setString("aFaxMcValue", faxPhone);
	    	}
	    	if (cellPhone != null && ! cellPhone.trim().equals("")){
	    		query.setString("aCellPhoneMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity());
            	query.setString("aCellPhoneMcValue", cellPhone);
            	countQuery.setString("aCellPhoneMcCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity());
            	countQuery.setString("aCellPhoneMcValue", cellPhone);
            }
	    	// Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
            }
            r.setCollection(query.list());
            if (requestCollectionInfo != null){
            	populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
            }
            
            return r;
            
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ibsCode,officePhone, homePhone, faxPhone," 
            		+ " cellPhone, idNumber,  name, lastName ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrdersByCustomerDataQBE/WorkOrderDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersByIds(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrdersByIds(List<Long> workOrderIds)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersByIds/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer strBuffer = new StringBuffer("from WorkOrder wo where wo.id in(");
            
            for (Long woId : workOrderIds) {
            	strBuffer.append(woId);
            	strBuffer.append(", ");
			}
            String queryStr = StringUtils.removeEnd(strBuffer.toString(), ", ");
            queryStr += ")";
            Query query = session.createQuery(queryStr);
            
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por workOrderIds==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByIds/WorkOrderDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrdersByCodes(List<String> workOrderCodes)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersByIds/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer strBuffer = new StringBuffer("from WorkOrder wo where wo.woCode in(");
            
            for (String woCode : workOrderCodes) {
            	strBuffer.append(woCode);
            	strBuffer.append(", ");
			}
            String queryStr = StringUtils.removeEnd(strBuffer.toString(), ", ");
            queryStr += ")";
            Query query = session.createQuery(queryStr);
            
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por workOrderIds==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByIds/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualAndPreviusType(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.WoType, co.com.directv.sdii.model.pojo.WoType)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByActualAndPreviusType(Dealer dealer,
			WoType actualType, WoType previusType) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWorkOrderByActualAndPreviusType/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.woTypeByWoTypeId.id = :actualType and ");
            stringQuery.append("wo.woTypeByPreviusWoTypeId.id = :previusType and ");
            stringQuery.append("wo.dealerId = :dealer ");
            //Query query = session.createQuery("from WorkOrder wo where wo.woTypeByWoTypeId.id = ?");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("actualType", actualType.getId());
            query.setLong("previusType", previusType.getId());
            query.setLong("dealer", dealer.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer y Tipo WO Actual y anterior ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByActualAndPreviusType/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByActualAndPreviusType(co.com.directv.sdii.model.pojo.WoType, co.com.directv.sdii.model.pojo.WoType)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByActualAndPreviusType(
			WoType actualType, WoType previusType)throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWorkOrderByActualAndPreviusType/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.woTypeByWoTypeId.id = :actualType and ");
            stringQuery.append("wo.woTypeByPreviusWoTypeId.id = :previusType");
            //Query query = session.createQuery("from WorkOrder wo where wo.woTypeByWoTypeId.id = ?");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("actualType", actualType.getId());
            query.setLong("previusType", previusType.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Tipo WO actual y anterior ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByActualAndPreviusType/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCustomer(co.com.directv.sdii.model.pojo.Customer)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByCustomer(Customer customer)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCustomer/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.customer.id = :customerId");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("customerId", customer.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Cliente ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCustomer/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCustomerAndRealizationDateExist(co.com.directv.sdii.model.pojo.Customer)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByCustomerAndRealizationDateExist(Customer customer)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getWorkOrderByCustomerAndRealizationDateExist/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.customer.id = :customerId and wo.woRealizationDate != null ");
            stringQuery.append("order by wo.woRealizationDate desc ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("customerId", customer.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Cliente y Fecha de Realización existente ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCustomerAndRealizationDateExist/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCustomerAddresAndRealizationDateExist(co.com.directv.sdii.model.pojo.Customer)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByCustomerAddressAndRealizationDateExist(
			Customer customer)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getWorkOrderByCustomerAndRealizationDateExist/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.customer.addressCode = :customerAddCode and wo.woRealizationDate != null ");
            stringQuery.append("order by wo.woRealizationDate desc ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("customerAddCode", customer.getAddressCode());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dirección de Cliente y fecha de realización existente ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCustomerAndRealizationDateExist/WorkOrderDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCustomerAndRealizationDate(co.com.directv.sdii.model.pojo.Customer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByCustomerAndRealizationDate(
			Customer customer, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCustomer/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            //Lleva la fecha inicial desde la hora cero
            GregorianCalendar calInit = new GregorianCalendar();
            calInit.setGregorianChange(initDate);
            calInit.set(Calendar.HOUR_OF_DAY,0);
            calInit.set(Calendar.MINUTE,0);
            calInit.set(Calendar.MILLISECOND,0);
            //Lleva la fecha final hasta la ?ltima hora del d?a
            GregorianCalendar calEnd = new GregorianCalendar();
            calEnd.setGregorianChange(endDate);
            calEnd.set(Calendar.HOUR_OF_DAY,24);
            calEnd.set(Calendar.MINUTE,0);
            calEnd.set(Calendar.MILLISECOND,0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.customer.id = :customerId and ");
            stringQuery.append("wo.woRealizationDate >= :calInit and ");
            stringQuery.append("wo.woRealizationDate <= :calEnd");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("customerId", customer.getId());
            query.setDate("calInit", calInit.getTime());
            query.setDate("calEnd", calEnd.getTime());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por cliente y rango de fechas de realización==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCustomer/WorkOrderDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerAndRealizationDate(co.com.directv.sdii.model.pojo.Dealer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByDealerAndRealizationDate(
			Dealer dealer, Date initDate, Date endDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerAndRealizationDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            //Lleva la fecha inicial desde la hora cero
            GregorianCalendar calInit = new GregorianCalendar();
            calInit.setGregorianChange(initDate);
            calInit.set(Calendar.HOUR_OF_DAY,0);
            calInit.set(Calendar.MINUTE,0);
            calInit.set(Calendar.MILLISECOND,0);
            //Lleva la fecha final hasta la ?ltima hora del d?a
            GregorianCalendar calEnd = new GregorianCalendar();
            calEnd.setGregorianChange(endDate);
            calEnd.set(Calendar.HOUR_OF_DAY,24);
            calEnd.set(Calendar.MINUTE,0);
            calEnd.set(Calendar.MILLISECOND,0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = :saleDealerId and ");
            stringQuery.append("wo.woRealizationDate >= :calInit and ");
            stringQuery.append("wo.woRealizationDate <= :calEnd");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("saleDealerId", dealer.getId());
            query.setDate("calInit", calInit.getTime());
            query.setDate("calEnd", calEnd.getTime());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer y rango de fechas de realización ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByDealerAndRealizationDate/WorkOrderDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerAndPostalCode(co.com.directv.sdii.model.pojo.Dealer, co.com.directv.sdii.model.pojo.PostalCode)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByDealerAndPostalCode(Dealer dealer,
			PostalCode postalCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCustomer/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }            
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = :saleDealerId and ");
            stringQuery.append("wo.postalCode.id = :postalCodeId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("saleDealerId", dealer.getId());
            query.setLong("postalCodeId", postalCode.getId());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer y Código postal ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByCustomer/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerIdAndWorkOrderId(java.lang.Long, java.lang.Long)
	 */
	public WorkOrder getWorkOrderByDealerIdAndWorkOrderId(final Long dealerId, final Long workOrderId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerIdAndWorkOrderId/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(WorkOrder.class.getName());
            stringQuery.append(" wo where wo.dealerId = :aDealerId and wo.id = :aWoId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aDealerId", dealerId);
            query.setLong("aWoId", workOrderId);
            return (WorkOrder)query.uniqueResult();
        } catch (Throwable ex) {
            log.error("== Error obteniendo la workorder por Dealer y WO  ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByDealerIdAndWorkOrderId/WorkOrderDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerAndRealizationDate(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByDealerAndRealizationDate(
			Date initDate, Date endDate) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerAndRealizationDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            //Lleva la fecha inicial desde la hora cero
            GregorianCalendar calInit = new GregorianCalendar();
            calInit.setGregorianChange(initDate);
            calInit.set(Calendar.HOUR_OF_DAY,0);
            calInit.set(Calendar.MINUTE,0);
            calInit.set(Calendar.MILLISECOND,0);
            //Lleva la fecha final hasta la ?ltima hora del d?a
            GregorianCalendar calEnd = new GregorianCalendar();
            calEnd.setGregorianChange(endDate);
            calEnd.set(Calendar.HOUR_OF_DAY,24);
            calEnd.set(Calendar.MINUTE,0);
            calEnd.set(Calendar.MILLISECOND,0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.woRealizationDate >= :calInit and ");
            stringQuery.append("wo.woRealizationDate <= :calEnd");
            Query query = session.createQuery(stringQuery.toString());
            query.setDate("calInit", calInit.getTime());
            query.setDate("calEnd", calEnd.getTime());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Rango de fechas de Realización ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByDealerAndRealizationDate/WorkOrderDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByDealerAndCreationDate(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getWorkOrderByDealerAndCreationDate(Dealer dealer, Date initDate,
			Date endDate) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByDealerAndCreationDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            //Lleva la fecha inicial desde la hora cero
            GregorianCalendar calInit = new GregorianCalendar();
            calInit.setGregorianChange(initDate);
            calInit.set(Calendar.HOUR_OF_DAY,0);
            calInit.set(Calendar.MINUTE,0);
            calInit.set(Calendar.MILLISECOND,0);
            //Lleva la fecha final hasta la ?ltima hora del d?a
            GregorianCalendar calEnd = new GregorianCalendar();
            calEnd.setGregorianChange(endDate);
            calEnd.set(Calendar.HOUR_OF_DAY,24);
            calEnd.set(Calendar.MINUTE,0);
            calEnd.set(Calendar.MILLISECOND,0);
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.saleDealer.id = :dealerId and ");
            stringQuery.append("wo.creationDate >= :calInit and ");
            stringQuery.append("wo.creationDate <= :calEnd");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealer.getId());
            query.setDate("calInit", calInit.getTime());
            query.setDate("calEnd", calEnd.getTime());
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Dealer, y Rango de fechas de creación ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByDealerAndCreationDate/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#calculateBackLogByDealerIdAndCurrentDate(java.lang.Long, java.util.Date)
	 */
	@Override
	public Long calculateBackLogByDealerIdAndCurrentDate(Long dealerId,
			Date currentDate) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia calculateBackLogByDealerIdAndCurrentDate/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select sum(wo.workingTime) from WorkOrder wo ");
            stringQuery.append("where ");
            stringQuery.append("wo.dealerId = :aDealerId and ");
            stringQuery.append(" (wo.workorderStatusByActualStatusId.woStateCode = :aScheduledStatusCode or wo.workorderStatusByActualStatusId.woStateCode = :aPendingStatusCode or wo.workorderStatusByActualStatusId.woStateCode = :aReScheduledStatusCode) and ");
            stringQuery.append("wo.woProgrammingDate <= :aCurrentDate");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aDealerId", dealerId);
            //jjimenezh 2010-10-22 agregar al cálculo del back log el estado asignado, hay que tener en cuenta que las work orders en este estado no tienen fecha de programación
            query.setString("aScheduledStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            query.setString("aPendingStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            query.setString("aReScheduledStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            query.setTimestamp("aCurrentDate", currentDate);
            
            Long result = (Long)query.uniqueResult();
            return result;
        }catch (Throwable ex) {
            log.error("== Error calculando Backlog por Dealer y fecha actual ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina calculateBackLogByDealerIdAndCurrentDate/WorkOrderDAO ==");
        }
	}
	

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCodeAndCountry(java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrder getWorkOrderByCodeAndCountry(String id, String countryCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderByCodeAndCountry/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" workorder where workorder.woCode = :id ");
            queryBuffer.append(" and workorder.country.countryCode = :country ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("id", id);
            query.setString("country", countryCode);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (WorkOrder) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por Codigo y Pais==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByCodeAndCountry/WorkOrderDAO ==");
        }
    }

    /**
     * Permite consutlar todas las ordenes de servicio para el proceso de asignador
     * @return List<WorkOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WorkOrder> getWorkOrdersByPage(Long workOrderId, 
			                                   Long pageSize,
			                                   int delayAllocator,
			                                   int delayWorkOrderCSRStatusPendent,
			                                   Long userId,
			                                   Long dealerDummie,
			                                   Long countryId,
			                                   String statusProccessAllocatorCode)
	throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getWorkOrdersByPage/WorkOrderDAO ==");
    	Session session = super.getSession();
    	
    	Calendar delayAllocatorDate = getDelayDate(delayAllocator,userId);
    	Calendar delayWorkOrderCSRStatusPendentDate = getDelayDate(delayWorkOrderCSRStatusPendent,userId);
    	
    	try {

    		StringBuffer strQuery = new StringBuffer();
    		
    		strQuery.append(" from " + WorkOrder.class.getName() + " wo   ");
    		strQuery.append(" where (exists(select 1  ");
    		strQuery.append("                from " + WorkOrder.class.getName() + " wo2 "); 
    		strQuery.append("                where wo2.id = wo.id "); 
    		strQuery.append("                      and wo2.importDate<:dateDelayAllocator ");
    	    strQuery.append("		               and not exists(select 1 ");
    		strQuery.append("    		                            from " + WorkOrderCSR.class.getName() + " wc ");
    		strQuery.append("                                                inner join wc.workOrderCSRStatus wcs ");  
    		strQuery.append("    		                           where wcs.code=:aWOCSRStatusPendiente "); 
    		strQuery.append("    		                                 and wc.woCode=wo2.woCode  ");
    		strQuery.append("    		                                 and wc.dateLastChange>:dateDelayWorkOrderCSRStatusPendent ");
    		strQuery.append("    		                                 and wc.reSchedule = :isReSchedule ");
    		strQuery.append("                                            and (wo2.dealerId is null or wo2.dealerId = :aDealerDummie) )  ");
    		strQuery.append("                      and not exists(select 1  ");
    		strQuery.append("                                       from " + WorkOrderCSR.class.getName() + " wc ");
    		strQuery.append("                                                inner join wc.workOrderCSRStatus wcs   ");
    		strQuery.append("                                      where wcs.code<>:aWOCSRStatusPendiente "); 
    		strQuery.append("    		                                 and wc.woCode=wo2.woCode   ) ) ");
    		strQuery.append("       or exists(select 1  ");
    		strQuery.append("    		        from " + WorkOrderCSR.class.getName() + " wc ");  
    		strQuery.append("    		       where wc.workOrderCSRStatus.code=:aWOCSRStatusAgendada "); 
    		strQuery.append("    		         and wc.woCode=wo.woCode ");
    		strQuery.append("                    and wc.reSchedule = :isReSchedule ");
    		strQuery.append("                    and wo.woProgrammingDate is not null ");
    		strQuery.append("                    and (wo.dealerId is null or wo.dealerId = :aDealerDummie) "); 
    		strQuery.append("                 )) ");
    		strQuery.append("        and wo.country.id=:aCountryId ");
    		strQuery.append("        and wo.workorderStatusByActualStatusId.woStateCode= :aWoStatusCode ");
    		strQuery.append("        and wo.processStatus.statusCode in ("+statusProccessAllocatorCode+") ");
    		if(workOrderId!=null && workOrderId!=0L){
    			strQuery.append("        and ( wo.id= :workOrderId or :workOrderId is null ) ");
    		}
    		strQuery.append(" order by wo.id asc  ");
    		
    		Query query = session.createQuery(strQuery.toString());
    		query.setString("aWoStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
    		//query.setString("aProcessStatusCode", statusProccessAllocatorCode );
    		query.setString("aWOCSRStatusAgendada", CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_AGENDADA.getCodeEntity());
    		query.setString("aWOCSRStatusPendiente", CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_PENDIENTE.getCodeEntity());
    		query.setString("isReSchedule", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
    		query.setLong("aDealerDummie", dealerDummie);
    		query.setLong("aCountryId", countryId);
    		if(workOrderId!=null && workOrderId!=0L){
    			query.setLong("workOrderId", workOrderId);
    		}
    		
    		query.setTimestamp("dateDelayAllocator", new Timestamp( delayAllocatorDate.getTimeInMillis() ) );
			query.setTimestamp("dateDelayWorkOrderCSRStatusPendent", new Timestamp( delayWorkOrderCSRStatusPendentDate.getTimeInMillis() ) );
			
    		if(log.isDebugEnabled()){
    			log.debug("Se consultarán work orders por página para el proceso de asignación, la cantidad es: " + pageSize);
    		}
    		query.setMaxResults(pageSize.intValue());
    		List<WorkOrder> result = query.list(); 
    		return result;
    	}catch (Throwable ex) {
    		log.error("== Error consultando WorkOrder por Página==");
    		throw this.manageException(ex);
    	} finally {
    		log.debug("== Termina getWorkOrdersByPage/WorkOrderDAO ==");
    	}
    }
    
    private Calendar getDelayDate(int delay,Long userId){
    	
    	Calendar delayAllocatorCalendar = Calendar.getInstance();
    	delayAllocatorCalendar.setTime(UtilsBusiness.getCurrentTimeZoneDateByUserId( userId, userDao));
    	delayAllocatorCalendar.add(Calendar.MINUTE, delay*-1);
    	return delayAllocatorCalendar;
    	 
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByElementId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WorkOrder getWorkOrderByElementId(Long elementId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWorkOrderByElementId/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select wo from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" wo, ");
            queryBuffer.append(ShippingOrder.class.getName());
            queryBuffer.append(" so, ");
            queryBuffer.append(ShippingOrderElement.class.getName());
            queryBuffer.append(" soe where wo.id = so.workOrderId and so.id = soe.soId and soe.element.id = :anElementId ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("anElementId", elementId);
            List<WorkOrder> objs = query.list();
            
            if ( ! objs.isEmpty()) {
                return objs.get(0);
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID de elemento ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByElementId/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByCodeAndCountryAndCustomer(java.lang.String,java.lang.Long,java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrder getWorkOrderByCodeAndCountryAndCustomer(String woCode, String countryId, String customerId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderByCode/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(WorkOrder.class.getName());
            stringQuery.append(" wo where wo.woCode = :aWorkOrderCode ");
            stringQuery.append("and wo.customer.customerCode = :customerId ");
            stringQuery.append("and wo.country.countryCode = :countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("aWorkOrderCode", woCode);
            query.setString("customerId", customerId);
            query.setString("countryId", countryId);
            WorkOrder workOrder = (WorkOrder)query.uniqueResult(); 
            return workOrder;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por código ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrderByCode/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersByIdsAndCrewAssignment(java.util.List, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Map<WorkOrder, WorkOrderAgenda> getWorkOrdersByIdsAndCrewAssignment(
			List<Long> workOrderIds, Long crewId, Long maxWoPerPdfFile) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWorkOrdersByIdsAndCrewAssignment/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer strBuffer = new StringBuffer("select woas.workOrder, woa from WoAssignment woas, WorkOrderAgenda woa where woas.id = woa.woAssignment.id and woas.isActive = :anIsActiveCode and woas.crewId = :aCrewId and woa.status = :woagendaActive and woas.workOrder.id in(");
            
            for (Long woId : workOrderIds) {
            	strBuffer.append(woId);
            	strBuffer.append(", ");
			}
            String queryStr = StringUtils.removeEnd(strBuffer.toString(), ", ");
            queryStr += ") order by woas.workOrder.customer.customerCode";
            
            Query query = session.createQuery(queryStr);
            query.setString("anIsActiveCode", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            query.setLong("aCrewId", crewId);
            query.setString("woagendaActive", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
            
            query.setMaxResults(maxWoPerPdfFile.intValue());
            
            List<Object[]> resultObj = query.list();
            Map<WorkOrder, WorkOrderAgenda> result = new HashMap<WorkOrder, WorkOrderAgenda>();
            for (Object[] objects : resultObj) {
				result.put((WorkOrder)objects[0], (WorkOrderAgenda)objects[1]);
			}
            
            return result;
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por workOrderIds and CrewId==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByIdsAndCrewAssignment/WorkOrderDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWoCodeByWoId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String getWoCodeByWoId(Long woId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getWorkOrderByID/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select wo.woCode from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" wo where wo.id = :id");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", woId);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (String) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByID/WorkOrderDAO ==");
        }
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<WorkOrder> getAttendedWorkOrdersByBuildingCodeAndCountryId(
			String buildingCode, Long countryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getAttendedWorkOrdersByBuildingCodeAndCountryId/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(WorkOrder.class.getName());
            queryBuffer.append(" wo where wo.buildingCode = :aBuildingCode and wo.country.id = :aCountryId and (wo.workorderStatusByActualStatusId.woStateCode = :aFinishedStatusCode or wo.workorderStatusByActualStatusId.woStateCode = :aRealizedStatusCode) order by wo.woRealizationDate desc");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("aBuildingCode", buildingCode);
            query.setLong("aCountryId", countryId);
            query.setString("aFinishedStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
            query.setString("aRealizedStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            List<WorkOrder> objs = query.list();
            return objs;
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por getAttendedWorkOrdersByBuildingCodeAndCountryId==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAttendedWorkOrdersByBuildingCodeAndCountryId/WorkOrderDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersForDealerTray(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WorkOrderResponse getWorkOrdersForDealerTray(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo, boolean isOrderByBuilding) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getWorkOrdersForDealerTray/WorkOrderDAO ==");
        try {
        	Session session = super.getSession();

        	//Id utilizados para no visualizar las wo activas con el estado del proceso de asignador no iniciado
        	Long activeStatusId = null;
        	Long notStartedProcessStatusId = null;
        	Long startedProcessStatusId = null;
        	
        	//para el caso de programIds igual a null
        	final String PROGRAM_CREW_IS_NULL = CodesBusinessEntityEnum.CORE_PROGRAM_CREW_IS_NULL.getCodeEntity();
        	String strProgramIdsDel="";
        	String strTempOr="";

        	//para el caso que soliciten todas las WO
        	boolean isExpirationGrouping = false;
        	boolean isWithoutAgendaDate = false;
        	boolean isAllWo = false;
        	boolean isOnLine =  filter.isScheduledCSR();
        	boolean isWoCode = ( filter.getWoCode() == null || filter.getWoCode().equals("") ) ? false : true;
        	boolean isWoTypeIds = ( filter.getWoTypeIds() == null || filter.getWoTypeIds().size() == 0 ) ? false : true;
        	boolean isWorkOrderMarkIds = ( filter.getWorkOrderMarkIds() == null || filter.getWorkOrderMarkIds().size() == 0 ) ? false : true;
        	boolean isServiceTypeIds = ( filter.getServiceTypeIds() == null || filter.getServiceTypeIds().size() == 0 ) ? false : true;
        	boolean isServiceCategoryIds = ( filter.getServiceCategoryIds() == null || filter.getServiceCategoryIds().size() == 0 ) ? false : true;
        	boolean isServiceIds = ( filter.getServiceIds() == null || filter.getServiceIds().size() == 0 ) ? false : true;
        	boolean isWoStatusIds = ( filter.getWoStatusIds() == null || filter.getWoStatusIds().size() == 0 ) ? false : true;
        	boolean isServiceDepartamentId = ( filter.getServiceDepartamentId() == null || filter.getServiceDepartamentId() <= 0 ) ? false : true;
        	boolean isServiceCityId = ( filter.getServiceCityId() == null || filter.getServiceCityId() <= 0L ) ? false : true;
        	boolean isServicePostalCodeId = ( filter.getServicePostalCodeId() == null || filter.getServicePostalCodeId() <= 0L ) ? false : true;
        	boolean isServiceAddress = ( filter.getServiceAddress() == null || filter.getServiceAddress().equals("") ) ? false : true;
        	Calendar woCreationDateInit = null;
        	Calendar woCreationDateEnd = null;
        	boolean isWoCreationDate = false;
        	if( filter.getWoCreationDateInit() != null && filter.getWoCreationDateEnd() != null ){
        		woCreationDateInit = Calendar.getInstance();
        		woCreationDateEnd = Calendar.getInstance();
        		woCreationDateInit.setTime( filter.getWoCreationDateInit() );
        		woCreationDateEnd.setTime( filter.getWoCreationDateEnd() );
        		if (woCreationDateInit.get(Calendar.YEAR) != 1 && woCreationDateEnd.get(Calendar.YEAR) != 1){
        			isWoCreationDate = true;
        		}
        	}
        	
        	Calendar woCancelationDateInit = null;
        	Calendar woCancelationDateEnd = null;
        	boolean isWoCancelationDate = false;
        	if( filter.getWoCancelationDateInit() != null && filter.getWoCancelationDateEnd() != null ){
        		woCancelationDateInit = Calendar.getInstance();
        		woCancelationDateEnd = Calendar.getInstance();
        		woCancelationDateInit.setTime( filter.getWoCancelationDateInit() );
        		woCancelationDateEnd.setTime( filter.getWoCancelationDateEnd() );
        		if (woCancelationDateInit.get(Calendar.YEAR) != 1 && woCancelationDateEnd.get(Calendar.YEAR) != 1){
        			isWoCancelationDate = true;
        		}
        	}
        	
        	Calendar woAgendationDateInit = null;
        	Calendar woAgendationDateEnd = null;
        	boolean isWoAgendationDate = false;
        	if( filter.getWoAgendationDateInit() != null && filter.getWoAgendationDateEnd() != null ){
        		woAgendationDateInit = Calendar.getInstance();
        		woAgendationDateEnd = Calendar.getInstance();
        		woAgendationDateInit.setTime( filter.getWoAgendationDateInit() );
        		woAgendationDateEnd.setTime( filter.getWoAgendationDateEnd() );
        		if (woAgendationDateInit != null && woAgendationDateInit.get(Calendar.YEAR) != 1 && woAgendationDateEnd.get(Calendar.YEAR) != 1){
        			isWoAgendationDate = true;
        		}
        	}
        	Calendar woAttentionDateInit = null;
        	Calendar woAttentionDateEnd = null;
        	boolean isWoAttentionDate = false;
        	if( filter.getWoAttentionDateInit() != null && filter.getWoAttentionDateEnd() != null){
        		woAttentionDateInit = Calendar.getInstance();
        		woAttentionDateEnd = Calendar.getInstance();
        		woAttentionDateInit.setTime( filter.getWoAttentionDateInit() );
        		woAttentionDateEnd.setTime( filter.getWoAttentionDateEnd() );
        		if (woAttentionDateInit != null && woAttentionDateInit.get(Calendar.YEAR) != 1 && woAttentionDateEnd.get(Calendar.YEAR) != 1){
        			isWoAttentionDate = true;
        		}
        	}
        	Calendar woFinalizationDateInit = null;
        	Calendar woFinalizationDateEnd = null;
        	boolean isWoFinalizationDate = false;
        	if( filter.getWoFinalizationDateInit() != null && filter.getWoFinalizationDateEnd() != null){
        		woFinalizationDateInit = Calendar.getInstance();
        		woFinalizationDateEnd = Calendar.getInstance();
        		woFinalizationDateInit.setTime( filter.getWoFinalizationDateInit() );
        		woFinalizationDateEnd.setTime( filter.getWoFinalizationDateEnd() );
        		if (woFinalizationDateInit != null && woFinalizationDateInit.get(Calendar.YEAR) != 1 && woFinalizationDateEnd.get(Calendar.YEAR) != 1){
        			isWoFinalizationDate = true;
        		}
        	}
        	boolean isServiceHourId = ( filter.getServiceHourIds() == null || filter.getServiceHourIds().size() == 0 ) ? false : true;
        	boolean isProgramId = ( filter.getProgramIds() == null || filter.getProgramIds().size() == 0 ) ? false : true;
        	boolean isProgramIdIsNull = false;
        	boolean isSaleDealerId = ( filter.getSaleDealerId() == null || filter.getSaleDealerId() <= 0 ) ? false : true;
        	boolean isCustomerIbs = ( filter.getCustomerIbs() == null || filter.getCustomerIbs().equals("") ) ? false : true;
        	boolean isCustomerHomePhoneNum = ( filter.getCustomerHomePhoneNum() == null || filter.getCustomerHomePhoneNum().equals("") ) ? false : true;
        	boolean isCustomerWorkPhoneNum = ( filter.getCustomerWorkPhoneNum() == null || filter.getCustomerWorkPhoneNum().equals("") ) ? false : true;
        	boolean isCustomerFaxNum = ( filter.getCustomerFaxNum() == null || filter.getCustomerFaxNum().equals("") ) ? false : true;
        	boolean isCustomerMobileNum = ( filter.getCustomerMobileNum() == null || filter.getCustomerMobileNum().equals("") ) ? false : true;
        	boolean isCustomerDocumentNum = ( filter.getCustomerDocumentNum() == null || filter.getCustomerDocumentNum().equals("") ) ? false : true;
        	boolean isCustomerFirstName = ( filter.getCustomerFirstName() == null || filter.getCustomerFirstName().equals("") ) ? false : true;
        	boolean isCustomerLastName = ( filter.getCustomerLastName() == null || filter.getCustomerLastName().equals("") ) ? false : true;
        	boolean isDealerId = ( filter.getDealersIds() == null || filter.getDealersIds().isEmpty() ) ? false : true;
        	boolean isBranchId = ( filter.getBranchIds() == null || filter.getBranchIds().isEmpty() )  ? false : true;
        	boolean isCrewId = ( filter.getCrewId() == null || filter.getCrewId().longValue() <= -2 ) ? false : true;
        	boolean customerBasicInfo = isServiceAddress || isCustomerIbs || isCustomerDocumentNum || isCustomerFirstName || isCustomerLastName;
        	boolean customerMediaContactInfo = isCustomerHomePhoneNum || isCustomerWorkPhoneNum || isCustomerFaxNum || isCustomerMobileNum;
        	boolean woAssigment = isProgramId || (isDealerId&& (isWithoutAgendaDate || isAllWo )) || isCrewId ;
        	boolean woAgendaDependencies = isWoAgendationDate || isServiceHourId || isExpirationGrouping;
        	boolean isCustomerClassIds = ( filter.getCustomerClassIds() == null || filter.getCustomerClassIds().isEmpty() ) ? false : true;
        	
        	StringBuffer stringCountQuery = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringQueryInner = new StringBuffer();
        	StringBuffer stringQueryWhere = new StringBuffer();
        	StringBuffer stringQueryOrder = new StringBuffer();
        	
        	stringCountQuery.append("SELECT COUNT(DISTINCT W.ID) FROM WORK_ORDERS W ");
        	stringQuery.append("SELECT DISTINCT W.ID, W.CREATION_DATE , W.BUILDING FROM WORK_ORDERS W ");
        	
        	/**
        	 * Para incluir los inner join
        	 * */
        	if( (isDealerId || isBranchId) && !filter.isDealerToWO() ){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_ASSIGNMENTS WOA ON WOA.WO_ID = W.ID ");
        	}
        	if( isServiceDepartamentId ){
        		stringQueryInner.append("INNER JOIN POSTAL_CODES PC ON PC.ID = W.POSTAL_CODE_ID ");
        		stringQueryInner.append("INNER JOIN CITIES C ON C.ID = PC.CITY_ID ");
        		stringQueryInner.append("INNER JOIN STATES STA ON STA.ID = C.STATE_ID ");
        	} else if( !isServiceDepartamentId && isServiceCityId ){
        		stringQueryInner.append("INNER JOIN POSTAL_CODES PC ON PC.ID = W.POSTAL_CODE_ID ");
        		stringQueryInner.append("INNER JOIN CITIES C ON C.ID = PC.CITY_ID ");
        	}
        	if( customerMediaContactInfo || customerBasicInfo || isCustomerClassIds){
        		stringQueryInner.append("INNER JOIN CUSTOMERS CUS ON CUS.ID = W.CUSTOMER_ID ");
        		if(customerMediaContactInfo){
            		stringQueryInner.append("{0} ");
        		}
        		if(isCustomerClassIds) {
            		stringQueryInner.append("INNER JOIN CUSTOMER_CLASS_TYPES CCTYPE ON CCTYPE.ID = CUS.CUST_TYPE_ID ");
            	}
        	}
        	if( isServiceTypeIds ){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_SERVICES WOS ON WOS.WO_ID = W.ID ");
        		stringQueryInner.append("INNER JOIN SERVICES S ON WOS.SERVICE_ID = S.ID ");
        		stringQueryInner.append("INNER JOIN SERVICE_CATEGORIES SC ON SC.ID = S.SERVICE_CATEGORY_ID ");
        		stringQueryInner.append("INNER JOIN SERVICE_TYPES ST ON ST.ID = SC.SERVICE_TYPE_ID ");
        	} else if( isServiceCategoryIds && !isServiceTypeIds ){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_SERVICES WOS ON WOS.WO_ID = W.ID ");
        		stringQueryInner.append("INNER JOIN SERVICES S ON WOS.SERVICE_ID = S.ID ");
        		stringQueryInner.append("INNER JOIN SERVICE_CATEGORIES SC ON SC.ID = S.SERVICE_CATEGORY_ID ");
        	} else if( !isServiceCategoryIds && isServiceIds ){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_SERVICES WOS ON WOS.WO_ID = W.ID ");
        	}
        	if( woAssigment && !isDealerId && !isBranchId && !woAgendaDependencies && !filter.isDealerToWO() ){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_ASSIGNMENTS WOA ON WOA.WO_ID = W.ID ");
        	} else if( !woAssigment && !isDealerId && !isBranchId && woAgendaDependencies && !filter.isDealerToWO() ){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_ASSIGNMENTS WOA ON WOA.WO_ID = W.ID ");
        		stringQueryInner.append("INNER JOIN WORK_ORDER_AGENDAS WOAGENDA ON WOAGENDA.WO_ASSIGNMENTS_ID = WOA.ID ");
        		if( isServiceHourId ){
        			stringQueryInner.append("INNER JOIN SERVICE_HOURS SH ON SH.ID = WOAGENDA.SERVICE_HOUR_ID ");
        		}
        	} else if( !woAssigment && ( (isDealerId || isBranchId) && !filter.isDealerToWO() ) && woAgendaDependencies){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_AGENDAS WOAGENDA ON WOAGENDA.WO_ASSIGNMENTS_ID = WOA.ID ");
        		if( isServiceHourId ){
        			stringQueryInner.append("INNER JOIN SERVICE_HOURS SH ON SH.ID = WOAGENDA.SERVICE_HOUR_ID ");
        		}
        	} else if( woAssigment && ( (isDealerId || isBranchId) && !filter.isDealerToWO() ) && woAgendaDependencies){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_AGENDAS WOAGENDA ON WOAGENDA.WO_ASSIGNMENTS_ID = WOA.ID ");
        		if( isServiceHourId ){
        			stringQueryInner.append("INNER JOIN SERVICE_HOURS SH ON SH.ID = WOAGENDA.SERVICE_HOUR_ID ");
        		}
        	}
        	
        	if(isWorkOrderMarkIds){
        		stringQueryInner.append(" INNER JOIN WORK_ORDER_WORK_ORDER_MARKS WOWOM ON W.ID=WOWOM.WORK_ORDER_ID ");
        	}

            if (isOnLine){
            	stringQueryInner.append(" INNER JOIN WORK_ORDER_PROCESS_SOURCES WOPS ON W.PROCESS_SOURCE_ID = WOPS.ID ");
            }
            
        	/**
        	 * Para incluir las condiciones dependiendo de los parametros
        	 */
        	
            Date actualDate=null;
            if(filter.getUserId()!=null && filter.getUserId()!=0){
            	actualDate=UtilsBusiness.getCurrentTimeZoneDateByUserId(Calendar.getInstance().getTime(), filter.getUserId(), userDao);
            	stringQueryWhere.append("	 WHERE :actualDate >= W.CREATION_DATE and ");
            }else{
            	stringQueryWhere.append("	 WHERE ");
            }
        	
        	stringQueryWhere.append(" W.COUNTRY_ID = :countryId ");
        	//Logica para asignar los ids de dealers por los que se va a filtrar
        	
        	if( isDealerId && !isBranchId ){
        		if ( !filter.isDealerToWO() ){
        			if(filter.getDealersIds().get(0) !=null && filter.getDealersIds().get(0).equals(new Long(-1))) {
        				stringQueryWhere.append("AND WOA.DEALER_ID IN (SELECT ID FROM DEALERS WHERE 1=1) ");        				
        			}else {
        				stringQueryWhere.append("AND WOA.DEALER_ID IN (" 	+ UtilsBusiness.longListToString( filter.getDealersIds(), "," ) + ") ");
        			}
        			stringQueryWhere.append("AND WOA.IS_ACTIVE = :woasigmentActive ");
        		}else{
        			stringQueryWhere.append("AND W.DEALER_ID IN (" + UtilsBusiness.longListToString( filter.getDealersIds(), "," ) + ") ");
        		}
    		} else if( !isDealerId && isBranchId ){
    			if ( !filter.isDealerToWO() ){
    				stringQueryWhere.append("AND WOA.DEALER_ID IN (" + UtilsBusiness.longListToString( filter.getBranchIds(), "," ) + ") ");
    				stringQueryWhere.append("AND WOA.IS_ACTIVE = :woasigmentActive ");
    			}else{
        			stringQueryWhere.append("AND W.DEALER_ID IN (" + UtilsBusiness.longListToString( filter.getBranchIds(), "," ) + ") ");
        		}
    		}
        	if( isServicePostalCodeId ){
        		stringQueryWhere.append("AND W.POSTAL_CODE_ID = :postalCodeId ");
        	}
        	if( isWoCode ){
        		stringQueryWhere.append("AND W.WO_CODE = :woCode ");
        	}
        	if( isWoStatusIds ){
        		stringQueryWhere.append("AND W.ACTUAL_STATUS_ID IN (" + UtilsBusiness.longListToString( filter.getWoStatusIds(), "," ) + ") ");
        	}
        	
        	//No se visualizan las wo activas con el estado del proceso de asignador no iniciado
        	stringQueryWhere.append("AND NOT(W.ACTUAL_STATUS_ID=:activeStatusId AND (W.PROCESS_STATUS_ID =:notStartedProcessStatusId or W.PROCESS_STATUS_ID =:startedProcessStatusId)) ");

        	if( isWoCreationDate ){
        		stringQueryWhere.append("AND W.CREATION_DATE BETWEEN :woCreationDateInit AND :woCreationDateEnd ");
        	}
        	if( isWoCancelationDate ){
        		stringQueryWhere.append("AND W.ID IN (SELECT WOSH.WO_ID FROM WORK_ORDER_STATUS_HISTORIES WOSH WHERE WOSH.WO_ID=W.ID AND WOSH.WO_STATUS_ID= :woStatus AND WOSH.STATUS_DATE BETWEEN :woCancelationDateInit AND :woCancelationDateEnd ) ");
        	}
        	if( isWoTypeIds ){
        		stringQueryWhere.append("AND W.WO_TYPE_ID IN (" + UtilsBusiness.longListToString( filter.getWoTypeIds(), "," ) + ") ");
        	}
        	if( isWoAttentionDate ){
        		stringQueryWhere.append("AND W.WO_REALIZATION_DATE BETWEEN :woAttentionDateInit AND :woAttentionDateEnd ");
        	}
        	if( isWoFinalizationDate ){
        		stringQueryWhere.append("AND W.FINALIZATION_DATE BETWEEN :woFinalizationDateInit AND :woFinalizationDateEnd ");
        	}
        	if( isSaleDealerId ){
        		stringQueryWhere.append("AND W.SALE_DEALER_ID = :saleDealerId ");
        	}
        	if( isServiceDepartamentId && isServiceCityId){
        		stringQueryWhere.append("AND C.ID = :cityId ");
        		stringQueryWhere.append("AND STA.ID = :stateId ");        		
        	} else if( isServiceDepartamentId && !isServiceCityId){
        		stringQueryWhere.append("AND STA.ID = :stateId ");
        	} else if( !isServiceDepartamentId && isServiceCityId ){
        		stringQueryWhere.append("AND C.ID = :cityId ");
        	}
        	if( customerBasicInfo ){
        		if( isServiceAddress ){
        			//gfandino - 31/05/2011: Modificación: Se adiciona la opción para consultar en mayusculas
            		//stringQueryWhere.append("AND CUS.CUSTOMER_ADDRESS LIKE :serviceAddress ");
        			stringQueryWhere.append("AND upper(CUS.CUSTOMER_ADDRESS) LIKE :serviceAddress ");
            	}
            	if( isCustomerIbs ){
            		//gfandino - 31/05/2011: Modificación: Se adiciona la opción para consultar en mayusculas
            		//stringQueryWhere.append("AND CUS.CUSTOMER_CODE = :customerIbs ");
            		stringQueryWhere.append("AND upper(CUS.CUSTOMER_CODE) = :customerIbs ");
            	}
            	if( isCustomerDocumentNum ){
            		stringQueryWhere.append("AND CUS.DOCUMENT_NUMBER = :customerDocumentNum ");
            	}
            	if( isCustomerFirstName ){
            		//gfandino - 31/05/2011: Modificación: Se adiciona la opción para consultar en mayusculas
            		//stringQueryWhere.append("AND CUS.FIRST_NAME LIKE :customerFirstName ");
            		stringQueryWhere.append("AND upper(CUS.FIRST_NAME) LIKE :customerFirstName ");
            	}
            	if( isCustomerLastName ){
            		//gfandino - 31/05/2011: Modificación: Se adiciona la opción para consultar en mayusculas
            		//stringQueryWhere.append("AND CUS.LAST_NAME LIKE :customerLastName ");
            		stringQueryWhere.append("AND upper(CUS.LAST_NAME) LIKE :customerLastName ");
            	}
        	}
        	if( customerMediaContactInfo ){        		
        		StringBuffer mediacontactBuffer = new StringBuffer();        		
        		
        		if( isCustomerHomePhoneNum ){
        			mediacontactBuffer.append("INNER JOIN CUSTOMER_MEDIA_CONTACTS CUSMC1 ON CUSMC1.CUSTOMER_ID = CUS.ID ");
        			mediacontactBuffer.append("INNER JOIN MEDIA_CONTACT_TYPES MCT1 ON MCT1.ID = CUSMC1.CONTACT_TYPE_ID AND MCT1.MEDIA_CODE = :homeMediaType AND CUSMC1.MEDIA_CONTACT_VALUE = :customerHomePhoneNum ");        			
        		}
        		if( isCustomerWorkPhoneNum ){
        			mediacontactBuffer.append("INNER JOIN CUSTOMER_MEDIA_CONTACTS CUSMC2 ON CUSMC2.CUSTOMER_ID = CUS.ID ");
        			mediacontactBuffer.append("INNER JOIN MEDIA_CONTACT_TYPES MCT2 ON MCT2.ID = CUSMC2.CONTACT_TYPE_ID AND MCT2.MEDIA_CODE = :workMediaType AND CUSMC2.MEDIA_CONTACT_VALUE = :customerWorkPhoneNum ");
        		}
        		if( isCustomerMobileNum ){
        			mediacontactBuffer.append("INNER JOIN CUSTOMER_MEDIA_CONTACTS CUSMC3 ON CUSMC3.CUSTOMER_ID = CUS.ID ");
        			mediacontactBuffer.append("INNER JOIN MEDIA_CONTACT_TYPES MCT3 ON MCT3.ID = CUSMC3.CONTACT_TYPE_ID AND MCT3.MEDIA_CODE = :mobileMediaType AND CUSMC3.MEDIA_CONTACT_VALUE = :customerMobileNum ");
        		}
        		if( isCustomerFaxNum ){
        			mediacontactBuffer.append("INNER JOIN CUSTOMER_MEDIA_CONTACTS CUSMC4 ON CUSMC4.CUSTOMER_ID = CUS.ID ");
        			mediacontactBuffer.append("INNER JOIN MEDIA_CONTACT_TYPES MCT4 ON MCT4.ID = CUSMC4.CONTACT_TYPE_ID AND MCT4.MEDIA_CODE = :faxMediaType AND CUSMC4.MEDIA_CONTACT_VALUE = :customerFaxNum ");
        		}
        		
        		String tempInner = stringQueryInner.toString();
        		stringQueryInner = new StringBuffer(MessageFormat.format(tempInner, mediacontactBuffer.toString()));
        	}
        	if( isCustomerClassIds ) {
        		stringQueryWhere.append("AND CCTYPE.CUSTOMER_CLASS_ID IN (" + UtilsBusiness.longListToString( filter.getCustomerClassIds(), "," ) + ") ");
        	}
        	if( isServiceIds ){
        		stringQueryWhere.append("AND WOS.SERVICE_ID IN (" + UtilsBusiness.longListToString( filter.getServiceIds(), "," ) + ") ");
        	}
        	if( isServiceTypeIds ){
        		stringQueryWhere.append("AND ST.ID IN (" + UtilsBusiness.longListToString( filter.getServiceTypeIds(), "," ) + ") ");
        	}
        	if( isServiceCategoryIds ){
        		stringQueryWhere.append("AND SC.ID IN (" + UtilsBusiness.longListToString( filter.getServiceCategoryIds(), "," ) + ") ");
        	}
        	if( woAssigment ){
        		if( !isDealerId && !isBranchId && !filter.isDealerToWO() ){
        			stringQueryWhere.append("AND WOA.IS_ACTIVE = :woasigmentActive ");
        		}
        		if (isProgramId) {

        			strProgramIdsDel = UtilsBusiness.longListToString(filter
        					.getProgramIds(), ",");
					int intCoincidencia = strProgramIdsDel
							.indexOf(PROGRAM_CREW_IS_NULL);

					// para el caso de programIds igual a null
					if (intCoincidencia != -1) {
						isProgramIdIsNull = true;
						strProgramIdsDel = strProgramIdsDel.replaceAll(
								"," + PROGRAM_CREW_IS_NULL,"")
								.replaceAll(PROGRAM_CREW_IS_NULL,"");
					}
					stringQueryWhere.append("AND ( ");
					if (strProgramIdsDel.trim().length() > 0) {
						stringQueryWhere.append(" WOA.PROGRAM_ID IN ("
								+ strProgramIdsDel + ") ");
						strTempOr = " OR ";
					}
					if (isProgramIdIsNull)
						stringQueryWhere.append(strTempOr
								+ " WOA.PROGRAM_ID IS NULL ");

					stringQueryWhere.append(" ) ");
				}
				if (isCrewId) {
					// para el caso de crewIds igual a null
					if (filter.getCrewId() == -1) {
						isCrewId = false;
						stringQueryWhere.append("AND WOA.CREW_ID IS NULL ");
					} else
						stringQueryWhere.append("AND WOA.CREW_ID = :crewId ");
				}
        	}
        	if( woAgendaDependencies ){
        		stringQueryWhere.append("AND WOAGENDA.STATUS = :woAgendaStatus ");
        		if( isWoAgendationDate ){
        			stringQueryWhere.append("AND WOAGENDA.AGENDATION_DATE BETWEEN :woAgendationDateInit AND :woAgendationDateEnd ");
        		}
        		if( isServiceHourId ){
        			stringQueryWhere.append("AND SH.ID IN (" + UtilsBusiness.longListToString( filter.getServiceHourIds(), "," ) + ") ");
        		}
        	}
        	if(isWithoutAgendaDate){
        		stringQueryWhere.append("AND W.ID NOT IN ( ");
        		stringQueryWhere.append("SELECT DISTINCT (asigment.WO_ID) ");
        		stringQueryWhere.append("FROM WORK_ORDER_ASSIGNMENTS asigment ");
        		stringQueryWhere.append("INNER JOIN WORK_ORDER_AGENDAS agenda ON agenda.WO_ASSIGNMENTS_ID = asigment.ID ");
        		stringQueryWhere.append("WHERE asigment.IS_ACTIVE = :isWithoutAgendaAssignmentStatus ");
        		stringQueryWhere.append("AND agenda.STATUS = :isWithoutAgendaStatus ) ");
         	}
        	
        	if(filter.getCustomerCategoryId()!=null){
        		stringQueryWhere.append(" AND (select cca.id ");
        		stringQueryWhere.append(" from  customers cu ");
        		stringQueryWhere.append(" inner join customer_class_types cct on (cct.id=cu.cust_type_id) ");
        		stringQueryWhere.append(" inner join customer_classes cc on (cc.id=cct.customer_class_id) ");
        		stringQueryWhere.append(" left join customer_category cca on (cc.customer_category_id = cca.id) ");
        		stringQueryWhere.append(" where cu.id = W.CUSTOMER_ID) = :customerCategoryId ");
        	}
        	
        	if(filter.getCustomerTypesIds()!=null && !filter.getCustomerTypesIds().isEmpty()){
        		stringQueryWhere.append(" AND exists (select 1 ");
        		stringQueryWhere.append(" from  customers cu "); 
        		stringQueryWhere.append(" inner join customer_class_types cct on (cct.id=cu.cust_type_id) ");
        		stringQueryWhere.append(" inner join customer_types ct on (ct.id=cct.customer_type_id) ");
        		stringQueryWhere.append(" where ct.id in ( ");
        		for(int i=0;i<filter.getCustomerTypesIds().size(); ++i){
        			stringQueryWhere.append(" "+filter.getCustomerTypesIds().get(i)+" ");
        			if(i<(filter.getCustomerTypesIds().size()-1)){
        				stringQueryWhere.append(" , ");
        			}
        		}
        		stringQueryWhere.append(" ) and cu.id = w.customer_id ) ");
        	}
        	
        	if(isWorkOrderMarkIds){
        		stringQueryWhere.append(" AND WOWOM.IS_ACTIVE = :aWorkOrderMarkIsActive AND WOWOM.WORK_ORDER_MARK_ID IN (" + UtilsBusiness.longListToString( filter.getWorkOrderMarkIds(), "," ) + ") ");
        	}
        	      	
        	if(isOnLine){
        		stringQueryWhere.append(" AND WOPS.PROCESS_SOURCE_CODE IN ( '" + UtilsBusiness.stringListToString( filter.getProcessSourceCodes(), "' , '" ) + "') ");
        	}
        	
        	if (isOrderByBuilding){
        		stringQueryOrder.append("ORDER BY W.BUILDING DESC, W.CREATION_DATE DESC ");
        	}else{
        		stringQueryOrder.append("ORDER BY W.CREATION_DATE DESC ");
        	}
        	
        	SQLQuery query = session.createSQLQuery(stringQuery.toString() + stringQueryInner.toString() + stringQueryWhere.toString() + stringQueryOrder.toString());
        	SQLQuery countQuery = session.createSQLQuery(stringCountQuery.toString() + stringQueryInner.toString() + stringQueryWhere.toString() + stringQueryOrder.toString());
        	
            if(filter.getUserId()!=null && filter.getUserId()!=0){
            	query.setTimestamp("actualDate", new Timestamp(actualDate.getTime()));
            	countQuery.setTimestamp("actualDate", new Timestamp(actualDate.getTime()));
            }
        	
        	/**
        	 * Para agregar los parametro de acuerdo al filtro
        	 */
        	query.setLong("countryId", filter.getCountryId());
        	countQuery.setLong("countryId", filter.getCountryId());
        	if( (isDealerId || isBranchId) && !filter.isDealerToWO() ){
    			query.setString("woasigmentActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		countQuery.setString("woasigmentActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
    		}
  
        	
        	if( isServicePostalCodeId ){
        		query.setLong("postalCodeId", filter.getServicePostalCodeId());
        		countQuery.setLong("postalCodeId", filter.getServicePostalCodeId());
        	}
        	if( isWoCode ){
        		query.setString("woCode", filter.getWoCode());
        		countQuery.setString("woCode", filter.getWoCode());
        	}
        	
        	//Se asignan los valores de los estados que nose deben visualizar si el proceso de asignacion no se ha iniciado
        	activeStatusId = CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getIdEntity( WorkorderStatus.class.getName() );
        	notStartedProcessStatusId = CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_NOT_STARTED.getIdEntity(ProcessStatus.class.getName() );
        	startedProcessStatusId = CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_STARTED.getIdEntity(ProcessStatus.class.getName() );
        	query.setLong("activeStatusId",activeStatusId);
        	countQuery.setLong("activeStatusId",activeStatusId);
        	query.setLong("notStartedProcessStatusId",notStartedProcessStatusId);
        	countQuery.setLong("notStartedProcessStatusId",notStartedProcessStatusId);
        	query.setLong("startedProcessStatusId",startedProcessStatusId);
        	countQuery.setLong("startedProcessStatusId",startedProcessStatusId);
        	
        	if( isWoCreationDate ){
        		query.setTimestamp("woCreationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoCreationDateInit() ).getTime() ) );
        		query.setTimestamp("woCreationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoCreationDateEnd() ).getTime() ) );
        		countQuery.setTimestamp("woCreationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoCreationDateInit() ).getTime() ) );
        		countQuery.setTimestamp("woCreationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoCreationDateEnd() ).getTime() ) );
        	}
        	if( isWoCancelationDate ){
        		Long canceledStatusId = CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getIdEntity( WorkorderStatus.class.getName() );
        		query.setLong("woStatus",canceledStatusId );
        		query.setTimestamp("woCancelationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoCancelationDateInit() ).getTime() ) );
        		query.setTimestamp("woCancelationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoCancelationDateEnd() ).getTime() ) );
        		countQuery.setLong("woStatus",canceledStatusId );
        		countQuery.setTimestamp("woCancelationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoCancelationDateInit() ).getTime() ) );
        		countQuery.setTimestamp("woCancelationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoCancelationDateEnd() ).getTime() ) );
        	}
        	if( isWoAttentionDate ){
        		query.setTimestamp("woAttentionDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoAttentionDateInit() ).getTime() ) );
        		query.setTimestamp("woAttentionDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoAttentionDateEnd() ).getTime() ) );
        		countQuery.setTimestamp("woAttentionDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoAttentionDateInit() ).getTime() ) );
        		countQuery.setTimestamp("woAttentionDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoAttentionDateEnd() ).getTime() ) );
        	}
        	if( isWoFinalizationDate ){
        		query.setTimestamp("woFinalizationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoFinalizationDateInit() ).getTime() ) );
        		query.setTimestamp("woFinalizationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoFinalizationDateEnd() ).getTime() ) );
        		countQuery.setTimestamp("woFinalizationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoFinalizationDateInit() ).getTime() ) );
        		countQuery.setTimestamp("woFinalizationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoFinalizationDateEnd() ).getTime() ) );
        	}
        	if( isSaleDealerId ){
        		query.setLong("saleDealerId", filter.getSaleDealerId());
        		countQuery.setLong("saleDealerId", filter.getSaleDealerId());
        	}
        	if( isServiceDepartamentId && isServiceCityId){
        		query.setLong("cityId", filter.getServiceCityId());
        		query.setLong("stateId", filter.getServiceDepartamentId());   		
        		countQuery.setLong("cityId", filter.getServiceCityId());
        		countQuery.setLong("stateId", filter.getServiceDepartamentId());
        	} else if( isServiceDepartamentId && !isServiceCityId){
        		query.setLong("stateId", filter.getServiceDepartamentId());
        		countQuery.setLong("stateId", filter.getServiceDepartamentId());
        	} else if( !isServiceDepartamentId && isServiceCityId ){
        		query.setLong("cityId", filter.getServiceCityId());
        		countQuery.setLong("cityId", filter.getServiceCityId());
        	}
        	
        	if( isServiceAddress ){
        		//gfandino - 21/05/2011 Modificación: Se convierte el String a mayusculas
        		/*query.setString("serviceAddress", "%"+filter.getServiceAddress()+"%");
        		countQuery.setString("serviceAddress", "%"+filter.getServiceAddress()+"%");*/
        		query.setString("serviceAddress", "%"+filter.getServiceAddress().toUpperCase()+"%");
        		countQuery.setString("serviceAddress", "%"+filter.getServiceAddress().toUpperCase()+"%");
        	}
        	if( customerBasicInfo ){
        		if( isCustomerIbs ){
        			//gfandino - 21/05/2011 Modificación: Se convierte el String a mayusculas
            		/*query.setString("customerIbs", filter.getCustomerIbs());        		
            		countQuery.setString("customerIbs", filter.getCustomerIbs());*/
        			query.setString("customerIbs", filter.getCustomerIbs().toUpperCase());        		
            		countQuery.setString("customerIbs", filter.getCustomerIbs().toUpperCase());
            	}
            	if( isCustomerDocumentNum ){
            		query.setString("customerDocumentNum", filter.getCustomerDocumentNum());        		
            		countQuery.setString("customerDocumentNum", filter.getCustomerDocumentNum());
            	}
            	if( isCustomerFirstName ){
            		//gfandino - 21/05/2011 Modificación: Se convierte el String a mayusculas
            		/*query.setString("customerFirstName", "%"+filter.getCustomerFirstName()+"%");
            		countQuery.setString("customerFirstName", "%"+filter.getCustomerFirstName()+"%");*/
            		query.setString("customerFirstName", "%"+filter.getCustomerFirstName().toUpperCase()+"%");
            		countQuery.setString("customerFirstName", "%"+filter.getCustomerFirstName().toUpperCase()+"%");
            	}
            	if( isCustomerLastName ){
            		//gfandino - 21/05/2011 Modificación: Se convierte el String a mayusculas
            		/*query.setString("customerLastName", "%"+filter.getCustomerLastName()+"%");
            		countQuery.setString("customerLastName", "%"+filter.getCustomerLastName()+"%");*/
            		query.setString("customerLastName", "%"+filter.getCustomerLastName().toUpperCase()+"%");
            		countQuery.setString("customerLastName", "%"+filter.getCustomerLastName().toUpperCase()+"%");
            	}
        	}
        	if( isCustomerHomePhoneNum ){
        		query.setString("homeMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
        		query.setString("customerHomePhoneNum", filter.getCustomerHomePhoneNum());
        		countQuery.setString("homeMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
        		countQuery.setString("customerHomePhoneNum", filter.getCustomerHomePhoneNum());
    		}
    		if( isCustomerWorkPhoneNum ){
    			query.setString("workMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity());
        		query.setString("customerWorkPhoneNum", filter.getCustomerWorkPhoneNum());
        		countQuery.setString("workMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity());
        		countQuery.setString("customerWorkPhoneNum", filter.getCustomerWorkPhoneNum());        			
    		}
    		if( isCustomerFaxNum ){
    			query.setString("faxMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity());
        		query.setString("customerFaxNum", filter.getCustomerFaxNum());
        		countQuery.setString("faxMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity());
        		countQuery.setString("customerFaxNum", filter.getCustomerFaxNum());
    		}
    		if( isCustomerMobileNum ){
    			query.setString("mobileMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity());
        		query.setString("customerMobileNum", filter.getCustomerMobileNum());
        		countQuery.setString("mobileMediaType", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity());
        		countQuery.setString("customerMobileNum", filter.getCustomerMobileNum());
    		}
    		if( woAgendaDependencies ){
    			query.setString("woAgendaStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
    			countQuery.setString("woAgendaStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
    		}
        	if( isCrewId ){
        		query.setLong("crewId", filter.getCrewId());
        		countQuery.setLong("crewId", filter.getCrewId());
        	}        		
        	if( isWoAgendationDate ){
        		query.setTimestamp("woAgendationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoAgendationDateInit() ).getTime() ) );
        		query.setTimestamp("woAgendationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoAgendationDateEnd() ).getTime() ) );
        		countQuery.setTimestamp("woAgendationDateInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getWoAgendationDateInit() ).getTime() ) );
        		countQuery.setTimestamp("woAgendationDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getWoAgendationDateEnd() ).getTime() ) );
        	}
        	
        	if( woAssigment ){
        		if( !isDealerId && !isBranchId && !filter.isDealerToWO() ){
        			query.setString("woasigmentActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        			countQuery.setString("woasigmentActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		}
        		if( isCrewId ){
        			query.setLong("crewId", filter.getCrewId());
            		countQuery.setLong("crewId", filter.getCrewId());
        		}
        	}
        	
        	
        	if( isWithoutAgendaDate ){
        		query.setString("isWithoutAgendaAssignmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		query.setString("isWithoutAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        		
        		countQuery.setString("isWithoutAgendaAssignmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		countQuery.setString("isWithoutAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        	}
        	
        	if(filter.getCustomerCategoryId()!=null){

        		query.setLong("customerCategoryId", filter.getCustomerCategoryId());
        		countQuery.setLong("customerCategoryId", filter.getCustomerCategoryId());

        	}
        	
        	if(isWorkOrderMarkIds){
        		query.setString("aWorkOrderMarkIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
        		countQuery.setString("aWorkOrderMarkIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollectionInfo != null ){
        		BigDecimal numberOfRecords = (BigDecimal) countQuery.uniqueResult();
        		recordQty = numberOfRecords.longValue();			
				query.setFirstResult( requestCollectionInfo.getFirstResult() );
				query.setMaxResults( requestCollectionInfo.getMaxResults() );	
        	}
        	
        	WorkOrderResponse response = new WorkOrderResponse();
        	List<Object[]> list = query.list();
        	if( requestCollectionInfo != null )
        			populatePaginationInfo( response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setWorkOrdersIds(list);
        	
        	return response;
        	
        }catch (Throwable ex) {
            log.error("== Error getWorkOrdersForDealerTray/WorkOrderDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrdersForDealerTray/WorkOrderDAO ==");
        }
	}
		
	/**
	 * 
	 * Metodo: Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
	 * Retorna los servicios asociados a una WorkOrder.
	 * @param woId
	 * @param requestCollectionInfo informacion para paginacion
	 * @return ServiceResponse servicios asociados a la WorkOrder
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ServiceResponse getWorkOrderServices(Long woId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
        
    	log.debug("== Inicia getWorkOrderServices/WorkOrderDAO ==");
        Session session = super.getSession();
        ServiceResponse r = new ServiceResponse();
        int firstResult = 0;
        int maxResult = 0;
        Long totalRowCount = 0L;

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            StringBuffer queryPrincipal = new StringBuffer();
            StringBuffer countQueryBuffer = new StringBuffer("select count(*) ");
            queryPrincipal.append(" select new ");
            queryPrincipal.append(Service.class.getName());
            queryPrincipal.append(" (sc.id,sc.serviceCategory,sc.serviceName,sc.serviceCode,sc.isShippingOrderRequired,sc.allowAddService,sc.allowDeleteService,sc.allowRecordingElement,sc.allowRetrieveElement) ");
            queryBuffer.append(" from ");
            queryBuffer.append(WorkOrderService.class.getName());
            queryBuffer.append(" wos join wos.service as sc ");
            queryBuffer.append(" where wos.woId = :aWOId order by sc.serviceName");
            
            //Paginacion
            countQueryBuffer.append( queryBuffer.toString() );       
            
            Query query = null;
            Query countQuery = null;
             
            queryPrincipal.append( queryBuffer.toString() );
        	query = session.createQuery( queryPrincipal.toString() );
        	countQuery = session.createQuery( countQueryBuffer.toString() );
           
            query.setLong("aWOId", woId);       

            // Paginación
            countQuery.setLong("aWOId", woId);
            
            // Paginación
            if (requestCollectionInfo != null){
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);                
            }
            r.setServices(query.list());
            if (requestCollectionInfo != null)
            	populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getServices().size(), totalRowCount.intValue());
            return r;
        } catch (Throwable ex) {
            log.error("== Error consultando getWorkOrderServices/WorkOrderDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderServices/WorkOrderDAO ==");
        }
	}

	@Override
	public Long countWoForExpirationGrouping(WorkOrderFilterTrayDTO filter)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio countWoForExpirationGrouping/WorkOrderDAO ==");
        try {
        	Session session = super.getSession();
        	Calendar expirationGroupingCalendarInit = null;
        	Calendar expirationGroupingCalendarEnd = null;
        	//para el caso que soliciten todas las WO
        	boolean isExpirationGrouping = false;       
        	boolean isWithoutAgenda = false;
        	boolean isAllWo = false;
        	if( filter.getExpirationGroupingInit() != null && filter.getExpirationGroupingEnd() != null){
        		expirationGroupingCalendarInit = Calendar.getInstance();
        		expirationGroupingCalendarInit.setTime( filter.getExpirationGroupingInit() );
        		expirationGroupingCalendarEnd = Calendar.getInstance();
        		expirationGroupingCalendarEnd.setTime( filter.getExpirationGroupingEnd() );
        		if ( expirationGroupingCalendarInit.get(Calendar.YEAR) == 1901 && expirationGroupingCalendarEnd.get(Calendar.YEAR) == 1901 ){
        			isWithoutAgenda = true;
        		} else if ( expirationGroupingCalendarInit.get(Calendar.YEAR) == 1900 || expirationGroupingCalendarEnd.get(Calendar.YEAR) == 1900 ){
        			isAllWo = true;
        		} else {
        			isExpirationGrouping = true;
        		}
        	}
        	boolean isWoStatusIds = ( filter.getWoStatusIds() == null || filter.getWoStatusIds().size() == 0 ) ? false : true; 
        	boolean isDealer = ( filter.getDealersIds() == null || filter.getDealersIds().isEmpty()) ? false : true;
        	
        	StringBuffer stringQuery = new StringBuffer();        	
        	
        	stringQuery.append("SELECT COUNT(DISTINCT W.ID) FROM WORK_ORDERS W ");
        	/**
        	 * Para incluir inner
        	 */
        	//Siempre se debe hacer join con WORK_ORDER_ASSIGNMENTS y WORK_ORDER_AGENDAS para saber si estan activas o no
        	if( !isAllWo || isDealer )
        		stringQuery.append("INNER JOIN WORK_ORDER_ASSIGNMENTS WOA ON WOA.WO_ID = W.ID ");
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		stringQuery.append("INNER JOIN WORK_ORDER_AGENDAS WOAGENDA ON WOAGENDA.WO_ASSIGNMENTS_ID = WOA.ID ");
        	} 
    		/**
        	 * Para incluir las condiciones dependiendo de los parametros
        	 */
        	stringQuery.append("WHERE W.COUNTRY_ID = :countryId ");
        	if( !isAllWo || isDealer)
        		stringQuery.append("AND WOA.IS_ACTIVE = :woAssigmentStatus ");
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		stringQuery.append("AND WOAGENDA.STATUS = :woAgendaStatus ");
        	} else if(isWithoutAgenda){
        		stringQuery.append("AND W.ID NOT IN ( ");
        		stringQuery.append("SELECT DISTINCT (asigment.WO_ID) ");
        		stringQuery.append("FROM WORK_ORDER_ASSIGNMENTS asigment ");
        		stringQuery.append("INNER JOIN WORK_ORDER_AGENDAS agenda ON agenda.WO_ASSIGNMENTS_ID = asigment.ID ");
        		stringQuery.append("WHERE asigment.IS_ACTIVE = :isWithoutAgendaAssignmentStatus ");
        		stringQuery.append("AND agenda.STATUS = :isWithoutAgendaStatus ) ");
        	}
    		
    		/**
        	 * Para incluir la condición del dealer
        	 */
    		if(isDealer){
        		stringQuery.append("AND WOA.DEALER_ID IN (" + UtilsBusiness.longListToString( filter.getDealersIds(), "," ) + ") ");
        	}
    		if( isWoStatusIds ){
        		stringQuery.append("AND W.ACTUAL_STATUS_ID IN (" + UtilsBusiness.longListToString( filter.getWoStatusIds(), "," ) + ") ");
        	}
        	/**
        	 * En caso que sea con fecha fe agendamiento se agrega el filtro
        	 */
    		if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		stringQuery.append("AND WOAGENDA.AGENDATION_DATE BETWEEN :expirationGroupingInit AND  :expirationGroupingEnd ");
        		
        	}
    		
    		SQLQuery query = session.createSQLQuery(stringQuery.toString());
        	
        	/**
        	 * Para agregar los parametro de acuerdo al filtro
        	 */
        	query.setLong("countryId", filter.getCountryId());
        	if( !isAllWo || isDealer)
        		query.setString("woAssigmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		query.setString("woAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        	}
        	
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		query.setTimestamp("expirationGroupingInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getExpirationGroupingInit() ).getTime() ) );
        		query.setTimestamp("expirationGroupingEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getExpirationGroupingEnd() ).getTime() ) );
        	} else if( isWithoutAgenda ){
        		query.setString("isWithoutAgendaAssignmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		query.setString("isWithoutAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        	}
        	
        	BigDecimal numberOfRecords = (BigDecimal) query.uniqueResult();
        	
        	return numberOfRecords.longValue();
        	
        }catch (Throwable ex) {
            log.error("== Error countWoForExpirationGrouping/WorkOrderDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina countWoForExpirationGrouping/WorkOrderDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getSaleDealersByWoAsignDealerIdAndCountry(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getSaleDealersByWoAsignDealerIdAndCountry(Long dealerId, Long countryId) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getSaleDealersByWoAsignDealerIdAndCountry/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            boolean isDealerId = dealerId != null && dealerId.longValue() > 0;
            StringBuffer queryPrincipal = new StringBuffer();
            queryPrincipal.append("select distinct(wo.saleDealer) from ");
            queryPrincipal.append(WorkOrder.class.getName());
            queryPrincipal.append(" wo ");
            queryPrincipal.append("where wo.country.id = :countryId  ");
            if( isDealerId )
            	queryPrincipal.append("and wo.dealerId = :dealerId ");
            
            queryPrincipal.append(" order by wo.saleDealer.dealerName asc ");
            Query query = session.createQuery( queryPrincipal.toString() );
           
            query.setLong("countryId", countryId); 
            if( isDealerId )
            	query.setLong("dealerId", dealerId);
            
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando getSaleDealersByWoAsignDealerIdAndCountry/WorkOrderDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSaleDealersByWoAsignDealerIdAndCountry/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByStateIbsWoCodeIbsWoCustCodeServiceCategoryCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Long getWorkOrderByStateIbsWoCodeIbsWoCustCodeServiceCategoryCode(String woIbsCode, String woCustCodeIbs, String categoryCode, Long woCountryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWorkOrderCanceledByIbsCodeCustCodeServiceCategoryCode/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(" select count(*) ");
            queryBuilder.append(" from ");
            queryBuilder.append(WorkOrder.class.getName());
            queryBuilder.append(" wo, ");
            queryBuilder.append(WorkOrderService.class.getName());
            queryBuilder.append(" wos ");
            queryBuilder.append(" where (wos.woId = wo.id and");
            queryBuilder.append(" wos.service.serviceCategory.serviceCategoryCode = :aCategoryCode) and");            
            queryBuilder.append(" wo.workorderStatusByActualStatusId.woStateCode <> :aStateCancel and");
            queryBuilder.append(" wo.workorderStatusByActualStatusId.woStateCode <> :aStateFinish and");
            queryBuilder.append(" wo.woCode <> :aWoIbsCode and");
            queryBuilder.append(" wo.country.id = :aWoCountryId and");            
            queryBuilder.append(" wo.customer.customerCode = :aWoCustCodeIbs");

            Query query = session.createQuery(queryBuilder.toString());
            query.setString( "aStateCancel", CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity() );
            query.setString( "aStateFinish", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity() );
            query.setString( "aWoIbsCode", woIbsCode );
            query.setString( "aWoCustCodeIbs", woCustCodeIbs );
            query.setLong( "aWoCountryId", woCountryCode );
            query.setString( "aCategoryCode", categoryCode );
            
            List<Long> objs = query.list();
            return objs.get(0);
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por getWorkOrderCanceledByIbsCodeCustCodeServiceCategoryCode==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderCanceledByIbsCodeCustCodeServiceCategoryCode/WorkOrderDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#countWoForJasper(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Long countWoForJasper(Long woId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio countWoForJasper/WorkOrderDAO ==");
		
		if(woId==null)
			return 0L;
		
		Session session = super.getSession();
		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select count(*) as countWoForJasper ");
			queryBuilder.append("from ");
			queryBuilder.append("postal_codes pc, ");
			queryBuilder.append("cities cit, ");
			queryBuilder.append("states stt, ");
			queryBuilder.append("customers c, ");
			
			queryBuilder.append("customers_class_types cct, ");
			
			queryBuilder.append("");
			queryBuilder.append("dealers d, ");
			queryBuilder.append("");
			queryBuilder.append("work_order_services wos, ");
			queryBuilder.append("services ser, ");
			queryBuilder.append("SERVICE_CATEGORIES sc, ");
			queryBuilder.append("");
			queryBuilder.append("work_orders wo, ");
			queryBuilder.append("WORK_ORDER_ASSIGNMENTS woas, ");
			queryBuilder.append("WORK_ORDER_AGENDAS woa ");
			queryBuilder.append("where ");
			queryBuilder.append("woas.id = woa.wo_assignments_id and ");
			queryBuilder.append("wo.id = woas.wo_id and ");
			queryBuilder.append("");
			queryBuilder.append("WO.SALE_DEALER_ID = d.ID and ");
			queryBuilder.append("");
			queryBuilder.append("WOS.WO_ID = WO.ID and ");
			queryBuilder.append("WOS.SERVICE_ID = SER.ID and ");
			queryBuilder.append("SER.SERVICE_CATEGORY_ID = SC.ID and ");
			queryBuilder.append("");
			queryBuilder.append("wo.customer_id = c.id and ");
			queryBuilder.append("C.CUST_TYPE_ID = cct.ID and ");
			queryBuilder.append("C.POSTAL_CODE_ID = PC.ID and ");
			queryBuilder.append("PC.CITY_ID = cit.ID and ");
			queryBuilder.append("cit.STATE_ID = STT.ID and ");
			queryBuilder.append("woa.status = 'S' and ");
			queryBuilder.append("WOAS.IS_ACTIVE = 'S' and ");
			queryBuilder.append("wo.id = ");
			queryBuilder.append(woId.toString());

			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("countWoForJasper",Hibernate.LONG);
			
			Long woCount = (Long)query.uniqueResult();
			return woCount;
		} catch (Throwable ex) {
			log.error("== Error consultando WorkOrder por countWoForJasper==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina countWoForJasper/WorkOrderDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByPreviusStatus(co.com.directv.sdii.model.pojo.WorkorderStatus)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WorkOrder> getWorkOrdersByCountryCustomerPostalCodeScheduleDateWOAddress(String countryIso2Code, String ibsCustomerCode, String postalCode, java.util.Date scheduleDate, String woAddressCode, String woAddress)
			throws DAOServiceException , DAOSQLException  {
		List<Object> listCuadruplas = null ;
		List<WorkOrder> listWorkOrders = null ;
		
		log.debug("== Inicio getWorkOrdersByCountryCustomerPostalCodeScheduleDateWOAddress/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            StringBuilder queryBuilder = new StringBuilder();
            StringBuilder queryOrder = new StringBuilder(" Order by wo.id desc ");
            
            queryBuilder.append("  from " + WorkOrder.class.getName() + " as wo ");
            queryBuilder.append("			INNER JOIN wo.country as co " );            
            queryBuilder.append("			INNER JOIN wo.customer as cu " );
            queryBuilder.append("			INNER JOIN wo.postalCode as pc " );
            queryBuilder.append("			INNER JOIN wo.workorderStatusByActualStatusId as wos " );
            queryBuilder.append(" WHERE wo.dealerId is not null ");
            queryBuilder.append("       and co.countryCode = :countryCode ");
            queryBuilder.append("       and cu.customerCode = :ibsCustomerCode ");
            queryBuilder.append("       and pc.postalCodeCode = :postalCode ");
            queryBuilder.append("       and wo.woAddressCode = :woAddressCode ");

            if ( scheduleDate != null ) {
               queryBuilder.append(" and trunc(wo.woProgrammingDate) = trunc(:woProgrammingDate) ");
            }else {
               //Validar que wo.woProgrammingDate sea minimo el dia de mañana  
               queryBuilder.append(" and (trunc(wo.woProgrammingDate) > trunc(sysdate) or wo.woProgrammingDate is null) ");	
            }
            
            queryBuilder.append(" and wos.woStateCode in ");
            queryBuilder.append("( '");
            queryBuilder.append( CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity() );
            queryBuilder.append("' , '");
            queryBuilder.append( CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity() );
            queryBuilder.append("' , '");
            queryBuilder.append( CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity() );
            queryBuilder.append("' , '");
            queryBuilder.append( CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity() );
            queryBuilder.append("' , '");
            queryBuilder.append( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity() );
            queryBuilder.append("' ) ");
            
            Query query = session.createQuery(queryBuilder.toString() + queryOrder.toString());
            if ( scheduleDate != null ) {
               query.setDate("woProgrammingDate", scheduleDate);
            } 
            query.setString("countryCode" , countryIso2Code );
            query.setString("ibsCustomerCode", ibsCustomerCode );
            query.setString("postalCode"  , postalCode );
            query.setString("woAddressCode"   , woAddressCode.trim() );
 
            listCuadruplas = query.list();
            listWorkOrders = new ArrayList<WorkOrder>() ;
            
            int i=0 ;
            Object[] arreglo = null ;
            while ( i<listCuadruplas.size() ) {
            	arreglo = (Object[])( listCuadruplas.get(i)) ;
            	listWorkOrders.add( (WorkOrder) arreglo[0] );
            	i++ ;
            }
            
            return listWorkOrders;
        } catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por getWorkOrdersByCountryCustomerPostalCodeScheduleDateWOAddress/WorkOrderDAO==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrdersByCountryCustomerPostalCodeScheduleDateWOAddress/WorkOrderDAO ==");
        }
	}

	@Override
	public int getDealerUsedCapacity(DealerWorkCapacityCriteria dealerWCCriteria) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio dealerWCCriteria/WorkOrderDAO ==");
		
		Session session = super.getSession();
		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT COUNT(distinct w.ID) woCount ");
			queryBuilder.append("  FROM WORK_ORDER_AGENDAS agenda ");
			queryBuilder.append("       INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.ID = agenda.WO_ASSIGNMENTS_ID ");
			queryBuilder.append("       INNER JOIN dealers d ON woa.DEALER_ID = d.ID ");
			queryBuilder.append("       INNER JOIN WORK_ORDERS w ON w.ID = woa.WO_ID ");
			queryBuilder.append("       INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			queryBuilder.append("       INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			queryBuilder.append("       INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			queryBuilder.append("       INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			queryBuilder.append("       INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			queryBuilder.append("       INNER JOIN WORK_ORDER_STATUS status ON status.ID = w.ACTUAL_STATUS_ID  ");
			queryBuilder.append("       inner join SERVICE_HOURS sh on sh.id = agenda.SERVICE_HOUR_ID  ");
			queryBuilder.append(" WHERE woa.IS_ACTIVE = :assigmentStatus ");
			queryBuilder.append("       AND agenda.STATUS = :agendaStatus ");
			queryBuilder.append("       AND w.COUNTRY_ID = :countryId ");
			queryBuilder.append("       AND agenda.AGENDATION_DATE BETWEEN :capacityDateInit AND :capacityDateEnd ");
			queryBuilder.append("       AND d.ID = :dealerId ");
			queryBuilder.append("       AND sh.id = :serviceHourId ");
			queryBuilder.append("       AND ssc.ID = :superCategoryId ");
			queryBuilder.append("       AND status.WO_STATE_CODE IN (:woStatusAssign,:woStatusReAssign,:woStatusSchedule,:woStatusReSchedule,:woStatusPending) ");

			SQLQuery query = session.createSQLQuery(queryBuilder.toString()).addScalar("woCount",Hibernate.LONG);
			query.setString("assigmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			query.setString("agendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
			query.setLong("countryId", dealerWCCriteria.getCountryId());
			query.setTimestamp("capacityDateInit", new Timestamp( UtilsBusiness.dateTo12am( dealerWCCriteria.getCapacityDate() ).getTime() ) );
			query.setTimestamp("capacityDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( dealerWCCriteria.getCapacityDate() ).getTime() ) );
			query.setLong("dealerId", dealerWCCriteria.getDealerId());
			query.setLong("serviceHourId", dealerWCCriteria.getServiceHourId());
			query.setLong("superCategoryId", dealerWCCriteria.getSuperCategoryServiceId());
			query.setString("woStatusAssign", CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
			query.setString("woStatusReAssign", CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
			query.setString("woStatusSchedule", CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
			query.setString("woStatusReSchedule", CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
			query.setString("woStatusPending", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
			
			Long woCount = (Long)query.uniqueResult();
			
			return woCount.intValue();
		} catch (Throwable ex) {
			log.error("== Error dealerWCCriteria/WorkOrderDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina dealerWCCriteria/WorkOrderDAO ==");
		}
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getDealerFromLastWoFromCustomer(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List<Dealer> getDealerFromLastWoFromCustomer(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getDealerFromLastWoFromCustomer/WorkOrderDAO ==");
		Session session = super.getSession();
		try {
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append(" SELECT word.dealer_id dealerId  ");
			queryBuilder.append("   FROM work_orders word  ");
			queryBuilder.append("  where word.id = (SELECT id ");
			queryBuilder.append(" 	                    FROM ( SELECT wo.id ");
			queryBuilder.append("                                 FROM WORK_ORDER_STATUS_HISTORIES wosh inner join work_orders wo on(wosh.wo_id = wo.id) ");
			queryBuilder.append("                                                             inner join CUSTOMERS cu on(wo.customer_id=cu.id) ");
			queryBuilder.append("                                                             inner join WORK_ORDER_STATUS wosta on(WOSH.WO_STATUS_ID=WOSTA.ID) ");
			queryBuilder.append("                                WHERE  wo.country_id = :countryCode ");
			queryBuilder.append("                                       and cu.customer_code = :ibsCustomerCode  ");
			queryBuilder.append("                                      and (WOSTA.WO_STATE_CODE = :woRealized "); 
			queryBuilder.append("                                 	      OR WOSTA.WO_STATE_CODE = :woFinished)  ");
			queryBuilder.append("                                ORDER BY wo.creation_date DESC ");
			queryBuilder.append(" 	                         ) ");
			queryBuilder.append("        				   WHERE rownum < 2) ");

			SQLQuery query = session.createSQLQuery(queryBuilder.toString()).addScalar("dealerId",Hibernate.LONG);
			query.setString("woRealized", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
			query.setString("woFinished", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
			query.setLong("countryCode", countryCode);
			query.setString("ibsCustomerCode", ibsCustomerCode);

			Long dealersIds = (Long) query.uniqueResult();
			if(dealersIds==null){
				return new ArrayList<Dealer>(); 
			}

			StringBuilder queryBuilderDealer = new StringBuilder();
			queryBuilderDealer.append(" from ");
			queryBuilderDealer.append(Dealer.class.getName());
			queryBuilderDealer.append(" de ");
			queryBuilderDealer.append("where ");
			queryBuilderDealer.append("de.id = :dealerId");

			Query queryDealer = session.createQuery(queryBuilderDealer.toString());
			queryDealer.setLong("dealerId", dealersIds);

			List<Dealer> dealer = queryDealer.list();
			return dealer;
		} catch (Throwable ex) {
			log.error("== Error consultando Dealers por getDealerFromLastWoFromCustomer==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerFromLastWoFromCustomer/WorkOrderDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getDealerFromLastWoFromCustomer(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List<Dealer> getDealerFromLastServiceInstallWoFromCustomer(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getDealerFromLastWoFromCustomer/WorkOrderDAO ==");
		Session session = super.getSession();
		try {
			
			StringBuilder queryBuilder = new StringBuilder();
			
			queryBuilder.append(" SELECT DISTINCT word.dealer_id AS dealerId ");
			queryBuilder.append("   FROM work_orders word ");
			queryBuilder.append("  WHERE word.id =(SELECT MAX (woss.id) ");
			queryBuilder.append("                                FROM work_orders woss ");
			queryBuilder.append("                              WHERE (woss.customer_id, woss.creation_date) = (SELECT cu.id customer_code, ");
			queryBuilder.append("                                                                                   MAX (creation_date) creation_date ");
			queryBuilder.append("                                                                              FROM work_orders wo INNER JOIN CUSTOMERS cu ON (wo.customer_id = cu.id) ");
			queryBuilder.append("                                                                                                  INNER JOIN WORK_ORDER_STATUS wosta ON (wosta.id = wo.actual_status_id) ");
			queryBuilder.append("                                                                             WHERE cu.customer_code = :ibsCustomerCode ");
			queryBuilder.append("                                                                                   AND (WOSTA.WO_STATE_CODE = :woRealized "); 
			queryBuilder.append("                                                                                        OR WOSTA.WO_STATE_CODE = :woFinished) ");
			queryBuilder.append("                                                                                   AND EXISTS(SELECT 1 "); 
			queryBuilder.append("                                                                                                FROM SERVICE_TYPES st INNER JOIN SERVICE_CATEGORIES sc ON (sc.SERVICE_TYPE_ID = st.ID) "); 
			queryBuilder.append("                                                                                                                      INNER JOIN SERVICES s ON (s.SERVICE_CATEGORY_ID = sc.ID) "); 
			queryBuilder.append("                                                                                                                      INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON (st.SERVICE_SUPER_CATEGORY_ID = ssc.ID) "); 
			queryBuilder.append("                                                                                                                      INNER JOIN work_order_SERVICEs wos ON (wos.service_id = s.ID) "); 
			queryBuilder.append("                                                                                                WHERE wos.wo_id = wo.id "); 
			queryBuilder.append("                                                                                                      AND ST.SERVICE_TYPE_code = :serviceTypeCode) ");
			queryBuilder.append("                                                                              GROUP BY cu.id) ");
			queryBuilder.append("                   AND word.country_id = :countryCode) ");
			                  
			SQLQuery query = session.createSQLQuery(queryBuilder.toString()).addScalar("dealerId",Hibernate.LONG);
			query.setString("woRealized", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
			query.setString("woFinished", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
			query.setLong("countryCode", countryCode);
			query.setString("ibsCustomerCode", ibsCustomerCode);
			query.setString("serviceTypeCode", CodesBusinessEntityEnum.WORKORDER_TYPE_INSTALL.getCodeEntity());
			

			List<Long> dealersIds = query.list();
			if(dealersIds==null){
				return new ArrayList<Dealer>(); 
			}
			if(dealersIds.size()==0 || dealersIds.isEmpty()){
				return new ArrayList<Dealer>();
			}

			StringBuilder queryBuilderDealer = new StringBuilder();
			queryBuilderDealer.append(" from ");
			queryBuilderDealer.append(Dealer.class.getName());
			queryBuilderDealer.append(" de ");
			queryBuilderDealer.append("where ");
			queryBuilderDealer.append("de.id = :dealerId");

			Query queryDealer = session.createQuery(queryBuilderDealer.toString());
			queryDealer.setLong("dealerId", dealersIds.get(0));

			List<Dealer> dealer = queryDealer.list();
			return dealer;
		} catch (Throwable ex) {
			log.error("== Error consultando Dealers por getDealerFromLastWoFromCustomer==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerFromLastWoFromCustomer/WorkOrderDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getLastWoFromCustomerFinisherOrRealized(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List<WorkOrder> getLastWoFromCustomerFinisherOrRealized(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getDealerFromLastWoFromCustomer/WorkOrderDAO ==");
		Session session = super.getSession();
		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT distinct word.id as workId ");
			queryBuilder.append("FROM WORK_ORDER_STATUS_HISTORIES wosh,");
			queryBuilder.append("  work_orders word ");
			queryBuilder.append("WHERE (WOSH.WO_STATUS_ID =");
			queryBuilder.append("  (SELECT WOSTA.ID FROM WORK_ORDER_STATUS wosta WHERE WOSTA.WO_STATE_CODE = :woRealized");
			queryBuilder.append("  )");
			queryBuilder.append("OR WOSH.WO_STATUS_ID =");
			queryBuilder.append("  (SELECT WOSTB.ID FROM WORK_ORDER_STATUS wostb WHERE WOSTB.WO_STATE_CODE = :woFinished");
			queryBuilder.append("  ) )");
			queryBuilder.append("AND wosh.wo_id =");
			queryBuilder.append("  (SELECT *");
			queryBuilder.append("  FROM");
			queryBuilder.append("    (SELECT wo.id");
			queryBuilder.append("    FROM work_orders wo");
			queryBuilder.append("    WHERE wo.customer_id =");
			queryBuilder.append("      ( SELECT cu.id FROM CUSTOMERS cu WHERE cu.customer_code = :cuCode");
			queryBuilder.append("      )");
			queryBuilder.append("    ORDER BY wo.creation_date DESC");
			queryBuilder.append("    )");
			queryBuilder.append("  WHERE rownum < 2");
			queryBuilder.append("  )");
			queryBuilder.append("AND wosh.wo_id = word.id ");            
			queryBuilder.append("AND word.country_id = :countryCode");

			SQLQuery query = session.createSQLQuery(queryBuilder.toString()).addScalar("workId",Hibernate.LONG);
			query.setString("woRealized", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
			query.setString("woFinished", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
			query.setString("cuCode", ibsCustomerCode);
			query.setLong("countryCode", countryCode);

			List<Long> woIds = query.list();
			if(woIds==null){
				return new ArrayList<WorkOrder>();
			}
			if(woIds.size()==0 || woIds.isEmpty()){
				return new ArrayList<WorkOrder>();
			}

			StringBuilder queryBuilderDealer = new StringBuilder();
			queryBuilderDealer.append(" from ");
			queryBuilderDealer.append(WorkOrder.class.getName());
			queryBuilderDealer.append(" wo ");
			queryBuilderDealer.append("where ");
			queryBuilderDealer.append("wo.id = :woId");

			Query queryDealer = session.createQuery(queryBuilderDealer.toString());
			queryDealer.setLong("woId", woIds.get(0));

			List<WorkOrder> dealer = queryDealer.list();
			return dealer;
		} catch (Throwable ex) {
			log.error("== Error consultando Dealers por getDealerFromLastWoFromCustomer==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerFromLastWoFromCustomer/WorkOrderDAO ==");
		}
	}		
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Object[]> getWorkOrdersAgendaByIdsAndCrewNotNull(List<Long> workOrderIds) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersAgendaByIdsAndCrewNotNull/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append("SELECT DISTINCT (wo.WO_CODE) ");
            strBuffer.append("FROM WORK_ORDERS wo ");
            strBuffer.append("WHERE wo.ID IN ("+ UtilsBusiness.longListToString(workOrderIds, ",") +") ");
            strBuffer.append("AND wo.ID NOT IN ( ");
            strBuffer.append("SELECT DISTINCT (asigment.WO_ID) ");
            strBuffer.append("FROM WORK_ORDER_ASSIGNMENTS asigment ");
            strBuffer.append("INNER JOIN WORK_ORDER_AGENDAS agenda ON agenda.WO_ASSIGNMENTS_ID = asigment.ID ");
            strBuffer.append("WHERE asigment.IS_ACTIVE = :asigmentActiveStatus ");
            strBuffer.append("AND agenda.STATUS = :woagendaActiveStatus ");
            strBuffer.append("AND asigment.CREW_ID IS NOT null ");
            strBuffer.append(") ");
            
            SQLQuery query = session.createSQLQuery(strBuffer.toString());
            query.setString("asigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            query.setString("woagendaActiveStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
            
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error getWorkOrdersAgendaByIdsAndCrewNotNull/WorkOrderDAO==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersAgendaByIdsAndCrewNotNull/WorkOrderDAO ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Object[]> getWorkOrdersActiveAgendaByIds(List<Long> workOrderIds) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersActiveAgendaByIds/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append("SELECT DISTINCT (wo.WO_CODE) ");
            strBuffer.append("FROM WORK_ORDERS wo ");
            strBuffer.append("WHERE wo.ID IN ("+ UtilsBusiness.longListToString(workOrderIds, ",") +") ");
            strBuffer.append("AND wo.ID NOT IN ( ");
            strBuffer.append("SELECT DISTINCT (asigment.WO_ID) ");
            strBuffer.append("FROM WORK_ORDER_ASSIGNMENTS asigment ");
            strBuffer.append("INNER JOIN WORK_ORDER_AGENDAS agenda ON agenda.WO_ASSIGNMENTS_ID = asigment.ID ");
            strBuffer.append("WHERE asigment.IS_ACTIVE = :asigmentActiveStatus ");
            strBuffer.append("AND agenda.STATUS = :woagendaActiveStatus ");
            strBuffer.append(") ");
            
            SQLQuery query = session.createSQLQuery(strBuffer.toString());
            query.setString("asigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            query.setString("woagendaActiveStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
            
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getWorkOrdersActiveAgendaByIds/WorkOrderDAO==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersActiveAgendaByIds/WorkOrderDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getLast90DaysWosByCustomerAndServiceTypeStatusInFinishedStatus(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getLast90DaysWosByCustomerAndServiceTypeStatusInFinishedStatus(Long customerId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getLast90DaysWosByCustomerAndServiceServiceStatusInFinishedStatus/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	
        	Calendar startDate = null;
			startDate = Calendar.getInstance();
			startDate.roll(Calendar.DAY_OF_YEAR, -90);
			
			Calendar endDate = null;
			endDate = Calendar.getInstance();
			
			stringQuery.append("SELECT count(1) ");
			stringQuery.append("FROM WORK_ORDERS w ");
			stringQuery.append("INNER JOIN WORK_ORDER_STATUS status on status.ID = w.ACTUAL_STATUS_ID ");
			stringQuery.append("INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append("INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append("INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append("WHERE ");
			stringQuery.append("w.CUSTOMER_ID = :customerId ");
			stringQuery.append("AND st.SERVICE_TYPE_CODE = :serviceTypeCode ");
			stringQuery.append("AND status.WO_STATE_CODE = :finishedStatusCode ");
			stringQuery.append("AND w.CREATION_DATE BETWEEN :startDate AND :endDate ");
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("customerId", customerId);
			query.setString("serviceTypeCode", CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE.getCodeEntity());
			query.setString("finishedStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
			query.setTimestamp("startDate", new Timestamp(  UtilsBusiness.dateTo12am( startDate.getTime() ).getTime() ) );
			query.setTimestamp("endDate", new Timestamp(  UtilsBusiness.dateTo12pm( endDate.getTime() ).getTime() ) );
			
			BigDecimal queryResponse = (BigDecimal) query.uniqueResult();
        	if( queryResponse != null )
        		return queryResponse.longValue();
        	return null;
        } catch (Throwable ex) {
            log.error("== Error getLast90DaysWosByCustomerAndServiceServiceStatusInFinishedStatus/WorkOrderDAO==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getLast90DaysWosByCustomerAndServiceServiceStatusInFinishedStatus/WorkOrderDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrdersForDealerTrayWithExpirationGrouping(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public WorkOrderResponse getWorkOrdersForDealerTrayWithExpirationGrouping(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio getWorkOrdersForDealerTrayWithExpirationGrouping/WorkOrderDAO ==");
        try {
        	Session session = super.getSession();
        	Calendar expirationGroupingCalendarInit = null;
        	Calendar expirationGroupingCalendarEnd = null;
        	//para el caso que soliciten todas las WO
        	boolean isExpirationGrouping = false;       
        	boolean isWithoutAgenda = false;
        	boolean isAllWo = false;
        	if( filter.getExpirationGroupingInit() != null && filter.getExpirationGroupingEnd() != null){
        		expirationGroupingCalendarInit = Calendar.getInstance();
        		expirationGroupingCalendarInit.setTime( filter.getExpirationGroupingInit() );
        		expirationGroupingCalendarEnd = Calendar.getInstance();
        		expirationGroupingCalendarEnd.setTime( filter.getExpirationGroupingEnd() );
        		if ( expirationGroupingCalendarInit.get(Calendar.YEAR) == 1901 && expirationGroupingCalendarEnd.get(Calendar.YEAR) == 1901 ){
        			isWithoutAgenda = true;
        		} else if ( expirationGroupingCalendarInit.get(Calendar.YEAR) == 1900 || expirationGroupingCalendarEnd.get(Calendar.YEAR) == 1900 ){
        			isAllWo = true;
        		} else {
        			isExpirationGrouping = true;
        		}
        	}
        	boolean isWoStatusIds = ( filter.getWoStatusIds() == null || filter.getWoStatusIds().size() == 0 ) ? false : true; 
        	boolean isDealer = ( filter.getDealersIds() == null || filter.getDealersIds().isEmpty() ) ? false : true;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCountQuery = new StringBuffer();
        	StringBuffer stringQueryInner = new StringBuffer();
        	StringBuffer stringQueryWhere = new StringBuffer();
        	
        	stringCountQuery.append("SELECT COUNT(DISTINCT W.ID) FROM WORK_ORDERS W ");
        	stringQuery.append("SELECT DISTINCT W.ID, W.CREATION_DATE FROM WORK_ORDERS W ");
        	/**
        	 * Para incluir inner
        	 */
        	//Siempre se debe hacer join con WORK_ORDER_ASSIGNMENTS y WORK_ORDER_AGENDAS para saber si estan activas o no
        	if( !isAllWo || isDealer )
        		stringQueryInner.append("INNER JOIN WORK_ORDER_ASSIGNMENTS WOA ON WOA.WO_ID = W.ID ");
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		stringQueryInner.append("INNER JOIN WORK_ORDER_AGENDAS WOAGENDA ON WOAGENDA.WO_ASSIGNMENTS_ID = WOA.ID ");
        	} 
    		/**
        	 * Para incluir las condiciones dependiendo de los parametros
        	 */
        	stringQueryWhere.append("WHERE W.COUNTRY_ID = :countryId ");
        	if( !isAllWo || isDealer )
        		stringQueryWhere.append("AND WOA.IS_ACTIVE = :woAssigmentStatus ");
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		stringQueryWhere.append("AND WOAGENDA.STATUS = :woAgendaStatus ");
        	} else if(isWithoutAgenda){
        		stringQueryWhere.append("AND W.ID NOT IN ( ");
        		stringQueryWhere.append("SELECT DISTINCT (asigment.WO_ID) ");
        		stringQueryWhere.append("FROM WORK_ORDER_ASSIGNMENTS asigment ");
        		stringQueryWhere.append("INNER JOIN WORK_ORDER_AGENDAS agenda ON agenda.WO_ASSIGNMENTS_ID = asigment.ID ");
        		stringQueryWhere.append("WHERE asigment.IS_ACTIVE = :isWithoutAgendaAssignmentStatus ");
        		stringQueryWhere.append("AND agenda.STATUS = :isWithoutAgendaStatus ) ");
        	}
    		
    		/**
        	 * Para incluir la condición del dealer
        	 */
    		if(isDealer){
    			stringQueryWhere.append("AND WOA.DEALER_ID IN (" + UtilsBusiness.longListToString( filter.getDealersIds(), "," ) + ") ");
        	}
    		if( isWoStatusIds ){
    			stringQueryWhere.append("AND W.ACTUAL_STATUS_ID IN (" + UtilsBusiness.longListToString( filter.getWoStatusIds(), "," ) + ") ");
        	}
        	/**
        	 * En caso que sea con fecha fe agendamiento se agrega el filtro
        	 */
    		if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
    			stringQueryWhere.append("AND WOAGENDA.AGENDATION_DATE BETWEEN :expirationGroupingInit AND  :expirationGroupingEnd ");
        		
        	}
    		stringQueryWhere.append("ORDER BY W.CREATION_DATE DESC ");
    		
    		SQLQuery query = session.createSQLQuery(stringQuery.toString() + stringQueryInner.toString() + stringQueryWhere.toString());
    		SQLQuery countQuery = session.createSQLQuery(stringCountQuery.toString() + stringQueryInner.toString() + stringQueryWhere.toString());
        	
        	/**
        	 * Para agregar los parametro de acuerdo al filtro
        	 */
        	query.setLong("countryId", filter.getCountryId());
        	countQuery.setLong("countryId", filter.getCountryId());
        	if( !isAllWo || isDealer ){
        		query.setString("woAssigmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		countQuery.setString("woAssigmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        	}
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		query.setString("woAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        		countQuery.setString("woAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        	}
        	
        	if( isExpirationGrouping && ( !isAllWo || !isWithoutAgenda ) ){
        		query.setTimestamp("expirationGroupingInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getExpirationGroupingInit() ).getTime() ) );
        		query.setTimestamp("expirationGroupingEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getExpirationGroupingEnd() ).getTime() ) );
        		
        		countQuery.setTimestamp("expirationGroupingInit", new Timestamp( UtilsBusiness.dateTo12am( filter.getExpirationGroupingInit() ).getTime() ) );
        		countQuery.setTimestamp("expirationGroupingEnd", new Timestamp( UtilsBusiness.dateTo12pm( filter.getExpirationGroupingEnd() ).getTime() ) );
        	} else if( isWithoutAgenda ){
        		query.setString("isWithoutAgendaAssignmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		query.setString("isWithoutAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        		
        		countQuery.setString("isWithoutAgendaAssignmentStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
        		countQuery.setString("isWithoutAgendaStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollectionInfo != null ){
        		BigDecimal numberOfRecords = (BigDecimal) countQuery.uniqueResult();
        		recordQty = numberOfRecords.longValue();			
				query.setFirstResult( requestCollectionInfo.getFirstResult() );
				query.setMaxResults( requestCollectionInfo.getMaxResults() );	
        	}
        	
        	WorkOrderResponse response = new WorkOrderResponse();
        	List<Object[]> list = query.list();
        	if( requestCollectionInfo != null )
        			populatePaginationInfo( response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), list.size(), recordQty.intValue() );
        	response.setWorkOrdersIds(list);
        	
        	return response;
        	
        }catch (Throwable ex) {
            log.error("== Error getWorkOrdersForDealerTrayWithExpirationGrouping/WorkOrderDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrdersForDealerTrayWithExpirationGrouping/WorkOrderDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getSaleDealersByWoAsignDealersIdAndCountry(java.util.List, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getSaleDealersByWoAsignDealersIdAndCountry(List<Long> dealerIds,Long countryId,
			String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSaleDealersByWoAsignDealersIdAndCountry/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            boolean isDealerId = dealerIds != null && !dealerIds.isEmpty();
            StringBuffer queryPrincipal = new StringBuffer();
            queryPrincipal.append("select d from ");
            queryPrincipal.append(Dealer.class.getName() +" d ");
            queryPrincipal.append(" where d.postalCode.city.state.country.id = :aCountryId");
            if( isDealerId )
            	queryPrincipal.append(" and d.id in ("+ UtilsBusiness.longListToString(dealerIds, ",") +") ");
            if( isSeller!=null ) 
            	queryPrincipal.append(" and d.isSeller = :aIsSeller " );
            if( isInstaller!=null )
            	queryPrincipal.append(" and d.isInstaller = :aIsInstaller ");
            queryPrincipal.append(" order by d.dealerName asc ");
            Query query = session.createQuery( queryPrincipal.toString() );           
            query.setLong("aCountryId", countryId);             
            if( isSeller!=null )
            	query.setString("aIsSeller", isSeller);
            if( isInstaller!=null)
            	query.setString("aIsInstaller", isInstaller);
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando getSaleDealersByWoAsignDealersIdAndCountry/WorkOrderDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSaleDealersByWoAsignDealersIdAndCountry/WorkOrderDAO ==");
        }
	}
	
	/*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByIDReport(java.lang.Long)
     */
     @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
     public List<ReportWorkOrderDTO> getWorkOrderByIDReport(Timestamp aSysdate,List<TechnologyVO> technologies,
          String activeStatus,
          String anIsResponsible,
          String anIsNotResponsible,
          String statusActiva,
          String statusAsignada,
          String statusAgendada,
          String statusReagendada,
          String statusRealizada,
          String statusFinalizada,
          String statusPendiente,
          String statusRechazada,
         String statusCancelada,
          String statusReasignada,
          String recordStatusLast,
          String elementClassDecoder,
          String workOrderTypeService,
          String woStatusWorkOrderTypeChangeHsp,
          String workOrderStatusRealized,
          String woManagmentElementClassDeco,
          String woManagmentElementClassSC,
          String woAttentionRer,
          String woAttentionReu,
          String elementIsSerialized,
          String mediaContactTypeTelepCode,
          String mediaContactTypeTelephWorkCode,
          String mediaContactTypeMobileCode,
          String mediaContactTypeMail,
          String mediaContactTypeFax,
          String codeUserControlTower,
          boolean isViewCustomerDocument,
          boolean isViewCustomerMail,
          String activeWorkOrderMark,
          String codeRequiredcontractMark,
          boolean isBuilding, long idWO) throws DAOServiceException, DAOSQLException {
           
      
           log.debug("== Inicio getWorkOrderByID/WorkOrderDAO ==");
      Session session = super.getSession();
      try {
          if (session == null) {
              throw new DAOServiceException( ErrorBusinessMessages.SESSION_NULL.getCode() );
          }
          StringBuilder queryBuffer = new StringBuilder();
          StringBuilder queryBufferSelect = new StringBuilder();
          StringBuilder queryBufferOrder = new StringBuilder();
          
          queryBufferSelect.append(" select  id, ");
          queryBufferSelect.append("         woCode, ");
          queryBufferSelect.append("         customerCode, ");
          queryBufferSelect.append("         customerDocument, ");          
          queryBufferSelect.append("         customerName, ");
          queryBufferSelect.append("         customerTypeName,  ");
          queryBufferSelect.append("         NVL(substr(datos.dealerName_depotCode,1,instr(datos.dealerName_depotCode,'|',1)-1), ' ') dealerName, ");
          queryBufferSelect.append("         NVL(substr(datos.dealerName_depotCode,instr(datos.dealerName_depotCode,'|',1)+1,length(datos.dealerName_depotCode)-instr(datos.dealerName_depotCode,'|',1)), ' ') depotCode, ");
          queryBufferSelect.append("         woStateName,  ");
          queryBufferSelect.append("         woStateCode,         ");
          queryBufferSelect.append("         ibs6StateName,   ");
          queryBufferSelect.append("         NVL(substr(datos.reason_dateLastModification,1,instr(datos.reason_dateLastModification,'|',1)-1), ' ') reason,  ");
          queryBufferSelect.append("         NVL(substr(datos.reason_dateLastModification,instr(datos.reason_dateLastModification,'|',1)+1,length(datos.reason_dateLastModification)-instr(datos.reason_dateLastModification,'|',1)), ' ') dateLastModification,  ");
          queryBufferSelect.append("         model,  ");
          queryBufferSelect.append("         creationDate,  ");
          queryBufferSelect.append("         importDate,  ");
          queryBufferSelect.append("         assignDealerDate,  ");
          queryBufferSelect.append("         jornada,  ");
          queryBufferSelect.append("         jornadaDate,   ");
          queryBufferSelect.append("         woProgrammingDate,   ");
          queryBufferSelect.append("         woRealizationDate,  ");
          queryBufferSelect.append("         finalizationDate,   ");
          queryBufferSelect.append("         woDescription,   ");
          queryBufferSelect.append("         woAction,   ");
          queryBufferSelect.append("         serviceTypeName,   ");
          queryBufferSelect.append("         serviceTypeCode,   ");
          queryBufferSelect.append("         serviceCode,   ");
          queryBufferSelect.append("         serviceName,   ");
          queryBufferSelect.append("         assignDealer,   ");
          queryBufferSelect.append("         assignDealerCode,  ");
          queryBufferSelect.append("         assignDealerCodeCode,  ");
          queryBufferSelect.append("         postalCodeCode,   ");
          queryBufferSelect.append("         stateName,   ");
          queryBufferSelect.append("         cityName,  ");
          queryBufferSelect.append("         NVL(substr(datos.respDoc_respName_ibsTech,1,instr(datos.respDoc_respName_ibsTech,'|',1)-1), ' ') responsableDoc,   ");
          queryBufferSelect.append("         NVL(substr(datos.respDoc_respName_ibsTech,instr(datos.respDoc_respName_ibsTech,'|',1)+1,instr(datos.respDoc_respName_ibsTech,'|',1,2) - instr(datos.respDoc_respName_ibsTech,'|',1) -1), ' ') responsableName,  ");
          queryBufferSelect.append("         helpers,  ");
          queryBufferSelect.append("         customerPhoneHome,  ");
          queryBufferSelect.append("         customerPhoneWork, ");
          queryBufferSelect.append("         customerCel,  ");
          queryBufferSelect.append("         customerMail,  ");          
          queryBufferSelect.append("         customerFax,  ");
          queryBufferSelect.append("         customerAddress,  ");               
          queryBufferSelect.append("         extraIndication,   ");
          queryBufferSelect.append("         contDecos,   ");
          queryBufferSelect.append("         previousVisits,   ");
          queryBufferSelect.append("         observationCompany,   ");
          queryBufferSelect.append("         retiredIRDSeries,  ");
          queryBufferSelect.append("         retiredSCSeries,   ");
          queryBufferSelect.append("         installedIRDSeries,    ");
          queryBufferSelect.append("         installedSCSeries,  ");
          queryBufferSelect.append("         NVL(substr(datos.dispatcher_y_loginDispatcher,1,instr(datos.dispatcher_y_loginDispatcher,'|',1)-1), ' ') dispatcher,    ");
          queryBufferSelect.append("         NVL(substr(datos.dispatcher_y_loginDispatcher,instr(datos.dispatcher_y_loginDispatcher,'|',1)+1,length(datos.dispatcher_y_loginDispatcher)-instr(datos.dispatcher_y_loginDispatcher,'|',1)), ' ') loginDispatcher,   ");
          queryBufferSelect.append("         NVL(substr(datos.respDoc_respName_ibsTech,instr(datos.respDoc_respName_ibsTech,'|',1,2)+1,length(datos.respDoc_respName_ibsTech)-instr(datos.respDoc_respName_ibsTech,'|',1, 2)), ' ') ibsTechnical,  ");
          queryBufferSelect.append("         zoneTypeName,   ");
          queryBufferSelect.append("         problemDescriptions,  ");
          queryBufferSelect.append("         contactPersonAgenda,   ");
          queryBufferSelect.append("         isRequiredContract,   ");          
          queryBufferSelect.append("         customerClassName, ");          
          queryBufferSelect.append("         serialNumber,  ");
          queryBufferSelect.append("         linkedSerialNumber,    ");                        
          queryBufferSelect.append("         optimusStatus,  ");                      
          queryBufferSelect.append("         optimusStatusDate,   ");                                 
          queryBufferSelect.append("         optimusDeclineReason,  ");            
          queryBufferSelect.append("         optimusDeclineDate ");
          
          queryBuffer.append("  FROM ( SELECT ");
		  queryBuffer.append("         TO_CHAR(w.ID) id, ");
		  queryBuffer.append("         w.WO_CODE woCode, ");
          queryBuffer.append("         NVL(TO_CHAR(w.CREATION_DATE,'dd/mm/yyyy hh:mi:ss am'),' ') creationDate,");
          queryBuffer.append("         NVL(TO_CHAR(w.IMPORT_DATE,'dd/mm/yyyy hh:mi:ss am'),' ') importDate,");
		  queryBuffer.append("         NVL(TO_CHAR(w.WO_PROGRAMMING_DATE,'dd/mm/yyyy hh:mi:ss am'),' ') woProgrammingDate,");
		  queryBuffer.append("         NVL(TO_CHAR(w.WO_REALIZATION_DATE,'dd/mm/yyyy hh:mi:ss am'),' ') woRealizationDate,");
          queryBuffer.append("         NVL(TO_CHAR(w.FINALIZATION_DATE,'dd/mm/yyyy hh:mi:ss am'),' ') finalizationDate,");		  
		  queryBuffer.append("         NVL(w.WO_DESCRIPTION,' ') woDescription,");
		  queryBuffer.append("         NVL(w.WO_ACTION,' ') woAction,");
		  queryBuffer.append("         NVL( TO_CHAR(w.OPTIMUS_STATUS_DATE,'dd/mm/yyyy hh:mi:ss am') , ' ' ) optimusStatusDate ,");
		  queryBuffer.append("         NVL((SELECT woStatus.WO_STATE_NAME FROM WORK_ORDER_STATUS woStatus WHERE woStatus.ID = w.ACTUAL_STATUS_ID),' ') woStateName,");		  
		  queryBuffer.append("         nvl((select wos.serial_number from work_order_types wot WHERE wo_type_code =:workOrderTypeDisconnection and wot.id = w.wo_type_id),' ') serialNumber,");
		  queryBuffer.append("         nvl((select wos.linked_serial_number from work_order_types wot WHERE wo_type_code =:workOrderTypeDisconnection and wot.id = w.wo_type_id),' ') linkedSerialNumber,");		  
		  queryBuffer.append("         NVL((select stype.SERVICE_TYPE_NAME from services s, service_categories scategory, service_types stype where s.id=wos.service_id and scategory.id=s.service_category_id and stype.id=scategory.service_type_id),' ') serviceTypeName,");
		  queryBuffer.append("         NVL((select stype.SERVICE_TYPE_CODE from services s, service_categories scategory, service_types stype where s.id=wos.service_id and scategory.id=s.service_category_id and stype.id=scategory.service_type_id),' ') serviceTypeCode,");		  
		  queryBuffer.append("         NVL((select s.SERVICE_CODE from services s where s.ID = wos.SERVICE_ID),' ') serviceCode,");
		  queryBuffer.append("         NVL((select s.SERVICE_NAME from services s where s.ID = wos.SERVICE_ID),' ') serviceName,");
		  queryBuffer.append("         NVL(cust.CUSTOMER_CODE,' ') customerCode,");
		  
		  if(isViewCustomerDocument){
			  queryBuffer.append("         NVL(CUST.DOCUMENT_NUMBER,' ') customerDocument,");  
		  }else{
			  queryBufferSelect.append("      ' ' customerDocument, ");  
		  }
		  
		  queryBuffer.append("         NVL(cust.FIRST_NAME ||' '|| cust.LAST_NAME,' ') customerName,");
		  queryBuffer.append("         NVL((select cust_types.CUSTOMER_TYPE_NAME FROM customer_class_types customer_class_types, customer_types cust_types WHERE customer_class_types.ID = cust.CUST_TYPE_ID and cust_types.ID = customer_class_types.customer_type_id),' ') customerTypeName,");
		  queryBuffer.append("         NVL((select SUBSTR(woIbsStatus.IBS_6_STATE_NAME,1,instr(woIbsStatus.IBS_6_STATE_NAME,'-',1)-1) from IBS_STATUS woIbsStatus where woIbsStatus.ID = w.IBS_ACTUAL_STATUS_ID),' ') woStateCode,");
		  queryBuffer.append("         NVL((select woIbsStatus.IBS_6_STATE_NAME from IBS_STATUS woIbsStatus where woIbsStatus.ID = w.IBS_ACTUAL_STATUS_ID),' ') ibs6StateName,");
		  queryBuffer.append("         NVL(ca.street_name || ' ' || NVL(ca.street_nr_first,' ') || ' ' || NVL(ca.street_nr_last,' ') ||' ' ||ca.street_sufix ||' ' ||ca.neighborhood ||' ' ||ca.extra_indications,' ') customerAddress,");
		  queryBuffer.append("         NVL(ca.extra_indications,' ') extraIndication,");
		  queryBuffer.append("         NVL(pc.POSTAL_CODE_CODE,' ') postalCodeCode,");
		  queryBuffer.append("         NVL((select citi.CITY_NAME from cities citi where citi.ID = pc.CITY_ID),' ') cityName,");
		  queryBuffer.append("         NVL((select state.STATE_NAME from cities citi, states state where citi.ID = pc.CITY_ID and state.ID=citi.STATE_ID),' ') stateName,");
		  queryBuffer.append("         NVL((select zonetype.ZONE_TYPE_NAME from zone_types zonetype where zonetype.ID=pc.ZONE_TYPE_ID),' ') zoneTypeName,");
		  queryBuffer.append("         NVL(TO_CHAR(wAssign.DEALER_ASSIGNMENT_DATE,'dd/mm/yyyy hh:mi:ss am'),' ') assignDealerDate,");		  
		  queryBuffer.append("         NVL((select TO_CHAR(woa.AGENDATION_DATE, 'dd/mm/yyyy hh:mi:ss am') ");
		  queryBuffer.append("              from WORK_ORDER_AGENDAS woa ");
		  queryBuffer.append("              where woa.WO_ASSIGNMENTS_ID=wAssign.ID ");
		  queryBuffer.append("              and woa.status = :activeStatus), ' ') jornadaDate,");		  
		  queryBuffer.append("         NVL((select WOA.CONTACT_PERSON ");
		  queryBuffer.append("              from WORK_ORDER_AGENDAS woa ");
		  queryBuffer.append("              where woa.WO_ASSIGNMENTS_ID=wAssign.ID");
		  queryBuffer.append("              AND woa.status = :activeStatus), ' ') contactPersonAgenda,");
		  queryBuffer.append("         NVL(TO_CHAR(crewHelpers.helpers),' ') helpers,");
		  queryBuffer.append("         NVL((select hora.SERVICE_HOURS_NAME ");
		  queryBuffer.append("              from SERVICE_HOURS hora, WORK_ORDER_AGENDAS woa");
		  queryBuffer.append("              where woa.WO_ASSIGNMENTS_ID=wAssign.ID");
		  queryBuffer.append("              and woa.status = :activeStatus and hora.ID=woa.SERVICE_HOUR_ID),' ') jornada,");
		  queryBuffer.append("         NVL(emp.DOCUMENT_NUMBER,' ') || '|' || NVL(emp.FIRST_NAME ||' ' ||emp.LAST_NAME,' ') || '|' ||  NVL(TO_CHAR(emp.IBS_TECHNICAL),' ') respDoc_respName_ibsTech,");
		  queryBuffer.append("         NVL((select U.NAME || '|' || U.LOGIN    ");
		  queryBuffer.append("              from WORK_ORDER_STATUS_HISTORIES wosh, USERS u");
		  queryBuffer.append("              where wosh.id =	(select max(wosh2.ID) ");
		  queryBuffer.append("                               FROM  WORK_ORDER_STATUS_HISTORIES wosh2 ");
		  queryBuffer.append("                               WHERE EXISTS (SELECT 1 FROM USERS u, ROLES r ");
		  queryBuffer.append("                                             WHERE wosh2.USER_ID = u.ID ");
		  queryBuffer.append("                                             and u.ROL_ID = r.ID ");
		  queryBuffer.append("                                             and r.ROL_CODE = :codeUserControlTower) ");
		  queryBuffer.append("                               and wosh2.WO_ID = wosh.WO_ID ");
		  queryBuffer.append("                               and wosh2.TYPE_CHANGE = :woStatusWorkOrderTypeChangeHsp) ");
		  queryBuffer.append("               and wosh.USER_ID = u.id ");
		  queryBuffer.append("               and wosh.WO_ID = w.id),' ') dispatcher_y_loginDispatcher, ");
		  queryBuffer.append("         NVL(dea.DEALER_NAME,dealer.DEALER_NAME) assignDealer, ");
		  queryBuffer.append("         NVL(dea.DEPOT_CODE,dealer.DEPOT_CODE) assignDealerCode,  ");
		  queryBuffer.append("         NVL(dea.DEALER_CODE,dealer.DEALER_CODE) assignDealerCodeCode,   ");
		  queryBuffer.append("         (select sale_dealer.DEALER_NAME || '|' || sale_dealer.DEPOT_CODE   ");
		  queryBuffer.append("         from DEALERS sale_dealer  ");
		  queryBuffer.append("         where	sale_dealer.ID = w.SALE_DEALER_ID) dealerName_depotCode,  ");
		  queryBuffer.append("         NVL((SELECT SUBSTR(c.PROBLEM_DESCRIPTION,1,399) PROBLEM_DESCRIPTION     ");
		  queryBuffer.append("              FROM CONTACTS c  ");
		  queryBuffer.append("              WHERE c.ID = (SELECT MAX(CMAX.ID) ");
		  queryBuffer.append("                            FROM CONTACTS CMAX");
		  queryBuffer.append("                            WHERE CMAX.WO_CODE = c.WO_CODE)");
		  queryBuffer.append("                            AND c.WO_CODE = w.WO_CODE),' ') problemDescriptions,");
		  queryBuffer.append("         NVL((select optimusStatus.DESCRIPTION    ");
		  queryBuffer.append("               from OPTIMUS_STATUS optimusStatus");
		  queryBuffer.append("               where optimusStatus.ID = w.OPTIMUS_STATUS_ID), ' ' ) optimusStatus ,");
		  queryBuffer.append("         NVL((select reason.WORKORDER_REASON_NAME || '|' || TO_CHAR(wosh.STATUS_DATE,'dd/mm/yyyy hh:mi:ss am') ");
		  queryBuffer.append("               from WORK_ORDER_STATUS_HISTORIES wosh, WORK_ORDER_REASONS reason");
		  queryBuffer.append("               where wosh.WO_ID = w.id");
		  queryBuffer.append("               AND wosh.WO_REASON_ID = reason.ID");
		  queryBuffer.append("               AND wosh.id =	(select max(wosh2.ID)");
		  queryBuffer.append("                               FROM  WORK_ORDER_STATUS_HISTORIES wosh2");
		  queryBuffer.append("                               WHERE EXISTS (select 1 from WORK_ORDER_STATUS workOrderStatus");
		  queryBuffer.append("                                             where workOrderStatus.WO_STATE_CODE IN ( :statusActiva ,:statusAsignada , :statusAgendada , :statusReagendada , :statusRealizada , :statusFinalizada , :statusPendiente , :statusRechazada , :statusCancelada , :statusReasignada)");
		  queryBuffer.append("                                             and workOrderStatus.ID = wosh2.WO_STATUS_ID)");
		  queryBuffer.append("                                             and wosh2.WO_ID = wosh.WO_ID)), ' ') reason_dateLastModification,");
		  queryBuffer.append("         NVL((SELECT (SELECT NAME  ");
		  queryBuffer.append("                       FROM TECHNOLOGIES ");
		  queryBuffer.append("                       WHERE ID = (SELECT MAX(TECHNOLOGY_ID) ");
		  queryBuffer.append("                                   FROM SHIPPING_ORDER_DETAILS sod");
		  queryBuffer.append("                                   WHERE sod.SHIPPING_ORDER_ID = shipping.ID");
		  
		  queryBuffer.append("                                   AND sod.TECHNOLOGY_ID IN ( ");

		  boolean flag = false;		  
		  for(TechnologyVO tvo : technologies){
		     if ( !flag ){
			    queryBuffer.append( tvo.getId() );
				flag = true;
			 }else{
			    queryBuffer.append(", " + tvo.getId() );
			 }
		  }
		  
		  queryBuffer.append(" ))) NAME ");
		  queryBuffer.append("               FROM SHIPPING_ORDERS shipping");
		  queryBuffer.append("               WHERE shipping.WO_ID=w.ID), ' ') model,"); 
		  queryBuffer.append("         NVL(TO_CHAR( (SELECT SUBSTR(wm_concat( CMC.MEDIA_CONTACT_VALUE),1,4000) customerPhoneHome  ");
		  queryBuffer.append("                        FROM CUSTOMER_MEDIA_CONTACTS CMC");
		  queryBuffer.append("                        INNER JOIN MEDIA_CONTACT_TYPES MCT");
		  queryBuffer.append("                        ON(CMC.CONTACT_TYPE_ID=MCT.ID)"); 		  
		  queryBuffer.append("                        WHERE MCT.MEDIA_CODE  =:mediaContactTypeTelepCode ");
		  queryBuffer.append("                        AND CMC.CUSTOMER_ID   =cust.ID )),' ') customerPhoneHome, ");
		  queryBuffer.append("         NVL(TO_CHAR( (SELECT SUBSTR(wm_concat( CMC.MEDIA_CONTACT_VALUE),1,4000) customerPhoneHome   ");
		  queryBuffer.append("                        FROM CUSTOMER_MEDIA_CONTACTS CMC "); 
		  queryBuffer.append("                        INNER JOIN MEDIA_CONTACT_TYPES MCT ON(CMC.CONTACT_TYPE_ID=MCT.ID)");
		  queryBuffer.append("                        WHERE MCT.MEDIA_CODE  =:mediaContactTypeTelephWorkCode AND CMC.CUSTOMER_ID =cust.ID )),' ') customerPhoneWork,"); 
		  queryBuffer.append("         NVL(TO_CHAR( (SELECT SUBSTR(wm_concat( CMC.MEDIA_CONTACT_VALUE),1,4000) customerPhoneHome    ");
		  queryBuffer.append("                        FROM CUSTOMER_MEDIA_CONTACTS CMC    "); 
		  queryBuffer.append("                        INNER JOIN MEDIA_CONTACT_TYPES MCT  ");
		  queryBuffer.append("                        ON(CMC.CONTACT_TYPE_ID=MCT.ID)   "); 
		  queryBuffer.append("                        WHERE MCT.MEDIA_CODE  =:mediaContactTypeMobileCode AND CMC.CUSTOMER_ID   =cust.ID )),' ') customerCel,");
		  
		  if(isViewCustomerMail){
		      queryBuffer.append("         NVL(TO_CHAR(  (SELECT SUBSTR(wm_concat( CMC.MEDIA_CONTACT_VALUE),1,4000) customerPhoneHome  "); 
		      queryBuffer.append("                        FROM CUSTOMER_MEDIA_CONTACTS CMC  ");
		      queryBuffer.append("                        INNER JOIN MEDIA_CONTACT_TYPES MCT ON(CMC.CONTACT_TYPE_ID=MCT.ID)  "); 		  
		      queryBuffer.append("                        WHERE MCT.MEDIA_CODE  =:mediaContactTypeMail AND CMC.CUSTOMER_ID  = cust.ID )),' ') customerMail, ");
		  }else{
			  queryBuffer.append("        ' ' customerMail, ");
		  }
		  
		  queryBuffer.append("         NVL(TO_CHAR(  (SELECT SUBSTR(wm_concat( CMC.MEDIA_CONTACT_VALUE),1,4000) customerPhoneHome    "); 
		  queryBuffer.append("                         FROM CUSTOMER_MEDIA_CONTACTS CMC  ");
		  queryBuffer.append("                         INNER JOIN MEDIA_CONTACT_TYPES MCT ON(CMC.CONTACT_TYPE_ID=MCT.ID) "); 
		  queryBuffer.append("                         WHERE MCT.MEDIA_CODE  =:mediaContactTypeFax AND CMC.CUSTOMER_ID   =cust.ID )),' ') customerFax, ");
		  //queryBuffer.append("         (SELECT /*+ parallel 6 */ COUNT(1) contDecos    "); 
		  queryBuffer.append("         (SELECT COUNT(1) contDecos    ");
		  queryBuffer.append("          FROM WAREHOUSE_ELEMENTS we   ");
		  queryBuffer.append("          INNER JOIN WAREHOUSES w ON(w.id=WE.WAREHOUSE_ID)  "); 
		  queryBuffer.append("          WHERE EXISTS (SELECT 1 FROM RECORD_STATUS R   ");
		  queryBuffer.append("                         WHERE R.ID=WE.RECORD_STATUS_ID AND R.RECORD_STATUS_CODE = :recordStatusLast) "); 
		  queryBuffer.append("          AND EXISTS (SELECT 1 FROM serialized s  ");
		  queryBuffer.append("                       INNER JOIN ELEMENTS e  ON(E.ID=S.ELEMENT_ID) "); 
		  queryBuffer.append("                       INNER JOIN ELEMENT_TYPES et  ON(Et.ID=E.ELEMENT_TYPE_ID) "); 
		  queryBuffer.append("                       INNER JOIN ELEMENT_MODELS em  ON(Em.ID=ET.ELEMENT_MODEL_ID) ");
		  queryBuffer.append("                       INNER JOIN ELEMENT_CLASSES ec  ON (Ec.ID = EM.ELEMENT_CLASS_ID ) "); 
		  queryBuffer.append("                       WHERE S.ELEMENT_ID=WE.SER_ID AND EC.ELEMENT_CLASS_CODE  =:elementClassDecoder) ");
		  queryBuffer.append("           AND cust.id  = W.CUSTOMER_ID) contDecos, "); 
		  queryBuffer.append("           (SELECT COUNT(1) previousVisits   ");
		  queryBuffer.append("            FROM WORK_ORDERS wsb  ");
		  queryBuffer.append("            WHERE Wsb.CUSTOMER_ID = Cust.ID  "); 
		  queryBuffer.append("            AND WSB.ID < w.ID   ");
		  queryBuffer.append("            AND EXISTS (SELECT 1 FROM WORK_ORDER_TYPES wtsb  "); 
		  queryBuffer.append("                        WHERE wtsb.ID=Wsb.WO_TYPE_ID  ");
		  queryBuffer.append("                        AND WTsb.WO_TYPE_CODE = :workOrderTypeService)  "); 
		  queryBuffer.append("            AND WSB.FINALIZATION_DATE >= (:aSysdate-30)) previousVisits,  ");
		  queryBuffer.append("          NVL( (SELECT woStatusHist1.DESCRIPTION observationCompany  ");
          queryBuffer.append("                FROM WORK_ORDER_STATUS_HISTORIES woStatusHist1  ");
		  queryBuffer.append("                WHERE woStatusHist1.id = (SELECT MAX(woStatusHist2.id) id ");
		  queryBuffer.append("                                          FROM WORK_ORDER_STATUS_HISTORIES woStatusHist2, WORK_ORDER_STATUS woStatus2 ");
          queryBuffer.append("                                          WHERE woStatusHist2.WO_STATUS_ID    =woStatus2.ID ");
		  queryBuffer.append("                                          AND woStatusHist2.DESCRIPTION IS NOT NULL ");
		  queryBuffer.append("                                          AND woStatusHist2.TYPE_CHANGE    =:woStatusWorkOrderTypeChangeHsp ");
          queryBuffer.append("                                          AND woStatus2.WO_STATE_CODE      =:workOrderStatusRealized  ");
		  queryBuffer.append("                                          AND W.ID                         =woStatusHist2.WO_ID ) ),' ') observationCompany, ");
		  queryBuffer.append("          NVL( (SELECT WME.SERIAL_CODE ");
          queryBuffer.append("                FROM WORK_ORDER_MANAGMENT_ELEMENTS wme ");
		  queryBuffer.append("                WHERE WME.work_order_service_id=wos.id ");
		  queryBuffer.append("                AND (WME.ELEMENT_CODE          =:woManagmentElementClassDeco OR WME.CODE_ELEMENT_CLASS_TYPE =:woManagmentElementClassDeco) ");
          queryBuffer.append("                AND WME.CODE_TYPE_MOVEMENT     =:woAttentionRer ");
		  queryBuffer.append("                AND WME.IS_SERIALIZED          =:elementIsSerialized  ),' ') retiredIRDSeries, ");
		  queryBuffer.append("          NVL( (SELECT WME.SERIAL_CODE   ");
          queryBuffer.append("                FROM WORK_ORDER_MANAGMENT_ELEMENTS wme ");
		  queryBuffer.append("                WHERE WME.work_order_service_id=wos.id ");
		  queryBuffer.append("                AND (WME.ELEMENT_CODE          =:woManagmentElementClassSC OR WME.CODE_ELEMENT_CLASS_TYPE =:woManagmentElementClassSC ) ");
          queryBuffer.append("                AND wme.CODE_TYPE_MOVEMENT     =:woAttentionRer ");
		  queryBuffer.append("                AND WME.IS_SERIALIZED          =:elementIsSerialized ),' ') retiredSCSeries, ");
		  queryBuffer.append("          NVL( (SELECT WME.SERIAL_CODE  ");
          queryBuffer.append("                FROM WORK_ORDER_MANAGMENT_ELEMENTS wme ");
		  queryBuffer.append("                WHERE WME.work_order_service_id=wos.id  ");
		  queryBuffer.append("                AND (WME.ELEMENT_CODE          =:woManagmentElementClassDeco OR WME.CODE_ELEMENT_CLASS_TYPE =:woManagmentElementClassDeco) ");
          queryBuffer.append("                AND wme.CODE_TYPE_MOVEMENT     =:woAttentionReu  ");
		  queryBuffer.append("                AND WME.IS_SERIALIZED          =:elementIsSerialized ),' ') installedIRDSeries, ");
		  queryBuffer.append("          NVL( (SELECT WME.SERIAL_CODE      ");
		  queryBuffer.append("                FROM WORK_ORDER_MANAGMENT_ELEMENTS wme ");
		  queryBuffer.append("                WHERE WME.work_order_service_id=wos.id ");
          queryBuffer.append("                AND (WME.ELEMENT_CODE          =:woManagmentElementClassSC OR WME.CODE_ELEMENT_CLASS_TYPE =:woManagmentElementClassSC ) ");
		  queryBuffer.append("                AND wme.CODE_TYPE_MOVEMENT     =:woAttentionReu ");
		  queryBuffer.append("                AND WME.IS_SERIALIZED          =:elementIsSerialized ),' ') installedSCSeries, ");
		  queryBuffer.append("          ( CASE WHEN w.optimus_decline_code IS NULL ");
		  queryBuffer.append("            THEN '  ' ");
          queryBuffer.append("            ELSE NVL (CONCAT( CONCAT( w.optimus_decline_code , ' - ' ) , w.optimus_decline_description ), ' ' ) ");
		  queryBuffer.append("            END) optimusDeclineReason , ");
		  queryBuffer.append("          NVL( (SELECT (SELECT cust_classes.customer_class_name ");
		  queryBuffer.append("                        FROM CUSTOMER_CLASSES cust_classes ");
		  queryBuffer.append("                        WHERE cust_classes.id = cust_class_types.customer_class_id)  ");
          queryBuffer.append("                FROM CUSTOMER_CLASS_TYPES cust_class_types  ");
		  queryBuffer.append("                WHERE cust_class_types.id = cust.cust_type_id ),' ') customerClassName , ");
		  queryBuffer.append("          NVL((SELECT DISTINCT 'S' work_order_mark_name ");
		  queryBuffer.append("               FROM WORK_ORDER_WORK_ORDER_MARKS wowom, WORK_ORDER_MARKS wom ");
		  queryBuffer.append("               WHERE WOWOM.WORK_ORDER_ID = w.id  ");
          queryBuffer.append("               AND wom.id = WOWOM.WORK_ORDER_MARK_ID  ");
		  queryBuffer.append("               AND WOM.CODE =:codeRequiredcontractMark ");
		  queryBuffer.append("               AND WOWOM.IS_ACTIVE = :activeWorkOrderMark),'N') isRequiredContract , ");
		  queryBuffer.append("          NVL( TO_CHAR( (SELECT WWM.USER_MARK_DATE  ");
		  queryBuffer.append("                         FROM WORK_ORDER_WORK_ORDER_MARKS WWM, WORK_ORDER_MARKS WM ");
		  queryBuffer.append("                         WHERE WM.ID = WWM.WORK_ORDER_MARK_ID ");
		  queryBuffer.append("                         AND W.ID        = WWM.WORK_ORDER_ID ");
		  queryBuffer.append("                         AND WM.CODE       = :codeWorkOrderMarkRejetByCrew  ");
		  queryBuffer.append("                         AND WWM.IS_ACTIVE = :activeStatus ), 'dd/mm/yyyy hh:mi:ss am'), ' ') optimusDeclineDate ");
		  queryBuffer.append("          FROM  WORK_ORDERS w  ");
		  queryBuffer.append("          INNER JOIN DEALERS dealer  ON dealer.ID = w.DEALER_ID ");
		  queryBuffer.append("          INNER JOIN WORK_ORDER_SERVICES wos ON w.ID = wos.WO_ID  ");
		  queryBuffer.append("          INNER JOIN CUSTOMERS cust ON cust.ID = w.CUSTOMER_ID  ");
		  queryBuffer.append("          INNER JOIN CUSTOMER_ADDRESSES ca ON ca.id = w.CUSTOMER_ADDRESSES_ID  ");
		  queryBuffer.append("          INNER JOIN postal_codes pc ON pc.ID=ca.POSTAL_CODE_ID   ");
		  queryBuffer.append("          LEFT JOIN WORK_ORDER_ASSIGNMENTS wAssign  ON  w.id =wAssign.WO_ID AND WASSIGN.IS_ACTIVE= :activeStatus  ");
		  queryBuffer.append("          LEFT JOIN WORK_ORDER_CREW_ASSIGMENTS crew  ON  crew.WO_ID     =W.ID AND crew.IS_ACTIVE= :activeStatus  ");
		  queryBuffer.append("          LEFT JOIN DEALERS dea ON dea.ID=wAssign.DEALER_ID    ");
		  queryBuffer.append("          LEFT JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID = crew.CREW_ID AND employeeCrew.IS_RESPONSIBLE= :anIsResponsible ");
		  queryBuffer.append("          LEFT JOIN EMPLOYEES emp ON emp.ID = employeeCrew.EMPLOYEE_ID ");
		  queryBuffer.append("          LEFT JOIN (SELECT crew.WO_ID, EMP.DOCUMENT_NUMBER || '-' || emp.FIRST_NAME || ' ' || emp.LAST_NAME helpers ");
		  queryBuffer.append("                     FROM WORK_ORDER_CREW_ASSIGMENTS crew ");
		  queryBuffer.append("	                   INNER JOIN EMPLOYEE_CREWS employeeCrew  ");
		  queryBuffer.append("                     ON employeeCrew.CREW_ID         = crew.CREW_ID " );
		  queryBuffer.append("		               AND employeeCrew.IS_RESPONSIBLE = :anIsNotResponsible ");
	      queryBuffer.append("		               INNER JOIN EMPLOYEES emp ");
	      queryBuffer.append("		               on EMP.id              = EMPLOYEECREW.EMPLOYEE_ID ");
	      queryBuffer.append("                     WHERE crew.IS_ACTIVE   = :activeStatus ) crewHelpers ON (w.id = crewHelpers.wo_id) ");
	      queryBuffer.append(" inner join WORK_ORDERS_EXPORT_DATA WOEXDA on (WOEXDA.WO_ID = w.ID and WOEXDA.WORK_ORDERS_EXPORT_ID = :expID ) ");
		  if (isBuilding){
              queryBufferOrder.append(" ORDER BY w.BUILDING DESC , w.WO_CODE ) datos ");
          }else{
              queryBufferOrder.append(" ORDER BY w.WO_CODE ) datos ");
          }		  

          
          Query query = session.createSQLQuery( queryBufferSelect.toString() + queryBuffer.toString() + queryBufferOrder.toString());
         
          //Parametros
          
          query.setParameter("expID", idWO);
          
          query.setParameter("aSysdate",   aSysdate, Hibernate.TIMESTAMP);

          
          query.setString("activeStatus", activeStatus );
          query.setString("anIsResponsible", anIsResponsible );
          query.setString("anIsNotResponsible", anIsNotResponsible);
          query.setString("statusActiva", statusActiva);
          query.setString("statusAsignada", statusAsignada );
          query.setString("statusAgendada", statusAgendada );
          query.setString("statusReagendada", statusReagendada );
          query.setString("statusRealizada", statusRealizada );
          query.setString("statusFinalizada", statusFinalizada );
          query.setString("statusPendiente", statusPendiente );
          query.setString("statusRechazada", statusRechazada );
          query.setString("statusCancelada", statusCancelada );
          query.setString("statusReasignada", statusReasignada );
          
          query.setString("recordStatusLast", recordStatusLast );
          query.setString("elementClassDecoder", elementClassDecoder );
          query.setString("workOrderTypeService", workOrderTypeService );
          query.setString("woStatusWorkOrderTypeChangeHsp", woStatusWorkOrderTypeChangeHsp );
          query.setString("workOrderStatusRealized", workOrderStatusRealized );
          query.setString("woManagmentElementClassDeco", woManagmentElementClassDeco );
          query.setString("woManagmentElementClassSC", woManagmentElementClassSC );
          query.setString("woAttentionRer", woAttentionRer );
          query.setString("woAttentionReu", woAttentionReu );
          query.setString("elementIsSerialized", elementIsSerialized );
          query.setString("mediaContactTypeTelepCode", mediaContactTypeTelepCode );
          query.setString("mediaContactTypeTelephWorkCode", mediaContactTypeTelephWorkCode );
          query.setString("mediaContactTypeMobileCode", mediaContactTypeMobileCode );
          query.setString("mediaContactTypeMail", mediaContactTypeMail );
          query.setString("mediaContactTypeFax", mediaContactTypeFax );
          
          query.setParameter("codeUserControlTower", codeUserControlTower, Hibernate.STRING);
          query.setParameter("woStatusWorkOrderTypeChangeHsp", woStatusWorkOrderTypeChangeHsp, Hibernate.STRING);
          
          query.setParameter("activeWorkOrderMark", activeWorkOrderMark, Hibernate.STRING);
          query.setParameter("codeRequiredcontractMark", codeRequiredcontractMark, Hibernate.STRING);
          
          query.setParameter("workOrderTypeDisconnection", CodesBusinessEntityEnum.WORKORDER_TYPE_DISCONNECTION.getCodeEntity(), Hibernate.STRING);
          
          query.setParameter("codeWorkOrderMarkRejetByCrew", CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REJECTED_BY_CREW.getCodeEntity(), Hibernate.STRING);
           ScrollableResults sr =  query.scroll(ScrollMode.FORWARD_ONLY);

           List<ReportWorkOrderDTO> lista = new ArrayList<ReportWorkOrderDTO>();
           while (sr.next()) {

                  lista.add( new ReportWorkOrderDTO(sr.get(0).toString()
                                                                               ,sr.get(1).toString()
                                                                               ,sr.get(2).toString()
                                                                               ,sr.get(3).toString()
                                                                               ,sr.get(4).toString()
                                                                               ,sr.get(5).toString()
                                                                               ,sr.get(6).toString()
                                                                               ,sr.get(7).toString()
                                                                               ,sr.get(8).toString()
                                                                               ,sr.get(9).toString()
                                                                               ,sr.get(10).toString()
                                                                               ,sr.get(11).toString()
                                                                               ,sr.get(12).toString()
                                                                               ,sr.get(13).toString()
                                                                               ,sr.get(14).toString()
                                                                               ,sr.get(15).toString()
                                                                               ,sr.get(16).toString()
                                                                               ,sr.get(17).toString()
                                                                               ,sr.get(18).toString()
                                                                               ,sr.get(19).toString()
                                                                               ,sr.get(20).toString()
                                                                               ,sr.get(21).toString()
                                                                               ,sr.get(22).toString()
                                                                               ,sr.get(23).toString()
                                                                               ,sr.get(24).toString()
                                                                               ,sr.get(25).toString()
                                                                               ,sr.get(26).toString()
                                                                               ,sr.get(27).toString()
                                                                               ,sr.get(28).toString()
                                                                               ,sr.get(29).toString()
                                                                               ,sr.get(30).toString()
                                                                               ,sr.get(31).toString()
                                                                               ,sr.get(32).toString()
                                                                               ,sr.get(33).toString()
                                                                               ,sr.get(34).toString()
                                                                               ,sr.get(35).toString()
                                                                               ,sr.get(36).toString()
                                                                               ,sr.get(37).toString()
                                                                               ,sr.get(38).toString()
                                                                               ,sr.get(39).toString()
                                                                               ,sr.get(40).toString()
                                                                               ,sr.get(41).toString()
                                                                              ,sr.get(42).toString()
                                                                               ,sr.get(43).toString()
                                                                               ,sr.get(44).toString()
                                                                               ,sr.get(45).toString()
                                                                               ,sr.get(46).toString()
                                                                               ,sr.get(47).toString()
                                                                               ,sr.get(48).toString()
                                                                               ,sr.get(49).toString()
                                                                               ,sr.get(50).toString()
                                                                               ,sr.get(51).toString()
                                                                               ,sr.get(52).toString()
                                                                               ,sr.get(53).toString()
                                                                              ,sr.get(54).toString()
                                                                               ,sr.get(55).toString()
                                                                               ,sr.get(56).toString()
                                                                               ,sr.get(57).toString()
                                                                               ,sr.get(58).toString()                                                                                 
                                                                               ,sr.get(59).toString()                                                                                  
                                                                               ,sr.get(60).toString()
                                                                               ,sr.get(61).toString()
                                                                               ,sr.get(62).toString()
                                                                               ,sr.get(63).toString()
                                                                               ,sr.get(64).toString()
                                                                               ));
                  

           
                  }
           sr.close();
          
          return lista;
      }catch (Throwable ex) {
          log.error("== Error consultando WorkOrder por ID==");
          throw this.manageException(ex);
      } finally {
          log.debug("== Termina getWorkOrderByID/WorkOrderDAO ==");
      }
  }

    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getWorkOrderByIDForTray(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getWorkOrderByIDForTray(	List<Long> woIDs, 
    												List<TechnologyVO> technologies, 
    												boolean isOrderByBuilding,
    												Long... idUsuario) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getWorkOrderByID/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException( ErrorBusinessMessages.SESSION_NULL.getCode() );
            }
            
            StringBuilder queryBuffer = new StringBuilder();
            queryBuffer.append("	SELECT w.ID,	");
            queryBuffer.append("	       w.WO_CODE,	");
            queryBuffer.append("	       w.WO_DESCRIPTION,	");
            queryBuffer.append("	       w.CREATION_DATE,	");
            queryBuffer.append("	       w.ACTUAL_STATUS_ID WO_STATE_ID,	");
            queryBuffer.append("	       WS.WO_STATE_CODE WO_STATE_CODE,	");
            queryBuffer.append("	       WS.WO_STATE_NAME WO_STATE_NAME,	");
            queryBuffer.append("	       HISTORY.ID AS REASON_ID,	");
            queryBuffer.append("	       HISTORY.WORKORDER_REASON_CODE AS REASON_CODE,	");
            queryBuffer.append("	       HISTORY.WORKORDER_REASON_NAME AS REASON_NAME,	");
            queryBuffer.append("	       w.POSTAL_CODE_ID POSTAL_CODE_ID,	");
            queryBuffer.append("	       PC.POSTAL_CODE_CODE POSTAL_CODE_CODE,	");
            queryBuffer.append("	       PC.POSTAL_CODE_NAME POSTAL_CODE_NAME,	");
            queryBuffer.append("	       CITY.CITY_NAME CITY_NAME,	");
            queryBuffer.append("	       STAT.STATE_NAME STATE_NAME,	");
            queryBuffer.append("	       CUST.CUSTOMER_CODE CUSTOMER_CODE,	");
            queryBuffer.append("	       CUST.FIRST_NAME CUSTOMER_FIRST_NAME,	");
            queryBuffer.append("	       CUST.LAST_NAME CUSTOMER_LAST_NAME,	");
            queryBuffer.append("	       c_a.STREET_NAME|| ' ' ||c_a.STREET_NR_FIRST|| ' ' ||c_a.STREET_NR_LAST|| ' ' ||c_a.STREET_SUFIX|| ' ' ||c_a.NEIGHBORHOOD|| ' ' ||c_a.EXTRA_INDICATIONS CUSTOMER_ADDRESS ,        ");			
            queryBuffer.append("	       CUST.DOCUMENT_NUMBER CUSTOMER_DOCUMENT_NUMBER,	");

            queryBuffer.append("        CUST_CLASS_TYPES.ID CUSTOMER_CLASS_TYPE_ID, ");
            queryBuffer.append("        CT.CUSTOMER_TYPE_CODE CUSTOMER_TYPE_CODE, ");
            queryBuffer.append("        CT.CUSTOMER_TYPE_NAME CUSTOMER_TYPE_NAME, ");
            
            queryBuffer.append("	       CUST.DOCUMENT_TYPE_ID CUS_DOC_TYPE_ID,	");
            queryBuffer.append("	       DT.DOCUMENT_TYPE_CODE CUS_DOC_TYPE_CODE,	");
            queryBuffer.append("	       DT.DOCUMENT_TYPE_NAME CUS_DOC_TYPE_NAME,	");
            queryBuffer.append("	       w.CUSTOMER_ID CUSTOMER_ID,	");
            queryBuffer.append("	       wo_agenda.AGENDATION_DATE WO_AGENDATION_DATE,	");
            queryBuffer.append("	       WO_AGENDA.SH_ID SERVICE_HOUR_ID,	");
            queryBuffer.append("	       WO_AGENDA.SERVICE_HOURS_NAME SERVICE_HOUR_NAME,	");
            queryBuffer.append("	       WO_ASSIG.CREW_ID ASSIGN_CREW_ID,	");
            queryBuffer.append("	       EMPL.FIRST_NAME || ' ' || EMPL.LAST_NAME RESPONSABLE_NAME,	");
            queryBuffer.append("	       EMPL.DOCUMENT_NUMBER RESPONSABLE_DOC_NUMBER,	");
            queryBuffer.append("	       PROG.PROGRAM_NAME PROGRAM_NAME,	");
            queryBuffer.append("	       (TRUNC (SYSDATE) - TRUNC (WO_ASSIG.DEALER_ASSIGNMENT_DATE)) DAYS_BETWEEN,	");
            queryBuffer.append("	       w.WO_TYPE_ID,	");
            queryBuffer.append("	       WO_TP.WO_TYPE_CODE WO_TYPE_CODE,	");
            queryBuffer.append("	       WO_TP.WO_TYPE_NAME WO_TYPE_NAME,	");
            queryBuffer.append("	       w.DEALER_ID DEALER_ID,	");
            queryBuffer.append("	       DEL.DEALER_CODE DEALER_CODE,	");
            queryBuffer.append("	       DEL.DEALER_NAME DEALER_NAME,	");
            queryBuffer.append("	       w.PROCESS_STATUS_ID,	");
            queryBuffer.append("	       (SELECT PS.STATUS_CODE	");
            queryBuffer.append("	          FROM WORK_ORDER_PROCESS_STATUS PS	");
            queryBuffer.append("	         WHERE PS.ID = w.PROCESS_STATUS_ID)	");
            queryBuffer.append("	          PROCESS_STATUS_CODE,	");
            queryBuffer.append("	       (SELECT wps.PROCESS_SOURCE_DESC	");
            queryBuffer.append("	          FROM WORK_ORDER_PROCESS_SOURCES wps	");
            queryBuffer.append("	         WHERE wps.ID = w.PROCESS_SOURCE_ID)	");
            queryBuffer.append("	          PROCESS_SOURCE_DESC,	");
            queryBuffer.append("	       w.AGENDATION_EXPIRED,	");
            queryBuffer.append("	       tec_wo.tec_code,	");
            queryBuffer.append("	       tec_wo.TEC_NAME,	");
            queryBuffer.append("		   tec_wo.SHIPPING_ORDER_CODE, ");
            queryBuffer.append("	       CUST.IS_MIGRATED IS_MIGRATED	");
            queryBuffer.append("	       , ZT.ZONE_TYPE_NAME,	");
            queryBuffer.append("	       to_char(WOM.WORK_ORDER_MARKSDTO)	");
            
            queryBuffer.append("	       , CITY_WO.CITY_NAME CITY_NAME_WO	");
            queryBuffer.append("	       , STAT_WO.STATE_NAME	STATE_NAME_WO ");
            queryBuffer.append("	       , W.POSTAL_CODE_ID POSTAL_CODE_ID_WO	");
            queryBuffer.append("	       , PC_WO.POSTAL_CODE_CODE POSTAL_CODE_CODE_WO	");
            queryBuffer.append("	       , PC_WO.POSTAL_CODE_NAME POSTAL_CODE_NAME_WO	");
            
            queryBuffer.append("	       , CUST_CLASS_TYPES.CUSTOMER_CLASS_ID CUSTOMER_CLASS_ID ");
			queryBuffer.append("	       , CUST_CLASS_TYPES.CUSTOMER_TYPE_ID CUSTOMER_TYPE_ID ");
			queryBuffer.append("	       , CCL.CUSTOMER_CLASS_NAME CUSTOMER_CLASS_NAME ");
			queryBuffer.append("	       , CCL.CUSTOMER_CLASS_CODE CUSTOMER_CLASS_CODE ");
			
			queryBuffer.append("	       , CCAT.ID CUSTOMER_CATEGORY_ID ");
			queryBuffer.append("	       , CCAT.CODE CUSTOMER_CATEGORY_CODE ");
			queryBuffer.append("	       , CCAT.NAME CUSTOMER_CATEGORY_NAME ");

			queryBuffer.append("		   , OS.CODE ");
			queryBuffer.append("		   , OS.DESCRIPTION ");
			queryBuffer.append("		   , w.OPTIMUS_DECLINE_CODE ");
			queryBuffer.append("		   , w.OPTIMUS_DECLINE_DESCRIPTION ");
            queryBuffer.append("	  FROM WORK_ORDERS w	");
            queryBuffer.append("	       INNER JOIN WORK_ORDER_STATUS WS	");
            queryBuffer.append("	          ON WS.ID = W.ACTUAL_STATUS_ID	");
            
            queryBuffer.append(" inner join CUSTOMER_ADDRESSES c_a on (c_a.id=W.CUSTOMER_ADDRESSES_ID) ");
            
            queryBuffer.append("	       INNER JOIN POSTAL_CODES PC	");
            queryBuffer.append("	          ON PC.ID = c_a.POSTAL_CODE_ID ");

            queryBuffer.append("	       INNER JOIN CITIES CITY	");
            queryBuffer.append("	          ON CITY.ID = PC.CITY_ID	");
            
            queryBuffer.append("	       INNER JOIN POSTAL_CODES PC_WO	");
            queryBuffer.append("	          ON PC_WO.ID = W.POSTAL_CODE_ID ");
            queryBuffer.append("	       INNER JOIN CITIES CITY_WO	");
            queryBuffer.append("	          ON CITY_WO.ID = PC_WO.CITY_ID	");
            queryBuffer.append("	       INNER JOIN STATES STAT_WO	");
            queryBuffer.append("	          ON STAT_WO.ID = CITY_WO.STATE_ID	");
            
            queryBuffer.append("	       INNER JOIN WORK_ORDER_TYPES WO_TP	");
            queryBuffer.append("	          ON WO_TP.ID = W.WO_TYPE_ID	");
            queryBuffer.append("	       INNER JOIN STATES STAT	");
            queryBuffer.append("	          ON STAT.ID = CITY.STATE_ID	");
            queryBuffer.append("	       INNER JOIN CUSTOMERS CUST	");
            queryBuffer.append("	          ON CUST.ID = W.CUSTOMER_ID	");
            
            queryBuffer.append("        INNER JOIN CUSTOMER_CLASS_TYPES CUST_CLASS_TYPES ");
            queryBuffer.append("           ON CUST_CLASS_TYPES.ID = CUST.CUST_TYPE_ID ");
			queryBuffer.append("        INNER JOIN CUSTOMER_TYPES CT ON (CT.ID=CUST_CLASS_TYPES.CUSTOMER_TYPE_ID) ");
			queryBuffer.append("        INNER JOIN CUSTOMER_CLASSES CCL ON (CCL.ID=CUST_CLASS_TYPES.CUSTOMER_CLASS_ID) ");
			queryBuffer.append("        INNER JOIN CUSTOMER_CATEGORY CCAT ON (CCAT.ID=CCL.CUSTOMER_CATEGORY_ID) ");
            
            queryBuffer.append("	       INNER JOIN DOCUMENT_TYPES DT	");
            queryBuffer.append("	          ON DT.ID = CUST.DOCUMENT_TYPE_ID	");
            queryBuffer.append("	       INNER JOIN WORK_ORDER_ASSIGNMENTS WO_ASSIG	");
            queryBuffer.append("	          ON WO_ASSIG.WO_ID = W.ID	");
            queryBuffer.append("	  		LEFT OUTER JOIN (SELECT WOA.ID,	");
            queryBuffer.append("	                                 WOA.WO_ASSIGNMENTS_ID,	");
            queryBuffer.append("	                                 SH.SERVICE_HOURS_NAME SERVICE_HOURS_NAME,	");
            queryBuffer.append("	                                 SH.ID SH_ID,	");
            queryBuffer.append("	                                 WOA.AGENDATION_DATE	");
            queryBuffer.append("	                            FROM WORK_ORDER_AGENDAS WOA	");
            queryBuffer.append("	                                 INNER JOIN SERVICE_HOURS SH	");
            queryBuffer.append("	                                    ON SH.ID = WOA.SERVICE_HOUR_ID	");
            queryBuffer.append("	                                    where 	");
            queryBuffer.append("	                                    WOA.STATUS =:woAgendaActive	");
            queryBuffer.append("	                                ) WO_AGENDA	");
            queryBuffer.append("	            ON WO_AGENDA.WO_ASSIGNMENTS_ID = WO_ASSIG.ID	");
            queryBuffer.append("	       LEFT OUTER JOIN WORK_ORDER_PROGRAMS PROG	");
            queryBuffer.append("	          ON PROG.ID = WO_ASSIG.PROGRAM_ID	");
            queryBuffer.append("	       LEFT OUTER JOIN DEALERS DEL	");
            queryBuffer.append("	          ON DEL.ID = WO_ASSIG.DEALER_ID	");
            queryBuffer.append("	       LEFT OUTER JOIN EMPLOYEE_CREWS EC	");
            queryBuffer.append("	          ON (EC.CREW_ID = WO_ASSIG.CREW_ID	");
            queryBuffer.append("	              AND EC.IS_RESPONSIBLE = :employeeIsResponsable)	");
            queryBuffer.append("	       LEFT OUTER JOIN EMPLOYEES EMPL	");
            queryBuffer.append("	          ON EMPL.ID = EC.EMPLOYEE_ID	");
            queryBuffer.append("	       LEFT OUTER JOIN (SELECT WR.ID,	");
            queryBuffer.append("	                               T.WO_ID,	");
            queryBuffer.append("	                               WR.WORKORDER_REASON_CODE,	");
            queryBuffer.append("	                               WR.WORKORDER_REASON_NAME	");
            queryBuffer.append("	                          FROM    WORK_ORDER_REASONS WR	");
            queryBuffer.append("	                               INNER JOIN WORK_ORDER_STATUS_HISTORIES WSH ON(WSH.WO_REASON_ID = WR.ID)	");
            queryBuffer.append("	                               INNER JOIN	");
            queryBuffer.append("	                                  (  SELECT WSH.WO_ID WO_ID,	");
            queryBuffer.append("	                                            MAX (WSH.ID) WSH_ID	");
            queryBuffer.append("	                                       FROM    WORK_ORDER_STATUS_HISTORIES WSH	");
            queryBuffer.append("	                                            INNER JOIN	");
            queryBuffer.append("	                                               WORK_ORDER_STATUS WS	");
            queryBuffer.append("	                                            ON WS.ID = WSH.WO_STATUS_ID	");
            queryBuffer.append("	                                      WHERE WS.WO_STATE_CODE IN	");
            queryBuffer.append("	                                               (:statusPending, :statusRejected, :statusReassign)	");
            queryBuffer.append("	                                   GROUP BY WSH.WO_ID) T ON (T.WSH_ID=WSH.ID)	");
            queryBuffer.append("	                               	");
            queryBuffer.append("	                               ) HISTORY	");
            queryBuffer.append("	          ON HISTORY.WO_ID = W.ID	");
            queryBuffer.append("	       LEFT OUTER JOIN (SELECT TEC1.CODE TEC_CODE,	");
            queryBuffer.append("	                               TEC1.NAME TEC_NAME,	");
            queryBuffer.append("	                               T1.WO_ID TEC_WO_ID,	");
            queryBuffer.append("	                               T1.SHIPPING_ORDER_CODE SHIPPING_ORDER_CODE	");
            queryBuffer.append("	                          FROM SHIPPING_ORDER_DETAILS ship_det	");
            queryBuffer.append("	                               INNER JOIN TECHNOLOGIES TEC1	");
            queryBuffer.append("	                                  ON TEC1.ID = SHIP_DET.TECHNOLOGY_ID	");
            queryBuffer.append("	                               INNER JOIN (  SELECT SO.WO_ID WO_ID, SO.SHIPPING_ORDER_CODE,	");
            queryBuffer.append("	                                                    MAX (SOD.ID) SOD_ID	");
            queryBuffer.append("	                                               FROM SHIPPING_ORDERS SO	");
            queryBuffer.append("	                                                    INNER JOIN SHIPPING_ORDER_DETAILS SOD	");
            queryBuffer.append("	                                                       ON SOD.SHIPPING_ORDER_ID =	");
            queryBuffer.append("	                                                             SO.ID	");
            queryBuffer.append("	                                                    INNER JOIN TECHNOLOGIES TEC	");
            queryBuffer.append("	                                                       ON TEC.ID =	");
            queryBuffer.append("	                                                             SOD.TECHNOLOGY_ID	");

            String tecCodeCondition = " 1<>1 ";
            for(TechnologyVO tvo: technologies){
            	tecCodeCondition+=" OR TEC.CODE = '"+tvo.getCode()+"' ";
            }
            
            queryBuffer.append("	                                              WHERE ( "+ tecCodeCondition +" )	");
            
            queryBuffer.append("	                                           GROUP BY SO.WO_ID, SO.SHIPPING_ORDER_CODE) t1	");
            queryBuffer.append("	                                  ON t1.SOD_ID = SHIP_DET.ID) tec_wo	");
            queryBuffer.append("	          ON tec_wo.TEC_WO_ID = w.id	");
            queryBuffer.append("	          left join ZONE_TYPES ZT on (ZT.ID=PC.ZONE_TYPE_ID)	");
//            queryBuffer.append("	          LEFT JOIN (SELECT WOWOM.WORK_ORDER_ID, ");
//            queryBuffer.append("	                  wm_concat (WOM.CODE||';'||WOM.NAME||';'||WOM.COLOR) over ( partition by WOWOM.WORK_ORDER_ID order by WOWOM.ID desc) WORK_ORDER_MARKSDTO  ");
//            queryBuffer.append("	             FROM    WORK_ORDER_MARKS WOM ");
//            queryBuffer.append("	                  INNER JOIN ");
//            queryBuffer.append("	                     WORK_ORDER_WORK_ORDER_MARKS WOWOM ");
//            queryBuffer.append("	                  ON (WOM.ID = WOWOM.WORK_ORDER_MARK_ID) ");
//            queryBuffer.append("	            WHERE WOWOM.IS_ACTIVE=:aWorkOrderMarkIsActive ");
//            queryBuffer.append("	                GROUP BY WOWOM.WORK_ORDER_ID) WOM ON(WOM.WORK_ORDER_ID=w.ID) ");
            queryBuffer.append("	          LEFT JOIN ( SELECT WOM.WORK_ORDER_ID, ");
            queryBuffer.append("	                             wm_concat (WOM.CODE || ';' || WOM.NAME || ';' || WOM.COLOR) WORK_ORDER_MARKSDTO ");
            queryBuffer.append("	                        FROM (SELECT WOWOM.WORK_ORDER_ID, ");
            queryBuffer.append("	                                     WOM.CODE, ");
            queryBuffer.append("	                                     WOM.NAME, ");
            queryBuffer.append("	                                     WOM.COLOR, ");
            queryBuffer.append("	                                     ROW_NUMBER () OVER (PARTITION BY WOWOM.WORK_ORDER_ID ORDER BY WOWOM.ID DESC) rn ");
            queryBuffer.append("	                                FROM WORK_ORDER_MARKS WOM INNER JOIN WORK_ORDER_WORK_ORDER_MARKS WOWOM ON (WOM.ID = WOWOM.WORK_ORDER_MARK_ID) ");
            queryBuffer.append("	                               WHERE WOWOM.IS_ACTIVE = :aWorkOrderMarkIsActive) WOM ");
            queryBuffer.append("	                       WHERE RN < 6 ");
            queryBuffer.append("	                       GROUP BY WOM.WORK_ORDER_ID) WOM ON (WOM.WORK_ORDER_ID = w.ID) ");

            queryBuffer.append(" 			 LEFT OUTER JOIN OPTIMUS_STATUS OS ON w.OPTIMUS_STATUS_ID = OS.ID ");
            
            Date actualDate=null;
            if(idUsuario!=null && idUsuario.length>0 && idUsuario[0]!=null && idUsuario[0]!=0){
            	actualDate=UtilsBusiness.getCurrentTimeZoneDateByUserId(Calendar.getInstance().getTime(), idUsuario[0], userDao);
            	queryBuffer.append("	 WHERE TRUNC (:actualDate) >= TRUNC (w.CREATION_DATE) and ");
            }else{
            	queryBuffer.append("	 WHERE ");
            }
            queryBuffer.append("	 WO_ASSIG.IS_ACTIVE = :woAssigmentActive	");
            queryBuffer.append(" AND " + UtilsBusiness.longListToOrInQuery(woIDs, "w.ID"));
            
            if (isOrderByBuilding){
            	queryBuffer.append("ORDER BY w.BUILDING DESC, w.CREATION_DATE DESC ");
            }else{
                queryBuffer.append(" ORDER BY w.CREATION_DATE DESC");
            }
            //UtilsBusiness.getCurrentTimeZoneDateByUserId(Calendar.getInstance().getTime(), userId, userDao);
            
            Query query = session.createSQLQuery(queryBuffer.toString());
            
            //Parametros
            if(idUsuario!=null && idUsuario.length>0 && idUsuario[0]!=null && idUsuario[0]!=0){
            	query.setDate("actualDate", actualDate);
            }
            query.setString("statusPending", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity() );
            query.setString("statusRejected", CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity() );
            //Reason de reasignación
            query.setString("statusReassign", CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity() );
            query.setString("woAssigmentActive", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );
            query.setString("woAgendaActive", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity() );
            query.setString("employeeIsResponsable", CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity() );
            query.setString("aWorkOrderMarkIsActive", CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity() );
                       
            //Ejecuta query      	
           	List<Object[]> list = query.list();
           	
            return list;

        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByID/WorkOrderDAO ==");
        }
    }
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal#getServiceWithWarranty(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@SuppressWarnings("rawtypes")
	public List getServiceWithWarranty(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getServiceWithWarranty/WorkOrderDAO ==");
		Session session = super.getSession();
		try {
			StringBuilder queryBuilder = new StringBuilder();
			
			queryBuilder.append(" SELECT seraw.service_code,stw.WARRRANTY_PERIOD,W.WO_REALIZATION_DATE,W.DEALER_ID ");
			queryBuilder.append("   FROM work_orders w  INNER JOIN WORK_ORDER_SERVICES woService  ON (w.id = WOSERVICE.WO_ID)  ");
			queryBuilder.append("                       INNER JOIN SERVICES s ON (s.id = WOSERVICE.SERVICE_ID)  ");          
			queryBuilder.append("                       INNER JOIN SERVICE_CATEGORIES sc ON (SC.ID = S.SERVICE_CATEGORY_ID) ");
			queryBuilder.append("                       INNER JOIN SERVICE_TYPES st ON (ST.ID = SC.SERVICE_TYPE_ID)      ");      
			queryBuilder.append(" 						INNER JOIN SERVICE_TYPE_WARRANTIES stw ON (STW.SERVICE_TYPE_ID = st.ID) ");
			queryBuilder.append("                       INNER JOIN SERVICE_WARRANTIES saw ON (stw.ID = SAW.SERVICE_TYPE_WARR_ID) ");           
			queryBuilder.append(" 		                INNER JOIN SERVICES seraw ON (seraw.id = SAW.SERVICE_ID) ");
			queryBuilder.append("  WHERE stw.country_id =  :countryCode ");
			queryBuilder.append("        AND w.id = ( (SELECT id ");
			queryBuilder.append("                        FROM (  SELECT wo.id ");
			queryBuilder.append("                                  FROM WORK_ORDER_STATUS_HISTORIES wosh INNER JOIN work_orders wo ON (wosh.wo_id = wo.id) ");
			queryBuilder.append(" 											                   INNER JOIN CUSTOMERS cu ON (wo.customer_id = cu.id) ");
			queryBuilder.append(" 															   INNER JOIN WORK_ORDER_STATUS wosta ON (WOSH.WO_STATUS_ID = WOSTA.ID) ");
			queryBuilder.append("                                 WHERE cu.customer_code = :cuCode ");
			queryBuilder.append(" 									    AND wo.country_id = :countryCode ");
			queryBuilder.append("                                       AND (WOSTA.WO_STATE_CODE =:woRealized "); 
			queryBuilder.append(" 										     OR WOSTA.WO_STATE_CODE =:woFinished) ");
			queryBuilder.append("                                 ORDER BY wo.creation_date DESC) ");
			queryBuilder.append("                       WHERE ROWNUM < 2)) ");

			SQLQuery query = session.createSQLQuery(queryBuilder.toString());
			query.setString("woRealized", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
			query.setString("woFinished", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
			query.setString("cuCode", ibsCustomerCode);
			query.setLong("countryCode", countryCode);

			List woServiceCode = query.list();
			if(woServiceCode==null){
				return new ArrayList<String>();
			}
			if(woServiceCode.size()==0 || woServiceCode.isEmpty()){
				return new ArrayList<String>();
			}

			return woServiceCode;
		} catch (Throwable ex) {
			log.error("== Error consultando Dealers por getServiceWithWarranty==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getServiceWithWarranty/WorkOrderDAO ==");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ReportsProductivityReportAndFileResponseDTO getReportsProductivityReport(ReportsComplyAndScheduleFilterDTO filter, 
			                                                                RequestCollectionInfo requestInfo,
			                                                                Long countryId,
				                                                            Date sysdate) throws DAOSQLException, DAOServiceException{
        try {
            log.debug("== Inicio getReportsComplyAndScheduleDTO/WorkOrderDAO ==");
            Session session = super.getSession();

            StringBuilder querySelect = new StringBuilder();
            StringBuilder queryFrom   = new StringBuilder();
            StringBuilder queryWhere   = new StringBuilder();
            StringBuilder queryOrder   = new StringBuilder();
            
            querySelect.append(" SELECT      C.CUSTOMER_CODE clientNumber, ");
            querySelect.append("             SO.SHIPPING_ORDER_CODE orderNumber, ");
            querySelect.append("             NVL (C.FIRST_NAME, '') || ' ' || NVL (C.LAST_NAME, '') clientName, ");
            querySelect.append("             CI.CITY_NAME city, ");
            querySelect.append("             W.CREATION_DATE creationDate, ");
            querySelect.append("             W.WO_CODE jobCard, ");
            querySelect.append("             NVL (S.SERVICE_CODE, '') || ' - ' || NVL (S.SERVICE_NAME, '') jobCardType, ");
            querySelect.append("             WS.WO_STATE_NAME jobIbsStatus, ");
            querySelect.append("             NVL (E.FIRST_NAME, '') || ' ' || NVL (E.LAST_NAME, '') technicalName, ");
            querySelect.append("             TRUNC (W.WO_REALIZATION_DATE) - TRUNC (W.CREATION_DATE) dayQty, ");
            querySelect.append("             W.WO_REALIZATION_DATE completationDate, ");
            querySelect.append("             C.CUSTOMER_ADDRESS address, ");
            querySelect.append("             to_char(mc.MEDIA_CONTACT_VALUE) telephone, ");
            querySelect.append("             W.WO_DESCRIPTION problemDescription, ");
            querySelect.append("             CASE WHEN SO.ID IS NOT NULL THEN ");
            querySelect.append("                       SOT.MODEL_NAME ");
            querySelect.append("                  ELSE ");
            querySelect.append("                       (SELECT ELEMD.MODEL_NAME  ");
            querySelect.append("                          FROM SERIALIZED ser INNER JOIN elements ele ON (ele.ID = ser.ELEMENT_ID) ");
            querySelect.append("                                              INNER JOIN ELEMENT_TYPES eletp ON (eletp.ID = ele.ELEMENT_TYPE_ID) ");
            querySelect.append("                                              INNER JOIN ELEMENT_MODELS elemd ON (elemd.ID = eletp.ELEMENT_MODEL_ID) ");
            querySelect.append("                         WHERE ser.SERIAL_CODE = WOS.SERIAL_NUMBER) ");
            querySelect.append("             END model, ");
            querySelect.append("             SOT.CODE product ");
            queryFrom.append("   FROM work_orders w INNER JOIN CUSTOMERS c ON (C.ID = W.CUSTOMER_ID) ");
            queryFrom.append("                      INNER JOIN COUNTRIES co ON (CO.ID = W.COUNTRY_ID) ");
            queryFrom.append("                                   INNER JOIN POSTAL_CODES pc ON (pc.ID = W.POSTAL_CODE_ID) ");
            queryFrom.append("                                   INNER JOIN CITIES ci ON (Ci.ID = PC.CITY_ID) ");
            queryFrom.append("                                   INNER JOIN WORK_ORDER_SERVICES wos ON (W.ID = WOS.WO_ID) ");
            queryFrom.append("                                   INNER JOIN SERVICES s ON (S.ID = WOS.SERVICE_ID) ");
            queryFrom.append("                                   INNER JOIN SERVICE_CATEGORIES sc ON (SC.ID = S.SERVICE_CATEGORY_ID)  ");
            queryFrom.append("                                   INNER JOIN SERVICE_TYPES st ON (ST.ID = SC.SERVICE_TYPE_ID)  ");
            queryFrom.append("                                   INNER JOIN WORK_ORDER_STATUS ws ON (WS.ID = W.ACTUAL_STATUS_ID) ");
            queryFrom.append("                                   LEFT JOIN SHIPPING_ORDERS so ON (w.id = SO.WO_ID) ");
            queryFrom.append("                                   LEFT JOIN WORK_ORDER_CREW_ASSIGMENTS WCA ON (W.ID = WCA.WO_ID AND WCA.IS_ACTIVE = :aActive) ");
            queryFrom.append("                                   LEFT JOIN CREWS cw ON (CW.ID = WCA.CREW_ID) ");
            queryFrom.append("                                   LEFT JOIN EMPLOYEE_CREWS ec ON (CW.ID = EC.CREW_ID AND EC.IS_RESPONSIBLE = :aActive) ");
            queryFrom.append("                                   LEFT JOIN EMPLOYEES e ON (e.ID = EC.EMPLOYEE_ID) ");
            queryFrom.append("                                   LEFT JOIN WORK_ORDER_ASSIGNMENTS wa ON (W.ID = WA.WO_ID AND WA.IS_ACTIVE = :aActive) ");
            queryFrom.append("                                   LEFT JOIN WORK_ORDER_AGENDAS woa ON (WA.ID = WOA.WO_ASSIGNMENTS_ID AND WOA.STATUS = :aActive) ");
            queryFrom.append("                                   LEFT JOIN (  SELECT CMC.CUSTOMER_ID id, ");
            queryFrom.append("                                                                  substr(wm_concat (CMC.MEDIA_CONTACT_VALUE),1,4000) ");
            queryFrom.append("                                                                  MEDIA_CONTACT_VALUE ");
            queryFrom.append("                                                        FROM  CUSTOMER_MEDIA_CONTACTS cmc INNER JOIN CONTACT_TYPES ct ON (CT.ID = CMC.CONTACT_TYPE_ID) ");
            queryFrom.append("                                                       GROUP BY CMC.CUSTOMER_ID) mc ON (mc.id = C.ID) ");
            queryFrom.append("                                   LEFT JOIN (SELECT SHIP_DET.SHIPPING_ORDER_ID ID,  ");
            queryFrom.append("                                                     TEC1.CODE,  ");
            queryFrom.append("                                                     S.MODEL_NAME ");
            queryFrom.append("                                                FROM SHIPPING_ORDER_DETAILS ship_det INNER JOIN TECHNOLOGIES TEC1 ON (TEC1.ID = SHIP_DET.TECHNOLOGY_ID) ");
            queryFrom.append("                                                                                    INNER JOIN (  SELECT SO.WO_ID WO_ID, ");
            queryFrom.append("                                                                                                         SO.SHIPPING_ORDER_CODE,  ");
            queryFrom.append("                                                                                                         MAX (SOD.ID) SOD_ID ");
            queryFrom.append("                                                                                                    FROM SHIPPING_ORDERS SO INNER JOIN SHIPPING_ORDER_DETAILS SOD ON(SOD.SHIPPING_ORDER_ID = SO.ID) ");
            queryFrom.append("                                                                                                                           INNER JOIN TECHNOLOGIES TEC ON(TEC.ID = SOD.TECHNOLOGY_ID) ");
            
            List<Technology> technologies=this.technologyDAO.getAllIRDTechnologies();
            String tecCodeCondition=" 1<>1 ";
            
            for(Technology t : technologies){
            	tecCodeCondition += " OR TEC.CODE = '"+t.getCode()+"' ";
            }

            queryFrom.append("                                                                                                   WHERE (  "+tecCodeCondition+"  ) ");
            queryFrom.append("                                                                                                   GROUP BY SO.WO_ID, SO.SHIPPING_ORDER_CODE) t1 ON (t1.SOD_ID = SHIP_DET.ID) ");
            queryFrom.append("                                                                                    INNER JOIN SHIPP_ORDER_IBS_ELEMENT_MODELS s ON (S.MODEL_CODE = SHIP_DET.IBS_MODEL_CODE)) SOT ON(SOT.ID=SO.ID) ");

            queryWhere.append(" WHERE CO.ID=:aCountryId ");
            queryWhere.append("       AND (WS.WO_STATE_CODE=:aStatusRealized OR WS.WO_STATE_CODE=:aStatusFinished) ");
            queryWhere.append("       AND W.WO_REALIZATION_DATE >= :beginDate ");
            queryWhere.append("       AND W.WO_REALIZATION_DATE <= :endDate ");
            
            queryWhere.append(" order by W.CREATION_DATE asc,w.id ");
            
            Query query = session.createSQLQuery(querySelect.toString() + queryFrom.toString() + queryWhere.toString() + queryOrder.toString())
								.addScalar("clientNumber",Hibernate.STRING)
								.addScalar("orderNumber",Hibernate.STRING)
								.addScalar("clientName",Hibernate.STRING)
								.addScalar("city",Hibernate.STRING)
								.addScalar("creationDate",Hibernate.DATE)
								.addScalar("jobCard",Hibernate.STRING)
								.addScalar("jobCardType",Hibernate.STRING)
								.addScalar("jobIbsStatus",Hibernate.STRING)
								.addScalar("technicalName",Hibernate.STRING)
								.addScalar("dayQty",Hibernate.LONG)
								.addScalar("completationDate",Hibernate.DATE)
								.addScalar("address",Hibernate.STRING)
								.addScalar("telephone",Hibernate.STRING)
								.addScalar("problemDescription",Hibernate.STRING)
								.addScalar("model",Hibernate.STRING)
								.addScalar("product",Hibernate.STRING)
                                .setResultTransformer(Transformers.aliasToBean(ReportsProductivityReportDTO.class));
            
            query.setParameter("beginDate", new Timestamp(UtilsBusiness.dateTo12am(filter.getBeginDate()).getTime()), Hibernate.TIMESTAMP);
            query.setParameter("endDate",   new Timestamp(UtilsBusiness.dateTo12pm(filter.getEndDate()  ).getTime()), Hibernate.TIMESTAMP);
            query.setParameter("aStatusRealized", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aStatusFinished", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aActive", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aCountryId", countryId, Hibernate.LONG);
            
            Long recordQty = 0L;
        	if( requestInfo != null ){	
        		Query countQuery = session.createSQLQuery(" select count(*) " + queryFrom.toString() + queryWhere.toString());
        		
        		countQuery.setParameter("beginDate", new Timestamp(UtilsBusiness.dateTo12am(filter.getBeginDate()).getTime()), Hibernate.TIMESTAMP);
        		countQuery.setParameter("endDate",   new Timestamp(UtilsBusiness.dateTo12pm(filter.getEndDate()  ).getTime()), Hibernate.TIMESTAMP);
        		countQuery.setParameter("aStatusRealized", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("aStatusFinished", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("aActive", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("aCountryId", countryId, Hibernate.LONG);
                
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestInfo.getFirstResult() );
				query.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	
        	ReportsProductivityReportAndFileResponseDTO response = new ReportsProductivityReportAndFileResponseDTO();
        	List<ReportsProductivityReportDTO> responseList = query.list();
        	if( requestInfo != null )
				populatePaginationInfo( response, requestInfo.getPageSize(), requestInfo.getPageIndex(), responseList.size(), recordQty.intValue() );
        	response.setReportsProductivityReportDTOList(responseList);
        	return response;
        }catch (Throwable ex) {
            log.error("== Error getReportsComplyAndScheduleDTO==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReportsComplyAndScheduleDTO/WorkOrderDAO ==");
        }
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(ReportsPendingServicesByDateFilterDTO filter, 
			                                                                      RequestCollectionInfo requestInfo,
			                                                                      Long countryId,
					                                                              Date sysdate) throws DAOSQLException, DAOServiceException{
		List<String> serviceCodes = null;
		try {
            log.debug("== Inicio getReportsPendingServicesByDate/WorkOrderDAO ==");
            Session session = super.getSession();
            
            StringBuilder querySelect = new StringBuilder();
            StringBuilder queryFrom   = new StringBuilder();
            StringBuilder queryWhere   = new StringBuilder();
            StringBuilder queryOrder   = new StringBuilder();
            
            querySelect.append(" SELECT      C.CUSTOMER_CODE clientNumber, ");
            querySelect.append("             SO.SHIPPING_ORDER_CODE orderNumber, ");
            querySelect.append("             NVL (C.FIRST_NAME, '') || ' ' || NVL (C.LAST_NAME, '') clientName, ");
            querySelect.append("             CI.CITY_NAME city, ");
            querySelect.append("             W.CREATION_DATE creationDate, ");
            querySelect.append("             W.WO_CODE jobCard, ");
            querySelect.append("             NVL (S.SERVICE_CODE, '') || ' - ' || NVL (S.SERVICE_NAME, '') jobCardType, ");
            querySelect.append("             WS.WO_STATE_NAME jobCardStatus, ");
            querySelect.append("             NVL (E.FIRST_NAME, '') || ' ' || NVL (E.LAST_NAME, '') technicalName, ");
            querySelect.append("             TRUNC (:aSysdate) - TRUNC (W.CREATION_DATE) dayQty, ");
            querySelect.append("             WOA.AGENDATION_DATE appointment, ");
            querySelect.append("             C.CUSTOMER_ADDRESS address, ");
            querySelect.append("             to_char(mc.MEDIA_CONTACT_VALUE) telephone, ");            
            querySelect.append("             W.WO_DESCRIPTION problemDescription, ");
            querySelect.append("             CASE WHEN SO.ID IS NOT NULL THEN ");
            querySelect.append("                       SOT.MODEL_NAME ");
            querySelect.append("                  ELSE ");
            querySelect.append("                       (SELECT ELEMD.MODEL_NAME  ");
            querySelect.append("                          FROM SERIALIZED ser INNER JOIN elements ele ON (ele.ID = ser.ELEMENT_ID) ");
            querySelect.append("                                              INNER JOIN ELEMENT_TYPES eletp ON (eletp.ID = ele.ELEMENT_TYPE_ID) ");
            querySelect.append("                                              INNER JOIN ELEMENT_MODELS elemd ON (elemd.ID = eletp.ELEMENT_MODEL_ID) ");
            querySelect.append("                         WHERE ser.SERIAL_CODE = WOS.SERIAL_NUMBER) ");
            querySelect.append("             END model, ");
            querySelect.append("             SOT.CODE product, ");
            querySelect.append("             WA.DEALER_ASSIGNMENT_DATE allocationDate ");
            queryFrom.append("   FROM work_orders w INNER JOIN CUSTOMERS c ON (C.ID = W.CUSTOMER_ID) ");
            queryFrom.append("                      INNER JOIN COUNTRIES co ON (CO.ID = W.COUNTRY_ID) ");
            queryFrom.append("                                   INNER JOIN POSTAL_CODES pc ON (pc.ID = W.POSTAL_CODE_ID) ");
            queryFrom.append("                                   INNER JOIN CITIES ci ON (Ci.ID = PC.CITY_ID) ");
            queryFrom.append("                                   INNER JOIN WORK_ORDER_SERVICES wos ON (W.ID = WOS.WO_ID) ");
            queryFrom.append("                                   INNER JOIN SERVICES s ON (S.ID = WOS.SERVICE_ID) ");
            queryFrom.append("                                   INNER JOIN SERVICE_CATEGORIES sc ON (SC.ID = S.SERVICE_CATEGORY_ID)  ");
            queryFrom.append("                                   INNER JOIN SERVICE_TYPES st ON (ST.ID = SC.SERVICE_TYPE_ID)  ");
            queryFrom.append("                                   INNER JOIN WORK_ORDER_STATUS ws ON (WS.ID = W.ACTUAL_STATUS_ID) ");
            queryFrom.append("                                   LEFT JOIN SHIPPING_ORDERS so ON (w.id = SO.WO_ID) ");
            queryFrom.append("                                   LEFT JOIN WORK_ORDER_CREW_ASSIGMENTS WCA ON (W.ID = WCA.WO_ID AND WCA.IS_ACTIVE = :aActive) ");
            queryFrom.append("                                   LEFT JOIN CREWS cw ON (CW.ID = WCA.CREW_ID) ");
            queryFrom.append("                                   LEFT JOIN EMPLOYEE_CREWS ec ON (CW.ID = EC.CREW_ID AND EC.IS_RESPONSIBLE = :aActive) ");
            queryFrom.append("                                   LEFT JOIN EMPLOYEES e ON (e.ID = EC.EMPLOYEE_ID) ");
            queryFrom.append("                                   LEFT JOIN WORK_ORDER_ASSIGNMENTS wa ON (W.ID = WA.WO_ID AND WA.IS_ACTIVE = :aActive) ");
            queryFrom.append("                                   LEFT JOIN WORK_ORDER_AGENDAS woa ON (WA.ID = WOA.WO_ASSIGNMENTS_ID AND WOA.STATUS = :aActive) ");
            queryFrom.append("                                   LEFT JOIN (  SELECT CMC.CUSTOMER_ID id, ");
            queryFrom.append("                                                                  substr(wm_concat (CMC.MEDIA_CONTACT_VALUE),1,4000) ");
            queryFrom.append("                                                                  MEDIA_CONTACT_VALUE ");
            queryFrom.append("                                                        FROM  CUSTOMER_MEDIA_CONTACTS cmc INNER JOIN CONTACT_TYPES ct ON (CT.ID = CMC.CONTACT_TYPE_ID) ");
            queryFrom.append("                                                       GROUP BY CMC.CUSTOMER_ID) mc ON (mc.id = C.ID) ");
            queryFrom.append("                                   LEFT JOIN (SELECT SHIP_DET.SHIPPING_ORDER_ID ID,  ");
            queryFrom.append("                                                     TEC1.CODE,  ");
            queryFrom.append("                                                     S.MODEL_NAME ");
            queryFrom.append("                                                FROM SHIPPING_ORDER_DETAILS ship_det INNER JOIN TECHNOLOGIES TEC1 ON (TEC1.ID = SHIP_DET.TECHNOLOGY_ID) ");
            queryFrom.append("                                                                                    INNER JOIN (  SELECT SO.WO_ID WO_ID, ");
            queryFrom.append("                                                                                                         SO.SHIPPING_ORDER_CODE,  ");
            queryFrom.append("                                                                                                         MAX (SOD.ID) SOD_ID ");
            queryFrom.append("                                                                                                    FROM SHIPPING_ORDERS SO INNER JOIN SHIPPING_ORDER_DETAILS SOD ON(SOD.SHIPPING_ORDER_ID = SO.ID) ");
            queryFrom.append("                                                                                                                           INNER JOIN TECHNOLOGIES TEC ON(TEC.ID = SOD.TECHNOLOGY_ID) ");
            
            List<Technology> technologies= this.technologyDAO.getAllIRDTechnologies();
            String tecCodeCondition=" 1<>1 ";
            for(Technology t : technologies){
            	tecCodeCondition += " OR TEC.CODE = '"+t.getCode()+"' ";
            }
            
            queryFrom.append("                                                                                                   WHERE ( "+tecCodeCondition+" ) ");
            queryFrom.append("                                                                                                   GROUP BY SO.WO_ID, SO.SHIPPING_ORDER_CODE) t1 ON (t1.SOD_ID = SHIP_DET.ID) ");
            queryFrom.append("                                                                                    INNER JOIN SHIPP_ORDER_IBS_ELEMENT_MODELS s ON (S.MODEL_CODE = SHIP_DET.IBS_MODEL_CODE)) SOT ON(SOT.ID=SO.ID) ");

            queryWhere.append(" WHERE CO.ID=:aCountryId ");
            
            List<String> codes = new ArrayList<String>();
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            
            List<Long> stateIds = woStatusIdsWithOutCodes(codes);
            
            int stateIdTam = stateIds.size();
            
            queryWhere.append(" AND ( 1<>1 ");
            for(int i=0; i<stateIdTam; ++i){
            	queryWhere.append(" OR WS.ID=:wostatusid"+i+" ");
            }
            queryWhere.append(" ) ");
            
            serviceCodes = new ArrayList<String>();
            if(filter.getServiceCodes()!=null && !filter.getServiceCodes().isEmpty())
	            for (String serviceCode : filter.getServiceCodes()) {
	            	if(serviceCode!=null && !serviceCode.equals("") && !serviceCode.equals(" "))
	            		serviceCodes.add(serviceCode);
				}
            
            int tam = 0;
            if(serviceCodes!=null && !serviceCodes.isEmpty()){
            	tam = serviceCodes.size();
            }
            
            for(int i=0; i<tam; ++i){
            	if(i==0)
            		queryWhere.append(" AND ( ");
            	
            	String nombreParametro=":serviceCode"+i;
            	queryWhere.append(" s.SERVICE_CODE = " + nombreParametro );
            	
            	if((i+1)==tam)
            		queryWhere.append(" )");
            	else
            		queryWhere.append(" OR ");
            	
            }
            
            queryWhere.append(" and (SC.SERVICE_CATEGORY_CODE = :aServiceCategoryCode or :aServiceCategoryCode is null) ");
            
            queryOrder.append(" order by creationDate asc,w.id ");
            
            Query query = session.createSQLQuery(querySelect.toString() + queryFrom.toString() + queryWhere.toString() + queryOrder.toString())
				.addScalar("clientNumber",Hibernate.STRING)
				.addScalar("orderNumber",Hibernate.STRING)
				.addScalar("clientName",Hibernate.STRING)
				.addScalar("city",Hibernate.STRING)
				.addScalar("creationDate",Hibernate.DATE)
				.addScalar("jobCard",Hibernate.STRING)
				.addScalar("jobCardType",Hibernate.STRING)
				.addScalar("jobCardStatus",Hibernate.STRING)
				.addScalar("technicalName",Hibernate.STRING)
				.addScalar("dayQty",Hibernate.LONG)
				.addScalar("appointment",Hibernate.DATE)
				.addScalar("address",Hibernate.STRING)
				.addScalar("telephone",Hibernate.STRING)
				.addScalar("problemDescription",Hibernate.STRING)
				.addScalar("model",Hibernate.STRING)
				.addScalar("product",Hibernate.STRING)
                .addScalar("allocationDate",Hibernate.DATE)
                .setResultTransformer(Transformers.aliasToBean(ReportsPendingServicesByDateDTO.class));
            
            for(int i=0; i<tam; ++i){
            	String nombreParametro="serviceCode"+i;
            	query.setParameter(nombreParametro, serviceCodes.get(i), Hibernate.STRING);
            }
            for(int i=0; i<stateIdTam; ++i){
            	String nombreParametro="wostatusid"+i;
            	query.setParameter(nombreParametro, stateIds.get(i), Hibernate.LONG);
            }
            query.setParameter("aServiceCategoryCode",filter.getCategoryCode(), Hibernate.STRING);
            query.setParameter("aActive", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aCountryId", countryId, Hibernate.LONG);
            query.setParameter("aSysdate", new Timestamp(sysdate.getTime()), Hibernate.TIMESTAMP);
            
            Long recordQty = 0L;
        	if( requestInfo != null ){	
        		Query countQuery = session.createSQLQuery(" select count(*) " + queryFrom.toString() + queryWhere.toString());
                for(int i=0; i<tam; ++i){
                	String nombreParametro="serviceCode"+i;
                	countQuery.setParameter(nombreParametro, serviceCodes.get(i), Hibernate.STRING);
                }
                for(int i=0; i<stateIdTam; ++i){
                	String nombreParametro="wostatusid"+i;
                	countQuery.setParameter(nombreParametro, stateIds.get(i), Hibernate.LONG);
                }
                countQuery.setParameter("aServiceCategoryCode", filter.getCategoryCode(), Hibernate.STRING);
                countQuery.setParameter("aActive", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
                countQuery.setParameter("aCountryId", countryId, Hibernate.LONG);
                
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestInfo.getFirstResult() );
				query.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	
        	ReportsPendingServicesByDateAndFileResponseDTO response = new ReportsPendingServicesByDateAndFileResponseDTO();
        	List<ReportsPendingServicesByDateDTO> responseList = query.list();
        	if( requestInfo != null )
				populatePaginationInfo( response, requestInfo.getPageSize(), requestInfo.getPageIndex(), responseList.size(), recordQty.intValue() );
        	response.setReportsPendingServicesByDateDTOList(responseList);
        	
        	return response;
        }catch (Throwable ex) {
            log.error("== Error getReportsPendingServicesByDate==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReportsPendingServicesByDate/WorkOrderDAO ==");
        }
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(ReportsComplyAndScheduleFilterDTO filter, 
			                                                              RequestCollectionInfo requestInfo,
			                                                              Long countryId) throws DAOSQLException, DAOServiceException{
        try {
            log.debug("== Inicio getReportsComplyAndSchedule/WorkOrderDAO ==");
            Session session = super.getSession();

            StringBuilder querySelect = new StringBuilder();
            StringBuilder queryFrom   = new StringBuilder();
            StringBuilder queryWhere   = new StringBuilder();
            StringBuilder queryOrder   = new StringBuilder();
            
            querySelect.append(" SELECT C.CUSTOMER_CODE clientNum, ");
            querySelect.append("        W.WO_CODE woNum, ");
            querySelect.append("        ST.SERVICE_TYPE_NAME serviceCat, ");
            querySelect.append("        nvl(S.SERVICE_CODE,'') || ' - ' || nvl(S.SERVICE_NAME,'') serviceTypeName, ");
            querySelect.append("        W.CREATION_DATE creationDate, ");
            querySelect.append("        WA.DEALER_ASSIGNMENT_DATE allocationDate, ");
            querySelect.append("        WOA.AGENDATION_DATE schedulerDate, ");
            querySelect.append("        W.WO_REALIZATION_DATE attentionDate, ");
            querySelect.append("        CI.CITY_NAME city, ");
            querySelect.append("        nvl(E.FIRST_NAME,'') || ' ' || nvl(E.LAST_NAME,'') employeeName, ");
            querySelect.append("        decode(nvl(wsh2.countAgendado,0),0,:aFalse,:aTrue) scheduled, ");
            querySelect.append("        decode(nvl(wsh2.countReAgendado,0),0,:aFalse,:aTrue) rescheduled ");
            queryFrom.append("   from work_orders w inner join CUSTOMERS c on(C.ID=W.CUSTOMER_ID) ");
            queryFrom.append("	       INNER JOIN WORK_ORDER_STATUS WS	 ON WS.ID = W.ACTUAL_STATUS_ID	");
            queryFrom.append("                      inner join COUNTRIES co ON (CO.ID = W.COUNTRY_ID) ");
            queryFrom.append("                      inner join WORK_ORDER_SERVICES wos on(W.ID=WOS.WO_ID) ");  
            queryFrom.append("                      inner join SERVICES s on(S.ID=WOS.SERVICE_ID) ");
            queryFrom.append("                      inner join SERVICE_CATEGORIES sc on(SC.ID=S.SERVICE_CATEGORY_ID) ");
            queryFrom.append("                      inner join SERVICE_TYPES st on(ST.ID=SC.SERVICE_TYPE_ID) ");
            queryFrom.append("                      inner join POSTAL_CODES p on(P.ID=W.POSTAL_CODE_ID) ");
            queryFrom.append("                      inner join CITIES ci on(Ci.ID = P.CITY_ID) ");
            queryFrom.append("                      left join WORK_ORDER_CREW_ASSIGMENTS WCA on(W.ID=WCA.WO_ID and WCA.IS_ACTIVE=:aActive) "); 
            queryFrom.append("                      left join CREWS cw on(CW.ID=WCA.CREW_ID) ");
            queryFrom.append("                      left join EMPLOYEE_CREWS ec on(CW.ID=EC.CREW_ID and EC.IS_RESPONSIBLE=:aActive) ");
            queryFrom.append("                      left join EMPLOYEES e on(e.ID=EC.EMPLOYEE_ID) ");
            queryFrom.append("                      left join WORK_ORDER_ASSIGNMENTS wa on(W.ID = WA.WO_ID AND WA.IS_ACTIVE=:aActive) ");
            queryFrom.append("                      left join WORK_ORDER_AGENDAS woa on(WA.ID = WOA.WO_ASSIGNMENTS_ID AND WOA.STATUS=:aActive) ");
            queryFrom.append("                      left join (select WSH.WO_ID, ");
            queryFrom.append("                                        sum(decode(WS.WO_STATE_CODE,:aStatusScheduled,1,0)) countAgendado, ");
            queryFrom.append("                                        sum(decode(WS.WO_STATE_CODE,:aStatusRescheduled,1,0)) countReAgendado ");
            queryFrom.append("                                   from WORK_ORDER_STATUS_HISTORIES wsh inner join WORK_ORDER_STATUS ws on(WS.ID = WSH.WO_STATUS_ID) ");
            queryFrom.append("                                  where WS.WO_STATE_CODE=:aStatusScheduled or WS.WO_STATE_CODE=:aStatusRescheduled ");
            queryFrom.append("                                  group by WSH.WO_ID) wsh2 on(W.ID=wsh2.WO_ID) ");
            
            queryWhere.append(" WHERE CO.ID=:aCountryId ");
            queryWhere.append("       AND W.CREATION_DATE >= :beginDate ");
            queryWhere.append("       AND W.CREATION_DATE <= :endDate ");
            
            List<String> codes = new ArrayList<String>();
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            
            List<Long> stateIds = woStatusIdsWithOutCodes(codes);
            
            int stateIdTam = stateIds.size();
            queryWhere.append(" AND ( 1<>1 ");
            for(int i=0; i<stateIdTam; ++i){
            	queryWhere.append(" OR WS.ID=:wostatusid"+i+" ");
            }
            queryWhere.append(" ) ");
            queryOrder.append(" order by creationDate asc,w.id ");
            
            Query query = session.createSQLQuery(querySelect.toString() + queryFrom.toString() + queryWhere.toString() + queryOrder.toString() )
				.addScalar("clientNum",Hibernate.STRING)
				.addScalar("woNum",Hibernate.STRING)
				.addScalar("serviceCat",Hibernate.STRING)
				.addScalar("serviceTypeName",Hibernate.STRING)
				.addScalar("creationDate",Hibernate.DATE)
				.addScalar("allocationDate",Hibernate.DATE)
				.addScalar("schedulerDate",Hibernate.DATE)
				.addScalar("attentionDate",Hibernate.DATE)
				.addScalar("city",Hibernate.STRING)
				.addScalar("employeeName",Hibernate.STRING)
				.addScalar("scheduled",Hibernate.STRING)
				.addScalar("rescheduled",Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ReportsComplyAndScheduleDTO.class));
            
            query.setParameter("beginDate", new Timestamp(UtilsBusiness.dateTo12am(filter.getBeginDate()).getTime()), Hibernate.TIMESTAMP);
            query.setParameter("endDate",   new Timestamp(UtilsBusiness.dateTo12pm(filter.getEndDate()).getTime()), Hibernate.TIMESTAMP);
            query.setParameter("aActive", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aTrue", CodesBusinessEntityEnum.TRUE.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aFalse", CodesBusinessEntityEnum.FALSE.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aStatusScheduled", CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aStatusRescheduled", CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity(), Hibernate.STRING);
            query.setParameter("aCountryId", countryId, Hibernate.LONG);
            
            for(int i=0; i<stateIdTam; ++i){
            	String nombreParametro="wostatusid"+i;
            	query.setParameter(nombreParametro, stateIds.get(i), Hibernate.LONG);
            }
            
        	Long recordQty = 0L;
        	if( requestInfo != null ){
        		
        		Query countQuery = session.createSQLQuery(" select count(*) " + queryFrom.toString() + queryWhere.toString());
        		countQuery.setParameter("beginDate", new Timestamp(UtilsBusiness.dateTo12am(filter.getBeginDate()).getTime()), Hibernate.TIMESTAMP);
        		countQuery.setParameter("endDate",   new Timestamp(UtilsBusiness.dateTo12pm(filter.getEndDate()  ).getTime()), Hibernate.TIMESTAMP);
        		countQuery.setParameter("aActive", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("aStatusScheduled", CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("aStatusRescheduled", CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("aCountryId", countryId, Hibernate.LONG);
                for(int i=0; i<stateIdTam; ++i){
                	String nombreParametro="wostatusid"+i;
                	countQuery.setParameter(nombreParametro, stateIds.get(i), Hibernate.LONG);
                }
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestInfo.getFirstResult() );
				query.setMaxResults( requestInfo.getMaxResults() );

        	}
        	ReportsComplyAndScheduleAndFileResponseDTO response = new ReportsComplyAndScheduleAndFileResponseDTO();
        	List<ReportsComplyAndScheduleDTO> responseList = query.list();
        	if( requestInfo != null )
				populatePaginationInfo( response, requestInfo.getPageSize(), requestInfo.getPageIndex(), responseList.size(), recordQty.intValue() );
        	response.setReportsComplyAndScheduleDTOList(responseList);
        	return response;
        }catch (Throwable ex) {
            log.error("== Error getReportsComplyAndSchedule==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReportsComplyAndSchedule/WorkOrderDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private List<Long> woStatusIdsWithOutCodes(List<String> codes) throws DAOServiceException, DAOSQLException{
		List<Long> returnValue=null;
        log.debug("== Inicio woStatusIdsWithOutCodes/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" SELECT ws.id FROM "+WorkorderStatus.class.getName()+" ws WHERE NOT( 1<>1 ");
            int tamCodes = codes.size();
            for(int i=0; i<tamCodes; ++i){
            	queryBuffer.append(" OR ws.woStateCode = :woStateCode"+i+" ");
            }
            queryBuffer.append(" ) ");
            Query query = session.createQuery(queryBuffer.toString());
            for(int i=0; i<tamCodes; ++i){
            	query.setString("woStateCode"+i, codes.get(i));
            }
            returnValue= (List<Long>)query.list();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo woStatusIdsWithOutCodes==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina woStatusIdsWithOutCodes/WorkOrderDAO ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long getCountryIdOfWorkOrderId(Long woId) throws DAOServiceException, DAOSQLException{
		Long returnValue=null;
        log.debug("== Inicio getCountryIdOfWorkOrderId/WorkOrderDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" SELECT wo.country.id FROM "+WorkOrder.class.getName()+" wo WHERE wo.id = :woId ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("woId", woId);
            returnValue= (Long)query.uniqueResult();
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getCountryIdOfWorkOrderId==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCountryIdOfWorkOrderId/WorkOrderDAO ==");
        }
	}
	
	/**
	 * Merodo encargado de traer las work order asociadas a un determinado grupo de work orders, teniendo en cuenta que una work order asociada es una
	 * work order que pertenezca al mismo dealer, al mismo cliente y este en un conjunto de estados dado
	 * @param states Estados posibles de las work order asociadas que buscara para la generacion del PDF
	 * @param wos work orders de las cuales se desea buscar sus work order asociadas
	 * @return work orders asociadas
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderTrayDTO> getWorkOrdersByStatesAndCustomerAndDealer(List<String> states, List<WorkOrder> wos) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia getWorkOrdersByStates/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+WorkOrderTrayDTO.class.getName()+"(wo , ");
            
            stringQuery.append(" (select ec.employee.firstName || ' ' || ec.employee.lastName from "+EmployeeCrew.class.getName()+" ec ");
            stringQuery.append(" where ec.crew in ( ");
            stringQuery.append(" select wca.crewId from "+WoCrewAssigments.class.getName()+" wca ");
            stringQuery.append(" where wca.isActive='"+CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()+"' and wca.woId.id=wo.id) ");
            stringQuery.append(" and ec.isResponsible='"+CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()+"') ) ");

            stringQuery.append(" from "+WorkOrder.class.getName()+" wo ");
            
            stringQuery.append(" where ");
            String stringStates=" '' ";
            for(String state:states){
            	stringStates+=" , '"+state+"'";
            }
            stringQuery.append(" wo.workorderStatusByActualStatusId.woStateCode in ("+stringStates+") ");
            
            stringQuery.append(" and ( 1<>1 ");
            String woIds="";
            for(int i=0; i<wos.size(); ++i){
            	woIds+=" wo.id <> :woId"+i+" ";
            	if(i!=(wos.size()-1)){
            		woIds+=" and ";
            	}
            }
            for(int i=0; i<wos.size(); ++i){
            	wos.get(i).getId();
            	wos.get(i).getDealerId();
            	wos.get(i).getCustomer().getId();
            	stringQuery.append(" or ( ");
            	stringQuery.append(" ( :woId"+i+" is null or ( "+woIds+" ) ) ");
            	stringQuery.append(" and (:dealerId"+i+" is null or wo.dealerId = :dealerId"+i+") ");
            	stringQuery.append(" and (:customerId"+i+" is null or wo.customer.id = :customerId"+i+") ) ");
            }
            stringQuery.append(" ) ");
            

            Query query = session.createQuery(stringQuery.toString());

            for(int i=0; i<wos.size(); ++i){
            	
                query.setParameter("woId"+i, wos.get(i).getId(), Hibernate.LONG);
                query.setParameter("dealerId"+i, wos.get(i).getDealerId(), Hibernate.LONG);
                query.setParameter("customerId"+i, wos.get(i).getCustomer().getId(), Hibernate.LONG);
            }
            
            
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error getWorkOrdersByStates ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByStates/WorkOrderDAO ==");
        }
	}

	/**
	 * Merodo encargado de traer las work order asociadas a un determinado grupo de work orders para la generacion del PDF de la wo, 
	 * teniendo en cuenta que una work order asociada es una
	 * work order que pertenezca al mismo dealer, al mismo cliente y este en un conjunto de estados dado
	 * @param states Estados posibles de las work order asociadas que buscara para la generacion del PDF
	 * @param wos work orders de las cuales se desea buscar sus work order asociadas
	 * @return work orders asociadas.
	 * @throws DAOServiceException 
	 * @throws DAOSQLException 
	 * @author aharker
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrder> getWorkOrdersByStatesAndCustomerAndDealerForPdf(List<String> states, List<WorkOrder> wos) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia getWorkOrdersByStates/WorkOrderDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select wo ");
            stringQuery.append(" from "+WorkOrder.class.getName()+" wo ");
            stringQuery.append(" where ");
            String stringStates=" '' ";
            for(String state:states){
            	stringStates+=" , '"+state+"'";
            }
            stringQuery.append(" wo.workorderStatusByActualStatusId.woStateCode in ("+stringStates+") ");
            
            stringQuery.append(" and ( 1<>1 ");
            String woIds="";
            for(int i=0; i<wos.size(); ++i){
            	woIds+=" wo.id <> :woId"+i+" ";
            	if(i!=(wos.size()-1)){
            		woIds+=" and ";
            	}
            }
            for(int i=0; i<wos.size(); ++i){
            	wos.get(i).getId();
            	wos.get(i).getDealerId();
            	wos.get(i).getCustomer().getId();
            	stringQuery.append(" or ( ");
            	stringQuery.append(" ( :woId"+i+" is null or ( "+woIds+" ) ) ");
            	stringQuery.append(" and (:dealerId"+i+" is null or wo.dealerId = :dealerId"+i+") ");
            	stringQuery.append(" and (:customerId"+i+" is null or wo.customer.id = :customerId"+i+") ) ");
            }
            stringQuery.append(" ) ");
            

            Query query = session.createQuery(stringQuery.toString());

            for(int i=0; i<wos.size(); ++i){
            	
                query.setParameter("woId"+i, wos.get(i).getId(), Hibernate.LONG);
                query.setParameter("dealerId"+i, wos.get(i).getDealerId(), Hibernate.LONG);
                query.setParameter("customerId"+i, wos.get(i).getCustomer().getId(), Hibernate.LONG);
            }
            
            
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error getWorkOrdersByStates ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWorkOrdersByStates/WorkOrderDAO ==");
        }
	}
	
	//REQ001 - WO Canceladas.
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkOrder> getCanceledWorkOrdersByDealerIdAndDates(Long dealerId, Date fromDate, Date toDate) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getCanceledWorkOrdersByDealerId/WorkOrderDAO ==");
		Session session = super.getSession();
		try{
			 StringBuffer queryString = new StringBuffer();
			 queryString.append("select wo from ");
			 queryString.append(WorkOrder.class.getName() +" wo ");
			 queryString.append(" WHERE  wo.saleDealer = :dealerId ");
			 queryString.append(" AND wo.cancelationDate BETWEEN TO_DATE(:fromDate, 'yyyy-mm-dd hh24:mi:ss')");
			 queryString.append(" AND TO_DATE(:toDate, 'yyyy-mm-dd hh24:mi:ss')");
			
			 Query query = session.createQuery(queryString.toString());
			 query.setLong("dealerId", dealerId);
			 
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 query.setString("fromDate", dateFormat.format(fromDate.getTime()));
			 query.setString("toDate", dateFormat.format(toDate.getTime()));
	            
			 return query.list();
		}
		catch (Throwable ex){
			log.error("== Error consultando en el metodo getCanceledWorkOrdersByDealerId/WorkOrderDAO ==");
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina getCanceledWorkOrdersByDealerId/WorkOrderDAO ==");
        }
		
	}
	
	//REQ inactivar tecnico
	@Override
	public List<ReportWorkOrderCrewAttentionDTO> getWorkOrderAttentionReport(Long countryId, Date nowDate, RequestCollectionInfo requestInfo, ReportWorkOrderCrewAttentionDTO reportWorkOrderCrewAttentionDTO) throws DAOSQLException, DAOServiceException {
		try {
            log.debug("== Inicio getWorkOrderAttentionReport/ReportsStockDAO ==");
            Session session = super.getSession();
            
			// Fecha Inicio
			Date movementDateIn = reportWorkOrderCrewAttentionDTO.getMovementDateIn();

			// Fecha Final
			Date movementDateOut = reportWorkOrderCrewAttentionDTO.getMovementDateOut();
			
          //Consulta que retorna el detalle de los elementos
            StringBuffer stringQuery = new StringBuffer("");
			
            stringQuery.append(" SELECT ");
			stringQuery.append("   WOCA.DEALER_NAME   dealerName, ");
			stringQuery.append("   WOCA.DEALER  dealerMain, ");
			stringQuery.append("   WOCA.WO_CODE  woCode, ");
			stringQuery.append("   WOCA.CREW_ID  crewId, ");
			stringQuery.append("   WOCA.MEMBERS_CREW	employeesCrew, ");
			stringQuery.append("   WOCA.NAME_TECHNICIAL_ATTENTION   responsibleCrew, ");
			stringQuery.append("   WOCA.DOCUMENT_NUMBER	documentNumber, ");
			stringQuery.append("   WOCA.IBS_TECHNICIAL  ibsTechnicial, ");
			stringQuery.append("   WOCA.ATTENTION_DATE  attentionDate "); 
            stringQuery.append(" FROM WORK_ORDER_CREW_ATTENTIONS WOCA ");
            stringQuery.append("   INNER JOIN WORK_ORDERS W on W.WO_CODE = WOCA.WO_CODE ");
            stringQuery.append(" WHERE W.COUNTRY_ID = :aCountryId ");
            stringQuery.append(" AND trunc(WOCA.ATTENTION_DATE) >= trunc(:aMovementDateIn) ");
            stringQuery.append(" AND trunc(WOCA.ATTENTION_DATE) <= trunc(:aMovementDateOut) ");
            
            Query querySQL = session.createSQLQuery(stringQuery.toString())
        			.addScalar("dealerName")
        			.addScalar("dealerMain")
        			.addScalar("woCode")
        			.addScalar("crewId" , Hibernate.LONG)
        			.addScalar("employeesCrew")
        			.addScalar("responsibleCrew")
        			.addScalar("documentNumber")
        			.addScalar("ibsTechnicial")
        			.addScalar("attentionDate" , Hibernate.DATE)       			
        			.setResultTransformer(Transformers.aliasToBean(ReportWorkOrderCrewAttentionDTO.class));
            
            querySQL.setParameter("aCountryId", countryId, Hibernate.LONG);
            
            if (movementDateIn != null) {
            	querySQL.setDate("aMovementDateIn",UtilsBusiness.obtenerPrimeraHoraDia(movementDateIn));
			}
			
			if (movementDateOut != null) {
				querySQL.setDate("aMovementDateOut",UtilsBusiness.obtenerUltimaHoraDia(movementDateOut));
			}
			
			//querySQL.setString("isResponsible",CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
			//querySQL.setString("movementType",CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_ENTRY.getCodeEntity());
					
          //Paginacion
        	if(requestInfo != null){		
	        	querySQL.setFirstResult(requestInfo.getFirstResult());
	        	querySQL.setMaxResults(requestInfo.getMaxResults());				
        	}            
            
        	List<ReportWorkOrderCrewAttentionDTO> responseList = querySQL.list();
        	return responseList;
		} catch (Throwable e) {
			log.error("== Error consultando en el metodo getWorkOrderAttentionReport/WorkOrderDAO ==");
			throw this.manageException(e);
		}finally {
            log.debug("== Termina getWorkOrderAttentionReport/WorkOrderDAO ==");
        }
	 }

}

