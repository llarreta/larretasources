package co.com.directv.sdii.facade.core.impl;

import java.util.List;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.ReportsCoreBusinessLocal;
import co.com.directv.sdii.ejb.business.core.impl.ReportsCoreBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreFacadeHspBeanLocal;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class CoreFacadeHspBean
 */
@Stateless(name="CoreFacadeHspBeanLocal",mappedName="ejb/CoreFacadeHspBeanLocal")
public class CoreFacadeHspBean extends BusinessBase implements CoreFacadeHspBeanLocal {

	@EJB(name="ReportsCoreBusinessLocal",beanInterface=ReportsCoreBusinessLocal.class)
	private ReportsCoreBusinessLocal reportsCoreBusinessLocal;
	
	private final static Logger log = UtilsBusiness.getLog4J(ReportsCoreBusiness.class);
	
    /**
     * Default constructor. 
     */
    public CoreFacadeHspBean() {
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreFacadeHspBeanLocal#reportsProductivityReport(co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReportsProductivityReportAndFileResponseDTO reportsProductivityReport(
			ReportsComplyAndScheduleFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		return reportsCoreBusinessLocal.reportsProductivityReport(filter, requestInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreFacadeHspBeanLocal#getReportsComplyAndSchedule(co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(
			ReportsComplyAndScheduleFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		return reportsCoreBusinessLocal.getReportsComplyAndSchedule(filter, requestInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreFacadeHspBeanLocal#getReportsPendingServicesByDate(co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(
			ReportsPendingServicesByDateFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		return reportsCoreBusinessLocal.getReportsPendingServicesByDate(filter, requestInfo);
	}

	//REQ002 - WO Agendadas en linea.
	@Override
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(
			ReportsSucceedWorkOrderFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		return reportsCoreBusinessLocal.getReportsSucceedWorkOrderCSR(filter, requestInfo);
	}  
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreFacadeHspBeanLocal#getReportWorkOrderRejection(co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ReportWorkOrderRejectionAndFileResponseDTO getReportWorkOrderRejection( ReportWorkOrderRejectionFilterDTO filter,
                                                                                   RequestCollectionInfo requestInfo) throws BusinessException {
		
		return reportsCoreBusinessLocal.reportWorkOrderRejection(filter, requestInfo);
		
	}
	
}
