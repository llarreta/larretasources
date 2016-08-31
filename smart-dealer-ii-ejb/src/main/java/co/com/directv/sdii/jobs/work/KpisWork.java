package co.com.directv.sdii.jobs.work;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;

import commonj.work.Work;

public class KpisWork extends WorkFinisher implements Work {

	public KpisWork(Long countryId, Long idParam) throws NamingException, PropertiesException {
		super(countryId,idParam);
	}

	private final static Logger logger = UtilsBusiness.getLog4J(KpisWork.class);
	
	@Override
	public boolean isDaemon() {
		return false;
	}

	@Override
	public void release() {
	}

	@Override
	public void run() {
		logger.info("Create ejecución del Job de KpisWork");
		String message = "";
		boolean fail = false;
		List<KpiCalculateDTO> kpiCalculateDTOs = null;
		try {
			kpiCalculateDTOs = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getKpiCalculateDTOByCalculationTypeCode(CodesBusinessEntityEnum.KPI_CALCULATION_TYPE_FRECUENCY.getCodeEntity(),getCountryId());

			if(kpiCalculateDTOs!=null){
				for (KpiCalculateDTO kpiCalculateDTO : kpiCalculateDTOs) {
					AssignmentFacadeLocator.getInstance().getDistributedQueueMessageBroker().setQueueCalculateKpi(kpiCalculateDTO);
				}
			}
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			message = e.getMessage();
			fail = true;
		} catch (PropertiesException e) {
			logger.error(e.getMessage(), e);
			message = e.getMessage();
			fail = true;
		}finally{
			logger.info("Finaliza ejecución del Job de KpisWork");
			try {
				this.finishWork(message, fail);
			} catch (BusinessException e) {
				logger.error("error del Job de KpisWork");
				e.printStackTrace();
			} catch (PropertiesException e) {
				logger.error("error del Job de KpisWork");
				e.printStackTrace();
			}
		}
		
	}
	
}
