/**
 * 
 */
package co.com.directv.sdii.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.IbsElementsNotificationBusinessRemote;
import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Esta clase ejecuta el Job para el informe de movimientos y estados de elementos a IBS
 * Implementa StatefulJob de Quartz Scheduler.
 * 
 * @author Jimmy Vélez Muñoz
 */
public class CmdIBSJob extends BusinessBase implements StatefulJob {

	private final static Logger log = UtilsBusiness.getLog4J(CmdIBSJob.class);

	private IbsElementsNotificationBusinessRemote ibsElementsNotificationBusinessRemote;


	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg) throws JobExecutionException {
//		log.debug(" == Inicia execute/CmdIBSJob ==> Fecha de ejecución: " + arg.getFireTime());
//		try {
//			this.sendStatusCmdToIBS();
//		} catch (BusinessException e) {
//			log.error("== Error execute/CmdIBSJob ==", e);
//			throw new JobExecutionException(e.getMessageCode(), e, false);
//		} catch (Throwable e) {
//			log.error("== Error execute/CmdIBSJob ==", e);
//			throw new JobExecutionException(e.getMessage(), e, false);
//		} 
//		finally {
//			log.debug(" == Termina execute/CmdIBSJob ==");
//		}
	}


	public IbsElementsNotificationBusinessRemote getIbsElementsNotificationBusinessRemote() {
		return ibsElementsNotificationBusinessRemote;
	}


	public void setIbsElementsNotificationBusinessRemote(
			IbsElementsNotificationBusinessRemote ibsElementsNotificationBusinessRemote) {
		this.ibsElementsNotificationBusinessRemote = ibsElementsNotificationBusinessRemote;
	}
}
