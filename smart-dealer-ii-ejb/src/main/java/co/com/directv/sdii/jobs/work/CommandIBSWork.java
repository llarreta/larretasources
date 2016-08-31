package co.com.directv.sdii.jobs.work;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;

import commonj.work.Work;

public class CommandIBSWork extends WorkFinisher implements Work  {

	public CommandIBSWork(Long countryId, Long idParam) throws NamingException,
			PropertiesException {
		super(countryId,idParam);
	}

	private final static Logger logger = UtilsBusiness.getLog4J(CommandIBSWork.class);
	
	@Override
	public boolean isDaemon() {
		return false;
	}

	@Override
	public void release() {		
	}

	@Override
	public void run() {
		String message = "";
		boolean fail = false;
		try {
			//AssignmentFacadeLocator.getInstance().getIbsElementsNotificationBusiness().sendStatusCmdToIBS(getCountryId());
			//Req-0098 - Paralelismo de Inventarios
			AssignmentFacadeLocator.getInstance().getIbsElementsNotificationBusiness().sendToMovCmdQueue(getCountryId());
			
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			message = e.getMessage();
			fail = true;
		}finally{
			try {
				this.finishWork(message, fail);
			} catch (BusinessException e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			} catch (PropertiesException e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

}
