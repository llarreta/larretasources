package co.com.directv.sdii.persistence.dao.config.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

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
import co.com.directv.sdii.model.dto.WorkOrderCanceledDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCancelDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * Session Bean implementation class WorkOrderCancelDAO
 */
@Stateless
public class WorkOrderCancelDAO extends BaseDao implements WorkOrderCancelDAOLocal {

    /**
     * Default constructor. 
     */
    public WorkOrderCancelDAO() {
    }
    
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderCancelDAO.class);

	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @param dateNow fecha actual para el pais que realiza la consulta
	 * @param days dias hacia atras en los que busca las work order canceladas
	 * @param requestInfo parametros de paginacion de la consulta
	 * @return resultado de la consulta de work orders canceladas
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WorkOrderCanceledResponse getCanceledWorkOrders(WorkOrderCanceledFilterDTO filter, Date dateNow,
			Integer days, RequestCollectionInfo requestInfo) throws DAOServiceException, DAOSQLException {
    	List<WorkOrderCanceledDTO> returnValue=null;
        log.debug("== Inicio getCanceledWorkOrders/WorkOrderCancelDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();

            queryBuffer.append(" SELECT woId, ");
            queryBuffer.append("       woCode,  ");
            queryBuffer.append("       woDescription,   ");
            queryBuffer.append("       woCreationDate,   ");
            queryBuffer.append("       woCancelationDate,  ");
            queryBuffer.append("       serviceHourName,   ");
            queryBuffer.append("       NVL(SUBSTR(woCanceladas.delers, 1, INSTR(woCanceladas.delers, '|', 1) - 1), ' ') dealerCode,   ");
            queryBuffer.append("       NVL(SUBSTR(woCanceladas.delers, INSTR(woCanceladas.delers, '|' , 1) + 1, LENGTH(woCanceladas.delers) - INSTR(woCanceladas.delers, '|', 1)), ' ') dealerName,    ");
            queryBuffer.append("       NVL(SUBSTR(customerCode_customerName, 1, INSTR(customerCode_customerName, '|', 1) - 1), ' ') customerCode,   ");
            queryBuffer.append("       NVL(SUBSTR(customerCode_customerName, INSTR(customerCode_customerName, '|' , 1) + 1, LENGTH(customerCode_customerName) - INSTR(customerCode_customerName, '|', 1)), ' ') customerName,  ");
            queryBuffer.append("       crewResponsibleName,    ");
            queryBuffer.append("       cancelationDescription   ");
            queryBuffer.append(" FROM    ");
            queryBuffer.append(" (SELECT wo.id woId,    ");
            queryBuffer.append("        wo.wo_code woCode,  ");
            queryBuffer.append("  wo.wo_description woDescription,    ");
            queryBuffer.append("        wo.creation_date woCreationDate,   ");
            queryBuffer.append("        wo.cancelation_date woCancelationDate ,   ");
            queryBuffer.append("        (SELECT TO_CHAR(woa.agendation_date,'dd/MM/yyyy HH:mi:ss AM')   ");
            queryBuffer.append("           FROM (SELECT MAX(woas.id) woaId,   ");
            queryBuffer.append("                        woas.wo_assignments_id waId,  ");
            queryBuffer.append("                        assigment.woId   ");
            queryBuffer.append("                   FROM WORK_ORDER_AGENDAS woas,   ");
            queryBuffer.append("                        (SELECT MAX(wa.id) waId,   ");
            queryBuffer.append("                                wa.wo_id woId   ");
            queryBuffer.append("                           FROM WORK_ORDER_ASSIGNMENTS wa  ");
            queryBuffer.append("                          WHERE wa.wo_id IN (SELECT wo2.id   ");
            queryBuffer.append("                                               FROM work_orders wo2   ");
            queryBuffer.append("                                              WHERE wo2.cancelation_date >= (TRUNC(:asysdate)-:days))  ");
            queryBuffer.append("                          GROUP BY wa.wo_id   ");
            queryBuffer.append("                        ) assigment  ");
            queryBuffer.append("                  WHERE assigment.waId = woas.wo_assignments_id   ");
            queryBuffer.append("                  GROUP BY woas.wo_assignments_id, assigment.woId) woag,  ");
            queryBuffer.append("                  WORK_ORDER_AGENDAS woa,  ");
            queryBuffer.append("                  service_hours sh    ");
            queryBuffer.append("            WHERE woag.woId(+) = wo.id   ");
            queryBuffer.append("              AND woa.id(+) = woag.woaId   ");
            queryBuffer.append("              AND sh.id(+) = woa.service_hour_id) serviceHourName,  ");
            queryBuffer.append("        (SELECT dealer_code  || '|' || dealer_name   ");
            queryBuffer.append("           FROM dealers d   ");
            queryBuffer.append("          WHERE d.id = wo.dealer_id) delers,   ");
            queryBuffer.append("        (SELECT cu.customer_code || '|' ||   ");
            queryBuffer.append("                cu.first_name   ");
            queryBuffer.append("                || ' '   ");
            queryBuffer.append("                || cu.last_name   ");
            queryBuffer.append("           FROM customers cu   ");
            queryBuffer.append("          WHERE cu.id = wo.customer_id) customerCode_customerName,  ");
            queryBuffer.append("        NVL(crewResponsible.name, ' ') crewResponsibleName,   ");
            queryBuffer.append("        (SELECT WSH2.DESCRIPTION   ");
            queryBuffer.append("           FROM WORK_ORDER_STATUS_HISTORIES WSH2   ");
            queryBuffer.append("          WHERE WSH2.ID = (SELECT MAX(WSH1.ID)   ");
            queryBuffer.append("                             FROM WORK_ORDER_STATUS_HISTORIES WSH1   ");
            queryBuffer.append("                            WHERE WSH1.WO_ID = wo.id   ");
            queryBuffer.append("                              AND EXISTS (SELECT 1   ");
            queryBuffer.append("                                            FROM WORK_ORDER_STATUS woswsh   ");
            queryBuffer.append("                                           WHERE woswsh.id = WSH1.WO_STATUS_ID   ");
            queryBuffer.append("                                             AND woswsh.wo_state_code = :cancel) ");
            queryBuffer.append("                              AND WSH1.DESCRIPTION IS NOT NULL)  ");
            queryBuffer.append("        ) cancelationDescription   ");
            queryBuffer.append("   FROM WORK_ORDERS wo,  ");
            queryBuffer.append("        (SELECT e.FIRST_NAME || ' ' || e.LAST_NAME name, wos.id   ");
            queryBuffer.append("           FROM WORK_ORDER_CREW_ASSIGMENTS wcas,   ");
            queryBuffer.append("                work_orders wos,   ");
            queryBuffer.append("                (SELECT MAX(id) wcaid,  ");
            queryBuffer.append("                        wca.wo_id woId  ");
            queryBuffer.append("                   FROM WORK_ORDER_CREW_ASSIGMENTS wca   ");
            queryBuffer.append("                  WHERE wca.wo_id IN (SELECT wo2.id  ");
            queryBuffer.append("                                        FROM work_orders wo2   ");
            queryBuffer.append("                                       WHERE wo2.cancelation_date >= (TRUNC(:asysdate)-:days))  ");
            queryBuffer.append("                  GROUP BY wca.wo_id  ");
            queryBuffer.append("                 ) maxwca,  ");
            queryBuffer.append("                 crews cr,  ");
            queryBuffer.append("                 EMPLOYEE_CREWS ec,   ");
            queryBuffer.append("                 EMPLOYEES e   ");
            queryBuffer.append("           WHERE maxwca.wcaid = wcas.id   ");
            queryBuffer.append("             AND cr.id = wcas.crew_id  ");
            queryBuffer.append("             AND ec.CREW_ID = wcas.crew_id  ");
            queryBuffer.append("             AND ec.IS_RESPONSIBLE =:true  ");
            queryBuffer.append("             AND e.id(+) = ec.employee_id  ");
            queryBuffer.append("             AND wcas.wo_id(+) = wos.id  ");
            queryBuffer.append("             AND cr.dealer_id(+) = wos.dealer_id   ");
            queryBuffer.append("        ) crewResponsible   ");
            queryBuffer.append("  WHERE EXISTS (SELECT 1   ");
            queryBuffer.append("                  FROM WORK_ORDER_STATUS wos  ");
            queryBuffer.append("                 WHERE wos.id = wo.actual_status_id  "); 
            queryBuffer.append("                   AND wos.wo_state_code  =:cancel) ");
            queryBuffer.append("    AND wo.cancelation_date >= (TRUNC(:asysdate)-:days)  ");
            queryBuffer.append("    AND (wo.dealer_id = :dealerCompany  ");
            queryBuffer.append("         OR :dealerCompany IS NULL  ");
            queryBuffer.append("         OR wo.dealer_id IN (SELECT id  ");
            queryBuffer.append("                               FROM dealers sucursal  ");
            queryBuffer.append("                              WHERE sucursal.dealer_branch_id = :dealerCompany))  ");
            queryBuffer.append("    AND (wo.dealer_id = :dealerBranchId  ");
            queryBuffer.append("         OR :dealerBranchId IS NULL)  ");
            queryBuffer.append("    AND (wo.wo_code = :workOrderCode   ");
            queryBuffer.append("         OR :workOrderCode IS NULL)  ");
            queryBuffer.append("    AND (:serviceId IS NULL   ");
            queryBuffer.append("         OR (EXISTS (SELECT 1   ");
            queryBuffer.append("                       FROM services s,  ");
            queryBuffer.append("                            work_order_services wose  ");
            queryBuffer.append("                      WHERE wose.service_id=s.id   ");
            queryBuffer.append("                        AND wose.wo_id = wo.id   ");
            queryBuffer.append("                        AND s.id = :serviceId)))   ");
            queryBuffer.append("    AND (:serviceTypeId IS NULL   ");
            queryBuffer.append("         OR (EXISTS (SELECT 1   ");
            queryBuffer.append("                       FROM service_types st,  ");
            queryBuffer.append("                            SERVICE_CATEGORIES sc,   ");
            queryBuffer.append("                            services s,  ");
            queryBuffer.append("                            work_order_services wose  ");
            queryBuffer.append("                      WHERE sc.service_type_id = st.id  ");
            queryBuffer.append("                        AND s.service_category_id = sc.id  ");
            queryBuffer.append("                        AND wose.service_id = s.id  ");
            queryBuffer.append("                        AND wose.wo_id = wo.id  ");
            queryBuffer.append("                        AND st.id = :serviceTypeId)))  ");
            queryBuffer.append("    AND (:countryId IS NULL  ");
            queryBuffer.append("         OR wo.country_id = :countryId)  ");
            queryBuffer.append("    AND wo.MANAGE_CANCELATION = :false  ");
            queryBuffer.append("    AND wo.id = crewResponsible.id(+) ");
            queryBuffer.append("  ORDER BY wo.id DESC) woCanceladas ");
			
            Query query = session.createSQLQuery(queryBuffer.toString())
            	.addScalar("woId",Hibernate.LONG)
            	.addScalar("woCode",Hibernate.STRING)
            	.addScalar("woDescription",Hibernate.STRING)
            	.addScalar("woCreationDate",Hibernate.TIMESTAMP)
            	.addScalar("woCancelationDate",Hibernate.TIMESTAMP)
            	.addScalar("serviceHourName",Hibernate.STRING)
            	.addScalar("dealerCode",Hibernate.STRING)
            	.addScalar("dealerName",Hibernate.STRING)
            	.addScalar("customerCode",Hibernate.STRING)
            	.addScalar("customerName",Hibernate.STRING)
            	.addScalar("crewResponsibleName",Hibernate.STRING)
            	.addScalar("cancelationDescription",Hibernate.STRING)
            	.setResultTransformer(Transformers.aliasToBean(WorkOrderCanceledDTO.class));

            query.setParameter("asysdate", new Timestamp(dateNow.getTime()), Hibernate.TIMESTAMP);
            query.setParameter("cancel", CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity(), Hibernate.STRING);
            query.setParameter("days", days, Hibernate.INTEGER);
            query.setParameter("true", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
            query.setParameter("false", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity(), Hibernate.STRING);

            if(filter!=null){
            	query.setParameter("countryId", filter.getCountryId(), Hibernate.LONG);
            	query.setParameter("dealerCompany", filter.getPrincipalDealerId(), Hibernate.LONG);
            	query.setParameter("dealerBranchId", filter.getBranchId(), Hibernate.LONG);
            	query.setParameter("workOrderCode", filter.getWoCode(), Hibernate.STRING);
            	query.setParameter("serviceTypeId", filter.getTypeServiceId(), Hibernate.LONG);
            	query.setParameter("serviceId", filter.getServiceId(), Hibernate.LONG); 
            }
            else{
            	query.setParameter("countryId", null, Hibernate.LONG);
            	query.setParameter("dealerCompany", null, Hibernate.LONG);
            	query.setParameter("dealerBranchId", null, Hibernate.LONG);
            	query.setParameter("workOrderCode", null, Hibernate.STRING);
            	query.setParameter("serviceTypeId", null, Hibernate.LONG);
            	query.setParameter("serviceId", null, Hibernate.LONG);            	
            }
            
            Long recordQty = 0L;
        	if( requestInfo != null ){	
        		Query countQuery = session.createSQLQuery(" select count(1) from ( " + queryBuffer.toString() + " ) ");
        		
        		countQuery.setParameter("asysdate", new Timestamp(dateNow.getTime()), Hibernate.TIMESTAMP);
        		countQuery.setParameter("cancel", CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("days", days, Hibernate.INTEGER);
        		countQuery.setParameter("true", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
        		countQuery.setParameter("false", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity(), Hibernate.STRING);
        		
        		if(filter!=null){
                	countQuery.setParameter("countryId", filter.getCountryId(), Hibernate.LONG);
                	countQuery.setParameter("dealerCompany", filter.getPrincipalDealerId(), Hibernate.LONG);
                	countQuery.setParameter("dealerBranchId", filter.getBranchId(), Hibernate.LONG);
                	countQuery.setParameter("workOrderCode", filter.getWoCode(), Hibernate.STRING);
                	countQuery.setParameter("serviceTypeId", filter.getTypeServiceId(), Hibernate.LONG);
                	countQuery.setParameter("serviceId", filter.getServiceId(), Hibernate.LONG); 
                }
                else{
                	countQuery.setParameter("countryId", null, Hibernate.LONG);
                	countQuery.setParameter("dealerCompany", null, Hibernate.LONG);
                	countQuery.setParameter("dealerBranchId", null, Hibernate.LONG);
                	countQuery.setParameter("workOrderCode", null, Hibernate.STRING);
                	countQuery.setParameter("serviceTypeId", null, Hibernate.LONG);
                	countQuery.setParameter("serviceId", null, Hibernate.LONG);            	
                }

	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( requestInfo.getFirstResult() );
				query.setMaxResults( requestInfo.getMaxResults() );				
        	}
            
            returnValue= (List<WorkOrderCanceledDTO>)query.list();
            
            WorkOrderCanceledResponse response = new WorkOrderCanceledResponse();
            
        	if( requestInfo != null )
				populatePaginationInfo( response, requestInfo.getPageSize(), requestInfo.getPageIndex(), returnValue.size(), recordQty.intValue() );
            
        	response.setListResponse(returnValue);
        	
        	return response;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getCanceledWorkOrders==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCanceledWorkOrders/WorkOrderCancelDAO ==");
        }
	}

	/**
	 * Metodo que realiza la consulta de cuantas work order se cancelaron en los ultimos dias, para un pais especifico, dado el numero de dias, el pais y la fecha actual
	 * @param countryId Id del pais
	 * @param days dias hacia atras con los cuales realizara la consulta
	 * @param dateNow fecha actual del sistema para el pais
	 * @return numero de work orders canceladas
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public Long getCanceledWorkOrdersCount(Long countryId, Integer days, Date dateNow, Long dealerId)
			throws DAOServiceException, DAOSQLException {

    	Long returnValue=null;
        log.debug("== Inicio getCanceledWorkOrdersCount/WorkOrderCancelDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();

    		queryBuffer.append(" select count(1) ");
    		queryBuffer.append(" from work_orders wo  ");
    		queryBuffer.append(" inner join WORK_ORDER_STATUS ws ON (ws.id=wo.actual_status_id)  ");
    		queryBuffer.append(" inner join dealers d on (d.id=wo.DEALER_ID) ");
    		
    		queryBuffer.append(" where ws.wo_state_code='"+CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity()+"' ");
    		queryBuffer.append(" and wo.cancelation_date>=trunc(:asysdate)-"+days+" ");
    		queryBuffer.append(" and ( :countryId is null or wo.country_id = :countryId ) ");
    		queryBuffer.append(" and ( :dealerId is null or wo.DEALER_ID = :dealerId  or d.dealer_branch_id = :dealerId  ) ");
    		queryBuffer.append(" AND wo.MANAGE_CANCELATION = :false ");
    		
            Query query = session.createSQLQuery(queryBuffer.toString());

            query.setParameter("asysdate", new Timestamp(dateNow.getTime()), Hibernate.TIMESTAMP);
            query.setParameter("countryId", countryId, Hibernate.LONG);
            query.setParameter("dealerId", dealerId, Hibernate.LONG);
            query.setParameter("false", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity(), Hibernate.STRING);
            
            returnValue= ((BigDecimal)query.uniqueResult()).longValue();;
        	
        	return returnValue;
        }catch (Throwable ex) {
            log.error("== Error consultando en el metodo getCanceledWorkOrdersCount==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCanceledWorkOrdersCount/WorkOrderCancelDAO ==");
        }
	}

	@Override
	public void manageCanceledWorkOrder(Long workOrderId, Long userId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio manageCanceledWorkOrder/WorkOrderCancelDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" UPDATE WORK_ORDERS SET MANAGE_CANCELATION = :true WHERE ID = :woId ");
            Query update = session.createSQLQuery(strignQuery.toString());
            
            update.setParameter("true", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
            update.setParameter("woId", workOrderId, Hibernate.LONG);
            
            update.executeUpdate();
        } catch (Throwable ex) {
            log.error("== Error en manageCanceledWorkOrder/WorkOrderCancelDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina manageCanceledWorkOrder/WorkOrderCancelDAO ==");
        }
	}

	
	
}
