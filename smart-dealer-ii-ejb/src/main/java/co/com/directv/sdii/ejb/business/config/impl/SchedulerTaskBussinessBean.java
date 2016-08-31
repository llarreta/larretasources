package co.com.directv.sdii.ejb.business.config.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.SchedulerTaskBussinessLocal;
import co.com.directv.sdii.ejb.business.config.SchedulerTaskBussinessRemote;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.jobs.work.AllocatorWork;
import co.com.directv.sdii.jobs.work.CommandIBSWork;
import co.com.directv.sdii.jobs.work.CoreWoWork;
import co.com.directv.sdii.jobs.work.DealerBuildingWork;
import co.com.directv.sdii.jobs.work.DownloadWorkOrderContactWork;
import co.com.directv.sdii.jobs.work.FileProcessorWork;
import co.com.directv.sdii.jobs.work.KpisWork;
import co.com.directv.sdii.jobs.work.ParallelProcessWork;
import co.com.directv.sdii.jobs.work.ReportMailCoreAllocatorWork;
import co.com.directv.sdii.jobs.work.ReportWorkOrderWork;
import co.com.directv.sdii.jobs.work.ScheduleReportWork;
import co.com.directv.sdii.jobs.work.WorkFinisher;
import co.com.directv.sdii.model.dto.SchedulerTaskDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.SchedulerTask;
import co.com.directv.sdii.model.pojo.SchedulerTaskDetails;
import co.com.directv.sdii.model.pojo.SchedulerTaskDetailsStatus;
import co.com.directv.sdii.model.pojo.SchedulerTaskStatus;
import co.com.directv.sdii.model.vo.SchedulerTaskVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.schedule.SchedulerTaskDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

import commonj.work.Work;
import commonj.work.WorkManager;

/**
 * Session Bean implementation class SchedulerTaskBussiness
 */
@Stateless(name="SchedulerTaskBussinessRemote")
@Local({SchedulerTaskBussinessLocal.class})
@Remote({SchedulerTaskBussinessRemote.class})
public class SchedulerTaskBussinessBean extends BusinessBase implements SchedulerTaskBussinessLocal, SchedulerTaskBussinessRemote {

    @EJB(name="SchedulerTaskDAOLocal",beanInterface=SchedulerTaskDAOLocal.class)
    private SchedulerTaskDAOLocal daoSchedulerTask;
    
    @EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
    private CountriesDAOLocal daoCountries;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAOLocal;
    
    private final static Logger log = UtilsBusiness.getLog4J(SchedulerTaskBussinessBean.class);
	
    /**
     * Default constructor. 
     */
    public SchedulerTaskBussinessBean() {
        
    }

	@Override
	public void createSchedulerTask(SchedulerTaskVO obj)
			throws BusinessException {
        log.debug("== Inicia createSchedulerTask/SchedulerTaskBussinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró el parametro");
        	daoSchedulerTask.createSchedulerTask(new SchedulerTask(obj));
        }catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion createSchedulerTask/SchedulerTaskBussinessBean");
            throw super.manageException(ex);
		}finally{
			log.debug("== Termina createSchedulerTask/SchedulerTaskBussinessBean ==");
		}
		
	}

	@Override
	public void deleteSchedulerTask(SchedulerTaskVO obj)
			throws BusinessException {
        log.debug("== Inicia deleteSchedulerTask/SchedulerTaskBussinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró el parametro");
        	daoSchedulerTask.deleteSchedulerTask(new SchedulerTask(obj));
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion deleteSchedulerTask/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina deleteSchedulerTask/SchedulerTaskBussinessBean ==");
		}
	}

	@Override
	public List<SchedulerTask> getAll() throws  BusinessException {
		log.debug("== Inicia getAll/SchedulerTaskBussinessBean ==");
		List<SchedulerTask> retorno = null;
		try{
			retorno = daoSchedulerTask.getAllSchedulerTask();
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getAll/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getAll/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}

	@Override
	public List<SchedulerTask> getAllSchedulerTaskByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllSchedulerTaskByCountryId/SchedulerTaskBussinessBean ==");
		List<SchedulerTask> retorno = null;
		try{
			retorno = daoSchedulerTask.getAllSchedulerTaskByCountryId(countryId);
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getAllSchedulerTaskByCountryId/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getAllSchedulerTaskByCountryId/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}

	@Override
	public List<SchedulerTask> getSchedulerTaskByCode(String parameterCode)
			throws BusinessException {
		log.debug("== Inicia getSchedulerTaskByCode/SchedulerTaskBussinessBean ==");
		List<SchedulerTask> retorno = null;
		try{
			UtilsBusiness.assertNotNull(parameterCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró codigo de parametro");
			retorno=daoSchedulerTask.getSchedulerTaskByCode(parameterCode);
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getSchedulerTaskByCode/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getSchedulerTaskByCode/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}

	@Override
	public SchedulerTaskVO getSchedulerTaskByCodeAndCountryId(String name,
			Long countryId) throws BusinessException {
		log.debug("== Inicia getSchedulerTaskByCodeAndCountryId/SchedulerTaskBussinessBean ==");
		SchedulerTaskVO retorno = null;
		try{
			UtilsBusiness.assertNotNull(name, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró codigo de parametro");
			retorno=new SchedulerTaskVO(daoSchedulerTask.getSchedulerTaskByCodeAndCountryId(name, countryId));
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getSchedulerTaskByCodeAndCountryId/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getSchedulerTaskByCodeAndCountryId/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}

	@Override
	public SchedulerTaskVO getSchedulerTaskByID(Long id) throws BusinessException {
		log.debug("== Inicia getSchedulerTaskByCodeAndCountryId/SchedulerTaskBussinessBean ==");
		SchedulerTaskVO retorno = null;
		try{
			UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró id de parametro");
			retorno=new SchedulerTaskVO(daoSchedulerTask.getSchedulerTaskByID(id));
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getSchedulerTaskByCodeAndCountryId/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getSchedulerTaskByCodeAndCountryId/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}

	@Override
	public void updateSchedulerTask(SchedulerTaskVO obj) throws BusinessException {
		log.debug("== Inicia updateSchedulerTask/SchedulerTaskBussinessBean ==");
		try{
			UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró el parametro");
			daoSchedulerTask.updateSchedulerTask(new SchedulerTask(obj));
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion updateSchedulerTask/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina updateSchedulerTask/SchedulerTaskBussinessBean ==");
		}
		
	}
	
	@Override
	public void updateSchedulerTask(SchedulerTaskDTO obj) throws BusinessException {
		log.debug("== Inicia updateSchedulerTask/SchedulerTaskBussinessBean ==");
		try{
			UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró el parametro");
			SchedulerTaskStatus tps = getSchedulerTaskStatusByCode(obj.getSchedulerTaskStatusCode());
			Country country = daoCountries.getCountriesByCode(obj.getCountryCode());
			SchedulerTaskVO tp = getSchedulerTaskByCodeAndCountryId(obj.getSchedulerTaskTypesCode(), country.getId());
			SchedulerTaskVO tpVO = new SchedulerTaskVO();
			tpVO.setId(tp.getId());
			tpVO.setCountry(tp.getCountry());
			tpVO.setSchedulerTaskTypes(tp.getSchedulerTaskTypes());
			tpVO.setDescription(tp.getDescription());
			tpVO.setNextExecutionDate(obj.getNextExecutionDate());
			tpVO.setPeriod(obj.getPeriod());
			tpVO.setSchedulerTaskStatus(tps);
			updateSchedulerTask(tpVO);
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion updateSchedulerTask/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina updateSchedulerTask/SchedulerTaskBussinessBean ==");
		}
		
	}

	@Override
	public List<SchedulerTask> getSchedulerTaskByStateAndId(String state, Long id) throws BusinessException {
		log.debug("== Inicia getSchedulerTaskByStateAndId/SchedulerTaskBussinessBean ==");
		List<SchedulerTask> retorno = null;
		try{
			UtilsBusiness.assertNotNull(state, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró estado de parametro");
			retorno=daoSchedulerTask.getSchedulerTaskForExecute(state,id);  // viene aca en 2do lugar
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getSchedulerTaskByStateAndId/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getSchedulerTaskByStateAndId/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}
	
	@Override
	public List<SchedulerTaskVO> getSchedulerTaskVOByStateAndId(String state,Long id) throws BusinessException {
		log.debug("== Inicia getSchedulerTaskByStateAndId/SchedulerTaskBussinessBean ==");
		List<SchedulerTaskVO> retorno = null;
		try{
			List<SchedulerTask> returnBussiness = getSchedulerTaskByStateAndId(state,id);
			retorno = new ArrayList<SchedulerTaskVO>();
			for(SchedulerTask tp: returnBussiness){
				retorno.add(new SchedulerTaskVO(tp));
			}
			return retorno;
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getSchedulerTaskByStateAndId/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getSchedulerTaskByStateAndId/SchedulerTaskBussinessBean ==");
		}
	}

	@Override
	public SchedulerTaskStatus getSchedulerTaskStatusByCode(String statusCode)
			throws BusinessException {
		log.debug("== Inicia getStateByCode/SchedulerTaskBussinessBean ==");
		SchedulerTaskStatus retorno = null;
		try{
			UtilsBusiness.assertNotNull(statusCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró estado de parametro");
			retorno=daoSchedulerTask.getSchedulerTaskStatusByCode(statusCode);
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getStateByCode/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getStateByCode/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}

	@Override
	public SchedulerTaskDetailsStatus getSchedulerTaskDetailStatusByCode(String statusCode)
			throws BusinessException {
		log.debug("== Inicia getStateByCode/SchedulerTaskBussinessBean ==");
		SchedulerTaskDetailsStatus retorno = null;
		try{
			UtilsBusiness.assertNotNull(statusCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró estado de parametro");
			retorno=daoSchedulerTask.getSchedulerTaskDetailStatusByCode(statusCode);
	    }catch (Throwable ex) {
	    	log.error("== Error al tratar de ejecutar la operacion getStateByCode/SchedulerTaskBussinessBean");
	        throw super.manageException(ex);
		}finally{
			log.debug("== Termina getStateByCode/SchedulerTaskBussinessBean ==");
		}
		return retorno;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long beginWork(Long idSchedulerTask, Long countryId) throws BusinessException, PropertiesException {
		try {
			SchedulerTaskDetails newDetail= new SchedulerTaskDetails(); 
			newDetail.setSchedulerTask(new SchedulerTask());
			newDetail.getSchedulerTask().setId(idSchedulerTask);
			newDetail.setInitDate(co.com.directv.sdii.common.util.UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAOLocal));
			newDetail.setSchedulerTaskDetailsStatus(getSchedulerTaskDetailStatusByCode(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_DETAIL_NO_FINISHED_PROCCESS.getCodeEntity()));
			daoSchedulerTask.createSchedulerTaskDetail(newDetail);
			return newDetail.getId();
		} catch (Throwable e) {
	    	log.error("== Error al tratar de ejecutar la operacion updateSchedulerTask/SchedulerTaskBussinessBean", e);
	        throw super.manageException(e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void finishWork(Long idSchedulerTaskDetail, String detail, boolean fail) throws BusinessException, PropertiesException {
		try {
			Long idSchedulerTask = daoSchedulerTask.finishSchedulerTaskDetail(idSchedulerTaskDetail, detail, fail);
			SchedulerTask st = daoSchedulerTask.getSchedulerTaskByID(idSchedulerTask);
			Calendar cal = Calendar.getInstance();
			cal.setTime(st.getNextExecutionDate());
			cal.add(Calendar.SECOND, st.getPeriod().intValue());
			Calendar calNow = Calendar.getInstance();
			calNow.setTime(co.com.directv.sdii.common.util.UtilsBusiness.getDateLastChangeOfUser(st.getCountry().getId(), userDao, systemParameterDAOLocal));
			while(calNow.after(cal)){
				cal.add(Calendar.SECOND, st.getPeriod().intValue());
			}
			st.setNextExecutionDate(cal.getTime());
			daoSchedulerTask.updateSchedulerTask(st);
		} catch (Throwable e) {
	    	log.error("== Error al tratar de ejecutar la operacion updateSchedulerTask/SchedulerTaskBussinessBean", e);
	        throw super.manageException(e);
		}
	}
	
	/**
	 * Metodo encargado de lanzar un hilo de ejecucion para cada uno de las tareas programadas encontradas
	 * @param workManager referencia al workManager de weblogic
	 * @param schedulerTask tareas programadas encontradas
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author Aharker
	 */
	@Override
	public void doWorkManager(WorkManager workManager, List<SchedulerTask> schedulerTask) throws BusinessException {//aca ya tiene las tareas en 
		log.info("Create ejecución del Job de Allocator, se tienen "+schedulerTask.size()+" tareas pendientes ");
		try {
			log.debug("Inicia JobWorkManager");
    		for(SchedulerTask tp: schedulerTask){
    			Work work = null;
    			boolean processBegin=false;
    			try{
    				log.info("tp.getSchedulerTaskTypes().getCode() = "+tp.getSchedulerTaskTypes().getCode());
    				
					if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_ALLOCATOR.getCodeEntity())){
						//proceso de asignador
						work = new AllocatorWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_KPI.getCodeEntity())){
						//proceso de KPI
						work = new KpisWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_FILE_PROCESS.getCodeEntity())){
						//proceso de procesamiento de archivos
						work = new FileProcessorWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_COMMAND_IBS.getCodeEntity())){
						//proceso de comandos de IBS
						work = new CommandIBSWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_REPORT_WORKORDER.getCodeEntity())){
						//proceso de reporte de work orders del dia anterior
						work = new ReportWorkOrderWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_CORE.getCodeEntity())){
						//proceso de core
						work = new CoreWoWork(tp.getCountry().getId(),tp.getId(),tp.getCountry().getCountryCode());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_PARALLEL_PROCESSING_CORE_ALLOCATOR_REPORT.getCodeEntity())){
						//proceso de reporte de work orders del dia anterior
						work = new ReportMailCoreAllocatorWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_PARALLEL_PROCESSING_CORE_ALLOCATOR.getCodeEntity())){
						//proceso de reporte de work orders del dia anterior
						work = new ParallelProcessWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_REPORTS_HSP.getCodeEntity())){
						//proceso de reportes de HSP+
						work = new ScheduleReportWork(tp.getCountry().getId(),tp.getId());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_DOWNLOAD_CUSTOMER_CONTACT.getCodeEntity())){
						//proceso de descargas de contacts de ESB
						work = new DownloadWorkOrderContactWork(tp.getCountry().getId(),tp.getId(),tp.getNextExecutionDate());
					}else if(tp.getSchedulerTaskTypes().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_CODE_UPDATE_DEALER_BUILDING.getCodeEntity())){
						//proceso de actualizacion de relacion dealer building.
						work = new DealerBuildingWork(tp.getCountry().getId(),tp.getId());
					}else{
						log.info("No se encontro el tipo de Work.");
					}
					if(work != null && work instanceof WorkFinisher){
						WorkFinisher wf = (WorkFinisher) work;
						wf.beginWork();
						processBegin=true;
						workManager.schedule(work);
					}
    			}catch(Exception ex){
    				if(processBegin){
	    				WorkFinisher wf = (WorkFinisher) work;
	    				wf.finishWork("", true);
    				}
    				log.error("Ocurrio un error en doWorkManager / SchedulerTaskBussinessBean -- "+ex);
    				ex.printStackTrace();
    			}
    		}
    		log.info("Termina JobWorkManager");
		}catch (Throwable e) {
			log.error("Ocurrio un error en doWorkManager / SchedulerTaskBussinessBean -- "+e);
			e.printStackTrace();
			super.manageException(e);
		}
	}
	
}
