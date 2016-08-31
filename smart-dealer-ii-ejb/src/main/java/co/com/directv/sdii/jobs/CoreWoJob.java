package co.com.directv.sdii.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.facade.core.CoreWOFacadeRemote;
import co.com.directv.sdii.facade.dealers.CountriesFacadeBeanRemote;

/**
 * Esta clase ejecuta los trabajos para la importación de work orders desde IBS 
 * 
 * Fecha de Creación: 22/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CoreWoJob implements StatefulJob {

	private final static Logger logger = UtilsBusiness.getLog4J(CoreWoJob.class);
	private CoreWOFacadeRemote coreWOFacade;
	
	private CountriesFacadeBeanRemote countriesFacade;
	
	public CoreWOFacadeRemote getCoreWOFacade() {
		return coreWOFacade;
	}
	public void setCoreWOFacade(CoreWOFacadeRemote coreWOFacade) {
		this.coreWOFacade = coreWOFacade;
	}

	public CountriesFacadeBeanRemote getCountriesFacade() {
		return countriesFacade;
	}
	public void setCountriesFacade(CountriesFacadeBeanRemote countriesFacade) {
		this.countriesFacade = countriesFacade;
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(logger.isInfoEnabled()){
			logger.info("Se inicia la ejecución del Job de Core");
		}
		if(!(coreWOFacade != null && countriesFacade != null)){
			logger.error("No se ha asignado la propiedad coreWOFacade al trabajo por tanto no se pudo ejecutar");
		}
	}

}
