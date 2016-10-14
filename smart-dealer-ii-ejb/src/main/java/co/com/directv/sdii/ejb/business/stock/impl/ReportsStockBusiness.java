/**
 * 
 */
package co.com.directv.sdii.ejb.business.stock.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.impl.ReportsCoreBusiness;
import co.com.directv.sdii.ejb.business.stock.ReportsStockBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.ReportsParameterInputDTO;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.DocumentClass;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReportsStockDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.WareHouseElementsReportDTO;

/**
 * CC053 - HSP Reportes de inventarios.
 * Session Bean implementation class ReportsStockBusiness
 */
@Stateless(name="ReportsStockBusinessLocal",mappedName="ejb/ReportsStockBusinessLocal")
public class ReportsStockBusiness  extends BusinessBase implements ReportsStockBusinessLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ReportsCoreBusiness.class);
	
	
	@EJB(name="ReportsStockDAOLocal", beanInterface=ReportsStockDAOLocal.class)
	private ReportsStockDAOLocal reportsStockDAO;
	
	//CC053
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;//debo modificar la interfas
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

	@EJB(name="DocumentClassDAOLocal", beanInterface=DocumentClassDAOLocal.class)
	private DocumentClassDAOLocal documentClassDAO;                                 
	
	@EJB(name="WarehouseTypeDAOLocal", beanInterface=WarehouseTypeDAOLocal.class)
    private WarehouseTypeDAOLocal warehouseTypeDAO;
    
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void generateReport(ReportsParameterInputDTO request)
			throws BusinessException {
		log.debug("== Inicia generateReport/ReportsStockBusiness ==");
    	try{
    		
    	    if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_QTY_WAREHOUSES_ELEMENTS.getCodeEntity())){
    	    	populateWarehouseElements(request);
    	    }else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_WAREHOUSES_ELEMENTS_IN_DETAILS.getCodeEntity())){
                populateWarehouseElementsInDetails(request);
    		}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_WAREHOUSES_MOVEMENTS_KARDEX.getCodeEntity())){
                populateWarehousesMovementsKardex(request);
    		}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_WAREHOUSES_MOVEMENTS.getCodeEntity())){
    			populateWarehousesMovements(request);
			}
    		
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportsStockBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportsStockBusiness ==");
		}

	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void populateWarehouseElements(ReportsParameterInputDTO request) throws BusinessException {
		try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			List<String> columnNames = new ArrayList<String>();
			columnNames.add("# "+ApplicationTextEnum.COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.BRANCH.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WAREHOUSE_TYPE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.LOCATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.MODEL_CODE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_TYPE_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_TYPE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.QUANTITY.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("company");
			fieldNames.add("branch");
			fieldNames.add("whTypeName");
			fieldNames.add("crewName");
			fieldNames.add("ubicacion");
			fieldNames.add("modelName"); //modelName and modelCode
			fieldNames.add("typeElementCode");
			fieldNames.add("elementTypeName");
			fieldNames.add("actualQuantity");
			
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				List<WareHouseElementsReportDTO> dataList = reportsStockDAO.getWarehouseElements(request.getCountryId(), ri);
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList, fieldNames, columnNames, page, nameFile);
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación generateReport/ReportsStockBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportsStockBusiness ==");
		}
	}
	
	/*
	 * Reporte generado por work: Reporte Saldo detallado - WAREHOUSE_ELEMENTS_IN_DETAILS - WED - automatico
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void populateWarehouseElementsInDetails(ReportsParameterInputDTO request) throws BusinessException {
		try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			WhElementSearchFilter whElementSearchFilter= new WhElementSearchFilter();
			whElementSearchFilter.setUserId(UtilsBusiness.getUserIdAdmin(userDao,systemParameterDAO,request.getCountryId() ));
    		
			List<String> columnNames = new ArrayList<String>();
			columnNames.add(ApplicationTextEnum.COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.BRANCH.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WAREHOUSE_TYPE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.LOCATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.MODEL_CODE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_TYPE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERIAL.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RID.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.QUANTITY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.AGE.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("company");
			fieldNames.add("branch");
			fieldNames.add("whTypeName");
			fieldNames.add("crewName");
			fieldNames.add("ubicacion");
			fieldNames.add("modelName");
			fieldNames.add("typeElementCode");
			fieldNames.add("elementTypeName");
			fieldNames.add("serialCode");
			fieldNames.add("rid");
			fieldNames.add("serialCodeLinked");
			fieldNames.add("actualQuantity");
			fieldNames.add("age");
			
			RequestCollectionInfo ri = new RequestCollectionInfo();
			while(needOtherCall){
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				QuantityWarehouseElementResponse response = warehouseElementDAO.getWarehouseElementsByWarehouse(whElementSearchFilter,ri,false,true);

				List dataList = response.getWareHouseElementsReportDTO();
				
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList, fieldNames, columnNames, page, nameFile);
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación generateReport/ReportsStockBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportsStockBusiness ==");
		}
	}
    //Req.ACM-F-05_HSP_ReportesSC_CC053
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void populateWarehousesMovementsKardex(ReportsParameterInputDTO request) throws BusinessException {
		try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			QuantityWarehouseElementsDTO quantityWarehouseElementsDTO = new QuantityWarehouseElementsDTO();
			
			if (request.getBeginDate() != null) 
				quantityWarehouseElementsDTO.setMovementDateIn(request.getBeginDate());
			
			if (request.getEndDate() != null)
				quantityWarehouseElementsDTO.setMovementDateOut(request.getEndDate());
			
			List<String> columnNames = new ArrayList<String>();
			columnNames.add(ApplicationTextEnum.COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.BRANCH.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WAREHOUSE_LOCATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.MODEL.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INITIAL_QUANTITY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ENTRIES.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPARTURES.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CURRENT_BALANCE.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("dealerNameCompany");
			fieldNames.add("dealerNameBranch");
			fieldNames.add("crewId");
			fieldNames.add("whTypeName");
			fieldNames.add("modelName");
			fieldNames.add("typeElementName");
			fieldNames.add("initialQuantity");
			fieldNames.add("inQuantity");
			fieldNames.add("outQuantity");
			fieldNames.add("actualQuantity");
			
			RequestCollectionInfoDTO ri = new RequestCollectionInfoDTO();
			while(needOtherCall){

				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				QuantityWarehouseElementResponse response=warehouseElementDAO.getQuantityWarehouseElementsSummariesByFilters(quantityWarehouseElementsDTO,ri);
				List<QuantityWarehouseElementsDTO> dataList = response.getQuantityWarehouseElementsDTO();
                
				//loop para recorrer el dto y setear la cantidad inial y la cantidad actual
				for (QuantityWarehouseElementsDTO dto : dataList) {
					double initialQuantity=0d;
					if(dto.getInitialQuantity()!=null)
						initialQuantity=dto.getInitialQuantity();
					double inQuantity=0d;
					if(dto.getInQuantity()!=null)
						inQuantity=dto.getInQuantity();
					double outQuantity=0d;
					if(dto.getOutQuantity()!=null)
						outQuantity=dto.getOutQuantity();
					dto.setActualQuantity(initialQuantity+inQuantity-outQuantity);
				}

				
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList, fieldNames, columnNames, page, nameFile);
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación generateReport/ReportsStockBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportsStockBusiness ==");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void populateWarehousesMovements(ReportsParameterInputDTO request) throws BusinessException {
		try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			
			QuantityWarehouseElementsDTO quantityWarehouseElementsDTO = new QuantityWarehouseElementsDTO();
			if (request.getBeginDate() != null) 
				quantityWarehouseElementsDTO.setMovementDateIn(request.getBeginDate());
			
			if (request.getEndDate() != null)
				quantityWarehouseElementsDTO.setMovementDateOut(request.getEndDate());
			
			List<String> columnNames = new ArrayList<String>();
			columnNames.add(ApplicationTextEnum.COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.BRANCH.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WAREHOUSE_LOCATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_TYPE_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.MOVEMENT_TYPE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CAUSAL_MOTION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.QUANTITY.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("dealerNameCompany");
			fieldNames.add("dealerNameBranch");
			fieldNames.add("isResponsibleOut");
			fieldNames.add("whName");
			fieldNames.add("typeElementCode");
			fieldNames.add("typeElementName");
			fieldNames.add("movClass");
			fieldNames.add("movTypeName");
			fieldNames.add("actualQuantity");
			
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				
				//List<QuantityWarehouseElementsDTO> dataList = reportsStockDAO.getWarehouseMovements(request.getCountryId(), request.getDateNow(), ri);
				List<QuantityWarehouseElementsDTO> dataList = reportsStockDAO.getWarehouseMovements(request.getCountryId(), request.getDateNow(), ri, quantityWarehouseElementsDTO);
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList, fieldNames, columnNames, page, nameFile);
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación generateReport/ReportsStockBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportsStockBusiness ==");
		}
	}
	
	
}
