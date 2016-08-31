package co.com.directv.sdii.common.enumerations;

import java.io.IOException;
import java.util.Properties;

import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * Clase de tipo Enum, encargada de realizar el mapping
 * de los EJB remote , los cuales se 
 * encuentran ubicados en un archivo de propiedades externo a la 
 * aplicacion.
 * 
 * Fecha de Creación: 17/07/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public enum EJBRemoteJNDINameEnum {

	ErrorMessageBusinessLocal("ErrorMessageBusinessLocal",""),
	AssignmentFacade("AssignmentFacade",""),
	DistributedQueueMessageBrokerLocal("DistributedQueueMessageBrokerLocal",""),
	ManageWorkForceServiceBrokerLocal("ManageWorkForceServiceBrokerLocal",""),
	SchedulerTaskBussinessRemote("SchedulerTaskBussinessRemote",""),
	FileProcessorBusinessBeanLocal("FileProcessorBusinessBeanLocal",""),
	ScheduleReportFacadeRemote("ScheduleReportFacadeRemote",""),
	FileProcessorFacadeBean("FileProcessorFacadeBean",""),
	BusinessParametersLocal("BusinessParametersLocal",""),
	IbsElementsNotificationBusiness("IbsElementsNotificationBusiness",""),
	AllocatorFacadeLocal("AllocatorFacadeLocal",""),
	CoreAssignmentFacadeRemote("CoreAssignmentFacadeRemote",""),
	CoreWOFacadeLocal("CoreWOFacadeLocal",""),
	CountriesFacadeBeanLocal("CountriesFacadeBeanLocal",""),
	SecurityFacadeBeanLocal("SecurityFacadeBeanLocal",""),
	WoInfoEsbServiceFacadeRemote("WoInfoEsbServiceFacade",""),
	JobTimeManagerExcecute("JobTimeManagerExcecute",""),
	SystemParameterBusinessBeanLocal("SystemParameterBusinessBeanLocal",""),
	BuildingFacadeBean("BuildingFacadeBean",""),
	ConfigBusinessAreasFacadeRemote("ConfigBusinessAreasFacadeLocal",""),
	ConfigTiposClienteFacadeRemote("ConfigTiposClienteFacadeLocal",""),
	ServiceCategoryFacadeBeanRemote("ServiceCategoryFacadeBeanLocal",""),
	;
    
    private String codeEntity;
    private String columnEntity;
   
    EJBRemoteJNDINameEnum(String pCodeEntity,String pColumnEntity) {
        this.codeEntity = pCodeEntity;
        this.columnEntity = pColumnEntity;
    }
    
     /**
     *
     * Metodo: Retorna el codigo de la
     * entidad relacionada.
     * @return String codeEntity
     * @author Joan Lopez
     * @throws IOException 
     */
    public String getCodeEntity() throws PropertiesException{
    	
    	try {
			StringBuffer str = new StringBuffer(PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_EJB_REMOTE_JNDI_NAME));
			Properties properties = new Properties();
			properties.load( getClass().getResourceAsStream( str.toString()) );
			String codeResource =  (String) properties.get(this.codeEntity); 
			this.codeEntity = codeResource != null ? codeResource.trim() : this.codeEntity;
		    return this.codeEntity;
    	} catch (Throwable ex) {
    		throw new PropertiesException("Error obteniendo instancia PropertiesReader ["+Constantes.LABEL_RUTA_EJB_REMOTE_JNDI_NAME+"]",ex);    		
		}
		
	        
    }
    
}
