
/**
 * Creado 10/06/2010 17:10:01
 */
package co.com.directv.sdii.job.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.kpi.job.KPIResultPersistenceManager;
import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.jobs.SchedulerFactoryProxy;

/**
 * Listener del contexto de la aplicación web que es ejecutado
 * cuando el contexto se inicializa y se destruye
 * 
 * Fecha de Creación: 10/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class SchedulerInitializerContextListener implements
		ServletContextListener {

	private final static Logger logger = UtilsBusiness.getLog4J(SchedulerInitializerContextListener.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvt) {
		SchedulerFactoryProxy.getInstance().stopScheduler();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvt) {
		try {
			
			PropertiesReader reader = UtilsBusiness.getMessageProperties(Constantes.LABEL_RUTA_JOBS_CONFIG);
			
			List<String> keys = reader.getKeys();
			String cronExpression;
			Map<String, String> jobMap = new HashMap<String, String>();
			for (String className : keys) {
				cronExpression = reader.getKey(className);
				jobMap.put(className, cronExpression);
			}
			
			Set<String> keySet = jobMap.keySet();
			
			for (String className : keySet) {
				cronExpression = jobMap.get(className);
				logger.debug("Se creará el siguiente job: " + className + " cron: " + cronExpression);
				try {
					SchedulerFactoryProxy.getInstance().scheduleJob(className, className, cronExpression);
				}catch (Exception e) {	}
			}
			
			SchedulerFactoryProxy.getInstance().startScheduler();
			
			//inicializa el job de cálculo de los KPI de los dealers
			//KPIResultPersistenceManager.getInstance().updateDealerIndicatorCalculationJob();
			//initializeKpiJob();
		} catch (Exception e) {
			logger.error("Al tratar de inciializar los Jobs: " + e.getMessage(), e);
		}
	}

//	private void initializeKpiJob() throws BusinessException {
//		AssignmentFacadeRemote facadeRemote = AssignmentFacadeLocator.getInstance().getAssignmetFacade();
//		facadeRemote.updateDealerIndicatorCalculationJob();
//	}
	
}
