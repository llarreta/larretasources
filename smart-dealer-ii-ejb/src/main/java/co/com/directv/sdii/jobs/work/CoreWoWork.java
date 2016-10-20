package co.com.directv.sdii.jobs.work;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;

import commonj.work.Work;

public class CoreWoWork extends WorkFinisher implements Work {

	private String countryCode;
	
	public CoreWoWork(Long countryId, Long idParam,String countryCode) throws NamingException,
			PropertiesException {
		super(countryId, idParam);
		this.countryCode = countryCode;
	}

	
	private final static Logger logger = UtilsBusiness.getLog4J(CoreWoWork.class);

	@Override
	public void run() {
		logger.info("Create ejecución del Job de Core");
		String message = "";
		boolean fail = false;
		try {
			logger.debug("Se inicia la ejecución del Job de Core");
			AssignmentFacadeLocator.getInstance().getCoreWOFacadeRemote().addWorkOrder(countryCode);
			
		} catch (Throwable e) {
			logger.error(e.getMessage());
			message = e.getMessage();
			fail = true;
			e.printStackTrace();
		}finally {
			logger.info("Termina la ejecución del Work de Core");
			try {
				this.finishWork(message, fail);
			} catch (BusinessException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (PropertiesException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isDaemon() {
		return false;
	}

	@Override
	public void release() {
	}
	
}