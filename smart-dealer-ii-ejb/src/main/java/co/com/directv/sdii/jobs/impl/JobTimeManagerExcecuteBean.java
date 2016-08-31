package co.com.directv.sdii.jobs.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.JmsLocationsEnum;
import co.com.directv.sdii.common.enumerations.WorkManagerConfigEnum;
import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.jobs.JobTimeManagerExcecuteBeanLocal;
import co.com.directv.sdii.jobs.JobTimeManagerExcecuteBeanRemote;
import co.com.directv.sdii.jobs.jarind.ManagerTimer;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;

import commonj.timers.TimerManager;

/**
 * Session Bean implementation class JobTimeManagerExcecute
 */
@Stateless(name="JobTimeManagerExcecute")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
@Local({JobTimeManagerExcecuteBeanLocal.class})
@Remote({JobTimeManagerExcecuteBeanRemote.class})
public class JobTimeManagerExcecuteBean  extends BusinessBase implements JobTimeManagerExcecuteBeanLocal,JobTimeManagerExcecuteBeanRemote {

    /**
     * Default constructor. 
     */
    public JobTimeManagerExcecuteBean() {
        
    }

	private final static Logger logger = UtilsBusiness.getLog4J(JobTimeManagerExcecuteBean.class);

	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

	private synchronized void runTimeManagerMethod() throws NamingException, PropertiesException {
		commonj.timers.TimerListener timerListener;
		
		Long timeManager = UtilsBusiness.readTimeProperties(UtilsBusiness.TIME_MANAGER,30000L);
		if(timeManager == null || timeManager < 0){
			logger.warn("JobTimeManager deshabilitado");
			return;
		}
		
		logger.info("Inicia JobTimeManager");
		Context ctx= new InitialContext();
		String jndiAllocator = WorkManagerConfigEnum.JNDI_TIME_ALLOCATOR.getCodeEntity();	
		TimerManager timerManager = (TimerManager)ctx.lookup(jndiAllocator);
		if(timerManager != null){
			logger.info("SI TENGO UNA INSTANCIA DE TimerManager");
		}else{
			logger.info("NO TENGO UNA INSTANCIA DE TimerManager");
		}
		
		try {
			SystemParameter createdJobsParameter = systemParameterDAO.getSysParamsByCodeAndCountryIdNull(CodesBusinessEntityEnum.SYSTEM_PARAM_MAIL_CREATED_JOBS.getCodeEntity());
			if(createdJobsParameter!=null){
				logger.info("Existe el parametro de jobs");
				if(createdJobsParameter.getValue().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity())){
					logger.info("Se creara el nuevo managerTimer");
					String jndiQueue = JmsLocationsEnum.TIME_MANAGER_JMS.getJmsQueueName();
			    	String jndiQueueConnectionFactory = JmsLocationsEnum.TIME_MANAGER_JMS.getJmsQueueConnectionFactoryName();
					timerListener = new ManagerTimer(jndiQueue, jndiQueueConnectionFactory,PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_LOG4J));
					timerManager.schedule(timerListener, 0, timeManager);
					logger.info("timeManager = "+timeManager);
					createdJobsParameter.setValue(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
					systemParameterDAO.updateSystemParameter(createdJobsParameter);
				}else{
					logger.info("El primer parametro NO esta en N, esta en "+ createdJobsParameter.getValue());
				}
			}else{
				logger.info("No existe el parametro de jobs");
			}

		} catch (DAOServiceException e) {
			e.printStackTrace();
		} catch (DAOSQLException e) {
			e.printStackTrace();
		}
		logger.info("Termina JobTimeManager");		
	}

	private synchronized void runTimeManagerMethodNoCluster(TimerManager timerManager) throws NamingException, PropertiesException {
		commonj.timers.TimerListener timerListener=null;
		Long timeManager = UtilsBusiness.readTimeProperties(UtilsBusiness.TIME_MANAGER,30000L);
		if(timeManager == null || timeManager < 0 ){
			logger.warn("JobTimeManager deshabilitado");
			return;
		}		
		
		logger.info("Inicia JobTimeManager");		
		String jndiQueue = JmsLocationsEnum.TIME_MANAGER_JMS.getJmsQueueName();
		String jndiQueueConnectionFactory = JmsLocationsEnum.TIME_MANAGER_JMS.getJmsQueueConnectionFactoryName();
		timerListener = new ManagerTimer(jndiQueue, jndiQueueConnectionFactory,PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_LOG4J));
		timerManager.schedule(timerListener, 0, timeManager);
		logger.info("timeManager = "+timeManager);
		logger.info("Termina JobTimeManager");		
		
	}

	
	@Override
	public void runTimeManager() throws BusinessException {
		
		logger.info("== Inicia runTimeManager/JobTimeManagerExcecute ==");
		try {
			runTimeManagerMethod();
		} catch(Throwable ex){
			logger.error("== Error al tratar de ejecutar la operación runTimeManager/JobTimeManagerExcecute ==", ex);
        	throw this.manageException(ex);
        } finally {
        	logger.info("== Termina runTimeManager/JobTimeManagerExcecute ==");
        }
			
	}
	
	@Override
	public void runTimeManagerNoCluster(TimerManager timerManager) throws BusinessException {
		
		logger.info("== Inicia runTimeManager/JobTimeManagerExcecute ==");
		try {
			SystemParameter parameter = systemParameterDAO.getSysParamsByCodeAndCountryIdNull(CodesBusinessEntityEnum.SYSTEM_PARAM_IS_IN_CLUSTER.getCodeEntity());
			if(parameter!=null){
				String isCluster = parameter.getValue();
				if(isCluster.equals(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()))
					runTimeManagerMethodNoCluster(timerManager);
			}
		} catch(Throwable ex){
			logger.error("== Error al tratar de ejecutar la operación runTimeManager/JobTimeManagerExcecute ==", ex);
        	throw this.manageException(ex);
        } finally {
        	logger.info("== Termina runTimeManager/JobTimeManagerExcecute ==");
        }
			
	}
}
