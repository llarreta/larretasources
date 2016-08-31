package co.com.directv.sdii.ws.business.core.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreFacadeHspBeanLocal;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.ws.business.core.ICoreHspWS;

@MTOM
@WebService(serviceName="CoreHspWSService",
		endpointInterface="co.com.directv.sdii.ws.business.core.ICoreHspWS",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		portName="CoreHspWSPort")
@Stateless()
public class CoreHspWS implements ICoreHspWS{

	@EJB(name="CoreFacadeHspBeanLocal",beanInterface=CoreFacadeHspBeanLocal.class)
	private CoreFacadeHspBeanLocal coreFacadeHspBeanLocal;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.ICoreHspWS#getReportsComplyAndSchedule(co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(
			ReportsComplyAndScheduleFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		return coreFacadeHspBeanLocal.getReportsComplyAndSchedule(filter, requestInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.ICoreHspWS#getReportsPendingServicesByDate(co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(
			ReportsPendingServicesByDateFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		return coreFacadeHspBeanLocal.getReportsPendingServicesByDate(filter, requestInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.ICoreHspWS#reportsProductivityReport(co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReportsProductivityReportAndFileResponseDTO reportsProductivityReport(
			ReportsComplyAndScheduleFilterDTO filter,
			RequestCollectionInfo requestInfo) throws BusinessException {
		return coreFacadeHspBeanLocal.reportsProductivityReport(filter, requestInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.ICoreHspWS#getReportWorkOrderRejection(co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ReportWorkOrderRejectionAndFileResponseDTO getReportWorkOrderRejection( ReportWorkOrderRejectionFilterDTO filter,
            RequestCollectionInfo requestInfo) throws BusinessException {
		
		return coreFacadeHspBeanLocal.getReportWorkOrderRejection(filter, requestInfo);
		
	}

	@Override
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(
			ReportsSucceedWorkOrderFilterDTO filter,
			RequestCollectionInfo requestInfo)	throws BusinessException {
		return coreFacadeHspBeanLocal.getReportsSucceedWorkOrderCSR(filter, requestInfo);
	}  
	
}
