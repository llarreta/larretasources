/**
 * 
 */
package co.com.directv.sdii.jobs;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.common.enumerations.WorkManagerConfigEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.exceptions.BusinessException;

import commonj.timers.TimerManager;


/**
 * Esta clase ejecuta el Hilo para WorkManager
 * @author cduarte, aharker
 *
 */
public class JobTimeManager  extends BusinessBase {

	private final static Logger logger = UtilsBusiness.getLog4J(JobTimeManager.class);

	public void runTimeManager() throws BusinessException {
		
		logger.info("== Inicia runTimeManager/JobTimeManager ==");
		try {
			Context ctx= new InitialContext();
			String jndiAllocator = WorkManagerConfigEnum.JNDI_TIME_ALLOCATOR.getCodeEntity();	
			//TimerManager timerManager =(TimerManager)ctx.lookup("weblogic.JobScheduler");
			TimerManager timerManager = (TimerManager)ctx.lookup(jndiAllocator);
			if(timerManager != null){
				logger.info("SI TENGO UNA INSTANCIA DE TimerManager");
			}else{
				logger.info("NO TENGO UNA INSTANCIA DE TimerManager");
			}
			logger.info("Se creara el nuevo managerTimer");
			
			AssignmentFacadeLocator.getInstance().getJobTimeManagerExcecute().runTimeManagerNoCluster(timerManager);
			
		} catch(Throwable ex){
			logger.error("== Error al tratar de ejecutar la operación runTimeManager/JobTimeManager ==", ex);
        } finally {
        	logger.info("== Termina runTimeManager/JobTimeManager ==");
        }
		
		
	}

}