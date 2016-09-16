package co.com.directv.sdii.facade.report.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

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
@Stateless(name="ReportGeneratorFacadeLocal", mappedName="ejb/ReportGeneratorFacadeLocal")
public class ReportGeneratorFacade implements ReportGeneratorFacadeLocal {
	
	@EJB
	private ReportGeneratorBusinessBeanLocal reportGeneratorBusinessBean;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReport(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrdersForReport(filter);
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrdersForReportAttentionFinalization(filter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportForAllocator(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrdersForReportForAllocator(filter);
	}
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getCustomerMediaContacts(java.lang.String)
	 */
	@Override
	public FileResponseDTO getCustomerMediaContacts(String customerCode)throws BusinessException {
		return reportGeneratorBusinessBean.getCustomerMediaContacts(customerCode);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getWorkOrderServices(java.lang.Long)
	 */
	@Override
	public FileResponseDTO getWorkOrderServices(Long woId) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrderServices(woId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getShippingOrderElements(java.lang.Long)
	 */
	@Override
	public FileResponseDTO getShippingOrderElements(Long soId) throws BusinessException {
		return reportGeneratorBusinessBean.getShippingOrderElements(soId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getDealerMediaContacts(java.lang.Long)
	 */
	@Override
	public FileResponseDTO getDealerMediaContacts(Long dealerCode) throws BusinessException {
		return reportGeneratorBusinessBean.getDealerMediaContacts(dealerCode);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getWorkOrderContacts(java.lang.String)
	 */
	@Override
	public FileResponseDTO getWorkOrderContacts(String woCode) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrderContacts(woCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#generateReport(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public FileResponseDTO generateReport(String cmd, String args,
			String fileName, String reportExtension) throws BusinessException {
		return reportGeneratorBusinessBean.generateReport(cmd, args, fileName, reportExtension);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getWorkOrdersForReport(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReport(List<String> woCodes,Long countryId, Long userId) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrdersForReport(woCodes, countryId, userId)
		;
	}

	@Override
	public FileResponseDTO generateWorkOrderPDF(List<String> workOrderCodes,Long countryId) throws BusinessException {
		return reportGeneratorBusinessBean.generateWorkOrderPDF(workOrderCodes, countryId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.report.ReportGeneratorFacadeLocal#getReferencesByFilterForExcel(co.com.directv.sdii.model.dto.ReferencesFilterDTO)
	 */
	@Override
	public FileResponseDTO getReferencesByFilterForExcel(ReferencesFilterDTO referenceDTO) throws BusinessException {
		return reportGeneratorBusinessBean.getReferencesByFilterForExcel(referenceDTO);
	}

	@Override
	public FileResponseDTO getTransferReasonByFilter(String transferReasonName) throws BusinessException {
		return reportGeneratorBusinessBean.getTransferReasonByFilter(transferReasonName);
	}

	@Override
	public FileResponseDTO getAllWarehousesByCountryId(Long countryId,String code) throws BusinessException {
		return reportGeneratorBusinessBean.getAllWarehousesByCountryId(countryId, code);
	}
	
	@Override
	public FileResponseDTO getWhByWhTypeDealerBranchCrewIds( Long countryId, 
																 String  warehouseTypeId, 
																 String  dealerId, 
																 String  branchId,
																 String  crewId
	) throws BusinessException {
		return reportGeneratorBusinessBean.getWhByWhTypeDealerBranchCrewIds( countryId, 
																			     warehouseTypeId, 
																			     dealerId, 
																			     branchId,
																			     crewId
                                                                                );
		
	}	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportForAllocator( List<String> woCodes, Long countryId) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrdersForReportForAllocator(woCodes, countryId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization( List<String> woCodes, Long countryId) throws BusinessException {
		return reportGeneratorBusinessBean.getWorkOrdersForReportAttentionFinalization(woCodes, countryId);
	}


	@Override
	public FileResponseDTO getWareHouseElementsActualByCustomer(
			WareHouseElementClientFilterRequestDTO request)
			throws BusinessException {
		return reportGeneratorBusinessBean.getWareHouseElementsActualByCustomer(request);
	}

	@Override
	public FileResponseDTO generateCrewWorkOrdersExcel(Long countryId, List<Long> workOrderIds , List<Long> crewId)throws BusinessException {
		return reportGeneratorBusinessBean.generateCrewWorkOrdersExcel(countryId, workOrderIds , crewId);
	}

}
