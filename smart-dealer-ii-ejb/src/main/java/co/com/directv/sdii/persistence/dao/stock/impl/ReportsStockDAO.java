package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.Date;
import java.util.List;

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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ReportsStockDAOLocal;
import co.com.directv.sdii.reports.dto.WareHouseElementsReportDTO;

/**
 * CC053 - HSP Reportes de inventarios.
 * Session Bean implementation class ReportsCoreDAO
 */
@Stateless(name="ReportsStockDAOLocal",mappedName="ejb/ReportsStockDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportsStockDAO extends BaseDao implements ReportsStockDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ReportsStockDAO.class);
	
	//CC053 - HSP Reportes
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReportsStockDAOLocal#getWarehouseElements(java.lang.Long, java.util.Date, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 * 
	 * release_correctiva_4.1.4_IN347753
	 * Se modifica la consulta reemplazando "DECODE" por "NVL2"
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WareHouseElementsReportDTO> getWarehouseElements(Long countryId, RequestCollectionInfo requestInfo) throws DAOSQLException, DAOServiceException {
        try {
            log.debug("== Inicio getWarehouseElements/ReportsStockDAO ==");
            Session session = super.getSession();

            //Consulta que retorna el detalle de los elementos
            StringBuffer stringQuery = new StringBuffer("");
           
            stringQuery.append(" SELECT ");
            /*
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,D.DEPOT_CODE,DS.DEPOT_CODE) ||  ' ' || DECODE(D.DEALER_BRANCH_ID,NULL,D.DEALER_NAME,DS.DEALER_NAME) company, ");
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,NULL,D.DEPOT_CODE || ' ' || D.DEALER_NAME) branch, ");
            stringQuery.append("	WT.WH_TYPE_NAME whTypeName, ");
            stringQuery.append("	DECODE(W.CREW_ID,NULL,'',E.FIRST_NAME || ' ' || E.LAST_NAME) crewName, ");
            stringQuery.append("	(CASE WHEN W.CREW_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || E.FIRST_NAME || ' ' || E.LAST_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.DEALER_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.CUSTOMER_ID IS NOT NULL THEN CUS.CUSTOMER_CODE || ' - ' || CUS.FIRST_NAME || ' - ' || CUS.LAST_NAME || ' - ' || WT.WH_TYPE_NAME END) AS ubicacion, ");
            stringQuery.append("	DECODE(EMS.MODEL_CODE,NULL,EMNS.MODEL_CODE,EMS.MODEL_CODE) || ' / ' || DECODE(EMS.MODEL_NAME,NULL,EMNS.MODEL_NAME,EMS.MODEL_NAME) modelName, ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_CODE,NULL,ETNS.TYPE_ELEMENT_CODE,ETS.TYPE_ELEMENT_CODE) typeElementCode, ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_NAME,NULL,ETNS.TYPE_ELEMENT_NAME,ETS.TYPE_ELEMENT_NAME) elementTypeName, ");
            */
            stringQuery.append("    NVL2(D.DEALER_BRANCH_ID,DS.DEPOT_CODE,D.DEPOT_CODE)||' '||NVL2(D.DEALER_BRANCH_ID,DS.DEALER_NAME,D.DEALER_NAME) company, ");
            stringQuery.append("    NVL2(D.DEALER_BRANCH_ID ,D.DEPOT_CODE || ' ' || D.DEALER_NAME,NULL) branch, ");
            stringQuery.append("	WT.WH_TYPE_NAME whTypeName, ");
            stringQuery.append("    NVL2(W.CREW_ID,E.FIRST_NAME ||' '|| E.LAST_NAME,'') crewName, ");
            stringQuery.append("	(CASE WHEN W.CREW_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || E.FIRST_NAME || ' ' || E.LAST_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.DEALER_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.CUSTOMER_ID IS NOT NULL THEN CUS.CUSTOMER_CODE || ' - ' || CUS.FIRST_NAME || ' - ' || CUS.LAST_NAME || ' - ' || WT.WH_TYPE_NAME END) AS ubicacion, ");
            stringQuery.append("    NVL2(EMS.MODEL_CODE ,EMS.MODEL_CODE,EMNS.MODEL_CODE) || ' / ' || NVL2(EMS.MODEL_NAME,EMS.MODEL_NAME,EMNS.MODEL_NAME) modelName, ");
            stringQuery.append("    NVL2(ETS.TYPE_ELEMENT_CODE,ETS.TYPE_ELEMENT_CODE,ETNS.TYPE_ELEMENT_CODE) typeElementCode, ");
            stringQuery.append("    NVL2(ETS.TYPE_ELEMENT_NAME,ETS.TYPE_ELEMENT_NAME,ETNS.TYPE_ELEMENT_NAME) elementTypeName, ");            
            stringQuery.append("	SUM(WE.ACTUAL_QUANTITY) AS actualQuantity ");
            stringQuery.append(" FROM WAREHOUSE_ELEMENTS WE "); 
            stringQuery.append("	INNER JOIN RECORD_STATUS RS ON(RS.ID = WE.RECORD_STATUS_ID) ");
            stringQuery.append("	INNER JOIN WAREHOUSES W ON(W.ID = WE.WAREHOUSE_ID) ");
            stringQuery.append("	LEFT JOIN DEALERS D ON(W.DEALER_ID = D.ID) "); 
            stringQuery.append("	LEFT JOIN DEALERS DS ON(D.DEALER_BRANCH_ID = DS.ID) "); 
            stringQuery.append("	LEFT JOIN WAREHOUSE_TYPES WT ON(WT.ID = W.WH_TYPE_ID) ");
            stringQuery.append("	LEFT JOIN ELEMENTS ES ON(ES.ID = WE.SER_ID ) ");
            stringQuery.append("	LEFT JOIN ELEMENT_TYPES ETS ON(ETS.ID = ES.ELEMENT_TYPE_ID) "); 
            stringQuery.append("	LEFT JOIN ELEMENT_MODELS EMS ON(EMS.ID = ETS.ELEMENT_MODEL_ID) ");       
            stringQuery.append("	LEFT JOIN ELEMENTS ENS ON(ENS.ID = WE.NOT_SER_ID ) ");
            stringQuery.append("	LEFT JOIN ELEMENT_TYPES ETNS ON(ETNS.ID = ENS.ELEMENT_TYPE_ID) "); 
            stringQuery.append("	LEFT JOIN ELEMENT_MODELS EMNS ON(EMNS.ID = ETNS.ELEMENT_MODEL_ID) ");      
            stringQuery.append("	LEFT JOIN EMPLOYEE_CREWS EC ON(EC.CREW_ID = W.CREW_ID AND EC.IS_RESPONSIBLE = 'S') "); 
            stringQuery.append("	LEFT JOIN EMPLOYEES E ON(E.ID = EC.EMPLOYEE_ID) ");
            stringQuery.append("	LEFT JOIN CUSTOMERS CUS ON(CUS.ID = W.CUSTOMER_ID) ");
            stringQuery.append(" WHERE W.COUNTRY_ID = :aCountryId ");
            stringQuery.append(" 	AND RS.RECORD_STATUS_CODE = 'U' ");
            stringQuery.append(" GROUP BY ");
            /*
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,D.DEPOT_CODE,DS.DEPOT_CODE) ||  ' ' || DECODE(D.DEALER_BRANCH_ID,NULL,D.DEALER_NAME,DS.DEALER_NAME), ");
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,NULL,D.DEPOT_CODE || ' ' || D.DEALER_NAME), ");
            stringQuery.append("	WT.WH_TYPE_NAME, ");
            stringQuery.append("	DECODE(W.CREW_ID,NULL,'',E.FIRST_NAME || ' ' || E.LAST_NAME), ");
            stringQuery.append("	(CASE WHEN W.CREW_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || E.FIRST_NAME || ' ' || E.LAST_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.DEALER_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.CUSTOMER_ID IS NOT NULL THEN CUS.CUSTOMER_CODE || ' - ' || CUS.FIRST_NAME || ' - ' || CUS.LAST_NAME || ' - ' || WT.WH_TYPE_NAME END), ");
            stringQuery.append("	DECODE(EMS.MODEL_CODE,NULL,EMNS.MODEL_CODE,EMS.MODEL_CODE) || ' / ' || DECODE(EMS.MODEL_NAME,NULL,EMNS.MODEL_NAME,EMS.MODEL_NAME),  ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_CODE,NULL,ETNS.TYPE_ELEMENT_CODE,ETS.TYPE_ELEMENT_CODE),  ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_NAME,NULL,ETNS.TYPE_ELEMENT_NAME,ETS.TYPE_ELEMENT_NAME) ");
            */            
            stringQuery.append("    NVL2(D.DEALER_BRANCH_ID,DS.DEPOT_CODE,D.DEPOT_CODE)||' '||NVL2(D.DEALER_BRANCH_ID,DS.DEALER_NAME,D.DEALER_NAME), ");
            stringQuery.append("    NVL2(D.DEALER_BRANCH_ID ,D.DEPOT_CODE || ' ' || D.DEALER_NAME,NULL), ");
            stringQuery.append("	WT.WH_TYPE_NAME , ");
            stringQuery.append("    NVL2(W.CREW_ID,E.FIRST_NAME ||' '|| E.LAST_NAME,''), ");
            stringQuery.append("	(CASE WHEN W.CREW_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || E.FIRST_NAME || ' ' || E.LAST_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.DEALER_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.CUSTOMER_ID IS NOT NULL THEN CUS.CUSTOMER_CODE || ' - ' || CUS.FIRST_NAME || ' - ' || CUS.LAST_NAME || ' - ' || WT.WH_TYPE_NAME END), ");
            stringQuery.append("    NVL2(EMS.MODEL_CODE ,EMS.MODEL_CODE,EMNS.MODEL_CODE) || ' / ' || NVL2(EMS.MODEL_NAME,EMS.MODEL_NAME,EMNS.MODEL_NAME), ");
            stringQuery.append("    NVL2(ETS.TYPE_ELEMENT_CODE,ETS.TYPE_ELEMENT_CODE,ETNS.TYPE_ELEMENT_CODE), ");
            stringQuery.append("    NVL2(ETS.TYPE_ELEMENT_NAME,ETS.TYPE_ELEMENT_NAME,ETNS.TYPE_ELEMENT_NAME)  ");
            
            
            stringQuery.append(" ORDER BY company DESC, branch DESC, whTypeName DESC, crewName DESC, ubicacion DESC");

			//----------------------------------------------------------------
			
            Query querySQL = session.createSQLQuery(stringQuery.toString())
        			.addScalar("company")
        			.addScalar("branch")
        			.addScalar("whTypeName")
        			.addScalar("crewName")
        			.addScalar("ubicacion")
        			.addScalar("modelName")
        			.addScalar("typeElementCode")
        			.addScalar("elementTypeName")
        			.addScalar("actualQuantity", Hibernate.DOUBLE)
        			.setResultTransformer(Transformers.aliasToBean(WareHouseElementsReportDTO.class));
            querySQL.setParameter("aCountryId", countryId, Hibernate.LONG);
            
          //Paginacion
        	if(requestInfo != null){		
	        	querySQL.setFirstResult(requestInfo.getFirstResult());
	        	querySQL.setMaxResults(requestInfo.getMaxResults());				
        	}
        	
        	List<WareHouseElementsReportDTO> responseList = querySQL.list();
        	return responseList;
        }catch (Throwable ex) {
            log.error("== Error getWarehouseElements==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseElements/ReportsStockDAO ==");
        }
	}

	//CC053 - HSP Reportes
	//Req.ACM-F-05_HSP_ReportesSC_CC053
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.ReportsCoreDAOLocal#getActivityBacklog()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<QuantityWarehouseElementsDTO> getWarehouseMovements(Long countryId, Date nowDate, RequestCollectionInfo requestInfo, QuantityWarehouseElementsDTO quantityWarehouseElementsDTO) throws DAOSQLException, DAOServiceException {
        try {
            log.debug("== Inicio getWarehouseMovements/ReportsStockDAO ==");
            Session session = super.getSession();
            
          //Fecha Inicio
			Date movementDateIn=quantityWarehouseElementsDTO.getMovementDateIn();
			
			//Fecha Final
			Date movementDateOut=quantityWarehouseElementsDTO.getMovementDateOut();
			
          //Consulta que retorna el detalle de los elementos
            StringBuffer stringQuery = new StringBuffer("");
           
            stringQuery.append(" SELECT ");
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,D.DEPOT_CODE,DS.DEPOT_CODE) ||  ' ' || DECODE(D.DEALER_BRANCH_ID,NULL,D.DEALER_NAME,DS.DEALER_NAME) dealerNameCompany, ");
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,NULL,D.DEPOT_CODE || ' ' || D.DEALER_NAME) dealerNameBranch, ");
            stringQuery.append("	DECODE(W.CREW_ID,NULL,'',E.FIRST_NAME || ' ' || E.LAST_NAME) isResponsibleOut, ");
            stringQuery.append("	(CASE WHEN W.CREW_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || E.FIRST_NAME || ' ' || E.LAST_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.DEALER_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.CUSTOMER_ID IS NOT NULL THEN CUS.CUSTOMER_CODE || ' - ' || CUS.FIRST_NAME || ' - ' || CUS.LAST_NAME || ' - ' || WT.WH_TYPE_NAME END) AS whName, ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_CODE,NULL,ETNS.TYPE_ELEMENT_CODE,ETS.TYPE_ELEMENT_CODE) typeElementCode, ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_NAME,NULL,ETNS.TYPE_ELEMENT_NAME,ETS.TYPE_ELEMENT_NAME) typeElementName, ");
          stringQuery.append("	DECODE(MT.MOV_CLASS,'E','Entrada','Salida') movClass, ");
          //stringQuery.append("	DECODE(MT.MOV_CLASS, :movementType,'Entrada','Salida') movClass, ");          
            stringQuery.append("	MT.MOV_TYPE_NAME movTypeName, ");
          stringQuery.append("	SUM((CASE WHEN MT.MOV_CLASS='E' THEN (WE.MOVED_QUANTITY) ELSE (-1*WE.MOVED_QUANTITY) END)) actualQuantity ");
          //stringQuery.append("	SUM((CASE WHEN MT.MOV_CLASS= :movementType THEN (WE.MOVED_QUANTITY) ELSE (-1*WE.MOVED_QUANTITY) END)) actualQuantity ");
            stringQuery.append(" FROM WAREHOUSE_ELEMENTS WE ");
            stringQuery.append("	INNER JOIN WAREHOUSES W ON(W.ID = WE.WAREHOUSE_ID) ");
            stringQuery.append("	LEFT JOIN MOVEMENT_TYPES MT ON(MT.ID = WE.MOV_TYPE_ID) ");
            stringQuery.append("	LEFT JOIN DEALERS D ON(D.ID = W.DEALER_ID) ");
            stringQuery.append("	LEFT JOIN DEALERS DS ON(DS.ID = D.DEALER_BRANCH_ID) ");
            stringQuery.append("	LEFT JOIN WAREHOUSE_TYPES WT ON(WT.ID = W.WH_TYPE_ID) ");
            stringQuery.append("	LEFT JOIN ELEMENTS ES ON(ES.ID = WE.SER_ID) ");
            stringQuery.append("	LEFT JOIN ELEMENT_TYPES ETS ON(ETS.ID = ES.ELEMENT_TYPE_ID) ");
            stringQuery.append("	LEFT JOIN ELEMENTS ENS ON(ENS.ID = WE.NOT_SER_ID) ");
            stringQuery.append("	LEFT JOIN ELEMENT_TYPES ETNS ON(ETNS.ID = ENS.ELEMENT_TYPE_ID) ");
           stringQuery.append("	LEFT JOIN EMPLOYEE_CREWS EC ON(EC.CREW_ID = W.CREW_ID AND EC.IS_RESPONSIBLE = 'S') ");
           //stringQuery.append("	LEFT JOIN EMPLOYEE_CREWS EC ON(EC.CREW_ID = W.CREW_ID AND EC.IS_RESPONSIBLE = :isResponsible) ");
            stringQuery.append("	LEFT JOIN EMPLOYEES E ON(E.ID = EC.EMPLOYEE_ID) ");
            stringQuery.append("	LEFT JOIN CUSTOMERS CUS ON(CUS.ID = W.CUSTOMER_ID) ");
            stringQuery.append(" WHERE W.COUNTRY_ID = :aCountryId ");
            stringQuery.append(" AND trunc(WE.MOVEMENT_DATE) >= trunc(:aMovementDateIn) ");
            stringQuery.append(" AND trunc(WE.MOVEMENT_DATE) <= trunc(:aMovementDateOut) ");
            stringQuery.append(" GROUP BY  ");
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,D.DEPOT_CODE,DS.DEPOT_CODE) ||  ' ' || DECODE(D.DEALER_BRANCH_ID,NULL,D.DEALER_NAME,DS.DEALER_NAME), ");
            stringQuery.append("	DECODE(D.DEALER_BRANCH_ID,NULL,NULL,D.DEPOT_CODE || ' ' || D.DEALER_NAME), ");
            stringQuery.append("	DECODE(W.CREW_ID,NULL,'',E.FIRST_NAME || ' ' || E.LAST_NAME), ");
            stringQuery.append("	(CASE WHEN W.CREW_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || E.FIRST_NAME || ' ' || E.LAST_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.DEALER_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || WT.WH_TYPE_NAME WHEN W.CUSTOMER_ID IS NOT NULL THEN CUS.CUSTOMER_CODE || ' - ' || CUS.FIRST_NAME || ' - ' || CUS.LAST_NAME || ' - ' || WT.WH_TYPE_NAME END), ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_CODE,NULL,ETNS.TYPE_ELEMENT_CODE,ETS.TYPE_ELEMENT_CODE), ");
            stringQuery.append("	DECODE(ETS.TYPE_ELEMENT_NAME,NULL,ETNS.TYPE_ELEMENT_NAME,ETS.TYPE_ELEMENT_NAME), ");
          stringQuery.append("	DECODE(MT.MOV_CLASS,'E','Entrada','Salida'), ");
          //stringQuery.append("	DECODE(MT.MOV_CLASS, :movementType ,'Entrada','Salida'), ");
            stringQuery.append("	MT.MOV_TYPE_NAME ");
            stringQuery.append(" ORDER BY ");
            stringQuery.append(" 	dealerNameCompany DESC, dealerNameBranch DESC, isResponsibleOut DESC, whName DESC ");
            
			//----------------------------------------------------------------
			
            Query querySQL = session.createSQLQuery(stringQuery.toString())
        			.addScalar("dealerNameCompany")
        			.addScalar("dealerNameBranch")
        			.addScalar("isResponsibleOut")
        			.addScalar("whName")
        			.addScalar("typeElementCode")
        			.addScalar("typeElementName")
        			.addScalar("movClass")
        			.addScalar("movTypeName")
        			.addScalar("actualQuantity", Hibernate.DOUBLE)       			
        			.setResultTransformer(Transformers.aliasToBean(QuantityWarehouseElementsDTO.class));
            
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
        	
        	List<QuantityWarehouseElementsDTO> responseList = querySQL.list();
        	return responseList;
        }catch (Throwable ex) {
            log.error("== Error getWarehouseMovements==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseMovements/ReportsStockDAO ==");
        }
	}
}
