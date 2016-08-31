package co.com.directv.sdii.jobs;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.ScheduleException;


/**
 * Reduce la complejidad para obtener una instancia del factory del scheduler
 * permitiendo agendar jobs mediante el nombra qualificado del job y una expresión
 * cron, el objeto estático del scheduler factory será inyectado por
 * un componente que lo inicializa al iniciar la aplicación web
 * 
 * Fecha de Creación: 10/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see org.quartz.SchedulerFactory      
 */
public final class SchedulerFactoryProxy {

	private final static Logger logger = UtilsBusiness.getLog4J(SchedulerFactoryProxy.class);
	
	private SchedulerFactory schedulerFactory;
	
	private Scheduler scheduler;
	
	private static SchedulerFactoryProxy mySelf;
	
	

	private SchedulerFactoryProxy() {
		super();
		configureSchedulerFactory();
	}
	
	/**
	 * Metodo: <Descripcion> <tipo> <descripcion>
	 * @author
	 */
	private void configureSchedulerFactory() {
		try {
			PropertiesReader reader = UtilsBusiness.getMessageProperties(Constantes.LABEL_RUTA_SCHEDULER_CONFIG);
			Properties props = new Properties();
			List<String> keys = reader.getKeys();
			for (String key : keys) {
				props.put(key, reader.getKey(key));
			}
			schedulerFactory = new StdSchedulerFactory(props);
		} catch (Exception e) {
			logger.error("Al intentar cargar las propiedades de la configuración de Jobs. " + e.getMessage(), e);
		}
	}

	public static SchedulerFactoryProxy getInstance(){
		if(mySelf == null)
			mySelf = new SchedulerFactoryProxy();
		return mySelf;
	}
	
	/**
	 * 
	 * Metodo: Crea un Job sin triggers asociados
	 * @param jobName nombre del job a crear
	 * @param jobClassName clase del job a ejecutar
	 * @param groupName nombre del grupo en que se desea crear el job
	 * @return JobDetail detalle del job creado
	 * @throws ScheduleException
	 * @author wjimenez 
	 */
	@SuppressWarnings("unchecked")
	public JobDetail createEmptyJob(String jobName, String jobClassName, String groupName) throws ScheduleException {
		try {
			Class jobClass = Class.forName(jobClassName);
			JobDetail jobDetail = new JobDetail(jobName, groupName, jobClass);
			return jobDetail;
		} catch (ClassNotFoundException e) {
			String msg = "no se pudo crear el Job " + jobName + ". " + e.getMessage();
			logger.error(msg);
			throw new ScheduleException(msg, e);
		}
	}
	
	/**
	 * 
	 * Metodo: programar un job para ejecución de acuerdo a una cronExpression
	 * @param jobName nombre del job a crear
	 * @param jobClassName clase del job a ejecutar
	 * @param cronExpression expresión que determina la periodicidad de ejecución del trigger
	 * @throws ScheduleException
	 * @author wjimenez
	 */
	@SuppressWarnings("unchecked")
	public void scheduleJob(String jobName, String jobClassName, String cronExpression) throws ScheduleException {
		scheduleJob(jobName, jobClassName, cronExpression, Collections.EMPTY_MAP);
	}
	
	/**
	 * 
	 * Metodo: programar un job para ejecución de acuerdo a una cronExpression y que permite
	 * agregar parámetros para el momento de la ejecución del job
	 * @param jobName nombre del job a crear
	 * @param jobClassName clase del job a ejecutar
	 * @param cronExpression expresión que determina la periodicidad de ejecución del trigger
	 * @param customJobData mapa con los parámetros asociados al Job. Para recuperar los parámetros
	 * debe usarse <code>JobExecutionContext.getJobDetail().getJobDataMap();</code>
	 * @throws ScheduleException
	 */
	public void scheduleJob(String jobName, String jobClassName, String cronExpression, Map<String, Object> customJobData) throws ScheduleException{
		try {
			if(logger.isDebugEnabled()){
				logger.debug("Se inicia la operación scheduleJob con el jobName = " + jobName + " jobClassName = " + jobClassName + " cronExpression \"" + cronExpression + "\"");
			}
			JobDetail jobDetail = createEmptyJob(jobName, jobClassName, Scheduler.DEFAULT_GROUP);
			
			jobDetail.setJobDataMap( new JobDataMap(customJobData) );
			
			String triggerName = jobName + "_trigger";
			Trigger trigger = new CronTrigger(triggerName, null, cronExpression);
			
			if(schedulerFactory == null){
				throw new ScheduleException("No se ha inicializado la schedulerFactory para agendar los jobs");
			}
			
			JobDetail oldJobDetail = getScheduler().getJobDetail(jobDetail.getName(), Scheduler.DEFAULT_GROUP);
			
			if(oldJobDetail != null){
				logger.debug("El job con nombre: \""+jobName+"\"  y clase \"" + jobClassName + "\" ya existe");
				String[] triggerGroupNames = scheduler.getTriggerGroupNames();
				if(triggerGroupNames != null && triggerGroupNames.length > 0){
					for(int i = 0 ; i < triggerGroupNames.length ; i++){
						Trigger oldTrigger = scheduler.getTrigger(triggerName, triggerGroupNames[i]);
						if(oldTrigger != null && oldTrigger instanceof CronTrigger){
							//Si la expresión ha cambiado:
							if(! ((CronTrigger)oldTrigger).getCronExpression().equalsIgnoreCase(cronExpression)){
								((CronTrigger)oldTrigger).setCronExpression(cronExpression);
								scheduler.rescheduleJob(triggerName, triggerGroupNames[i], oldTrigger);
								logger.debug("Se ha actualizado la periodicidad de la ejecución del job " + jobName+ " la nueva expresión es: " + cronExpression);
							}
						}
					}
				}
				return;
			}
			
			scheduler.scheduleJob(jobDetail, trigger);
			
			if(logger.isDebugEnabled()){
				logger.debug("Finaliza la operación scheduleJob");
			}
		} catch (ParseException e) {
			logger.error("Al tratar de crear un trigger con la expresión de cron: " + cronExpression, e);
		} catch (SchedulerException e) {
			logger.error("Al tratar de agendar el Job en el scheduler que retorna el schedulerFactory", e);
		}
	}
	
	/**
	 * 
	 * Metodo: Adiciona un trigger con su propia cronExpression a un job previamente creado.
	 * El trigger se adiciona al grupo por defecto.
	 * @param jobName nombre del Job existente al que se le agregará el trigger
	 * @param triggerId identificador que se quiere asignar al trigger
	 * @param cronExpression expresión que determina la periodicidad de ejecución del trigger
	 * @param customData mapa con los parámetros que se quieran pasar para la ejecución del trigger. Para recuperar los parámetros
	 * debe usarse <code>JobExecutionContext.getTrigger().getJobDataMap();</code>
	 * @throws ScheduleException 
	 * @author wjimenez 
	 */
	public void addTriggerToJob(String jobName, String triggerId, String cronExpression, Map<String, Object> customData) throws ScheduleException{
		JobDetail jobDetail = null;
		try {
			jobDetail = scheduler.getJobDetail(jobName, Scheduler.DEFAULT_GROUP);
		} catch (SchedulerException e) {
			throw new ScheduleException("se presentó un error al obtener el job " + jobName + " para agregarle el trigger con id = " + triggerId);
		}
		if(jobDetail != null) {
			addTriggerToJob(jobDetail, triggerId, Scheduler.DEFAULT_GROUP, cronExpression, customData);	
		} else {
			throw new ScheduleException("no se encontró el job " + jobName + " para agregarle el trigger con id = " + triggerId + ". Debe crear el job primero");
		}
		
	}
	
	/**
	 * 
	 * Metodo: Adiciona un trigger con su propia cronExpression a un job previamente creado
	 * @param jobDetail Job existente al que se le agregará el trigger
	 * @param triggerId identificador que se quiere asignar al trigger
	 * @param groupName nombre del grupo al que pertenece el job para adicionar el trigger
	 * @param cronExpression expresión que determina la periodicidad de ejecución del trigger
	 * @param customTriggerData mapa con los parámetros que se quieran pasar para la ejecución del trigger
	 * @throws ScheduleException	 
	 * @author wjimenez
	 */
	public void addTriggerToJob(JobDetail jobDetail, String triggerId, String groupName, String cronExpression, Map<String, Object> customTriggerData) throws ScheduleException{
		
		String triggerName = null;
		Trigger trigger = null;
		try {
			
			triggerName = jobDetail.getName() + "_" + triggerId + "_trigger";
			trigger = new CronTrigger(triggerName, groupName, cronExpression);
			trigger.setJobDataMap(new JobDataMap(customTriggerData));
			
			if(schedulerFactory == null){
				throw new ScheduleException("No se ha inicializado la schedulerFactory para agendar los jobs");
			}
			
			try {
				getScheduler().scheduleJob(jobDetail, trigger);
			} catch (ObjectAlreadyExistsException e) {//cuando el jobDetail ya se ha agregado, se deben pasar los parámetros del job y solo programar el trigger
				trigger.setJobName(jobDetail.getName());
				trigger.setJobGroup(groupName);
				scheduler.scheduleJob(trigger);
			} catch (SchedulerException e) {
				String msg = new StringBuffer("no fue posible agregar el trigger ").append(triggerName != null ? triggerName : "null").append(" del grupo ")
				.append(groupName != null ? groupName : "null").append(" al job ").append(jobDetail != null ? jobDetail.getName() : "null").toString();
				logger.error(msg);
				throw new ScheduleException(msg, e);
			}

		} catch(ObjectAlreadyExistsException e) {
			rescheduleJob(triggerName, groupName, trigger);
			String msg = new StringBuffer("La configuración del trigger ")
				.append(triggerName != null ? triggerName : "null")
				.append(" ya existía. Se reprogramó de acuerdo a la expresión ")
				.append(cronExpression != null ? cronExpression : "null").toString();
			logger.info(msg);
		} catch (SchedulerException e) {
			String msg = "Al tratar de agendar el Trigger en el scheduler que retorna el schedulerFactory. " + e.getMessage();
			logger.error(msg);
			throw new ScheduleException(msg, e);
		} catch (ParseException e) {
			String msg = "Al tratar de crear un trigger con la expresión de cron: " + cronExpression + ". " + e.getMessage();
			logger.error(msg);
			throw new ScheduleException(msg, e);
		}
		
	}
	
	/**
	 * 
	 * Metodo: borra un trigger de un job previamente creado
	 * @param jobDetail Job existente al que se le agregará el trigger
	 * @param triggerId identificador que se quiere asignar al trigger
	 * @param groupName nombre del grupo al que pertenece el job para adicionar el trigger
	 * @throws ScheduleException	 
	 * @author cduarte
	 */
	public void removeTriggerToJob(JobDetail jobDetail, String triggerId, String groupName) throws ScheduleException{
		
		String triggerName = null;
			
		if(schedulerFactory == null){
			throw new ScheduleException("No se ha inicializado la schedulerFactory para agendar los jobs");
		}
		
		//Se crea el nombre del trigger
		triggerName = jobDetail.getName() + "_" + triggerId + "_trigger";
		//Elimina el trigger por nombre
		unscheduleJob(triggerName, groupName);
		
	}
	
	private void rescheduleJob(String triggerName, String groupName, Trigger trigger) throws ScheduleException {
		try {
			scheduler.rescheduleJob(triggerName, groupName, trigger);
		} catch (SchedulerException e) {
			throw new ScheduleException("No se pudo reagendar el Trigger en el scheduler que retorna el schedulerFactory", e);
		}
	}
	
	/**
	 * 
	 * Metodo: borra un trigger de un job previamente creado
	 * @param triggerName Nombre del trigger
	 * @param groupName nombre del grupo al que pertenece el job para adicionar el trigger
	 * @throws ScheduleException	 
	 * @author cduarte
	 */
	 private void unscheduleJob(String triggerName, String groupName) throws ScheduleException {
		try {
			//se borra el trigger
			getScheduler().unscheduleJob(triggerName, groupName);
		} catch (SchedulerException e) {
			throw new ScheduleException("No se pudo eliminar el Trigger en el scheduler que retorna el schedulerFactory", e);
		}
	}
	
	public void stopScheduler(){
		logger.debug("Se invoca la operación para detener el scheduler");
		try {			
			if(scheduler != null && scheduler.isStarted()){
				logger.debug("Se detendrá  scheduler");
				for(String j : scheduler.getJobNames(Scheduler.DEFAULT_GROUP))		
					scheduler.interrupt(j, Scheduler.DEFAULT_GROUP);		
				scheduler.shutdown();

			}	
		} catch (SchedulerException e) {	
			logger.error("Error al finalizar el planificador", e);	
		}
	}
	
	public void startScheduler(){
		logger.debug("Se invoca la operación para iniciar el scheduler");
		try {
			if(scheduler != null && ! scheduler.isStarted()){
				if(logger.isInfoEnabled()){
					logger.info("empieza la inicialización del scheduler");
				}
				scheduler.start();
				if(logger.isInfoEnabled()){
					logger.info("finaliza la inicialización del scheduler");
				}
			}
		} catch (SchedulerException e) {	
			logger.error("Error al inicializar el scheduler", e);	
		}
	}
	
	/**
	 * 
	 * Metodo: Elimina el job y los triggers asociados
	 * @param jobDetail job que se desea eliminar
	 * @throws ScheduleException 
	 * @author wjimenez
	 */
	public void deleteJob(JobDetail jobDetail) throws ScheduleException {
		deleteJob(jobDetail.getName(), jobDetail.getGroup());
	}

	/**
	 * 
	 * Metodo: Elimina el job y los triggers asociados
	 * @param jobName nombre del job que se desea eliminar
	 * @param groupName nombre del grupo al que pertenece el job que se desea eliminar
	 * @throws ScheduleException 
	 * @author wjimenez
	 */
	public void deleteJob(String jobName, String groupName) throws ScheduleException {
		try {
			getScheduler().deleteJob(jobName, groupName);
		} catch (Exception e) {
			String msg = "no se pudo eliminar el Job. " + e.getMessage(); 
			logger.error(msg);
			throw new ScheduleException(msg, e);
		}
	}

	private Scheduler getScheduler() throws SchedulerException {
		if(scheduler == null || scheduler.isShutdown()){
			logger.info("El scheduler era nulo o no estaba activo, se creará una instancia con el factory");
			scheduler = schedulerFactory.getScheduler();
		}
		return scheduler;
	}
	
	/**
	 * 
	 * Metodo: Obtiene el job y los triggers asociados
	 * @param jobName nombre del job que se desea eliminar
	 * @param groupName nombre del grupo al que pertenece el job que se desea eliminar
	 * @throws ScheduleException 
	 * @author cduarte
	 */
	public JobDetail getJobDetail(String jobName, String groupName) throws ScheduleException {
		JobDetail jobDetail = null;
		try {
			jobDetail = getScheduler().getJobDetail(jobName, groupName);
		} catch (Exception e) {
			String msg = "no se pudo eliminar el Job. " + e.getMessage(); 
			logger.error(msg);
			throw new ScheduleException(msg, e);
		}
		return jobDetail;
	}
	
}
