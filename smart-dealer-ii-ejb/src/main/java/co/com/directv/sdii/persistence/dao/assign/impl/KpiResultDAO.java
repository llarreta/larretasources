package co.com.directv.sdii.persistence.dao.assign.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO;
import co.com.directv.sdii.assign.kpi.dto.ResponseSearchKpiResultsDTO;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.KpiResult;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ResponseSearchKpiResultsResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad KpiResult
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.KpiResult
 * @see co.com.directv.sdii.model.hbm.KpiResult.hbm.xml
 */
@Stateless(name="KpiResultDAOLocal",mappedName="ejb/KpiResultDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiResultDAO extends BaseDao implements KpiResultDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(KpiResultDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#createKpiResult(co.com.directv.sdii.model.pojo.KpiResult)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createKpiResult(KpiResult obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createKpiResult/KpiResultDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el KpiResult ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiResult/KpiResultDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#updateKpiResult(co.com.directv.sdii.model.pojo.KpiResult)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateKpiResult(KpiResult obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateKpiResult/KpiResultDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el KpiResult ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiResult/KpiResultDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#deleteKpiResult(co.com.directv.sdii.model.pojo.KpiResult)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteKpiResult(KpiResult obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteKpiResult/KpiResultDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from KpiResult entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el KpiResult ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiResult/KpiResultDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#getKpiResultsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiResult getKpiResultByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getKpiResultByID/KpiResultDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiResult.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (KpiResult) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getKpiResultByID/KpiResultDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiResultByID/KpiResultDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#getAllKpiResults()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiResult> getAllKpiResults() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllKpiResults/KpiResultDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(KpiResult.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllKpiResults/KpiResultDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllKpiResults/KpiResultDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#getDealerIndicatorResultByKpiConfigurationIdAndDealerId(java.lang.Long, java.lang.Long)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiResult getDealerIndicatorResultByKpiConfigurationIdAndDealerId(Long kpiConfigurationId, Long dealerId) throws DAOSQLException,
			DAOServiceException {
		log.debug("== Inicio getDealerIndicatorResultByConfigurationId/KpiResultDAO ==");
        Session session = super.getSession();

        try {
        	String entityName = KpiResult.class.getName();
        	StringBuffer stringQuery = new StringBuffer("from ").append(entityName)
	        	.append(" entity where entity.id = (select max(c.id) from ").append(entityName).append(" c")
	        	.append(" where c.kpiConfiguration.id = :configurationId and c.dealer.id = :dealerId)");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("configurationId", kpiConfigurationId);
            query.setLong("dealerId", dealerId);

            return (KpiResult) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerIndicatorResultByConfigurationId/KpiResultDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerIndicatorResultByConfigurationId/KpiResultDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#getKpiResultByCriteria(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiResult getKpiResultByCriteria(Long countryId,
			Long serviceSuperCategoryId, Long zoneTypeId, Long indicatorId,
			Long dealerId) throws DAOSQLException, DAOServiceException {

		log.debug("== Inicio getKpiResultByCriteria/KpiResultDAO ==");
        Session session = super.getSession();
        try {
        	StringBuilder stringQuery = new StringBuilder("from ");
        	stringQuery.append(KpiResult.class.getName());
        	stringQuery.append(" entity where entity.country.id = :aCountryId");
        	stringQuery.append(" and entity.kpiConfiguration.serviceSuperCategory.id = :aSuperCatId");
        	stringQuery.append(" and entity.kpiConfiguration.zoneTypes.id = :aZoneTypeId");
        	stringQuery.append(" and entity.kpiConfiguration.kpi.id = :aKpiId");
        	stringQuery.append(" and entity.dealer.id = :aDealerId");
        	stringQuery.append(" order by entity.resultDate desc");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            query.setLong("aSuperCatId", serviceSuperCategoryId);
            query.setLong("aZoneTypeId", zoneTypeId);
            query.setLong("aKpiId", indicatorId);
            query.setLong("aDealerId", dealerId);

            query.setMaxResults(1);
            
            List<KpiResult> resultList = query.list();
            
            if(resultList.isEmpty()){
            	return null;
            }
            
            return resultList.get(0);

        } catch (Throwable ex){
			log.error("== Error getKpiResultByCriteria/KpiResultDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiResultByCriteria/KpiResultDAO ==");
        }
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#getKpiResultDealersByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseSearchKpiResultsResponse getKpiResultDealersByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request, RequestCollectionInfo requestCollInfo ) throws DAOSQLException, DAOServiceException {

		log.debug("== Inicio getKpiResultDealersByDealerIdAndBetweenDate/KpiResultDAO ==");
        Session session = super.getSession();
        
        //variables utilizadas para crear la instruccion sql
        StringBuilder stringSelect = new StringBuilder("");
        StringBuilder stringFrom = new StringBuilder("");
        StringBuilder stringWhere = new StringBuilder("");
        
        //variables utilizadas para crear la instruccion sql del paginador
        StringBuilder stringSelectCount = new StringBuilder("");
        
        //variables utilizadas para alamcenar cuales fueron los datos de entrada 
        boolean countryIdSpecified = false,
    	zoneTypeNameIdSpecified = false,
    	serviceSuperCategoryIdSpecified = false,
    	startDateSpecified = false,
    	endDateSpecified = false;
    	String strWhereAnd= " WHERE ";
        
        try {
        	
        	//Consulta que retorna los dealerId dependiendo del filtro ingresado
            stringSelect.append(" select d.id dealerId, ");
        	stringSelect.append("        D.DEALER_CODE dealerCode, ");               
        	stringSelect.append("        D.DEPOT_CODE depotCode, ");               
        	stringSelect.append("        D.DEALER_NAME dealerName ");
        	
            //Consulta que retorna la cantidad de dealerId dependiendo del filtro ingresado para el paginador
        	stringSelectCount.append(" select count(*) ");
        	
        	stringFrom.append("   from DEALERS d inner join (select distinct d.id id ");
        	stringFrom.append("                                from KPI_RESULTS kr inner join KPI_CONFIGURATIONS kc on(kc.id=KR.KPI_CONFIGURATION_ID) "); 
        	stringFrom.append("                              inner join ZONE_TYPES z on(KC.ZONE_TYPE_ID=z.ID  ) ");
        	stringFrom.append("                              inner join dealers d on(KR.DEALER_ID=D.ID  ) ");
        	stringFrom.append("                              inner join COUNTRIES c on(KR.COUNTRY_ID=c.ID  ) ");
        	stringFrom.append("                              inner join SERVICE_SUPER_CATEGORIES ssc on(KC.SERV_SUPER_CAT_ID=ssc.ID  ) ");

        	//si trae pais
        	if (request.getCountryId() != null && request.getCountryId().longValue() > 0) {
            	countryIdSpecified = true;
            	stringWhere.append(strWhereAnd + "                                c.id = :aCountryId ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae tipo de zona
        	if (request.getZoneTypeId() != null && request.getZoneTypeId().longValue() > 0) {
        		zoneTypeNameIdSpecified = true;
        		stringWhere.append(strWhereAnd + "                                z.id = :aZoneTypeNameId ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae categoria de servicio
        	if (request.getServiceSuperCategoryId() != null && request.getServiceSuperCategoryId().longValue() > 0) {
        		serviceSuperCategoryIdSpecified = true;
        		stringWhere.append(strWhereAnd + "                                SSC.ID = :aServiceSuperCategoryId ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae fecha inicial
        	if (request.getStartDate() != null) {
        		startDateSpecified = true;
        		stringWhere.append(strWhereAnd + "                                trunc(KR.RESULT_DATE) >= trunc(:aStartDate) ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae fecha final
        	if (request.getEndDate() != null) {
        		endDateSpecified = true;
        		stringWhere.append(strWhereAnd + "                                trunc(KR.RESULT_DATE) <= trunc(:aEndDate)    ");
            	strWhereAnd=" AND ";
    		}
        	
        	stringWhere.append("                              ) d2 on(d.id=d2.id)  ");
        	
        	strWhereAnd= " WHERE ";
        	//si trae dealers
        	if (request.getDealersId() != null && request.getDealersId().size() > 0) {
        		stringWhere.append(strWhereAnd + " (D.ID in("+ UtilsBusiness.longListToString(request.getDealersId(),",") +") ");
        		stringWhere.append("                or D.DEALER_BRANCH_ID in("+ UtilsBusiness.longListToString(request.getDealersId(),",") +")) ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae dealersBrach
        	if (request.getDealersBranchId() != null && request.getDealersBranchId().size() > 0) {
        		stringWhere.append(strWhereAnd + " (D.ID in("+ UtilsBusiness.longListToString(request.getDealersBranchId(),",") +")) ");
            	strWhereAnd=" AND ";
    		}
        	
        	Query query = session.createSQLQuery(stringSelect.toString() + stringFrom.toString() + stringWhere.toString());
        	Query countQuery = session.createSQLQuery(stringSelectCount.toString() + stringFrom.toString() + stringWhere.toString());

        	//si traia pais
        	if(countryIdSpecified){
				query.setLong("aCountryId", request.getCountryId());
				countQuery.setLong("aCountryId", request.getCountryId());
        	}
        	
        	//si traia tipo de zona
        	if(zoneTypeNameIdSpecified){
				query.setLong("aZoneTypeNameId", request.getZoneTypeId());
				countQuery.setLong("aZoneTypeNameId", request.getZoneTypeId());
        	}
        	
        	//si traia categoria de servicio
        	if(serviceSuperCategoryIdSpecified){
				query.setLong("aServiceSuperCategoryId", request.getServiceSuperCategoryId());
				countQuery.setLong("aServiceSuperCategoryId", request.getServiceSuperCategoryId());
        	}
        	
        	//si traia fecha inicial
    		if(startDateSpecified){
				query.setDate("aStartDate", UtilsBusiness.obtenerPrimeraHoraDia(request.getStartDate()));
				countQuery.setDate("aStartDate", UtilsBusiness.obtenerPrimeraHoraDia(request.getStartDate()));
        	}
        	
    		//si traia fecha final
        	if(endDateSpecified){
				query.setDate("aEndDate", UtilsBusiness.obtenerUltimaHoraDia(request.getEndDate()));
				countQuery.setDate("aEndDate", UtilsBusiness.obtenerUltimaHoraDia(request.getEndDate()));
            }

        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){            	
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();	
	        	query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ResponseSearchKpiResultsResponse response= new ResponseSearchKpiResultsResponse();
        	response.setResponse(fillKpiResultDealersByDealerIdAndBetweenDate(query.list()));
            
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), response.getResponse().size(), recordQty.intValue() );
        	return response;

        } catch (Throwable ex){
			log.error("== Error getKpiResultDealersByDealerIdAndBetweenDate==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiResultDealersByDealerIdAndBetweenDate/KpiResultDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal#getKpiResultByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseSearchKpiResultsResponse getKpiResultByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request) throws DAOSQLException, DAOServiceException {

		log.debug("== Inicio getKpiResultByDealerIdAndBetweenDate/KpiResultDAO ==");
        Session session = super.getSession();
        
        //variables utilizadas para crear la instruccion sql
        StringBuilder stringSelect = new StringBuilder("");
        StringBuilder stringFrom = new StringBuilder("");
        StringBuilder stringWhere = new StringBuilder("");
        StringBuilder stringGroup = new StringBuilder("");
        StringBuilder stringOrder = new StringBuilder("");
        
        //variables utilizadas para alamcenar cuales fueron los datos de entrada
        boolean countryIdSpecified = false,
    	zoneTypeNameIdSpecified = false,
    	serviceSuperCategoryIdSpecified = false,
    	startDateSpecified = false,
    	endDateSpecified = false;
    	String strWhereAnd= " WHERE ";
        
        try {
        	
        	//Consulta que retorna los kpi_result dependiendo de los datos de entrada
        	stringSelect.append("SELECT d.ID dealerId, ");
		    stringSelect.append("       d.DEALER_CODE dealerCode, ");
		    stringSelect.append("       d.DEPOT_CODE depotCode, ");
		    stringSelect.append("       d.DEALER_NAME dealerName, ");
		    stringSelect.append("       k.CODE AS code, ");
		    stringSelect.append("       TO_CHAR (kr.RESULT_DATE, 'mm') AS mounth, ");
		    stringSelect.append("       TO_CHAR (kr.RESULT_DATE, 'yyyy') AS year, ");
		    stringSelect.append("       round(SUM (kr.result)/count(*),2) AS valueKpi, ");
		    stringSelect.append("       min(kc.goal) AS valueGoal ");
			stringFrom.append(" 	        FROM KPI_RESULTS kr ");
			stringFrom.append(" 	             INNER JOIN KPI_CONFIGURATIONS kc ");
			stringFrom.append(" 	                ON kr.KPI_CONFIGURATION_ID = kc.ID ");
			stringFrom.append(" 	             INNER JOIN KPIS k ");
			stringFrom.append(" 	                ON kc.KPI_ID = k.ID ");
			stringFrom.append(" 	             INNER JOIN DEALERS d ");
			stringFrom.append(" 	                ON d.ID = kr.DEALER_ID ");
			

			//si trae pais
        	if (request.getCountryId() != null && request.getCountryId().longValue() > 0) {
            	countryIdSpecified = true;
            	stringWhere.append(strWhereAnd + " kr.COUNTRY_ID = :aCountryId ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae tipo de zona
        	if (request.getZoneTypeId() != null && request.getZoneTypeId().longValue() > 0) {
        		zoneTypeNameIdSpecified = true;
        		stringWhere.append(strWhereAnd + " kc.ZONE_TYPE_ID = :aZoneTypeNameId ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae categoria de servicio
        	if (request.getServiceSuperCategoryId() != null && request.getServiceSuperCategoryId().longValue() > 0) {
        		serviceSuperCategoryIdSpecified = true;
        		stringWhere.append(strWhereAnd + " kc.SERV_SUPER_CAT_ID = :aServiceSuperCategoryId ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae dealers
        	if (request.getDealersId() != null && request.getDealersId().size() > 0) {
        		stringWhere.append(strWhereAnd + " d.ID in("+ UtilsBusiness.longListToString(request.getDealersId(),",") +") ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae fecha inicio
        	if (request.getStartDate() != null) {
        		startDateSpecified = true;
        		stringWhere.append(strWhereAnd + " trunc(kr.RESULT_DATE) >= trunc(:aStartDate) ");
            	strWhereAnd=" AND ";
    		}
        	
        	//si trae fecha final
        	if (request.getEndDate() != null) {
        		endDateSpecified = true;
        		stringWhere.append(strWhereAnd + " trunc(kr.RESULT_DATE) <= trunc(:aEndDate) ");
            	strWhereAnd=" AND ";
    		}

        	stringGroup.append("       GROUP BY d.ID, ");
        	stringGroup.append("       d.DEALER_CODE, ");
        	stringGroup.append("       d.DEPOT_CODE, ");
        	stringGroup.append("       d.DEALER_NAME, ");
        	stringGroup.append(" 		k.CODE, ");
        	stringGroup.append(" 		TO_CHAR (kr.RESULT_DATE, 'mm'), ");
        	stringGroup.append(" 		TO_CHAR (kr.RESULT_DATE, 'yyyy') ");
            
        	stringOrder.append(" order by 2,7,6,5 ");
                	
        	Query query = session.createSQLQuery(stringSelect.toString() + stringFrom.toString() + stringWhere.toString() + stringGroup.toString() + stringOrder.toString());

        	//si traia pais
        	if(countryIdSpecified){
        		query.setLong("aCountryId", request.getCountryId());
        	}
        	
        	//si traia tipo de zona
        	if(zoneTypeNameIdSpecified){
        		query.setLong("aZoneTypeNameId", request.getZoneTypeId());
        	}
        	
        	//si traia categoria de servicio
        	if(serviceSuperCategoryIdSpecified){
        		query.setLong("aServiceSuperCategoryId", request.getServiceSuperCategoryId());
        	}
        	
        	//si traia fecha inicial
    		if(startDateSpecified){
        		query.setDate("aStartDate", UtilsBusiness.obtenerPrimeraHoraDia(request.getStartDate()));
        	}
        	
    		//si traia fecha final
        	if(endDateSpecified){
        		query.setDate("aEndDate", UtilsBusiness.obtenerUltimaHoraDia(request.getEndDate()));
            }

        	ResponseSearchKpiResultsResponse response= new ResponseSearchKpiResultsResponse();
        	response.setResponse(fillKpiResultByDealerIdAndBetweenDate(query.list()));
            
        	return response;

        } catch (Throwable ex){
			log.error("== Error getKpiResultByDealerIdAndBetweenDate==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getKpiResultByDealerIdAndBetweenDate/KpiResultDAO ==");
        }
	}
	
	/**
	 * Metodo: permite almacenar los datos devueltos en una lista en una lista ResponseSearchKpiResultsDTO
	 * @param request Lista de objectos retornados por la consulta getKpiResultDealersByDealerIdAndBetweenDate
	 * @return List<ResponseSearchKpiResultsDTO> lista que encapsula los datos de la consulta paginados
	 * @author cduarte
	 */
	private List<ResponseSearchKpiResultsDTO> fillKpiResultDealersByDealerIdAndBetweenDate(List<Object[]> request){
		List<ResponseSearchKpiResultsDTO> response=new ArrayList<ResponseSearchKpiResultsDTO>();
		for (Object[] object : request) {
			Long dealerId=((BigDecimal)object[0]).longValue(); 
			Long dealerCode=((BigDecimal)object[1]).longValue();
			String depotCode=((String)object[2]);
			String dealerName=((String)object[3]);
			String codeKpi="";
			String mounth="";
			String year="";
			Double valueKpi=0D;
			Double valueGoal=0D;
			
			ResponseSearchKpiResultsDTO responseSearchKpiResultsDTO = new ResponseSearchKpiResultsDTO(dealerId,dealerCode,depotCode,dealerName,codeKpi,mounth,year,valueKpi,valueGoal);
			
			response.add(responseSearchKpiResultsDTO);
		}
		return response;
	}
	
	/**
	 * Metodo: permite almacenar los datos devueltos en una lista en una lista ResponseSearchKpiResultsDTO
	 * @param request Lista de objectos retornados por la consulta getKpiResultByDealerIdAndBetweenDate
	 * @return List<ResponseSearchKpiResultsDTO> lista que encapsula los datos de la consulta paginados
	 * @author cduarte
	 */
	private List<ResponseSearchKpiResultsDTO> fillKpiResultByDealerIdAndBetweenDate(List<Object[]> request){
		List<ResponseSearchKpiResultsDTO> response=new ArrayList<ResponseSearchKpiResultsDTO>();
		for (Object[] object : request) {
			Long dealerId=((BigDecimal)object[0]).longValue(); 
			Long dealerCode=((BigDecimal)object[1]).longValue();
			String depotCode=((String)object[2]);
			String dealerName=((String)object[3]);
			String codeKpi=((String)object[4]);
			String mounth=((String)object[5]);
			String year=((String)object[6]);
			Double valueKpi=((BigDecimal)object[7]).doubleValue();
			Double valueGoal=((BigDecimal)object[8]).doubleValue();
			
			ResponseSearchKpiResultsDTO responseSearchKpiResultsDTO = new ResponseSearchKpiResultsDTO(dealerId,dealerCode,depotCode,dealerName,codeKpi,mounth,year,valueKpi,valueGoal);
			
			response.add(responseSearchKpiResultsDTO);
		}
		return response;
	}
	
}
