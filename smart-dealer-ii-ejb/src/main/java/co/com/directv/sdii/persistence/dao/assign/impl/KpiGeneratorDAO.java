package co.com.directv.sdii.persistence.dao.assign.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal;

/**
 * 
 * Implementacion para realizar las consultas necesarias para calcular KPI's 
 * 
 * Fecha de Creación: 2/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="KpiGeneratorDAOLocal",mappedName="ejb/KpiGeneratorDAOLocal")
public class KpiGeneratorDAO extends BaseDao implements KpiGeneratorDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(KpiGeneratorDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoToCalculateCycleTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Object[]> getWoIdAndRealizationDateByIndicatorDtoInStatus(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("SELECT DISTINCT (w.ID),w.WO_REALIZATION_DATE ");
			stringQuery.append("FROM WORK_ORDERS w ");
			stringQuery.append("INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append("INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append("INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append("INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append("INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQuery.append("WHERE ");
			stringQuery.append("w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("AND woa.DEALER_ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("AND w.ACTUAL_STATUS_ID IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append("AND w.WO_REALIZATION_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQuery.append("AND pc.ZONE_TYPE_ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQuery.append("AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("superCategoryId", dealerIndicatorDto.getServiceSuperCategoryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());			
			
			return query.list();
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
		}	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoToCalculateCycleTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Double getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,
			                                                              java.util.Date startDate,
			                                                              java.util.Date endDate,
			                                                              List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateCycleTimeIndicator/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQueryOutside = new StringBuffer();
			StringBuffer stringQueryInside = new StringBuffer();
			
			stringQueryInside.append("SELECT w.ID, ");
			stringQueryInside.append("       cast(max(w.WO_REALIZATION_DATE) as date) - ");
			stringQueryInside.append("       MIN (ws.DEALER_ASSIGNMENT_DATE) daysSum ");
			stringQueryInside.append("  FROM WORK_ORDERS w ");
			stringQueryInside.append(" INNER JOIN WORK_ORDER_STATUS wsw on(wsw.ID=W.ACTUAL_STATUS_ID) "); 
			stringQueryInside.append(" INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQueryInside.append(" INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQueryInside.append(" INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQueryInside.append(" INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQueryInside.append(" INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQueryInside.append(" INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQueryInside.append(" INNER JOIN (  SELECT WOASSIGNMENT.WO_ID id, ");
			stringQueryInside.append("         				CAST ( ");
			stringQueryInside.append("            					MIN ( woAssignment.DEALER_ASSIGNMENT_DATE) AS DATE) DEALER_ASSIGNMENT_DATE ");
			stringQueryInside.append("    			   FROM    WORK_ORDER_ASSIGNMENTS woAssignment INNER JOIN DEALERS d ON (d.id = woAssignment.DEALER_ID) ");
			stringQueryInside.append("   			  WHERE d.id = :dealerId ");
			stringQueryInside.append("   			  GROUP BY WOASSIGNMENT.WO_ID) ws ON (WS.ID = w.id) ");


			stringQueryInside.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQueryInside.append(" AND wsw.ID IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQueryInside.append(" AND w.WO_REALIZATION_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQueryInside.append(" AND pc.ZONE_TYPE_ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQueryInside.append(" AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			stringQueryInside.append(" AND ws.DEALER_ASSIGNMENT_DATE IS NOT NULL ");
			stringQueryInside.append(" AND EXISTS ");
			stringQueryInside.append(" (SELECT 1 ");
			stringQueryInside.append("    FROM WORK_ORDER_ASSIGNMENTS woa ");
			stringQueryInside.append("   WHERE     woa.WO_ID = w.ID ");
			stringQueryInside.append("         AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");
			stringQueryInside.append("         AND woa.DEALER_ID = :dealerId) ");
			stringQueryInside.append(" group by w.ID ");
			
			stringQueryOutside.append("SELECT DECODE( COUNT(ID), ");
			stringQueryOutside.append("               0, ");
			stringQueryOutside.append("				  0, ");
			stringQueryOutside.append("               SUM(daysSum)/COUNT(ID) ");
			stringQueryOutside.append("              ) cycleTime ");
			stringQueryOutside.append("FROM (");
			stringQueryOutside.append(stringQueryInside.toString());
			stringQueryOutside.append(")");
			
			SQLQuery query = session.createSQLQuery(stringQueryOutside.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("superCategoryId", dealerIndicatorDto.getServiceSuperCategoryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());			
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.doubleValue();
			else
				return 0D;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateCycleTimeIndicator/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateCycleTimeIndicator/KpiGeneratorDAO ==");
		}	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoToCalculateCycleTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Double getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,
			                                                                                java.util.Date startDate,
			                                                                                java.util.Date endDate,
			                                                                                List<Long> woStatus,
			                                                                                Long onTimeSystemHourquantity) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQueryOutside = new StringBuffer();
			StringBuffer stringQueryInside = new StringBuffer();

			stringQueryInside.append(" SELECT w.ID, ");
			stringQueryInside.append("  	  max(w.WO_REALIZATION_DATE) WO_REALIZATION_DATE, ");
			stringQueryInside.append("  	  Max(AGENDATION_DATE) AGENDATION_DATE, ");
			stringQueryInside.append("  	  Max(INIT_TIME) INIT_TIME, ");
			stringQueryInside.append("  	  Max(END_TIME) END_TIME ");
            stringQueryInside.append("  FROM WORK_ORDERS w ");
            stringQueryInside.append("       inner join WORK_ORDER_STATUS wsw on(wsw.ID=w.ACTUAL_STATUS_ID) "); 
            stringQueryInside.append("       INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
            stringQueryInside.append("       INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
            stringQueryInside.append("       INNER JOIN ZONE_TYPES z ON z.ID = pc.ZONE_TYPE_ID ");
			stringQueryInside.append("       INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQueryInside.append("       INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQueryInside.append("       INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQueryInside.append("       INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQueryInside.append("       INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQueryInside.append("       INNER JOIN (select wa1.WO_ID, ");
			stringQueryInside.append("                          AGENDATION_DATE, ");
			stringQueryInside.append("                          SH1.INIT_TIME, ");
			stringQueryInside.append("                          SH1.END_TIME,    ");
			stringQueryInside.append("                          wa1.DEALER_ID    ");
			stringQueryInside.append("                     from WORK_ORDER_AGENDAS woa1  ");
			stringQueryInside.append("                          inner join WORK_ORDER_ASSIGNMENTS wa1 on(woa1.WO_ASSIGNMENTS_ID=wa1.id)  ");
			stringQueryInside.append("                          inner join SERVICE_HOURS sh1 on(woa1.SERVICE_HOUR_ID = sh1.id )  ");
			stringQueryInside.append("                    where woa1.agendation_Date = (select min(woas.agendation_Date)   ");
			stringQueryInside.append("                                                    from WORK_ORDER_AGENDAS woas  ");
			stringQueryInside.append("                                                         inner join WORK_ORDER_ASSIGNMENTS was on(woas.WO_ASSIGNMENTS_ID=was.id) "); 
			stringQueryInside.append("                                                   where was.DEALER_ID = wa1.DEALER_ID  ");
			stringQueryInside.append("                                                         and was.WO_ID = wa1.WO_ID  ");
			stringQueryInside.append("                                                  )  ");
			stringQueryInside.append("             ) wa1 ON(wa1.WO_ID = w.id  and wa1.DEALER_ID=woa.DEALER_ID)  ");
            stringQueryInside.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQueryInside.append(" AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQueryInside.append(" AND woa.DEALER_ID = :dealerId ");//para filtrar por el dealer asignado
			stringQueryInside.append(" AND wsw.ID IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQueryInside.append(" AND w.WO_REALIZATION_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQueryInside.append(" AND z.ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQueryInside.append(" AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			stringQueryInside.append(" and WA1.AGENDATION_DATE IS NOT NULL ");
			stringQueryInside.append(" GROUP BY w.ID ");
			
			stringQueryOutside.append("SELECT DECODE( COUNT(ID),0,0, ");
			stringQueryOutside.append("       sum(case when cast(WO_REALIZATION_DATE as date) >to_date(TO_char(AGENDATION_DATE,'dd/mm/yyyy') || ' ' ||TO_char(INIT_TIME,'hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss')-:onTimeSystemHourquantity/24 and  "); 
			stringQueryOutside.append("                     cast(WO_REALIZATION_DATE as date) <  to_date(TO_char(AGENDATION_DATE,'dd/mm/yyyy') || ' ' ||TO_char(END_TIME,'hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss')+:onTimeSystemHourquantity/24 then ");
			stringQueryOutside.append("              1            						");
			stringQueryOutside.append("           else            						");
			stringQueryOutside.append("              0            			  			");
			stringQueryOutside.append("            end)/COUNT(ID)) OnTime 			");
			stringQueryOutside.append("FROM (");
			stringQueryOutside.append(stringQueryInside.toString());
			stringQueryOutside.append(")");
			
			SQLQuery query = session.createSQLQuery(stringQueryOutside.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("superCategoryId", dealerIndicatorDto.getServiceSuperCategoryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			query.setLong("onTimeSystemHourquantity", onTimeSystemHourquantity);
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.doubleValue();
			else
				return 0D;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoToCalculateBackLogIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getWoToCalculateBackLogIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate,Date endDate, List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoToCalculateBackLogIndicator/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("SELECT COUNT (distinct w.ID) ");
			stringQuery.append("  FROM WORK_ORDERS w ");
			stringQuery.append(" INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN DEALERS d ON woa.DEALER_ID = d.ID ");
			stringQuery.append(" inner join WORK_ORDER_STATUS ws on(WS.ID=W.ACTUAL_STATUS_ID) "); 
			stringQuery.append(" INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append(" INNER JOIN ZONE_TYPES z ON z.ID = pc.ZONE_TYPE_ID ");
			stringQuery.append(" INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append(" INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");

			stringQuery.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append(" AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append(" AND woa.DEALER_ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append(" AND ws.id NOT IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append(" AND woa.DEALER_ASSIGNMENT_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQuery.append(" AND z.ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQuery.append(" AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("superCategoryId", dealerIndicatorDto.getServiceSuperCategoryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoToCalculateBackLogIndicator/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoToCalculateBackLogIndicator/KpiGeneratorDAO ==");
		}	
	}  
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoToCalculateBackLogIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getWoAttendedOrFinishedCountToCalculateBackLogIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate,Date endDate, List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoToCalculateBackLogIndicator/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("SELECT COUNT (distinct w.ID) ");
			stringQuery.append("FROM WORK_ORDERS w ");
			stringQuery.append("INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append("INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append("INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append("INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append("INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQuery.append("WHERE ");
			stringQuery.append("w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("AND woa.DEALER_ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("AND w.ACTUAL_STATUS_ID IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append("AND w.WO_REALIZATION_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQuery.append("AND pc.ZONE_TYPE_ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQuery.append("AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("superCategoryId", dealerIndicatorDto.getServiceSuperCategoryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoToCalculateBackLogIndicator/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoToCalculateBackLogIndicator/KpiGeneratorDAO ==");
		}	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoIdAndRealizationDateByIndicatorDtoNotInStatus(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Object[]> getWoIdAndRealizationDateByIndicatorDtoNotInStatus(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("SELECT DISTINCT (w.ID),woa.DEALER_ASSIGNMENT_DATE ");
			stringQuery.append("FROM WORK_ORDERS w ");
			stringQuery.append("INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append("INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append("INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append("INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append("INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQuery.append("WHERE ");
			stringQuery.append("w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("AND woa.DEALER_ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("AND w.ACTUAL_STATUS_ID NOT IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append("AND woa.DEALER_ASSIGNMENT_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQuery.append("AND pc.ZONE_TYPE_ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQuery.append("AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("superCategoryId", dealerIndicatorDto.getServiceSuperCategoryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			return query.list();
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoIdAndRealizationDateByIndicatorDto/KpiGeneratorDAO ==");
		}	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoIdAndRealizationDateByIndicatorDtoNotInStatus(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Double getWoIdAndRealizationDateByIndicatorDtoNotInStatusToCalculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate,List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoIdAndRealizationDateByIndicatorDtoNotInStatusToCalculateAgingIndicator/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQueryInside = new StringBuffer();
			StringBuffer stringQueryOutside = new StringBuffer();
			
			stringQueryInside.append("SELECT w.ID, ");
			stringQueryInside.append("   sysdate - cast(max(woa.DEALER_ASSIGNMENT_DATE) as date) daysSum ");
			stringQueryInside.append("  FROM WORK_ORDERS w ");
			stringQueryInside.append(" INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQueryInside.append(" INNER JOIN DEALERS d ON woa.DEALER_ID = d.ID ");
			stringQueryInside.append(" INNER JOIN WORK_ORDER_STATUS ws on(WS.ID=W.ACTUAL_STATUS_ID) ");
			stringQueryInside.append(" INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQueryInside.append(" INNER JOIN ZONE_TYPES z ON z.ID = pc.ZONE_TYPE_ID ");
			stringQueryInside.append(" INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQueryInside.append(" INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQueryInside.append(" INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQueryInside.append(" INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQueryInside.append(" INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");

			stringQueryInside.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQueryInside.append(" AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQueryInside.append(" AND d.id = :dealerId ");//para filtrar por el dealer asignado
			stringQueryInside.append(" AND ws.ID NOT IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQueryInside.append(" AND woa.DEALER_ASSIGNMENT_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQueryInside.append(" AND z.ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQueryInside.append(" AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			stringQueryInside.append(" GROUP BY w.ID ");//Para filtrar por super-categoria
			
			stringQueryOutside.append("SELECT DECODE( COUNT(ID), ");
			stringQueryOutside.append("               0, ");
			stringQueryOutside.append("				  0, ");
			stringQueryOutside.append("               SUM(daysSum)/COUNT(ID) ");
			stringQueryOutside.append("              ) Aging ");
			stringQueryOutside.append("FROM (");
			stringQueryOutside.append(stringQueryInside.toString());
			stringQueryOutside.append(")");
			
			SQLQuery query = session.createSQLQuery(stringQueryOutside.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("superCategoryId", dealerIndicatorDto.getServiceSuperCategoryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.doubleValue();
			else
				return 0D;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoIdAndRealizationDateByIndicatorDtoNotInStatusToCalculateAgingIndicator/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoIdAndRealizationDateByIndicatorDtoNotInStatusToCalculateAgingIndicator/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceSuper(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Object[]> getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceCat(DealerIndicatorDTO dealerIndicatorDto,Date startDate, Date endDate) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceSuper/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" SELECT w.CREATION_DATE,w.CUSTOMER_ID ");
			stringQuery.append(" FROM WORK_ORDERS w ");
			stringQuery.append("      INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append("      INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append("      INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append("      INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append("      INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append("      INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQuery.append(" WHERE w.COUNTRY_ID = :countryId ");
			stringQuery.append(" AND pc.ZONE_TYPE_ID = :zoneTypeId ");
			stringQuery.append(" AND ssc.CODE = :superCatCode ");
			stringQuery.append(" AND w.CREATION_DATE BETWEEN :startDate AND :endDate ");
			
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setString("superCatCode", CodesBusinessEntityEnum.SUPER_CATEGORY_TECHNICAL_ASSISTANCE.getCodeEntity());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			
			return query.list();
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceSuper/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceSuper/KpiGeneratorDAO ==");
		}	
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceCat(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceCat(DealerIndicatorDTO dealerIndicatorDto,
			                                                                           Date startDate, 
			                                                                           Date endDate, 
			                                                                           List<Long> woStatus) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceCat/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" SELECT COUNT (distinct w.ID) ");
			stringQuery.append("   FROM WORK_ORDERS w ");
			stringQuery.append(" INNER JOIN CUSTOMERS c ON c.ID = W.CUSTOMER_ID ");
			stringQuery.append(" INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append(" INNER JOIN ZONE_TYPES z ON z.ID = pc.ZONE_TYPE_ID ");
			stringQuery.append(" INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append(" INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQuery.append(" INNER JOIN (SELECT cs.ID,ws.WO_REALIZATION_DATE ");
			stringQuery.append(" 	   FROM WORK_ORDERS ws ");
			stringQuery.append("            INNER JOIN WORK_ORDER_STATUS woss on(woss.ID=ws.ACTUAL_STATUS_ID) ");
			stringQuery.append("		    INNER JOIN CUSTOMERS cs ON cs.ID = WS.CUSTOMER_ID ");
			stringQuery.append("		    INNER JOIN WORK_ORDER_ASSIGNMENTS woas ON woas.WO_ID = ws.ID ");
			stringQuery.append("		    INNER JOIN POSTAL_CODES pcs ON pcs.ID = ws.POSTAL_CODE_ID ");
			stringQuery.append("		    INNER JOIN ZONE_TYPES zs ON zs.ID = pcs.ZONE_TYPE_ID ");
			stringQuery.append("		    INNER JOIN DEALERS ds ON woas.DEALER_ID = ds.ID ");
			stringQuery.append("	  WHERE ws.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("		AND woas.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("		AND ds.ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("		AND woss.id IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append("		AND zs.id = :zoneTypeId) cs on(cs.ID = c.ID and cs.WO_REALIZATION_DATE BETWEEN w.CREATION_DATE-90 AND w.CREATION_DATE)  ");//Para filtrar por tipo de zona de WO			
			
			stringQuery.append(" WHERE w.COUNTRY_ID = :countryId ");
			stringQuery.append(" AND z.id = :zoneTypeId ");
			stringQuery.append(" AND ssc.CODE = :superCatCode ");
			stringQuery.append(" AND w.CREATION_DATE BETWEEN :startDate AND :endDate ");
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setString("superCatCode", CodesBusinessEntityEnum.SUPER_CATEGORY_TECHNICAL_ASSISTANCE.getCodeEntity());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceSuper/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceSuper/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoIdByDealerIdAndCustIdAndRealizedOrFinished(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.lang.Long, java.util.Date, java.util.Date, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Object[]> getWoIdByDealerIdAndCustIdAndRealizedOrFinished(DealerIndicatorDTO dealerIndicatorDto, Long custId, Date startDate,Date endDate, List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoIdByDealerIdAndCustIdAndRealizedOrFinished/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("SELECT DISTINCT (w.ID),woa.DEALER_ASSIGNMENT_DATE ");
			stringQuery.append("FROM WORK_ORDERS w ");
			stringQuery.append("INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append("INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append("WHERE ");
			stringQuery.append("w.CUSTOMER_ID = :custId ");//para filtrar por el cliente
			stringQuery.append("AND w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("AND woa.DEALER_ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("AND w.ACTUAL_STATUS_ID IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append("AND w.WO_REALIZATION_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQuery.append("AND pc.ZONE_TYPE_ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO

			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("custId", custId);
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			return query.list();
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoIdByDealerIdAndCustIdAndRealizedOrFinished/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoIdByDealerIdAndCustIdAndRealizedOrFinished/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getNumberOfWoInStatusByFilter(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getNumberOfWoInStatusByFilter(DealerIndicatorDTO dealerIndicatorDto, Date startDate,Date endDate, List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getNumberOfWoInStatusByFilter/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" SELECT COUNT (distinct w.ID) ");
			stringQuery.append("   FROM WORK_ORDERS w ");
			stringQuery.append(" INNER JOIN WORK_ORDER_STATUS ws on ws.id=W.ACTUAL_STATUS_ID "); 
			stringQuery.append(" INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN DEALERS d on d.id=woa.DEALER_ID ");
			stringQuery.append(" INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append(" INNER JOIN ZONE_TYPES z ON z.ID = PC.ZONE_TYPE_ID ");

			stringQuery.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append(" AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append(" AND d.id = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append(" AND ws.id IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append(" AND w.WO_REALIZATION_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas
			stringQuery.append(" AND z.id = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getNumberOfWoInStatusByFilter/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getNumberOfWoInStatusByFilter/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getAttendedOrFinalizedWoOfRecoveryService(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getAttendedOrFinalizedWoOfRecoveryService(DealerIndicatorDTO dealerIndicatorDto, Date startDate,Date endDate, List<Long> woStatus) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getAttendedOrFinalizedWoOfRecoveryService/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" SELECT COUNT (distinct w.ID) ");
			stringQuery.append("   FROM WORK_ORDERS w ");
			stringQuery.append("  INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN DEALERS d ON woa.DEALER_ID = d.ID ");
			stringQuery.append(" inner join WORK_ORDER_STATUS ws on(WS.ID=W.ACTUAL_STATUS_ID)  ");
			stringQuery.append(" INNER JOIN WORK_ORDER_AGENDAS agenda ON agenda.WO_ASSIGNMENTS_ID = woa.ID ");
			stringQuery.append(" INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append(" INNER JOIN ZONE_TYPES z ON z.ID = pc.ZONE_TYPE_ID ");
			stringQuery.append(" INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append(" INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");

			stringQuery.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append(" AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append(" AND agenda.STATUS = :woAgendaActiveStatus ");//para filtrar por wo que tenga agenda activa
			stringQuery.append(" AND d.ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append(" AND ws.ID IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append(" AND w.WO_REALIZATION_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas de atención
			stringQuery.append(" AND z.ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQuery.append(" AND ssc.CODE = :superCatCode ");//Para filtrar por super-categoria
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			query.setString("woAgendaActiveStatus", CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
			query.setString("superCatCode", CodesBusinessEntityEnum.SUPER_CATEGORY_RECOVERY.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getAttendedOrFinalizedWoOfRecoveryService/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getAttendedOrFinalizedWoOfRecoveryService/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getAssignedWoOfRecoveryService(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getAssignedWoOfRecoveryService(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAssignedWoOfRecoveryService/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" SELECT COUNT (distinct w.ID) ");
			stringQuery.append(" FROM WORK_ORDERS w ");
			stringQuery.append(" INNER JOIN WORK_ORDER_ASSIGNMENTS woa ON woa.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN DEALERS d ON d.ID = woa.DEALER_ID ");
			stringQuery.append(" INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append(" INNER JOIN ZONE_TYPES z ON z.ID = pc.ZONE_TYPE_ID ");
			stringQuery.append(" INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append(" INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append(" INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append(" INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQuery.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("   AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("   AND d.ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("   AND woa.DEALER_ASSIGNMENT_DATE BETWEEN :startDate AND :endDate ");//Para filtrar por las fechas de asignacion
			stringQuery.append("   AND z.ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQuery.append("   AND ssc.CODE = :superCatCode ");//Para filtrar por super-categoria
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", dealerIndicatorDto.getCountryId());
			query.setLong("zoneTypeId", dealerIndicatorDto.getZoneTypeId());
			query.setLong("dealerId", dealerIndicatorDto.getDealerId());
			query.setTimestamp("startDate", new Timestamp( startDate.getTime() ) );
			query.setTimestamp("endDate", new Timestamp( endDate.getTime() ) );
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			query.setString("superCatCode", CodesBusinessEntityEnum.SUPER_CATEGORY_RECOVERY.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getAssignedWoOfRecoveryService/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getAssignedWoOfRecoveryService/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoToCalculateBackLogIndicatorForSkills(java.lang.Long, java.lang.Long, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getWoToCalculateBackLogForBalancedSkill(Long countryId,
			                                            Long dealerId, 
			                                            Long postalCodeId, 
			                                            List<Long> woStatus,
			                                            String codeServiceSuperCategory) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWoToCalculateBackLogForBalancedSkill/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			boolean checkSuperCategoryBackLog1=false,checkSuperCategoryBackLog2=false;
			
			StringBuffer stringQuery = new StringBuffer();
			
			//Instalaciones , mudanzas y servicios = El Backlog de todas las categorías de servicios menos  desconexiones, y reservado uso local
			if(codeServiceSuperCategory.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_INSTALLATION.getCodeEntity()) 
			   || codeServiceSuperCategory.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_TECHNICAL_ASSISTANCE.getCodeEntity()) 
			   || codeServiceSuperCategory.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_COBRANZAS.getCodeEntity()) ){
				checkSuperCategoryBackLog1=true;
			//Reservado Uso local = El Backlog de todas las categorías de servicios menos  desconexiones
			}else if(codeServiceSuperCategory.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_RESERVED_LOCAL_USE.getCodeEntity())){
				checkSuperCategoryBackLog2=true;
			}//else Desconexiones  = El Backlog de todas las categorías de servicios
			
			stringQuery.append(" SELECT COUNT (distinct w.ID) ");
			stringQuery.append(" FROM WORK_ORDER_ASSIGNMENTS woa INNER JOIN WORK_ORDERS w ON w.ID = woa.WO_ID ");
			stringQuery.append("                         INNER JOIN dealers d ON d.ID = woa.DEALER_ID ");
			stringQuery.append("                         INNER JOIN WORK_ORDER_STATUS ws on(WS.ID=W.ACTUAL_STATUS_ID) ");
			stringQuery.append("                         INNER JOIN WORK_ORDER_SERVICES wos ON(wos.WO_ID = w.ID) ");
			
			if(checkSuperCategoryBackLog1 || checkSuperCategoryBackLog2){
				stringQuery.append("                         INNER JOIN SERVICES s ON(s.ID = wos.SERVICE_ID) ");
				stringQuery.append("                         INNER JOIN SERVICE_CATEGORIES sc ON(sc.ID = s.SERVICE_CATEGORY_ID) ");
				stringQuery.append("                         INNER JOIN SERVICE_TYPES st ON(st.ID = sc.SERVICE_TYPE_ID) ");
				stringQuery.append("                         INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON (ssc.ID = st.SERVICE_SUPER_CATEGORY_ID) ");
			}
			
			stringQuery.append(" WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("       AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("       AND d.ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("       AND WS.ID NOT IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			
			if(checkSuperCategoryBackLog1){
				stringQuery.append(" AND ssc.ID != :superCategoryDesconId AND ssc.ID != :superCategoryReservLocUseId ");
			}else if(checkSuperCategoryBackLog2){
				stringQuery.append(" AND ssc.ID != :superCategoryDesconId ");
			}
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", countryId);
			query.setLong("dealerId", dealerId);
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			if(checkSuperCategoryBackLog1){
				query.setLong("superCategoryDesconId",CodesBusinessEntityEnum.SUPER_CATEGORY_RECOVERY.getIdEntity(ServiceSuperCategory.class.getName()));
				query.setLong("superCategoryReservLocUseId",CodesBusinessEntityEnum.SUPER_CATEGORY_RECOVERY.getIdEntity(ServiceSuperCategory.class.getName()));
			}else if(checkSuperCategoryBackLog2){
				query.setLong("superCategoryDesconId",CodesBusinessEntityEnum.SUPER_CATEGORY_RECOVERY.getIdEntity(ServiceSuperCategory.class.getName()));
			}
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoToCalculateBackLogForBalancedSkill/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoToCalculateBackLogForBalancedSkill/KpiGeneratorDAO ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal#getWoToCalculateBackLogForPrioritizedSkill(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getWoToCalculateBackLogForPrioritizedSkill(Long countryId,Long dealerId, Long postalCodeId, Long zoneTypeId,Long serviceSuperCategoryId, List<Long> woStatus)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWoToCalculateBackLogForPrioritizedSkill/KpiGeneratorDAO ==");
		try{
			Session session = super.getSession();
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" SELECT COUNT (distinct w.ID) ");
			stringQuery.append("   FROM WORK_ORDER_ASSIGNMENTS woa ");
			stringQuery.append("        INNER JOIN WORK_ORDERS w ON w.ID = woa.WO_ID ");
			stringQuery.append("        INNER JOIN WORK_ORDER_STATUS ws on WS.ID=W.ACTUAL_STATUS_ID "); 
			stringQuery.append("        INNER JOIN dealers d ON d.ID = woa.DEALER_ID ");
			stringQuery.append("        INNER JOIN POSTAL_CODES pc ON pc.ID = w.POSTAL_CODE_ID ");
			stringQuery.append("        INNER JOIN ZONE_TYPES zt ON zt.ID = PC.ZONE_TYPE_ID ");
			stringQuery.append("        INNER JOIN WORK_ORDER_SERVICES wos ON wos.WO_ID = w.ID ");
			stringQuery.append("        INNER JOIN SERVICES s ON s.ID = wos.SERVICE_ID ");
			stringQuery.append("        INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID ");
			stringQuery.append("        INNER JOIN SERVICE_TYPES st ON st.ID = sc.SERVICE_TYPE_ID ");
			stringQuery.append("        INNER JOIN SERVICE_SUPER_CATEGORIES ssc ON ssc.ID = st.SERVICE_SUPER_CATEGORY_ID ");
			stringQuery.append("  WHERE w.COUNTRY_ID = :countryId ");//para filtrar por pais
			stringQuery.append("        AND woa.IS_ACTIVE = :woAsigmentActiveStatus ");//para filtrar por la asignacion que esta activa
			stringQuery.append("        AND d.ID = :dealerId ");//para filtrar por el dealer asignado
			stringQuery.append("        AND WS.ID NOT IN ("+ UtilsBusiness.longListToString(woStatus, ",") +") ");//para filtrar por estado de WO validos
			stringQuery.append("        AND zt.ID = :zoneTypeId ");//Para filtrar por tipo de zona de WO
			stringQuery.append("        AND ssc.ID = :superCategoryId ");//Para filtrar por super-categoria
			
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			
			query.setLong("countryId", countryId);
			query.setLong("superCategoryId", serviceSuperCategoryId);
			query.setLong("zoneTypeId", zoneTypeId);
			query.setLong("dealerId", dealerId);
			query.setString("woAsigmentActiveStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
			
			BigDecimal queryResult = (BigDecimal) query.uniqueResult(); 
			if( queryResult != null )
				return queryResult.longValue();
			else
				return 0L;
		}catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación getWoToCalculateBackLogForPrioritizedSkill/KpiGeneratorDAO ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWoToCalculateBackLogForPrioritizedSkill/KpiGeneratorDAO ==");
		}	
	}
	
}
