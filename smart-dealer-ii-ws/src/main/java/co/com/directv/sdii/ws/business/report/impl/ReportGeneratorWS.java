package co.com.directv.sdii.ws.business.report.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.WorkOrderCancelFacadeLocal;
import co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.ws.business.report.IReportGeneratorWS;

@MTOM(threshold=3072)
@WebService(serviceName="ReportGeneratorWS",
		endpointInterface="co.com.directv.sdii.ws.business.report.IReportGeneratorWS",
		targetNamespace="http://report.business.ws.sdii.directv.com.co/",
		portName="ReportGeneratorPort")	
@Stateless()
public class ReportGeneratorWS implements IReportGeneratorWS {
	
	@EJB
	private ReportGeneratorFacadeLocal reportGeneratorFacade;
	
	@EJB
	private WorkOrderCancelFacadeLocal workOrderCancelFacadeLocal;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	public FileResponseDTO getWorkOrdersForReport(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return reportGeneratorFacade.getWorkOrdersForReport(filter);
	}

/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return reportGeneratorFacade.getWorkOrdersForReportAttentionFinalization(filter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	public FileResponseDTO getWorkOrdersForReportForAllocator(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return reportGeneratorFacade.getWorkOrdersForReportForAllocator(filter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getCustomerMediaContacts(java.lang.String)
	 */
	@Override
	public FileResponseDTO getCustomerMediaContacts(String customerCode) throws BusinessException {
		return reportGeneratorFacade.getCustomerMediaContacts(customerCode);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrderServices(java.lang.Long)
	 */
	@Override
	public FileResponseDTO getWorkOrderServices(Long woId) throws BusinessException {
		return reportGeneratorFacade.getWorkOrderServices(woId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getShippingOrderElements(java.lang.Long)
	 */
	@Override
	public FileResponseDTO getShippingOrderElements(Long soId) throws BusinessException {
		return reportGeneratorFacade.getShippingOrderElements(soId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getDealerMediaContacts(java.lang.Long)
	 */
	@Override
	public FileResponseDTO getDealerMediaContacts(Long dealerCode) throws BusinessException {
		return reportGeneratorFacade.getDealerMediaContacts(dealerCode);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrderContacts(java.lang.String)
	 */
	@Override
	public FileResponseDTO getWorkOrderContacts(String woCode) throws BusinessException {
		return reportGeneratorFacade.getWorkOrderContacts(woCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#generateReport(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public FileResponseDTO generateReport(String cmd, String args,
			String fileName, String reportExtension) throws BusinessException {
		return reportGeneratorFacade.generateReport(cmd, args, fileName, reportExtension);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrdersForReportForGroupAction(java.util.List, java.lang.Long)
	 */
	@Override
	public FileResponseDTO getWorkOrdersForReportForGroupAction(List<String> woCodes,Long countryId, Long userId) throws BusinessException {
		return reportGeneratorFacade.getWorkOrdersForReport(woCodes, countryId, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#generateWorkOrderPDF(java.util.List, java.lang.Long)
	 */
	@Override
	public FileResponseDTO generateWorkOrderPDF(List<String> workOrderCodes,Long countryId) throws BusinessException {
		return reportGeneratorFacade.generateWorkOrderPDF(workOrderCodes, countryId);
	}

	@Override
	public FileResponseDTO getReferencesByFilterForExcel(ReferencesFilterDTO referenceDTO) throws BusinessException {
		return reportGeneratorFacade.getReferencesByFilterForExcel(referenceDTO);
	}

	@Override
	public FileResponseDTO getTransferReasonByFilter(String transferReasonName) throws BusinessException {
		return reportGeneratorFacade.getTransferReasonByFilter(transferReasonName);
	}

	@Override
	public FileResponseDTO getAllWarehousesByCountryId(Long countryId,String code) throws BusinessException {
		return reportGeneratorFacade.getAllWarehousesByCountryId(countryId, code);
	}

	/*
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWhByWhTypeDealerBranchCrewIds(java.util.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */	
	@Override
	public FileResponseDTO getWhByWhTypeDealerBranchCrewIds(Long countryId,
																String  warehouseTypeId, 
																String  dealerId, 
																String  branchId,
																String  crewId 
    ) throws BusinessException {
		return reportGeneratorFacade.getWhByWhTypeDealerBranchCrewIds( countryId, 
																		   warehouseTypeId, 
																		   dealerId, 
																		   branchId,
																		   crewId 
																		);
		
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrdersForReportAttentionFinalizationGroupAction(java.util.List, java.lang.Long)
	 */
	@Override
	public FileResponseDTO getWorkOrdersForReportAttentionFinalizationGroupAction( List<String> woCodes, Long countryId) throws BusinessException {
		return reportGeneratorFacade.getWorkOrdersForReportAttentionFinalization(woCodes, countryId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.report.IReportGeneratorWS#getWorkOrdersForReportForAllocatorGroupAction(java.util.List, java.lang.Long)
	 */
	@Override
	public FileResponseDTO getWorkOrdersForReportForAllocatorGroupAction( List<String> woCodes, Long countryId) throws BusinessException {
		return reportGeneratorFacade.getWorkOrdersForReportForAllocator(woCodes, countryId);
	}

	@Override
	public FileResponseDTO getWareHouseElementsActualByCustomer(
			WareHouseElementClientFilterRequestDTO request)
			throws BusinessException {
		return reportGeneratorFacade.getWareHouseElementsActualByCustomer(request);
	}

	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @return archivo de resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public FileResponseDTO getCanceledWorkOrdersReport(
			WorkOrderCanceledFilterDTO filter) throws BusinessException {
		return workOrderCancelFacadeLocal.getCanceledWorkOrdersReport(filter);
	}
	// Req export planilla tecnico excel
	@Override
	public FileResponseDTO generateCrewWorkOrdersExcel(Long countryId, List<Long> workOrderIds, List<Long> crewIds)throws BusinessException {
		return reportGeneratorFacade.generateCrewWorkOrdersExcel(countryId, workOrderIds,crewIds);
	}

}
