package co.com.directv.sdii.facade.report.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.xml.bind.annotation.XmlMimeType;

import co.com.directv.sdii.ejb.business.report.ReportGeneratorInventoryModule01BusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.report.ReportGeneratorInventoryModule01FacadeLocal;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

/**
 * 
 * Implementación de interfaz que expone metodos para generar reportes 
 * 
 * Fecha de Creación: 14/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="ReportGeneratorInventoryModule01FacadeLocal", mappedName="ejb/ReportGeneratorInventoryModule01FacadeLocal")
public class ReportGeneratorInventoryModule01Facade implements ReportGeneratorInventoryModule01FacadeLocal {
	
	@EJB
	private ReportGeneratorInventoryModule01BusinessBeanLocal reportGeneratorInventoryModule01BusinessBean;

	@Override
	public FileResponseDTO getMovementQueueHspToIbsByFilter(
			MovCmdQueueFilterDTO filter) throws BusinessException {
		return reportGeneratorInventoryModule01BusinessBean.getMovementQueueHspToIbsByFilter(filter);
	}

	@Override
	public FileResponseDTO getImportLogByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria, Long userId)
			throws BusinessException {
		return reportGeneratorInventoryModule01BusinessBean.getImportLogByCriteria(modifyImportLogCriteria, userId);
	}
	
	@Override
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements)
			throws BusinessException {
		return reportGeneratorInventoryModule01BusinessBean.getImportLogItemsByImportLogIdAndIsSerialized(filterImportLogElements);
	}

	@Override
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsNotSerialized(
			ImportLogElementsFilterDTO filterImportLogElements)
			throws BusinessException {
		return reportGeneratorInventoryModule01BusinessBean.getImportLogItemsByImportLogIdAndIsNotSerialized(filterImportLogElements);
	}
	
	@Override
	public FileResponseDTO searchAdjustmentsBySearchParameters(AdjustmentRequestDTO filter) throws BusinessException {
		return reportGeneratorInventoryModule01BusinessBean.searchAdjustmentsBySearchParameters(filter);
	}

	@Override
	public FileResponseDTO getAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request) throws BusinessException{
		return reportGeneratorInventoryModule01BusinessBean.getAdjustmentElementsForAuthorization(request);
	}

}
