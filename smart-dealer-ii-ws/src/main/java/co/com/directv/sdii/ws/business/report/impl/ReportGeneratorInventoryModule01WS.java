
package co.com.directv.sdii.ws.business.report.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.report.ReportGeneratorInventoryModule01FacadeLocal;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;
import co.com.directv.sdii.ws.business.report.IReportGeneratorInventoryModule01WS;

@MTOM(threshold=3072)
@WebService(serviceName="ReportGeneratorInventoryModule01WS",
		endpointInterface="co.com.directv.sdii.ws.business.report.IReportGeneratorInventoryModule01WS",
		targetNamespace="http://report.business.ws.sdii.directv.com.co/",
		portName="ReportGeneratorInventoryModule01Port")	
@Stateless()
public class ReportGeneratorInventoryModule01WS implements IReportGeneratorInventoryModule01WS {
	
	@EJB
	private ReportGeneratorInventoryModule01FacadeLocal reportGeneratorInventoryModule01Facade;

	@Override
	public FileResponseDTO getMovementQueueHspToIbsByFilter(
			MovCmdQueueFilterDTO filter) throws BusinessException {
		return reportGeneratorInventoryModule01Facade.getMovementQueueHspToIbsByFilter(filter);
	}
	
	@Override
	public FileResponseDTO getImportLogByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria, Long userId) throws BusinessException {
		return reportGeneratorInventoryModule01Facade.getImportLogByCriteria(modifyImportLogCriteria, userId);
	}

	@Override
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements) throws BusinessException{
		return reportGeneratorInventoryModule01Facade.getImportLogItemsByImportLogIdAndIsSerialized(filterImportLogElements);
	}
	
	@Override
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsNotSerialized(
			ImportLogElementsFilterDTO filterImportLogElements) throws BusinessException{
		return reportGeneratorInventoryModule01Facade.getImportLogItemsByImportLogIdAndIsNotSerialized(filterImportLogElements);
	}

	@Override
	public FileResponseDTO searchAdjustmentsBySearchParameters(AdjustmentRequestDTO filter) throws BusinessException {
		return reportGeneratorInventoryModule01Facade.searchAdjustmentsBySearchParameters(filter);
	}
	
	@Override
	public FileResponseDTO getAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request) throws BusinessException{
		return reportGeneratorInventoryModule01Facade.getAdjustmentElementsForAuthorization(request);
	}
	
}
