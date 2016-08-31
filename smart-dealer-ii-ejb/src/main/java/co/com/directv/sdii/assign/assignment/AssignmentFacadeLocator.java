/**
 * Creado 26/05/2011 18:25:57
 */
package co.com.directv.sdii.assign.assignment;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.EJBRemoteJNDINameEnum;
import co.com.directv.sdii.common.util.BeanContextUtils;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.broker.DistributedQueueMessageBrokerRemote;
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerRemote;
import co.com.directv.sdii.ejb.business.config.SchedulerTaskBussinessRemote;
import co.com.directv.sdii.ejb.business.file.FileProcessorBusinessBeanRemote;
import co.com.directv.sdii.ejb.business.stock.IbsElementsNotificationBusinessRemote;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.facade.allocator.AllocatorFacadeRemote;
import co.com.directv.sdii.facade.assign.BuildingFacadeBeanRemote;
import co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote;
import co.com.directv.sdii.facade.config.ConfigBusinessAreasFacadeRemote;
import co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeRemote;
import co.com.directv.sdii.facade.config.ScheduleReportFacadeRemote;
import co.com.directv.sdii.facade.config.ServiceCategoryFacadeBeanRemote;
import co.com.directv.sdii.facade.core.CoreWOFacadeRemote;
import co.com.directv.sdii.facade.schedule.WoInfoEsbServiceFacadeRemote;
import co.com.directv.sdii.facade.security.SecurityFacadeBeanRemote;
import co.com.directv.sdii.jobs.JobTimeManagerExcecuteBeanRemote;

/**
 * Se encarga de localizar el bean que ofrece el acceso a las operaciones de 
 * persistencia del módulo de asignador
 * 
 * Fecha de Creación: 26/05/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public final class AssignmentFacadeLocator {

	private static AssignmentFacadeLocator mySelf;
	private final static Logger log = UtilsBusiness.getLog4J(AssignmentFacadeLocator.class);

	private AssignmentFacadeLocator() {
		super();
	}

	public static AssignmentFacadeLocator getInstance(){
		if(mySelf == null){
			mySelf = new AssignmentFacadeLocator();
		}
		return mySelf;
	}
	
	public AssignmentFacadeRemote getAssignmetFacade() {
		AssignmentFacadeRemote facadeRemote;
		try {
			facadeRemote = BeanContextUtils.getInstance().lookupEjb(AssignmentFacadeRemote.class, EJBRemoteJNDINameEnum.AssignmentFacade.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean AssignmentFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean AssignmentFacadeRemote del contexto");
		}
		return facadeRemote;
	}
	
	public CoreAssignmentFacadeRemote getCoreAssignmentFacade(){
		CoreAssignmentFacadeRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(CoreAssignmentFacadeRemote.class, EJBRemoteJNDINameEnum.CoreAssignmentFacadeRemote.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean CoreAssignmentFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean CoreAssignmentFacadeRemote del contexto");
		}
		return result;
	}
	
	public CoreWOFacadeRemote getCoreWOFacadeRemote(){
		CoreWOFacadeRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(CoreWOFacadeRemote.class, EJBRemoteJNDINameEnum.CoreWOFacadeLocal.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean CoreWOFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean CoreWOFacadeRemote del contexto");
		}
		return result;
	}
	
	/**
	 * Metodo encargado de tomar una referencia del EJB remoto para la fachada de procesamiento en paralelo de core y asignador
	 * @return Fachada remota del EJB de procesamiento en paralelo de core y asignador
	 * @author Aharker
	 */
	public WoInfoEsbServiceFacadeRemote getWoInfoEsbServiceFacadeRemote() {
		WoInfoEsbServiceFacadeRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(WoInfoEsbServiceFacadeRemote.class,EJBRemoteJNDINameEnum.WoInfoEsbServiceFacadeRemote.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean WoInfoEsbServiceFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean WoInfoEsbServiceFacadeRemote del contexto");
		}
		return result;
	}
	
	public SecurityFacadeBeanRemote getSecurityFacade(){
		SecurityFacadeBeanRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(SecurityFacadeBeanRemote.class, EJBRemoteJNDINameEnum.SecurityFacadeBeanLocal.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean AllocatorFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean AllocatorFacadeRemote del contexto");
		}
		return result;
	}
	
	
	public AllocatorFacadeRemote getAllocatorFacade(){
		AllocatorFacadeRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(AllocatorFacadeRemote.class, EJBRemoteJNDINameEnum.AllocatorFacadeLocal.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean AllocatorFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean AllocatorFacadeRemote del contexto");
		}
		return result;
	}

	public SchedulerTaskBussinessRemote getSchedulerTaskFacade(){
		SchedulerTaskBussinessRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(SchedulerTaskBussinessRemote.class, EJBRemoteJNDINameEnum.SchedulerTaskBussinessRemote.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean SchedulerTaskBussinessRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean SchedulerTaskBussinessRemote del contexto");
		}
		return result;
	}
	
	public DistributedQueueMessageBrokerRemote getDistributedQueueMessageBroker(){
		DistributedQueueMessageBrokerRemote result;
		try{
			result  = BeanContextUtils.getInstance().lookupEjb(DistributedQueueMessageBrokerRemote.class,EJBRemoteJNDINameEnum.DistributedQueueMessageBrokerLocal.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean DistributedQueueMessageBrokerRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean DistributedQueueMessageBrokerRemote del contexto");
		}
		return result;
	}
	
	public ManageWorkForceServiceBrokerRemote getManageWorkForceServiceBroker(){
		ManageWorkForceServiceBrokerRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(ManageWorkForceServiceBrokerRemote.class, EJBRemoteJNDINameEnum.ManageWorkForceServiceBrokerLocal.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean ManageWorkForceServiceBrokerRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean ManageWorkForceServiceBrokerRemote del contexto");
		}
		return result;
	}
	
	public FileProcessorBusinessBeanRemote getFileProcessorBusinessBean(){
		FileProcessorBusinessBeanRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(FileProcessorBusinessBeanRemote.class, EJBRemoteJNDINameEnum.FileProcessorBusinessBeanLocal.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean FileProcessorBusinessBeanRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean FileProcessorBusinessBeanRemote del contexto");
		}
		return result;
	}
	
	public ScheduleReportFacadeRemote getScheduleReportFacade(){
		ScheduleReportFacadeRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(ScheduleReportFacadeRemote.class, EJBRemoteJNDINameEnum.ScheduleReportFacadeRemote.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean FileProcessorBusinessBeanRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean FileProcessorBusinessBeanRemote del contexto");
		}
		return result;
	}
	
	public IbsElementsNotificationBusinessRemote getIbsElementsNotificationBusiness(){
		IbsElementsNotificationBusinessRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(IbsElementsNotificationBusinessRemote.class, EJBRemoteJNDINameEnum.IbsElementsNotificationBusiness.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean IbsElementsNotificationBusinessRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean IbsElementsNotificationBusinessRemote del contexto");
		}
		return result;
	}
	
	public JobTimeManagerExcecuteBeanRemote getJobTimeManagerExcecute(){
		JobTimeManagerExcecuteBeanRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(JobTimeManagerExcecuteBeanRemote.class, EJBRemoteJNDINameEnum.JobTimeManagerExcecute.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean JobTimeManagerExcecuteBeanRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean JobTimeManagerExcecuteBeanRemote del contexto");
		}
		return result;
	}
	
	public BuildingFacadeBeanRemote getBuildingFacade(){
		BuildingFacadeBeanRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(BuildingFacadeBeanRemote.class, EJBRemoteJNDINameEnum.BuildingFacadeBean.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean BuildingFacadeBeanRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean BuildingFacadeBeanRemote del contexto");
		}
		return result;
	}
	
	public ConfigBusinessAreasFacadeRemote getConfigBusinessAreasFacade(){
		ConfigBusinessAreasFacadeRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(ConfigBusinessAreasFacadeRemote.class, EJBRemoteJNDINameEnum.ConfigBusinessAreasFacadeRemote.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean ConfigBusinessAreasFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean ConfigBusinessAreasFacadeRemote del contexto");
		}
		return result;
	}
	
	public ConfigTiposClienteFacadeRemote getConfigTiposClienteFacade(){
		ConfigTiposClienteFacadeRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(ConfigTiposClienteFacadeRemote.class, EJBRemoteJNDINameEnum.ConfigTiposClienteFacadeRemote.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean ConfigTiposClienteFacadeRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean ConfigTiposClienteFacadeRemote del contexto");
		}
		return result;
	}
	
	public ServiceCategoryFacadeBeanRemote getServiceCategoryFacade(){
		ServiceCategoryFacadeBeanRemote result;
		try {
			result = BeanContextUtils.getInstance().lookupEjb(ServiceCategoryFacadeBeanRemote.class, EJBRemoteJNDINameEnum.ServiceCategoryFacadeBeanRemote.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean ServiceCategoryFacadeBeanRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean ServiceCategoryFacadeBeanRemote del contexto");
		}
		return result;
	}
	
}
