package co.com.directv.sdii.jobs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

import co.com.directv.sdii.common.enumerations.EJBRemoteJNDINameEnum;
import co.com.directv.sdii.common.util.BeanContextUtils;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.stock.IbsElementsNotificationBusinessRemote;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.facade.core.CoreWOFacadeRemote;
import co.com.directv.sdii.facade.dealers.CountriesFacadeBeanRemote;
import co.com.directv.sdii.facade.file.FileProcessorFacadeRemote;

public class SdiiJobFactory extends SimpleJobFactory {

	private final static Logger logger = UtilsBusiness.getLog4J(SdiiJobFactory.class);
	
	@Override
	public Job newJob(TriggerFiredBundle trigger) throws SchedulerException {
		
		if(logger.isDebugEnabled()){
			logger.debug("Se inicia la instanciación de un Job en el JobFactory");
		}
		
		Job result = super.newJob(trigger);
		if(result instanceof CoreWoJob){
			CoreWOFacadeRemote coreWOFacade;
			try {
				coreWOFacade = BeanContextUtils.getInstance().lookupEjb(CoreWOFacadeRemote.class, EJBRemoteJNDINameEnum.CoreWOFacadeLocal.getCodeEntity());
			} catch (PropertiesException e) {
				logger.error("No se pudo obtener el bean CoreWOFacadeRemote del contexto");
				throw new IllegalStateException("No se pudo obtener el bean CoreWOFacadeRemote del contexto");
			}
			((CoreWoJob)result).setCoreWOFacade(coreWOFacade);
			CountriesFacadeBeanRemote countriesFacade;
			try {
				countriesFacade = BeanContextUtils.getInstance().lookupEjb(CountriesFacadeBeanRemote.class, EJBRemoteJNDINameEnum.CountriesFacadeBeanLocal.getCodeEntity());
			} catch (PropertiesException e) {
				logger.error("No se pudo obtener el bean CountriesFacadeBeanRemote del contexto");
				throw new IllegalStateException("No se pudo obtener el bean CountriesFacadeBeanRemote del contexto");
			}
			((CoreWoJob)result).setCountriesFacade(countriesFacade);
		}else if(result instanceof FileProcessorJobEC){
			FileProcessorFacadeRemote fileProcessorFacade;
			try {
				fileProcessorFacade = BeanContextUtils.getInstance().lookupEjb(FileProcessorFacadeRemote.class, EJBRemoteJNDINameEnum.FileProcessorFacadeBean.getCodeEntity());
			} catch (PropertiesException e) {
				logger.error("No se pudo obtener el bean FileProcessorFacadeRemote del contexto");
				throw new IllegalStateException("No se pudo obtener el bean FileProcessorFacadeRemote del contexto");
			}
			((FileProcessorJobEC)result).setFileProcessorFacade(fileProcessorFacade);
			CountriesFacadeBeanRemote countriesFacade;
			try {
				countriesFacade = BeanContextUtils.getInstance().lookupEjb(CountriesFacadeBeanRemote.class, EJBRemoteJNDINameEnum.CountriesFacadeBeanLocal.getCodeEntity());
			} catch (PropertiesException e) {
				logger.error("No se pudo obtener el bean CountriesFacadeBeanRemote del contexto");
				throw new IllegalStateException("No se pudo obtener el bean CountriesFacadeBeanRemote del contexto");
			}
			((FileProcessorJobEC)result).setCountriesFacade(countriesFacade);
		}else if(result instanceof CmdIBSJob){
			IbsElementsNotificationBusinessRemote ibsElementsNotificationBusinessRemote;
			try {
				ibsElementsNotificationBusinessRemote = BeanContextUtils.getInstance().lookupEjb(IbsElementsNotificationBusinessRemote.class, EJBRemoteJNDINameEnum.IbsElementsNotificationBusiness.getCodeEntity());
			} catch (PropertiesException e) {
				logger.error("No se pudo obtener el bean IbsElementsNotificationBusinessRemote del contexto");
				throw new IllegalStateException("No se pudo obtener el bean IbsElementsNotificationBusinessRemote del contexto");
			}
			((CmdIBSJob)result).setIbsElementsNotificationBusinessRemote(ibsElementsNotificationBusinessRemote);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Se Finaliza la instanciación de un Job en el JobFactory");
		}
		return result;
	}

}
