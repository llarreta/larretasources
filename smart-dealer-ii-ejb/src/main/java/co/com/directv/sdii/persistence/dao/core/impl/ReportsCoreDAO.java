package co.com.directv.sdii.persistence.dao.core.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;
import co.com.directv.sdii.model.dto.AuxTechnicianDetailsDTO;
import co.com.directv.sdii.model.dto.DispacherProductivityDTO;
import co.com.directv.sdii.model.dto.MonthlyActivityResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionDTO;
import co.com.directv.sdii.model.dto.ReportsParameterInputDTO;
import co.com.directv.sdii.model.dto.WoPendingLackMaterialsDTO;
import co.com.directv.sdii.model.pojo.ScheduleReportParameter;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.impl.WorkOrderDAO;
import co.com.directv.sdii.persistence.dao.core.ReportsCoreDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;

/**
 * Session Bean implementation class ReportsCoreDAO
 */
@Stateless(name="ReportsCoreDAOLocal",mappedName="ejb/ReportsCoreDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportsCoreDAO extends BaseDao implements ReportsCoreDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(WorkOrderDAO.class);

	@EJB
	private TechnologyDAOLocal technologyDAO;
	
    /**
     * Default constructor. 
     */
    public ReportsCoreDAO() {
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ReportsCoreDAOLocal#getActivityBacklog()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ActivityBacklogResponseDTO> getActivityBacklog(Long countryId,Date nowDate,RequestCollectionInfo requestInfo, String ... serviceTypes ) throws DAOSQLException,
			DAOServiceException {
        try {
            log.debug("== Inicio getActivityBacklog/ReportsCoreDAO ==");
            Session session = super.getSession();

            StringBuilder query = new StringBuilder();
            
            query.append(" select wo.wo_code numWorkOrder, ");
            query.append("        cu.customer_code ibsClient, ");
            query.append("        to_char(ct.MEDIA_CONTACT_VALUE) phone, ");
            query.append("        cu.customer_address address, ");
            query.append("        woag.AGENDATION_DATE programmingDate, ");
            query.append("        sh.service_hours_name serviceHour, ");
            query.append("        wo.creation_date creationDate, ");
            query.append("        wo.wo_description description, ");
            query.append("        ws.wo_state_name stateWorkOrder, ");
            query.append("        i6s.ibs_6_state_name ibsState, ");
            query.append("       (e.first_name || ' ' || e.last_name) crewResponsible, ");
            query.append("       d.depot_code depotCodeDealer, ");
            query.append("       d.dealer_name nameDealer, ");
            
            
            //Spira 22159 - REPORTES FTP:Campos inconsistentes
            /*
            query.append("       ( select count(id)  ");
            query.append("           from WORK_ORDER_AGENDAS  ");
            query.append("          where WO_ASSIGNMENTS_ID in ( select id  ");
            query.append("                                         from WORK_ORDER_ASSIGNMENTS where wo_id = wo.id ) ) reschedule, ");
            */
            
            query.append("       ( select count(id)  																																		");
            query.append("           from WORK_ORDER_STATUS_HISTORIES wosh  																												");
            query.append("           where wosh.WO_STATUS_ID in (  																															");
            query.append(" 										   select id from work_order_status   																						");
            query.append("           							   where wo_state_code in ( :workOrderAgRgStatus )																			");
            query.append("										   and   wosh.WO_REASON_ID in (  select wor.ID      																		");
            query.append(" 															             from  work_order_reasons wor 																");
            query.append("                     													 inner join    work_order_reason_categories worc on wor.WO_REASON_CATEGORY_ID = worc.ID 	");
            query.append(" 																		 where worc.WO_REASON_CATEGORY_CODE in ( :workOrderAgRgStatus )    							");
            query.append("                     													)																							");
            query.append("     	                                )                                                   																		");
            query.append("           and wosh.WO_ID = wo.id  																																");
            query.append("   	)  reschedule, 	                        																													");
            
                                 
            query.append("       ( select users.login  ");
            query.append("           from users inner join WORK_ORDER_STATUS_HISTORIES on ( WORK_ORDER_STATUS_HISTORIES.user_id = users.id and WORK_ORDER_STATUS_HISTORIES.type_change='"+CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity()+"' )  ");
            query.append("          where WORK_ORDER_STATUS_HISTORIES.id = ( select max(wsh.id) item  ");
            query.append("                                           from WORK_ORDER_STATUS_HISTORIES wsh left join users u on (u.id=wsh.user_id) left join ROLES r on(r.id=u.rol_id) where r.rol_code = '"+CodesBusinessEntityEnum.CODE_USER_CONTROL_TOWER.getCodeEntity()+"' and wsh.wo_id = wo.id and WORK_ORDER_STATUS_HISTORIES.type_change='"+CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity()+"' ) ) dispatcher, ");

            query.append("       ( select users.name  ");
            query.append("           from users inner join WORK_ORDER_STATUS_HISTORIES on ( WORK_ORDER_STATUS_HISTORIES.user_id = users.id and WORK_ORDER_STATUS_HISTORIES.type_change='"+CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity()+"' )  ");
            query.append("          where WORK_ORDER_STATUS_HISTORIES.id = ( select max(wsh.id) item  ");
            query.append("                                           from WORK_ORDER_STATUS_HISTORIES wsh left join users u on (u.id=wsh.user_id) left join ROLES r on(r.id=u.rol_id) where r.rol_code = '"+CodesBusinessEntityEnum.CODE_USER_CONTROL_TOWER.getCodeEntity()+"' and wsh.wo_id = wo.id and WORK_ORDER_STATUS_HISTORIES.type_change='"+CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity()+"' ) ) dispacherName, ");
            
            query.append("       stt.state_name state, ");
            query.append("       city.city_name city, ");
            query.append("       c_t.customer_type_name customerTypeName, ");
            query.append("       ( select woStatusHist1.DESCRIPTION observationCompany ");
            query.append("           from WORK_ORDER_STATUS_HISTORIES woStatusHist1 inner join WORK_ORDER_STATUS woStatus1 on(woStatus1.ID=woStatusHist1.WO_STATUS_ID) ");
            query.append("          where woStatus1.WO_STATE_CODE = '"+CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()+"' ");
            query.append("            and woStatusHist1.DESCRIPTION is not null ");
            query.append("            and woStatusHist1.TYPE_CHANGE='"+CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity()+"' ");
            query.append("            and WOSTATUSHIST1.WO_ID = wo.id ");
            query.append("            and woStatusHist1.status_date= (select max(status_date) ");
            query.append("                                              from WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join WORK_ORDER_STATUS woStatus2 on(woStatusHist2.WO_STATUS_ID=woStatus2.ID) ");
            query.append("                                             where woStatus2.WO_STATE_CODE = '"+CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()+"' ");
            query.append("                                               and woStatusHist2.DESCRIPTION is not null  ");
            query.append("                                               and woStatusHist2.TYPE_CHANGE='"+CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity()+"' ");
            query.append("                                               and WOSTATUSHIST1.WO_ID = WOSTATUSHIST2.WO_ID) ) observationCompany, ");
            query.append("       ( select count(1) ");
            query.append("          from WAREHOUSE_ELEMENTS we inner join RECORD_STATUS r on(R.ID=WE.RECORD_STATUS_ID) ");
            query.append("                                     inner join WAREHOUSES w on(w.id=WE.WAREHOUSE_ID) ");
            query.append("                                     inner join customers c on(c.id=W.CUSTOMER_ID) ");
            query.append("                                     inner join serialized s on(S.ELEMENT_ID=WE.SER_ID) ");
            query.append("                                     inner join ELEMENTS e on(E.ID=S.ELEMENT_ID) ");
            query.append("                                     inner join ELEMENT_TYPES et on(Et.ID=E.ELEMENT_TYPE_ID) ");
            query.append("                                     inner join ELEMENT_MODELS em on(Em.ID=ET.ELEMENT_MODEL_ID) ");
            query.append("                                     inner join ELEMENT_CLASSES ec on(Ec.ID=EM.ELEMENT_CLASS_ID) ");
            query.append("         where R.RECORD_STATUS_CODE='"+CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()+"' and c.id=cu.id and EC.ELEMENT_CLASS_CODE='"+CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity()+"' ) contDecos ");
            query.append(" , d2.depot_code depotCodeSalerCompany, d2.dealer_name nameCodeSalerCompany ");
            query.append(" , wt.WO_TYPE_NAME serviceCategory, wt.WO_TYPE_CODE serviceCategoryCode, serv.service_name  serviceName ");
            query.append(" , serv.service_code serviceCode, st.service_type_name serviceTypeName ");
            query.append(" , case when (ws.wo_state_code in ( '"+CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()+"' , '"+CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()+"') ) then ( trunc(:aSysdate)-trunc((select max(status_date) estado from WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join WORK_ORDER_STATUS woStatus2 on(woStatusHist2.WO_STATUS_ID=woStatus2.ID) ");
            query.append("                                             where woStatus2.WO_STATE_CODE in ( '"+CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()+"' , '"+CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()+"' ) ");
            query.append("                                                        and WOSTATUSHIST2.WO_ID = wo.id)) ) else null end pendingDays ");
            query.append(" , ( select wor.WORKORDER_REASON_NAME from WORK_ORDER_REASONS wor where wor.id =  ( select wo_reason_id from WORK_ORDER_STATUS_HISTORIES where id = (select max(id) reason from WORK_ORDER_STATUS_HISTORIES wos where wos.wo_id=wo.id ) ) ) lastReason ");
            query.append(" from work_orders wo left join customers cu on ( cu.id = wo.customer_id ) left join (SELECT CMC.CUSTOMER_ID CUSTOMER_ID, ");
            query.append(" substr(wm_concat (CT.MEDIA_NAME ||'='||CMC.MEDIA_CONTACT_VALUE),1,4000) MEDIA_CONTACT_VALUE ");
            query.append(" FROM  CUSTOMER_MEDIA_CONTACTS cmc INNER JOIN media_contact_types ct ON (ct.ID = CMC.CONTACT_TYPE_ID) ");
            query.append(" where ct.media_code=:mediaContactTypeTelepCode or ct.media_code=:mediaContactTypeTelephWorkCode or ct.media_code=:mediaContactTypeMobileCode or ct.media_code=:mediaContactTypeFaxCode ");
            query.append(" GROUP BY CMC.CUSTOMER_ID) ct on ( ct.CUSTOMER_ID = cu.id ) ");
            query.append(" left join WORK_ORDER_ASSIGNMENTS woa on ( woa.wo_id = wo.id and woa.is_active=:booleanTrue ) ");
            query.append(" left join WORK_ORDER_AGENDAS woag on ( woag.wo_assignments_id = woa.id and woag.status=:booleanTrue ) ");
            query.append(" left join service_hours sh on ( sh.id = woag.service_hour_id) ");
            query.append(" left join WORK_ORDER_STATUS ws on ( ws.id = wo.actual_status_id ) ");
            query.append(" left join IBS_STATUS i6s on ( i6s.id = wo.ibs_actual_status_id ) ");
            query.append(" left join WORK_ORDER_CREW_ASSIGMENTS woca on ( woca.wo_id = wo.id and woca.is_active=:booleanTrue) ");
            query.append(" left join EMPLOYEE_CREWS ec on ( ec.crew_id = woca.crew_id and ec.is_responsible = :booleanTrue ) ");
            query.append(" left join EMPLOYEES e on ( e.id = ec.employee_id ) ");
            query.append(" left join dealers d on ( d.id = wo.dealer_id ) ");
            query.append(" left join postal_codes pc on ( pc.id = cu.postal_code_id ) ");
            query.append(" left join cities city on ( city.id = pc.city_id ) ");
            query.append(" left join states stt on ( stt.id = city.state_id ) ");
            
            query.append(" left join CUSTOMER_CLASS_TYPES cct on ( cct.id = cu.cust_type_id ) ");
			
			query.append(" left join CUSTOMER_TYPES c_t on ( c_t.id = cct.CUSTOMER_TYPE_ID ) ");
            
            query.append(" left join dealers d2 on ( d2.id = wo.sale_dealer_id ) ");
            query.append(" left join WORK_ORDER_TYPES wt on ( wt.id = wo.wo_type_id ) ");
            query.append(" left join work_order_services wos on ( wos.wo_id = wo.id ) ");
            query.append(" left join services serv on ( serv.id = wos.service_id ) ");
            query.append(" left join SERVICE_CATEGORIES sc on ( sc.id = serv.service_category_id ) ");
            query.append(" left join service_types st on ( st.id = sc.service_type_id ) ");
            query.append(" where wo.country_id = :aCountryId ");
            query.append(" and ( wo.actual_status_id not in ( select id from  WORK_ORDER_STATUS ws where ws.wo_state_code in ( '"+CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity()+"' , '"+CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity()+"' , '"+CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()+"' ) ) ) ");
            if(serviceTypes!=null && serviceTypes.length>0){
            	String woTypeIdIn = "";
            	for(int i=0; i<serviceTypes.length; ++i){
            		woTypeIdIn += " '"+serviceTypes[i]+"' ";
            		if(i<(serviceTypes.length-1)){
            			woTypeIdIn += " , ";
            		}
            	}
            	query.append(" and ( wo.wo_type_id in ( select id from  WORK_ORDER_TYPES wt where wt.wo_type_code in ( "+woTypeIdIn+" ) ) ) ");
            }

            query.append(" order by wo.creation_date desc ");

            List<String> codes = new ArrayList<String>();
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            
            Query querySQL = session.createSQLQuery(query.toString())
					.addScalar("numWorkOrder", Hibernate.STRING)
					.addScalar("ibsClient", Hibernate.STRING)
					.addScalar("phone", Hibernate.STRING)
					.addScalar("address", Hibernate.STRING)
					.addScalar("programmingDate", Hibernate.DATE)
					.addScalar("serviceHour", Hibernate.STRING)
					.addScalar("creationDate", Hibernate.DATE)
					.addScalar("description", Hibernate.STRING)
					.addScalar("stateWorkOrder", Hibernate.STRING)
					.addScalar("ibsState", Hibernate.STRING)
					.addScalar("crewResponsible", Hibernate.STRING)
					.addScalar("depotCodeDealer", Hibernate.STRING)
					.addScalar("reschedule", Hibernate.LONG)
					.addScalar("dispatcher", Hibernate.STRING)
					.addScalar("state", Hibernate.STRING)
					.addScalar("city", Hibernate.STRING)
					.addScalar("customerTypeName", Hibernate.STRING)
					.addScalar("observationCompany", Hibernate.STRING)
					.addScalar("contDecos", Hibernate.LONG)
					.addScalar("depotCodeSalerCompany", Hibernate.STRING)
					.addScalar("nameCodeSalerCompany", Hibernate.STRING)
					.addScalar("serviceCategory", Hibernate.STRING)
					.addScalar("serviceCategoryCode", Hibernate.STRING)
					.addScalar("serviceName", Hibernate.STRING)
					.addScalar("serviceCode", Hibernate.STRING)
					.addScalar("serviceTypeName", Hibernate.STRING)
					.addScalar("pendingDays", Hibernate.LONG)
					.addScalar("lastReason", Hibernate.STRING)
					.addScalar("nameDealer", Hibernate.STRING)
					.addScalar("dispacherName", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(ActivityBacklogResponseDTO.class));
            querySQL.setParameter("aCountryId", countryId, Hibernate.LONG);
            querySQL.setParameter("aSysdate",   new Timestamp(nowDate.getTime()), Hibernate.TIMESTAMP);
            
            querySQL.setParameter("mediaContactTypeTelepCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeTelephWorkCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeMobileCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeFaxCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity(), Hibernate.STRING );
            
            querySQL.setParameter("booleanTrue", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING );
            
            querySQL.setParameter("workOrderAgRgStatus", CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()+","+CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity(), Hibernate.STRING );
            
            
        	if( requestInfo != null ){			
	        	querySQL.setFirstResult( requestInfo.getFirstResult() );
	        	querySQL.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	List<ActivityBacklogResponseDTO> responseList = querySQL.list();
        	return responseList;
        }catch (Throwable ex) {
            log.error("== Error getActivityBacklog==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActivityBacklog/ReportsCoreDAO ==");
        }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DispacherProductivityDTO> getDispacherProductivity(Long countryId, RequestCollectionInfo requestInfo, java.util.Date dateNow) throws DAOSQLException,DAOServiceException {
        try {
            log.debug("== Inicio getDispacherProductivity/ReportsCoreDAO ==");
            Session session = super.getSession();

            StringBuilder query = new StringBuilder();
            
            query.append(" select wo_code woCode, cu.customer_code customerCode, u.login dispacher,  u.name dispacherName, ws.wo_state_name state, wsh.status_date stateDate ");
            query.append("  , sh.service_hours_name serviceHourName, (e.first_name || ' ' || e.last_name) crewResponsible, d.depot_code depotCodeDealer, d.dealer_name nameDealer ");
            query.append("  , wt.WO_TYPE_NAME serviceCategory, serv.service_code serviceCategoryCode, serv.service_name  serviceName, wo.creation_date creationDate ");
            query.append(" from work_orders wo ");
            query.append("  inner join customers cu on ( cu.id = wo.customer_id ) ");
            query.append("  inner join WORK_ORDER_STATUS_HISTORIES wsh on ( wsh.wo_id = wo.id and wsh.type_change='"+CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity()+"' ) ");
            query.append("  left join users u on ( u.id = wsh.user_id ) left join WORK_ORDER_STATUS ws on ( ws.id = wsh.wo_status_id ) ");
            query.append("  left join WORK_ORDER_ASSIGNMENTS wa on ( wo.id = wa.wo_id and wa.is_active=:booleanTrue ) ");
            query.append("  left join WORK_ORDER_AGENDAS woa on ( wa.id = woa.wo_assignments_id ) left join service_hours sh on ( woa.service_hour_id = sh.id ) ");
            query.append("  left join WORK_ORDER_CREW_ASSIGMENTS woca on ( woca.wo_id = wo.id  and woca.is_active=:booleanTrue ) ");
            query.append("  left join EMPLOYEE_CREWS ec on ( ec.crew_id = woca.crew_id and ec.is_responsible = :booleanTrue ) ");
            query.append("  left join EMPLOYEES e on ( e.id = ec.employee_id ) left join dealers d on ( d.id = wo.dealer_id ) ");
            query.append("  left join WORK_ORDER_TYPES wt on ( wt.id = wo.wo_type_id ) ");
            query.append("  left join work_order_services wos on ( wos.wo_id = wo.id ) ");
            query.append("  left join services serv on ( serv.id = wos.service_id ) ");
            query.append("  left join ROLES r on (r.id=u.rol_id) ");
            query.append(" where r.rol_code = '"+CodesBusinessEntityEnum.CODE_USER_CONTROL_TOWER.getCodeEntity()+"' and trunc(wsh.status_date) = trunc(:dateNow) and wo.country_id = :aCountryId ");
            query.append(" order by wo.wo_code, wsh.status_date desc ");
            
            List<String> codes = new ArrayList<String>();
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            
            Query querySQL = session.createSQLQuery(query.toString())
            		.addScalar("woCode", Hibernate.STRING)
            		.addScalar("customerCode", Hibernate.STRING)
            		.addScalar("dispacher", Hibernate.STRING)
            		.addScalar("state", Hibernate.STRING)
            		.addScalar("stateDate", Hibernate.TIMESTAMP)
            		.addScalar("serviceHourName", Hibernate.STRING)
            		.addScalar("crewResponsible", Hibernate.STRING)
            		.addScalar("depotCodeDealer", Hibernate.STRING)
            		.addScalar("nameDealer", Hibernate.STRING)
            		.addScalar("serviceCategory", Hibernate.STRING)
            		.addScalar("serviceCategoryCode", Hibernate.STRING)
            		.addScalar("serviceName", Hibernate.STRING)
            		.addScalar("creationDate", Hibernate.DATE)
            		.addScalar("dispacherName", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(DispacherProductivityDTO.class));
            querySQL.setParameter("aCountryId", countryId, Hibernate.LONG);
            querySQL.setParameter("booleanTrue", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING );
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(dateNow.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            querySQL.setParameter("dateNow", new Timestamp(calendar.getTimeInMillis()), Hibernate.TIMESTAMP);
        	if( requestInfo != null ){			
	        	querySQL.setFirstResult( requestInfo.getFirstResult() );
	        	querySQL.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	List<DispacherProductivityDTO> responseList = querySQL.list();
        	return responseList;
        }catch (Throwable ex) {
            log.error("== Error getDispacherProductivity==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDispacherProductivity/ReportsCoreDAO ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MonthlyActivityResponseDTO> getMonthlyActivity(ReportsParameterInputDTO request,Date nowDate, RequestCollectionInfo requestInfo) throws DAOSQLException,
			DAOServiceException {
        try {
            log.debug("== Inicio getActivityBacklog/ReportsCoreDAO ==");
            Session session = super.getSession();

            StringBuilder query = new StringBuilder();
            
            query.append("  ");
            
            List<String> codes = new ArrayList<String>();
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            
            StringBuilder queryBuffer = new StringBuilder();
            queryBuffer.append(" SELECT w.ID id, ");
            queryBuffer.append("        w.WO_CODE woCode, ");
            queryBuffer.append("        cust.CUSTOMER_CODE customerCode, ");
            queryBuffer.append("        cust.FIRST_NAME ||' '|| cust.LAST_NAME customerName, ");

            queryBuffer.append("        (select ct.CUSTOMER_TYPE_NAME ");
            queryBuffer.append("           from customer_class_types cct ");
			queryBuffer.append("           inner join customer_types ct on (ct.id=cct.customer_type_id) ");
            queryBuffer.append("          where cct.ID = cust.CUST_TYPE_ID) customerTypeName, ");
            
            queryBuffer.append("        (SELECT sale_dealer.DEALER_NAME ");
            queryBuffer.append("           FROM DEALERS sale_dealer ");
            queryBuffer.append("          WHERE sale_dealer.ID = w.SALE_DEALER_ID) dealerNameS, ");
            queryBuffer.append("        (SELECT sale_dealer.DEPOT_CODE ");
            queryBuffer.append("           FROM DEALERS sale_dealer ");
            queryBuffer.append("          WHERE sale_dealer.ID = w.SALE_DEALER_ID) depotCodeS, ");
            queryBuffer.append("        (SELECT woStatus.WO_STATE_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_STATUS woStatus ");
            queryBuffer.append("          WHERE woStatus.ID = w.ACTUAL_STATUS_ID) woStateName, ");
            queryBuffer.append("        (SELECT woIbsStatus.IBS_6_STATE_NAME ");
            queryBuffer.append("           FROM IBS_STATUS woIbsStatus ");
            queryBuffer.append("          WHERE woIbsStatus.ID = w.IBS_ACTUAL_STATUS_ID) ibs6StateName, ");
            queryBuffer.append("        (SELECT  reason.WORKORDER_REASON_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_STATUS_HISTORIES woStatusHist INNER JOIN WORK_ORDER_REASONS reason ON reason.ID = woStatusHist.WO_REASON_ID ");
            queryBuffer.append("          WHERE woStatusHist.id = (SELECT max(woStatusHist.id) ");
            queryBuffer.append("                                              FROM WORK_ORDER_STATUS_HISTORIES woStatusHist INNER JOIN WORK_ORDER_STATUS workOrderStatus ON workOrderStatus.ID = woStatusHist.WO_STATUS_ID ");
            queryBuffer.append("                                             WHERE woStatusHist.WO_ID = w.ID ");
            queryBuffer.append("                                               AND workOrderStatus.WO_STATE_CODE in ( :statusActiva ,:statusAsignada , ");
            queryBuffer.append("        																			  :statusAgendada , :statusReagendada , ");
            queryBuffer.append("        																			  :statusRealizada , :statusFinalizada , ");
            queryBuffer.append("        																			  :statusPendiente , :statusRechazada , ");
            queryBuffer.append("        																			  :statusCancelada , :statusReasignada ) ");
            queryBuffer.append("                                            ) ) reason, ");
            queryBuffer.append("        (SELECT (SELECT NAME ");
            queryBuffer.append("                   FROM TECHNOLOGIES ");
            
            List<Technology> technologies = this.technologyDAO.getAllIRDTechnologies();
            
            queryBuffer.append(" 	                      WHERE ID = ( CASE ");
            for(Technology tvo : technologies){
            	queryBuffer.append(" 	                      WHEN ( SELECT COUNT(1) ");
            	queryBuffer.append("                                             FROM SHIPPING_ORDER_DETAILS ");
            	queryBuffer.append("                                            WHERE SHIPPING_ORDER_ID=shipping.ID ");
            	queryBuffer.append("                                              AND TECHNOLOGY_ID= "+tvo.getId()+" )>0 THEN "+tvo.getId()+"    	 ");
            }
            
            queryBuffer.append(" 	                                   END )  ) ");

            queryBuffer.append("           FROM SHIPPING_ORDERS shipping ");
            queryBuffer.append("          WHERE shipping.WO_ID=w.ID ");
            queryBuffer.append("            AND ROWNUM=1) model, ");
            queryBuffer.append("        w.CREATION_DATE creationDate, ");
            queryBuffer.append("        w.IMPORT_DATE importDate, ");
            queryBuffer.append("        (SELECT wAssign.DEALER_ASSIGNMENT_DATE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND wAssign.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("            AND wAssign.DEALER_ASSIGNMENT_DATE = ( SELECT MAX(wAssign2.DEALER_ASSIGNMENT_DATE) ");
            queryBuffer.append("                                                     FROM WORK_ORDER_ASSIGNMENTS wAssign2 ");
            queryBuffer.append("                                                    WHERE wAssign2.WO_ID=w.ID) ");
            queryBuffer.append("            AND ROWNUM=1) assignDealerDate, ");
            queryBuffer.append("        (SELECT hora.SERVICE_HOURS_NAME ");
            queryBuffer.append("           FROM SERVICE_HOURS hora ");
            queryBuffer.append("          WHERE ROWNUM=1 ");
            queryBuffer.append("            AND EXISTS(SELECT 1 ");
            queryBuffer.append("                         FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN WORK_ORDER_AGENDAS woa ON woa.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("                        WHERE hora.ID=woa.SERVICE_HOUR_ID AND wAssign.WO_ID=w.ID ");
            queryBuffer.append("                          AND woa.status= :activeStatus ");
            queryBuffer.append("                          AND woa.AGENDATION_DATE = ( SELECT MAX(woa2.AGENDATION_DATE) ");
            queryBuffer.append("                                                        FROM WORK_ORDER_AGENDAS woa2 ");
            queryBuffer.append("                                                       WHERE woa2.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("                                                         AND woa2.status= :activeStatus) ");
            queryBuffer.append("                       ) ");
            queryBuffer.append("         ) jornada, ");
            queryBuffer.append("        (SELECT woa.AGENDATION_DATE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN WORK_ORDER_AGENDAS woa ON woa.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND woa.status= :activeStatus ");
            queryBuffer.append("            AND woa.AGENDATION_DATE = ( SELECT MAX(woa2.AGENDATION_DATE) ");
            queryBuffer.append("                                          FROM WORK_ORDER_AGENDAS woa2 ");
            queryBuffer.append("                                         WHERE woa2.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("                                           AND woa2.status= :activeStatus) ");
            queryBuffer.append("            AND ROWNUM=1 ) jornadaDate, ");
            queryBuffer.append("        w.WO_PROGRAMMING_DATE woProgrammingDate, ");
            queryBuffer.append("        w.WO_REALIZATION_DATE woRealizationDate, ");
            queryBuffer.append("        w.FINALIZATION_DATE finalizationDate, ");
            queryBuffer.append("        w.WO_DESCRIPTION woDescription, ");
            queryBuffer.append("        w.WO_ACTION woAction, ");
            queryBuffer.append("        stype.SERVICE_TYPE_NAME serviceTypeName, ");
            queryBuffer.append("        stype.SERVICE_TYPE_CODE serviceTypeCode, ");
            queryBuffer.append("        s.SERVICE_CODE serviceCode, ");
            queryBuffer.append("        s.SERVICE_NAME serviceName, ");
            queryBuffer.append("        (SELECT dea.DEALER_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN DEALERS dea ON dea.ID=wAssign.DEALER_ID ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND wAssign.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("            AND ROWNUM=1 ");
            queryBuffer.append("         ) assignDealer, ");
            queryBuffer.append("        (SELECT dea.DEPOT_CODE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN DEALERS dea ON dea.ID=wAssign.DEALER_ID ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND wAssign.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("            AND ROWNUM=1) assignDealerCode, ");
            queryBuffer.append("        (SELECT pc.POSTAL_CODE_CODE ");
            queryBuffer.append("           FROM POSTAL_CODES pc ");
            queryBuffer.append("          WHERE pc.ID = w.POSTAL_CODE_ID ");
            queryBuffer.append("         ) postalCodeCode, ");
            queryBuffer.append("        (SELECT state.STATE_NAME ");
            queryBuffer.append("           FROM POSTAL_CODES pc INNER JOIN CITIES city ON city.ID = pc.CITY_ID ");
            queryBuffer.append("                                INNER JOIN STATES state ON state.ID = city.STATE_ID ");
            queryBuffer.append("          WHERE pc.ID = w.POSTAL_CODE_ID) stateName, ");
            queryBuffer.append("        (SELECT city.CITY_NAME ");
            queryBuffer.append("           FROM POSTAL_CODES pc INNER JOIN CITIES city ON city.ID = pc.CITY_ID WHERE pc.ID = w.POSTAL_CODE_ID ");
            queryBuffer.append("         ) cityName, ");
            queryBuffer.append("        (SELECT emp.DOCUMENT_NUMBER ");
            queryBuffer.append("           FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID=crew.CREW_ID ");
            queryBuffer.append("                                                                              AND employeeCrew.IS_RESPONSIBLE= :anIsResponsible ");
            queryBuffer.append("                                        INNER JOIN EMPLOYEES emp ON emp.ID=employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("          WHERE crew.WO_ID=w.ID and crew.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("         ) responsableDoc, ");
            queryBuffer.append("        ( SELECT emp.FIRST_NAME||' '||emp.LAST_NAME ");
            queryBuffer.append("            FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID=crew.CREW_ID ");
            queryBuffer.append("                                                                               AND employeeCrew.IS_RESPONSIBLE= :anIsResponsible ");
            queryBuffer.append("                                         INNER JOIN EMPLOYEES emp ON emp.ID=employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("           WHERE crew.WO_ID=w.ID ");
            queryBuffer.append("                 and crew.IS_ACTIVE= :activeStatus ) responsableName, ");
            queryBuffer.append("        (SELECT woStatusHist.DESCRIPTION ");
            queryBuffer.append("           FROM WORK_ORDER_STATUS_HISTORIES woStatusHist ");
            queryBuffer.append("          WHERE woStatusHist.id = ( SELECT max(woStatusHist.id) ");
            queryBuffer.append("                                      FROM WORK_ORDER_STATUS_HISTORIES woStatusHist ");
            queryBuffer.append("                                     WHERE woStatusHist.WO_ID = w.ID ");                         
            queryBuffer.append("                                       AND woStatusHist.DESCRIPTION IS NOT NULL ) ");
            queryBuffer.append("         ) reasonDesc, ");
            queryBuffer.append("        (SELECT dealer.DEALER_NAME ");
            queryBuffer.append("           FROM DEALERS dealer ");
            queryBuffer.append("          WHERE dealer.ID = w.DEALER_ID) dealerNameWo, ");
            queryBuffer.append("        (SELECT dealer.DEPOT_CODE  ");
            queryBuffer.append("           FROM DEALERS dealer ");
            queryBuffer.append("          WHERE dealer.ID = w.DEALER_ID) deptocodeWo, ");
            queryBuffer.append("        ( SELECT crew.CREW_ID ");
            queryBuffer.append("            FROM WORK_ORDER_CREW_ASSIGMENTS crew ");
            queryBuffer.append("           WHERE crew.WO_ID=w.ID and crew.IS_ACTIVE= :activeStatus ) crewId, ");
            queryBuffer.append("        to_char(MCV.MEDIA_CONTACT_VALUE) customerPhone, ");            
            queryBuffer.append("        CUST.CUSTOMER_ADDRESS customerAddress, ");
            queryBuffer.append("        (select count(1) contDecos ");
            queryBuffer.append("           from WAREHOUSE_ELEMENTS we INNER JOIN RECORD_STATUS r on(R.ID=WE.RECORD_STATUS_ID) ");
            queryBuffer.append("                                      INNER JOIN WAREHOUSES w on(w.id=WE.WAREHOUSE_ID) ");
            queryBuffer.append("                                      INNER JOIN customers c on(c.id=W.CUSTOMER_ID) ");
            queryBuffer.append("                                      INNER JOIN serialized s on(S.ELEMENT_ID=WE.SER_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENTS e on(E.ID=S.ELEMENT_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENT_TYPES et on(Et.ID=E.ELEMENT_TYPE_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENT_MODELS em on(Em.ID=ET.ELEMENT_MODEL_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENT_CLASSES ec on(Ec.ID=EM.ELEMENT_CLASS_ID) ");
            queryBuffer.append("          where R.RECORD_STATUS_CODE = :recordStatusLast ");
            queryBuffer.append("            and c.id=cust.id and EC.ELEMENT_CLASS_CODE=:elementClassDecoder ) contDecos, ");
            queryBuffer.append("        (select count(1) previousVisits ");
            queryBuffer.append("           from WORK_ORDERS wsb inner join WORK_ORDER_TYPES wtsb on(wtsb.ID=Wsb.WO_TYPE_ID) ");
            queryBuffer.append("          where Wsb.CUSTOMER_ID=Cust.ID  ");
            queryBuffer.append("            and WSB.ID!=w.ID "); 
            queryBuffer.append("            and WTsb.WO_TYPE_CODE=:workOrderTypeService ");
            queryBuffer.append("            and WSB.FINALIZATION_DATE >= (:aSysdate-30) ) previousVisits, ");
            queryBuffer.append("        (SELECT emp.id ");
            queryBuffer.append("           FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID = crew.CREW_ID ");
            queryBuffer.append("                                                                                  AND employeeCrew.IS_RESPONSIBLE = :anIsResponsible ");
            queryBuffer.append("                                        INNER JOIN EMPLOYEES emp ON emp.ID = employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("          WHERE crew.WO_ID = w.ID AND crew.IS_ACTIVE = :activeStatus) ibsTechnical, ");
            queryBuffer.append("        (select woStatusHist1.DESCRIPTION observationCompany ");
            queryBuffer.append("           from WORK_ORDER_STATUS_HISTORIES woStatusHist1 inner join WORK_ORDER_STATUS woStatus1 on(woStatus1.ID=woStatusHist1.WO_STATUS_ID) ");
            queryBuffer.append("          where WOSTATUSHIST1.WO_ID = W.ID ");
            queryBuffer.append("            AND woStatusHist1.status_date= (select max(status_date) ");
            queryBuffer.append("                                              from WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join WORK_ORDER_STATUS woStatus2 on(woStatusHist2.WO_STATUS_ID=woStatus2.ID) ");
            queryBuffer.append("                                             where WOSTATUSHIST1.WO_ID = WOSTATUSHIST2.WO_ID   ");
            queryBuffer.append("                                               and woStatusHist2.DESCRIPTION is not null "); 
            queryBuffer.append("                                               and woStatusHist2.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp ");
            queryBuffer.append("                                               and woStatus2.WO_STATE_CODE=:statusRealizada )");
            queryBuffer.append("            AND woStatus1.WO_STATE_CODE=:statusRealizada  ");
            queryBuffer.append("            AND woStatusHist1.DESCRIPTION is not null ");
            queryBuffer.append("            AND woStatusHist1.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp ) observationCompany, ");
            queryBuffer.append("        (select WME.SERIAL_CODE "); 
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id  ");
            queryBuffer.append("            AND (WME.ELEMENT_CODE=:woManagmentElementClassDeco ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassDeco )  ");
            queryBuffer.append("            AND WME.CODE_TYPE_MOVEMENT=:woAttentionRer ");
            queryBuffer.append("            AND WME.IS_SERIALIZED=:elementIsSerialized) retiredIRDSeries, ");
            queryBuffer.append("        (select WME.SERIAL_CODE "); 
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id  ");
            queryBuffer.append("            and (WME.ELEMENT_CODE=:woManagmentElementClassSC ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassSC ) ");
            queryBuffer.append("            and wme.CODE_TYPE_MOVEMENT=:woAttentionRer ");
            queryBuffer.append("            and WME.IS_SERIALIZED=:elementIsSerialized ) retiredSCSeries, ");
            queryBuffer.append("        (select WME.SERIAL_CODE "); 
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id ");
            queryBuffer.append("            and (WME.ELEMENT_CODE=:woManagmentElementClassDeco ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassDeco) ");
            queryBuffer.append("            and wme.CODE_TYPE_MOVEMENT=:woAttentionReu ");
            queryBuffer.append("            and WME.IS_SERIALIZED=:elementIsSerialized ) installedIRDSeries, ");
            queryBuffer.append("        (select WME.SERIAL_CODE  ");
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id ");
            queryBuffer.append("            and (WME.ELEMENT_CODE=:woManagmentElementClassSC ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassSC )  ");
            queryBuffer.append("            and wme.CODE_TYPE_MOVEMENT=:woAttentionReu ");
            queryBuffer.append("            and WME.IS_SERIALIZED=:elementIsSerialized ) installedSCSeries, ");
            queryBuffer.append("         (SELECT U.NAME ");
            queryBuffer.append("            FROM WORK_ORDER_STATUS_HISTORIES woStatusHist1 inner join USERS u on(woStatusHist1.user_id=U.ID) ");
            queryBuffer.append("           WHERE woStatusHist1.id= (SELECT max(WOSTATUSHIST2.ID) ");
            queryBuffer.append("                                      FROM WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join USERS u2 on(woStatusHist2.user_id=U2.ID) ");  
            queryBuffer.append("                                                                           inner join ROLES r2 on(r2.id=U2.ROL_ID) ");
            queryBuffer.append("                                     WHERE WOSTATUSHIST2.WO_ID=W.ID  ");
            queryBuffer.append("                                       AND R2.ROL_CODE=:codeUserControlTower  ");
            queryBuffer.append("                                       AND WOSTATUSHIST2.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp)) dispatcher, ");
            queryBuffer.append("         (SELECT U.LOGIN  ");
            queryBuffer.append("                   FROM WORK_ORDER_STATUS_HISTORIES woStatusHist1 inner join USERS u on(woStatusHist1.user_id=U.ID) ");
            queryBuffer.append("                  WHERE woStatusHist1.id= (SELECT max(WOSTATUSHIST2.ID)  ");
            queryBuffer.append("                                             FROM WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join USERS u2 on(woStatusHist2.user_id=U2.ID) ");  
            queryBuffer.append("                                                                                  inner join ROLES r2 on(r2.id=U2.ROL_ID) ");
            queryBuffer.append("                                            WHERE WOSTATUSHIST2.WO_ID=W.ID  ");
            queryBuffer.append("                                            AND R2.ROL_CODE=:codeUserControlTower  ");
            queryBuffer.append("                                            AND WOSTATUSHIST2.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp)) loginDispatcher, ");
            queryBuffer.append("        (SELECT WOAs.AGENDATION_DATE ");
            queryBuffer.append("           FROM WORK_ORDER_AGENDAS woas ");
            queryBuffer.append("          WHERE woas.id = (SELECT MIN (WOA.ID) ");
            queryBuffer.append("                             FROM    WORK_ORDER_ASSIGNMENTS wa INNER JOIN WORK_ORDER_AGENDAS woa ON WA.ID = WOA.WO_ASSIGNMENTS_ID ");
            queryBuffer.append("                            WHERE w.id = wa.WO_ID)) dateFirstScheduling, ");
            queryBuffer.append("        (SELECT emp.FIRST_NAME || ' ' || emp.LAST_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID = crew.CREW_ID ");
            queryBuffer.append("                                                                                  AND employeeCrew.IS_RESPONSIBLE = :anIsResponsible ");
            queryBuffer.append("                                        INNER JOIN EMPLOYEES emp ON emp.ID = employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("          WHERE crew.WO_ID = w.ID ");
            queryBuffer.append("            AND crew.ID = (SELECT MIN (CREW2.ID) ");
            queryBuffer.append("                             FROM WORK_ORDER_CREW_ASSIGMENTS crew2 INNER JOIN EMPLOYEE_CREWS employeeCrew2 ON employeeCrew2.CREW_ID = crew2.CREW_ID ");
            queryBuffer.append("                                                                                                  AND employeeCrew2.IS_RESPONSIBLE = :anIsResponsible ");
            queryBuffer.append("                                                           INNER JOIN EMPLOYEES emp2 ON emp2.ID = employeeCrew2.EMPLOYEE_ID ");
            queryBuffer.append("                            WHERE crew2.WO_ID = w.ID)) firstResponsibleCrewAssign, ");
            queryBuffer.append("        (SELECT D.DEPOT_CODE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wa INNER JOIN DEALERS d ON (d.id = WA.DEALER_ID) ");
            queryBuffer.append("          WHERE wa.id = (SELECT MIN (wa2.id) ");
            queryBuffer.append("                           FROM WORK_ORDER_ASSIGNMENTS wa2 ");
            queryBuffer.append("                          WHERE W.ID = WA2.WO_ID AND WA2.DEALER_ID IS NOT NULL)) codeDepotDealerFirstAssign, ");
            queryBuffer.append("        (SELECT D.DEALER_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wa INNER JOIN DEALERS d ON (d.id = WA.DEALER_ID) ");
            queryBuffer.append("          WHERE W.ID = WA.WO_ID ");
            queryBuffer.append("            AND wa.id = (SELECT MIN (wa2.id) ");
            queryBuffer.append("                           FROM WORK_ORDER_ASSIGNMENTS wa2 ");
            queryBuffer.append("                          WHERE W.ID = WA2.WO_ID AND WA2.DEALER_ID IS NOT NULL)) nameDealerFirstAssign, ");
            queryBuffer.append("         to_char(crewHelpers.helpers) helpers ");            
            queryBuffer.append("  FROM WORK_ORDERS w INNER JOIN WORK_ORDER_TYPES WT ON wt.id =W.WO_TYPE_ID ");
            queryBuffer.append("                     INNER JOIN WORK_ORDER_STATUS ws ON W.ACTUAL_STATUS_ID = WS.ID ");
            queryBuffer.append("                     INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
            queryBuffer.append("                     INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
            queryBuffer.append("                     INNER JOIN SERVICE_CATEGORIES scategory ON scategory.ID = s.SERVICE_CATEGORY_ID ");
            queryBuffer.append("                     INNER JOIN SERVICE_TYPES stype ON stype.ID = scategory.SERVICE_TYPE_ID ");
            queryBuffer.append("                     INNER JOIN CUSTOMERS cust ON cust.ID = w.CUSTOMER_ID ");
            queryBuffer.append("                     LEFT JOIN (SELECT CMC.CUSTOMER_ID, ");
            queryBuffer.append("                                        substr(wm_concat (MCT.MEDIA_NAME ||'='|| CMC.MEDIA_CONTACT_VALUE),1,4000) MEDIA_CONTACT_VALUE "); 
            queryBuffer.append("                                   FROM CUSTOMER_MEDIA_CONTACTS CMC inner join MEDIA_CONTACT_TYPES MCT on(CMC.CONTACT_TYPE_ID=MCT.ID) ");
            queryBuffer.append("                                  where MCT.MEDIA_CODE=:mediaContactTypeTelepCode ");
            queryBuffer.append("                                        or MCT.MEDIA_CODE=:mediaContactTypeTelephWorkCode ");
            queryBuffer.append("                                        or MCT.MEDIA_CODE=:mediaContactTypeMobileCode ");
            queryBuffer.append("                                  GROUP BY CMC.CUSTOMER_ID) MCV ON(cust.ID=MCV.CUSTOMER_ID) ");
            queryBuffer.append("                     LEFT JOIN ( SELECT crew.WO_ID,substr(wm_concat ( EMP.DOCUMENT_NUMBER || '-' || emp.FIRST_NAME || ' ' || emp.LAST_NAME),1,4000) helpers  ");
            queryBuffer.append("                                   FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID = crew.CREW_ID ");
            queryBuffer.append("                                                                                                          AND employeeCrew.IS_RESPONSIBLE = :anIsNotResponsible ");
            queryBuffer.append("                                                                INNER JOIN EMPLOYEES emp ON emp.ID = employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("                            WHERE crew.IS_ACTIVE = :activeStatus ");
            queryBuffer.append("                         GROUP BY crew.WO_ID) crewHelpers ON (w.id = crewHelpers.wo_id) ");
            queryBuffer.append(" WHERE CREATION_DATE >= :aBeginDate ");
            queryBuffer.append("   AND CREATION_DATE <= :aEndDate ");
            queryBuffer.append("   AND WT.WO_TYPE_CODE in(:workOrderTypeService ");
            queryBuffer.append("                          ,:workOrderTypeDisconnection ");
            queryBuffer.append("                          ,:workOrderTypeInstall ");
            queryBuffer.append("                          ,:workOrderTypeMove ");
            queryBuffer.append("                          ,:workOrderTypeLocalUse) ");
            queryBuffer.append("   AND WS.WO_STATE_CODE in(:statusRealizada ");
            queryBuffer.append("                          ,:statusFinalizada ");
            queryBuffer.append("                          ,:statusCancelada) ");
            queryBuffer.append(" ORDER BY w.WO_CODE ");
            
            Query querySQL = session.createSQLQuery(queryBuffer.toString())
            					    .addScalar("id", Hibernate.STRING)
                                    .addScalar("woCode", Hibernate.STRING)
                                    .addScalar("customerCode", Hibernate.STRING)
                                    .addScalar("customerName", Hibernate.STRING)
                                    .addScalar("customerTypeName", Hibernate.STRING)
                                    .addScalar("dealerNameS", Hibernate.STRING)
                                    .addScalar("depotCodeS", Hibernate.STRING)
                                    .addScalar("woStateName", Hibernate.STRING)
                                    .addScalar("ibs6StateName", Hibernate.STRING)
                                    .addScalar("reason", Hibernate.STRING)
                                    .addScalar("model", Hibernate.STRING)
                                    .addScalar("creationDate", Hibernate.STRING)
                                    .addScalar("importDate", Hibernate.STRING)
                                    .addScalar("assignDealerDate", Hibernate.STRING)
                                    .addScalar("jornada", Hibernate.STRING)
                                    .addScalar("jornadaDate", Hibernate.STRING)
                                    .addScalar("woProgrammingDate", Hibernate.STRING)
                                    .addScalar("woRealizationDate", Hibernate.STRING)
                                    .addScalar("finalizationDate", Hibernate.STRING)
                                    .addScalar("woDescription", Hibernate.STRING)
                                    .addScalar("woAction", Hibernate.STRING)
                                    .addScalar("serviceTypeName", Hibernate.STRING)
                                    .addScalar("serviceTypeCode", Hibernate.STRING)
                                    .addScalar("serviceCode", Hibernate.STRING)
                                    .addScalar("serviceName", Hibernate.STRING)
                                    .addScalar("assignDealer", Hibernate.STRING)
                                    .addScalar("assignDealerCode", Hibernate.STRING)
                                    .addScalar("postalCodeCode", Hibernate.STRING)
                                    .addScalar("stateName", Hibernate.STRING)
                                    .addScalar("cityName", Hibernate.STRING)
                                    .addScalar("responsableDoc", Hibernate.STRING)
                                    .addScalar("responsableName", Hibernate.STRING)
                                    .addScalar("reasonDesc", Hibernate.STRING)
                                    .addScalar("dealerNameWo", Hibernate.STRING)
                                    .addScalar("deptocodeWo", Hibernate.STRING)
                                    .addScalar("crewId", Hibernate.STRING)
                                    .addScalar("customerPhone", Hibernate.STRING)
                                    .addScalar("customerAddress", Hibernate.STRING)
                                    .addScalar("contDecos", Hibernate.STRING)
                                    .addScalar("previousVisits", Hibernate.STRING)
                                    .addScalar("ibsTechnical", Hibernate.STRING)
                                    .addScalar("observationCompany", Hibernate.STRING)
                                    .addScalar("retiredIRDSeries", Hibernate.STRING)
                                    .addScalar("retiredSCSeries", Hibernate.STRING)
                                    .addScalar("installedIRDSeries", Hibernate.STRING)
                                    .addScalar("installedSCSeries", Hibernate.STRING)
                                    .addScalar("dispatcher", Hibernate.STRING)
                                    .addScalar("loginDispatcher", Hibernate.STRING)
                                    .addScalar("dateFirstScheduling", Hibernate.STRING)
                                    .addScalar("firstResponsibleCrewAssign", Hibernate.STRING)
                                    .addScalar("codeDepotDealerFirstAssign", Hibernate.STRING)
                                    .addScalar("nameDealerFirstAssign", Hibernate.STRING)
                                    .addScalar("helpers", Hibernate.STRING)
            					    .setResultTransformer(Transformers.aliasToBean(MonthlyActivityResponseDTO.class));
            
            //Filtro de fecha inicial y final
            querySQL.setParameter("aBeginDate", new Timestamp(request.getBeginDate().getTime()), Hibernate.TIMESTAMP);
            querySQL.setParameter("aEndDate",   new Timestamp(request.getEndDate().getTime()), Hibernate.TIMESTAMP);
            querySQL.setParameter("aSysdate",   new Timestamp(nowDate.getTime()), Hibernate.TIMESTAMP);

            querySQL.setParameter("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("anIsNotResponsible", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity(), Hibernate.STRING );
            
            querySQL.setParameter("statusActiva", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusAsignada", CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusAgendada", CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusReagendada", CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusRealizada", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusFinalizada", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusPendiente", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusRechazada", CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusCancelada", CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusReasignada", CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity(), Hibernate.STRING );
            
            querySQL.setParameter("recordStatusLast", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("elementClassDecoder", CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woStatusWorkOrderTypeChangeHsp", CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woManagmentElementClassDeco", CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_DECO.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woManagmentElementClassSC", CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_SC.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woAttentionRer", CodesBusinessEntityEnum.WO_ATTENTION_RER.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woAttentionReu", CodesBusinessEntityEnum.WO_ATTENTION_REU.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("elementIsSerialized", CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeTelepCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeTelephWorkCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeMobileCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity(), Hibernate.STRING );
            
            //Filtro de tipos de workOrder
            querySQL.setParameter("workOrderTypeService", CodesBusinessEntityEnum.WORKORDER_TYPE_SERVICE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("workOrderTypeDisconnection", CodesBusinessEntityEnum.WORKORDER_TYPE_DISCONNECTION.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("workOrderTypeInstall", CodesBusinessEntityEnum.WORKORDER_TYPE_INSTALL.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("workOrderTypeMove", CodesBusinessEntityEnum.WORKORDER_TYPE_MOVE.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("workOrderTypeLocalUse", CodesBusinessEntityEnum.WORKORDER_TYPE_LOCAL_USE.getCodeEntity(), Hibernate.STRING);
            
            querySQL.setParameter("codeUserControlTower", CodesBusinessEntityEnum.CODE_USER_CONTROL_TOWER.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("woStatusWorkOrderTypeChangeHsp", CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity(), Hibernate.STRING);
            
            
            
        	if( requestInfo != null ){			
	        	querySQL.setFirstResult( requestInfo.getFirstResult() );
	        	querySQL.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	List<MonthlyActivityResponseDTO> responseList = querySQL.list();
        	return responseList;
        }catch (Throwable ex) {
            log.error("== Error getActivityBacklog==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActivityBacklog/ReportsCoreDAO ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MonthlyActivityResponseDTO> getJoinMonthlyActivity(ReportsParameterInputDTO request, Date nowDate, RequestCollectionInfo requestInfo) throws DAOSQLException,
			DAOServiceException {        try {
            log.debug("== Inicio getActivityBacklog/ReportsCoreDAO ==");
            Session session = super.getSession();

            List<String> codes = new ArrayList<String>();
            codes.add(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
            
            StringBuilder queryBuffer = new StringBuilder();
            queryBuffer.append(" SELECT w.ID id, ");
            queryBuffer.append("        w.WO_CODE woCode, ");
            queryBuffer.append("        cust.CUSTOMER_CODE customerCode, ");
            queryBuffer.append("        cust.FIRST_NAME ||' '|| cust.LAST_NAME customerName, ");
            
            queryBuffer.append("        (select ct.CUSTOMER_TYPE_NAME ");
            queryBuffer.append("           from customer_class_types cct ");
			queryBuffer.append("           inner join customer_types ct on (ct.id=cct.customer_type_id) ");
            queryBuffer.append("          where cct.ID = cust.CUST_TYPE_ID) customerTypeName, ");
            
            queryBuffer.append("        (SELECT sale_dealer.DEALER_NAME ");
            queryBuffer.append("           FROM DEALERS sale_dealer ");
            queryBuffer.append("          WHERE sale_dealer.ID = w.SALE_DEALER_ID) dealerNameS, ");
            queryBuffer.append("        (SELECT sale_dealer.DEPOT_CODE ");
            queryBuffer.append("           FROM DEALERS sale_dealer ");
            queryBuffer.append("          WHERE sale_dealer.ID = w.SALE_DEALER_ID) depotCodeS, ");
            queryBuffer.append("        (SELECT woStatus.WO_STATE_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_STATUS woStatus ");
            queryBuffer.append("          WHERE woStatus.ID = w.ACTUAL_STATUS_ID) woStateName, ");
            queryBuffer.append("        (SELECT woIbsStatus.IBS_6_STATE_NAME ");
            queryBuffer.append("           FROM IBS_STATUS woIbsStatus ");
            queryBuffer.append("          WHERE woIbsStatus.ID = w.IBS_ACTUAL_STATUS_ID) ibs6StateName, ");
            queryBuffer.append("        (SELECT  reason.WORKORDER_REASON_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_STATUS_HISTORIES woStatusHist INNER JOIN WORK_ORDER_REASONS reason ON reason.ID = woStatusHist.WO_REASON_ID ");
            queryBuffer.append("          WHERE woStatusHist.id = (SELECT max(woStatusHist.id) ");
            queryBuffer.append("                                              FROM WORK_ORDER_STATUS_HISTORIES woStatusHist INNER JOIN WORK_ORDER_STATUS workOrderStatus ON workOrderStatus.ID = woStatusHist.WO_STATUS_ID ");
            queryBuffer.append("                                             WHERE woStatusHist.WO_ID = w.ID ");
            queryBuffer.append("                                               AND workOrderStatus.WO_STATE_CODE in ( :statusActiva ,:statusAsignada , ");
            queryBuffer.append("        																			  :statusAgendada , :statusReagendada , ");
            queryBuffer.append("        																			  :statusRealizada , :statusFinalizada , ");
            queryBuffer.append("        																			  :statusPendiente , :statusRechazada , ");
            queryBuffer.append("        																			  :statusCancelada , :statusReasignada ) ");
            queryBuffer.append("                                            ) ) reason, ");
            queryBuffer.append("        (SELECT (SELECT NAME ");
            queryBuffer.append("                   FROM TECHNOLOGIES ");
            
            List<Technology> technologies = this.technologyDAO.getAllIRDTechnologies();
            
            queryBuffer.append(" 	                      WHERE ID = ( CASE ");
            for(Technology tvo : technologies){
            	queryBuffer.append(" 	                      WHEN ( SELECT COUNT(1) ");
            	queryBuffer.append("                                             FROM SHIPPING_ORDER_DETAILS ");
            	queryBuffer.append("                                            WHERE SHIPPING_ORDER_ID=shipping.ID ");
            	queryBuffer.append("                                              AND TECHNOLOGY_ID= "+tvo.getId()+" )>0 THEN "+tvo.getId()+"    	 ");
            }
            
            queryBuffer.append(" 	                                   END )  ) ");
            
            queryBuffer.append("           FROM SHIPPING_ORDERS shipping ");
            queryBuffer.append("          WHERE shipping.WO_ID=w.ID ");
            queryBuffer.append("            AND ROWNUM=1) model, ");
            queryBuffer.append("        w.CREATION_DATE creationDate, ");
            queryBuffer.append("        w.IMPORT_DATE importDate, ");
            queryBuffer.append("        (SELECT wAssign.DEALER_ASSIGNMENT_DATE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND wAssign.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("            AND wAssign.DEALER_ASSIGNMENT_DATE = ( SELECT MAX(wAssign2.DEALER_ASSIGNMENT_DATE) ");
            queryBuffer.append("                                                     FROM WORK_ORDER_ASSIGNMENTS wAssign2 ");
            queryBuffer.append("                                                    WHERE wAssign2.WO_ID=w.ID) ");
            queryBuffer.append("            AND ROWNUM=1) assignDealerDate, ");
            queryBuffer.append("        (SELECT hora.SERVICE_HOURS_NAME ");
            queryBuffer.append("           FROM SERVICE_HOURS hora ");
            queryBuffer.append("          WHERE ROWNUM=1 ");
            queryBuffer.append("            AND EXISTS(SELECT 1 ");
            queryBuffer.append("                         FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN WORK_ORDER_AGENDAS woa ON woa.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("                        WHERE hora.ID=woa.SERVICE_HOUR_ID AND wAssign.WO_ID=w.ID ");
            queryBuffer.append("                          AND woa.status= :activeStatus ");
            queryBuffer.append("                          AND woa.AGENDATION_DATE = ( SELECT MAX(woa2.AGENDATION_DATE) ");
            queryBuffer.append("                                                        FROM WORK_ORDER_AGENDAS woa2 ");
            queryBuffer.append("                                                       WHERE woa2.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("                                                         AND woa2.status= :activeStatus) ");
            queryBuffer.append("                       ) ");
            queryBuffer.append("         ) jornada, ");
            queryBuffer.append("        (SELECT woa.AGENDATION_DATE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN WORK_ORDER_AGENDAS woa ON woa.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND woa.status= :activeStatus ");
            queryBuffer.append("            AND woa.AGENDATION_DATE = ( SELECT MAX(woa2.AGENDATION_DATE) ");
            queryBuffer.append("                                          FROM WORK_ORDER_AGENDAS woa2 ");
            queryBuffer.append("                                         WHERE woa2.WO_ASSIGNMENTS_ID=wAssign.ID ");
            queryBuffer.append("                                           AND woa2.status= :activeStatus) ");
            queryBuffer.append("            AND ROWNUM=1 ) jornadaDate, ");
            queryBuffer.append("        w.WO_PROGRAMMING_DATE woProgrammingDate, ");
            queryBuffer.append("        w.WO_REALIZATION_DATE woRealizationDate, ");
            queryBuffer.append("        w.FINALIZATION_DATE finalizationDate, ");
            queryBuffer.append("        w.WO_DESCRIPTION woDescription, ");
            queryBuffer.append("        w.WO_ACTION woAction, ");
            queryBuffer.append("        stype.SERVICE_TYPE_NAME serviceTypeName, ");
            queryBuffer.append("        stype.SERVICE_TYPE_CODE serviceTypeCode, ");
            queryBuffer.append("        s.SERVICE_CODE serviceCode, ");
            queryBuffer.append("        s.SERVICE_NAME serviceName, ");
            queryBuffer.append("        (SELECT dea.DEALER_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN DEALERS dea ON dea.ID=wAssign.DEALER_ID ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND wAssign.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("            AND ROWNUM=1 ");
            queryBuffer.append("         ) assignDealer, ");
            queryBuffer.append("        (SELECT dea.DEPOT_CODE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wAssign INNER JOIN DEALERS dea ON dea.ID=wAssign.DEALER_ID ");
            queryBuffer.append("          WHERE wAssign.WO_ID=w.ID ");
            queryBuffer.append("            AND wAssign.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("            AND ROWNUM=1) assignDealerCode, ");
            queryBuffer.append("        (SELECT pc.POSTAL_CODE_CODE ");
            queryBuffer.append("           FROM POSTAL_CODES pc ");
            queryBuffer.append("          WHERE pc.ID = w.POSTAL_CODE_ID ");
            queryBuffer.append("         ) postalCodeCode, ");
            queryBuffer.append("        (SELECT state.STATE_NAME ");
            queryBuffer.append("           FROM POSTAL_CODES pc INNER JOIN CITIES city ON city.ID = pc.CITY_ID ");
            queryBuffer.append("                                INNER JOIN STATES state ON state.ID = city.STATE_ID ");
            queryBuffer.append("          WHERE pc.ID = w.POSTAL_CODE_ID) stateName, ");
            queryBuffer.append("        (SELECT city.CITY_NAME ");
            queryBuffer.append("           FROM POSTAL_CODES pc INNER JOIN CITIES city ON city.ID = pc.CITY_ID WHERE pc.ID = w.POSTAL_CODE_ID ");
            queryBuffer.append("         ) cityName, ");
            queryBuffer.append("        (SELECT emp.DOCUMENT_NUMBER ");
            queryBuffer.append("           FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID=crew.CREW_ID ");
            queryBuffer.append("                                                                              AND employeeCrew.IS_RESPONSIBLE= :anIsResponsible ");
            queryBuffer.append("                                        INNER JOIN EMPLOYEES emp ON emp.ID=employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("          WHERE crew.WO_ID=w.ID and crew.IS_ACTIVE= :activeStatus ");
            queryBuffer.append("         ) responsableDoc, ");
            queryBuffer.append("        ( SELECT emp.FIRST_NAME||' '||emp.LAST_NAME ");
            queryBuffer.append("            FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID=crew.CREW_ID ");
            queryBuffer.append("                                                                               AND employeeCrew.IS_RESPONSIBLE= :anIsResponsible ");
            queryBuffer.append("                                         INNER JOIN EMPLOYEES emp ON emp.ID=employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("           WHERE crew.WO_ID=w.ID ");
            queryBuffer.append("                 and crew.IS_ACTIVE= :activeStatus ) responsableName, ");
            queryBuffer.append("        (SELECT woStatusHist.DESCRIPTION ");
            queryBuffer.append("           FROM WORK_ORDER_STATUS_HISTORIES woStatusHist ");
            queryBuffer.append("          WHERE woStatusHist.id = ( SELECT max(woStatusHist.id) ");
            queryBuffer.append("                                      FROM WORK_ORDER_STATUS_HISTORIES woStatusHist ");
            queryBuffer.append("                                     WHERE woStatusHist.WO_ID = w.ID ");                         
            queryBuffer.append("                                       AND woStatusHist.DESCRIPTION IS NOT NULL ) ");
            queryBuffer.append("         ) reasonDesc, ");
            queryBuffer.append("        (SELECT dealer.DEALER_NAME ");
            queryBuffer.append("           FROM DEALERS dealer ");
            queryBuffer.append("          WHERE dealer.ID = w.DEALER_ID) dealerNameWo, ");
            queryBuffer.append("        (SELECT dealer.DEPOT_CODE  ");
            queryBuffer.append("           FROM DEALERS dealer ");
            queryBuffer.append("          WHERE dealer.ID = w.DEALER_ID) deptocodeWo, ");
            queryBuffer.append("        ( SELECT crew.CREW_ID ");
            queryBuffer.append("            FROM WORK_ORDER_CREW_ASSIGMENTS crew ");
            queryBuffer.append("           WHERE crew.WO_ID=w.ID and crew.IS_ACTIVE= :activeStatus ) crewId, ");
            queryBuffer.append("        to_char(MCV.MEDIA_CONTACT_VALUE) customerPhone, ");
            queryBuffer.append("        CUST.CUSTOMER_ADDRESS customerAddress, ");
            queryBuffer.append("        (select count(1) contDecos ");
            queryBuffer.append("           from WAREHOUSE_ELEMENTS we INNER JOIN RECORD_STATUS r on(R.ID=WE.RECORD_STATUS_ID) ");
            queryBuffer.append("                                      INNER JOIN WAREHOUSES w on(w.id=WE.WAREHOUSE_ID) ");
            queryBuffer.append("                                      INNER JOIN customers c on(c.id=W.CUSTOMER_ID) ");
            queryBuffer.append("                                      INNER JOIN serialized s on(S.ELEMENT_ID=WE.SER_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENTS e on(E.ID=S.ELEMENT_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENT_TYPES et on(Et.ID=E.ELEMENT_TYPE_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENT_MODELS em on(Em.ID=ET.ELEMENT_MODEL_ID) ");
            queryBuffer.append("                                      INNER JOIN ELEMENT_CLASSES ec on(Ec.ID=EM.ELEMENT_CLASS_ID) ");
            queryBuffer.append("          where R.RECORD_STATUS_CODE = :recordStatusLast ");
            queryBuffer.append("            and c.id=cust.id and EC.ELEMENT_CLASS_CODE=:elementClassDecoder ) contDecos, ");
            queryBuffer.append("        (select count(1) previousVisits ");
            queryBuffer.append("           from WORK_ORDERS wsb inner join WORK_ORDER_TYPES wtsb on(wtsb.ID=Wsb.WO_TYPE_ID) ");
            queryBuffer.append("          where Wsb.CUSTOMER_ID=Cust.ID  ");
            queryBuffer.append("            and WSB.ID!=w.ID "); 
            queryBuffer.append("            and WTsb.WO_TYPE_CODE=:workOrderTypeService ");
            queryBuffer.append("            and WSB.FINALIZATION_DATE >= (:aSysdate-30) ) previousVisits, ");
            queryBuffer.append("        (SELECT emp.id ");
            queryBuffer.append("           FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID = crew.CREW_ID ");
            queryBuffer.append("                                                                                  AND employeeCrew.IS_RESPONSIBLE = :anIsResponsible ");
            queryBuffer.append("                                        INNER JOIN EMPLOYEES emp ON emp.ID = employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("          WHERE crew.WO_ID = w.ID AND crew.IS_ACTIVE = :activeStatus) ibsTechnical, ");
            queryBuffer.append("        (select woStatusHist1.DESCRIPTION observationCompany ");
            queryBuffer.append("           from WORK_ORDER_STATUS_HISTORIES woStatusHist1 inner join WORK_ORDER_STATUS woStatus1 on(woStatus1.ID=woStatusHist1.WO_STATUS_ID) ");
            queryBuffer.append("          where WOSTATUSHIST1.WO_ID = W.ID ");
            queryBuffer.append("            AND woStatusHist1.status_date= (select max(status_date) ");
            queryBuffer.append("                                              from WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join WORK_ORDER_STATUS woStatus2 on(woStatusHist2.WO_STATUS_ID=woStatus2.ID) ");
            queryBuffer.append("                                             where WOSTATUSHIST1.WO_ID = WOSTATUSHIST2.WO_ID   ");
            queryBuffer.append("                                               and woStatusHist2.DESCRIPTION is not null "); 
            queryBuffer.append("                                               and woStatusHist2.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp ");
            queryBuffer.append("                                               and woStatus2.WO_STATE_CODE=:statusRealizada )");
            queryBuffer.append("            AND woStatus1.WO_STATE_CODE=:statusRealizada  ");
            queryBuffer.append("            AND woStatusHist1.DESCRIPTION is not null ");
            queryBuffer.append("            AND woStatusHist1.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp ) observationCompany, ");
            queryBuffer.append("        (select WME.SERIAL_CODE "); 
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id  ");
            queryBuffer.append("            AND (WME.ELEMENT_CODE=:woManagmentElementClassDeco ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassDeco )  ");
            queryBuffer.append("            AND WME.CODE_TYPE_MOVEMENT=:woAttentionRer ");
            queryBuffer.append("            AND WME.IS_SERIALIZED=:elementIsSerialized) retiredIRDSeries, ");
            queryBuffer.append("        (select WME.SERIAL_CODE "); 
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id  ");
            queryBuffer.append("            and (WME.ELEMENT_CODE=:woManagmentElementClassSC ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassSC ) ");
            queryBuffer.append("            and wme.CODE_TYPE_MOVEMENT=:woAttentionRer ");
            queryBuffer.append("            and WME.IS_SERIALIZED=:elementIsSerialized ) retiredSCSeries, ");
            queryBuffer.append("        (select WME.SERIAL_CODE "); 
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id ");
            queryBuffer.append("            and (WME.ELEMENT_CODE=:woManagmentElementClassDeco ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassDeco) ");
            queryBuffer.append("            and wme.CODE_TYPE_MOVEMENT=:woAttentionReu ");
            queryBuffer.append("            and WME.IS_SERIALIZED=:elementIsSerialized ) installedIRDSeries, ");
            queryBuffer.append("        (select WME.SERIAL_CODE  ");
            queryBuffer.append("           from WORK_ORDER_MANAGMENT_ELEMENTS wme ");  
            queryBuffer.append("          where WME.work_order_service_id=wos.id ");
            queryBuffer.append("            and (WME.ELEMENT_CODE=:woManagmentElementClassSC ");
            queryBuffer.append("                 or WME.CODE_ELEMENT_CLASS_TYPE=:woManagmentElementClassSC )  ");
            queryBuffer.append("            and wme.CODE_TYPE_MOVEMENT=:woAttentionReu ");
            queryBuffer.append("            and WME.IS_SERIALIZED=:elementIsSerialized ) installedSCSeries, ");
            queryBuffer.append("         (SELECT U.NAME ");
            queryBuffer.append("            FROM WORK_ORDER_STATUS_HISTORIES woStatusHist1 inner join USERS u on(woStatusHist1.user_id=U.ID) ");
            queryBuffer.append("           WHERE woStatusHist1.id= (SELECT max(WOSTATUSHIST2.ID) ");
            queryBuffer.append("                                      FROM WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join USERS u2 on(woStatusHist2.user_id=U2.ID) ");  
            queryBuffer.append("                                                                           inner join ROLES r2 on(r2.id=U2.ROL_ID) ");
            queryBuffer.append("                                     WHERE WOSTATUSHIST2.WO_ID=W.ID  ");
            queryBuffer.append("                                       AND R2.ROL_CODE=:codeUserControlTower  ");
            queryBuffer.append("                                       AND WOSTATUSHIST2.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp)) dispatcher, ");
            queryBuffer.append("         (SELECT U.LOGIN  ");
            queryBuffer.append("                   FROM WORK_ORDER_STATUS_HISTORIES woStatusHist1 inner join USERS u on(woStatusHist1.user_id=U.ID) ");
            queryBuffer.append("                  WHERE woStatusHist1.id= (SELECT max(WOSTATUSHIST2.ID)  ");
            queryBuffer.append("                                             FROM WORK_ORDER_STATUS_HISTORIES woStatusHist2 inner join USERS u2 on(woStatusHist2.user_id=U2.ID) ");  
            queryBuffer.append("                                                                                  inner join ROLES r2 on(r2.id=U2.ROL_ID) ");
            queryBuffer.append("                                            WHERE WOSTATUSHIST2.WO_ID=W.ID  ");
            queryBuffer.append("                                            AND R2.ROL_CODE=:codeUserControlTower  ");
            queryBuffer.append("                                            AND WOSTATUSHIST2.TYPE_CHANGE=:woStatusWorkOrderTypeChangeHsp)) loginDispatcher, ");
            queryBuffer.append("        (SELECT WOAs.AGENDATION_DATE ");
            queryBuffer.append("           FROM WORK_ORDER_AGENDAS woas ");
            queryBuffer.append("          WHERE woas.id = (SELECT MIN (WOA.ID) ");
            queryBuffer.append("                             FROM    WORK_ORDER_ASSIGNMENTS wa INNER JOIN WORK_ORDER_AGENDAS woa ON WA.ID = WOA.WO_ASSIGNMENTS_ID ");
            queryBuffer.append("                            WHERE w.id = wa.WO_ID)) dateFirstScheduling, ");
            queryBuffer.append("        (SELECT emp.FIRST_NAME || ' ' || emp.LAST_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID = crew.CREW_ID ");
            queryBuffer.append("                                                                                  AND employeeCrew.IS_RESPONSIBLE = :anIsResponsible ");
            queryBuffer.append("                                        INNER JOIN EMPLOYEES emp ON emp.ID = employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("          WHERE crew.WO_ID = w.ID ");
            queryBuffer.append("            AND crew.ID = (SELECT MIN (CREW2.ID) ");
            queryBuffer.append("                             FROM WORK_ORDER_CREW_ASSIGMENTS crew2 INNER JOIN EMPLOYEE_CREWS employeeCrew2 ON employeeCrew2.CREW_ID = crew2.CREW_ID ");
            queryBuffer.append("                                                                                                  AND employeeCrew2.IS_RESPONSIBLE = :anIsResponsible ");
            queryBuffer.append("                                                           INNER JOIN EMPLOYEES emp2 ON emp2.ID = employeeCrew2.EMPLOYEE_ID ");
            queryBuffer.append("                            WHERE crew2.WO_ID = w.ID)) firstResponsibleCrewAssign, ");
            queryBuffer.append("        (SELECT D.DEPOT_CODE ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wa INNER JOIN DEALERS d ON (d.id = WA.DEALER_ID) ");
            queryBuffer.append("          WHERE wa.id = (SELECT MIN (wa2.id) ");
            queryBuffer.append("                           FROM WORK_ORDER_ASSIGNMENTS wa2 ");
            queryBuffer.append("                          WHERE W.ID = WA2.WO_ID AND WA2.DEALER_ID IS NOT NULL)) codeDepotDealerFirstAssign, ");
            queryBuffer.append("        (SELECT D.DEALER_NAME ");
            queryBuffer.append("           FROM WORK_ORDER_ASSIGNMENTS wa INNER JOIN DEALERS d ON (d.id = WA.DEALER_ID) ");
            queryBuffer.append("          WHERE W.ID = WA.WO_ID ");
            queryBuffer.append("            AND wa.id = (SELECT MIN (wa2.id) ");
            queryBuffer.append("                           FROM WORK_ORDER_ASSIGNMENTS wa2 ");
            queryBuffer.append("                          WHERE W.ID = WA2.WO_ID AND WA2.DEALER_ID IS NOT NULL)) nameDealerFirstAssign, ");
            queryBuffer.append("         to_char(crewHelpers.helpers) helpers ");
            queryBuffer.append("  FROM WORK_ORDERS w INNER JOIN WORK_ORDER_TYPES WT ON wt.id =W.WO_TYPE_ID ");
            queryBuffer.append("                     INNER JOIN WORK_ORDER_STATUS ws ON W.ACTUAL_STATUS_ID = WS.ID ");
            queryBuffer.append("                     INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
            queryBuffer.append("                     INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
            queryBuffer.append("                     INNER JOIN SERVICE_CATEGORIES scategory ON scategory.ID = s.SERVICE_CATEGORY_ID ");
            queryBuffer.append("                     INNER JOIN SERVICE_TYPES stype ON stype.ID = scategory.SERVICE_TYPE_ID ");
            queryBuffer.append("                     INNER JOIN CUSTOMERS cust ON cust.ID = w.CUSTOMER_ID ");
            queryBuffer.append("                     LEFT JOIN (SELECT CMC.CUSTOMER_ID, ");
            queryBuffer.append("                                        substr(wm_concat (MCT.MEDIA_NAME ||'='|| CMC.MEDIA_CONTACT_VALUE),1,4000) MEDIA_CONTACT_VALUE "); 
            queryBuffer.append("                                   FROM CUSTOMER_MEDIA_CONTACTS CMC inner join MEDIA_CONTACT_TYPES MCT on(CMC.CONTACT_TYPE_ID=MCT.ID) ");
            queryBuffer.append("                                  where MCT.MEDIA_CODE=:mediaContactTypeTelepCode ");
            queryBuffer.append("                                        or MCT.MEDIA_CODE=:mediaContactTypeTelephWorkCode ");
            queryBuffer.append("                                        or MCT.MEDIA_CODE=:mediaContactTypeMobileCode ");
            queryBuffer.append("                                  GROUP BY CMC.CUSTOMER_ID) MCV ON(cust.ID=MCV.CUSTOMER_ID) ");
            queryBuffer.append("                     LEFT JOIN ( SELECT crew.WO_ID,substr(wm_concat ( EMP.DOCUMENT_NUMBER || '-' || emp.FIRST_NAME || ' ' || emp.LAST_NAME),1,4000) helpers  ");
            queryBuffer.append("                                   FROM WORK_ORDER_CREW_ASSIGMENTS crew INNER JOIN EMPLOYEE_CREWS employeeCrew ON employeeCrew.CREW_ID = crew.CREW_ID ");
            queryBuffer.append("                                                                                                          AND employeeCrew.IS_RESPONSIBLE = :anIsNotResponsible ");
            queryBuffer.append("                                                                INNER JOIN EMPLOYEES emp ON emp.ID = employeeCrew.EMPLOYEE_ID ");
            queryBuffer.append("                            WHERE crew.IS_ACTIVE = :activeStatus ");
            queryBuffer.append("                         GROUP BY crew.WO_ID) crewHelpers ON (w.id = crewHelpers.wo_id) ");
            queryBuffer.append(" WHERE CREATION_DATE >= :aBeginDate ");
            queryBuffer.append("   AND CREATION_DATE <= :aEndDate ");
            queryBuffer.append("   AND WT.WO_TYPE_CODE in(:workOrderTypeService ");
            queryBuffer.append("                          ,:workOrderTypeDisconnection ");
            queryBuffer.append("                          ,:workOrderTypeInstall ");
            queryBuffer.append("                          ,:workOrderTypeMove ");
            queryBuffer.append("                          ,:workOrderTypeLocalUse) ");
            queryBuffer.append(" ORDER BY w.WO_CODE ");
            
            Query querySQL = session.createSQLQuery(queryBuffer.toString())
            					    .addScalar("id", Hibernate.STRING)
                                    .addScalar("woCode", Hibernate.STRING)
                                    .addScalar("customerCode", Hibernate.STRING)
                                    .addScalar("customerName", Hibernate.STRING)
                                    .addScalar("customerTypeName", Hibernate.STRING)
                                    .addScalar("dealerNameS", Hibernate.STRING)
                                    .addScalar("depotCodeS", Hibernate.STRING)
                                    .addScalar("woStateName", Hibernate.STRING)
                                    .addScalar("ibs6StateName", Hibernate.STRING)
                                    .addScalar("reason", Hibernate.STRING)
                                    .addScalar("model", Hibernate.STRING)
                                    .addScalar("creationDate", Hibernate.STRING)
                                    .addScalar("importDate", Hibernate.STRING)
                                    .addScalar("assignDealerDate", Hibernate.STRING)
                                    .addScalar("jornada", Hibernate.STRING)
                                    .addScalar("jornadaDate", Hibernate.STRING)
                                    .addScalar("woProgrammingDate", Hibernate.STRING)
                                    .addScalar("woRealizationDate", Hibernate.STRING)
                                    .addScalar("finalizationDate", Hibernate.STRING)
                                    .addScalar("woDescription", Hibernate.STRING)
                                    .addScalar("woAction", Hibernate.STRING)
                                    .addScalar("serviceTypeName", Hibernate.STRING)
                                    .addScalar("serviceTypeCode", Hibernate.STRING)
                                    .addScalar("serviceCode", Hibernate.STRING)
                                    .addScalar("serviceName", Hibernate.STRING)
                                    .addScalar("assignDealer", Hibernate.STRING)
                                    .addScalar("assignDealerCode", Hibernate.STRING)
                                    .addScalar("postalCodeCode", Hibernate.STRING)
                                    .addScalar("stateName", Hibernate.STRING)
                                    .addScalar("cityName", Hibernate.STRING)
                                    .addScalar("responsableDoc", Hibernate.STRING)
                                    .addScalar("responsableName", Hibernate.STRING)
                                    .addScalar("reasonDesc", Hibernate.STRING)
                                    .addScalar("dealerNameWo", Hibernate.STRING)
                                    .addScalar("deptocodeWo", Hibernate.STRING)
                                    .addScalar("crewId", Hibernate.STRING)
                                    .addScalar("customerPhone", Hibernate.STRING)
                                    .addScalar("customerAddress", Hibernate.STRING)
                                    .addScalar("contDecos", Hibernate.STRING)
                                    .addScalar("previousVisits", Hibernate.STRING)
                                    .addScalar("ibsTechnical", Hibernate.STRING)
                                    .addScalar("observationCompany", Hibernate.STRING)
                                    .addScalar("retiredIRDSeries", Hibernate.STRING)
                                    .addScalar("retiredSCSeries", Hibernate.STRING)
                                    .addScalar("installedIRDSeries", Hibernate.STRING)
                                    .addScalar("installedSCSeries", Hibernate.STRING)
                                    .addScalar("dispatcher", Hibernate.STRING)
                                    .addScalar("loginDispatcher", Hibernate.STRING)
                                    .addScalar("dateFirstScheduling", Hibernate.STRING)
                                    .addScalar("firstResponsibleCrewAssign", Hibernate.STRING)
                                    .addScalar("codeDepotDealerFirstAssign", Hibernate.STRING)
                                    .addScalar("nameDealerFirstAssign", Hibernate.STRING)
                                    .addScalar("helpers", Hibernate.STRING)
            					    .setResultTransformer(Transformers.aliasToBean(MonthlyActivityResponseDTO.class));
            
          //Filtro de fecha inicial y final
            querySQL.setParameter("aBeginDate", new Timestamp(request.getBeginDate().getTime()), Hibernate.TIMESTAMP);
            querySQL.setParameter("aEndDate",   new Timestamp(request.getEndDate().getTime()), Hibernate.TIMESTAMP);
            querySQL.setParameter("aSysdate",   new Timestamp(nowDate.getTime()), Hibernate.TIMESTAMP);

            querySQL.setParameter("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("anIsNotResponsible", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity(), Hibernate.STRING );
            
            querySQL.setParameter("statusActiva", CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusAsignada", CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusAgendada", CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusReagendada", CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusRealizada", CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusFinalizada", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusPendiente", CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusRechazada", CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusCancelada", CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("statusReasignada", CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity(), Hibernate.STRING );
            
            querySQL.setParameter("recordStatusLast", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("elementClassDecoder", CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woStatusWorkOrderTypeChangeHsp", CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woManagmentElementClassDeco", CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_DECO.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woManagmentElementClassSC", CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_SC.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woAttentionRer", CodesBusinessEntityEnum.WO_ATTENTION_RER.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("woAttentionReu", CodesBusinessEntityEnum.WO_ATTENTION_REU.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("elementIsSerialized", CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeTelepCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeTelephWorkCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("mediaContactTypeMobileCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity(), Hibernate.STRING );
            
            //Filtro de tipos de workOrder
            querySQL.setParameter("workOrderTypeService", CodesBusinessEntityEnum.WORKORDER_TYPE_SERVICE.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("workOrderTypeDisconnection", CodesBusinessEntityEnum.WORKORDER_TYPE_DISCONNECTION.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("workOrderTypeInstall", CodesBusinessEntityEnum.WORKORDER_TYPE_INSTALL.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("workOrderTypeMove", CodesBusinessEntityEnum.WORKORDER_TYPE_MOVE.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("workOrderTypeLocalUse", CodesBusinessEntityEnum.WORKORDER_TYPE_LOCAL_USE.getCodeEntity(), Hibernate.STRING);
            
            querySQL.setParameter("codeUserControlTower", CodesBusinessEntityEnum.CODE_USER_CONTROL_TOWER.getCodeEntity(), Hibernate.STRING);
            querySQL.setParameter("woStatusWorkOrderTypeChangeHsp", CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity(), Hibernate.STRING);
            
        	if( requestInfo != null ){			
	        	querySQL.setFirstResult( requestInfo.getFirstResult() );
	        	querySQL.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	List<MonthlyActivityResponseDTO> responseList = querySQL.list();
        	return responseList;
        }catch (Throwable ex) {
            log.error("== Error getActivityBacklog==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActivityBacklog/ReportsCoreDAO ==");
        }
	}
	/**
	 * CC54 Wo Pendientes por falta de materiales
	 * 
	 * release_4.2.1.5_Spira 28917 – Reporte WO_PENDING_MATERIALS  - reportar la observación en el momento de cambio de estado
     * modificación
     * ialessan
     * marzo 2014 
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WoPendingLackMaterialsDTO> getWoPendingLackMaterials(Long countryId, RequestCollectionInfo requestInfo, String reasonCode) throws DAOSQLException, DAOServiceException {
        try {
            log.debug("== Inicio getWoPendingLackMaterials/ReportsCoreDAO ==");
            Session session = super.getSession();

            StringBuilder query = new StringBuilder();
            

            query.append(" SELECT  DE.DEPOT_CODE             depotCode, ");
            query.append("         DE.DEALER_NAME            dealerName, ");
            query.append("         WH.STATUS_DATE            statusDate, ");
            query.append("         CUS.CUSTOMER_CODE         ibsCustomerCode, ");
            query.append("         WO.WO_CODE                ibsWoCode, ");
            query.append("         WT.WO_TYPE_NAME           woTypeName, ");
            query.append("         WR.WORKORDER_REASON_NAME  woReasonName, ");
          //query.append("         WO.WO_DESCRIPTION         woDescription ");
            query.append("         WH.DESCRIPTION            woDescription ");            
            query.append("   FROM  WORK_ORDERS              WO ");
            query.append("  INNER  JOIN WORK_ORDER_STATUS_HISTORIES   WH   ON (WO.ID           = WH.WO_ID) ");
            query.append("  INNER  JOIN WORK_ORDER_REASONS   		  WR   ON (WH.WO_REASON_ID = WR.ID) ");
            query.append("  INNER  JOIN WORK_ORDER_STATUS    		  WST  ON (WST.ID          = WH.WO_STATUS_ID) ");
            query.append("  INNER  JOIN DEALERS              		  DE   ON (WO.DEALER_ID    = DE.ID) ");
            query.append("  INNER  JOIN CUSTOMERS            		  CUS  ON (CUS.ID = WO.CUSTOMER_ID)  ");
            query.append("  INNER  JOIN WORK_ORDER_TYPES    		  WT   ON (WO.WO_TYPE_ID   = WT.ID) ");            
            query.append("  WHERE  WST.WO_STATE_CODE          = :statusRejected ");
            query.append("    AND  WR.WORKORDER_REASON_CODE   = :reasonCode ");
            query.append("    AND  WO.COUNTRY_ID              = :countryId ");

            Query querySQL = session.createSQLQuery(query.toString())
					.addScalar("depotCode", Hibernate.STRING)
					.addScalar("dealerName", Hibernate.STRING)
					.addScalar("statusDate", Hibernate.TIMESTAMP)
					.addScalar("ibsCustomerCode", Hibernate.STRING)
					.addScalar("ibsWoCode", Hibernate.STRING)
					.addScalar("woTypeName", Hibernate.STRING)
					.addScalar("woReasonName", Hibernate.STRING)
					.addScalar("woDescription", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(WoPendingLackMaterialsDTO.class));
            
            querySQL.setParameter("countryId", countryId, Hibernate.LONG );
            querySQL.setParameter("statusRejected", CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity(), Hibernate.STRING );
            querySQL.setParameter("reasonCode",reasonCode, Hibernate.STRING );
                        
        	if( requestInfo != null ){			
	        	querySQL.setFirstResult( requestInfo.getFirstResult() );
	        	querySQL.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	List<WoPendingLackMaterialsDTO> responseList = querySQL.list();
        	return responseList;            
	        }catch (Throwable ex) {
	            log.error("== Error getWoPendingLackMaterials==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getWoPendingLackMaterials/ReportsCoreDAO ==");
	        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ReportsCoreDAOLocal#getReportWorkOrderRejection(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReportWorkOrderRejectionAndFileResponseDTO getReportWorkOrderRejection(String woTypesCodes,
			                                                                      String woStateCodeReject,
			                                                                      Long countryId,
			                                                                      RequestCollectionInfo requestInfo) throws DAOSQLException, DAOServiceException{
        try {
            log.debug("== Inicio getReportsComplyAndScheduleDTO/WorkOrderDAO ==");
            Session session = super.getSession();

            StringBuilder querySelect = new StringBuilder();
            StringBuilder queryFrom   = new StringBuilder();
            StringBuilder queryWhere   = new StringBuilder();
            StringBuilder queryOrder   = new StringBuilder();
            
            querySelect.append(" SELECT C.CUSTOMER_CODE customerCode, ");
            querySelect.append("        W.WO_CODE woCode, ");
            querySelect.append("        WS.WO_STATE_CODE woStateCode, ");
            querySelect.append("        SER.SERVICE_CODE serviceCode, ");
            querySelect.append("        CC.CITY_NAME cityName, ");
            querySelect.append("        WOS.SERIAL_NUMBER serialNumber, ");
            querySelect.append("        to_char(W.CREATION_DATE,'dd/mm/yyyy hh:mi:ss am') creationDate, ");
            querySelect.append("        to_char(WOA.DEALER_UNASSIGNMENT_DATE,'dd/mm/yyyy hh:mi:ss am') dateUnassignmentDealer, ");
            querySelect.append("        D.DEALER_CODE ||' '|| nvl(D.DEALER_NAME,' ') dealerAssignment,  ");
            querySelect.append("        to_char(WOA.DEALER_ASSIGNMENT_DATE,'dd/mm/yyyy hh:mi:ss am') dateAssignmentDealer, ");
            querySelect.append("        WOR.WORKORDER_REASON_NAME woReasonName, ");
            querySelect.append("        WSH.DESCRIPTION observation, ");
            querySelect.append("        nvl(((trunc(WOA.DEALER_UNASSIGNMENT_DATE)-trunc(WOA.DEALER_ASSIGNMENT_DATE))+1),0) countDate ");
            queryFrom.append("   FROM WORK_ORDERS w INNER JOIN WORK_ORDER_STATUS ws ON (WS.ID = W.ACTUAL_STATUS_ID) ");
            queryFrom.append("                      INNER JOIN CUSTOMERS c ON (c.ID = W.CUSTOMER_ID) ");
            queryFrom.append("                      INNER JOIN POSTAL_CODES pcc ON (pcc.ID = C.POSTAL_CODE_ID) INNER JOIN CITIES CC ON (CC.ID = PCC.CITY_ID) ");
            queryFrom.append("                      INNER JOIN WORK_ORDER_TYPES wt ON (wt.ID = W.WO_TYPE_ID) ");
            queryFrom.append("                      INNER JOIN WORK_ORDER_SERVICES wos ON (W.id = WoS.WO_ID) ");
            queryFrom.append("                      INNER JOIN SERVICES ser ON (ser.id = WOS.SERVICE_ID) ");
            queryFrom.append("                      INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON (W.id = WOA.WO_ID) ");
            queryFrom.append("                      INNER JOIN DEALERS d ON (d.id = WOA.DEALER_ID) ");
            queryFrom.append("                      INNER JOIN WORK_ORDER_STATUS_HISTORIES wsh ON (wsh.id = WOA.WO_STATUS_HISTORY_ID) ");
            queryFrom.append("                      INNER JOIN WORK_ORDER_REASONS wor ON (wor.id = WSH.WO_REASON_ID) ");
            queryWhere.append("  WHERE WS.WO_STATE_CODE = :woStateCodeReject ");
            queryWhere.append("  AND W.COUNTRY_ID = :aCountryId ");
            
            String woTypes[] =  woTypesCodes.trim().split(",");
            String strQueryWoTypeCodes = "";
            String strOrQuery = "";
            for(int i=0; i<woTypes.length; ++i){
            	strQueryWoTypeCodes += strOrQuery + " WT.WO_TYPE_CODE = :woTypeCode"+ i + " ";
            	strOrQuery = " or ";
            }
            
            if(!strQueryWoTypeCodes.equals("")){
            	strQueryWoTypeCodes = " AND ( " +  strQueryWoTypeCodes + ") ";
            }
            
            queryWhere.append(strQueryWoTypeCodes);
            
            queryOrder.append("  ORDER BY W.WO_CODE,SER.SERVICE_CODE,WOA.DEALER_ASSIGNMENT_DATE ");
            
            Query query = session.createSQLQuery(querySelect.toString() + queryFrom.toString() + queryWhere.toString() + queryOrder.toString())
            					 .addScalar("customerCode",Hibernate.STRING)
         						 .addScalar("woCode",Hibernate.STRING)
         						 .addScalar("woStateCode",Hibernate.STRING)
         						 .addScalar("serviceCode",Hibernate.STRING)
         						 .addScalar("cityName",Hibernate.STRING)
         						 .addScalar("serialNumber",Hibernate.STRING)
         						 .addScalar("creationDate",Hibernate.STRING)
         						 .addScalar("dateUnassignmentDealer",Hibernate.STRING)
         						 .addScalar("dealerAssignment",Hibernate.STRING)
         						 .addScalar("dateAssignmentDealer",Hibernate.STRING)
         						 .addScalar("woReasonName",Hibernate.STRING)
         						 .addScalar("observation",Hibernate.STRING)
         						 .addScalar("countDate",Hibernate.STRING)
                                 .setResultTransformer(Transformers.aliasToBean(ReportWorkOrderRejectionDTO.class));
            
            query.setParameter("aCountryId", countryId, Hibernate.LONG);
            query.setParameter("woStateCodeReject", woStateCodeReject, Hibernate.STRING);
            for(int i=0; i<woTypes.length; ++i){
            	query.setParameter("woTypeCode"+i, woTypes[i], Hibernate.STRING);
            }
            
            Long recordQty = 0L;
        	if( requestInfo != null ){	
        		Query countQuery = session.createSQLQuery(" select count(*) " + queryFrom.toString() + queryWhere.toString());
        		
        		countQuery.setParameter("aCountryId", countryId, Hibernate.LONG);
        		countQuery.setParameter("woStateCodeReject", woStateCodeReject, Hibernate.STRING);
                for(int i=0; i<woTypes.length; ++i){
                	countQuery.setParameter("woTypeCode"+i, woTypes[i], Hibernate.STRING);
                }
                
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestInfo.getFirstResult() );
				query.setMaxResults( requestInfo.getMaxResults() );				
        	}
        	
        	ReportWorkOrderRejectionAndFileResponseDTO response = new ReportWorkOrderRejectionAndFileResponseDTO();
        	List<ReportWorkOrderRejectionDTO> responseList = query.list();
        	if( requestInfo != null )
				populatePaginationInfo( response, requestInfo.getPageSize(), requestInfo.getPageIndex(), responseList.size(), recordQty.intValue() );
        	response.setReportWorkOrderRejectionDTO(responseList);
        	return response;
        	
        }catch (Throwable ex) {
            log.error("== Error getReportsComplyAndScheduleDTO==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReportsComplyAndScheduleDTO/WorkOrderDAO ==");
        }
			
	}
	@Override
	public List<AuxTechnicianDetailsDTO> getAuxiliarTechnicianByDate(
			Long countryId, RequestCollectionInfo requestInfo, Date beginDate, Date endDate, Set<ScheduleReportParameter> scheduleReportParamSet)
			throws DAOSQLException, DAOServiceException {
		try {
			log.debug("== Inicio getWoPendingLackMaterials/ReportsCoreDAO ==");
	        Session session = super.getSession();
	
	        StringBuilder query = new StringBuilder();
	        
	
	        query.append(" SELECT  WO.WO_CODE             woCode, ");
	        query.append("         ER.ROL_CODE            employeeCode, ");
	        query.append("         E.DOCUMENT_NUMBER            employeeDocument, ");
	        query.append("         E.FIRST_NAME ||' '|| E.LAST_NAME         employeeName ");
	        query.append("   FROM  WORK_ORDERS              WO ");
	        query.append("  INNER  JOIN WORK_ORDER_CREW_ASSIGMENTS WOCA ON (WO.ID = WOCA.WO_ID) ");
	        query.append("  INNER  JOIN EMPLOYEE_CREWS EC               ON (WOCA.CREW_ID = EC.CREW_ID) ");
	        query.append("  INNER  JOIN EMPLOYEES E                     ON (E.ID = EC.EMPLOYEE_ID) ");
	        query.append("  INNER  JOIN EMPLOYEE_ROLES ER               ON (ER.ID = EC.EMPLOYEE_ROL_ID) ");
	        query.append("  INNER  JOIN WORK_ORDER_STATUS WOS           ON (WOS.ID = WO.ACTUAL_STATUS_ID) ");
	        query.append("  WHERE WO.IMPORT_DATE BETWEEN TO_DATE(:beginDate, 'yyyy-mm-dd hh24:mi:ss') ");
			query.append("  AND TO_DATE(:endDate, 'yyyy-mm-dd hh24:mi:ss')");

			if(scheduleReportParamSet != null){
				//AND ( WOS.CODE = 'R' OR WOS.CODE = 'P')
				Object[] srArray = scheduleReportParamSet.toArray();
				
				for(int i = 0; i < srArray.length; i++){
					if(i == 0){ //is the first one.
						query.append(" AND ( ");					
					}
					ScheduleReportParameter srParam = (ScheduleReportParameter)srArray[i];
					
					query.append(" WOS.WO_STATE_CODE = '" + srParam.getValue() + "'");
					
					if(i != srArray.length-1){ //is not the last one.
						query.append(" OR ");
					}else{
						query.append(" ) ");
					}	
				}
			}
			
			Query querySQL = session.createSQLQuery(query.toString())
					.addScalar("woCode", Hibernate.STRING)
					.addScalar("employeeCode", Hibernate.STRING)
					.addScalar("employeeDocument", Hibernate.STRING)
					.addScalar("employeeName", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(AuxTechnicianDetailsDTO.class));
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        querySQL.setParameter("beginDate", dateFormat.format(beginDate.getTime()));
	        querySQL.setParameter("endDate", dateFormat.format(endDate.getTime()));
	        
	                    
	    	if( requestInfo != null ){			
	        	querySQL.setFirstResult( requestInfo.getFirstResult() );
	        	querySQL.setMaxResults( requestInfo.getMaxResults() );				
	    	}
	    	List<AuxTechnicianDetailsDTO> responseList = querySQL.list();
	    	return responseList;            
        }catch (Throwable ex) {
            log.error("== Error getWoPendingLackMaterials==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoPendingLackMaterials/ReportsCoreDAO ==");
        }
	}
    
}
