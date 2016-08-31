package co.com.directv.sdii.ws.business.core.tray.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.tray.TrayWorkOrderManagmentFacadeLocal;
import co.com.directv.sdii.model.dto.EmployeeCrewDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.VerifyDesconectionSerialsDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WorkOrderServiceResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderTrayResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ExpirationGroupingVO;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
import co.com.directv.sdii.ws.business.core.tray.ITrayWorkOrderManagmentWS;

/**
 * Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compañía Instaladora
 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignación Manual o Cancelación
 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
 * Caso de Uso ADS - 65 - Atencion y Finalizacion Work Orders
 * Caso de Uso ADS - 66 - Finalizacion Work Orders
 * Implementa de las operaciones de neegocio para
 * la gestion de Workorders.
 * 
 * Fecha de Creación: 6/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@MTOM(threshold=3072)
@WebService(serviceName="TrayWorkorderManagmentService",
		endpointInterface="co.com.directv.sdii.ws.business.core.tray.ITrayWorkOrderManagmentWS",
		targetNamespace="http://directvla.com/contract/sdii/trayworkorders",
		portName="TrayWorkorderManagmentPort")	
@Stateless()
public class TrayWorkorderManagmentWS implements ITrayWorkOrderManagmentWS {
	
	@EJB
	private TrayWorkOrderManagmentFacadeLocal trayWOFacade;
	
	@Override
	public WorkOrderTrayResponse getWorkOrdersAttentionFinalization(WorkOrderFilterTrayDTO filterDTO, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return trayWOFacade.getWorkOrdersAttentionFinalization(filterDTO, requestCollectionInfo);		
	}
	
	@Override
	public WorkOrderServiceResponse getWorkOrderServices(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return trayWOFacade.getWorkOrderServices(woId, requestCollectionInfo);
	}

	@Override
	public WorkOrderServiceResponse getWorkOrderServicesAttention(Long woId,	RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return trayWOFacade.getWorkOrderServicesAttention(woId, requestCollectionInfo);
	}

	@Override
	public WorkOrderServiceResponse getWorkOrderServicesFinalization(Long woId, RequestCollectionInfo requestCollectionInfo)	throws BusinessException {
		return trayWOFacade.getWorkOrderServicesFinalization(woId, requestCollectionInfo);
	}

	@Override
	public void attentionWorkOrder(TrayWOManagmentDTO request)	throws BusinessException {
		trayWOFacade.attentionWorkOrder(request);	
	}

	@Override
	public List<ServiceVO> getServicesByWoTypeId(Long woTypeId)throws BusinessException {
		return trayWOFacade.getServicesByWoTypeId(woTypeId);
	}
	
	@Override
	public List<WorkorderStatusVO> getDealerTrayFilterStatuss() throws BusinessException {
		return trayWOFacade.getDealerTrayFilterStatuss();
	}

	@Override
	public WorkOrderTrayDTO getWorkorderDetail(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return trayWOFacade.getWorkorderDetail(filter);
	}

	@Override
	public List<ExpirationGroupingVO> getAllExpirationGroupingVO(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return trayWOFacade.getAllExpirationGroupingVO(filter);
	}

	@Override
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerId(Long countryId, Long dealerId) throws BusinessException {
		return trayWOFacade.getAllResponsableEmployeeCrewByCountryAndDealerId(countryId, dealerId);
	}

	@Override
	public List<ServiceCategoryVO> getAllServiceCategories() throws BusinessException {
		return trayWOFacade.getAllServiceCategories();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.tray.ITrayWorkOrderManagmentWS#getServiceCategoryByTypesId(java.util.List)
	 */
	@Override
	public List<ServiceCategoryVO> getServiceCategoryByTypesId(
			List<Long> typesId) throws BusinessException {
		return trayWOFacade.getServiceCategoryByTypesId(typesId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.tray.ITrayWorkOrderManagmentWS#getActiveServicesByServiceCategories(java.util.List)
	 */
	@Override
	public List<ServiceVO> getActiveServicesByServiceCategories(
			List<Long> categoriesId) throws BusinessException {
		return trayWOFacade.getActiveServicesByServiceCategories(categoriesId);
	}
	
	@Override
	public List<WoTypeVO> getAllWorkorderTypes() throws BusinessException {
		return trayWOFacade.getAllWorkorderTypes();
	}

	@Override
	public List<ProgramVO> getActiveProgramsByDealerId(Long dealerId)throws BusinessException {
		return trayWOFacade.getActiveProgramsByDealerId(dealerId);
	}

	@Override
	public List<DealerVO> getSaleDealersByWoAsignDealerIdAndCountry(Long dealerId, Long countryId) throws BusinessException {
		return trayWOFacade.getSaleDealersByWoAsignDealerIdAndCountry(dealerId, countryId);
	}

	@Override
	public RequiredServiceElementVO getRequiredServiceElements(TrayWOManagmentDTO request) throws BusinessException {
		return trayWOFacade.getRequiredServiceElements(request); 
	}

	@Override
	public WorkOrderTrayResponse getWorkOrdersForDealerTray(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo)	throws BusinessException {
		return trayWOFacade.getWorkOrdersForDealerTray(filter, requestCollectionInfo);
	}

	@Override
	public EnvelopeEncapsulateResponse workOrderFinalization(List<TrayWOManagmentDTO> trayRequest) throws BusinessException {
		return trayWOFacade.workOrderFinalization(trayRequest);
	}

	@Override
	public WorkOrderTrayResponse getWorkOrdersForAllocator(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return trayWOFacade.getWorkOrdersForAllocator(filter, requestCollectionInfo);
	}

	@Override
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException {
		try {
			return trayWOFacade.getAllServiceType();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
		
	}	
	
    @Override
    public List<WorkorderStatusVO> getWorkOrderStatusForDealerTray() throws BusinessException {
    	return trayWOFacade.getWorkOrderStatusForDealerTray();
    }

	@Override
	public SerializedVO getElementBySerialCode(TrayWOManagmentDTO request) throws BusinessException {
		return trayWOFacade.getElementBySerialCode(request);
	}
	
	/**
	 * Metodo encargado de validar los seriales ingresados para la atencion de una work order de desconexion
	 * @param request
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public void verifyDesconectionSerials(VerifyDesconectionSerialsDTO request) throws BusinessException {
		trayWOFacade.verifyDesconectionSerials(request);
	}
	
	/**
	 * Metodo que verifica en la atencion de una work order de desconexion si los seriales ingresados coinciden con los asociados al servicio
	 * @param request datos de los seriales, la work order y el servicio al cual se le realizara la validacion
	 * @throws BusinessException
	 * @author aharker
	 */
	public String getSerialForAttentionDesconectionWO(Long woId, Long woServiceId) throws BusinessException{
		return trayWOFacade.getSerialForAttentionDesconectionWO(woId,woServiceId);
	}
	
	@Override
	public List<CrewVO> getCrewsByDealerId(Long dealerId) throws BusinessException {
		return trayWOFacade.getCrewsByDealerId(dealerId);
	}

	@Override
	public void validateWorkOrdersAgenda(List<Long> workOrderIds) throws BusinessException {
		trayWOFacade.validateWorkOrdersAgenda(workOrderIds);
	}

	@Override
	public List<EnvelopeEncapsulateResponse> validateWoBeforeGeneratingPdf(List<Long> woIds, Long countryId) throws BusinessException {
		return trayWOFacade.validateWoBeforeGeneratingPdf(woIds, countryId);
	}

	@Override
	public List<WorkOrderTrayDTO> getWorkordersFinalization(TrayWOManagmentDTO trayRequest) throws BusinessException {
		return trayWOFacade.getWorkordersFinalization(trayRequest);
	}

	@Override
	public List<WorkOrderTrayDTO> getWorkordersToSchedule(TrayWOManagmentDTO trayRequest) throws BusinessException {
		return trayWOFacade.getWorkordersToSchedule(trayRequest);
	}

	@Override
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerIds(Long countryId, List<Long> dealerIds) throws BusinessException {		
		return trayWOFacade.getAllResponsableEmployeeCrewByCountryAndDealerIds(countryId, dealerIds);
	}
	
	@Override
	public List<DealerVO> getSaleDealersByWoAsignDealerIdsAndCountry(List<Long> dealerIds, Long countryId,
			String isSeller, String isInstaller) throws BusinessException {
		return trayWOFacade.getSaleDealersByWoAsignDealerIdsAndCountry(dealerIds, countryId, isSeller, isInstaller);
	}
	
	@Override
	public List<ProgramVO> getActiveProgramsByDealerIds(List<Long> dealerIds)throws BusinessException {
		return trayWOFacade.getActiveProgramsByDealerIds(dealerIds);
	}

	
	@Override
	public CustomerVO getCustomerResources(String customerCode, Long userId) throws BusinessException {
		return trayWOFacade.getCustomerResources(customerCode, userId);
	}
	
	@Override
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForDificulty(Long woId) throws BusinessException{
		return trayWOFacade.getWorkOrdersForTheSameClientForDificulty(woId);
	}
	
	@Override
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForReject(Long woId) throws BusinessException{
		return trayWOFacade.getWorkOrdersForTheSameClientForReject(woId);
	}
	
	@Override
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignDinamicCrew(List<Long> woIds) throws BusinessException{
		return trayWOFacade.getWorkOrdersForTheSameClientForAssignDinamicCrew(woIds);
	}
	
	@Override
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignStaticCrew(List<Long> woIds) throws BusinessException{
		return trayWOFacade.getWorkOrdersForTheSameClientForAssignStaticCrew(woIds);
	}
	
}
