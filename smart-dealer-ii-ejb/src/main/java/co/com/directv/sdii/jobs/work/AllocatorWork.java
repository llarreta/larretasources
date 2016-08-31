package co.com.directv.sdii.jobs.work;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;

import commonj.work.Work;

/**
 * 
 * @author Aharker
 *
 */
public class AllocatorWork extends WorkFinisher implements Work {

	public AllocatorWork(Long countryId, Long idParam) throws NamingException,
			PropertiesException {
		super(countryId,idParam);
	}

	private final static Logger logger = UtilsBusiness.getLog4J(AllocatorWork.class);
	
	@Override
	public void run() {
		logger.info("Create ejecución del Job de Allocator");
		String message = "";
		boolean fail = false;
		try {
			logger.debug("Se inicia la ejecución del Job de Allocator");
			AssignmentFacadeLocator.getInstance().getAllocatorFacade().allocWorkOrders(getCountryId());
			
		} catch (Throwable e) {
			logger.error(e.getMessage());
			message = e.getMessage();
			fail = true;
			e.printStackTrace();
		}finally{
			logger.info("Termina la ejecución del Work de AllocatorWork");
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
