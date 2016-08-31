package co.com.directv.sdii.ejb.business.core.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.ExcelGenerator;
import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.common.util.MailMessage;
import co.com.directv.sdii.common.util.MailSenderLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.ReportsCoreBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;
import co.com.directv.sdii.model.dto.AuxTechnicianDetailsDTO;
import co.com.directv.sdii.model.dto.DispacherProductivityDTO;
import co.com.directv.sdii.model.dto.MonthlyActivityResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsParameterInputDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.dto.WoPendingLackMaterialsDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.ScheduleReportParameter;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ReportsCoreDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

/**
 * Session Bean implementation class ReportsCoreBusiness
 */
@Stateless(name="ReportsCoreBusinessLocal",mappedName="ejb/ReportsCoreBusinessLocal")
public class ReportsCoreBusiness extends BusinessBase implements ReportsCoreBusinessLocal {

	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal woDao;
	
	private final static Logger log = UtilsBusiness.getLog4J(ReportsCoreBusiness.class);
	
	@EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	
	@EJB(name="ReportsCoreDAOLocal", beanInterface=ReportsCoreDAOLocal.class)
	private ReportsCoreDAOLocal reportsCoreDAO;
	
	//REQ002 - WO Agendadas en linea.
	@EJB
	private ExcelGeneratorLocal excelGenerator;
	
	@EJB(name="WorkOrderCSRDAOLocal", beanInterface=WorkOrderCSRDAOLocal.class)
	private WorkOrderCSRDAOLocal workOrderCSRDAO ;
	
	@EJB(name="MailSenderLocal", beanInterface=MailSenderLocal.class)
	private MailSenderLocal mailSenderLocal;
	
    /**
     * Default constructor. 
     */
    public ReportsCoreBusiness() {
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ReportsProductivityReportAndFileResponseDTO reportsProductivityReport(
			ReportsComplyAndScheduleFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		try{
			validReportsProductivityReport(filter);
			Country country =getCountry(filter.getCountryCode());
			Date sysdate = getDate(country.getId(), userDao, systemParameterDAO);

			String max=CodesBusinessEntityEnum.CONSTANT_MAX_TIME_REPORT_CORE_PRODUCTIVITY.getCodeEntity();
			Long maxTime = Long.parseLong(max);
			Long difInMilis=(filter.getEndDate().getTime() - filter.getBeginDate().getTime());
			Long difInSegs=difInMilis/1000L;
			Long difInMins = difInSegs / 60L;
			Long difInHours = difInMins/60L;
			Long difInDays=difInHours/24L;
			if(difInDays>maxTime){
        		Object[] params = new Object[1];
        		params[0]=max;
        		throw new BusinessException(ErrorBusinessMessages.QUERY_SIZE_DAYS_RANGE_EXCEEDED.getCode(), ErrorBusinessMessages.QUERY_SIZE_DAYS_RANGE_EXCEEDED.getMessage(params));
			}
			
			ReportsProductivityReportAndFileResponseDTO response = new ReportsProductivityReportAndFileResponseDTO();
			if(!filter.isFileResponse()){
				if(requestInfo==null){
					requestInfo = new RequestCollectionInfo();
					requestInfo.setPageIndex(1);
					requestInfo.setPageSize(Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_PRODUCTIVITY.getCodeEntity()));
				}else{
					if(requestInfo.getPageSize()>Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_PRODUCTIVITY.getCodeEntity())){
		        		Object[] params = new Object[1];
		        		params[0]=CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_PRODUCTIVITY.getCodeEntity();
		        		throw new BusinessException(ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getCode(), ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getMessage(params));
					}
					if(requestInfo.getPageIndex()<=0 || requestInfo.getPageSize()<=0){
						throw new BusinessException("Los parametros de paginacion son invalidos");
					}
				}
				return woDao.getReportsProductivityReport(filter, requestInfo,country.getId(),sysdate);
			}else{
				boolean needOtherCall = true;
				int page = 0;
				int pageSize = Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT.getCodeEntity());
				String nameFile=null;
				List<String> columnNames = new ArrayList<String>();
				columnNames.add(ApplicationTextEnum.CLIENT_NUMBER.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.ORDER_NUMBER.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CLIENT_NAME.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.JOB_CARD.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.JOB_CARD_TYPE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.JOB_CARD_IBS_STATUS.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.TECHNICAL_NAME.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DAY_QUANTITY.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.COMPLETION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.ADDRESS.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.TELEPHONE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.PROBLEM_DESCRIPTION.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.MODEL.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.PRODUCT.getApplicationTextValue());
				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("clientNumber");
				fieldNames.add("orderNumber");
				fieldNames.add("clientName");
				fieldNames.add("city");
				fieldNames.add("creationDate");
				fieldNames.add("jobCard");
				fieldNames.add("jobCardType");
				fieldNames.add("jobIbsStatus");
				fieldNames.add("technicalName");
				fieldNames.add("dayQty");
				fieldNames.add("completationDate");
				fieldNames.add("address");
				fieldNames.add("telephone");
				fieldNames.add("problemDescription");
				fieldNames.add("model");
				fieldNames.add("product");
				while(needOtherCall){
					RequestCollectionInfo ri = new RequestCollectionInfo();
					ri.setPageIndex(page+1);
					ri.setPageSize(pageSize);
					List<ReportsProductivityReportDTO> dataList = woDao.getReportsProductivityReport(filter, ri,country.getId(),sysdate).getReportsProductivityReportDTOList();
					if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
						needOtherCall = false;
					}
					nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile);
					++page;
				}
				String nameFileResponse = "report"+nameFile;
				String fileType = "";
				if(filter.isCsvFile()){
					nameFileResponse+=".csv";
					fileType = "text/plain";
				}else{
					nameFileResponse+=".zip";
					fileType = "application/zip";
					UtilsBusiness.createZip(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".zip",ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv","report"+nameFile+".csv", filter.getFileName()+".csv");
					File temp = new File(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv");
					temp.delete();
				}
				response.setFile(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, filter.getFileName(), fileType) );
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(
			ReportsComplyAndScheduleFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		try{
			validReportsProductivityReport(filter);
			Country country =getCountry(filter.getCountryCode());
			ReportsComplyAndScheduleAndFileResponseDTO response = new ReportsComplyAndScheduleAndFileResponseDTO();
			String max=CodesBusinessEntityEnum.CONSTANT_MAX_TIME_REPORT_CORE_COMPLY_AND_SCHEDULE.getCodeEntity();
			Long maxTime = Long.parseLong(max);
			Long difInMilis=(filter.getEndDate().getTime() - filter.getBeginDate().getTime());
			Long difInSegs=difInMilis/1000L;
			Long difInMins = difInSegs / 60L;
			Long difInHours = difInMins/60L;
			Long difInDays=difInHours/24L;
			if(difInDays>maxTime){
				if(difInDays>maxTime){
	        		Object[] params = new Object[1];
	        		params[0]=max;
	        		throw new BusinessException(ErrorBusinessMessages.QUERY_SIZE_DAYS_RANGE_EXCEEDED.getCode(), ErrorBusinessMessages.QUERY_SIZE_DAYS_RANGE_EXCEEDED.getMessage(params));
				}
			}
			if(!filter.isFileResponse()){
				if(requestInfo==null){
					requestInfo = new RequestCollectionInfo();
					requestInfo.setPageIndex(1);
					requestInfo.setPageSize(Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_COMPLY_AND_SCHEDULE.getCodeEntity()));
				}else{
					if(requestInfo.getPageSize()>Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_COMPLY_AND_SCHEDULE.getCodeEntity())){
		        		Object[] params = new Object[1];
		        		params[0]=CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_COMPLY_AND_SCHEDULE.getCodeEntity();
		        		throw new BusinessException(ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getCode(), ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getMessage(params));
					}
					if(requestInfo.getPageIndex()<=0 || requestInfo.getPageSize()<=0){
						throw new BusinessException("Los parametros de paginacion son invalidos");
					}
				}
				return woDao.getReportsComplyAndSchedule(filter, requestInfo,country.getId());
			}else{
				boolean needOtherCall = true;
				int page = 0;
				int pageSize = Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT.getCodeEntity());
				String nameFile=null;
				List<String> columnNames = new ArrayList<String>();
				columnNames.add(ApplicationTextEnum.CLIENT_NUMBER.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.WO_NUMBER.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.SERVICE_CAT.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.SERVICE_TYPE_NAME.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.ASSIGNATION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.SCHEDULE_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.ATTENTION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.TECHNICAL_NAME.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.SCHEDULED.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.RESCHEDULED.getApplicationTextValue());
				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("clientNum");
				fieldNames.add("woNum");
				fieldNames.add("serviceCat");
				fieldNames.add("serviceTypeName");
				fieldNames.add("creationDate");
				fieldNames.add("allocationDate");
				fieldNames.add("schedulerDate");
				fieldNames.add("attentionDate");
				fieldNames.add("city");
				fieldNames.add("employeeName");
				fieldNames.add("scheduled");
				fieldNames.add("rescheduled");
				while(needOtherCall){
					RequestCollectionInfo ri = new RequestCollectionInfo();
					ri.setPageIndex(page+1);
					ri.setPageSize(pageSize);
					List<ReportsComplyAndScheduleDTO> dataList = woDao.getReportsComplyAndSchedule(filter, ri,country.getId()).getReportsComplyAndScheduleDTOList();
					if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
						needOtherCall = false;
					}
					nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile);
					++page;
				}
				String nameFileResponse = "report"+nameFile;
				String fileType = "";
				if(filter.isCsvFile()){
					nameFileResponse+=".csv";
					fileType = "text/plain";
				}else{
					nameFileResponse+=".zip";
					fileType = "application/zip";
					UtilsBusiness.createZip(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".zip",ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv","report"+nameFile+".csv", filter.getFileName()+".csv");
					File temp = new File(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv");
					temp.delete();
				}
				response.setFile(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, filter.getFileName(), fileType) );
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(
			ReportsPendingServicesByDateFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		try{
			validGetReportsPendingServicesByDate(filter);
			Country country =getCountry(filter.getCountryCode());
			Date sysdate = getDate(country.getId(), userDao, systemParameterDAO);
			ReportsPendingServicesByDateAndFileResponseDTO response = new ReportsPendingServicesByDateAndFileResponseDTO();	
			if(!filter.isFileResponse()){
				if(requestInfo==null){
					requestInfo = new RequestCollectionInfo();
					requestInfo.setPageIndex(1);
					requestInfo.setPageSize(Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_PENDING_SERVICES_BY_DATE.getCodeEntity()));
				}else{
					if(requestInfo.getPageSize()>Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_PENDING_SERVICES_BY_DATE.getCodeEntity())){
		        		Object[] params = new Object[1];
		        		params[0]=CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_PENDING_SERVICES_BY_DATE.getCodeEntity();
		        		throw new BusinessException(ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getCode(), ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getMessage(params));
					}
					if(requestInfo.getPageIndex()<=0 || requestInfo.getPageSize()<=0){
						throw new BusinessException("Los parametros de paginacion son invalidos");
					}
				}
				return woDao.getReportsPendingServicesByDate(filter, requestInfo,country.getId(),sysdate);
			}else{
				boolean needOtherCall = true;
				int page = 0;
				int pageSize = Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT.getCodeEntity());
				String nameFile=null;
				List<String> columnNames = new ArrayList<String>();
				columnNames.add(ApplicationTextEnum.CLIENT_NUMBER.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.ORDER_NUMBER.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CLIENT_NAME.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.JOB_CARD.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.JOB_CARD_TYPE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.JOB_CARD_STATUS.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.TECHNICAL_NAME.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DAY_QUANTITY.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.APPOINTMENT.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.ADDRESS.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.TELEPHONE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.PROBLEM_DESCRIPTION.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.MODEL.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.PRODUCT.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.ASSIGNATION_DATE.getApplicationTextValue());
				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("clientNumber");
				fieldNames.add("orderNumber");
				fieldNames.add("clientName");
				fieldNames.add("city");
				fieldNames.add("creationDate");
				fieldNames.add("jobCard");
				fieldNames.add("jobCardType");
				fieldNames.add("jobCardStatus");
				fieldNames.add("technicalName");
				fieldNames.add("dayQty");
				fieldNames.add("appointment");
				fieldNames.add("address");
				fieldNames.add("telephone");
				fieldNames.add("problemDescription");
				fieldNames.add("model");
				fieldNames.add("product");
				fieldNames.add("allocationDate");
				while(needOtherCall){
					RequestCollectionInfo ri = new RequestCollectionInfo();
					ri.setPageIndex(page+1);
					ri.setPageSize(pageSize);
					List<ReportsPendingServicesByDateDTO> dataList = woDao.getReportsPendingServicesByDate(filter, ri,country.getId(),sysdate).getReportsPendingServicesByDateDTOList();
					if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
						needOtherCall = false;
					}
					nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile);
					++page;
				}
				String nameFileResponse = "report"+nameFile;
				String fileType = "";
				if(filter.isCsvFile()){
					nameFileResponse+=".csv";
					fileType = "text/plain";
				}else{
					nameFileResponse+=".zip";
					fileType = "application/zip";
					UtilsBusiness.createZip(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".zip",ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv","report"+nameFile+".csv", filter.getFileName()+".csv");
					File temp = new File(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv");
					temp.delete();
				}
				response.setFile(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, filter.getFileName(), fileType) );
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
	}
    
    private Country getCountry(String countryCode) throws BusinessException, DAOServiceException, DAOSQLException{
	    
    	List<String> params = new ArrayList<String>();
    	Country country =countryDao.getCountriesByCode(countryCode);
		if(country==null){
			params.add(countryCode);
			throw new BusinessException(ErrorBusinessMessages.CORE_CR065.getCode(), ErrorBusinessMessages.CORE_CR065.getMessage(), params);
		}
		
		return country;
		
	}
    
    private Date getDate(Long countryId, UserDAOLocal userDao, SystemParameterDAOLocal systemParameterDAO){
    	return UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO);
    }
    
    private void validReportsProductivityReport(ReportsComplyAndScheduleFilterDTO filter) throws BusinessException{
		
    	if(filter.getCountryCode()==null || filter.getCountryCode().equals("")){
			throw new BusinessException(ErrorBusinessMessages.COUNTRY_REQUIRED.getCode(), ErrorBusinessMessages.COUNTRY_REQUIRED.getMessage());
		}
			
		if(filter.getBeginDate()==null){
			throw new BusinessException(ErrorBusinessMessages.START_DATE_REQUIRED.getCode(), ErrorBusinessMessages.START_DATE_REQUIRED.getMessage());
		}
			
		if(filter.getEndDate()==null){
			throw new BusinessException(ErrorBusinessMessages.FINAL_DATE_REQUIRED.getCode(), ErrorBusinessMessages.FINAL_DATE_REQUIRED.getMessage());
		}
		
		if(filter.getBeginDate().after(filter.getEndDate())){
			throw new BusinessException(ErrorBusinessMessages.INIT_DATE_AFTER_END_DATE.getCode(), ErrorBusinessMessages.INIT_DATE_AFTER_END_DATE.getMessage());
		}
		
	}
    
    /**
     * Metodo: Permite validar el dato pais como requerida
     * @param filter
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    private void validGetReportsPendingServicesByDate(ReportsPendingServicesByDateFilterDTO filter) throws BusinessException{
    	if(filter.getCountryCode()==null || filter.getCountryCode().equals("")){
			throw new BusinessException(ErrorBusinessMessages.COUNTRY_REQUIRED.getCode(), ErrorBusinessMessages.COUNTRY_REQUIRED.getMessage());
		}
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.core.ReportsCoreBusinessLocal#getActivityBacklog()
     */
    public List<ActivityBacklogResponseDTO> getActivityBacklog(String countryCode) throws BusinessException{
    	
    	try{
    		log.debug("== Inicia getActivityBacklog/ReportGeneratorBusinessBean ==");
        	validGetActivityBacklog(countryCode);
    		Country country = this.getCountry(countryCode);
    		Date sysdate = getDate(country.getId(), userDao, systemParameterDAO);
    		return reportsCoreDAO.getActivityBacklog(country.getId(),sysdate, null, CodesBusinessEntityEnum.WORKORDER_TYPE_INSTALL.getCodeEntity(),CodesBusinessEntityEnum.WORKORDER_TYPE_SERVICE.getCodeEntity(),CodesBusinessEntityEnum.WORKORDER_TYPE_MOVE.getCodeEntity(),CodesBusinessEntityEnum.WORKORDER_TYPE_LOCAL_USE.getCodeEntity());
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getActivityBacklog/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getActivityBacklog/ReportGeneratorBusinessBean ==");
		}
		
    }
    
    /**
     * Metodo: Permite validar el dato pais como requerida
     * @param countryCode
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    private void validGetActivityBacklog(String countryCode) throws BusinessException{
    	if(countryCode==null || countryCode.trim().equals("")){
			throw new BusinessException(ErrorBusinessMessages.COUNTRY_REQUIRED.getCode(), ErrorBusinessMessages.COUNTRY_REQUIRED.getMessage());
		}
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.core.ReportsCoreBusinessLocal#generateReport(co.com.directv.sdii.model.dto.ReportsParameterInputDTO)
     */
	//CC053 y REQ002
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void generateReport(ReportsParameterInputDTO request) throws BusinessException{
    	
    	log.info("== Inicia generateReport/ReportGeneratorBusinessBean ==");
    	try{
    		
    	    if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_MONTHLY_ACTIVITY.getCodeEntity())){
    	    	populateMonthlyActivity(request);
    	    }else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_JOIN_MONTHLY_ACTIVITY.getCodeEntity())){
    	    	populateJoinMonthlyActivity(request);
    		}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_BACKLOG_ACTIVITY.getCodeEntity())){
    			populateReportActivityBacklog(request, CodesBusinessEntityEnum.WORKORDER_TYPE_INSTALL.getCodeEntity(),CodesBusinessEntityEnum.WORKORDER_TYPE_SERVICE.getCodeEntity(),CodesBusinessEntityEnum.WORKORDER_TYPE_MOVE.getCodeEntity(),CodesBusinessEntityEnum.WORKORDER_TYPE_LOCAL_USE.getCodeEntity());
    		}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_ACTIVITY_BACKLOG_DISCONNECTS.getCodeEntity())){
    			populateReportActivityBacklog(request, CodesBusinessEntityEnum.WORKORDER_TYPE_DISCONNECTION.getCodeEntity());
			}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_PRODUCTIVITY_DISPATCHERS.getCodeEntity())){
				populateDispacherProductivity(request);
			}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_REJECTED_RN_03_WORK_ORDERS.getCodeEntity())){
				pupulateWoPendingLackMaterials(request);
			}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_SUCCEED_WORK_ORDERS.getCodeEntity())){
				populateSucceedWorkOrdersCSR(request);
			}else if(request.getCodeScheduleReportType().equals(CodesBusinessEntityEnum.REPORT_TYPE_CORE_AUXILIAR_EMPLOYEE.getCodeEntity())){
				populateAuxiliarTechnician(request);
			}
    		
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.info("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    	
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void populateReportActivityBacklog(ReportsParameterInputDTO request, String ... serviceTypes) throws BusinessException{
    	try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			List<String> columnNames = new ArrayList<String>();
			columnNames.add(ApplicationTextEnum.WO.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_IBS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.TELEPHONE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ADDRESS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PROGRAMATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PROGRAMATION_JOURNEY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WO_DESCRIPTION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.HSP_STATUS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.IBS_STATUS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RESPONSABLE_CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPOT_INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.BACKLOG_DAYS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RESCHEDULING.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_USER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPARTMENT.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CONFIGURATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.REASON.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SELLER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPOT_SELLER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.COMPANY_OBSERVATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_TYPE.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("numWorkOrder");
			fieldNames.add("ibsClient");
			fieldNames.add("phone");
			fieldNames.add("address");
			fieldNames.add("programmingDate");
			fieldNames.add("serviceHour");
			fieldNames.add("creationDate");
			fieldNames.add("description");
			fieldNames.add("stateWorkOrder");
			fieldNames.add("ibsState");
			fieldNames.add("crewResponsible");
			fieldNames.add("depotCodeDealer");
			fieldNames.add("nameDealer");
			fieldNames.add("pendingDays");
			fieldNames.add("reschedule");
			fieldNames.add("dispatcher");
			fieldNames.add("dispacherName");
			fieldNames.add("serviceCategory");
			fieldNames.add("serviceName");
			fieldNames.add("serviceCode");
			fieldNames.add("state");
			fieldNames.add("city");
			fieldNames.add("contDecos");
			fieldNames.add("lastReason");
			fieldNames.add("nameCodeSalerCompany");
			fieldNames.add("depotCodeSalerCompany");
			fieldNames.add("observationCompany");
			fieldNames.add("customerTypeName");
			Date sysdate = getDate(request.getCountryId(), userDao, systemParameterDAO);
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				List<ActivityBacklogResponseDTO> dataList=reportsCoreDAO.getActivityBacklog(request.getCountryId(),sysdate, ri, serviceTypes);
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile,"serviceHour");
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void populateDispacherProductivity(ReportsParameterInputDTO request) throws BusinessException{
    	try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			List<String> columnNames = new ArrayList<String>();
			columnNames.add(ApplicationTextEnum.WO.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_IBS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_USER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.HSP_STATUS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CHANGE_STATUS_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RESPONSABLE_CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPOT_INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PROGRAMATION_JOURNEY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("woCode");
			fieldNames.add("customerCode");
			fieldNames.add("dispacher");
			fieldNames.add("dispacherName");
			fieldNames.add("state");
			fieldNames.add("stateDate");
			fieldNames.add("crewResponsible");
			fieldNames.add("depotCodeDealer");
			fieldNames.add("nameDealer");
			fieldNames.add("serviceHourName");
			fieldNames.add("serviceCategory");
			fieldNames.add("serviceName");
			fieldNames.add("serviceCategoryCode");
			fieldNames.add("creationDate");
			
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				List<DispacherProductivityDTO> dataList=reportsCoreDAO.getDispacherProductivity(request.getCountryId(), ri, request.getDateNow());
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile, "serviceHourName");
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
			
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void populateMonthlyActivity(ReportsParameterInputDTO request) throws BusinessException{
    	try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			List<String> columnNames = new ArrayList<String>();
			columnNames.add(ApplicationTextEnum.WO.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_IBS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_USER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SELLER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPOT_SELLER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.HSP_STATUS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.IBS_STATUS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.REASON.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_MODEL.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PUBLICATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ASSIGNATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PROGRAMATION_JOURNEY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PROGRAMATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ATTENTION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.FINALIZATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WO_DESCRIPTION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ACTION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPOT_INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.POSTAL_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPARTMENT.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RESPONSABLE_DOCUMENT.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RESPONSABLE_CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.AUXILIAR_CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.LAST_GESTION_REASON.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PHONE_CLIENTE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_ADDRESS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DECOS_QUANTITY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PREVIOUS_VISITS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.TECHNICAL_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.COMPANY_OBSERVATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RETIRED_IRD_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RETIRED_SC_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLED_IRD_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLED_SC_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_USER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DATE_FIRST_SCHEDULING.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.FIRST_RESPONSIBLE_CREW_ASSIGN.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CODE_DEPOT_DEALER_FIRST_ASSIGN.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.NAME_DEALER_FIRST_ASSIGN.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("woCode");
			fieldNames.add("customerCode");
			fieldNames.add("customerName");
			fieldNames.add("customerTypeName");
			fieldNames.add("dealerNameS");
			fieldNames.add("depotCodeS");
			fieldNames.add("woStateName");
			fieldNames.add("ibs6StateName");
			fieldNames.add("reason");
			fieldNames.add("model");
			fieldNames.add("creationDate");
			fieldNames.add("importDate");
			fieldNames.add("assignDealerDate");
			fieldNames.add("jornada");
			fieldNames.add("jornadaDate");
			fieldNames.add("woRealizationDate");
			fieldNames.add("finalizationDate");
			fieldNames.add("woDescription");
			fieldNames.add("woAction");
			fieldNames.add("serviceTypeName");
			fieldNames.add("serviceTypeCode");
			fieldNames.add("serviceCode");
			fieldNames.add("serviceName");
			fieldNames.add("assignDealerCode");
			fieldNames.add("assignDealer");
			fieldNames.add("postalCodeCode");
			fieldNames.add("stateName");
			fieldNames.add("cityName");
			fieldNames.add("responsableDoc");
			fieldNames.add("responsableName");
			fieldNames.add("helpers");
			fieldNames.add("reasonDesc");
			fieldNames.add("customerPhone");
			fieldNames.add("customerAddress");
			fieldNames.add("contDecos");
			fieldNames.add("previousVisits");
			fieldNames.add("ibsTechnical");
			fieldNames.add("observationCompany");
			fieldNames.add("retiredIRDSeries");
			fieldNames.add("retiredSCSeries");
			fieldNames.add("installedIRDSeries");
			fieldNames.add("installedSCSeries");
			fieldNames.add("dispatcher");
			fieldNames.add("loginDispatcher");
			fieldNames.add("dateFirstScheduling");
			fieldNames.add("firstResponsibleCrewAssign");
			fieldNames.add("codeDepotDealerFirstAssign");
			fieldNames.add("nameDealerFirstAssign");
			Date sysdate = getDate(request.getCountryId(), userDao, systemParameterDAO);
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				List<MonthlyActivityResponseDTO> dataList=new ArrayList<MonthlyActivityResponseDTO>();

	    	    dataList=reportsCoreDAO.getMonthlyActivity(request,sysdate, ri);
	    	    
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile, ApplicationTextEnum.JOURNEY.getApplicationTextValue());
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
			
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void populateJoinMonthlyActivity(ReportsParameterInputDTO request) throws BusinessException{
    	try{
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			List<String> columnNames = new ArrayList<String>();
			columnNames.add(ApplicationTextEnum.WO.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_IBS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_TYPE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SELLER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPOT_SELLER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.HSP_STATUS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.IBS_STATUS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.REASON.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ELEMENT_MODEL.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PUBLICATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ASSIGNATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PROGRAMATION_JOURNEY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PROGRAMATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ATTENTION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.FINALIZATION_DATE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WO_DESCRIPTION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ACTION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.SERVICE_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPOT_INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLER_COMPANY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.POSTAL_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEPARTMENT.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RESPONSABLE_DOCUMENT.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RESPONSABLE_CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.AUXILIAR_CREW.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.LAST_GESTION_REASON.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PHONE_CLIENTE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.CLIENT_ADDRESS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DECOS_QUANTITY.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.PREVIOUS_VISITS.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.TECHNICAL_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.COMPANY_OBSERVATION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RETIRED_IRD_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.RETIRED_SC_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLED_IRD_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.INSTALLED_SC_SERIE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DISPATCHER_USER.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("woCode");
			fieldNames.add("customerCode");
			fieldNames.add("customerName");
			fieldNames.add("customerTypeName");
			fieldNames.add("dealerNameS");
			fieldNames.add("depotCodeS");
			fieldNames.add("woStateName");
			fieldNames.add("ibs6StateName");
			fieldNames.add("reason");
			fieldNames.add("model");
			fieldNames.add("creationDate");
			fieldNames.add("importDate");
			fieldNames.add("assignDealerDate");
			fieldNames.add("jornada");
			fieldNames.add("jornadaDate");
			fieldNames.add("woRealizationDate");
			fieldNames.add("finalizationDate");
			fieldNames.add("woDescription");
			fieldNames.add("woAction");
			fieldNames.add("serviceTypeName");
			fieldNames.add("serviceTypeCode");
			fieldNames.add("serviceCode");
			fieldNames.add("serviceName");
			fieldNames.add("assignDealerCode");
			fieldNames.add("assignDealer");
			fieldNames.add("postalCodeCode");
			fieldNames.add("stateName");
			fieldNames.add("cityName");
			fieldNames.add("responsableDoc");
			fieldNames.add("responsableName");
			fieldNames.add("helpers");
			fieldNames.add("reasonDesc");
			fieldNames.add("customerPhone");
			fieldNames.add("customerAddress");
			fieldNames.add("contDecos");
			fieldNames.add("previousVisits");
			fieldNames.add("ibsTechnical");
			fieldNames.add("observationCompany");
			fieldNames.add("retiredIRDSeries");
			fieldNames.add("retiredSCSeries");
			fieldNames.add("installedIRDSeries");
			fieldNames.add("installedSCSeries");
			fieldNames.add("dispatcher");
			fieldNames.add("loginDispatcher");
			Date sysdate = getDate(request.getCountryId(), userDao, systemParameterDAO);
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				List<MonthlyActivityResponseDTO> dataList=new ArrayList<MonthlyActivityResponseDTO>();

	    	    dataList=reportsCoreDAO.getJoinMonthlyActivity(request,sysdate, ri);
	    	    
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile, ApplicationTextEnum.JOURNEY.getApplicationTextValue());
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
	        SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
			
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    }
    
    //CC54 WO pendientes por falta de materiales
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void pupulateWoPendingLackMaterials(ReportsParameterInputDTO request, String ... serviceTypes) throws BusinessException{
    	try{
    		String reasonCode = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_WORKORDER_REASON_RN03.getCodeEntity(), request.getCountryId()).getValue(); 
			boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			List<String> columnNames = new ArrayList<String>();
			columnNames.add("# "+ApplicationTextEnum.DEPOT_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DEALER_NAME.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.DATE_REJECTION.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.IBS_CLIENT_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.IBS_WO_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.WO_TYPE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.ACTION.getApplicationTextValue()+" ("+ApplicationTextEnum.REASON.getApplicationTextValue()+")");
			columnNames.add(ApplicationTextEnum.OBSERVATION.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("depotCode");
			fieldNames.add("dealerName");
			fieldNames.add("statusDate");
			fieldNames.add("ibsCustomerCode");
			fieldNames.add("ibsWoCode");
			fieldNames.add("woTypeName");
			fieldNames.add("woReasonName");
			fieldNames.add("woDescription");
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				List<WoPendingLackMaterialsDTO> dataList=reportsCoreDAO.getWoPendingLackMaterials(request.getCountryId(), ri, reasonCode);
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile);
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    }

	//REQ002 - WO Agendadas en linea.
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void populateSucceedWorkOrdersCSR(ReportsParameterInputDTO request) throws BusinessException{
    	try{
    		final Long countryId = request.getCountryId();
    		ReportsSucceedWorkOrderFilterDTO filterDTO = new ReportsSucceedWorkOrderFilterDTO();
    		
    		Country country = countryDao.getCountriesByID(request.getCountryId());
    		filterDTO.setCountryCode(country.getCountryCode());
    		filterDTO.setFileName(request.getNameFileResponse());
    		filterDTO.setFromDate(request.getBeginDate());
    		filterDTO.setToDate(request.getEndDate());
    		filterDTO.setFileResponse(true);
    		
    		ReportsSucceedWorkOrderCSRAndFileResponseDTO woAndFileResponseDTO = this.getReportsSucceedWorkOrderCSR(filterDTO, null);
    		
    		SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
    		String fileName =  woAndFileResponseDTO.getFile().getFileName()+formatoFecha.format(request.getDateNow())+ ".xls";
    		String filePath = ExcelGenerator.getReportsPathTemp()+fileName;
    		InputStream inputStream = woAndFileResponseDTO.getFile().getDataHandler().getInputStream();
    		
    		//Se carga para que luego se envie por FTP.
    		FileResponseDTO fileResponseDTO = new FileResponseDTO();
    		fileResponseDTO.setFileName(fileName);
    		fileResponseDTO.setDataHandler(woAndFileResponseDTO.getFile().getDataHandler());
    		request.setFileResponseDTO(fileResponseDTO);
    		
    		UtilsBusiness.createFile(filePath, inputStream);
    		final File temp = new File(filePath);
    	
    		SystemParameter sysParamRecipientsEmail = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_SUCCEED_WO_CSR_REPORT_RECIPIENT_MAIL.getCodeEntity(), countryId);
    		
    		File[] attachments = new File[]{temp};
    		final MailMessage mm = new MailMessage(sysParamRecipientsEmail.getValue(), ApplicationTextEnum.WO_CSR_SUCCESS.getApplicationTextValue(), "", attachments);

    		// Envio asincrono de mails
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						mailSenderLocal.send(mm, countryId, false);
						temp.delete();
					} catch (EmailMessageException email) {
						log.error("== Error al tratar de enviar el mail == ", email);
					}
				}
			};
			new Thread(runnable).start();
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(
			ReportsSucceedWorkOrderFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		log.debug("== Inicia getReportsSucceedWorkOrderCSR/ReportGeneratorBusinessBean ==");
		try{
			//Validación
			UtilsBusiness.assertNotNull(filter.getCountryCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(filter.getFromDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(filter.getToDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			if(filter.getFromDate().after(filter.getToDate())){
	    		log.error("== La fechas Desde/Hasta estan al reves. ==");
	    		throw new BusinessException(ErrorBusinessMessages.VALUE_RANGE_INVALID.getCode(), ErrorBusinessMessages.VALUE_RANGE_INVALID.getMessage());
	    	}
			
			Country country = getCountry(filter.getCountryCode());
			ReportsSucceedWorkOrderCSRAndFileResponseDTO response = new ReportsSucceedWorkOrderCSRAndFileResponseDTO();
			Integer defaultPageSize = Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT_CORE_SUCCEED_WO.getCodeEntity());
			if(!filter.isFileResponse()){
				if(requestInfo==null){
					requestInfo = new RequestCollectionInfo();
					requestInfo.setPageIndex(1);
					requestInfo.setPageSize(defaultPageSize);
				}else{
					if(requestInfo.getPageSize() > defaultPageSize){
		        		Object[] params = new Object[1];
		        		params[0]=defaultPageSize;
		        		throw new BusinessException(ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getCode(), ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getMessage(params));
					}
					if(requestInfo.getPageIndex()<=0 || requestInfo.getPageSize()<=0){
						throw new BusinessException("Los parametros de paginacion son invalidos");
					}
				}
				return workOrderCSRDAO.getReportsSucceedWorkOrderCSR(filter, requestInfo);
				
			}else{
				UtilsBusiness.assertNotNull(filter.getFileName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    		try{
	    			List<ReportsSucceedWorkOrderCSRDTO> wosld = workOrderCSRDAO.getReportsSucceedWorkOrderCSR(filter, requestInfo).getReportsSucceedWorkOrderCSRDTOList();
	    			
	    			String fileName = filter.getFileName();
	    			ByteArrayOutputStream baos = null;
	    			baos = excelGenerator.createExcelStreamWithJasper(wosld, null, null, CodesBusinessEntityEnum.REPORTS_SUCCEED_WORK_ORDER_CSR_JASPER_FILE.getCodeEntity());
	    			
	    			if( baos == null ){
	    				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
	    			}
	    			DataSource ds = new  ByteArrayDataSource( baos.toByteArray() , "application/vnd.ms-excel" );
	    			DataHandler dh = new DataHandler(ds);
		    		FileResponseDTO fileResponse = new FileResponseDTO();
		    		fileResponse.setFileName(fileName);
		    		fileResponse.setDataHandler(dh);
		    		response.setFile(fileResponse);	    			
	    		} catch (Throwable ex) {
	    			log.error("== Error al tratar de ejecutar la operación getReportsSucceedWorkOrderCSR/ReportGeneratorBusinessBean");
	    			throw super.manageException(ex);
	    		} finally {
	    			log.debug("== Termina getReportsSucceedWorkOrderCSR/ReportGeneratorBusinessBean ==");
	    		}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getReportsSucceedWorkOrderCSR/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getReportsSucceedWorkOrderCSR/ReportGeneratorBusinessBean ==");
		}
		
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.core.ReportsCoreBusinessLocal#reportWorkOrderRejection(co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     */
    @Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ReportWorkOrderRejectionAndFileResponseDTO reportWorkOrderRejection( ReportWorkOrderRejectionFilterDTO filter,
                                                                                RequestCollectionInfo requestInfo) throws BusinessException {
		try{
			validReportWorkOrderRejection(filter.getCountryCode());
			Country country =getCountry(filter.getCountryCode());
			
			SystemParameter spWoTypesCodes =  systemParameterDAO.getSysParamByCodeAndCountryCode(CodesBusinessEntityEnum.SYSTEM_PARAM_REPORT_WORK_ORDER_REJECT_FILTER_TYPE_WORK_ORDER.getCodeEntity(), filter.getCountryCode());
			String woTypesCodes = "";
			if(spWoTypesCodes!=null && spWoTypesCodes.getValue()!=null){
				woTypesCodes = spWoTypesCodes.getValue();
			}
			
			//Se obtiene la paginacion para el reporte de rechazos
			int pageSize = Integer.parseInt(CodesBusinessEntityEnum.PAGE_SIZE_REPORT_WORK_ORDER_REJECT.getCodeEntity());
            String woStateCodeReject = CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity();
			
			ReportWorkOrderRejectionAndFileResponseDTO response = new ReportWorkOrderRejectionAndFileResponseDTO();
			if(!filter.isFileResponse()){
				if(requestInfo==null){
					requestInfo = new RequestCollectionInfo();
					requestInfo.setPageIndex(1);
					requestInfo.setPageSize(pageSize);
				}else{
					if(requestInfo.getPageSize()>pageSize){
		        		Object[] params = new Object[1];
		        		params[0]=pageSize;
		        		throw new BusinessException(ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getCode(), ErrorBusinessMessages.QUERY_SIZE_EXCEEDED.getMessage(params));
					}
					if(requestInfo.getPageIndex()<=0 || requestInfo.getPageSize()<=0){
						throw new BusinessException("Los parametros de paginacion son invalidos");
					}
				}
				return reportsCoreDAO.getReportWorkOrderRejection(woTypesCodes,woStateCodeReject,country.getId(),requestInfo);
			}else{
				boolean needOtherCall = true;
				int page = 0;
				String nameFile=null;
				List<String> columnNames = new ArrayList<String>();
				columnNames.add(ApplicationTextEnum.CLIENT_IBS.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.WO_CODE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.STATUS.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.SERVICE_TYPE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.SERIAL_IRD.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.CREATION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DATE_REJECTION.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DEALER.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DEALER_ALLOCATION_DATE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.REASON_REJECTION.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.OBSERVATION.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.NUMBER_DAYS.getApplicationTextValue());
				
				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("customerCode");
				fieldNames.add("woCode");
				fieldNames.add("woStateCode");
				fieldNames.add("serviceCode");
				fieldNames.add("cityName");
				fieldNames.add("serialNumber");
				fieldNames.add("creationDate");
				fieldNames.add("dateUnassignmentDealer");
				fieldNames.add("dealerAssignment");
				fieldNames.add("dateAssignmentDealer");
				fieldNames.add("woReasonName");
			    fieldNames.add("observation");
			    fieldNames.add("countDate");
				 
				while(needOtherCall){
					RequestCollectionInfo ri = new RequestCollectionInfo();
					ri.setPageIndex(page+1);
					ri.setPageSize(pageSize);
					List<ReportWorkOrderRejectionDTO> dataList = reportsCoreDAO.getReportWorkOrderRejection(woTypesCodes,woStateCodeReject,country.getId(),requestInfo).getReportWorkOrderRejectionDTO();
					if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
						needOtherCall = false;
					}
					nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile);
					++page;
				}
				String nameFileResponse = "report"+nameFile;
				String fileType = "";
				if(filter.isCsvFile()){
					nameFileResponse+=".csv";
					fileType = "text/plain";
				}else{
					nameFileResponse+=".zip";
					fileType = "application/zip";
					UtilsBusiness.createZip(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".zip",ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv","report"+nameFile+".csv", filter.getFileName()+".csv");
					File temp = new File(ExcelGenerator.getReportsPathTemp()+"report"+nameFile+".csv");
					temp.delete();
				}
				response.setFile(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, filter.getFileName(), fileType) );
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
	}
    
    /**
     * Metodo: Permite validar los datos de entrada para consultar el reporte de workorder de rechazos
     * @param countryCode
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    private void validReportWorkOrderRejection(String countryCode) throws BusinessException{
    	if(countryCode==null || countryCode.trim().equals("")){
			throw new BusinessException(ErrorBusinessMessages.COUNTRY_REQUIRED.getCode(), ErrorBusinessMessages.COUNTRY_REQUIRED.getMessage());
		}
    }
    //CC54 WO pendientes por falta de materiales
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void populateAuxiliarTechnician(ReportsParameterInputDTO request) throws BusinessException{
    	try{
    		
    		Set<ScheduleReportParameter> scheduleReportParamSet = request.getScheduleReportParameters();
    		//DAO de schedule report. obtener todos los params WOS de el tipo de reporte tecnico aux.	
    		
    		boolean needOtherCall = true;
			int page = 0;
			int pageSize = request.getPageSize();
			String nameFile=null;
			List<String> columnNames = new ArrayList<String>();
			//columnNames.add("Código WO");
			//columnNames.add("Código del Rol del colaborador");
			//columnNames.add("Número de documento del colaborador");
			//columnNames.add("Nombre del colaborador");
			columnNames.add(ApplicationTextEnum.WO_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.COL_ROL_CODE.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.COL_DOC_NUMBER.getApplicationTextValue());
			columnNames.add(ApplicationTextEnum.COL_NAME.getApplicationTextValue());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("woCode");
			fieldNames.add("employeeCode");
			fieldNames.add("employeeDocument");
			fieldNames.add("employeeName");
			
			while(needOtherCall){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(page+1);
				ri.setPageSize(pageSize);
				if(pageSize<=0){
					ri=null;
				}
				List<AuxTechnicianDetailsDTO> dataList=reportsCoreDAO.getAuxiliarTechnicianByDate(request.getCountryId(), ri, request.getBeginDate(),request.getEndDate(), scheduleReportParamSet);
				if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
					needOtherCall = false;
				}
				nameFile=UtilsBusiness.generateCsv(dataList,fieldNames,columnNames,page, nameFile);
				++page;
			}
			String nameFileResponse = "report"+nameFile;
			String fileType = "";
			nameFileResponse+=".csv";
			fileType = "text/plain";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("_yyyy_MM_dd_HH_mm");
			request.setFileResponseDTO(UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, request.getNameFileResponse()+formatoFecha.format(request.getDateNow())+".csv", fileType) );
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
    }
    
}
