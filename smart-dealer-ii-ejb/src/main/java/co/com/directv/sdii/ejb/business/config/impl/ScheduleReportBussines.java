package co.com.directv.sdii.ejb.business.config.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ScheduleReportBussinesLocal;
import co.com.directv.sdii.ejb.business.core.ReportsCoreBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.ReportsStockBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CreateAuxiliarTechnicianReportDTO;
import co.com.directv.sdii.model.dto.CreateScheduleReportDTO;
import co.com.directv.sdii.model.dto.ReportsParameterInputDTO;
import co.com.directv.sdii.model.dto.ScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportFilterDTO;
import co.com.directv.sdii.model.dto.ScheduleReportStatusDTO;
import co.com.directv.sdii.model.dto.ScheduleReportTypeDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.FtpConfiguration;
import co.com.directv.sdii.model.pojo.ScheduleReport;
import co.com.directv.sdii.model.pojo.ScheduleReportParameter;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.ScheduleReportPeriodTypeVO;
import co.com.directv.sdii.model.vo.ScheduleReportTypeVO;
import co.com.directv.sdii.model.vo.ScheduleReportVO;
import co.com.directv.sdii.persistence.dao.config.ScheduleReportDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.file.FtpConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

/**
 * Session Bean implementation class ScheduleReportBussines
 */
@Stateless
public class ScheduleReportBussines extends BusinessBase implements ScheduleReportBussinesLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ScheduleReportBussines.class);
	
	@EJB(name="ReportsCoreBusinessLocal",beanInterface=ReportsCoreBusinessLocal.class)
	private ReportsCoreBusinessLocal reportsCoreBusinessLocal;
	
	//CC053 - HSP Reportes de inventarios.
	@EJB(name="ReportsStockBusinessLocal", beanInterface=ReportsStockBusinessLocal.class)
	private ReportsStockBusinessLocal reportsStockBusinessLocal;
	
	@EJB
	private ScheduleReportDAOLocal scheduleReportDAOLocal;
	
	@EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="FtpConfigurationDAOLocal", beanInterface=FtpConfigurationDAOLocal.class)
    private FtpConfigurationDAOLocal ftpConfigurationDAOLocal;
	
    /**
     * Default constructor. 
     */
    public ScheduleReportBussines() {
    }

	@Override
	public void createScheduleReport(CreateScheduleReportDTO csrDTO)
			throws BusinessException {
        try {
        	log.debug("== Inicio createScheduleReport/ScheduleReportBussines ==");
        	UtilsBusiness.assertNotNull(csrDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontraron los parametros de creacion ");
        	UtilsBusiness.assertNotNull(csrDTO.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontro el parametro del usuario para la creacion ");
        	UtilsBusiness.assertNotNull(csrDTO.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontro el parametro del pais para la creacion ");
        	UtilsBusiness.assertNotNull(csrDTO.getTypeId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontro el parametro del tipo de reporte para la creacion ");
        	UtilsBusiness.assertNotNull(csrDTO.getBeginDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontro el parametro dela fecha de inicio del reporte para la creacion ");
        	UtilsBusiness.assertNotNull(csrDTO.getEndDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontro el parametro dela fecha de finalizacion del reporte para la creacion ");
            UtilsBusiness.assertBeginDateNotLessToDate(csrDTO.getBeginDate(),csrDTO.getEndDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
            		ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());            
            SystemParameter range =systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.CONSTANT_MAX_RANGE_MONTHS_BETWEEN.getCodeEntity(), csrDTO.getCountryId());
            UtilsBusiness.assertMonthsBetween(csrDTO.getBeginDate(), csrDTO.getEndDate(), range.getValue(), 
            		ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getCode(), ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage());
            
        	ScheduleReportVO srvo= new ScheduleReportVO();
        	User user=daoUser.getUserById(csrDTO.getUserId());
        	if(user==null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	Country c = countryDao.getCountriesByID(csrDTO.getCountryId());
        	if(c==null){
        		throw new BusinessException(ErrorBusinessMessages.COUNTRY_NOT_EXIST.getMessage());
        	}
        	
        	//CC053 - HSP Reportes - CRUD Programacion.
        	//verificar que solo se pueda crear 5 reportes por pais
        	Date dateNow=UtilsBusiness.getDateLastChangeOfUser(csrDTO.getCountryId(),daoUser, systemParameterDAO);
        	ScheduleReportPeriodTypeVO periodType = scheduleReportDAOLocal.getScheduleReportPeriodTypeByCode(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_NA.getCodeEntity());
			Long maxReportsByCountry = scheduleReportDAOLocal.getScheduleReportsQuantityByCountryId(csrDTO.getCountryId(), periodType.getId(),dateNow);
			String maxScheduleReports=CodesBusinessEntityEnum.CONSTANT_MAX_SCHEDULE_REPORTS_BY_COUNTRY.getCodeEntity();
			Long maxQuantity = Long.parseLong(maxScheduleReports);
			if(maxReportsByCountry >= maxQuantity){
				throw new BusinessException(ErrorBusinessMessages.MAX_REPORTS_BY_COUNTRY.getMessage());
			}
        	
        	ScheduleReportTypeVO scheduleReportTypeVO = scheduleReportDAOLocal.getScheduleReportTypeId(csrDTO.getTypeId());
        	if(scheduleReportTypeVO==null){
        		throw new BusinessException(ErrorBusinessMessages.REPORT_TYPE_NOT_EXIST.getMessage());
        	}
        	srvo.setCreationDate(dateNow);
        	srvo.setBeginDate(csrDTO.getBeginDate());
        	srvo.setEndDate(csrDTO.getEndDate());
        	srvo.setCountry(c);
        	Calendar cal=Calendar.getInstance();
        	cal.setTimeInMillis(dateNow.getTime());
        	cal.add(Calendar.DAY_OF_MONTH, 1);
        	cal.set(Calendar.MILLISECOND, 0);
        	cal.set(Calendar.SECOND, 0);
        	cal.set(Calendar.MINUTE, 0);
        	SystemParameter sp=systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.CODE_HOUR_OF_REPORTS.getCodeEntity(), csrDTO.getCountryId());
        	Integer param=Integer.parseInt(sp.getValue());
        	if(param<1 || param>22){
        		throw new BusinessException(ErrorBusinessMessages.HOUR_PROGRAM_PARAMETER_NOT_IN_RANGE.getMessage());
        	}
        	cal.set(Calendar.HOUR_OF_DAY, param);
        	srvo.setExecutionDate(cal.getTime());
        	//CC053 - HSP Reportes - CRUD Programacion.
        	srvo.setCode(new Random().nextInt()+"");
        	srvo.setPeriodType(scheduleReportDAOLocal.getScheduleReportPeriodTypeByCode(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_NA.getCodeEntity()));
        	srvo.setReportType(scheduleReportTypeVO);
        	srvo.setStatus(scheduleReportDAOLocal.getScheduleReportStateByCode(CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_PENDING.getCodeEntity()));
        	srvo.setUser(user);
        	srvo.setPeriod(0L);
        	//CC053 - HSP Reportes - CRUD Programacion.
        	srvo.setIncludeLastTime(CodesBusinessEntityEnum.CODE_NOT_INCLUDE_LAST_TIME.getCodeEntity());
        	scheduleReportDAOLocal.createScheduleReport(srvo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createScheduleReport/ScheduleReportBussines ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createScheduleReport/ScheduleReportBussines ==");
        } 
	}

	@Override
	public List<ScheduleReportTypeDTO> getScheduleReportTypes()
			throws BusinessException {
        try {
        	log.debug("== Inicio getScheduleReportTypes/ScheduleReportBussines ==");

        	return scheduleReportDAOLocal.getScheduleReportTypes();
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getScheduleReportTypes/ScheduleReportBussines ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportTypes/ScheduleReportBussines ==");
        }
	}

	//CC053 - HSP Reportes - CRUD Programacion.
	@Override
	public List<ScheduleReportStatusDTO> getScheduleReportStatus() throws BusinessException {
        try {
        	log.debug("== Inicio getScheduleReportStatus/ScheduleReportBussines ==");

        	return scheduleReportDAOLocal.getAllScheduleReportStatus();
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getScheduleReportStatus/ScheduleReportBussines ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportStatus/ScheduleReportBussines ==");
        }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processReports(Long countryId) throws BusinessException {
        try {
        	log.debug("== Inicio getScheduleReportTypes/ScheduleReportBussines ==");

        	Date dateNow=UtilsBusiness.getDateLastChangeOfUser(countryId,daoUser, systemParameterDAO);
        	
        	List<ScheduleReportVO> scheduleReportVOs = scheduleReportDAOLocal.getPendingReportsForThisTime(countryId,dateNow);
        	
        	for(ScheduleReportVO srvo: scheduleReportVOs){
            	
        		Set<ScheduleReportParameter> scheduleReportParamSet = new HashSet<ScheduleReportParameter>(scheduleReportDAOLocal.getScheduleReportParamByScheduleReportId(srvo.getId()));
            	srvo.setScheduleReportParamSet(scheduleReportParamSet);
        		
        		ReportsParameterInputDTO reportsParameterInputDTO=createReportsParameterInputDTO(srvo, dateNow);
        		beginReport(srvo, dateNow);
        		String message="Terminado exitosamente";
        		String newState="";
				//CC054 y REQ002.
        		if(srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_MONTHLY_ACTIVITY.getCodeEntity())
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_JOIN_MONTHLY_ACTIVITY.getCodeEntity())
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_BACKLOG_ACTIVITY.getCodeEntity())
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_ACTIVITY_BACKLOG_DISCONNECTS.getCodeEntity())
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_PRODUCTIVITY_DISPATCHERS.getCodeEntity())
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_REJECTED_RN_03_WORK_ORDERS.getCodeEntity())
						|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_SUCCEED_WORK_ORDERS.getCodeEntity())
						//REQ Proceso de inactivación de técnico (Auditoría sobre los movimientos de cuadrillas)
						|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CORE_AUXILIAR_EMPLOYEE.getCodeEntity())						|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_CREW_MOVEMENTS.getCodeEntity())
						|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_WO_TECHNICIAL.getCodeEntity())){
        			try{
            			reportsCoreBusinessLocal.generateReport(reportsParameterInputDTO);
            			sendFileByFtp(reportsParameterInputDTO);
        			}catch(Throwable ex){
        				message=ex.getMessage();
        				if(ex.getMessage().length()>3950){
        					message=ex.getMessage().substring(0, 3950);
        				}
        	        }
        		
        		//CC053 - HSP Reportes de inventarios.
        		}else if ( srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_QTY_WAREHOUSES_ELEMENTS.getCodeEntity())
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_WAREHOUSES_ELEMENTS_IN_DETAILS.getCodeEntity()) 
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_WAREHOUSES_MOVEMENTS_KARDEX.getCodeEntity()) 
        				|| srvo.getReportType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_TYPE_STOCK_WAREHOUSES_MOVEMENTS.getCodeEntity())){
        			try{
            			reportsStockBusinessLocal.generateReport(reportsParameterInputDTO);
            			sendFileByFtp(reportsParameterInputDTO); 
        			}catch(Throwable ex){
        				message=ex.getMessage();
        				if(ex.getMessage().length()>3950){
        					message=ex.getMessage().substring(0, 3950);
        				}
        	        }
        		}else{
        			log.info("No se identifico el tipo de reporte");
        		}
        		newState=reProgramScheduleReport(srvo, dateNow);
        		newState=finishReport(srvo,newState);
        		scheduleReportDAOLocal.createScheduleReportLog(srvo.getId(), message, newState, dateNow);
        	}        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getScheduleReportTypes/ScheduleReportBussines ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getScheduleReportTypes/ScheduleReportBussines ==");
        }
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void beginReport(ScheduleReportVO srvo, Date dateNow) throws DAOServiceException, DAOSQLException, PropertiesException {
    	srvo.setStatus(scheduleReportDAOLocal.getScheduleReportStateByCode(CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_STARTED.getCodeEntity()));
    	scheduleReportDAOLocal.updateScheduleReport(srvo);
    	scheduleReportDAOLocal.createScheduleReportLog(srvo.getId(), "", CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_STARTED.getCodeEntity(), dateNow);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private String finishReport(ScheduleReportVO srvo, String newState) throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(srvo.getReportType().getReportClass().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_PROCESS_CLASS_USER.getCodeEntity()) 
    			&& srvo.getPeriodType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_NA.getCodeEntity())){
        	srvo.setStatus(scheduleReportDAOLocal.getScheduleReportStateByCode(CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_FINISHED.getCodeEntity()));
        	scheduleReportDAOLocal.updateScheduleReport(srvo);
        	newState=CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_FINISHED.getCodeEntity();
    	}
    	return newState;
    }
    
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void sendFileByFtp(ReportsParameterInputDTO reportsParameterInputDTO) throws PropertiesException, IOException, Throwable {
		List<FtpConfiguration> ftpConfigurations=this.ftpConfigurationDAOLocal.getFtpConfigurationByCountryIdAndCode(reportsParameterInputDTO.getCountryId(), reportsParameterInputDTO.getCodeScheduleReportType());
		
		for(FtpConfiguration ftpc: ftpConfigurations){
		   if(ftpc.getIsSecurity().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
			   UtilsBusiness.sendFileSFtp(reportsParameterInputDTO.getFileResponseDTO().getFileName(), reportsParameterInputDTO.getFileResponseDTO().getDataHandler().getInputStream(),ftpc.getHost(),ftpc.getPort().intValue(),ftpc.getFtpConfigurationUser(),ftpc.getPassword(),ftpc.getPath());
		   }else{
			   UtilsBusiness.sendFileFtp(reportsParameterInputDTO.getFileResponseDTO().getFileName(), reportsParameterInputDTO.getFileResponseDTO().getDataHandler().getInputStream(),ftpc.getHost(),ftpc.getPort().intValue(),ftpc.getFtpConfigurationUser(), ftpc.getPassword(),ftpc.getPath());
		   }
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private ReportsParameterInputDTO createReportsParameterInputDTO(ScheduleReportVO srvo, Date dateNow) throws PropertiesException{
		ReportsParameterInputDTO returnValue = new ReportsParameterInputDTO();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(srvo.getExecutionDate().getTime());
    	Calendar calendarBegin = Calendar.getInstance();
    	calendarBegin.setTimeInMillis(srvo.getExecutionDate().getTime());
    	if(srvo.getPeriodType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_MONTH.getCodeEntity())){
        	calendarBegin.set(Calendar.DAY_OF_MONTH, 1);
        	calendarBegin.set(Calendar.MILLISECOND, 0);
        	calendarBegin.set(Calendar.SECOND, 0);
        	calendarBegin.set(Calendar.MINUTE, 0);
        	calendarBegin.set(Calendar.HOUR_OF_DAY, 0);
        	calendarBegin.add(Calendar.MONTH, (-1)*(srvo.getPeriod().intValue()));
        	returnValue.setBeginDate(calendarBegin.getTime());
    		if(srvo.getIncludeLastTime().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity())){
    			calendar.set(Calendar.DAY_OF_MONTH, 1);
    			calendar.set(Calendar.MILLISECOND, 0);
            	calendar.set(Calendar.SECOND, 0);
            	calendar.set(Calendar.MINUTE, 0);
            	calendar.set(Calendar.HOUR_OF_DAY, 0);
    		}
    		returnValue.setEndDate(calendar.getTime());
    	}else if(srvo.getPeriodType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_DIARY.getCodeEntity())){
        	calendarBegin.set(Calendar.MILLISECOND, 0);
        	calendarBegin.set(Calendar.SECOND, 0);
        	calendarBegin.set(Calendar.MINUTE, 0);
        	calendarBegin.set(Calendar.HOUR_OF_DAY, 0);
        	calendarBegin.add(Calendar.DAY_OF_MONTH, (-1)*(srvo.getPeriod().intValue()));
        	returnValue.setBeginDate(calendarBegin.getTime());
    		if(srvo.getIncludeLastTime().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity())){
    			calendar.set(Calendar.MILLISECOND, 0);
            	calendar.set(Calendar.SECOND, 0);
            	calendar.set(Calendar.MINUTE, 0);
            	calendar.set(Calendar.HOUR_OF_DAY, 0);
    		}
    		returnValue.setEndDate(calendar.getTime());
    	}else{
        	Calendar cBegin = Calendar.getInstance();
        	cBegin.setTime(srvo.getBeginDate());
        	cBegin.set(Calendar.MILLISECOND, 0);
        	cBegin.set(Calendar.SECOND, 0);
        	cBegin.set(Calendar.MINUTE, 0);
        	cBegin.set(Calendar.HOUR_OF_DAY, 0);
        	returnValue.setBeginDate(cBegin.getTime());
        	Calendar c = Calendar.getInstance();
        	c.setTime(srvo.getEndDate());
			c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	//ACM-F-05_HSP_ReportesSC_CC053
        	//c.add(Calendar.DAY_OF_MONTH, 1);
        	returnValue.setEndDate(c.getTime());
    	}
    	
    	returnValue.setScheduleReportParameters(srvo.getScheduleReportParamSet());
    	returnValue.setCodeScheduleReport(srvo.getCode());
    	returnValue.setCodeScheduleReportType(srvo.getReportType().getCode());
    	returnValue.setIdScheduleReport(srvo.getId());
    	returnValue.setCountryId(srvo.getCountry().getId());
    	returnValue.setFileResponseDTO(new FileResponseDTO());
    	returnValue.setDateNow(dateNow);
    	returnValue.setNameFileResponse(srvo.getReportType().getPrefixFile());
    	returnValue.setPageSize(srvo.getReportType().getPageSize().intValue());
    	return returnValue;
    }
    
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private String reProgramScheduleReport(ScheduleReportVO srvo, Date dateNow) throws BusinessException{
    	try{
    		Calendar calendar=Calendar.getInstance();
    		calendar.setTimeInMillis(srvo.getExecutionDate().getTime());
    		Calendar calendarNow = Calendar.getInstance();
    		calendarNow.setTimeInMillis(dateNow.getTime());
    		boolean found=false;
    		String returnValue="";
    		//if(srvo.getReportType().getReportClass().equalsIgnoreCase(CodesBusinessEntityEnum.REPORT_PROCESS_CLASS_SYSTEM.getCodeEntity())){
    			if(srvo.getPeriodType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_MONTH.getCodeEntity())){
    				boolean add=true;
    				while(add){
    					calendar.add(Calendar.MONTH, 1);
    					if(calendar.after(calendarNow)){
    						add=false;
    					}
    				}
    				found=true;
    			}else if(srvo.getPeriodType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_DIARY.getCodeEntity())){
    				boolean add=true;
    				while(add){
    					calendar.add(Calendar.DAY_OF_MONTH, 1);
    					if(calendar.after(calendarNow)){
    						add=false;
    					}
    				}
    				found=true;
    			}else{
    				log.info("No se identifico el tipo de periodicidad");
    			}
    		//}
    		if(found){
    			srvo.setExecutionDate(calendar.getTime());
    			srvo.setStatus(scheduleReportDAOLocal.getScheduleReportStateByCode(CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_PENDING.getCodeEntity()));
    			scheduleReportDAOLocal.updateScheduleReport(srvo);
    			returnValue=CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_PENDING.getCodeEntity();
    		}
    		return returnValue;
    	} catch(Throwable ex){
    		log.error("== Error al tratar de ejecutar la operación getScheduleReportTypes/ScheduleReportBussines ==");
    		throw this.manageException(ex);
    	} finally {
    		log.debug("== Termina getScheduleReportTypes/ScheduleReportBussines ==");
    	}
    }

	//CC053 - HSP Reportes - CRUD Programacion.
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ScheduleReportDTO> getScheduleReports(ScheduleReportFilterDTO filter)
			throws BusinessException {
		try{
			log.debug("== Inicio getScheduleReportByCountryId/ScheduleReportBussines ==");
			
			UtilsBusiness.assertNotNull(filter.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontraron los parametros de pais ");
			UtilsBusiness.assertNotNull(filter.getStatusId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontraron los parametros de status ");
			UtilsBusiness.assertNotNull(filter.getTypeId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontraron los parametros de tipo ");
			
			
			ScheduleReportPeriodTypeVO periodType = scheduleReportDAOLocal.getScheduleReportPeriodTypeByCode(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_NA.getCodeEntity());
			
			List<ScheduleReportDTO> returnValue = scheduleReportDAOLocal.getScheduleReports(filter, periodType.getId());
						
			return returnValue;
		}catch(Throwable ex){
    		log.error("== Error al tratar de ejecutar la operación getScheduleReportTypes/ScheduleReportBussines ==");
    		throw this.manageException(ex);
		}
		finally{
			log.debug("== Termina getScheduleReportByCountryId/ScheduleReportBussines ==");
		}
				
	}

	@Override
	public void deleteScheduleReport(Long id) throws BusinessException {
		try {
			log.debug("== Inicio deleteScheduleReport/ScheduleReportBussines ==");
			
			ScheduleReport scheduleReport = scheduleReportDAOLocal.getScheduleReportById(id);
			scheduleReportDAOLocal.deleteScheduleReportLog(id);
			scheduleReportDAOLocal.deleteScheduleReport(scheduleReport);
			
		}catch (Throwable ex){
    		log.error("== Error al tratar de ejecutar la operación getScheduleReportTypes/ScheduleReportBussines ==");
    		throw this.manageException(ex);
		}
		finally{
			log.debug("== Termina deleteScheduleReport/ScheduleReportBussines ==");
		}
	}

	@Override
	public void createAuxiliarTechnicianReport(CreateAuxiliarTechnicianReportDTO filter) throws BusinessException {
		try {
			log.debug("== Inicio createAuxiliarTechnicianReport/ScheduleReportBussines ==");
			
        	UtilsBusiness.assertNotNull(filter, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontraron los parametros de creacion ");
        	UtilsBusiness.assertNotNull(filter.getCountryCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontró el CountryCode ");
        	UtilsBusiness.assertNotNull(filter.getBeginDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontró el beginDate ");
        	UtilsBusiness.assertNotNull(filter.getEndDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontró el endDate ");
        	UtilsBusiness.assertNotNull(filter.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), 
        			ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontró el userId ");
			
			ScheduleReport sr = new ScheduleReport();
			
			ScheduleReportTypeVO scheduleReportTypeVO = scheduleReportDAOLocal.getScheduleReportTypeByCode(CodesBusinessEntityEnum.REPORT_TYPE_CORE_AUXILIAR_EMPLOYEE.getCodeEntity());
			
			sr.setBeginDate(filter.getBeginDate());
			sr.setEndDate(filter.getEndDate());
			sr.setReportType(scheduleReportTypeVO);
			sr.setCode(new Random().nextInt()+"");
			
			//Codes Busines sdii_blablabla = WOS (WO Status)
			/* ID FK  CD  
			 * 1   1 WOS    R
			 * 2   1 WOS    T
			 * 3   1 PEP    1230815
			 * 
			 * MODIFICAR LA QUERY PARA QUE TMB FILTRE POR WOS
			 * */
			Country country = countryDao.getCountriesByCode(filter.getCountryCode());
			
			User user=daoUser.getUserById(filter.getUserId());
			sr.setUser(user);
			
			sr.setCountry(country);
        	sr.setPeriodType(scheduleReportDAOLocal.getScheduleReportPeriodTypeByCode(CodesBusinessEntityEnum.REPORTS_PROCESS_PERIOD_TYPE_NA.getCodeEntity()));
        	sr.setPeriod(0L);
        	sr.setStatus(scheduleReportDAOLocal.getScheduleReportStateByCode(CodesBusinessEntityEnum.CODE_REPORTS_PROCESS_PENDING.getCodeEntity()));
        	
        	Date dateNow=UtilsBusiness.getDateLastChangeOfUser(country.getId(),daoUser, systemParameterDAO);
        	sr.setCreationDate(dateNow);
        	
        	
        	sr.setReportType(scheduleReportDAOLocal.getScheduleReportTypeByCode(CodesBusinessEntityEnum.REPORT_TYPE_CORE_AUXILIAR_EMPLOYEE.getCodeEntity()));
        	
        	sr.setIncludeLastTime(CodesBusinessEntityEnum.CODE_NOT_INCLUDE_LAST_TIME.getCodeEntity());
        	
        	Calendar cal=Calendar.getInstance();
        	cal.setTimeInMillis(dateNow.getTime());
        	cal.add(Calendar.DAY_OF_MONTH, 1);
        	cal.set(Calendar.MILLISECOND, 0);
        	cal.set(Calendar.SECOND, 0);
        	cal.set(Calendar.MINUTE, 0);
        	SystemParameter sp=systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.CODE_HOUR_OF_REPORTS.getCodeEntity(), country.getId());
        	Integer param=Integer.parseInt(sp.getValue());
        	if(param<1 || param>22){
        		throw new BusinessException(ErrorBusinessMessages.HOUR_PROGRAM_PARAMETER_NOT_IN_RANGE.getMessage());
        	}
        	cal.set(Calendar.HOUR_OF_DAY, param);
        	sr.setExecutionDate(cal.getTime());
        				
			//Llamar al dao createScheduleReport
        	
			scheduleReportDAOLocal.createScheduleReport(sr);
			
			for(WorkorderStatus woStatus : filter.getWoStatusList()){
				ScheduleReportParameter scheduleReportParam = new ScheduleReportParameter();
				scheduleReportParam.setValue(woStatus.getWoStateCode());
				scheduleReportParam.setCode(CodesBusinessEntityEnum.REPORT_PARAMETER_WO_STATUS.getCodeEntity());
				scheduleReportParam.setScheduleReport(sr);
				scheduleReportDAOLocal.createScheduleReportParameter(scheduleReportParam);
			}
			
		}catch (Throwable ex){
    		log.error("== Error al tratar de ejecutar la operación getScheduleReportTypes/ScheduleReportBussines ==");
    		throw this.manageException(ex);
		}
		finally{
			log.debug("== Termina createAuxiliarTechnicianReport/ScheduleReportBussines ==");
		}
	}
	
}
