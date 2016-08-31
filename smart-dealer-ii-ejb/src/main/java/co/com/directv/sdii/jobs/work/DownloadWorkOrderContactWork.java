package co.com.directv.sdii.jobs.work;

import java.util.Date;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;

import commonj.work.Work;

/**
 * Work utilizado para descargar los contacts de una workOrder
 * 
 * Fecha de Creación: 27/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class DownloadWorkOrderContactWork extends WorkFinisher implements Work {

	private final static Logger logger = UtilsBusiness.getLog4J(DownloadWorkOrderContactWork.class);
	
	private Date executionDate;
	
	public DownloadWorkOrderContactWork(Long countryId, Long idParam,Date executionDate)
			throws NamingException, PropertiesException {
		super(countryId, idParam);
		this.executionDate = executionDate;
	}

	@Override
	public boolean isDaemon() {
		return false;
	}

	@Override
	public void release() {
	}

	@Override
	public void run() {
		logger.info("Create ejecución del WM DownloadWorkOrderContactWork");
		String message = "";
		boolean fail = false;
		try {
			AssignmentFacadeLocator.getInstance().getCoreWOFacadeRemote().downloadWorkOrderContact(getCountryId(),executionDate);
		}catch (BusinessException e) {
			logger.error(e.getMessage());
			message = e.getMessage();
			fail = true;
			e.printStackTrace();
		} finally{
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

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	
	

}
